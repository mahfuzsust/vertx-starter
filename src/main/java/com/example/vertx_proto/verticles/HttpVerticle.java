package com.example.vertx_proto.verticles;

import com.example.vertx_proto.handlers.HelloHandler;
import com.example.vertx_proto.services.HelloService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpVerticle extends AbstractVerticle {
	@Override
	public void start(Promise<Void> startPromise) {
		Router rootRouter = Router.router(vertx);
		rootRouter.route().handler(BodyHandler.create());

		Router apiRouter = Router.router(vertx);

		HelloService helloService = new HelloService();
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
