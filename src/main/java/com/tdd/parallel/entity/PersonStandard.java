package com.tdd.parallel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "personstandard")
public class PersonStandard {

  @Id
  private String id;
  private String name;
}