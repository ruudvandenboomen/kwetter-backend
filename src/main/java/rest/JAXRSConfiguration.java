package rest;

import io.swagger.jaxrs.config.BeanConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
     public JAXRSConfiguration() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("2.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/Kwetter/api");
        beanConfig.setResourcePackage("rest");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan();
    }
}
