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
              @focus="createBtnActive = !createBtnActive"
            ></v-textarea>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-row v-if="createBtnActive" class="d-flex justify-end mr-5">
        <v-btn @click="$refs.input.reset(); createBtnActive= !createBtnActive;" class="cancel-btn">취소</v-btn>
        <v-btn @click="createComment(); createBtnActive != createBtnActive;" class="comment-btn">댓글</v-btn>
      </v-row>
    </v-list>
    <v-list three-line>
      <template v-for="(comment, index) in comments">
        <v-subheader
          v-if="comment.header"
          :key="comment.header"
          v-text="header"
        ></v-subheader>

        <v-divider
          v-else-if="comment.divider"
          :key="index"
          :inset="comment.inset"
        ></v-divider>

        <v-list-item
          v-else
          :key="comment.uniquePk"
        >
          <v-list-item-avatar>
            <v-img :src="comment.profileImg"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="comment.writerName"></v-list-item-title>
            <v-list-item-subtitle v-if="!comment.updateActive" v-html="comment.content"></v-list-item-subtitle>
             <v-list-item-subtitle v-if="comment.updateActive">  
              <v-textarea
                filled
                color="orange"
                auto-grow
                label="댓글수정"
                rows="2"
                row-height="20"
                v-model="comment.content"
                hide-details=""
              ></v-textarea>
             </v-list-item-subtitle> 
            <v-list-item-title v-if="comment.updateActive" class="d-flex justify-end mr-1">
              <v-btn @click="comment.updateActive = !comment.updateActive; " class="cancel-btn">취소</v-btn>
              <v-btn @click="updateComment(comment)" class="comment-btn">수정</v-btn>
            </v-list-item-title>
          </v-list-item-content>
          <v-list-item-icon class="mx-0 my-auto d-flex justify-end">
            <v-menu
              bottom
              rightcomment.updateActive =!comment.updateActive
              :nudge-width="100"
              transition="slide-y-transition"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  v-bind="attrs"
                  v-on="on"
                >
                  <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
              </template>

              <v-list>
                <v-list-item @click="comment.updateActive =!comment.updateActive">
                  <v-list-item-title style="text-align:center;">수정</v-list-item-title>
                </v-list-item>
                <v-list-item @click="deleteComment(comment.commentPk)" >
                  <v-list-item-title style="text-align:center;">삭제</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
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
    //댓글 작성 파라미터
    commentWrite : {
      content : '',
      feedPk : 0,
    },

    header:'댓글목록',

    comments :[],
    //로그인 유저 정보
    user :{
      nickname:'',
      userPk:'',
      image:'',
    },
    createBtnActive: false,

  }),
  props : {
    card: Object,
  },
  created() {
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
      this.commentWrite.content =''
      http.post(`comment/createComment`, params)
        .then((res)=>{
          if(res.data.status){
            this.getComment()
          }
        }).catch((err) => {
          console.log(err)
        })
    },
    deleteComment(commentPk){
      http.delete(`comment/deleteComment?commentPk=${commentPk}`)
        .then((res)=>{
          if(res.data.status){
            console.log(res.data)
          }
        }).catch((err) => {
          console.log(err)
        })
    },
    updateComment(comment) {
      const params = {
        "commentPk" : comment.commentPk,
        "content" : comment.content,
        "feedPk" : comment.feedPk,
        "userPk" : comment.userPk,
      }
      http.put(`comment/updateComment`, params)
        .then((res)=>{
          if(res.data.status){
            comment.updateActive = false;
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
                  "commentPk" : comment.commentPk,
                  "uniquePk": String(comment.feedPk) + '/' + String(comment.commentPk),
                  "content": comment.content,
                  "profileImg" : this.baseImg + comment.image,
                  "writerName": comment.nickname,
                  "updateActive": false,
                  }
                ]
              } else {
                return [
                  { divider: true, inset: true },
                  {
                  "userPk" : comment.userPk,
                  "commentPk" : comment.commentPk,
                  "uniquePk": String(comment.feedPk) + '/' + String(comment.commentPk),
                  "content": comment.content,
                  "profileImg" : this.baseImg + comment.image,
                  "writerName": comment.nickname,
                  "updateActive": false,
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
  }
}
</script>

<style scoped>
.cancel-btn {
  width: 70px;
}



</style>
