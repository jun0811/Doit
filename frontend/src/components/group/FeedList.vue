<template>
    <v-card
      class="mx-auto"
    >
      <div class="pa-0 d-flex flex-nowrap justify-center align-center flex-column scrollBar">

        <MyFeeds v-for="(card,idx) in cards" :key="idx" :card="card"></MyFeeds>
      </div>        
    </v-card>
</template>

<script>
import MyFeeds from '@/components/group/MyFeeds.vue'
import http from "../../http-common";


  export default {
    components: {
      MyFeeds,
    },
    created(){
      http.get(`feed/userFeed?userPk=${sessionStorage.getItem("userpk")}`)
      .then((res)=>{
        this.cards = res.data.object
        console.log(this.cards)
      })
    }
    ,
    data: () => ({
      cards:[]
    }),
  }
</script>

<style scoped>
  .scrollBar{
    overflow: scroll;
    width: 400px;
    height:800px;
    padding: 20px;
  }

</style>
