<template>
  <nav class="navbar navbar-expand-lg bg-dark navbar-dark">
    <div class="container">
      <router-link :to="{name:'home'}" class = "navbar-brand">King of Bots</router-link>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" :to="{name:'pk_index'}">对战</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{name:'record_index'}">对局列表</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{name:'rank_list_index'}">排行榜</router-link>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              用户名
            </a>
            <ul class="dropdown-menu">
              <li>
                <router-link :to="{name:'user_bot_index'}" class="dropdown-item">我的Bot</router-link>
              </li>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="#">退出</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <router-view> </router-view>
<!-- 踩坑点：  一定 要加router-view不然会无法渲染页面 -->
</template>

<script>

</script>

<style scoped>

</style>