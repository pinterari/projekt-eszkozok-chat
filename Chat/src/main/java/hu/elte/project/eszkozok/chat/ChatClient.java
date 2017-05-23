package hu.elte.project.eszkozok.chat;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * <h1>ChatClient</h1> 
 * A ChatClient a Chat alkalmazás kliens oldali részét valósítja meg,
 * lehetővé téve a felhasználók egymással történő kommunikációját a szerveren keresztül.
 * 
 * @author Baráth Zsófia
 */
public class ChatClient {

	private PrintWriter pw;
	private BufferedReader br;
	private volatile boolean exit = false;

	private List<String> chatRooms = new ArrayList<String>();
	private List<String> signedInUsers = new ArrayList<String>();
	private List<String> messages = new ArrayList<String>();
	private Socket socket;

	/**
	 * A kliens konstruktora, ami létrehoz egy socketet a megadott paraméterekkel,
	 * majd beállítja a kommunikációs csatornákat.
	 *  
	 * @param host 	Az a hoszt, amin keresztül kapcsolódunk a szerverhez.
	 * @param port 	Az a port, amin keresztül kapcsolódunk a szerverhez.
	 * @throws 		Exception 
	 */
	public ChatClient(String host, int port) throws Exception {
		socket = new Socket(host, port);
		pw = new PrintWriter(socket.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	/**
	 * A felhasználó bejelentkezését teszi lehetővé.
	 * <p>
	 * Újra bekéri az adatokat addig, amíg a szerver nem hagyja azokat jóvá.
	 * 
	 * @param stdinReader 	Ezen keresztül folyik a kommunikáció a felhasználóval.
	 * @throws IOException
	 */
	public void signIn(BufferedReader stdinReader) throws IOException {
		String answer = "";
		while (!answer.equals("ok")) {
			System.out.print("Felhasznalónév: ");
			pw.println(stdinReader.readLine());
			System.out.print("Jelszó: ");
			pw.println(stdinReader.readLine());

			answer = br.readLine();
			System.out.println(answer);
		}
		System.out.println("Sikeres bejelentkezés!");
	}

	/**
	 * A felhasználó regisztrációját teszi lehetővé.
	 * <p>
	 * Újra bekéri az adatokat addig, amíg a szerver nem hagyja azokat jóvá.
	 * 
	 * @param stdinReader 	Ezen keresztül folyik a kommunikáció a felhasználóval.
	 * @throws IOException
	 */
	public void signUp(BufferedReader stdinReader) throws IOException {
		String answer = "";
		while (!answer.equals("ok")) {
			System.out.print("Felhasználónév: ");
			pw.println(stdinReader.readLine());
			System.out.print("Keresztnév: ");
			pw.println(stdinReader.readLine());
			System.out.print("Vezetéknév: ");
			pw.println(stdinReader.readLine());
			System.out.print("E-mail: ");
			pw.println(stdinReader.readLine());
			System.out.print("Jelszó: ");
			pw.println(stdinReader.readLine());

			answer = br.readLine();
		}
		System.out.println("Sikeres regisztráció!");
	}

	/**
	 * Segédfüggvény adatok listázására.
	 * 
	 * @param data			Kiírni kívánt lista.
	 * @throws IOException
	 */
	public void listData(List<String> data) throws IOException {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.err.println("Hiba történt az adok kiírása közben.");
			e.printStackTrace();
		}
		
		for (int i = 0; i < data.size(); ++i) {
			System.out.println("+ " + data.get(i));
		}
	}

	/**
	 * A szerverrel történő kommunikációt bonyolítja le.
	 * <p>
	 * Elsőként a felhasználónak be kell jelentkeznie, vagy regisztrálnia kell.
	 * Sikeres regisztráció esetén a felhasználót átirányíta a bejelentkező oldalra.
	 * <p>
	 * Bejelentkezés után a felhasználónak lehetősége van kilépésre, belépésre egy létező szobába,
	 * vagy új szoba létrehozására. A szobákba más felhasználókat is meghívhat.
	 * <p>
	 * A függvény két szálat indít; az első az üzeneteket továbbítja a szerverre,
	 * a második pedig a szerverről érkező üzeneteket fogadja. 
	 * 
	 * @throws IOException
	 */
	public void communicate() throws IOException {
		BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

		// Bejelentkezés vagy regisztráció
		System.out.println("[1] Bejelentkezés");
		System.out.println("[2] Regisztráció");
		
		String sin = stdinReader.readLine();
		pw.println(sin);
		
		if (sin.equals("1")) {
			signIn(stdinReader);
		} else if (sin.equals("2")) {
			signUp(stdinReader);
			signIn(stdinReader);
		} else {
			return;
		}

		// Üzenetek továbbítása a szerver felé
		new Thread() {
			public void run() {
				BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
				String message = "";
				String toDo = "";
				try {

					System.out.println("[1] Szoba létrehozása");
					System.out.println("[2] Belépés egy létező szobába");
					System.out.println("[3] Kilépés");

					toDo = stdinReader.readLine();
					pw.println(toDo);

					while (!toDo.equals("3")) {
						if (toDo.equals("1")) {

							System.out.println("Szoba neve: ");
							pw.println(stdinReader.readLine());
							
						} else if (toDo.equals("2")) {
							
							System.out.println("Szoba azonosítója: ");
							pw.println(stdinReader.readLine());
							
							System.out.println("Használható parancsok: ");
							System.out.println(" -- [EXIT] Kilépés");
							System.out.println(" -- [INVITEUSER] Felhasználó meghívása a szobába");

							while (!message.toUpperCase().equals("EXIT")) {
								if (!message.toUpperCase().equals("INVITEUSER")) {
									message = stdinReader.readLine();
									pw.println(message);
								} else {
									System.out.println("Felhasználó meghívása: ");
									message = stdinReader.readLine();
									pw.println(message);
								}
							}
						}

						System.out.println("[1] Szoba létrehozása");
						System.out.println("[2] Belépés egy létező szobába");
						System.out.println("[3] Kilépés");
						
						toDo = stdinReader.readLine();
						pw.println(toDo);
						message = "";

					}

				} catch (IOException e) {
					System.err.println("Kommunikácios hiba a küldő szálban.");
				}
				exit = true;
			}
		}.start();

		// Üzenetek fogadása a szerver felől
		new Thread() {
			public void run() {
				try {
					while (!exit) {
						
						String message = br.readLine();

						if (message != null) {
							
							if (message.equals("CHATROOMS")) {
								message = br.readLine();
								chatRooms.clear();
								while (!message.equals("DONE")) {
									chatRooms.add(message);
									message = br.readLine();
								}
								System.out.println("--- Elérhető chatszobák: ");
								listData(chatRooms);
							}
							
							if (message.equals("USERS")) {
								message = br.readLine();
								signedInUsers.clear();
								while (!message.equals("DONE")) {
									signedInUsers.add(message);
									message = br.readLine();
								}
								System.out.println("--- Elérhető felhasználók: ");
								listData(signedInUsers);
							}

							if (message.equals("PREVMESSAGES")) {
								message = br.readLine();
								messages.clear();
								while (!message.equals("DONE")) {
									messages.add(message);
									message = br.readLine();
								}
								System.out.println("--- Korábbi üzenetek: ");
								listData(messages);
							}

							System.out.println(message);

						}
					}
				} catch (IOException e) {
					System.err.println("Kommunikációs hiba a fogadó szálban.");
				}
			}
		}.start();

	}

	/**
	 * A kliens osztály main metódusa. Adott host és port paraméterekkel létrehoz
	 * egy saját ChatClient példányt, majd elindítja a kommunikációt.
	 * 
	 * @param args 			Parancssori argumentumok listája
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port = 8081;
		ChatClient cc = new ChatClient(host, port);
		cc.communicate();
	}

}
