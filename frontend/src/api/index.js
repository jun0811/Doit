import http from '../http-common';
import store from '../store/index';

const response = {
  UNAUTHORIZED : 401,
  FOBBIDEN : 403
}

const api = {
  login(payload) {
    return http.post('/user/login', payload);
  },

  logout() {
    // store.dispatch("LOGOUT");
    alert("로그아웃 되었습니다😒");
    return http.get('/user/logout')
  },

  onFobbiden() {
    if (store.getters.getAccessToken)
      alert("권한이 없음");
    else alert("로그인 필요");
  }
};

export { api, response }
