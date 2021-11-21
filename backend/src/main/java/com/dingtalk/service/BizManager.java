package com.dingtalk.service;

import com.aliyun.dingboot.common.token.ITokenManager;
import com.aliyun.dingtalkexclusive_1_0.models.SendAppDingHeaders;
import com.aliyun.dingtalkexclusive_1_0.models.SendAppDingRequest;
import com.aliyun.dingtalkexclusive_1_0.models.SendAppDingResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserListsimpleRequest;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.request.OapiV2UserGetuserinfoRequest;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiUserListsimpleResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.dingtalk.config.AppConfig;
import com.dingtalk.constant.UrlConstant;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主业务service，编写你的代码
 */
@Slf4j(topic = "bizManager")
@Service
public class BizManager {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ITokenManager tokenManager;

    /**
     * 获取部门列表
     *
     * @param deptId 部门id
     * @return deptList 部门列表
     */
    public List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList(Long deptId){
        try {
            // 获取access_token
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());

            DingTalkClient client = new DefaultDingTalkClient(UrlConstant.DEPT_LIST_SUB);
            OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
            req.setDeptId(deptId);
            req.setLanguage("zh_CN");
            OapiV2DepartmentListsubResponse rsp = client.execute(req, accessToken);
            log.info("getDeptList rsp body:{}", rsp.getBody());
            if (rsp.getErrcode() == 0) {
                return rsp.getResult();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取部门用户信息
     *
     * @param deptId 部门id
     * @return 部门用户列表
     */
    public OapiUserListsimpleResponse.PageResult getDeptUser(Long deptId){
        try {
            // 获取access_token
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());

            DingTalkClient client = new DefaultDingTalkClient(UrlConstant.USER_LIST_SIMPLE);
            OapiUserListsimpleRequest req = new OapiUserListsimpleRequest();
            req.setDeptId(deptId);
            req.setCursor(0L);
            req.setSize(10L);
            req.setOrderField("modify_desc");
            req.setContainAccessLimit(false);
            req.setLanguage("zh_CN");
            OapiUserListsimpleResponse rsp = client.execute(req, accessToken);
            log.info("getDeptUser rsp body:{}", rsp.getBody());
            if (rsp.getErrcode() == 0) {
                return rsp.getResult();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送ding
     *
     * @param userId
     * @param msg ding消息
     * @return 部门用户列表
     */
    public SendAppDingResponse pushDingMsg(String userId, String msg) throws Exception {
        // 获取access_token
        String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());

        com.aliyun.dingtalkexclusive_1_0.Client client = createClient();
        SendAppDingHeaders sendAppDingHeaders = new SendAppDingHeaders();
        sendAppDingHeaders.xAcsDingtalkAccessToken = accessToken;
        SendAppDingRequest sendAppDingRequest = new SendAppDingRequest()
                .setUserids(java.util.Arrays.asList(userId))
                .setContent(msg);
        try {
            SendAppDingResponse sendAppDingResponse = client.sendAppDingWithOptions(sendAppDingRequest, sendAppDingHeaders, new RuntimeOptions());
            log.info("pushDingMsg rsp header:{}", sendAppDingResponse.getHeaders());
            return sendAppDingResponse;
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("pushDingMsg err, code:{} msg:{}", err.code, err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("pushDingMsg _err, code:{} msg:{}", err.code, err.message);
            }

        }
        return null;
    }


    /**
     * 使用 Token 初始化账号Client
     * @return Client
     * @throws Exception
     */
    public com.aliyun.dingtalkexclusive_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkexclusive_1_0.Client(config);
    }
}
