import Vue from 'vue';
import axios from "axios";
import VueAxios from "vue-axios";

export default {
  init() {
    Vue.use(VueAxios, axios);
    Vue.axios.defaults.baseURL = '/api/';
  },

  get(resource, params) {
    return Vue.axios.get(resource, params).catch(error => {
      throw new Error(`ApiService ${error}`);
    });
  },

  post(resource, params) {
    return Vue.axios.post(`${resource}`, params);
  },

  put(resource, params) {
    return Vue.axios.put(`${resource}`, params);
  },

  delete(resource) {
    return Vue.axios.delete(resource);
  }
};