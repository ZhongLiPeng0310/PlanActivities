webpackJsonp([12],{GU4Y:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=s("SmhM"),i={name:"payment-info",data:function(){return{row:void 0,Rphone:"",dingwei:void 0,xingming:"",Uphone:"",diZi:"",getList:[],newGetList:[],totalPrice:0,payCommList:JSON.parse(sessionStorage.getItem("payCommList")),roleInfo:JSON.parse(sessionStorage.getItem("roleInfo"))}},mounted:function(){this.con()},methods:{con:function(){var e=this;console.log("this.roleInfo ",this.roleInfo),console.log("this.payCommList",this.payCommList),console.log("this.$route.query.payCommList ",this.$route.query.payCommList),console.log("this.$route.query ",this.$route.query),console.log("传值this.$route.query.tableSelectRows",this.$route.query.tableSelectRows),void 0!==this.$route.query.tableSelectRows&&(console.log("看看你"),this.row=this.$route.query.tableSelectRows.getName,this.Rphone=this.$route.query.tableSelectRows.phone,this.dingwei=this.$route.query.tableSelectRows.provinceName+this.$route.query.tableSelectRows.cityName+this.$route.query.tableSelectRows.areaName+this.$route.query.tableSelectRows.address),console.log("传值this.row",this.row),Object(o.a)("getAddress",{}).then(function(t){console.log("getAddress",t),e.getList=t.data,console.log("查询地址this.getList",e.getList),e.newGetList=e.getList.filter(function(e){return console.log("遍历数组的默认参数 item.betrue",e.betrue),0==e.betrue}),e.diZi=e.newGetList[0].provinceName+e.newGetList[0].cityName+e.newGetList[0].areaName+e.newGetList[0].address,e.xingming=e.newGetList[0].getName,e.Uphone=e.newGetList[0].phone,console.log("默认地址this.newGerList ",e.newGetList),console.log("this.diZi",e.diZi)})},DwAdd:function(){console.log(111),this.$router.push({path:"/location-info2",query:{goodsCode:this.$route.query.goodsCode,orderMoney:this.$route.query.orderMoney,orderSum:this.$route.query.orderSum,cartCode:this.$route.query.cartCode,storeCode:this.$route.query.storeCode}})},addShopCar:function(){var e=this;void 0===this.row?Object(o.a)("addOrder",{goodsCode:this.$route.query.goodsCode,orderMoney:this.$route.query.orderMoney,orderSum:this.$route.query.orderSum,cartCode:this.$route.query.cartCode,storeCode:this.$route.query.storeCode,addressCode:this.newGetList[0].addressCode}).then(function(t){0===t.code?(e.$message.success(t.msg),setTimeout(function(){e.$router.push({path:"/order-list"})})):e.$message.error(t.msg)}):Object(o.a)("addOrder",{goodsCode:this.$route.query.goodsCode,orderMoney:this.$route.query.orderMoney,orderSum:this.$route.query.orderSum,cartCode:this.$route.query.cartCode,storeCode:this.$route.query.storeCode,addressCode:this.$route.query.tableSelectRows.addressCode}).then(function(t){0===t.code?(e.$message.success(t.msg),setTimeout(function(){e.$router.push({path:"/order-list"})})):e.$message.error(t.msg)})}}},r={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"main"},[o("div",{staticClass:"address",on:{click:function(t){return e.DwAdd()}}},[e._m(0),e._v(" "),o("div",{staticClass:"address-info"},[o("div",{directives:[{name:"show",rawName:"v-show",value:void 0===this.row,expression:"this.row===undefined"}],staticClass:"address-info-div1"},[o("span",[e._v(e._s(this.xingming))]),e._v(" "),o("span",{staticClass:"address-info-div1-span2"},[e._v(e._s(this.Uphone))])]),e._v(" "),o("div",{directives:[{name:"show",rawName:"v-show",value:void 0!==this.row,expression:"this.row!==undefined"}],staticClass:"address-info-div1"},[o("span",[e._v(e._s(this.row))]),e._v(" "),o("span",{staticClass:"address-info-div1-span2"},[e._v(e._s(this.Rphone))])]),e._v(" "),o("div",{directives:[{name:"show",rawName:"v-show",value:void 0===this.row,expression:"this.row===undefined"}],staticClass:"address-info-div2"},[e._v(e._s(this.diZi))]),e._v(" "),o("div",{directives:[{name:"show",rawName:"v-show",value:void 0!==this.row,expression:"this.row!==undefined"}],staticClass:"address-info-div1"},[e._v(e._s(this.dingwei))]),e._v(" "),o("div",{staticClass:"address-info-div3"},[e._v("收获不便时，可选择暂存服务")])])]),e._v(" "),o("div",{staticClass:"book"},[o("div"),e._v(" "),o("ul",{staticClass:"book-list"},e._l(e.payCommList,function(t,i){return o("li",{key:i,staticClass:"book-list-item"},[o("div",{staticClass:"book-count-info"},[o("img",{directives:[{name:"show",rawName:"v-show",value:t.imagePath,expression:"item.imagePath"}],attrs:{src:t.imagePath}}),e._v(" "),o("img",{directives:[{name:"show",rawName:"v-show",value:!t.imagePath,expression:"!item.imagePath"}],attrs:{src:s("psQ+")}}),e._v(" "),o("div",[o("div",[e._v(e._s(t.goodsName))]),e._v(" "),o("div",[e._v("圆若骊珠，赤若金丸，肉似玻璃，核如黑漆。")]),e._v(" "),o("div",[o("span",[e._v("￥"+e._s(t.salePrice*t.orderSum))]),e._v(" "),o("div",{staticClass:"change-count-box"},[o("div",[e._v("共"+e._s(t.orderSum)+"件")])])])])])])}),0),e._v(" "),o("div",{staticClass:"close-count"},[o("div",[o("div",[o("b",[e._v("合计:")]),e._v(" "),o("span",[e._v(e._s(this.$route.query.orderMoney))])]),e._v(" "),o("el-button",{staticClass:"div-button",attrs:{type:"primary"},on:{click:e.addShopCar}},[e._v("结算")])],1)])])])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"address-icon"},[t("img",{attrs:{src:s("xKG9"),alt:""}})])}]};var a=s("VU/8")(i,r,!1,function(e){s("jPSZ")},"data-v-e0cddd78",null);t.default=a.exports},SmhM:function(e,t,s){"use strict";var o=s("Nv7v"),i={getAddress:{url:"/app/address/getAddress",method:"post"},updateAddre:{url:"/app/address/updateAddre",method:"post"},addOrder:{url:"/app/order/saveCartOrder",method:"post"},updateAddSubCart:{url:"/app/cart/updateAddSubCart",method:"post"}};t.a=function(e,t){return Object(o.a)(i[e].url,i[e].method,t,{},i[e].fileConfig)}},jPSZ:function(e,t){}});
//# sourceMappingURL=12.cca023eb64ebadb4ef37.js.map