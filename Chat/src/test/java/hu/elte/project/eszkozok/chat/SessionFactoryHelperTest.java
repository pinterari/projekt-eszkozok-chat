package hu.elte.project.eszkozok.chat;

import static org.junit.Assert.assertNotEquals;

import org.hibernate.SessionFactory;
import org.junit.Test;

import hu.elte.project.eszkozok.chat.db.SessionFactoryHelper;

public class SessionFactoryHelperTest {

	@Test
	public void testSessionFactoryHelperTest() {
		SessionFactory factory = SessionFactoryHelper.getSessionFactory();
		assertNotEquals(factory, null);
	}
	
}
