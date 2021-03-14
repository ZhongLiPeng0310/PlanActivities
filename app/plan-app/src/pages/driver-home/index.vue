<template>
  <div>
    <div class="container" v-show="suerType === 'driver'">
      <div class="div_img">
        <img src="../../assets/u919.png" alt="">
        2020020713270034
      </div>
      <div class="div_span">
        <span>门店：新华书店（新街口店）</span>
      </div>
      <div class="div_span">
        <span>地址：江苏省南京市秦淮区中山东路65号</span>
      </div>
    </div>
    <div class="container2" v-show="suerType === 'driver'">
      <div class="div_span">
        <span>店长：张丽丽</span>
      </div>
      <div class="div_span">
        <span>手机：13745673313</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'driver-info',
  data () {
    return {}
  },
  methods: {
	    toPage (data) {
			this.$router.push({path: '/order-detail'})
    }
  },
  
  computed: {
    suerType () {
      return this.$store.state.suerType
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  margin-top: 10px;
  width: 100%;
  border-top: 1px solid #dddddd;
  border-bottom: 1px solid #dddddd;
  padding-left: 15px;

  .div_img {
    width: 100%;
    height: 50px;
    line-height: 50px;
    display: flex;
    align-items: center;

    img {
      margin: 10px;
    }
  }
  .div_span {
    padding-left: 25px;
    padding-bottom: 10px;
  }
}
.container2 {
  margin-top: 10px;
  width: 100%;
  border-bottom: 1px solid #dddddd;
  padding-left: 15px;

  .div_img {
    width: 100%;
    height: 50px;
    line-height: 50px;
    display: flex;
    align-items: center;

    img {
      margin: 10px;
    }
  }
  .div_span {
    padding-left: 25px;
    padding-bottom: 10px;
  }
}
</style>
