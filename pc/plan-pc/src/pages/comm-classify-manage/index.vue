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
                <el-form-item label="分类名称" prop="className">
                  <el-input v-model="dialogFormData.className"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span='12'>
                <el-form-item label="分类备注" prop="remark">
                  <el-input v-model="dialogFormData.remark"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          </i-dialog>
        </td>
        <td class="th2">
          产品分类详情
        </td>
      </tr>
      <tr>
        <td class="td1">

          <!-- <el-menu>
            <el-tree :data="data" :props="defaultProps"></el-tree>
          </el-menu> -->
          <el-tree
          :data="menuList"
          :props="defaultProps" 
          @node-click="handleNodeClick">
          </el-tree>

        </td>
        <td class="td2">
          <div class="td2_div">

            <el-form :model="dialogFormData2" ref="form" :rules="formRules" label-width="100px">
              <el-row>
                <el-col :span='10'>
                  <el-form-item label="分类名称">
                    <el-input v-model="dialogFormData2.className"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span='10'>
                  <el-form-item label="备注">
                    <el-input v-model="dialogFormData2.remark"></el-input>
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
  name: 'comm-classify-manage', // 这个据说很重要
  inject: ["reload"], // 解决页面刷新的方法
  // 使用 ↓
  components: {
    IDialog
  },
  data () {
    return {
      defaultProps: { //树型图
        children:'classSecondList',
        label: 'className'
      },
      tableSelectRows: [],
      dialogFormData2: {},
      dialogVisible: false, //弹框
      dialogTitle: '新增分类',
      // 弹框的内容
      dialogFormData: {
      },
      formRules: { // 表单的验证
        className: [
          {required: true, message: '请输入', trigger: 'change'}
        ],
        // test2: [
        //   {required: true, message: '请输入', trigger: 'change'}
        // ],
        // test3: [
        //   {required: true, message: '请输入', trigger: 'change'}
        // ]
      },
      toolbar: [ //传给子组件的值
        {
          btnName: '新增',
          type: 'primary',
          func: () => {
            // console.log('新增')
            this.dialogVisible = true
            this.dialogType = 'add'
            this.dialogTitle = '新增分类'
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
            this.dialogTitle = '修改分类'
            const json2 = JSON.parse(JSON.stringify(this.tableSelectRows).replace(/classFirstCode|classSecondCode/g, 'classCode')) // 改名字
            req('getGoodsClassify', {classCode: json2.classCode}).then(data => {
              console.log('getGoodsClassify data: ', data)
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
            
            // console.log('this.tableSelectRows', typeof(this.tableSelectRows.classifyParent))
            
            this.$confirm('此操作将永久删除该数据，是否继续').then(() => {

              const json2 = JSON.parse(JSON.stringify(this.tableSelectRows).replace(/classFirstCode|classSecondCode/g, 'classCode')) // 改名字
              req('deleteGoodsClassify', {
                classCode: json2.classCode,
                }).then(data => { // 调用接口删除 userID里面 名字为ids 的数据
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
            this.reload()
          }
        }
      ],
      menuList: [
      ]
    }
  },
  mounted () {
    this.search()
  },
  methods: {
    search () {
      req('listAllGoodsClassify', {}).then(data => {
        console.log('listAllGoodsClassify: ', data)
        const json = JSON.parse(JSON.stringify(data).replace(/classFirstName|classSecondName/g, 'className')) // 改名字
        this.menuList = json.data
        console.log('this.menuList: ',this.menuList)
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
      // console.log('this.tableSelectRows: ', this.tableSelectRows)
      const json2 = JSON.parse(JSON.stringify(this.tableSelectRows).replace(/classFirstCode|classSecondCode/g, 'classCode')) // 改名字
      // console.log('json2: ', json2)
      req('getGoodsClassify',{
        classCode: json2.classCode
      }).then(data => {
        // console.log('菜单详情',data)
        this.dialogFormData2 = data.data
        
        // console.log(this.dialogFormData2)
      })
    },
    dialogconfirm () { // 检验新增 修改分类表单
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.dialogType === 'add') { // 当表单是新增的时候
          const json2 = JSON.parse(JSON.stringify(this.tableSelectRows).replace(/classFirstCode|classSecondCode/g, 'classCode')) // 改名字
            console.log ('不等于零', json2.classCode)
            req('addGoodsClassify', {
              ...this.dialogFormData,
              lastClassCode: json2.classCode
              }).then(data => {
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
              const json2 = JSON.parse(JSON.stringify(this.tableSelectRows).replace(/classFirstCode|classSecondCode/g, 'classCode')) // 改名字
              console.log('this.tableSelectRows.version: ', this.tableSelectRows.version)
              req('updateGoodsClassify', { // 修改
                ...this.dialogFormData,
                classCode: json2.classCode,
                version: this.dialogFormData.version,
                }).then(data => {
                console.log('updateGoodsClassify data',data)
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
      // border: 1px solid #ddd;
      height: 400px;
      width: 320px;
      display: inline-block;
    }
    .td2 {
      border: 1px solid #ddd;
      width: 580px;
      height: 400px;

      .td2_div {
        position: relative;
        top: -150px;
      }
    }
    .el-menu-item {
      height: 30px;
      line-height: 30px;
    }
  }
}
</style>
