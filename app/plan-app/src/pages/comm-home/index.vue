<template>
  <div class="container">

    <div class="header">
      <div class="header-he" >

        <!-- 搜索框 -->
        <div class="header-info">
          <div>
            <el-button @click="skip()">搜索</el-button>
          </div>
        </div>

        <!-- 分类1 -->
        <div class="lead">
          <div class="leader">
              <ul class="book-classify">
                <li
                :class="{active: item.classFirstCode === currentClassify.classFirstCode}"
                v-for="(item, index) in oneClassifyList"
                :key="index"
                @click="getTwoClassify(item)">

                  <b></b>
                  <span>{{item.classFirstName}}</span>

                </li>
                
                <li>
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                </li>
                <!-- 分类2 -->
                <div class="book-choice" @click="commClassify()">
                  <img src="../../assets/dingbufenlei.png" alt="">
                  <span>分类</span>
                </div>
              </ul>
          </div>
        </div>
        
        
        <!-- 轮播图 -->
        <div class="banner-pic-list">
          <el-carousel height="170px" indicator-position="none">
            <el-carousel-item v-for="(item, index) in picList" :key="index">
              <img :src="item.imagePath" alt="" @click="toDetailPage222(item)">
            </el-carousel-item>
          </el-carousel>
        </div>
        
      </div>
    </div>

    <div class="module-title">为你推荐</div>

    <ul class="comm-list">
      <li
        v-for="(item, index) in commList"
        :key="index"
        @click="toDetailPage(item)">
        
        <div class="book-info">
          <div>{{item.villageName}}</div>
          <div class="book-img">
            <img :src="item.imagePath" alt="">
            <div class="div-info">{{item.villageIntroduce}}</div>
          </div>
        </div>
        <!-- <div>{{item.goodsPrice}}</div> -->
      </li>
    </ul>
  </div>
</template>

<script>
import req from '@/api/comm-home.js'

export default {
  name: 'comm-home',
  data () {
    return {
      picList: [],
      commList: [],
      oneClassifyList: [], // 一级分类列表
      currentClassify: {},  // 选择了哪个一级分类 可能不需要用到 10.23.  15：12
    }
  },
  mounted () {
    this.getSliderPicList() // 首页轮播图
    this.getHotComm() // 热门乡村
    this.getClassify() // 商品的一级分类
  },
  methods: {
    skip () {
      this.$router.push({path: '/search-comm'})
    },
    commClassify () {
      this.$router.push({path: '/comm-classify'})
    },
    getSliderPicList () { // 首页-查询首页轮播图接口
      req('getSliderPicList', {}).then(data => {
        console.log('getSliderPicList data: ', data)
        this.picList = data.data
      })
    },
    getHotComm () { // 首页-查询热门乡村接口
      req('getHotComm', {}).then(data => {
        console.log('getHotComm data', data)
        this.commList = data.data
      })
    },
    getClassify () { // 商品-查询一级产品分类列接口
      req('getClassify', {}).then(data => {
        console.log('getClassify_data', data)

        this.oneClassifyList = data.data
      })
    },
    getTwoClassify (item) { // item 是一级产品分类的信息，需要跳转到分类界面并查看
      this.currentClassify = item // 更新选择了哪一个一级分类
      console.log('item 是一级产品分类的信息: ', item)
      // 接下来的代码应该是跳转 1023 15 19
      this.$router.push({path: '/comm-classify2',
        query: {
          item: item
        }
      })

    },
    toDetailPage222 (data) {
      console.log('toDetailPage', data)
      sessionStorage.setItem('currentComm', JSON.stringify(data)) // comm-detail 存信息，然后在乡村详情里面调用
      this.$router.push({path: '/comm-detail'})
    },
    toDetailPage (data) {
      console.log('toDetailPage', data)
      sessionStorage.setItem('currentComm', JSON.stringify(data)) // comm-detail 存信息，然后在乡村详情里面调用
      this.$router.push({path: '/country-introduce'})
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 1px;
  padding-bottom: 70px;
  background: #f7f6f6;
}

.header {
  position: relative;
  width: 100%;
  height: 260px;

  .header-he{
    width: 100%;
    height: 235px;
    border-radius: 0 0 80px 80px;
    position: relative;
    background: #d0efe7;
    float: left;

    .lead{ // 分类框
      position: relative;
      z-index: 2;
      width: 90%;
      height: 45px; // 把滑动部分的高度超过外边框的高度，滑动条就会被隐藏掉。
      overflow-y: hidden;
      background: white;
      margin: 10px auto;
      border-radius: 20px;

      .leader {
        height: 64px;
        width: 100%;
        white-space: nowrap; // 元素不换行
        overflow-x: auto; // 设置x轴可滑动

        .book-classify { // ui
          height: 100%;
          padding: 0 0 0 2.5%;
          margin: 0;
          display: flex;

          li {
            line-height: 45px;
            padding: 0;
            margin: 0;
            list-style: none;
            padding: 0 13px;
            font-size: 15px;

          }

          li.active {
            width: auto;
            font-size: 15px;
            color: rgb(195,152,98);
          }

          .book-choice { // 分类2 按钮
            display: flex;
            width: 80px;
            height: 45px;
            line-height: 45px;
            position: absolute;
            right: 0;
            z-index: 3;
            background: white;
            border-style: solid;
            border-width: 0 0 0 1px;

            img {
              width: 30px;
              height: 30px;
              margin: auto 0;
            }
            span {
              line-height: 45px;
            }
          }
        }
      }
    }
  
    .header-info { // 搜索框
      position: relative;
      z-index: 2;
      margin: 5px auto;
      width: 90%;
      border-color: red;

      div {
        display: flex;

        .el-button {
          width: 100%;
        }
      }
    }

    .banner-pic-list{ // 轮播图
      position: relative;
      z-index: 2;
      margin: 5px auto;
      width: 90%;
      border-radius: 20px;

      .el-carousel {
        border-radius: 20px;

        .el-carousel__container {
          border-radius: 20px;

          .el-carousel__item {

            img {
              width: 100%;
              height: 100%;
            }
          }
        }
      }
    }
  }
}

.module-title {
  width: 100%;
  height: 30px;
  text-align: center;
  line-height: 30px;
  background: #f7f6f6;
  margin-top: 20px;
  font-weight: bolder;
  font-size: 15px;
}

.comm-list {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  width: 100%;
  // margin-top: 10px;

  li {
    position: relative;
    width: 100%;
    background: #fff;
    padding: 10px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 5px 1%;
    border-radius: 10px;
    height: 140px;
    display: flex;

    .book-info {

      div:first-child {
        font-size: 15px;
        padding-left: 10px;
      }
      .book-img {
        width: 100%;
        height: 100%;
        border-radius: 20px;
        display: flex;
        
        img {
          width: 55%;
          height: 95px;
          border-radius: 20px;
        }
        .div-info {
          font-size: 15px;
          padding-left: 20px;
          height: 95px;
          overflow: hidden;
          word-break: break-all;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 4;
        }
      }
    }

  }
}
</style>
