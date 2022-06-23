package com.cqupt.annotation.vo;

public enum OperateCode {
    /**
     * 系统异常
     */
    SYSTEM_ERROR("90001", "系统异常"),
    EX_NULL("90002", "空指针异常"),
    EX_CAST("90003", "类型转换异常"),
    EX_IO("90004", "类型转换异常"),
    SUCCESS("00000", "成功");
    private String operateCode;
    private String operateMsg;

    OperateCode(String operateCode, String operateMsg) {
        this.operateCode = operateCode;
        this.operateMsg = operateMsg;
    }

    public String getOperateCode() {
        return operateCode;
    }

    public String getOperateMsg() {
        return operateMsg;
    }
}
