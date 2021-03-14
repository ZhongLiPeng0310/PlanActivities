<template>
  <div>
    <div class="lead">
      <div class="leader">
          <ul>
            <li :class="{active: this.orderType === 'all'}" @click="getOrderList('', 'all')">全部订单</li>
            <li :class="{active: this.orderType === 'payed'}" @click="getOrderList(7, 'payed')">已付款</li>
            <li :class="{active: this.orderType === 'cancel'}" @click="getOrderList(9, 'cancel')">已取消</li>
            <li :class="{active: this.orderType === 'pickup'}" @click="getOrderList(1, 'pickup')">待取货</li>
            <li :class="{active: this.orderType === 'confirm'}" @click="getOrderList(5, 'confirm')">已完成</li>
          </ul>
      </div>
    </div>

    <div
    class="order-item"
    v-for="item of list"
    :key="item.id"
    @click="toPage(item)">
        <div class="item-top">
            <img src="../../assets/myOrder.png" alt="">
            <span>{{item.orderCode}}</span>
            <span>
                {{
                    item.orderState === 7 ? '已下单' :
                    item.orderState === 9 ? '已取消' :
                    item.orderState === 1 ? '已到货' :
                    item.orderState === 5 ? '已完成未评价' :
                    '已完成已评价'
                }}
            </span>
        </div>
        <div
        class="item-center"
        v-for="(childItem, index) in item.goodsList"
        :key="index">
            <div class="img-con">
                <img :src="childItem.imagePath" alt="">
            </div>
            <div class="text">
                <div class="text-con">
                    {{childItem.goodsName}}
                </div>
                <div class="props-con">
                    {{childItem.goodsIntroduce}}
                </div>
                <div class="price-con">
                    <span>￥</span>
                    <span>{{childItem.salePrice}} &nbsp;</span>
                    <span>x{{childItem.orderSum}}</span>
                </div>
            </div>
        </div>

        <div class="item-bottom">
            <span>共{{item.sumGoods}}件商品，合计￥</span>
            <span>{{item.orderMoney}}</span>
        </div>

        <div class="item-more" v-show="userType === 2">
            <div
              class="btn"
              @click.stop="orderupdateOrderState(item, 9)"
              v-show="item.orderState === 7 || item.orderState === 1">
                <span>取消订单</span>
            </div>

            <div class="btn" @click.stop="evaluate(item)" v-show="item.orderState === 5">
                <span>评价</span>
            </div>

            <div class="btn" @click.stop="orderupdateOrderState(item, 5)" v-show="item.orderState === 1">
                <span>确认收货</span>
            </div>
        </div>
        
        <div class="container" v-show="userType === 4">
          <div>
            <img class="img1" src="../../assets/u1056.png" alt="">
            {{item.userName}}
            <img class="img2" src="../../assets/u1060.png" alt="">
            {{item.phone}}
          </div>
        </div>

        <div class="item-more" v-show="userType === 3">
            <div
              class="btn"
              @click.stop="changeOrderStatus(item, 9)"
              v-show="item.orderState === 1 || item.orderState === 7">
                <span>取消订单</span>
            </div>
            <div class="btn" @click.stop="changeOrderStatus(item, 1)" v-show="item.orderState === 7">
                <span>确认到货</span>
            </div>
            <div class="btn" @click.stop="changeOrderStatus(item, 7)" v-show="item.orderState === 1">
                <span>取消到货</span>
            </div>
            <!-- <div class="btn" @click.stop="changeOrderStatus(item, 5)" v-show="item.orderState === 1">
                <span>确认取货</span>
            </div> -->
            <!-- <div class="btn" @click.stop="changeOrderStatus(item, 1)" v-show="item.orderState === 5">
                <span>取消取货</span>
            </div> -->
        </div>
    </div>
  </div>
</template>

<script>
import req from '@/api/order-list.js'

export default {
  name: 'order-list',
  data () {
    return {
      list: [],
      orderType: 'all'
    }
  },
  computed: {
    userType () {
      // console.log('userType: ', JSON.parse(sessionStorage.getItem('roleInfo')).role)
      return JSON.parse(sessionStorage.getItem('roleInfo')).role
    }
  },
  mounted () {
    console.log('this.$route.query: ', this.$route.query)
    this.getOrderList(this.$route.query.name, this.$route.query.code)
  },
  methods: {
    getOrderList (orderState = '', type) { // 订单-查询订单列表接口 type 是状态all
    
      console.log('this.$route.query.name: ', this.$route.query.name)
      let data = {
        orderState: orderState,
        pageSize: 100,
        pageNum: 1
      }

      if (!orderState) { // 清除已下单的订单编号
        delete data.orderState
      }
      this.orderType = type
      console.log('getOrderList.data.orderState', data)
      if (JSON.parse(sessionStorage.getItem('roleInfo')).role === 2) { // 客户端
        req('getOrderList', {
          ...data
        }).then(data => {
          console.log('getOrderList data', data)

          if (data.code === 0) {
            this.list = data.data.list
            console.log('this.list', this.list)
          } else {
            this.$message.info(data.msg)
          }
        })
      } else if (JSON.parse(sessionStorage.getItem('roleInfo')).role === 3) {
        req('getShopOrderList', {
          ...data
        }).then(data => {

          if (data.msg === '还没有客户下订单到您的店铺！'){
            this.list = ''
            
            this.$message.info('此订单列表已经完成所有操作')
            return
          }
          // console.log(data)
          if (data.code === 0) {
            this.list = data.data.list
          } else {
            this.$message.info(data.msg)
          }
        })
      }
    },
    toPage (data) { // 订单详情
      this.$router.push({path: '/order-detail', query: data}) // 同下
		},
		evaluate (data) { // 订单评价
			console.log('evaluate_data', data)
			this.$router.push({path: '/order-evaluate', query: data}) // query 传数据，data 是item （选中商品）的信息，在下一个界面用this.$route.query 拿到该信息
    }, 
    changeOrderStatus (item, changeStatus) { // 站长端
      console.log('item, changeStatus', item, changeStatus)
      this.$confirm('确定进行该操作吗?').then(() => {
        req('changeOrderStatus', {
          orderCode: item.orderCode,
          orderState: changeStatus,
          version: item.version
          }).then(data => {
          if (data.code === 0) {
            this.$message.success(data.msg)

            this.getOrderList('', this.orderType)
          } else {
            this.$message.error(data.msg)
          }
        })
      }).catch((e) => {}) // 用来消除 取消 按钮的报错（意料之外的取消）
    }, 
    orderupdateOrderState (item, changeStatus) { // 客户端
      console.log('item, orderupdateOrderState', item, changeStatus)
      this.$confirm('确定进行该操作吗?').then(() => {
        req('orderupdateOrderState', {
          orderCode: item.orderCode,
          orderState: changeStatus,
          version: item.version
          }).then(data => {
          if (data.code === 0) {
            this.$message.success(data.msg)

            this.getOrderList('', this.orderType)
          } else {
            this.$message.error(data.msg)
          }
        })
      }).catch((e) => {}) // 用来消除 取消 按钮的报错（意料之外的取消）
    }
  }
}
</script>

<style lang="scss" scoped>
  .lead{
    height: 45px; // 把滑动部分的高度超过外边框的高度，滑动条就会被隐藏掉。
    overflow-y: hidden;

    .leader {
        height: 64px;
        width: 100%;
        white-space: nowrap; // 元素不换行
        overflow-x: auto; // 设置x轴可滑动
        ul {
            height: 100%;
            padding: 0 0 0 2.5%;
            margin: 0;
            display: flex;
            li {
              line-height: 64px;
              padding: 0;
              margin: 0;
              list-style: none;
              padding: 0 13px;
              font-size: 15px;
            }
            .active {
              width: auto;
              font-size: 15px;
              color: rgb(195,152,98);
            }
        }
    }
  }
  .order-item {
      width: 95%;
      margin: 0 auto 12px;
      background-color: white;
      border-radius: 8px;
      padding: 10px 15px;
      box-sizing: border-box;

      .item-top {
          width: 100%;
          display: flex;
          align-items: center;
          position: relative;
          img {
              width: 13px;
              height: 16px;
          }
          span:nth-child(2) {
              font-size: 16px;
              flex: 1;
              line-height: 38px;
              position: relative;
              left: 8px;
          }
          span:nth-child(3) {
              position: absolute;
              right: 0;
              font-size: 13px;
              color: rgb(195,152,98);
          }
      }

      .item-center {
          width: 100%;
          display: flex;
          box-sizing: border-box;
          padding-top: 5px;
          .img-con {
              flex: 1;
              text-align: center;
              img {
                  width: 84px;
                  height: 84px;
                  border-radius: 15px;
              }
          }
          .text {
              flex: 2.2;
              .text-con {
                  font-size: 15px;
                  line-height: 22px;
                  overflow : hidden;
                  text-overflow: ellipsis;
                  display: -webkit-box;
                  -webkit-line-clamp: 2;
                  -webkit-box-orient: vertical;
              }
              .props-con {
                  font-size: 13.5px;
                  line-height: 26px;
                  color: rgb(168,168,168);
              }
              .price-con {
                  text-align: right;
                  line-height: 26px;
                  span:nth-child(1) {
                      font-size: 11px;
                      color: rgb(242,0,0);
                  }
                  span:nth-child(2) {
                      font-size: 16px;
                      color: rgb(242,0,0);
                  }
                  span:nth-child(3) {
                      font-size: 11px;
                      color: rgb(168,168,168);
                  }
              }
          }
      }

      .item-bottom {
          width: 100%;
          line-height: 40px;
          text-align: right;
          padding-top: 5px;
          span:nth-child(1) {
              font-size: 15px;
          }
          span:nth-child(2) {
              font-size: 20px;
          }
      }

      .item-more {
          width: 100%;
          display: flex;
          .btn {
              width: 92px;
              height: 28px;
              border-radius: 20px;
              position: relative;
              border: 1.5px solid rgb(195,152,98);
              margin-left: auto;
              margin-top: 10px;
              margin-bottom: 6px;
              span {
                  font-size: 15px;
                  position: absolute;
                  top: 50%;
                  left: 50%;
                  width: 100%;
                  text-align: center;
                  transform: translate(-50%, -50%);
                  color: rgb(195,152,98);
              }
          }
      }
      .container {
        width: 100%;
        border-bottom: 1px solid #dddddd;
        padding-left: 5px;

        div {
          width: 100%;
          height: 50px;
          line-height: 50px;
          display: flex;
          align-items: center;

          .img1 {
            height: 24px;
            width: 24px;
            margin-right: 10px;
          }
          .img2 {
            height: 24px;
            width: 24px;
            margin: 0 10px;
          }
        }
      }
  }
</style>
