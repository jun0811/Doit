<template>
  <div>
      <Header></Header>
      <NavBar></NavBar>
      <v-container class="pa-0 pa-md-3">
        <!-- <SearchBar></SearchBar> -->
        <br>
        <!-- <div class="d-flex align-center flex-column mx-auto"> -->
        <v-row>
          <v-col>
            <h3 class="pl-1 pl-sm-16 mb-3">{{ category_k }} 카테고리</h3>
          </v-col>
          <v-col>
            <v-btn text outlined>공부</v-btn>
            <v-btn text outlined>다이어트</v-btn>
            <v-btn text outlined>운동</v-btn>
            <v-btn text outlined>책</v-btn>
            <v-btn text outlined>생활습관</v-btn>
            <v-btn text outlined>취미</v-btn>
            <v-btn text outlined>기타</v-btn>        
          </v-col>
        </v-row>
        <div v-if="!empty" class="pl-1 pl-sm-16">
          총 <span style="color: #F9802D">{{ length }}</span> 개의 그룹이 있습니다.
        </div>
        <GroupCard :page="page" :category="category_e"></GroupCard>
        <div v-if="empty" class="d-flex align-center flex-column">
          <h3>'{{ category_k }}' 카테고리에는 아직 그룹이 생성되지 않았어요.</h3>
          <br>
          <br>
          <h4>'{{ category_k }}' 관련 그룹을 만들고 싶으시다면?</h4>
          <v-btn text class="text-h6" color="#F9802D" @click="createGroup">
            그룹 만들기
          </v-btn>
        </div>
        <!-- </div> -->
        <div v-else class="d-flex align-center flex-column">
          <v-pagination
            color="orange"
            v-model="page"
            :length="pageCount"
            :total-visible="7"
          ></v-pagination>
          <h4 class="mt-6">'{{ category_k }}' 관련 그룹을 만들고 싶으시다면?</h4>
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
import NavBar from "@/components/common/NavBar.vue";
// import SearchBar from "@/components/common/SearchBar";
import Footer from "@/components/common/Footer";
import GroupCard from "@/components/group/GroupCard.vue";
// import http from "../../http-common"
import { categoryGroup } from "@/api/group/index.js"


export default {
  name: 'CategoryList',
  props:{
    category_e:{ type: String, default: ""},
    category_k:{ type: String, default: ""}
  },
  data() {
    return {
      results: {},
      page:1,
      pageCount:0,
      groups:[],
      empty: '',
      length: 0,
      categories: {'study':'공부', 'exercise':'운동' , 'diet':'다이어트', 'hobby':'취미' , 'book':'독서' , 'life':'생활습관', 'etc':'기타'},
    }
  },
  updated(){

  },
  created() {
    // console.log(this.category);
    categoryGroup(
      {
        "category":this.category_e,
        "direction":"ASC",
        "page":this.page,
        "size":9,
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
      NavBar,
      Footer, 
      // SearchBar, 
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