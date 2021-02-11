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
        <!-- <v-row class="ml-3 mb-3"> -->
          <v-row class="ml-3 mb-3">
            {{m.userPk}}
          </v-row>
          <v-row class="ml-3 mb-6 message-box">
            {{m.message}}
          </v-row>
          <!-- <div v-bind:class="m.style"> -->
          <!-- <h5 style="margin:3px">
            </h5> -->
          <!-- </div> -->
        <!-- </v-row> -->
      </div>
        <hr class="mt-3">
      <!-- </v-virtual-scroll> -->
      <v-row>
        <v-col cols="8">
          <input 
            type="text" 
            v-model="content" 
            placeholder="메세지를 입력하세요."
          >
        </v-col>
        <v-col cols="2">
          <button @click="sendMessage()">SEND</button>
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
      productPrice: ''
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

.message-box {
  border: 1px solid grey;
  border-radius: 20px;
  width: 100px;
  font-size: 110%;
  padding: 10px;
}
</style>