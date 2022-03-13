package me.andrew.routes;

import me.andrew.models.User;
import me.andrew.providers.DatabaseProvider;
import org.hibernate.dialect.*;

public class ProfileGetUserByIdRouteBuilder extends BaseRouteBuilder {
    private DatabaseProvider databaseProvider;
    public ProfileGetUserByIdRouteBuilder(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
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
