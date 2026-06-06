<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="aside">
      <div class="logo">
        <el-icon><Van /></el-icon>
        <span>4S店服务系统</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/home">
          <el-icon><House /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-sub-menu index="vehicle">
          <template #title>
            <el-icon><Van /></el-icon>
            <span>我的车辆</span>
          </template>
          <el-menu-item index="/vehicle/list">车辆列表</el-menu-item>
          <el-menu-item index="/vehicle/bind">绑定车辆</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="booking">
          <template #title>
            <el-icon><Calendar /></el-icon>
            <span>预约管理</span>
          </template>
          <el-menu-item index="/booking/create">创建预约</el-menu-item>
          <el-menu-item index="/booking/list">我的预约</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="inspection">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>车检报告</span>
          </template>
          <el-menu-item index="/inspection/create">创建报告</el-menu-item>
          <el-menu-item index="/vehicle/list">报告列表</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="order">
          <template #title>
            <el-icon><List /></el-icon>
            <span>维修工单</span>
          </template>
          <el-menu-item index="/order/create">创建工单</el-menu-item>
          <el-menu-item index="/order/list">工单列表</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="admin">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>管理中心</span>
          </template>
          <el-menu-item index="/admin/serviceItem">服务项目管理</el-menu-item>
          <el-menu-item index="/admin/workstation">工位管理</el-menu-item>
          <el-menu-item index="/admin/technician">技师管理</el-menu-item>
          <el-menu-item index="/admin/part">配件管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-title">{{ currentTitle }}</div>
        <div class="header-user">
          <el-avatar :size="32" style="background-color: #409EFF">
            <el-icon><User /></el-icon>
          </el-avatar>
          <span class="username">客户</span>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const activeMenu = computed(() => route.path)
const currentTitle = computed(() => route.meta?.title || '首页')
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.aside {
  background-color: #304156;
  overflow-x: hidden;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  gap: 8px;
  border-bottom: 1px solid #1f2d3d;
}

.logo .el-icon {
  font-size: 24px;
}

.aside :deep(.el-menu) {
  border-right: none;
}

.header {
  background-color: #fff;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-title {
  font-size: 18px;
  font-weight: 500;
  color: #303133;
}

.header-user {
  display: flex;
  align-items: center;
  gap: 10px;
}

.username {
  color: #606266;
  font-size: 14px;
}

.main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
