<template>
  <div>
    <div>user昵称: {{bot_name}}</div>
    <div>user年龄: {{bot_age}}</div>
  </div>
  <route-view></route-view>
</template>

<script>
import $ from 'jquery'
import {ref} from "vue";

export default {
  name:"App",
  setup:()=>{
    let bot_name = ref("");
    let bot_age = ref("");

    $.ajax({
      url:"http://127.0.0.1:3000/user/userto/",
      type:"get",
      success:resp =>{
        bot_name.value = resp.name;
        bot_age.value = resp.age;
      }
    });

    return{
      bot_name,
      bot_age
    }
  }
}
</script>

<style>
body {
  background-image: url("../src/assets/backgrand.jpg");
  background-size: cover;
}
</style>
