<template>
  <div class="container">
    <div class="evaluate-title">
      <span :class="{active: evaluateBtnType === 'all'}" @click="getEvaluate('6', 'all')">全部评价</span>
      <span :class="{active: evaluateBtnType === 'perfect'}" @click="getEvaluate('5', 'perfect')">好评</span>
      <span :class="{active: evaluateBtnType === 'good'}" @click="getEvaluate('3', 'good')">中评</span>
      <span :class="{active: evaluateBtnType === 'differ'}" @click="getEvaluate('1', 'differ')">差评</span>
    </div>

    <ul class="evaluate-list">
      <li v-for="(item, index) in list" :key="index">
        <!-- <div class="head-pic">
          <img src="../../assets/head_pic.png" alt="">
        </div> -->

        <div class="content">
          <div class="title">
            <div class="user-info">
              <div class="name">{{item.userAcct}}</div>
              <div class="date">{{item.createTime}}</div>
            </div>

            <div
              class="star"
              :style="{backgroundPosition: `${90 - item.appraiseLevel*18}px 0`}">
            </div>
          </div>
          <div class="msg">{{item.appraiseDetai}}</div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import req from '@/api/comm-evaluate.js'

export default {
  name: 'comm-evaluate',
  data () {
    return {
      list: [],
      evaluateBtnType: 'all'
    }
  },
  mounted () {
    this.getEvaluate('6', 'all')
  },
  methods: {
    getEvaluate (score = '', type) {
      this.evaluateBtnType = type

      let commData = JSON.parse(sessionStorage.getItem('currentComm'))
      let data = {
        goodsCode: commData.goodsCode,
        appraiseLevel: score,
        pageSize: 100,
        pageNum: 1
      }

      if (!score) {
        delete data.evaluateScore
      }
      console.log('前getEvaluate', data)
      req('getEvaluate', {
        ...data
      }).then(data => {
        console.log('getEvaluate data', data)

        this.list = data.data.list
        
        if (this.list.length === 0){
          this.$message.info('暂无评价')
          return
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  background: rgb(119, 117, 117);
  width: 100%;
  height: 100%;
  padding-bottom: 10px;
  overflow: scroll;

  .evaluate-title {
    width: 100%;
    height: 50px;
    line-height: 50px;
    padding: 0 10px;
    box-sizing: border-box;

    span {
      padding: 0 10px;
    }

    .active {
      color: #C39862;
      font-size: 20px;
    }
  }

  .evaluate-list {
    width: 95%;
    background: #fff;
    margin: 0 auto;
    border-radius: 10px;

    li {
      display: flex;

      // .head-pic {
      //   width: 50px;
      //   height: 50px;
      //   padding: 10px;

      //   img {
      //     width: 100%;
      //     height: 100%;
      //   }
      // }

      .content {
        flex: 1;
        padding: 10px;

        .title {
          display: flex;
          justify-content: space-between;
          align-items: center;
          width: 100%;
          height: 60px;

          .user-info {
            .date {
              color: #ddd;
              font-size: 14px;
            }
          }

          .star {
            width: 90px;
            height: 18px;
            background-image: url('../../assets/stars.png');
            background-position: 0 0;
            background-repeat: no-repeat;
          }
        }
      }
    }
  }
}
</style>
