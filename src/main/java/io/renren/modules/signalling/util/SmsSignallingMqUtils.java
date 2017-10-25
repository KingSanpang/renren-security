package io.renren.modules.signalling.util;

import io.renren.modules.sms.dto.SignallingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * <dl>
 * <dt>SmsSignallingMqUtils</dt>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>CreateDate: 2017-10-24</dd>
 * </dl>
 *
 * @author Administrator
 */

@Component("smsSignallingMqUtils")
public class SmsSignallingMqUtils {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination mQueue2;

    public void sendMessage(final SignallingDto signalling){
        jmsTemplate.send(mQueue2, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(signalling);
            }
        });
    }
}