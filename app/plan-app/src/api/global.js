import req from '../fetch/index.js'

const config = {
  // 查询客户信息接口
  getUserInfo: {
    url: '/app/client/getMyself',
    method: 'post'
  },
  getStoreBy: { // 查询站点信息接口  3
    url: '/app/storer/getStoreByCode',
    method: 'post'
  },
  getMySelfs: { // 查询供应商信息接口  4
    url: '/app/supplier/getMySelf',
    method: 'post'
  },
  authForm: {
    url: 'uaa/auth/form',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
