package net.thumbtack.school.concert.server;

import net.thumbtack.school.concert.db.mydb.data.DataBase;
import net.thumbtack.school.concert.db.mysql.data.MyBatisUtils;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.resources.ConcertResource;
import net.thumbtack.school.concert.server.config.MyResourceConfig;
import net.thumbtack.school.concert.server.config.MyResourceConfigHiber;
import net.thumbtack.school.concert.server.config.Settings;
import net.thumbtack.school.concert.settings.Mode;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private static boolean setUpIsDone = false;


    private static org.eclipse.jetty.server.Server jettyServer;

    private static void attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(Server::stopServer));
    }

    public static void createServerHiber() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(Settings.getRestHTTPPort()).build();
        MyResourceConfigHiber config = new MyResourceConfigHiber();
        config.register(Server.class);
        jettyServer = JettyHttpContainerFactory.createServer(baseUri, config);
        LOGGER.info("Server started at port " + Settings.getRestHTTPPort());
    }

    public static void createServer() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(Settings.getRestHTTPPort()).build();
        MyResourceConfig config = new MyResourceConfig();
        config.register(Server.class);
        jettyServer = JettyHttpContainerFactory.createServer(baseUri, config);
        LOGGER.info("Server started at port " + Settings.getRestHTTPPort());
    }

    public static void stopServer() {
        LOGGER.info("Stopping server");
        try {
            jettyServer.stop();
            jettyServer.destroy();
        } catch (Exception e) {
            LOGGER.error("Error stopping service", e);
            System.exit(1);
        }
        LOGGER.info("Server stopped");
    }


    public static void main(String[] args) throws ConfigurationException, MyException {
        attachShutDownHook();
        Configuration config = new PropertiesConfiguration("conf/conf.properties");
        net.thumbtack.school.concert.settings.Settings settings = new net.thumbtack.school.concert.settings.Settings(config.getString("mode"));
        if (settings.getMode() == Mode.HIBERNATE) {
            createServerHiber();
        } else {
            createServer();
        }
        if (settings.getMode() == Mode.SQL) {
            if (!setUpIsDone) {
                boolean initSqlSessionFactory = MyBatisUtils.initSqlSessionFactory();
                if (!initSqlSessionFactory) {
                    throw new RuntimeException("Can't create connection, stop");
                }
                setUpIsDone = true;
            }
            ConcertResource.setMode(Mode.SQL);
        } else if (settings.getMode() == Mode.RAM) {
            DataBase.startServer();
            ConcertResource.setMode(Mode.RAM);
        }
    }
}