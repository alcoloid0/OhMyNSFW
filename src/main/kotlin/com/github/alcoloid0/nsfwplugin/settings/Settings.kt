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

import com.github.alcoloid0.nsfwplugin.OhMyNsfwPlugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.configuration.file.YamlConfiguration
import java.net.InetSocketAddress
import java.net.Proxy
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.name

class Settings(private val settingsPath: Path) {
    lateinit var yamlConfiguration: YamlConfiguration private set
    lateinit var proxy: Proxy private set

    init {
        reload()
    }

    inline fun <reified T> value(path: String): T? = yamlConfiguration.get(path) as T?

    fun component(path: String, tagResolver: TagResolver = TagResolver.empty()): Component {
        return value<String>(path)?.let { MINI_MESSAGE.deserialize(it, tagResolver) } ?: Component.empty()
    }

    fun componentList(path: String, tagResolver: TagResolver = TagResolver.empty()): List<Component> {
        return value<List<String>>(path)?.map { MINI_MESSAGE.deserialize(it, tagResolver) } ?: emptyList()
    }

    fun message(key: String, tagResolver: TagResolver = TagResolver.empty()): Component {
        return component("message-prefix", tagResolver).append(component("message.$key", tagResolver))
    }

    fun reload() {
        if (Files.notExists(settingsPath)) {
            OhMyNsfwPlugin.instance.saveResource(settingsPath.fileName.name, false)
        }

        yamlConfiguration = YamlConfiguration.loadConfiguration(settingsPath.toFile())

        updateProxy()
    }

    private fun updateProxy() {
        try {
            val proxyType = value<String>("proxy-settings.type")?.let {
                Proxy.Type.valueOf(it.uppercase())
            }

            if (proxyType != null && proxyType != Proxy.Type.DIRECT) {
                val (hostname, port) = value<String>("proxy-settings.address")!!.split(":")

                proxy = Proxy(proxyType, InetSocketAddress(hostname, port.toInt()))
                return
            }
        } catch (_: Exception) {
        }

        proxy = Proxy.NO_PROXY
    }

    companion object {
        private val MINI_MESSAGE = MiniMessage.miniMessage()
    }
}
