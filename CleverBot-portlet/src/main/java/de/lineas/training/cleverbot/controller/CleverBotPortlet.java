package de.lineas.training.cleverbot.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.lineas.training.cleverbot.model.ChatLine;

public class CleverBotPortlet extends MVCPortlet {
	
	private static final List<ChatLine> chatLines 	= new ArrayList<ChatLine>();
	private static final ChatterBotFactory factory 	= new ChatterBotFactory();
	
	private static ChatterBot bot1;
	private static ChatterBotSession bot1Session;
	
	public CleverBotPortlet () {
		super();
		
		initChatterBot();
	}
	

	private void initChatterBot() {
		try {
			bot1 		= factory.create(ChatterBotType.CLEVERBOT);
			bot1Session = bot1.createSession();
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
	}


	public void addChatLine(ActionRequest actionRequest, ActionResponse actionResponse){
		String chatLine = ParamUtil.getString(actionRequest, "line");

		try {
			if (chatLine != null && !chatLine.isEmpty()) {
				String response = bot1Session.think(chatLine);
				
				ChatLine myLine 	= new ChatLine("Me", chatLine);
				ChatLine botLine 	= new ChatLine("CleverBot", response);
				
				chatLines.add(myLine);
				chatLines.add(botLine);
				
				System.out.println(myLine);
				System.out.println(botLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render (RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {
		
		renderRequest.setAttribute("chatLines", chatLines);
		
		super.render(renderRequest, renderResponse);
	}
}
