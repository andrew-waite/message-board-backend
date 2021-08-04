package me.andrew.routes;

import org.apache.camel.builder.*;

public class LandingPageRouteBuilder extends BaseRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("/profile/{id}")
            .process(exchange -> {
                exchange.getIn().setBody("{\"firstName\": \"Bob" + exchange.getIn().getHeader("id") + "\"}" );
            });
    }
}
