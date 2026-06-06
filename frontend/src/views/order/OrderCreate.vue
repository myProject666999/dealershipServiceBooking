<template>
  <el-card>
    <template #header>创建工单</template>

    <el-form :model="form" label-width="120px" style="max-width: 700px">
      <el-form-item label="客户ID">
        <el-input-number v-model="form.customerId" :min="1" />
      </el-form-item>
      <el-form-item label="车辆ID">
        <el-input-number v-model="form.vehicleId" :min="1" />
      </el-form-item>
      <el-form-item label="关联预约ID">
        <el-input-number v-model="form.bookingId" :min="0" />
      </el-form-item>
      <el-form-item label="技师ID">
        <el-input-number v-model="form.technicianId" :min="1" />
      </el-form-item>

      <el-divider content-position="left">服务项目</el-divider>
      <el-table :data="form.items" border style="width: 100%; margin-bottom: 10px">
        <el-table-column label="服务项目" width="250">
          <template #default="scope">
            <el-select v-model="scope.row.serviceItemId" placeholder="选择项目">
              <el-option
                v-for="s in serviceItemList"
                :key="s.id"
                :label="s.name"
                :value="s.id"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="数量" width="150">
          <template #default="scope">
            <el-input-number v-model="scope.row.quantity" :min="1" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button type="danger" size="small" @click="form.items.splice(scope.$index, 1)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button type="primary" plain @click="addItem">
        <el-icon><Plus /></el-icon>
        添加项目
      </el-button>

      <el-divider content-position="left">使用配件</el-divider>
      <el-table :data="form.parts" border style="width: 100%; margin-bottom: 10px">
        <el-table-column label="配件" width="250">
          <template #default="scope">
            <el-select v-model="scope.row.partId" placeholder="选择配件">
              <el-option
                v-for="p in partList"
                :key="p.id"
                :label="p.name"
                :value="p.id"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="数量" width="150">
          <template #default="scope">
            <el-input-number v-model="scope.row.quantity" :min="1" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button type="danger" size="small" @click="form.parts.splice(scope.$index, 1)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button type="primary" plain @click="addPart">
        <el-icon><Plus /></el-icon>
        添加配件
      </el-button>

      <el-form-item style="margin-top: 20px">
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
import { createRepairOrder } from '@/api/repairOrder'
import { listServiceItems } from '@/api/serviceItem'
import { listParts } from '@/api/part'

const router = useRouter()
const serviceItemList = ref([])
const partList = ref([])

const form = ref({
  customerId: 1,
  vehicleId: null,
  bookingId: null,
  technicianId: null,
  items: [],
  parts: []
})

const addItem = () => {
  form.value.items.push({ serviceItemId: null, quantity: 1 })
}

const addPart = () => {
  form.value.parts.push({ partId: null, quantity: 1 })
}

const submit = async () => {
  await createRepairOrder(form.value)
  ElMessage.success('创建成功')
  router.push('/order/list')
}

const reset = () => {
  form.value = {
    customerId: 1,
    vehicleId: null,
    bookingId: null,
    technicianId: null,
    items: [],
    parts: []
  }
}

const loadData = async () => {
  serviceItemList.value = await listServiceItems()
  partList.value = await listParts()
}

onMounted(() => {
  loadData()
})
</script>
