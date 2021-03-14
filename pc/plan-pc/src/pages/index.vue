<template>
   <el-container style="padding-top:20px;padding-left:20px">
  <el-aside width="316px" height="425px">
      <div class="btn-class">
          <el-button v-for="(item,index) in toolbar" 
          :key="index" 
          @click="item.func"
          :type="item.type">{{item.btnName}}
          </el-button>
     </div>

    <el-tree :data="classifyData"
     :props="defaultProps" 
     @node-click="handleNodeClick">
     </el-tree>
     
      </el-aside>
  <el-container>
      <el-main>
        <div class="header">菜单详情</div>
        <el-form :Data="detailData" :inline="true">
            <el-form-item label="菜单编号">
                <el-input v-model="detailData.menuId"></el-input>
            </el-form-item>
            <el-form-item label="菜单名称">
                <el-input v-model="detailData.menuName"></el-input>
            </el-form-item>
            <el-form-item label="菜单路径">
                <el-input v-model="detailData.menuRoute"></el-input>
            </el-form-item>
            <el-form-item label="父级编号">
                <el-input v-model="detailData.menuParent"></el-input>
            </el-form-item>
            <el-form-item label="备注信息">
                <el-input v-model="detailData.menuComment"></el-input>
            </el-form-item>
            <el-form-item label="版本号">
                <el-input v-model="detailData.version"></el-input>
            </el-form-item>
        </el-form>
      </el-main>
  </el-container>

     <i-dialog 
        v-model="dialogVisible"
        :title="dialogTitle"
        @dialog-before-close="dialogBeforeClose"
        @dialog-cancel="dialogCancel"
        @dialog-confirm="dialogConfirm">  
        <el-form :model="dialogFormData" ref="form" :rules="formRules" label-width="110px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="菜单名称" prop="menuName">
                        <el-input v-model="dialogFormData.menuName"></el-input>
                    </el-form-item> 
                </el-col>
                 <el-col :span="12">
                    <el-form-item label="菜单路径" prop="menuRoute">
                        <el-input v-model="dialogFormData.menuRoute"></el-input>
                    </el-form-item> 
                </el-col>
                <el-col :span="12">
                    <el-form-item label="备注信息" prop="menuComment">
                        <el-input v-model="dialogFormData.menuComment"></el-input>
                    </el-form-item> 
                </el-col>
            </el-row>
        </el-form>
        </i-dialog>
</el-container>
</template>

<script>
import IDialog from '@/components/common/i-dialog.vue'
import req from '@/api/menu-manage.js'
import axios from 'axios'
export default {
    name:'menu-manage',
    components:{
      IDialog
    },
    inject:["reload"],//解决页面刷新的方法
    data () {
        return {
            detailData: {
            },
            dialogTitle:'',
            dialogVisible:false,
            classifyData:[],
            dialogFormData:{},
            Noderows:[],
            defaultProps: {
            children: 'childGoodsCateList',
            label: 'menuName'
           },
            toolbar:[
                {
                    btnName:'新增',
                    type:'primary',
                    func:() => {
                        if(!this.Noderows.menuId){
                
                        }else{
                         this.dialogFormData = this.Noderows
                        }
                         this.dialogTitle='新增菜单'
                         this.dialogVisible = true
                    }
                },
                {
                    btnName:'修改',
                    type:'primary',
                    func:() => {                       
                        if(this.Noderows.length === 0){
                            this.$message.info('请修改需要的数据')

                            return 
                        }
                        if(this.Noderows.length > 1) {
                            this.$message.info('请修改一条数据')
                            
                            return
                        }
                    //  req('findGoodsCateById',{
                    //     access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
                    //     goodsCateId: this.Noderows.goodsCateId,
                    // }).then(data => {
                    //     console.log('查询商品分类详情',data)
                    // })
                        this.dialogTitle='修改菜单',
                        this.dialogVisible = true
                        this.dialogFormData = this.Noderows
                        console.log('修改')
                }
                },
                {
                    btnName:'删除',
                    type:'primary',
                    func:() => {
                        if(this.Noderows.length === 0) {
                            this.$message.info('请选择需要删除的数据')       
                            return
                        }
                        this.$confirm('此操作将永久删除该文件，是否继续').then(() =>{
                             let uls=this.Noderows.menuId
                            console.log(uls)
                    req('deleteMenuById',{
                            access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
                            menuIds:uls
                        }).then(data => {
                            if(data.code === 0) {
                            this.$message({
                                type:'sucesss',
                                message:data.msg
                            })
                        }else {
                            this.$message({
                                type:'error',
                                message:data.msg
                            })
                        }
                            console.log(data)
                            this.refreshInfo()
                        })                            
                        })
                    }
                },
                {
                    btnName:'刷新',
                    type:'primary',
                    func:() => {
                        console.log('刷新')
                        this.reload();  //解决页面刷新的方法
                    }
                },
            ],
            formRules: {
                cateName:[
                    {required:true,message:'请输入商品名称',trigger:'change'}
                ],
                cateLevel:[
                    {required:true,message:'请输入商品级别',trigger:'change'}
                ],
            },
        }
    },
    mounted () {
      this.refreshInfo()
    },
     methods: {
         refreshInfo () {
         req('listMenus',{
          access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
          }).then(data => {
          console.log('data',data)
          console.log('data',typeof( data))
          this.classifyData = data.data
          console.log('list', this.classifyData)
        })
       },
        handlerSelect(key,path) {
            if(key !==this.$route.path) {
            this.$router.replace({path: key})
            } 
        },
         handleNodeClick(data) {
         console.log(data);
         this.Noderows = data
         console.log('Noderows',this.Noderows)
         
          req('findMenuById',{
              access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
              menuId:this.Noderows.menuId
            }).then(data => {
              console.log('菜单详情',data)
              this.detailData = data.data
              console.log(this.detailData)
            })  
        },
       dialogCancel () {
          this.dialogVisible = false
          this.$refs.form.resetFields()     
          this.reload();  //解决页面刷新的方法  
        },
        updateChange(e) {
          this.$forceUpdate() 
        },
       dialogBeforeClose() {
          this.$refs.form.resetFields()
        },
        dialogConfirm() {
           this.$refs.form.validate((valid) => {
            if(this.dialogTitle === '新增菜单'){
                console.log('dialogFormData',this.dialogFormData)
              if(valid) {
                 req('addMenu',{
                    access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
                    ...this.dialogFormData,
                  }).then(data => {
                    console.log('data',data)
                    this.refreshInfo()
                    this.dialogVisible = false
                    this.$refs.form.resetFields()
                  })  
             }else {
                    this.$message.info(data.msg)
                    return false 
             }
              
          }

           if(this.dialogTitle === '修改菜单'){
             if(valid) {
               req('updateMenuById',{
            access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
            ...this.dialogFormData
           }).then(data => {
             console.log('data',data)
             this.refreshInfo()
             this.dialogVisible = false
            this.$refs.form.resetFields()
           })  
             }else{
                this.$message.info(data.msg)
                 return false 
             }         
          }
          
          })
        },
        // refresh () {
        //    req('listTreeGoodsCates',{
        //    access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
        //    }).then(data => {
        //      this.classifyData = data.data
        //      console.log('list', this.classifyData)
        //    })               
        // },
    }
}
</script>

<style lang="scss" scoped>
.el-container {
    .el-main {
        .header {
            height: 52px;
            line-height: 54px;
            border: 1px solid #ddd;
            width: 96%;
            padding-left:20px;
            
        }
        .el-form--inline {
            padding-left:20px;
            padding-top: 20px;
            height: 425px;
            border: 1px solid #ddd;
            width: 96%;
        }
    }
    .el-aside {
        .btn-class {
          width:314px;
          height:52px;
          border:1px solid #ddd;
          display:flex;
          align-items: center;
          justify-content: center;
        }
          .el-menu {
            border: 1px solid #ddd;
            .el-menu-item {
                 height: 40px;
                line-height: 40px;
             }
         }
        .el-tree {
                height: 425px;
                border: 1px solid #ddd;
                padding-top: 20px;
                padding-left: 30px;
        }      
      }
    }
.el-main {
    padding: 0px;
}
.el-form {
  .el-row {
    .el-col-12{
       /deep/ .el-form-item  {
        margin-bottom: 10px;
    }
    .cateLevel-tishi {
        color: red;
        font-size:10px;
        height:10px;
        line-height: 10px;
    }
    }
  
  }
}
</style>