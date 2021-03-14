import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  strict: true,
  state: { // vuex 中存储的数据都在这里，我们也可以去App.vue里面调用 （$store.state.count）看看值有没有而判断是否成功调用
    count: 2
  }
})

export default store
