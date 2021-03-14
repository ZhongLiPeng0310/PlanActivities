<template>
  <div class="container">
    <ul class="book-list">
      <li class="book-list-item"
      v-for="(item, index) in list"
      :key="index">

        <!-- 选择按钮 -->
        <div class="select-btn-box">
          <div>
            <input type="checkbox" @change="inputChange(item)">
            <div class="action" :style="{display: item.checked ? 'block' : 'none'}"></div>
          </div>
        </div>
        <!-- 产品信息 -->
        <div class="book-count-info">
          <!-- <img :src="item.goodsImagePath" alt="">goodsIntroduce -->
          <img v-show="item.imagePath" :src="item.imagePath" >
          <img v-show="!item.imagePath" src="../../assets/timg.png">
          <div>
            <div>{{item.goodsName}}</div>
            <div>圆若骊珠，赤若金丸，肉似玻璃，核如黑漆。</div>
            <!-- <div>{{item.goodsIntroduce}}</div> -->  <!-- 此处是介绍 -->
            <div>
              <span>￥{{item.salePrice*item.orderSum}}</span>
              <div class="change-count-box">
                <div @click="countDelete(item)">-</div>
                <div>{{item.orderSum}}</div>
                <div @click="countAdd(item)">+</div>
              </div>
            </div>
          </div>
        </div>

      </li>
    </ul>

    <div class="close-count">
      <div>
        <div>
          <input type="checkbox" @change="allInputChange">
          <div class="action" :style="{display: allChecked ? 'block' : 'none'}"></div>
        </div>
        <span>全选</span>
      </div>

      <div>
        <div>
          <b>合计:</b>
          <span>{{totalPrice.toFixed(2)}}</span>
        </div>

        <el-button class="div-button" type="info" @click="deleteShopCar">删除</el-button>
        <el-button class="div-button" type="primary" @click="addShopCar">结算</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import req from '@/api/shop-car.js'

export default {
  name: 'shop-car',
  data () {
    return {
      allChecked: false,
      totalPrice: 0,
      list: []
    }
  },
  mounted () {
    this.getShopCarList()
  },
  methods: {
    getShopCarList () { // 查询购物车接口
      req('getShopData', {
        pageSize: 100, 
        pageNum: 1
        }).then(data => {
          console.log('getShopData_data', data)
          this.list = data.data.list.map(item => {
            return Object.assign({}, item, {checked: false})
        })

        this.allChecked = false
        this.totalPrice = this.getTotal()
      })
    },
    addShopCar () {
      let payCommList = this.list.filter(item => {
        return item.checked === true
      })

      if (!payCommList.length) {
        this.$message.info('请先选择需要购买的商品')

        return
      }

      let goodsIds = this.list.filter(item => { // 用逗号隔开传值
        return item.checked === true
      }).map(item => {
        return item.goodsCode
      }).toString()

      let clientGoodsNums = this.list.filter(item => { // 用逗号隔开传值
        return item.checked === true
      }).map(item => {
        return item.orderSum
      }).toString()

      let shopCartIds = this.list.filter(item => { // 用逗号隔开传值
        return item.checked === true
      }).map(item => {
        return item.cartCode
      }).toString()

      let storeCodes = this.list.filter(item => { // 用逗号隔开传值
        return item.checked === true
      }).map(item => {
        return item.storeCode
      }).toString()
      
      console.log('选中的项payCommList', this.payCommList)
      console.log('storeCodes_data ', storeCodes)
      sessionStorage.setItem('payCommList', JSON.stringify(payCommList))
      setTimeout(() => {
        this.$router.push({
          path: '/payment-info',
          query: {
            payCommList: payCommList,
            goodsCode: goodsIds,
            orderMoney: this.totalPrice.toFixed(2),
            orderSum: clientGoodsNums,
            cartCode: shopCartIds,
            storeCode: storeCodes
          }
        })
      })
    },
    deleteShopCar () {
      let payCommList = this.list.filter(item => {
        return item.checked === true
      })

      if (!payCommList.length) {
        this.$message.info('请先选择需要删除的商品')

        return
      }

      let shopCartIds = this.list.filter(item => {
        return item.checked === true
      }).map(item => {
        return item.cartCode
      }).toString()

      req('deleteShopCar', {cartCode: shopCartIds}).then(data => {
        if (data.code === 0) {
          this.$message.success(data.msg)

          this.getShopCarList()
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    // 全选按钮
    allInputChange () {
      this.allChecked = !this.allChecked

      this.list.forEach(item => {
        item.checked = this.allChecked
      })

      this.totalPrice = this.getTotal()
    },
    // 单选按钮
    inputChange (item) {
      item.checked = !item.checked

      for (let i = 0; i < this.list.length; i++) {
        if (this.list[i].checked === false) {
          this.allChecked = false

          break
        } else {
          this.allChecked = true
        }
      }

      this.totalPrice = this.getTotal()
    },
    countDelete (data) {
      if (data.orderSum > 1) {
        data.orderSum = parseFloat(data.orderSum) - 1
      }
      // console.log('updateAddSubCart-', data)
      req('updateAddSubCart', { // 修改数量记录在数据库
        cartCode: data.cartCode,
        orderSum: data.orderSum
        }).then(data => {
      })

      this.totalPrice = this.getTotal()
    },
    countAdd (data) {
      data.orderSum = parseFloat(data.orderSum) + 1
      req('updateAddSubCart', { // 修改数量记录在数据库
        cartCode: data.cartCode,
        orderSum: data.orderSum
        }).then(data => {
      })
      this.totalPrice = this.getTotal()
    },
    getTotal () {
      let total = 0

      this.list.forEach(item => {
        if (item.checked) {
          total = total + parseFloat(item.orderSum * item.salePrice)
        }
      })

      return parseFloat(total.toFixed(2))
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  height: 100%;
  background: #ddd;
  padding-bottom: 110px;
}
  .book-list {
    width: 100%;
    // position: absolute;
    // left: 50%;
    // bottom: 55px;
    overflow: auto;
    // transform: translate(-50%, 0);

    li {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      width: 100%;
      height: 150px;
      background: #fff;
      border-radius: 10px;
      padding: 10px 10px;
      box-sizing: border-box;
      margin-top: 10px;

      .select-btn-box {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 40px;
        height: 100%;

        >div {
          position: relative;
          width: 20px;
          height: 20px;
          border: 2px solid #ddd;
          border-radius: 50%;

          input {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            padding: 0;
            margin: 0;
            width: 15px;
            height: 15px;
            z-index: 1;
            opacity: 0;
          }

          .action {
            display: none;
            position: absolute;
            width: 15px;
            height: 15px;
            border-radius: 50%;
            background: rgb(197, 156, 104);
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 0;
          }
        }
      }

      .book-count-info {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        flex: 1;
        height: 100%;

        >img {
          width: 25%;
          height: 80%;
          vertical-align: middle;
          align-self: center;
          border-radius: 20px;
        }

        >div {
          > div:first-child {
            font-size: 20px;
            width: 100%;
            height: 30px;
            padding-left: 10px;
            box-sizing: border-box;
          }

          > div:nth-child(2) {
            font-size: 14px;
            width: 100%;
            height: 30px;
            padding-left: 10px;
            box-sizing: border-box;
            color: #ddd;
          }

          > div:nth-child(3) {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 14px;
            width: 100%;
            height: 30px;
            padding-top: 20px;
            padding-left: 10px;
            box-sizing: border-box;

            .change-count-box {
              display: flex;
              height: 20px;

              div:first-child {
                width: 30px;
                height: 20px;
                text-align: center;
                line-height: 20px;
                border: 1px solid #ddd;
              }

              div:nth-child(2) {
                width: 50px;
                height: 20px;
                text-align: center;
                line-height: 20px;
                border: 1px solid #ddd;
                border-left: none;
                border-right: none;
              }

              div:nth-child(3) {
                width: 30px;
                height: 20px;
                text-align: center;
                line-height: 20px;
                border: 1px solid #ddd;
              }
            }
          }
        }
      }
    }
  }

.close-count {
  position: fixed;
  bottom: 60px;
  width: 100%;
  height: 50px;
  background: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 10px;
  box-sizing: border-box;

  div:first-child {
    display: flex;
    height: 50px;
    align-items: center;

    div {
      position: relative;
      width: 20px;
      height: 20px;
      border: 2px solid #ddd;
      border-radius: 50%;
      margin-right: 10px;

      input {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        padding: 0;
        margin: 0;
        width: 15px;
        height: 15px;
        z-index: 1;
        opacity: 0;
      }

      .action {
        display: none;
        position: absolute;
        width: 15px;
        height: 15px;
        border-radius: 50%;
        background: rgb(197, 156, 104);
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 0;
      }
    }

    span {
      width: 32px;
    }
  }

  div:nth-child(2) {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    height: 50px;
    flex: 1;

    >div:first-child {
      height: 50px;
      line-height: 50px;
      font-size: 14px;
      flex: 1;
      margin-left: 10px;


      b {
        width: 32px;
      }
      span {
        font-size: 18px;
        color: red;
      }
    }

    .div-button {
      width: 80px;
      height: 40px;
      color: #fff;
      outline: none;
      text-align: center;
      border: none;
      border-radius: 10px;
      margin-left: 20px;
    }
  }
}

</style>
