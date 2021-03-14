import req from '../fetch/index.js'

const config = {
  getOrderList: { // 客户端订单-查询订单列表接口
    url: '/app/order/getOrdersList',
    method: 'post'
  },
  orderupdateOrderState: { // 客户端订单-修改订单状态接口
    url: '/app/order/updateOrderState',
    method: 'post'
  },
  getShopOrderList: { // 查询站点订单列表接口
    url: '/app/storeOrder/getStoreOrdersList',
    method: 'post'
  },
  changeOrderStatus: { // 修改订单状态接口
    url: '/app/storeOrder/updateOrderState',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
