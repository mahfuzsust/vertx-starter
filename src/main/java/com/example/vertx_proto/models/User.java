package com.example.vertx_proto.models;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@DataObject
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	public User() {}


	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	public User(JsonObject json) {
		this.id = json.getLong("id");
		this.name = json.getString("name");
	}

	// Getters and setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public JsonObject toJson() {
		return JsonObject.mapFrom(this);
	}

	@Override
	public String toString() {
		return Json.encodePrettily(this);
	}
}
