<template>
  <div>
    <v-row class="mx-1 mx-sm-3 mb-4 mt-0">
      Doit에서 가장 많이 나온 키워드를 확인해보세요!
    </v-row>
    <v-row class="ml-sm-3 px-5 px-sm-0">
      <img 
        src="@/assets/img/wordcloud.png"  
        alt="word-cloud"
        class="word-cloud-img"
      >
    </v-row>
  </div>
</template>

<script>
export default {
    name: "WordCloud",
}
</script>

<style>

.word-cloud-img {
  width: 100%;
  height: 100%;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .word-cloud-img {
    width: 100%;
    height: 100%;
  }
}
</style>