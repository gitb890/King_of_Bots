<template>
<div class="playgrand">
  <GameMap>123</GameMap>
</div>
</template>

<script>
import GameMap from './GameMap'

export default {
  components:{
    GameMap,
  }
}
</script>

<style scoped>
div.playgrand{
  width: 68vw;
  height: 78vh;
  background: lightcyan;
  margin: 20px auto;
}
</style>