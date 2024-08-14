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

package com.github.alcoloid0.nsfwplugin.image.provider

import com.github.alcoloid0.nsfwplugin.OhMyNsfwPlugin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.Proxy
import java.net.URL
import javax.imageio.ImageIO

abstract class ImageProvider(private val proxy: Proxy) {
    abstract val name: String

    abstract suspend fun getRandomImageUrl(): URL

    suspend fun getRandomImage() = withContext(Dispatchers.IO) {
        getRandomImageUrl().inputStream().use { inputStream -> ImageIO.read(inputStream) }!!
    }

    protected fun URL.inputStream(): InputStream = openConnection(proxy).apply {
        val description = OhMyNsfwPlugin.instance.description
        setRequestProperty("User-Agent", "${description.name} v${description.version}")
    }.getInputStream()

    companion object {
        val FILE_EXTENSIONS = setOf("jpg", "png", "jpeg")
    }
}