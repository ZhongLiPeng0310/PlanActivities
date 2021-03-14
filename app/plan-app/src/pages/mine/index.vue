<template>
  <div>
    <div class="user">
        <div class="img-con">
            <img v-show="!userInfo.imagePath" src="../../assets/user_default.png">
            <img v-show="userInfo.imagePath" :src="userInfo.imagePath" >
        </div>
        <div class="text-con">
          <div class="text-con-div">{{ userInfo.userName }}</div>
          <div class="text-con-div2">账号：{{ userInfo.userAcct }}</div>
        </div>
    </div>
    <!-- 供应商 4 -->
    <div class="container" v-show="userType === 4">
      <div>
        <img src="../../assets/u1157.png" alt="">
        {{userInfo.userName}}
      </div>
      <div>
        <img src="../../assets/u1151.png" alt="">
        {{userInfo.phone}}
      </div>
    </div>
    <!-- 站长 -->
    <div class="container2" v-show="userType === 3">
      <div>
        <img src="../../assets/u919.png" alt="">
        门店：{{userInfo.storeName}}
      </div>
      <div>
        <img src="../../assets/u295.png" alt="">
        {{userInfo.address}}
      </div>
    </div>

    <div class="content">

        <div class="con-item"
        v-show="userType === 2">

          <div class="order_img">
            <div @click.stop="evaluate(7, 'payed')" class="order_img_div">
              <img src="../../assets/yixiadan.png" alt="">
              <div>已付款</div>
            </div>
            
            <div @click.stop="evaluate(9, 'cancel')" class="order_img_div">
              <img src="../../assets/quxiao.png" alt="">
              <div>已取消</div>
            </div>

            <div @click.stop="evaluate(1, 'pickup')" class="order_img_div">
              <img src="../../assets/daishouhuo.png" alt="">
              <div>待取货</div>
            </div>

            <div @click.stop="evaluate(5, 'confirm')" class="order_img_div">
              <img src="../../assets/yiwancheng.png" alt="">
              <div>已完成</div>
            </div>

            <div @click.stop="evaluate('', 'all')" class="order_img_div">
              <img src="../../assets/quanbudingdan.png" alt="">
              <div>全部订单</div>
            </div>

          </div>

        </div>

        <div class="con-item">

          <div class="order_img">
            <div @click="toPage('/change-password')" class="order_img_div">
              <img src="../../assets/mima.png" alt="">
              <div>修改密码</div>
            </div>

            <div class="order_img_div" @click="Discount" v-show="userType === 2">
              <img src="../../assets/youhuiquan.png" alt="">
              <div>优惠卷</div>
            </div>

            <div class="order_img_div" @click="zhiLiao" v-show="userType === 2">
              <img src="../../assets/wdzl.png" alt="">
              <div>资料</div>
            </div>

            <div class="order_img_div" @click="Mphone">
              <img src="../../assets/dizhi.png" alt="">
              <div>地址</div>
            </div>

            <div @click="toLogin" class="order_img_div">
              <img src="../../assets/tuichu.png" alt="">
              <div>退出</div>
            </div>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
import req from '@/api/mine.js'

export default {
  name: 'mine',
  data () {
    return {
      userInfo: {},
    }
  },
  computed: {
    userType () {
      return JSON.parse(sessionStorage.getItem('roleInfo')).role
    }
  },
  mounted () {
    this.getUserInfo()
  },
  methods: {
    getUserInfo () {
      if (JSON.parse(sessionStorage.getItem('roleInfo')).role === 2) { // 客户

        req('getUserInfo', {}).then(data => {
          console.log('getUserInfo', data)
          this.userInfo = data.data
        })
      } else if (JSON.parse(sessionStorage.getItem('roleInfo')).role === 3) { // 站长

          req('getByCode', {}).then(data => {
          console.log('getByCode', data)
          this.userInfo = data.data
        })
      } else {
          req('getMySelf', {}).then(data => {
          console.log('getMySelf', data)
          this.userInfo = data.data
        })
      }

    },
		evaluate (data, type) { // 订单评价
			console.log('evaluate_data, type', data, type)
			this.$router.push({path: '/order-list', query: {name: data, code: type}}) // query 传数据，data 是item （选中商品）的信息，在下一个界面用this.$route.query 拿到该信息
    }, 
    toPage (data) {
      // console.log('toPage', data)
      this.$router.push({path: data})
    },
    Discount (){
      this.$confirm('您目前没有优惠卷', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {}).catch((e) => {}) // 用来消除 取消 按钮的报错
    },
    zhiLiao (){
      this.$confirm('您的资料无需修改', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {}).catch((e) => {}) // 用来消除 取消 按钮的报错
    },
    Mphone (){
      this.$router.push({path: '/location-info'})
    },
    toLogin () { // 退出登陆的时候清空sessionStorage 里面的数据
      sessionStorage.clear('userInfo')
      sessionStorage.clear('roleInfo')
      sessionStorage.clear('currentComm')

      this.$router.push('/login')
    }
  }
}
</script>

<style lang="scss" scoped>
.user {
    width: 100%;
    display: flex;
    height: 101px;

    .img-con {
        flex: 1;
        display: flex;
        align-items: center;
        img {
            border-radius: 50%;
            width: 65px;
            height: 65px;
            position: relative;
            left: 50%;
        }
    }
    .text-con {
      flex: 5;
      position: relative;
      top: 70%;
      left: 55%;
      transform: translate(-50%, -50%);
      
      .text-con-div {
        padding-bottom: 5px;
        font-size: 22px;
        box-sizing: border-box;
      }

      .text-con-div2 {
        padding-top: 5px;
        font-size: 12px;
        color: #999;
      }
    }
}

.container {
  width: 100%;
  border-bottom: 1px solid #dddddd;
  padding-left: 15px;
  box-sizing: border-box;

  div {
    width: 100%;
    height: 35px;
    line-height: 35px;
    display: flex;
    align-items: center;

    img {
      height: 24px;
      width: 24px;
      margin: 10px;
    }
  }
}

.container2 {
  width: 100%;
  border-bottom: 1px solid #dddddd;
  padding-left: 15px;
  padding-bottom: 15px;
  box-sizing: border-box;
  
  div {
    width: 95%;
    height: 40px;
    padding-right: 15px;
    box-sizing: border-box;
    display: flex;
    align-items: center;

    img {
      width: 20px;
      height: 20px;
      margin: 10px;
    }
  }
}

.content {
    width: 100%;
    height: 206px;
    padding: 18px 16px;
    margin: 0 auto;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    background-color: white;
    border-radius: 8px;

        .div_imgc {
          margin: 10px 0;
          position: relative;

          .img_left {
              position: absolute;
              left: 0;
              width: 16px;
              height: 20px;
              line-height: 20px;
          }
          .div_span1 {
              position: relative;
              left: 20px;
          }
          .div_span2 {
              position: absolute;
              font-size: 15px;
              color: #dddddd;
              right: 20px;
          }
          .img_rigth {
              position: absolute;
              right: 0;
              height: 20px;
              line-height: 20px;
          }
        }
        .div_img {
          margin: 10px 0;
          position: relative;

          .img_left {
              position: absolute;
              left: 0;
              width: 16px;
              height: 20px;
              line-height: 20px;
          }
          span {
              position: relative;
              left: 20px;
          }
          .img_rigth {
              position: absolute;
              right: 0;
              height: 20px;
              line-height: 20px;
          }
        }

        .order_img {
          display: flex;
          justify-content: space-around;
          border: 1px solid #999;
          border-radius: 20px;
          margin: 20px 0;
          padding: 10px;

          .order_img_div{
            text-align: center;

            img{
              width: 50px;
              height: 50px;
            }
          }
        }
}
  .img-spc {
      width: 15px;
      height: 15px;
  }
</style>
