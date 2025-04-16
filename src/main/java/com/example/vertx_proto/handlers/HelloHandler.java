package com.example.vertx_proto.handlers;

import com.example.proto.HelloRequest;
import com.example.vertx_proto.services.HelloService;
import io.vertx.ext.web.Router;

import static com.example.vertx_proto.utils.JsonUtil.handleJsonRequest;

public class HelloHandler implements Handler {
	private final HelloService service;

	public HelloHandler(HelloService service) {
		this.service = service;
	}

	@Override
	public void mountRoutes(Router router) {
		router.post("/HelloService/SayHello").handler(ctx ->
			handleJsonRequest(ctx, HelloRequest.newBuilder(), service::sayHello)
		);
	}
}
