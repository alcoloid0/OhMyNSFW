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
import com.github.alcoloid0.nsfwplugin.util.NekoBotImageType
import com.github.alcoloid0.nsfwplugin.util.NsfwSubreddit
import com.github.alcoloid0.nsfwplugin.image.map.ImageMap
import com.github.alcoloid0.nsfwplugin.image.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.impl.GelbooruImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.impl.NekoBotImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.impl.RedditImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.impl.Rule34ImageProvider
import com.github.alcoloid0.nsfwplugin.settings.Settings
import com.github.alcoloid0.nsfwplugin.util.extensions.sendMessage
import com.github.alcoloid0.nsfwplugin.util.extensions.sendSettingsMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Optional
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission
import kotlin.time.measureTime

@Command("ohmynsfw", "nsfw")
class OhMyNsfwCommand {
    private val settings: Settings by OhMyNsfwPlugin.Companion::settings

    @Subcommand("nekobot")
    @CommandPermission("ohmynsfw.use.nekobot")
    fun onNekoBot(player: Player, imageType: NekoBotImageType) {
        request(player, NekoBotImageProvider(imageType))
    }

    @Subcommand("rule34")
    @CommandPermission("ohmynsfw.use.rule34")
    fun onRule34(player: Player, @Optional tags: String = "") {
        request(player, Rule34ImageProvider(tags))
    }

    @Subcommand("reddit")
    @CommandPermission("ohmynsfw.use.reddit")
    fun onReddit(player: Player, subreddit: NsfwSubreddit) {
        request(player, RedditImageProvider(subreddit))
    }

    @Subcommand("gelbooru")
    @CommandPermission("ohmynsfw.use.gelbooru")
    fun onGelbooru(player: Player, @Optional tags: String = "") {
        request(player, GelbooruImageProvider(tags))
    }

    @Subcommand("reload")
    @CommandPermission("ohmynsfw.reload")
    fun onReload(actor: BukkitCommandActor) {
        val millis = measureTime { settings.reload() }.inWholeMilliseconds
        val millisPlaceholder = Placeholder.unparsed("ms", "$millis")

        actor.sender.sendSettingsMessage("settings-reloaded", millisPlaceholder)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun request(offlinePlayer: OfflinePlayer, imageProvider: ImageProvider) {
        val placeholder = Placeholder.unparsed("name", imageProvider.name)

        val handler = CoroutineExceptionHandler { _, _ ->
            offlinePlayer.player?.sendSettingsMessage("request-error-occurred", placeholder)
        }

        GlobalScope.launch(handler) {
            launch {
                val itemStack = ImageMap.createItemStack(imageProvider.getRandomImage())

                OhMyNsfwPlugin.scheduler.runTask {
                    offlinePlayer.player?.inventory?.addItem(itemStack)
                    offlinePlayer.player?.sendSettingsMessage("request-complete", placeholder)
                    ImageMap.cacheService.cacheItemStack(itemStack)
                }
            }

            offlinePlayer.player?.sendSettingsMessage("request-prepare", placeholder)
        }
    }
}