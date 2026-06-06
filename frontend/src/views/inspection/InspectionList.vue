<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center">
          <span>车检报告列表</span>
          <div style="display: flex; gap: 10px; align-items: center">
            <span>客户ID：</span>
            <el-input-number v-model="customerId" :min="1" style="width: 120px" />
            <el-button type="primary" @click="loadData">
              <el-icon><Search /></el-icon>
              查询
            </el-button>
          </div>
        </div>
      </template>

      <el-table :data="reportList" v-loading="loading" style="width: 100%">
        <el-table-column prop="id" label="报告编号" width="100" />
        <el-table-column prop="vehicleId" label="车辆ID" width="100" />
        <el-table-column prop="technicianId" label="技师ID" width="100" />
        <el-table-column prop="inspectionTime" label="车检时间" width="200" />
        <el-table-column label="总体状态" width="120">
          <template #default="scope">
            <el-tag :type="statusType(scope.row.overallStatus)">{{ statusText(scope.row.overallStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="140">
          <template #default="scope">
            <el-button size="small" type="primary" @click="viewDetail(scope.row)">
              查看详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { listByCustomer } from '@/api/vehicle'
import { listInspectionsByVehicle } from '@/api/inspection'

const router = useRouter()
const loading = ref(false)
const reportList = ref([])
const customerId = ref(1)

const statusText = (status) => {
  const map = { 1: '正常', 2: '轻微问题', 3: '需维修' }
  return map[status] || '未知'
}

const statusType = (status) => {
  const map = { 1: 'success', 2: 'warning', 3: 'danger' }
  return map[status] || 'info'
}

const loadData = async () => {
  loading.value = true
  try {
    const vehicles = await listByCustomer(customerId.value)
    const allReports = []
    for (const vehicle of vehicles) {
      try {
        const reports = await listInspectionsByVehicle(vehicle.id)
        allReports.push(...reports)
      } catch (e) {
        console.error(`Failed to load inspections for vehicle ${vehicle.id}`, e)
      }
    }
    reportList.value = allReports
  } finally {
    loading.value = false
  }
}

const viewDetail = (row) => {
  router.push(`/inspection/report/${row.id}`)
}

onMounted(() => {
  loadData()
})
</script>
