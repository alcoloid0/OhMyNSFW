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

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

class ImageMapCacheService(private val cacheDirectory: Path) {
    init {
        if (Files.notExists(cacheDirectory) || !Files.isDirectory(cacheDirectory)) {
            Files.deleteIfExists(cacheDirectory)
            Files.createDirectories(cacheDirectory)
        }
    }

    fun cacheItemStack(itemStack: ItemStack): Boolean {
        require(itemStack.itemMeta is MapMeta) { "ItemStack must have a MapMeta." }

        val mapView = (itemStack.itemMeta as MapMeta).mapView!!
        val renderers = mapView.renderers

        require(renderers.any { it is ImageMapRenderer }) { "No ImageMapRenderer found." }

        val renderer = renderers.filterIsInstance<ImageMapRenderer>().first()

        val cachePath = cacheDirectory.resolve("${mapView.id}.${IMAGE_FORMAT}")

        return ImageIO.write(renderer.image, IMAGE_FORMAT, cachePath.toFile())
    }

    fun getImage(mapId: Int): BufferedImage? {
        val cachePath = cacheDirectory.resolve("${mapId}.${IMAGE_FORMAT}")

        return if (Files.exists(cachePath)) ImageIO.read(cachePath.toFile()) else null
    }

    companion object {
        private const val IMAGE_FORMAT = "png"
    }
}
