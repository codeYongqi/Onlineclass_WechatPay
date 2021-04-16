package com.example.demo.Controller.admin;

import com.example.demo.Service.VideoService;
import com.example.demo.model.Entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;

    /**
     * 通过id删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("deleteById")
    public int deleteById(@RequestParam(value = "video_id") int videoId){
        return videoService.delete(videoId);
    }

    /**
     * 更新视频信息
     * @param video
     * @return
     */
    @PutMapping("update")
    public int update(@RequestBody Video video){
        return videoService.update(video);
    }

    /**
     * 插入新的视频
     * @param video
     * @return
     */
    @PostMapping("save")
    public int save(@RequestBody Video video){
        int rows = videoService.save(video);
        System.out.println(video.getId());
        return rows;
    }
}
