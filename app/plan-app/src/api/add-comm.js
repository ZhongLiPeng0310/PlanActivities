import req from '../fetch/index.js'

const config = {
  submit: { // 新增产品接口（提交产品）
    url: '/pc/goods/saveGoods',
    method: 'post'
  },
  getFirst: { // 一级分类下拉框
    url: '/pc/goods/getFirstClass',
    method: 'post'
  },
  getSecond: { // 二级分类下拉框
    url: '/pc/goods/getSecondClass',
    method: 'post'
  },
  getAllStore: { // 站点下拉框
    url: '/pc/goods/getAllStore',
    method: 'post'
  },
  getDriverInfo: { // 查询供应商身边的站点
    url: '/app/supplier/getStoreBySupplier',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request