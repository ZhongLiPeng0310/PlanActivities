<template>
  <div class="container">
    <!-- 头部分类 -->
    <div class="header-lead">
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
    </div>

    <!-- 身体 -->
    <div class="body-list">
    <!-- 轮播图 -->
      <div class="banner-pic-list">
        <el-carousel height="170px" indicator-position="none">
          <el-carousel-item v-for="(item, index) in picList" :key="index">
            <img :src="item.imagePath" alt="" @click="toDetailPage222(item)">
          </el-carousel-item>
        </el-carousel>
      </div>

      <ul class="book-classify-conteiner">
        <li class="book-classify-item" v-for="(item, index) in commList" :key="index">
          <div class="book-classify-item-title">{{item.classSecondName}}</div>
          <ul class="book-list">
            <li
            v-for="(childItem, childIndex) in item.classSecondGoodsList"
            :key="childIndex"
            @click="getCommDetail(childItem)">

              <!-- <img :src="childItem.imagePath" alt=""> -->
              <img v-show="childItem.imagePath" :src="childItem.imagePath" >
              <img v-show="!childItem.imagePath" src="../../assets/timg.png">
              <div>{{childItem.goodsName}}</div>
              <div>
                ￥{{childItem.salePrice}}
              </div>

            </li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import req from '@/api/comm-classify2.js'

export default {
  name: 'comm-classify2',
  data () {
    return {
      picList: [], // 轮播图
      oneClassifyList: [], // 一级分类列表
      currentClassify: {},  // 选择了哪个一级分类
      commList: [] //商品
    }
  },
  mounted () {
    this.getClassify()
    this.getSliderPicList()
  },
  methods: {
    getClassify () { // 商品-查询一级商品分类列接口
      req('getClassify', {}).then(data => {
        console.log('一级getClassify_data', data)

        this.oneClassifyList = data.data

        this.currentClassify = this.$route.query.item

        this.getTwoClassify(this.currentClassify)
      })
    },
    getSliderPicList () { // 首页-查询首页轮播图接口
      req('getSliderPicList', {}).then(data => {
        console.log('getSliderPicList data: ', data)
        this.picList = data.data
      })
    },
    getTwoClassify (item) { // 商品-查询二级商品分类以及商品接口
      this.currentClassify = item
      console.log('item: ', item)
      req('getTwoClassify', {lastClassCode: item.classFirstCode}).then(data => {
        console.log('二级getTwoClassify_data', data)

        this.commList = data.data
      })
    },
    getCommDetail (item) {
      sessionStorage.setItem('currentComm', JSON.stringify(item)) // 记录点击的是哪一本书的信息

      this.$router.push({path: '/comm-detail'})
    },
    commClassify () {
      this.$router.push({path: '/comm-classify'})
    },
  }
}
</script>

<style lang="scss" scoped>
.container {
  position: absolute;
  top: 0;
  bottom: 0;
  width: 100%;
  background: #f7f6f6;
  margin-bottom: 60px;

  .header-lead { // 头
    position: fixed;
    z-index: 3;
    width: 100%;
    height: 65px;
    background: #f7f6f6;

    .lead{ // 分类框
      position: fixed;
      z-index: 3;
      left: 50%;
      transform: translate(-50%, 0);
      width: 95%;
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
  }

  .body-list { // 身体
    width: 100%;
    position: relative;
    top: 65px;
    margin-bottom: 60px;

    .banner-pic-list{ // 轮播图
      position: relative;
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

    .book-classify-conteiner {
      position: relative;
      bottom: 0;
      overflow: auto;

      .book-classify-item {
        .book-classify-item-title {
          width: 100%;
          height: 40px;
          padding-left: 10px;
          box-sizing: border-box;
          line-height: 40px;
        }

        .book-list {
          width: 95%;
          border-radius: 20px;
          margin: auto;
          background: #fff;
          display: flex;
          flex-wrap: wrap;
          padding: 10px 0;
          box-sizing: border-box;

          li {
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            align-items: center;
            width: 50%;
            border-radius: 20px;
            margin-bottom: 10px;

            img {
              width: 70%;
              height: 110px;
              border-radius: 20px;
            }

            div:nth-child(2) {
              font-size: 12px;
            }

            div:nth-child(4) {
              color: red;
              font-size: 14px;
              font-weight: bold;

              span {
                color: #ddd;
                font-weight: normal;
                font-size: 12px;
              }
            }
          }
        }
      }
    }
  }
  
}
</style>
