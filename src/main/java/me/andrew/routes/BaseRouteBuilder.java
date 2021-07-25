package me.andrew.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

public abstract class BaseRouteBuilder extends RouteBuilder {
    private static String CAMEL_HTTP_OPTIONS = "?sync=true&nettySharedHttpServer=#defaultHttpServer";

    @Override
    public RouteDefinition from(String url) {
        return super.from(String.format("netty-http://localhost:7000/%s%s", url, CAMEL_HTTP_OPTIONS));
    }
}
