<template>
  <el-card>
    <template #header>创建预约</template>

    <el-form :model="form" label-width="120px" style="max-width: 600px">
      <el-form-item label="客户ID">
        <el-input-number v-model="form.customerId" :min="1" />
      </el-form-item>
      <el-form-item label="选择车辆">
        <el-select v-model="form.vehicleId" placeholder="请选择车辆" @change="onVehicleChange">
          <el-option
            v-for="v in vehicleList"
            :key="v.id"
            :label="`${v.plateNumber} - ${v.brand} ${v.model}`"
            :value="v.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="选择工位">
        <el-select v-model="form.workstationId" placeholder="请选择工位">
          <el-option
            v-for="w in workstationList"
            :key="w.id"
            :label="w.name"
            :value="w.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="服务项目">
        <el-select v-model="selectedServiceItems" multiple placeholder="请选择服务项目">
          <el-option
            v-for="s in serviceItemList"
            :key="s.id"
            :label="`${s.name} - ¥${s.price}`"
            :value="s.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预约日期">
        <el-date-picker
          v-model="form.bookingDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择预约日期"
        />
      </el-form-item>
      <el-form-item label="时间段">
        <el-select v-model="form.timeSlot" placeholder="请选择时间段">
          <el-option label="09:00-10:00" value="09:00-10:00" />
          <el-option label="10:00-11:00" value="10:00-11:00" />
          <el-option label="11:00-12:00" value="11:00-12:00" />
          <el-option label="14:00-15:00" value="14:00-15:00" />
          <el-option label="15:00-16:00" value="15:00-16:00" />
          <el-option label="16:00-17:00" value="16:00-17:00" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.remark" type="textarea" :rows="3" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">提交预约</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createBooking } from '@/api/booking'
import { listByCustomer } from '@/api/vehicle'
import { listWorkstations } from '@/api/workstation'
import { listServiceItems } from '@/api/serviceItem'

const router = useRouter()

const form = ref({
  customerId: 1,
  vehicleId: null,
  workstationId: null,
  serviceItemIds: [],
  bookingDate: '',
  timeSlot: '',
  remark: ''
})

const selectedServiceItems = ref([])
const vehicleList = ref([])
const workstationList = ref([])
const serviceItemList = ref([])

const onVehicleChange = () => {}

const submit = async () => {
  form.value.serviceItemIds = selectedServiceItems.value
  await createBooking(form.value)
  ElMessage.success('预约成功')
  router.push('/booking/list')
}

const reset = () => {
  form.value = {
    customerId: 1,
    vehicleId: null,
    workstationId: null,
    serviceItemIds: [],
    bookingDate: '',
    timeSlot: '',
    remark: ''
  }
  selectedServiceItems.value = []
}

const loadData = async () => {
  const [vehicles, workstations, items] = await Promise.all([
    listByCustomer(1),
    listWorkstations(),
    listServiceItems()
  ])
  vehicleList.value = vehicles
  workstationList.value = workstations
  serviceItemList.value = items
}

onMounted(() => {
  loadData()
})
</script>
