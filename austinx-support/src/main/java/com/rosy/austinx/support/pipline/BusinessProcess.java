package com.rosy.austinx.support.pipline;

/**
 * 业务执行器
 */
public interface BusinessProcess<T extends ProcessModel> {

    /**
     * 真正处理逻辑
     */
    void process(ProcessContext<T> context);
}
