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

package com.github.alcoloid0.nsfwplugin.extra

import com.github.alcoloid0.nsfwplugin.OhMyNsfwPlugin
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.image.BufferedImage
import java.io.InputStream
import java.lang.reflect.Type
import java.net.Proxy
import java.net.URL
import java.util.concurrent.atomic.AtomicReference
import javax.imageio.ImageIO

object HttpHelper {
    private val description by OhMyNsfwPlugin.instance::description

    private val gson: Gson = Gson()
    private val atomicProxy: AtomicReference<Proxy> = AtomicReference(Proxy.NO_PROXY)

    var proxy: Proxy
        get() = atomicProxy.get()
        set(value) = atomicProxy.set(value)

    suspend fun fetchImage(url: URL): BufferedImage = withContext(Dispatchers.IO) {
        url.inputStream().use(ImageIO::read)
    }

    suspend fun <T> fetchJson(url: URL, clazz: Class<T>): T = withContext(Dispatchers.IO) {
        url.inputStream().reader().use { gson.fromJson(it, clazz) }
    }

    suspend fun <T> fetchJson(url: URL, type: Type): T = withContext(Dispatchers.IO) {
        url.inputStream().reader().use { gson.fromJson(it, type) }
    }

    private fun URL.inputStream(): InputStream = openConnection(proxy).apply {
        setRequestProperty("User-Agent", "${description.name}/${description.version}")
        setRequestProperty("Accept", "image/jpeg,image/png,application/json")
    }.getInputStream()
}