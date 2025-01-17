package com.linkwechat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linkwechat.common.exception.wecom.WeComException;
import com.linkwechat.common.utils.StringUtils;
import com.linkwechat.domain.WeMsgTlp;
import com.linkwechat.domain.material.entity.WeMaterial;
import com.linkwechat.domain.msgtlp.dto.WeMsgTlpDto;
import com.linkwechat.domain.msgtlp.entity.WeTlpMaterial;
import com.linkwechat.domain.msgtlp.query.WeMsgTlpAddQuery;
import com.linkwechat.domain.msgtlp.query.WeMsgTlpQuery;
import com.linkwechat.domain.msgtlp.vo.WeMsgTlpVo;
import com.linkwechat.mapper.WeMaterialMapper;
import com.linkwechat.mapper.WeMsgTlpMapper;
import com.linkwechat.mapper.WeTlpMaterialMapper;
import com.linkwechat.service.IWeMsgTlpAttachmentsService;
import com.linkwechat.service.IWeMsgTlpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 欢迎语模板表(WeMsgTlp)
 *
 * @author danmo
 * @since 2022-03-28 10:21:24
 */
@Service
@Slf4j
public class WeMsgTlpServiceImpl extends ServiceImpl<WeMsgTlpMapper, WeMsgTlp> implements IWeMsgTlpService {

    @Resource
    private IWeMsgTlpAttachmentsService weMsgTlpAttachmentsService;

//    @Resource
//    private WeMsgTlpMapper weMsgTlpMapper;

    @Resource
    private WeMaterialMapper weMaterialMapper;

    @Resource
    private WeTlpMaterialMapper weTlpMaterialMapper;


    @Transactional(rollbackFor = {WeComException.class, Exception.class})
    @Override
    public void addOrUpdate(WeMsgTlpDto weMsgTlpDto) {
        if (ObjectUtil.isNotEmpty(weMsgTlpDto.getId())) {
            weTlpMaterialMapper.delete(new LambdaQueryWrapper<WeTlpMaterial>().eq(WeTlpMaterial::getTlpId, weMsgTlpDto.getId()));
        }
        WeMsgTlp weMsgTlp = new WeMsgTlp();
        BeanUtil.copyProperties(weMsgTlpDto, weMsgTlp);
        saveOrUpdate(weMsgTlp);
        if (CollectionUtil.isNotEmpty(weMsgTlpDto.getUserIdList())) {
            weMsgTlp.setUserIds(String.join(",", weMsgTlpDto.getUserIds()));
        }
        if (CollectionUtil.isNotEmpty(weMsgTlpDto.getUserNameList())) {
            weMsgTlp.setUserNames(String.join(",", weMsgTlpDto.getUserNames()));
        }
        if (ObjectUtil.isNotEmpty(weMsgTlpDto.getId())) {
            weMsgTlpAttachmentsService.addMsgTlpAttachments(weMsgTlp.getId(), weMsgTlpDto.getAttachmentList());
        } else {
            weMsgTlpAttachmentsService.updateMsgTlpAttachments(weMsgTlp.getId(), weMsgTlpDto.getAttachmentList());
        }
        Long id = weMsgTlp.getId();
        log.info("模板修改删除id:{}", id);
        List<WeTlpMaterial> weTlpMaterialList = weMsgTlpDto.getWeTlpMaterialList();
        weTlpMaterialList.forEach(weTlpMaterial -> {
            weTlpMaterial.setTlpId(id);
            weTlpMaterialMapper.insert(weTlpMaterial);
        });
    }

    @Transactional(rollbackFor = {WeComException.class, Exception.class})
    @Override
    public void addMsgTlp(WeMsgTlp weMsgTlp) {
        throw new WeComException("接口废弃");
    }

    @Transactional(rollbackFor = {WeComException.class, Exception.class})
    @Override
    public void updateMsgTlp(WeMsgTlpAddQuery query) {
        WeMsgTlp weMsgTlp = new WeMsgTlp();
        weMsgTlp.setId(query.getId());
        weMsgTlp.setTplType(query.getTplType());
        if (CollectionUtil.isNotEmpty(query.getUserIds())) {
            weMsgTlp.setUserIds(String.join(",", query.getUserIds()));
        }
        if (CollectionUtil.isNotEmpty(query.getUserNames())) {
            weMsgTlp.setUserNames(String.join(",", query.getUserNames()));
        }
        if (updateById(weMsgTlp)) {
            weMsgTlpAttachmentsService.updateMsgTlpAttachments(weMsgTlp.getId(), query.getAttachments());
        }
    }

    @Transactional(rollbackFor = {WeComException.class, Exception.class})
    @Override
    public void delMsgTlp(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new WeComException("模板ID不能为空");
        }
        update(Wrappers.lambdaUpdate(WeMsgTlp.class).set(WeMsgTlp::getDelFlag, 1).in(WeMsgTlp::getId, ids));
        log.info("模板删除ids:{}", ids.toString());
        weTlpMaterialMapper.delete(new LambdaQueryWrapper<WeTlpMaterial>().in(WeTlpMaterial::getTlpId, ids));
    }

    @Override
    public WeMsgTlpVo getInfo(Long id) {
        return null;
    }

    @Override
    public List<WeMsgTlpVo> getList(WeMsgTlpQuery query) {
        List<String> userIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(query.getUserId())) {
            String[] split = query.getUserId().split(",");
            userIds = Arrays.asList(split);
        }
        List<WeMsgTlpVo> list = this.baseMapper.getList(query, userIds);
        return list;
    }

    @Override
    public List<WeMaterial> preview(Long tlpId) {
        return weMaterialMapper.getWeMaterialListByTlpId(tlpId);
    }

    @Override
    @Transactional
    public void updateCategory(WeMsgTlpQuery query) {
        List<Long> ids = query.getIds();
        Long categoryIdNew = query.getCategoryIdNew();
        update(Wrappers.lambdaUpdate(WeMsgTlp.class).set(WeMsgTlp::getCategoryId, categoryIdNew).in(WeMsgTlp::getId, ids));
    }

}
