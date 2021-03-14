<template>
  <div>
    <!-- search 是获取组件内的点击事件 :model 是这个form 表单的值 -->
    <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset"> 
      <!-- 不加prop 的话，在生命周期里的重置（.resetFields()）进不去 -->
      <el-form-item label="客户名称" prop="userName">
        <el-input v-model="formData.userName"></el-input>
      </el-form-item>
      <el-form-item label="客户账号" prop="userAcct">
        <el-input v-model="formData.userAcct"></el-input>
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
    :selectionShow="false"
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
  </div>
</template>

<script>
// 注册↓
import ISearch from '@/components/common/i-search.vue'
import ITable from '@/components/common/i-table.vue'
import IDialog from '@/components/common/i-dialog.vue'
import req from '@/api/use-manage.js'

export default {
  name: 'client-manage',
  // 使用 ↓
  components: {
    ISearch,
    ITable,
    IDialog
  },
  data () {
    return {
      formData: {},
      pageInfo: { // 子组件传过来的值
        pageIndex: 1,
        pageSize: 5,
        total: 0
      },
      toolbar: [ //传给子组件的值
      ],
      sexOptions: [
        {label: '男', value: 1},
        {label: '女', value: 0},
      ],
      columnList: [ //传给子组件的值
        {label: '客户账号', prop: 'userAcct'},
        {label: '姓名', prop: 'userName'},
        {label: '性别', prop: 'sex', distName: 'sexOptions'},
        {label: '手机', prop: 'phone'},
        {label: '客户邮箱', prop: 'email'},
        {label: '身份证', prop: 'idCard'}
      ],
      tableData: [ //传给子组件的值
      ]
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
      req('listGetTableData', {
        // access_token: JSON.parse(sessionStorage.getItem('userInfo')).access_token, // 拿令牌的
        ...this.formData,
        role: JSON.parse(sessionStorage.getItem('roleInfo')).role,
        pageSize: this.pageInfo.pageSize,
        pageNum: this.pageInfo.pageIndex
      }).then(data => {
        console.log('data', data)
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
      this.dialogVisible = false
      this.$refs.form.resetFields()
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
    },
    dialogconfirm () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // console.log('表单通过')
        } else {
          return false
        }
      })
    }
  }
}
</script>
