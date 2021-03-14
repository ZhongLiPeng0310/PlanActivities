<template>
  <div>
    <!-- search 是获取组件内的点击事件 :model 是这个form 表单的值 -->
    <!-- <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset"> 
      <el-form-item label="轮播图名称" prop="PictureShowName">
        <el-input v-model="formData.PictureShowName"></el-input>
      </el-form-item>
    </i-search> -->

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
      :formatter="columnFormatter">
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
          <el-form-item label="活动" prop="planId">
            <el-input v-model="dialogFormData.planId" placeholder="请输入活动"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="序号" prop="sortNo">
            <el-input v-model="dialogFormData.sortNo" placeholder="请输入序号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="轮播图" prop="showImageUrl">
            <el-input v-model="dialogFormData.showImageUrl" placeholder="轮播图"></el-input>
          </el-form-item>
        </el-col>
       <el-col :span='12'>
          <el-form-item label="开始时间" prop="beginTime">
            <el-input v-model="dialogFormData.beginTime" placeholder="请输入开始时间"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="结束时间" prop="endTime">
            <el-input v-model="dialogFormData.endTime" placeholder="请输入结束时间"></el-input>
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
// import qs from 'qs'
import req from '@/api/plan-manage.js'

export default {
  name: 'tb-picture-show-manage',
  // 使用 ↓
  components: {
    ISearch,
    ITable,
    IDialog,
    IFile
  },
  data () {
    return {
      dialogType: 'add', // 新增
      formData: {
      },
      dialogFormData: {
      },
      dialogVisible: false,
      dialogTitle: '新增轮播图',
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
            this.dialogTitle = '新增轮播图'
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
            this.dialogTitle = '修改轮播图'
            req('getPictureShowById', { // 查看轮播图详情
              id: this.tableSelectRows[0].id
              }).then(data => {
              // console.log('getGoodsById data: ', data)
              this.dialogFormData = Object.assign({}, data.data)
              console.log('ss:',this.dialogFormData)
              // this.dialogFormData.phone = Number(data.data.phone)
              // console.log('this.dialogFormData.phone: ',this.dialogFormData.phone)
              // this.dialogFormData.imagePath = data.data.imagePath
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
                // console.log('item', item)
                return item.id
              }).toString() // 拿到需要操作的数据并存到ids 里面

              let roles = this.tableSelectRows.map(item => {
                return item.role
              }).toString() // 拿到需要操作的数据并存到ids 里面
              req('deletePictureShow', {
                id: ids,
                role: roles,
                nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
                }).then(data => { // 调用接口删除 userCode里面 名字为ids 的数据
                this.$message.success(data.msg) // 打印
                // console.log('msg: ', data.msg)
                this.fetch() // 更新
              })
            })
          }
        }
      ],
      // roleOptions: [
      //   {label: '管理员', value: '1'},
      //   {label: '站长', value: '3'},
      // ],
      // sexOptions: [
      //   {label: '男', value: 1},
      //   {label: '女', value: 0},
      // ],
      columnList: [ //传给子组件的值
        {label: '活动', prop: 'planId'},
        {label: '序号', prop: 'sortNo'},
        {label: '图片路径', prop: 'showImageUrl'},
        {label: '开始时间', prop: 'beginTime'},
        {label: '结束时间', prop: 'endTime'},
        // {label: 'id', prop: 'id'}
      ],
      tableData: [ //传给子组件的值
      ],
      tableSelectRows: [],
      formRules: {
        planId: [
          {required: true, message: '请输入活动id', trigger: 'change'}
        ],
        sortNo: [
          {required: true, message: '请输入排序号', trigger: 'change'}
        ],
        beginTime: [
          {required: true, message: '请输入开始时间', trigger: 'change'}
        ],
        endTime: [
          {required: true, message: '请输入结束时间', trigger: 'change'}
        ],
        showImageUrl: [
          {required: true, message: '请选择图片路径', trigger: 'change'}
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
      this.pageInfo.pageSize = 5
      this.pageInfo.pageIndex = 1 // 不加这里行的话，在第二页搜索第一页的时候不成功
      this.search()
    },
    search () { // 查询函数
      req('listPictureShowByPage', {
        // access_token: JSON.parse(sessionStorage.getItem('userInfo')).access_token,
        ...this.formData,
        pageSize: this.pageInfo.pageSize,
        pageNum: this.pageInfo.pageIndex,
        // nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
      }).then(data => {
        // console.log('dataUser', data)
        this.tableData = data.data.list // 这一行代码，如果没有数据，会连表头都不显示
        this.pageInfo.pageIndex = data.data.pageNum
        this.pageInfo.pageSize = data.data.pageSize
        this.pageInfo.total = data.data.total
      })
    },
    reset () { //重置
      // this.$refs.form.resetFields()
      // this.$emit('reset')
      // console.log('重置按钮按下了')
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
      this.search() // 这里不用fetch 而用search 是因为fetch 一直处于监听状态
      // console.log('当前多少页', value)
      // this.pageInfo.pageIndex = value
    },
    handleSelectionChange (rows) { // 选中了rows 行
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
            req('savePictureShow', {
              ...this.dialogFormData
              // classFirstCode: ,
              // classSecondCode: ,
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
          } else {
              req('savePictureShow', {...this.dialogFormData}).then(data => {
                // console.log('测试updateComm data',data)
                if (data.code === 0) {
                  this.$message.success(data.msg)

                  this.fetch()
                  this.$refs.form.resetFields()
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
    columnFormatter (row, column, cellValue, index) {
      // console.log('row, column, cellValue, index: ', row, column, cellValue, index)
      let distName = this.columnList.filter(item => {
        // console.log('column.property', column.property)
        return item.prop === column.property
      })[0].distName

      if (distName) {
        let transfer = [] // 原来的写法有bug，借用中间值会消除bug，与之前c 遇到的差不多

        transfer = this[distName].filter(item => {
          return item.value === row[column.property]
        })
        
        if (transfer[0]) {
          // console.log('transfer ', transfer)
          // console.log('transfer[0] ', transfer[0].label)
          return transfer[0].label
        } else {
          return row[column.property]
        }
      } else {
        return row[column.property]
      }
      // console.log('distName, row, column, cellValue, index: ', distName, row, column, cellValue, index)
      //   return this[distName].filter(item => {
      //     return item.value === row[column.property]
      //   })[0].label
      // } else {
      //   return row[column.property]
      // }
    },
  }
}
</script>
