package com.example.demo.Mapper;

import com.example.demo.Provider.VideoProvider;
import com.example.demo.model.Entity.Video;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface VideoMapper {

    @Select("select * from video")
    List<Video> findAll();

    @Select("select * from video where id = #{id}")
    Video findById(int id);

    //@Update("update video set title = #{title} ,summary = #{summary},view_num=#{viewNum} where id = #{id}")
    @UpdateProvider(value = VideoProvider.class,method = "updateVideo")
    int update(Video video);

    @Delete("delete from video where id = #{id}")
    int delete(int id);

    @Insert("insert into video (title) values (#{title})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int save(Video video);
}
