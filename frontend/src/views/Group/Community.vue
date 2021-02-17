<template>
  <div>
    <Header></Header>
    <!-- 그룹 간략 소개 시작 -->
      <v-container>
        <v-row class="d-flex justify-center group-intro">
          <v-col cols="4" sm="3" class="d-flex align-center justify-start">
            <div class="group-image">
                <img class="profile" v-if="!image" src="" alt="">
                <img class="profile" v-else :src="`http://ssafydoit.s3.ap-northeast-2.amazonaws.com/` + image" alt="">
            </div>
          </v-col>
          <v-col cols="8" sm="5">
            <h3>{{user_info.name}}</h3>
            <p class="ma-0"> 멤버 : {{user_num}}/{{user_info.maxNum}}</p>
            <p class="ma-0"> {{user_info.startDate}} ~ {{user_info.endDate}}</p>
            <div class="" style="display:inline-block" v-for="(tag,idx) in user_info.tags" :key="idx">
              <router-link :to="{name: 'GroupList', params: {word: tag}}" style="display:inline-block">
                <p class="ma-0 mr-1" > #{{tag}}</p>
              </router-link>
            </div>
          </v-col>
          <v-col cols="3" sm="2" class="d-flex flex-column justify-end" v-if="this.$store.state.account.accessToken">
            <v-btn class="group" outlined width="75" v-if="loginUser==leader" @click="updateGroup">그룹 수정</v-btn>
            <v-btn class="group" outlined width="75" v-if="!joined" @click="joinGroup">가입하기</v-btn>
            <v-btn class="group" outlined width="75" v-if="joined && loginUser != leader" @click="withdrawGroup">탈퇴하기</v-btn>
          </v-col>
        </v-row>
      </v-container>
    <!-- 그룹 소개 끝 -->
    <!-- 메인 content -->
    <v-container class="pa-3 px-sm-16 py-sm-6 px-0" style="width: 100%">
      <v-row class="d-flex justify-center" >
        <v-col  cols="12" sm="5" v-if="joined" class="mt-4">

            <v-tabs color="orange" class="d-flex justify-center">
              <!-- <v-tab>자유 피드</v-tab> -->
              <v-tab @click="UserList">그룹정보</v-tab>
              <v-tab @click="FeedList">인증피드</v-tab>
            </v-tabs>            
        </v-col>       
        <v-col v-if="feed && joined" cols="12" class="d-flex flex-column justify-space-around align-center mx-sm-16">
          <v-row>
            <v-col  cols="12" sm="12" class="d-flex justify-space-around"> 
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
                    class="date-input"
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
                    class="date-input"
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
              <div class="d-flex align-center">
                <v-btn @click="feedRead" text class="search-btn ml-sm-2" outlined> 검색 </v-btn >
              </div>
            </v-col>
          </v-row>
          <v-row class="" style="width: 100%">
            <v-col v-if="cards.length" cols="12" sm="10" class="d-flex justify-end pr-sm-16">
              <v-btn text x-large class="mt-5" @click="feedWrite"> 글작성 <img src="@/assets/img/edit.png" alt="writing" class="write-icon"></v-btn>
            </v-col>
            <!-- <v-col cols="1" class="d-flex align-center">
              
            </v-col> -->
          </v-row>
          <v-row class="d-flex justify-center align-center flex-column card">
            <v-col cols="10" v-if="cards.length" class="feeds">
              <FeedCard v-for="(card,idx) in cards" :key="idx" :card="card" ></FeedCard>
            </v-col>
            <v-col cols="10" v-else>
              <v-row  class="d-flex justify-center">
                <v-col cols="12">
                  <h3 class="mt-16" style="text-align:center;">해당 기간에는 작성된 피드가 없어요🤷‍♂️</h3>
                </v-col>
              </v-row> 
              <v-row  class="d-flex justify-center">
                <v-btn text x-large class="mt-5" @click="feedWrite"> 글작성 <img src="@/assets/img/edit.png" alt="writing" class="write-icon"></v-btn>
              </v-row>
            </v-col>
          </v-row>
        </v-col>
        <v-col v-if="users" cols="11" class="d-flex justify-center align-center flex-column">
          <GroupMember :groupPk="groupPk"></GroupMember>
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
    groupPk: {type:String},
    notiFeed : Boolean,
    notiGroup: Object,
  },
  data() {
    return {
      full: false,
      image: "",
      menu2: false,
      menu1: false,
      user_info: {},
      user_num: 0,
      feed: false, //
      users: true, 
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
    },
    groupPk(){
      this.start = new Date().toISOString().substr(0,10);
      this.end = new Date().toISOString().substr(0,10);
      http.get(`group/detailGroup?groupPk=${this.groupPk}`)
      .then((res)=>{
          this.user_info= res.data.object
          this.user_num = this.user_info.users.length
          this.leader = res.data.object.leader
          this.tags = res.data.object.tags
          this.loginUser = this.$store.state.account.userpk
          this.image = res.data.object.image

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
  ,
  methods: {
    updateGroup(){
      this.$router.push({name:"GroupUpdate",params:{groupPk:this.groupPk}})
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
    // 그룹가입
    joinGroup(){
    http.get(`group/joinGroup?groupPk=${this.groupPk}`)
    .then((res)=>{
      console.log(res)
      if(res.data.status){
        alert('그룹에 가입하였습니다.🐱‍🚀')
        // this.$router.go()
      }else{
        if(res.data.data =="인원이 가득 찼습니다.") alert('가입인원이 최대입니다.')
        else alert('탈퇴하였가나 추방당한 그룹입니다.')
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
        // console.log(this.cards)
      })
    },
  },
  created(){
    http.get(`group/detailGroup?groupPk=${this.groupPk}`)
    .then((res)=>{
      console.log(res)
      this.user_info= res.data.object
      this.user_num = this.user_info.users.length
      this.leader = res.data.object.leader
      this.tags = res.data.object.tags
      this.loginUser = this.$store.state.account.userpk
      this.image = res.data.object.image
    }),
    http.get('group/currentUserGroup')
      .then((res)=>{
        this.joined = res.data.object.some((group)=>{
          if(this.groupPk == group.groupPk){
            return true
          }
        })
        if (!this.joined) {
          this.users = true;
        }
      }),
    http.get(`feed/groupFeed?end=${this.end}&groupPk=${this.groupPk}&start=${this.start}`)
    .then((res)=>{
      this.cards = res.data.object
    })
    if (this.notiFeed) {
      console.log('noti', this.groupPk)
      this.FeedList()
      console.log('notigroup', this.notiGroup.feedPk)
      

      // const year = tempDate[0]
      // const month = tempDate[1]
      // const day = tempDate[2]
      const year = 2021
      const month = 2
      const day = 16
      this.start = `${year}-${String(month).padStart(2, "0")}-${String(day).padStart(2, "0")}`
      this.end = this.start
      this.feedRead()
      // const tempDate = this.notiGroup.createDate
      // const feedDate = tempDate[0] + '-' + tempDate[1] + '-' + tempDate[2]
      // console.log('feedDate', feedDate)
      // this.end = feedDate
      // this.start = feedDate

    }
  }
}
</script>

<style scoped>
  .group-intro {
    border: 1px solid #E0E0E0;
    margin-left: 240px;
    margin-right: 240px;
  } 

  @media only screen and (min-width: 300px) and (max-width: 599px) {
  .group-intro {
    border: 1px solid #E0E0E0;
    margin-left: 0px;
    margin-right: 0px;
    } 
  }
  .group-image {
    width: 90px;
    height: 90px;
    border-radius: 70%;
    overflow: hidden;
    border: 1px solid #FFB685
  }
  .icon-style {
    width: 6%;
  }
  .profile {
    width:100%;
    height:100%;
    object-fit: cover;
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

  .date-input >>> input {
    text-align: center;
  }

  .write-icon {
    width:20px; 
    height:20px;
    margin-left: 5px;
  }
</style>