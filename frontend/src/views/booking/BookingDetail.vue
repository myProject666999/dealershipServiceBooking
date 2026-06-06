<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>预约详情</span>
        <el-button @click="$router.back()">返回</el-button>
      </div>
    </template>

    <el-descriptions :column="2" border v-loading="loading">
      <el-descriptions-item label="预约号">{{ detail?.id }}</el-descriptions-item>
      <el-descriptions-item label="客户ID">{{ detail?.customerId }}</el-descriptions-item>
      <el-descriptions-item label="车辆ID">{{ detail?.vehicleId }}</el-descriptions-item>
      <el-descriptions-item label="工位ID">{{ detail?.workstationId }}</el-descriptions-item>
      <el-descriptions-item label="预约日期">{{ detail?.bookingDate }}</el-descriptions-item>
      <el-descriptions-item label="时间段">{{ detail?.timeSlot }}</el-descriptions-item>
      <el-descriptions-item label="状态">
        <el-tag>{{ statusText(detail?.status) }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="取消原因">{{ detail?.cancelReason || '-' }}</el-descriptions-item>
      <el-descriptions-item label="备注" :span="2">{{ detail?.remark || '-' }}</el-descriptions-item>
      <el-descriptions-item label="创建时间" :span="2">{{ detail?.createTime }}</el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getBookingById } from '@/api/booking'

const route = useRoute()
const loading = ref(false)
const detail = ref(null)

const statusText = (status) => {
  const map = { 0: '待确认', 1: '已确认', 2: '已完成', 3: '已取消' }
  return map[status] || '未知'
}

const loadData = async () => {
  loading.value = true
  try {
    detail.value = await getBookingById(route.params.id)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>
