import {ADD_FEED, DELETE_FEED} from './actions.type';
import feedService from '../api/feedService';

const state = {
  id: Number,
  name: String,
  url: String
};

export const actions = {
  async [ADD_FEED](context, payload) {
    let formData = new FormData();
    formData.append('file', payload.file);
    await feedService.add(payload.name, payload.url, formData);
  },
  async [DELETE_FEED](context, id) {
    await feedService.delete(id)
  }
};

const getters = {
  id(state) {
    return state.id;
  },
  name(state) {
    return state.name;
  },
  url(state) {
    return state.url;
  }
};

const mutations = {
  ['setValues'](state, {id, name, url}) {
    state.id = id;
    state.name = name;
    state.url = url;
  }
};

export default {
  state,
  actions,
  getters,
  mutations
};