package testsconfig.tcContainer.annotations;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target({TYPE})
@ExtendWith(TcContainerAnnotationConfig.class)
public @interface TcContainer {
}