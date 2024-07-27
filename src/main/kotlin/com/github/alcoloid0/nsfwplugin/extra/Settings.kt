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

package com.github.alcoloid0.nsfwplugin.extra

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import revxrsal.commands.locales.LocaleReader
import revxrsal.commands.locales.Locales
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class Settings(private val plugin: Plugin) {
    private val yamlFilePath: Path = plugin.dataFolder.toPath().resolve(FILE_NAME)
    lateinit var yamlConfiguration: YamlConfiguration private set

    init {
        reload()
    }

    inline fun <reified T> value(path: String): T? = yamlConfiguration.get(path) as T?

    fun component(path: String, vararg tags: TagResolver): Component? {
        return value<String>(path)?.let {
            miniMessage.deserialize(it, TagResolver.resolver(*tags))
        }
    }

    fun componentList(path: String, vararg tags: TagResolver): List<Component> {
        val list = value<List<String>>(path)

        return when {
            list == null -> emptyList()
            else -> list.map { miniMessage.deserialize(it, TagResolver.resolver(*tags)) }
        }
    }

    fun message(key: String, vararg tags: TagResolver): Component {
        val prefix = component("message-prefix", *tags) ?: Component.empty()
        val message = component("message.$key", *tags) ?: Component.empty()
        return prefix.append(message)
    }

    fun reload() {
        if (Files.notExists(yamlFilePath)) {
            plugin.saveResource(FILE_NAME, false)
        }

        yamlConfiguration = YamlConfiguration.loadConfiguration(yamlFilePath.toFile())
    }

    // HACK:
    class LampLocaleReader(private val settings: Settings) : LocaleReader {
        override fun containsKey(key: String?)
            = settings.yamlConfiguration.contains("message.$key")

        override fun get(key: String?): String {
            val component = Component.empty().color(NamedTextColor.WHITE)
            component.append(settings.message(key!!, ARGUMENT_TAG_RESOLVER))
            return LegacyComponentSerializer.legacySection().serialize(component)
        }

        override fun getLocale(): Locale = Locales.ENGLISH

        companion object {
            private val ARGUMENT_TAG_RESOLVER = TagResolver.builder()
                .resolver(Placeholder.parsed("parameter", "{0}"))
                .resolver(Placeholder.parsed("argument", "{0}"))
                .resolver(Placeholder.parsed("input", "{1}"))
                .build()
        }
    }

    companion object {
        private const val FILE_NAME = "settings.yml"
        private val miniMessage = MiniMessage.miniMessage()
    }
}
