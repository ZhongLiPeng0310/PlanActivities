<template>
  <div>
   <el-form ref="form" :model="formData" :rules="formRules" inline label-width="80px">
      <el-form-item label="产品名称" prop="goodsName">
        <el-input v-model="formData.goodsName"></el-input>
      </el-form-item>
      <el-form-item label="一级分类" prop="classFirstCode">
        <el-select v-model="formData.classFirstCode" @change="oneChange">
          <el-option v-for="(item, index) in oneSort"
          :key="index"
          :label="item.label"
          :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="二级分类" prop="classSecondCode">
        <el-select v-model="formData.classSecondCode" placeholder="请输入">
          <el-option v-for="(item, index) in twoSort"
          :key="index"
          :label="item.label"
          :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model.number="formData.phone"></el-input>
      </el-form-item>
      <el-form-item label="广告词" prop="advertisement">
        <el-input v-model="formData.advertisement"></el-input>
      </el-form-item>
      <el-form-item label="产品介绍" prop="goodsIntroduce">
        <el-input v-model="formData.goodsIntroduce"></el-input>
      </el-form-item>
      <el-form-item label="供应编码" prop="userCode" v-show="userType === 3">
        <el-input v-model="formData.userCode"></el-input>
      </el-form-item>
      <el-form-item label="库存" prop="stock">
        <el-input v-model="formData.stock"></el-input>
      </el-form-item>
      <el-form-item label="成本价" prop="costPrice">
        <el-input v-model="formData.costPrice"></el-input>
      </el-form-item>
      <el-form-item label="售价" prop="salePrice">
        <el-input v-model="formData.salePrice"></el-input>
      </el-form-item>
      <el-form-item label="上传图片" prop="imagePath" class="ifile">
        <div>
          <i-file v-model="formData.imagePath"></i-file>
        </div>
      </el-form-item>
      <el-form-item label="保质期" prop="qualityDate">
        <el-input v-model="formData.qualityDate"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button class="register-btn" type="info" @click="submit">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import IFile from '@/components/common/i-file.vue'
import req from '@/api/add-comm.js'

export default {
  name: 'register',
  // 使用
  components: {
    IFile
  },
  data () {
    return {
      adc:  '',
      station: [],
      oneSort: [],
      twoSort:[],
      formData: {},
      formRules: {
        goodsName: [
          {required: true, message: '请输入用户账号', trigger: 'change'}
        ],
        userPassword: [
          {required: true, message: '请输入用户密码', trigger: 'change'}
        ],
        classFirstCode: [
          {required: true, message: '请输入一级分类', trigger: 'change'}
        ],
        classSecondCode: [
           {required: true, message: '请输入二级分类', trigger: 'change'}
        ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'change'},
          {type:'number', message: '电话号码必须为数字'},
          {validator:this.validateTel,trigger:'change'}
        ],
        advertisement: [
          {required: true, message: '请输入用户名称', trigger: 'change'}
        ],
        sex: [
          {required: true, message: '请选择性别', trigger: 'change'}
        ],
        goodsIntroduce: [
          {required: true, message: '请输入身份证', trigger: 'change'}
        ],
        userCode: [
          {required: true, message: '请输入用户邮箱', trigger: 'change'}
        ],
        stock: [
          {required: true, message: '请输入库存', trigger: 'change'}
        ],
        costPrice: [
          {required: true, message: '请输入成本价', trigger: 'change'}
        ],
        salePrice: [
          {required: true, message: '请输入售价', trigger: 'change'}
        ],
        imagePath: [
          {}
        ]
      }

    }
  },
  computed: {
    userType () {
      return JSON.parse(sessionStorage.getItem('roleInfo')).role
    }
  },
  mounted () {
    this.getAllStore()
  },
  methods: {
    toLogin () {
      this.$router.push({path: '/login'})
    },
    getAllStore () {
      req('getFirst', {}).then(data => { //一级下拉框 
        // console.log('一级', data)
        this.oneSort.splice(0)
        for(let i = 0; i < data.data.length; i++) {
          this.oneSort.push({label: data.data[i].classFirstName, value: data.data[i].classFirstCode})
        }
        
      })
    },
    oneChange (value) { // 一级下拉框
      console.log('oneValue', value)
      this.firstId = value
      
      if (this.formData.classSecondCode) { // 复选清空
        this.formData.classSecondCode = ''
      }
      
      this.twochange()
    },
    twochange () { // 单api 两级下拉框的操作2
      req('getSecond', {lastClassCode: this.firstId}).then(data => {
        console.log(data)
        this.twoSort.splice(0)
        for(let i = 0; i < data.data.length; i++) { // 
          this.twoSort.push({label: data.data[i].classSecondName, value: data.data[i].classSecondCode})
        }
      })
    },
    submit () {
      // 
      this.$refs.form.validate(valid => {
        if (valid) {
          if (JSON.parse(sessionStorage.getItem('roleInfo')).role === 3) {

            let storeCodes = JSON.parse(sessionStorage.getItem('roleInfo')).storeCode
            req('submit', { // 站长添加
              ...this.formData,
              storeCode: storeCodes
            }).then(data => {
              if (data.code === 0) {0
                this.$message.success(data.msg) // 提示框

                
                this.$refs.form.resetFields()
                this.dialogVisible = false
              } else {
                this.$message.error(data.msg) // 提示框
              }
            })
          } else {
            console.log(222222) // 供应商添加
            req('getDriverInfo', {}).then( data => {
              this.adc = data.data[0].storeCode
              console.log(this.adc)

              req('submit', { // 添加
                ...this.formData,
                storeCode: this.adc,
                userCode: JSON.parse(sessionStorage.getItem('roleInfo')).userCode
              }).then(data => {
                if (data.code === 0) {0
                  this.$message.success(data.msg) // 提示框

                  
                  this.$refs.form.resetFields()
                  this.dialogVisible = false
                } else {
                  this.$message.error(data.msg) // 提示框
                }
              })
            })
          }
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
    }
  }

}
</script>

<style lang="scss" scoped>
.header {
  position: relative;
  width: 100%;
  height: 50px;
  background: #ddd;
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
  width: 80%;
  margin: 0 auto;

  img {
    width: 100%;
  }
}

.el-form {
  padding-bottom: 70px;
  margin-top: 20px;
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
  width: 150px;
  height: 40px;
  margin-top: 20px;
}
</style>
