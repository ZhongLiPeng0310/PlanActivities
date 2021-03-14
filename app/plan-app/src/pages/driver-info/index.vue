<template>
  <div>
    <div
    class="container"
    v-for="(item, index) in driverList"
    :key="index">
      <div>
        <img src="../../assets/u1080.png" alt="">
        {{item.userName}}
      </div>
      <div>
        <img src="../../assets/u1060.png" alt="">
        {{item.phone}}
      </div>
    </div>
  </div>
</template>

<script>
import req from '@/api/driver-info.js'

export default {
  name: 'driver-info',
  data () {
    return {
      driverList: []
    }
  },
  mounted () {
    this.getDriver()
  },
  methods: {
    getDriver () {
      req('getStoreInfo', {}).then(data => {
        console.log('getStoreInfo data', data)
        this.driverList = data.data
      })
    }
  }
  
  // computed: {
  //   suerType () {
  //     return this.$store.state.suerType
  //     if (this.$store.state.suerType === 'driver') {
  //       this.$route.meta.title.show = false
  //       console.log(this.$route.meta.title.show)
  //     }
  //   }
  // }
}
</script>

<style lang="scss" scoped>

.el-container {
  .el-container {

    .el-main {
        padding: 0;
        width: 100%;

      .container {
        width: 100%;
        border-bottom: 1px solid #dddddd;
        padding-left: 15px;
        box-sizing: border-box;

        div {
          width: 100%;
          height: 50px;
          line-height: 50px;
          display: flex;
          align-items: center;

          img {
            height: 24px;
            width: 24px;
            margin: 10px;
          }
        }
      }
    }
  }
}

</style>
