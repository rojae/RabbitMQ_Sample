package kr.rojae.rabbitmq.sample2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Docs : https://www.rabbitmq.com/tutorials/tutorial-two-java
 * Command
    java -cp .:amqp-client-5.21.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar NewTask.java First message.
    java -cp .:amqp-client-5.21.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar NewTask.java Second message..
    java -cp .:amqp-client-5.21.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar NewTask.java Third message...
    java -cp .:amqp-client-5.21.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar NewTask.java Fourth message....
    java -cp .:amqp-client-5.21.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar NewTask.java Fifth message.....
 */
public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";
//    private static final String[] ARGV = {
//            "First message.",
//            "Second message..",
//            "Third message...",
//            "Fourth message....",
//            "Fifth message....."
//    };

    public static void main(String[] argv) throws Exception {
        // hard setting
        //argv = ARGV;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            for(String arg : argv){
                channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
                String message = String.join(" ", arg);
                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }

}