import http from '../http-common';
import store from '../store/index';

const request = {
  login(payload) {
    return http.post('/user/login', payload);
  },

  onUnauthorized() {
    store.dispatch("LOGOUT");
    alert("로그인창 띄워야지");
  }
};

export { request }
