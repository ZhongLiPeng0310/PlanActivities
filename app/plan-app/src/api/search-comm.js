import req from '../fetch/index.js'

const config = {
  getSliderPicList: { // 首页-查询首页轮播图接口
    url: '/app/homePage/getPictureShow',
    method: 'post'
  },
  getHotComm: { // 首页-查询热门乡村接口
    url: '/app/homePage/getHotVillage',
    method: 'post'
  },
  getVillageList: { // 搜索-查询乡村接口
    url: '/app/goods/getVillageList',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
