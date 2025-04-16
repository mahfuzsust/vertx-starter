package com.example.vertx_proto.verticles;

import com.example.vertx_proto.handlers.UserHandler;
import com.example.vertx_proto.repositories.UserRepository;
import com.example.vertx_proto.services.UserService;
import com.example.vertx_proto.services.impl.UserServiceImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class HttpVerticle extends AbstractVerticle {
	@Override
	public void start(Promise<Void> startPromise) {
		Router rootRouter = Router.router(vertx);
		rootRouter.route().handler(BodyHandler.create());

		Router apiRouter = Router.router(vertx);

		ServiceProxyBuilder builder = new ServiceProxyBuilder(vertx)
			.setAddress(UserRepository.ADDRESS);

		UserRepository repository = builder.build(UserRepository.class);
		UserService userService = new UserServiceImpl(repository);
		UserHandler userHandler = new UserHandler(userService);
		userHandler.mountRoutes(apiRouter);

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
