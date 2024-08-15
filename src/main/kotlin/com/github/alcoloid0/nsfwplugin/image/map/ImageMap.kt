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

package com.github.alcoloid0.nsfwplugin.image.map

import com.github.alcoloid0.nsfwplugin.OhMyNsfwPlugin
import com.github.alcoloid0.nsfwplugin.util.extensions.displayName
import com.github.alcoloid0.nsfwplugin.util.extensions.lore
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.map.MapPalette
import org.bukkit.map.MapView
import java.awt.image.BufferedImage

object ImageMap {
    private val cacheService by OhMyNsfwPlugin.Companion::cacheService
    private val settings by OhMyNsfwPlugin.Companion::settings

    fun createItemStack(
        image: BufferedImage,
        tagResolver: TagResolver = TagResolver.empty()
    ): ItemStack {
        val itemStack = ItemStack(Material.FILLED_MAP)

        if (settings.value<Boolean>("map-item-settings.glow-effect") == true) {
            itemStack.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1)
        }

        val itemMeta = (itemStack.itemMeta as MapMeta).apply {
            val hideItemSpecifics = try {
                ItemFlag.valueOf("HIDE_ITEM_SPECIFICS") // Spigot 1.20.4+
            } catch (_: Exception) {
                ItemFlag.valueOf("HIDE_POTION_EFFECTS")
            }

            addItemFlags(ItemFlag.HIDE_ENCHANTS, hideItemSpecifics)

            lore(settings.componentList("map-item-settings.lore", tagResolver))
            displayName(settings.component("map-item-settings.name", tagResolver))

            mapView = updateMapView(Bukkit.createMap(Bukkit.getWorlds().first()), image)
        }

        return itemStack.also { stack -> stack.itemMeta = itemMeta }
    }

    fun cacheItemStack(itemStack: ItemStack) = cacheService.cacheItemStack(itemStack)

    fun restore(mapView: MapView) {
        cacheService.getImage(mapView.id)?.let { image -> updateMapView(mapView, image) }
    }

    private fun updateMapView(mapView: MapView, image: BufferedImage): MapView {
        return mapView.apply {
            renderers.clear()
            addRenderer(ImageMapRenderer(MapPalette.resizeImage(image)))
        }
    }
}