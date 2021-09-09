package com.tdd.databuilder;

import com.github.javafaker.Faker;
import com.tdd.parallel.entity.jsonview.PersonJsonview;
import lombok.Builder;
import lombok.Getter;

import java.util.Locale;

@Builder
@Getter
public class PersonJsonviewBuilder {

  private static final Faker faker = new Faker(new Locale("en-CA.yml"));
  private final PersonJsonview personType;


  public static PersonJsonviewBuilder personWithIdAndNameJsonview() {
    PersonJsonview person = new PersonJsonview();
    person.setId(faker.regexify("[A-Za-z]{12}"));
    person.setName(faker.artist()
                        .name());
    return PersonJsonviewBuilder.builder()
                                .personType(person)
                                .build();
  }


  public PersonJsonview create() {
    return this.personType;
  }
}
