package me.andrew.routes;

import me.andrew.SpringContext;
import me.andrew.models.User;
import me.andrew.providers.DatabaseProvider;
import org.apache.camel.builder.*;
import org.apache.camel.component.jackson.*;
import org.apache.camel.model.*;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spi.annotations.Dataformat;

import java.util.*;

public class ProfileGetUserByIdRouteBuilder extends BaseRouteBuilder {
    private DatabaseProvider databaseProvider = null;

    public ProfileGetUserByIdRouteBuilder() {
        this.databaseProvider = (DatabaseProvider) SpringContext.getBean("databaseProvider");
    }
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
