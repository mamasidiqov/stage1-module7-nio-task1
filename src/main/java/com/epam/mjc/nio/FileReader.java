package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class FileReader {

    public Profile getDataFromFile(File file) {
        String name = null;
        Integer age = null;
        String email = null;
        Long phone = null;

        // try-with-resources: stream avtomatik yopiladi (testCodeHasStreamClosing uchun)
        try (Stream<String> lines = Files.lines(file.toPath())) {
            for (String line : (Iterable<String>) lines::iterator) {
                String[] parts = line.split(":", 2);
                if (parts.length < 2) {
                    continue;
                }
                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "Name":
                        name = value;
                        break;
                    case "Age":
                        age = Integer.parseInt(value);
                        break;
                    case "Email":
                        email = value;
                        break;
                    case "Phone":
                        phone = Long.parseLong(value);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Faylni o'qishda xatolik: " + file, e);
        }

        return new Profile(name, age, email, phone);
    }
}