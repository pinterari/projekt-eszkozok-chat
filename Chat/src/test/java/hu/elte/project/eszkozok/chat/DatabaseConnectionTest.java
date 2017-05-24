package hu.elte.project.eszkozok.chat;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import hu.elte.project.eszkozok.chat.entity.ChatGroup;
import hu.elte.project.eszkozok.chat.entity.Message;
import hu.elte.project.eszkozok.chat.entity.User;
import junit.framework.TestCase;

public class DatabaseConnectionTest extends TestCase {

	@Test
	public void testJdbc() {
		String user = "project_chat";
		String pass = "project_chat";
		String jdbcUrl = "jdbc:mysql://localhost:3306/project_chat?useSSL=false";

		try {
			System.out.println("Connecting to database: " + jdbcUrl);
			Connection myCon = DriverManager.getConnection(jdbcUrl, user, pass);
			System.out.println("Connection successfull!");
			myCon.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	@Test
	public void testHibernate() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(Message.class).addAnnotatedClass(ChatGroup.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {
			User user = new User("proba", "Imre", "Béla", "imike@gmail.com",
					"hasheltPasswordAmiPerPillNincsIsHashelve");
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			session = factory.getCurrentSession();
			session.beginTransaction();
			session.createQuery("delete from User where username = 'proba'").executeUpdate();
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}
}
