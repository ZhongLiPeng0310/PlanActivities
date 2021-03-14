<template>
  <div>
    <!-- search 是获取组件内的点击事件 :model 是这个form 表单的值 -->
    <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset"> 
      <el-form-item label="乡村名称" prop="villageName">
        <el-input v-model="formData.villageName"></el-input>
      </el-form-item>
      <el-form-item label="乡村编码" prop="villageCode">
        <el-input v-model="formData.villageCode"></el-input>
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
        <!-- <el-col :span='12'>
          <el-form-item label="热门乡村编号" prop="hotCode" v-show="dialogType === 'edit'">
            <el-input v-model="dialogFormData.hotCode"></el-input>
          </el-form-item>
        </el-col> -->
        <el-col :span='12'>
          <el-form-item label="热门位排序" prop="hotId" v-show="dialogType !== 'update'">
            <el-input v-model="dialogFormData.hotId"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="乡村编号" prop="villageCode" v-show="dialogType !== 'update'">
            <comm-select v-model="dialogFormData.villageCode"></comm-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    </i-dialog>

    <i-dialog
    v-model="dialogVisible2"
    title="修改热门乡村展示数量"
    @dialog-before-close="dialogBeforeClose"
    @dialog-cancel="dialogCancel"
    @dialog-confirm="dialogconfirm">

      <el-form :model="dialogFormData2" ref="form" :rules="formRules2" label-width="100px">
        <el-col :span='12'>
          <el-form-item label="展示热门数" prop="showNo">
            <el-input v-model="dialogFormData2.showNo"></el-input>
          </el-form-item>
        </el-col>
      </el-form>

    </i-dialog>
  </div>
</template>

<script>
// 注册↓
import ISearch from '@/components/common/i-search.vue'
import ITable from '@/components/common/i-table.vue'
import IDialog from '@/components/common/i-dialog.vue'
import CommSelect from './comm-select.vue'
import req from '@/api/use-manage.js'

export default {
  name: 'hot-comm-manage',
  // 使用 ↓
  components: {
    ISearch,
    ITable,
    IDialog,
    CommSelect
  },
  data () {
    return {
      tableSelectRows: [], // 所选的行数
      dialogType: '', // 弹框的状态（新增或修改）
      formData: {}, // 查询
      dialogFormData: {}, // 新增 修改
      dialogFormData2: {}, // 展示
      dialogTableVisible: false, // 嵌套里面的嵌套 选择
      dialogVisible: false,
      dialogVisible2: false,
      dialogTitle: '热门位乡村选择',
      pageInfo: { // 子组件传过来的值
        pageIndex: 1,
        pageSize: 5,
        total: 0
      },
      toolbar: [ //传给子组件的值
        {
          btnName: '新增',
          type: 'primary',
          func: () => {
            // console.log('新增', this.pageInfo),
            this.dialogVisible = true
            this.dialogType = 'add'
            this.dialogTitle = '新增热门乡村'
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
            this.dialogTitle = '修改热门乡村排序'
            req('getHotGoods', {hotCode: this.tableSelectRows[0].hotCode}).then(data => {
              // console.log('getHotGoods data: ', data)
              this.dialogFormData = Object.assign({}, data.data)
            })
          }
        },
        {
          btnName: '删除',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) {
              this.$message.info('请选择需要移除的乡村')
              
              return
            }

            this.$confirm('此操作将此数据移除热门位，是否继续').then(() => {
              let ids = this.tableSelectRows.map(item => {
                return item.hotCode
              }).toString() // 拿到需要操作的数据并存到ids 里面

              req('deleteHotGoods', {hotCode: ids}).then(data => { // 调用接口删除 hotCode 名字为ids 的数据
                this.$message.success(data.msg) // 打印
                this.fetch() // 更新
              })
            })
          }
        },
        {
          btnName: '展示数量设置',
          type: 'primary',
          func: () => {
            
            req('getHotGoodsShowNum', {...this.dialogFormData2}).then(data => {
              // console.log('展示数量设置', data)
              this.dialogFormData2 = data.data
            })

            this.dialogVisible2 = true
            this.dialogType = 'update'
            this.dialogTitle = '修改热门乡村展示数量'
          }
        }
      ],
      columnList: [ //传给子组件的值
        {label: '热门排序', prop: 'hotId'},
        {label: '乡村编号', prop: 'villageCode'},
        {label: '乡村名称', prop: 'villageName'},
        {label: '乡村介绍', prop: 'villageIntroduce'},
        {label: '热门乡村位编码', prop: 'hotCode'},
        {label: '版本号', prop: 'version'}
      ],
      tableData: [ //传给子组件的值
      ],
      formRules: {
        hotId: [
          {required: true, message: '请输入', trigger: 'change'}
        ],
        villageCode: [
          {required: true, message: '请输入', trigger: 'blur'}
        ],
      },
      formRules2: {
        showNo: [
          {required: true, message: '请输入', trigger: 'change'}
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
      req('listHotGoods', {
        // access_token: JSON.parse(sessionStorage.getItem('userInfo')).access_token,
        ...this.formData,
        pageSize: this.pageInfo.pageSize,
        pageNum: this.pageInfo.pageIndex
      }).then(data => {
        console.log('listHotGoods', data)
        this.tableData = data.data.list // 这一行代码，如果没有数据，会连表头都不显示
        this.pageInfo.pageIndex = data.data.pageNum
        this.pageInfo.pageSize = data.data.pageSize
        this.pageInfo.total = data.data.total
      })
    },
    reset () {
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
      this.dialogVisible2 = false
    },
    dialogconfirm () { // 检验新增 修改用户表单
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.dialogType === 'add') { // 当表单是新增的时候
            req('addHotGoods', {...this.dialogFormData}).then(data => {
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
              req('updateHotGoods', {
                ...this.dialogFormData,
                hotCode: this.tableSelectRows[0].hotCode,
                version: this.tableSelectRows[0].version
                }).then(data => {
                // console.log('测试updateUser data',data)
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
              req('updateHotGoodsShowNum', {...this.dialogFormData2}).then(data => {
                // console.log('updateHotGoodsShowNum data',data)
                if (data.code === 0) {
                  this.$message.success(data.msg)

                  this.fetch()
                  this.$refs.form.resetFields()
                  this.dialogVisible2 = false
                } else {
                  this.$message.error(data.msg)
                }
              })
            }
        } else {
          return false
        }
      })
    }
  }
}
</script>
