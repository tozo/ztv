import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import router from './router';
import store from './store'
import ApiService from './api/apiService';

library.add(faUserSecret);

Vue.config.productionTip = false;
Vue.use(BootstrapVue);
Vue.component('font-awesome-icon', FontAwesomeIcon);

ApiService.init();

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
