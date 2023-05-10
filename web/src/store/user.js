import $ from "jquery";
// import router from "@/router";
// import {useRouter} from "vue-router";


export default {
    state:{
        id : "",
        username: "",
        photo: "",
        token: "",
        is_login : false,
        pulling_info : true   //是否正在从服务器拉取信息
    },
    getters:{

    },
    mutations:{//使用这里的函数要使用commit，同步操作
        updateUser(state,user){
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.is_login = user.is_login;
        },
        updateToken(state,token){
            state.token = token
        },
        logout(state){
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.is_login = false;
        },
        updatePulling(state,pulling_info){
            state.pulling_info = pulling_info;
        }
    },
    actions:{//使用这里的函数需要用dispatch，异步操作
        login(context,data) {

            $.ajax({
                url: "http://192.168.1.2:3000/api/user/account/token/",
                type: "post",
                data: {
                    username: data.username,
                    password: data.password,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        localStorage.setItem("jwt_token", resp.token);
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            });
        },
        getinfo(context,data) {
            console.log("getinfo")
            console.log(context.state.token)
            $.ajax({
                url: "http://192.168.1.2:3000/api/user/account/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    if (resp.error_message === "success"){
                        context.commit("updateUser",{
                            ...resp,
                            is_login : true,
                        })
                        data.success(resp);
                    }else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            })
        },
        logout(context) {
            localStorage.removeItem("jwt_token")
            context.commit("logout")
        }
    },
    modules:{

    }
}