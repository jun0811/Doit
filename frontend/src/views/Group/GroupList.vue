<template>
  <div>
      <Header></Header>
      <v-container class="pa-3 pa-sm-16">
        <SearchBar></SearchBar>
        <br>
        <span class="mx-auto">{{ word }}</span>
        <!-- <div class="d-flex align-center flex-column mx-auto"> -->
        <GroupCard></GroupCard>
        <GroupCard></GroupCard>
        <GroupCard></GroupCard>
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

export default {
    name: 'GroupList',
    props:{
      word:{ type: String, default: ""}
    },
    components: { 
        Header, 
        Footer, 
        SearchBar, 
        GroupCard 
    },
    data() {
        return {
            
        }
    }
}
</script>

<style>

</style>