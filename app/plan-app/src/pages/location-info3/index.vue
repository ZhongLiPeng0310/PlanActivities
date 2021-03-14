<template>
  <div class="main">
    <ul class="book-list">
      <li class="book-list-item"
      v-for="(item, index) in getList"
      :key="index">

        <!-- 选择按钮 -->
        <div class="select-btn-box">
          <div class="select-btn-box-div">
            <input type="checkbox" @change="inputChange(item)">
            <div class="action" :style="{display: item.checked ? 'block' : 'none'}"></div>
          </div>
        </div>
        <!-- 地点信息 -->
        <div class="address">
          <!-- 图标 -->
          <div class="address-icon">
              <img src="../../assets/Dw.png" alt="">
          </div>
          <!-- 信息 -->
          <div class="address-info">
            <div class="address-info-div1">
              <span>{{item.getName}}</span>
              <span class="address-info-div1-span2">{{item.phone}}</span>
            </div>
            <div class="address-info-div2">{{item.provinceName}}&nbsp;{{item.cityName}}&nbsp;{{item.areaName}}&nbsp;{{item.address}}</div>
            <div v-show="item.betrue === 0" class="address-info-div3">默认地址</div>
          </div>
        </div>

      </li>
    </ul>
    <div>
      <div class="btn1">
          <el-button type="success" class="btn1-el-bu" @click="addLoca">添加</el-button>
      </div>
      <div class="btn2">
          <el-button type="primary" class="btn2-el-bu" @click="XiuLoca">修改</el-button>
      </div>
      <div class="btn3">
          <el-button type="danger" class="btn3-el-bu" @click="ShanLoca">删除</el-button>
      </div>
      <div class="btn4">
          <el-button type="primary" class="btn4-el-bu" @click="QueLoca">确定</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import IFile from '@/components/common/i-file.vue'
import req from '@/api/location-info.js'

export default {
  name: 'location-info',
  // 使用
  components: {
    IFile
  },
  data () {
    return {
      diZi: '',
      getList: [],
      roleInfo: JSON.parse(sessionStorage.getItem('roleInfo')),
      tableSelectRows: []
    }
  },
  mounted () {
    this.cankan()
  },
  methods: {
    cankan () {
      req('getAddress', {}).then(data => {
        this.getList = data.data
        this.diZi = this.getList[0].provinceName + this.getList[0].cityName + this.getList[0].areaName + this.getList[0].address
        console.log('this.diZi', this.diZi)
        this.getList = data.data.map(item => { // 按钮
          return Object.assign({}, item, {checked: false})
        })
        console.log('this.getList22', this.getList)
      })
    },
    addLoca () {
      this.$router.push({path: '/location-add'})
    },
    XiuLoca (){
      if (this.tableSelectRows.length === 0){
        this.$message.info('请选择需要修改的数据')
        return
      }
      if (this.tableSelectRows.length > 1) { // 当选中的数据数大于1
        this.$message.info('一次只能修改一个数据')
        return
      }
      console.log('this.tableSelectRows', this.tableSelectRows)
      this.$router.push({
        path: '/location-xiu',
        query: {
          xiuLoca: this.tableSelectRows
        }
      })
    },
    ShanLoca () { // 删除
      if (this.tableSelectRows.length === 0){
        this.$message.info('请选择需要删除的数据')
        return
      }
      console.log('this.tableSelectRows删除函数', this.tableSelectRows)
      req('deleteAddress', {
        addressCode: this.tableSelectRows[0].addressCode
      }).then(data => {
        this.$message.success(data.msg) // 打印
        // console.log('msg: ', data.msg)
        this.cankan() // 更新
      })
    },
    QueLoca () { // 确认
      if (this.tableSelectRows.length === 0){
        this.$message.info('请选择数据')
        return
      }
      if (this.tableSelectRows.length > 1) { // 当选中的数据数大于1
        this.$message.info('请选择一个收货地址')
        return
      }
      console.log('this.tableSelectRows确认', this.tableSelectRows)

      this.$router.push({
          path: '/payment-info2',
          query: {
            tableSelectRows: this.tableSelectRows[0],
            payCommList: this.$route.query.payCommList,
            goodsCode: this.$route.query.goodsCode,
            orderMoney: this.$route.query.orderMoney,
            orderSum: this.$route.query.orderSum,
            cartCode: this.$route.query.cartCode,
            storeCode: this.$route.query.storeCode
          }
        })
    },
    // 单选按钮
    inputChange (item) {
      console.log('选择的item', item)
      item.checked = !item.checked
      this.tableSelectRows = this.getList.filter(item => {
        return item.checked === true
      })
    },
  }

}
</script>

<style lang="scss" scoped>
.main {
  background: #F3F3F3;
  height: 100%;

  
  li {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      width: 95%;
      height: 85px;
      background: #fff;
      border-radius: 10px;
      padding: 10px 10px;
      box-sizing: border-box;
      margin: 0 10px 10px 10px;

      .select-btn-box {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 40px;
        height: 100%;
        margin-left: 8px;

        .select-btn-box-div {
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
      .address {
        position: relative;
        display: flex;
        background: white;
        border-radius: 10px;

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
            padding-top: 2px;
            // font-size: 12px;
            line-height: 30px;

            .address-info-div1-span2 {
              font-size: 15px;
              color: #999;
            }
          }
          .address-info-div3 {
            padding-top: 2px;
            line-height: 30px;
            font-size: 15px;
            color: red;
          }
        }

        .address-button {
          position: absolute;
          top: 50%;
          left: 85%;
          transform: translate(-50%, -50%);
        }
      }
      
    }
  }
  
  .btn1 { // 添加
    width: 80px;
    height: 40px;
    position: absolute;
    left: 87%;
    transform: translate(-50%, -50%);
    bottom: 50px;

    .btn1-el-bu {
      width: 100%;
      height: 100%;
      border-radius: 10px;
    }
  }
  .btn2 { // 添加
    width: 80px;
    height: 40px;
    position: absolute;
    left: 62%;
    transform: translate(-50%, -50%);
    bottom: 50px;

    .btn2-el-bu {
      width: 100%;
      height: 100%;
      border-radius: 10px;
    }
  }
  .btn3 { // 添加
    width: 80px;
    height: 40px;
    position: absolute;
    left: 37%;
    transform: translate(-50%, -50%);
    bottom: 50px;

    .btn3-el-bu {
      width: 100%;
      height: 100%;
      border-radius: 10px;
    }
  }
  .btn4 { // 添加
    width: 80px;
    height: 40px;
    position: absolute;
    left: 12%;
    transform: translate(-50%, -50%);
    bottom: 50px;

    .btn4-el-bu {
      width: 100%;
      height: 100%;
      border-radius: 10px;
    }
  }
</style>
