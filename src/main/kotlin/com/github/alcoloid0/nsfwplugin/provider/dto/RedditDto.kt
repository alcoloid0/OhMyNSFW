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

package com.github.alcoloid0.nsfwplugin.provider.dto

import com.google.gson.annotations.SerializedName

data class RedditEntryDto<T>(val kind: String, val data: T)

data class RedditCommonDto<T>(
    val after: String,
    val dist: Int,
    val modhash: String,
    @SerializedName("geo_filter") val geoFilter: String,
    val children: List<RedditEntryDto<T>>,
    val before: String
)

data class RedditPostDto(val url: String, /* skip... */)