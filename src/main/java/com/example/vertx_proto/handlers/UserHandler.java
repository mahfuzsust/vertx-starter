package com.example.vertx_proto.handlers;

import com.example.vertx_proto.CreateUserRequest;
import com.example.vertx_proto.DeleteUserRequest;
import com.example.vertx_proto.GetUserRequest;
import com.example.vertx_proto.UpdateUserRequest;
import com.example.vertx_proto.services.UserService;
import io.vertx.ext.web.Router;

import static com.example.vertx_proto.utils.JsonUtil.handleJsonRequest;

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
