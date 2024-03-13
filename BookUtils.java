package net.glitchtechs.starapi;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;

public class BookUtils {
    public static ItemStack createBookGUI(String title, String author, int totalPages) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta)book.getItemMeta();
        bookMeta.setTitle(title);
        bookMeta.setAuthor(author);
        bookMeta.setGeneration(Generation.ORIGINAL);

        for(int i = 1; i <= totalPages; ++i) {
            bookMeta.addPage(new String[]{String.valueOf(i)});
        }

        book.setItemMeta(bookMeta);
        return book;
    }

    public static void setPageContent(BookMeta bookMeta, int pageNumber, String clickUrl, String hoverText, String page, String... lines) {
        if (pageNumber >= 1 && pageNumber <= 50) {
            BaseComponent[] components = TextComponent.fromLegacyText(MessageUtils.hex(String.join("\n", lines)));
            ClickEvent clickEvent;
            if (clickUrl != null && !clickUrl.isEmpty()) {
                clickEvent = new ClickEvent(Action.OPEN_URL, clickUrl);
                components[components.length - 1].setClickEvent(clickEvent);
            }

            if (hoverText != null && !hoverText.isEmpty()) {
                HoverEvent hoverEvent = new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(MessageUtils.hex(hoverText)));
                components[components.length - 1].setHoverEvent(hoverEvent);
            }

            if (page != null && !page.isEmpty()) {
                clickEvent = new ClickEvent(Action.CHANGE_PAGE, page);
                components[components.length - 1].setClickEvent(clickEvent);
            }

            bookMeta.spigot().setPage(pageNumber, components);
        } else {
            throw new IllegalArgumentException("Invalid page number " + pageNumber);
        }
    }
}