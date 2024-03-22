package net.glitchtechs.starapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileBuilder {
    public static void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Folder created: " + folder.getAbsolutePath());
                folder.mkdirs();
            } else {
                System.err.println("Failed to create folder: " + folder.getAbsolutePath());
            }
        } else {
            System.out.println("Folder already exists: " + folder.getAbsolutePath());
        }

    }

    public static void createFile(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Parent directories created: " + parentDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create parent directories: " + parentDir.getAbsolutePath());
            }
        }

        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getAbsolutePath());
                file.mkdirs();
            } else {
                System.out.println("File already exists: " + file.getAbsolutePath());
            }
        } catch (IOException var4) {
            System.err.println("Failed to create file: " + file.getAbsolutePath());
            var4.printStackTrace();
        }

    }

    public static void writeToFile(String filePath, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            try {
                writer.write(content);
                System.out.println("Content written to file: " + filePath);
            } catch (Throwable var6) {
                try {
                    writer.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            writer.close();
        } catch (IOException var7) {
            System.err.println("Failed to write to file: " + filePath);
            var7.printStackTrace();
        }

    }

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