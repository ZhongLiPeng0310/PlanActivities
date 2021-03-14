<template>
  <div class="container">
    <ul class="book-classify">
      <li
      :class="{active: item.classFirstCode === currentClassify.classFirstCode}"
      v-for="(item, index) in oneClassifyList"
      :key="index"
      @click="getTwoClassify(item)">

        <b></b>
        <span>{{item.classFirstName}}</span>

      </li>
    </ul>

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
</template>

<script>
import req from '@/api/comm-classify.js'

export default {
  name: 'comm-classify',
  data () {
    return {
      oneClassifyList: [], // 一级分类列表
      currentClassify: {},  // 选择了哪个一级分类
      commList: [] //商品
    }
  },
  mounted () {
    this.getClassify()
  },
  methods: {
    getClassify () { // 商品-查询一级商品分类列接口
      req('getClassify', {}).then(data => {
        console.log('getClassify_data', data)

        this.oneClassifyList = data.data

        this.currentClassify = this.oneClassifyList[0]

        this.getTwoClassify(this.currentClassify)
      })
    },
    getTwoClassify (item) { // 商品-查询二级商品分类以及商品接口
      this.currentClassify = item
      console.log('item: ', item)
      req('getTwoClassify', {lastClassCode: item.classFirstCode}).then(data => {
        console.log('getTwoClassify_data', data)

        this.commList = data.data
      })
    },
    getCommDetail (item) {
      sessionStorage.setItem('currentComm', JSON.stringify(item)) // 记录点击的是哪一本书的信息

      this.$router.push({path: '/comm-detail'})
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  position: absolute;
  top: 0;
  bottom: 0;
  width: 100%;
  background: #ddd;
  margin-bottom: 60px;

  .book-classify {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    width: 20%;
    background: #fff;

    li {
      position: relative;
      width: 100%;
      height: 40px;
      text-align: center;
      line-height: 40px;
      font-size: 14px;

      >span {
        display: inline-block;
        width: 100%;
        height: 100%;
      }

    }
    li.active {
      color: rgb(197, 156, 104);
      background: #ddd;

      b {
        position: absolute;
        left: 0;
        top: 50%;
        width: 3px;
        height: 20px;
        background: rgb(197, 156, 104);
        margin-top: -10px;
      }
    }
  }

  .book-classify-conteiner {
    position: absolute;
    top: 0;
    left: 21%;
    bottom: 0;
    overflow: auto;
    width: 78%;

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
        background: #fff;
        display: flex;
        flex-wrap: wrap;
        padding: 10px 0;
        box-sizing: border-box;
        border-radius: 20px;
        margin: auto;

        li {
          display: flex;
          flex-direction: column;
          justify-content: flex-start;
          align-items: center;
          width: 50%;
          margin-bottom: 10px;

          img {
            width: 70%;
            height: 110px;
            border-radius: 20px;
          }

          div:nth-child(2) {
            font-size: 12px;
          }

          div:nth-child(3) {
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
</style>
