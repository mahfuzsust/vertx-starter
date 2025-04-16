package com.example.vertx_proto.utils;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import io.vertx.ext.web.RoutingContext;

import java.util.function.Function;

public class JsonUtil {
	public static  <ReqT extends Message, ResT extends Message> void handleJsonRequest(
		RoutingContext ctx,
		Message.Builder requestBuilder,
		Function<ReqT, ResT> serviceCall
	) {
		try {
			JsonFormat.parser().ignoringUnknownFields().merge(ctx.body().asString(), requestBuilder);
			@SuppressWarnings("unchecked")
			ReqT request = (ReqT) requestBuilder.build();

			ResT response = serviceCall.apply(request);
			String jsonResponse = JsonFormat.printer().print(response);

			ctx.response()
				.putHeader("Content-Type", "application/json")
				.end(jsonResponse);
		} catch (Exception e) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}
}
