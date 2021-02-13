<template>
  <v-container>
    <v-row d-flex>
      <v-col v-for="(a,idx) in size" :key="idx"  cols="1" class="pa-0">
        <div class="box"></div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
var array = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
// cnt  0 1 2 3 

export default {
  data: () =>({
    size: array

  })
}
</script>

<style scoped>
  .color1{
    /* #FFE0B2 */
  }
  .color2{
    /* #FFB74D */
  }
  .color3{
    /* #FB8C00 */
  }
  .box{
    border: 1px solid;
    background:  #FB8C00;
    padding-bottom:100%;
  }
</style>