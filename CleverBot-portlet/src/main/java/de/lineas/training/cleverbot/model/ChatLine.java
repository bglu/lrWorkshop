package de.lineas.training.cleverbot.model;

/**
 * Immutable class representing one line of chat dialog.
 * @author Benjamin.Glusa
 *
 */
public class ChatLine {
	
	private final String speaker;
	private final String line;
	
	private final static String CSS_CLASS_BOT 	= "speaker-bot";
	private final static String CSS_CLASS_HUMAN = "speaker-human";
	
	public ChatLine (String speaker, String line) {
		this.speaker 	= speaker;
		this.line 		= line;
		
	}
	
	@Override
	public String toString() {
		String cssClass = speaker.equals("CleverBot") ? CSS_CLASS_BOT : CSS_CLASS_HUMAN;
		return "<span class='" + cssClass + "'>" + speaker + "</span>: " + line;
	}


	public String getSpeaker() {
		return speaker;
	}


	public String getLine() {
		return line;
	}
	

}
