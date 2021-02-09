<template>
  <v-row
    class="my-6 mx-12 d-flex align-center flex-wrap justify-space-between"
  >
    <v-col cols="5" class="profile-card d-flex align-center" v-for="(item, idx) in paginatedData" :key="idx">
      <img class="profile-image" :src="item.userimage">
      <span class="mx-2">{{item.nickname}}</span>
      <v-btn normal x-small class="px-0">강퇴</v-btn>
    </v-col>
  </v-row>
</template>

<script>
import http from "../../http-common";


  export default {
    data: () => ({
      loading: false,
      selection: 1,
      users :[],
      category:'',
      content:'',
      leader:'',
      groupName:'',
      totalNum:'',
      endDate:'',
      createDate:'',
    }),
    props: {
      page:Number,
      groupPk:String,
    }, 
    created() {
      http.get(`/group/detailGroup?groupPk=${this.groupPk}`)
      .then((res)=>{
        console.log(res.data.object)
        this.users = res.data.object.users
        this.category = res.data.object.category
        this.content = res.data.object.content
        this.leader = res.data.object.leader
        this.groupName = res.data.object.name
        this.totalNum = res.data.object.totalNum
        this.endDate = res.data.object.endDate
        this.createDate = res.data.object.createDate
      })
    },
    computed: {
      paginatedData () {
        // console.log(this.page)
        const start = (this.page-1) * 4,
              end = start + 4;
        // console.log(this.member.slice(start, end))
        return this.users.slice(start, end);
      }
    }
  }
</script>

<style scoped>

.profile-card {
  /* width: px; */
  height: 50px;
  margin: 10px 20px;
  padding: 0px 20px;
}

.profile-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 1px solid orange;
}

</style>