import store from '../../store/index';
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

const notiType = {
    NEWFEED: 0,
    NEWCHAT: 1,
    CONFIRMFEED: 2,
    KICKOUT: 3,
    AUTHORIZE: 4,
    COMMENT: 5,
}

const api = {
    async connect() {
      let serverURL = "http://localhost:8080/ws";
      let socket = new SockJS(serverURL);
      let stompClient = Stomp.over(socket);
      await stompClient.connect({ "accessToken": store.getters.getAccessToken },
      frame => {
        console.log(frame)
        store.commit("SET_CONNECTED", true);
      },
      error => {
        console.log('소켓 연결 실패', error);
      });
      return stompClient;
    },
    
    disconnect() {
        store.getters.getStompClient.disconnect();
    },
    
    sendNoti(notification) {
      if (store.getters.getStompClient == null) return;
      store.getters.getStompClient.send("/publish/noti", JSON.stringify(notification),{"accessToken": store.getters.getAccessToken})
    }
}

export { notiType, api }