<template>
  <div class="i-table">
    <div class="btn-list">
      <!-- 这是个公共组件，必须可以多次使用
          所以这里用v-for 输出。v-for 里面的内容
          直接在使用该组件的data () 里面加 -->
      <!-- v-for 的item 是一个参数，指数组中的每一项，
          index 是索引（下标），in 后面的toolbar
          是要遍历的数组 -->
      <el-button
      v-show="toolbar.length"
      v-for="(item, index) in toolbar"
      :key="index"
      :type="item.type"
      @click="item.func">
        {{item.btnName}}
      </el-button>
    </div>

    <div class="table-container">
      <!-- :data 是展示数据table 表单的array 数据 -->
      <el-table 
      v-show="tableData.length"
      border
      :data="tableData"
      height="460"
      @selection-change="handleSelectionChange">
        <el-table-column
          v-if="selectionShow"
          type="selection"
          width="55"
          height="80"
          align="center">
        </el-table-column>

        <slot></slot>
      </el-table>
    </div>

    <div class="table-pagination">
       <el-pagination
       v-show="tableData.length"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageInfo.pageIndex"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'i-table',
  props: {
    toolbar: {
      type: Array,
      default: () => {
        return []
      }
    },
    tableData: {
      type: Array,
      default: () => {
        return []
      }
    },
    pageInfo: {
      type: Object,
      default: () => {
        return {
          pageIndex: 1,
          pageSize: 5,
          total: 0
        }
      }
    },
    selectionShow: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {}
  },
  methods: {
    // 当页条数
    handleSizeChange (value) {
      this.$emit('handleSizeChange', value)
      // this.pageInfo.pageSize = value
    },
    // 当前页数
    handleCurrentChange (value) {
      this.$emit('handleCurrentChange', value)
      // this.pageInfo.pageIndex = value
    },
    handleSelectionChange (rows) {
      console.log('i-table', rows)
      this.$emit('selection-change', rows)
    }
  }
}
</script>

<style lang="scss" scoped>
.i-table {
  padding: 10px;

  .btn-list {
    margin-bottom: 10px;
  }

  .table-pagination {
    .el-pagination {
      text-align: right;
    }
  }
}
</style>
