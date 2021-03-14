import req from '../fetch/index.js'

const config = {
  // portExport: {
  //   url: '/api/media/stimulate/exportExcel',
  //   method: 'post',
  //   fileConfig: {
  //     type: 'file',
  //     fileName: '销售激励报表'
  //   }
  // },
  getSliderPicList: { // 首页-查询首页轮播图接口
    url: '/app/homePage/getPictureShow',
    method: 'post'
  },
  getHotComm: { // 首页-查询热门乡村接口
    url: '/app/homePage/getHotVillage',
    method: 'post'
  },
  getVillageAndGoods: { // 首页-查询热门乡村接口
    url: '/app/homePage/getVillageAndGoods',
    method: 'post'
  },
  getClassify: { // 商品-查询一级商品分类列接口
    url: '/app/goods/getFirstClass',
    method: 'post'
  },
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request
