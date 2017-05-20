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

public class DatabaseTest extends TestCase {

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
			System.out.println("Creating new User...");
			User user = new User("proba", "Imre", "Füty", "imike@gmail.com",
					"hasheltPasswordAmiPerPillNincsIsHashelve");

			System.out.println("Begining save transaction...");
			session.beginTransaction();

			System.out.println("Saving new User to Database...");
			session.save(user);

			System.out.println("Commiting save transaction...");
			session.getTransaction().commit();

			session = factory.getCurrentSession();
			System.out.println("Begining delete transaction...");
			session.beginTransaction();

			System.out.println("Delete User from Database...");
			session.createQuery("delete from User where username = 'proba'").executeUpdate();

			System.out.println("Commiting delete transaction...");
			session.getTransaction().commit();

			System.out.println("Done!");
		} finally {
			factory.close();
		}
	}
}
