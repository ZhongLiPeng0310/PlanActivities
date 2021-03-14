import req from '../fetch/index.js'

const config = {
  getStoreInfo: { // 查询站长附近供应商接口
    url: '/app/storer/getStoreSupplierByCode',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
