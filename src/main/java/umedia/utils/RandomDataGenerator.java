package umedia.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import umedia.stomp.DataMessage;

@Component
public class RandomDataGenerator implements
		ApplicationListener<BrokerAvailabilityEvent> {

	private final MessageSendingOperations<String> messagingTemplate;

	@Autowired
	public RandomDataGenerator(
			final MessageSendingOperations<String> messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public void onApplicationEvent(final BrokerAvailabilityEvent event) {
	}

	@Scheduled(fixedDelay = 1000)
	public void sendDataUpdates() {

		this.messagingTemplate.convertAndSend("/topic/data1",
				new DataMessage());

	}

	@Scheduled(fixedDelay = 1000)
	public void sendDataUpdates2() {
		this.messagingTemplate.convertAndSend("/topic/data2",
				new DataMessage());
	}
}