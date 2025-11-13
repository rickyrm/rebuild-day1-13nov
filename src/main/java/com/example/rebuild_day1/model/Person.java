package com.example.rebuild_day1.model;

public class Person {

    private String name;
    private String secondName;
    private int age;
    private String city;

    public Person(String name, String secondName, int age, String city) {
        this.name=name;
        this.secondName=secondName;
        this.age=age;
        this.city=city;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
