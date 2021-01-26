<template>
  <div>
  <Header></Header>
      <v-container class="pa-8 pa-sm-16 d-flex align-center flex-column mx-auto">
        <h1>🎉가입 완료✨</h1>
        <br>
        <v-btn @click="main" text color="#F9802D"> 메인페이지로 </v-btn>
      </v-container>
  <Footer></Footer>
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import http from '../../http-common'

export default {
  components: {Header, Footer},

  created() {
    // console.log(this.$route.query.authKey)
    http.get(`/user/confirmSignup?authKey=${this.$route.query.authKey}&email=${this.$route.query.email}`) 
    .then((res)=>{
      console.log(res)
    })
  }
}
</script>

<style>

</style>