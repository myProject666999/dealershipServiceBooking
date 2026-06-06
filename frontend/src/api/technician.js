import request from './request'

export function listTechnicians() {
  return request({
    url: '/technician/list',
    method: 'get'
  })
}

export function getTechnicianById(id) {
  return request({
    url: `/technician/${id}`,
    method: 'get'
  })
}

export function saveTechnician(data) {
  return request({
    url: '/technician/',
    method: 'post',
    data
  })
}

export function updateTechnician(data) {
  return request({
    url: '/technician/',
    method: 'put',
    data
  })
}

export function deleteTechnician(id) {
  return request({
    url: `/technician/${id}`,
    method: 'delete'
  })
}
