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

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material

object AdventureSerializer {
    // HACK: Adventure BukkitComponentSerializer is broken in Paper 1.21 (?)
    //  -- net.kyori:adventure-platform-bukkit:4.3.3
    val LEGACY = if (Material.entries.any { it.name == "NETHERITE_PICKAXE" }) {
        LegacyComponentSerializer.builder().hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build()
    } else {
        LegacyComponentSerializer.builder().character('§').build()
    }

    val MINI_MESSAGE = MiniMessage.miniMessage()
}