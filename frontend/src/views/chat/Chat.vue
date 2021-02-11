<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
    <v-container class="container-width container-style">
      <!-- <v-virtual-scroll
        height="300"
      > -->
      <v-row>
        <v-col cols="2">
          <img 
            :src="productImg" 
            alt=""
            class="prd-img"
          >
        </v-col>
        <v-col cols="10">
          <v-row class="mb-3">
            {{ productName }}
          </v-row>
          <v-row class="mb-1 price-style">
            {{ productPrice }} 마일리지
          </v-row>
        </v-col>
      </v-row>
      <hr class="mb-3">
      <div v-for="(m, idx) in msg" :key="idx">
        <div v-if="m.userPk==user1['userPk']" class="d-flex flex-column align-end">
          <v-row class="mb-3 mr-3">
            {{user1['userNick']}}
          </v-row>
          <v-row class="mb-6 mr-3 my-message-box">
            {{m.message}}
          </v-row>
        </div>
        <div v-else class="d-flex flex-column align-start">
          <v-row class="ml-3 mb-3">
            {{user2['userNick']}}
          </v-row>
          <v-row class="ml-3 mb-6 other-message-box">
            {{m.message}}
          </v-row>
        </div>

      </div>
        <hr class="mt-3">
      <!-- </v-virtual-scroll> -->
      <v-row class="mt-3">
        <v-col cols="10">
          <v-text-field
            label="메세지를 입력하세요."
            v-model="content" 
            single-line
            outlined
            @keyup.enter="sendMessage()"
          ></v-text-field>
          <!-- <input 
            type="text" 
            v-model="content" 
            placeholder="메세지를 입력하세요."
          > -->
        </v-col>
        <v-col cols="2" class="d-flex justify-start">
          <div class="send-icon">
            <font-awesome-icon 
              icon="paper-plane" 
              @click="sendMessage()"
            /> 
          </div>
          <!-- <button @click="sendMessage()">SEND</button> -->
        </v-col>
      </v-row>
    </v-container>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import NavBar from "@/components/common/NavBar.vue";
import Footer from "@/components/common/Footer.vue";
import http from '../../http-common'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: "Chat",
  components: {
    Header,
    NavBar,
    Footer,
  },
  data: () => {
    return {
      roomid : 14,
      id : 84,
      productImg: '',
      idx:0,
      msg:[],
      room: '',
      content:'',
      stompClient:null,
      product: '',
      productName: '',
      productPrice: '',
      user1: {'userPk' : 0, 'userNick' : ''},
      user2: {'userPk' : 0, 'userNick' : ''},
    }
  },
  created(){
    this.id = this.$store.state.account.userpk;

    http.get(`/chat/room/${this.roomid}`)
    .then(res => {
        console.log('res', res);
        this.productImg = res.data.object.room.product.image
        this.product = res.data.object.room.product
        this.productName = this.product.title
        this.productPrice = this.product.mileage
        this.msg = res.data.object.messages;
        this.room = res.data.object.room;
        this.user1['userPk'] = res.data.object.currentUser.userPk
        this.user1['userNick'] = res.data.object.currentUser.nickname
        this.user2['userPk'] = res.data.object.other.userPk
        this.user2['userNick'] = res.data.object.other.nickname
        this.connect();
    })
  },
  methods:{
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
    }
  }
};
</script>
<style scoped>
.container-width {
width: 600px; 
/* height: 400px; */
margin-top: 50px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
    .container-width {
      width: 370px;
    }
}

.container-style {
  /* background-color: #DCDCDC; */
  border: 1px solid grey;
  border-radius: 15px;
}
.myMsg{
text-align: right;
color : gray;
}
.otherMsg{
  text-align: left;
}

.prd-img {
  /* width: ; */
}

.price-style {
  color: grey;
  font-size: 80%;
}

.other-message-box {
  /* border: 1px solid grey; */
  background-color: #EEEEEE;
  border-radius: 20px;
  max-width: 40%;
  font-size: 110%;
  padding: 10px;
  word-break: break-all;
}

.my-message-box {
  /* border: 1px solid grey; */
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
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .send-icon {
    font-size: 30px;
    cursor: pointer;
    padding-top: 8px;
  }
}
</style>