package com.example.vertx;

import com.example.vertx.utils.HibernateUtil;
import com.example.vertx.verticles.HttpVerticle;
import com.example.vertx.verticles.ServiceRegistryVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

public class Main {
	public static void main(String[] args) {
		long startTime = currentTimeMillis();
		createTable();
		VertxOptions vertxOptions = new VertxOptions()
			.setWarningExceptionTime(10).setWarningExceptionTimeUnit(TimeUnit.SECONDS)
			.setMaxEventLoopExecuteTime(20).setMaxEventLoopExecuteTimeUnit((TimeUnit.SECONDS));
		Vertx vertx = Vertx.vertx(vertxOptions);

		List<Future<String>> futures = new ArrayList<>();
		Future<String> httpVerticle = vertx.deployVerticle(new HttpVerticle());
		Future<String> serviceRegistryVerticle = vertx.deployVerticle(new ServiceRegistryVerticle());
		futures.add(httpVerticle);
		futures.add(serviceRegistryVerticle);

		Future.join(futures)
			.onSuccess(ok -> System.out.printf("✅ Application started in %d ms\n", (currentTimeMillis() - startTime)))
			.onFailure(Throwable::printStackTrace);
	}

	public static void createTable() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try (session) {
			Transaction tx = session.beginTransaction();
			session.createNativeQuery(
				"CREATE TABLE IF NOT EXISTS users (" +
					"id SERIAL PRIMARY KEY, " +
					"name VARCHAR(255) NOT NULL " +
					")"
			).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
	}

}
