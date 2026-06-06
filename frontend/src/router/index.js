import { createRouter, createWebHistory } from 'vue-router'
import PageLayout from '@/components/PageLayout.vue'

const routes = [
  {
    path: '/',
    component: PageLayout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'vehicle/list',
        name: 'VehicleList',
        component: () => import('@/views/vehicle/VehicleList.vue'),
        meta: { title: '我的车辆' }
      },
      {
        path: 'vehicle/bind',
        name: 'VehicleBind',
        component: () => import('@/views/vehicle/VehicleBind.vue'),
        meta: { title: '绑定车辆' }
      },
      {
        path: 'vehicle/suggestion/:vehicleId',
        name: 'MaintenanceSuggestion',
        component: () => import('@/views/vehicle/MaintenanceSuggestion.vue'),
        meta: { title: '保养建议' }
      },
      {
        path: 'booking/create',
        name: 'BookingCreate',
        component: () => import('@/views/booking/BookingCreate.vue'),
        meta: { title: '创建预约' }
      },
      {
        path: 'booking/list',
        name: 'BookingList',
        component: () => import('@/views/booking/BookingList.vue'),
        meta: { title: '我的预约' }
      },
      {
        path: 'booking/detail/:id',
        name: 'BookingDetail',
        component: () => import('@/views/booking/BookingDetail.vue'),
        meta: { title: '预约详情' }
      },
      {
        path: 'inspection/list',
        name: 'InspectionList',
        component: () => import('@/views/inspection/InspectionList.vue'),
        meta: { title: '车检报告列表' }
      },
      {
        path: 'inspection/create',
        name: 'InspectionCreate',
        component: () => import('@/views/inspection/InspectionCreate.vue'),
        meta: { title: '创建车检报告' }
      },
      {
        path: 'inspection/report/:id',
        name: 'InspectionReport',
        component: () => import('@/views/inspection/InspectionReport.vue'),
        meta: { title: '车检报告详情' }
      },
      {
        path: 'order/list',
        name: 'OrderList',
        component: () => import('@/views/order/OrderList.vue'),
        meta: { title: '工单列表' }
      },
      {
        path: 'order/detail/:id',
        name: 'OrderDetail',
        component: () => import('@/views/order/OrderDetail.vue'),
        meta: { title: '工单详情' }
      },
      {
        path: 'order/create',
        name: 'OrderCreate',
        component: () => import('@/views/order/OrderCreate.vue'),
        meta: { title: '创建工单' }
      },
      {
        path: 'admin/serviceItem',
        name: 'ServiceItemManage',
        component: () => import('@/views/admin/ServiceItemManage.vue'),
        meta: { title: '服务项目管理' }
      },
      {
        path: 'admin/workstation',
        name: 'WorkstationManage',
        component: () => import('@/views/admin/WorkstationManage.vue'),
        meta: { title: '工位管理' }
      },
      {
        path: 'admin/technician',
        name: 'TechnicianManage',
        component: () => import('@/views/admin/TechnicianManage.vue'),
        meta: { title: '技师管理' }
      },
      {
        path: 'admin/part',
        name: 'PartManage',
        component: () => import('@/views/admin/PartManage.vue'),
        meta: { title: '配件管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
