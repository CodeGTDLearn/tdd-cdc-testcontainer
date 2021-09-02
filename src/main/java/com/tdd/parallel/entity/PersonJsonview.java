package com.tdd.parallel.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.tdd.parallel.core.Views;
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
@Document(collection = "personjsonview")
public class PersonJsonview {

  @Id
  @JsonView(Views.ResponseViews.AdminFilter.class)
  private String id;

  @JsonView(Views.ResponseViews.AdminFilter.class)
  private String password;

  @JsonView(Views.ResponseViews.UserFilter.class)
  private String name;

  @JsonView(Views.ResponseViews.UserFilter.class)
  private String category;
}