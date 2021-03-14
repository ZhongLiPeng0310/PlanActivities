<template>
  <div class="container">

    <div class="header-info">
      <el-input v-model="villageName" placeholder="请输入乡村名称"></el-input>
      <el-button icon="el-icon-search" @click="getHotComm"></el-button>   
    </div>

    
    <ul class="comm-list">
      <li
        v-for="(item, index) in commList"
        :key="index"
        @click="toDetailPage(item)">
        
        <div class="book-info">
          <div>{{item.villageName}}</div>
          <div class="book-img">
            <img :src="item.imageCode" alt="">
            <div class="div-info">{{item.villageIntroduce}}</div>
          </div>
        </div>
        <!-- <div>{{item.goodsPrice}}</div> -->
      </li>
    </ul>
  </div>
</template>

<script>
import req from '@/api/search-comm.js'

export default {
  name: 'search-comm',
  data () {
    return {
      villageName: '',
      pageInfo: { // 子组件传过来的值
        pageIndex: 1,
        pageSize: 5,
        total: 0
      },
      picList: [],
      commList: []
    }
  },
  mounted () {
    this.getHotComm ()
  },
  methods: {
    skip () {
      console.log(123456)
    },
    getHotComm () { // 搜索
      req('getVillageList', {
        villageName: this.villageName,
        pageNum: this.pageInfo.pageNum,
        pageSize: this.pageInfo.pageSize
      }).then(data => {
        console.log('getVillageList data', data)
        this.commList = data.data.list
      })
    },
    toDetailPage (data) {
      console.log('toDetailPage', data)
      sessionStorage.setItem('currentComm', JSON.stringify(data)) // 存信息，然后在乡村详情里面调用
      this.$router.push({path: '/country-introduce'})
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 1px;
  height: 100%;
  padding-bottom: 70px;
  background: #ddd;
  overflow: scroll;
}

.header-info {
  margin: 5px;
  display: flex;
                 
  .el-input {
    /deep/ .el-input__inner {
      height: 30px;
    }
  }
  .el-button {
    position: relative;
    height: 30px;
    width: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    line-height: 30px;
    background-repeat: no-repeat;
    background-size: 60% 90%;
    background-position: 50% 50%;
  }
}

.el-carousel {
  .el-carousel__container {
    .el-carousel__item {
      img {
        width: 100%;
        height: 100%;
      }
    }
  }
}

.module-title {
  width: 100%;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #fff;
  background: #ddd;
  margin-top: 10px;
  font-weight: bolder;
  font-size: 20px;
}

.comm-list {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  width: 100%;
  // margin-top: 10px;

  li {
    position: relative;
    width: 100%;
    background: #fff;
    padding: 10px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 5px 1%;
    border-radius: 10px;
    height: 140px;
    display: flex;

    .book-info {

      div:first-child {
        font-size: 15px;
        padding-left: 10px;
      }
      .book-img {
        width: 100%;
        height: 100%;
        border-radius: 20px;
        display: flex;
        
        img {
          width: 55%;
          height: 95px;
          border-radius: 20px;
        }
        .div-info {
          font-size: 15px;
          padding-left: 20px;
          height: 95px;
          overflow: hidden;
          word-break: break-all;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 4;
        }
      }
    }

  }
}
</style>
