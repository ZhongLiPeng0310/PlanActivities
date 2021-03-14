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
        <div class="header">分类详情</div>
        <el-form :Data="detailData" :inline="true">
            <el-form-item label="商品编号">
                <el-input v-model="detailData.goodsCateId"></el-input>
            </el-form-item>
            <el-form-item label="商品名称">
                <el-input v-model="detailData.cateName"></el-input>
            </el-form-item>
            <el-form-item label="商品层级">
                <el-input v-model="detailData.cateLevel"></el-input>
            </el-form-item>
            <el-form-item label="上一级编号">
                <el-input v-model="detailData.cateParent"></el-input>
            </el-form-item>
            <el-form-item label="记录版本号">
                <el-input v-model="detailData.version"></el-input>
            </el-form-item>
            <el-form-item label="备注信息">
                <el-input v-model="detailData.cateComment"></el-input>
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
                    <el-form-item label="商品名称" prop="cateName">
                        <el-input v-model="dialogFormData.cateName"></el-input>
                    </el-form-item> 
                </el-col>
                <el-col :span="12">
                    <el-form-item label="商品级别" prop="cateLevel" v-if="dialogTitle === '新增分类'">
                        <el-input v-model="dialogFormData.cateLevel" @input="updateChange($event)" placeholder="请输入级别，例如:1(第一级),2(第二级)"></el-input>
                        <!-- <div class="cateLevel-tishi">若该商品级别为最高级别，请点击下方刷新后再添加</div>  -->
                    </el-form-item> 
                </el-col>
                <el-col :span="12">
                    <el-form-item label="上一级编号" prop="cateParent">
                        <el-input  v-model="dialogFormData.cateParent" @input="updateChange($event)"></el-input>
                    </el-form-item> 
                </el-col>
                <el-col :span="12">
                    <el-form-item label="备注信息" prop="cateComment">
                        <el-input v-model="dialogFormData.cateComment"></el-input>
                    </el-form-item> 
                </el-col>
                <el-col :span="12" v-if="dialogTitle === '修改分类'">
                    <el-form-item label="版本号" prop="version">
                        <el-input v-model="dialogFormData.version"></el-input>
                    </el-form-item> 
                </el-col>
            </el-row>
        </el-form>
        </i-dialog>
</el-container>
</template>

<script>
import IDialog from '@/components/common/i-dialog.vue'
import req from '@/api/comm-classify-manage.js'
import axios from 'axios'
export default {
    name:'comm-classify-manage',
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
            label: 'cateName'
           },
            toolbar:[
                {
                    btnName:'新增',
                    type:'primary',
                    func:() => {
                        if(!this.Noderows.cateLevel){
                
                        }else{
                          this.dialogFormData.cateLevel = parseInt(this.Noderows.cateLevel)+1
                          this.dialogFormData.cateParent = this.Noderows.goodsCateId
                        }
                         this.dialogTitle='新增分类'
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
                        this.dialogTitle='修改分类',
                        this.dialogVisible = true
                        this.dialogFormData = Object.assign({},this.Noderows) 
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
                             let uls=this.selectionrows.goodsCateId
                            console.log( uls )
                    req('deleteGoodsCateById',{
                            access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
                            goodsCateId:uls
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
         req('listTreeGoodsCates',{
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
         this.Noderows = data
         
         req('findGoodsCateById',{
            access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
            goodsCateId:this.Noderows.goodsCateId
           }).then(data => {
             console.log('查询商品详情分类',data)
             this.detailData = data.data
             console.log(this.detailData)
           })  
      },
       dialogCancel () {
          this.dialogVisible = false
          this.$refs.form.resetFields()
          this.dialogFormData.cateName = ''
          this.dialogFormData.cateComment = ''
          this.updateChange()
        },
        updateChange(e) {
          this.$forceUpdate() 
        },
       dialogBeforeClose() {
          this.$refs.form.resetFields()
        },
        dialogConfirm() {
           this.$refs.form.validate((valid) => {
            if(this.dialogTitle === '新增分类'){
              if(valid) {
                 req('addGoodsCate',{
            access_token:JSON.parse(sessionStorage.getItem('userInfo')).access_token,
            ...this.dialogFormData,
            cateLevel:parseInt( this.dialogFormData.cateLevel)
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

           if(this.dialogTitle === '修改分类'){
             if(valid) {
               req('updateGoodsCateById',{
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