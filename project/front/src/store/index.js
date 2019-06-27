import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import permission from './modules/permission'
import ws from './modules/socket'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    ws,
    app,
    user,
    permission
  },
  getters
})

export default store
