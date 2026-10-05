# Project PEAR

Open-source **network drop simulator** for testing how apps behave when traffic is briefly blocked.

PEAR toggles firewall rules to block or allow traffic on demand, so you can simulate lag / disconnects **without** tearing down Wi‑Fi or Ethernet (and without waiting for a reconnect).

---

## Features

- Instant network state toggle (block / allow via firewall rules)
- Bindable hotkeys — mouse buttons and single keyboard keys
- Notification sound on enable / disable

---

## How it works

Rather than disabling the network adapter, PEAR manages **firewall rules** for incoming (and related) traffic. That avoids the reconnect delay you get when dropping Wi‑Fi or unplugging Ethernet, so lag can be switched on and off immediately.

---

## Requirements

- **Windows**
- **Administrator privileges** (needed to change firewall rules)

---

## Disclaimer

**Not for cheating.** PEAR is for education and local / lab testing of network failure behavior. Most online games detect or punish artificial latency and packet loss; using a lag switch in multiplayer will likely get you banned. Use only in environments you own or have permission to test.
