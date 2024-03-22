package net.glitchtechs.starapi

import java.io.*

object FileBuilder {
    fun createFolder(folderPath: String?) {
        val folder = File(folderPath)
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                println("Folder created: " + folder.absolutePath)
                folder.mkdirs()
            } else {
                System.err.println("Failed to create folder: " + folder.absolutePath)
            }
        } else {
            println("Folder already exists: " + folder.absolutePath)
        }
    }

    fun createFile(filePath: String?) {
        val file = File(filePath)
        val parentDir = file.parentFile
        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                println("Parent directories created: " + parentDir.absolutePath)
            } else {
                System.err.println("Failed to create parent directories: " + parentDir.absolutePath)
            }
        }

        try {
            if (file.createNewFile()) {
                println("File created: " + file.absolutePath)
                file.mkdirs()
            } else {
                println("File already exists: " + file.absolutePath)
            }
        } catch (var4: IOException) {
            System.err.println("Failed to create file: " + file.absolutePath)
            var4.printStackTrace()
        }
    }

    fun writeToFile(filePath: String, content: String?) {
        try {
            val writer = BufferedWriter(FileWriter(filePath))

            try {
                writer.write(content)
                println("Content written to file: $filePath")
            } catch (var6: Throwable) {
                try {
                    writer.close()
                } catch (var5: Throwable) {
                    var6.addSuppressed(var5)
                }

                throw var6
            }

            writer.close()
        } catch (var7: IOException) {
            System.err.println("Failed to write to file: $filePath")
            var7.printStackTrace()
        }
    }

    fun readFromFile(filePath: String): String {
        val content = StringBuilder()

        try {
            val reader = BufferedReader(FileReader(filePath))

            var line: String?
            try {
                while ((reader.readLine().also { line = it }) != null) {
                    content.append(line).append(System.lineSeparator())
                }
            } catch (var6: Throwable) {
                try {
                    reader.close()
                } catch (var5: Throwable) {
                    var6.addSuppressed(var5)
                }

                throw var6
            }

            reader.close()
        } catch (var7: IOException) {
            System.err.println("Failed to read from file: $filePath")
            var7.printStackTrace()
        }

        return content.toString()
    }
}