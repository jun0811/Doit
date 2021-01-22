import axios from 'axios';
import { request } from './api/index'
import account from './store/modules/account'

const http = axios.create({
    baseURL: 'http://localhost:8080/',
    headers: {
        'Content-type': 'application/json',
    },
})

http.interceptors.response.use(function (response) {
    return response;
  }, function (error) {
    if (error.response.status == 403)
        request.onUnauthorized();
});

http.interceptors.request.use(function (config) {
    if(account.state.accessToken)
        config.headers.accessToken = account.state.accessToken;
    return config;
}, function (error) {
        return Promise.reject(error);
});


export default http;