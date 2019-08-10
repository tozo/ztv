import {FETCH_STATUS} from './actions.type';
import statusService from '../api/statusService';
import {SET_STATUS} from './mutations.type';

const state = {
  numberOfFeeds: Number,
  numberOfStreams: Number,
  numberOfFilteredStreams: Number
};

export const actions = {
  async [FETCH_STATUS](context) {
    return await statusService.retrieve()
      .then(({data}) => {
        context.commit(SET_STATUS, data);
      })
  }
};

const getters = {
  numberOfFeeds(state) {
    return state.numberOfFeeds;
  },
  numberOfStreams(state) {
    return state.numberOfStreams;
  },
  numberOfFilteredStreams(state) {
    return state.numberOfFilteredStreams;
  }
};

const mutations = {
  [SET_STATUS](state, status) {
    state.numberOfFeeds = status.numberOfFeeds;
    state.numberOfStreams = status.numberOfStreams;
    state.numberOfFilteredStreams = status.numberOfFilteredStreams;
  },
};

export default {
  state,
  actions,
  getters,
  mutations
};