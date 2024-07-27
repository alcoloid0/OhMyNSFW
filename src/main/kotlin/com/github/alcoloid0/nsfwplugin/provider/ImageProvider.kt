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

package com.github.alcoloid0.nsfwplugin.provider

import com.github.alcoloid0.nsfwplugin.provider.impl.NekoBotImageProvider
import com.github.alcoloid0.nsfwplugin.provider.impl.Rule34ImageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.image.BufferedImage
import java.net.URI
import javax.imageio.ImageIO

interface ImageProvider {
    suspend fun getRandomUri(vararg extra: String): URI

    suspend fun getRandomImage(vararg extra: String): BufferedImage {
        return withContext(Dispatchers.IO) {
            ImageIO.read(getRandomUri(*extra).toURL())!!
        }
    }

    companion object {
        val NekoBot: ImageProvider = NekoBotImageProvider()
        val Rule34: ImageProvider = Rule34ImageProvider()
    }
}