package ru.itmo.lab5.data;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Перечисление, представляющее возможные страны.
 */
public enum Country {
    UNITED_KINGDOM, // Соединенное Королевство
    GERMANY,        // Германия
    FRANCE,         // Франция
    ITALY;          // Италия

    /**
     * Возвращает строку, содержащую названия всех стран, разделенных запятыми.
     *
     * @return строка с названиями стран
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (Country country : values()) {
            nameList.append(country.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
