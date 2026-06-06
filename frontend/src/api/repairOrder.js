import request from './request'

export function createRepairOrder(data) {
  return request({
    url: '/repairOrder/create',
    method: 'post',
    data
  })
}

export function getRepairOrderById(id) {
  return request({
    url: `/repairOrder/${id}`,
    method: 'get'
  })
}

export function listRepairOrdersByCustomer(customerId) {
  return request({
    url: `/repairOrder/listByCustomer/${customerId}`,
    method: 'get'
  })
}

export function payRepairOrder(id, payMethod) {
  return request({
    url: `/repairOrder/pay/${id}`,
    method: 'post',
    params: { payMethod }
  })
}
