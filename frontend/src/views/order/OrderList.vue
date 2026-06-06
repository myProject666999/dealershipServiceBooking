<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center">
          <span>工单列表</span>
          <el-button type="primary" @click="$router.push('/order/create')">
            <el-icon><Plus /></el-icon>
            新建工单
          </el-button>
        </div>
      </template>

      <el-table :data="orderList" v-loading="loading" style="width: 100%">
        <el-table-column prop="id" label="工单号" width="80" />
        <el-table-column prop="bookingId" label="预约ID" width="100" />
        <el-table-column prop="vehicleId" label="车辆ID" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="statusType(scope.row.status)">{{ statusText(scope.row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="totalAmount" label="总金额(¥)" width="120" />
        <el-table-column prop="payStatus" label="支付状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.payStatus === 1 ? 'success' : 'warning'">
              {{ scope.row.payStatus === 1 ? '已支付' : '未支付' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" type="primary" @click="viewDetail(scope.row)">详情</el-button>
            <el-button
              v-if="scope.row.payStatus !== 1"
              size="small"
              type="success"
              @click="handlePay(scope.row)"
            >支付</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listRepairOrdersByCustomer, payRepairOrder } from '@/api/repairOrder'

const router = useRouter()
const loading = ref(false)
const orderList = ref([])
const customerId = 1

const statusText = (s) => {
  const map = { 0: '待处理', 1: '进行中', 2: '已完成', 3: '已取消' }
  return map[s] || '未知'
}

const statusType = (s) => {
  const map = { 0: 'warning', 1: 'primary', 2: 'success', 3: 'info' }
  return map[s] || ''
}

const loadData = async () => {
  loading.value = true
  try {
    orderList.value = await listRepairOrdersByCustomer(customerId)
  } finally {
    loading.value = false
  }
}

const viewDetail = (row) => {
  router.push(`/order/detail/${row.id}`)
}

const handlePay = (row) => {
  ElMessageBox.confirm(`确定支付工单 #${row.id}，金额 ¥${row.totalAmount}？`, '支付确认', {
    type: 'warning'
  }).then(async () => {
    await payRepairOrder(row.id, 1)
    ElMessage.success('支付成功')
    loadData()
  }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
