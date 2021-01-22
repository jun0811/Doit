import http from '../http-common';
// import axios from 'axios';

// import  router  from '../router/index'
import  account  from '../store/modules/account'

const onUnauthorized = () => {
  alert("로그인창 띄워야지");
};

const request = {
  login(payload) {
    return http.post('/user/login', payload);
  },
  
  get(path) {
    try {
      return http.get(path, { headers: 
        { accessToken : account.getters.getAccessToken(account.state) } });
    } catch (response) {
      const { state } = response;
      if (state === 403) return onUnauthorized();
      if (state >= 400 && state <= 500) alert(response.data);
      throw Error(response);
    }
  },
  post(path, data) {
    try {
      return http.post(path, data, { headers: 
        { accessToken : account.getters.getAccessToken(account.state) } });
    } catch (error) {
      alert(error);
    }
  },
  put(path, data) {
    try {
      return http.put(path, data, { headers: 
        { accessToken : account.getters.getAccessToken(account.state) } });
    } catch (error) {
      window.alert(error);
    }
  },
  delete(path) {
    try {
      return http.delete(path, { headers: 
        { accessToken : account.getters.getAccessToken(account.state) } });
    } catch (error) {
      window.alert(error);
    }
  },
};

export { request }
