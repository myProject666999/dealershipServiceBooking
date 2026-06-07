<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>工位管理</span>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          新增
        </el-button>
      </div>
    </template>

    <el-table :data="list" v-loading="loading" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="code" label="工位编号" width="140" />
      <el-table-column prop="name" label="工位名称" />
      <el-table-column label="工位类型" width="120">
        <template #default="scope">
          {{ typeMap[scope.row.type] }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑' : '新增' + '工位'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="工位编号">
          <el-input v-model="form.code" placeholder="请输入工位编号" />
        </el-form-item>
        <el-form-item label="工位名称">
          <el-input v-model="form.name" placeholder="请输入工位名称" />
        </el-form-item>
        <el-form-item label="工位类型">
          <el-select v-model="form.type" style="width: 100%">
            <el-option label="保养" :value="1" />
            <el-option label="维修" :value="2" />
            <el-option label="综合" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注" />
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
import { listAllWorkstations, saveWorkstation, updateWorkstation, deleteWorkstation } from '@/api/workstation'

const typeMap = {
  1: '保养',
  2: '维修',
  3: '综合'
}

const loading = ref(false)
const list = ref([])
const dialogVisible = ref(false)
const form = reactive({})

const loadData = async () => {
  loading.value = true
  try {
    list.value = await listAllWorkstations()
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.keys(form).forEach(k => delete form[k])
  form.status = 1
  form.type = 1
  form.code = ''
  form.remark = ''
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const submit = async () => {
  const payload = {
    name: form.name,
    code: form.code,
    type: form.type,
    status: form.status,
    remark: form.remark
  }
  if (form.id) {
    payload.id = form.id
    await updateWorkstation(payload)
  } else {
    await saveWorkstation(payload)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm(`确定删除工位【${row.name}】？`, '提示', { type: 'warning' })
    .then(async () => {
      await deleteWorkstation(row.id)
      ElMessage.success('删除成功')
      loadData()
    }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
