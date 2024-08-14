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
import com.github.alcoloid0.nsfwplugin.listener.MapInitializeListener
import com.github.alcoloid0.nsfwplugin.image.map.ImageMapCacheService
import com.github.alcoloid0.nsfwplugin.settings.Settings
import com.github.alcoloid0.nsfwplugin.settings.SettingsLocaleReader
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.bukkit.BukkitCommandHandler
import kotlin.io.path.Path
import kotlin.time.measureTime

class OhMyNsfwPlugin : JavaPlugin() {
    private lateinit var commandHandler: BukkitCommandHandler

    override fun onEnable() = with(measureTime {
        instance = this

        adventure = BukkitAudiences.create(this)
        settings = Settings(Path(dataFolder.path, "settings.yml"))
        cacheService = ImageMapCacheService(Path(dataFolder.path, "cache"))

        commandHandler = BukkitCommandHandler.create(this)
        commandHandler.translator.add(SettingsLocaleReader(settings))
        commandHandler.register(OhMyNsfwCommand())
        commandHandler.registerBrigadier()

        server.pluginManager.registerEvents(MapInitializeListener(), this)
    }) {
        logger.info("Plugin Enabled (${inWholeMilliseconds}ms)")
    }

    override fun onDisable() {
        commandHandler.unregisterAllCommands()
        adventure.close()
    }

    companion object {
        lateinit var instance: OhMyNsfwPlugin private set
        lateinit var settings: Settings private set
        lateinit var cacheService: ImageMapCacheService private set
        lateinit var adventure: BukkitAudiences private set
    }
}