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
            <div v-if="item.audit === 2"> 审核未通过 </div>
            <div v-if="item.audit === 3"> 未审核 </div>
            <div v-if="item.audit === 1"> 通过审核 </div>
          </div>
        </div>
      </li>
    </ul>

    <div class="close-count" v-show="userType === 3">
      <div>
        <div>
          <input type="checkbox" @change="allInputChange">
          <div class="action" :style="{display: allChecked ? 'block' : 'none'}"></div>
        </div>
        <span>全选</span>
      </div>

      <div>
        <button @click="deleteShopCar">驳回</button>
        <button @click="addShopCar">通过</button>
      </div>
    </div>
  </div>
</template>

<script>
import req from '@/api/inquire-comm.js'

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
    this.getDriver()
  },
  computed: {
    userType () {
      return JSON.parse(sessionStorage.getItem('roleInfo')).role
    }
  },
  methods: {
    getDriver () {
      req('getSupplierMyGoods', { // 查询提交的产品信息
        storeCode: JSON.parse(sessionStorage.getItem('roleInfo')).storeCode
      }).then(data => {
        console.log('getSupplierMyGoods data', data)
        this.list = data.data.map(item => {
            return Object.assign({}, item, {checked: false})
        })
        this.allChecked = false // 选择了之后改变按钮
      })
    },
    addShopCar () { // 审核通过
      let payCommList = this.list.filter(item => {
        return item.checked === true
      })

      if (!payCommList.length) {
        this.$message.info('请先选择审核的商品')

        return
      }

      let shopCartIds = this.list.filter(item => {
        return item.checked === true
      }).map(item => {
        return item.goodsCode
      }).toString()

      let versions = this.list.filter(item => {
        return item.checked === true
      }).map(item => {
        return item.version
      }).toString()
      
      req('addOrder', { // 审核通过
        goodsCode: shopCartIds,
        audit: 1,
        version: versions
      }).then(data => {
        if (data.code === 0) {
          this.$message.success(data.msg)

          this.getDriver() // 更新页面
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    deleteShopCar () { // 驳回的商品
      let payCommList = this.list.filter(item => {
        return item.checked === true
      })

      if (!payCommList.length) {
        this.$message.info('请先选择驳回的产品')

        return
      }

      let shopCartIds = this.list.filter(item => {
        return item.checked === true
      }).map(item => {
        return item.goodsCode
      }).toString()

      let versions = this.list.filter(item => {
        return item.checked === true
      }).map(item => {
        return item.version
      }).toString()

      console.log ('goodsCode version', shopCartIds, versions)
      req('deleteShopCar', { // 驳回
        goodsCode: shopCartIds,
        audit: 2,
        version: versions
        }).then(data => {
        if (data.code === 0) {
          this.$message.success(data.msg)

          this.getDriver() // 更新页面
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

    },
  }
}
</script>

<style lang="scss" scoped>
.container {
  // height: 300%;
  background: #ddd;
  padding-bottom: 110px;
}
  .book-list {
    width: 100%;
    overflow: auto;

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
          }
        }
      }
    }
  }

.close-count {
  position: fixed;
  bottom: 0px;
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
  }

  div:nth-child(2) {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    height: 50px;
    flex: 1;

    button {
      width: 80px;
      height: 40px;
      background: rgb(197, 156, 104);
      color: #fff;
      outline: none;
      line-height: 40px;
      text-align: center;
      border: none;
      border-radius: 10px;
      margin-left: 20px;
    }

    button:nth-child(2) {
      background: red;
    }
  }
}

</style>
