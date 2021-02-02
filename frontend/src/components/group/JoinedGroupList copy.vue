<template>
  <v-list>
    <v-list-group
      v-for="item in items"
      :key="item.title"
      v-model="item.active"
      :prepend-icon="item.action"
      no-action
    >
      <template v-slot:activator>
        <v-list-item-content>
          <v-list-item-title v-text="item.title"></v-list-item-title>
        </v-list-item-content>
      </template>

      <v-list-item
        v-for="subItem in item.items"
        :key="subItem.groupPk"
        @click="group(subItem.groupPk)"
      >
        <v-list-item-content>
          <v-list-item-title> {{ subItem.name }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list-group>
  </v-list>
    <!-- </v-list-group> -->
</template>

<script>
import http from '../../http-common'

export default {
  name: "JoinedGroupList",
  data: () => ({
    items: [
      {
        action: '',
        title: '가입된 그룹',
        active: true,
        items: [
          
        ],
      },
    ],
  }),
  created() {
    // 현재 로그인 한사람의 가입 그룹 리스트
    if(this.$store.getters.getAccessToken){
        http.get('/group/currentUserGroup')
          .then((res)=>{
          this.items[0].items = res.data.object;
          console.log(this.items.items)
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

</style>