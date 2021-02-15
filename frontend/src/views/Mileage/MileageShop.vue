<template>
  <div>
    <Header></Header>
    <v-container class="d-flex flex-column justify-center pa-0 pa-md-3">
      <v-row class="d-flex flex-wrap px-md-16 mt-3">
        <v-col 
        cols="12" xs="12" sm="5" md="5" lg="5" xl="5"
        class="mx-6 mx-sm-0 mx-md-0 mx-lg-0 mx-xl-0 pt-0"> 
          <h1 style="display:inline-block; word-break: keep-all;">마일리지 Shop</h1>
        </v-col>
        <v-col cols="4" sm="2" class="pt-0">
          <v-btn
            text
            x-large
            @click="chatList"
          >나의 채팅</v-btn>
        </v-col>
        <v-col 
          class="d-flex justify-end align-center pl-6 px-sm-0 px-md-0 px-lg-0 px-xl-0 pt-0" 
          cols="7" xs="6" sm="3" md="3" lg="3" xl="3"> 
          <span v-show="joined"  style="word-break: keep-all;">나의 마일리지</span>
          <v-btn v-show="joined" elevation="0" color="transparent" class="mileage mx-1 pa-0 orange--text"
          router-link :to="{name: 'MileageHistory'}">{{mileage}}</v-btn>
        </v-col>
        <v-col 
          cols="12" xs="4" sm="2" md="2" lg="2" xl="2"
          class="d-flex justify-end pr-6 px-sm-0 px-md-0 px-lg-0 px-xl-0 pt-0"> 
          <v-btn 
            outlined 
            class="write-btn"
            router-link :to="{name: 'ProductWrite'}"
            >물품 등록하기</v-btn>
        </v-col>
      </v-row >
      <!--카테고리 클릭시 페이지 1로 초기화 하고 프로덕트 리스트도 다시 바꿔주기 -->
      <v-row class="mt-3 py-3 mx-3 mx-sm-16 d-flex justify-space-between">
        <v-btn 
          depressed
          class="d-flex justify-center ma-1 category"
          v-for="(category, idx) in categories"
          :key="idx"
          value ="category"
          @click="keywordSearch(idx)"
        >
          {{category}}
        </v-btn>
      </v-row>

      <ProductPage :page="page" :keyword="keyword" :option="option"></ProductPage>
      <v-pagination
        color="orange"
        v-model="page"
        :length="pageCount"
        :total-visible="7"
      ></v-pagination>
    </v-container>
    <Footer></Footer>
  </div>
</template>

<script>
import "@/assets/css/profile.css";
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import ProductPage from "@/components/mileage/ProductPage.vue";
import http from "../../http-common";

export default {
  name: "MileageShop",
  components: {
    Header,
    Footer,
    ProductPage,
  },
  data() {
    return {
      categories:[
        '전체','음식', '책', '운동', '취미', '카테고리5', '카테고리6', 
      ],
      page:1,
      pageCount :1,
      products:[],
      direction:'DESC',
      keyword:'',
      option:'',
      mileage:90,
      joind:false,
      selectedKeyword:'전체',
      category: '',
    }
  },
  created() {
    this.getMileage()
    this.getProducts()
  },
  methods: {
    getProducts() {
      http.get(`/product/search?direction=${this.direction}&keyword=${this.keyword}&option=${this.option}&pg=${this.page}`)
      .then((res)=>{
        this.products = res.data.object.content
      })
    },
    keywordSearch(idx) {
      const selectedKeyword = this.categories[idx]
      const categoriesBtn = document.querySelectorAll('.category')
      categoriesBtn.forEach(category => {
        if (category.innerText === selectedKeyword) {
          category.classList.add('selelctedKeyword')
        } else {
          category.classList.remove('selelctedKeyword')
        }
      })
      if (selectedKeyword ==="전체") {
        this.keyword = ""
        this.option = ""
        this.getProducts()
      } else {
        this.keyword = selectedKeyword
        this.option = 'category'
        this.getProducts()
      }
    },
    getMileage() {
      http.get(`/user/detailUser`)
      .then((res)=>{
        this.mileage = res.data.object.mileage
      })
    },
    chatList() {
      this.$router.push('/chatlist')
    }
  },

  computed : {
    joined () {
      return this.$store.getters.isAuthenticated
    }
  },
};
</script>

<style scoped>
.write-btn {
  border : 2px solid grey;
  border-radius: 15px;
}

.write-btn:hover {
  border: 2px solid #F9802D;
  color: orange;
}

.mileage {
  color :  #F9802D;
}

.selelctedKeyword {
  color: #F9802D;
  background-color: rgb(248, 227, 188) !important
}
</style>