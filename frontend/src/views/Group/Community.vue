<template>
  <div>
    <Header></Header>
    <!-- 그룹 간략 소개 시작 -->
    <hr>
      <v-container>
        <v-row class="d-flex justify-start">
          <v-col cols="3" sm-cols="2" class="d-flex align-center  justify-end ml-10">
            <div class="group-image">
                <img src="" alt="">
            </div>
          </v-col>
          <v-col cols="5">
            <h3>{{user_info.name}}</h3>
            <p class="ma-0"> 멤버 : {{user_num}}/{{user_info.maxNum}}</p>
            <p class="ma-0"> {{user_info.startDate}} ~ {{user_info.endDate}}</p>
            <div class="" style="display:inline-block" v-for="(tag,idx) in user_info.tags" :key="idx">
              <router-link :to="{name: 'GroupList', params: {word: tag}}" style="display:inline-block">
                <p class="ma-0 mr-1" > #{{tag}}</p>
              </router-link>
            </div>
          </v-col>
          <v-col cols="2" class="d-flex flex-column justify-end" v-if="this.$store.state.account.accessToken">
            
            <v-btn class="group" outlined width="75" v-if="loginUser==leader" @click="updateGroup">그룹 수정</v-btn>
            <v-btn class="group" outlined width="75" v-if="!joined" @click="joinGroup">가입하기</v-btn>
            <v-btn class="group" outlined width="75" v-if="joined && loginUser != leader" @click="withdrawGroup">탈퇴하기</v-btn>
          </v-col>
        </v-row>
      </v-container>
    <hr>
    <!-- 그룹 소개 끝 -->
    <!-- 메인 content -->


    <v-container class="pa-3 px-sm-16 py-sm-6 px-0" >
      <v-row class="d-flex justify-center">
        <v-col  cols="9" class="d-flex justify-space-around mx-16">
          <div>
            <span class="text-h6"></span>
          </div>
          <div>
            <v-btn text v-bind:class="{selected: feed}" class="text-h5" :model="feed" @click="FeedList"> <font-awesome-icon icon="rss-square"/>FEED</v-btn>
            <v-btn text v-bind:class="{selected: users}" class="text-h5" :model="users" @click="UserList"> <font-awesome-icon icon="users"/>MEMBERS</v-btn>
          </div>
          <div>
            <v-btn text class="text-h6" @click="feedWrite"> 글작성 </v-btn>
          </div>
        </v-col>
        <v-col  cols="9" class="d-flex justify-space-around mx-16"> 
          <!-- 날짜 선택 시작 -->
          <!-- start day -->
          <v-menu
            v-model="menu1"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="start"
                label="시작일"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="start"
              @input="menu1 = false"
            ></v-date-picker>
          </v-menu>
          <h2 class="ma-4"> ~ </h2>
          <!-- end day -->
          <v-menu
            v-model="menu2"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="end"
                label="종료일"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="end"
              @input="menu2 = false"
            ></v-date-picker>
          </v-menu>
          <!-- 날짜 선택 끝 -->
          <v-btn @click="feedRead" text class="ma-4 search-btn" outlined> 검색 </v-btn >
        </v-col>
        <v-col v-if="feed" cols="9" class="d-flex justify-space-around mx-sm-16">
          <div v-if="cards.length" class="d-flex justify-center align-center flex-column card">
            <FeedCard v-for="(card,idx) in cards" :key="idx" :card="card" ></FeedCard>
          </div>
          <div v-else> 
            <h2>해당 기간에는 작성된 피드가 없어요🤷‍♂️</h2>
          </div>
        </v-col>
        <v-col v-if="users" cols="10" class="d-flex justify-center flex-column align-center mx-sm-16">
          <GroupMember :groupPk="groupPk"></GroupMember>
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
import FeedCard from "@/components/group/FeedCard";
import http from "../../http-common"
// 해야할일 현재 날짜 받아와서 현재 달, 날짜 값으로 feed 보여주기 


const date=new Date()

export default {
  components: { Header, Footer, GroupMember,FeedCard },
  props: {
    groupPk: {type:Number}
  },
  data() {
    return {
      menu2: false,
      menu1: false,
      user_info: {},
      user_num: 0,
      feed: true, //
      users: false, 
      joined: false, // 현재 유저 가입 여부 확인
      start: new Date().toISOString().substr(0,10),
      end: new Date().toISOString().substr(0,10),
      year: date.getFullYear(),
      month: date.getMonth()+1,
      day :date.getDate(),
      submit: false,
      cards: [],
      leader:'',
      loginUser:'',
      tags:[],
    }
  },
  watch: {
    end(){
      this.submit = false
      const start = new Date(this.start)
      const end = new Date(this.end)
      const TODAY = new Date() // 
      let dateDiff = Math.ceil((end.getTime()-start.getTime())/(1000*3600*24));
      let check = Math.ceil((end.getTime()-TODAY.getTime())/(1000*3600*24));
      if (check>=1){
        alert('미래를 볼순 없어요!')
        this.end = `${this.year}-${String(this.month).padStart(2, "0")}-${String(this.day).padStart(2, "0")}`
        this.submit = true
      }
      else if (dateDiff<0){
        alert('시작 날짜 이후 날짜를 선택해주세요')
        this.end = this.start
        this.submit = true
      }else{
        this.submit = true
      }
    },
    start(){
      this.submit = false
      const start = new Date(this.start)
      const end = new Date(this.end)
      const TODAY = new Date() // 
      let dateDiff = Math.ceil((end.getTime()-start.getTime())/(1000*3600*24));
      let check = Math.ceil((start.getTime()-TODAY.getTime())/(1000*3600*24));
      if (check>=1){
        alert('미래를 볼순 없어요!')
        this.start = `${this.year}-${String(this.month).padStart(2, "0")}-${String(this.day).padStart(2, "0")}`
        this.submit = true

      }
      else if (dateDiff<0){
        alert('끝 날짜 이전 날짜를 선택해주세요')
        this.start=  this.end
        this.submit = true
      }else{
        this.submit = true
      }
    }
  }
  ,
  methods: {
    updateGroup(){
      this.$router.push({name:"GroupUpdate",params:{groupPk:Number(this.groupPk)}})
    },
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
        this.$router.go()
      }
      })
    },
    withdrawGroup(){
      http.delete(`group/withdrawGroupUser?groupPk=${this.groupPk}`)
      .then((res)=>{
        if(res.data.status){
          alert('탈퇴 되었습니다.')
          this.$router.push("/")
        }else{
          alert('탈퇴 실패!')
        }
      })
    },
    feedRead(){
      const pk = Number(this.groupPk)
      http.get(`feed/groupFeed?end=${this.end}&groupPk=${pk}&start=${this.start}`)
      .then((res)=>{
        // console.log(res.data.object)
        this.cards = res.data.object
        console.log(this.cards)
      })
    },
  },
  created(){
    http.get(`group/detailGroup?groupPk=${this.groupPk}`)
    .then((res)=>{
        this.user_info= res.data.object
        this.user_num = this.user_info.users.length
        this.leader = res.data.object.leader
        this.tags = res.data.object.tags
        this.loginUser = this.$store.state.account.userpk
    }),
    http.get('group/currentUserGroup')
      .then((res)=>{
        this.joined = res.data.object.some((group)=>{
          if(this.groupPk == group.groupPk){
            return true
          }
        })
      }),
    http.get(`feed/groupFeed?end=${this.end}&groupPk=${this.groupPk}&start=${this.start}`)
    .then((res)=>{
      this.cards = res.data.object
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
  /* .temp {
    border: 1px solid;
    width: 100%;
  } */
  .selected{
    color:#F9802D
  }
  .group{
    border: 1px solid #F9802D;
    color: #F9802D
  }
  .hashtag-del-btn {
    color:#FFE0B2;
    outline: transparent;
  }

  .search-btn {
    border : 1ps solid grey;
  }
  .card{
    width: 120%;
  }
</style>