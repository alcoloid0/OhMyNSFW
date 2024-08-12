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
import com.github.alcoloid0.nsfwplugin.extra.NsfwSubreddit
import com.github.alcoloid0.nsfwplugin.extra.sendSettingsMessage
import com.github.alcoloid0.nsfwplugin.map.ImageMap
import com.github.alcoloid0.nsfwplugin.provider.impl.GelbooruImageProvider
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
    @Subcommand("nekobot")
    @CommandPermission("ohmynsfw.use.nekobot")
    fun onNekoBot(player: Player, imageType: NekoBotImageType) {
        ImageMap.request(player, NekoBotImageProvider(imageType))
    }

    @Subcommand("rule34")
    @CommandPermission("ohmynsfw.use.rule34")
    fun onRule34(player: Player, @Optional tags: String = "") {
        ImageMap.request(player, Rule34ImageProvider(tags))
    }

    @Subcommand("reddit")
    @CommandPermission("ohmynsfw.use.reddit")
    fun onReddit(player: Player, subreddit: NsfwSubreddit) {
        ImageMap.request(player, RedditImageProvider(subreddit))
    }

    @Subcommand("gelbooru")
    @CommandPermission("ohmynsfw.use.gelbooru")
    fun onGelbooru(player: Player, @Optional tags: String = "") {
        ImageMap.request(player, GelbooruImageProvider(tags))
    }

    @Subcommand("reload")
    @CommandPermission("ohmynsfw.reload")
    fun onReload(actor: BukkitCommandActor) {
        OhMyNsfwPlugin.settings.reload()
        actor.sender.sendSettingsMessage("settings-reloaded")
    }
}