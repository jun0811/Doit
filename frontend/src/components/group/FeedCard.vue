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

    <v-card-title>뜨개질 인증 1일차✌</v-card-title>

    <v-card-text>
      <div>열심히 했따~~</div>
    </v-card-text>

    <v-divider class="mx-4"></v-divider>
  </v-card>
</template>

<script>
  export default {
    data: () => ({
      loading: false,
      selection: 1,
    }),
  }
</script>

<style>

</style>