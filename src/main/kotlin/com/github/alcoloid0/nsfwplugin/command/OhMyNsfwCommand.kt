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
import com.github.alcoloid0.nsfwplugin.image.MetadataImage
import com.github.alcoloid0.nsfwplugin.image.map.ImageMap
import com.github.alcoloid0.nsfwplugin.image.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.extra.NekoBotImageType
import com.github.alcoloid0.nsfwplugin.image.provider.extra.NsfwSubreddit
import com.github.alcoloid0.nsfwplugin.image.provider.impl.GelbooruImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.impl.NekoBotImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.impl.RedditImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.impl.Rule34ImageProvider
import com.github.alcoloid0.nsfwplugin.settings.Settings
import com.github.alcoloid0.nsfwplugin.util.HttpHelper
import com.github.alcoloid0.nsfwplugin.util.extensions.sendSettingsMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
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
        request(player, NekoBotImageProvider()) { it.random(imageType.name) }
    }

    @Subcommand("rule34")
    @CommandPermission("ohmynsfw.use.rule34")
    fun onRule34(player: Player, @Optional tags: String = "") {
        request(player, Rule34ImageProvider()) { it.random(*tags.split(" ").toTypedArray()) }
    }

    @Subcommand("reddit")
    @CommandPermission("ohmynsfw.use.reddit")
    fun onReddit(player: Player, subreddit: NsfwSubreddit) {
        request(player, RedditImageProvider()) { it.random(subreddit.name) }
    }

    @Subcommand("gelbooru")
    @CommandPermission("ohmynsfw.use.gelbooru")
    fun onGelbooru(player: Player, @Optional tags: String = "") {
        request(player, GelbooruImageProvider()) { it.random(*tags.split(" ").toTypedArray()) }
    }

    @Subcommand("reload")
    @CommandPermission("ohmynsfw.reload")
    fun onReload(actor: BukkitCommandActor) = with(measureTime {
        settings.reload()
        HttpHelper.proxy = settings.proxy()
    }) {
        actor.sender.sendSettingsMessage(
            "settings-reloaded", Placeholder.unparsed("millis", "$inWholeMilliseconds")
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    private inline fun request(
        offlinePlayer: OfflinePlayer,
        imageProvider: ImageProvider,
        crossinline lazyMetadataImage: suspend (provider: ImageProvider) -> MetadataImage
    ) {
        val providerPlaceholder = Placeholder.unparsed("provider", imageProvider.name)

        val handler = CoroutineExceptionHandler { _, _ ->
            offlinePlayer.player?.sendSettingsMessage("request-error-occurred", providerPlaceholder)
        }

        GlobalScope.launch(handler) {
            val metadataImage = lazyMetadataImage(imageProvider)

            val metadataResolver = TagResolver.builder().resolver(providerPlaceholder)
                .resolver(Placeholder.unparsed("direct-url", metadataImage.directUrl.toString()))
                .resolver(Placeholder.unparsed("extra-info", metadataImage.extraInfo ?: ""))
                .resolver(Placeholder.unparsed("rating", if (metadataImage.isNsfw) "NSFW" else "SFW"))
                .build()

            val itemStack = ImageMap.createItemStack(metadataImage.download(), metadataResolver)

            OhMyNsfwPlugin.scheduler.runTask {
                offlinePlayer.player?.run {
                    inventory.addItem(itemStack)
                    sendSettingsMessage("request-complete", metadataResolver)
                    ImageMap.cacheItemStack(itemStack)
                }
            }
        }

        offlinePlayer.player?.sendSettingsMessage("request-prepare", providerPlaceholder)
    }
}