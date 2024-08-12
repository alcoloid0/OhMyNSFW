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

import com.github.alcoloid0.nsfwplugin.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.provider.dto.GelbooruPostDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class Rule34ImageProvider(tags: String) : ImageProvider() {
    override val baseUrl: String = "https://api.rule34.xxx"

    private val encodedTags = URLEncoder.encode(tags, StandardCharsets.UTF_8.name())
    private val jsonUri = URI("$baseUrl/index.php?page=dapi&s=post&q=index&json=1&tags=$encodedTags")

    override suspend fun getRandomImageUri() = withContext(Dispatchers.IO) {
        val entries = jsonUri.toURL().openStream().reader()
            .use { reader -> Gson().fromJson(reader, TYPE_TOKEN) as List<GelbooruPostDto> }
            .filter { entry -> entry.image.substringAfterLast('.') in FILE_EXTENSIONS }

        URI(entries.random().fileUrl)
    }

    companion object {
        private val FILE_EXTENSIONS = setOf("jpg", "png", "jpeg")
        private val TYPE_TOKEN = object : TypeToken<List<GelbooruPostDto>>() {}.type
    }
}