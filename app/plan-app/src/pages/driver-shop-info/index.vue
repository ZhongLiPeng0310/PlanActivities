<template>
  <div>
    <div class="box" v-for="(item, index) in list" :key="index">
      <div class="shop-info">
        <img src="../../assets/u919.png" alt="">
        <span>{{item.storeCode}}</span>
      </div>
      <div>
        站点：{{item.storeName}}
      </div>
      <div>
        地址：{{item.address}}
      </div>

      <div>
        站长：{{item.userName}}
      </div>
      <div>
        手机：{{item.phone}}
      </div>
    </div>
  </div>
</template>

<script>
import req from '@/api/driver-shop-info.js'

export default {
  data () {
    return {
      list: []
    }
  },
  mounted () {
    this.getDriverInfo()
  },
  methods: {
    getDriverInfo () {
      req('getDriverInfo', {}).then(data => {
        this.list = data.data
        console.log('getDriverInfo', data)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.box {
  padding: 10px;
  border-bottom: 1px solid #ddd;

  .shop-info {
    width: 100%;
    height: 30px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding-left: 0;

    img {
      margin-right: 6px;
    }
  }

  >div {
    height: 30px;
    line-height: 30px;
    padding-left: 30px;
  }
}
</style>
