<template>
  <div class="container">
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

</style>