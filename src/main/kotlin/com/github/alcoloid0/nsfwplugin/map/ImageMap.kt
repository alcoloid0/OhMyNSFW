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

package com.github.alcoloid0.nsfwplugin.map

import com.github.alcoloid0.nsfwplugin.OhMyNsfwPlugin
import com.github.alcoloid0.nsfwplugin.settings.Settings
import com.github.alcoloid0.nsfwplugin.extra.sendSettingsMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta
import java.awt.image.BufferedImage

object ImageMap {
    val cacheService: ImageMapCacheService by OhMyNsfwPlugin.Companion::cacheService

    fun createItemStack(image: BufferedImage): ItemStack {
        val itemStack = ItemStack(Material.FILLED_MAP)

        val settings: Settings by OhMyNsfwPlugin.Companion::settings

        if (settings.value<Boolean>("map-item-settings.glow-effect") == true) {
            itemStack.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1)
        }

        val itemMeta = (itemStack.itemMeta as MapMeta).apply {
            addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ITEM_SPECIFICS)

            lore(settings.componentList("map-item-settings.lore"))
            displayName(settings.component("map-item-settings.name"))

            mapView = Bukkit.createMap(Bukkit.getWorlds().first()).apply {
                renderers.clear()
                addRenderer(ImageMapRenderer(image))
            }
        }

        return itemStack.also { stack -> stack.itemMeta = itemMeta }
    }

    @OptIn(DelicateCoroutinesApi::class)
    inline fun request(
        offlinePlayer: OfflinePlayer,
        crossinline lazyImage: suspend () -> BufferedImage
    ) {
        val handler = CoroutineExceptionHandler { _, _ ->
            offlinePlayer.player?.sendSettingsMessage("request-error-occurred")
        }

        GlobalScope.launch(handler) {
            launch {
                val itemStack = createItemStack(lazyImage())
                offlinePlayer.player?.inventory?.addItem(itemStack)
                offlinePlayer.player?.sendSettingsMessage("request-complete")
                cacheService.cacheItemStack(itemStack)
            }

            offlinePlayer.player?.sendSettingsMessage("request-prepare")
        }
    }
}