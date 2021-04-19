package com.example.demo.Service;

import com.example.demo.Dto.VideoOrderDto;
import com.example.demo.model.Entity.VideoOrder;

public interface VideoOrderService {

    String save(VideoOrderDto videoOrderDto);
}
