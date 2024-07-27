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
import com.github.alcoloid0.nsfwplugin.provider.dto.RedditCommonDto
import com.github.alcoloid0.nsfwplugin.provider.dto.RedditEntryDto
import com.github.alcoloid0.nsfwplugin.provider.dto.RedditPostDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI

typealias EntryCommonPostDto = RedditEntryDto<RedditCommonDto<RedditPostDto>>

class RedditImageProvider(private val subreddit: String) : ImageProvider {
    private val jsonUri = URI("https://www.reddit.com/r/$subreddit.json?sort=top&t=daily")

    override suspend fun getRandomUri(vararg extra: String) = withContext(Dispatchers.IO) {
        val entry = jsonUri.toURL().openStream().reader()
            .use { reader -> Gson().fromJson(reader.readText(), TYPE_TOKEN) }

        val posts = entry.data.children
            .map { it.data }
            .filter { post -> post.url.substringAfterLast('.') in FILE_EXTENSIONS }

        URI(posts.random().url)
    }

    companion object {
        private val FILE_EXTENSIONS = setOf("jpg", "png", "jpeg")
        private val TYPE_TOKEN = (object : TypeToken<EntryCommonPostDto>() {})
    }
}