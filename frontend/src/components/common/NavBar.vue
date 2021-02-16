<template>
  <v-container fluid fixed-header class="nav-style">
    <v-row class="d-flex justify-center">
      <v-col cols="5" sm="2" class="d-flex justify-center">
        <v-btn 
          text 
          large
          @click="introduction"
        >
          Doit을 소개합니다
        </v-btn>
      </v-col>
      <v-col cols="4" sm="2" class="d-flex justify-center">
        <v-btn 
          text 
          large
          @click="goToCategory"
        >
          그룹 둘러보기
        </v-btn>
      </v-col>
      <v-col cols="3" sm="2" class="d-flex justify-center">
        <v-btn 
          text 
          large
          @click="goToShop"
        >
          Shop
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <div class="navbar-line" role="presentation"></div>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "NavBar",
  methods: {
    goToShop() {
      this.$router.push("/mileageshop")
    },
    goToCategory() {
      this.$router.push({name: 'CategoryList', params: {category: 'study'}})
    },
    introduction() {
      this.$router.push("/introduction")
    }
  }
}
</script>

<style scoped>
.navbar-line {
  width: 100%;
  border-bottom: 1px solid black;
  opacity: 0.2;
}

.nav-style {
  /* position: fixed; */
  background-color: white;
  /* margin-bottom: 50px; */
  z-index: 1;
}
</style>