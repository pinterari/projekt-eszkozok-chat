package hu.elte.project.eszkozok.chat;

import hu.elte.project.eszkozok.chat.entity.*;
import java.net.*;
import java.io.*;
import java.util.*;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class ChatServer {

    private final int port;
    private ServerSocket server;
    
    private Map<String, PrintWriter> clients = new HashMap<String, PrintWriter>();
    private Map<String, Integer> currentRoom = new HashMap<String, Integer>();
    
    private static SessionFactory factory;
    
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
    
        
    //Bejelentkezése
    private synchronized boolean addClient(User user, String password, PrintWriter pw) {
        if (clients.get(user.getUserName()) != null) return false;
        try{

	        if(user.getPassword().equals(password)){
	        	clients.put(user.getUserName(), pw);
	        	currentRoom.put(user.getUserName(), -1);
	        	send(user.getUserName(), "bejelentkezett.", "-1");
	        	return true;
	        }else{
	        	System.out.println("Hibas jelszo");
	        }

        } catch (Exception e){
        	System.err.println("Hiba a kliensek fogadasakor.");
            e.printStackTrace();
        }
        return false;
    }
        
    private synchronized void removeClient(String name) {
        clients.remove(name);
        currentRoom.remove(name);
    }
    

        
    //altalanos kuldes, a kliens uzenetet tovabbitja a tobbi kliensnek, az uzeneteket elmenti az adatbazisba    
    private synchronized void send(String name, String message, String room) {
        System.out.println(name + ": " + message);
        
        for (String n : clients.keySet()) {
        	if (! n.equals(name)) {
        		clients.get(n).println(name + ": " + message);
        	}
        }
    }
    
    //az adott group korabbi uzeneteit osszeszedi, alkuldi a megadott usernek
    private synchronized void sendPrevMessages(Integer groupId, String name) {
        
        Session session = factory.openSession();
        Transaction tx = null;
        //User user = null;
        try{
           tx = session.beginTransaction();
           
           String hql = "FROM Message m WHERE m.chatGroup_id = :groupId";
           Query<Message> query = session.createQuery(hql);
           query.setParameter("groupId",groupId);
           List<Message> messages = query.list();
           
           clients.get(name).println("PREVMESSAGES");
           for (Message message: messages){
        	   String username = getUserFromId(message.getUser()).getUserName();
        	   //String username = message.getUser().getUserName();
        	   String toSend = username + ": " + message.getMessage() + " : " + message.getDate();
	           clients.get(name).println(toSend);
           }
           clients.get(name).println("DONE");      

           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }

    }
    
    //kikeresi hogy melyik felhasznalok vannak eppen benne a groupban
    private synchronized void updatePrevMessages(int groupId, String name, String message){
    	try{
    		createMessage(groupId, name, message);
	        //uzenet elkuldese
	        for (String n : currentRoom.keySet()) {
	        	if (currentRoom.get(n) == groupId) {
	        		clients.get(n).println(name + ": " + message);
	        	}
	        }

        } catch (Exception e){
        	System.err.println("Hiba az uzenetek updateje kozben.");
            e.printStackTrace();
        }
    	
    }
    
    
    
    //elkuldi a kliensnek hogy kik vannak bejelentkezve (a fooldali listahoz)
    private synchronized void sendSignedInUsers(String name) {
    	clients.get(name).println("USERS");
    	for (String n : clients.keySet()) {
            if (! n.equals(name)) {
                clients.get(name).println(n + " is signed in ");
            }
        }
    	clients.get(name).println("DONE");
    }
    
    
    //az osszes felhasznalot kuldi el a kliensnek (uj felhasznalao felvetele a szobaba)
    private synchronized void sendAllUsers(String name) {
    	
    	Session session = factory.openSession();
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           List<User> users = session.createQuery("FROM User").list();
           clients.get(name).println("USERS");
           for(User user : users){
              String toSend = user.getId() + ": " + user.getUserName();
              clients.get(name).println(toSend);
           }
           tx.commit();
           clients.get(name).println("DONE");
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
    	
    }
    
    //a kliens szamara elerheto szobakat kuldi at
    private synchronized void sendChatrooms(User user) {
    	
    	Session session = factory.openSession();
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
    	
	    	clients.get(user.getUserName()).println("CHATROOMS");
	    	Set<ChatGroup> rooms = user.getChatGroupSet();
	    	session.update(user);
	    	for(ChatGroup room : rooms){
				 String toSend = room.getId() + ": " + room.getName();
				 clients.get(user.getUserName()).println(toSend);
			}
	    	clients.get(user.getUserName()).println("DONE");
	    	tx.commit();
    	
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
         }finally {
            session.close(); 
         }

    }

    
  //szoba mentése a táblába
    public synchronized Integer createRoom(String name, String username) {
        Session session = factory.openSession();
        User user = getUserFromUsername(username);
        session.update(user);
        Transaction tx = null;
        Integer roomID = null;
        try{
           tx = session.beginTransaction();
           ChatGroup room = new ChatGroup();
           roomID = (Integer) session.save(room); 
           room.setName(name);
           session.update(room);
           tx.commit();
           
           tx = session.beginTransaction();
           Set<User> userSet = new HashSet<User>();
           if(room.getUserSet() == null){
        	   userSet.add(user);
           }else{
        	   userSet = room.getUserSet();
        	   userSet.add(user);
           }

           room.setUserSet(userSet);
           session.update(room); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return roomID;
     }
    
    
    //Felhasználó mentése a táblába
    public synchronized Integer createUser(String username, String firstname, String lastname, String email, String password) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;
        try{
           tx = session.beginTransaction();
           User user = new User(username, firstname, lastname, email, password);
           userID = (Integer) session.save(user); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return userID;
     }
    
    
    public synchronized Integer createMessage(Integer roomId, String username, String messageText) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer messageID = null;
        try{
           tx = session.beginTransaction();
           
           Message message = new Message();
           message.setMessage(messageText);
           java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());

           message.setDate(sqlDate);
           message.setChatGroup(roomId);
           message.setUser(getUserFromUsername(username).getId());
           messageID = (Integer) session.save(message);
           session.update(message);
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return messageID;
     }
    
    
    
    //Felhasználó kiválasztása
    public synchronized User getUserFromUsername(String username) {
    	Session session = factory.openSession();
        Transaction tx = null;
        User user = null;
        try{
           tx = session.beginTransaction();
           
           String hql = "FROM User u WHERE u.userName = :user_name";
           Query query = session.createQuery(hql);
           query.setParameter("user_name",username);
           List<User> results = query.list();
           user = results.get(0);
           
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return user;
     }
    
    
    public synchronized User getUserFromId(int userId) {
    	Session session = factory.openSession();
        Transaction tx = null;
        User user = null;
        try{
        	tx = session.beginTransaction();
            user = (User)session.get(User.class, userId); 
            tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return user;
     }
    
    
    
    
    //felhasznalo meghivasa a szobaba
	 private synchronized void inviteUser(String name, int roomId, int invitedUserId) {
	 	
	 	Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         
	         User user = getUserFromId(invitedUserId);
	         
	         String hql = "FROM ChatGroup r WHERE r.id = :roomId";
	         Query query = session.createQuery(hql);
	         query.setParameter("roomId",roomId);
	         List<ChatGroup> results2 = query.list();
	         ChatGroup room = results2.get(0);
	         
	         Set<ChatGroup> roomSet = new HashSet<ChatGroup>();
	         
	         if(user.getChatGroupSet() == null){
	        	   roomSet.add(room);
	           }else{
	        	   roomSet = user.getChatGroupSet();
	        	   roomSet.add(room);
	           }
	         
	         user.setChatGroupSet(roomSet);
	         
	         Set<User> userSet = room.getUserSet();
	         userSet.add(user);
	         room.setUserSet(userSet);
	         
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	 	
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
            String message = null;
            try {
            	User user;
            	//regisztracio
            	if(br.readLine().equals("2")){
            		String username = br.readLine();
            		//System.out.println(username);
            		String firstname = br.readLine();
            		//System.out.println(firstname);
            		String lastname = br.readLine();
            		//System.out.println(lastname);
            		String email = br.readLine();
            		//System.out.println(email);
            		String password = br.readLine();
            		//System.out.println(password);
            		createUser(username, firstname, lastname, email, password);
            		user = getUserFromUsername(username);
            	}
            	
            	//bejelentkezes
            	boolean ok = false;
                while (! ok) {
                    name = br.readLine();
                    password = br.readLine();
                    if (name == null) return;
                    if(getUserFromUsername(name) == null){
                    	pw.println("nok");
                    }
                    user = getUserFromUsername(name);
                    ok = addClient(user, password, pw);
                    
                    if (ok) {
                    	pw.println("ok"); 
                    	user = getUserFromUsername(name);
                    }else pw.println("nok");
                }
            	
                //bejelentkezett felhasznalok es szobak kuldesi a kliensnek
                sendSignedInUsers(name);
                sendChatrooms(getUserFromUsername(name));
                //kliens megadja hogy mit akar
                String toDo = br.readLine();
                
                while(!toDo.equals("3")){
                	
	                if(toDo.equals("1")){
	                	//Chatszoba letrehozasa
	                	String roomName = br.readLine();
	                	createRoom(roomName, name);
	                	
	                }else if(toDo.equals("2")){
	                	//Belepes chatszobaba;
	                	
	                	//Elerheto szobak elkuldese a kliensnek
	                	sendChatrooms(getUserFromUsername(name));
	                	String roomId = br.readLine();
	                	
	                	//bejelentkezes a szobaba
	                	currentRoom.replace(name, Integer.parseInt(roomId));
	                	//korabbi uzenetek atkuldese a felhasznalonak
	                	sendPrevMessages(Integer.parseInt(roomId), name);
	                	//a csoportban levo osszes embernek elkuldi az infot hogy a felhasznalo belepett a szobaba
	                	updatePrevMessages(Integer.parseInt(roomId), name, " belepett.");
	                	
	                	while (! "exit".equals(message)) {
	                		if(! "INVITEUSER".equals(message)){
	                			//uzenet kuldese a szobaba
		                        message = br.readLine();
		                        if (message == null) break;
		                        updatePrevMessages(Integer.parseInt(roomId), name, message);
	                		} else {
	                			//felhasznalo meghivasa a szobaba
	                			sendAllUsers(name);
	                			message = br.readLine();
	                			inviteUser(name, Integer.parseInt(roomId), Integer.parseInt(message));
	                		}
	                    }
	                	//kilepteti a szobabol
	                	currentRoom.replace(name, -1);
	                	
	                	
	                }
	                
	                sendSignedInUsers(name);
	                sendChatrooms(getUserFromUsername(name));
	                toDo = br.readLine();
                	message = "";
	                
                }

            } catch (IOException e) {
                System.err.println("Kommunikacios problema egy kliensnel. Nev: " + name);
            } finally {
                if (name != null) send(name, "kijelentkezett.", "-1");
                removeClient(name);
                try {pw.close(); br.close();} catch (IOException e) {}
            }
        }
    }
        
    public static void main(String[] args) throws Exception {
    	//Class.forName("org.hsqldb.jdbc.JDBCDriver");
    	try{
            factory = new Configuration().configure().buildSessionFactory();
         }catch (Throwable ex) { 
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex); 
         }
        ChatServer server = new ChatServer(8081);
        if (server != null) server.handleClients();
    }
        
}

