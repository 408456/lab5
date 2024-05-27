package ru.itmo.lab5.data;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Перечисление, представляющее единицы измерения.
 */
public enum UnitOfMeasure {
    METERS,         // Метры
    CENTIMETERS,    // Сантиметры
    LITERS;         // Литры

    /**
     * Возвращает строку, содержащую названия всех элементов перечисления, разделенных запятыми.
     *
     * @return строка с названиями единиц измерения
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (UnitOfMeasure unitOfMeasure: values()) {
            nameList.append(unitOfMeasure.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
