webpackJsonp([24],{"3Nu5":function(e,t){},xxag:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=i("woOf"),o=i.n(s),n=i("Nv7v"),r={getSupplierMyGoods:{url:"/app/supplier/getMyGoods",method:"post"},addOrder:{url:"/pc/goods/updateGoodsAudit",method:"post"},deleteShopCar:{url:"/pc/goods/updateGoodsAudit",method:"post"}},a=function(e,t){return Object(n.a)(r[e].url,r[e].method,t,{},r[e].fileConfig)},c={name:"shop-car",data:function(){return{allChecked:!1,totalPrice:0,list:[]}},mounted:function(){this.getDriver()},computed:{userType:function(){return JSON.parse(sessionStorage.getItem("roleInfo")).role}},methods:{getDriver:function(){var e=this;a("getSupplierMyGoods",{storeCode:JSON.parse(sessionStorage.getItem("roleInfo")).storeCode}).then(function(t){console.log("getSupplierMyGoods data",t),e.list=t.data.map(function(e){return o()({},e,{checked:!1})}),e.allChecked=!1})},addShopCar:function(){var e=this;if(this.list.filter(function(e){return!0===e.checked}).length){var t=this.list.filter(function(e){return!0===e.checked}).map(function(e){return e.goodsCode}).toString(),i=this.list.filter(function(e){return!0===e.checked}).map(function(e){return e.version}).toString();a("addOrder",{goodsCode:t,audit:1,version:i}).then(function(t){0===t.code?(e.$message.success(t.msg),e.getDriver()):e.$message.error(t.msg)})}else this.$message.info("请先选择审核的商品")},deleteShopCar:function(){var e=this;if(this.list.filter(function(e){return!0===e.checked}).length){var t=this.list.filter(function(e){return!0===e.checked}).map(function(e){return e.goodsCode}).toString(),i=this.list.filter(function(e){return!0===e.checked}).map(function(e){return e.version}).toString();console.log("goodsCode version",t,i),a("deleteShopCar",{goodsCode:t,audit:2,version:i}).then(function(t){0===t.code?(e.$message.success(t.msg),e.getDriver()):e.$message.error(t.msg)})}else this.$message.info("请先选择驳回的产品")},allInputChange:function(){var e=this;this.allChecked=!this.allChecked,this.list.forEach(function(t){t.checked=e.allChecked})},inputChange:function(e){e.checked=!e.checked;for(var t=0;t<this.list.length;t++){if(!1===this.list[t].checked){this.allChecked=!1;break}this.allChecked=!0}}}},d={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"container"},[s("ul",{staticClass:"book-list"},e._l(e.list,function(t,o){return s("li",{key:o,staticClass:"book-list-item"},[s("div",{staticClass:"select-btn-box"},[s("div",[s("input",{attrs:{type:"checkbox"},on:{change:function(i){return e.inputChange(t)}}}),e._v(" "),s("div",{staticClass:"action",style:{display:t.checked?"block":"none"}})])]),e._v(" "),s("div",{staticClass:"book-count-info"},[s("img",{directives:[{name:"show",rawName:"v-show",value:t.imagePath,expression:"item.imagePath"}],attrs:{src:t.imagePath}}),e._v(" "),s("img",{directives:[{name:"show",rawName:"v-show",value:!t.imagePath,expression:"!item.imagePath"}],attrs:{src:i("psQ+")}}),e._v(" "),s("div",[s("div",[e._v(e._s(t.goodsName))]),e._v(" "),s("div",[e._v("圆若骊珠，赤若金丸，肉似玻璃，核如黑漆。")]),e._v(" "),2===t.audit?s("div",[e._v(" 审核未通过 ")]):e._e(),e._v(" "),3===t.audit?s("div",[e._v(" 未审核 ")]):e._e(),e._v(" "),1===t.audit?s("div",[e._v(" 通过审核 ")]):e._e()])])])}),0),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:3===e.userType,expression:"userType === 3"}],staticClass:"close-count"},[s("div",[s("div",[s("input",{attrs:{type:"checkbox"},on:{change:e.allInputChange}}),e._v(" "),s("div",{staticClass:"action",style:{display:e.allChecked?"block":"none"}})]),e._v(" "),s("span",[e._v("全选")])]),e._v(" "),s("div",[s("button",{on:{click:e.deleteShopCar}},[e._v("驳回")]),e._v(" "),s("button",{on:{click:e.addShopCar}},[e._v("通过")])])])])},staticRenderFns:[]};var l=i("VU/8")(c,d,!1,function(e){i("3Nu5")},"data-v-23ed8392",null);t.default=l.exports}});
//# sourceMappingURL=24.11f9f8f6fdba7364c7c3.js.map