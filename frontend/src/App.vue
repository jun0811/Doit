<template>
  <v-app>
    <div>
      <!-- <Header></Header>
      <WordCloud></WordCloud>
      <Aside></Aside>
      <Footer></Footer> -->
      <router-view></router-view>
    </div>
  </v-app>
</template>

<script>
import "./assets/css/main.css";
// import Header from "@/components/common/Header.vue";
// import Footer from "@/components/common/Footer.vue";
// import Aside from "@/components/common/Aside.vue";
// import WordCloud from "@/components/WordCloud.vue";

export default {
  name: 'App',

  components: {
      // Header,
      // Footer,
      // Aside,
      // WordCloud
    },
  

  data: () => ({
    //
  }),
};
</script>
