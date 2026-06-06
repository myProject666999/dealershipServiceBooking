<template>
  <el-card>
    <template #header>绑定车辆</template>

    <el-form :model="form" label-width="100px" style="max-width: 500px">
      <el-form-item label="客户ID">
        <el-input-number v-model="form.customerId" :min="1" />
      </el-form-item>
      <el-form-item label="车牌号">
        <el-input v-model="form.plateNumber" placeholder="请输入车牌号" />
      </el-form-item>
      <el-form-item label="品牌">
        <el-input v-model="form.brand" placeholder="如：宝马、奔驰" />
      </el-form-item>
      <el-form-item label="车型">
        <el-input v-model="form.model" placeholder="如：3系、C级" />
      </el-form-item>
      <el-form-item label="车架号">
        <el-input v-model="form.vin" placeholder="请输入17位VIN码" />
      </el-form-item>
      <el-form-item label="里程(km)">
        <el-input-number v-model="form.mileage" :min="0" />
      </el-form-item>
      <el-form-item label="购车日期">
        <el-date-picker v-model="form.buyDate" type="date" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">提交绑定</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { bindVehicle } from '@/api/vehicle'

const router = useRouter()

const form = ref({
  customerId: 1,
  plateNumber: '',
  brand: '',
  model: '',
  vin: '',
  mileage: 0,
  buyDate: ''
})

const submit = async () => {
  await bindVehicle(form.value)
  ElMessage.success('绑定成功')
  router.push('/vehicle/list')
}

const reset = () => {
  form.value = {
    customerId: 1,
    plateNumber: '',
    brand: '',
    model: '',
    vin: '',
    mileage: 0,
    buyDate: ''
  }
}
</script>
