import req from '../fetch/index.js'

const config = {
  getEvaluate: { // 订单-查询订单评价商品信息列表接口
    url: '/app/order/getOrdersByCode',
    method: 'post'
  },
  addEvaluate: { // 订单-新增订单商品评价接口(订单列表页订单已完成后的评价按钮)
    url: '/app/clientOrder/addGoodsEvaluate',
    method: 'post'
  },
  addAppraise: { // 订单-新增订单商品评价接口(订单列表页订单已完成后的评价按钮)
    url: '/app/order/saveOrdersAppraise',
    method: 'post'
  },
  changeOrderStatus: { // 客户端订单-修改订单状态接口
    url: '/app/order/updateOrderState',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
