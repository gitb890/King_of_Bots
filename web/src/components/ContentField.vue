<template>
  <div class="container content-feild">
    <div class="card">
      <div class="card-body">
        <slot></slot>
      </div>
    </div>
  </div>
  <router-view> </router-view>
</template>

<script>
export default {
  name: "ContentField"
}
</script>

<style scoped>
div.content-feild{
  margin-top: 20px;
}
</style>