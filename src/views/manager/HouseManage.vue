<template>
    <div class="house-manage">
        <!-- 操作栏部分 -->
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

        <!-- 房屋列表表格 -->
        <el-table :data="filteredHouseList" style="width: 100%" v-loading="listLoading">
            <el-table-column type="id" label="房屋ID" width="60" />
            <el-table-column prop="house_number" label="房屋编号" />
            <el-table-column prop="building_name" label="楼栋名称" />
            <el-table-column prop="floor" label="楼层" />
            <el-table-column prop="owner_id" label="租户ID" sortable>
                <template #default="{ row }">
                    <span>{{ row.owner_id ? row.owner_id : '暂无' }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态">
                <template #default="{ row }">
                    <el-tag :type="statusType[row.status]">
                        {{ statusText[row.status] }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="price" label="租金" />
            <el-table-column prop="area" label="面积" />
            <el-table-column prop="water_fee" label="剩余水费" />
            <el-table-column prop="power_fee" label="剩余电费" />
            <el-table-column prop="deposit" label="押金" />
            <el-table-column prop="created_at" label="创建时间" />
            <el-table-column prop="updated_at" label="更新时间" />
            <el-table-column label="操作" width="180">
                <template #default="{ row }">
                    <el-button size="small" @click="handleEdit(row)">编辑</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
                    <el-button size="small" type="primary" @click="createContract(row)"
                        v-if="row.owner_id && row.status === 'waiting'">
                        创建合同
                    </el-button>
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
                <el-form-item label="房间ID">
                    <el-input v-model="currentHouse.id" />
                </el-form-item>
                <el-form-item label="房间号">
                    <el-input v-model="currentHouse.house_number" />
                </el-form-item>
                <el-form-item label="租户id">
                    <el-input v-model="currentHouse.owner_id" />
                </el-form-item>
                <el-form-item label="楼栋名称">
                    <el-input v-model="currentHouse.building_name" />
                </el-form-item>
                <el-form-item label="楼层">
                    <el-input-number v-model="currentHouse.floor" :min="1" />
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="currentHouse.status" placeholder="请选择状态">
                        <el-option label="空闲" value="vacant" />
                        <el-option label="已预定" value="booked" />
                        <el-option label="等待中" value="waiting" />
                        <el-option label="已出租" value="occupied" />
                        <el-option label="维护中" value="under_maintenance" />
                    </el-select>
                </el-form-item>
                <el-form-item label="租金">
                    <el-input-number v-model="currentHouse.price" :min="0" />
                </el-form-item>
                <el-form-item label="面积">
                    <el-input-number v-model="currentHouse.area" :min="0" />
                </el-form-item>
                <el-form-item label="水费">
                    <el-input-number v-model="currentHouse.water_fee" :min="0" />
                </el-form-item>
                <el-form-item label="电费">
                    <el-input-number v-model="currentHouse.power_fee" :min="0" />
                </el-form-item>
                <el-form-item label="押金">
                    <el-input-number v-model="currentHouse.deposit" :min="0" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleSubmit">提交</el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 创建合同对话框 -->
        <el-dialog v-model="contractDialogVisible" title="创建合同" width="50%">
            <el-form :model="contractFormData" ref="contractForm" label-width="100px">
                <el-form-item label="房屋ID">
                    <el-input v-model="contractFormData.house_id" readonly />
                </el-form-item>
                <el-form-item label="房间号">
                    <el-input v-model="contractFormData.house_number" readonly />
                </el-form-item>
                <el-form-item label="用户ID">
                    <el-input v-model="contractFormData.owner_id" readonly />
                </el-form-item>
                <el-form-item label="月租">
                    <el-input-number v-model="contractFormData.price" :min="0" readonly />
                </el-form-item>
                <el-form-item label="押金">
                    <el-input-number v-model="contractFormData.deposit" :min="0" readonly />
                </el-form-item>
                <el-form-item label="合同起始时间">
                    <el-date-picker v-model="contractFormData.start_date" type="date" placeholder="选择起始时间" />
                </el-form-item>
                <el-form-item label="合同结束时间">
                    <el-date-picker v-model="contractFormData.end_date" type="date" placeholder="选择结束时间" />
                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button type="primary" @click="submitContract">提交合同</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import api from '@/config/axios'
import dayjs from 'dayjs'

const listLoading = ref(false)
const houseList = ref([])

const contractDialogVisible = ref(false)
const contractFormData = ref({})

const dialogVisible = ref(false)  // 用于房屋编辑对话框显示控制
const currentHouse = ref(null)  // 当前正在编辑的房屋数据

const statusType = {
    vacant: 'success',
    occupied: 'danger',
    booked: 'warning',
    waiting: 'info',
    under_maintenance: 'primary'
}

const statusText = {
    vacant: '空闲',
    occupied: '已出租',
    booked: '已预定',
    waiting: '等待中',
    under_maintenance: '维护中'
}

const searchKey = ref('')

// 创建合同
const createContract = (house) => {
    contractFormData.value = {
        house_id: house.id,
        house_number: house.house_number,
        owner_id: house.owner_id,
        price: house.price,
        deposit: house.deposit,
        start_date: '',
        end_date: ''
    }
    console.log(contractFormData.value)
    contractDialogVisible.value = true  // 显示合同创建表单
}

// 提交合同
const submitContract = async () => {
    try {
        console.log('提交合同:', contractFormData.value)
        const res = await api.post('leaseContract/insertLeaseContract', {
            ...contractFormData.value,
            user_id: contractFormData.value.owner_id,
            start_date: dayjs(contractFormData.value.start_date).format('YYYY-MM-DD'),
            end_date: dayjs(contractFormData.value.end_date).format('YYYY-MM-DD')
        })
        if (res.data.code !== 0) {
            ElMessage.error('合同创建失败' + res.data.message)
            return
        }
        ElMessage.success(`合同为房屋编号 ${contractFormData.value.house_number} 创建成功！`)
        contractDialogVisible.value = false  // 提交后关闭弹窗
        window.location.reload();
    } catch (e) {
        ElMessage.error('合同创建失败')
    }
}

// 添加房屋
const handleAdd = () => {
    currentHouse.value = { house_number: '', status: 'vacant', price: 0 }
    dialogVisible.value = true
}

// 编辑房屋
const handleEdit = (house) => {
    currentHouse.value = { ...house }
    dialogVisible.value = true
}

// 删除房屋
const handleDelete = (house) => {
    const index = houseList.value.findIndex(item => item.house_number === house.house_number)
    if (index !== -1) {
        houseList.value.splice(index, 1)
    }
}

// 提交房屋表单
const handleSubmit = () => {
    const index = houseList.value.findIndex(item => item.house_number === currentHouse.value.house_number)
    if (index === -1) {
        houseList.value.push({ ...currentHouse.value })
    } else {
        houseList.value[index] = { ...currentHouse.value }
    }
    dialogVisible.value = false
}

// 过滤房屋列表
const filteredHouseList = computed(() => {
    return houseList.value.filter(house => house.house_number.includes(searchKey.value))
})

// 获取房屋列表数据
const fetchData = async () => {
    listLoading.value = true
    try {
        const res = await api.post('house/getHouseList')
        if (res.data.code === 0) {
            houseList.value = res.data.data
        }
    } catch (error) {
        ElMessage.error('获取数据失败')
    } finally {
        listLoading.value = false
    }
}

onMounted(() => {
    fetchData()
})
</script>

<style lang="scss" scoped>
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

.el-tag {
    font-size: 12px;
    padding: 3px 8px;
}

.resizable-component {
    contain: strict;
}
</style>