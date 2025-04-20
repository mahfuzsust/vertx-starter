package com.example.vertx.handlers;

import io.vertx.ext.web.Router;

public interface Handler {
	void mountRoutes(Router router);
}
