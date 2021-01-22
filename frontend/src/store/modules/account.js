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
  LOGIN(state, { object }) {
    if (!object) return;
    state.accessToken = object;
  },
  LOGOUT(state) {
    state.accessToken = null;
  },
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
