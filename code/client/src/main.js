// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import api from './api/api'
import accessToken from './util/accessToken'
import http from './util/http'
import storage from './util/storage'

Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.prototype.$message = ElementUI.Message;
Vue.prototype.$confirm = ElementUI.MessageBox.confirm;

Vue.prototype.$token = accessToken;
Vue.prototype.$http = http;
Vue.prototype.$api = api;
Vue.prototype.$storage = storage;


new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});

