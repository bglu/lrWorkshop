package de.lineas.training.cleverbot.persistence;

import java.util.List;

import de.lineas.training.cleverbot.model.ChatLine;

public interface ChatLineStorer {

	void addChatLine (ChatLine chatLine);
	List<ChatLine> getChatLines ();
	
}
