package net.glitchtechs.starapi

import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.EntityType


object MessageBuilder {
    fun buildHoverText(baseComponent: TextComponent, hoverText: String?): TextComponent {
        baseComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent(hoverText)))
        return baseComponent
    }

    fun buildCommandText(text: String?, clickCommand: String?): TextComponent {
        val component = TextComponent(text)
        component.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, clickCommand)
        return component
    }

    fun buildEntityText(text: String?, entityType: EntityType): TextComponent {
        val component = TextComponent(text)
        component.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_ENTITY, arrayOf<BaseComponent>(TextComponent("{id:\"minecraft:" + entityType.key.key + "\"}")))
        return component
    }

    fun buildOpenUrlText(text: String?, url: String?): TextComponent {
        val component = TextComponent(text)
        component.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, url)
        return component
    }
}