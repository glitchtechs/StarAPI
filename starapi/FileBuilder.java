package net.glitch.starapi;

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

        // Create parent directories if they don't exist
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
        } catch (IOException e) {
            System.err.println("Failed to create file: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public static void writeToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("Content written to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }

    public static String readFromFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Failed to read from file: " + filePath);
            e.printStackTrace();
        }

        return content.toString();
    }

}

