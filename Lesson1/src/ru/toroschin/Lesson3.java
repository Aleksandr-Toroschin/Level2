package ru.toroschin;

import java.util.*;

public class Lesson3 {
    public static void main(String[] args) {
        // 1. Задание. Создать массив из слов ...
        makeArray();

        // 2. Задание. Телефонный справочник ...
        usePhoneBook();
    }

    public static void makeArray() {
        String[] words = {"Russia", "England", "Germany", "France", "Belarus",
                        "Ukraine", "Russia", "China", "Brazil", "Egypt",
                        "India", "Japan", "China", "Russia", "China",
                        "Russia", "India", "China", "Japan", "France"};

        Map<String, Integer> map = new HashMap<>();
        int val;
        for (String addWords: words) {
            if (map.containsKey(addWords)) {
                val = map.get(addWords);
                map.replace(addWords, val, ++val);
            } else {
                map.put(addWords, 1);
            }
        }
        System.out.println("Список уникальных слов и их количество:");
        System.out.println(map);
    }

    public static void usePhoneBook() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("А", "89998889999");
        phoneBook.add("Б", "89990009999");
        phoneBook.add("В", "89998881111");
        phoneBook.add("Г", "89998880000");
        phoneBook.add("А", "89998883333");
        phoneBook.add("А", "89990004444");
        phoneBook.add("Б", "89008889999");

        System.out.println("Список телефонов по фамилии:");
        System.out.println(phoneBook.get("А"));
    }
}
