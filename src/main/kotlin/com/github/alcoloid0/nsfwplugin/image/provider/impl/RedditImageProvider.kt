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

import com.github.alcoloid0.nsfwplugin.extra.NsfwSubreddit
import com.github.alcoloid0.nsfwplugin.image.provider.ImageProvider
import com.github.alcoloid0.nsfwplugin.image.provider.dto.RedditLinkDto
import com.github.alcoloid0.nsfwplugin.image.provider.dto.RedditListingDto
import com.github.alcoloid0.nsfwplugin.image.provider.dto.RedditThingDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.Proxy
import java.net.URL

private typealias ThingListingLinkDto = RedditThingDto<RedditListingDto<RedditLinkDto>>

class RedditImageProvider(proxy: Proxy, subreddit: NsfwSubreddit) : ImageProvider(proxy) {
    override val name = "r/$subreddit"

    private val jsonUrl = URL("https://www.reddit.com/r/$subreddit.json?sort=top&t=daily&limit=100")

    override suspend fun getRandomImageUrl(): URL = withContext(Dispatchers.IO) {
        val mainThing: ThingListingLinkDto = jsonUrl.inputStream().reader()
            .use { reader -> Gson().fromJson(reader, TYPE_TOKEN) }

        val links = mainThing.data.children.map { thing -> thing.data }
            .filter { link -> link.url.substringAfterLast('.') in FILE_EXTENSIONS }

        URL(links.random().url)
    }

    companion object {
        private val TYPE_TOKEN = object : TypeToken<ThingListingLinkDto>() {}.type
    }
}