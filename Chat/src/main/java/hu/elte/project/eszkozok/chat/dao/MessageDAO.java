package hu.elte.project.eszkozok.chat.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import hu.elte.project.eszkozok.chat.db.SessionFactoryHelper;
import hu.elte.project.eszkozok.chat.entity.Message;

public class MessageDAO {

	public static List<Message> getMessages(int chatGroupID) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		Query<Message> query = currentSession
				.createQuery("FROM Message m WHERE m.chatGroupID =:chatGroupID", Message.class)
				.setParameter("chatGroupID", chatGroupID);
		;
		List<Message> messages = query.getResultList();
		trans.commit();
		return messages;
	}

	public static void saveMessage(Message message) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		currentSession.saveOrUpdate(message);
		trans.commit();
	}

}
