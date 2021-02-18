<template>
  <div>
    <h3>관리자 페이지</h3>
    <v-container>
      <v-row>
        <v-col>
          <GroupList></GroupList>
        </v-col>
      </v-row>
      <v-row class="mt-10">
        <v-col>
          <MemberList></MemberList>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import http from "../http-common"
import GroupList from "@/components/admin/GroupList"
import MemberList from "@/components/admin/MemberList"
export default {
  components:{
    GroupList, MemberList
  },
  created () {
    http.get('user/detailUser')
    .then((res)=>{
      console.log(res)
    })

    
  }
}
</script>

<style>

</style>