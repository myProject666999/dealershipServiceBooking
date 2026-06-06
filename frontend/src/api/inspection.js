import request from './request'

export function createInspectionReport(data) {
  return request({
    url: '/inspection/createReport',
    method: 'post',
    data
  })
}

export function getInspectionReportDetail(id) {
  return request({
    url: `/inspection/report/${id}`,
    method: 'get'
  })
}

export function listInspectionsByVehicle(vehicleId) {
  return request({
    url: `/inspection/listByVehicle/${vehicleId}`,
    method: 'get'
  })
}
