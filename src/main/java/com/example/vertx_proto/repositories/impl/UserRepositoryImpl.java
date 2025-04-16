package com.example.vertx_proto.repositories.impl;

import com.example.vertx_proto.models.User;
import com.example.vertx_proto.repositories.UserRepository;
import com.example.vertx_proto.utils.HibernateUtil;
import io.vertx.core.Future;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepositoryImpl implements UserRepository {
	@Override
	public Future<Void> save(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try (session) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.persist(user);
			transaction.commit();
			return Future.succeededFuture();
		} catch (Exception e) {
			e.printStackTrace();
			return Future.failedFuture(e);
		}
	}

	@Override
	public Future<User> findById(Long id) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			User user = session.get(User.class, id);
			session.close();
			if (user == null) {
				return Future.failedFuture(new Exception("User not found"));
			}
			return Future.succeededFuture(user);
		} catch (Exception e) {
			e.printStackTrace();
			return Future.failedFuture(e);
		}
	}
}
