package com.linkwechat.scheduler.listener;

import com.alibaba.fastjson.JSONObject;
import com.linkwechat.common.enums.QwAppMsgBusinessTypeEnum;
import com.linkwechat.common.utils.spring.SpringUtils;
import com.linkwechat.domain.msg.QwAppMsgBody;
import com.linkwechat.scheduler.service.AbstractAppMsgService;
import com.linkwechat.service.IWeGroupService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author sxw
 * @description 企微应用消息监听
 * @date 2022/4/3 15:39
 **/
@Slf4j
@Component
public class QwAppMsgListener {

    @RabbitHandler
    @RabbitListener(queues = "${wecom.mq.queue.app-msg:Qu_AppMsg}")
    public void subscribe(String msg, Channel channel, Message message) {
        try {
            log.info("应用通知消息监听：msg:{}",msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            QwAppMsgBody appMsgBody = JSONObject.parseObject(msg, QwAppMsgBody.class);
            QwAppMsgBusinessTypeEnum qwAppMsgBusinessTypeEnum = QwAppMsgBusinessTypeEnum.parseEnum(appMsgBody.getBusinessType());
            if(Objects.nonNull(qwAppMsgBusinessTypeEnum)){
                switch (qwAppMsgBusinessTypeEnum){
                    case AGENT:
                        SpringUtils.getBean(qwAppMsgBusinessTypeEnum.getBeanName(), AbstractAppMsgService.class).sendAgentMsg(appMsgBody);
                        break;
                    default: //默认通用发送
                        SpringUtils.getBean(qwAppMsgBusinessTypeEnum.getBeanName(), AbstractAppMsgService.class).sendAppMsg(appMsgBody);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("应用通知消息监听-消息处理失败 msg:{},error:{}",msg,e);
        }
    }
}
