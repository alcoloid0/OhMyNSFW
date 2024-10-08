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

package com.github.alcoloid0.nsfwplugin.util.extensions

import com.github.alcoloid0.nsfwplugin.OhMyNsfwPlugin
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender

fun CommandSender.sendMessage(message: ComponentLike) {
    OhMyNsfwPlugin.adventure.sender(this).sendMessage(message)
}

fun CommandSender.sendSettingsMessage(key: String, tagResolver: TagResolver = TagResolver.empty()) {
    sendMessage(OhMyNsfwPlugin.settings.message(key, tagResolver))
}