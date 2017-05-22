package hu.elte.project.eszkozok.chat;

import java.awt.EventQueue;

import hu.elte.project.eszkozok.chat.gui.ChatFrame;

public class Test {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try{
					ChatFrame frame=new ChatFrame();
					frame.setVisible(true);
				}catch (Exception ex){
					ex.printStackTrace();
				}
				
			}
		});	
	}
}
