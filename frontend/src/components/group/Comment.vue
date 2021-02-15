<template>
  <div>
    <v-list>
      <v-list-item>
        <v-list-item-avatar>
          <v-img src="https://images.unsplash.com/photo-1578452496128-ab2d0703a8de?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title >{{user.nickname}}</v-list-item-title>
          <v-list-item-subtitle> 
            <v-textarea
              filled
              color="orange"
              auto-grow
              label="댓글입력"
              rows="2"
              row-height="20"
              ref="input"
              v-model="comment.content"
              hide-details=""
              class="comment-input"
              @focus="buttonActivate()"
            ></v-textarea>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-row v-if="btnActive" class="d-flex justify-end mr-5">
        <v-btn @click="$refs.input.reset(); buttonDeactivate();" class="cancel-btn">취소</v-btn>
        <v-btn @click="createComment; buttonDeactivate();" class="comment-btn">댓글</v-btn>
      </v-row>
    </v-list>
    <v-list three-line>
      <template v-for="(comment, index) in comments">
        <v-subheader
          v-if="comment.header"
          :key="comment.header"
          v-text="comment.header"
        ></v-subheader>

        <v-divider
          v-else-if="comment.divider"
          :key="index"
          :inset="comment.inset"
        ></v-divider>

        <v-list-item
          v-else
          :key="comment.writerName"
        >
          <v-list-item-avatar>
            <v-img :src="comment.profileImg"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="comment.writerName"></v-list-item-title>
            <v-list-item-subtitle v-html="comment.content"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </template>
    </v-list>
  </div>
</template>

<script>

import http from "../../http-common";


export default {
  data: () => ({
    feedPk : 44,
    baseImg : 'https://ssafydoit.s3.ap-northeast-2.amazonaws.com/',
    comment : {
      content : '',
      feedPk : 44,
      userPk : 8
    }, //작성자의 코멘트

    comments :[],
    user :{}, //nickname, userPk, image가 들어있음
    btnActive:false,
  }),
  props : {
    card: Object,
  },
  created() {
    this.user = this.$store.getters.getName
    this.getComment()
    this.getUser()
  }, 
  methods: {
    createComment() {
      const params = {
        "content" : this.comment.content,
        "feedPk" : this.comment.feedPk,
        "userPk" : this.user.userPk
      }
      this.comment.content =''
      http.post(`comment/createComment`, params)
        .then((res)=>{
          if(res.data.status){
            console.logt(res.data.object)
          }
        }).catch((err) => {
          console.log(err)
        })
    }, 
    getComment() {
      http.get(`comment/commentList?feedPk=${this.card.feedPk}`)
        .then((res)=>{
          if(res.data.status){
            console.log('getComment', res.data.object)
            const response = res.data.object
            this.comments = []
            let cnt = 0;
            this.comments = response.flatMap(comment=> {
              if (cnt === 0) {
                cnt = cnt + 1
                return [
                  { "header": '댓글목록' },
                  {
                  "content": comment.content,
                  "profileImg" : this.baseImg + comment.image,
                  "writerName": '유저이름',
                  }
                ]
              } else {
                return [
                  { divider: true, inset: true },
                  {
                  "content": comment.content,
                  "profileImg" : this.baseImg + comment.image,
                  "writerName": '유저이름',
                  }
                ]
              }
            })
            console.log(this.comments)
          }
      })
    },
    getUser() {
      http.get(`user/detailUser`)
        .then((res)=>{
          if(res.data.status){
            this.user = res.data.object
          }
      })
    },
    buttonActivate() {
      this.btnActive = true;
    },
    buttonDeactivate() {
      this.btnActive = false;
    }
  }
}
</script>

<style scoped>
.cancel-btn {
  width: 70px;
}



</style>
