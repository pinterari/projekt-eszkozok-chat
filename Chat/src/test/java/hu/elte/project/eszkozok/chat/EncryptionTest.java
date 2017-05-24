package hu.elte.project.eszkozok.chat;

import org.junit.Test;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import junit.framework.TestCase;

public class EncryptionTest extends TestCase {

	@Test
	public void testEncryptionPlugin() {
		Argon2 argon2 = Argon2Factory.create();
		char[] password = "password".toCharArray();

		try {
			String hash = argon2.hash(2, 65536, 1, "password");
			assertEquals(argon2.verify(hash, password), true);
		} finally {
			argon2.wipeArray(password);
		}
	}
}
