<template>
  <div>
    <v-row class="mx-1 mx-sm-3 mb-5 mt-0">
      <h4>Doit에서 가장 많이 나온 키워드를 확인해보세요!</h4>
    </v-row>
    <v-row class="mx-0 px-3 px-sm-0">
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