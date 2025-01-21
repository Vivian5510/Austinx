package com.rosy.austinx.support.pipline;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 业务执行模板（把责任链的逻辑串起来）
 */
@Data
public class ProcessTemplate {

    private List<BusinessProcess> processList;
}