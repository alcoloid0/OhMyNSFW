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

import com.github.alcoloid0.nsfwplugin.extra.AdventureSerializer
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import revxrsal.commands.locales.LocaleReader
import revxrsal.commands.locales.Locales
import java.util.*

class SettingsLocaleReader(private val settings: Settings) : LocaleReader {
    override fun containsKey(key: String?): Boolean {
        return key?.let { settings.yamlConfiguration.contains("message.$it") } ?: false
    }

    override fun get(key: String?): String {
        val component = settings.message(key!!, ARGUMENT_TAG_RESOLVER)

        return AdventureSerializer.LEGACY.serialize(component)
    }

    override fun getLocale(): Locale = Locales.ENGLISH

    companion object {
        // HACK: Lamp does not support formatting in MiniMessage tags and instead uses {0}, {1}, etc.
        private val ARGUMENT_TAG_RESOLVER = TagResolver.resolver(
            Placeholder.parsed("parameter", "{0}"),
            Placeholder.parsed("argument", "{0}"),
            Placeholder.parsed("input", "{1}")
        )
    }
}