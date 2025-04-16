package com.example.vertx_proto.services;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import io.vertx.core.Future;

public interface HelloService {
	Future<HelloResponse> sayHello(HelloRequest request);
}
