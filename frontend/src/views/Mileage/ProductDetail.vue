<template>
<div>
  <Header></Header>
  <NavBar></NavBar>
    <v-container class="container-width">
        <v-row>
          <v-col class="img-wrapper">
            <img class="product-img" src="https://images.unsplash.com/photo-1498050108023-c5249f4df085?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1052&q=80" alt="product image">
          </v-col>
        </v-row>
        <v-row class="my-3 px-6 d-flex align-center">
          <span class="profile-wrapper pa-0 d-flex ">
            <img
            class="profile-img" 
            src="https://images.unsplash.com/photo-1529092419721-e78fb7bddfb2?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=964&q=80" 
            alt="글쓴이 이미지"
            >
          </span>
          <span class="pl-4">
            {{product.nickname}}
          </span>
        </v-row>
        <v-row class="d-flex justify-center mt-2">
          <hr class="mb-4 line">
        </v-row>
        <v-row class="d-flex flex-column align-start mx-0 mx-sm-1">
          <v-row class="prd-title pb-0">
              {{product.title}}
          </v-row>
          <v-row class="prd-category">카테고리: {{product.category}}</v-row>
          <v-row class="prd-mileage">{{product.mileage}} 마일리지</v-row>
          <v-row class="prd-content py-5">{{product.content}}</v-row>
        </v-row>
        <v-row class="d-flex justify-center">
          <hr class="mt-4 line">
        </v-row>
        <v-row class="d-flex justify-center">
          <v-col class="d-flex justify-center">
            <v-btn 
              class="deal-btn" 
              large
              text
              @click="chatting"
              >
              채팅으로 거래하기
            </v-btn>
          </v-col>
        </v-row>
    </v-container>
  <Footer></Footer>

</div>
</template>

<script>
import "@/assets/css/profile.css";
import Header from "@/components/common/Header.vue";
import NavBar from "@/components/common/NavBar.vue";
import Footer from "@/components/common/Footer.vue";
import http from "../../http-common";

export default {
  name: "ProductDetail",
  components: {
    Header,
    NavBar,
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
  },
  methods: {
    chatting() {
      this.$router.push("/chat")
    }
  }
};
</script>

<style scoped>
.container-width {
width: 600px; 
margin-top: 50px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
    .container-width {
      width: 370px;
    }
}

.img-wrapper {
  width:600px;
  height:400px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .img-wrapper {
    width:370px;
    height:270px;
  }
}

.line {
  width:570px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .line {
    width:340px;
  }
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
  padding:12px;
  margin:6px;
  /* width: 100%; */
}

.prd-category {
  font-size: 13px;
  padding: 12px;
  /* margin-bottom: 6px; */
  margin-left: 6px;
  color: grey;
}

.prd-mileage {
  font-size : 13px;
  padding-left:12px;
  margin:6px;
  height: 100%;
  line-height: 44px;
}

.prd-content {
  font-size : 15px;
  padding-left: 12px;
  padding-right: 12px;
  margin:6px;
}


.deal-btn {
  margin:30px;
  border: 2px solid orange;
  border-radius: 15px;
  background-color:white;

}

</style>