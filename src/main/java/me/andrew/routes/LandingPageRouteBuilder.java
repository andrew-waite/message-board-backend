package me.andrew.routes;

import org.apache.camel.builder.*;

public class LandingPageRouteBuilder extends BaseRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("")
            .process(exchange -> exchange.getIn().setBody("Hello World"));
    }
}
