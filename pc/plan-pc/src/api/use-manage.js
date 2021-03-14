import req from '../fetch/fetch/index.js'

const config = {
  // portExport: {
  //   url: 'http://p2991w6815.oicp.vip',
  //   method: 'post',
  //   fileConfig: {
  //     type: 'file',
  //     fileName: '销售激励报表'
  //   }
  // },
  // 客户端请求地址 和 http请求方式
  getTableData: { // 用户管理-分页查询用户列表接口
    url: '/pc/user/listUsersByPage',
    method: 'post'
  },
  addUser: { // 用户管理-新增用户接口
    url: '/pc/user/saveUser',
    method: 'post'
  },
  updateUser: { // 用户管理-修改用户信息接口
    url: '/pc/user/updateUser',
    method: 'post'
  },
  getUserDetailData: { // 用户管理-查询用户详情接口
    url: '/pc/user/getUserByUserCode',
    method: 'post'
  },
  deleteUser: { // 用户管理-删除用户接口
    url: '/pc/user/deleteUser',
    method: 'post'
  },
  listMenu: { // 菜单管理-查询菜单名列表接口
    url: '/pc/menu/listMenus',
    method: 'post'
  },
  getTopOfColumn: { // 顶部栏接口
    url: '/pc/user/getUserUrl',
    method: 'post'
  },
  listMenuHome: { // 菜单管理-根据角色查询首页菜单列表接口
    url: '/pc/menu/listMenuHome',
    method: 'post'
  },
  addMenu: { // 菜单管理-新增菜单接口
    url: '/pc/menu/saveMenu',
    method: 'post'
  },
  getMenu: { // 菜单管理-查询菜单详情接口
    url: '/pc/menu/getMenuByCode',
    method: 'post'
  },
  updateMenu: { // 菜单管理-修改菜单接口
    url: '/pc/menu/updateMenu',
    method: 'post'
  },
  deleteMenu: { // 菜单管理-删除用户接口 .
    url: '/pc/menu/deleteMenu',
    method: 'post'
  },
  listAuditGoodsByPage: { // 审核管理-分页查询商品接口
    url: '/pc/goods/listAuditGoodsByPage',
    method: 'post'
  },
  commGetTableData: { // 商品管理-分页查询商品接口
    url: '/pc/goods/listGoodsByPage',
    method: 'post'
  },
  addComm: { // 商品管理-新增商品接口
    url: '/pc/goods/saveGoods',
    method: 'post'
  },
  updateComm: { // 商品管理-修改商品接口
    url: '/pc/goods/updateGoods',
    method: 'post'
  },
  deleteComm: { // 商品管理-删除商品
    url: '/pc/goods/deleteGoods',
    method: 'post'
  },
  commGetUserDetailData: { // 商品管理-查询商品详情接口
    url: '/pc/goods/getGoodsByGoodsCode',
    method: 'post'
  },
  listGoodsClassify: { // 商品管理-查询产品一级分类接口
    url: '/pc/goods/getFirstClass',
    method: 'post'
  },
  listGoodsClassifySecond: { // 商品管理-查询产品二级分类接口
    url: '/pc/goods/getSecondClass',
    method: 'post'
  },
  listGoodsGetAllStore: { // 商品管理-查询产品站点接口
    url: '/pc/goods/getAllStore',
    method: 'post'
  },
  updateGoodsShelfState: {// 商品管理-修改商品上下架状态接口
    url: '/pc/goods/updateGoodsState',
    method: 'post'
  },
  updateGoodsAudit: {// 商品管理-修改商品审核状态接口
    url: '/pc/goods/updateGoodsAudit',
    method: 'post'
  },
  imgGetTableData: { // 轮播图-分页查询首页轮播图接口
    url: '/pc/pictureShow/listPictureShowByPage',
    method: 'post'
  },
  deleteImg: { // 轮播图-删除首页轮播图接口
    url: '/pc/pictureShow/deletePictureShow',
    method: 'post'
  },
  addImg: { // 轮播图-新增首页轮播图
    url: '/pc/pictureShow/savePictureShow',
    method: 'post'
  },
  listGoods: { // 5.4 9.3 查询商品接口
    url: '/pc/pictureShow/listGoodsByPage',
    method: 'post'
  },
  updateImg: { // 轮播图-修改首页轮播图状态接口
    url: '/pc/pictureShow/updatePictureShowState',
    method: 'post'
  },
  listAllGoodsClassify: { // 商品分类管理-查询商品分类列表接口
    url: '/pc/goodsclass/listGoodsClass',
    method: 'post'
  },
  getGoodsClassify: { // 商品分类管理-查询商品分类详情接口
    url: '/pc/goodsclass/getClassByClassCode',
    method: 'post'
  },
  addGoodsClassify: { // 商品分类管理-新增
    url: '/pc/goodsclass/saveGoodsClass',
    method: 'post'
  },
  updateGoodsClassify: { // 商品分类管理-修改商品分类接口
    url: '/pc/goodsclass/updateGoodsClass',
    method: 'post'
  },
  deleteGoodsClassify: { // 商品分类管理-删除
    url: '/pc/goodsclass/deleteGoodsClass',
    method: 'post'
  },
  listGetTableData: { // 客户管理-分页查询客户信息接口
    url: '/pc/customer/listCustomerByPage',
    method: 'post'
  },
  listOrders: { // 订单管理-分页查询订单接口
    url: '/pc/order/listOrderByPage',
    method: 'post'
  },
  getListOrder: { // 订单管理-查询订单详情接口
    url: '/pc/order/getOrderByOrderCode',
    method: 'post'
  },
  updateOrderState: { // 订单管理-修改订单状态接口
    url: '/pc/order/updateOrderState',
    method: 'post'
  },
  listHotGoods: { // 热门位乡村管理-分页查询热门乡村接口
    url: '/pc/hotVillage/listHotVillageByPage',
    method: 'post'
  },
  listVillageByPage: { // 热门位乡村管理-链接乡村列表查询接口
    url: '/pc/hotVillage/listVillageByPage',
    method: 'post'
  },
  addHotGoods: { // 热门乡村管理-新增热门乡村接口
    url: '/pc/hotVillage/saveHotVillage',
    method: 'post'
  },
  updateHotGoods: { // 热门乡村管理-修改热门乡村接口
    url: '/pc/hotVillage/updateHotVillage',
    method: 'post'
  },
  getHotGoods: { // 热门乡村管理-查询热门乡村详情接口
    url: '/pc/hotVillage/getHotVillageByHotCode',
    method: 'post'
  },
  deleteHotGoods: { // 热门乡村管理-删除热门乡村接口
    url: '/pc/hotVillage/deleteHotVillage',
    method: 'post'
  },
  getHotGoodsShowNum: { // 热门乡村管理-查询热门乡村展示数量接口
    url: '/pc/hotVillage/getShowNo',
    method: 'post'
  },
  updateHotGoodsShowNum: { // 热门乡村管理-修改热门乡村数量展示接口
    url: '/pc/hotVillage/saveShowNo',
    method: 'post'
  },
  listStores: { // 站点信息管理-分页查询站点信息
    url: '/pc/store/listStoreByPage',
    method: 'post'
  },
  listStoreBoss: { // 站点信息管理-分页查询站点信息
    url: '/pc/store/listStoreBoss',
    method: 'post'
  },
  listArea: { // 站点信息管理-省市区下拉框
    url: '/pc/store/listArea',
    method: 'post'
  },
  addStore: { // 站点信息管理-新增站点信息详情接口
    url: '/pc/store/saveStore',
    method: 'post'
  },
  updateStore: { // 站点信息管理-修改站点信息详情接口
    url: '/pc/store/updateStore',
    method: 'post'
  },
  getStore: { // 站点信息管理-查询站点信息详情接口
    url: '/pc/store/getStoreByStoreCode',
    method: 'post'
  },
  deleteStore: { // 站点信息管理-删除站点信息接口
    url: '/pc/store/deleteStore',
    method: 'post'
  },
  listDrivers: { // 供应商信息管理-分页查询供应商接口
    url: '/pc/supplier/listSupplierByPage',
    method: 'post'
  },
  addDriver: { // 供应商信息管理-新增供应商信息接口
    url: '/pc/supplier/saveSupplier',
    method: 'post'
  },
  updateDriver: { // 供应商信息管理-修改供应商信息接口
    url: '/pc/supplier/updateSupplier',
    method: 'post'
  },
  getDriver: { // 供应商信息管理-查询供应商信息详情接口
    url: '/pc/supplier/getSupplierByUserCode',
    method: 'post'
  },
  deleteDriver: { // 供应商信息管理-删除供应商信息接口
    url: '/pc/supplier/deleteSupplier',
    method: 'post'
  },
  listVillage: { // 乡村信息管理-分页查询乡村接口
    url: '/pc/village/listVillageByPage',
    method: 'post'
  },
  addVillage: { // 乡村信息管理-新增乡村信息接口
    url: '/pc/village/saveVillage',
    method: 'post'
  },
  updateVillage: { // 乡村信息管理-修改乡村信息接口
    url: '/pc/village/updateVillage',
    method: 'post'
  },
  getVillage: { // 乡村信息管理-查询乡村信息详情接口
    url: '/pc/village/getVillageByVillageCode',
    method: 'post'
  },
  deleteVillage: { // 乡村信息管理-删除乡村信息接口
    url: '/pc/village/deleteVillage',
    method: 'post'
  }
}

const request = function (funcName, requestParam) {
  return req(config[funcName].url, config[funcName].method, requestParam, {}, config[funcName].fileConfig)
}
export default request