package com.tdd.testsconfig.annotation;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class TestcontainerConfigAnn implements Extension {

  private final static String MONGO_VERSION = "mongo:4.4.2";
  private final static String MONGO_URI_PROPERTY = "spring.data.mongodb.uri";

  private static final MongoDBContainer testContainer;

  // KEEP: in 'ANNOTATION+EXTENSION-CLASSES' only this STATIC-METHOD is allowed
  static {
    testContainer = new MongoDBContainer(DockerImageName.parse(MONGO_VERSION));
    startContainer();
    System.setProperty(MONGO_URI_PROPERTY,testContainer.getReplicaSetUrl());
  }


  public static void startContainer() {
    testContainer.start();
  }

  public static void closeContainer() {
    testContainer.close();
  }

  public static MongoDBContainer getTestcontainer() {
    return testContainer;
  }
}
