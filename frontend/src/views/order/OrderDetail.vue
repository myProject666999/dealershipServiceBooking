<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>工单详情</span>
        <el-button @click="$router.back()">返回</el-button>
      </div>
    </template>

    <div v-loading="loading">
      <el-descriptions :column="2" border v-if="detail?.order">
        <el-descriptions-item label="工单号">{{ detail.order.id }}</el-descriptions-item>
        <el-descriptions-item label="预约ID">{{ detail.order.bookingId }}</el-descriptions-item>
        <el-descriptions-item label="车辆ID">{{ detail.order.vehicleId }}</el-descriptions-item>
        <el-descriptions-item label="客户ID">{{ detail.order.customerId }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag>{{ statusText(detail.order.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="支付状态">
          <el-tag :type="detail.order.payStatus === 1 ? 'success' : 'warning'">
            {{ detail.order.payStatus === 1 ? '已支付' : '未支付' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="总金额" :span="2">
          <span style="font-size: 20px; color: #f56c6c; font-weight: bold">¥{{ detail.order.totalAmount }}</span>
        </el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">服务项目</el-divider>
      <el-table v-if="detail?.items?.length" :data="detail.items" style="width: 100%">
        <el-table-column prop="serviceItemName" label="项目名称" />
        <el-table-column prop="quantity" label="数量" width="80" />
        <el-table-column prop="unitPrice" label="单价(¥)" width="100" />
        <el-table-column prop="subtotal" label="小计(¥)" width="100" />
      </el-table>
      <el-empty v-else description="暂无项目" />

      <el-divider content-position="left">使用配件</el-divider>
      <el-table v-if="detail?.parts?.length" :data="detail.parts" style="width: 100%">
        <el-table-column prop="partName" label="配件名称" />
        <el-table-column prop="quantity" label="数量" width="80" />
        <el-table-column prop="unitPrice" label="单价(¥)" width="100" />
        <el-table-column prop="subtotal" label="小计(¥)" width="100" />
      </el-table>
      <el-empty v-else description="未使用配件" />
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getRepairOrderById } from '@/api/repairOrder'

const route = useRoute()
const loading = ref(false)
const detail = ref(null)

const statusText = (s) => {
  const map = { 0: '待处理', 1: '进行中', 2: '已完成', 3: '已取消' }
  return map[s] || '未知'
}

const loadData = async () => {
  loading.value = true
  try {
    detail.value = await getRepairOrderById(route.params.id)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>
