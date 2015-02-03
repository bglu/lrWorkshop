package de.lineas.training.cleverbot.model;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;




public class ChatLineTest {
	
	@Test
	public void testSpeakerAndLine () {
		String speaker 		= "Me";
		String line 		= "Hello World...";
		ChatLine chatLine 	= new ChatLine(speaker, line);
		
		Assert.assertEquals(speaker, chatLine.getSpeaker());
		Assert.assertEquals(line, chatLine.getLine());
	}
	

	@Test
	public void testToString () {
		String speaker 		= "Me";
		String line 		= "Hello World...";
		
		ChatLine chatLine 	= new ChatLine(speaker, line);
		
		String expected = "<span class='speaker-human'>Me</span>: Hello World...";
		
		Assert.assertEquals(expected, chatLine.toString());
		
		String expected_bot = "<span class='speaker-bot'>CleverBot</span>: Hello World...";
		chatLine 	= new ChatLine("CleverBot", line);
		Assert.assertEquals(expected_bot,  chatLine.toString());
		
	}
	
	

}
