package hu.elte.project.eszkozok.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hu.elte.project.eszkozok.chat.dao.ChatGroupDAO;
import hu.elte.project.eszkozok.chat.dao.MessageDAO;
import hu.elte.project.eszkozok.chat.dao.UserDAO;
import hu.elte.project.eszkozok.chat.encryption.PasswordEncryptor;
import hu.elte.project.eszkozok.chat.entity.ChatGroup;
import hu.elte.project.eszkozok.chat.entity.Message;
import hu.elte.project.eszkozok.chat.entity.User;

/**
 * <h1>ChatServer</h1>
 * A ChatServer a chat alkalmazás szerveroldali részét valósítja meg.
 * Kliensek csatlakozhatnak hozzá, és a köztük történő kommunikációt bonyolítja le.
 * 
 * @author Baráth Zsófia
 */
public class ChatServer {

	private static final int PORT = 8081;
	private ServerSocket server;

	private Map<String, PrintWriter> clients = new HashMap<String, PrintWriter>();
	private Map<String, Integer> currentRoom = new HashMap<String, Integer>();

	/**
	 * A szerver konstruktora, ami létrehoz egy ServerSocketet a megadott portszámmal,
	 * amin keresztül kapcsolódni tudnak hozzá a kliensek.
	 * 
	 * @param port A port, amin keresztül a kliensek kapcsolódnak.
	 */
	ChatServer(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("A szerver elindult.");
		} catch (IOException e) {
			System.err.println("Hiba történt a szerver indításánál.");
			e.printStackTrace();
		}
	}

	/**
	 * A kliensek fogadásáért felelős függvény. Minden socketen keresztül csatlakozott
	 * kliensnek létrehoz egy ClientHandler példányt, majd elindítja az így létrejött szálat. 
	 */
	public void handleClients() {
		while (true) {
			try {
				new ClientHandler(server.accept()).start();
			} catch (IOException e) {
				System.err.println("Hiba történt a kliensek fogadásakor.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Bejelentkezéskor validáció után a kliensek listájához adja a felhasználót.
	 * <p> 
	 * Hamisat ad vissza, ha nem létezik felhasználó ilyen névvel,
	 * vagy hibás az adott névhez tartozó jelszó.
	 * 
	 * @param user		A felhasználói objektum, amit hozzá szeretnénk adni.
	 * @param password	A begépelt jelszó.
	 * @param pw		A csatorna, amin keresztül kommunikálunk a felhasználóval.
	 * @return			Sikerült-e bejelentkeznie a felhasználónak.
	 */
	private synchronized boolean addClient(User user, String password, PrintWriter pw) {
		if (clients.get(user.getUserName()) != null) {
			return false;
		}

		try {

			if (PasswordEncryptor.verifyPassword(user, password)) {
				clients.put(user.getUserName(), pw);
				currentRoom.put(user.getUserName(), -1);
				send(user.getUserName(), "bejelentkezett.");
				return true;
			} else {
				System.out.println("Hibás jelszó!");
			}

		} catch (Exception e) {
			System.err.println("Hiba történt a kliens fogadásakor.");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Eltávolítja a bejelentkezettek és a szobában tartózkodók listájáról a felhasználót.
	 * 
	 * @param username	Annak a felhasználónak a neve, akit el szeretnénk távolítani.
	 */
	private synchronized void removeClient(String username) {
		clients.remove(username);
		currentRoom.remove(username);
	}

	/**
	 * Általános célú üzenetküldés.
	 * <p>
	 * A szerver elküldi a klienseknek az adott felhasználó üzenetét,
	 * a be- és kijelentkezésről tájékoztat.
	 * 
	 * @param username	A felhasználó neve, aki írta az üzenetet.
	 * @param message	A továbbküldendő üzenet.
	 * @param room		A szoba, amelyikben a felhasználó éppen van.
	 */
	private synchronized void send(String username, String message) {
		for (String n : clients.keySet()) {
			if (!n.equals(username)) {
				clients.get(n).println(username + ": " + message);
			}
		}
	}

	/**
	 * Összegyűjti az adott szoba korábbi üzeneteit, majd átküldi a megadott nevű felhasználónak.
	 * 
	 * @param groupId	A szoba azonosítója.
	 * @param name		A felhasználó neve.
	 */
	private synchronized void sendPrevMessages(int groupId, String name) {
		List<Message> messages = MessageDAO.getMessages(groupId);

		clients.get(name).println("PREVMESSAGES");
		for (Message message : messages) {
			String username = UserDAO.getUser(message.getUserID()).getUserName();
			String toSend = "[" + message.getDate() + "] " + username + ": " + message.getMessage();
			clients.get(name).println(toSend);
		}
		clients.get(name).println("DONE");
	}

	/**
	 * Elküldi a megadott azonosítójú szobában tartózkodóknak az adott üzenetet.
	 * 
	 * @param groupId	A szoba azonosítója.
	 * @param username	A felhasználó neve.
	 * @param message	A felhasználó üzenete.
	 */
	private synchronized void updatePrevMessages(int groupId, String username, String message) {
		try {
			createMessage(groupId, username, message);

			for (String n : currentRoom.keySet()) {
				if (currentRoom.get(n) == groupId) {
					clients.get(n).println(username + ": " + message);
				}
			}

		} catch (Exception e) {
			System.err.println("Hiba történt az üzenetek betöltése közben.");
			e.printStackTrace();
		}

	}

	/**
	 * Elküldi a kliensnek az épp bejelentkezett felhasználók listáját, az adott
	 * felhasználó kivételével. 
	 * 
	 * @param username	A felhasználó neve. 
	 */
	private synchronized void sendSignedInUsers(String username) {
		clients.get(username).println("USERS");
		for (String n : clients.keySet()) {
			if (!n.equals(username)) {
				clients.get(username).println(n + " be van jelentkezve.");
			}
		}
		clients.get(username).println("DONE");
	}

	/**
	 * Új szoba létrehozásakor elküldi a kliensnek azokat a felhasználókat, 
	 * akik még nincsenek benne az adott azonosítójú szobában,
	 * azon felhasználó kivételével, aki létrehozta a szobát.
	 * 
	 * @param username	A szobát létrehozó felhasználó neve. 
	 * @param roomID	A szoba azonosítója.
	 * @return			Az összegyűjtött felhasználók száma.
	 */
	private synchronized int sendValidUsersForinvite(String username, int roomID) {
		List<User> users = UserDAO.getUsers();
		Set<User> usersInGroup = ChatGroupDAO.getChatGroup(roomID).getUserSet();
		int counter = 0;

		clients.get(username).println("USERS");
		for (User user : users) {
			if (!user.getUserName().equals(username) && !usersInGroup.contains(user)) {
				String toSend = user.getId() + ": " + user.getUserName();
				clients.get(username).println(toSend);
				counter++;
			}
		}
		clients.get(username).println("DONE");
		return counter;
	}

	/**
	 * Elküldi a felhasználó számára az elérhető szobákat.
	 * 
	 * @param user	A felhasználó, akinek elküldjük a szobákat.
	 */
	private synchronized void sendChatrooms(User user) {
		clients.get(user.getUserName()).println("CHATROOMS");
		
		Set<ChatGroup> rooms = user.getChatGroupSet();
		for (ChatGroup room : rooms) {
			String toSend = room.getId() + ": " + room.getName();
			clients.get(user.getUserName()).println(toSend);
		}
		
		clients.get(user.getUserName()).println("DONE");
	}

	/**
	 * Új szobát hoz létre, majd hozzárendeli az adott felhasználót.
	 * 
	 * @param roomName	Az új chatszoba neve.	
	 * @param username	Annak a felhasználónak a neve, aki létrehozta a szobát.
	 */
	public synchronized void createRoom(String roomName, String username) {
		User user = UserDAO.getUser(username);

		ChatGroup room = new ChatGroup();
		room.setName(roomName);
		ChatGroupDAO.saveChatGroup(room);
		ChatGroupDAO.addUserToChatGroup(room, user);
	}

	/**
	 * Létrehoz egy üzenet objektumot a szoba azonosítója, a felhasználó neve és az üzenet szövege alapján, 
	 * hozzárendeli a jelenlegi időpontot, majd ezt elmenti az adatbázisba.
	 * 
	 * @param roomId		A szoba azonosítója.
	 * @param username		A felhasználó neve.
	 * @param messageText	Az üzenet szövege.
	 */
	public synchronized void createMessage(Integer roomId, String username, String messageText) {
		Message message = new Message();

		message.setMessage(messageText);

		Timestamp sqlDate = new Timestamp(new Date().getTime());
		message.setDate(sqlDate);

		message.setChatGroupID(roomId);
		message.setUserID(UserDAO.getUser(username).getId());

		MessageDAO.saveMessage(message);
	}

	/**
	 * Hozzárendeli az adott azonosítójú felhasználót az adott azonosítójú chatszobához.
	 * 
	 * @param roomId			A szoba azonosítója, amibe meghívjuk a felhasználót.
	 * @param invitedUserId		A meghívott felhasználó azonosítja.
	 */
	private synchronized void inviteUser(int roomId, int invitedUserId) {
		User user = UserDAO.getUser(invitedUserId);
		ChatGroup room = ChatGroupDAO.getChatGroup(roomId);
		ChatGroupDAO.addUserToChatGroup(room, user);
	}

	
	/**
	 * <h2>ClientHandler</h2>
	 * A szerver és a kliens kommunikációját lebonyolító segédosztály,
	 * ami egy szálat társít minden egyes csatlakozott felhasználóhoz.
	 * 
	 * @author Baráth Zsófia
	 */
	class ClientHandler extends Thread {

		PrintWriter pw;
		BufferedReader br;
		String name;
		String password;

		/**
		 * A konstruktor a kapott socket kommunikációs csatornáit kérdezi le,
		 * ezeken keresztül fogja tartani a kapcsolatot az adott klienssel.
		 * 
		 * @param s A socket, amin keresztül a kliens kapcsolódik a szerverhez.
		 */
		ClientHandler(Socket s) {
			try {
				pw = new PrintWriter(s.getOutputStream(), true);
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			} catch (IOException e) {
				System.err.println("Inicializálási probléma történt egy kliensnél.");
			}
		}

		/**
		 * A kommunikációt lebonyolító szál. Főbb funkciói:
		 * 
		 * <h3>Regisztráció</h4>
		 * Beolvassa a felhasználó adatait, majd megkísérli elmenteni őket.
		 * Egészen addig kéri be a felhasználó adatait, amíg nem sikerült a mentés.
		 * 
		 * <h3>Bejelentkezés</h3>
		 * Beolvassa a felhasználónevet és jelszót. Újra bekéri az adatokat,
		 * amíg nem talál megfelelő párt.
		 * 
		 * <h3>A felhasználó által végrehajtani kívánt funkciók:</h3>
		 * <ul>
		 * 	<li> Chatszoba létrehozása </li>
		 * 	<li> Belépés egy chatszobába </li>
		 * 	<li> Felhasználó meghívása a chatszobába </li>
		 *  <li> Üzenet küldése a chatszobába </li>
		 *  <li> Kilépés a chatszobából </li>
		 * </ul>
		 */
		
		@Override
		public void run() {
			String message = "";
			try {
				
				//Regisztráció
				if (br.readLine().equals("2")) {
					boolean ok = false;
					while (!ok) {
						String username = br.readLine();
						String firstname = br.readLine();
						String lastname = br.readLine();
						String email = br.readLine();
						String password = PasswordEncryptor.encryptPassword(br.readLine());
						
						if (UserDAO.saveUser(new User(username, firstname, lastname, email, password))) {
							pw.println("ok");
							ok = true;
						} else {
							pw.println("nok");
						}
					}
				}

				// Bejelentkezés
				boolean ok = false;
				while (!ok) {
					name = br.readLine();
					password = br.readLine();

					if (name == null) return;

					User user = UserDAO.getUser(name);
					
					if (user == null) {
						pw.println("nok");
					} else {
						ok = addClient(user, password, pw);

						if (ok) pw.println("ok");
						else pw.println("nok");
						
					}
				}

				// Elküldi a kliensnek a bejelentkezett felhasználók és elérhető szobák listáját
				sendSignedInUsers(name);
				sendChatrooms(UserDAO.getUser(name));
				
				// Kliens megadja hogy mit akar
				String toDo = br.readLine();

				while (!toDo.equals("3")) {

					//Chatszoba létrehozása
					if (toDo.equals("1")) {
						String roomName = br.readLine();
						createRoom(roomName, name);

					} else if (toDo.equals("2")) {
						// Belépés egy chatszobába

						// Elérhető szobák elküldése a kliensnek
						sendChatrooms(UserDAO.getUser(name));
						String roomId = br.readLine();
						int numberRoomID = Integer.parseInt(roomId);

						// Bejelentkezés a szobába
						currentRoom.replace(name, numberRoomID);
						
						// Korábbi üzenetek betöltése
						sendPrevMessages(numberRoomID, name);
						
						// Elküldi a csoportban lévőknek, hogy a felhasználó belépett
						updatePrevMessages(numberRoomID, name, " belepett.");

						while (!"EXIT".equals(message.toUpperCase())) {
							
							if (!"INVITEUSER".equals(message.toUpperCase())) {
								// Üzenet küldése a szobába
								message = br.readLine();
								if (message == null) break;
								updatePrevMessages(numberRoomID, name, message);
								
							} else {
								// Felhasználó meghívása a szobába
								int numberOfSentUsers = sendValidUsersForinvite(name, numberRoomID);
								if (numberOfSentUsers > 0) {
									message = br.readLine();
									inviteUser(numberRoomID, Integer.parseInt(message));
								}
								message = "";
							}
						}
						
						// Kilépteti a felhasználót a szobából
						currentRoom.replace(name, -1);

					}

					sendSignedInUsers(name);
					sendChatrooms(UserDAO.getUser(name));
					
					toDo = br.readLine();
					message = "";

				}

			} catch (IOException e) {
				System.err.println("Kommunikációs probléma történt a következő kliensnél: " + name);
			} finally {
				
				if (name != null) {
					send(name, "kijelentkezett.");
					removeClient(name);
				}
					
				try {
					pw.close();
					br.close();
				} catch (IOException e) {
					System.err.println("Hiba történt a kommunikációs csatornák bezárása közben"
							+ " a következő kliensnél: " + name);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * A szerver main metódusa, ami létrehoz egy ChatServer példányt a megadott portszámmal,
	 * majd amíg nem szűnik meg az objektum, a klienseket kezeli.
	 * 
	 * @param args			Parancssori argumentumok listája.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ChatServer server = new ChatServer(PORT);
		if (server != null)
			server.handleClients();
	}

}
