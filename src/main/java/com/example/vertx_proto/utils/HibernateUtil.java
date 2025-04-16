package com.example.vertx_proto.utils;

import com.example.vertx_proto.models.User;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import java.util.Properties;

/**
 * HibernateUtil.java
 * <p>
 * Description: [Your Description Here]
 * Author: mahfuz
 * Created: 16.04.2025
 */
public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		Properties props = new Properties();
		props.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
		props.setProperty("hibernate.connection.url", System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/mydb"));
		props.setProperty("hibernate.connection.username", System.getenv().getOrDefault("DB_USER", "postgres"));
		props.setProperty("hibernate.connection.password", System.getenv().getOrDefault("DB_PASSWORD", "postgres"));
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		props.setProperty("hibernate.show_sql", "true");

		return new Configuration()
			.addProperties(props)
			.addAnnotatedClass(User.class)
			.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
