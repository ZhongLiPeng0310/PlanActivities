<template>
  <div class="div222">
    <table class="table2">
      <tr>
        <td class="th1">
          <el-button
          v-show="toolbar.length"
          v-for="(item, index) in toolbar"
          :key="index"
          :type="item.type"
          @click="item.func">
            {{item.btnName}}
          </el-button>

          <i-dialog
          v-model="dialogVisible"
          :title="dialogTitle"
          @dialog-before-close="dialogBeforeClose"
          @dialog-cancel="dialogCancel"
          @dialog-confirm="dialogconfirm">
          
          <el-form :model="dialogFormData" ref="form" :rules="formRules" label-width="100px">
            <el-row>
              <el-col :span='12'>
                <el-form-item label="菜单名称" prop="menuName">
                  <el-input v-model="dialogFormData.menuName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span='12'>
                <el-form-item label="菜单路由" prop="route">
                  <el-input v-model="dialogFormData.route"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span='12'>
                <el-form-item label="菜单备注" prop="remarks">
                  <el-input v-model="dialogFormData.remarks"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          </i-dialog>
        </td>
        <td class="th2">
          菜单详情
        </td>
      </tr>
      <tr>
        <td class="td1">

          <!-- <el-menu>
            <el-menu-item
            v-for="(item, index) in menuList"
            :index="item.path"
            :key="index">
              <span slot="title">{{item.menuName}}</span>
            </el-menu-item>
          </el-menu> -->
          <el-tree 
          :data="menuList"
          :props="defaultProps" 
          @node-click="handleNodeClick">
          </el-tree>

        </td>
        <td class="td2">
          <div class="td2_div">
            <el-form :model="dialogFormData2" ref="dialogFormData2" :rules="formRules" label-width="100px">
              <el-row>
                <el-col :span='10'>
                  <el-form-item label="菜单名称">
                    <el-input v-model="dialogFormData2.menuName"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span='10'>
                  <el-form-item label="菜单路由">
                    <el-input v-model="dialogFormData2.route"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span='10'>
                  <el-form-item label="版本号">
                    <el-input v-model="dialogFormData2.version"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span='10'>
                  <el-form-item label="备注">
                    <el-input v-model="dialogFormData2.remarks"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import IDialog from '@/components/common/i-dialog.vue'
import req from '@/api/use-manage.js'
import axios from 'axios'

export default {
  name: 'menu-manage', // 这个据说很重要
  inject: ["reload"], // 解决页面刷新的方法
  // 使用 ↓
  components: {
    IDialog
  },
  data () {
    return {
      tableSelectRows: [],
      dialogFormData2: {},
      dialogVisible: false, //弹框
      dialogTitle: '新增用户',
      // 弹框的内容
      dialogFormData: {
      },
      formRules: { // 表单的验证
        menuName: [
          {required: true, message: '请输入', trigger: 'change'}
        ],
        route: [
          {required: true, message: '请输入', trigger: 'change'}
        ]
      },
      toolbar: [ //传给子组件的值
        {
          btnName: '新增',
          type: 'primary',
          func: () => {
            // console.log('新增', this.pageInfo)
            this.dialogVisible = true
            this.dialogType = 'add'
            this.dialogTitle = '新增菜单'
          }
        },
        {
          btnName: '修改',
          type: 'primary',
          func: () => {
            // console.log('修改', this.pageInfo)

            
            if (this.tableSelectRows.length === 0) {
              this.$message.info('请选择要修改的菜单')
              
              return
            }

            this.dialogType = 'edit'
            this.dialogVisible = true
            this.dialogTitle = '修改菜单'
            req('getMenu', {menuCode: this.tableSelectRows.menuCode}).then(data => {
              console.log('getMenu data: ', data)
              this.dialogFormData = Object.assign({}, data.data)
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
              this.$refs.dialogFormData2.resetFields()
              req('deleteMenu', {menuCode: this.tableSelectRows.menuCode}).then(data => { // 调用接口删除 userID里面 名字为ids 的数据
                this.$message.success(data.msg) // 打印
                // console.log('msg: ', data.msg)
                this.search() // 更新
              })
            })
          }
        },
        {
          btnName: '刷新',
          type: 'primary',
          func: () => {
            // console.log('刷新', this.pageInfo)
            this.reload() //解决页面刷新的方法
          }
        }
      ],
      menuList: [
      ],
      defaultProps: {
        children: 'childGoodsCateList',
        label: 'menuName'
      }
    }
  },
  mounted () {
    this.search()
  },
  methods: {
    search () {
      req('listMenu', {}).then(data => {
        console.log('listMenu_data: ', data)
        this.menuList = data.data
      })
    },
    dialogBeforeClose () { // 关闭清空表单
      // console.log(111)
      this.$refs.form.resetFields()
    },
    dialogCancel () {
      this.dialogVisible = false
      this.$refs.form.resetFields()
      this.reload();  //解决页面刷新的方法
    },
    handleNodeClick (data) {
      this.tableSelectRows = data

      req('getMenu',{
        menuCode:this.tableSelectRows.menuCode
      }).then(data => {
        console.log('菜单详情',data)
        this.dialogFormData2 = data.data
        console.log(this.dialogFormData2)
      })
    },
    dialogconfirm () { // 检验新增 修改用户表单
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.dialogType === 'add') { // 当表单是新增的时候
            req('addMenu', {...this.dialogFormData}).then(data => {
              if (data.code === 0) {
                this.$message.success(data.msg) // 提示框

                this.search()
                this.$refs.form.resetFields()
                this.dialogVisible = false
              } else {
                this.$message.error(data.msg) // 提示框
              }
            })
          } else {
              req('updateMenu', {...this.dialogFormData}).then(data => {
                // console.log('updateMenu data',data)
                if (data.code === 0) {
                  this.$message.success(data.msg)

                  this.search()
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
  }
}
</script>

<style lang="scss" scoped>
.div222 {
  width: 905px;
  height: 480px;

  .table2 {
    border: 1px solid #ddd;
    border-collapse: collapse;
    position: relative;

    .th1 {
      width: 320px;
      height: 50px;
      border: 1px solid #ddd;
      text-align: center;
    }
    .th2 {
      width: 580px;
      border: 1px solid #ddd;
      padding-left: 20px;
      font-size: 20px;
    }
    .td1 {
      height: 400px;
      width: 320px;
      display: inline-block;
      border: 0 solid #ddd;
    }
    .td2 {
      border: 1px solid #ddd;
      height: 400px;
      padding-top: 10px;
      border-bottom: none;

      .td2_div {
        position: relative;
        top: -128px;
      }
    }
    .el-menu-item {
      height: 30px;
      line-height: 30px;
      color: #000000;
    }
  }
}
</style>
