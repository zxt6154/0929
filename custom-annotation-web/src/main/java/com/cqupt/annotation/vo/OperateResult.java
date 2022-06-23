package com.cqupt.annotation.vo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OperateResult<T> {

    private String operateCode = null;

    private String operateMsg = null;

    private String operateErr = null;

    private Object operateResult = null;

    private Long operateTimstamp;

    public OperateResult() {
        this.operateTimstamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public OperateResult(OperateCode operateCode) {
        this.operateCode = operateCode.getOperateCode();
        this.operateMsg = operateCode.getOperateMsg();
        this.operateTimstamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public OperateResult(OperateCode operateCode, String operateMsg) {
        this.operateCode = operateCode.getOperateCode();
        this.operateMsg = operateMsg;
        this.operateTimstamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public <T extends Throwable> OperateResult(OperateCode operateCode, T ex) {
        this.operateCode = operateCode.getOperateCode();
        this.operateMsg = operateCode.getOperateMsg();
        this.operateErr = ex.getMessage();
        this.operateTimstamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public OperateResult(OperateCode operateCode, T operateResult) {
        this.operateCode = operateCode.getOperateCode();
        this.operateMsg = operateCode.getOperateMsg();
        this.operateResult = operateResult;
        this.operateTimstamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public OperateResult(OperateCode operateCode, Integer operateCount, T operateResult) {
        this.operateCode = operateCode.getOperateCode();
        this.operateMsg = operateCode.getOperateMsg();
        this.operateResult = operateResult;
        this.operateTimstamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public OperateResult(String code, String msg, T content) {
        this.operateCode = code;
        this.operateMsg = msg;
        this.operateResult = content;
        this.operateTimstamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }


    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> OperateResult<T> success() {
        return new OperateResult<T>(OperateCode.SUCCESS);
    }

    public static <T> OperateResult<T> success(T content) {
        return new OperateResult<T>(OperateCode.SUCCESS, content);
    }

    public static <T> OperateResult<T> success(T content, OperateCode operateCode) {
        return new OperateResult<T>(operateCode, content);
    }

    public static <T> OperateResult<T> success(T content, OperateCode operateCode, Integer operateCount) {
        return new OperateResult<T>(operateCode, operateCount, content);
    }


    public static <T> OperateResult<T> paramError() {
        return new OperateResult<T>(OperateCode.SYSTEM_ERROR);
    }

    public static <T> OperateResult<T> paramError(String operateMsg) {
        return new OperateResult<T>(OperateCode.SYSTEM_ERROR,operateMsg);
    }

    public static <T extends Throwable> OperateResult systemError(T ex) {
        return new OperateResult(OperateCode.SYSTEM_ERROR,ex.getMessage());
    }

    /**
     * 失败
     *

     * @param <T>
     * @return
     */
    public static <T> OperateResult<T> fail() {
        return new OperateResult<T>(OperateCode.SYSTEM_ERROR);
    }
    public static <T> OperateResult<T> fail(T content, OperateCode operateCode) {
        return new OperateResult<T>(operateCode, content);
    }
    public static <T> OperateResult<T> fail(T content) {
        return new OperateResult<T>(OperateCode.SYSTEM_ERROR, content);
    }

    public static <T> OperateResult<T> fail(String code, String msg) {
        return new OperateResult<T>(code, msg, null);
    }
    public static <T> OperateResult<T> fail(OperateCode operateCode) {
        return new OperateResult<T>(operateCode);
    }

    public static <T extends Throwable> OperateResult fail(OperateCode operateCode, T ex) {
        return new OperateResult(operateCode, ex);
    }

    /**
     * 设置
     *
     * @param operateCode
     * @param <T>
     * @return
     */
    public static <T> OperateResult<T> set(OperateCode operateCode) {
        return new OperateResult<T>(operateCode);
    }

    public static <T> OperateResult<T> set(OperateCode operateCode, T content) {
        return new OperateResult<T>(operateCode, content);
    }

    public static <T> OperateResult<T> set(String code, String msg) {
        return new OperateResult<T>(code, msg, null);
    }

    /**
     * 异常
     *
     * @param operateCode
     * @param ex
     * @param <T>
     * @return
     */
    public static <T extends Throwable> OperateResult exception(OperateCode operateCode, T ex) {
        return new OperateResult(operateCode, ex);
    }


    public OperateResult operateCode(String operateCode) {
        this.operateCode = operateCode;
        return this;
    }

}
