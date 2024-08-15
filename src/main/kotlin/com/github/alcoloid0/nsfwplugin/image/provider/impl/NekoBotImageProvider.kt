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

import com.github.alcoloid0.nsfwplugin.image.MetadataImage
import com.github.alcoloid0.nsfwplugin.util.HttpHelper
import com.github.alcoloid0.nsfwplugin.image.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.dto.NekoBotResultDto
import com.github.alcoloid0.nsfwplugin.image.provider.extra.NekoBotImageType
import kotlinx.coroutines.coroutineScope
import java.net.URL

class NekoBotImageProvider : ImageProvider {
    override val name = "nekobot"

    override suspend fun random(vararg extra: String) = coroutineScope {
        require(extra.isNotEmpty()) { "At least one type must be provided." }

        val imageType = NekoBotImageType.entries.find { it.name.equals(extra.first(), true) }

        require(imageType != null) { "Unknown type: ${extra.first()}" }

        val jsonUrl = URL("https://nekobot.xyz/api/image?type=${imageType.name.lowercase()}")
        val result: NekoBotResultDto = HttpHelper.fetchJson(jsonUrl, NekoBotResultDto::class.java)

        check(result.success) { "NekoBot API Get Request Failed: ${result.message}" }

        MetadataImage(URL(result.message), imageType.name, imageType.isNsfw)
    }
}