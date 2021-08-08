package me.andrew.routes;

import me.andrew.SpringContext;
import me.andrew.models.User;
import org.apache.camel.builder.*;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spi.annotations.Dataformat;

public class LandingPageRouteBuilder extends BaseRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("/profile/{id}")
            .process(exchange -> {
                var id = exchange.getIn().getHeader("id", Integer.class);
                var user = this.databaseProvider.getById(User.class, id);

                exchange.getIn().setBody(user);
            })
            .marshal(jacksonJsonDataFormat);
    }
}
