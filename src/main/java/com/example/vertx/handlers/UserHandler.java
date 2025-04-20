package com.example.vertx.handlers;


import com.example.vertx.CreateUserRequest;
import com.example.vertx.DeleteUserRequest;
import com.example.vertx.GetUserRequest;
import com.example.vertx.UpdateUserRequest;
import com.example.vertx.services.UserService;
import io.vertx.ext.web.Router;

import static com.example.vertx.utils.JsonUtil.handleJsonRequest;

public class UserHandler implements Handler {
	private final UserService service;

	public UserHandler(UserService service) {
		this.service = service;
	}

	@Override
	public void mountRoutes(Router router) {
		router.post("/UserService/Create").handler(ctx ->
			handleJsonRequest(ctx, CreateUserRequest.newBuilder(), service::createUser)
		);
		router.post("/UserService/Update").handler(ctx ->
			handleJsonRequest(ctx, UpdateUserRequest.newBuilder(), service::updateUser)
		);
		router.post("/UserService/Get").handler(ctx ->
			handleJsonRequest(ctx, GetUserRequest.newBuilder(), service::getUser)
		);
		router.post("/UserService/Delete").handler(ctx ->
			handleJsonRequest(ctx, DeleteUserRequest.newBuilder(), service::deleteUser)
		);
	}
}
