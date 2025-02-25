<template>
    <div class="announcement-manage">
      <el-row>
        <el-col :span="24">
          <el-card>
            <template #header>
              <span>公告管理</span>
              <el-button type="primary" size="small" @click="handleAdd" style="float: right;">
                <el-icon><Plus /></el-icon>
                新增公告
              </el-button>
            </template>
            <el-table :data="announcements" style="width: 100%">
              <el-table-column prop="title" label="标题" />
              <el-table-column prop="date" label="发布日期" />
              <el-table-column label="操作" width="180">
                <template #default="{ row }">
                  <el-button size="small" @click="handleEdit(row)">编辑</el-button>
                  <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
      </el-row>
  
      <!-- 公告编辑对话框 -->
      <el-dialog v-model="dialogVisible" title="公告信息">
        <el-form :model="currentAnnouncement" ref="formRef">
          <el-form-item label="标题" prop="title">
            <el-input v-model="currentAnnouncement.title" />
          </el-form-item>
          <el-form-item label="内容" prop="content">
            <el-input type="textarea" v-model="currentAnnouncement.content" />
          </el-form-item>
          <el-form-item label="发布日期" prop="date">
            <el-date-picker v-model="currentAnnouncement.date" type="date" placeholder="选择日期" />
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
  
  const announcements = ref([
    { id: 1, title: '公告标题 1', content: '这里是公告内容 1...', date: '2024-05-15' },
    { id: 2, title: '公告标题 2', content: '这里是公告内容 2...', date: '2024-05-14' },
    // 更多公告...
  ]);
  
  const dialogVisible = ref(false);
  const currentAnnouncement = ref({ title: '', content: '', date: '' });
  const formRef = ref(null);
  
  const handleAdd = () => {
    currentAnnouncement.value = { title: '', content: '', date: '' };
    dialogVisible.value = true;
  };
  
  const handleEdit = (announcement) => {
    currentAnnouncement.value = { ...announcement };
    dialogVisible.value = true;
  };
  
  const handleDelete = (announcement) => {
    const index = announcements.value.findIndex((item) => item.id === announcement.id);
    if (index !== -1) {
      announcements.value.splice(index, 1);
      ElMessage.success('删除成功');
    }
  };
  
  const handleSubmit = () => {
    if (!currentAnnouncement.value.title || !currentAnnouncement.value.content || !currentAnnouncement.value.date) {
      ElMessage.error('请填写完整的公告信息');
      return;
    }
    if (currentAnnouncement.value.id) {
      // 编辑公告
      const index = announcements.value.findIndex((item) => item.id === currentAnnouncement.value.id);
      if (index !== -1) {
        announcements.value[index] = { ...currentAnnouncement.value };
        ElMessage.success('编辑成功');
      }
    } else {
      // 新增公告
      const newId = announcements.value.length ? Math.max(...announcements.value.map((item) => item.id)) + 1 : 1;
      announcements.value.push({ ...currentAnnouncement.value, id: newId });
      ElMessage.success('新增成功');
    }
    dialogVisible.value = false;
  };
  </script>
  
  <style scoped>
  .announcement-manage {
    padding: 20px;
  }
  </style>
  