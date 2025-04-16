package com.example.vertx_proto.services.impl;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.example.vertx_proto.services.HelloService;
import io.vertx.core.Future;

public class HelloServiceImpl implements HelloService {
	@Override
	public Future<HelloResponse> sayHello(HelloRequest request) {
		HelloResponse response = HelloResponse.newBuilder()
			.setMessage("Hello, " + request.getName())
			.build();
		return Future.succeededFuture(response);
	}
}
