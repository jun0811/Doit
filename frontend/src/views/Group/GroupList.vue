<template>
  <div>
      <Header></Header>
      <v-container class="pa-3 pa-sm-16">
        <SearchBar></SearchBar>
        <br>
        <!-- <div class="d-flex align-center flex-column mx-auto"> -->
        <GroupCard v-for="(result,idx) in results" :key="idx" :group="result"></GroupCard>
        <!-- </div> -->
      </v-container>
      <Footer></Footer>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import SearchBar from "@/components/common/SearchBar";
import Footer from "@/components/common/Footer";
import GroupCard from "@/components/group/GroupCard.vue";
import http from "../../http-common"



export default {
    name: 'GroupList',
    props:{
      word:{ type: String, default: ""}
    },
    created() {
      http.get(`/group/searchGroup?tag=${this.word}`)
      .then((res)=>{
        this.results = res.data.object
        
      })
      
    },
    components: { 
        Header, 
        Footer, 
        SearchBar, 
        GroupCard 
    },
    data() {
        return {
          results: {}
        }
    }
}
</script>

<style>

</style>