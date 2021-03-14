<template>
  <div>
    <el-container>
      <el-header height="100px">
        <router-link to="/">策划君管理系统</router-link>

        <div>
          <el-popover
          placement="top"
          v-model="visible">
            <el-button size="mini" type="text" @click="signOut">退出登陆</el-button>
            <!-- 这个slot 是用来触发弹框的 -->
            <div slot="reference" class="user-info">
              <img v-show="this.topData.userImage" :src="this.topData.userImage" alt="">
              <img v-show="!this.topData.userImage" src="../assets/c.jpg" alt="">
              <span v-show="this.topData.userName">{{ this.topData.userName }}</span>
            </div>
          </el-popover>
        </div>
      </el-header>

      <el-container>
        <el-aside 
        width="180px"
        height="672px"
        background-color="#a6eff7">
          <el-menu 
          @select="handlerSelect"
          background-color="#a6eff7"
          text-color="#000000"
          active-text-color="#66b1ff">
            <el-menu-item
            v-for="(item, index) in menuList"
            :index="item.path"
            :key="index">
              <span>
                {{item.menuName}}
              </span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main width="1000px">
          <router-view></router-view>
          <!-- ↑套东西的 -->
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import req from '@/api/use-manage.js'

export default {
  name: 'home',
  data () {
    return {
      visible: false,
      topData: {},
      menuList: []
    }
  },
  mounted () {
    this.search()
  },
  methods: {
    search () {

      req('getTopOfColumn').then(data => {
        this.topData = data.data
        // console.log('getTopOfColumn', data)

          // 如果在这里调用的话，当页面刷新会重复调用
          // req('listMenuHome', {role: data.data.role}).then(data => {
          //   // console.log('listMenuHome: ', data.data.menuList)
          //   this.menuList = data.data.menuList.map(item => {
          //     // console.log('this.menuList', this.menuList)
          //     return Object.assign({}, item, {
          //       title: item.menuName,
          //       path: item.menuPath
          //     })
          //   })
          // })

        this.menuList = JSON.parse(sessionStorage.getItem('menuInfo'))
      })
    },
    signOut () {
      // 清空账号信息
      sessionStorage.clear('userInfo')
      // 清空角色信息
      sessionStorage.clear('roleInfo')
      // 清空左边列表
      sessionStorage.clear('menuInfo')
      // 返回登陆界面
      this.$router.push({ path: 'login' })
    },
    handlerSelect (key, path) {
      if (key !== this.$route.path){ // 这一行的route 没有r
        this.$router.replace({path: key})
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .el-header {
    background-color: rgb(166, 239, 247);
    display: flex;
    justify-content: space-between;
    align-items: center;

    .router-link-active {
      font-size: 25px;
      color: #2597f5;
      text-decoration: none;
    }

    .user-info {
      display: flex;
      align-items: center;
      height: 100%;

      img {
        height: 60px;
        width: 60px;
        margin-right: 20px;
        border-radius: 50%;
      }
    }
  }
  
  .el-aside {
    background-color: while;
    color: #333;
    text-align: center;
    background: #f2f2f2;
    // line-height: 200px;
    padding: 0;
  }
  
  .el-main {
    background-color: while;
    color: #333;
    // text-align: center;
    // line-height: 160px;
  }
</style>
