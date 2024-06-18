<script lang="ts" setup>
import {
  Check,
  Delete,
} from '@element-plus/icons-vue'
let tableData = ref([]);

let filterData=ref([]);

let displayData = ref([]);


function indexMethod(index) {
  return (currentPage.value - 1) * pageSize.value + index + 1;
}

import { ref } from 'vue'

const currentPage = ref(1)
const pageSize = ref(10)
const small = ref(false)
const background = ref(false)
const disabled = ref(false)

const handleSizeChange = (val: number) => {
  updateDisplayData();
}
const handleCurrentChange = (val: number) => {
  updateDisplayData();
}

function updateDisplayData() {
  const startIndex = (currentPage.value - 1) * pageSize.value;
  const endIndex = startIndex + pageSize.value;
  displayData.value = filterData.value.slice(startIndex, endIndex);
}

function filterDataQuery(){
  filterData.value =tableData.value.filter(item=>{
    if(formInline.uid && formInline.uid!==item.uid){
      return false
    }
    if(formInline.type){
      if(formInline.type=='0' && (item.type=='00'||item.type=='10')){
        return true
      }
      else if(formInline.type=='1' && (item.type=='01'||item.type=='11')){
        return true
      }
      else if(formInline.type=='2' && (item.type=='02'||item.type=='12')){
        return true
      }
      else{
        return false
      }
    }
    return true
  })
  updateDisplayData();
}
import {getAllRequestService,acceptRequestService,refuseRequestService} from "@/api/process.js"
const processButtonClick=async(rid,type)=>{
  if(type===0){
    let response=await acceptRequestService({rid:rid})
    
  }
  else{
    let response= await refuseRequestService({rid:rid})
  }
  tableData.value=tableData.value.filter(item=> item.requestId!==rid)
  filterDataQuery()
}


const getRequestData=async ()=> {
  let response =await getAllRequestService();
  console.log(response.data)
  tableData.value = response.data;
  console.log(tableData.value)
  filterDataQuery();
  updateDisplayData();
}


import { reactive } from 'vue'

const formInline = reactive({
  uid: "",
  type: ""
})

const onSubmit = () => {
  console.log('submit!')
}
//获取申请信息
getRequestData()


</script>


<template>
  <div class="container">
    <el-card class="card">
      <div class="card-header" style="font-weight: bolder;">
        <span>申请处理</span>
      </div>
      <div style="margin-top: 20px;">
        <hr>
      </div>
      <el-form :inline="true" :model="formInline" class="form-inline">
        <el-form-item label="申请人uid">
          <el-input v-model="formInline.uid" placeholder="请输入申请人uid" clearable />
        </el-form-item>
        <el-form-item label="申请类型">
          <el-select v-model="formInline.type" placeholder="请选择" clearable>
            <el-option label="删除" value="0" />
            <el-option label="增加" value="1" />
            <el-option label="修改" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="filterDataQuery">查询</el-button>
        </el-form-item>
      </el-form>

      <div class="table-pagination">
        <el-table :data="displayData" stripe border style="width: 100%">
          <el-table-column type="index" :index="indexMethod" label="序号" width="180" />
          <el-table-column prop="fromName" label="申请人" width="180" />
          <el-table-column prop="fromUid" label="UID" />
          <el-table-column label="类型">
            <template #default="scope">
              <span v-if="scope.row.type ==='0'">删除</span>
              <span v-else-if="scope.row.type ==='1'">增加</span>
              <span v-else>修改</span>
            </template>
          </el-table-column>
          <el-table-column prop="description" label="内容" />
          <el-table-column label="操作" width="180" align="center" #default="scope">
            <el-button type="success" :icon="Check" circle @click="processButtonClick(scope.row.rid,0)"/>
            <el-button type="danger" :icon="Delete" circle @click="processButtonClick(scope.row.rid,1)"/>
          </el-table-column>
        </el-table>

        <el-pagination class="el-p" v-model:current-page="currentPage" v-model:page-size="pageSize"
          :page-sizes="[5, 10, 15, 20]" :small="small" :disabled="disabled" :background="background"
          layout="jumper,total, sizes, prev, pager, next " :total="displayData.length" @size-change="handleSizeChange"
          @current-change="handleCurrentChange" />
      </div>
    </el-card>


  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
}

.card {
  margin-bottom: 20px;
}

.table-pagination {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.el-p {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.form-inline .el-input {
  --el-input-width: 220px;
}

.form-inline .el-select {
  --el-select-width: 220px;
}
</style>