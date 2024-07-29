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

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.nio.file.Files
import java.nio.file.Path

class Settings(private val plugin: Plugin) {
    private val yamlFilePath: Path = plugin.dataFolder.toPath().resolve(FILE_NAME)

    lateinit var yamlConfiguration: YamlConfiguration private set

    init {
        reload()
    }

    inline fun <reified T> value(path: String): T? = yamlConfiguration.get(path) as T?

    fun component(path: String, vararg tags: TagResolver): Component? {
        return value<String>(path)?.let {
            MINI_MESSAGE.deserialize(it, TagResolver.resolver(*tags))
        }
    }

    fun componentList(path: String, vararg tags: TagResolver): List<Component> {
        return value<List<String>>(path)?.map {
            MINI_MESSAGE.deserialize(it, TagResolver.resolver(*tags))
        } ?: emptyList()
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

    companion object {
        private const val FILE_NAME = "settings.yml"
        private val MINI_MESSAGE = MiniMessage.miniMessage()
    }
}
