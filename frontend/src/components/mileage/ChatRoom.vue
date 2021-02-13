<template>
<div>
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
        <!-- 채팅 상단부: 상품 이름/가격, 닫기 버튼 표시 시작-->
        <v-toolbar
          class="toolbar"
          elevation="0"
          style="height: 65px;"
        >
        <v-container>
          <v-row class="py-5">
            <v-col cols="2" class="mt-1">
              <img 
                :src="productImg" 
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
                >닫기</v-btn>
              </v-card-actions>                        
            </v-col>
          </v-row>   
        </v-container>  
        </v-toolbar>
        <!-- 채팅 상단부: 상품 이름/가격, 닫기 버튼 표시 끝-->             
        <v-card-text 
          style="padding-top: 100px; padding-bottom: 90px; min-height:900px;"
          class="scroll"
        >
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
        <!-- 메세지 입력칸, send 아이콘 표시 시작 -->
        <v-container class="input-style">
          <v-row class="mt-3 mb-3 input-style2">
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
            </v-col>
          </v-row> 
        </v-container>    
        <!-- 메세지 입력칸, send 아이콘 표시 끝 -->             
      </v-card>
    </template>
  </v-dialog>
</div>  
</template>

<script>
import http from "../../http-common";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: "ChatRoom",
  data() {
    return {
      product: {},
      user : 'nickname',
      seller: '',
      roomid : 0,
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
      roomCheck: false
    }
  },
  props:{
    product_id: String,
    // product: Object,
  },
  created() {
    this.id = this.$store.state.account.userpk;
    // console.log(this.product_id)

    http.get(`/product/${this.product_id}`)
    .then((res)=>{
      console.log(res);
      this.product = res.data.object
      this.seller = this.product.user_pk
    })   

    this.seller = this.product.user_pk
    console.log(this.product)
    // var objDiv = document.getElementById("scroll"); 
    // objDiv.scrollTop = objDiv.scrollHeight;
  },
  methods: {
    sendMessage(){
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
            // console.log(res)
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
    enterRoom(v) {
      http.get(`/chat/room/${v}`)
      .then(res => {
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
    },

    chatting() {
      http.get('chat/getList')
      .then(res => {
        this.roomCheck = res.data.object.some((res) => {
          if(this.product.id == res.product.id) {
            this.roomid = res.id
            console.log('111')
            return true
          }
        })
      })
      setTimeout(() => {
        
        if(this.roomCheck == true) {
          this.enterRoom(this.roomid)
        }
        else {
          // console.log(this.seller, Number(this.id))
          if (this.seller !== Number(this.id)) {    // 02월 13일 이 부분부터 해야함(판매자와 유저가 같을 경우 어떻게 처리할 것인지!)
            // console.log(this.product.id);
            http.post(`chat/createRoom?product_pk=${this.product.id}`)
            .then((res) => {
              this.roomid = res.data.object.id
  
              this.enterRoom(this.roomid )
            })
          }
          else {
            if(this.$route.path !== '/chatlist')
              this.$router.push('/chatlist')
            else {
              // console.log(this.roomid)
              this.enterRoom(this.roomid )
            }
          }
        }
      }, 1000);


    }
  },
}
</script>

<style scoped>
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
 bottom: 75px;
 max-width: 600px;
 max-height: 80px;
 /* background-color: white; */
}
</style>