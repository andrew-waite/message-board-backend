package me.andrew;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.camel.component.netty.http.DefaultNettySharedHttpServer;
import org.apache.camel.component.netty.http.NettySharedHttpServer;
import org.apache.camel.component.netty.http.NettySharedHttpServerBootstrapConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@CommonsLog
@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		new Main().start();
	}

	public void start() {
		SpringApplication.run(Main.class);

		JettyReverseProxy.start();
	}

	@Bean(name = "defaultHttpServer")
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
}
