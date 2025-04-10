<template>
  <div class="repair-manage">
    <div class="operate-bar">
      <el-input v-model="searchKey" placeholder="搜索报修单号" style="width: 240px; margin-left: auto;" clearable>
        <template #append>
          <el-button :icon="Search" />
        </template>
      </el-input>
    </div>

    <el-table :data="repairList" style="width: 100%" align="center">
      <el-table-column prop="id" label="报修单号" />
      <el-table-column prop="house_id" label="房间ID" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="statusType[row.status]">
            {{ statusText[row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="created_at" label="报修日期" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row)" type="primary" v-if="row.status == 'pending'">接受</el-button>
          <el-button size="small" @click="handleSuccess(row)" type="success"
            v-if="row.status == 'in_progress'">完成</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination :total="100" :page-size="10" layout="total, prev, pager, next" />
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
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { Plus, Search, } from '@element-plus/icons-vue'
import api from '@/config/axios';

const repairList = ref([]);

const dialogVisible = ref(false);
const currentRepair = ref({ id: '', house_id: '', description: '', status: '', created_at: '' });
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

const handleSuccess = async (row) => {
  const res = await api.post('repair/updateRepairStatusById', { 
    ...row,
    status: 'completed'
   })
  if (res.data.code != 0) {
    ElMessage.error('状态更改错误：' + res.data.message)
    return
  }
  ElMessage.success('更新成功！')
  fetchRepairs()
}

const fetchRepairs = async () => {
  const res = await api.get('repair/getAllRepairs')
  if (res.data.code != 0) {
    ElMessage.error("获取保修信息错误: " + res.data.message)
    return
  }
  repairList.value = res.data.data
}

onMounted(() => {
  fetchRepairs()
})
</script>

<style scoped>
.repair-manage {
  padding: 20px;
}

.pagination {
  margin-top: 20px;
}
</style>