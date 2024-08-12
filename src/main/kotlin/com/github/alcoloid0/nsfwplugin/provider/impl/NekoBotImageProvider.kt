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

package com.github.alcoloid0.nsfwplugin.provider.impl

import com.github.alcoloid0.nsfwplugin.extra.NekoBotImageType
import com.github.alcoloid0.nsfwplugin.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.provider.dto.NekoBotResultDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI

class NekoBotImageProvider(imageType: NekoBotImageType) : ImageProvider() {
    override val baseUrl: String = "https://nekobot.xyz"

    private val jsonUri = URI("$baseUrl/api/image?type=$imageType")

    override suspend fun getRandomImageUri() = withContext(Dispatchers.IO) {
        val result = jsonUri.toURL().openStream().reader()
            .use { Gson().fromJson(it, NekoBotResultDto::class.java) }

        check(result.success) { "NekoBot API Get Request Failed: ${result.message}" }

        URI(result.message)
    }
}