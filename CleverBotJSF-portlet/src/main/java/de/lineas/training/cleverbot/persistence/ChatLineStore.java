package de.lineas.training.cleverbot.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.lineas.training.cleverbot.model.ChatLine;

@ManagedBean
@SessionScoped
public class ChatLineStore implements ChatLineStorer {
	
	private static List<ChatLine> chatLines = new ArrayList<ChatLine> ();

	@Override
	public void addChatLine(ChatLine chatLine) {
		if (chatLine != null) {
			chatLines.add(chatLine);
		}

	}

	@Override
	public List<ChatLine> getChatLines() {
		return chatLines;
	}

}
