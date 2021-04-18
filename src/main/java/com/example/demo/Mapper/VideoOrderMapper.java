package com.example.demo.Mapper;

import com.example.demo.model.Entity.VideoOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface VideoOrderMapper {

    @Insert("INSERT INTO `video_order` " +
            "(`openid`, `out_trade_no`, `state`," +
            " `create_time`, `notify_time`, `total_fee`," +
            " `nickname`, `head_img`, `video_id`, " +
            "`video_title`, `video_img`, `user_id`, " +
            "`ip`, `del`)" +
            "VALUES" +
            "(#{openid},#{outTradeNo},#{state}," +
            "#{createTime},#{notifyTime},#{totalFee}," +
            "#{nickname},#{headImg},#{videoId}," +
            "#{videoTitle},#{videoImg},#{userId}," +
            "#{ip},#{del});")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insert(VideoOrder videoOrder);

    //通过主键查询订单
    @Select("select * from video_order where id = #{order_id} and del = 0")
    VideoOrder findById(@Param("order_id")int id);

    //通过订单流水号查询订单
    @Select("select * from video_order where out_trade_no = #{out_trade_no}")
    VideoOrder findByOutTradeNo(@Param("out_trade_no")String outTradeNo);

    //逻辑删除订单
    @Update("update video_order set del = 0 where id=#{id} and user_id = #{user_id}")
    int del(@Param("id")int id,@Param("user_id") int userId);

    //查找用户全部订单
    @Select("select * from video_order where user_id = #{userId}")
    List<VideoOrder> findMyOrderList(int userId);

    //通过订单流水号更新订单
    @Update("update video_order set state = #{state} ,notify_time = #{notifyTime},openid=#{openid} " +
            "where out_trade_no = #{outTradeNo}")
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);
}
