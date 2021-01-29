import { api } from '../../api/index'

const state = {
  accessToken: null,
  name: null,
  email: null,
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
  }
};

const mutations = {
  SET_ACCESSTOKEN(state, { accessToken, email, name }) {
    state.accessToken = accessToken;
    state.email = email;
    state.name = name;
  },
  
  LOGIN(state, { headers, data }) {
    if (!headers.accesstoken) return;
    state.accessToken = headers.accesstoken;
    sessionStorage.accessToken = headers.accesstoken;
    sessionStorage.name = data.object.nickname;
    sessionStorage.email = data.object.email;

    state.name = data.object.nickname;
    state.email = data.object.email;
  },
  LOGOUT(state) {
    state.accessToken = null;
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("name");
    sessionStorage.removeItem("email");

    state.name = null;
    state.email = null;
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
