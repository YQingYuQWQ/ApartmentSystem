<template>
    <div class="contract-manage">
      <!-- 操作栏 -->
      <div class="operate-bar">
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          新增合同
        </el-button>
        <el-input
          v-model="searchKey"
          placeholder="搜索合同编号"
          style="width: 240px; margin-left: auto;"
          clearable
        >
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
      </div>
  
      <!-- 合同列表 -->
      <el-table :data="filteredContractList" style="width: 100%">
        <el-table-column prop="contract_number" label="合同编号" />
        <el-table-column prop="tenant_name" label="租户姓名" />
        <el-table-column prop="start_date" label="开始日期" />
        <el-table-column prop="end_date" label="结束日期" />
        <el-table-column label="状态">
          <template #default="{row}">
            <el-tag :type="statusType[row.status]">
              {{ statusText[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{row}">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          :total="filteredContractList.length"
          :page-size="10"
          layout="total, prev, pager, next"
        />
      </div>
  
      <!-- 合同编辑对话框 -->
      <el-dialog v-model="dialogVisible" title="合同信息">
        <el-form :model="currentContract" ref="formRef">
          <el-form-item label="合同编号" prop="contract_number" :rules="[{ required: true, message: '请输入合同编号', trigger: 'blur' }]">
            <el-input v-model="currentContract.contract_number" />
          </el-form-item>
          <el-form-item label="租户姓名" prop="tenant_name" :rules="[{ required: true, message: '请输入租户姓名', trigger: 'blur' }]">
            <el-input v-model="currentContract.tenant_name" />
          </el-form-item>
          <el-form-item label="开始日期" prop="start_date" :rules="[{ required: true, message: '请选择开始日期', trigger: 'change' }]">
            <el-date-picker v-model="currentContract.start_date" type="date" placeholder="选择开始日期" />
          </el-form-item>
          <el-form-item label="结束日期" prop="end_date" :rules="[{ required: true, message: '请选择结束日期', trigger: 'change' }]">
            <el-date-picker v-model="currentContract.end_date" type="date" placeholder="选择结束日期" />
          </el-form-item>
          <el-form-item label="状态" prop="status" :rules="[{ required: true, message: '请选择状态', trigger: 'change' }]">
            <el-select v-model="currentContract.status" placeholder="请选择状态">
              <el-option label="进行中" value="active" />
              <el-option label="已结束" value="expired" />
              <el-option label="已取消" value="canceled" />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">提交</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue'
  
  // 假数据
  const contractList = ref([
    { contract_number: 'C001', tenant_name: '张三', start_date: '2023-01-01', end_date: '2023-12-31', status: 'active' },
    { contract_number: 'C002', tenant_name: '李四', start_date: '2023-02-01', end_date: '2023-11-30', status: 'expired' },
    { contract_number: 'C003', tenant_name: '王五', start_date: '2023-05-01', end_date: '2024-05-01', status: 'active' }
  ])
  
  // 合同状态类型和状态文本映射
  const statusType = {
    active: 'success',
    expired: 'danger',
    canceled: 'warning'
  }
  
  const statusText = {
    active: '进行中',
    expired: '已结束',
    canceled: '已取消'
  }
  
  const searchKey = ref('') // 搜索关键字
  const dialogVisible = ref(false) // 控制编辑对话框显示与否
  const currentContract = ref(null) // 当前编辑的合同信息
  
  // 添加合同操作
  const handleAdd = () => {
    currentContract.value = { contract_number: '', tenant_name: '', start_date: '', end_date: '', status: 'active' } // 重置为默认值
    dialogVisible.value = true
  }
  
  // 编辑合同操作
  const handleEdit = (contract) => {
    currentContract.value = { ...contract } // 复制合同信息
    dialogVisible.value = true
  }
  
  // 删除合同操作
  const handleDelete = (contract) => {
    const index = contractList.value.findIndex(item => item.contract_number === contract.contract_number)
    if (index !== -1) {
      contractList.value.splice(index, 1) // 删除该合同
    }
  }
  
  // 提交表单操作
  const handleSubmit = () => {
    const index = contractList.value.findIndex(item => item.contract_number === currentContract.value.contract_number)
    if (index === -1) {
      // 如果是新增合同
      contractList.value.push({ ...currentContract.value })
    } else {
      // 如果是编辑合同
      contractList.value[index] = { ...currentContract.value }
    }
    dialogVisible.value = false // 提交后关闭对话框
  }
  
  // 搜索合同
  const filteredContractList = computed(() => {
    return contractList.value.filter(contract => contract.contract_number.includes(searchKey.value))
  })
  </script>
  
  <style scoped>
  .contract-manage {
    padding: 24px;
    background-color: #fff;
  
    .operate-bar {
      display: flex;
      justify-content: flex-start;
      margin-bottom: 20px;
    }
  
    .pagination {
      margin-top: 20px;
    }
  }
  </style>
  