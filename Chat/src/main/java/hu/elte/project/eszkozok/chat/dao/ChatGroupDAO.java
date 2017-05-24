package hu.elte.project.eszkozok.chat.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import hu.elte.project.eszkozok.chat.db.SessionFactoryHelper;
import hu.elte.project.eszkozok.chat.entity.ChatGroup;
import hu.elte.project.eszkozok.chat.entity.User;
import hu.elte.project.eszkozok.chat.entity.UserChatGroup;

/**
 * <h1>ChatGroupDAO</h1> 
 * A ChatGroupDAO a chat alkalmazás adatbázis kapcsolatát
 * valósítja meg a ChatGroup entitáson át. Kapcsolatot létesít az adatbázissal a
 * Hibernate segítségével és kész objektumokat ad vissza illetve ment le.
 * 
 * @author Katona Bence
 *
 */
public class ChatGroupDAO {

	/**
	 * Egy egyedi adatbázis azonosító alapján lekérdezi a szoba objektumot.
	 * 
	 * @param id	A szoba egyedi adatbázis azonosítója.
	 * @return 		Az azonosító alapján lekérdezett ChatGroup objektum.
	 */
	public static ChatGroup getChatGroup(int id) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		ChatGroup chatGroup = currentSession.get(ChatGroup.class, id);
		trans.commit();
		return chatGroup;
	}

	/**
	 * A megadott felhasználót hozzáadja a szobához.
	 * 
	 * @param chatGroup		A szoba objektum, ahova hozzá akarjuk adni a felhasználót.
	 * @param user			A felhasználói objektum, akit hozzá szeretnénk adni a szobához.
	 */
	public static void addUserToChatGroup(ChatGroup chatGroup, User user) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();

		UserChatGroup userChatGroup = new UserChatGroup();
		userChatGroup.setUserID(user.getId());
		userChatGroup.setChatGroupID(chatGroup.getId());

		currentSession.saveOrUpdate(userChatGroup);
		trans.commit();
	}

	/**
	 * A megadott szoba objektumot elmenti az adatbázisba.
	 * 
	 * @param chatGroup		A szoba objektum, amit menteni akarunk.
	 */
	public static void saveChatGroup(ChatGroup chatGroup) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		currentSession.saveOrUpdate(chatGroup);
		trans.commit();
	}

	/**
	 * Egy egyedi adatbázis azonosító alapján kitörli az adatbázisból a szobát.
	 * 
	 * @param id	A szoba egyedi adatbázis azonosítója.
	 */
	public static void deleteChatGroup(int id) {
		Session currentSession = SessionFactoryHelper.getSessionFactory().getCurrentSession();
		Transaction trans = currentSession.beginTransaction();
		currentSession.createQuery("delete from ChatGroup where id=:chatGroupId").setParameter("chatGroupId", id).executeUpdate();
		trans.commit();
	}
}
