<template>
  <img 
    src="@/assets/img/wordcloud.png"  
    alt="word-cloud"
    class="word-cloud-img"
  >
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