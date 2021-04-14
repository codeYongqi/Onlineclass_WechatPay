package com.example.demo.Controller;

import com.example.demo.Service.VideoService;
import com.example.demo.model.Entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuyongqi
 */
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("pageVideo")
    public Object pageVideo(){
        return videoService.findAll();
    }

    @GetMapping("findById")
    public Object findById(int id){
        return videoService.findById(id);
    }

    @DeleteMapping("deleteById")
    public int deleteById(int id){
        return videoService.delete(id);
    }

    @PutMapping("update")
    public int update(int videoId,String title){
        Video video = new Video();
        video.setTitle(title);
        video.setId(videoId);
        return videoService.update(video);
    }

}
