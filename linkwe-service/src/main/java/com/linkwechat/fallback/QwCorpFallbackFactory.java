package com.linkwechat.fallback;

import com.linkwechat.common.core.domain.AjaxResult;
import com.linkwechat.domain.wecom.query.WeBaseQuery;
import com.linkwechat.domain.wecom.query.agentdev.WeTransformExternalUserIdQuery;
import com.linkwechat.domain.wecom.query.agentdev.WeTransformUserIdQuery;
import com.linkwechat.domain.wecom.query.agentdev.WeUnionidTransformExternalUserIdQuery;
import com.linkwechat.domain.wecom.vo.agentdev.WeTransformCorpVO;
import com.linkwechat.domain.wecom.vo.agentdev.WeTransformExternalUserIdVO;
import com.linkwechat.domain.wecom.vo.agentdev.WeTransformUserIdVO;
import com.linkwechat.domain.wecom.vo.agentdev.WeUnionidTransformExternalUserIdVO;
import com.linkwechat.fegin.QwCorpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author sxw
 * @description 企微企业接口回调
 * @date 2022/3/29 22:52
 **/
@Component
@Slf4j
public class QwCorpFallbackFactory implements QwCorpClient {
    @Override
    public AjaxResult<WeTransformCorpVO> transformCorpId(WeBaseQuery query) {
        return null;
    }

    @Override
    public AjaxResult<WeTransformUserIdVO> transformUserId(WeTransformUserIdQuery query) {
        return null;
    }

    @Override
    public AjaxResult<WeTransformExternalUserIdVO> transformExternalUserId(WeTransformExternalUserIdQuery query) {
        return null;
    }

    @Override
    public AjaxResult<WeUnionidTransformExternalUserIdVO> unionidTransformExteralUserId(WeUnionidTransformExternalUserIdQuery query) {
        return null;
    }

    @Override
    public AjaxResult removeAllWeAccessToken(String corpId) {
        return null;
    }
}
