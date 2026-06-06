<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center">
          <span>我的车辆</span>
          <el-button type="primary" @click="goBind">
            <el-icon><Plus /></el-icon>
            绑定车辆
          </el-button>
        </div>
      </template>

      <el-table :data="vehicleList" v-loading="loading" style="width: 100%">
        <el-table-column prop="licensePlate" label="车牌号" width="120" />
        <el-table-column prop="brand" label="品牌" width="120" />
        <el-table-column prop="model" label="车型" width="140" />
        <el-table-column prop="vin" label="车架号(VIN)" width="180" />
        <el-table-column prop="currentMileage" label="里程(km)" width="120" />
        <el-table-column label="操作" width="260">
          <template #default="scope">
            <el-button size="small" type="primary" @click="viewSuggestion(scope.row)">
              <el-icon><MagicStick /></el-icon>
              保养建议
            </el-button>
            <el-button size="small" @click="handleEdit(scope.row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="editVisible" title="编辑车辆信息" width="500px">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="车牌号">
          <el-input v-model="editForm.licensePlate" />
        </el-form-item>
        <el-form-item label="品牌">
          <el-input v-model="editForm.brand" />
        </el-form-item>
        <el-form-item label="车型">
          <el-input v-model="editForm.model" />
        </el-form-item>
        <el-form-item label="车架号">
          <el-input v-model="editForm.vin" />
        </el-form-item>
        <el-form-item label="里程">
          <el-input-number v-model="editForm.currentMileage" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmEdit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listByCustomer, updateVehicle, deleteVehicle } from '@/api/vehicle'

const router = useRouter()
const loading = ref(false)
const vehicleList = ref([])
const editVisible = ref(false)
const editForm = ref({})

const customerId = 1

const loadData = async () => {
  loading.value = true
  try {
    vehicleList.value = await listByCustomer(customerId)
  } finally {
    loading.value = false
  }
}

const goBind = () => {
  router.push('/vehicle/bind')
}

const viewSuggestion = (row) => {
  router.push(`/vehicle/suggestion/${row.id}`)
}

const handleEdit = (row) => {
  editForm.value = { ...row }
  editVisible.value = true
}

const confirmEdit = async () => {
  await updateVehicle(editForm.value)
  ElMessage.success('更新成功')
  editVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm(`确定删除车辆 ${row.licensePlate}？`, '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteVehicle(row.id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
