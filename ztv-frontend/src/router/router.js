import Vue from "vue";
import Router from "vue-router";
import Index from "../views/Index";
import Settings from '../views/Settings';


Vue.use(Router);

const router = new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    {path: '/', component: Index},
    {path: '/settings', component: Settings}
  ]
});

export default router;