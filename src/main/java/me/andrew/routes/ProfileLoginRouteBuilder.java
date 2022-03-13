package me.andrew.routes;

import me.andrew.models.User;
import org.apache.camel.*;

public class ProfileLoginRouteBuilder extends BaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("/profile/login")
            .unmarshal(unmarshallUsingPojo(User.class))
            .process(exchange -> {
                var body = exchange.getIn().getBody(User.class);
                if (body.getEmail().equalsIgnoreCase("bob@example.com")) {
                    var user = User.builder()
                        .id(1)
                        .firstName("Bob")
                        .lastName("The builder")
                        .email("bob@example.com")
                        .build();

                    exchange.getIn().setBody(user);
                } else {
                    exchange.getIn().setBody("The requested user could not be found");
                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
                }
            })
            .process(exchange -> convertToJsonBody(exchange));
    }
}
