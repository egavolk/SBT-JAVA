package edu.phystech.sbt.java.reflection.serializer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Person {
    private final String firstName = "Egor";
    private final String lastName = "Volkov";
    private final Address address = new Address();
    private final List<String> phoneNumbers = new ArrayList<>();
    private final Set<String> phoneNumbers1 = new HashSet<>();
    private final int[] phoneNumbers2 = new int[3];
    private final Integer[] phoneNumbers3 = new Integer[3];


    Person() {
        phoneNumbers.add("111");
        phoneNumbers.add("222");
        phoneNumbers1.add("333");
        phoneNumbers1.add("444");
    }
}

class Address {
    private final String city = "Cherepovets";
    private final String postalCode = "162626";
}