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
    <v-card-title >
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
    </v-card-title>
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
      <!-- 본인이면 피드 수정 --> 
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
      console.log(this.card)
      this.check = this.card.authCheck
    },
    methods: {
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

</style>