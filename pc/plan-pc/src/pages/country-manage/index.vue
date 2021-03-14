<template>
  <div>
    <!-- search 是获取组件内的点击事件 :model 是这个form 表单的值 -->
    <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset">
      <el-form-item label="乡村编码" prop="villageCode">
        <el-input v-model="formData.villageCode"></el-input>
      </el-form-item>
      <el-form-item label="乡村名称" prop="villageName">
        <el-input v-model="formData.villageName"></el-input>
      </el-form-item>
      <!-- 不加prop 的话，在生命周期里的重置（.resetFields()）进不去 -->
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
    </i-search>

    <!-- <el-button @click="test">click</el-button> -->

    <!-- 这里的:toolbar  :tableData 是props 传参，名字可以自己定的 
         大致是调用boolbar 里的值的意思 -->
    <!-- 因为在i-table 里面有了<slot></slot> 所以会在组件插入的东西，
          加入到组件内部而不影响组件的公共性 -->
    <i-table
    :toolbar="toolbar"
    :tableData="tableData"
    :pageInfo="pageInfo"
    :selectionShow="true"
    @handleSizeChange="handleSizeChange"
    @handleCurrentChange="handleCurrentChange"
    @selection-change="handleSelectionChange">
      <el-table-column
      v-for="(item, index) in columnList"
      :key="index"
      :label="item.label"
      :prop="item.prop"
      align="center">
      </el-table-column>
    </i-table>

    <!-- 新增修改 -->
    <i-dialog
    v-model="dialogVisible"
    :title="dialogTitle"
    @dialog-before-close="dialogBeforeClose"
    @dialog-cancel="dialogCancel"
    @dialog-confirm="dialogconfirm">
    
    <el-form :model="dialogFormData" ref="form" :rules="formRules" label-width="100px">
      <el-row>
        <el-col :span='12'>
          <el-form-item label="乡村名称" prop="villageName">
            <el-input :disabled="this.dialogType === 'details'" v-model="dialogFormData.villageName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="详细地址" prop="address">
            <el-input :disabled="this.dialogType === 'details'" v-model.number="dialogFormData.address"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="图片" prop="imageCode">
            <i-file v-if="this.dialogType != 'details'" v-model="dialogFormData.imageCode"></i-file>
            <el-button v-model="dialogFormData.imageCode" v-if="this.dialogType === 'details' && dialogFormData.imageCode == undefined" type="text">无图片</el-button>
            <el-button v-model="dialogFormData.imageCode" v-if="this.dialogType === 'details' && dialogFormData.imageCode != undefined" type="text" @click="watchImg(dialogFormData.imageCode)">预览</el-button>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="省级编号" prop="provinceCode">
            <el-select :disabled="this.dialogType === 'details'" @change="citId(dialogFormData.provinceCode)" v-model="dialogFormData.provinceCode" placeholder="所在省份">
              <el-option v-for="(item, index) in provinceSort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="市级编号" prop="cityCode">
            <el-select :disabled="this.dialogType === 'details'" @change="areId(dialogFormData.cityCode)" v-model="dialogFormData.cityCode" placeholder="所在市">
              <el-option v-for="(item, index) in citySort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="区级编号" prop="areaCode">
            <el-select :disabled="this.dialogType === 'details'" v-model="dialogFormData.areaCode" placeholder="所在区">
              <el-option v-for="(item, index) in areaSort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="乡村介绍" prop="villageIntroduce">
            <el-input :disabled="this.dialogType === 'details'" type="textarea" v-model="dialogFormData.villageIntroduce"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    </i-dialog>

    <!-- 详情框 -->
    <i-dialog
    v-model="dialogVisible2"
    :title="dialogTitle"
    @dialog-before-close="dialogBeforeClose"
    @dialog-cancel="dialogCancel"
    @dialog-confirm="dialogconfirm">
    
    <el-form :model="dialogFormData2" ref="form" label-width="100px">
      <el-row>
        <el-col :span='12'>
          <el-form-item label="乡村名称" prop="villageName">
            <el-input v-model="dialogFormData2.villageName" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="详细地址" prop="address">
            <el-input v-model.number="dialogFormData2.address" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="图片" prop="imageCode">
            <el-button v-model="dialogFormData2.imageCode" v-if="dialogFormData2.imageCode == undefined" type="text">无图片</el-button>
            <el-button v-model="dialogFormData2.imageCode" v-if="dialogFormData2.imageCode != undefined" type="text" @click="watchImg(dialogFormData2.imageCode)">预览</el-button>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="省级名称" prop="provinceCode">
            <el-select @change="citId(dialogFormData2.provinceCode)" v-model="dialogFormData2.provinceName" placeholder="所在省份" :disabled="true">
              <el-option v-for="(item, index) in provinceSort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="市级名称" prop="cityCode">
            <el-select @change="areId(dialogFormData2.cityCode)" v-model="dialogFormData2.cityName" placeholder="所在市" :disabled="true">
              <el-option v-for="(item, index) in citySort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="区级名称" prop="areaCode">
            <el-select v-model="dialogFormData2.areaCode" placeholder="所在区" :disabled="true">
              <el-option v-for="(item, index) in areaSort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="乡村介绍" prop="villageIntroduce">
            <el-input type="textarea" v-model="dialogFormData2.villageIntroduce" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    </i-dialog>
  </div>
</template>

<script>
// 注册↓
import ISearch from '@/components/common/i-search.vue'
import ITable from '@/components/common/i-table.vue'
import IFile from '@/components/common/i-file.vue'
import IDialog from '@/components/common/i-dialog.vue'
import req from '@/api/use-manage.js'

export default {
  name: 'driver-info-manage',
  // 使用 ↓
  components: {
    ISearch,
    ITable,
    IDialog,
    IFile
  },
  data () {
    return {
      dialogType: 'add',
      provinceSort: [], // 省级编号
      citySort: [], // 市级
      areaSort: [], // 区级
      provinceSortF: [], // 非弹框 省级编号
      citySortF: [], // 非弹框 市级
      areaSortF: [], // 非弹框 区级
      tableSelectRows: [], // 选中的行数
      formData: {},
      dialogFormData: {},
      dialogFormData2: {}, // 详情框
      dialogVisible: false,
      dialogVisible2: false,
      dialogTitle: '新增用户',
      pageInfo: { // 子组件传过来的值
        pageIndex: 1,
        pageSize: 5,
        total: 0
      },
      toolbar: [ //传给子组件的值
        {
          btnName: '详情',
          type: 'primary',
          func: () => {
            // console.log('详情', this.pageInfo)

            if (this.tableSelectRows.length === 0){
              this.$message.info('请选择需要查看的数据')
              return
            }
            if (this.tableSelectRows.length > 1) { // 当选中的数据数大于1
              this.$message.info('一次只能查看一个数据')
              return
            }

            this.dialogVisible = true
            this.dialogType = 'details'
            this.dialogTitle = '乡村详情'
            req('getVillage', {
              villageCode: this.tableSelectRows[0].villageCode
              }).then(data => {
                // console.log('详情', data)
                Promise.all([
                this.citId(data.data.provinceCode),
                this.areId(data.data.cityCode)
              ]).then(dataList => {
                this.dialogFormData = Object.assign({}, data.data, {
                  imageCode: data.data.imageCode
                })
              })
            })
          }
        },
        {
          btnName: '新增',
          type: 'primary',
          func: () => {
            // console.log('新增', this.pageInfo)
            this.dialogVisible = true
            this.dialogType = 'add'
            this.dialogTitle = '新增乡村'
          }
        },
        {
          btnName: '修改',
          type: 'primary',
          func: () => {
            // console.log('修改', this.pageInfo)

            if (this.tableSelectRows.length === 0){
              this.$message.info('请输入需要修改的数据')
              return
            }
            if (this.tableSelectRows.length > 1) { // 当选中的数据数大于1
              this.$message.info('一次只能修改一个数据')
              return
            }

            this.dialogType = 'edit'
            this.dialogVisible = true
            this.dialogTitle = '修改用户'
            req('getVillage', {
              villageCode: this.tableSelectRows[0].villageCode
              }).then(data => {
                // console.log('修改 详情', data)
                Promise.all([
                this.citId(data.data.provinceCode),
                this.areId(data.data.cityCode)
              ]).then(dataList => {
                this.dialogFormData = Object.assign({}, data.data, {
                  imageCode: data.data.imageCode
                })
              })
            })
            //   console.log('getDriver data: ', data)
            //   this.dialogFormData = Object.assign({}, data.data)
            //   this.dialogFormData.imageCode = data.data.imageCode
              
            //   this.proId()
            //   // console.log('修改： ', this.dialogFormData.cityCode, this.dialogFormData.areaCode)
            //   this.citId(this.dialogFormData.provinceCode)
            //   this.areId(this.dialogFormData.cityCode)
            // })
          }
        },
        {
          btnName: '删除',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) {
              this.$message.info('请选择要删除的数据')
              
              return
            }

            this.$confirm('此操作将永久删除该数据，是否继续').then(() => {
              let ids = this.tableSelectRows.map(item => {
                return item.villageCode
              }).toString() // 拿到需要操作的数据并存到ids 里面

              req('deleteVillage', {
                villageCode: ids,
                nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
                }).then(data => { // 调用接口删除 userID里面 名字为ids 的数据
                this.$message.success(data.msg) // 打印
                // console.log('msg: ', data.msg)
                this.fetch() // 更新
              })
            })
          }
        }
      ],
      columnList: [ //传给子组件的值
        {label: '乡村编码', prop: 'villageCode'},
        {label: '乡村名称', prop: 'villageName'},
        {label: '乡村介绍', prop: 'villageIntroduce'},
        {label: '图片编码', prop: 'imageCode'},
        {label: '省份', prop: 'provinceName'},
        {label: '市', prop: 'cityName'},
        {label: '区', prop: 'areaName'},
        {label: '详细地址', prop: 'address'},
        {label: '版本号', prop: 'version'}
      ],
      tableData: [ //传给子组件的值
      ],
      tableData2: [],
      formRules: {
        villageName: [
          {required: true, message: '请输入乡村名称', trigger: 'change'}
        ],
        address: [
          {required: true, message: '请输入详细地址', trigger: 'change'}
        ],
        imageCode: [
          {required: true, message: '请输入图片', trigger: 'change'}
        ],
        provinceCode: [
          {required: true, message: '请输入省级编号', trigger: 'change'}
        ],
        cityCode: [
          {required: true, message: '请输入市级编号', trigger: 'change'}
        ],
        areaCode: [
          {required: true, message: '请输入区级编号', trigger: 'change'}
        ],
        villageIntroduce: [
          {required: true, message: '请输入乡村介绍', trigger: 'change'}
        ],
      }
    }
  },
  mounted () { // 获取总条数
    this.pageInfo.total = this.tableData.length
    this.fetch() // 不监听的话。会在其他页找不到
    this.proId()
  },
  methods: {
    fetch () {
      this.pageInfo.pageSize = 5
      this.pageInfo.pageIndex = 1 // 不加这里行的话，在第二页搜索第一页的时候不成功
      this.search()
    },
    search () { // 查询函数
      req('listVillage', {
        // access_token: JSON.parse(sessionStorage.getItem('userInfo')).access_token, // 拿令牌的
        ...this.formData, // 如果报错，这里可能还需要传一个 role 角色编号
        role: JSON.parse(sessionStorage.getItem('roleInfo')).role,
        pageSize: this.pageInfo.pageSize,
        pageNum: this.pageInfo.pageIndex
      }).then(data => {
        // console.log(data)
        this.tableData = data.data.list // 这一行代码，如果没有数据，会连表头都不显示
        this.pageInfo.pageIndex = data.data.pageNum
        this.pageInfo.pageSize = data.data.pageSize
        this.pageInfo.total = data.data.total
      })
    },
    reset () {
      // console.log('重置按钮被点击了')
      this.fetch()
      this.citySortF = [] // 非弹出框 装市下拉框的数组
      this.areaSortF = [] // 非弹出框 装区下拉框的数组
      // this.$refs.ISearch.$refs.form.resetFields()
    },
    handleSizeChange (value) {
      this.pageInfo.pageSize = value
      this.search() // 这里不用fetch 而用search 是因为fetch 一直处于监听状态
      // this.pageInfo.pageSize = value
      // console.log('每页多少条', value)
    },
    handleCurrentChange (value) {
      this.pageInfo.pageIndex = value
      this.search() // 这里不用fetch 而用search 是因为fetch 一直处于监听状态
      // console.log('当前多少页', value)
      // this.pageInfo.pageIndex = value
    },
    handleSelectionChange (rows) {
      // console.log('index', rows)
      this.tableSelectRows = rows
    },
    dialogBeforeClose () { // 关闭清空表单
      // console.log(111)
      this.$refs.form.resetFields()
      this.citySort = [] // 弹出框 装市下拉框的数组
      this.areaSort = [] // 弹出框 装区下拉框的数组
      this.citySortF = [] // 非弹出框 装市下拉框的数组
      this.areaSortF = [] // 非弹出框 装区下拉框的数组
    },
    dialogCancel () {
      this.$refs.form.resetFields()
      this.citySort = [] // 弹出框 装市下拉框的数组
      this.areaSort = [] // 弹出框 装区下拉框的数组
      this.citySortF = [] // 非弹出框 装市下拉框的数组
      this.areaSortF = [] // 非弹出框 装区下拉框的数组
      this.dialogVisible = false
      this.dialogVisible2 = false
    },
    watchImg (data) { // 打开图片
      window.open(data) // 路径
    },
    dialogconfirm () { // 检验新增 修改用户表单
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.dialogType === 'add') { // 当表单是新增的时候
            req('addVillage', {
              ...this.dialogFormData,
              nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
              }).then(data => {
              if (data.code === 0) {
                this.$message.success(data.msg) // 提示框

                this.fetch()
                this.$refs.form.resetFields()
                this.citySort = [] // 弹出框 装市下拉框的数组
                this.areaSort = [] // 弹出框 装区下拉框的数组
                this.citySortF = [] // 非弹出框 装市下拉框的数组
                this.areaSortF = [] // 非弹出框 装区下拉框的数组
                this.dialogVisible = false
              } else {
                this.$message.error(data.msg) // 提示框
              }
            })
          } else if (this.dialogType === 'edit') {
              req('updateVillage', {
                ...this.dialogFormData,
                nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
                }).then(data => {
                // console.log('updateDriver data',data)
                if (data.code === 0) {
                  this.$message.success(data.msg)

                  this.fetch()
                  this.$refs.form.resetFields()
                  this.citySort = [] // 弹出框 装市下拉框的数组
                  this.areaSort = [] // 弹出框 装区下拉框的数组
                  this.citySortF = [] // 非弹出框 装市下拉框的数组
                  this.areaSortF = [] // 非弹出框 装区下拉框的数组
                  this.dialogVisible = false
                } else {
                  this.$message.error(data.msg)
                }
              })
            } else {
              this.dialogVisible2 = false
            }
        } else {
          return false
        }
      })
    },
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
        if (this.dialogVisible) { // 修改新增展开的弹框 如果有东西（调整弹出框）
          this.dialogFormData.cityCode = ''
          this.dialogFormData.areaCode = ''
          this.citySort = [] // 弹出框 装市下拉框的数组
          this.areaSort = [] // 弹出框 装区下拉框的数组

          req('listArea', {parentCode: value}).then(data => {
            // console.log('市级 list', data)
            this.citySort = data.data.map(item => {
              return Object.assign({}, data, {label: item.areaName, value: item.areaCode})
            })

            resolve(this.citySort)
          })
        } else { // 如果不是弹出框  （调整非弹出框）
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
        }
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
        if (this.dialogVisible) {
          this.dialogFormData.areaCode = ''
          this.areaSort = []

          req('listArea', {parentCode: value}).then(data => {
            // console.log('区级 list', data)
            this.areaSort = data.data.map(item => {
              return Object.assign({}, data, {label: item.areaName, value: item.areaCode})
            })

            resolve(this.areaSort)
          })
        } else {
          this.formData.areaCode = ''
          this.areaSortF = []

          req('listArea', {parentCode: value}).then(data => {
            // console.log('区级 list', data)
            this.areaSortF = data.data.map(item => {
              return Object.assign({}, data, {label: item.areaName, value: item.areaCode})
            })

            resolve(this.areaSortF)
          })
        }
      })
    },
    validateIdCard (rule, value, callback) {
      let reg = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/

      // console.log(reg.test(value))

      if (!reg.test(value)) {
        callback(new Error('请输入正确的身份证号'))
      } else {
        callback()
      }
    }
  }
}
</script>
