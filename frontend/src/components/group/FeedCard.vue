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
      src="https://wonderfulmind.co.kr/wp-content/uploads/2018/05/knitting.jpg"
    ></v-img>

    <v-card-title>{{this.card.content}}</v-card-title>

    <v-card-text>
      <div>{{this.card.writer}}</div>
      <div>작성날짜: {{this.card.createDate.slice(0,10)}}</div>
      <v-btn v-if="this.card.writer == this.$store.getters.getName" class="my-3" >피드 수정</v-btn> 
      <!-- 본인이면 피드 수정 -->
      <div v-else>
        <v-btn class="my-3" v-if="!auth" @click="accept">미인증</v-btn>
        <v-btn class="my-3" v-else>인증</v-btn>
        <!-- 다른 유저면 인증/미인증으로 구분 -->
      </div>
    </v-card-text>

    <v-divider class="mx-4"></v-divider>
  </v-card>
</template>

<script>
import http from '../../http-common'

  export default {
    props: {
      card: Object
    },
    data: () => ({
      loading: false,
      selection: 1,
      message: "미인증",
      auth: false, // 인증 피드 확인 변수
      writer: false // 본인 여부 판단 변수
    }),
    created(){
      // 본인 여부
      if(this.card.userPk === sessionStorage.getItem("userPk")) this.writer = true
      // 인증 확인 여부
      this.auth = this.card.authUsers.some((res)=>{
        if(sessionStorage.getItem("userpk") == res.userPk){
          console.log("인증")
          return true
        }
      })
    },
    methods: {
      accept(){
        console.log(this.card)
        if(this.card.writer == this.$store.getters.getName) console.log(this.card.feedPk)
        else{
          http.get(`feed/authCheckFeed?feedPk=${this.card.feedPk}`)
          .then(()=>
            this.auth= true
          )
        }
      }  
    }
  }
</script>

<style>

</style>