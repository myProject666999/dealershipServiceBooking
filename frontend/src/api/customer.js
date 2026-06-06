import request from './request'

export function listCustomers() {
  return request({
    url: '/customer/list',
    method: 'get'
  })
}

export function getCustomerById(id) {
  return request({
    url: `/customer/${id}`,
    method: 'get'
  })
}

export function saveCustomer(data) {
  return request({
    url: '/customer/',
    method: 'post',
    data
  })
}

export function updateCustomer(data) {
  return request({
    url: '/customer/',
    method: 'put',
    data
  })
}

export function deleteCustomer(id) {
  return request({
    url: `/customer/${id}`,
    method: 'delete'
  })
}
