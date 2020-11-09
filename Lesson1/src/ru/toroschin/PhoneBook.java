package ru.toroschin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhoneBook {
    private final Map<String, String> phoneBook;

    PhoneBook() {
        phoneBook = new HashMap<>();
    }

    public void add(String fio, String phone) {
        // добавляем телефон и фио
        phoneBook.put(phone, fio);
    }

    public Set get(String fio) {
        // ищем номер телефона по фамилии
        if (phoneBook.containsValue(fio)) {

            // есть такая фамилия, ищем телефон
            Set<String> phoneNumbers = new HashSet<>();
            for (Map.Entry tel : phoneBook.entrySet()) {
                if (tel.getValue().equals(fio)) {
                    phoneNumbers.add(String.valueOf(tel.getKey()));
                }
            }
            return phoneNumbers;
        } else {
            return new HashSet();
        }
    }
}
