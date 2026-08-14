# Welcome to Locatix!

A Better and More Advanced Coordinate Plugin/Mod.


# Plugin

This plugin provides an advanced coordinate system for your players. It is also almost fully configurable.

## Features
1. All world names can be custom named.
2. Server language and a language system where players can choose their own language are available.
3. You can reach players, change the server language, and reload the plugin via the admin panel.
4. Players can teleport to their last death location when they die. (Configurable)
5. Players can share their coordinates with each other. The player receiving coordinates hears a notification sound, and the sender enters a cooldown to use the command. (Configurable)
6. Players' language preferences and whether coordinate display is enabled are stored in `data/playerdata.yml`.
7. XYZ coordinates can be displayed on the actionbar or bossbar. Additionally, the bossbar structure is configurable.
8. Coordinates are displayed as XYZ on the actionbar/bossbar, and the update interval is configurable.
9. You can disable these features in any world you want.
10. If you are in the Nether/Overworld, you can access Overworld/Nether coordinates using the `/locatix convert` command depending on the situation. 

## Commands & Permissions

| Command                         | Permission                 | Description |
|---|---|---|
 /locatix reload                 | locatix.admin.reload       | Reload the plugin. |
 /locatix resetconfig            | locatix.admin.resetconfig  | Reset the config. |
 /locatix get <player>           | locatix.admin.get          | View a player's coordinates and current world. |
 /locatix serverlang <lang>      | locatix.admin.serverlang   | Change server language. |
 /locatix adminpanel             | locatix.admin.panel        | Opens the admin panel. |
 Teleport Permission             | locatix.admin.teleport     | Allows teleporting to a player. |
 View admin commands             | locatix.admin.help         | Allows viewing admin commands. |
 /locatix help                   | locatix.player.help        | Show this help menu. |
 /locatix coordinates            | locatix.player.coordinates | Toggle coordinates on/off. |
 /locatix convert <nether/world> | locatix.player.convert     | Convert your coordinates between the Nether and the Overworld. |
 /locatix lastdeath              | locatix.player.lastdeath   | Show your last death location. |
 /locatix share <player>         | locatix.player.share       | Share your location for another player. |
 /locatix lang <lang>            | locatix.player.lang        | Allows players to change their own language. |
 /locatix                        | locatix.*                  | It grants access to all commands. |

## PlaceholderAPI (PAPI) 

Locatix includes built-in support for [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/). You can use Locatix placeholders in any compatible plugin (such as TAB, Scoreboards, Holograms, or Chat formats) without needing to download external expansions.

| Placeholder                | About |
|---|---|
| %locatix_x%                | Returns the player's X coordinate. |
| %locatix_y%                | Returns the player's Y coordinate. |
| %locatix_z%                | Returns the player's Z coordinate. |
| %locatix_nether_coords%    | Converts Overworld coordinates to Nether coordinates. |
| %locatix_overworld_coords% | Converts Nether coordinates to Overworld coordinates. |
| %locatix_lastdeath_coords% | Returns the X, Y, and Z coordinates of the player's last death location. |


# Mod

This mod is currently **under development** and will soon work seamlessly with the plugin.

## Features

1. You can see your XYZ coordinates on the Actionbar.
2. You can copy your current coordinates using the `V` key.
3. You can convert your current location to the Nether/Overworld coordinates using the `F10` key. You can also view this status from the F3 menu without using a key.
4. You can toggle the Actionbar on and off using the F9 key.

# Building
To compile Locatix, your Java version must be 21 or higher.

After cloning this repository, run these commands:
-   **On Linux or macOS:** `./gradlew build`
-   **On Windows:** `gradlew build`
- 
All `.jar` files will be generated in the `all_jars` directory.

# License

This project is licensed under the GNU General Public License v3.0 (GPLv3). Please refer to the [`LICENSE`](https://github.com/Creeperucan/locatix/blob/main/LICENSE) file for more details before using this project.