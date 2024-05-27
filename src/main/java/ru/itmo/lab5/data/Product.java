package ru.itmo.lab5.data;

import ru.itmo.lab5.utility.DateTimeHandler;
import ru.itmo.lab5.utility.Validateable;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import ru.itmo.lab5.input.Console;
import java.util.Arrays;


public class Product implements Validateable, Comparable<Product> {
    private Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private Date creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer price; // Поле может быть null, Значение поля должно быть больше 0
    private UnitOfMeasure unitOfMeasure; // Поле может быть null
    private Person owner; // Поле может быть null
    public static long nextId = 1; // Статическое поле для генерации уникальных идентификаторов продуктов

    /**
     * Конструктор для создания объекта класса Product.
     *
     * @param name          наименование продукта
     * @param coordinates   координаты продукта
     * @param price         цена продукта
     * @param unitOfMeasure единица измерения продукта
     * @param owner         владелец продукта
     */
    public Product(String name, Coordinates coordinates, Integer price, UnitOfMeasure unitOfMeasure, Person owner) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
        this.id = nextId++; // Генерация уникального идентификатора для продукта
    }

    /**
     * Конструктор для создания объекта класса Product с заданным идентификатором.
     *
     * @param id            идентификатор продукта
     * @param name          наименование продукта
     * @param coordinates   координаты продукта
     * @param creationDate  дата создания продукта
     * @param price         цена продукта
     * @param unitOfMeasure единица измерения продукта
     * @param owner         владелец продукта
     */
    public Product(Long id, String name, Coordinates coordinates, Instant creationDate, Integer price, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = Date.from(creationDate);
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
    }

    /**
     * Проверяет валидность объекта Product.
     *
     * @return true, если объект валиден, иначе false
     */
    @Override
    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (price == null || price < 0) return false;
        if (unitOfMeasure == null) return false;
        return owner != null;
    }


    /**
     * Сравнивает данный объект Product с указанным объектом Product по цене и имени.
     *
     * @param o объект Product для сравнения
     * @return отрицательное целое число, ноль или положительное целое число, если этот объект меньше, равен или больше указанного объекта Product
     */
    @Override
    public int compareTo(Product o) {
        int price = this.price.compareTo(o.price);
        if (price != 0) {
            return price;
        }
        return this.name.compareTo(o.name);
    }

    /**
     * Возвращает строковое представление объекта Product.
     *
     * @return строковое представление объекта Product
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", unitOfMeasure=" + unitOfMeasure +
                ", owner=" + owner +
                '}';
    }

    /**
     * Проверяет равенство данного объекта Product с указанным объектом на равенство.
     *
     * @param o объект для сравнения
     * @return true, если объекты равны, иначе false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(coordinates, product.coordinates) && Objects.equals(creationDate, product.creationDate) && Objects.equals(price, product.price) && unitOfMeasure == product.unitOfMeasure && Objects.equals(owner, product.owner);
    }


    /**
     * Обновляет данные текущего объекта Product данными из другого объекта Product.
     *
     * @param product объект Product, данные которого будут использованы для обновления текущего объекта
     */
    public void update(Product product) {
        this.name = product.name;
        this.coordinates = product.coordinates;
        this.creationDate = product.creationDate;
        this.price = product.price;
        this.unitOfMeasure = product.unitOfMeasure;
        this.owner = product.owner;
    }

    /**
     * Возвращает идентификатор данного объекта Product.
     *
     * @return идентификатор объекта Product
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Integer getPrice() {
        return price;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    public static Product fromArray(String[] a) {
        if (a == null || a.length < 7) {
            throw new IllegalArgumentException("Недостаточно данных для создания продукта");
        }

        try {
            long id = Long.parseLong(a[0]);
            if (id <= 0) {
                throw new IllegalArgumentException("Неверное значение для id: " + a[0]);
            }

            String name = a[1];
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Неверное значение для name: " + a[1]);
            }

            Coordinates coordinates = Coordinates.fromString(a[2]);

            Instant creationDate = Instant.parse(a[3]);

            Integer price;
            if ("null".equalsIgnoreCase(a[4])) {
                price = null;
            } else {
                price = Integer.parseInt(a[4]);
                if (price <= 0) {
                    throw new IllegalArgumentException("Неверное значение для price: " + a[4]);
                }
            }

            UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(a[5]);

            Person owner;
            if ("null".equalsIgnoreCase(a[6])) {
                owner = null;
            } else {
                owner = Person.fromString(a[6]);
            }

            return new Product(id, name, coordinates, creationDate, price, unitOfMeasure, owner);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new IllegalArgumentException("Ошибка парсинга значений", e);
        }
    }


    public static String[] toArray(Product e) {
        ArrayList<String> list = new ArrayList<>();

        if (e.getId() == null || e.getId() <= 0) {
            throw new IllegalArgumentException("Неверное значение для id: " + e.getId());
        }
        list.add(e.getId().toString());

        if (e.getName() == null || e.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Неверное значение для name: " + e.getName());
        }
        list.add(e.getName());

        if (!e.getCoordinates().validate()) {
            throw new IllegalArgumentException("Неверное значение для coordinates: " + e.getCoordinates());
        }
        list.add(e.getCoordinates().toString());

        Instant creationDate = e.getCreationDate().toInstant();
        list.add(DateTimeHandler.formatInstantToString(creationDate));

        list.add(e.getPrice() == null || e.getPrice() <= 0 ? "null" : e.getPrice().toString());

        if (e.getUnitOfMeasure() == null) {
            throw new IllegalArgumentException("Неверное значение для unitOfMeasure: null");
        }
        list.add(e.getUnitOfMeasure().toString());

        if (e.getOwner() != null && !e.getOwner().validate()) {
            throw new IllegalArgumentException("Некорректные данные для owner: " + e.getOwner());
        }
        list.add(e.getOwner() == null ? "null" : e.getOwner().toString());

        return list.toArray(new String[0]);
    }



}