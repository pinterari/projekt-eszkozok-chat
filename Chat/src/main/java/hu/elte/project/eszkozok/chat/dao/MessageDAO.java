package hu.elte.project.eszkozok.chat.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import hu.elte.project.eszkozok.chat.db.SessionFactoryHelper;
import hu.elte.project.eszkozok.chat.entity.Message;

/**
 * <h1>MessageDAO</h1> 
 * A MessageDAO a chat alkalmazás adatbázis kapcsolatát
 * valósítja meg a Message entitáson át. Kapcsolatot létesít az adatbázissal a
 * Hibernate segítségével és kész objektumokat ad vissza illetve ment le.
 * 
 * @author Katona Bence
 *
 */
public class MessageDAO {

	/**
	 * A szoba egyedi adatbázis azonosítója alapján megadja a szobához tartozó üzeneteket
	 * időrendi sorrendben.
	 * 
	 * @param chatGroupID	A szoba egyedi adatbázis azonosítója.
	 * @return				A szobához tartozó összes üzenet.
	 */
	public static List<Message> getMessages(int chatGroupID) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		Query<Message> query = currentSession
				.createQuery("FROM Message m WHERE m.chatGroupID =:chatGroupID", Message.class)
				.setParameter("chatGroupID", chatGroupID);
		List<Message> messages = query.getResultList();
		trans.commit();
		return messages;
	}

	/**
	 * A megadott üzenet objektumot elmenti az adatbázisba.
	 * 
	 * @param message		Az üzenet objektum, amit menteni akarunk.
	 */
	public static void saveMessage(Message message) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		currentSession.saveOrUpdate(message);
		trans.commit();
	}

}
