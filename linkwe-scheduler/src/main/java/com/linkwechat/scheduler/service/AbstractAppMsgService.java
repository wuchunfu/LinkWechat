package com.linkwechat.scheduler.service;

import com.alibaba.fastjson.JSONObject;
import com.linkwechat.domain.WeCorpAccount;
import com.linkwechat.domain.msg.QwAppMsgBody;
import com.linkwechat.domain.wecom.query.msg.WeAppMsgQuery;
import com.linkwechat.domain.wecom.vo.msg.WeAppMsgVo;
import com.linkwechat.fegin.QwAppMsgClient;
import com.linkwechat.service.IWeCorpAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sxw
 * @description 消息发送抽象模板类
 * @date 2022/4/14 19:03
 **/
@Service
@Slf4j
public abstract class AbstractAppMsgService {

    @Resource
    private QwAppMsgClient qwAppMsgClient;

    @Autowired
    private IWeCorpAccountService weCorpAccountService;

    /**
     * 消息结果处理
     */
    protected void callBackResult(WeAppMsgVo appMsgVo) {

    }

    /**
     * 具体业务处理消息体
     *
     * @param appMsgBody
     * @return
     */
    protected abstract WeAppMsgQuery getWeAppMsg(QwAppMsgBody appMsgBody);


    public void sendAppMsg(QwAppMsgBody appMsgBody) {
        WeCorpAccount corpAccountVo = weCorpAccountService.getCorpAccountByCorpId(appMsgBody.getCorpId());
        WeAppMsgQuery weAppMsg = getWeAppMsg(appMsgBody);
        weAppMsg.setAgentid(corpAccountVo.getAgentId());
        WeAppMsgVo data = qwAppMsgClient.sendAppMsg(weAppMsg).getData();
        callBackResult(data);
    }


    public void sendAgentMsg(QwAppMsgBody appMsgBody) {
        try {
            WeAppMsgQuery weAppMsg = getWeAppMsg(appMsgBody);
            WeAppMsgVo data = qwAppMsgClient.sendAppMsg(weAppMsg).getData();
            callBackResult(data);
        } catch (Exception e) {
            log.error("sendAgentMsg 执行异常: query:{}",JSONObject.toJSONString(appMsgBody),e);
            WeAppMsgVo errorBody = new WeAppMsgVo();
            errorBody.setErrMsg(e.getMessage());
            errorBody.setErrCode(-1);
            callBackResult(errorBody);
        }

    }
}
