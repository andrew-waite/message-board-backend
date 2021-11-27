package me.andrew.routes;

import me.andrew.models.User;
import me.andrew.providers.DatabaseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileGetUserByIdRouteBuilder extends BaseRouteBuilder {
    @Autowired
    private DatabaseProvider databaseProvider;

    public ProfileGetUserByIdRouteBuilder() {
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
