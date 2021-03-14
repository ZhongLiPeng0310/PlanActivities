<template>
  <div class="container">
    <div class="book-banner">
      <!-- <img :src="commDetailData.imagePath" alt=""> -->
      <img v-show="commDetailData.imagePath" :src="commDetailData.imagePath" >
      <img v-show="!commDetailData.imagePath" src="../../assets/timg.png">
    </div>

    <div class="book-detail">
      <div class="book-price">￥{{commDetailData.salePrice}}</div>
      <div class="book-name">{{commDetailData.goodsName}}</div>
      <div class="book-Describe">{{commDetailData.goodsIntroduce}}</div>
      <div class="comm-evaluate">
        商品评分:
        <!-- 加一个星星 -->
        <el-rate v-model="commDetailData.appraiseLevel" disabled></el-rate>
        <span>{{commDetailData.appraiseLevel}}</span>
        分
      </div>
      <div class="count-box">
        <span>数量</span>

        <div class="handler-box">
          <div @click="countDelete">-</div>
          <div>{{bookCount}}</div>
          <div @click="countAdd">+</div>
        </div>
      </div>

      <div class="address">
        <span>送至</span>
        <span class="iconfont iconhtmal5icon14"></span>
        <span v-show="commDetailData.storeName">{{commDetailData.storeName}}</span>
        <span v-show="!commDetailData.storeName">{{address}}</span>
      </div>
    </div>

    <div class="footer">
      <div @click="toShopCar">
        <img src="../../assets/shop_car2.png" alt="">
        <span>购物车</span>
      </div>

      <div @click="addShopCar">加入购物车</div>
      <div @click="payNow">立即购买</div>
    </div>

  </div>
</template>

<script>
import req from '@/api/comm-detail.js'

export default {
  name: 'comm-detail',
  data () {
    return {
      payCommList: [],
      commDetailData: {
        goodsName: '',
        goodsIntroduce: '',
        salePrice: '',
        imagePath: '',
        appraiseLevel: 0,
        goodsCode: '',
        version: ''
      },
      bookCount: 1, // 购买的数量
      address: '新华书店（新街口店）'
    }
  },
  mounted () {
    this.getDetail()
  },
  methods: {
    countDelete () {
      if (this.bookCount > 1) {
        this.bookCount--
      }
    },
    countAdd () {
      this.bookCount++
    },
    getDetail () { // 查询商品信息详情接口
      req('getDetail', {
        goodsCode: JSON.parse(
          sessionStorage.getItem('currentComm')).goodsCode // 调用sessionStorage 里currentComm的数据
        }).then(data => {
          console.log('getDetail_data', data)
          this.commDetailData = data.data
          this.commDetailData.appraiseLevel = parseInt(data.data.appraiseLevel)
           console.log('this.commDetailData_data ', this.commDetailData)
      })
    },
    toShopCar () {
      this.$router.push({path: '/shop-car'})
    },
    payNow () { // 新增订单接口
      let orderMoneys = parseFloat(this.commDetailData.salePrice) * parseFloat(this.bookCount)
      console.log('orderMoneys', orderMoneys)
      console.log('this.commDetailData_data2 ', this.commDetailData)

        // req('payNow', {
        //   goodsCode: this.commDetailData.goodsCode,
        //   orderSum: this.bookCount,
        //   orderMoney: orderMoneys,
        //   cartCode: '0',
        //   storeCode: this.commDetailData.storeCode
        // }).then(data => {
        //   if (data.code === 0) {
        //     this.$message.success(data.msg)

        //     this.$router.push({path: '/order-list'})
        //   } else {
        //     this.$message.error(data.msg)
        //   }
        // })
        console.log('sessionStorage.getItem', sessionStorage.getItem('currentComm'))

        this.$router.push({
          path: '/payment-info2',
          query: {
            goodsCode: this.commDetailData.goodsCode,
            orderMoney: orderMoneys,
            orderSum: this.bookCount,
            cartCode: 0,
            storeCode: this.commDetailData.storeCode
          }
        })
    },
    addShopCar () { // 新增购物车接口
      req('addShopCar', {
        goodsCode: this.commDetailData.goodsCode,
        orderSum: this.bookCount,
        storeCode: this.commDetailData.storeCode
        }).then(data => {
        if (data.code === 0) {
          this.$message.success(data.msg)

        } else {
          this.$message.error(data.msg)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  background: #ddd;
}

.book-banner {
  width: 100%;
  height: 300px;
  padding: 10px;
  background: #fff;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  box-sizing: border-box;

  img {
    display: block;
    width: 80%;
    height: 100%;
    margin: 0 auto;
    border-radius: 20px;
  }
}

.book-detail {
  padding: 10px;
  box-sizing: border-box;
  background: #fff;
  margin-top: 10px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  width: 100%;
  margin: 10px auto 0;

  .book-price {
    font-size: 22px;
    color: red;
  }

  // .book-name {
  //   font-size: 18px;
  //   line-height: 30px;
  // }

  .comm-evaluate { // 评分
    margin-top: 10px;
    span {
      color: red;
      font-size: 20px;
    }
  }

  .book-Describe {
    font-size: 14px;
    color: #333;
    font-weight: bold;
    border-bottom: 1px solid #ddd;
    padding: 10px 0;
  }

  .count-box {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 40px;

    .handler-box {
      display: flex;
      height: 30px;
      align-items: center;
      border: 1px solid #ddd;
      border-radius: 5px;
      margin-top: 10px;

      div:first-child {
        width: 30px;
        height: 30px;
        text-align: center;
        line-height: 30px;
      }

      div:nth-child(2) {
        width: 40px;
        height: 30px;
        text-align: center;
        line-height: 30px;
        border-left: 1px solid #ddd;
        border-right: 1px solid #ddd;
      }

      div:nth-child(3) {
        width: 30px;
        height: 30px;
        text-align: center;
        line-height: 30px;
      }
    }
  }

  .address {
    margin-top: 10px;

    .iconhtmal5icon14 {
      color: #ddd;
    }
  }
}

.footer {
  width: 100%;
  height: 50px;
  position: fixed;
  bottom: 0;
  background: #fff;
  border-top: 1px solid #ddd;
  display: flex;
  justify-content: flex-end;
  box-sizing: border-box;
  align-items: center;

   div:first-child {
    width: 50px;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    font-size: 12px;

    img {
      width: 25px;
      height: 25px;
    }
  }

  div:nth-child(2) {
    width: 100px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    border: 1px solid rgb(197, 156, 104);
    background: #fff;
    border-radius: 10px;
    margin-left: 10px;
  }

  div:nth-child(3) {
    width: 100px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    background: rgb(197, 156, 104);
    color: #fff;
    border-radius: 10px;
    margin-left: 10px;
    margin-right: 10px;
  }
}
</style>
