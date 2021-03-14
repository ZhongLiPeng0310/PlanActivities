<template>
  <div>
    <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset">
      <el-form-item label="供应商编码" prop="userCode">
        <el-input v-model="formData.userCode"></el-input>
      </el-form-item>
      <el-form-item label="供应商姓名" prop="userName">
        <el-input v-model="formData.userName"></el-input>
      </el-form-item>
      <el-form-item label="所在省份" prop="provinceCode">
        <el-select clearable v-model="formData.provinceCode" @change="getCityData">
          <el-option
            v-for="(item, index) in provinceOptions"
            :key="index"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所在城市" prop="cityCode">
        <el-select clearable v-model="formData.cityCode" @change="getRegionData">
          <el-option
            v-for="(item, index) in cityOptions"
            :key="index"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所在区" prop="areaCode">
        <el-select clearable v-model="formData.areaCode">
          <el-option
            v-for="(item, index) in regionOptions"
            :key="index"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
    </i-search>

    <i-table
      :toolbar="toolbar"
      :tableData="tableData"
      :pageInfo="pageInfo"
      :selectionShow="true"
      v-loading="tableLoading"
      @handleSizeChange="handleSizeChange"
      @handleCurrentChange="handleCurrentChange"
      @selection-change="handleSelectionChange">
      <el-table-column
        v-for="(item, index) in columnList"
        :key="index"
        :label="item.label"
        :prop="item.prop"
        align="center"
        :width="item.width"
        :show-overflow-tooltip="true"
        :formatter="columnFormatter">
      </el-table-column>
    </i-table>

    <i-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      @dialog-before-close="dialogBeforeClose"
      @dialog-cancel="dialogCancel"
      @dialog-confirm="dialogConfirm">
      <el-form :model="dialogFormData" ref="form" :rules="formRules" label-width="120px">
        <el-row>
          <el-col :span='12'>
            <el-form-item label="供应商姓名" prop="userName">
              <el-input v-model="dialogFormData.userName" :disabled="this.dialogType === 'detail'" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='12'>
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model.number="dialogFormData.phone" :disabled="this.dialogType === 'detail'" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='12'>
            <el-form-item label="供应商账号" prop="userAcct">
              <el-input v-model="dialogFormData.userAcct" :disabled="this.dialogType === 'detail'" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='12'>
            <el-form-item label="密码" prop="userPassword">
              <el-input type="password" v-model="dialogFormData.userPassword" :disabled="this.dialogType === 'detail'" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='12'>
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="dialogFormData.idCard" :disabled="this.dialogType === 'detail'" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='12'>
            <el-form-item label="所在省份" prop="provinceCode">
              <el-select v-model="dialogFormData.provinceCode" :disabled="this.dialogType === 'detail'" @change="getCityData">
                <el-option
                  v-for="(item, index) in dialogProvinceOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span='12'>
            <el-form-item label="所在城市" prop="cityCode">
              <el-select v-model="dialogFormData.cityCode" :disabled="this.dialogType === 'detail'" @change="getRegionData">
                <el-option
                  v-for="(item, index) in dialogCityOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在区域" prop="areaCode">
              <el-select v-model="dialogFormData.areaCode" :disabled="this.dialogType === 'detail'">
                <el-option
                  v-for="(item, index) in dialogRegionOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商头像" prop="imagePath">
              <i-file :disabled="dialogType === 'detail'" v-if="dialogType != 'detail'" v-model="dialogFormData.imagePath"></i-file>
              <el-button v-model="dialogFormData.imagePath" v-if="dialogType == 'detail'" type="text" @click="watchImg(dialogFormData.imagePath)">查看</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </i-dialog>
  </div>
</template>

<script>// 注册↓
import ISearch from '@/components/common/i-search.vue'
import ITable from '@/components/common/i-table.vue'
import IFile from '@/components/common/i-file.vue'
import IDialog from '@/components/common/i-dialog.vue'
import req from '@/api/use-manage.js'

export default {
  name: 'driver-info-manage',
  components: {
    ISearch,
    ITable,
    IDialog,
    IFile
  },
  data () {
    return {
      dialogType: 'add',
      tableLoading: false,
      formData: {
        userCode: '',
        userName: '',
        provinceCode: '',
        cityCode: '',
        areaCode: ''
      },
      // 省
      provinceOptions: [],
      // 市
      cityOptions: [],
      // 区
      regionOptions: [],
      // 弹出框-省
      dialogProvinceOptions: [],
      // 弹出框-市
      dialogCityOptions: [],
      // 弹出框-区
      dialogRegionOptions: [],
      dialogFormData: {
        userName: '',
        phone: '',
        imagePath: '',
        userAcct: '',
        userPassword: '',
        idCard: '',
        provinceCode: '',
        cityCode: '',
        areaCode: ''
      },
      dialogVisible: false,
      dialogTitle: '新增供应商',
      pageInfo: {
        pageNum: 1,
        pageSize: 5,
        total: 0
      },
      toolbar: [
        {
          btnName: '详情',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) {
              this.$message.info('请选择需要修改的数据')

              return
            }

            if (this.tableSelectRows.length > 1) {
              this.$message.info('当前不支持批量修改数据')

              return
            }

            this.dialogVisible = true
            this.dialogType = 'detail'
            this.dialogTitle = '查看供应商详情'
            req('getDriver', {userCode: this.tableSelectRows[0].userCode}).then(data => {
              Promise.all([
                this.getCityData(data.data.provinceCode),
                this.getRegionData(data.data.cityCode)
              ]).then(dataList => {
                this.dialogFormData = Object.assign({}, data.data, {
                  imagePath: data.data.imagePath
                })
                this.dialogFormData.phone = parseInt(data.data.phone)
                console.log('this.dialogFormData ', this.dialogFormData)
              })
            })
          }
        },
        {
          btnName: '新增',
          type: 'primary',
          func: () => {
            this.dialogVisible = true
            this.dialogType = 'add'
            this.dialogTitle = '新增供应商'
          }
        },
        {
          btnName: '修改',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) {
              this.$message.info('请选择需要修改的数据')

              return
            }

            if (this.tableSelectRows.length > 1) {
              this.$message.info('当前不支持批量修改数据')

              return
            }

            this.dialogVisible = true
            this.dialogType = 'edit'
            this.dialogTitle = '修改供应商'
            req('getDriver', {userCode: this.tableSelectRows[0].userCode}).then(data => {
              Promise.all([
                this.getCityData(data.data.provinceCode),
                this.getRegionData(data.data.cityCode)
              ]).then(dataList => {
                this.dialogFormData = Object.assign({}, data.data, {
                  imagePath: data.data.imagePath
                })
                
                this.dialogFormData.phone = parseInt(data.data.phone)
                console.log('this.dialogFormData: ', this.dialogFormData)
              })
            })
          }
        },
        {
          btnName: '删除',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) {
              this.$message.info('请选择需要删除的数据')

              return
            }

            this.$confirm('此操作将永久删除该文件,是否继续?').then(() => {
              let ids = this.tableSelectRows.map(item => {
                return item.userCode
              }).toString()

              req('deleteDriver', {userCode: ids, nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role}).then(data => {
                this.$message.success(data.msg)

                this.fetch()
              })
            })
          }
        }
      ],
      columnList: [
        {label: '供应商编号', prop: 'userCode'},
        {label: '姓名', prop: 'userName'},
        {label: '手机', prop: 'phone'},
        {label: '身份证', prop: 'idCard'},
        {label: '账号', prop: 'userAcct'}
      ],
      tableData: [],
      tableSelectRows: [],
      formRules: {
        userName: [
          { required: true, message: '请输入供应商名称', trigger: 'change' }
        ],
        phone: [
          { required: true, message: '请输入联系电话', trigger: 'change' },
          { type: 'number', message: '联系电话必须为数字' }
        ],
        userAcct: [
          { required: true, message: '请输入供应商账号', trigger: 'change' }
        ],
        userPassword: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
        businessCode: [
          { required: true, message: '请输入营业执照编码', trigger: 'change' }
        ],
        idCard: [
          { required: true, message: '请输入身份证号', trigger: 'change' },
          { validator: this.validateIdCard, trigger: 'change' }
        ],
        provinceCode: [
          { required: true, message: '请选择省', trigger: 'change' }
        ],
        cityCode: [
          { required: true, message: '请选择市', trigger: 'change' }
        ],
        areaCode: [
          { required: true, message: '请选择区', trigger: 'change' }
        ]
        // imagePath: [
        //   { required: true, message: '请选择供应商头像图片', trigger: 'change' }
        // ]
      }
    }
  },
  mounted () {
    this.search()
    this.getProvinceData()
  },
  methods: {
    fetch () {
      this.pageInfo.pageSize = 5
      this.pageInfo.pageNum = 1
      this.search()
    },
    search () {
      this.tableLoading = true

      req('listDrivers', {
        ...this.formData,
        role: JSON.parse(sessionStorage.getItem('roleInfo')).role,
        pageSize: this.pageInfo.pageSize,
        pageNum: this.pageInfo.pageNum
      }).then(data => {
        this.tableLoading = false
        this.tableData = data.data.list
        this.pageInfo.pageNum = data.data.pageNum
        this.pageInfo.pageSize = data.data.pageSize
        this.pageInfo.total = data.data.total
      }).catch(() => {
        this.tableLoading = false
      })
    },
    reset () {
      this.fetch()
    },
    // 获得省接口
    // provinceOptions cityOptions regionOptions
    getProvinceData (id) {
      req('listArea', {parentCode: 1}).then(data => {
        console.log('listArea_data: ', data)
        this.provinceOptions = data.data.map(item => {
          return Object.assign({}, data.data, {label: item.areaName, value: item.areaCode})
        })

        this.dialogProvinceOptions = data.data.map(item => {
          return Object.assign({}, data.data, {label: item.areaName, value: item.areaCode})
        })
      })
    },
    // 获得市接口
    getCityData (id) {
      if (!id) {
        this.formData.cityCode = ''
        this.formData.areaCode = ''
        this.cityOptions = []
        this.regionOptions = []

        return
      }

      return new Promise((resolve, reject) => {
        if (this.dialogVisible) {
          this.dialogFormData.cityCode = ''
          this.dialogFormData.areaCode = ''
          this.dialogCityOptions = []
          this.dialogRegionOptions = []

          req('listArea', {parentCode: id}).then(data => {
            this.dialogCityOptions = data.data.map(item => {
              return Object.assign({}, data.data, {label: item.areaName, value: item.areaCode})
            })

            resolve(this.dialogCityOptions)
          })
        } else {
          this.formData.cityCode = ''
          this.formData.areaCode = ''
          this.cityOptions = []
          this.regionOptions = []

          req('listArea', {parentCode: id}).then(data => {
            this.cityOptions = data.data.map(item => {
              return Object.assign({}, data.data, {label: item.areaName, value: item.areaCode})
            })

            resolve(this.cityOptions)
          })
        }
      })
    },
    // 获得区接口
    getRegionData (id) {
      if (!id) {
        this.formData.areaCode = ''
        this.regionOptions = []

        return
      }

      return new Promise((resolve, reject) => {
        if (this.dialogVisible) {
          this.dialogFormData.areaCode = ''
          this.dialogRegionOptions = []

          req('listArea', {parentCode: id}).then(data => {
            this.dialogRegionOptions = data.data.map(item => {
              return Object.assign({}, data.data, {label: item.areaName, value: item.areaCode})
            })

            resolve(this.dialogRegionOptions)
          })
        } else {
          this.formData.areaCode = ''
          this.regionOptions = []

          req('listArea', {parentCode: id}).then(data => {
            this.regionOptions = data.data.map(item => {
              return Object.assign({}, data.data, {label: item.areaName, value: item.areaCode})
            })

            resolve(this.regionOptions)
          })
        }
      })
    },
    handleSizeChange (value) {
      this.pageInfo.pageNum = 1
      this.pageInfo.pageSize = value
      this.search()
    },
    handleCurrentChange (value) {
      this.pageInfo.pageNum = value
      this.search()
    },
    handleSelectionChange (rows) {
      this.tableSelectRows = rows
    },
    dialogBeforeClose () {
      this.$refs.form.resetFields()
      this.cityOptions = []
      this.regionOptions = []
      this.dialogCityOptions = []
      this.dialogRegionOptions = []
    },
    dialogCancel () {
      this.$refs.form.resetFields()
      this.cityOptions = []
      this.regionOptions = []
      this.dialogCityOptions = []
      this.dialogRegionOptions = []
      this.dialogVisible = false
    },
    dialogConfirm () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.dialogType === 'add') {
            req('addDriver', {
              ...this.dialogFormData, 
              }).then(data => {
              if (data.code === 0) {
                this.$message.success(data.msg)

                this.fetch()
                this.$refs.form.resetFields()
                this.cityOptions = []
                this.regionOptions = []
                this.dialogCityOptions = []
                this.dialogRegionOptions = []
                this.dialogVisible = false
              } else {
                this.$message.error(data.msg)
              }
            })
          } else {
            req('updateDriver', {...this.dialogFormData, nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role}).then(data => {
              if (data.code === 0) {
                this.$message.success(data.msg)

                this.fetch()
                this.$refs.form.resetFields()
                this.cityOptions = []
                this.regionOptions = []
                this.dialogCityOptions = []
                this.dialogRegionOptions = []
                this.dialogVisible = false
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        } else {
          return false
        }
      })
    },
    watchImg (data) { // 打开图片
      window.open(data) // 路径
    },
    // 表格表头的循环list变量名一定要是columnList
    columnFormatter (row, column, cellValue, index) {
      let distName = this.columnList.filter(item => {
        return item.prop === column.property
      })[0].distName

      if (distName) {
        let currentDist = []

        currentDist = this[distName].filter(item => {
          return item.value === row[column.property]
        })

        if (currentDist[0]) {
          return currentDist[0].label
        } else {
          return row[column.property]
        }
      } else {
        return row[column.property]
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
