import req from '../fetch/index.js'

const config = {
  getEvaluate: { // 商品-查询商品评价列表接口
    url: '/app/goods/getGoodsAppraise',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
