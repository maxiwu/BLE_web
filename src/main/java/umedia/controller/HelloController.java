package umedia.controller;

import java.util.Random;

import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import umedia.stomp.DataMessage;
import umedia.stomp.Greeting;
import umedia.stomp.HelloMessage;

@Controller
@RequestMapping(value = "/")
public class HelloController {
	@RequestMapping(method = RequestMethod.GET)
	public String sayHello(ModelMap model) {
		model.addAttribute("greeting", "Hello World from Spring 4 MVC");
		return "welcome";
	}

	@RequestMapping(value = "/helloagain", method = RequestMethod.GET)
	public String sayHelloAgain(ModelMap model) {
		model.addAttribute("greeting", "Hello World Again, from Spring 4 MVC");
		return "welcome";
	}
	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public DataMessage greeting(HelloMessage hello) throws InterruptedException
	{
		Thread.sleep(3000); // simulated delay
        //return new Greeting("hi return");
		return new DataMessage();
	}
	
	
	
/*	@Scheduled(fixedDelay = 500)
    public void sendDataUpdates() {

        this.messagingTemplate.convertAndSend(
            "/data", new Random().nextInt(100));

    }*/
}
