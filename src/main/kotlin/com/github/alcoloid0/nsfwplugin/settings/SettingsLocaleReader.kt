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

package com.github.alcoloid0.nsfwplugin.settings

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import revxrsal.commands.locales.LocaleReader
import revxrsal.commands.locales.Locales
import java.util.*

class SettingsLocaleReader(private val settings: Settings) : LocaleReader {
    override fun containsKey(key: String?): Boolean {
        return key?.let { settings.yamlConfiguration.contains("message.$it") } ?: false
    }

    override fun get(key: String?): String {
        requireNotNull(key) { "Key cannot be null" }

        return COMPONENT_SERIALIZER.serialize(settings.message(key, ARGUMENT_TAG_RESOLVER))
    }

    override fun getLocale(): Locale = Locales.ENGLISH

    companion object {
        private val COMPONENT_SERIALIZER = LegacyComponentSerializer.legacySection()

        private val ARGUMENT_TAG_RESOLVER = TagResolver.resolver(
            Placeholder.parsed("parameter", "{0}"),
            Placeholder.parsed("argument", "{0}"),
            Placeholder.parsed("input", "{1}")
        )
    }
}