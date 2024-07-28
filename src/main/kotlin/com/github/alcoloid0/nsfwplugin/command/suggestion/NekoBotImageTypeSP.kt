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