package com.rosy.main.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 短信记录信息
 * </p>
 *
 * @author Rosy
 * @since 2025-01-20
 */
@Getter
@Setter
@TableName("sms_record")
public class SmsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息模板ID
     */
    private Long messageTemplateId;

    /**
     * 手机号
     */
    private Long phone;

    /**
     * 发送短信渠道商的ID
     */
    private Byte supplierId;

    /**
     * 发送短信渠道商的名称
     */
    private String supplierName;

    /**
     * 短信发送的内容
     */
    private String msgContent;

    /**
     * 下发批次的ID
     */
    private String seriesId;

    /**
     * 计费条数
     */
    private Byte chargingNum;

    /**
     * 回执内容
     */
    private String reportContent;

    /**
     * 短信状态： 10.发送 20.成功 30.失败
     */
    private Byte status;

    /**
     * 发送日期：20211112
     */
    private Integer sendDate;

    /**
     * 创建时间
     */
    private Integer created;

    /**
     * 更新时间
     */
    private Integer updated;
}
