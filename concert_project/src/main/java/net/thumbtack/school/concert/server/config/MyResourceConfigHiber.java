package net.thumbtack.school.concert.server.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class MyResourceConfigHiber  extends ResourceConfig {

    public MyResourceConfigHiber() {
        packages("net.thumbtack.school.concert.hibernate.resources",
                "net.thumbtack.school.concert.rest.mappers");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }

}
