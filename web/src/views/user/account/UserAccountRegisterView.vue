<template>
  <ContentField>
    <div class="row justify-content-md-center">
      <div class="col-3">
        <form @submit.prevent = "register">
          <div class="mb-3">
            <label for="username" class="form-label">用户名：</label>
            <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">密 码：</label>
            <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
          </div>
          <div class="mb-3">
            <label for="confirmedPassword" class="form-label">确认密码：</label>
            <input v-model="confirmedPassword" type="password" class="form-control" id="confirmedPassword" placeholder="请再次输入密码">
          </div>
          <div class="message">{{ message }}</div>
          <button type="submit" class="btn btn-primary">提交</button>
        </form>
      </div>
    </div>
  </ContentField>
</template>

<script>
import ContentField from "@/components/ContentField";
import { ref } from 'vue';
import router from "@/router";
import $ from 'jquery';

export default {
  components:{
    ContentField
  },
  setup() {
    let username = ref('');
    let password = ref('');
    let confirmedPassword = ref('');
    let message = ref('');

    const register = () =>{
      $.ajax({
        url:"http://localhost:3000/api/user/account/register/",
        type:"post",
        data:{
          username : username.value,
          password : password.value,
          confirmedPassword : confirmedPassword.value,
        },
        success(resp){
          if (resp.message === "success"){
            router.push({name: "user_account_login"})
          }else {
            message.value = resp.message;
          }
        }
      });
    }

    return{
      username,
      password,
      confirmedPassword,
      message,
      register
    }
  }
}
</script>

<style scoped>
button {
  width: 100%;
}

div.message {
  color: red;
}
</style>