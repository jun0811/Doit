<template>
  <v-app>
    <div>
      <Header></Header>
      <NavBar></NavBar>
      <SearchBar></SearchBar>
      <Category></Category>
      <v-container class="px-3 px-sm-16 mt-16">
        <v-row class="d-flex justify-space-between">
          <v-col cols="12" sm="6" class="px-1 pl-sm-16">
            <WordCloud></WordCloud>
          </v-col>
          <v-col cols="12" sm="5" class="px-2 pr-sm-16">
            <Ranking></Ranking>
          </v-col>
        </v-row>
      </v-container>
      <Footer></Footer>
    </div>
  </v-app>
</template>

<script>
import "../assets/css/main.css";
import Header from "@/components/common/Header.vue";
import NavBar from "@/components/common/NavBar.vue";
import Footer from "@/components/common/Footer.vue";
import SearchBar from "@/components/common/SearchBar.vue";
import WordCloud from "@/components/WordCloud.vue";
import Category from "@/components/Category.vue";
import Ranking from "@/components/Ranking.vue";

export default {
  name: "Main",
  components: {
    Header,
    NavBar,
    Footer,
    WordCloud,
    SearchBar,
    Category,
    Ranking,
  },
};
</script>

<style>
</style>