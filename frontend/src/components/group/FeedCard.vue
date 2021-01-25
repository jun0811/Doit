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
      src="https://lh3.googleusercontent.com/proxy/YvWALrhNm-SwJDQW2OueRmvhIPYbVGSrUsfHP1qifM8pUPOqVcjX_eeg560eVA0gF2yVoZQ0pdkZafAmu944aGvs4tPSe4CO1mTjk6WFEsIsz2jRHSrGSrSa-Z2awAJQBYdAqn2CgiIK869H4chgsfgqdnCWmYfspBIAlwToiKvCDHzpUtAhzWLWFaqVJSGLC6Nfh6tQpotWbjIxs_R-xeqzJ-yffgB4aAN49tx-uTg5xBUayvss9h58drBY8T7BL9yzpdJAY8wFqRx00PYM9ud0VxM_zjwo4bi2AoBb6y3gsA"
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