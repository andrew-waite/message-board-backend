package me.andrew;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.apachecommons.CommonsLog;
import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.URI;

@CommonsLog
public class JettyReverseProxy {
    public static void start() {
        log.info("Starting jetty reverse proxy");

        Server server = new Server(8080);

        ConnectHandler proxy = new ConnectHandler();
        server.setHandler(proxy);

        ServletContextHandler context = new ServletContextHandler(proxy, "/*", ServletContextHandler.NO_SESSIONS);

       var proxyServlet = new ServletHolder(new ProxyServlet(){
            @Override
            protected String rewriteTarget(HttpServletRequest request) {
                return "http://127.0.0.1:7000" + request.getRequestURI();
            }
        });
        context.addServlet(proxyServlet, "/*");

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Started jetty reverse proxy");
    }
}
