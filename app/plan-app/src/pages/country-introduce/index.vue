<template>
  <div class="container">


    <div class="book-banner">
      <!-- <img :src="commDetailData.imagePath" alt=""> -->
      
      <img v-show="commDetailData.imagePath" :src="commDetailData.imagePath" >
      <img v-show="!commDetailData.imagePath" src="../../assets/timg.png">
    </div>

    <div class="book-font">
      <div>{{ commDetailData.villageName }}</div>
      <el-button icon="el-icon-star-off" circle></el-button>
    </div>
    <!-- <div class="con-Introduce">{{ commDetailData.villageIntroduce }}</div> -->
    <div class="cont">
      <div class="con-Intro">乡村介绍</div>
      <div v-show="!commDetailData.villageIntroduce" class="con-Introduce">一条条小河宛如蓝色的缎带缠绕着一望无际的绿色田野，远处一座座造型古朴、色彩和谐的小屋</div>
      <div v-show="commDetailData.villageIntroduce" class="con-Introduce">{{ commDetailData.villageIntroduce }}</div>
    </div>
    <div class="module-title">乡村产品</div>

    <ul class="comm-list">
      <li
        v-for="(item, index) in commList"
        :key="index"
        @click="toDetailPage(item)">
        <img :src="item.imagePath" alt="">
        <div class="book-info">
          <div>
            <span>{{item.goodsName}}</span>
            <span class="book-info-span">销售量：{{item.sumSale}}</span>
          </div>
          <span style="color: red;">￥{{item.salePrice}}</span>
        </div>
        <!-- <div>{{item.goodsPrice}}</div>  -->
      </li>
    </ul>
  </div>
</template>

<script>
import req from '@/api/comm-home.js'

export default {
  name: 'comm-home',
  data () {
    return {
      commDetailData: [],
      picList: [],
      commList: []
    }
  },
  mounted () {
    // this.getSliderPicList()
    this.getHotComm()
    this.getVillageAndGoods()
  },
  methods: {
    getSliderPicList () { // 首页-查询首页轮播图接口
      req('getSliderPicList', {}).then(data => {
        console.log('getSliderPicList data: ', data)
        this.picList = data.data
      })
    },
    getVillageAndGoods () { // 查询热门乡村及产品详情接口
      req('getVillageAndGoods', {
          villageCode: JSON.parse(
          sessionStorage.getItem('currentComm')).villageCode // 调用sessionStorage
        }).then(data => {
          console.log('查询热门乡村及产品详情接口 getVillageAndGoods_data', data)
          this.commDetailData = data.data[0]
          this.commDetailData.appraiseLevel = parseInt(data.data.appraiseLevel)
          console.log('this.commDetailData_data查询 ', this.commDetailData)
      })
    },
    getHotComm () { // 首页-查询热门乡村接口
      req('getVillageAndGoods', {villageCode: JSON.parse(
          sessionStorage.getItem('currentComm')).villageCode
      }).then(data => {
          console.log('getHotComm data', data.data[0].homePageInfoList)
          this.commList = data.data[0].homePageInfoList
      })
    },
    toDetailPage (data) {
      console.log('toDetailPage', data)
      sessionStorage.setItem('currentComm', JSON.stringify(data)) // 存信息，然后在乡村详情里面调用
      this.$router.push({path: '/comm-detail'})
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
  border: 0;
  // border-bottom-left-radius: 10px;
  // border-bottom-right-radius: 10px;
  box-sizing: border-box;

  img {
    display: block;
    width: 100%;
    height: 100%;
    margin: 0 auto;
    border-radius: 20px;
  }
}

.book-font{
  font-size: 20px;
  background: #fff;
  height: 30px;
  width: 100%;
  padding-left: 20px;
  display: flex;
  align-items: center;
  padding-bottom: 5px;
  justify-content: space-between;

  .el-button { 
    margin-right: 35px;
    height: 40px;
    line-height: 0;
  }
}

.cont {
    background: #fff;
    padding-left: 20px;
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;

    .con-Intro{
      color: rgb(145, 145, 145);
      font-style: italic;
    }

    .con-Introduce {
      padding-bottom: 15px;
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
}

.comm-list {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  width: 100%;
  // margin-top: 10px;

  li {
    position: relative;
    width: 48%;
    background: #fff;
    padding: 10px;
    box-sizing: border-box;
    margin: 5px 1%;
    border-radius: 10px;

    img {
      width: 100%;
      height: 170px;
      border-radius: 20px;
    }
    
    .book-info {
      font-size: 15px;
      display: flex;
      justify-content: space-between;

      /deep/.book-info-span {
        font-size: 10px;
        color: #ddd;
      }
    }
  }
}
</style>
