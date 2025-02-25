<template>
    <div class="house-manage">
        <div class="operate-bar">
            <el-button type="primary" @click="handleAdd">
                <el-icon>
                    <Plus />
                </el-icon>
                新增房屋
            </el-button>
            <el-input v-model="searchKey" placeholder="搜索房屋编号" style="width: 240px; margin-left: auto;" clearable>
                <template #append>
                    <el-button :icon="Search" />
                </template>
            </el-input>
        </div>

        <el-table :data="filteredHouseList" style="width: 100%">
            <el-table-column prop="house_number" label="房屋编号" />
            <el-table-column prop="status" label="状态">
                <template #default="{ row }">
                    <el-tag :type="statusType[row.status]">
                        {{ statusText[row.status] }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="price" label="租金" />
            <el-table-column label="操作" width="180">
                <template #default="{ row }">
                    <el-button size="small" @click="handleEdit(row)">编辑</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="pagination">
            <el-pagination :total="houseList.length" :page-size="10" layout="total, prev, pager, next" />
        </div>

        <!-- 房屋编辑对话框 -->
        <el-dialog v-model="dialogVisible" title="房屋信息">
            <el-form :model="currentHouse" ref="form" label-width="100px">
                <el-form-item label="房屋编号">
                    <el-input v-model="currentHouse.house_number" />
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="currentHouse.status" placeholder="请选择状态">
                        <el-option label="空闲" value="available" />
                        <el-option label="已出租" value="occupied" />
                    </el-select>
                </el-form-item>
                <el-form-item label="租金">
                    <el-input-number v-model="currentHouse.price" :min="0" />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">提交</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script setup>
import { ref, computed } from 'vue'

const houseList = ref([
    { house_number: '001', status: 'available', price: 2000 },
    { house_number: '002', status: 'occupied', price: 3000 },
    { house_number: '003', status: 'available', price: 2500 }
])

const statusType = {
    available: 'success',
    occupied: 'danger'
}

const statusText = {
    available: '空闲',
    occupied: '已出租'
}

const searchKey = ref('') 
const dialogVisible = ref(false) 
const currentHouse = ref(null) 


const handleAdd = () => {
    currentHouse.value = { house_number: '', status: 'available', price: 0 } 
    dialogVisible.value = true
}


const handleEdit = (house) => {
    currentHouse.value = { ...house } 
    dialogVisible.value = true
}


const handleDelete = (house) => {
    const index = houseList.value.findIndex(item => item.house_number === house.house_number)
    if (index !== -1) {
        houseList.value.splice(index, 1) 
    }
}

const handleSubmit = () => {
    const index = houseList.value.findIndex(item => item.house_number === currentHouse.value.house_number)
    if (index === -1) {
        houseList.value.push({ ...currentHouse.value })
    } else {
        houseList.value[index] = { ...currentHouse.value }
    }
    dialogVisible.value = false
}

const filteredHouseList = computed(() => {
    return houseList.value.filter(house => house.house_number.includes(searchKey.value))
})
</script>
<style scoped>
.house-manage {
    padding: 20px;
    background-color: #f5f5f5;
}

.operate-bar {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
}

.pagination {
    margin-top: 20px;
    text-align: center;
}

.el-table {
    margin-bottom: 20px;
}

/* 自定义一些样式 */
.el-table-column {
    padding: 10px;
}

.el-input {
    width: 240px;
}

.el-dialog {
    width: 60%;
    max-width: 800px;
}

/* 如果你需要根据自己的需求进行样式调整 */
.el-tag {
    font-size: 12px;
    padding: 3px 8px;
}
</style>