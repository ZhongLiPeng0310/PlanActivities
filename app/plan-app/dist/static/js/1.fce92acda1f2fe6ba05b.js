webpackJsonp([1],{"7S3y":function(e,t){},"8LNm":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=a("Nv7v"),r={getDriverInfo:{url:"/app/supplier/getStoreBySupplier",method:"post"}},s=function(e,t){return Object(o.a)(r[e].url,r[e].method,t,{},r[e].fileConfig)},i={data:function(){return{list:[]}},mounted:function(){this.getDriverInfo()},methods:{getDriverInfo:function(){var e=this;s("getDriverInfo",{}).then(function(t){e.list=t.data,console.log("getDriverInfo",t)})}}},n={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",e._l(e.list,function(t,r){return o("div",{key:r,staticClass:"box"},[o("div",{staticClass:"shop-info"},[o("img",{attrs:{src:a("gPBh"),alt:""}}),e._v(" "),o("span",[e._v(e._s(t.storeCode))])]),e._v(" "),o("div",[e._v("\n      站点："+e._s(t.storeName)+"\n    ")]),e._v(" "),o("div",[e._v("\n      地址："+e._s(t.address)+"\n    ")]),e._v(" "),o("div",[e._v("\n      站长："+e._s(t.userName)+"\n    ")]),e._v(" "),o("div",[e._v("\n      手机："+e._s(t.phone)+"\n    ")])])}),0)},staticRenderFns:[]};var l=a("VU/8")(i,n,!1,function(e){a("dy8W")},"data-v-2b5cca80",null);t.default=l.exports},Bnux:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=a("Dd8w"),r=a.n(o),s=(a("mtWM"),a("mw3O"),a("hSyu")),i=a("Nv7v"),n={submit:{url:"/pc/goods/saveGoods",method:"post"},getFirst:{url:"/pc/goods/getFirstClass",method:"post"},getSecond:{url:"/pc/goods/getSecondClass",method:"post"},getAllStore:{url:"/pc/goods/getAllStore",method:"post"},getDriverInfo:{url:"/app/supplier/getStoreBySupplier",method:"post"}},l=function(e,t){return Object(i.a)(n[e].url,n[e].method,t,{},n[e].fileConfig)},c={name:"register",components:{IFile:s.a},data:function(){return{adc:"",station:[],oneSort:[],twoSort:[],formData:{},formRules:{goodsName:[{required:!0,message:"请输入用户账号",trigger:"change"}],userPassword:[{required:!0,message:"请输入用户密码",trigger:"change"}],phone:[{required:!0,message:"请输入手机号",trigger:"change"},{type:"number",message:"电话号码必须为数字"},{validator:this.validateTel,trigger:"change"}],advertisement:[{required:!0,message:"请输入用户名称",trigger:"change"}],sex:[{required:!0,message:"请选择性别",trigger:"change"}],goodsIntroduce:[{required:!0,message:"请输入身份证",trigger:"change"}],userCode:[{required:!0,message:"请输入用户邮箱",trigger:"change"}]}}},computed:{userType:function(){return JSON.parse(sessionStorage.getItem("roleInfo")).role}},mounted:function(){this.getAllStore()},methods:{toLogin:function(){this.$router.push({path:"/login"})},getAllStore:function(){var e=this;l("getFirst",{}).then(function(t){e.oneSort.splice(0);for(var a=0;a<t.data.length;a++)e.oneSort.push({label:t.data[a].classFirstName,value:t.data[a].classFirstCode})})},oneChange:function(e){console.log("oneValue",e),this.firstId=e,this.formData.classSecondCode&&(this.formData.classSecondCode=""),this.twochange()},twochange:function(){var e=this;l("getSecond",{lastClassCode:this.firstId}).then(function(t){console.log(t),e.twoSort.splice(0);for(var a=0;a<t.data.length;a++)e.twoSort.push({label:t.data[a].classSecondName,value:t.data[a].classSecondCode})})},submit:function(){var e=this;if(3===JSON.parse(sessionStorage.getItem("roleInfo")).role){var t=JSON.parse(sessionStorage.getItem("roleInfo")).storeCode;l("submit",r()({},this.formData,{storeCode:t})).then(function(t){0===t.code?(e.$message.success(t.msg),e.$refs.form.resetFields(),e.dialogVisible=!1):e.$message.error(t.msg)})}else console.log(222222),l("getDriverInfo",{}).then(function(t){e.adc=t.data[0].storeCode,console.log(e.adc),l("submit",r()({},e.formData,{storeCode:e.adc,userCode:JSON.parse(sessionStorage.getItem("roleInfo")).userCode})).then(function(t){0===t.code?(e.$message.success(t.msg),e.$refs.form.resetFields(),e.dialogVisible=!1):e.$message.error(t.msg)})})},validateTel:function(e,t,a){""===t||void 0===t||null==t?a():/^[1][3-9][0-9]{9}$/.test(t)||""===t?a():a(new Error("请输入正确的电话号码"))}}},d={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-form",{ref:"form",attrs:{model:e.formData,rules:e.formRules,inline:"","label-width":"80px"}},[a("el-form-item",{attrs:{label:"产品名称",prop:"goodsName"}},[a("el-input",{model:{value:e.formData.goodsName,callback:function(t){e.$set(e.formData,"goodsName",t)},expression:"formData.goodsName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"一级分类",prop:"classFirstCode"}},[a("el-select",{on:{change:e.oneChange},model:{value:e.formData.classFirstCode,callback:function(t){e.$set(e.formData,"classFirstCode",t)},expression:"formData.classFirstCode"}},e._l(e.oneSort,function(e,t){return a("el-option",{key:t,attrs:{label:e.label,value:e.value}})}),1)],1),e._v(" "),a("el-form-item",{attrs:{label:"二级分类",prop:"classSecondCode"}},[a("el-select",{attrs:{placeholder:"请输入"},model:{value:e.formData.classSecondCode,callback:function(t){e.$set(e.formData,"classSecondCode",t)},expression:"formData.classSecondCode"}},e._l(e.twoSort,function(e,t){return a("el-option",{key:t,attrs:{label:e.label,value:e.value}})}),1)],1),e._v(" "),a("el-form-item",{attrs:{label:"手机号",prop:"phone"}},[a("el-input",{model:{value:e.formData.phone,callback:function(t){e.$set(e.formData,"phone",e._n(t))},expression:"formData.phone"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"广告词",prop:"advertisement"}},[a("el-input",{model:{value:e.formData.advertisement,callback:function(t){e.$set(e.formData,"advertisement",t)},expression:"formData.advertisement"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"产品介绍",prop:"goodsIntroduce"}},[a("el-input",{model:{value:e.formData.goodsIntroduce,callback:function(t){e.$set(e.formData,"goodsIntroduce",t)},expression:"formData.goodsIntroduce"}})],1),e._v(" "),a("el-form-item",{directives:[{name:"show",rawName:"v-show",value:3===e.userType,expression:"userType === 3"}],attrs:{label:"供应编码",prop:"userCode"}},[a("el-input",{model:{value:e.formData.userCode,callback:function(t){e.$set(e.formData,"userCode",t)},expression:"formData.userCode"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"库存",prop:"stock"}},[a("el-input",{model:{value:e.formData.stock,callback:function(t){e.$set(e.formData,"stock",t)},expression:"formData.stock"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"成本价",prop:"costPrice"}},[a("el-input",{model:{value:e.formData.costPrice,callback:function(t){e.$set(e.formData,"costPrice",t)},expression:"formData.costPrice"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"售价",prop:"salePrice"}},[a("el-input",{model:{value:e.formData.salePrice,callback:function(t){e.$set(e.formData,"salePrice",t)},expression:"formData.salePrice"}})],1),e._v(" "),a("el-form-item",{staticClass:"ifile",attrs:{label:"上传图片",prop:"imagePath"}},[a("div",[a("i-file",{model:{value:e.formData.imagePath,callback:function(t){e.$set(e.formData,"imagePath",t)},expression:"formData.imagePath"}})],1)]),e._v(" "),a("el-form-item",{attrs:{label:"保质期",prop:"qualityDate"}},[a("el-input",{model:{value:e.formData.qualityDate,callback:function(t){e.$set(e.formData,"qualityDate",t)},expression:"formData.qualityDate"}})],1),e._v(" "),a("el-form-item",[a("el-button",{staticClass:"register-btn",attrs:{type:"info"},on:{click:e.submit}},[e._v("添     加")])],1)],1)],1)},staticRenderFns:[]};var u=a("VU/8")(c,d,!1,function(e){a("FpFK")},"data-v-410dffdf",null);t.default=u.exports},FpFK:function(e,t){},J524:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=a("woOf"),r=a.n(o),s=a("mvHQ"),i=a.n(s),n=a("Nv7v"),l={getEvaluate:{url:"/app/order/getOrdersByCode",method:"post"},addEvaluate:{url:"/app/clientOrder/addGoodsEvaluate",method:"post"},addAppraise:{url:"/app/order/saveOrdersAppraise",method:"post"},changeOrderStatus:{url:"/app/order/updateOrderState",method:"post"}},c=function(e,t){return Object(n.a)(l[e].url,l[e].method,t,{},l[e].fileConfig)},d=a("mtWM"),u=a.n(d),m={name:"order-evaluate",data:function(){return{appraiseInfoList:[]}},mounted:function(){this.getEvaluate()},methods:{getEvaluate:function(){var e=this;c("getEvaluate",{orderCode:this.$route.query.orderCode}).then(function(t){e.appraiseInfoList=t.data.goodsList,console.log("this.appraiseInfoList: ",t)})},addEvaluate:function(){var e=this;u()({method:"post",url:"http://29ng920368.wicp.vip/app/order/saveAppraise",data:i()({orderCode:this.$route.query.orderCode,appraiseInfoList:this.appraiseInfoList.map(function(e){return r()({},{goodsCode:e.goodsCode,appraiseDetail:e.appraiseDetail,appraiseLevel:e.appraiseLevel})})}),headers:{"Content-Type":"application/json;",Authorization:"Bearer "+JSON.parse(sessionStorage.getItem("userInfo")).access_token}}).then(function(t){console.log("this.appraiseInfoList该主题",e.appraiseInfoList),console.log("this.$route.query",e.$route.query),c("changeOrderStatus",{orderCode:e.$route.query.orderCode,orderState:8,version:e.$route.query.version}).then(function(e){console.log("改状态",e)}),0===t.data.code?(e.$message.success(t.data.msg),e.$router.push({path:"/order-list"})):e.$message.error(t.data.msg)})}}},p={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"container"},[e._l(e.appraiseInfoList,function(t,o){return a("div",{key:o,staticClass:"comm"},[a("div",{staticClass:"pic-info"},[a("img",{attrs:{src:t.imagePath,alt:""}}),e._v(" "),a("div",{staticClass:"star"},[a("el-rate",{model:{value:t.appraiseLevel,callback:function(a){e.$set(t,"appraiseLevel",a)},expression:"item.appraiseLevel"}})],1)]),e._v(" "),a("div",{staticClass:"eva-box"},[a("div",{staticClass:"title"},[e._v("分享你的使用体验吧")]),e._v(" "),a("el-input",{staticStyle:{display:"block",width:"95%",margin:"0 auto"},attrs:{type:"textarea",rows:8},model:{value:t.appraiseDetail,callback:function(a){e.$set(t,"appraiseDetail",a)},expression:"item.appraiseDetail"}})],1)])}),e._v(" "),a("el-button",{staticClass:"submit-btn",attrs:{type:"primary"},on:{click:e.addEvaluate}},[e._v("提交")])],2)},staticRenderFns:[]};var f=a("VU/8")(m,p,!1,function(e){a("7S3y")},"data-v-5fcfbf7c",null);t.default=f.exports},dy8W:function(e,t){}});
//# sourceMappingURL=1.fce92acda1f2fe6ba05b.js.map