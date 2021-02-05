<template>
  <div>
    <Header></Header>
    <v-container class="pa-3 pa-sm-16 d-flex flex-column justify-center">
      <v-row class="py-3">
        <v-col class="mx-3 mx-sm-16"> 
          <h1>마일리지 Shop</h1>
        </v-col>
      </v-row >
      <!--카테고리 클릭시 페이지 1로 초기화 하고 프로덕트 리스트도 다시 바꿔주기 -->
      <v-row class="py-3 mx-3 mx-sm-16 d-flex justify-space-between">
        <v-btn 
          depressed
          class="d-flex justify-center"
          v-for="(category, idx) in categories"
          :key="idx"
        >
          {{category}}
        </v-btn>
      </v-row>

        <ProductPage :page="page" :pageCount="pageCount">{{page}}</ProductPage>

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
      selectedCategory : 'All',
      categories:[
        '카테고리1', '카테고리2', '카테고리3', '카테고리4', '카테고리5', '카테고리6', 
      ],
      products:[],
      page:1,
    }
  },
  created() {
    http.get('/product/getAll')
    .then((res)=>{
      // console.log(res.data.object)
      this.products = res.data.object
    })
  },
  computed: {
    pageCount () {
      let listLeng = this.products.length,
        listSize = 9,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;
      return page;
    },
  }
};
</script>

<style>
</style>