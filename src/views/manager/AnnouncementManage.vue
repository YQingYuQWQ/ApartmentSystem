<template>
    <div class="announcement-manage" v-loading.fullscreen.lock="loading">
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
            <el-table :data="announcements" style="width: 100%" :fit="true" align="center">
              <el-table-column prop="title" label="标题" width="200" />
              <el-table-column prop="content" label="内容" width="200"/>
              <el-table-column prop="created_at" label="发布日期" width="200"/>
              <el-table-column label="操作" width="200">
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
  import { descriptionItemProps, ElMessage } from 'element-plus';
  import api from '@/config/axios';
  import { Plus } from '@element-plus/icons-vue';
  
  const announcements = ref([]);
  
  const dialogVisible = ref(false);
  const currentAnnouncement = ref({ title: '', content: '', date: '' });
  const formRef = ref(null);
  const loading = ref(true)
  
  const handleAdd = () => {
    currentAnnouncement.value = { title: '', content: '', date: '' };
    dialogVisible.value = true;
  };
  
  const handleEdit = (announcement) => {
    currentAnnouncement.value = { ...announcement };
    dialogVisible.value = true;
  };
  
  const handleDelete = async (announcement) => {
    currentAnnouncement.value = { ...announcement };
    const res = await api.post('announcement/deleteAnnouncementById', {
      id: currentAnnouncement.value.id
    })
    if(res.data.code != 0)
      {
        ElMessage.error(res.data.message)
        return
      }
      ElMessage.success('删除成功')
      window.location.reload();
  };
  
  const handleSubmit = async () => {
    if (!currentAnnouncement.value.title || !currentAnnouncement.value.content) {
      ElMessage.error('请填写完整的公告信息');
      return;
    }
    if (currentAnnouncement.value.id) {
      const res = await api.post('announcement/updateAnnouncementById', {
        id: currentAnnouncement.value.id,
        title: currentAnnouncement.value.title,
        content: currentAnnouncement.value.content
      })
      if(res.data.code != 0)
      {
        ElMessage.error(res.data.message)
        return
      }
      ElMessage.success('修改成功')
      window.location.reload();
    } else {
      const res = await api.post('announcement/insertAnnouncement', {
        title: currentAnnouncement.value.title,
        content: currentAnnouncement.value.content
      })
      if(res.data.code != 0)
      {
        ElMessage.error(res.data.message)
        return
      }
      ElMessage.success('新增成功')
      window.location.reload();
    }
    dialogVisible.value = false;
  };

  const fetchAnnouncements = async () => {
    const AnnList = await api.post('announcement/getAllAnnouncement')
    if(AnnList.data.code != 0){
      ElMessage.error('公告获取错误' + error)
      return 
    }
    announcements.value = AnnList.data.data
    loading.value = false
  }

onMounted(() => {
  fetchAnnouncements()
})
  </script>
  
  <style scoped>
  .announcement-manage {
    padding: 20px;
  }
  </style>