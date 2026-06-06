<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center">
          <span>我的预约</span>
          <el-button type="primary" @click="$router.push('/booking/create')">
            <el-icon><Plus /></el-icon>
            新建预约
          </el-button>
        </div>
      </template>

      <el-table :data="bookingList" v-loading="loading" style="width: 100%">
        <el-table-column prop="id" label="预约号" width="80" />
        <el-table-column prop="bookingDate" label="预约日期" width="120" />
        <el-table-column prop="timeSlot" label="时间段" width="140" />
        <el-table-column prop="vehicleId" label="车辆ID" width="80" />
        <el-table-column prop="workstationId" label="工位ID" width="80" />
        <el-table-column label="状态" width="100">
          <template #default="scope">
            <el-tag :type="statusType(scope.row.status)">{{ statusText(scope.row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" type="primary" @click="viewDetail(scope.row)">详情</el-button>
            <el-button
              v-if="scope.row.status === 0"
              size="small"
              type="danger"
              @click="handleCancel(scope.row)"
            >取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listBookingsByCustomer, cancelBooking } from '@/api/booking'

const router = useRouter()
const loading = ref(false)
const bookingList = ref([])
const customerId = 1

const statusText = (status) => {
  const map = { 0: '待确认', 1: '已确认', 2: '已完成', 3: '已取消' }
  return map[status] || '未知'
}

const statusType = (status) => {
  const map = { 0: 'warning', 1: 'primary', 2: 'success', 3: 'info' }
  return map[status] || ''
}

const loadData = async () => {
  loading.value = true
  try {
    bookingList.value = await listBookingsByCustomer(customerId)
  } finally {
    loading.value = false
  }
}

const viewDetail = (row) => {
  router.push(`/booking/detail/${row.id}`)
}

const handleCancel = (row) => {
  ElMessageBox.prompt('请输入取消原因', '取消预约', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /.+/,
    inputErrorMessage: '请输入取消原因'
  }).then(async ({ value }) => {
    await cancelBooking(row.id, value)
    ElMessage.success('取消成功')
    loadData()
  }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
