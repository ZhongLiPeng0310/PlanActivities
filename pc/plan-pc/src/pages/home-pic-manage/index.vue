<template>
  <div>
    <!-- search 是获取组件内的点击事件 :model 是这个form 表单的值 -->
    <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset"> 
      <!-- 不加prop 的话，在生命周期里的重置（.resetFields()）进不去 -->
      <el-form-item label="状态" prop="showState">
        <el-select v-model="formData.showState" placeholder="全部">
          <el-option v-for="(item, index) in stateOptions"
          :key="index"
          :label="item.label"
          :value="item.value"></el-option>
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
      align="center"
      :width="item.width"
      :formatter="columnFormatter">

        <!-- template 是在组件内进行插槽，这里是为了预览图片 -->
        <template slot-scope="scope">
          <!-- 预览图片 -->
          <el-button v-if="item.prop == 'preview'" type="text" @click="watchImg(scope.row)">预览</el-button>
          <!-- 显示其他数据 -->
          <span v-else>{{ filterDist(scope.row[item.prop], item) }}</span>
        </template>
      </el-table-column>
    </i-table>

    <!-- 弹框 -->
    <i-dialog
    v-model="dialogVisible"
    :title="dialogTitle"
    @dialog-before-close="dialogBeforeClose"
    @dialog-cancel="dialogCancel"
    @dialog-confirm="dialogconfirm">
    
    <el-form :model="dialogFormData" ref="form" :rules="formRules" label-width="100px">
      <el-row>
        <el-col :span='12'>
          <el-form-item label="轮播图片" prop="imagePath">
            <i-file v-model="dialogFormData.imagePath"></i-file>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="排序" prop="sortNo">
            <el-input v-model.number="dialogFormData.sortNo"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="有效期始" prop="beginTime">
            <el-date-picker
              v-model="dialogFormData.beginTime"
              placeholder="选择日期"
              @change="sTime"
              :picker-options="startTimePickerOptions">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="有效期止" prop="endTime">
            <el-date-picker
              v-model="dialogFormData.endTime"
              placeholder="选择日期"
              @change="eTime"
              :picker-options="endTimePickerOptions">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="商品编号" prop="goodsCode">
            <comm-select v-model="dialogFormData.goodsCode"></comm-select>
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
import IFile from '@/components/common/i-file.vue'
import axios from 'axios'
import req from '@/api/use-manage.js'
import CommSelect from './comm-select.vue'

export default {
  name: 'user-manage',
  // 使用 ↓
  components: {
    ISearch,
    ITable,
    IDialog,
    IFile,
    CommSelect
  },
  data () {
    return {
      startTimedata: '', // 轮播开始时间
      endTimedata: '', // 轮播结束时间
      dialogType: 'add', // 新增
      formData: {}, // 搜索
      dialogFormData: {}, // 弹出框
      dialogVisible: false,
      dialogTitle: '新增用户',
      pageInfo: { // 子组件传过来的值
        pageIndex: 1,
        pageSize: 5,
        total: 0
      },
      startTimePickerOptions: { // 用来限制时间的，value 是该方法绑定的那个点的时间
        disabledDate: (value) => {
          // console.log(value)
          return this.dialogFormData.endTime < value
        }
      },
      endTimePickerOptions: {
        disabledDate: (value) => {
          // console.log('123456789', this.$moment(this.dialogFormData.beginTime).format("YYYY-MM-DD"))
          // console.log('this.formData.payTimeStart  and  value', this.formData.payTimeStart, value)
          return this.dialogFormData.beginTime > value
        }
      },
      toolbar: [ //传给子组件的值
        {
          btnName: '新增',
          type: 'primary',
          func: () => {
            // console.log('新增', this.pageInfo)
            this.dialogVisible = true
            this.dialogType = 'add'
            this.dialogTitle = '新增轮播图'
            
            if (this.beginTime < this.endTime) {
              this.$message.info('请正确输入时间')
              return
            }
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

            this.$confirm('此操作将会删除，是否继续').then(() => {
              let ids = this.tableSelectRows.map(item => {
                return item.showCode
              }).toString() // 拿到需要操作的数据并存到ids 里面

              req('deleteImg', {showCode: ids}).then(data => { // 调用接口删除 userID里面 名字为ids 的数据
                this.$message.success(data.msg) // 打印
                // console.log('msg: ', data.msg)
                this.fetch() // 更新
              })
            })
          }
        },
        {
          btnName: '启用',
          type: 'primary',
          func: () => {
            // console.log('启用updateImg', this.pageInfo)
            if (this.tableSelectRows.length === 0) {
              this.$message.info('请选择要启用的数据')
              
              return
            }

            let showIds = this.tableSelectRows.map(item => {
              return item.showCode
            }).toString() // 拿到需要操作的数据并存到ids 里面

            let sion = this.tableSelectRows.map(item => {
              return item.version
            }).toString()

            req('updateImg', {
              showCode: showIds,
              showState: 1,
              version: sion,
              }).then(data => { // 调用接口删除 userID里面 名字为ids 的数据
              this.$message.success(data.msg) // 打印
              this.fetch() // 更新
            })
          }
        },
        {
          btnName: '禁用',
          type: 'primary',
          func: () => {
            // console.log('禁用', this.pageInfo)
          if (this.tableSelectRows.length === 0) {
              this.$message.info('请选择要禁用的数据')
              
              return
            }

            let showIds = this.tableSelectRows.map(item => {
              return item.showCode
            }).toString() // 拿到需要操作的数据并存到ids 里面

            let sion = this.tableSelectRows.map(item => {
              return item.version
            }).toString()

            req('updateImg', {
              showCode: showIds,
              showState: 2,
              version: sion,
              }).then(data => { // 调用接口删除 updateImg 名字为ids 的数据
              this.$message.success(data.msg) // 打印
              this.fetch() // 更新
            })
          }
        }
      ],
      stateOptions: [
        {label: '禁用', value: '2'},
        {label: '启用', value: '1'}
      ],
      columnList: [ //传给子组件的值
        {label: '排序', prop: 'sortNo'},
        {label: '图片路径', prop: 'imagePath'},
        {label: '轮播图状态', prop: 'showState', distName: 'stateOptions'},
        {label: '轮播图编码', prop: 'showCode'},
        {label: '预览', prop: 'preview'},
        {label: '有效期起', prop: 'beginTime'},
        {label: '有效期止', prop: 'endTime'},
        {label: '版本号', prop: 'version'}
      ],
      tableData: [ //传给子组件的值
      ],
      tableSelectRows: [],
      formRules: {
        imagePath: [
          {required: true, message: '请输入', trigger: 'change'}
        ],
        sortNo: [
          {required: true, message: '请输入', trigger: 'change'},
          {type:'number', message: '电话号码必须为数字'}
        ],
        beginTime: [
          {required: true, message: '请选择日期', trigger: 'change' }
        ],
        endTime: [
          {required: true, message: '请选择日期', trigger: 'change' }
        ],
        goodsCode: [
          {required: true, message: '请输入', trigger: 'blur'}
        ]
      }
    }
  },
  mounted () { // 获取总条数
    this.pageInfo.total = this.tableData.length
    this.fetch() // 不监听的话。会在其他页找不到
  },
  methods: {
    fetch () {
      this.pageInfo.pageSize = 10
      this.pageInfo.pageIndex = 1 // 不加这里行的话，在第二页搜索第一页的时候不成功
      this.search()
    },
    search () { // 查询函数
      req('imgGetTableData', {
        // access_token: JSON.parse(sessionStorage.getItem('userInfo')).access_token, // 拿令牌的
        ...this.formData,
        pageSize: this.pageInfo.pageSize,
        pageNum: this.pageInfo.pageIndex
      }).then(data => {
        console.log(data)
        this.tableData = data.data.list // 这一行代码，如果没有数据，会连表头都不显示
        this.pageInfo.pageIndex = data.data.pageNum
        this.pageInfo.pageSize = data.data.pageSize
        this.pageInfo.total = data.data.total
        console.log('this.tableData_data: ', this.tableData)
      })
    },
    reset () { //重置
      console.log('重置按钮被点击了')
      this.fetch()
      // this.$refs.ISearch.$refs.form.resetFields()
    },
    handleSizeChange (value) {
      this.pageInfo.pageSize = value
      this.search()
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
    dialogconfirm () { // 检验新增 修改用户表单
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.dialogType === 'add') { // 当表单是新增的时候
            req('addImg', {
              beginTime: this.$moment(this.dialogFormData.beginTime).format("YYYY-MM-DD"),
              endTime: this.$moment(this.dialogFormData.endTime).format("YYYY-MM-DD"),
              sortNo: this.dialogFormData.sortNo,
              imagePath: this.dialogFormData.imagePath,
              goodsCode: this.dialogFormData.goodsCode,
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
          }
        } else {
          return false
        }
      })
    },
    sTime (time) {
      // console.log(time)
      this.startTimedata = time
    },
    eTime (time) {
      // console.log(time)
      this.endTimedata = time
      this.conTime()
    },
    conTime () {
      if (this.startTimedata < this.endTimedata) {
        // console.log('这里时间对比通过了')
      } else {
        this.$message.error('请正确输入时间段')
        this.$refs.form.resetFields()
        this.dialogVisible = false
      }
    },
    watchImg (data) { // 打开图片
      window.open(data.imagePath) // 路径
    },
    columnFormatter (row, column, cellValue, index) { // 将value 转换成label
      // console.log('row, column, cellValue, index: ',row, column, cellValue, index)
      let distName = this.columnList.filter(item => {
        return item.prop === column.property
      })[0].distName

      if (distName) {
        return this[distName].filter(item => {
          return item.value === row[column.property]
        })[0].label
      } else {
        return row[column.property]
      }
    },
    filterDist (value, item) { // 转换字典
      // console.log('value: ', value)
      // console.log('item: ', item)
      if (item.distName) {
        // console.log('if_item.distName: ', item.distName)
        return this[item.distName].filter(distItem => { // 将状态的value 转换成label
          return distItem.value === value
        })[0].label // .label 是转string 的，如果value 值是int 的话
      } else {
        return value
      }
    }
  }
}
</script>
