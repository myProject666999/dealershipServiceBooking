<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <span>配件管理</span>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          新增
        </el-button>
      </div>
    </template>

    <el-table :data="list" v-loading="loading" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="partCode" label="配件编号" width="150" />
      <el-table-column prop="name" label="配件名称" />
      <el-table-column prop="brand" label="适用品牌" width="120" />
      <el-table-column prop="price" label="价格(¥)" width="120" />
      <el-table-column prop="stock" label="库存" width="100" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑' : '新增' + '配件'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="配件编号">
          <el-input v-model="form.partCode" />
        </el-form-item>
        <el-form-item label="配件名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="适用品牌">
          <el-input v-model="form.brand" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number v-model="form.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="库存">
          <el-input-number v-model="form.stock" :min="0" />
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
import { listParts, savePart, updatePart, deletePart } from '@/api/part'

const loading = ref(false)
const list = ref([])
const dialogVisible = ref(false)
const form = reactive({})

const loadData = async () => {
  loading.value = true
  try {
    list.value = await listParts()
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.keys(form).forEach(k => delete form[k])
  form.stock = 0
  form.price = 0
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const submit = async () => {
  if (form.id) {
    await updatePart(form)
  } else {
    await savePart(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm(`确定删除配件【${row.name}】？`, '提示', { type: 'warning' })
    .then(async () => {
      await deletePart(row.id)
      ElMessage.success('删除成功')
      loadData()
    }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
