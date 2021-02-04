<template>
  <div>
    <Header></Header>
    <!-- <v-container class="pa-3 pa-sm-16 d-flex flex-column justify-center">
      <v-row class="py-3">
        <v-col class="mx-3 mx-sm-16"> 
          <h1>Product Detail</h1>
        </v-col>
      </v-row>
    </v-container> -->
    <v-card
      class="mx-auto my-12 d-flex flex-column align-center product-card"
      width="600"
    >
      <v-row class="d-flex justify-space-between" width="100%">
        <v-col cols="3" class="mx-6 my-4" style="text-align:center">
          <b>{{product.title}}</b>
        </v-col>
        <v-col cols="3" class="mx-6 my-4" style="text-align:center">
          {{user}}
        </v-col>
      </v-row>
      <div class="img-wrapper">
        <img class="product-img" src="https://images.unsplash.com/photo-1466584241662-8cb021032c1a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80" alt="">
      </div>
      <v-card class="content-card">
        <v-card-text>
          {{product.content}}
        </v-card-text>
      </v-card>
    </v-card>

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

.product-card {
  border-radius : 20px;
}

.img-wrapper {
  width:500px;
  height:300px;
}

.product-img {
  width: 100%;
  height:100%;
  overflow: hidden;
  object-fit: cover;
  border-radius: 5px;
}

.content-card {
  width: 500px;
  min-height: 100px;
  margin : 30px 0 40px;
  border-radius : 5px;
}
</style>