import request from './request'

export function listParts() {
  return request({
    url: '/part/list',
    method: 'get'
  })
}

export function getPartById(id) {
  return request({
    url: `/part/${id}`,
    method: 'get'
  })
}

export function savePart(data) {
  return request({
    url: '/part/',
    method: 'post',
    data
  })
}

export function updatePart(data) {
  return request({
    url: '/part/',
    method: 'put',
    data
  })
}

export function deletePart(id) {
  return request({
    url: `/part/${id}`,
    method: 'delete'
  })
}
