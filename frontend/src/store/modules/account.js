import { api } from '../../api/index'

const state = {
  accessToken: null,
  name: null,
  email: null,
  userpk: null,
  mileage: null,
};

const getters = {
  isAuthenticated(state) {
    return !!state.accessToken;
  },
  getAccessToken(state) {
    return state.accessToken;
  },
  getEmail(state){
    return state.email;
  },
  getName(state){
    return state.name;
  },
  getMileage(state) {
    return state.mileage;
  }
};

const mutations = {
  SET_ACCESSTOKEN(state, { accessToken, email, name, userpk, mileage }) {
    state.accessToken = accessToken;
    state.email = email;
    state.name = name;
    state.userpk = userpk;
    state.mileage = mileage;
  },
  SET_NAME(state, { name }){
    state.name = name;
    sessionStorage.name = name;
  },  
  LOGIN(state, { headers, data }) {
    if (!headers.accesstoken) return;
    state.accessToken = headers.accesstoken;
    state.name = data.object.nickname;
    state.email = data.object.email;
    state.userpk = data.object.id;
    state.mileage = data.object.mileage;

    sessionStorage.accessToken = headers.accesstoken;
    sessionStorage.name = data.object.nickname;
    sessionStorage.email = data.object.email;
    sessionStorage.userpk = data.object.id;
    sessionStorage.mileage = data.object.mileage;
  },
  LOGOUT(state) {
    state.accessToken = null;
    state.name = null;
    state.email = null;
    state.userpk = null;
    state.mileage = null;

    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("name");
    sessionStorage.removeItem("email");
    sessionStorage.removeItem("userpk");
    sessionStorage.removeItem("mileage");
  },
};

const actions = {
  async LOGIN({ commit }, payload) {

    const response = await api.login(payload);
    console.log(response)
    commit('LOGIN', response);
    return response;
  },
  async LOGOUT({ commit }) {
    const response = await api.logout();
    commit('LOGOUT');
    return response;
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
