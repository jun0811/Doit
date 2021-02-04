<template>
  <div>
    <Header></Header>
    <v-card
      class="mx-auto my-12 d-flex flex-column align-center product-card"
      width="600"
    >
      <v-row class="d-flex justify-space-around title-wrapper">
        <v-col cols="9" class="pl-4 py-0 px-0">
          <b>{{product.title}}</b>
        </v-col>
        <v-col cols="3" class="py-0 px-0" style="text-align:center">
          {{user}}
        </v-col>
      </v-row>
      <div class="img-wrapper">
        <img class="product-img" src="https://images.unsplash.com/photo-1498050108023-c5249f4df085?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1052&q=80" alt="product image">
      </div>
      <v-card class="content-card" style="border-radius : 5px;">
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

.title-wrapper {
  width:100%;
  margin : 10px;
  padding : 10px 30px
}


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