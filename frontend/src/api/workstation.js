import request from './request'

export function listWorkstations() {
  return request({
    url: '/workstation/list',
    method: 'get'
  })
}

export function listAllWorkstations() {
  return request({
    url: '/workstation/listAll',
    method: 'get'
  })
}

export function getAvailableSlots(workstationId, startDate, days) {
  return request({
    url: '/workstation/availableSlots',
    method: 'get',
    params: { workstationId, startDate, days }
  })
}

export function getWorkstationById(id) {
  return request({
    url: `/workstation/${id}`,
    method: 'get'
  })
}

export function saveWorkstation(data) {
  return request({
    url: '/workstation/',
    method: 'post',
    data
  })
}

export function updateWorkstation(data) {
  return request({
    url: '/workstation/',
    method: 'put',
    data
  })
}

export function deleteWorkstation(id) {
  return request({
    url: `/workstation/${id}`,
    method: 'delete'
  })
}
