import req from '../fetch/index.js'

const config = {
  getSupplierMyGoods: { // app-查询提交的产品信息接口 供应商不需要传字段 站长需要
    url: '/app/supplier/getMyGoods',
    method: 'post'
  },
  addOrder: { // 审核订单，与下相同（通过）
    url: '/pc/goods/updateGoodsAudit',
    method: 'post'
  },
  deleteShopCar: { // 审核订单，与上相同（驳回）
    url: '/pc/goods/updateGoodsAudit',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
