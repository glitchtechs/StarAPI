package net.glitchtechs.starapi.InventoryUtils;

import net.glitchtechs.starapi.MessageUtils.MessageColors;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.meta.BookMeta;

public class BookContent {

    public static void setPageContent(BookMeta bookMeta, int pageNumber, String clickUrl, String hoverText, String page, String... lines) {
        if (pageNumber >= 1 && pageNumber <= 50) {
            BaseComponent[] components = TextComponent.fromLegacyText(MessageColors.hex(String.join("\n", lines)));
            ClickEvent clickEvent;
            if (clickUrl != null && !clickUrl.isEmpty()) {
                clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, clickUrl);
                components[components.length - 1].setClickEvent(clickEvent);
            }

            if (hoverText != null && !hoverText.isEmpty()) {
                HoverEvent hoverEvent = new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(MessageColors.hex(hoverText)));
                components[components.length - 1].setHoverEvent(hoverEvent);
            }

            if (page != null && !page.isEmpty()) {
                clickEvent = new ClickEvent(ClickEvent.Action.CHANGE_PAGE, page);
                components[components.length - 1].setClickEvent(clickEvent);
            }

            bookMeta.spigot().setPage(pageNumber, components);
        } else {
            throw new IllegalArgumentException("Invalid page number " + pageNumber);
        }
    }

}
