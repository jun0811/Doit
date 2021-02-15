<template>
  <div>
    <v-list>
      <v-list-item>
        <v-list-item-avatar>
          <v-img v-if="user.image" :src="user.image"></v-img>
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
              v-model="commentWrite.content"
              hide-details=""
              class="comment-input"
              @focus="buttonActivate()"
            ></v-textarea>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-row v-if="btnActive" class="d-flex justify-end mr-5">
        <v-btn @click="$refs.input.reset(); buttonDeactivate();" class="cancel-btn">취소</v-btn>
        <v-btn @click="createComment(); buttonDeactivate();" class="comment-btn">댓글</v-btn>
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
          :key="comment.commentPk"
        >
          <v-list-item-avatar>
            <v-img :src="comment.profileImg"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="comment.writerName"></v-list-item-title>
            <v-list-item-subtitle v-html="comment.content"></v-list-item-subtitle>
          </v-list-item-content>
          <v-list-item-icon 
            v-if="comment.userPk===user.userPk"
            class="mx-0 my-auto d-flex justify-end">
            <font-awesome-icon icon="ellipsis-v"/>
          </v-list-item-icon>
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
    commentWrite : {
      content : '',
      feedPk : 44,
    }, //작성자의 코멘트

    comments :[],
    user :{
      nickname:'',
      userPk:'',
      image:'',
    }, //nickname, userPk, image가 들어있음
    btnActive:false,
  }),
  props : {
    card: Object,
  },
  created() {
    console.log(this.card)
    this.commentWrite.feedPk = this.card.feedPk
    this.getComment()
    this.getUser()
  }, 
  methods: {
    createComment() {
      const params = {
        "content" : this.commentWrite.content,
        "feedPk" : this.commentWrite.feedPk,
        "userPk" : this.user.userPk
      }
      this.comment.content =''
      http.post(`comment/createComment`, params)
        .then((res)=>{
          if(res.data.status){
            console.log(res.data.object)
          }
        }).catch((err) => {
          console.log(err)
        })
    }, 
    getComment() {
      http.get(`comment/commentList?feedPk=${this.card.feedPk}`)
        .then((res)=>{
          if(res.data.status){
            const response = res.data.object
            response.sort(function (a,b) {
              return a.commentPk > b.commentPk ? -1 : 1;
            })
            this.comments = []
            let cnt = 0;
            this.comments = response.flatMap(comment=> {
              if (cnt === 0) {
                cnt = cnt + 1
                return [
                  { "header": '댓글목록' },
                  {
                  "userPk" : comment.userPk,
                  "commentPk": String(comment.feedPk) + '/' + String(comment.commentPk),
                  "content": comment.content,
                  "profileImg" : this.baseImg + comment.image,
                  "writerName": comment.nickname,
                  }
                ]
              } else {
                return [
                  { divider: true, inset: true },
                  {
                  "userPk" : comment.userPk,
                  "commentPk": String(comment.feedPk) + '/' + String(comment.commentPk),
                  "content": comment.content,
                  "profileImg" : this.baseImg + comment.image,
                  "writerName": comment.nickname,
                  }
                ]
              }
            })
          }
      })
    },
    getUser() {
      http.get(`user/detailUser`)
        .then((res)=>{
          if(res.data.status){
            this.user.nickname = res.data.object.nickname
            this.user.userPk = res.data.object.userPk
            this.user.image = this.baseImg + res.data.object.image
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
