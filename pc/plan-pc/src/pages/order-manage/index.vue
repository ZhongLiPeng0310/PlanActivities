<template>
  <div>
    <!-- search 是获取组件内的点击事件 :model 是这个form 表单的值 -->
    <i-search ref="iSearch" :model="formData" @search="fetch" @reset="reset"> 
      <el-form-item label="下单人" prop="userName">
        <el-input v-model="formData.userName"></el-input>
      </el-form-item>

      <el-form-item label="订单编号" prop="orderCode">
        <el-input v-model="formData.orderCode"></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="phone">
        <el-input v-model="formData.phone"></el-input>
      </el-form-item>

      <!-- 不加prop 的话，在生命周期里的重置（.resetFields()）进不去 -->
      <el-form-item label="订单状态" prop="orderState">
        <el-select v-model="formData.orderState" placeholder="全部">
          <el-option label="已下单" value="7"></el-option>
          <el-option label="已取消" value="9"></el-option>
          <el-option label="已到货" value="1"></el-option>
          <el-option label="已取货" value="3"></el-option>
          <el-option label="已完成未评价" value="5"></el-option>
          <el-option label="已完成已评价" value="8"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="收款期始" prop="payTime">
        <!-- <el-date-picker
          v-model="formData.startPayTime"
          type="date"
          placeholder="选择日期"
          @change="sTime"
          :picker-options="startTimePickerOptions">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="收款期止" prop="endPayTime">
        <el-date-picker
          v-model="formData.endPayTime"
          type="date"
          placeholder="选择日期"
          @change="eTime"
          :picker-options="endTimePickerOptions">
        </el-date-picker> -->
        <el-date-picker
        v-model="formData.payTime"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期">
        </el-date-picker>
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

    <el-dialog
    title="订单详情"
    :visible.sync="dialogVisible">
    
      <el-table
      :data="tableData2"
      border
      style="width: 100%">
      <el-table-column
        v-for="(item, index) in columnList2"
        :key="index"
        :label="item.label"
        :prop="item.prop"
        align="center">
      </el-table-column>
    </el-table>
    
    </el-dialog>    
  </div>
</template>

<script>
// 注册↓
import ISearch from '@/components/common/i-search.vue'
import ITable from '@/components/common/i-table.vue'
import IDialog from '@/components/common/i-dialog.vue'
import req from '@/api/use-manage.js'

export default {
  name: 'order-manage',
  // 使用 ↓
  components: {
    ISearch,
    ITable,
    IDialog
  },
  data () {
    return {
      tableSelectRows: [],
      dialogVisible: false,
      formData: {
        userName: '',
        orderCode: '',
        phone: '',
        orderState: '',
        payTime: [],
      },
      pageInfo: { // 子组件传过来的值
        pageIndex: 1,
        pageSize: 5,
        total: 0
      },
      startTimePickerOptions: { // 用来限制时间的，value 是该方法绑定的那个点的时间
        disabledDate: (value) => {
          // console.log(value)
          return this.formData.endPayTime < value
        }
      },
      endTimePickerOptions: {
        disabledDate: (value) => {
          // console.log('this.formData.startPayTime  and  value', this.formData.startPayTime, value)
          return this.formData.startPayTime > value
        }
      },
      toolbar: [ //传给子组件的值
        {
          btnName: '详情',
          type: 'primary',
          func: () => {
            // console.log('新增', this.pageInfo)

            if (this.tableSelectRows.length === 0){
              this.$message.info('请选择需要查看详情的订单')
              return
            }
            if (this.tableSelectRows.length > 1) { // 当选中的数据数大于1
              this.$message.info('一次只能查看一个订单的详情')
              return
            }

            this.dialogVisible = true
            
            let id = this.tableSelectRows.map(item => {
                return item.orderCode
              }).toString()
              
            req('getListOrder', {
              orderCode: id
            }).then(data => {
              console.log('getListOrder', data)
              this.tableData2 = data.data.goodsList // 这一行代码，如果没有数据，会连表头都不显示
              for (let i = 0; i < this.tableData2.length; i++ ) {

                this.tableData2[i].orderCode = data.data.orderCode
                this.tableData2[i].userCode = data.data.userCode
                this.tableData2[i].orderMoney = data.data.orderMoney
              }
            })
          }
        },
        {
          btnName: '订单取消',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) { // 选择数为零，删除支持多选
              this.$message.info('请选择需要取消的订单')
              
              return
            }
            
            let ids = this.tableSelectRows.map(item => {
              return item.orderCode
            }).toString() // 拿到需要操作的数据并存到ids 里面

            let sion = this.tableSelectRows.map(item => {
              return item.version
            }).toString()

            console.log('sion', sion)
            req('updateOrderState', {
              orderCode: ids,
              orderState: 9,
              version: sion,
              nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
              }).then(data => {
                // console.log(data)
                this.$message.success(data.msg) // 打印
                this.fetch()
            })
          }
        },
        {
          btnName: '订单到货',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) { // 选择数为零，删除支持多选
              this.$message.info('请选择需要订单到货的订单')
              
              return
            }
            
            let ids = this.tableSelectRows.map(item => {
              return item.orderCode
            }).toString() // 拿到需要操作的数据并存到ids 里面

            let sion = this.tableSelectRows.map(item => {
              return item.version
            }).toString()

            req('updateOrderState', {
              orderCode: ids,
              orderState: 1,
              version: sion,
              nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
              }).then(data => {
                // console.log(data)
                this.$message.success(data.msg) // 打印
                this.fetch()
            })
          }
        },
        {
          btnName: '已下单',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) { // 选择数为零，删除支持多选
              this.$message.info('请选择需要已下单的订单')
              
              return
            }
            
            let ids = this.tableSelectRows.map(item => {
              return item.orderCode
            }).toString() // 拿到需要操作的数据并存到ids 里面

            let sion = this.tableSelectRows.map(item => {
              return item.version
            }).toString()

            req('updateOrderState', {
              orderCode: ids,
              orderState: 7,
              version: sion,
              nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
              }).then(data => {
                // console.log(data)
                this.$message.success(data.msg) // 打印
                this.fetch()
            })
          }
        },
        {
          btnName: '订单已取货',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) { // 选择数为零，删除支持多选
              this.$message.info('请选择需要订单已取货的订单')
              
              return
            }
            
            let ids = this.tableSelectRows.map(item => {
              return item.orderCode
            }).toString() // 拿到需要操作的数据并存到ids 里面

            let sion = this.tableSelectRows.map(item => {
              return item.version
            }).toString()

            req('updateOrderState', {
              orderCode: ids,
              orderState: 3,
              version: sion,
              nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
              }).then(data => {
                // console.log(data)
                this.$message.success(data.msg) // 打印
                this.fetch()
            })
          }
        },
        {
          btnName: '已完成未评价',
          type: 'primary',
          func: () => {
            if (this.tableSelectRows.length === 0) { // 选择数为零，删除支持多选
              this.$message.info('请选择需要已完成已评价的订单')
              
              return
            }
            
            let ids = this.tableSelectRows.map(item => {
              return item.orderCode
            }).toString() // 拿到需要操作的数据并存到ids 里面

            let sion = this.tableSelectRows.map(item => {
              return item.version
            }).toString()

            req('updateOrderState', {
              orderCode: ids,
              orderState: 5,
              version: sion,
              nowRole: JSON.parse(sessionStorage.getItem('roleInfo')).role
              }).then(data => {
                // console.log(data)
                this.$message.success(data.msg) // 打印
                this.fetch()
            })
          }
        }
      ],
      orderS: [
        { label: '已下单', value: '7' },
        { label: '已取消', value: '9' },
        { label: '已到货', value: '1' },
        { label: '已取货', value: '3' },
        { label: '已完成未评价', value: '5' },
        { label: '已完成已评价', value: '8' }
      ],
      columnList: [ //传给子组件的值
        {label: '订单编号', prop: 'orderCode'},
        {label: '订单总价', prop: 'orderMoney'},
        {label: '订单状态', prop: 'orderState', distName: 'orderS'},
        {label: '站点编号', prop: 'storeCode'},
        {label: '下单人姓名', prop: 'userName'},
        {label: '下单人手机号', prop: 'phone'},
        {label: '版本号', prop: 'version'},
        {label: '确认付款时间', prop: 'createTime'},
      ],
      columnList2: [
        {label: '用户编号', prop: 'userCode'},
        {label: '订单编号', prop: 'orderCode'},
        {label: '商品编号', prop: 'goodsCode'},
        {label: '商品名称', prop: 'goodsName'},
        {label: '商品数量', prop: 'orderSum'},
        {label: '总金额', prop: 'orderMoney'},
        {label: '售价', prop: 'salePrice'},
      ],
      tableData: [ //传给子组件的值
      ],
      tableData2:[], // 详情框
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
      console.log('this.formData', this.pageInfo.pageSize)
      req('listOrders', {
        // access_token: JSON.parse(sessionStorage.getItem('userInfo')).access_token,
        ...this.formData,
        startPayTime: this.formData.payTime[0] ? this.$moment(this.formData.payTime[0]).format('YYYY-MM-DD') : '',
        endPayTime: this.formData.payTime[1] ? this.$moment(this.formData.payTime[1]).format('YYYY-MM-DD') : '',
        role: JSON.parse(sessionStorage.getItem('roleInfo')).role,
        pageSize: this.pageInfo.pageSize,
        pageNum: this.pageInfo.pageIndex
      }).then(data => {
        console.log('listOrders', data)
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
      this.$refs.form.resetFields()
      this.dialogVisible = false
    },
    dialogconfirm () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log('表单通过')
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
    conTime () {
      if (this.startTimedata < this.endTimedata) {
        // console.log('这里时间对比通过了')
      } else {
        this.$refs.iSearch.$refs.form.resetFields() // 清空biao'd
        this.$message.error('请正确输入时间段')
        this.fetch()
      }
    }
  }
}
</script>

