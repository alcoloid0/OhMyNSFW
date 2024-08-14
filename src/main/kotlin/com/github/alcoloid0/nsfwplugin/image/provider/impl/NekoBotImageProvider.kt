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

package com.github.alcoloid0.nsfwplugin.image.provider.impl

import com.github.alcoloid0.nsfwplugin.extra.NekoBotImageType
import com.github.alcoloid0.nsfwplugin.image.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.dto.NekoBotResultDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.Proxy
import java.net.URL

class NekoBotImageProvider(proxy: Proxy, imageType: NekoBotImageType) : ImageProvider(proxy) {
    override val name = "nekobot.xyz"

    private val jsonUrl = URL("https://nekobot.xyz/api/image?type=${imageType.name.lowercase()}")

    override suspend fun getRandomImageUrl(): URL = withContext(Dispatchers.IO) {
        val result = jsonUrl.inputStream().reader()
            .use { reader -> Gson().fromJson(reader, NekoBotResultDto::class.java) }

        check(result.success) { "NekoBot API Get Request Failed: ${result.message}" }

        URL(result.message)
    }
}