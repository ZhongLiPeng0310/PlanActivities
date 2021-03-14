<template>
  <div>
    <!-- search 是获取组件内的点击事件 :model 是这个form 表单的值 -->
    <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset"> 
      <el-form-item label="产品名称" prop="goodsName">
        <el-input v-model="formData.goodsName"></el-input>
      </el-form-item>
      <!-- 不加prop 的话，在生命周期里的重置（.resetFields()）进不去 -->
      <el-form-item label="商品状态" prop="goodsState">
        <el-select v-model="formData.goodsState" placeholder="请输入">
          <el-option v-for="(item, index) in commState"
          :key="index"
          :label="item.label"
          :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="广告词" prop="advertisement">
        <el-input v-model="formData.advertisement"></el-input>
      </el-form-item>
      <!-- <el-form-item label="出版社" prop="qualityDate">
        <el-input v-model="formData.qualityDate"></el-input>
      </el-form-item>
      <el-form-item label="作者" prop="sumSale">
        <el-input v-model="formData.sumSale"></el-input>
      </el-form-item> -->
    </i-search>

    <!-- <el-button @click="dictPage()">click</el-button> -->

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
    @dialog-confirm="dialogconfirm"
    >
    
    <el-form
    :model="dialogFormData"
    ref="form"
    :rules="formRules"
    label-width="100px">
      <el-row>
        <el-col :span='12'>
          <el-form-item label="产品名称" prop="goodsName">
            <el-input v-model="dialogFormData.goodsName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="站点名称" prop="storeCode">
            <!-- <el-input v-model="dialogFormData.storeCode"></el-input> -->
            <el-select v-model="dialogFormData.storeCode" @change="stationChange">
              <el-option v-for="(item, index) in station"
              :key="index"
              :label="item.label"
              :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="一级分类" prop="classFirstCode">
            <!-- <el-select 
            @focus="listGoodsClassify" 
            @change="listGoodsClassifySecond(dialogFormData.classFirstCode)" 
            v-model="dialogFormData.classFirstCode" placeholder="请输入"> -->
            <el-select v-model="dialogFormData.classFirstCode" @change="oneChange">
              <el-option v-for="(item, index) in oneSort"
              :key="index"
              :label="item.label"
              :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="二级分类" prop="classSecondCode">
            <el-select v-model="dialogFormData.classSecondCode" placeholder="请输入">
              <el-option v-for="(item, index) in twoSort"
              :key="index"
              :label="item.label"
              :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="广告词" prop="advertisement">
            <el-input type="textarea" v-model="dialogFormData.advertisement"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="产品介绍" prop="goodsIntroduce">
            <el-input type="textarea" v-model="dialogFormData.goodsIntroduce"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="保质期/月" prop="qualityDate">
            <el-input v-model="dialogFormData.qualityDate"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="供应商名称" prop="userCode">
            <el-select v-model="dialogFormData.userCode" @focus="supplierChange">
              <el-option v-for="(item, index) in supplier"
              :key="index"
              :label="item.label"
              :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="商品库存" prop="stock">
            <el-input v-model.number="dialogFormData.stock"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="成本价" prop="costPrice">
            <el-input
            @onblur="check(this)"
            onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
            v-model="dialogFormData.costPrice"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span='12'>
          <el-form-item label="在售价" prop="salePrice">
            <el-input
            @onblur="check(this)"
            onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
            v-model="dialogFormData.salePrice"></el-input>
          </el-form-item>
        </el-col>
        
        <el-col :span='12'>
          <el-form-item label="图片" prop="imagePath">
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
import req from '@/api/use-manage.js'

export default {
  name: 'comm-manage',
  // 使用 ↓
  components: {
    ISearch,
    ITable,
    IDialog,
    IFile
  },
  data () {
    return {
      firstId: '',
      dialogType: 'add', // 新增
      formData: { // 搜素框表单
      },
      dialogFormData: { // 弹出框表单
      },
      dialogVisible: false, // 控制弹出框的判断
      dialogTitle: '新增用户',
      pageInfo: { // 子组件传过来的值
        pageIndex: 1,
        pageSize: 5,
        total: 0
      },
      toolbar: [ //传给子组件的值
        // {
        //   btnName: '新增',
        //   type: 'primary',
        //   func: () => {
        //     // console.log('新增', this.pageInfo),
        //     this.dialogVisible = true
        //     this.dialogType = 'add'
        //     this.dialogTitle = '新增商品'

        //     req('listGoodsGetAllStore', {}).then(data => {
        //       console.log('站长_data: ', data)
        //       this.station.splice(0)
        //       for(let i = 0; i < data.data.length; i++) {
        //         this.station.push({label: data.data[i].storeName, value: data.data[i].storeCode})
        //       }
        //     })

        //     req('listGoodsClassify', {}).then(data => { // 一级下拉框的api
        //       console.log('listGoodsClassify: ', data)
        //       this.oneSort.splice(0)
        //       for(let i = 0; i < data.data.length; i++) {
        //         this.oneSort.push({label: data.data[i].classFirstName, value: data.data[i].classFirstCode})
        //       }
        //     })
        //   }
        // },
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
            this.dialogTitle = '修改商品信息'

            req('listGoodsGetAllStore', {}).then(data => {
              console.log('站长_data: ', data)
              this.station.splice(0)
              for(let i = 0; i < data.data.length; i++) {
                this.station.push({label: data.data[i].storeName, value: data.data[i].storeCode})
              }
            })

            req('listGoodsClassify', {}).then(data => {
              // console.log('一级', data)
              this.oneSort.splice(0)
              for(let i = 0; i < data.data.length; i++) {
                this.oneSort.push({label: data.data[i].classFirstName, value: data.data[i].classFirstCode})
              }
              
            })

            req('commGetUserDetailData', {
              goodsCode: this.tableSelectRows[0].goodsCode
              }).then(data => {
              Promise.all([
                this.oneChange(data.data.classFirstCode)
              ]).then(dataList => {
                this.dialogFormData = Object.assign({}, data.data, {
                  imagePath: data.data.imagePath,
                  stock: parseInt(data.data.stock)
                })
              })
            })
          }
        },
        {
          btnName: '删除',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) { // 选择数为零，删除支持多选
              this.$message.info('请选择要删除的数据')
              
              return
            }

            this.$confirm('此操作将永久删除该数据，是否继续').then(() => {
              let ids = this.tableSelectRows.map(item => {
                return item.goodsCode
              }).toString() // 拿到需要操作的数据并存到ids 里面

              req('deleteComm', {goodsCode: ids}).then(data => { // 调用接口删除 goodsId里面 名字为ids 的数据
                this.$message.success(data.msg) // 打印
                // console.log('msg: ', data.msg)
                this.fetch() // 更新
              })
            })
          }
        },
        {
          btnName: '上架',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) { // 选择数为零，删除支持多选
              this.$message.info('请选择要上架的数据')
              
              return
            }

            
            let ids = this.tableSelectRows.map(item => {
              return item.goodsCode
            }).toString() // 拿到需要操作的数据并存到ids 里面

            let sion = this.tableSelectRows.map(item => {
              // console.log('item: ', item)
              return item.version
            }).toString()

            let stock = this.tableSelectRows.map(item => {
              return item.stock
            }).toString()

            stock = parseInt(stock)
            // console.log('stock: ', typeof(stock))
            req('updateGoodsShelfState', {
              goodsCode: ids,
              goodsState: 2,
              version: sion
              }).then(data => {
              this.$message.success(data.msg) // 打印
              this.fetch()
            })
          }
        },
        {
          btnName: '下架',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) { // 选择数为零，删除支持多选
              this.$message.info('请选择要下架的数据')
              
              return
            }
            
            let ids = this.tableSelectRows.map(item => {
              return item.goodsCode
            }).toString() // 拿到需要操作的数据并存到ids 里面

            let sion = this.tableSelectRows.map(item => {
              return item.version
            }).toString()

            let stock = this.tableSelectRows.map(item => {
              return item.stock
            }).toString()

            stock = parseInt(stock)

            req('updateGoodsShelfState', {
              goodsCode: ids,
              goodsState: 1,
              version: sion
              }).then(data => {
              this.$message.success(data.msg) // 打印
              this.fetch()
            })
          }
        }
      ],
      station: [], // 站点下拉框
      supplier: [], //供应商下拉框
      oneSort: [
      ],
      twoSort: [],
      commState: [
        // {label: '售罄', value: '0'},
        {label: '下架', value: 1},
        {label: '在售', value: 2},
        {label: '未发布', value: 3},
      ],
      columnList: [ //传给子组件的值 分页查询的表头
        {label: '产品名称', prop: 'goodsName'},
        {label: '产品编码', prop: 'goodsCode'},
        {label: '一级分类名称', prop: 'classFirstName'},
        {label: '二级分类名称', prop: 'classSecondName'},
        {label: '广告词', prop: 'advertisement'},
        {label: '产品介绍', prop: 'goodsIntroduce'},
        {label: '保质期', prop: 'qualityDate'},
        {label: '供应商名称', prop: 'userName'},
        {label: '库存', prop: 'stock'},
        {label: '成本价', prop: 'costPrice'},
        {label: '售价', prop: 'salePrice'},
        {label: '商品状态', prop: 'goodsState', distName: 'commState'},
        {label: '销售量', prop: 'sumSale'},
        {label: '图片', prop: 'imagePath'},
        {label: '上架时间', prop: 'upTime'},
        {label: '版本号', prop: 'version'}
      ],
      tableData: [ //传给子组件的值
        // {goodsName: '1', costPrice: '1', salePrice: '1', goodsSales: '1', oneClassifyName: '1', twoClassifyName: '1', advertisement: '1', goodsIntroduce: '1', goodsStateId: '1', goodsShelfTime: '1', goodsViewsNum: '1', goodsId: '1', userCode: '1', stock: '1', storeCode: '1'},
      ],
      // SecondName: [],
      tableSelectRows: [], // 选中的行数
      formRules: {
        goodsName: [
          {required: true, message: '请输入商品名称', trigger: 'change'}
        ],
        storeCode: [
          {required: true, message: '请输入书号', trigger: 'change'}
        ],
        userCode: [
          {required: true, message: '请输入商家名称', trigger: 'change'}
        ],
        stock: [
          {required: true, message: '请输入商品库存'},
          {type:'number', message: '商品库存为数字'}
        ],
        costPrice: [
          {required: true, message: '请输入成本价', trigger: 'change'}
        ],
        salePrice: [
          {required: true, message: '请输入在售价', trigger: 'change'}
        ],
        classFirstCode: [
          {required: true, message: '请输入一级分类', trigger: 'change'}
        ],
        classSecondName: [
          {required: true, message: '请输入二级分类', trigger: 'change'}
        ],
        sumSale: [
          {required: true, message: '请输入作者', trigger: 'change'}
        ],
        qualityDate: [
          {required: true, message: '请输入出版社', trigger: 'change'}
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
      req('commGetTableData', {
        ...this.formData,
        pageSize: this.pageInfo.pageSize,
        pageNum: this.pageInfo.pageIndex
      }).then(data => {
        console.log('commGetTableData_data: ', data)
        this.tableData = data.data.list // 当后台数据是零的时候，好像是连表头的不显示的
        this.pageInfo.pageIndex = data.data.pageNum
        this.pageInfo.pageSize = data.data.pageSize
        this.pageInfo.total = data.data.total
      })
      // this.getName()
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
            req('addComm', {
              ...this.dialogFormData
              // classFirstCode: ,
              // classSecondCode: ,
              }).then(data => {
              if (data.code === 0) {0
                this.$message.success(data.msg) // 提示框

                this.fetch()
                this.$refs.form.resetFields()
                this.dialogVisible = false
              } else {
                this.$message.error(data.msg) // 提示框
              }
            })
          } else {
              req('updateComm', {...this.dialogFormData}).then(data => {
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
    check (e) { // 数字和小数的自定义方法
      var re = /^\d+(?=\.{0,1}\d+$|$)/
      if (e.value != "") {
        if (!re.test(e.value)) {
            alert("请输入正确的数字");
            e.value = "";
            e.focus();
        }
      }
    },
    oneChange (value) {
      console.log('oneValue', value)
      this.firstId = value
      
      if (this.dialogFormData.classSecondCode) { // 复选清空
        this.dialogFormData.classSecondCode = ''
      }
      
      this.twochange()
    },
    twochange () { // 单api 两级下拉框的操作2
      req('listGoodsClassifySecond', {lastClassCode: this.firstId}).then(data => {
        // console.log(data)
        this.twoSort.splice(0)
        for(let i = 0; i < data.data.length; i++) { // 如果走刘晨曦的代码的话data 后面还需要.goodsClassifyList
          this.twoSort.push({label: data.data[i].classSecondName, value: data.data[i].classSecondCode})
        }
      })
    },
    stationChange (value) {
      
        console.log(1111)
        console.log('stationChange_value: ', value)
     
    },
    supplierChange () {
      // console.log('你点击了供应商_data: ')
      req('listDrivers', {}).then(data => {
        // console.log('供应商_data: ', data)
        this.supplier.splice(0)
        for(let i = 0; i < data.data.length; i++) {
          this.supplier.push({label: data.data[i].userName, value: data.data[i].userCode})
        }
      })
    }
    
  }
}
</script>
