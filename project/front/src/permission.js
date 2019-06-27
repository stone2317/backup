import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css' // Progress 进度条样式
import {getToken} from '@/utils/auth' // 验权
const whiteList = ['/login', '/404'] //白名单,不需要登录的路由
router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    //如果已经登录

    if (to.path === '/login') {
      console.log('path==login')
      next({path: '/'})
      NProgress.done() // 结束Progress
    } else if (!store.getters.role) {
      console.log('role is empty')
      store.dispatch('GetInfo').then(() => {
        next({...to})
      })
    } else {
      console.log('go to next,'+to.path)
      next()
    }
  } else if (whiteList.indexOf(to.path) !== -1) {
    //如果前往的路径是白名单内的,就可以直接前往
    console.log("white list,"+to.path)
    next()
  } else {
    //如果路径不是白名单内的,而且又没有登录,就跳转登录页面
    console.log('need to login,'+to.path)
    store.commit('RESET_USER')    
    next('/login')
 
    NProgress.done() // 结束Progress
  }
})
router.afterEach(() => {
  NProgress.done() // 结束Progress
})
