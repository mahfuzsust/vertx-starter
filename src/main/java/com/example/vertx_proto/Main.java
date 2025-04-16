package com.example.vertx_proto;

import com.example.vertx_proto.verticles.HttpVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

public class Main {
	public static void main(String[] args) {
		long startTime = currentTimeMillis();

		VertxOptions vertxOptions = new VertxOptions()
			.setWarningExceptionTime(10).setWarningExceptionTimeUnit(TimeUnit.SECONDS)
			.setMaxEventLoopExecuteTime(20).setMaxEventLoopExecuteTimeUnit((TimeUnit.SECONDS));
		Vertx vertx = Vertx.vertx(vertxOptions);

		List<Future<String>> futures = new ArrayList<>();
		Future<String> periodicProducerDeployment = vertx.deployVerticle(new HttpVerticle());
		futures.add(periodicProducerDeployment);

		Future.join(futures)
			.onSuccess(ok -> System.out.printf("✅ Application started in %d ms\n", (currentTimeMillis() - startTime)))
			.onFailure(Throwable::printStackTrace);
	}
}
