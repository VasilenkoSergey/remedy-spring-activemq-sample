package io.vasilenko.remedy.spring.activemq.sample.mq;

import com.bmc.thirdparty.org.slf4j.Logger;
import com.bmc.thirdparty.org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MqSender {

    private final Logger log = LoggerFactory.getLogger(MqSender.class);

    @Value("${sender.destination}")
    private String destination;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        log.info("message: {}", message);
        jmsTemplate.convertAndSend(destination, message);
    }
}
