import req from '../fetch/index.js'

const config = {
  // 查询供应商身边的站点
  getDriverInfo: {
    url: '/app/supplier/getStoreBySupplier',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
