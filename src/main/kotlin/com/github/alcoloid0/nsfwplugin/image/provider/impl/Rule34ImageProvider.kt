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

import com.github.alcoloid0.nsfwplugin.image.provider.dto.GelbooruPostDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.Proxy

// "Running Gelbooru Beta 0.2"
class Rule34ImageProvider(proxy: Proxy, vararg tags: String) : GelbooruImageProvider(proxy, *tags) {
    override val name = "rule34"

    override val apiUrl = "https://api.rule34.xxx"

    override suspend fun getRandomImageUrl() = withContext(Dispatchers.IO) {
        val posts = jsonURL.inputStream().reader()
            .use { reader -> Gson().fromJson(reader, TYPE_TOKEN) as List<GelbooruPostDto> }

        posts.randomImageFileUrl()
    }

    companion object {
        private val TYPE_TOKEN = object : TypeToken<List<GelbooruPostDto>>() {}.type
    }
}