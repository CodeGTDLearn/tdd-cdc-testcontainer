package com.tdd.testcontainer.annotation;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class TcAnnotationClass implements Extension {

  final static String MONGO_VERSION = "mongo:4.4.2";
  final static String MONGO_URI_PROPERTY = "spring.data.mongodb.uri";

  static MongoDBContainer container;

  static {
    container = new MongoDBContainer(DockerImageName.parse(MONGO_VERSION));

    container.start();

    System.setProperty(MONGO_URI_PROPERTY,container.getReplicaSetUrl());

  }

  public static MongoDBContainer getContainer() {
    return container;
  }


  public static void containerHeader(String title) {
    System.out.printf(
         "\n\n>=====================< %s >=====================<\n" +
              " --> Container-Name: %s\n" +
              " --> Container-Url: %s\n" +
              " --> Container-Running: %s\n" +
              ">=====================< %s >=====================<\n\n%n",
         title,
         container.getContainerName(),
         container.getReplicaSetUrl(),
         container.isRunning(),
         title
                     );
  }

}
