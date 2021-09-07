package com.tdd.databuilder;

import com.github.javafaker.Faker;
import com.tdd.parallel.entity.standard.PersonStandard;
import lombok.Builder;
import lombok.Getter;

import java.util.Locale;

@Builder
@Getter
public class PersonBuilder {

  private final PersonStandard person;

  private static final Faker faker = new Faker(new Locale("en-CA.yml"));


  public static PersonBuilder personWithName() {
    PersonStandard person = new PersonStandard();
    person.setName(faker.artist()
                        .name());
    return PersonBuilder.builder()
                        .person(person)
                        .build();
  }


  public static PersonBuilder personWithIdAndName() {
    PersonStandard person = new PersonStandard();
    person.setId(faker.regexify("[A-Za-z]{12}"));
    person.setName(faker.artist()
                        .name());
    return PersonBuilder.builder()
                        .person(person)
                        .build();
  }


  public PersonStandard create() {
    return this.person;
  }
}
