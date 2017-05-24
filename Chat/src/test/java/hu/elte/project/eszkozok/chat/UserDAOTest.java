package hu.elte.project.eszkozok.chat;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import hu.elte.project.eszkozok.chat.dao.UserDAO;
import hu.elte.project.eszkozok.chat.entity.User;

public class UserDAOTest {
	
	private static int id;

	@BeforeClass 
    public static void init() {
		User user = new User("###username###", "Elek", "Teszt", "teszt@gmail.com", "nemTúlBiztonságosJelszó");
		UserDAO.saveUser(user);
		id = user.getId();
    }

	@Test
    public void getUserTestId() {
		User user = UserDAO.getUser(id);
		assertEquals("###username###", user.getUserName());
    }

	@Test 
    public void getUserTestUsername() {
		User user = UserDAO.getUser("###username###");
		assertEquals("teszt@gmail.com", user.getEmail());
    }

	@AfterClass
	public static void destroy() {
		UserDAO.deleteUser(id);
	}
}
