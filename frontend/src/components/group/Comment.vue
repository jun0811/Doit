<template>
  <div>
    <v-list>
      <v-subheader
        v-text="createHeader"
      ></v-subheader>
      <v-list-item>
        <v-list-item-avatar>
          <!-- 연결하면 유저 이미지로 사용 -->
          <!-- <v-img v-if="user.image" :src="user.image"></v-img> -->
          <!-- 이미지 연결 전 임시 프로필사진 -->
          <v-img src="https://images.unsplash.com/photo-1589807407471-ed87e4ab940b?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"></v-img>
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
              maxlength="70"
              v-model="newContent"
              hide-details=""
              class="comment-input"
              @focus="createBtnActive = true"
            ></v-textarea>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-row v-if="createBtnActive" class="d-flex justify-end mr-5">
        <v-btn text @click="$refs.input.reset(); createBtnActive= false;" class="cancel-btn">취소</v-btn>
        <v-btn text @click="createComment(); createBtnActive = false;" class="comment-btn">댓글</v-btn>
      </v-row>
    </v-list>
    <v-list three-line v-if="commentCount > 0">
      <v-subheader
        v-text="listHeader"
      ></v-subheader>
      <template v-for="(comment, index) in paginatedData">

        <v-divider
          v-if="comment.divider"
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
              <v-btn text @click="comment.updateActive = !comment.updateActive; " class="cancel-btn">취소</v-btn>
              <v-btn text @click="updateComment(comment)" class="comment-btn">수정</v-btn>
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
                  <v-list-item-title text style="text-align:center;">수정</v-list-item-title>
                </v-list-item>
                <v-list-item @click="deleteComment(comment.commentPk)" >
                  <v-list-item-title text style="text-align:center;">삭제</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-list-item-icon>
        </v-list-item>
      </template>
    </v-list>
    <v-pagination v-if="pageCount >=2"
      v-model="page"
      circle
      color="orange"
      :length="pageCount"
      :total-visible="5"
      class="pagination-btn"
    ></v-pagination>
     <v-row v-if="commentCount ==0"
      class="ma-4 d=flex flex-column align-center zero-comment">
        <font-awesome-icon class="mt-6 fa-2x" icon="comment-dots"/>
        <div class="my-2" >댓글이 없어요</div>
        <div class="mb-6" >첫번째 댓글을 남겨주세요.</div>
      </v-row>
  </div>
</template>

<script>

import http from "../../http-common";
import { notiType, sendNotify } from "../../api/notification/index"

export default {
  data: () => ({
    feedPk : 44,
    baseImg : 'https://ssafydoit.s3.ap-northeast-2.amazonaws.com/',
    //댓글 작성 파라미터

    newContent : '',


    createHeader: '댓글작성',
    listHeader:'댓글목록',

    comments :[],
    //로그인 유저 정보
    user :{
      nickname:'',
      userPk:'',
      image:'',
    },
    createBtnActive: false,
    //페이지 네이션 변수
    page:1,
    commentCount :0,
    listSize :5,
  }),
  props : {
    card: Object,
    groupPk: String,
  },
  created() {
    this.feedPk = this.card.feedPk
    this.getComment()
    this.getUser()
  },
  watch: {
    newContent: function () {
      if (this.newContent.length >= 70) {
        alert('댓글내용은 70자를 초과할수 없습니다.')
      }
    }
  }, 
  computed: {
    pageCount () {
      let page = Math.floor(this.commentCount / this.listSize);
      if (this.commentCount % this.listSize > 0) page += 1;
      return page;
    },
    paginatedData () {
      const start = (this.page-1) * this.listSize *2,
            end = start + this.listSize*2 -1;
      console.log(this.comments.slice(start, end))
      return this.comments.slice(start, end);
    }
  },
  methods: {
    
    getComment() {
      let cnt = 1;
      http.get(`comment/commentList?feedPk=${this.card.feedPk}`)
        .then((res)=>{
          if(res.data.status){
            const response = res.data.object
            this.commentCount = response.length
            response.sort(function (a,b) {
              return a.commentPk > b.commentPk ? -1 : 1;
            })
            this.comments = []
            this.comments = response.flatMap(comment=> {
              const commentData = {
                "userPk" : comment.userPk,
                "commentPk" : comment.commentPk,
                "uniquePk": String(comment.feedPk) + '/' + String(comment.commentPk),
                "content": comment.content,
                "profileImg" : this.baseImg + comment.image,
                "writerName": comment.nickname,
                "updateActive": false,
                }
              const divi = { divider: true, inset: true }
              if (cnt === 1) {
                cnt++
                return commentData
              } else {
                return [divi, commentData]
              }
            })
          }
      })
    },
    createComment() {
      this.createBtnActive = false;
      const params = {
        "content" : this.newContent,
        "feedPk" : this.feedPk,
        "userPk" : this.user.userPk
      }
      this.newContent =''
      http.post(`comment/createComment`, params)
        .then((res)=>{
          if(res.data.status){
            this.getComment()
            sendNotify({
                "notiType": notiType.COMMENT,
                "userPk": this.card.userPk,
                "targetId": this.card.feedPk
            })
          }
        }).catch((err) => {
          console.log(err)
        })
    },
    deleteComment(commentPk){
      http.delete(`comment/deleteComment?commentPk=${commentPk}`)
        .then((res)=>{
          if(res.data.status){
            this.getComment()
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

.cancel-btn{
  border: 3px solid  #5dc9b6;
}


.cancel-btn:hover{
  background-color: #5dc9b6;
  color:white;
}
.comment-btn {
  border: 3px solid  #F9802D;
}


.comment-btn:hover {
  background-color:#F9802D;
  color:white;
}

.zero-comment {
  color:#F9802D;
  border: 1px solid #fad6bc;
  border-radius: 5px;
}

</style>
