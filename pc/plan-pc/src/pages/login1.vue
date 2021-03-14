<template>
  <div>
    <el-form :model="dialogFormData2" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input type="username" placeholder="请输入账号" v-model="dialogFormData2.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="dialogFormData2.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="login">登录</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
    import axios from 'axios'
    import qs from 'qs'
    import req from '@/api/use-manage.js'
    export default {
        name: "Login",
        data() {
            return {
                dialogFormData2: {
                    username: '',
                    password: ''
                },

                // 表单验证，需要在 el-form-item 元素中增加 prop 属性
                rules: {
                    username: [
                        {required: true, message: '账号不可为空', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '密码不可为空', trigger: 'blur'}
                    ]
                },

                // 对话框显示和隐藏
                dialogVisible: false
            }
        },
        methods: {
            handleClose: function () { console.log("Handle Close，空函数"); },
            onSubmit(formName) {
                // 为表单绑定验证功能
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // 使用 vue-router 路由到指定页面，该方式称之为编程式导航
                        this.$router.push("/main");
                    } else {
                        this.dialogVisible = true;
                        return false;
                    }
                });
            },
            login() {
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
                            message: data.data.msg,
                        });
                            this.dialogVisible = true
                    }
                })
            },
            getHeader(token) {
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
                            this.$router.push({path: '/client-manage'})
                        } else {
                            this.$router.push({path: '/home'})
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
  .login-box {
    border: 1px solid #DCDFE6;
    width: 350px;
    margin: 180px auto;
    padding: 35px 35px 15px 35px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
  }

  .login-title {
    text-align: center;
    margin: 0 auto 40px auto;
    color: #303133;
  }
</style>

