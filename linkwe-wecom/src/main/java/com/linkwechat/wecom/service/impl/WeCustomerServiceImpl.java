package com.linkwechat.wecom.service.impl;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.linkwechat.common.constant.WeConstans;
import com.linkwechat.common.utils.DateUtils;
import com.linkwechat.wecom.client.WeCustomerClient;
import com.linkwechat.wecom.client.WeUserClient;
import com.linkwechat.wecom.domain.dto.WeCustomerDto;
import com.linkwechat.wecom.domain.dto.WeFollowUserDto;
import com.linkwechat.wecom.domain.dto.WeUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkwechat.wecom.mapper.WeCustomerMapper;
import com.linkwechat.wecom.domain.WeCustomer;
import com.linkwechat.wecom.service.IWeCustomerService;

/**
 * 企业微信客户Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-13
 */
@Service
public class WeCustomerServiceImpl implements IWeCustomerService 
{
    @Autowired
    private WeCustomerMapper weCustomerMapper;


    @Autowired
    private WeCustomerClient weFollowUserClient;


    @Autowired
    private WeUserClient weUserClient;

    /**
     * 查询企业微信客户
     * 
     * @param id 企业微信客户ID
     * @return 企业微信客户
     */
    @Override
    public WeCustomer selectWeCustomerById(Long id)
    {
        return weCustomerMapper.selectWeCustomerById(id);
    }

    /**
     * 查询企业微信客户列表
     * 
     * @param weCustomer 企业微信客户
     * @return 企业微信客户
     */
    @Override
    public List<WeCustomer> selectWeCustomerList(WeCustomer weCustomer)
    {
        return weCustomerMapper.selectWeCustomerList(weCustomer);
    }

    /**
     * 新增企业微信客户
     * 
     * @param weCustomer 企业微信客户
     * @return 结果
     */
    @Override
    public int insertWeCustomer(WeCustomer weCustomer)
    {
        weCustomer.setCreateTime(DateUtils.getNowDate());
        return weCustomerMapper.insertWeCustomer(weCustomer);
    }

    /**
     * 修改企业微信客户
     * 
     * @param weCustomer 企业微信客户
     * @return 结果
     */
    @Override
    public int updateWeCustomer(WeCustomer weCustomer)
    {
        return weCustomerMapper.updateWeCustomer(weCustomer);
    }

    /**
     * 批量删除企业微信客户
     * 
     * @param ids 需要删除的企业微信客户ID
     * @return 结果
     */
    @Override
    public int deleteWeCustomerByIds(Long[] ids)
    {
        return weCustomerMapper.deleteWeCustomerByIds(ids);
    }

    /**
     * 删除企业微信客户信息
     * 
     * @param id 企业微信客户ID
     * @return 结果
     */
    @Override
    public int deleteWeCustomerById(Long id)
    {
        return weCustomerMapper.deleteWeCustomerById(id);
    }


    /**
     * 客户同步接口
     * @return
     */
    @Override
    public void synchWeCustomer() {

        WeFollowUserDto weFollowUserDto = weFollowUserClient.getFollowUserList();

        if(WeConstans.WE_SUCCESS_CODE.equals(weFollowUserDto.getErrcode())
        && ArrayUtil.isNotEmpty(weFollowUserDto.getFollow_user())){

            Arrays.asList(weFollowUserDto.getFollow_user())
                    .stream().forEach(k->{

                //获取指定联系人对应的客户
                WeCustomerDto externalUserid
                        = weFollowUserClient.list(k);
                if(WeConstans.WE_SUCCESS_CODE.equals(externalUserid.getErrcode())
                && ArrayUtil.isNotEmpty(externalUserid.getExternal_userid())){

                    Arrays.asList(externalUserid.getExternal_userid()).forEach(v->{

                        //获取指定客户的详情
                        WeCustomerDto externalContact = weFollowUserClient.get(v);

                        if(WeConstans.WE_SUCCESS_CODE.equals(externalContact.getErrcode())){

                            //分装成需要入库的数据格式
                            WeCustomerDto.ExternalContact
                                    weExternalContact = externalContact.getExternal_contact();
                            if(null != weExternalContact){


                                WeCustomer.builder()
                                         .externalUserid(weExternalContact.getExternal_userid())
                                         .name(weExternalContact.getName())
                                         .avatar(weExternalContact.getAvatar())
                                         .type(weExternalContact.getType())
                                         .gender(weExternalContact.getGender())
                                         .unionid(weExternalContact.getUnionid())
//               自定义                   .birthday()
//                添加人的id              .userId(weExternalContact.getExternal_userid())
//                 添加人的名称            .userName()
//                添加人所在部门           .departmentName()
//                 添加人员描述           .description()
//                       备注号码                 .remarkMobiles()
                                        .corpName(weExternalContact.getCorp_name())
                                        .corpFullName(weExternalContact.getCorp_full_name())
                                        .position(weExternalContact.getPosition());
//              客户来源                 .addWay()

                                List<WeFollowUserDto> followUsers = externalContact.getFollow_user();
                                if(CollectionUtil.isNotEmpty(followUsers)){
                                    WeFollowUserDto followUser = followUsers.get(0);
                                    followUser.getUserid();
                                    followUser.getDescription();
                                    followUser.getRemark_mobiles();
                                    followUser.getAdd_way();

                                    WeUserDto weUserDto
                                            = weUserClient.getUserByUserId(followUser.getUserid());
                                    if(WeConstans.WE_SUCCESS_CODE.equals(weUserDto.getErrcode())){
                                        weUserDto.getName();

                                    }



                                }


                            }




                        }
                    });

                }

            });


        }

    }




}
