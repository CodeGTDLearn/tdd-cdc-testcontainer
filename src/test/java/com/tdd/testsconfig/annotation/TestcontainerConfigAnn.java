package com.tdd.testsconfig.annotation;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class TestcontainerConfigAnn implements Extension {

  private final static String MONGO_VERSION = "mongo:4.4.2";
  private final static String APP_PROPERTIES_MONGO_URI = "spring.data.mongodb.uri";

  private static MongoDBContainer testContainer;

  // KEEP: in 'ANNOTATION+EXTENSION-CLASSES' only this STATIC-METHOD is allowed
  static {
    startTestcontainer();
  }

  public static void startTestcontainer() {
    testContainer = new MongoDBContainer(DockerImageName.parse(MONGO_VERSION));
    testContainer.start();
    System.setProperty(APP_PROPERTIES_MONGO_URI,testContainer.getReplicaSetUrl());
  }


  public static void restartTestcontainer() {
    testContainer.close();
    startTestcontainer();
  }


  public static void closeTestcontainer() {
    testContainer.close();
  }


  public static MongoDBContainer getTestcontainer() {
    return testContainer;
  }
}
