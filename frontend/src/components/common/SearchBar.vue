<template>
    <div class="mx-auto search-bar">
        <v-text-field
            v-model="word"
            solo
            label="만들고 싶은 습관을 검색해보세요!"
            clearable
            color=""
        ></v-text-field>
    </div>
</template>

<script>
  export default {
    data () {
      return {
        word: ""
      }
    },
  }
</script>

<style>

</style>