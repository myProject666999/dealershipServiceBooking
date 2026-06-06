package com.dealership.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dealership.entity.InspectionReport;

import java.util.List;
import java.util.Map;

public interface InspectionService extends IService<InspectionReport> {

    Map<String, Object> createReport(Map<String, Object> payload);

    Map<String, Object> getReportDetail(Long reportId);

    List<InspectionReport> listByVehicleId(Long vehicleId);
}
