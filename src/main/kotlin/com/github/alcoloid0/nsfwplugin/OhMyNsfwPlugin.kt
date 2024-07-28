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

package com.github.alcoloid0.nsfwplugin

import com.github.alcoloid0.nsfwplugin.command.OhMyNsfwCommand
import com.github.alcoloid0.nsfwplugin.command.locale.SettingsLocaleReader
import com.github.alcoloid0.nsfwplugin.command.suggestion.NekoBotImageTypeSP
import com.github.alcoloid0.nsfwplugin.extra.NekoBotImageType
import com.github.alcoloid0.nsfwplugin.extra.Settings
import com.github.alcoloid0.nsfwplugin.listener.MapInitializeListener
import com.github.alcoloid0.nsfwplugin.map.ImageMapCacheService
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.bukkit.BukkitCommandHandler
import revxrsal.commands.ktx.parameterSuggestions
import kotlin.io.path.Path
import kotlin.time.measureTime

class OhMyNsfwPlugin : JavaPlugin() {
    private lateinit var commandHandler: BukkitCommandHandler

    override fun onEnable() = with(measureTime {
        settings = Settings(plugin = this)
        cacheService = ImageMapCacheService(Path(dataFolder.path, "cache"))

        commandHandler = BukkitCommandHandler.create(this)
        commandHandler.autoCompleter.parameterSuggestions<NekoBotImageType>(
            NekoBotImageTypeSP()
        )
        commandHandler.translator.add(SettingsLocaleReader(settings))
        commandHandler.register(OhMyNsfwCommand())
        commandHandler.registerBrigadier()

        server.pluginManager.registerEvents(MapInitializeListener(), this)
    }) {
        logger.info("Plugin Enabled (${inWholeMilliseconds}ms)")
    }

    override fun onDisable() {
        commandHandler.unregisterAllCommands()
    }

    companion object {
        lateinit var settings: Settings private set
        lateinit var cacheService: ImageMapCacheService private set
    }
}