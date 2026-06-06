import request from './request'

export function listServiceItems() {
  return request({
    url: '/serviceItem/list',
    method: 'get'
  })
}

export function getServiceItemById(id) {
  return request({
    url: `/serviceItem/${id}`,
    method: 'get'
  })
}

export function saveServiceItem(data) {
  return request({
    url: '/serviceItem/',
    method: 'post',
    data
  })
}

export function updateServiceItem(data) {
  return request({
    url: '/serviceItem/',
    method: 'put',
    data
  })
}

export function deleteServiceItem(id) {
  return request({
    url: `/serviceItem/${id}`,
    method: 'delete'
  })
}
