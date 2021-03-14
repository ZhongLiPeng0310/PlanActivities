<template>
  <div>
    <input id="file" type="file" @change="inputChange($event)" />
  </div>
</template>

<script>
import axios from 'axios'
// 这个页面是测试上传图片的
export default {
  data () {
    return {}
  },
  methods: {
    inputChange (event) {
      let form = new FormData() // 直接在这里创建form 表单
      let file = document.getElementById('file').files[0]

      form.append('imageFile', file, file.name)
      form.append('access_token', JSON.parse(sessionStorage.getItem('userInfo')).access_token)

      console.log(form)
      axios({
        url: 'http://30k5t94102.wicp.vip/pc/imageUpload/uploadImage',
        method: 'post',
        data: form,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(data => {
        console.log('data', data)
      })
    }
  }
}
</script>
