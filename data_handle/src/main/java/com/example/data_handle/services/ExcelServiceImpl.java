package com.example.data_handle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelServiceImpl {

    @Autowired
    private ExcelImportListener excelImportListener;

    private void test() {
       // EasyExcel.read(new File("/Users/alice/Documents/wokesheet.xlsx"), UserInfo.class, excelImportListener);
    }
}
