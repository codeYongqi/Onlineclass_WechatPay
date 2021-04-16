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

    /**
     * 分页接口
     * @param page 第几页
     * @param size 每页数据大小
     * @return
     */
    @GetMapping("pageVideo")
    public Object pageVideo(@RequestParam(value = "page",defaultValue = "1")int page,
                            @RequestParam(value = "size",defaultValue = "10")int size){
        return videoService.findAll();
    }

    /**
     * 通过id查询视频
     * @param videoId
     * @return
     */
    @GetMapping("findById")
    public Object findById(@RequestParam(value = "video_id") int videoId){
        return videoService.findById(videoId);
    }

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
     * @param title
     * @return
     */
    @PostMapping("save")
    public int save(@RequestParam(value = "title") String title){
        Video video = new Video();
        video.setTitle(title);
        int rows = videoService.save(video);
        System.out.println(video.getId());
        return rows;
    }

}
