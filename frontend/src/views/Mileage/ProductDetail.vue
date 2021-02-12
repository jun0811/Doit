<template>
<div>
  <Header></Header>
  <NavBar></NavBar>
    <v-container class="container-width">
        <v-row>
          <v-col class="img-wrapper">
            <img class="product-img" src="https://images.unsplash.com/photo-1498050108023-c5249f4df085?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1052&q=80" alt="product image">
          </v-col>
        </v-row>
        <v-row class="my-3 px-6 d-flex align-center">
          <span class="profile-wrapper pa-0 d-flex ">
            <img
            class="profile-img" 
            src="https://images.unsplash.com/photo-1529092419721-e78fb7bddfb2?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=964&q=80" 
            alt="글쓴이 이미지"
            >
          </span>
          <span class="pl-4">
            {{product.nickname}}
          </span>
        </v-row>
        <v-row class="d-flex justify-center mt-2">
          <hr class="mb-4 line">
        </v-row>
        <v-row class="d-flex flex-column align-start mx-0 mx-sm-1">
          <v-row class="prd-title pb-0">
              {{product.title}}
          </v-row>
          <v-row class="prd-category">카테고리: {{product.category}}</v-row>
          <v-row class="prd-mileage">{{product.mileage}} 마일리지</v-row>
          <v-row class="prd-content py-5">{{product.content}}</v-row>
        </v-row>
        <v-row class="d-flex justify-center">
          <hr class="mt-4 line">
        </v-row>
        <v-row class="d-flex justify-center">
          <v-col class="d-flex justify-center">
            <v-dialog
              transition="dialog-bottom-transition"
              max-width="600"
              hide-overlay
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="deal-btn"
                  text
                  large
                  v-bind="attrs"
                  v-on="on"
                  @click="chatting"
                >채팅으로 거래하기</v-btn>
              </template>
              <template v-slot:default="dialog">
                <v-card>
                  <v-toolbar
                    class="toolbar"
                    elevation="0"
                    style="height: 65px;"
                  >
                  <v-container>
                    <v-row class="py-5">
                      <v-col cols="2" class="mt-1">
                        <img 
                          src="https://upload.wikimedia.org/wikipedia/commons/f/f1/2ChocolateChipCookies.jpg" 
                          alt="product-img"
                          class="prd-img"
                        >
                      </v-col>
                      <v-col cols="7" class="name-price-style d-flex flex-column justify-center mt-10">
                        <v-row class="mb-3">
                          {{ productName }}
                        </v-row>
                        <v-row class="mb-1 price-style">
                          {{ productPrice }} 마일리지
                        </v-row>
                      </v-col>
                      <v-col cols="3" class="mt-5">
                        <v-card-actions class="justify-end">
                          <v-btn
                            text
                            @click="dialog.value = false"
                          >Close</v-btn>
                        </v-card-actions>                        
                      </v-col>
                    </v-row>   
                  </v-container>               
                  </v-toolbar>
                  <v-card-text style="padding-top: 100px; padding-bottom: 90px; min-height:900px;">
                    <div v-for="(m, idx) in msg" :key="idx">
                      <!-- 메세지 보낸 사람이 현재 유저일 경우(user1일 경우) user1의 닉네임 표시 -->
                      <div v-if="m.userPk==user1['userPk']" class="d-flex flex-column align-end">
                        <v-row class="mb-3 mr-3">
                          {{user1['userNick']}}
                        </v-row>
                        <v-row class="mb-6 mr-3 my-message-box">
                          {{m.message}}
                        </v-row>
                      </div>
                      <!-- 메세지 보낸 사람이 상대방일 경우(user2일 경우) user2의 닉네임 표시 -->
                      <div v-else class="d-flex flex-column align-start">
                        <v-row class="ml-3 mb-3">
                          {{user2['userNick']}}
                        </v-row>
                        <v-row class="ml-3 mb-6 other-message-box">
                          {{m.message}}
                        </v-row>
                      </div>
                    </div>
                  </v-card-text>
                  <!-- <div > -->
                    <v-container class="input-style">
                      <v-row class="mt-3 mb-3">
                        <v-col cols="8" sm="10">
                          <v-text-field
                            label="메세지를 입력하세요."
                            v-model="content" 
                            single-line
                            outlined
                            @keyup.enter="sendMessage()"
                            background-color="white"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="2" class="d-flex justify-start">
                          <div class="send-icon">
                            <font-awesome-icon 
                              :icon="['far', 'paper-plane']"
                              @click="sendMessage()"                         
                            /> 
                          </div>
                          <!-- <button @click="sendMessage()">SEND</button> -->
                        </v-col>
                      </v-row> 
                    </v-container>                 
                  <!-- </div> -->
                    <!-- <v-container style="position:fixed; bottom: 0;"> -->

                    <!-- </v-container> -->
                </v-card>
              </template>
            </v-dialog>
          </v-col>
        </v-row>
    </v-container>
  <Footer></Footer>

</div>
</template>

<script>
import "@/assets/css/profile.css";
import Header from "@/components/common/Header.vue";
import NavBar from "@/components/common/NavBar.vue";
import Footer from "@/components/common/Footer.vue";
import http from "../../http-common";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: "ProductDetail",
  components: {
    Header,
    NavBar,
    Footer,
  },
  data() {
    return {
      product:'',
      user : 'nickname',
      roomid : 16,
      id : 84,
      productImg: '',
      idx:0,
      msg:[],
      room: '',
      content:'',
      stompClient:null,
      productName: '',
      productPrice: '',
      user1: {'userPk' : 0, 'userNick' : ''},
      user2: {'userPk' : 0, 'userNick' : ''},
    }
  },
  props:{
    product_id: Number,
  },
  created() {
    http.get(`/product/${this.product_id}`)
    .then((res)=>{
      console.log(res.data.object)
      this.product = res.data.object
    })   
  },
  computed: {
  },
  methods: {
    // chatting() {
    //   this.$router.push("/chat")
    // },
    sendMessage(){
    // console.log(this.content);
     if(this.content.trim() !='' && this.stompClient!=null) {
        let chatMessage = {
          'message': this.content,
          'roomPk' : this.roomid,
        }
        this.stompClient.send("/publish/chat", JSON.stringify(chatMessage),{"accessToken": this.$store.getters.getAccessToken})
        this.content=''
    }
    },
    connect() {
      const serverURL = "http://localhost:8080/ws";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)

      var headers = {
        'accessToken': this.$store.getters.getAccessToken
        };
       this.stompClient.connect(
        headers,
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // console.log(this.roomid)
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/subscribe/chat/room/"+ this.roomid , res => {
            console.log(res)
            console.log('메시지',JSON.parse(res.body));
            this.msg.push(JSON.parse(res.body));
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );
    },
    chatting() {
    this.id = this.$store.state.account.userpk;

    http.get(`/chat/room/${this.roomid}`)
    .then(res => {
        console.log('res', res);
        this.productImg = res.data.object.room.product.image
        this.productName = res.data.object.room.product.title
        this.productPrice = res.data.object.room.product.mileage
        this.msg = res.data.object.messages;
        this.room = res.data.object.room;
        this.user1['userPk'] = res.data.object.currentUser.userPk
        this.user1['userNick'] = res.data.object.currentUser.nickname
        this.user2['userPk'] = res.data.object.other.userPk
        this.user2['userNick'] = res.data.object.other.nickname
        this.connect();
    }) 
    }
  }
};
</script>

<style scoped>
.container-width {
width: 600px; 
margin-top: 50px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
    .container-width {
      width: 370px;
    }
}

.img-wrapper {
  width:600px;
  height:400px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .img-wrapper {
    width:370px;
    height:270px;
  }
}

.line {
  width:570px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .line {
    width:340px;
  }
}

.product-img {
  width: 100%;
  height:100%;
  overflow: hidden;
  object-fit: cover;
  border-radius: 5px;
}

.profile-img {
  width: 50px;
  height: 50px;
  object-fit:cover;
  border-radius: 50%;
  border : 3px solid orange;
}

.prd-title {
  font-size: 20px;
  font-weight:bold;
  padding:12px;
  margin:6px;
  /* width: 100%; */
}

.prd-category {
  font-size: 13px;
  padding: 12px;
  /* margin-bottom: 6px; */
  margin-left: 6px;
  color: grey;
}

.prd-mileage {
  font-size : 13px;
  padding-left:12px;
  margin:6px;
  height: 100%;
  line-height: 44px;
}

.prd-content {
  font-size : 15px;
  padding-left: 12px;
  padding-right: 12px;
  margin:6px;
}


.deal-btn {
  margin:30px;
  border: 2px solid orange;
  border-radius: 15px;
  background-color:white;
}

.toolbar {
  position: fixed;
  color: black;
  width: 600px;
  /* height: 55px; */
  border-bottom: 1px solid grey;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
.toolbar {
  position: fixed;
  color: black;
  width: 330px;
  /* height: 55px; */
  border-bottom: 1px solid grey;
}
}

.prd-img {
  margin-top: 24px;
  width: 80%;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .prd-img {
    width: 100%;
  margin-top: 24px;

  }
}

.name-price-style {
  margin-top: 24px;
  margin-bottom: 12px;
}
.price-style {
  color: grey;
  font-size: 80%;
}
.other-message-box {
  background-color: #EEEEEE;
  border-radius: 20px;
  max-width: 40%;
  font-size: 110%;
  padding: 10px;
  word-break: break-all;
}

.my-message-box {
  background-color: #F9802D;
  border-radius: 20px;
  max-width: 40%;
  font-size: 110%;
  padding: 10px;
  color: white;
  word-break: break-all;
}

.send-icon {
  font-size: 40px;
  cursor: pointer;
  /* color: black; */
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .send-icon {
    font-size: 30px;
    cursor: pointer;
    padding-top: 8px;
  }
}

.input-style {
 position: fixed; 
 bottom: 0;
 max-width: 600px;
 /* background-color: white; */
}
</style>