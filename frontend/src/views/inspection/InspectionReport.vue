<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>车检报告详情</span>
        <el-button @click="$router.back()">返回</el-button>
      </div>
    </template>

    <div v-loading="loading">
      <el-descriptions :column="2" border v-if="detail">
        <el-descriptions-item label="报告ID">{{ detail.report?.id }}</el-descriptions-item>
        <el-descriptions-item label="报告编号">{{ detail.report?.reportNo }}</el-descriptions-item>
        <el-descriptions-item label="车辆ID">{{ detail.report?.vehicleId }}</el-descriptions-item>
        <el-descriptions-item label="预约ID">{{ detail.report?.bookingId }}</el-descriptions-item>
        <el-descriptions-item label="技师ID">{{ detail.report?.technicianId }}</el-descriptions-item>
        <el-descriptions-item label="当前里程">{{ detail.report?.currentMileage }}</el-descriptions-item>
        <el-descriptions-item label="检测时间" :span="2">{{ detail.report?.inspectionTime }}</el-descriptions-item>
        <el-descriptions-item label="总体评价" :span="2">{{ detail.report?.description }}</el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">检查状态</el-divider>
      <el-descriptions :column="3" border v-if="detail">
        <el-descriptions-item label="外观状态">
          <el-tag :type="checkStatusType(detail.report?.appearanceStatus)">{{ checkStatusText(detail.report?.appearanceStatus) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="轮胎状态">
          <el-tag :type="checkStatusType(detail.report?.tireStatus)">{{ checkStatusText(detail.report?.tireStatus) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="灯光状态">
          <el-tag :type="checkStatusType(detail.report?.lightStatus)">{{ checkStatusText(detail.report?.lightStatus) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="底盘状态">
          <el-tag :type="checkStatusType(detail.report?.chassisStatus)">{{ checkStatusText(detail.report?.chassisStatus) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="电池状态">
          <el-tag :type="checkStatusType(detail.report?.batteryStatus)">{{ checkStatusText(detail.report?.batteryStatus) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="总体状态">
          <el-tag :type="checkStatusType(detail.report?.overallStatus)">{{ checkStatusText(detail.report?.overallStatus) }}</el-tag>
        </el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">检测照片</el-divider>
      <div v-if="detail?.photos?.length" class="photo-grid">
        <el-image
          v-for="(p, idx) in detail.photos"
          :key="idx"
          :src="p.photoUrl || 'https://placehold.co/200x150?text=No+Image'"
          :preview-src-list="detail.photos.map(i => i.photoUrl || 'https://placehold.co/200x150?text=No+Image')"
          fit="cover"
          class="photo-item"
        />
      </div>
      <el-empty v-else description="暂无照片" />

      <el-divider content-position="left">检测问题</el-divider>
      <el-table v-if="detail?.issues?.length" :data="detail.issues" style="width: 100%">
        <el-table-column prop="itemName" label="检测项目" width="150" />
        <el-table-column prop="description" label="问题描述" />
        <el-table-column prop="severity" label="严重程度" width="100">
          <template #default="scope">
            <el-tag :type="severityType(scope.row.severity)">{{ severityText(scope.row.severity) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="suggestAction" label="处理建议" />
      </el-table>
      <el-empty v-else description="未检测到问题" />
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getInspectionReportDetail } from '@/api/inspection'

const route = useRoute()
const loading = ref(false)
const detail = ref(null)

const severityText = (s) => {
  const map = { 1: '轻微', 2: '一般', 3: '严重' }
  return map[s] || '未知'
}

const severityType = (s) => {
  const map = { 1: 'success', 2: 'warning', 3: 'danger' }
  return map[s] || 'info'
}

const checkStatusText = (status) => {
  const map = { 1: '正常', 2: '轻微问题', 3: '需维修' }
  return map[status] || '未知'
}

const checkStatusType = (status) => {
  const map = { 1: 'success', 2: 'warning', 3: 'danger' }
  return map[status] || 'info'
}

const loadData = async () => {
  loading.value = true
  try {
    detail.value = await getInspectionReportDetail(route.params.id)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.photo-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}
.photo-item {
  width: 200px;
  height: 150px;
  border-radius: 4px;
}
</style>
