package com.lineal.rabbitmq_hello.three;

import com.lineal.rabbitmq_hello.utils.RabbitMqUtils;
import com.lineal.rabbitmq_hello.utils.SleepUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * @description: 消息手动应答消费者01
 * @author: lineal
 * @date: 2023/3/20
 * @version: 1.0
 **/
public class Work04 {

    //队列名称
    private static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C2等待接受消息处理，时间较长");


        DeliverCallback deliverCallback = (s, delivery) -> {

            SleepUtils.sleep(30);
            System.out.println("接受到的消息：" + new String(delivery.getBody(), "utf-8"));
            // 进行手动应答
            /*
            * 1.表示消息的标记 tag
            * 2.是否批量应答 multiple false:不批量应答信道中的消息，只应答当前消息
            * */
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        // 采用手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, a->{
            System.out.println("消费者取消消费接口回调");
        });
    }
}