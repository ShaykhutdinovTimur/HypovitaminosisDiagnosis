package ru.ifmo.ctddev.muratov.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * @author amir.
 */

public class Main {
    public static void main(String[] args) throws Exception {
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
    }

}
