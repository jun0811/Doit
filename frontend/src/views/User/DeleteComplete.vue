<template>
  <div>
    <Header></Header>
    <v-container class="container-width">
      <v-row>
        <v-col class="d-flex justify-center">
          <h2>회원 탈퇴가 완료되었습니다.</h2>
        </v-col>
      </v-row>
      <v-row>
        <v-col class="d-flex justify-center">
          그동안 Doit을 이용해 주셔서 감사합니다.
        </v-col>
      </v-row>
      <v-row>
        <v-col class="d-flex justify-center pt-0 mb-7">
          보다 나은 Doit으로 다시 만나뵐 수 있기를 바랍니다.
        </v-col>
      </v-row>
      <v-row class="mb-10">
        <v-col class="d-flex justify-center">
          <v-btn
            outlined
            @click="mainBtn"
          >
            Doit 홈 바로가기
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";

export default {
  name: "DeleteComplete",
  components: {
    Header,
    Footer,
  },
  methods: {
    mainBtn () {
      this.$router.push("/")
      this.$router.go()
    }
  }
}
</script>

<style>
  .container-width {
  width: 55%; 
  margin-top: 80px;
  }

  @media only screen and (min-width: 300px) and (max-width: 599px) {
      .container-width {
        width: 90%;
      }
  }
</style>