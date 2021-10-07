package testsconfig.tcContainer.annotations;

import testsconfig.utils.TestUtils;
import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class TcContainerConfig implements Extension {

  private final static String MONGO_IMAGE = "mongo:4.4.2";
  private final static String APP_PROPERTIES_MONGO_URI = "spring.data.mongodb.uri";

  private static MongoDBContainer testContainer;

  // KEEP:
  // a) in 'ANNOTATION+EXTENSION-CLASSES' only this STATIC-METHOD is allowed
  // b) this sort of method will be started automatically when the class is called/started via annotation
  static {
    startTestcontainer();
  }

  public static void startTestcontainer() {
    testContainer = new MongoDBContainer(DockerImageName.parse(MONGO_IMAGE));
    testContainer.start();
    System.setProperty(APP_PROPERTIES_MONGO_URI,testContainer.getReplicaSetUrl());
    TestUtils.globalContainerMessage(getTcContainer(),"container-start");
    TestUtils.globalContainerMessage(getTcContainer(),"container-state");
  }


  public static void restartTestcontainer() {
    TestUtils.globalContainerMessage(getTcContainer(),"container-end");
    testContainer.close();
    startTestcontainer();
  }


  public static void closeTestcontainer() {
    TestUtils.globalContainerMessage(getTcContainer(),"container-end");
    testContainer.close();
  }


  public static MongoDBContainer getTcContainer() {
    return testContainer;
  }
}