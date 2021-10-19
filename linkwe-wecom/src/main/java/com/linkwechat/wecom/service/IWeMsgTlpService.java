package com.linkwechat.wecom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linkwechat.wecom.domain.WeMsgTlp;

/**
 * 欢迎语模板Service接口
 * 
 * @author ruoyi
 * @date 2020-10-04
 */
public interface IWeMsgTlpService extends IService<WeMsgTlp>
{


    /**
     * 新增欢迎语模板
     * 
     * @param weMsgTlp 欢迎语模板
     * @return 结果
     */
    void addorUpdate(WeMsgTlp weMsgTlp);


}
