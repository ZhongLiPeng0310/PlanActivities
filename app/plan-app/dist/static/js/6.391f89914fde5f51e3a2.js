webpackJsonp([6],{"/MbL":function(e,t){e.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAA60lEQVQ4T6XSsUpDQRCF4S+gRJCUVhrQQrCMheALpFKQlBaC6Bv4HnkEkyaQWgUb+3QKdpJK0KSwFSwsRFZu4LLe9S441e7OzM+ZndPwz2jU9O8U+adUXQqwhmvsF40T9PAWg1KAGxxGxbc4yAEs4zMhuRnnqhSs4B1LEeQLLXyU31Mj9HERAUY4yRnhCG1s4rhoGOMZL7j6S8EZLkuruyvOXSxWeo7BAhKPMMV2jTeCkq0qQNj9rz0nYOuYh1xZwS4eMp3dwWMMCNTXTMAGZjEg3Ic4rYGEmvDZP1Hlgz2sJiDBYPc5RsqchG/cciERBcOfZgAAAABJRU5ErkJggg=="},JlBH:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=r("Dd8w"),o=r.n(s),i=r("Nv7v"),a={getOrderList:{url:"/app/order/getOrdersList",method:"post"},orderupdateOrderState:{url:"/app/order/updateOrderState",method:"post"},getShopOrderList:{url:"/app/storeOrder/getStoreOrdersList",method:"post"},changeOrderStatus:{url:"/app/storeOrder/updateOrderState",method:"post"}},n=function(e,t){return Object(i.a)(a[e].url,a[e].method,t,{},a[e].fileConfig)},c={name:"order-list",data:function(){return{list:[],orderType:"all"}},computed:{userType:function(){return JSON.parse(sessionStorage.getItem("roleInfo")).role}},mounted:function(){console.log("this.$route.query: ",this.$route.query),this.getOrderList(this.$route.query.name,this.$route.query.code)},methods:{getOrderList:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"",r=arguments[1];console.log("this.$route.query.name: ",this.$route.query.name);var s={orderState:t,pageSize:100,pageNum:1};t||delete s.orderState,this.orderType=r,console.log("getOrderList.data.orderState",s),2===JSON.parse(sessionStorage.getItem("roleInfo")).role?n("getOrderList",o()({},s)).then(function(t){console.log("getOrderList data",t),0===t.code?(e.list=t.data.list,console.log("this.list",e.list)):e.$message.info(t.msg)}):3===JSON.parse(sessionStorage.getItem("roleInfo")).role&&n("getShopOrderList",o()({},s)).then(function(t){if("还没有客户下订单到您的店铺！"===t.msg)return e.list="",void e.$message.info("此订单列表已经完成所有操作");0===t.code?e.list=t.data.list:e.$message.info(t.msg)})},toPage:function(e){this.$router.push({path:"/order-detail",query:e})},evaluate:function(e){console.log("evaluate_data",e),this.$router.push({path:"/order-evaluate",query:e})},changeOrderStatus:function(e,t){var r=this;console.log("item, changeStatus",e,t),this.$confirm("确定进行该操作吗?").then(function(){n("changeOrderStatus",{orderCode:e.orderCode,orderState:t,version:e.version}).then(function(e){0===e.code?(r.$message.success(e.msg),r.getOrderList("",r.orderType)):r.$message.error(e.msg)})}).catch(function(e){})},orderupdateOrderState:function(e,t){var r=this;console.log("item, orderupdateOrderState",e,t),this.$confirm("确定进行该操作吗?").then(function(){n("orderupdateOrderState",{orderCode:e.orderCode,orderState:t,version:e.version}).then(function(e){0===e.code?(r.$message.success(e.msg),r.getOrderList("",r.orderType)):r.$message.error(e.msg)})}).catch(function(e){})}}},d={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",[s("div",{staticClass:"lead"},[s("div",{staticClass:"leader"},[s("ul",[s("li",{class:{active:"all"===this.orderType},on:{click:function(t){return e.getOrderList("","all")}}},[e._v("全部订单")]),e._v(" "),s("li",{class:{active:"payed"===this.orderType},on:{click:function(t){return e.getOrderList(7,"payed")}}},[e._v("已付款")]),e._v(" "),s("li",{class:{active:"cancel"===this.orderType},on:{click:function(t){return e.getOrderList(9,"cancel")}}},[e._v("已取消")]),e._v(" "),s("li",{class:{active:"pickup"===this.orderType},on:{click:function(t){return e.getOrderList(1,"pickup")}}},[e._v("待取货")]),e._v(" "),s("li",{class:{active:"confirm"===this.orderType},on:{click:function(t){return e.getOrderList(5,"confirm")}}},[e._v("已完成")])])])]),e._v(" "),e._l(e.list,function(t){return s("div",{key:t.id,staticClass:"order-item",on:{click:function(r){return e.toPage(t)}}},[s("div",{staticClass:"item-top"},[s("img",{attrs:{src:r("Q6Bz"),alt:""}}),e._v(" "),s("span",[e._v(e._s(t.orderCode))]),e._v(" "),s("span",[e._v("\n              "+e._s(7===t.orderState?"已下单":9===t.orderState?"已取消":1===t.orderState?"已到货":5===t.orderState?"已完成未评价":"已完成已评价")+"\n          ")])]),e._v(" "),e._l(t.goodsList,function(t,r){return s("div",{key:r,staticClass:"item-center"},[s("div",{staticClass:"img-con"},[s("img",{attrs:{src:t.imagePath,alt:""}})]),e._v(" "),s("div",{staticClass:"text"},[s("div",{staticClass:"text-con"},[e._v("\n                  "+e._s(t.goodsName)+"\n              ")]),e._v(" "),s("div",{staticClass:"props-con"},[e._v("\n                  "+e._s(t.goodsIntroduce)+"\n              ")]),e._v(" "),s("div",{staticClass:"price-con"},[s("span",[e._v("￥")]),e._v(" "),s("span",[e._v(e._s(t.salePrice)+"  ")]),e._v(" "),s("span",[e._v("x"+e._s(t.orderSum))])])])])}),e._v(" "),s("div",{staticClass:"item-bottom"},[s("span",[e._v("共"+e._s(t.sumGoods)+"件商品，合计￥")]),e._v(" "),s("span",[e._v(e._s(t.orderMoney))])]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:2===e.userType,expression:"userType === 2"}],staticClass:"item-more"},[s("div",{directives:[{name:"show",rawName:"v-show",value:7===t.orderState||1===t.orderState,expression:"item.orderState === 7 || item.orderState === 1"}],staticClass:"btn",on:{click:function(r){return r.stopPropagation(),e.orderupdateOrderState(t,9)}}},[s("span",[e._v("取消订单")])]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:5===t.orderState,expression:"item.orderState === 5"}],staticClass:"btn",on:{click:function(r){return r.stopPropagation(),e.evaluate(t)}}},[s("span",[e._v("评价")])]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:1===t.orderState,expression:"item.orderState === 1"}],staticClass:"btn",on:{click:function(r){return r.stopPropagation(),e.orderupdateOrderState(t,5)}}},[s("span",[e._v("确认收货")])])]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:4===e.userType,expression:"userType === 4"}],staticClass:"container"},[s("div",[s("img",{staticClass:"img1",attrs:{src:r("/MbL"),alt:""}}),e._v("\n          "+e._s(t.userName)+"\n          "),s("img",{staticClass:"img2",attrs:{src:r("grtT"),alt:""}}),e._v("\n          "+e._s(t.phone)+"\n        ")])]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:3===e.userType,expression:"userType === 3"}],staticClass:"item-more"},[s("div",{directives:[{name:"show",rawName:"v-show",value:1===t.orderState||7===t.orderState,expression:"item.orderState === 1 || item.orderState === 7"}],staticClass:"btn",on:{click:function(r){return r.stopPropagation(),e.changeOrderStatus(t,9)}}},[s("span",[e._v("取消订单")])]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:7===t.orderState,expression:"item.orderState === 7"}],staticClass:"btn",on:{click:function(r){return r.stopPropagation(),e.changeOrderStatus(t,1)}}},[s("span",[e._v("确认到货")])]),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:1===t.orderState,expression:"item.orderState === 1"}],staticClass:"btn",on:{click:function(r){return r.stopPropagation(),e.changeOrderStatus(t,7)}}},[s("span",[e._v("取消到货")])])])],2)})],2)},staticRenderFns:[]};var g=r("VU/8")(c,d,!1,function(e){r("MNyw")},"data-v-1eb5d7eb",null);t.default=g.exports},MNyw:function(e,t){},Q6Bz:function(e,t){e.exports="data:image/png;base64,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"}});
//# sourceMappingURL=6.391f89914fde5f51e3a2.js.map