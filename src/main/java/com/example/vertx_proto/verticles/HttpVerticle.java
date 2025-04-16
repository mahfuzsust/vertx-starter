package com.example.vertx_proto.verticles;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.google.protobuf.util.JsonFormat;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.Router;

import java.nio.charset.StandardCharsets;

public class HttpVerticle extends AbstractVerticle {
	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		Router router = Router.router(vertx);

		router.post("/api/:method").handler(ctx -> {
			String method = ctx.pathParam("method");
			ctx.request().body().onSuccess(buffer -> {
				try {
					String bufferString = buffer.toString(StandardCharsets.UTF_8);
					HelloRequest.Builder builder = HelloRequest.newBuilder();
					JsonFormat.parser().ignoringUnknownFields().merge(bufferString, builder);
					HelloRequest request = builder.build();
					HelloResponse response;

					if (method.equals("sayHello")) {
						response = HelloResponse.newBuilder()
							.setMessage("Hello " + request.getName())
							.build();
					} else {
						ctx.response().setStatusCode(404).end("Method not found");
						return;
					}
					ctx.response()
						.putHeader("Content-Type", "application/json")
						.end(Buffer.buffer(response.toByteArray()));

				} catch (Exception e) {
					ctx.response().setStatusCode(500).end("Invalid request: " + e.getMessage());
				}
			});
		});

		vertx.createHttpServer().requestHandler(router).listen(8888);
	}
}
