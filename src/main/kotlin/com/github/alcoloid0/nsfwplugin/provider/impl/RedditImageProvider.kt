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

import com.github.alcoloid0.nsfwplugin.extra.NsfwSubreddit
import com.github.alcoloid0.nsfwplugin.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.provider.dto.RedditListingDto
import com.github.alcoloid0.nsfwplugin.provider.dto.RedditThingDto
import com.github.alcoloid0.nsfwplugin.provider.dto.RedditLinkDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI

private typealias ThingListingLinkDto = RedditThingDto<RedditListingDto<RedditLinkDto>>

class RedditImageProvider(subreddit: NsfwSubreddit) : ImageProvider() {
    private val jsonUri = URI("https://www.reddit.com/r/$subreddit.json?sort=top&t=daily&limit=100")

    override suspend fun getRandomImageUri() = withContext(Dispatchers.IO) {
        val mainThing: ThingListingLinkDto = jsonUri.toURL().openStream().reader()
            .use { reader -> Gson().fromJson(reader.readText(), TYPE_TOKEN) }

        val links = mainThing.data.children.map { thing -> thing.data }
            .filter { link -> link.url.substringAfterLast('.') in FILE_EXTENSIONS }

        URI(links.random().url)
    }

    companion object {
        private val FILE_EXTENSIONS = setOf("jpg", "png", "jpeg")
        private val TYPE_TOKEN = object : TypeToken<ThingListingLinkDto>() {}.type
    }
}