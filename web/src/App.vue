<template>
<!--  <div>-->
<!--    <div>user昵称: {{bot_name}}</div>-->
<!--    <div>user年龄: {{bot_age}}</div>-->
<!--  </div>-->
  <NavBar />
  <route-view> </route-view>
</template>

<script>
// import $ from 'jquery'
// import {ref} from "vue";
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
// import $ from 'jquery'


export default {
  components:{
    NavBar
  },
  setup(){

  }
}
</script>

<style>
body {
  background-image: url("../src/assets/backgrand.png");
  background-size: cover;
}
</style>
