package com.cqupt.annotation;

import lombok.Data;

/**
 * @author alice
 */
@Data
public class Student {

    @Check(paramValues = {"zhangxintong"})
    private String name;

    @Check(paramValues = {"alice"})
    private String nickName;
}
