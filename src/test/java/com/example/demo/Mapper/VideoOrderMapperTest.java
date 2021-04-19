package com.example.demo.Mapper;

import com.example.demo.model.Entity.VideoOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class VideoOrderMapperTest {

    @Autowired
    VideoOrderMapper videoOrderMapper;

    @Test
    void insert() {
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setCreateTime(new Date());
        videoOrder.setTotalFee(50000);
        videoOrder.setDel(0);
        videoOrder.setVideoTitle("ssh高级课程");
        videoOrderMapper.insert(videoOrder);
        assertNotNull(videoOrder.getId());
        System.out.println(videoOrder.getId());
    }

    @Test
    void findById() {
        VideoOrder videoOrder = videoOrderMapper.findById(2);
        assertNotNull(videoOrder);
    }

    @Test
    void findByOutTradeNo() {
    }

    @Test
    void del() {
        int rows = videoOrderMapper.del(5,5);
        assertNotEquals(rows,0);
    }

    @Test
    void findMyOrderList() {
    }

    @Test
    void updateVideoOrderByOutTradeNo() {
    }
}