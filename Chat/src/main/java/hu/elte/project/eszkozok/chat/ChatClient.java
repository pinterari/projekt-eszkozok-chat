package hu.elte.project.eszkozok.chat;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class ChatClient {

	private PrintWriter pw;
	private BufferedReader br;
	private volatile boolean exit = false;

	private List<String> chatRooms = new ArrayList<String>();
	private List<String> signedInUsers = new ArrayList<String>();
	private List<String> messages = new ArrayList<String>();
	private Socket socket;

	public ChatClient(String host, int port) throws Exception {
		socket = new Socket(host, port);
		pw = new PrintWriter(socket.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void signIn(BufferedReader stdinReader) throws IOException {
		// Nev bekerese, amig a szerver valasza nem "ok"
		String answer = "";
		while (!answer.equals("ok")) {
			System.out.print("Felhasznalonev: ");
			pw.println(stdinReader.readLine());
			System.out.print("Jelszo: ");
			pw.println(stdinReader.readLine());
			
			answer = br.readLine();
			System.out.println(answer);
		}
		System.out.println("Sikeres bejelentkezes.");
	}

	// Regisztracios adatok atkuldese szerverre
	public void signUp(BufferedReader stdinReader) throws IOException {
		String answer = "";
		while (!answer.equals("ok")) {
			System.out.print("Username: ");
			pw.println(stdinReader.readLine());
			System.out.print("First name: ");
			pw.println(stdinReader.readLine());
			System.out.print("Last name: ");
			pw.println(stdinReader.readLine());
			System.out.print("email: ");
			pw.println(stdinReader.readLine());
			System.out.print("Password: ");
			pw.println(stdinReader.readLine());
			
			answer = br.readLine();
			System.out.println(answer);
		}
		System.out.println("Sikeres regisztracio!");
	}

	// Listak kiiratasa
	public void listData(List<String> data) throws IOException {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.err.println("Hiba.");
			e.printStackTrace();
		}
		for (int i = 0; i < data.size(); ++i) {
			System.out.println("+ " + data.get(i));
		}
	}

	public void communicate() throws IOException {
		BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

		// Bejelentkezes vagy regisztracio. reg utan vissza a bejeltkezo oldalra
		System.out.println("Bejel(1) / Reg(2)");
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

		// Szal a begepelt uzenetek tovabbitasara a szerver fele
		new Thread() {
			public void run() {
				BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
				String message = "";
				String toDo = "";
				try {
					
					System.out.println("Szoba keszites(1) / belepes szobaba(2) / kilepes(3)");

					toDo = stdinReader.readLine();
					pw.println(toDo);

					while (!toDo.equals("3")) {
						if (toDo.equals("1")) {
							// Szoba nevenek bekerese, atkuldese serverre
							System.out.println("Chatszoba neve:");
							pw.println(stdinReader.readLine());
						} else if (toDo.equals("2")) {
							// Szoba azonositojat varja, es atkuldi servernek
							System.out.println("Chatszoba azonositoja:");
							pw.println(stdinReader.readLine());

							while (!message.equals("exit")) {
								if (!message.equals("INVITEUSER")) {
									message = stdinReader.readLine();
									pw.println(message);
								} else {
									System.out.println("Invite a user");
									message = stdinReader.readLine();
									pw.println(message);
								}
							}
						}

						System.out.println("Szoba keszites(1) / belepes szobaba(2) / kilepes(3)");
						toDo = stdinReader.readLine();
						pw.println(toDo);
						message = "";

					}

				} catch (IOException e) {
					System.err.println("Kommunikacios hiba a kuldo szalban.");
				}
				exit = true;
			}
		}.start();

		// Szal az a szerver felol erkezo uzenetek fogadasara es megjelenitesere
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
								listData(chatRooms);
							}

							if (message.equals("USERS")) {
								message = br.readLine();
								signedInUsers.clear();
								while (!message.equals("DONE")) {
									signedInUsers.add(message);
									message = br.readLine();
								}
								listData(signedInUsers);
							}

							if (message.equals("PREVMESSAGES")) {
								message = br.readLine();
								messages.clear();
								while (!message.equals("DONE")) {
									messages.add(message);
									message = br.readLine();
								}
								listData(messages);
							}

							System.out.println(message);

						}
					}
				} catch (IOException e) {
					System.err.println("Kommunikacios hiba a fogado szalban.");
				}
			}
		}.start();

	}

	public static void main(String[] args) throws Exception {
		String host = "localhost"; // server host
		int port = 8081; // server port
		ChatClient cc = new ChatClient(host, port);
		cc.communicate();
	}

}
