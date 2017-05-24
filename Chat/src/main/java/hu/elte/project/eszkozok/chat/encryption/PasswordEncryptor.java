package hu.elte.project.eszkozok.chat.encryption;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import hu.elte.project.eszkozok.chat.entity.User;

/**
 * <h1>PasswordEncryptor</h1> 
 * A biztonságos adattároláshoz szükséges jelszótitkosítást és 
 * validálást elvégző osztály.
 * 
 * @author Katona Bence
 *
 */
public class PasswordEncryptor {

	/**
	 * Argon2 algorithmussal titkosítja a megadott karakterláncot.
	 * 
	 * @param password	A jelszó nyes formában.
	 * @return			A hashelt jelszó.
	 */
	public static String encryptPassword(String password) {
		Argon2 argon2 = Argon2Factory.create();
		return argon2.hash(2, 65536, 1, password);
	}

	/**
	 * A megadott felhasználó objektum jelszó mezőjében lévő hashelt jelszót összehasonlítja 
	 * a beírt jelszóval.
	 * 
	 * @param user		A felhasználó objektum, ami alapján validálunk.
	 * @param password	A beírt jelszó.
	 * @return 			A nyers jelszó megegyezik-e az adatbázisban tárolt hashelt jelszóval.
	 */
	public static boolean verifyPassword(User user, String password) {
		Argon2 argon2 = Argon2Factory.create();
		return user != null && argon2.verify(user.getPassword(), password);
	}

}