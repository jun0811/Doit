<template>
  <div>
      <Header></Header>
      <NavBar></NavBar>
      <v-container class="pa-0 pa-md-3">
        <br>
        <v-row class="mb-3">
          <v-col cols="12" sm="6" class="d-flex flex-column justify-center">
            <h3 class="pl-1 pl-sm-16"><span class="orange-color">{{ category_k }}</span> 카테고리</h3>
          </v-col>
          <v-col cols="12" sm="6">
            <v-btn text outlined @click="clickCategory('공부')">공부</v-btn>
            <v-btn text outlined @click="clickCategory('다이어트')">다이어트</v-btn>
            <v-btn text outlined @click="clickCategory('운동')">운동</v-btn>
            <v-btn text outlined @click="clickCategory('책')">책</v-btn>
            <v-btn text outlined @click="clickCategory('생활습관')">생활습관</v-btn>
            <v-btn text outlined @click="clickCategory('취미')">취미</v-btn>
            <v-btn text outlined @click="clickCategory('기타')">기타</v-btn> 
          </v-col>
        </v-row>
        <div v-if="!empty" class="pl-1 pl-sm-16">
          총 <span class="orange-color">{{ length }}</span> 개의 그룹이 있습니다.
        </div>
        <GroupCard :page="page" :category="category"></GroupCard>
        <div v-if="empty" class="d-flex align-center flex-column">
          <h3 class="mt-16">'{{ category_k }}' 카테고리에는 아직 그룹이 생성되지 않았어요.</h3>
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
import Footer from "@/components/common/Footer";
import GroupCard from "@/components/group/GroupCard.vue";
import { categoryGroup } from "@/api/group/index.js"


export default {
  name: 'CategoryList',
  props:{
    // category_e:{ type: String, default: ""},
    // category_k:{ type: String, default: ""}
    category: {type: String, default: ""}
  },
  data() {
    return {
      results: {},
      page:1,
      pageCount:0,
      groups:[],
      empty: '',
      length: 0,
      category_k: '',
      categories: {'공부': 'study', '운동': 'exercise', '다이어트': 'diet', '취미': 'hobby', '독서': 'book', '생활습관': 'life', '기타': 'etc'},
      categories2: {'study':'공부', 'exercise':'운동', 'diet':'다이어트', 'hobby':'취미', 'book':'독서', 'life':'생활습관', 'etc':'기타'},
    }
  },
  created() {
    // console.log(this.category);
    categoryGroup(
      {
        "category":this.category,
        "direction":"ASC",
        "page":this.page,
        "size":9,
      },
      (res) =>{
        if (res.status){
        // console.log(res)
        this.groups = res.data.object.content
        this.pageCount = parseInt(res.data.object.totalPages)
        this.empty = res.data.object.empty
        this.length = res.data.object.content.length
        this.category_k = this.categories2[this.category]
        // console.log(this.category_k = this.categories2[this.category]);
        }
      },
      (err) =>{
        console.log(err)
        alert("검색 결과 가져오기 실패")
      }
    )
  },
  updated(){
    categoryGroup(
      {
        "category":this.category,
        "direction":"ASC",
        "page":this.page,
        "size":9,
      },
      (res) =>{
        if (res.status){
        // console.log(res)
        this.groups = res.data.object.content
        this.pageCount = parseInt(res.data.object.totalPages)
        this.empty = res.data.object.empty
        this.length = res.data.object.content.length
        this.category_k = this.categories2[this.category]
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
      GroupCard 
  },
  methods: {
    createGroup() {
      this.$router.push('/group/groupcreate')
    },
    clickCategory(v) {
      console.log(v);
      this.category = this.categories[v]
      this.category_k = v
    }
  }
}
</script>

<style scoped>
.orange-color {
  color: #F9802D;
}
</style>