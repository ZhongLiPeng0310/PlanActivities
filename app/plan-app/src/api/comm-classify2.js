import req from '../fetch/index.js'

const config = {
  getClassify: { // 商品-查询一级商品分类列接口
    url: '/app/goods/getFirstClass',
    method: 'post'
  },
  getTwoClassify: { // 商品-查询二级商品分类以及商品接口
    url: '/app/goods/getSecondClass',
    method: 'post'
  },
  getSliderPicList: { // 首页-查询首页轮播图接口
    url: '/app/homePage/getPictureShow',
    method: 'post'
  },
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
