<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>服务项目管理</span>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          新增
        </el-button>
      </div>
    </template>

    <el-table :data="list" v-loading="loading" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="code" label="项目编码" width="140" />
      <el-table-column prop="name" label="项目名称" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="price" label="价格(¥)" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
            {{ scope.row.status === 1 ? '上架' : '下架' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑' : '新增' + '服务项目'" width="500px">
      <el-form :model="form" label-width="140px">
        <el-form-item label="项目编码" required>
          <el-input v-model="form.code" placeholder="请输入项目编码" />
        </el-form-item>
        <el-form-item label="项目名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number v-model="form.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item label="建议里程间隔(km)">
          <el-input-number v-model="form.suggestMileageInterval" :min="0" />
        </el-form-item>
        <el-form-item label="建议天数间隔">
          <el-input-number v-model="form.suggestDayInterval" :min="0" />
        </el-form-item>
        <el-form-item label="是否上架">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listServiceItems, saveServiceItem, updateServiceItem, deleteServiceItem } from '@/api/serviceItem'

const loading = ref(false)
const list = ref([])
const dialogVisible = ref(false)
const form = reactive({})

const loadData = async () => {
  loading.value = true
  try {
    list.value = await listServiceItems()
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.keys(form).forEach(k => delete form[k])
  form.name = ''
  form.code = ''
  form.description = ''
  form.price = 0
  form.sort = 0
  form.status = 1
  form.suggestMileageInterval = 0
  form.suggestDayInterval = 0
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  if (form.status === undefined) form.status = row.onSale !== undefined ? row.onSale : 1
  dialogVisible.value = true
}

const submit = async () => {
  if (!form.code) {
    ElMessage.warning('请输入项目编码')
    return
  }
  const payload = {
    name: form.name,
    code: form.code,
    description: form.description,
    price: form.price,
    sort: form.sort || 0,
    status: form.status,
    suggestMileageInterval: form.suggestMileageInterval || 0,
    suggestDayInterval: form.suggestDayInterval || 0
  }
  if (form.id) {
    payload.id = form.id
    await updateServiceItem(payload)
  } else {
    await saveServiceItem(payload)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm(`确定删除服务项目【${row.name}】？`, '提示', { type: 'warning' })
    .then(async () => {
      await deleteServiceItem(row.id)
      ElMessage.success('删除成功')
      loadData()
    }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
