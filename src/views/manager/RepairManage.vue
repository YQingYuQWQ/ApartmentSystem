<template>
    <div class="repair-manage">
      <div class="operate-bar">
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          新增报修
        </el-button>
        <el-input
          v-model="searchKey"
          placeholder="搜索报修单号"
          style="width: 240px; margin-left: auto;"
          clearable
        >
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
      </div>
  
      <el-table :data="repairList" style="width: 100%">
        <el-table-column prop="repair_number" label="报修单号" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">
              {{ statusText[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="报修日期" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          :total="100"
          :page-size="10"
          layout="total, prev, pager, next"
        />
      </div>
  
      <!-- 报修编辑对话框 -->
      <el-dialog v-model="dialogVisible" title="报修信息">
        <el-form :model="currentRepair" ref="formRef">
          <el-form-item label="报修单号" prop="repair_number">
            <el-input v-model="currentRepair.repair_number" disabled />
          </el-form-item>
          <el-form-item label="问题描述" prop="description">
            <el-input type="textarea" v-model="currentRepair.description" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="currentRepair.status" placeholder="选择状态">
              <el-option label="待处理" value="pending" />
              <el-option label="处理中" value="in_progress" />
              <el-option label="已完成" value="completed" />
            </el-select>
          </el-form-item>
          <el-form-item label="处理日期" prop="date">
            <el-date-picker v-model="currentRepair.date" type="date" placeholder="选择日期" />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">提交</el-button>
        </template>
      </el-dialog>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { ElMessage } from 'element-plus';
  
  const repairList = ref([
    { repair_number: 'R001', description: '水管漏水', status: 'pending', date: '2024-05-15' },
    { repair_number: 'R002', description: '电梯故障', status: 'in_progress', date: '2024-05-14' },
    { repair_number: 'R003', description: '空调不制冷', status: 'completed', date: '2024-05-13' },
    // 更多报修单...
  ]);
  
  const dialogVisible = ref(false);
  const currentRepair = ref({ repair_number: '', description: '', status: '', date: '' });
  const formRef = ref(null);
  const searchKey = ref('');
  
  const statusType = {
    pending: 'warning',
    in_progress: 'info',
    completed: 'success',
  };
  
  const statusText = {
    pending: '待处理',
    in_progress: '处理中',
    completed: '已完成',
  };
  
  const handleAdd = () => {
    currentRepair.value = { repair_number: '', description: '', status: '', date: '' };
    dialogVisible.value = true;
  };
  
  const handleEdit = (repair) => {
    currentRepair.value = { ...repair };
    dialogVisible.value = true;
  };
  
  const handleDelete = (repair) => {
    const index = repairList.value.findIndex((item) => item.repair_number === repair.repair_number);
    if (index !== -1) {
      repairList.value.splice(index, 1);
      ElMessage.success('删除成功');
    }
  };
  
  const handleSubmit = () => {
    if (!currentRepair.value.description || !currentRepair.value.status || !currentRepair.value.date) {
      ElMessage.error('请填写完整的报修信息');
      return;
    }
    if (currentRepair.value.repair_number) {
      // 编辑报修
      const index = repairList.value.findIndex((item) => item.repair_number === currentRepair.value.repair_number);
      if (index !== -1) {
        repairList.value[index] = { ...currentRepair.value };
        ElMessage.success('编辑成功');
      }
    } else {
      // 新增报修
      const newRepair = { ...currentRepair.value, repair_number: `R${(repairList.value.length + 1).toString().padStart(3, '0')}` };
      repairList.value.push(newRepair);
      ElMessage.success('新增成功');
    }
    dialogVisible.value = false;
  };
  </script>
  
  <style scoped>
  .repair-manage {
    padding: 20px;
  }
  
  .pagination {
    margin-top: 20px;
  }
  </style>
  