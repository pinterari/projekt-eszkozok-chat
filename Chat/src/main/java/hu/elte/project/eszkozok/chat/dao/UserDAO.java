package hu.elte.project.eszkozok.chat.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import hu.elte.project.eszkozok.chat.db.SessionFactoryHelper;
import hu.elte.project.eszkozok.chat.entity.User;

/**
 * <h1>UserDAO</h1> 
 * A UserDAO a chat alkalmazás adatbázis kapcsolatát
 * valósítja meg a User entitáson át. Kapcsolatot létesít az adatbázissal a
 * Hibernate segítségével és kész objektumokat ad vissza illetve ment le.
 * 
 * @author Katona Bence
 *
 */
public class UserDAO {

	/**
	 * Lekérdezi az összes  adatbázisban lévő felhasználó objektumot.
	 * 
	 * @return	Az összes adatbázisban lévő felhasználó objektum.
	 */
	public static List<User> getUsers() {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		Query<User> query = currentSession.createQuery("from User", User.class);
		List<User> users = query.getResultList();
		trans.commit();
		return users;
	}

	/**
	 * Egy egyedi adatbázis azonosító alapján lekérdezi a felhasználó objektumot.
	 * 
	 * @param id	A felhasználó egyedi adatbázis azonosítója.
	 * @return 		Az azonosító alapján lekérdezett User objektum.
	 */
	public static User getUser(int id) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		User user = currentSession.get(User.class, id);
		trans.commit();
		return user;
	}

	/**
	 * Egy egyedi felhasználónév alapján lekérdezi a felhasználó objektumot.
	 * 
	 * @param username	A felhasználó egyedi felhasználóneve.
	 * @return 			A felhasználónév alapján lekérdezett User objektum.
	 */
	public static User getUser(String username) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		Query<User> query = currentSession.createQuery("from User where username=:username", User.class)
				.setParameter("username", username);
		User user = query.getResultList().size() == 1 ? query.getResultList().get(0) : null;
		trans.commit();
		return user;
	}

	/**
	 * A megadott felhasználó objektumot elmenti az adatbázisba, ha az még nincs benne.
	 * 
	 * @param user		A felhasználó objektum, amit menteni akarunk.
	 * @return			Sikerült-e elmenteni a felhasználót.
	 */
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
	
	/**
	 * Egy egyedi adatbázis azonosító alapján kitörli az adatbázisból a felhasználót.
	 * 
	 * @param id	A felhasználó egyedi adatbázis azonosítója.
	 */
	public static void deleteUser(int id) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		currentSession.createQuery("delete from User where id=:userId").setParameter("userId", id).executeUpdate();
		trans.commit();
	}

}
