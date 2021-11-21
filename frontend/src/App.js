import axios from "axios"
import React from "react"
import "./App.css"
import "antd/dist/antd.min.css"
import {Button, message, Input, Radio, Space} from "antd"
import {login} from "./login.js";
// import * as dd from "dingtalk-jsapi"

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //内网穿透工具介绍:
            // https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration?pnamespace=app
            domain: "",
            corpId: '',
            authCode: '',
            userId: '',
            showType: 0,
            userName: '',
            deptList: [],
            targetDept: "",
            userList: [],
            targetUser: "",
            dingMsg: "",
        }
    }

    render() {

        // 1为根部门
        this.chooseDept(1);
        return (
            // 主模块
            <div className="App">
                {this.state.showType === 0 && (
                    <div>
                        <h2>发送ding消息</h2>
                        <Radio.Group
                            onChange={(e) => this.addDept(e)}
                            value={this.state.targetDept}
                        >
                            <Space direction="vertical">
                                {this.state.deptList.map((item, i) => (
                                    <Radio value={item.deptId} key={i}>
                                        {item.name}
                                    </Radio>
                                ))}
                            </Space>
                        </Radio.Group>
                        <br />
                        <br />
                        <Button type="primary" onClick={() => this.chooseUser()}>
                            选中部门
                        </Button>
                        <br />

                    </div>
                )}
                {this.state.showType === 1 && (
                    <div>
                        <h2>发送ding消息</h2>
                        <Radio.Group
                            onChange={(e) => this.addUser(e)}
                            value={this.state.targetUser}
                        >
                            <Space direction="vertical">
                                {this.state.userList.map((item, i) => (
                                    <Radio value={item.userid} key={i}>
                                        {item.name}
                                    </Radio>
                                ))}
                            </Space>
                        </Radio.Group>
                        <br />
                        <br />
                        <Button type="primary" onClick={() => this.showMsgInput()}>
                            选中用户
                        </Button>
                        <br />
                    </div>
                )}
                {this.state.showType === 2 && (
                    <div>
                        <h2>发送ding消息</h2>
                        <Input onChange={(e)=>this.inputMsg(e)} placeholder="ding消息" />
                        <br />
                        <br />
                        <Button type="primary" onClick={() => this.pushDing()}>
                            发送ding消息
                        </Button>
                        <br />
                    </div>
                )}
            </div>
        );
    }
    inputMsg(e){
        this.setState({ dingMsg: e.target.value })
    }
    addDept(e){
        this.setState({ targetDept: e.target.value })
    }
    addUser(e){
        this.setState({ targetUser: e.target.value })
    }
    chooseDept(deptId) {
        axios
            .post(this.state.domain + "/biz/getDeptList", JSON.stringify(deptId), {
                headers: {"Content-Type": "application/json"},
            })
            .then((res) => {
                if (res.data.success) {
                    this.setState({
                        deptList: res.data.data
                    })
                } else {
                    message.error(res.data.errorMsg)
                }
            })
            .catch((error) => {
                alert("chooseDept err, " + JSON.stringify(error))
            })
    }
    chooseUser() {
        axios
            .post(this.state.domain + "/biz/getDeptUser", JSON.stringify(this.state.targetDept), {
                headers: {"Content-Type": "application/json"},
            })
            .then((res) => {
                if (res.data.success) {
                    this.setState({
                        userList: res.data.data,
                        showType: 1,
                    })
                } else {
                    message.error(res.data.errorMsg)
                }
            })
            .catch((error) => {
                alert("chooseUser err, " + JSON.stringify(error))
            })
    }

    showMsgInput(){
        this.setState({ showType: 2 })
    }

    pushDing() {
        axios
            .post(this.state.domain + "/biz/sendDing?userId=" + this.state.targetUser + "&message=" + this.state.dingMsg )
            .then((res) => {
                if (res.data.success) {
                    message.sccess("发送成功")
                } else {
                    message.error(res.data.errorMsg)
                }
            })
            .catch((error) => {
                alert("pushDing err, " + JSON.stringify(error))
            })
    }

}

export default App;
