<template>
  <ContentField>
    我的Bot
  </ContentField>
</template>

<script>
import ContentField from "@/components/ContentField";
import {useStore} from "vuex";
import $ from "jquery";

export default {
  components:{
    ContentField
  },
  setup() {
    const store =useStore()
    console.log(store.state.user.token)
    // $.ajax({
    //   url: "http://192.168.1.2:3000/user/bot/add/",
    //   type: "post",
    //   data:{
    //     title : "Bot标题",
    //     description: "Bot的描述",
    //     content:"Bot的代码"
    //   },
    //   headers: {
    //     Authorization: "Bearer " + store.state.user.token,
    //   },
    //   success(resp){
    //     console.log(resp)
    //   },
    //   error(resp){
    //     console.log(resp)
    //   }
    // })

    // $.ajax({
    //   url: "http://192.168.1.2:3000/user/bot/remove/",
    //   type: "post",
    //   data:{
    //     bot_id:2,
    //   },
    //   headers: {
    //     Authorization: "Bearer " + store.state.user.token,
    //   },
    //   success(resp){
    //     console.log(resp)
    //   },
    //   error(resp){
    //     console.log(resp)
    //   }
    // })

    // $.ajax({
    //   url: "http://192.168.1.2:3000/user/bot/update/",
    //   type: "post",
    //   data:{
    //     bot_id : 3,
    //     title : "Bot标题1",
    //     description: "Bot的描述1",
    //     content:"Bot的代码1"
    //   },
    //   headers: {
    //     Authorization: "Bearer " + store.state.user.token,
    //   },
    //   success(resp){
    //     console.log(resp)
    //   },
    //   error(resp){
    //     console.log(resp)
    //   }
    // })

    $.ajax({
      url: "http://192.168.1.2:3000/user/bot/getlist/",
      type: "get",
      headers: {
        Authorization: "Bearer " + store.state.user.token,
      },
      success(resp){
        console.log(resp)
      },
      error(resp){
        console.log(resp)
      }
    })

  }
}
</script>

<style scoped>

</style>