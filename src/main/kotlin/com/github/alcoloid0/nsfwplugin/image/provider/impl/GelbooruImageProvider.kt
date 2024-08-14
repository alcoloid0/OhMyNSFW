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

import com.github.alcoloid0.nsfwplugin.image.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.dto.GelbooruPostDto
import com.github.alcoloid0.nsfwplugin.image.provider.dto.GelbooruPostListDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.Proxy
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

open class GelbooruImageProvider(proxy: Proxy, vararg tags: String) : ImageProvider(proxy) {
    override val name = "gelbooru"

    private val encodedTags = URLEncoder.encode(tags.joinToString(" "), StandardCharsets.UTF_8.name())

    protected open val apiUrl = "https://gelbooru.com/index.php"

    protected val jsonURL: URL get() = URL("$apiUrl?page=dapi&s=post&q=index&json=1&tags=$encodedTags")

    override suspend fun getRandomImageUrl(): URL = withContext(Dispatchers.IO) {
        val postList = jsonURL.inputStream().reader()
            .use { reader -> Gson().fromJson(reader, GelbooruPostListDto::class.java) }

        postList.post.randomImageFileUrl()
    }

    protected fun List<GelbooruPostDto>.randomImageFileUrl(): URL {
        return URL(filter { it.image.substringAfterLast('.') in FILE_EXTENSIONS }.random().fileUrl)
    }
}