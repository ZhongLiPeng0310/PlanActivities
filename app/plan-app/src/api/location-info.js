import req from '../fetch/index.js'

const config = {
  getAddress: { // 地址-查询地址接口/app/address/deleteAddress
    url: '/app/address/getAddress',
    method: 'post'
  },
  deleteAddress: { // 地址-删除地址接口
    url: '/app/address/deleteAddress',
    method: 'post'
  },
  updateAddress: { // 修改地址接口
    url: '/app/address/updateAddSubAddress',
    method: 'post'
  },
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
