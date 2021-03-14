
import req from '../fetch/index.js'

const config = {
  // portExport: {
  //   url: '/api/media/stimulate/exportExcel',
  //   method: 'post',
  //   fileConfig: {
  //     type: 'file',
  //     fileName: '销售激励报表'
  //   }
  // },
  getDetail: { // 商品-查询产品详情接口
    url: '/app/goods/getGoods',
    method: 'post'
  },
  payNow: { // 订单-新增订单接口
    url: '/app/order/saveCartOrder',
    method: 'post'
  },
  addShopCar: { // 购物车-新增购物车接口
    url: '/app/cart/saveCart',
    method: 'post'
  },
  getCommEvaluatesgetCommEvaluates: { // 商品-查询商品评价接口
    url: '/app/goods/getGoodsAppraise',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
