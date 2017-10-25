package io.renren.modules.signalling.consumer;

import org.springframework.stereotype.Component;
import javax.jms.*;

/**
 * <dl>
 * <dt>SmsSignallingMessageListener</dt>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2017-10-24</dd>
 * </dl>
 *
 * @author Administrator
 */
@Component("smsSignallingMessageListener")
public class SmsSignallingMessageListener implements MessageListener {
    @Override
    public void onMessage(final Message message) {
        try {
            System.out.println(Thread.currentThread().getName() + "接收数据=" + ((ObjectMessage)message).getObject());

            message.acknowledge();
            //手动确认处理完成，如果出现异常，那么只要抛出runtimeException，mq会自动重发。
            /*
            if(ack){
//                message.acknowledge();
            }else{
//                throw new RuntimeException("重发吧");
            }
            */
//            System.out.println("接收线程"+Thread.currentThread().getName()+"接收消息:"+((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}