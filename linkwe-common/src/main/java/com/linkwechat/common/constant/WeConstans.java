package com.linkwechat.common.constant;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 企业微信相关常量
 * @author: HaoN
 * @create: 2020-08-26 17:01
 **/
public class WeConstans {

    /**
     * 企业微信相关token
     */
    public static final String WE_COMMON_ACCESS_TOKEN = "we_common_access_token";


    /**
     * 获取外部联系人相关 token
     */
    public static final String WE_CONTACT_ACCESS_TOKEN = "we_contact_access_token";


    /**
     * 供应商相关token
     */
    public static final String WE_PROVIDER_ACCESS_TOKEN = "we_provider_access_token";


    /**
     * 企业微信接口返回成功code
     */
    public static final Integer WE_SUCCESS_CODE = 0;


    /**
     * 企业微信端根部门id
     */
    public static final Long WE_ROOT_DEPARMENT_ID = 1L;


    /**
     * 企业微信通讯录用户启用
     */
    public static final Integer WE_USER_START = 1;


    /**
     * 企业微信通讯录用户停用
     */
    public static final Integer WE_USER_STOP = 0;


    /**
     * 同步功能提示语
     */
    public static final String SYNCH_TIP = "后台开始同步数据，请稍后关注进度";


    /**
     * 离职未分配
     */
    public static final Integer LEAVE_NO_ALLOCATE_STATE = 0;


    /**
     * 离职已分配分配
     */
    public static final Integer LEAVE_ALLOCATE_STATE = 1;


    /**
     * 已激活
     */
    public static final Integer WE_USER_IS_ACTIVATE = 1;


    /**
     * 已禁用
     */
    public static final Integer WE_USER_IS_FORBIDDEN = 2;


    /**
     * 已离职
     */
    public static final Integer WE_USER_IS_LEAVE = 6;


    /**
     * 未激活
     */
    public static final Integer WE_USER_IS_NO_ACTIVATE = 4;


    /**
     * 企业微信素材目录根id
     */
    public static final Integer WE_ROOT_CATEGORY_ID = 0;


    /**
     * 单人活码
     */
    public static final Integer SINGLE_EMPLE_CODE_TYPE = 1;


    /**
     * 多人活码
     */
    public static final Integer MANY_EMPLE_CODE_TYPE = 2;


    /**
     * 批量单人活码
     */
    public static final Integer BATCH_SINGLE_EMPLE_CODE_TYPE = 3;


    /**
     * 在小程序中联系场景
     */
    public static final Integer SMALL_ROUTINE_EMPLE_CODE_SCENE = 1;


    /**
     * 通过二维码联系场景
     */
    public static final Integer QR_CODE_EMPLE_CODE_SCENE = 2;

    /**
     * 客户添加时无需经过确认自动成为好友,是
     */
    public static final Boolean IS_JOIN_CONFIR_MFRIENDS = true;

    /**
     * 客户添加时无需经过确认自动成为好友,否
     */
    public static final Boolean NOT_IS_JOIN_CONFIR_MFRIENDS = false;

    /**
     * 批量生成的单人码 活动场景
     */
    public static final String ONE_PERSON_CODE_GENERATED_BATCH="批量生成的单人码";

    /**
     * 微信接口相应端错误字段
     */
    public static final String WE_ERROR_FIELD = "errcode";


    /**
     * 递归
     */
    public static final Integer YES_IS_RECURSION = 0;


    /**
     * 获取所有子部门数据
     */
    public static final Integer DEPARTMENT_SUB_WEUSER = 1;


    /**
     * 获取当前部门
     */
    public static final Integer DEPARTMENT_CURRENT_WEUSER = 0;


    /**
     * 通讯录用户激活
     */
    public static final Integer YES_IS_ACTIVATE = 1;


    /**
     * 通讯录用户未激活
     */
    public static final Integer NO_IS_ACTIVATE = 2;


    /**
     * 不存在外部联系人的关系
     */
    public static final Integer NOT_EXIST_CONTACT = 84061;

    public static final String COMMA = ",";

    public static final String USER_ID = "userid";

    public static final String CURSOR = "cursor";

    public static final String CORPID = "CORP_ID";

    /**
     * 业务id类型1:组织机构id,2:成员id
     */
    public static final Integer USE_SCOP_BUSINESSID_TYPE_USER = 2;
    public static final Integer USE_SCOP_BUSINESSID_TYPE_ORG = 1;

    /**
     * 客户流失通知开关 0:关闭 1:开启
     */
    public static final String DEL_FOLLOW_USER_SWITCH_CLOSE = "0";
    public static final String DEL_FOLLOW_USER_SWITCH_OPEN = "1";
    /**
     * 企微回调事件类型路由
     */
    public final static Map<String, String> eventRoute = new HashMap<String, String>() {
        {
            //成员事件
            put("change_contact", "weEventChangeContactImpl");
            //异步任务完成通知
            put("batch_job_result", "weEventBatchJobResultImpl");
            //外部联系人事件
            put("change_external_contact", "weEventChangeExternalContactImpl");
        }
    };

    //性别，1表示男性，2表示女性
    //表示所在部门是否为上级，0-否，1-是，顺序与Department字段的部门逐一对应
    //激活状态：1=已激活 2=已禁用 4=未激活 已激活代表已激活企业微信或已关注微工作台（原企业号） 5=成员退出
    public static enum corpUserEnum {

        User_SEX_TYPE_MAN(1, "男性"),
        User_SEX_TYPE_WEMAN(2, "女性"),

        IS_DEPARTMENT_SUPERIOR_YES(1, "是"),
        IS_DEPARTMENT_SUPERIOR_NO(0, "否"),

        ACTIVE_STATE_ONE(1, "已激活"),
        ACTIVE_STATE_TWO(2, "已禁用"),
        ACTIVE_STATE_FOUR(4, "未激活"),
        ACTIVE_STATE_FIVE(5, "成员退出");

        private int key;
        private String value;

        /**
         * 构造方法
         *
         * @param key
         * @param value
         */
        corpUserEnum(int key, String value) {
            this.setKey(key);
            this.setValue(value);
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
//
//    public static XContentBuilder getFinanceTextMapping() throws IOException {
//        // 创建 会话文本Mapping
//        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
//                .startObject()
//                .startObject("properties")
//                .startObject("msgid")
//                .field("type", "keyword")
//                .endObject()
//                .startObject("action")
//                .field("type", "keyword")
//                .endObject()
//                .startObject("from")
//                .field("type", "keyword")
//                .endObject()
//                .startObject("tolist")
//                .field("type", "text")
//                .endObject()
//                .startObject("roomid")
//                .field("type", "keyword")
//                .endObject()
//                .startObject("msgtime")
//                .field("type", "long")
//                .endObject()
//                .startObject("msgtype")
//                .field("type", "keyword")
//                .endObject()
//                .startObject("content")
//                .field("type", "text")
//                .endObject()
//                .endObject()
//                .endObject();
//        return xContentBuilder;
//    }
}
