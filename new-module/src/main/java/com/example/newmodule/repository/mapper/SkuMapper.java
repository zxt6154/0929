package com.example.newmodule.repository.mapper;

import com.example.newmodule.repository.entity.Sku;
import com.example.newmodule.repository.entity.SkuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuMapper {
    long countByExample(SkuExample example);

    int deleteByExample(SkuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    List<Sku> selectByExample(SkuExample example);

    Sku selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sku record, @Param("example") SkuExample example);

    int updateByExample(@Param("record") Sku record, @Param("example") SkuExample example);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);
}