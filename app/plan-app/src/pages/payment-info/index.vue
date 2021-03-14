<template>
  <div class="main">
    
    <!-- 地点信息 -->
    <div class="address" @click="DwAdd()">
      <!-- 图标 -->
      <div class="address-icon">
          <img src="../../assets/Dw.png" alt="">
      </div>
      <!-- 信息 -->
      <div class="address-info">
        <div v-show="this.row===undefined" class="address-info-div1">
          <span>{{this.xingming}}</span>
          <span class="address-info-div1-span2">{{this.Uphone}}</span>
        </div>
        <div v-show="this.row!==undefined" class="address-info-div1">
          <span>{{this.row}}</span>
          <span class="address-info-div1-span2">{{this.Rphone}}</span>
        </div>
        <div v-show="this.row===undefined" class="address-info-div2">{{this.diZi}}</div>
        <div v-show="this.row!==undefined" class="address-info-div1">{{this.dingwei}}</div>
        <div class="address-info-div3">收获不便时，可选择暂存服务</div>
      </div>
    </div>

    <!-- 商品信息 -->
    <div class="book">
      <div>
        <!-- 店名 -->
      </div>
      <!-- 购物车 -->
      <ul class="book-list">
        <li class="book-list-item"
        v-for="(item, index) in payCommList"
        :key="index">

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
                  <div>共{{item.orderSum}}件</div>
                </div>
              </div>
            </div>
          </div>

        </li>
      </ul>

      <div class="close-count">
        <div>
          <div>
            <b>合计:</b>
            <span>{{this.$route.query.orderMoney}}</span>
          </div>

          <el-button class="div-button" type="primary" @click="addShopCar">结算</el-button>
        </div>
      </div>

    </div>

  </div>
</template>

<script>
import req from '@/api/payment-info.js'

export default {
  name: 'payment-info',
  data () {
    return {
      row: undefined, // 更新名字
      Rphone: '',
      dingwei: undefined, // 更新地址
      xingming: '',
      Uphone: '',
      diZi: '',
      getList: [],
      newGetList: [],
      totalPrice: 0,
      payCommList: JSON.parse(sessionStorage.getItem('payCommList')),
      roleInfo: JSON.parse(sessionStorage.getItem('roleInfo'))
    }
  },
  mounted () {
    this.con()
  },
  methods: {
    con () {
      console.log('this.roleInfo ', this.roleInfo)
      console.log('this.payCommList', this.payCommList)
      console.log('this.$route.query.payCommList ', this.$route.query.payCommList)
      console.log('this.$route.query ', this.$route.query)
      console.log('传值this.$route.query.tableSelectRows', this.$route.query.tableSelectRows)

      if (this.$route.query.tableSelectRows!==undefined) {
        console.log('看看你')
        this.row = this.$route.query.tableSelectRows.getName
        this.Rphone = this.$route.query.tableSelectRows.phone
        this.dingwei = this.$route.query.tableSelectRows.provinceName 
        + this.$route.query.tableSelectRows.cityName 
        + this.$route.query.tableSelectRows.areaName 
        + this.$route.query.tableSelectRows.address
      }

      console.log('传值this.row', this.row)
      req('getAddress', {}).then(data => {
          console.log('getAddress', data)
          this.getList = data.data
          console.log('查询地址this.getList', this.getList)
          // 筛选默认地址
          this.newGetList = this.getList.filter(item => {
            console.log('遍历数组的默认参数 item.betrue', item.betrue)
            return item.betrue == 0
          })
          this.diZi = this.newGetList[0].provinceName + this.newGetList[0].cityName + this.newGetList[0].areaName + this.newGetList[0].address
          this.xingming = this.newGetList[0].getName
          this.Uphone = this.newGetList[0].phone
          console.log('默认地址this.newGerList ', this.newGetList)

          console.log('this.diZi', this.diZi)
        })
    },
    DwAdd () {
      console.log(111)
      this.$router.push({path: '/location-info2'
        , query: {
          goodsCode: this.$route.query.goodsCode,
          orderMoney: this.$route.query.orderMoney,
          orderSum: this.$route.query.orderSum,
          cartCode: this.$route.query.cartCode,
          storeCode: this.$route.query.storeCode
        }
      })
    },
    addShopCar () { // 结算
      if (this.row===undefined){
        req('addOrder', {
          goodsCode: this.$route.query.goodsCode,
          orderMoney: this.$route.query.orderMoney,
          orderSum: this.$route.query.orderSum,
          cartCode: this.$route.query.cartCode,
          storeCode: this.$route.query.storeCode,
          addressCode: this.newGetList[0].addressCode
        }).then(data => {
          if (data.code === 0) {
            this.$message.success(data.msg)
  
            setTimeout(() => {
              this.$router.push({path: '/order-list'})
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      } else {
          req('addOrder', {
            goodsCode: this.$route.query.goodsCode,
            orderMoney: this.$route.query.orderMoney,
            orderSum: this.$route.query.orderSum,
            cartCode: this.$route.query.cartCode,
            storeCode: this.$route.query.storeCode,
            addressCode: this.$route.query.tableSelectRows.addressCode
          }).then(data => {
            if (data.code === 0) {
              this.$message.success(data.msg)
    
              setTimeout(() => {
                this.$router.push({path: '/order-list'})
              })
            } else {
              this.$message.error(data.msg)
            }
          })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  background: #F3F3F3;
  height: 100%;

  .address {
    display: flex;
    background: white;
    border-radius: 10px;
    padding: 10px 10px;
    margin: 0 10px 0 10px;

    .address-icon {
      margin: 10px;
      
      img{
        display: flex;
        width: 40px;
        height: 40px;
      }
    }

    .address-info {
      padding-left: 5px;

      .address-info-div1 {

        .address-info-div1-span2 {
          font-size: 15px;
          color: #999;
        }
      }

      .address-info-div3 {
        padding-top: 2px;
        font-size: 12px;
        color: red;
      }
    }
  }

  .book {
    margin: 0 10px;

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

                div {
                  width: 50px;
                  height: 20px;
                  text-align: center;
                  line-height: 20px;
                  border: 1px solid #ddd;
                  border-left: none;
                  border-right: none;
                }
              }
            }
          }
        }
      }
    }
  }
  .close-count {
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 50px;
    background: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 10px;
    margin-left: -10px;
    box-sizing: border-box;

    div {
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
}
</style>
