import Vuex from 'vuex';

import Vue from 'vue';
import Feed from './feed.module'
import Settings from './settings.module';
import Status from './status.module';
import Config from './config.module';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    Feed,
    Settings,
    Status,
    Config
  },
  strict: process.env.NODE_ENV !== 'production'
});