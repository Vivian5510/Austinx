package com.rosy.main.service.impl;

import com.rosy.main.domain.entity.SmsRecord;
import com.rosy.main.mapper.SmsRecordMapper;
import com.rosy.main.service.ISmsRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信记录信息 服务实现类
 * </p>
 *
 * @author Rosy
 * @since 2025-01-20
 */
@Service
public class SmsRecordServiceImpl extends ServiceImpl<SmsRecordMapper, SmsRecord> implements ISmsRecordService {

}
