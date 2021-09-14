package com.tdd.parallel.entity.jsonview;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.tdd.parallel.core.views.Views.PersonViews.*;
import static com.tdd.parallel.core.views.Views.PersonViews.AdminResponseView;
import static com.tdd.parallel.core.views.Views.PersonViews.UserResponseView;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "personjsonview")
public class PersonJsonview {

  @Id
  @JsonView(AdminResponseView.class)
  private String id;

  @JsonView(value = {UserResponseView.class, PostFilterRequestView.class})
  private String name;

}