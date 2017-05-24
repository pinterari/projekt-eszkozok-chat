package hu.elte.project.eszkozok.chat.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * <h1>SessionFactoryHelper</h1> 
 * A Hibernate adatbázis lekérdezésekhez szükséges SessionFactory-t 
 * előállító segédosztály.
 * 
 * @author Katona Bence
 *
 */
public class SessionFactoryHelper {

	private static final SessionFactory sessionFactory;

	static {
		try {
			Configuration config = new Configuration();
			sessionFactory = config.configure().buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Error in creating SessionFactory object." + e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Visszaadja a SessionFactory objektumot.
	 * 
	 * @return	Visszaadja a SessionFactory objektumot.
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
