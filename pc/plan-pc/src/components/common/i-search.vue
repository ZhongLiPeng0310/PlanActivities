<template>
  <div class="i-search">
    <el-form ref="form" :inline="true" :model="model">
      <slot></slot>
      <!-- ↑是插槽 在调用这个组件的时候，页面可以在<i-search>这里加入东西</i-search> -->
      <el-form-item>
        <el-button @click="search" type="primary">查询</el-button>
        <el-button @click="reset" type="primary">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'i-search',
  props: {
    model: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data () {
    return {
      formData: {}
    }
  },
  methods: {
    search () {
      this.$emit('search')
    },
    reset () {
      this.$refs.form.resetFields()
      this.$emit('reset') // 先清空再传参，不然会点两次才有效果
    }
  }
}
</script>

<style lang="scss" scoped>
.i-search {
  padding: 10px;
  background-color: white;

  .el-form {
    // /deep/ 是专门覆盖其他样式的 （增加权重），但它不能用于最外层
    /deep/ .el-form-item {
      margin-bottom: 0;
      .el-form-item__content {
        .el-button{
          height: 30px;
          padding: 0 15px;
        }
      }

      .el-form-item__label {
        width: 85px;
      }
    }
  }
}
</style>
