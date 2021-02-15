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
      <template v-for="(item, index) in items">
        <v-subheader
          v-if="item.header"
          :key="item.header"
          v-text="item.header"
        ></v-subheader>

        <v-divider
          v-else-if="item.divider"
          :key="index"
          :inset="item.inset"
        ></v-divider>

        <v-list-item
          v-else
          :key="item.title"
        >
          <v-list-item-avatar>
            <v-img :src="item.avatar"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="item.title"></v-list-item-title>
            <v-list-item-subtitle v-html="item.subtitle"></v-list-item-subtitle>
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
    items: [
        { header: '댓글목록' },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
          title: 'Brunch this weekend?',
          subtitle: `<span class="text--primary">Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?`,
        },
        { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
          title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>',
          subtitle: `<span class="text--primary">to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend.`,
        },
        { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
          title: 'Oui oui',
          subtitle: '<span class="text--primary">Sandra Adams</span> &mdash; Do you have Paris recommendations? Have you ever been?',
        },
        { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
          title: 'Birthday gift',
          subtitle: '<span class="text--primary">Trevor Hansen</span> &mdash; Have any ideas about what we should get Heidi for her birthday?',
        },
        { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg',
          title: 'Recipe to try',
          subtitle: '<span class="text--primary">Britta Holt</span> &mdash; We should eat this: Grate, Squash, Corn, and tomatillo Tacos.',
        },
      ],
    comment : {
      content:'',
      feedPk: 44,
      userPk: 8, 
    },
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
    // console.log(this.card)
  }, 
  methods: {
    createComment() {
      const params = {
        "content" : this.comment.content,
        "feedPk" : this.comment.feedPk,
        "userPk" : this.user.userPk
      }
      http.post(`comment/createComment`, params)
        .then((res)=>{
          if(res.data.status){
            console.log(res.data.object)
          }
        }).catch((err) => {
          console.log(err)
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
    getComment() {
      http.get(`comment/commentList?feedPk=${this.card.feedPk}`)
        .then((res)=>{
          if(res.data.status){
            console.log('getComment', res.data.object)
            this.comment.content =''
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
