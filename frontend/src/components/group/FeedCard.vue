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
    <!-- <v-card-title v-if="writer">
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
          <v-btn class="green--text" @click="feedUpdate" text width="100%">피드 수정 </v-btn>
          <v-btn class="red--text" @click="feedDelete" text width="100%">피드 삭제 </v-btn>    
        </v-list>
      </v-menu>
    </v-card-title> -->
    <v-img
      height="250"
      src="https://wonderfulmind.co.kr/wp-content/uploads/2018/05/knitting.jpg"
    ></v-img>

    <v-card-title>{{this.card.content}}</v-card-title>

    <v-card-text>
      <v-row>
        <v-col class="col-8">
          작성자:<span> {{this.card.writer}}</span>
         <div> 작성날짜: {{this.card.createDate.slice(0,10)}}</div>
        </v-col>
        <v-col  class="col-4 d-flex justify-end">
          <img v-if="check" src="@/assets/img/stamp.png" class="stamp">
        </v-col>
      </v-row>
      <v-row class="d-flex justify-space-between  align-center mx-3">
        <!-- 본인이면 피드 수정 --> 
        <div v-if="!writer">
          <v-btn outlined class="my-3 btn" v-if="!auth" @click="accept" >인증</v-btn>
          <span class="my-3" v-else >인증을 해주셨습니다.</span>
          <!-- 다른 유저면 인증/미인증으로 구분 -->
        </div>
        <div v-if="writer">
          <v-btn outlined class="my-3 btn green-btn" @click="feedUpdate">피드 수정 </v-btn>
          <v-btn outlined class="my-3 btn red-btn" @click="feedDelete">피드 삭제 </v-btn>    
        </div>
        <v-btn
        icon
        @click="show=!show">
          <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
        </v-btn>
      </v-row>
    </v-card-text>
     <v-expand-transition>
      <div v-show="show">
        <v-divider></v-divider>
        <v-card-text>
          <Comment :card="card"></Comment>
        </v-card-text>
      </div>
    </v-expand-transition>
    <v-divider class="mx-4"></v-divider>
  </v-card>
</template>

<script>
import Comment from '../../components/group/Comment.vue'
import http from '../../http-common'

  export default {
    props: {
      card: Object
    },
    components: {
      Comment
    },
    data: () => ({
      loading: false,
      selection: 1,
      message: "미인증",
      auth: false, // 인증 피드 확인 변수
      writer: false, // 본인 여부 판단 변수
      check: false,
      show:false,
    }),
    created(){
      this.check= (this.card.authCheck ==='true' ? true:false) 
      // 본인 여부
      if(this.card.userPk == sessionStorage.getItem("userpk")) {
        this.writer = true
      }
      // 인증 확인 여부
      this.auth = this.card.authUsers.some((res)=>{
        if(sessionStorage.getItem("userpk") == res.userPk){
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
        .then(()=>{
          this.$router.go()
          alert("삭제되었습니다.")
          }
        )
      },
      feedUpdate(){
        this.$router.push({name:"FeedUpdate",params:{feedPk:Number(this.card.feedPk),"content":this.card.content, "writer":this.card.writer}})
      }
    }
  }
</script>

<style scoped>
.stamp{
  width: 50px;
}
span{
color: #F9802D
}
.btn{
  border: 1px solid #F9802D;
  color: #F9802D
}
.green-btn:hover {  
  color:green;
  border:1px solid green;
}

.red-btn:hover {  
  color:red;
  border:1px solid red;
}


</style>