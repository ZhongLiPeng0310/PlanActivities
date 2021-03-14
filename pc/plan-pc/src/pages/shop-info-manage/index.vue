<template>
  <div>
    <!-- search 是获取组件内的点击事件 :model 是这个form 表单的值 -->
    <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset">
      <el-form-item label="站点编码" prop="storeCode">
        <el-input v-model="formData.storeCode"></el-input>
      </el-form-item>
      <el-form-item label="站点名称" prop="storeName">
        <el-input v-model="formData.storeName"></el-input>
      </el-form-item>
      <el-form-item label="站长名称" prop="userName">
        <el-input v-model="formData.userName"></el-input>
      </el-form-item>
      <el-form-item label="乡村编码" prop="villageCode">
        <el-input v-model="formData.villageCode"></el-input>
      </el-form-item>
      <!-- 不加prop 的话，在生命周期里的重置（.resetFields()）进不去 -->
      <el-form-item label="省级编号" prop="provinceCode">
        <el-select clearable @change="citId" v-model="formData.provinceCode" >
          <el-option v-for="(item, index) in provinceSortF"
          :key="index"
          :label="item.label"
          :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="市级编号" prop="cityCode">
        <el-select clearable @change="areId" v-model="formData.cityCode">
          <el-option v-for="(item, index) in citySortF"
          :key="index"
          :label="item.label"
          :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="区级编号" prop="areaCode">
        <el-select clearable v-model="formData.areaCode">
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

    <i-dialog
    v-model="dialogVisible"
    :title="dialogTitle"
    @dialog-before-close="dialogBeforeClose"
    @dialog-cancel="dialogCancel"
    @dialog-confirm="dialogconfirm">
    
    <el-form :model="dialogFormData" ref="form" :rules="formRules" label-width="100px">
      <el-row>
        <el-col :span='12'>
          <el-form-item label="站点名称" prop="storeName">
            <el-input v-model="dialogFormData.storeName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model.number="dialogFormData.phone"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="站长编码" prop="userCode">
            <el-select v-model="dialogFormData.userCode" @focus="userCodeSortChange" :placeholder="userCodeOld">
              <el-option v-for="(item, index) in userCodeSort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="乡村编码" prop="villageCode">
            <el-select v-model="dialogFormData.villageCode" @focus="villageCodeChange" placeholder="乡村编码">
              <el-option v-for="(item, index) in villageCodeSort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="营业执照" prop="licenseCode">
            <el-input v-model="dialogFormData.licenseCode"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="省级编号" prop="provinceCode">
            <el-select @change="citId(dialogFormData.provinceCode)" v-model="dialogFormData.provinceCode" placeholder="所在省份">
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
            <el-select @change="areId(dialogFormData.cityCode)" v-model="dialogFormData.cityCode" placeholder="所在市">
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
            <el-select v-model="dialogFormData.areaCode" placeholder="所在区">
              <el-option v-for="(item, index) in areaSort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="详细地址" prop="address">
            <el-input type="textarea" v-model="dialogFormData.address"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    </i-dialog>

    <!-- 详情框 -->
    <i-dialog
    v-model="dialogVisible2"
    :title="dialogTitle"
    @dialog-cancel="dialogCancel2"
    @dialog-confirm="dialogconfirm2">
    
    <el-form :model="dialogFormData2" :rules="formRules" label-width="100px">
      <el-row>
        <el-col :span='12'>
          <el-form-item label="站点名称" prop="storeName">
            <el-input v-model="dialogFormData2.storeName" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="站点编码" prop="storeCode">
            <el-input v-model="dialogFormData2.storeCode" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model.number="dialogFormData2.phone" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="站长编码" prop="userCode">
            <el-input v-model="dialogFormData2.userCode" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="营业执照" prop="licenseCode">
            <el-input v-model="dialogFormData2.licenseCode" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="详细地址" prop="address">
            <el-input v-model="dialogFormData2.address" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="省级编号" prop="provinceCode">
            <el-select @change="citId(dialogFormData2.provinceCode)" v-model="dialogFormData2.provinceCode" placeholder="所在省份" :disabled="true">
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
            <el-select @change="areId(dialogFormData2.cityCode)" v-model="dialogFormData2.cityCode" placeholder="所在市" :disabled="true">
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
          <el-form-item label="省级名称" prop="provinceName">
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
          <el-form-item label="市级名称" prop="cityName">
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
          <el-form-item label="区级名称" prop="areaName">
            <el-select v-model="dialogFormData2.areaName" placeholder="所在区" :disabled="true">
              <el-option v-for="(item, index) in areaSort"
              :key="index"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="版本号" prop="version">
            <el-input v-model="dialogFormData2.version" :disabled="true"></el-input>
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
import IDialog from '@/components/common/i-dialog.vue'
import req from '@/api/use-manage.js'

export default {
  name: 'shop-info-manage',
  // 使用 ↓
  components: {
    ISearch,
    ITable,
    IDialog
  },
  data () {
    return {
      villageCodeSort: [], // 乡村编码下拉框
      userCodeSort: [], // 站长编号下拉框
      provinceSort: [], // 省级编号
      citySort: [], // 市级
      areaSort: [], // 区级
      provinceSortF: [], // 省级编号
      citySortF: [], // 市级
      areaSortF: [], // 区级
      userCodeOld: '站长编码', // placeholder 的值
      tableSelectRows: [], // 选中的行数
      formData: {},
      dialogFormData: {
      },
      dialogFormData2: {},
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
          func: () => { // 如果是多条数据详情，那就用table 表格，如果是单条数据就用form 表单
            if (this.tableSelectRows.length === 0){
              this.$message.info('请选择需要查看的数据')
              return
            }
            if (this.tableSelectRows.length > 1) { // 当选中的数据数大于1
              this.$message.info('一次只能查看一个数据')
              return
            }

            this.dialogType = 'details'
            this.dialogVisible2 = true
            this.dialogTitle = '站点详情'
            req('getStore', {
              storeCode: this.tableSelectRows[0].storeCode
              }).then(data => {
                // console.log('修改 详情', data)
                Promise.all([
                  this.citId(data.data.provinceCode),
                  this.areId(data.data.cityCode)
                ]).then(dataList => {
                  this.userCodeOld = data.data.storeName
                  this.dialogFormData2 = Object.assign({}, data.data, {
                    phone: parseInt(data.data.phone)
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
            this.dialogTitle = '新增站点'
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
            this.dialogTitle = '修改站点信息'
            
            req('getStore', {
              storeCode: this.tableSelectRows[0].storeCode
              }).then(data => {
                // console.log('修改 详情', data)
                Promise.all([
                  this.userCodeSortChange(), // 站长
                  this.villageCodeChange(),  // 乡村
                  this.citId(data.data.provinceCode),
                  this.areId(data.data.cityCode)
                ]).then(dataList => {
                  this.userCodeOld = data.data.storeName
                  this.dialogFormData = Object.assign({}, data.data, {
                    phone: parseInt(data.data.phone)
                  })
                })
              })
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
                return item.storeCode
              }).toString() // 拿到需要操作的数据并存到ids 里面

              req('deleteStore', {
                storeCode: ids,
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
        {label: '站点编号', prop: 'storeCode'},
        {label: '站点名称', prop: 'storeName'},
        {label: '电话', prop: 'phone'},
        {label: '详细地址', prop: 'address'},
        {label: '店长名称', prop: 'userName'},
        {label: '站点账号', prop: 'userAcct'},
        {label: '版本号', prop: 'version'}
      ],
      tableData: [ //传给子组件的值
      ],
      tableData2: [], // 详情框
      formRules: {
        storeName: [
          {required: true, message: '请输入站点名称', trigger: 'change'}
        ],
        phone: [
          {required: true, message: '请输入联系电话', trigger: 'blur'},
          {type:'number', message: '电话号码必须为数字'},
          {validator:this.validateTel,trigger:'change'}
        ],
        userCode: [
          {required: true, message: '请输入店长编号', trigger: 'change'}
        ],
        licenseCode: [
          {required: true, message: '请输入营业执照', trigger: 'change'}
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
        address: [
          {required: true, message: '请输入详细地址', trigger: 'change'}
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
      req('listStores', {
        // access_token: JSON.parse(sessionStorage.getItem('userInfo')).access_token, // 拿令牌的
        ...this.formData,
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
      this.search()
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
    },
    dialogCancel () {
      this.$refs.form.resetFields()
      this.dialogVisible = false
    },
    dialogCancel2 () {
      this.dialogVisible2 = false
    },
    dialogconfirm () { // 检验新增 修改用户表单
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.dialogType === 'add') { // 当表单是新增的时候
            req('addStore', {
              ...this.dialogFormData,
              // nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
              }).then(data => {
              if (data.code === 0) {
                this.$message.success(data.msg) // 提示框

                this.fetch()
                this.$refs.form.resetFields()
                this.dialogVisible = false
              } else {
                this.$message.error(data.msg) // 提示框
              }
            })
          } else if (this.dialogType === 'edit') {
              req('updateStore', {
                ...this.dialogFormData,
                nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
                }).then(data => {
                // console.log('updateStore data',data)
                if (data.code === 0) {
                  this.$message.success(data.msg)

                  this.fetch()
                  this.$refs.form.resetFields()
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
    dialogconfirm2 () { // 详情框的确定 （关闭）
      this.dialogVisible2 = false
    },
    userCodeSortChange () { // 站长编码下拉框
      console.log('选择站长编码')
      req('listStoreBoss', {}).then(data => {
        console.log('选择站长编码的data', data)
        this.userCodeSort.splice(0)
        for(let i=0; i<data.data.length; i++){
          this.userCodeSort.push({
            label: data.data[i].userName,
            value: data.data[i].userCode
          })
        }
      })
    },
    villageCodeChange () { // 乡村编码下拉框
      console.log('选择乡村编码')
      req('listVillage', {}).then(data => {
        console.log('选择乡村编码的data', data)
        this.villageCodeSort.splice(0)
        for(let i=0; i<data.data.list.length; i++){
          this.villageCodeSort.push({
            label: data.data.list[i].villageName,
            value: data.data.list[i].villageCode
          })
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
