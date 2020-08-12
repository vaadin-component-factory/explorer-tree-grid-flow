package org.vaadin.explorer.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author jcgueriaud
 */
public class PersonUtil {

    public static List<Person> buildPersons(int offset, int size) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            persons.add(generateRandom(offset + i));
        }
        return persons;
    }

    public static List<Person> buildPersons() {
        return buildPersons(0,1000);
    }

    public static Person generateRandom(int id) {
        Person person = new Person();
        person.setId(id);
        person.setFirstName(generateRandomString());
        person.setLastName(generateRandomString());
        person.setAge(generateRandomInt(99));
        person.setPrice(new Price(generateRandomInt(1000)));
        return person;
    }

    private static String generateRandomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


    private static int generateRandomInt(int bound){
        Random random = new Random();
        return random.nextInt(bound);
    }
}
