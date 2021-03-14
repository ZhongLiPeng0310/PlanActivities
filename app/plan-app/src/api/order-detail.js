import req from '../fetch/index.js'

const config = {
  getClientDetail: { // 用户订单-查询订单详情接口
    url: '/app/order/getOrdersByCode',
    method: 'post'
  },
  getShopDetail: { // 店长订单-查询店长订单详情接口
    url: '/app/storeOrder/getStoreOrdersByCode',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
