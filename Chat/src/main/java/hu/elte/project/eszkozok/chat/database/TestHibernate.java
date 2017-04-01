package hu.elte.project.eszkozok.chat.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import hu.elte.project.eszkozok.chat.entity.ChatGroup;
import hu.elte.project.eszkozok.chat.entity.Message;
import hu.elte.project.eszkozok.chat.entity.User;

public class TestHibernate {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Message.class)
				.addAnnotatedClass(ChatGroup.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {
			 System.out.println("Creating new User...");
			 User user = new User("proba", "Imre", "Füty", "imike@gmail.com", "hasheltPasswordAmiPerPillNincsIsHashelve");
			 
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
