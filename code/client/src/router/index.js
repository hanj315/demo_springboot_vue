import Vue from 'vue'
import Router from 'vue-router'
import UserManagement from '@/view/admin/user/UserManagement'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: UserManagement
    }
  ]
})
