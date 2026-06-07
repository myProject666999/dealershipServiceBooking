<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 10px">
          <span>车检报告列表</span>
          <div style="display: flex; gap: 10px; align-items: center; flex-wrap: wrap">
            <span>客户ID：</span>
            <el-input-number v-model="customerId" :min="1" style="width: 120px" />
            <span>车辆：</span>
            <el-select v-model="filterVehicleId" placeholder="全部车辆" clearable style="width: 200px">
              <el-option
                v-for="v in vehicleList"
                :key="v.id"
                :label="v.licensePlate"
                :value="v.id"
              />
            </el-select>
            <span>技师：</span>
            <el-select v-model="filterTechnicianId" placeholder="全部技师" clearable style="width: 180px">
              <el-option
                v-for="t in technicianList"
                :key="t.id"
                :label="t.name"
                :value="t.id"
              />
            </el-select>
            <el-button type="primary" @click="loadReports">
              <el-icon><Search /></el-icon>
              查询
            </el-button>
          </div>
        </div>
      </template>

      <el-table :data="filteredReportList" v-loading="loading" style="width: 100%">
        <el-table-column prop="reportNo" label="报告编号" width="150" />
        <el-table-column label="车辆" width="150">
          <template #default="scope">
            {{ vehicleMap[scope.row.vehicleId]?.licensePlate || scope.row.vehicleId }}
          </template>
        </el-table-column>
        <el-table-column label="技师" width="120">
          <template #default="scope">
            {{ technicianMap[scope.row.technicianId]?.name || scope.row.technicianId }}
          </template>
        </el-table-column>
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
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { listByCustomer } from '@/api/vehicle'
import { listTechnicians } from '@/api/technician'
import { listInspectionsByVehicle } from '@/api/inspection'

const router = useRouter()
const loading = ref(false)
const reportList = ref([])
const customerId = ref(1)
const vehicleList = ref([])
const technicianList = ref([])
const filterVehicleId = ref(null)
const filterTechnicianId = ref(null)

const vehicleMap = computed(() => {
  const map = {}
  for (const v of vehicleList.value) {
    map[v.id] = v
  }
  return map
})

const technicianMap = computed(() => {
  const map = {}
  for (const t of technicianList.value) {
    map[t.id] = t
  }
  return map
})

const filteredReportList = computed(() => {
  let list = reportList.value
  if (filterVehicleId.value) {
    list = list.filter(r => r.vehicleId === filterVehicleId.value)
  }
  if (filterTechnicianId.value) {
    list = list.filter(r => r.technicianId === filterTechnicianId.value)
  }
  return list
})

const statusText = (status) => {
  const map = { 1: '正常', 2: '轻微问题', 3: '需维修' }
  return map[status] || '未知'
}

const statusType = (status) => {
  const map = { 1: 'success', 2: 'warning', 3: 'danger' }
  return map[status] || 'info'
}

const loadBaseData = async () => {
  try {
    const [vehicles, technicians] = await Promise.all([
      listByCustomer(customerId.value),
      listTechnicians()
    ])
    vehicleList.value = vehicles
    technicianList.value = technicians
  } catch (e) {
    console.error('Failed to load base data', e)
  }
}

const loadReports = async () => {
  loading.value = true
  try {
    const vehicles = await listByCustomer(customerId.value)
    vehicleList.value = vehicles
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

onMounted(async () => {
  await loadBaseData()
  await loadReports()
})
</script>
