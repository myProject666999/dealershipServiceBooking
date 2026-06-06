import request from './request'

export function createBooking(data) {
  return request({
    url: '/booking/create',
    method: 'post',
    data
  })
}

export function listBookingsByCustomer(customerId) {
  return request({
    url: `/booking/listByCustomer/${customerId}`,
    method: 'get'
  })
}

export function getBookingById(id) {
  return request({
    url: `/booking/${id}`,
    method: 'get'
  })
}

export function updateBookingStatus(id, status) {
  return request({
    url: '/booking/status',
    method: 'put',
    params: { id, status }
  })
}

export function cancelBooking(id, cancelReason) {
  return request({
    url: `/booking/cancel/${id}`,
    method: 'post',
    params: { cancelReason }
  })
}
