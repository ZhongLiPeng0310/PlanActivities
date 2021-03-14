<template>
  <div id="app">
    <!-- 解决页面刷新的方法 -->
      <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
export default {
    name: 'App',

    provide () { // 刷新
      return{
        reload: this.reload
      }
    },
    data () { // 刷新
      return{
        isRouterAlive: true
      }
    },
    methods:{ // 刷新
      reload(){
        this.isRouterAlive =false;
        this.$nextTick(function(){
          this.isRouterAlive=true
        })
      }
    }
  }
</script>

<style lang="scss">
@import "./styles/global.css";
@import "./styles/global.scss";
</style>
