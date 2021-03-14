<template>
  <div class="container" v-loading="loading">
    <div class="file-box">
      <el-button class="addBtn" @click="addImg" type="primary" icon="el-icon-plus"></el-button>
      <span v-show="imgPath">picture uploaded</span>
      <el-button v-show="imgPath" @click="delImg" class="delBtn" type="danger" icon="el-icon-delete"></el-button>
    </div>
    <input style="display: none;" id="imgFile" type="file" ref="iFile" name="imageFile" @change="inputChange($event)"/>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'i-file',
  props: {
    value: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      imgPath: '',
      loading: false
    }
  },
  watch: {
    value: {
      handler (value) {
        this.imgPath = value

        if (!value) {
          document.getElementById('imgFile').value = ''
        }
      },
      deep: true
    }
  },
  methods: {
    openImg () {
      window.open(this.imgPath)
    },
    addImg () {
      this.$refs.iFile.click()
    },
    delImg () {
      this.imgPath = ''
      this.$emit('input', this.imgPath)
    },
    inputChange (event) {
      if (event.target.files.length) {
        this.loading = true
        let form = new FormData()
        let jpgFile = document.getElementById('imgFile').files[0]
        form.append('imageFile', jpgFile, jpgFile.name)
        form.append('access_token', JSON.parse(sessionStorage.getItem('userInfo')).access_token)

        axios({
          method: 'post',
          url: 'http://29ng920368.wicp.vip/pc/images/uploadImage',
          data: form,
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(data => {
          console.log('img', data)
          this.loading = false
          this.imgPath = data.data.data.imagePath
          this.$emit('input', this.imgPath) // 显示出图片的名字
        }).catch(() => {
          this.loading = false
        })
      } else {
        this.imgPath = ''
        this.$emit('input', this.imgPath)
      }
    }
  }
}
</script>
