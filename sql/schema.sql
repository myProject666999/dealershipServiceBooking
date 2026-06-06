-- =============================================
-- 4S店保养预约与车检系统 数据库脚本
-- Database: dealership_service_booking
-- =============================================

DROP DATABASE IF EXISTS `dealership_service_booking`;
CREATE DATABASE `dealership_service_booking` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `dealership_service_booking`;

-- =============================================
-- 1. 车主用户表
-- =============================================
CREATE TABLE `customer` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `open_id` VARCHAR(64) NOT NULL COMMENT '微信小程序openid',
  `nickname` VARCHAR(64) DEFAULT NULL COMMENT '昵称',
  `avatar_url` VARCHAR(255) DEFAULT NULL COMMENT '头像',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `real_name` VARCHAR(32) DEFAULT NULL COMMENT '真实姓名',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_open_id` (`open_id`),
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车主用户表';

-- =============================================
-- 2. 车辆表
-- =============================================
CREATE TABLE `vehicle` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `customer_id` BIGINT NOT NULL COMMENT '车主用户ID',
  `license_plate` VARCHAR(20) DEFAULT NULL COMMENT '车牌号',
  `vin` VARCHAR(32) DEFAULT NULL COMMENT '车架号VIN',
  `brand` VARCHAR(64) DEFAULT NULL COMMENT '品牌',
  `model` VARCHAR(64) DEFAULT NULL COMMENT '车型',
  `color` VARCHAR(32) DEFAULT NULL COMMENT '车身颜色',
  `current_mileage` INT DEFAULT 0 COMMENT '当前里程(km)',
  `last_service_mileage` INT DEFAULT 0 COMMENT '上次保养里程(km)',
  `last_service_time` DATETIME DEFAULT NULL COMMENT '上次保养时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_license_plate` (`license_plate`),
  UNIQUE KEY `uk_vin` (`vin`),
  KEY `idx_customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆表';

-- =============================================
-- 3. 服务项目表（小保养、大保养等）
-- =============================================
CREATE TABLE `service_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(64) NOT NULL COMMENT '项目名称',
  `code` VARCHAR(32) NOT NULL COMMENT '项目编码',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '项目描述',
  `suggest_mileage_interval` INT DEFAULT NULL COMMENT '建议里程间隔(km)',
  `suggest_day_interval` INT DEFAULT NULL COMMENT '建议时间间隔(天)',
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '基础价格',
  `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态 0-下架 1-上架',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务项目表';

-- =============================================
-- 4. 工位表
-- =============================================
CREATE TABLE `workstation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(32) NOT NULL COMMENT '工位名称',
  `code` VARCHAR(32) NOT NULL COMMENT '工位编号',
  `type` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '工位类型 1-保养 2-维修 3-综合',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工位表';

-- =============================================
-- 5. 预约表
-- =============================================
CREATE TABLE `booking` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `booking_no` VARCHAR(32) NOT NULL COMMENT '预约单号',
  `customer_id` BIGINT NOT NULL COMMENT '车主用户ID',
  `vehicle_id` BIGINT NOT NULL COMMENT '车辆ID',
  `workstation_id` BIGINT NOT NULL COMMENT '工位ID',
  `service_item_id` BIGINT DEFAULT NULL COMMENT '服务项目ID',
  `booking_date` DATE NOT NULL COMMENT '预约日期',
  `start_time` TIME NOT NULL COMMENT '开始时间',
  `end_time` TIME NOT NULL COMMENT '结束时间',
  `current_mileage` INT DEFAULT NULL COMMENT '预约时里程(km)',
  `customer_remark` VARCHAR(255) DEFAULT NULL COMMENT '客户备注',
  `status` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '状态 1-待确认 2-已确认 3-已到店 4-已完成 5-已取消',
  `cancel_reason` VARCHAR(255) DEFAULT NULL COMMENT '取消原因',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_booking_no` (`booking_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_vehicle_id` (`vehicle_id`),
  KEY `idx_workstation_time` (`workstation_id`, `booking_date`, `start_time`, `end_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约表';

-- =============================================
-- 6. 技师表
-- =============================================
CREATE TABLE `technician` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(32) NOT NULL COMMENT '技师姓名',
  `code` VARCHAR(32) NOT NULL COMMENT '技师工号',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `level` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '等级 1-初级 2-中级 3-高级 4-专家',
  `specialty` VARCHAR(128) DEFAULT NULL COMMENT '专长',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态 0-离职 1-在职',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='技师表';

-- =============================================
-- 7. 车检报告表
-- =============================================
CREATE TABLE `inspection_report` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `report_no` VARCHAR(32) NOT NULL COMMENT '车检报告编号',
  `booking_id` BIGINT DEFAULT NULL COMMENT '预约ID',
  `vehicle_id` BIGINT NOT NULL COMMENT '车辆ID',
  `technician_id` BIGINT NOT NULL COMMENT '车检技师ID',
  `inspection_time` DATETIME NOT NULL COMMENT '车检时间',
  `current_mileage` INT NOT NULL COMMENT '车检时里程(km)',
  `appearance_status` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '外观状态 1-正常 2-轻微问题 3-需维修',
  `tire_status` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '轮胎状态 1-正常 2-轻微问题 3-需维修',
  `light_status` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '灯光状态 1-正常 2-轻微问题 3-需维修',
  `chassis_status` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '底盘状态 1-正常 2-轻微问题 3-需维修',
  `battery_status` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '电池状态 1-正常 2-轻微问题 3-需维修',
  `overall_status` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '总体状态 1-正常 2-有建议项 3-需维修',
  `description` TEXT COMMENT '车检描述',
  `suggestion` TEXT COMMENT '维修建议',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_report_no` (`report_no`),
  KEY `idx_booking_id` (`booking_id`),
  KEY `idx_vehicle_id` (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车检报告表';

-- =============================================
-- 8. 车检照片表
-- =============================================
CREATE TABLE `inspection_photo` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `report_id` BIGINT NOT NULL COMMENT '车检报告ID',
  `category` VARCHAR(32) NOT NULL COMMENT '照片类别 appearance/tire/light/chassis/battery/other',
  `photo_url` VARCHAR(255) NOT NULL COMMENT '照片URL',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '照片描述',
  `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_report_id` (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车检照片表';

-- =============================================
-- 9. 车检问题明细表
-- =============================================
CREATE TABLE `inspection_issue` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `report_id` BIGINT NOT NULL COMMENT '车检报告ID',
  `category` VARCHAR(32) NOT NULL COMMENT '问题类别 appearance/tire/light/chassis/battery',
  `item_name` VARCHAR(64) NOT NULL COMMENT '问题项目名称',
  `severity` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '严重程度 1-轻微 2-一般 3-严重',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '问题描述',
  `suggest_action` VARCHAR(128) DEFAULT NULL COMMENT '建议处理措施',
  `estimated_price` DECIMAL(10,2) DEFAULT NULL COMMENT '预估费用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_report_id` (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车检问题明细表';

-- =============================================
-- 10. 配件表
-- =============================================
CREATE TABLE `part` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `part_no` VARCHAR(64) NOT NULL COMMENT '配件编号',
  `name` VARCHAR(128) NOT NULL COMMENT '配件名称',
  `brand` VARCHAR(64) DEFAULT NULL COMMENT '品牌',
  `model` VARCHAR(64) DEFAULT NULL COMMENT '适配车型',
  `spec` VARCHAR(128) DEFAULT NULL COMMENT '规格型号',
  `unit` VARCHAR(16) NOT NULL DEFAULT '个' COMMENT '单位',
  `purchase_price` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '进货价',
  `sale_price` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '销售价',
  `stock_quantity` INT NOT NULL DEFAULT 0 COMMENT '库存数量',
  `warning_quantity` INT NOT NULL DEFAULT 10 COMMENT '预警数量',
  `location` VARCHAR(64) DEFAULT NULL COMMENT '存放位置',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_part_no` (`part_no`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配件表';

-- =============================================
-- 11. 维修工单表
-- =============================================
CREATE TABLE `repair_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` VARCHAR(32) NOT NULL COMMENT '工单编号',
  `booking_id` BIGINT DEFAULT NULL COMMENT '预约ID',
  `inspection_report_id` BIGINT DEFAULT NULL COMMENT '车检报告ID',
  `customer_id` BIGINT NOT NULL COMMENT '车主ID',
  `vehicle_id` BIGINT NOT NULL COMMENT '车辆ID',
  `technician_id` BIGINT NOT NULL COMMENT '主修技师ID',
  `current_mileage` INT DEFAULT NULL COMMENT '维修时里程(km)',
  `order_type` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '工单类型 1-保养 2-维修 3-保养+维修',
  `labor_fee` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '工时费',
  `parts_fee` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '配件费',
  `total_amount` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '总金额',
  `discount_amount` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '优惠金额',
  `pay_amount` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '实付金额',
  `status` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '状态 1-待施工 2-施工中 3-待结算 4-已完成 5-已取消',
  `pay_status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '支付状态 0-未支付 1-已支付',
  `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
  `pay_method` TINYINT(2) DEFAULT NULL COMMENT '支付方式 1-微信 2-支付宝 3-银行卡 4-现金',
  `in_time` DATETIME DEFAULT NULL COMMENT '进厂时间',
  `out_time` DATETIME DEFAULT NULL COMMENT '出厂时间',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_vehicle_id` (`vehicle_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修工单表';

-- =============================================
-- 12. 维修工单项目表（服务项目明细）
-- =============================================
CREATE TABLE `repair_order_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` BIGINT NOT NULL COMMENT '工单ID',
  `item_type` TINYINT(2) NOT NULL COMMENT '项目类型 1-服务项目 2-自定义维修项目',
  `service_item_id` BIGINT DEFAULT NULL COMMENT '服务项目ID',
  `item_name` VARCHAR(128) NOT NULL COMMENT '项目名称',
  `item_price` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '项目单价',
  `quantity` INT NOT NULL DEFAULT 1 COMMENT '数量',
  `subtotal` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '小计',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修工单项目表';

-- =============================================
-- 13. 工单配件领用表
-- =============================================
CREATE TABLE `repair_order_part` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` BIGINT NOT NULL COMMENT '工单ID',
  `part_id` BIGINT NOT NULL COMMENT '配件ID',
  `part_no` VARCHAR(64) NOT NULL COMMENT '配件编号（冗余）',
  `part_name` VARCHAR(128) NOT NULL COMMENT '配件名称（冗余）',
  `unit` VARCHAR(16) NOT NULL DEFAULT '个' COMMENT '单位',
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '领用单价',
  `quantity` INT NOT NULL DEFAULT 1 COMMENT '领用数量',
  `subtotal` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '小计',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_part_id` (`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单配件领用表';

-- =============================================
-- 14. 库存变动记录表
-- =============================================
CREATE TABLE `part_stock_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `part_id` BIGINT NOT NULL COMMENT '配件ID',
  `type` TINYINT(2) NOT NULL COMMENT '变动类型 1-入库 2-出库(领用) 3-盘点调整 4-退货',
  `quantity` INT NOT NULL COMMENT '变动数量（正数入库，负数出库）',
  `before_quantity` INT NOT NULL COMMENT '变动前数量',
  `after_quantity` INT NOT NULL COMMENT '变动后数量',
  `related_order_no` VARCHAR(32) DEFAULT NULL COMMENT '关联单号',
  `operator` VARCHAR(32) DEFAULT NULL COMMENT '操作人',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_part_id` (`part_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存变动记录表';

-- =============================================
-- 初始化数据
-- =============================================

-- 初始化服务项目
INSERT INTO `service_item` (`name`, `code`, `description`, `suggest_mileage_interval`, `suggest_day_interval`, `price`, `sort`, `status`) VALUES
('小保养', 'MINOR_MAINTENANCE', '更换机油、机滤、常规检查', 5000, 180, 398.00, 1, 1),
('大保养', 'MAJOR_MAINTENANCE', '更换机油、机滤、空滤、空调滤、燃油滤、火花塞', 20000, 720, 998.00, 2, 1),
('四轮定位', 'WHEEL_ALIGNMENT', '四轮定位检测与调整', NULL, NULL, 198.00, 3, 1),
('轮胎动平衡', 'TIRE_BALANCING', '轮胎动平衡检测与调整', NULL, NULL, 40.00, 4, 1),
('空调清洗', 'AC_CLEANING', '空调系统清洗消毒', NULL, NULL, 168.00, 5, 1),
('刹车系统保养', 'BRAKE_SERVICE', '刹车片检查、刹车油检测更换', 40000, 730, 298.00, 6, 1),
('电池检测', 'BATTERY_CHECK', '蓄电池性能检测', NULL, NULL, 30.00, 7, 1);

-- 初始化工位
INSERT INTO `workstation` (`name`, `code`, `type`, `status`) VALUES
('1号保养工位', 'WS-001', 1, 1),
('2号保养工位', 'WS-002', 1, 1),
('3号综合工位', 'WS-003', 3, 1),
('4号维修工位', 'WS-004', 2, 1),
('5号维修工位', 'WS-005', 2, 1),
('6号综合工位', 'WS-006', 3, 1);

-- 初始化技师
INSERT INTO `technician` (`name`, `code`, `phone`, `level`, `specialty`, `status`) VALUES
('张师傅', 'T-001', '13800000001', 3, '发动机维修', 1),
('李师傅', 'T-002', '13800000002', 2, '底盘维修', 1),
('王师傅', 'T-003', '13800000003', 2, '电气系统', 1),
('赵师傅', 'T-004', '13800000004', 1, '常规保养', 1),
('陈师傅', 'T-005', '13800000005', 4, '综合诊断', 1);

-- 初始化配件
INSERT INTO `part` (`part_no`, `name`, `brand`, `model`, `spec`, `unit`, `purchase_price`, `sale_price`, `stock_quantity`, `warning_quantity`, `location`) VALUES
('P-001', '全合成机油 5W-30', '美孚', '通用', '4L', '桶', 180.00, 268.00, 50, 10, 'A-01'),
('P-002', '机油滤清器', '曼牌', '通用', '标准', '个', 25.00, 45.00, 80, 20, 'A-02'),
('P-003', '空气滤清器', '曼牌', '通用', '标准', '个', 35.00, 65.00, 60, 15, 'A-03'),
('P-004', '空调滤清器', '曼牌', '通用', '活性炭', '个', 45.00, 85.00, 55, 15, 'A-04'),
('P-005', '燃油滤清器', '曼牌', '通用', '标准', '个', 50.00, 95.00, 40, 10, 'A-05'),
('P-006', '火花塞', 'NGK', '通用', '铱金', '支', 55.00, 98.00, 100, 30, 'B-01'),
('P-007', '刹车片（前）', '布雷博', '通用', '标准', '套', 180.00, 350.00, 30, 10, 'B-02'),
('P-008', '刹车片（后）', '布雷博', '通用', '标准', '套', 160.00, 320.00, 30, 10, 'B-03'),
('P-009', '刹车油', '博世', '通用', 'DOT4 1L', '桶', 50.00, 95.00, 40, 10, 'B-04'),
('P-010', '蓄电池', '瓦尔塔', '通用', '60Ah', '个', 320.00, 580.00, 15, 5, 'C-01');

-- 初始化测试用户
INSERT INTO `customer` (`open_id`, `nickname`, `avatar_url`, `phone`, `real_name`) VALUES
('test_openid_001', '王先生', '', '13900000001', '王小明'),
('test_openid_002', '李女士', '', '13900000002', '李小红');

-- 初始化测试车辆
INSERT INTO `vehicle` (`customer_id`, `license_plate`, `vin`, `brand`, `model`, `color`, `current_mileage`, `last_service_mileage`, `last_service_time`) VALUES
(1, '京A12345', 'LVHFA1638H5000001', '大众', '帕萨特 2023款', '黑色', 23500, 18000, '2025-12-15 10:30:00'),
(1, '京B88888', 'LVHFA1638H5000002', '丰田', '凯美瑞 2024款', '白色', 5800, 0, NULL),
(2, '京C66666', 'LVHFA1638H5000003', '本田', '雅阁 2023款', '银色', 42000, 35000, '2025-09-20 14:00:00');
