package me.andrew.routes;

import com.fasterxml.jackson.databind.SerializationFeature;
import me.andrew.SpringContext;
import me.andrew.providers.DatabaseProvider;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.RouteDefinition;

public abstract class BaseRouteBuilder extends RouteBuilder {
    private static String CAMEL_HTTP_OPTIONS = "?sync=true&nettySharedHttpServer=#defaultHttpServer";
    public DatabaseProvider databaseProvider;
    public JacksonDataFormat jacksonJsonDataFormat;

    public BaseRouteBuilder() {
        intializeJacksonJsonDataFormat();
        this.databaseProvider = (DatabaseProvider) SpringContext.getBean("databaseProvider");
    }

    @Override
    public RouteDefinition from(String url) {
        return super.from(String.format("netty-http://localhost:7000/api%s%s", url, CAMEL_HTTP_OPTIONS))
            .process(exchange -> {
                exchange.getIn().setHeader("Content-Type", "application/json");
                exchange.getIn().setHeader("refer", "localhost");
                exchange.getIn().setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
                exchange.getIn().setHeader("Access-Control-Allow-Methods", "GET, POST, PUT");
            });
    }

    private void intializeJacksonJsonDataFormat() {
        this.jacksonJsonDataFormat = new JacksonDataFormat();
        this.jacksonJsonDataFormat.setAllowJmsType(true);
        this.jacksonJsonDataFormat.disableFeature(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
