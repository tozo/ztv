import {FETCH_FEEDS, FETCH_STREAMS} from './actions.type';
import {SET_FEEDS, SET_STREAMS} from './mutations.type';
import feedService from '../api/feedService';
import streamService from '../api/streamService';

const state = {
  feeds: [],
  streams: []
};

const getters = {
  feeds(state) {
    return state.feeds;
  },
  streams(state) {
    return state.streams;
  }
};

export const actions = {
  async [FETCH_FEEDS](context) {
    return feedService.findAll()
      .then(({data}) => {
        context.commit(SET_FEEDS, data);
      })
      .catch(error => {
        throw new Error(error);
      });
  },
  async [FETCH_STREAMS](context) {
    return streamService.findAll()
      .then(({data}) => {
        context.commit(SET_STREAMS, data);
      })
      .catch(error => {
        throw new Error(error);
      });
  }
};

const mutations = {
  [SET_FEEDS](state, feeds) {
    state.feeds = feeds;
  },
  [SET_STREAMS](state, streams) {
    state.streams = streams;
  }
};

export default {
  state,
  getters,
  actions,
  mutations
};
