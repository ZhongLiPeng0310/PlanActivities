import req from '../fetch/index.js'

const config = {
  getAddress: { // 地址-查询地址接口
    url: '/app/address/getAddress',
    method: 'post'
  },
  updateAddre: { // 地址-修改地址接口
    url: '/app/address/updateAddre',
    method: 'post'
  },
  addOrder: { // 订单-新增订单接口
    url: '/app/order/saveCartOrder',
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
