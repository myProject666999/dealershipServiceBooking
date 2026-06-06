<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>技师管理</span>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          新增
        </el-button>
      </div>
    </template>

    <el-table :data="list" v-loading="loading" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="phone" label="电话" width="150" />
      <el-table-column prop="specialty" label="专长" />
      <el-table-column prop="level" label="级别" width="120" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑' : '新增' + '技师'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="专长">
          <el-input v-model="form.specialty" placeholder="如：发动机维修、电路" />
        </el-form-item>
        <el-form-item label="级别">
          <el-select v-model="form.level">
            <el-option label="初级技师" value="初级技师" />
            <el-option label="中级技师" value="中级技师" />
            <el-option label="高级技师" value="高级技师" />
            <el-option label="技术总监" value="技术总监" />
          </el-select>
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
import { listTechnicians, saveTechnician, updateTechnician, deleteTechnician } from '@/api/technician'

const loading = ref(false)
const list = ref([])
const dialogVisible = ref(false)
const form = reactive({})

const loadData = async () => {
  loading.value = true
  try {
    list.value = await listTechnicians()
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.keys(form).forEach(k => delete form[k])
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const submit = async () => {
  if (form.id) {
    await updateTechnician(form)
  } else {
    await saveTechnician(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm(`确定删除技师【${row.name}】？`, '提示', { type: 'warning' })
    .then(async () => {
      await deleteTechnician(row.id)
      ElMessage.success('删除成功')
      loadData()
    }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
