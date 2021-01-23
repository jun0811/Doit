import { request } from '../../api/index'

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
  LOGIN(state, { object }) {
    if (!object) return;
    state.accessToken = object;
    localStorage.accessToken = object;
  },
  LOGOUT(state) {
    state.accessToken = null;
    localStorage.removeItem("accessToken");
  }
};

const actions = {
  async LOGIN({ commit }, payload) {
    const response = await request.login(payload);
    commit('LOGIN', response.data);
    return response;
  },
  async LOGOUT({ commit }) {
    commit('LOGOUT');
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
