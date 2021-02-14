<template>
  <v-card
    max-width="400"
    class="mx-auto"
  >
    <v-virtual-scroll
      :bench="benched"
      :items="items"
      height="600"
      class="joined-size"
      item-height="64"
    >
      <template v-slot="{ item }">
        <v-list-item :key="item">
          <v-list-item-action>
            <v-btn
              fab
              small
              depressed
              color="orange"
            >
            </v-btn>
          </v-list-item-action>

          <v-list-item-content>
            <v-list-item-title>
              {{ item.name }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>
      </template>
    </v-virtual-scroll>
  </v-card>

</template>

<script>
import http from '../../http-common'

export default {
  name: "JoinedGroupList",
  data: () => ({
    benched: 0,
    items: [],
  }),
  created() {
    // 현재 로그인 한사람의 가입 그룹 리스트
    if(this.$store.getters.getAccessToken){
        http.get('/group/currentUserGroup')
          .then((res)=>{
          this.items = res.data.object;
          console.log(res)
      })
    }    
  },
  methods: {
    group(no) {
      this.$router.push({name:"Community",params:{groupPk:Number(no)}});
    },
  }
}
</script>

<style>
.joined-size {
  width: 100%;
  height: 600px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .joined-size {
    width: 120%;
    height: 300px;
  }
}
</style>