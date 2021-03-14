<template>
  <div>
    <div class="pwd-con">
        <div class="pwd-item">
            <div class="item-left">原密码</div>
            <input type="password" v-model="userPassword" placeholder="请输入原密码">
        </div>
        <div class="pwd-item">
            <div class="item-left">新密码</div>
            <input type="password" v-model="userNewPassword" placeholder="请输入新密码">
        </div>
        <div class="pwd-item">
            <div class="item-left">确认新密码</div>
            <input type="password" v-model="userNewConfirmPassword" placeholder="请再次输入新密码">
        </div>
    </div>
    <div class="btn">
        <el-button class="btn-button" type="info" @click="submit">确定</el-button>
    </div>
  </div>
</template>

<script>
import req from '@/api/change-password.js'

export default {
  name: 'change-password',
  data () {
    return {
      userPassword: '',
      userNewPassword: '',
      userNewConfirmPassword: ''
    }
  },
  
  methods: {
    submit () {
      if (!this.userPassword) {
        this.$message.info('请输入原密码')

        return
      }
      if (!this.userNewPassword) {
        this.$message.info('请输入新密码')

        return
      }
      if (!this.userNewConfirmPassword) {
        this.$message.info('请确认新密码')

        return
      }
      if (this.userNewPassword !== this.userNewConfirmPassword) {
        this.$message.info('两次输入的密码不一致')

        return
      }
      req('changePassword', {
        userPassword: this.userPassword,
        userNewPassword: this.userNewPassword
      }).then(data => {
        if (data.code === 0) {
          this.$message.success(data.msg)

          sessionStorage.clear('userInfo')
          sessionStorage.clear('roleInfo')
          sessionStorage.clear('currentComm')

          this.$router.push('/login') // 修改密码之后需要重新登陆
        } else {
          this.$message.error(data.msg)
        }
        // console.log(data)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .pwd-con {
      width: 95%;
      height: 160px;
      margin: 12px auto;
      background-color: white;
      padding: 12px 24px;
      display: flex;
      flex-direction: column;
      box-sizing: border-box;
      border-radius: 8px;
      .pwd-item {
          flex: 1;
          width: 100%;
          display: flex;
          line-height: 45.34px;
          .item-left {
              flex: 1;
              font-size: 17px;
          }
          input {
              flex: 2;
              border: none;
              outline: none;
              font-size: 17px;
              color: rgb(168,168,168);
              &::placeholder {
                  color: rgb(168,168,168);
              }
          }
      }
  }
  .btn {
      width: 300px;
      height: 65px;
      position: absolute;
      left: 50%;
      margin-left: -150px;
      bottom: 80px;

      .btn-button {
        width: 100%;
        height: 100%;
        font-size: 22px;
        color: white;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      }
  }
</style>
