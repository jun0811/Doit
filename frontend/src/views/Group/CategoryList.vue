<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
      <!-- 2월 15일 일단 GroupList의 v-container를 아래에 붙여놓음 이후부터 코딩할 것-->
      <v-container class="pa-0 pa-md-3">
        <SearchBar></SearchBar>
        <br>
        <!-- <div class="d-flex align-center flex-column mx-auto"> -->
        <GroupCard :page="page" :word="word"></GroupCard>
        <v-pagination
          color="orange"
          v-model="page"
          :length="pageCount"
          :total-visible="7"
        ></v-pagination>
        <div v-if="!results" class="d-flex align-center flex-column">
          <h3>'검색키워드'와(과) 일치하는 검색결과가 없습니다.</h3>
          <br>
          <br>
          <h4>'검색키워드' 관련 그룹을 만들고 싶으시다면?</h4>
          <v-btn text class="text-h6" color="#F9802D" @click="createGroup">
            그룹 만들기
          </v-btn>
        </div>
        <!-- </div> -->
        <div v-else class="d-flex align-center flex-column">
          <h4 class="mt-6">'검색키워드' 관련 그룹을 만들고 싶으시다면?</h4>
          <v-btn text class="text-h6" color="#F9802D" @click="createGroup">
            그룹 만들기
          </v-btn>
        </div>

      </v-container>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import NavBar from "@/components/common/NavBar.vue";
import Footer from "@/components/common/Footer.vue";

export default {
  name: "GroupCategoryList",
  components: {
    Header,
    NavBar,
    Footer,
  }
}
</script>

<style>

</style>