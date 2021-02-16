<template>
  <v-container class="grass">
    <v-row d-flex>
      <v-col v-for="(cnt,idx) in size" :key="idx"  cols="1" class="pa-0 mr-1 mb-1">
        <div class="box" :class="{ color1: check1(cnt), color2: check2(cnt), color3: check3(cnt) }"></div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import http from "../http-common"


export default {
  data: () =>({
    size: [],
  }),
  created () {
    http.get(`data/grass?userPk=${sessionStorage.getItem("userpk")}`)
    .then((res)=> {
      this.size = res.data.object
    })
  },
  methods:{
    check1 (cnt){
      if (cnt==1) return true
      return false
    },
    check2 (cnt){
      if (cnt==2) return true
      return false
    },
    check3 (cnt){
      if (cnt>=3) return true
      return false
    }
  }
}
</script>

<style scoped>
  .grass{
    width: 350px;
  }
  .color1{
    background: #FFE0B2
  }
  .color2{ 
    background:#FFB74D
  }
  .color3{
    background: #FB8C00
  }
  .box{
    border: 1px solid rgb(2, 2, 2, 0.3);
    padding-bottom:100%;
  }
</style>