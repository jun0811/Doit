<template>
  <div>
      <Header></Header>
      <v-container class="pa-0 pa-md-3">
        <SearchBar></SearchBar>
        <br>
        <!-- <div class="d-flex align-center flex-column mx-auto"> -->
        <GroupCard :page="page" :word="word"></GroupCard>
        <v-pagination
          color="orange"
          v-model="page"
          :length="pageCount"
          :total-visible="7"
        ></v-pagination>
        <div v-if="!results" class="d-flex align-center flex-column">
          <h3>'검색키워드'와(과) 일치하는 검색결과가 없습니다.</h3>
          <br>
          <br>
          <h4>'검색키워드' 관련 그룹을 만들고 싶으시다면?</h4>
          <v-btn text class="text-h6" color="#F9802D" @click="createGroup">
            그룹 만들기
          </v-btn>
        </div>
        <!-- </div> -->
        <div v-else class="d-flex align-center flex-column">
          <h4 class="mt-6">'검색키워드' 관련 그룹을 만들고 싶으시다면?</h4>
          <v-btn text class="text-h6" color="#F9802D" @click="createGroup">
            그룹 만들기
          </v-btn>
        </div>

      </v-container>
      <Footer></Footer>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import SearchBar from "@/components/common/SearchBar";
import Footer from "@/components/common/Footer";
import GroupCard from "@/components/group/GroupCard.vue";
// import http from "../../http-common"
import { searchGroup } from "@/api/group/index.js"


export default {
  name: 'GroupList',
  props:{
    word:{ type: String, default: ""}
  },
    data() {
        return {
          results: {},
          page:1,
          pageCount:0,
          groups:[],
        }
    },
  created() {
    searchGroup(
      {
        "direction":"ASC",
        "page":this.page,
        "size":9,
        "tag":this.word,
      },
      (res) =>{
        if (res.status){
        console.log(res)
        this.groups = res.data.object.content
        this.pageCount = parseInt(res.data.object.totalPages)
        }
      },
      (err) =>{
        console.log(err)
        alert("검색 결과 가져오기 실패")
      }
    )
  },
  components: { 
      Header, 
      Footer, 
      SearchBar, 
      GroupCard 
  },
  methods: {
    createGroup(){
      this.$router.push('/group/groupcreate')
    }
  }
}
</script>

<style scoped>

</style>