/*
 * Copyright (C) 2024 alcoloid (alcoloid0)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.alcoloid0.nsfwplugin.command

import com.github.alcoloid0.nsfwplugin.OhMyNsfwPlugin
import com.github.alcoloid0.nsfwplugin.extra.NekoBotImageType
import com.github.alcoloid0.nsfwplugin.extra.sendSettingsMessage
import com.github.alcoloid0.nsfwplugin.map.ImageMap
import com.github.alcoloid0.nsfwplugin.provider.impl.NekoBotImageProvider
import com.github.alcoloid0.nsfwplugin.provider.impl.RedditImageProvider
import com.github.alcoloid0.nsfwplugin.provider.impl.Rule34ImageProvider
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Optional
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

@Command("ohmynsfw", "nsfw")
class OhMyNsfwCommand {
    // TODO: provider router/manager/service

    @Subcommand("nekobot")
    @CommandPermission("ohmynsfw.use.nekobot")
    fun onNekoBot(player: Player, imageType: NekoBotImageType) {
        ImageMap.request(player) {
            NekoBotImageProvider().getRandomImage(imageType.toString())
        }
    }

    @Subcommand("rule34")
    @CommandPermission("ohmynsfw.use.rule34")
    fun onRule34(player: Player, @Optional tags: String = "") {
        ImageMap.request(player) {
            Rule34ImageProvider().getRandomImage(*tags.split(" ").toTypedArray())
        }
    }

    // TODO: Refactor this

    @Subcommand("r/nsfw")
    @CommandPermission("ohmynsfw.use.reddit.nsfw")
    fun onRedditNsfw(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("nsfw").getRandomImage()
        }
    }

    @Subcommand("r/nsfw2")
    @CommandPermission("ohmynsfw.use.reddit.nsfw2")
    fun onRedditNsfw2(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("nsfw2").getRandomImage()
        }
    }

    @Subcommand("r/hentai")
    @CommandPermission("ohmynsfw.use.reddit.hentai")
    fun onRedditHentai(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("hentai").getRandomImage()
        }
    }

    @Subcommand("r/bdsm")
    @CommandPermission("ohmynsfw.use.reddit.bdsm")
    fun onRedditBdsm(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("bdsm").getRandomImage()
        }
    }

    @Subcommand("r/anal")
    @CommandPermission("ohmynsfw.use.reddit.anal")
    fun onRedditAnal(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("anal").getRandomImage()
        }
    }

    @Subcommand("r/boobs")
    @CommandPermission("ohmynsfw.use.reddit.boobs")
    fun onRedditBoobs(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("boobs").getRandomImage()
        }
    }

    @Subcommand("r/legalteens")
    @CommandPermission("ohmynsfw.use.reddit.legalteens")
    fun onRedditLegalTeens(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("LegalTeens").getRandomImage()
        }
    }

    @Subcommand("r/furry")
    @CommandPermission("ohmynsfw.use.reddit.furry")
    fun onRedditFurry(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("furry").getRandomImage()
        }
    }

    @Subcommand("r/toocuteforporn")
    @CommandPermission("ohmynsfw.use.reddit.toocuteforporn")
    fun onRedditTooCuteForPorn(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("TooCuteForPorn").getRandomImage()
        }
    }

    @Subcommand("r/just18")
    @CommandPermission("ohmynsfw.use.reddit.just18")
    fun onRedditJust18(player: Player) {
        ImageMap.request(player) {
            RedditImageProvider("Just18").getRandomImage()
        }
    }

    @Subcommand("reload")
    @CommandPermission("ohmynsfw.reload")
    fun onReload(actor: BukkitCommandActor) {
        OhMyNsfwPlugin.settings.reload()
        actor.sender.sendSettingsMessage("settings-reloaded")
    }
}