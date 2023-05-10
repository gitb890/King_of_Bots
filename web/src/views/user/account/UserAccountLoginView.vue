<template>
  <ContentField>
    <div class="row justify-content-md-center">
      <div class="col-3">
        <form @submit.prevent = "login">
          <div class="mb-3">
            <label for="username" class="form-label">用户名：</label>
            <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">密 码：</label>
            <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
          </div>
          <div class="error-mes">{{ error_message }}</div>
          <button type="submit" class="btn btn-primary">提交</button>
        </form>
      </div>
    </div>
  </ContentField>
</template>

<script>
import ContentField from "@/components/ContentField";
import { useStore } from 'vuex';
//公共变量
import {ref} from 'vue';
//私有变量
import router from '../../../router/index.js'
// import {useRouter} from 'vue-router'


export default {
  components:{
    ContentField
  },
  setup() {
    const store = useStore();
    let username = ref('');
    let password = ref('');
    let error_message = ref('');

    const jwt_token = localStorage.getItem("jwt_token");

    if (jwt_token) {
      store.commit("updateToken", jwt_token);
      store.dispatch("getinfo", {
        success() {
          router.push({name:'home'});
          store.commit("updatePulling", false)
        },
        error() {
          store.commit("updatePulling", false)
        }
      })
    } else {
      store.commit("updatePulling", false)
    }


    // const router = useRouter()
    const login = () => {
      error_message.value = "";
      store.dispatch("login", {
        username: username.value,
        password: password.value,
        success() {
          store.dispatch("getinfo", {
            success() {
              router.push({name: 'home' });
            }
          })
        },
        error() {
          error_message.value = "用户名或密码错误";
        }
      })
    }

    return {
      username,
      password,
      error_message,
      login,
    }
  }
}
</script>

<style scoped>
button{
  width: 100%;
}
div.error-mes{
  color: red;
}
</style>