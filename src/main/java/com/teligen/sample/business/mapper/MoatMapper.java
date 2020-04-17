package com.teligen.sample.business.mapper;

import com.teligen.sample.business.model.common.Moat;

public interface MoatMapper {
    int deleteByPrimaryKey(String keyid);

    int insert(Moat record);

    int insertSelective(Moat record);

    Moat selectByPrimaryKey(String keyid);

    int updateByPrimaryKeySelective(Moat record);

    int updateByPrimaryKey(Moat record);
}