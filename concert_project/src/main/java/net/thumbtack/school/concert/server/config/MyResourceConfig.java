package net.thumbtack.school.concert.server.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class MyResourceConfig extends ResourceConfig {

    public MyResourceConfig() {
        packages("net.thumbtack.school.concert.resources",
                "net.thumbtack.school.concert.rest.mappers");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }

}
