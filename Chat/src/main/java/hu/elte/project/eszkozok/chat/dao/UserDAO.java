package hu.elte.project.eszkozok.chat.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import hu.elte.project.eszkozok.chat.db.SessionFactoryHelper;
import hu.elte.project.eszkozok.chat.entity.User;

public class UserDAO {

	public static List<User> getUsers() {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		Query<User> query = currentSession.createQuery("from User", User.class);
		List<User> users = query.getResultList();
		trans.commit();
		return users;
	}

	public static User getUser(int id) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		User user = currentSession.get(User.class, id);
		trans.commit();
		return user;
	}

	public static User getUser(String username) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		Query<User> query = currentSession.createQuery("from User where username=:username", User.class)
				.setParameter("username", username);
		User user = query.getResultList().size() == 1 ? query.getResultList().get(0) : null;
		trans.commit();
		return user;
	}

	public static boolean saveUser(User user) {
		boolean userNotInDatabase = UserDAO.getUser(user.getUserName()) == null;

		if (userNotInDatabase) {
			Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
			Transaction trans = currentSession.beginTransaction();
			currentSession.saveOrUpdate(user);
			trans.commit();
			return true;
		}

		return false;
	}

	public void deleteUser(int id) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		currentSession.createQuery("delete from User where id=:userId").setParameter("userId", id).executeUpdate();
		trans.commit();
	}

}
