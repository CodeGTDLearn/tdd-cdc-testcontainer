package com.tdd.databuilder;

import com.github.javafaker.Faker;
import com.tdd.parallel.entity.PersonOnlyName;
import lombok.Builder;
import lombok.Getter;

import java.util.Locale;

@Builder
@Getter
public class PersonSlimBuilder {

  private static final Faker faker = new Faker(new Locale("en-CA.yml"));
  private final PersonOnlyName personType;


  public static PersonSlimBuilder personOnlyName() {
    PersonOnlyName person = new PersonOnlyName();
    person.setName("personSlimJUST_Name");
    return PersonSlimBuilder.builder()
                            .personType(person)
                            .build();
  }


  public PersonOnlyName create() {
    return this.personType;
  }
}
