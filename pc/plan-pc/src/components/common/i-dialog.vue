<template>
  <div>
    <!-- .sync 是双向绑定 -->
    <el-dialog
      :title="title"
      :visible.sync="visible"
      :before-close="dialogBeforeClose"
      @close="dialogClose"
      :append-to-body="appendToBody"
      width="770px">

      <slot></slot>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogCancel">取 消</el-button>
        <el-button type="primary" @click="dialogconfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'i-dialog',
  props: {
    value: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    appendToBody: { // 处理当弹出框里面再点弹出框会变暗的情况
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      visible: false
    }
  },
  methods: {
    dialogClose () {
      this.visible = false
      this.$emit('input', this.visible)
    },
    dialogBeforeClose () {
      this.$emit('dialog-before-close')
      this.visible = false
    },
    dialogCancel () {
      this.$emit('dialog-cancel')
    },
    dialogconfirm () {
      this.$emit('dialog-confirm')
    }
  },
  watch: {
    value: {
      handler (value) {
        this.visible = value
      },
      deep: true
    }
  }
}
</script>
