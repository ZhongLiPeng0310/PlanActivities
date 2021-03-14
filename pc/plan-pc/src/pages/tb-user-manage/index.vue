<template>
  <div>
    <!-- search 是获取组件内的点击事件 :model 是这个form 表单的值 -->
    <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset"> 
      <el-form-item label="用户名" prop="userName">
        <el-input v-model="formData.userName"></el-input>
      </el-form-item>
      <!-- 不加prop 的话，在生命周期里的重置（.resetFields()）进不去 -->
      <el-form-item label="用户账号" prop="userAcct">
        <el-input v-model="formData.userAcct"></el-input>
      </el-form-item>
      <el-form-item label="角色" prop="role">
        <el-select v-model="formData.role">
          <el-option
          v-for="(item, index) in roleOptions"
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
          <el-form-item label="用户账号" prop="userAcct">
            <el-input v-model="dialogFormData.userAcct" placeholder="请输入用户账号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="用户姓名" prop="userName">
            <el-input v-model="dialogFormData.userName" placeholder="请输入用户名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="用户性别" prop="sex">
            <el-select v-model="dialogFormData.sex" placeholder="请选择性别">
              <el-option v-for="(item, index) in sexOptions" :key="index" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model.number="dialogFormData.phone"  placeholder="请输入手机号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="用户邮箱" prop="email">
            <el-input v-model="dialogFormData.email" placeholder="请输入用户邮箱"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="身份证" prop="idCard">
            <el-input
            v-model="dialogFormData.idCard"
            placeholder="请输入身份证"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="用户密码" prop="userPassword">
            <el-input type="password" v-model="dialogFormData.userPassword" placeholder="请输入用户密码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="用户角色" prop="role">
            <el-select v-model="dialogFormData.role" placeholder="请选择用户角色">
              <el-option v-for="(item, index) in roleOptions" :key="index" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="上传头像" prop="imagePath">
            <i-file v-model="dialogFormData.imagePath"></i-file>
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
  name: 'tb-user-manage',
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
      dialogTitle: '新增用户',
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
            this.dialogTitle = '新增用户'
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
            req('getUserDetailData', { // 查用户详情
              id: this.tableSelectRows[0].id
              }).then(data => {
              // console.log('getUserDetailData data: ', data)
              this.dialogFormData = Object.assign({}, data.data)
              this.dialogFormData.phone = Number(data.data.phone)
              // console.log('this.dialogFormData.phone: ',this.dialogFormData.phone)
              this.dialogFormData.imagePath = data.data.imagePath
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
              req('deleteUser', {
                id: ids,
                role: roles,
                nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
                }).then(data => { // 调用接口删除 userCode里面 名字为ids 的数据
                if (data.msg === '不可以删除自己的账号') {
                  this.$message.error(data.msg)
                  this.fetch() // 更新

                  return
                }
                this.$message.success(data.msg) // 打印
                // console.log('msg: ', data.msg)
                this.fetch() // 更新
              })
            })
          }
        }
      ],
      roleOptions: [
        {label: '管理员', value: '1'},
        {label: '站长', value: '3'},
      ],
      sexOptions: [
        {label: '男', value: 1},
        {label: '女', value: 0},
      ],
      columnList: [ //传给子组件的值
        {label: '账号', prop: 'userAcct'},
        {label: '名称', prop: 'userName'},
        {label: '角色', prop: 'role', distName: 'roleOptions'},
        {label: '性别', prop: 'sex', distName: 'sexOptions'},
        {label: '手机', prop: 'phone'},
        {label: '邮箱', prop: 'email'},
        {label: '身份证', prop: 'idCard'}
        // {label: 'id', prop: 'id'}
      ],
      tableData: [ //传给子组件的值
      ],
      tableSelectRows: [],
      formRules: {
        userAcct: [
          {required: true, message: '请输入用户账号', trigger: 'change'}
        ],
        userName: [
          {required: true, message: '请输入用户名称', trigger: 'change'}
        ],
        sex: [
          {required: true, message: '请选择性别', trigger: 'change'}
        ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'change'},
          {type:'number', message: '电话号码必须为数字'},
          {validator:this.validateTel,trigger:'change'}
        ],
        email: [
          {required: true, message: '请输入用户邮箱', trigger: 'change'}
        ],
        idCard: [
          {required: true, message: '请输入身份证', trigger: 'change'},
          {validator: this.validateIdCard, trigger: 'change'}
        ],
        userPassword: [
          {required: true, message: '请输入用户密码', trigger: 'change'}
        ],
        role: [
          {required: true, message: '请选择角色', trigger: 'change'}
        ],
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
      req('getTableData', {
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
          // console.log('1')
            req('addUser', {
              ...this.dialogFormData,
              nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
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
              req('updateUser', {
                ...this.dialogFormData,
                nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
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
    validateIdCard (rule, value, callback) { // 自定义表单校验
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
