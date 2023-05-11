package ru.ma1gus.practice.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Person {

    private int id;
    @NotEmpty(message = "Укажите имя!")
    @Size(min = 1, max = 30, message = "Имя должно быть больше одной буквы, но не больше 30 букв.")
    private String name;

    @Min(value = 0, message = "Возраст должен быть больше нуля.")
    private int age;

    @NotEmpty(message = "Укажите почту!")
    @Email(message = "Почта введена некорректно.")
    private String email;

    @Pattern(regexp = "[а-яА-Я]+, [а-яА-Я]+, \\d{6}", message = "Адрес должен соответствовать формату: Страна, Город, почтовый индекс(6 цифр)")
    private String address;
    public Person() {}
    public Person(int id, String name, int age, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    // Конструктор для теста пакетной вставки
    public Person(int i, String s, int i1, String s1) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}
