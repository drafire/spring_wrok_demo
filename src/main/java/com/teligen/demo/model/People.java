package com.teligen.demo.model;

public class People {
    private String name;
    private int age;
    private int sex;

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public People() {
    }

    public People(String name, int age, int sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
