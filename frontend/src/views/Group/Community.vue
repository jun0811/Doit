<template>
  <div>
    <Header></Header>
    <!-- 그룹 간략 소개 시작 -->

    <hr>
      <v-container>
        <v-row class="d-flex justify-center">
          <v-col cols="4">
            <h3>{{user_info.name}}</h3>
            <p class="ma-0"> 멤버 : {{user_num}}/{{user_info.maxNum}}</p>
            <p class="ma-0"> {{user_info.startDate}} ~ {{user_info.endDate}}</p>
            <div class="d-flex justify-start">
              <p class="ma-0" v-for="(tag,idx) in user_info.tags" :key="idx"> #{{tag}}  </p>
            </div>
          </v-col>
          <v-col cols="4" sm-cols="2" class="d-flex align-center justify-space-around">
            <div class="group-image">
                <img src="" alt="">
            </div>
          </v-col>
        </v-row>
      </v-container>
    <hr>
    <!-- 그룹 소개 끝 -->
    <!-- 메인 content -->
    <v-btn @click="joinGroup">회원가입</v-btn>
    <v-btn @click="withdrawGroup">탈퇴하기</v-btn>
    <v-container class="pa-3 px-sm-16 py-sm-6 px-0" >
      <v-row class="d-flex justify-center">
        <v-col  cols="9" class="d-flex justify-space-around mx-16">
          <div>
            <span class="text-h6"> | 01 | </span>
          </div>
          <div >
            <v-btn text v-bind:class="{selected : feed}" class="text-h5" :model="feed" @click="FeedList"> <font-awesome-icon icon="rss-square"/>FEED</v-btn>
            <v-btn text class="text-h5" v-bind:class="{selected: users}" :model="users" @click="UserList"> <font-awesome-icon icon="users"/>MEMBERS</v-btn>
          </div>
          <div>
            <v-btn text class="text-h6" @click="feedWrite"> 글작성 </v-btn>
          </div>
        </v-col>
        <v-col  cols="9" class="d-flex justify-space-around mx-16">
          <span>1 2 3 4 5 6 7 8 9 10 11 12 
            13 14 15 16 17 18 19 20 21 22 23 24
            25 26 27 28 29 30 </span>
        </v-col>
        <v-col v-if="feed" cols="9" class="d-flex justify-space-around mx-sm-16 ">
          <div class="temp d-flex align-center flex-column">
            <p>cards</p>
            <p>cards</p>
            <p>cards</p>
            <p>cards</p>
            <p>cards</p>
            <p>cards</p>
            <p>cards</p>
          </div>
        </v-col>
        <v-col v-if="users" cols="9" class="d-flex justify-center mx-sm-16 ">
          <GroupMember></GroupMember>
          <!-- <div class="temp">
            asdasdasd
          </div> -->
        </v-col>
      </v-row>
    </v-container>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import Footer from "@/components/common/Footer";
import GroupMember from "@/components/group/GroupMember";
import http from "../../http-common"
// 해야할일 현재 날짜 받아와서 현재 달, 날짜 값으로 feed 보여주기 
export default {
  components: { Header, Footer, GroupMember },
  props: {
    groupPk: {type:String}
  },
  data() {
    return {
      user_info: {},
      user_num: 0,
      feed: true,
      users: false,
      joined : false, // 현재 유저 가입 여부 확인
    }
  },
  methods: {
    FeedList(){
      this.feed = true
      this.users = false
    },
    UserList(){
      this.feed = false
      this.users = true
    },
    feedWrite(){
      this.$router.push({name:"FeedWrite",params:{groupPk:this.groupPk}})
    },
    joinGroup(){
    http.get(`group/joinGroup?groupPk=${this.groupPk}`)
    .then((res)=>{
      if(res.data.status){
        alert('그룹에 가입하였습니다.🐱‍🚀')
      }
      })
    },
    withdrawGroup(){
      http.delete(`group/withdrawGroupUser?groupPk=${this.groupPk}`)
      .then((res)=>{
        if(res.data.status){
          alert('탈퇴 되었습니다.')
        }else{
          alert('탈퇴 실패!')
        }
      })
    }
  },
  created(){
    http.get(`group/detailGroup?groupPk=${this.groupPk}`)
    .then((res)=>{
      this.user_info= res.data.object
      console.log(this.user_info)
      this.user_num = this.user_info.users.length
    })
  }
}
</script>

<style scoped>
  .group-image {
    width: 90px;
    height: 90px;
    border-radius: 70%;
    overflow: hidden;
    border: 1px solid #FFB685
}
  .text-h5 {
    color: #E0E0E0
  }
  .temp {
    border: 1px solid;
    width: 100%;
  }
  .selected{
    color:#F9802D
  }

</style>