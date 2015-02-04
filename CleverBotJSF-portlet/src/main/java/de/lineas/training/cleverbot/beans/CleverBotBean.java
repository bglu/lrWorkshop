package de.lineas.training.cleverbot.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

import de.lineas.training.cleverbot.model.ChatLine;
import de.lineas.training.cleverbot.persistence.ChatLineStore;
import de.lineas.training.cleverbot.persistence.ChatLineStorer;

import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class CleverBotBean {
	private String userLine = "";
	
	@ManagedProperty(value = "#{chatLineStore}")
	private ChatLineStorer chatLineStore;
	
	private static ChatterBotFactory factory = new ChatterBotFactory();
	
	private static ChatterBot bot1;
	private ChatterBotSession bot1session;
	
	
	public CleverBotBean() {
		try {
			bot1 = factory.create(ChatterBotType.CLEVERBOT);
			bot1session = bot1.createSession();
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
	
	public String sendLine() {
		System.out.println("UserLine: " + userLine);
		
		try {
			if (!userLine.isEmpty()) {
				String response = bot1session.think(userLine);
				
				ChatLine myLine = new ChatLine("Me", userLine);
				ChatLine botLine = new ChatLine("CleverBot", response);
				
				chatLineStore.addChatLine(myLine);
				chatLineStore.addChatLine(botLine);
				
				System.out.println(myLine);
				System.out.println(botLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}

	public String getUserLine() {
		return userLine;
	}

	public void setUserLine(String userLine) {
		this.userLine = userLine;
	}

	public List<ChatLine> getChatLines() {
		return chatLineStore.getChatLines();
	}

	public void setChatLineStore(ChatLineStorer chatLineStore) {
		this.chatLineStore = chatLineStore;
	}

	public ChatLineStorer getChatLineStore() {
		return chatLineStore;
	}

	
}
