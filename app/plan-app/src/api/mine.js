import req from '../fetch/index.js'

const config = {
  getUserInfo: { // 查询客户信息接口 分成了客户2，站点，供应商
    url: '/app/client/getMyself',
    method: 'post'
  },
  getByCode: { // 查询客户信息接口 分成了客户，站点3，供应商
    url: '/app/storer/getStoreByCode',
    method: 'post'
  },
  getMySelf: { // 查询客户信息接口 分成了客户，站点，供应商4
    url: '/app/supplier/getMySelf',
    method: 'post'
  },
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
