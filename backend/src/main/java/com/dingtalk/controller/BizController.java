package com.dingtalk.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkexclusive_1_0.models.SendAppDingResponse;
import com.dingtalk.api.response.OapiUserListsimpleResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.service.BizManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主业务Controller，编写你的代码
 */
@RestController
@RequestMapping("/biz")
public class BizController {

    @Autowired
    BizManager bizManager;

    /**
     * 获取部门列表
     *
     * @param params
     * @return
     */
    @PostMapping("/getDeptList")
    public RpcServiceResult getDeptList(@RequestBody String params) {
        Long deptId = Long.parseLong(params);
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> deptList = bizManager.getDeptList(deptId);
        if (deptList == null) {
            return RpcServiceResult.getFailureResult("-1", "获取部门列表失败");
        }
        if (deptList.isEmpty()) {
            return RpcServiceResult.getFailureResult("-2", "没有更多子部门");
        }
        return RpcServiceResult.getSuccessResult(deptList);
    }

    /**
     * 获取部门用户
     *
     * @param params
     * @return
     */
    @PostMapping("/getDeptUser")
    public RpcServiceResult getDeptUser(@RequestBody String params) {
        Long deptId = Long.parseLong(params);
        OapiUserListsimpleResponse.PageResult deptUserResult = bizManager.getDeptUser(deptId);
        if (deptUserResult == null) {
            return RpcServiceResult.getFailureResult("-1", "获取部门用户列表失败");
        }
        return RpcServiceResult.getSuccessResult(deptUserResult.getList());
    }

    /**
     * 发送ding消息
     *
     * @param userId
     * @param message
     * @return
     */
    @PostMapping("/sendDing")
    public RpcServiceResult sendDing(@RequestParam String userId, @RequestParam String message) {
        try {
            bizManager.pushDingMsg(userId, message);
        } catch (Exception e) {
            e.printStackTrace();
            return RpcServiceResult.getFailureResult("-1", "发送ding失败");
        }
        return RpcServiceResult.getSuccessResult(null);
    }
}
