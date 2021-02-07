<template>
  <v-container class="ma-auto word-cloud-width"> 
    <v-row class="pb-10">
      <v-col class="">
        <img 
          src="@/assets/img/wordcloud.png"  
          alt="word-cloud"
          class="word-cloud-img"
        >
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
    name: "WordCloud",
}
</script>

<style>
.word-cloud-width {
  max-width: 100vw;
}

.word-cloud-img {
  width: 100%;
  height: 500px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .word-cloud-img {
    width: 100%;
    height: 180px;
  }
}
</style>