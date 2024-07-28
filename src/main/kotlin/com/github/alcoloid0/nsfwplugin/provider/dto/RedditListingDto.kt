package com.github.alcoloid0.nsfwplugin.provider.dto

data class RedditListingDto<T>(
    val before: String,
    val after: String,
    val modhash: String,
    val children: List<RedditThingDto<T>>
)