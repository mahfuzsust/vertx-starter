package com.example.vertx_proto.handlers;

import io.vertx.ext.web.Router;

public interface Handler {
	void mountRoutes(Router router);
}
