package com.example.vertx_proto.utils;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;

import java.util.function.Function;

public class JsonUtil {
	public static <ReqT extends Message, ResT extends Message> void handleJsonRequest(
		RoutingContext ctx,
		Message.Builder requestBuilder,
		Function<ReqT, Future<ResT>> serviceCall
	) {
		try {
			String requestBody = ctx.body().asString();

			JsonFormat.parser().ignoringUnknownFields().merge(requestBody, requestBuilder);
			@SuppressWarnings("unchecked")
			ReqT request = (ReqT) requestBuilder.build();

			serviceCall.apply(request).onSuccess(response -> {
				try {
					String json = JsonFormat.printer().print(response);
					ctx.response().putHeader("Content-Type", "application/json").end(json);
				} catch (Exception e) {
					ctx.response().setStatusCode(500).end("{\"error\": \"" + e.getMessage() + "\"}");
				}
			}).onFailure(err -> {
				ctx.response().setStatusCode(500).end("{\"error\": \"" + err.getMessage() + "\"}");
			});

		} catch (Exception e) {
			ctx.response().setStatusCode(400).end("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}
}
