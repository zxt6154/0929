package com.example.newmodule.services;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.example.newmodule.bean.UserInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

@Service
public class ServicesImpl {

    @Autowired
    private ExcelImportListener excelImportListener;

    public void test() {
        EasyExcel.read(new File("/Users/alice/Documents/wokesheet.xlsx"), UserInfo.class, excelImportListener).sheet().doRead();
        //EasyExcel.write(new File("/Users/alice/Documents/wtest2.xlsx"), UserInfo.class).sheet("ceshi").doWrite(excelImportListener.lists);
    }

    public void testRedisDistributeLock() {
        EasyExcel.read(new File("/Users/alice/Documents/wokesheet.xlsx"), UserInfo.class, excelImportListener).sheet().doRead();
        //EasyExcel.write(new File("/Users/alice/Documents/wtest2.xlsx"), UserInfo.class).sheet("ceshi").doWrite(excelImportListener.lists);
    }
}
