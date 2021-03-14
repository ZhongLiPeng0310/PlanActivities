<template>
  <div class="container">
    <div
    class="comm"
    v-for="(item, index) in appraiseInfoList"
    :key="index">
      <div class="pic-info">
        <img :src="item.imagePath" alt="">

        <div class="star">
        <!-- element ui 组件 评分 -->
          <el-rate v-model="item.appraiseLevel"></el-rate>
        </div>
      </div>

      <div class="eva-box">
        <div class="title">分享你的使用体验吧</div>

        <el-input
        v-model="item.appraiseDetail"
        style="display: block;width:95%;margin: 0 auto;"
        type="textarea" :rows="8"></el-input>
      </div>

    </div>

      <el-button class="submit-btn" type="primary" @click="addEvaluate">提交</el-button>
  </div>
</template>

<script>
import req from '@/api/order-evaluate.js'
import axios from 'axios'

export default {
  name: 'order-evaluate',
  data () {
    return {
      appraiseInfoList: []
    }
  },
  mounted () {
    // console.log('this.$route.query: ', this.$route.query) // 在这里的时候console 好像是无效的
    // this.$route.query是order-list 里evaluate (data) 传过来的数据
    this.getEvaluate()
  },
  methods: {
    getEvaluate () { // 订单-查询订单评价商品信息列表接口
      req('getEvaluate', {
        orderCode: this.$route.query.orderCode
        }).then(data => {
        this.appraiseInfoList = data.data.goodsList
        console.log('this.appraiseInfoList: ', data)
      })
    },
    addEvaluate () { // 评价
      axios({
        method: 'post',
        url: 'http://29ng920368.wicp.vip/app/order/saveAppraise',
        data: JSON.stringify({
          orderCode: this.$route.query.orderCode,
          appraiseInfoList: this.appraiseInfoList.map(item => {
            return Object.assign({}, {
              goodsCode: item.goodsCode,
              appraiseDetail: item.appraiseDetail,
              appraiseLevel: item.appraiseLevel})
          })
        }),
        headers: {
          'Content-Type': 'application/json;',
          Authorization: 'Bearer ' + JSON.parse(sessionStorage.getItem('userInfo')).access_token // 使用sessionStorage 里面的access_token
        }
      }).then(data => {
        // 该状态
        console.log('this.appraiseInfoList该主题', this.appraiseInfoList)
        console.log('this.$route.query', this.$route.query)
        req('changeOrderStatus', {
          orderCode: this.$route.query.orderCode,
          orderState: 8,
          version: this.$route.query.version
          }).then(data => {
            console.log('改状态', data)
        })

        if (data.data.code === 0) {
          this.$message.success(data.data.msg)

          this.$router.push({path: '/order-list'}) // 成功之后跳转回订单页面，并改变订单页面的那个评价状态
        } else {
          this.$message.error(data.data.msg)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  background: #fff;

  .comm {
    width: 100%;
    padding: 10px 10px 0;
    box-sizing: border-box;
    background: #fff;

    .pic-info {
      width: 100%;
      display: flex;
      justify-content: space-between;
      align-items: center;

      img {
        width: 150px;
        height: 150px;
      }

      .star {
        width: 180px;
        height: 36px;
        background-repeat: no-repeat;
        background-size: 100% 100%;

        /deep/ .el-rate__item { // 不加权重不显示0 0
          .el-rate__icon {
            font-size: 25px;
          }
        }
      }
    }

    .eva-box {
      width: 100%;
      .title {
        padding-left: 10px;
        line-height: 40px;
      }
    }
  }

  .submit-btn {
    display: block;
    width: 95%;
    margin: 10px auto 0;
    height: 40px;
    line-height: 40px;
  }
}
</style>
