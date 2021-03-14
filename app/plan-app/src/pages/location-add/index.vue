<template>
  <div>
   <el-form ref="form" :model="formData" :rules="formRules" inline label-width="80px">
      <el-form-item label="省级编号" prop="provinceCode">
        <el-select clearable @change="citId(formData.provinceCode)" v-model="formData.provinceCode" placeholder="所在省份">
          <el-option v-for="(item, index) in provinceSortF"
          :key="index"
          :label="item.label"
          :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="市级编号" prop="cityCode">
        <el-select clearable @change="areId(formData.cityCode)" v-model="formData.cityCode" placeholder="所在市">
          <el-option v-for="(item, index) in citySortF"
          :key="index"
          :label="item.label"
          :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="区级编号" prop="areaCode">
        <el-select clearable v-model="formData.areaCode" placeholder="所在区">
          <el-option v-for="(item, index) in areaSortF"
          :key="index"
          :label="item.label"
          :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="详细地址" prop="address">
        <el-input v-model="formData.address"></el-input>
      </el-form-item>
      <el-form-item label="收件人" prop="getName">
        <el-input v-model="formData.getName"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model.number="formData.phone"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button class="register-btn" type="primary" @click="submit">添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import IFile from '@/components/common/i-file.vue'
import req from '@/api/location-add.js'

export default {
  name: 'location',
  // 使用
  components: {
    IFile
  },
  data () {
    return {
      formData: {},
      provinceSortF: [], // 非弹框 省级编号
      citySortF: [], // 非弹框 市级
      areaSortF: [], // 非弹框 区级
      formRules: {
        provinceCode: [
          {required: true, message: '请输入省级编号', trigger: 'change'}
        ],
        cityCode: [
          {required: true, message: '请输入市级编号', trigger: 'change'}
        ],
        areaCode: [
          {required: true, message: '请输入区级编号', trigger: 'change'}
        ],
        address: [
          {required: true, message: '请输入详细地址', trigger: 'change'}
        ],
        getName: [
          {required: true, message: '请输入收件人名称', trigger: 'change'}
        ],
        phone: [
          {required: true, message: '请输入手机号码', trigger: 'change'},
          {type:'number', message: '电话号码必须为数字'},
          {validator:this.validateTel,trigger:'change'}
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
    this.proId()
  },
  methods: {
    proId () {
      req('listArea', {parentCode: 1}).then(data => {
        this.provinceSort = data.data.map(item => {
          return Object.assign({}, data, {label: item.areaName, value: item.areaCode})
        })

        this.provinceSortF = data.data.map(item => {
          return Object.assign({}, data, {label: item.areaName, value: item.areaCode})
        })
      })
    },
    // 获得市接口
    citId (value) {
      // console.log(value)
      if (!value) { // 当省没有选中 or 重新选择了省
        this.formData.cityCode = '' // formData是第一个form
        this.formData.areaCode = ''
        this.citySortF = [] // 非弹出框 装市下拉框的数组
        this.areaSortF = [] // 非弹出框 装区下拉框的数组

        return
      }
      return new Promise((resolve, reject) => {
          this.formData.cityCode = ''
          this.formData.areaCode = ''
          this.citySortF = [] // 非弹出框 装市F下拉框的数组
          this.areaSortF = [] // 非弹出框 装区下拉框的数组

          req('listArea', {parentCode: value}).then(data => { // 查询省市区下拉框接口
            // console.log('市级 list', data)
            this.citySortF = data.data.map(item => {
              return Object.assign({}, data, {label: item.areaName, value: item.areaCode})
            })
            resolve(this.citySortF)
          })
      })
    },
    areId (value) {
      // console.log(value)
      if (!value) { // 当区没有选中 or 重新选择了区
        this.formData.areaCode = ''
        this.areaSortF = []

        return
      }

      return new Promise((resolve, reject) => {
          this.formData.areaCode = ''
          this.areaSortF = []

          req('listArea', {parentCode: value}).then(data => {
            // console.log('区级 list', data)
            this.areaSortF = data.data.map(item => {
              return Object.assign({}, data, {label: item.areaName, value: item.areaCode})
            })

            resolve(this.areaSortF)
          })
      })
    },
    submit () {
      this.$refs.form.validate(valid => {
        if (valid) {
          req('saveAddress', {...this.formData}).then(data => {
              if (data.code === 0) {0
                this.$message.success(data.msg) // 提示框

                
                this.$refs.form.resetFields()
              } else {
                this.$message.error(data.msg) // 提示框
              }
          })
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
