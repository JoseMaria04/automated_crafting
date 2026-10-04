# Automated Crafting — unofficial port to Minecraft 26.3 (Fabric)

> **This is an unofficial fork.** Automated Crafting was created by
> [supersaiyansubtlety](https://gitlab.com/supersaiyansubtlety) and is published under the MIT license.
> Original source: <https://gitlab.com/supersaiyansubtlety/automated_crafting> ·
> Modrinth: <https://modrinth.com/mod/automated-crafting>.
> All credit for the mod's design, textures and translations goes to the original author.
> Please **don't report bugs from this port to the original author**; open an issue here instead.

The original mod was discontinued for 1.21+ in favour of the vanilla Crafter. This fork (branch `26.3`) ports it to
Fabric **26.3** so worlds with existing Automated Crafting Table contraptions keep working.

### What changed in the port
- Same block, item and block entity IDs, and the same inventory layout (template 0-8, input 9-17, output 18), so
  existing auto crafters load with their contents.
- Old-world data (e.g. 1.20.1) is upgraded through Minecraft's data fixer: item counts, enchantments, names, damage…
  are kept. Tested by migrating a 1.20.1 world with the original 1.4.15 jar.
- The GUI was rebuilt with vanilla screens (LibGui is not available for 26.3). Same layout: template, input, output.
- Config is still `config/automated_crafting.json` with the same keys; Cloth Config / Mod Menu are no longer needed
  (the in-game config screen was removed — edit the JSON). Crowdin translation downloads were removed; bundled
  translations are kept.
- Output stacks now respect the item's max stack size (previously unstackable results could stack to 64).

### Building
Requires JDK 25: `./gradlew build` → `build/libs/automated_crafting-<version>.jar`.
`./gradlew runClientGameTest` opens an auto crafter and screenshots the GUI.

---

<!--required for Modrinth centering -->
<center>
<div style="text-align:center;line-height:250%">

## Automated Crafting

![environment: both](https://img.shields.io/badge/environment-both-4caf50)  
[![loader: Fabric](https://img.shields.io/badge/loader-Fabric-cdc4ae?logo=data:image/svg+xml;base64,PHN2ZyB2aWV3Qm94PSIwIDAgMjYgMjgiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgc3R5bGU9ImZpbGwtcnVsZTpldmVub2RkO2NsaXAtcnVsZTpldmVub2RkO3N0cm9rZS1saW5lam9pbjpyb3VuZDtzdHJva2UtbWl0ZXJsaW1pdDoyIj48cGF0aCBkPSJtMTAgMjQtOC04TDE0IDRWMmgybDYgNnY0TDEwIDI0eiIgc3R5bGU9ImZpbGw6I2RiZDBiNCIvPjxwYXRoIGQ9Ik0xMiA2djJoLTJ2Mkg4djJINnYySDR2MkgydjRoMnYyaDJ2MmgydjJoMnYtMmgydi0yaDJ2LTJoMnYtMmgydi0yaDJ2LTRoLTJ2LTJoLTJWOGgtMlY2aDJ2MmgydjJoMnYyaDJ2Mmgydi0yaC0yVjhoLTJWNmgtMlY0aC0yVjJoMnYyaDJ2MmgydjJoMnYyaDJ2NGgtMnYyaC00djJoLTJ2MmgtMnYyaC0ydjRoLTJ2Mkg4di0ySDZ2LTJINHYtMkgydi0ySDB2LTRoMnYtMmgydi0yaDJ2LTJoMlY4aDJWNmgyVjJoMlYwaDJ2MmgtMnY0aC0yIiBzdHlsZT0iZmlsbDojMzgzNDJhIi8+PHBhdGggc3R5bGU9ImZpbGw6IzgwN2E2ZCIgZD0iTTIyIDEyaDJ2MmgtMnoiLz48cGF0aCBkPSJNMiAxOGgydjJoMnYyaDJ2MmgydjJIOHYtMkg2di0ySDR2LTJIMnYtMiIgc3R5bGU9ImZpbGw6IzlhOTI3ZSIvPjxwYXRoIGQ9Ik0yIDE2aDJ2MmgydjJoMnYyaDJ2Mkg4di0ySDZ2LTJINHYtMkgydi0yeiIgc3R5bGU9ImZpbGw6I2FlYTY5NCIvPjxwYXRoIGQ9Ik0yMiAxMnYtMmgtMlY4aC0yVjZsLTIuMDIzLjAyM0wxNiA4aDJ2MmgydjJoMnpNMTAgMjR2LTJoMnYtNGg0di00aDJ2LTJoMnY0aC0ydjJoLTJ2MmgtMnYyaC0ydjJoLTIiIHN0eWxlPSJmaWxsOiNiY2IyOWMiLz48cGF0aCBkPSJNMTQgMThoLTR2LTJIOHYtMmgydjJoNHYyem00LTRoLTR2LTJoLTJ2LTJoLTJWOGgydjJoMnYyaDR2MnpNMTQgNGgydjJoLTJWNHoiIHN0eWxlPSJmaWxsOiNjNmJjYTUiLz48L3N2Zz4=)](https://fabricmc.net/)
<a href="https://quiltmc.org/"><img width=75 src="https://raw.githubusercontent.com/QuiltMC/art/master/brand/1024png/quilt_available_dark.png" alt="available for: Quilt Loader"></a>  
<a href="https://modrinth.com/mod/fabric-api/versions"><img width="64" src="https://i.imgur.com/Ol1Tcf8.png" alt="Requires: Fabric API"></a>
[![supports: Cloth Config](https://img.shields.io/badge/supports-Cloth_Config-89dd43?logo=data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PCFET0NUWVBFIHN2ZyBQVUJMSUMgIi0vL1czQy8vRFREIFNWRyAxLjEvL0VOIiAiaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkIj48c3ZnIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiIHZpZXdCb3g9IjAgMCAyNjQgMjY0IiB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbDpzcGFjZT0icHJlc2VydmUiIHhtbG5zOnNlcmlmPSJodHRwOi8vd3d3LnNlcmlmLmNvbS8iIHN0eWxlPSJmaWxsLXJ1bGU6ZXZlbm9kZDtjbGlwLXJ1bGU6ZXZlbm9kZDtzdHJva2UtbGluZWpvaW46cm91bmQ7c3Ryb2tlLW1pdGVybGltaXQ6MjsiPjxwYXRoIGlkPSJiYWNrZ3JvdW5kIiBkPSJNODQsMjA3bC0zNiwtMzZsMCwtMzlsMTgsLTE4bDE4LC0yMWwyMSwtMThsMzYsLTM2bDAsLTIxbDIxLDBsMTgsMjFsMzYsMzZsMCwxOGwtNTQsNzhsLTM5LDM2bC0zOSwwWiIgc3R5bGU9ImZpbGw6IzljZmY1NTsiLz48cGF0aCBpZD0ib3V0bGluZSIgZD0iTTE2MiwxOGwwLC0xOGwtMjEsMGwwLDE4bC0xOCwwbDAsMzlsLTE4LDBsMCwxOGwtMjEsMGwwLDE4bC0xOCwwbDAsMjFsLTE4LDBsMCwxOGwtMTgsMGwwLDE4bC0yMSwwbDAsMzlsMjEsMGwwLDE4bDE4LDBsMCwxOGwxOCwwbDAsMjFsMTgsMGwwLDE4bDIxLDBsMCwtMThsMTgsMGwwLC0yMWwxOCwwbDAsLTE4bDIxLDBsMCwtMThsMTgsMGwwLC0xOGwxOCwwbDAsLTIxbDM5LDBsMCwtMThsMTgsMGwwLC0zOWwtMTgsMGwwLC0xOGwtMjEsMGwwLC0xOGwtMTgsMGwwLC0xOGwtMTgsMGwwLC0yMWwtMTgsMGwwLDIxbDE4LDBsMCwxOGwxOCwwbDAsMThsMTgsMGwwLDM5bDIxLDBsMCwxOGwtMjEsMGwwLC0xOGwtMTgsMGwwLC0yMWwtMTgsMGwwLC0xOGwtMTgsMGwwLC0xOGwtMjEsMGwwLDE4bDIxLDBsMCwxOGwxOCwwbDAsMjFsMTgsMGwwLDM2bC0xOCwwbDAsMjFsLTE4LDBsMCwxOGwtMjEsMGwwLDE4bC0xOCwwbDAsMThsLTE4LDBsMCwyMWwtMjEsMGwwLC0yMWwtMTgsMGwwLC0xOGwtMTgsMGwwLC0xOGwtMTgsMGwwLC0zOWwxOCwwbDAsLTE4bDE4LDBsMCwtMThsMTgsMGwwLC0yMWwyMSwwbDAsLTE4bDE4LDBsMCwtMThsMTgsMGwwLC0zOWwyMSwwIiBzdHlsZT0iZmlsbDojMTE1MTEwOyIvPjxwYXRoIGlkPSJzaGFkZS0xIiBzZXJpZjppZD0ic2hhZGUgMSIgZD0iTTQ4LDE3MWwtMTgsMGwwLDE4bDE4LDBsMCwxOGwxOCwwbDAsMThsMTgsMGwwLDIxbDIxLDBsMCwtMjFsLTIxLDBsMCwtMThsLTE4LDBsMCwtMThsLTE4LDBsMCwtMThabTE2OCwtNTdsMjEsMGwwLDE4bC0yMSwwbDAsLTE4WiIgc3R5bGU9ImZpbGw6IzZlYWEzMzsiLz48cGF0aCBpZD0ic2hhZGUtMiIgc2VyaWY6aWQ9InNoYWRlIDIiIGQ9Ik00OCwxNTBsLTE4LDBsMCwyMWwxOCwwbDAsMThsMTgsMGwwLDE4bDE4LDBsMCwxOGwyMSwwbDAsLTE4bC0yMSwwbDAsLTE4bC0xOCwwbDAsLTE4bC0xOCwwbDAsLTIxWiIgc3R5bGU9ImZpbGw6IzgwY2MzZDsiLz48cGF0aCBpZD0ic2hhZGUtMyIgc2VyaWY6aWQ9InNoYWRlIDMiIGQ9Ik0xMDUsMjI1bDAsLTE4bDE4LDBsMCwtMzZsMzksMGwwLC0zOWwxOCwwbDAsLTE4bDE4LDBsMCwzNmwtMTgsMGwwLDIxbC0xOCwwbDAsMThsLTIxLDBsMCwxOGwtMTgsMGwwLDE4bC0xOCwwWm0xMTEsLTExMWwtMTgsMGwwLC0yMWwtMTgsMGwwLC0xOGwtMTgsMGwwLC0xOGwxOCwwbDAsMThsMTgsMGwwLDE4bDE4LDBsMCwyMVoiIHN0eWxlPSJmaWxsOiM4OWRjNDI7Ii8+PHBhdGggaWQ9InNoYWRlLTQiIHNlcmlmOmlkPSJzaGFkZSA0IiBkPSJNMTQxLDE3MWwtMzYsMGwwLC0yMWwtMjEsMGwwLC0xOGwyMSwwbDAsMThsMzYsMGwwLDIxWm0zOSwtMzlsLTM5LDBsMCwtMThsLTE4LDBsMCwtMjFsLTE4LDBsMCwtMThsMTgsMGwwLDE4bDE4LDBsMCwyMWwzOSwwbDAsMThabS0xOCwtNzVsLTIxLDBsMCwtMThsMjEsMGwwLDE4WiIgc3R5bGU9ImZpbGw6IzkwZWI0YjsiLz48L3N2Zz4=)](https://modrinth.com/mod/cloth-config/versions)
[![supports: Mod Menu](https://img.shields.io/badge/supports-Mod_Menu-134bfe?logo=data:image/webp+xml;base64,UklGRlIAAABXRUJQVlA4TEUAAAAv/8F/AA9wpv9T/M///McDFLeNpKT/pg8WDv6jiej/BAz7v+bbAKDn9D9l4Es/T/9TBr708/Q/ZeBLP0//c7Xiqp7z/QEA)](https://modrinth.com/mod/modmenu/versions)  
[![license: MIT](https://img.shields.io/badge/license-MIT-white)](https://gitlab.com/supersaiyansubtlety/automated_crafting/-/blob/master/LICENSE)
[![source on: GitLab](https://img.shields.io/badge/source_on-GitLab-fc6e26?logo=gitlab)](https://gitlab.com/supersaiyansubtlety/automated_crafting)
[![issues: GitLab](https://img.shields.io/gitlab/issues/open-raw/supersaiyansubtlety/automated_crafting?label=issues&logo=gitlab)](https://gitlab.com/supersaiyansubtlety/automated_crafting/-/issues)
[![localized: Percentage](https://badges.crowdin.net/automated-crafting/localized.svg)](https://crwd.in/automated-crafting)  
[![Modrinth: Downloads](https://img.shields.io/modrinth/dt/automated-crafting?logo=modrinth&label=Modrinth&color=00ae5d)](https://modrinth.com/mod/automated-crafting/versions)
[![CurseForge: Downloads](https://img.shields.io/curseforge/dt/389435?logo=curseforge&label=CurseForge&color=f16437)](https://www.curseforge.com/minecraft/mc-mods/automated-crafting/files)  
[![chat: Discord](https://img.shields.io/discord/1006391289006280746?logo=discord&color=5964f3)](https://discord.gg/xABmPngXAH)
<a href="https://coindrop.to/supersaiyansubtlety"><img width="77" style="border-radius:3px" src="https://coindrop.to/embed-button.png" alt="coindrop.to me"></a>
<a href="https://ko-fi.com/supersaiyansubtlety"><img width="100" src="https://i.ibb.co/4gwRR8L/p.png" alt="Buy me a coffee"></a>

</div>
</center>

---

### Adds a simple autocrafter block. Has a simple mode and a... not simple mode.

Must be installed on both client and server.

Adds a single block: the Automated Crafting Table.

---

### Automated Crafting is discontinued on Minecraft 1.21+ because of the vanilla Crafter

There's a [converter](https://modrinth.com/mod/automated-crafting/version/1.0.0+MC1.21)
that can be used to turn Automated Crafting Tables into Crafters when updating a world to 1.21.

<details>

<summary>Converter details</summary>

Use the converter when updating a pre-1.21 world with Automated Crafting Tables to 1.21.  
Contraptions that used the Automated Crafting Table are unlikely to work with the Crafter, but this will at least
minimize the loss of blocks and items.  
Automated Crafting Tables' input will be copied to its Crafter replacement, but its output will be lost because
Crafters' output slots don't hold items.

</details>

---

<img alt="Automated Crafting Table" width="142" src="https://gitlab.com/uploads/-/system/project/avatar/19136557/automated_crafting_icon+400x400.png">
<img alt="Automated Crafting Table recipe" width="300" src="https://lh3.googleusercontent.com/yMvEWd7sOwhxDpsvlEQBXdFwX-fpwG_T3tlITGYNfR6CM2s3s55X9sudybASEHdABbx5g2D1TiUaM0Iv2BbMgYoD2WrjN3pY5LPYOZooKUfJ8nHv6KZQYHvBTeJxoRR9OmNuEPp1PZ14cGbeFe8HWWVKOfThhd3spf_Xr2qmjnD8kX8I0ig_x83IyRwQd6kg7adwtqyPWw7TPkmqZS6Mt5WMQTjVvPJJI1MB5ay5vx4oer-hR_W69_qAiGgpNrL9vCsM841SzFLz6hmWKhwpJHb56vM8T_4QOH2j1TdJUpQBl_Z0mocSdKp2OZbK0mmgKc_h6EY5mnkDD8VE3a_QaYWo_-BemUGUeUdcrjK0FtBX_VTkckVTN4InuOGd4HhkiyUSgbDjspuhoJJRP3HbOB0CPwXmgIVpTcPJRwBTYYWED0EaxEB_f68SyfXquifo-Xd51CDdFMMvaRKYY7atVm0YJWkdA61hxKcaeQSoJ2ecjGyJ6chtMaAbOOaPYI-OwYRRvjQ7ZNeIQCCEY6XJFGqjAAF3RI0KoUJvNZJtYIYkJmL6ZZk88Dr2SeH3w8-kmHUiT-ElGtSIVVPmfWDdVijYZ-0IJOvS6aB2msOUa2qgTzTMgdscwpa4ez97gF9ryosw_bcqQML0ejzaJhRa2Ep-LC_e7gLnxk3KL4ztlUR9An7E8lq4-2yOlBu6gM4qTOB9=w800-h500-k-ft">

Any stripped wood or logs may be substituted for those shown in the recipe above.

Basically, you insert items (with hoppers and droppers) so that a recipe pattern is in its inventory, then give it a redstone signal and it crafts the recipe.

---

<details>

<summary>Configuration</summary>

Supports [Cloth Config](https://modrinth.com/mod/cloth-config/versions) and
[Mod Menu](https://modrinth.com/mod/modmenu/versions) for configuration, but neither is required.

Options will use their default values if [Cloth Config](https://modrinth.com/mod/cloth-config/versions) is absent.

If [Cloth Config](https://modrinth.com/mod/cloth-config/versions) is present, options can be configured either through
[Mod Menu](https://modrinth.com/mod/modmenu/versions) or by editing `config/automated_crafting.json` in your
instance folder (`.minecraft/` by default for the vanilla launcher).

- Enable Simple Mode; default: `true`
  > In simple mode you can set a recipe template

- Enable Quasi-Connectivity; default: `false`
  > If the block above an auto crafter would be powered, the auto crafter is powered (but not updated)

- Enable Redstone Dust Redirection; default: `false`
  > Redstone dust next to auto an auto crafter will point into the auto crafter

- Craft Continuously While Powered; default: `false`
  > When an auto crafter is powered and contains a valid recipe, that recipe will be crafted immediately

- Comparator reads 15 if output isn't empty; default: `false`
  > A comparator reading an auto crafter with anything in its output slot will read 15

- Download translation updates; default: `true`
  > Download translations from Crowdin when the game launches

</details>

---

<details>

<summary>Translating</summary>

[![localized: Percentage](https://badges.crowdin.net/automated-crafting/localized.svg)](https://crwd.in/automated-crafting)  
If you'd like to help translate Automated Crafting, you can do so on
[Crowdin](https://crwd.in/automated-crafting).  
New translations will be added once approved without the mod needing an update thanks to
[CrowdinTranslate](https://github.com/gbl/CrowdinTranslate).

</details>

---

This mod is only for Fabric (works on Quilt, too!) and I won't be porting it to Forge. The license is MIT, however, so anyone else is free to port it.

I'd appreciate links back to this page if you port or otherwise modify this project, but links aren't required.
