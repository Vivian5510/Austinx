package com.rosy.main.service.impl;

import com.rosy.main.domain.entity.MessageTemplate;
import com.rosy.main.mapper.MessageTemplateMapper;
import com.rosy.main.service.IMessageTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息模板信息 服务实现类
 * </p>
 *
 * @author Rosy
 * @since 2025-01-20
 */
@Service
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplate> implements IMessageTemplateService {

}
