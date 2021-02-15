import store from '../../store/index';
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

const notiType = {
    NEWFEED: 0,
    NEWCHAT: 1,
    CONFIRMFEED: 2,
    KICKOUT: 3,
    AUTHORIZE: 4
}

const api = {
    async connect(accessToken) {
      let serverURL = "http://localhost:8080/ws";
      let socket = new SockJS(serverURL);
      let stompClient = Stomp.over(socket);
      await stompClient.connect({ "accessToken": accessToken },
      frame => {
        console.log(frame)
        store.commit("SET_CONNECTED", true);
      },
      error => {
        console.log('소켓 연결 실패', error);
      });
      return stompClient;
    },
    
    async disconnect() {
        store.getters.getStompClient.disconnect();
    }
}

export { notiType, api }