# Greeter Bro

**Greeter Bro** is a simple Minecraft mod that automatically greets players when they join the game. It's fully configurable via the **Mod Menu** UI and is perfect for community servers that want to add a friendly touch.

It supports and integrates with [**Styled Chat**](https://modrinth.com/mod/styled-chat), detecting its formatted join messages and responding accordingly, making Greeter Bro compatible with customized chat systems used by many modded servers.

Also, GreeterBro detects silent join messages from the [**Vanish**](https://modrinth.com/mod/vanish) mod and ignores them, ensuring vanished players are not greeted publicly.

---

## 🎉 Features

- **Auto Greeting on Join** – Automatically sends a chat message to welcome players.
- **First-Time Join Support** – Show a different message for new players.
- **Name Change Detection** – Optionally greet players when they join under a new name.
- **Player Blacklist** – Prevent specific players from being greeted.
- **Randomized Delay** – Greetings are sent after a random delay within a configurable time range.
- **Configurable via Mod Menu** – No need to edit config files manually.
- **%player% Placeholder Support** – Insert the joining player’s name into greetings.

---

## ⚙️ Configuration

All options are available in-game via the **Mod Menu** UI (requires the [Mod Menu](https://modrinth.com/mod/modmenu) mod).

### Settings

- **Enable Join Greetings** – Toggle greeting functionality on or off.
- **Join Message** – The message sent to regular players.
- **First Join Message** – Message shown only the first time a player joins.
- **Greet on Name Change** – Whether to greet players when they log in with a different name than last time.
- **Blacklist** – A list of usernames that should not be greeted.
- **Delay Range** – Set minimum and maximum delay (in ticks). A random number in this range will be chosen each time.

---

## 📘 Commands

Greeter Bro includes a few in-game commands to help you manage greetings directly from within Minecraft.

| Command                                  | Description                                               | Example                              |
|------------------------------------------|-----------------------------------------------------------|--------------------------------------|
| `/greeterbro blacklist list`             | Lists all players currently on the blacklist.             | `/greeterbro blacklist`              |
| `/greeterbro blacklist add <player>`     | Adds a player to the blacklist to prevent greetings.      | `/greeterbro blacklist add Steve`    |
| `/greeterbro blacklist remove <player>`  | Removes a player from the blacklist.                      | `/greeterbro blacklist remove Steve` |


---

## 🧩 Requirements

- Minecraft (supported versions listed in the mod's release info)
- [Mod Menu](https://modrinth.com/mod/modmenu) (for configuration)

---

## 📦 Installation

1. Download **Greeter Bro** from the [releases](https://modrinth.com/mod/greeterbro/versions) page.
2. Place the `.jar` file in your Minecraft `mods` folder.
3. Launch the game with a compatible mod loader.
4. Configure the mod via Mod Menu in the title screen or pause menu.

---

## 🙋 Support & Feedback

Have an idea or found a bug? Open an issue or contribute on the [GitHub repository](https://github.com/OrdinarySMP/GreeterBro).
