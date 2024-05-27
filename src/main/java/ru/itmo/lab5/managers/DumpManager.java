package ru.itmo.lab5.managers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import ru.itmo.lab5.data.Product;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

import ru.itmo.lab5.input.Console;

public class DumpManager {
    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Имя файла не должно быть пустым или null");
        }
        this.fileName = fileName;
        this.console = console;
    }

    private String collection2CSV(Collection<Product> collection) {
        try {
            StringWriter sw = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(sw);
            for (Product e : collection) {
                String[] array = Product.toArray(e);
                csvWriter.writeNext(array);
            }
            csvWriter.close();
            return sw.toString();
        } catch (Exception e) {
            console.printError("Ошибка сериализации: " + e.getMessage());
            return null;
        }
    }

    public void writeCollection(Collection<Product> collection) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String csv = collection2CSV(collection);
            if (csv == null) return;
            writer.write(csv);
            console.println("Коллекция успешно сохранена в файл!");
        } catch (IOException e) {
            console.printError("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private LinkedList<Product> CSV2collection(String s) {
        console.println("Содержимое файла перед обработкой:");
        console.println(s.toString());
        LinkedList<Product> ds = new LinkedList<>();
        if (s.isEmpty() || !s.contains(",")) {
            console.println("Загрузочный файл пуст или не содержит данных: " + fileName);
            return ds;
        }

        try {
            StringReader sr = new StringReader(s);
//            CSVReader csvReader = new CSVReader(sr);
            CSVReader csvReader = new CSVReaderBuilder(sr)
                    .withSkipLines(1) // если у вас есть заголовок
                    .build();
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                try {
                    Product p = Product.fromArray(record);
                    if (p != null && p.validate()) {
                        ds.add(p);
                    } else {
                        console.printError("Файл с коллекцией содержит недействительные данные: " + String.join(", ", record));
                    }
                } catch (IllegalArgumentException e) {
                    console.printError("Ошибка при обработке строки: " + String.join(", ", record));
                    console.printError(e.getMessage());
                }
            }
            csvReader.close();
        } catch (Exception e) {
            console.printError("Ошибка десериализации: " + e.getMessage());
        }
        return ds;
    }

    public LinkedList<Product> readCollection() {
        LinkedList<Product> collection = new LinkedList<>();
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                console.printError("Загрузочный файл не найден: " + fileName);
                return collection;
            }

            StringBuilder s = new StringBuilder();

            try (Scanner fileReader = new Scanner(file)) {
                while (fileReader.hasNextLine()) {
                    s.append(fileReader.nextLine());
                    s.append("\n");
                }
            }

            LinkedList<Product> loadedCollection = CSV2collection(s.toString());
            if (!loadedCollection.isEmpty()) {
                console.println("Коллекция успешно загружена!");
                return loadedCollection;
            } else {
                console.println("В загрузочном файле не обнаружена необходимая коллекция!");
            }
        } catch (FileNotFoundException exception) {
            console.printError("Загрузочный файл не найден: " + fileName);
        } catch (Exception e) {
            console.printError("Ошибка при чтении файла: " + e.getMessage());
        }
        return collection;
    }
}
