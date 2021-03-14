<template>
  <div>
    <!-- 背景 -->
    <div>
      <img id="u0_img" class="img " src="../assets/hucs.jpg"/>
    </div>
    <!-- 组合 -->
    <div class="u2">
      <div class="u3">
        <div class="u4">
          <div class="u5">
            <p><span>请登录</span></p>
          </div>
          <el-form :model="dialogFormData2" ref="form" label-width="100px">
            <el-row>
              <el-col :span='15'>
                  <el-input v-model="dialogFormData2.username" placeholder="请输入用户名"></el-input>
              </el-col>
              <el-col :span='15'>
                  <el-input type="password" v-model="dialogFormData2.password" placeholder="请输入密码"></el-input>
              </el-col>
            </el-row>
            <el-button type="primary" @click="login">确 定</el-button>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import req from '@/api/use-manage.js'

export default {
  name: 'login',
  data () {
    return {
      dialogFormData2: {
        username: '',
        password: ''
      },
      // menuList: []
    }
  },
  methods: {
    // dialogconfirm () {
    //   this.$router.push({path: '/user-manage'})
    // }
    login () {
      axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
      axios({
        method: 'post',
        url: 'http://localhost:8080/uaa/auth/form', // 本地
        data: qs.stringify(this.dialogFormData2),
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;'
        }
      }).then(data => {
        console.log(data)
        if (data.data.code === 0) {
          this.$message({
            type: 'success',
            message: data.data.msg
          })

          sessionStorage.setItem('userInfo', JSON.stringify(data.data.data))

          // this.$router.push({path: '/client-manage'})  // 直接跳转
          this.getHeader(data.data.data.access_token)  // 进入顶部栏api 再跳转
        } else {
          this.$message({
            type: 'error',
            message: data.data.msg
          })
        }
      })
    },
    getHeader (token) {
      axios({
        method: 'post',
        url: 'http://localhost:8080/pc/user/getUserUrl', // 本地
        data: qs.stringify({access_token: token}),
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;'
        }
      }).then(data => {
        if (data.data.code === 0) {
          sessionStorage.setItem('roleInfo', JSON.stringify(data.data.data))

          // console.log('login data: ', data)
          console.log('data.data.data.role: ', data.data.data.role)

          // 解决home 页面刷新重复调用的问题（这样就可以做到，新增之后重新登陆才会刷新）
          req('listMenuHome', {role: data.data.data.role}).then(data => {
            console.log('data: ', data)
            console.log('listMenuHome: ', data.data)  // 查看data
            let menuList = data.data.map(item => { // 要在这里定义 不然会异步（应该是异步）
              // console.log('this.menuList', menuList)
              return Object.assign({}, item, {
                title: item.menuName,
                path: item.route
              })
            })
            sessionStorage.setItem('menuInfo', JSON.stringify(menuList)) // 存入sessionStorage 方便home 页面的调用
          })

          if (data.data.data.role === '2') {
            this.$router.push({ path: '/client-manage' })
          } else {
            this.$router.push({ path: '/home' })
          }
        } else {
          this.$message({
            type: 'error',
            message: data.data.msg
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
#u0_img {
  position: absolute;
  left: 0px;
  top: 0px;
  width: 100%;
  height: 100%;
}
.u2{
  position: absolute;
  left: 0px;
  top: 0px;
  width: 0px;
  height: 0px;

  .u3 {
    position: absolute;
    left: 800px;
    top: 120px;
    width: 278px;
    height: 249px;
    color: #777;
    background: rgba(255, 255, 255, 0.5);

    .u4 {
      text-align: center;

      .el-form {
        position: relative;

        .el-row {
          .el-col {
            width: 220px;
            height: 20px;
            margin: 20px auto;
            position: relative;
            left: 30px;
          }
        }

        .el-button {
          padding: 0 25px;
          margin-top: 20px;
        }
      }
    }
  }
}
</style>
