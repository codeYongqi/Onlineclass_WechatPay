package com.example.demo.Mapper;

import com.example.demo.model.Entity.Video;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface VideoMapper {

    @Select("select * from video")
    @Results({
            @Result(column = "cover_img",property = "coverImg")
    })
    List<Video> findAll();

    @Select("select * from video where id = #{id}")
    Video findById(int id);

    @Update("update video set name = #{name} where id = #{id}")
    int update(Video video);

    @Delete("delete from video where id = #{id}")
    int delete(int id);
}
