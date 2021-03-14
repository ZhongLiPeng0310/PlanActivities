<template>
  <div>
    <div class="logo">
      <img src="../assets/loG.png" alt="">
    </div>

    <el-form :model="formData" ref="form" :rules="formRules" inline label-width="65px">
      <el-form-item prop="username">
        <el-input v-model="formData.username" placeholder="用户名" ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="formData.password" placeholder="密码" ></el-input>
      </el-form-item>
      <el-form-item>
      </el-form-item>
    </el-form>

    <el-button @click="login" class="login-btn" type="info">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</el-button>
    <div class="register-btn">
      <el-button class="register-font" type="text" @click="$router.push({path: '/register'})">新用户？点击这里登录</el-button>
    </div>
  </div>
</template>

<script>
import req from '@/api/global.js'
import axios from 'axios'
import qs from 'qs'

export default {
  name: 'login',
  data () {
    return {
      formData: {
        username: '',
        password: ''
      },
      formRules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'change'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    login () {
      this.$refs.form.validate(valid => {
        if (valid) {
          axios({
            method: 'post',
            url: 'http://localhost:8080/uaa/auth/form', // 大钟的花生壳
            data: qs.stringify(this.formData),
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded;'
            }
          }).then(data => {
            if (data.data.code === 0) {
              this.$message({
                type: 'success',
                message: data.data.msg
              })

              // this.getHeader(data.data.data.access_token)
              console.log('login_data: ', data)
              sessionStorage.setItem('userInfo', JSON.stringify(data.data.data))

              this.getUserInfo(data.data.data.access_token)
            } else {
              this.$message({
                type: 'error',
                message: data.data.msg
              })
            }
          })
        } else {
          return false
        }
      })
    },
    getUserInfo (token) {
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

          if (data.data.data.role === '2') { // 用户登录

            req('getUserInfo', {}).then(data => {
              console.log('data', data)
  
              sessionStorage.setItem('roleInfo', JSON.stringify(data.data))
  
              if (data.data.role === 2) { // 角色2客户可以使用模块：客户端所有功能
                this.$router.push({path: '/home'},onComplete => {},onAbort => {})
              } else if (data.data.role === 4) { // 角色3站长可以使用模块：站长端所有功能
                this.$router.push({path: '/order-list'},onComplete => {},onAbort => {})
              } else { // 角色4供应商可以使用模块：供应商端所有功能
                this.$router.push({path: '/driver-shop-info'},onComplete => {},onAbort => {})
              }
            })
          } else if (data.data.data.role === '3') {
              req('getStoreBy', {}).then(data => {
                sessionStorage.setItem('roleInfo', JSON.stringify(data.data))
                this.$router.push({path: '/order-list'},onComplete => {},onAbort => {})
              })
          } else if (data.data.data.role === '4') {
              req('getMySelfs', {}).then(data => {
                console.log('getMySelfs', data) 
                sessionStorage.setItem('roleInfo', JSON.stringify(data.data))
                this.$router.push({path: '/driver-shop-info'},onComplete => {},onAbort => {})
              })
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
.logo {
  margin-top: 100px;
  width: 30%;
  margin: 100px auto 10px auto;

  img {
    width: 100%;
    margin: 0 auto;
  }
}

.el-form {

  .el-form-item {
    width: 100%;
    text-align: center;
    margin: 0 auto;
    margin-bottom: 10px;

    /deep/ .el-form-item__error {
      padding-top: 0;
    }
  }
}

.login-btn {
  display: block;
  margin: 0 auto;
  width: 200px;
  height: 40px;
}

.register-btn {
  font-size: 14px;
  text-align: center;

  .register-font {
    color: #888888;
  }
}
</style>
