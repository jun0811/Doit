<template>
  <div class="room">
    <h1>title</h1>
    <hr>

    <div v-for="(m, idx) in msg" :key="idx">
      <div v-bind:class="m.style">
      <h5 style="margin:3px">
        {{m.userPk}}
        </h5>
      {{m.message}}
      </div>
    </div>
    <hr />
    <input type="text" v-model="content" placeholder="보낼 메세지" size="100" />
    <button @click="sendMessage()"> SEND</button>
  </div>
</template>

<script>
import http from '../../http-common'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: "Chat",
    data: () => {
    return {
      roomid : 13,
      id : 105,
      nickname: '버징가',
      idx:0,
      msg:[],
      room: '',
      content:'',
      stompClient:null
    }
  },
  created(){
    this.id = this.$store.state.account.userpk;
    console.log('userid', this.id);
    http.get(`/chat/room/${this.roomid}`)
    .then(res => {
        console.log('res', res);
        this.msg = res.data.object.messages;
        this.room = res.data.object.room;
    })
    this.connect();
  },
  methods:{
    sendMessage(){
    console.log(this.content);
     if(this.content.trim() !='' && this.stompClient!=null) {
        let chatMessage = {
          'message': this.content,
          'roomPk' : this.room.id,
          'userPk' : this.$store.state.account.userpk,
          'nickname' : this.nickname
        }
        this.stompClient.send("/publish/chat", JSON.stringify(chatMessage),{})
   
        this.content=''
    }
    },
    connect() {
      const serverURL = "http://localhost:8080/ws";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
       this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // console.log(this.roomid)
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/subscribe/chat/room"+this.roomid , res => {
            console.log(JSON.parse(res.body));
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
.myMsg{
text-align: right;
color : gray;
}
.otherMsg{
  text-align: left;
}
</style>