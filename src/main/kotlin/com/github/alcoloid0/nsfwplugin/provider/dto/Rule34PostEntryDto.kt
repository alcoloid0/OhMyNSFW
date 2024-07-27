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

data class Rule34PostEntryDto(
    @SerializedName("preview_url") val previewUrl: String,
    @SerializedName("sample_url") val sampleUrl: String,
    @SerializedName("file_url") val fileUrl: String,
    val directory: Int,
    val hash: String,
    val width: Int,
    val height: Int,
    val id: Int,
    val image: String,
    val change: Int,
    val owner: String,
    @SerializedName("parent_id") val parentId: Int,
    val rating: String,
    val sample: Boolean,
    @SerializedName("sample_height") val sampleHeight: Int,
    @SerializedName("sample_width") val sampleWidth: Int,
    val score: Int,
    val tags: String,
    val source: String,
    val status: String,
    @SerializedName("has_notes") val hasNotes: Boolean,
    @SerializedName("comment_count") val commentCount: Int,
)