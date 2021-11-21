# Ding消息推送-Demo

> - 此demo主要展示ding消息推送功能。
> - 项目结构
    >   - rear-end：后端模块，springboot构建，功能接口功能包括：获取部门列表、获取部门用户、发送ding消息。
>   - front-end：前端模块，react构建，场景功能包括：展示部门列表、展示部门用户、发送ding消息。
>



### 研发环境准备

1. 需要有一个钉钉注册企业，如果没有可以创建：https://oa.dingtalk.com/register_new.htm#/

2. 成为钉钉开发者，参考文档：https://developers.dingtalk.com/document/app/become-a-dingtalk-developer

3. 登录钉钉开放平台后台创建一个H5应用： https://open-dev.dingtalk.com/#/index

4. 配置应用

   配置开发管理，参考文档：https://developers.dingtalk.com/document/app/configure-orgapp

    - **此处配置“应用首页地址”需公网地址，若无公网ip，可使用钉钉内网穿透工具：**

      https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration

![image-20210706171740868](https://img.alicdn.com/imgextra/i4/O1CN01C9ta8k1L3KzzYEPiH_!!6000000001243-2-tps-953-517.png)



配置相关权限：https://developers.dingtalk.com/document/app/address-book-permissions

本demo使用接口相关权限：

"专属钉钉发DING权限"、"通讯录部门信息读权限"、“通讯录部门成员读权限”

![image-20210706172027870](https://img.alicdn.com/imgextra/i3/O1CN016WCr6428wDdBhkWi6_!!6000000007996-2-tps-1358-571.png)



### 运行

**下载本项目至本地**

```shell
git clone https://github.com/open-dingtalk/h5app-ding-message-push-demo.git
```

### 获取相应参数

获取到以下参数，修改后端application.yaml

```yaml
app:
  app_key: *****
  app_secret: *****
  agent_id: *****
  corp_id: *****
```

参数获取方法：登录开发者后台

1. 获取corpId：https://open-dev.dingtalk.com/#/index
2. 进入应用开发-企业内部开发-点击进入应用-基础信息-获取appKey、appSecret、agentId

### 修改前端页面

**打开项目，命令行中执行以下命令，编译打包生成build文件**

```shell
cd front-end
npm install
npm run build
```

**将打包好的静态资源文件放入后端**

![image-20210706173224172](https://img.alicdn.com/imgextra/i2/O1CN01QLp1Qw1TCVrPddfjZ_!!6000000002346-2-tps-322-521.png)

### 启动项目

- 启动springboot
- 移动端钉钉点击工作台，找到应用，进入应用

[comment]: <> (### 页面展示)

[comment]: <> (部门列表)

[comment]: <> (![图1]&#40;/Users/nannanness/Desktop/11.png&#41;)

[comment]: <> (部门用户列表)

[comment]: <> (![图2]&#40;/Users/nannanness/Desktop/33.png&#41;)

[comment]: <> (消息输入框)

[comment]: <> (![图3]&#40;/Users/nannanness/Desktop/44.png&#41;)

[comment]: <> (发送成功提示)

[comment]: <> (![图4]&#40;/Users/nannanness/Desktop/66.png&#41;)

[comment]: <> (服务窗收到ding消息)

[comment]: <> (![图5]&#40;/Users/nannanness/Desktop/77.png&#41;)

### **参考文档**

1. 获取企业内部应用access_token，文档链接：https://developers.dingtalk.com/document/app/obtain-orgapp-token
2. 获取部门列表，文档链接：https://developers.dingtalk.com/document/app/obtain-the-department-list-v2
3. 获取部门用户基础信息，文档链接：https://developers.dingtalk.com/document/app/queries-the-simple-information-of-a-department-user
