package com.tdd.databuilder;

import com.github.javafaker.Faker;
import com.tdd.parallel.entity.standard.PersonStandard;
import lombok.Builder;
import lombok.Getter;

import java.util.Locale;

@Builder
@Getter
public class PersonStandardBuilder {

  private static final Faker faker = new Faker(new Locale("en-CA.yml"));
  private final PersonStandard personType;


  public static PersonStandardBuilder personWithIdAndNameStandard() {
    PersonStandard person = new PersonStandard();
    person.setId(faker.regexify("[A-Za-z]{12}"));
    person.setName(faker.artist()
                        .name());
    return PersonStandardBuilder.builder()
                                .personType(person)
                                .build();
  }


  public PersonStandard create() {
    return this.personType;
  }
}
