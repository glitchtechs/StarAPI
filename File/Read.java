package net.glitchtechs.starapi.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Read {

    public static String readFromFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            try {
                while((line = reader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
            } catch (Throwable var6) {
                try {
                    reader.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            reader.close();
        } catch (IOException var7) {
            System.err.println("Failed to read from file: " + filePath);
            var7.printStackTrace();
        }

        return content.toString();
    }

}
