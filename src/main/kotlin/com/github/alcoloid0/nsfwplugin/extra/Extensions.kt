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

import com.github.alcoloid0.nsfwplugin.OhMyNsfwPlugin
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.Plugin

private val LEGACY_SERIALIZER = BukkitComponentSerializer.legacy()

fun CommandSender.sendSettingsMessage(key: String, tagResolver: TagResolver = TagResolver.empty()) {
    OhMyNsfwPlugin.adventure.sender(this).sendMessage(OhMyNsfwPlugin.settings.message(key, tagResolver))
}

fun ItemMeta.displayName(component: Component?) {
    setDisplayName(component?.let(LEGACY_SERIALIZER::serialize))
}

fun ItemMeta.lore(components: List<Component>?) {
    lore = components?.map(LEGACY_SERIALIZER::serialize)
}

fun Plugin.runBukkitTask(task: () -> Unit) = Bukkit.getScheduler().runTask(this, Runnable(task))