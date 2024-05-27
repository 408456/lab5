package ru.itmo.lab5.data;

import ru.itmo.lab5.utility.Validateable;

import java.util.Objects;


/**
 * Класс, представляющий координаты.
 */
public class Coordinates implements Validateable {
    private Integer x; // Значение поля должно быть больше -454, Поле не может быть null
    private Double y; // Поле не может быть null

    /**
     * Конструктор класса.
     *
     * @param x координата x
     * @param y координата y
     */
    public Coordinates(Integer x, Double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Проверяет, что значения координат удовлетворяют условиям.
     *
     * @return true, если координаты валидны, иначе false
     */
    @Override
    public boolean validate() {
        if (x == null || y == null) return false;
        return x > -454;
    }

    /**
     * Переопределение метода toString.
     *
     * @return строковое представление объекта Coordinates
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Переопределение методов equals и hashCode.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static Coordinates fromString(String s) {
        try {
            // Ищем подстроку "Coordinates"
            int startIndex = s.indexOf("Coordinates");
            if (startIndex == -1) {
                throw new IllegalArgumentException("Неверный формат строки для Coordinates");
            }

            // Получаем подстроку, содержащую координаты
            String coordSubstr = s.substring(startIndex);

            // Ищем индексы начала и конца блока координат
            int startIndexX = coordSubstr.indexOf("x=");
            int endIndexX = coordSubstr.indexOf(",", startIndexX);

            int startIndexY = coordSubstr.indexOf("y=");
            int endIndexY = coordSubstr.indexOf("}", startIndexY);

            // Извлекаем значения координат
            Integer x = Integer.parseInt(coordSubstr.substring(startIndexX + 2, endIndexX));
            Double y = Double.parseDouble(coordSubstr.substring(startIndexY + 2, endIndexY));

            if (x <= -454) {
                throw new IllegalArgumentException("Неверное значение для x: " + x);
            }

            if (y == null) {
                throw new IllegalArgumentException("Неверное значение для y");
            }

            return new Coordinates(x, y);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка преобразования строки в объект Coordinates", e);
        }
    }




}
