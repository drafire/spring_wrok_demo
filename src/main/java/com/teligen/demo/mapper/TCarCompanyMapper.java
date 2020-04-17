package com.teligen.demo.mapper;

import com.teligen.demo.model.TCarCompany;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TCarCompanyMapper {
    @Delete({
        "delete from T_CAR_COMPANY",
        "where ID = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    @Insert({
        "insert into T_CAR_COMPANY (ID, REFCHECKCARKEYID, ",
        "NAME, IDENTITYCARD, ",
        "REMARK, SEX)",
        "values (#{id,jdbcType=VARCHAR}, #{refcheckcarkeyid,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{identitycard,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR}, #{sex,jdbcType=DECIMAL})"
    })
    int insert(TCarCompany record);

    int insertSelective(TCarCompany record);

    @Select({
        "select",
        "ID, REFCHECKCARKEYID, NAME, IDENTITYCARD, REMARK, SEX",
        "from T_CAR_COMPANY",
        "where ID = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    TCarCompany selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TCarCompany record);

    @Update({
        "update T_CAR_COMPANY",
        "set REFCHECKCARKEYID = #{refcheckcarkeyid,jdbcType=VARCHAR},",
          "NAME = #{name,jdbcType=VARCHAR},",
          "IDENTITYCARD = #{identitycard,jdbcType=VARCHAR},",
          "REMARK = #{remark,jdbcType=VARCHAR},",
          "SEX = #{sex,jdbcType=DECIMAL}",
        "where ID = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(TCarCompany record);
}