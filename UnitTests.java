package test;

import java.util.List;

import fmi.informatics.extending.Person;

public class UnitTests {

	public static void TestAscendingSortPeopleByAge(List<Person> people){

        for (int i = 0;i < people.size() - 1;i++) {
            if (people.get(i).getAge() > people.get(i+1).getAge()) {
                System.out.println("Сортиране работи грешно");
                return;
            }
        }
        System.out.println("Сортирането работи:Д");

    }
}
