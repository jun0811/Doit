<template>
  <div>
    <v-footer 
    color="#F9802D"
    class="foot d-flex flex-column justify-center py-6 white--text">
      <v-row
        justify="center"

        no-gutters
      >
        <span class="mr-3">만든사람들 : </span>
        <span v-for="(member,idx) in members" :key="idx" class="d-flex align-center">
          <font-awesome-icon v-if="idx !== 0" class="circle mx-2" icon="circle"/>
          <span >{{member}}</span>
        </span>
      </v-row >
      <v-row no-gutters>
        <span class="mr-3">소속 : </span>
        <span>삼성청년소프트웨어(SSAFY) 4기 광주 1반</span>
      </v-row>
      <v-row no-gutters>
        <v-col
          class="py-4 text-center "
          cols="12"
        >
          {{ new Date().getFullYear() }} — <strong>Doit</strong>
        </v-col>
      </v-row>
    </v-footer>

  </div>

</template>

<script>
  export default {
    data: () => ({
      members:['김부희', '김소정','박다솔','이승준','최예은','한지현'],
    }),
  }
</script>

<style scoped>

.foot {
  display:absolute;
  bottom:-100px;
  background-color: darkorange;
  color:lightgoldenrodyellow;
}

.circle{
  width: 0.25rem;
  height: 0.25rem;
}
</style>