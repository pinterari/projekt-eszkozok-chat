package hu.elte.project.eszkozok.chat.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import hu.elte.project.eszkozok.chat.db.SessionFactoryHelper;
import hu.elte.project.eszkozok.chat.entity.ChatGroup;
import hu.elte.project.eszkozok.chat.entity.User;
import hu.elte.project.eszkozok.chat.entity.UserChatGroup;

public class ChatGroupDAO {

	public static ChatGroup getChatGroup(int id) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		ChatGroup chatGroup = currentSession.get(ChatGroup.class, id);
		trans.commit();
		return chatGroup;
	}

	public static void addUserToChatGroup(ChatGroup chatGroup, User user) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();

		UserChatGroup userChatGroup = new UserChatGroup();
		userChatGroup.setUserID(user.getId());
		userChatGroup.setChatGroupID(chatGroup.getId());

		currentSession.saveOrUpdate(userChatGroup);
		trans.commit();
	}

	public static void saveChatGroup(ChatGroup chatGroup) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		currentSession.saveOrUpdate(chatGroup);
		trans.commit();
	}

}
