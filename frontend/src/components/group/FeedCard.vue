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
    <!-- 모달 : 수정 삭제 -->
    <v-card-title v-if="writer">
      <v-spacer></v-spacer>
      <v-menu
        bottom
        left
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            text-dark
            icon
            v-bind="attrs"
            v-on="on"
          ><v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-btn class="green--text" text width="100%">피드 수정 </v-btn>
          <v-btn class="red--text" @click="feedDelete" text width="100%">피드 삭제 </v-btn>    
        </v-list>
      </v-menu>
    </v-card-title>
    <v-img
      height="250"
      src="https://wonderfulmind.co.kr/wp-content/uploads/2018/05/knitting.jpg"
    ></v-img>

    <v-card-title>{{this.card.content}}</v-card-title>

    <v-card-text>
      <v-row>
       <v-col class="col-8">
         <span>{{this.card.writer}}</span>
          <div>작성날짜: {{this.card.createDate.slice(0,10)}}</div>
        </v-col>
        <v-col  v-if="check" class="col-4 d-flex justify-end">
          <img src="@/assets/img/stamp.png" class="stamp">
        </v-col>
      </v-row>
      <!-- 본인이면 피드 수정 -->
      <div v-if="!writer">
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
      writer: false, // 본인 여부 판단 변수
      check: false
    }),
    created(){
      this.check=this.card.authCheck 
      // 본인 여부
      if(this.card.userPk == sessionStorage.getItem("userpk")) {
        this.writer = true
        console.log("111")
      }
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
        if(this.card.writer == this.$store.getters.getName) console.log(this.card.feedPk)
        else{
          http.get(`feed/authCheckFeed?feedPk=${this.card.feedPk}`)
          .then(()=>
            this.auth= true
          )
        }
      },
      feedDelete(){
        http.delete(`feed/deleteFeed?feedPk=${this.card.feedPk}`)
        .then((res)=>{
          this.$router.go()
          console.log(res)
          alert("삭제되었습니다.")
          }
        )
      },
    }
  }
</script>

<style scoped>
 .stamp{
   width: 50px;
 }

</style>