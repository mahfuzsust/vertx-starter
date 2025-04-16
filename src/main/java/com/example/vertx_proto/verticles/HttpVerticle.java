package com.example.vertx_proto.verticles;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.example.vertx_proto.codecs.ProtoMessageCodec;
import com.example.vertx_proto.handlers.HelloHandler;
import com.example.vertx_proto.repositories.UserRepository;
import com.example.vertx_proto.services.HelloService;
import com.example.vertx_proto.services.impl.HelloServiceImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class HttpVerticle extends AbstractVerticle {
	@Override
	public void start(Promise<Void> startPromise) {
		vertx.eventBus().registerDefaultCodec(HelloRequest.class, new ProtoMessageCodec<>(HelloRequest.getDefaultInstance()));
		vertx.eventBus().registerDefaultCodec(HelloResponse.class, new ProtoMessageCodec<>(HelloResponse.getDefaultInstance()));

		Router rootRouter = Router.router(vertx);
		rootRouter.route().handler(BodyHandler.create());

		Router apiRouter = Router.router(vertx);

		ServiceProxyBuilder builder = new ServiceProxyBuilder(vertx)
			.setAddress(UserRepository.ADDRESS);

		UserRepository repository = builder.build(UserRepository.class);

		HelloService helloService = new HelloServiceImpl(repository);
		HelloHandler helloHandler = new HelloHandler(helloService);
		helloHandler.mountRoutes(apiRouter);

		rootRouter.route("/api/v1/*").subRouter(apiRouter);

		vertx.createHttpServer()
			.requestHandler(rootRouter)
			.listen(8080)
			.onSuccess(server -> {
				System.out.println("Server running at http://localhost:" + server.actualPort());
				startPromise.complete();
			})
			.onFailure(startPromise::fail);
	}
}
