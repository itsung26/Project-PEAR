# Project PEAR

Open-source network drop simulator for Windows.

Licensed under the [MIT License](LICENSE).

---

## Features

- Instant lag on/off via Windows Firewall (`blockinbound,blockoutbound` ↔ restore outbound)
- Global hotkey keyboard keys or mouse buttons (works while other apps are focused)
- Click **Hotkey** in the UI to set a new keybind
- Live status bar: **Lag inactive** / **Lag active**

---

## Requirements

- **Windows** (64-bit)
- **Administrator privileges** (required to change firewall rules)

---

## Download

Get the latest build from the [GitHub Releases](../../releases) page.
Available in portable as well as installer format.

Windows may show a warning on first run due to an unsigned build choose **More info** and then **Run anyway**

---

## Install

### Installer

1. Run `PEAR_installer_edition.exe`.
2. Approve administrator prompt if prompted.
3. Launch **PEAR** from the Start Menu (under **Project PEAR**) or the Desktop shortcut.
4. Approve administrator prompt again when the app starts (it must run as administrator).

Default install location: `C:\Program Files\PEAR\`

### Portable

1. Unzip `PEAR_portable_edition.zip`.
2. Open the `PEAR` folder and run `PEAR.exe`.
3. Keep the folder layout intact (`PEAR.exe`, `app\`, `runtime\`, `assets\`).

---

## How it works

PEAR does **not** disable the network adapter. It runs:

- **Lag on:** `netsh advfirewall set allprofiles firewallpolicy blockinbound,blockoutbound`
- **Lag off:** `netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound`

That avoids the reconnect delay you get from dropping Wi‑Fi or unplugging Ethernet.

---

## Updating

Install a newer release installer over the existing one. For portable builds, replace the unzipped folder with the new zip contents.

---

## Disclaimer

**Not for cheating.** PEAR is for education and local / lab testing of network failure behavior. Most online games detect or punish artificial latency and packet loss; using a lag switch in multiplayer will likely get you banned. Use only on systems and networks you own or have permission to test.
