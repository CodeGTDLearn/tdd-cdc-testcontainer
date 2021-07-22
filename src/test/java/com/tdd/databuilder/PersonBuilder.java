package com.tdd.databuilder;

import com.github.javafaker.Faker;
import com.tdd.parallel.entity.Person;
import lombok.Builder;
import lombok.Getter;

import java.util.Locale;

@Builder
@Getter
public class PersonBuilder {

  private final Person person;

  private static final Faker faker = new Faker(new Locale("en-CA.yml"));


  public static PersonBuilder personWithName() {
    Person person = new Person();
    person.setName(faker.artist()
                        .name());
    return PersonBuilder.builder()
                        .person(person)
                        .build();
  }


  public static PersonBuilder personWithIdAndName() {
    Person person = new Person();
    person.setId(faker.regexify("[A-Za-z]{12}"));
    person.setName(faker.artist()
                        .name());
    return PersonBuilder.builder()
                        .person(person)
                        .build();
  }


  public Person create() {
    return this.person;
  }
}
