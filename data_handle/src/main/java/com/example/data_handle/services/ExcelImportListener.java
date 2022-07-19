package com.example.data_handle.services;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.data_handle.bean.UserInfo;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ExcelImportListener extends AnalysisEventListener<UserInfo> {


    private Map<String, UserInfo> mapBefore = new HashMap<>();

    private Map<String, List<UserInfo>> mapAfter = new HashMap<>();

    Map<String, Integer> mapProduct = new HashMap<>();

    @Getter
    ArrayList<UserInfo> lists = new ArrayList<>();

    @Override
    public void invoke(UserInfo userInfo, AnalysisContext analysisContext) {

        if(mapBefore.containsKey(userInfo.getUserId())) {
            if (!Objects.equals(mapBefore.get(userInfo.getUserId()).getUserVoteNum(), userInfo.getUserVoteNum())) {
                List<UserInfo> userInfoList = new ArrayList<>();
                userInfoList.add(mapBefore.get(userInfo.getUserId()));
                userInfoList.add(userInfo);
                mapAfter.put(userInfo.getUserId(), userInfoList);
            }
        } else {
            mapBefore.put(userInfo.getUserId(), userInfo);
        }
//        if(mapProduct.containsKey(userInfo.getUserId())) {
//            Integer votenum = mapProduct.get(userInfo.getUserId());
//            Integer voteAdd = votenum + userInfo.getUserVoteNum();
//            mapProduct.put(userInfo.getUserId(), voteAdd);
//            lists.add(new UserInfo(userInfo.getUserId(), voteAdd));
//        } else {
//            mapProduct.put(userInfo.getUserId(), userInfo.getUserVoteNum());
//            lists.add(userInfo);
//        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("=============start");
        System.out.println(mapAfter.size());
        System.out.println("end===========");

        mapAfter.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });
    }
}