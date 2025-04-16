package com.example.vertx_proto.utils;

import jakarta.persistence.Entity;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import java.util.Properties;
import java.util.Set;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		Properties props = new Properties();
		props.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
		props.setProperty("hibernate.connection.url", System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/postgres"));
		props.setProperty("hibernate.connection.username", System.getenv().getOrDefault("DB_USER", "postgres"));
		props.setProperty("hibernate.connection.password", System.getenv().getOrDefault("DB_PASSWORD", "postgres"));
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		props.setProperty("hibernate.show_sql", "true");

		Configuration configuration = new Configuration()
			.addProperties(props);

		Reflections reflections = new Reflections("com.example.vertx_proto.models");

		Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
		for (Class<?> entity : entities) {
			configuration.addAnnotatedClass(entity);
		}
		return configuration.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
