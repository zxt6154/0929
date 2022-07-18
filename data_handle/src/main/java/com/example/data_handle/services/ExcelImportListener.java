package com.example.data_handle.services;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.data_handle.bean.UserInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExcelImportListener extends AnalysisEventListener<UserInfo> {

    private Map<String, UserInfo> mapBefore = new HashMap<>();

    private Map<String, Integer> mapAfter = new HashMap<>();

    @Override
    public void invoke(UserInfo userInfo, AnalysisContext analysisContext) {
        if(mapBefore.containsKey(userInfo.getUserId())) {
            mapAfter.put(userInfo.getUserId(), userInfo.getUserVoteNum());
        }
        mapBefore.put(userInfo.getUserId(), userInfo);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
