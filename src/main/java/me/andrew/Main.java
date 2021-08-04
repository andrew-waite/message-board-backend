package me.andrew;

import lombok.extern.apachecommons.CommonsLog;
import me.andrew.routes.LandingPageRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.component.netty.http.DefaultNettySharedHttpServer;
import org.apache.camel.component.netty.http.NettySharedHttpServer;
import org.apache.camel.component.netty.http.NettySharedHttpServerBootstrapConfiguration;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultRegistry;

import java.util.Map;

@CommonsLog
public class Main {
	public static void main(String[] args) {
		new Main().start();
	}

	public void start() {
		var server = createAndStartDefaultNettyServer();
		startCamelContext(server);
	}

	public NettySharedHttpServer createAndStartDefaultNettyServer() {
		log.info("Stating netty server");
		var nettyConfiguration = new NettySharedHttpServerBootstrapConfiguration();

		nettyConfiguration.setPort(7000);
		nettyConfiguration.setHost("0.0.0.0");

		var server = new DefaultNettySharedHttpServer();
		server.setNettyServerBootstrapConfiguration(nettyConfiguration);

		server.start();

		log.info("Netty server started");

		return server;
	}

	public void startCamelContext(NettySharedHttpServer server) {
		log.info("Starting camel context and routes");
		var context = new DefaultCamelContext();

		var registry = new DefaultRegistry();
		registry.bind("defaultHttpServer", server);
		context.setRegistry(registry);

		context.start();

		registerCamelRoutes(context);

		log.info("All routes started");
	}

	private void registerCamelRoutes(CamelContext context) {
		try {
			context.addRoutes(new LandingPageRouteBuilder());
		} catch (Exception e) {
			throw new RuntimeException("Unable to register camel routes", e);
		}
	}
}
