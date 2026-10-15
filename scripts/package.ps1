# Builds the Windows app image, portable zip, and exe installer described in the README.
# Requires a JDK with jpackage, and WiX Toolset 3 (candle.exe / light.exe) for the installer.
param(
    [string]$Version = "1.0.0"
)

$ErrorActionPreference = "Stop"

$Root = Resolve-Path (Join-Path $PSScriptRoot "..")
Set-Location $Root

if ($Version -match '^v?(\d+\.\d+(?:\.\d+)?)') {
    $AppVersion = $Matches[1]
} else {
    $AppVersion = "1.0.0"
}

# Stable across releases so a newer installer upgrades an existing install.
$UpgradeUuid = "c4a7e2b1-9d3f-4a6e-8c15-7b2d4e6f8a90"

$OutDir = Join-Path $Root "out"
$InputDir = Join-Path $Root "dist\input"
$ImageDir = Join-Path $Root "dist\PEAR"
$ReleaseDir = Join-Path $Root "release"
$JavaHome = (Get-Item (Get-Command java).Source).Directory.Parent.FullName
$ModulePath = @(
    (Join-Path $JavaHome "jmods"),
    "lib\javafx.base.jar",
    "lib\javafx.graphics.jar",
    "lib\javafx.controls.jar",
    "lib\javafx.fxml.jar"
) -join ";"

function Find-Mt {
    $kits = "C:\Program Files (x86)\Windows Kits\10\bin"
    if (-not (Test-Path $kits)) {
        return $null
    }
    return Get-ChildItem $kits -Recurse -Filter mt.exe -ErrorAction SilentlyContinue |
        Where-Object { $_.FullName -match '\\x64\\mt.exe$' } |
        Sort-Object FullName -Descending |
        Select-Object -First 1 -ExpandProperty FullName
}

Write-Host "Compiling (app version $AppVersion)"
if (Test-Path $OutDir) { Remove-Item $OutDir -Recurse -Force }
if (Test-Path (Join-Path $Root "dist")) { Remove-Item (Join-Path $Root "dist") -Recurse -Force }
New-Item -ItemType Directory -Force -Path $InputDir, $ReleaseDir | Out-Null

$sources = Get-ChildItem (Join-Path $Root "src") -Recurse -Filter *.java | ForEach-Object { $_.FullName }
& javac --module-path $ModulePath --add-modules javafx.controls,javafx.fxml -cp "lib\jnativehook-2.2.2.jar" -d $OutDir @sources
if ($LASTEXITCODE -ne 0) { throw "javac failed ($LASTEXITCODE)" }

& jar --create --file (Join-Path $InputDir "pear.jar") -C $OutDir .
if ($LASTEXITCODE -ne 0) { throw "jar failed ($LASTEXITCODE)" }
Copy-Item "lib\jnativehook-2.2.2.jar" $InputDir

Write-Host "Creating application image"
& jpackage `
    --type app-image `
    --dest dist `
    --name PEAR `
    --app-version $AppVersion `
    --vendor "Project PEAR" `
    --description "Lightweight lag switch utility" `
    --icon "assets\pear.ico" `
    --input $InputDir `
    --main-jar pear.jar `
    --main-class com.projectpear.App `
    --module-path $ModulePath `
    --add-modules javafx.controls,javafx.fxml `
    --java-options "-Djava.library.path=`$APPDIR/../runtime/bin" `
    --app-content "assets"
if ($LASTEXITCODE -ne 0) { throw "jpackage app-image failed ($LASTEXITCODE)" }

$runtimeBin = Join-Path $ImageDir "runtime\bin"
New-Item -ItemType Directory -Force -Path $runtimeBin | Out-Null
# JavaFX natives are not inside the modular jars. Copy any that the runtime does not already have.
Get-ChildItem "jfx-bin" -Filter *.dll | ForEach-Object {
    $dest = Join-Path $runtimeBin $_.Name
    if (-not (Test-Path $dest)) {
        Copy-Item $_.FullName $dest
    }
}

$assetsDest = Join-Path $ImageDir "assets"
if (-not (Test-Path (Join-Path $assetsDest "pear.png"))) {
    New-Item -ItemType Directory -Force -Path $assetsDest | Out-Null
    Copy-Item "assets\*" $assetsDest -Force
}

$mt = Find-Mt
if (-not $mt) {
    throw "mt.exe was not found. Install the Windows SDK so PEAR.exe can require administrator."
}
Write-Host "Embedding administrator manifest"
$launcher = Join-Path $ImageDir "PEAR.exe"
(Get-Item $launcher).IsReadOnly = $false
& $mt -nologo -manifest "pear.manifest" -outputresource:"$launcher;#1"
if ($LASTEXITCODE -ne 0) { throw "mt.exe failed ($LASTEXITCODE)" }

Write-Host "Creating portable zip"
$portableZip = Join-Path $ReleaseDir "PEAR_portable_edition.zip"
if (Test-Path $portableZip) { Remove-Item $portableZip -Force }
Compress-Archive -Path $ImageDir -DestinationPath $portableZip

if (-not (Get-Command candle -ErrorAction SilentlyContinue)) {
    throw "candle.exe was not found. Install WiX Toolset 3 and add it to PATH to build the installer."
}

Write-Host "Creating installer"
& jpackage `
    --type exe `
    --dest $ReleaseDir `
    --name PEAR `
    --app-version $AppVersion `
    --vendor "Project PEAR" `
    --description "Lightweight lag switch utility" `
    --icon "assets\pear.ico" `
    --app-image $ImageDir `
    --license-file LICENSE `
    --about-url "https://github.com/itsung26/Project-PEAR" `
    --win-menu `
    --win-menu-group "Project PEAR" `
    --win-shortcut `
    --win-dir-chooser `
    --install-dir PEAR `
    --win-upgrade-uuid $UpgradeUuid
if ($LASTEXITCODE -ne 0) { throw "jpackage exe failed ($LASTEXITCODE)" }

$generatedInstaller = Join-Path $ReleaseDir "PEAR-$AppVersion.exe"
$installer = Join-Path $ReleaseDir "PEAR_installer_edition.exe"
if (-not (Test-Path $generatedInstaller)) {
    throw "Expected installer was not created: $generatedInstaller"
}
if (Test-Path $installer) { Remove-Item $installer -Force }
Move-Item $generatedInstaller $installer

Write-Host "Portable:  $portableZip"
Write-Host "Installer: $installer"
