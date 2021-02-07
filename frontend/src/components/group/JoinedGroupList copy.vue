<template>
  <v-card
    class="mx-auto"
    tile
  >
    <v-list
      nav
      dense
    >
      <v-virtual-scroll
        :bench="benched"
        :items="items"
        item-height="64"
        class="joined-size"
      >
        <v-list-item
          v-for="subItem in items"
          :key="subItem.groupPk"
          @click="group(subItem.groupPk)"
        >
          <!-- 추후 그룹 사진 넣을 예정-->
          <!-- <v-list-item-icon>
            <v-icon v-text="item.icon"></v-icon>
          </v-list-item-icon> -->

          <v-list-item-content>
            <v-list-item-title v-text="subItem.name"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-virtual-scroll>
    </v-list>
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
      this.$router.push(`/group/community?no=${no}`);
    },
  }
}
</script>

<style>
.joined-size {
  width: 100%;
  height: 100%;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .joined-size {
    width: 120%;
    height: 100%;
  }
}
</style>