package hu.elte.project.eszkozok.chat.encryption;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import hu.elte.project.eszkozok.chat.entity.User;

public class PasswordEncryptor {

	public static String encryptPassword(String password) {
		Argon2 argon2 = Argon2Factory.create();
		return argon2.hash(2, 65536, 1, password);
	}
	
	public static boolean verifyPassword(User user, String password) {
		Argon2 argon2 = Argon2Factory.create();
		return user != null && argon2.verify(user.getPassword(), password);
	}
	
}