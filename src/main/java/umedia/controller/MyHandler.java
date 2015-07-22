package umedia.controller;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException
	{
		//simplest without STOMP
		Thread.sleep(3000); // simulated delay
        TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!");
        try {
			session.sendMessage(msg);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
}
