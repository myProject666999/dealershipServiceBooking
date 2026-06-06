package com.dealership.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dealership.common.BusinessException;
import com.dealership.entity.InspectionIssue;
import com.dealership.entity.InspectionPhoto;
import com.dealership.entity.InspectionReport;
import com.dealership.mapper.InspectionIssueMapper;
import com.dealership.mapper.InspectionPhotoMapper;
import com.dealership.mapper.InspectionReportMapper;
import com.dealership.service.InspectionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InspectionServiceImpl extends ServiceImpl<InspectionReportMapper, InspectionReport> implements InspectionService {

    @Autowired
    private InspectionIssueMapper inspectionIssueMapper;

    @Autowired
    private InspectionPhotoMapper inspectionPhotoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createReport(Map<String, Object> payload) {
        Map<String, Object> reportMap = (Map<String, Object>) payload.get("report");
        if (reportMap == null) {
            throw new BusinessException("车检报告数据不能为空");
        }
        List<Map<String, Object>> issuesList = (List<Map<String, Object>>) payload.get("issues");
        if (issuesList == null) {
            issuesList = Collections.emptyList();
        }
        List<Map<String, Object>> photosList = (List<Map<String, Object>>) payload.get("photos");
        if (photosList == null) {
            photosList = Collections.emptyList();
        }

        InspectionReport report = objectMapper.convertValue(reportMap, InspectionReport.class);
        if (report.getVehicleId() == null) {
            throw new BusinessException("车辆ID不能为空");
        }
        if (report.getTechnicianId() == null) {
            throw new BusinessException("技师ID不能为空");
        }
        List<InspectionIssue> issues = objectMapper.convertValue(issuesList, new TypeReference<List<InspectionIssue>>() {});
        List<InspectionPhoto> photos = objectMapper.convertValue(photosList, new TypeReference<List<InspectionPhoto>>() {});

        String reportNo = "RPT" + System.currentTimeMillis();
        report.setReportNo(reportNo);
        if (report.getInspectionTime() == null) {
            report.setInspectionTime(LocalDateTime.now());
        }
        if (report.getCurrentMileage() == null) {
            report.setCurrentMileage(0);
        }
        if (report.getAppearanceStatus() == null) report.setAppearanceStatus(1);
        if (report.getTireStatus() == null) report.setTireStatus(1);
        if (report.getLightStatus() == null) report.setLightStatus(1);
        if (report.getChassisStatus() == null) report.setChassisStatus(1);
        if (report.getBatteryStatus() == null) report.setBatteryStatus(1);
        if (report.getOverallStatus() == null) report.setOverallStatus(1);
        save(report);

        if (issues != null && !issues.isEmpty()) {
            for (InspectionIssue issue : issues) {
                issue.setReportId(report.getId());
                inspectionIssueMapper.insert(issue);
            }
        }

        if (photos != null && !photos.isEmpty()) {
            for (InspectionPhoto photo : photos) {
                photo.setReportId(report.getId());
                inspectionPhotoMapper.insert(photo);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("report", report);
        return result;
    }

    @Override
    public Map<String, Object> getReportDetail(Long reportId) {
        InspectionReport report = getById(reportId);
        if (report == null) {
            throw new BusinessException("车检报告不存在");
        }
        List<InspectionIssue> issues = inspectionIssueMapper.selectList(
                new LambdaQueryWrapper<InspectionIssue>().eq(InspectionIssue::getReportId, reportId));
        List<InspectionPhoto> photos = inspectionPhotoMapper.selectList(
                new LambdaQueryWrapper<InspectionPhoto>().eq(InspectionPhoto::getReportId, reportId));

        Map<String, Object> result = new HashMap<>();
        result.put("report", report);
        result.put("issues", issues);
        result.put("photos", photos);
        return result;
    }

    @Override
    public List<InspectionReport> listByVehicleId(Long vehicleId) {
        return list(new LambdaQueryWrapper<InspectionReport>()
                .eq(InspectionReport::getVehicleId, vehicleId)
                .orderByDesc(InspectionReport::getCreateTime));
    }
}
