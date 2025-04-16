package com.example.vertx_proto.services;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;

public class HelloService {
	public HelloResponse sayHello(HelloRequest request) {
		return HelloResponse.newBuilder()
			.setMessage("Hello, " + request.getName())
			.build();
	}
}
