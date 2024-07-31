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
import com.github.alcoloid0.nsfwplugin.provider.dto.GelbooruPostListDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class GelbooruImageProvider : ImageProvider {
    override suspend fun getRandomUri(vararg extra: String) = withContext(Dispatchers.IO) {
        val tags = extra.joinToString(" ")
            .let { URLEncoder.encode(it, StandardCharsets.UTF_8.name()) }

        val url = URI("$BASE_URL?page=dapi&s=post&q=index&json=1&tags=$tags").toURL()

        val postList = url.openStream().reader()
            .use { reader -> Gson().fromJson(reader, GelbooruPostListDto::class.java) }

        val posts = postList.post
            .filter { entry -> entry.image.substringAfterLast('.') in FILE_EXTENSIONS }

        URI(posts.random().fileUrl)
    }

    companion object {
        private const val BASE_URL = "https://gelbooru.com/index.php"
        private val FILE_EXTENSIONS = setOf("jpg", "png", "jpeg")
    }
}