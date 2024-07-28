package com.github.alcoloid0.nsfwplugin.command.locale

import com.github.alcoloid0.nsfwplugin.extra.Settings
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import revxrsal.commands.locales.LocaleReader
import revxrsal.commands.locales.Locales
import java.util.*

class SettingsLocaleReader(private val settings: Settings) : LocaleReader {
    override fun containsKey(key: String?)
            = settings.yamlConfiguration.contains("message.$key")

    override fun get(key: String?): String {
        val component = Component.empty().color(NamedTextColor.WHITE)
        component.append(settings.message(key!!, ARGUMENT_TAG_RESOLVER))
        return LegacyComponentSerializer.legacySection().serialize(component)
    }

    override fun getLocale(): Locale = Locales.ENGLISH

    companion object {
        private val ARGUMENT_TAG_RESOLVER = TagResolver.builder()
            .resolver(Placeholder.parsed("parameter", "{0}"))
            .resolver(Placeholder.parsed("argument", "{0}"))
            .resolver(Placeholder.parsed("input", "{1}"))
            .build()
    }
}