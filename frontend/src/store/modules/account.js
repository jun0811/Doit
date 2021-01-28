import { api } from '../../api/index'

const state = {
  accessToken: null,
};

const getters = {
  isAuthenticated(state) {
    return !!state.accessToken;
  },
  getAccessToken(state) {
    return state.accessToken;
  },
};

const mutations = {
  SET_ACCESSTOKEN(state, token) {
    state.accessToken = token;
  },  
  
  LOGIN(state, { accesstoken }) {
    if (!accesstoken) return;
    state.accessToken = accesstoken;
    sessionStorage.accessToken = accesstoken;
  },
  LOGOUT(state) {
    state.accessToken = null;
    sessionStorage.removeItem("accessToken");
  },
};

const actions = {
  async LOGIN({ commit }, payload) {
    const response = await api.login(payload);
    commit('LOGIN', response.headers);
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
