<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>保养建议 - 车辆ID：{{ vehicleId }}</span>
        <el-button @click="$router.back()">返回</el-button>
      </div>
    </template>

    <div v-loading="loading">
      <el-alert
        v-if="suggestion"
        :title="suggestion"
        type="info"
        :closable="false"
        show-icon
      />
      <el-empty v-else description="暂无保养建议" />
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getSuggestion } from '@/api/vehicle'

const route = useRoute()
const vehicleId = route.params.vehicleId
const loading = ref(false)
const suggestion = ref('')

const loadData = async () => {
  loading.value = true
  try {
    suggestion.value = await getSuggestion(vehicleId)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>
