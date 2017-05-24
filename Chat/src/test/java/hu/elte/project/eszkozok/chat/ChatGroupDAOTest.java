package hu.elte.project.eszkozok.chat;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import hu.elte.project.eszkozok.chat.dao.ChatGroupDAO;
import hu.elte.project.eszkozok.chat.entity.ChatGroup;

public class ChatGroupDAOTest {

	private static int id;

	@BeforeClass 
    public static void init() {
		ChatGroup chatGroup = new ChatGroup("###TESZTSZOBA###");
		ChatGroupDAO.saveChatGroup(chatGroup);
		id = chatGroup.getId();
    }

	@Test
    public void getChatGroupTestId() {
		ChatGroup chatGroup = ChatGroupDAO.getChatGroup(id);
		assertEquals("###TESZTSZOBA###", chatGroup.getName());
    }

	@AfterClass
	public static void destroy() {
		ChatGroupDAO.deleteChatGroup(id);
	}
	
}
