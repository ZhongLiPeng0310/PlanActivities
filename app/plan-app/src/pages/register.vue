<template>
  <div>
    <div class="header">
      <span>注册</span>
    </div>

    <div class="logo">
      <img src="../assets/loG.png" alt="">
    </div>

   <el-form ref="form" :model="formData" :rules="formRules" inline label-width="80px">
      <el-form-item prop="userAcct">
        <el-input v-model="formData.userAcct" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item prop="userPassword">
        <el-input type="password" v-model="formData.userPassword" placeholder="密码"></el-input>
      </el-form-item>
      <!-- <el-form-item label="确认密码" prop="confirmUserPassword">
        <el-input type="password" v-model="formData.confirmUserPassword"></el-input>
      </el-form-item> -->
      <el-form-item prop="phone">
        <el-input v-model.number="formData.phone" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item prop="userName">
        <el-input v-model="formData.userName" placeholder="用户名称"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex" class="radios">
        <el-radio-group v-model="formData.sex">
          <el-radio label="1">男</el-radio>
          <el-radio label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="idCard">
        <el-input v-model="formData.idCard" placeholder="身份证"></el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input v-model="formData.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item label="图片" prop="imagePath" class="ifile">
        <div>
          <i-file v-model="formData.imagePath"></i-file>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button class="register-btn" type="info" @click="submit">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</el-button>
        <div>
          <el-button class="register-font" type="text" @click="toLogin">已有账号？点击登录</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import IFile from '@/components/common/i-file.vue'

export default {
  name: 'register',
  // 使用
  components: {
    IFile
  },
  data () {
    return {
      formData: {},
      formRules: {
        userAcct: [
          {required: true, message: '请输入用户账号', trigger: 'change'}
        ],
        userPassword: [
          {required: true, message: '请输入用户密码', trigger: 'change'}
        ],
        // confirmUserPassword: [
        //   {required: true, message: '请确认密码', trigger: 'change'}
        // ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'change'},
          { type: 'number', message: '手机号必须为数字' },
          {validator:this.validateTel,trigger:'change'}
        ],
        userName: [
          {required: true, message: '请输入用户名称', trigger: 'change'}
        ],
        sex: [
          {required: true, message: '请选择性别', trigger: 'change'}
        ],
        idCard: [
          {required: true, message: '请输入身份证', trigger: 'change'},
          { validator: this.validateIdCard, trigger: 'change' }
        ],
        email: [
          {required: true, message: '请输入用户邮箱', trigger: 'change'}
        ]
      }

    }
  },
  methods: {
    toLogin () {
      this.$router.push({path: '/login'})
    },
    submit () {
      this.$refs.form.validate(valid => {
        if (valid) {
          // if (this.formData.userPassword !== this.formData.confirmUserPassword) {
          //   this.$message.info('两次输入的密码不一致')

          //   return
          // }

          axios({
            method: 'post',
            // url: 'http://29916786qq.zicp.vip/app/register/clientRegister', // 强哥的花生壳
            // url: 'http://3050g139z6.wicp.vip:13788/app/register/clientRegister', // 赵汝杰的花生壳
            url: 'http://29ng920368.wicp.vip/app/client/userRegister', // 朝涛的花生壳
            // url: 'http://2996h19g33.eicp.vip/app/client/userRegister', // 大钟的花生壳
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

              // sessionStorage.setItem('userInfo', JSON.stringify(data.data.data))

              setTimeout(() => {
                this.$router.push({path: '/login'})
              })
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
    validateTel(rule,value,callback) { // 电话号码自定义校验
      let reg = /^[1][3-9][0-9]{9}$/
      if (value === '' || value ===undefined || value == null) {
        callback ()
      }else {
        if((!reg.test(value))&& value !==''){
            callback(new Error('请输入正确的电话号码'))
        }else {
            callback ()
        }
      }
    },
    validateIdCard (rule, value, callback) {
      let reg = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/

      console.log(reg.test(value))

      if (!reg.test(value)) {
        callback(new Error('请输入正确的身份证号'))
      } else {
        callback()
      }
    }
  }

}
</script>

<style lang="scss" scoped>
.header {
  width: 100%;
  height: 50px;
  font-size: 20px;
  margin-top: 10px;
  line-height: 50px;
  text-align: center;

  span.iconzuojiantou {
    position: absolute;
    top: 50%;
    left: 20px;
    transform: translate(0, -50%);
    color: #409EFF;
  }
}

.logo {
  width: 30%;
  margin: 0 auto;

  img {
    width: 100%;
  }
}

.el-form {
  position: relative;

  .el-form-item {
    width: 100%;
    text-align: center;
    margin: 8px auto;
    box-sizing: border-box;
  }

  .radios {
    position: relative;
    width: 100%;
    margin: 8px auto;
    left: -45px;
  }

  .ifile {
    width: 100%;
    margin: 8px auto;
    
    div {
      width: 200px;
      display: flex;
      justify-content: flex-start;
    }

  }
}

.register-btn {
  width: 200px;
  height: 40px;
  margin-top: 20px;
}
.register-font{
  color: #888888;
}
</style>
