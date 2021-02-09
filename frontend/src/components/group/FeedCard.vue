<template>
  <v-card
    :loading="loading"
    class="mx-auto my-12"
    width="550"
  >
    <template slot="progress">
      <v-progress-linear
        color="deep-purple"
        height="10"
        indeterminate
      ></v-progress-linear>
    </template>

    <v-img
      height="250"
      src="https://wonderfulmind.co.kr/wp-content/uploads/2018/05/knitting.jpg"
    ></v-img>

    <v-card-title>{{this.card.content}}</v-card-title>

    <v-card-text>
      <div>{{this.card.writer}}</div>
      <div>작성날짜: {{this.card.createDate.slice(0,10)}}</div>
    </v-card-text>

    <v-divider class="mx-4"></v-divider>
  </v-card>
</template>

<script>
  export default {
    props: {
      card: Object
    },
    created(){
      console.log(this.card)
    },
    data: () => ({
      loading: false,
      selection: 1,
    }),
  }
</script>

<style>

</style>