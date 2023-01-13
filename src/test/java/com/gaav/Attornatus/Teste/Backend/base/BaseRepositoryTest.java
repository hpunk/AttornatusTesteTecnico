package com.gaav.Attornatus.Teste.Backend.base;

import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseRepositoryTest {
    protected Person getPerson() {
        Person person = new Person();
        person.setName("Carlos");
        person.setBirthDate(Date.valueOf(LocalDate.of(2000, Month.JANUARY,24)));
        return person;
    }

    protected Address getAddress() {
        Address newAddress = new Address();

        newAddress.setZip("3451-000");
        newAddress.setCity("Rio Pequeno do Meio");
        newAddress.setNumber("34");
        newAddress.setStreet("Rua Ruan");
        newAddress.setIsMain(false);
        return newAddress;
    }

    protected List<Address> generateListOfAddressesForPerson(Person person) {
        Address newAddress1 = new Address();
        newAddress1.setZip("3451-000");
        newAddress1.setCity("Rio Pequeno do Meio");
        newAddress1.setNumber("31");
        newAddress1.setStreet("Rua Oxigenio");
        newAddress1.setIsMain(false);
        newAddress1.setPerson(person);

        Address newAddress2 = new Address();
        newAddress2.setZip("3452-000");
        newAddress2.setCity("Rio Pequeno do Meio");
        newAddress2.setNumber("32");
        newAddress2.setStreet("Rua Cobre");
        newAddress2.setIsMain(false);
        newAddress2.setPerson(person);

        Address newAddress3 = new Address();
        newAddress3.setZip("3453-000");
        newAddress3.setCity("Rio Pequeno do Meio");
        newAddress3.setNumber("33");
        newAddress3.setStreet("Rua Prata");
        newAddress3.setIsMain(false);
        newAddress3.setPerson(person);

        Address newAddress4 = new Address();
        newAddress4.setZip("3454-000");
        newAddress4.setCity("Rio Pequeno do Meio");
        newAddress4.setNumber("34");
        newAddress4.setStreet("Rua Ouro");
        newAddress4.setIsMain(false);
        newAddress4.setPerson(person);

        Address newAddress5 = new Address();
        newAddress5.setZip("3455-000");
        newAddress5.setCity("Rio Pequeno do Meio");
        newAddress5.setNumber("35");
        newAddress5.setStreet("Rua Calcio");
        newAddress5.setIsMain(false);
        newAddress5.setPerson(person);

        Address newAddress6 = new Address();
        newAddress6.setZip("3456-000");
        newAddress6.setCity("Rio Pequeno do Meio");
        newAddress6.setNumber("36");
        newAddress6.setStreet("Rua Magnesio");
        newAddress6.setIsMain(false);
        newAddress6.setPerson(person);

        return List.of(newAddress1, newAddress2, newAddress3, newAddress4, newAddress5, newAddress6);
    }

    protected List<Person> generateListOfPerson() {
        Person person1 = new Person();
        person1.setName("Carlos");
        person1.setBirthDate(Date.valueOf(LocalDate.of(2000, Month.JANUARY,24)));

        Person person2 = new Person();
        person2.setName("Marcos");
        person2.setBirthDate(Date.valueOf(LocalDate.of(2001, Month.FEBRUARY,12)));

        Person person3 = new Person();
        person3.setName("Luiza");
        person3.setBirthDate(Date.valueOf(LocalDate.of(2002, Month.MARCH,12)));

        Person person4 = new Person();
        person4.setName("Joao");
        person4.setBirthDate(Date.valueOf(LocalDate.of(1998, Month.DECEMBER,1)));

        Person person5 = new Person();
        person5.setName("Ze");
        person5.setBirthDate(Date.valueOf(LocalDate.of(2010, Month.JANUARY,26)));

        Person person6 = new Person();
        person6.setName("Maria");
        person6.setBirthDate(Date.valueOf(LocalDate.of(2007, Month.JULY,7)));

        return List.of(person1, person2, person3, person4, person5, person6);
    }
}
