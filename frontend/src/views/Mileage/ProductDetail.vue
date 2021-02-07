<template>
<div>
  <Header></Header>
    <v-container class="d-flex flex-column justify-center align-center">
        <v-row>
          <v-col class="img-wrapper">
            <img class="product-img" src="https://images.unsplash.com/photo-1498050108023-c5249f4df085?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1052&q=80" alt="product image">
          </v-col>
        </v-row>
        <v-row class="content-wrapper my-3 px-6 d-flex align-center">
          <span class="profile-wrapper pa-0 d-flex ">
            <img
            class="profile-img" 
            src="https://images.unsplash.com/photo-1529092419721-e78fb7bddfb2?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=964&q=80" 
            alt="글쓴이 이미지"
            >
          </span>
          <span class="pl-4">
            {{user}}
          </span>
        </v-row>
        <hr style="width:570px;" class="mb-4">
        <v-row class="d-flex flex-column content-wrapper ml-8">
          <v-col class="prd-title">{{product.title}}</v-col>
          <v-col class="prd-category">무슨무슨 카테고리</v-col>
          <v-col class="prd-mileage">얼마얼마 마일리지</v-col>
          <v-col class="prd-content">{{product.content}}</v-col>
        </v-row>
        <hr style="width:570px;" class="mt-4">
        <v-btn class="deal-btn" 
          large
          >
          채팅으로 거래하기
        </v-btn>
    </v-container>
  <Footer></Footer>

</div>
</template>

<script>
import "@/assets/css/profile.css";
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import http from "../../http-common";

export default {
  name: "ProductDetail",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      product:'',
      user : 'nickname',
    }
  },
  props:{
    product_id: Number,
  },
  created() {
    http.get(`/product/${this.product_id}`)
    .then((res)=>{
      console.log(res.data.object)
      this.product = res.data.object
    })
  },
  computed: {
  }
};
</script>

<style scoped>

.content-wrapper {
  width:600px;
}


.img-wrapper {
  width:600px;
  height:400px;
}

.product-img {
  width: 100%;
  height:100%;
  overflow: hidden;
  object-fit: cover;
  border-radius: 5px;
}

.profile-img {
  width: 50px;
  height: 50px;
  object-fit:cover;
  border-radius: 50%;
  border : 3px solid orange;
}

.prd-title {
  font-size: 20px;
  font-weight:bold;
  padding:0;
  margin:6px;
}

.prd-category {
  font-size: 18px;
  padding:0;
  margin:6px;
}

.prd-mileage {
  font-size : 18px;
  padding:0;
  margin:6px;
}

.prd-content {
  font-size : 15px;
  padding:0;
  margin:15px;
}


.deal-btn {
  margin:30px;
  border: 2px solid orange;
  border-radius: 15px;
  background-color:white;

}

</style>