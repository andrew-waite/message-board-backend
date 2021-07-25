package me.andrew;

import io.netty.handler.codec.string.*;
import me.andrew.routes.*;
import org.apache.camel.*;
import org.apache.camel.component.bean.*;
import org.apache.camel.component.directvm.*;
import org.apache.camel.component.netty.http.*;
import org.apache.camel.impl.*;
import org.apache.camel.support.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.context.support.*;

public class Main {
	public static void main(String[] args) {
		new Main().start();
	}

	public void start() {
		var server = startNettyServer();
		startCamel(server);
	}

	public NettySharedHttpServer startNettyServer() {
		var nettyConfiguration = new NettySharedHttpServerBootstrapConfiguration();

		nettyConfiguration.setPort(7000);
		nettyConfiguration.setHost("0.0.0.0");

		var server = new DefaultNettySharedHttpServer();
		server.setNettyServerBootstrapConfiguration(nettyConfiguration);

		server.start();

		return server;
	}

	public void startCamel(NettySharedHttpServer server) {
		var camelContext = new DefaultCamelContext();

		var registry = new DefaultRegistry();
		registry.bind("httpServer", server);

		camelContext.setRegistry(registry);
		camelContext.start();

		try {
			camelContext.addRoutes(new LandingPageRouteBuilder());
		} catch (Exception e) {
			throw new RuntimeException("Unable to register camel routes");
		}
	}
}
