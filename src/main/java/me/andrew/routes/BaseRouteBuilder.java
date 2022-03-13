package me.andrew.routes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.RouteDefinition;

public abstract class BaseRouteBuilder extends RouteBuilder {
    private static String CAMEL_HTTP_OPTIONS = "?sync=true&nettySharedHttpServer=#defaultHttpServer";
    public JacksonDataFormat jacksonJsonDataFormat;

    public BaseRouteBuilder() {
        intializeJacksonJsonDataFormat();
    }

    @Override
    public RouteDefinition from(String uri) {
     return super.from(String.format("netty-http://localhost:7000/api%s%s", uri, CAMEL_HTTP_OPTIONS))
            .process(exchange -> {
                exchange.getIn().setHeader("Content-Type", "application/json");
               // exchange.getIn().setHeader("refer", "localhost");
                //exchange.getIn().setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
               // exchange.getIn().setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
                //exchange.getIn().setHeader("Access-Control-Allow-Credentials", true);
                //exchange.getIn().setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token, Authorization");
            });
    }

    public JacksonDataFormat unmarshallUsingPojo(Class<?> clazz) {
        return new JacksonDataFormat(clazz);
    }

    public Exchange convertToJsonBody(Exchange exchange) {
        var body = exchange.getIn().getBody();

        try {
            exchange.getIn().setBody(new ObjectMapper().writeValueAsString(body));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return exchange;
    }


    private void intializeJacksonJsonDataFormat() {
        this.jacksonJsonDataFormat = new JacksonDataFormat();
        this.jacksonJsonDataFormat.setAllowJmsType(true);
        this.jacksonJsonDataFormat.disableFeature(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
