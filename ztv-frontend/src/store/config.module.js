import {ADD_CONFIG, FETCH_CONFIG} from './actions.type';
import configService from '../api/configService';
import {SET_CONFIG, SET_CONFIG_VALUE} from './mutations.type';

const state = {
  name: String,
  value: String,
  description: String
};

export const actions = {
  async [ADD_CONFIG](context, payload) {
    await configService.add(payload);
  },
  async [FETCH_CONFIG](context, name) {
    return await configService.find(name)
      .then(({data}) => {
        context.commit(SET_CONFIG, data);
    })
  }
};

const getters = {
  getConfig(state) {
    return state;
  }
};

const mutations = {
  [SET_CONFIG](state, config) {
    state.name = config.name;
    state.value = config.value;
    state.description = config.description;
  },
  [SET_CONFIG_VALUE](state, value) {
    state.value = value;
  }
};

export default {
  state,
  actions,
  getters,
  mutations
};