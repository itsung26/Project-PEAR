# Third-Party Notices

Project PEAR redistributes or links against the following third-party software.
This file is for attribution only and does not grant any license to PEAR itself.
Full license texts for the bundled JDK are included under `runtime/legal/` in
packaged builds.

---

## Eclipse Temurin / OpenJDK (Java runtime)

- **Component:** Bundled application runtime produced by `jpackage`
- **Project:** [Eclipse Temurin](https://adoptium.net/) / OpenJDK
- **License:** GNU General Public License, version 2, with the Classpath Exception
- **More information:** See `runtime/legal/` in the installed or portable app image
  (per-module `LICENSE`, `ASSEMBLY_EXCEPTION`, and related notices)

---

## OpenJFX (JavaFX)

- **Component:** JavaFX libraries used for the UI  
  (`javafx.base`, `javafx.graphics`, `javafx.controls`, `javafx.fxml`, and native binaries under `jfx-bin/`)
- **Project:** [OpenJFX](https://openjdk.org/projects/openjfx/)
- **License:** GNU General Public License, version 2, with the Classpath Exception  
  (same licensing model as OpenJDK for the OpenJFX distribution commonly used with Temurin)
- **Source:** https://github.com/openjdk/jfx

---

## JNativeHook

- **Component:** Global keyboard and mouse hooks  
  (`jnativehook-2.2.2.jar`, `JNativeHook-2.2.2.x86_64.dll`)
- **Project:** [kwhat/jnativehook](https://github.com/kwhat/jnativehook)
- **Version:** 2.2.2
- **License:** GNU Lesser General Public License, version 3.0 (LGPL-3.0),  
  as an extension of the GNU General Public License, version 3.0
- **License texts:**  
  - https://github.com/kwhat/jnativehook/blob/2.2/COPYING.LESSER.md  
  - https://github.com/kwhat/jnativehook/blob/2.2/COPYING.md
- **Notes:** PEAR uses JNativeHook as a separate shared library/jar and does not
  modify its source. If you modify JNativeHook and redistribute those changes,
  LGPL terms for the modified library apply.

---

## Other notices

Packaged Windows builds may also include Microsoft Visual C++ runtime DLLs and
related redistributable components required by the JDK / JavaFX native libraries.
Those are redistributed under their respective Microsoft terms as provided with
the JDK/JavaFX binary distribution.
