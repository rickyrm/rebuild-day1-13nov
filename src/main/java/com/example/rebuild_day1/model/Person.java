package com.example.rebuild_day1.model;

public class Person {

    private String id;
    private String name;
    private String secondName;
    private int age;
    private String city;

    public Person(String id, String name, String secondName, int age, String city) {
        this.id=id;
        this.name=name;
        this.secondName=secondName;
        this.age=age;
        this.city=city;

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
