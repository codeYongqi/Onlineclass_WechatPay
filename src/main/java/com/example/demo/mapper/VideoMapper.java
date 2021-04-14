package com.example.demo.mapper;

import com.example.demo.model.entity.Video;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VideoMapper {

    @Select("select * from video")
    List<Video> findAll();
}
