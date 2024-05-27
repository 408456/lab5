package ru.itmo.lab5.data;

import ru.itmo.lab5.utility.Validateable;

import java.util.Objects;

/**
 * Класс, представляющий местоположение.
 */
public class Location implements Validateable {
    private long x;          // Координата X
    private int y;           // Координата Y
    private String name;     // Название местоположения; Поле не может быть null

    /**
     * Конструктор класса.
     *
     * @param x    координата X
     * @param y    координата Y
     * @param name название местоположения
     */
    public Location(long x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    /**
     * Проверяет, что местоположение валидно.
     *
     * @return true, если местоположение валидно, иначе false
     */
    @Override
    public boolean validate() {
        // Проверяем, что поле name не равно null
        return name != null;
    }

    /**
     * Переопределение методов equals и hashCode.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }
    /**
     * Переопределение метода toString.
     *
     * @return строковое представление объекта Location
     */
    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
    /**
     * Конструктор, принимающий строку вида "x ; y ; name".
     *
     * @param s строка с данными о местоположении
     * @throws IllegalArgumentException если строка не соответствует ожидаемому формату
     */
    public Location(String s) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка для создания объекта Location не может быть null или пустой");
        }

        try {
            String[] parts = s.split(" ; ");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Некорректный формат строки для создания объекта Location: ожидалось 3 части, получено " + parts.length);
            }

            long x;
            int y;
            String name;

            try {
                x = parts[0].equals("null") ? -1 : Long.parseLong(parts[0].trim());
                if (x < 0 && !parts[0].equals("null")) {
                    throw new IllegalArgumentException("Значение x должно быть неотрицательным или равным null");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректное значение для x: " + parts[0]);
            }

            try {
                y = parts[1].equals("null") ? 0 : Integer.parseInt(parts[1].trim());
                if (y < 0 && !parts[1].equals("null")) {
                    throw new IllegalArgumentException("Значение y должно быть неотрицательным или равным null");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректное значение для y: " + parts[1]);
            }

            name = parts[2].trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Название местоположения не может быть пустым");
            }

            this.x = x;
            this.y = y;
            this.name = name;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка при создании объекта Location из строки: " + s, e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неизвестная ошибка при парсинге строки в объект Location", e);
        }
    }

    public static Location fromString(String s) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка для парсинга Location не может быть null или пустой");
        }

        try {
            String[] parts = s.split(" ; ");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Некорректный формат строки для создания объекта Location: ожидалось 3 части, получено " + parts.length);
            }

            long x;
            int y;
            String name;

            try {
                x = parts[0].equals("null") ? -1 : Long.parseLong(parts[0].trim());
                if (x < 0 && !parts[0].equals("null")) {
                    throw new IllegalArgumentException("Значение x должно быть неотрицательным или равным null");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректное значение для x: " + parts[0]);
            }

            try {
                y = parts[1].equals("null") ? 0 : Integer.parseInt(parts[1].trim());
                if (y < 0 && !parts[1].equals("null")) {
                    throw new IllegalArgumentException("Значение y должно быть неотрицательным или равным null");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректное значение для y: " + parts[1]);
            }

            name = parts[2].trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Название местоположения не может быть пустым");
            }

            return new Location(x, y, name);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка при создании объекта Location из строки: " + s, e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неизвестная ошибка при парсинге строки в объект Location", e);
        }
    }


}
