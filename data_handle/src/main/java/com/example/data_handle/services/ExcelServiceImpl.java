package com.example.data_handle.services;

import com.alibaba.excel.EasyExcel;
import com.example.data_handle.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ExcelServiceImpl {

    @Autowired
    private ExcelImportListener excelImportListener;

    public void test(File file) {
        //EasyExcel.read(file, UserInfo.class, excelImportListener);
    }
}
