import req from '../fetch/index.js'

const config = {
  getShopData: { // 购物车-查询购物车接口
    url: '/app/cart/getCart',
    method: 'post'
  },
  addOrder: { // 订单-新增订单接口
    url: '/app/order/saveCartOrder',
    method: 'post'
  },
  deleteShopCar: { // 购物车-删除购物车接口
    url: '/app/cart/deleteCart',
    method: 'post'
  },
  updateAddSubCart: { // 购物车-修改购物车数量接口
    url: '/app/cart/updateAddSubCart',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
