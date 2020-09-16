package com.company.lab2;

import com.company.lab2.domain.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lab2Main {

    public static void main(String[] args) {
        try {
            Person person1 = new Person("Alex", "Un", "alex@ya.ru", "+79951231223");
            Person person2 = new Person("Liza", "Do", "liza@ya.ru", "+79911231921");
            Person person3 = new Person("Marzia", "Vang", "marzia@ya.ru", "+7991312313");
            List<Person> personList = new ArrayList<>();
            personList.add(person1);
            personList.add(person2);
            personList.add(person3);
            Lab2SaveDB.savePersonList(personList);

            List<Person> persons = Lab2LoadDB.loadPersonList();

            persons.forEach(System.out::println);

            Person prs = PersonService.findPersonByName(persons, "Liza");

            if (prs != null) {
                prs.setPhone("+71111111111");
                prs.setEmail("ivan@mail.ru");
            }

            Lab2SaveDB.savePersonList(persons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
