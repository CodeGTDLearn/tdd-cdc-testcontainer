package testsconfig.tcContainer.annotations;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static java.lang.System.setProperty;
import static testsconfig.utils.TestUtils.globalContainerMessage;

public class TcContainerAnnotationConfig implements Extension {

  private final static String MONGO_IMAGE = "mongo:4.4.2";
  private final static String APP_PROPERTIES_MONGO_URI = "spring.data.mongodb.uri";

  private static MongoDBContainer testContainer;

  /*
   ╔════════════════════════════════════════════════════════════════════════════╗
   ║                         ANNOTATION+EXTENSION-CLASSES                       ║
   ╠════════════════════════════════════════════════════════════════════════════╣
   ║ a) in 'ANNOTATION-EXTENSION-CLASSES' only this STATIC-METHOD is allowed    ║
   ║ b) this sort of method(STATIC-METHOD) 'starts automatically'(auto-starting)║
   ║ c) "WHEN" the class is called/started via annotation                       ║
   ╚════════════════════════════════════════════════════════════════════════════╝
  */
  static {
    startTestcontainer();
  }

  public static void startTestcontainer() {

    testContainer = new MongoDBContainer(DockerImageName.parse(MONGO_IMAGE));
    testContainer.start();
    setProperty(APP_PROPERTIES_MONGO_URI, testContainer.getReplicaSetUrl());
    globalContainerMessage(getTcContainer(), "container-start");
    globalContainerMessage(getTcContainer(), "container-state");
  }


  public static void restartTestcontainer() {

    globalContainerMessage(getTcContainer(), "container-end");
    testContainer.close();
    startTestcontainer();
  }


  public static void closeTestcontainer() {

    globalContainerMessage(getTcContainer(), "container-end");
    testContainer.close();
  }


  public static MongoDBContainer getTcContainer() {

    return testContainer;
  }
}