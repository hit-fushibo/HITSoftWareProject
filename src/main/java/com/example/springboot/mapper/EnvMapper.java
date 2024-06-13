package com.example.springboot.mapper;

//@Mapper
public interface EnvMapper {

//    @Select("select value from env_var where env=#{env}")
    String findVarByEnv(String env);
//    @Update("update env_var set value=#{value} where env=#{env}")
    void updateByEnv(String env,String value);
}
