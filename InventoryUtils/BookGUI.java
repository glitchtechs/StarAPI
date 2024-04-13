package net.glitchtechs.starapi.InventoryUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookGUI {

    public static ItemStack createBookGUI(String title, String author, int totalPages) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta)book.getItemMeta();
        bookMeta.setTitle(title);
        bookMeta.setAuthor(author);
        bookMeta.setGeneration(BookMeta.Generation.ORIGINAL);

        for(int i = 1; i <= totalPages; ++i) {
            bookMeta.addPage(new String[]{String.valueOf(i)});
        }

        book.setItemMeta(bookMeta);
        return book;
    }

}
