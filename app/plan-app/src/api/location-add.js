import req from '../fetch/index.js'

const config = {
  getAddress: { // 地址-查询地址接口
    url: '/app/address/getAddress',
    method: 'post'
  },
  listArea: { // 站点信息管理-省市区下拉框
    url: '/pc/store/listArea',
    method: 'post'
  },
  saveAddress: { // 新增地址接口
    url: '/app/address/saveAddress',
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
