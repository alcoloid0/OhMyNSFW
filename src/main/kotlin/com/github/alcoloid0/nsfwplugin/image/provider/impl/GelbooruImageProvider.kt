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
import com.github.alcoloid0.nsfwplugin.image.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.dto.GelbooruPostDto
import com.github.alcoloid0.nsfwplugin.image.provider.dto.GelbooruPostListDto
import com.github.alcoloid0.nsfwplugin.util.HttpHelper
import com.github.alcoloid0.nsfwplugin.util.extensions.hasImageExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

open class GelbooruImageProvider : ImageProvider {
    override val name = "gelbooru"

    protected open val baseUrl = "https://gelbooru.com/index.php"

    override suspend fun random(vararg extra: String) = withContext(Dispatchers.IO) {
        val tags = extra.joinToString(" ")
        val encodedTags = URLEncoder.encode(tags, StandardCharsets.UTF_8.name())
        val jsonUrl = URL("$baseUrl?page=dapi&s=post&q=index&json=1&tags=$encodedTags")

        val posts = fetchListOfPosts(jsonUrl).filter { it.image.hasImageExtension() }

        check(posts.isNotEmpty()) { "No posts found for the given tags: $tags" }

        val post: GelbooruPostDto = posts.random()

        MetadataImage(URL(post.fileUrl), post.tags, post.rating in setOf("questionable", "explicit"))
    }

    protected open suspend fun fetchListOfPosts(jsonUrl: URL) = coroutineScope {
        HttpHelper.fetchJson(jsonUrl, GelbooruPostListDto::class.java).post
    }
}