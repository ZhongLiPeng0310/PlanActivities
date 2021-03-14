import Vue from 'vue'
import Router from 'vue-router'
import ErrorPage from '@/components/router销毁/error.vue'
import Login from '@/pages/login.vue'
import Test from '@/pages/test.vue'
import Test2 from '@/pages/test2.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/home',
      name: 'home',
      redirect: 'user-manage',
      component: () => import(/* webpackChunkName: "home" */ '@/pages/home.vue'),
      children: [
        {
          path: '/user-manage',
          name: 'user-manage',
          component: () => import (/* webpackChunkName: "user-manage" */ '@/pages/user-manage/index.vue')
        },
        {
          path: '/tb-user-manage',
          name: 'tb-user-manage',
          component: () => import (/* webpackChunkName: "tb-user-manage" */ '@/pages/tb-user-manage/index.vue')
        },
        {
          path: '/tb-goods-manage',
          name: 'tb-goods-manage',
          component: () => import (/* webpackChunkName: "tb-goods-manage" */ '@/pages/tb-goods-manage/index.vue')
        },
        {
          path: '/tb-plan-class-manage',
          name: 'tb-plan-class-manage',
          component: () => import (/* webpackChunkName: "tb-plan-class-manage" */ '@/pages/tb-plan-class-manage/index.vue')
        },
        {
          path: '/tb-plan-manage',
          name: 'tb-plan-manage',
          component: () => import (/* webpackChunkName: "tb-plan-manage" */ '@/pages/tb-plan-manage/index.vue')
        },
        {
          path: '/tb-plan-store-manage',
          name: 'tb-plan-store-manage',
          component: () => import (/* webpackChunkName: "tb-plan-store-manage" */ '@/pages/tb-plan-store-manage/index.vue')
        },
        {
          path: '/tb-picture-show-manage',
          name: 'tb-picture-show-manage',
          component: () => import (/* webpackChunkName: "tb-picture-show-manage" */ '@/pages/tb-picture-show-manage/index.vue')
        },
        {
          path: '/tb-super-plan-manage',
          name: 'tb-super-plan-manage',
          component: () => import (/* webpackChunkName: "tb-super-plan-manage" */ '@/pages/tb-super-plan-manage/index.vue')
        },
        {
          path: '/tb-order-manage',
          name: 'tb-order-manage',
          component: () => import (/* webpackChunkName: "tb-order-manage" */ '@/pages/tb-order-manage/index.vue')
        },


        {
          path: '/test',
          name: 'test',
          component: Test
        },
        {
          path: '/test2',
          name: 'test2',
          component: Test2
        },
        {
          path: '/menu-manage',
          name: 'menu-manage',
          component: () => import (/* webpackChunkName: "menu-manage" */ '@/pages/menu-manage/index.vue')
        },
        {
          path: '/country-manage',
          name: 'country-manage',
          component: () => import (/* webpackChunkName: "country-manage" */ '@/pages/country-manage/index.vue')
        },
        {
          path: '/goods-audit-manage',
          name: 'goods-audit-manage',
          component: () => import (/* webpackChunkName: "goods-audit-manage" 审核管理 */ '@/pages/audit-comm/index.vue')
        },
        {
          path: '/comm-manage',
          name: 'comm-manage',
          component: () => import (/* webpackChunkName: "comm-manage" */ '@/pages/comm-manage/index.vue')
        },
        {
          path: '/home-pic-manage',
          name: 'home-pic-manage',
          component: () => import (/* webpackChunkName: "home-pic-manage" */ '@/pages/home-pic-manage/index.vue')
        },
        {
          path: '/comm-classify-manage',
          name: 'comm-classify-manage',
          component: () => import (/* webpackChunkName: "comm-classify-manage" */ '@/pages/comm-classify-manage/index.vue')
        },
        {
          path: '/client-manage',
          name: 'client-manage',
          component: () => import (/* webpackChunkName: "client-manage" */ '@/pages/client-manage/index.vue')
        },
        {
          path: '/order-manage',
          name: 'order-manage',
          component: () => import (/* webpackChunkName: "order-manage" */ '@/pages/order-manage/index.vue')
        },
        {
          path: '/hot-comm-manage',
          name: 'hot-comm-manage',
          component: () => import (/* webpackChunkName: "hot-comm-manage" */ '@/pages/hot-comm-manage/index.vue')
        },
        {
          path: '/shop-info-manage',
          name: 'shop-info-manage',
          component: () => import (/* webpackChunkName: "shop-info-manage" */ '@/pages/shop-info-manage/index.vue')
        },
        {
          path: '/driver-info-manage',
          name: 'driver-info-manage',
          component: () => import (/* webpackChunkName: "driver-info-manage" */ '@/pages/driver-info-manage/index.vue')
        }
      ]
    },
    {
      path: '*',
      name: 'error',
      component: ErrorPage
    }

  ]
})
