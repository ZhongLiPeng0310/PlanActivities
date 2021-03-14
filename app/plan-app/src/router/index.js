import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    // 登录
    {
      path: '/login',
      name: 'login',
      component: () => import(/* webpackChunkName: "login" */ '@/pages/login.vue')
    },
    {
      path: '/',
      redirect: '/login'
    },
    // 注册
    {
      path: '/register',
      name: 'register',
      component: () => import(/* webpackChunkName: "register" */ '@/pages/register.vue'),
      meta: {
        title: [{
          titleName: '注册',
          toPath: '/register'
        }],
        goBack: '/login',
        goBackShow: true,
        headerHide: false,
        footerShow: false
      }
    },
    {
      path: '/home',
      name: 'home',
      redirect: '/comm-home',
      component: () => import(/* webpackChunkName: "home" */ '@/pages/home.vue'),
      children: [
        // 首页
        {
          path: '/comm-home',
          name: 'comm-home',
          component: () => import(/* webpackChunkName: "comm-home" */ '@/pages/comm-home/index.vue'),
          meta: {
            title: [
              {
                titleName: '千村万物',
                toPath: '/comm-home'
              }
            ],
            goBack: '/comm-home',
            goBackShow: false,
            headerHide: false,
            footerShow: true
          }
        },
        {
          path: '/search-comm',
          name: 'search-comm',
          component: () => import(/* webpackChunkName: "search-comm" */ '@/pages/search-comm/index.vue'),
          meta: {
            title: [
              {
                titleName: '搜索',
                toPath: '/search-comm'
              }
            ],
            goBack: '/comm-home',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        // 乡村介绍 country-introduce
        {
          path: '/country-introduce',
          name: 'country-introduce',
          component: () => import(/* webpackChunkName: "comm-home" */ '@/pages/country-introduce/index.vue'),
          meta: {
            title: [
              {
                titleName: '乡村',
                toPath: '/country-introduce',
                active: true
              }
            ],
            goBack: '/comm-home',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        // 产品详情
        {
          path: '/comm-detail',
          name: 'comm-detail',
          component: () => import(/* webpackChunkName: "comm-detail" */ '@/pages/comm-detail/index.vue'),
          meta: {
            title: [
              {
                titleName: '产品',
                toPath: '/comm-detail',
                active: true
              },
              {
                titleName: '评价',
                toPath: '/comm-evaluate'
              }
            ],
            goBack: '/comm-home',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        {
          path: '/payment-info',
          name: 'payment-info',
          component: () => import(/* webpackChunkName: "payment-info" */ '@/pages/payment-info/index.vue'),
          meta: {
            title: [
              {
                titleName: '确认订单',
                toPath: '/payment-info',
                active: true
              }
            ],
            goBack: '/comm-home',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        {
          path: '/payment-info2',
          name: 'payment-info2',
          component: () => import(/* webpackChunkName: "payment-info2" */ '@/pages/payment-info2/index.vue'),
          meta: {
            title: [
              {
                titleName: '确认订单',
                toPath: '/payment-info2',
                active: true
              }
            ],
            goBack: '/comm-home',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        // 产品评价
        {
          path: '/comm-evaluate',
          name: 'comm-evaluate',
          component: () => import(/* webpackChunkName: "comm-evaluate" */ '@/pages/comm-evaluate/index.vue'),
          meta: {
            title: [
              {
                titleName: '产品评价',
                toPath: '/comm-evaluate'
              }
            ],
            goBack: '/comm-detail',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        // 产品分类
        {
          path: '/comm-classify',
          name: 'comm-classify',
          component: () => import(/* webpackChunkName: "comm-classify" */ '@/pages/comm-classify/index.vue'),
          meta: {
            title: [
              {
                titleName: '产品分类',
                toPath: '/comm-classify'
              }
            ],
            goBack: '/comm-home',
            goBackShow: false,
            headerHide: false,
            footerShow: true
          }
        },
        // 产品分类2
        {
          path: '/comm-classify2',
          name: 'comm-classify2',
          component: () => import(/* webpackChunkName: "comm-classify2" */ '@/pages/comm-classify2/index.vue'),
          meta: {
            title: [
              {
                titleName: '产品分类',
                toPath: '/comm-classify2'
              }
            ],
            goBack: '/comm-home',
            goBackShow: false,
            headerHide: false,
            footerShow: true
          }
        },
        // 购物车
        {
          path: '/shop-car',
          name: 'shop-car',
          component: () => import(/* webpackChunkName: "shop-car" */ '@/pages/shop-car/index.vue'),
          meta: {
            title: [
              {
                titleName: '购物车',
                toPath: '/shop-car'
              }
            ],
            goBack: '/comm-home',
            goBackShow: true,
            headerHide: false,
            footerShow: true
          }
        },
        // 我的
        {
          path: '/mine',
          name: 'mine',
          component: () => import(/* webpackChunkName: "mine" */ '@/pages/mine/index.vue'),
          meta: {
            title: [
              {
                titleName: '我的',
                toPath: '/mine'
              }
            ],
            goBack: '/comm-home',
            goBackShow: false,
            headerHide: false,
            footerShow: true
          }
        },
        {
          path: '/location-info',
          name: 'location-info',
          component: () => import(/* webpackChunkName: "location-info" */ '@/pages/location-info/index.vue'),
          meta: {
            title: [
              {
                titleName: '我的地址',
                toPath: '/location-info'
              }
            ],
            goBack: '/mine',
            goBackShow: true,
            headerHide: false,
            footerShow: true
          }
        },
        {
          path: '/location-info2',
          name: 'location-info2',
          component: () => import(/* webpackChunkName: "location-info2" */ '@/pages/location-info2/index.vue'),
          meta: {
            title: [
              {
                titleName: '我的地址',
                toPath: '/location-info2'
              }
            ],
            goBack: '/mine',
            goBackShow: true,
            headerHide: false,
            footerShow: true
          }
        },
        {
          path: '/location-info3',
          name: 'location-info3',
          component: () => import(/* webpackChunkName: "location-info3" */ '@/pages/location-info3/index.vue'),
          meta: {
            title: [
              {
                titleName: '我的地址',
                toPath: '/location-info3'
              }
            ],
            goBack: '/mine',
            goBackShow: true,
            headerHide: false,
            footerShow: true
          }
        },
        {
          path: '/location-add',
          name: 'location-add',
          component: () => import(/* webpackChunkName: "location-add" */ '@/pages/location-add/index.vue'),
          meta: {
            title: [
              {
                titleName: '添加地址',
                toPath: '/location-add'
              }
            ],
            goBack: '/location-info',
            goBackShow: true,
            headerHide: false,
            footerShow: true
          }
        },
        {
          path: '/location-xiu',
          name: 'location-xiu',
          component: () => import(/* webpackChunkName: "location-xiu" */ '@/pages/location-xiu/index.vue'),
          meta: {
            title: [
              {
                titleName: '修改地址',
                toPath: '/location-xiu'
              }
            ],
            goBack: '/location-info',
            goBackShow: true,
            headerHide: false,
            footerShow: true
          }
        },
        // 订单列表
        {
          path: '/order-list',
          name: 'order-list',
          component: () => import(/* webpackChunkName: "order-list" */ '@/pages/order-list/index.vue'),
          meta: {
            title: [
              {
                titleName: '我的订单',
                toPath: '/order-list'
              }
            ],
            goBack: '/mine',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        // 修改密码
        {
          path: '/change-password',
          name: 'change-password',
          component: () => import(/* webpackChunkName: "change-password" */ '@/pages/change-password/index.vue'),
          meta: {
            title: [
              {
                titleName: '修改密码',
                toPath: '/change-password'
              }
            ],
            goBack: '/mine',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        // 修改店铺邀请码
        {
          path: '/inquire-comm',
          name: 'inquire-comm',
          component: () => import(/* webpackChunkName: "inquire-comm" */ '@/pages/inquire-comm/index.vue'),
          meta: {
            title: [
              {
                titleName: '查看已提交产品',
                toPath: '/inquire-comm'
              }
            ],
            goBack: '/mine',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        // 订单详情
        {
          path: '/order-detail',
          name: 'order-detail',
          component: () => import(/* webpackChunkName: "order-detail" */ '@/pages/order-detail/index.vue'),
          meta: {
            title: [
              {
                titleName: '订单详情',
                toPath: '/order-detail'
              }
            ],
            goBack: '/order-list',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        // 订单评价
        {
          path: '/order-evaluate',
          name: 'order-evaluate',
          component: () => import(/* webpackChunkName: "order-evaluate" */ '@/pages/order-evaluate/index.vue'),
          meta: {
            title: [
              {
                titleName: '订单评价',
                toPath: '/order-evaluate'
              }
            ],
            goBack: '/order-list',
            goBackShow: true,
            headerHide: false,
            footerShow: false
          }
        },
        {
          path: '/driver-info',
          name: 'driver-info',
          component: () => import(/* webpackChunkName: "driver-info" */ '@/pages/driver-info/index.vue'),
          meta: {
            title: [
              {
                titleName: '供应商信息',
                toPath: '/driver-info'
              }
            ],
            goBack: '/',
            goBackShow: false,
            headerHide: false,
            footerShow: true
          }
        },
        {
          path: '/driver-home',
          name: 'driver-home',
          component: () => import(/* webpackChunkName: "driver-home" */ '@/pages/driver-home/index.vue'),
          meta: {
            title: [
              {
                titleName: '附近站点信息',
                toPath: '/driver-home'
              }
            ],
            goBack: '/',
            goBackShow: false,
            headerHide: false,
            footerShow: true
          }
        },
        {
          path: '/driver-shop-info',
          name: 'driver-shop-info',
          component: () => import(/* webpackChunkName: "order-evaluate" */ '@/pages/driver-shop-info/index.vue'),
          meta: {
            title: [
              {
                titleName: '附近站点信息',
                toPath: '/driver-shop-info'
              }
            ],
            goBack: '/',
            goBackShow: false,
            headerHide: false,
            footerShow: true
          }
        },
        {
          path: '/add-comm',
          name: 'add-comm',
          component: () => import(/* webpackChunkName: "order-evaluate" */ '@/pages/add-comm/index.vue'),
          meta: {
            title: [
              {
                titleName: '添加产品',
                toPath: '/add-comm'
              }
            ],
            goBack: '/',
            goBackShow: false,
            headerHide: false,
            footerShow: true
          }
        }

      ]
    }
  ]
})
