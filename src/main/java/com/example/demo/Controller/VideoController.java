package com.example.demo.Controller;

import com.example.demo.Service.VideoService;
import com.example.demo.model.Entity.Video;
import com.example.demo.utils.JsonData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PageHelper.startPage(page,size); 
        List<Video> list = videoService.findAll();
        PageInfo<Video> pageInfo = new PageInfo<>(list);
        Map<String,Object> data = new HashMap<>();
        data.put("total_size",pageInfo.getTotal());
        data.put("total_page",pageInfo.getPages());
        data.put("current_page",page);
        data.put("data",list);
        return data;
    }

    /**
     * 通过id查询视频
     * @param videoId
     * @return
     */
    @GetMapping("findById")
    public Object findById(@RequestParam(value = "video_id") int videoId){
        return JsonData.buildSuccess(videoService.findById(videoId));
    }

}
