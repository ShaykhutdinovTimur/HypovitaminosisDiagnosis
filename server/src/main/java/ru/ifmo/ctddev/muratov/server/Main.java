package ru.ifmo.ctddev.muratov.server;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;


/**
 * @author amir.
 */

public class Main {
    public static void main(String[] args) throws Exception {

        File dir = new File("server/src/main/resources/photos");
        if (dir.exists()) {
            FileUtils.cleanDirectory(dir);
        }
        File dir2 = new File("server/target/classes/photos");
        if (dir2.exists()) {
            FileUtils.cleanDirectory(dir2);
        }
        Server server = new Server(8081);

        HandlerCollection handlers = new HandlerCollection();

        WebAppContext context = new WebAppContext();
        context.setResourceBase("server/src/main/resources");
        context.setContextPath("/");
        //context.setDefaultsDescriptor("server/src/main/webdefault/webdefault.xml");

        handlers.addHandler(context);
        server.setHandler(handlers);

        server.start();
        server.join();
//        if (dir.exists()) {
//            FileUtils.cleanDirectory(dir);
//        }
    }

}
