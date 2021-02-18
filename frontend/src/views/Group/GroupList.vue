<template>
  <div>
      <Header></Header>
      <v-container class="pa-0 pa-md-3">
        <SearchBar></SearchBar>
        <br>
        <!-- <div class="d-flex align-center flex-column mx-auto"> -->
        <div v-if="!empty" class="pl-sm-16 pb-3">
          총 <span style="color: #F9802D">{{ contentlength }}</span> 개의 검색 결과가 있습니다.
        </div>
        <GroupCard :page="page" :word="word"></GroupCard>
        <div v-if="empty" class="d-flex align-center flex-column">
          <h3>'{{ word }}'와(과) 일치하는 검색결과가 없습니다.</h3>
          <br>
          <br>
          <h4>'{{ word }}' 관련 그룹을 만들고 싶으시다면?</h4>
          <v-btn text class="text-h6" color="#F9802D" @click="createGroup">
            그룹 만들기
          </v-btn>
        </div>
        <!-- </div> -->
        <div v-else class="d-flex align-center flex-column">
          <v-pagination
            v-if="pageCount>=1"
            color="orange"
            v-model="page"
            :length="pageCount"
            :total-visible="7"
            class="mt-12 "
          ></v-pagination>
          <h4 class="mt-12">'{{ word }}' 관련 그룹을 만들고 싶으시다면?</h4>
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
    word:{ type: String, default: ""},
    page: Number,
  },
    data() {
        return {
          results: {},
          pageCount:0,
          groups:[],
          empty: '',
          contentlength: 0,
        }
    },
  updated(){
    searchGroup(
      {
        "direction":"DESC",
        "page":this.page,
        "size":9,
        "tag":this.word,
      },
      (res) =>{
        if (res.status){
        console.log(res)
        this.groups = res.data.object.content
        this.pageCount = parseInt(res.data.object.totalPages)
        this.empty = res.data.object.sort.empty
        this.contentlength = res.data.object.totalElements
        
        }
      },
      (err) =>{
        console.log(err)
        alert("검색 결과 가져오기 실패")
      }
    )
  },
  created() {
    searchGroup(
      {
        "direction":"DESC",
        "page":this.page,
        "size":9,
        "tag":this.word,
      },
      (res) =>{
        if (res.status){
        console.log(res)
        this.groups = res.data.object.content
        this.pageCount = parseInt(res.data.object.totalPages)
        this.empty = res.data.object.empty
        this.length = res.data.object.content.length
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