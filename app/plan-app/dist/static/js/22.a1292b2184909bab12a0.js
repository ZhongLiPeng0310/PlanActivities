webpackJsonp([22],{IfLo:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=i("mvHQ"),a=i.n(o),l=i("Nv7v"),n={getSliderPicList:{url:"/app/homePage/getPictureShow",method:"post"},getHotComm:{url:"/app/homePage/getHotVillage",method:"post"},getVillageList:{url:"/app/goods/getVillageList",method:"post"}},s=function(e,t){return Object(l.a)(n[e].url,n[e].method,t,{},n[e].fileConfig)},c={name:"search-comm",data:function(){return{villageName:"",pageInfo:{pageIndex:1,pageSize:5,total:0},picList:[],commList:[]}},mounted:function(){this.getHotComm()},methods:{skip:function(){console.log(123456)},getHotComm:function(){var e=this;s("getVillageList",{villageName:this.villageName,pageNum:this.pageInfo.pageNum,pageSize:this.pageInfo.pageSize}).then(function(t){console.log("getVillageList data",t),e.commList=t.data.list})},toDetailPage:function(e){console.log("toDetailPage",e),sessionStorage.setItem("currentComm",a()(e)),this.$router.push({path:"/country-introduce"})}}},g={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"container"},[i("div",{staticClass:"header-info"},[i("el-input",{attrs:{placeholder:"请输入乡村名称"},model:{value:e.villageName,callback:function(t){e.villageName=t},expression:"villageName"}}),e._v(" "),i("el-button",{attrs:{icon:"el-icon-search"},on:{click:e.getHotComm}})],1),e._v(" "),i("ul",{staticClass:"comm-list"},e._l(e.commList,function(t,o){return i("li",{key:o,on:{click:function(i){return e.toDetailPage(t)}}},[i("div",{staticClass:"book-info"},[i("div",[e._v(e._s(t.villageName))]),e._v(" "),i("div",{staticClass:"book-img"},[i("img",{attrs:{src:t.imageCode,alt:""}}),e._v(" "),i("div",{staticClass:"div-info"},[e._v(e._s(t.villageIntroduce))])])])])}),0)])},staticRenderFns:[]};var r=i("VU/8")(c,g,!1,function(e){i("Qwns")},"data-v-3203003e",null);t.default=r.exports},Qwns:function(e,t){}});
//# sourceMappingURL=22.a1292b2184909bab12a0.js.map