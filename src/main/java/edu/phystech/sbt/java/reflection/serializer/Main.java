package edu.phystech.sbt.java.reflection.serializer;

public class Main {
    public static void main(String[] args) {
        Serializer serializer1 = new JsonSerializer();
        Person p = new Person();
        System.out.println(serializer1.serialize(p));
        Serializer serializer2 = new XMLSerializer();
        System.out.println(serializer2.serialize(p));
    }
}
