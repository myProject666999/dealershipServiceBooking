<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="intro-card">
          <template #header>
            <div class="card-header">
              <el-icon :size="24" color="#409EFF"><Van /></el-icon>
              <span>欢迎使用汽车4S店服务预约系统</span>
            </div>
          </template>
          <p>本系统提供一站式汽车售后服务管理，包括车辆绑定、在线预约保养维修、车检报告查看、维修工单管理等功能。</p>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="quick-entries">
      <el-col :span="6" v-for="item in entries" :key="item.path">
        <el-card class="entry-card" shadow="hover" @click="goTo(item.path)">
          <el-icon :size="40" :color="item.color">{{ item.icon }}</el-icon>
          <div class="entry-title">{{ item.title }}</div>
          <div class="entry-desc">{{ item.desc }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const entries = [
  { path: '/vehicle/list', title: '我的车辆', desc: '管理您的所有车辆信息', icon: 'Van', color: '#409EFF' },
  { path: '/booking/create', title: '预约服务', desc: '在线预约保养维修服务', icon: 'Calendar', color: '#67C23A' },
  { path: '/booking/list', title: '我的预约', desc: '查看预约记录和状态', icon: 'Tickets', color: '#E6A23C' },
  { path: '/order/list', title: '维修工单', desc: '查看和管理维修工单', icon: 'List', color: '#F56C6C' }
]

const goTo = (path) => {
  router.push(path)
}
</script>

<style scoped>
.intro-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: 500;
}

.intro-card p {
  color: #606266;
  line-height: 1.8;
  font-size: 14px;
}

.quick-entries {
  margin-top: 10px;
}

.entry-card {
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
}

.entry-card:hover {
  transform: translateY(-4px);
}

.entry-title {
  margin-top: 12px;
  font-size: 16px;
  font-weight: 500;
  color: #303133;
}

.entry-desc {
  margin-top: 8px;
  font-size: 12px;
  color: #909399;
}
</style>
