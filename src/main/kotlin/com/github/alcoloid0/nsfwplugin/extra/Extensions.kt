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
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender
import org.bukkit.inventory.meta.ItemMeta

fun CommandSender.sendSettingsMessage(key: String, vararg tags: TagResolver) {
    val component = OhMyNsfwPlugin.settings.message(key, *tags)

    // HACK: Adventure BukkitAudiences is broken in Paper 1.21 (?)
    //  -- net.kyori:adventure-platform-bukkit:4.3.3
    sendMessage(AdventureSerializer.LEGACY.serialize(component))
}

fun ItemMeta.displayName(component: Component?) {
    setDisplayName(component?.let(AdventureSerializer.LEGACY::serialize))
}

fun ItemMeta.lore(components: List<Component>?) {
    lore = components?.map(AdventureSerializer.LEGACY::serialize)
}