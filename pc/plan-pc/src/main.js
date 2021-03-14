// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/index.js'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import moment from 'moment' // 导入文件

Vue.prototype.$moment = moment // 赋值使用 改变成dataTime
Vue.use(ElementUI); // 使用了element-ui 的框架
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

// 这个是拦截器
// router.beforeEach((to, from, next) => {
//   let userInfo = JSON.parse(sessionStorage.getItem('userInfo'))

//   if (userInfo && userInfo.access_token) {
//     next()
//   } else {
//     next('/login')
//   }
// })
