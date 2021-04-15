package com.example.demo.Service;

import com.example.demo.model.Entity.Video;
import org.springframework.stereotype.Component;

import java.util.List;

public interface VideoService {
    List<Video> findAll();

    Video findById(int id);

    int update(Video video);

    int delete(int id);

    int save(Video video);
}
