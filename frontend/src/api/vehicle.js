import request from './request'

export function listByCustomer(customerId) {
  return request({
    url: `/vehicle/listByCustomer/${customerId}`,
    method: 'get'
  })
}

export function getById(id) {
  return request({
    url: `/vehicle/${id}`,
    method: 'get'
  })
}

export function bindVehicle(data) {
  return request({
    url: '/vehicle/bind',
    method: 'post',
    data
  })
}

export function updateVehicle(data) {
  return request({
    url: '/vehicle/',
    method: 'put',
    data
  })
}

export function deleteVehicle(id) {
  return request({
    url: `/vehicle/${id}`,
    method: 'delete'
  })
}

export function getSuggestion(vehicleId) {
  return request({
    url: `/vehicle/suggestion/${vehicleId}`,
    method: 'get'
  })
}
