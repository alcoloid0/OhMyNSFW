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

import com.github.alcoloid0.nsfwplugin.util.HttpHelper
import com.github.alcoloid0.nsfwplugin.image.provider.dto.GelbooruPostDto
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope

// "Running Gelbooru Beta 0.2"
class Rule34ImageProvider(vararg tags: String) : GelbooruImageProvider(*tags) {
    override val name = "rule34"

    override val apiUrl = "https://api.rule34.xxx"

    override suspend fun getRandomImageUrl() = coroutineScope {
        HttpHelper.fetchJson<List<GelbooruPostDto>>(jsonURL, TYPE_TOKEN).randomImageFileUrl()
    }

    companion object {
        private val TYPE_TOKEN = object : TypeToken<List<GelbooruPostDto>>() {}.type
    }
}