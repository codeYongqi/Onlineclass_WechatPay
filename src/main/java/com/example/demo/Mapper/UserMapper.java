package com.example.demo.Mapper;

import com.example.demo.model.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User findById(@Param("id")int userId);

    @Select("select * from user where github_id = #{github_id}")
    User findByGithubId(@Param("github_id")double githubId);

    @Insert("INSERT INTO `user` (`openid`, `name`, `head_img`, `phone`, `sign`, `city`, `create_time`, `email`,`github_id`)" +
            "VALUES" +
            "(NULL, #{name}, #{headImg}, NULL, '全栈工程师',  NULL, #{createTime}, #{email},#{githubId});")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int saveUser(User user);
}
