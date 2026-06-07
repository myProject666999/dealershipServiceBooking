<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>创建车检报告</span>
        <el-button @click="$router.back()">返回</el-button>
      </div>
    </template>

    <el-form :model="form" label-width="120px" style="max-width: 600px">
      <el-form-item label="关联预约">
        <el-input-number v-model="form.bookingId" :min="1" />
      </el-form-item>
      <el-form-item label="车辆">
        <el-select v-model="form.vehicleId" placeholder="请选择车辆" @change="handleVehicleChange">
          <el-option
            v-for="v in vehicleList"
            :key="v.id"
            :label="`${v.licensePlate} - ${v.brand} ${v.model}`"
            :value="v.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="当前里程">
        <el-input-number v-model="form.currentMileage" :min="0" />
      </el-form-item>
      <el-form-item label="技师">
        <el-select v-model="form.technicianId" placeholder="请选择技师">
          <el-option
            v-for="t in technicianList"
            :key="t.id"
            :label="`${t.name} (${t.code})`"
            :value="t.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="总体评价">
        <el-input v-model="form.overallComment" type="textarea" :rows="2" />
      </el-form-item>
      <el-form-item label="检测照片URL">
        <el-input
          v-model="photoUrlInput"
          placeholder="输入URL后回车添加"
          @keyup.enter="addPhoto"
        />
        <div style="margin-top: 8px; display: flex; flex-wrap: wrap; gap: 8px">
          <el-tag
            v-for="(url, idx) in form.photos"
            :key="idx"
            closable
            @close="form.photos.splice(idx, 1)"
          >{{ url.length > 30 ? url.substring(0, 30) + '...' : url }}</el-tag>
        </div>
      </el-form-item>
      <el-divider content-position="left">检测问题</el-divider>
      <div v-for="(item, idx) in form.issues" :key="idx" class="issue-item">
        <el-form-item label="项目名称">
          <el-input v-model="item.itemName" />
        </el-form-item>
        <el-form-item label="问题描述">
          <el-input v-model="item.description" type="textarea" :rows="2" />
        </el-form-item>
        <el-form-item label="严重程度">
          <el-select v-model="item.severity">
            <el-option label="轻微" :value="1" />
            <el-option label="一般" :value="2" />
            <el-option label="严重" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="建议">
          <el-input v-model="item.suggestion" />
        </el-form-item>
        <el-button type="danger" size="small" @click="form.issues.splice(idx, 1)">删除此项</el-button>
        <el-divider />
      </div>
      <el-form-item>
        <el-button type="primary" plain @click="addIssue">
          <el-icon><Plus /></el-icon>
          添加问题
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">提交</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { createInspectionReport } from '@/api/inspection'
import { listByCustomer } from '@/api/vehicle'
import { listTechnicians } from '@/api/technician'

const router = useRouter()
const photoUrlInput = ref('')
const vehicleList = ref([])
const technicianList = ref([])

const form = ref({
  bookingId: null,
  vehicleId: null,
  technicianId: null,
  currentMileage: 0,
  overallComment: '',
  photos: [],
  issues: []
})

const loadData = async () => {
  try {
    vehicleList.value = await listByCustomer(1)
    technicianList.value = await listTechnicians()
  } catch (e) {
    console.error('Failed to load initial data', e)
  }
}

const handleVehicleChange = (vehicleId) => {
  const vehicle = vehicleList.value.find(v => v.id === vehicleId)
  if (vehicle && vehicle.currentMileage != null) {
    form.value.currentMileage = vehicle.currentMileage
  }
}

const addPhoto = () => {
  if (photoUrlInput.value.trim()) {
    form.value.photos.push(photoUrlInput.value.trim())
    photoUrlInput.value = ''
  }
}

const addIssue = () => {
  form.value.issues.push({
    itemName: '',
    description: '',
    severity: 2,
    suggestion: ''
  })
}

const submit = async () => {
  const payload = {
    report: {
      bookingId: form.value.bookingId,
      vehicleId: form.value.vehicleId,
      technicianId: form.value.technicianId,
      description: form.value.overallComment,
      currentMileage: form.value.currentMileage,
      inspectionTime: new Date().toISOString(),
      appearanceStatus: 1,
      tireStatus: 1,
      lightStatus: 1,
      chassisStatus: 1,
      batteryStatus: 1,
      overallStatus: 1
    },
    issues: form.value.issues.map(i => ({
      category: 'other',
      itemName: i.itemName,
      description: i.description,
      severity: i.severity,
      suggestAction: i.suggestion
    })),
    photos: form.value.photos.map(url => ({
      category: 'other',
      photoUrl: url
    }))
  }
  
  await createInspectionReport(payload)
  ElMessage.success('创建成功')
  router.push('/inspection/list')
}

const reset = () => {
  form.value = {
    bookingId: null,
    vehicleId: null,
    technicianId: null,
    currentMileage: 0,
    overallComment: '',
    photos: [],
    issues: []
  }
  photoUrlInput.value = ''
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.issue-item {
  padding: 10px;
  background-color: #fafafa;
  border-radius: 4px;
  margin-bottom: 10px;
}
</style>
