package me.andrew.routes;

import org.apache.camel.builder.*;

public class LandingPageRouteBuilder extends RouteBuilder {

	public void configure() throws Exception {
		from("netty-http://0.0.0.0:7000?sync=true&nettySharedHttpServer=#httpServer")
				.process(exchange -> {
					exchange.getIn().setBody("hello world");
				});
	}
}
