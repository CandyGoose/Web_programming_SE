import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Application configuration class that sets the base path for all JAX-RS resources.
 */
@ApplicationPath("/api")
public class MainApplication extends Application {

}