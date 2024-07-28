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

package com.github.alcoloid0.nsfwplugin.command.suggestion

import com.github.alcoloid0.nsfwplugin.extra.NekoBotImageType
import revxrsal.commands.autocomplete.SuggestionProvider
import revxrsal.commands.command.CommandActor
import revxrsal.commands.command.ExecutableCommand

class NekoBotImageTypeSP : SuggestionProvider {
    override fun getSuggestions(
        args: MutableList<String>,
        sender: CommandActor,
        command: ExecutableCommand
    ): List<String> = NekoBotImageType.entries.map { "$it".lowercase() }
}