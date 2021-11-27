package me.andrew.routes;

import me.andrew.models.User;
import org.springframework.stereotype.Component;

@Component
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
                    exchange.getIn().setBody("Unable to find a user with those details");
                }

            })
            .process(exchange -> convertToJsonBody(exchange));
    }
}
