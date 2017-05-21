package hu.elte.project.eszkozok.chat;

import java.awt.EventQueue;
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
import hu.elte.project.eszkozok.chat.gui.ChatFrame;

public class ChatServer {

	private final int port;
	private ServerSocket server;

	private Map<String, PrintWriter> clients = new HashMap<String, PrintWriter>();
	private Map<String, Integer> currentRoom = new HashMap<String, Integer>();

	public int getPort() {
		return port;
	}

	ChatServer(int port) {
		this.port = port;
		try {
			server = new ServerSocket(port);
			System.out.println("A chat server elindult.");
		} catch (IOException e) {
			System.err.println("Hiba a chat server inditasanal.");
			e.printStackTrace();
		}
	}

	public void handleClients() {
		while (true) {
			try {
				new ClientHandler(server.accept()).start();
			} catch (IOException e) {
				System.err.println("Hiba a kliensek fogadasakor.");
				e.printStackTrace();
			}
		}
	}

	// Bejelentkezése
	private synchronized boolean addClient(User user, String password, PrintWriter pw) {
		if (clients.get(user.getUserName()) != null) {
			return false;
		}

		try {

			if (PasswordEncryptor.verifyPassword(user, password)) {
				clients.put(user.getUserName(), pw);
				currentRoom.put(user.getUserName(), -1);
				send(user.getUserName(), "bejelentkezett.", "-1");
				return true;
			} else {
				System.out.println("Hibas jelszo");
			}

		} catch (Exception e) {
			System.err.println("Hiba a kliensek fogadasakor.");
			e.printStackTrace();
		}
		return false;
	}

	private synchronized void removeClient(String name) {
		clients.remove(name);
		currentRoom.remove(name);
	}

	// Altalanos kuldes, a kliens uzenetet tovabbitja a tobbi kliensnek, az
	// uzeneteket elmenti az adatbazisba
	private synchronized void send(String name, String message, String room) {
		System.out.println(name + ": " + message);

		for (String n : clients.keySet()) {
			if (!n.equals(name)) {
				clients.get(n).println(name + ": " + message);
			}
		}
	}

	// Az adott group korabbi uzeneteit osszeszedi, alkuldi a megadott usernek
	private synchronized void sendPrevMessages(Integer groupId, String name) {
		List<Message> messages = MessageDAO.getMessages(groupId);

		clients.get(name).println("PREVMESSAGES");
		for (Message message : messages) {
			String username = UserDAO.getUser(message.getUserID()).getUserName();
			String toSend = username + ": " + message.getMessage() + " : " + message.getDate();
			clients.get(name).println(toSend);
		}
		clients.get(name).println("DONE");
	}

	// Kikeresi hogy melyik felhasznalok vannak eppen benne a groupban
	private synchronized void updatePrevMessages(int groupId, String name, String message) {
		try {
			createMessage(groupId, name, message);
			// Uzenet elkuldese
			for (String n : currentRoom.keySet()) {
				if (currentRoom.get(n) == groupId) {
					clients.get(n).println(name + ": " + message);
				}
			}

		} catch (Exception e) {
			System.err.println("Hiba az uzenetek updateje kozben.");
			e.printStackTrace();
		}

	}

	// Elkuldi a kliensnek hogy kik vannak bejelentkezve (a fooldali listahoz)
	private synchronized void sendSignedInUsers(String name) {
		clients.get(name).println("USERS");
		for (String n : clients.keySet()) {
			if (!n.equals(name)) {
				clients.get(name).println(n + " is signed in ");
			}
		}
		clients.get(name).println("DONE");
	}

	// Az osszes felhasznalot kuldi el a kliensnek (uj felhasznalo felvetele a
	// szobaba)
	private synchronized int sendValidUsersForinvite(String name, int roomID) {
		List<User> users = UserDAO.getUsers();
		Set<User> usersInGroup = ChatGroupDAO.getChatGroup(roomID).getUserSet();
		int counter = 0;

		clients.get(name).println("USERS");
		for (User user : users) {
			if (!user.getUserName().equals(name) && !usersInGroup.contains(user)) {
				String toSend = user.getId() + ": " + user.getUserName();
				clients.get(name).println(toSend);
				counter++;
			}
		}
		clients.get(name).println("DONE");
		return counter;
	}

	// A kliens szamara elerheto szobakat kuldi at
	private synchronized void sendChatrooms(User user) {
		clients.get(user.getUserName()).println("CHATROOMS");
		Set<ChatGroup> rooms = user.getChatGroupSet();
		for (ChatGroup room : rooms) {
			String toSend = room.getId() + ": " + room.getName();
			clients.get(user.getUserName()).println(toSend);
		}
		clients.get(user.getUserName()).println("DONE");
	}

	// Szoba mentése a táblába
	public synchronized void createRoom(String name, String username) {
		User user = UserDAO.getUser(username);

		ChatGroup room = new ChatGroup();
		room.setName(name);
		ChatGroupDAO.saveChatGroup(room);
		ChatGroupDAO.addUserToChatGroup(room, user);
	}

	public synchronized void createMessage(Integer roomId, String username, String messageText) {
		Message message = new Message();

		message.setMessage(messageText);

		Timestamp sqlDate = new Timestamp(new Date().getTime());
		message.setDate(sqlDate);

		message.setChatGroupID(roomId);
		message.setUserID(UserDAO.getUser(username).getId());

		MessageDAO.saveMessage(message);
	}

	// Felhasznalo meghivasa a szobaba
	private synchronized void inviteUser(int roomId, int invitedUserId) {
		User user = UserDAO.getUser(invitedUserId);
		ChatGroup room = ChatGroupDAO.getChatGroup(roomId);
		ChatGroupDAO.addUserToChatGroup(room, user);
	}

	class ClientHandler extends Thread {

		PrintWriter pw;
		BufferedReader br;
		String name;
		String password;

		ClientHandler(Socket s) {
			try {
				pw = new PrintWriter(s.getOutputStream(), true);
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			} catch (IOException e) {
				System.err.println("Inicializalasi problema egy kliensnel.");
			}
		}

		@Override
		public void run() {
			String message = "";
			try {
				// Regisztracio
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

				// Bejelentkezes
				boolean ok = false;
				while (!ok) {
					name = br.readLine();
					password = br.readLine();

					if (name == null)
						return;

					User user = UserDAO.getUser(name);
					if (user == null) {
						pw.println("nok");
					} else {
						ok = addClient(user, password, pw);

						if (ok) {
							pw.println("ok");
						} else {
							pw.println("nok");
						}
					}

				}

				// Bejelentkezett felhasznalok es szobak kuldesi a kliensnek
				sendSignedInUsers(name);
				sendChatrooms(UserDAO.getUser(name));
				// Kliens megadja hogy mit akar
				String toDo = br.readLine();

				while (!toDo.equals("3")) {

					if (toDo.equals("1")) {
						// Chatszoba letrehozasa
						String roomName = br.readLine();
						createRoom(roomName, name);

					} else if (toDo.equals("2")) {
						// Belepes chatszobaba;

						// Elerheto szobak elkuldese a kliensnek
						sendChatrooms(UserDAO.getUser(name));
						String roomId = br.readLine();
						int numberRoomID = Integer.parseInt(roomId);

						// Bejelentkezes a szobaba
						currentRoom.replace(name, numberRoomID);
						// Korabbi uzenetek atkuldese a felhasznalonak
						sendPrevMessages(numberRoomID, name);
						// A csoportban levo osszes embernek elkuldi az infot
						// hogy a felhasznalo belepett a szobaba
						updatePrevMessages(numberRoomID, name, " belepett.");

						while (!"EXIT".equals(message.toUpperCase())) {
							if (!"INVITEUSER".equals(message.toUpperCase())) {
								// Uzenet kuldese a szobaba
								message = br.readLine();
								if (message == null)
									break;
								updatePrevMessages(numberRoomID, name, message);
							} else {
								// Felhasznalo meghivasa a szobaba
								int numberOfSentUsers = sendValidUsersForinvite(name, numberRoomID);
								if (numberOfSentUsers > 0) {
									message = br.readLine();
									inviteUser(numberRoomID, Integer.parseInt(message));
								}
								message = "";
							}
						}
						// Kilepteti a szobabol
						currentRoom.replace(name, -1);

					}

					sendSignedInUsers(name);
					sendChatrooms(UserDAO.getUser(name));
					toDo = br.readLine();
					message = "";

				}

			} catch (IOException e) {
				System.err.println("Kommunikacios problema egy kliensnel. Nev: " + name);
			} finally {
				if (name != null)
					send(name, "kijelentkezett.", "-1");
				removeClient(name);
				try {
					pw.close();
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ChatServer server = new ChatServer(8081);
		if (server != null)
			server.handleClients();
	}

}
