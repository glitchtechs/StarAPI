package net.glitchtechs.starapi

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta


object BookUtils {
    fun createBookGUI(title: String?, author: String?, totalPages: Int): ItemStack {
        val book = ItemStack(Material.WRITTEN_BOOK)
        val bookMeta = book.itemMeta as BookMeta?
        bookMeta!!.setTitle(title)
        bookMeta.author = author
        bookMeta.generation = BookMeta.Generation.ORIGINAL

        for (i in 1..totalPages) {
            bookMeta.addPage(*arrayOf(i.toString()))
        }

        book.setItemMeta(bookMeta)
        return book
    }

    fun setPageContent(bookMeta: BookMeta, pageNumber: Int, clickUrl: String?, hoverText: String?, page: String?, vararg lines: String?) {
        if (pageNumber >= 1 && pageNumber <= 50) {
            val components = TextComponent.fromLegacyText(MessageUtils.hex(java.lang.String.join("\n", *lines)))
            var clickEvent: ClickEvent
            if (clickUrl != null && !clickUrl.isEmpty()) {
                clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, clickUrl)
                components[components.size - 1].clickEvent = clickEvent
            }

            if (hoverText != null && !hoverText.isEmpty()) {
                val hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(MessageUtils.hex(hoverText)))
                components[components.size - 1].hoverEvent = hoverEvent
            }

            if (page != null && !page.isEmpty()) {
                clickEvent = ClickEvent(ClickEvent.Action.CHANGE_PAGE, page)
                components[components.size - 1].clickEvent = clickEvent
            }

            bookMeta.spigot().setPage(pageNumber, *components)
        } else {
            throw IllegalArgumentException("Invalid page number $pageNumber")
        }
    }
}