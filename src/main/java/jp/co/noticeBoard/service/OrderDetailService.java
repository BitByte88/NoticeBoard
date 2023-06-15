package jp.co.noticeBoard.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.co.noticeBoard.dao.mapper.OrderDetailMapper;
import jp.co.noticeBoard.dto.BoardCommentDto;
import jp.co.noticeBoard.dto.BoardDeleteDto;
import jp.co.noticeBoard.dto.OrderDetailDto;

@Service
public class OrderDetailService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailService.class);

    //１ページあたりの最大件数
    @Value("${list.max.count}")
    private Integer limit;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 注文詳細情報を取得する。
     *
     * @param orderNo 注文No
     * @return エラーメッセージリスト
     */
    public List<OrderDetailDto> getOrderDetailList(String orderNo) throws Exception {
        return orderDetailMapper.getOrderDetailList(orderNo);

    }

    /**
     * 掲示文を削除する。
     *
     * @param orderNo 注文No
     * @return エラーメッセージリスト
     */
    public void deleteBoard(BoardDeleteDto deleteDto) throws Exception {
        orderDetailMapper.deleteBoard(deleteDto);

    }

    /**
     * 閲覧数をカウントアップする。
     *
     * @param orderNo 掲示文No
     * @return エラーメッセージリスト
     */
    public void updateViewCount(String orderNo) throws Exception {
        orderDetailMapper.updateViewCount(orderNo);

    }

    /**
     * コメント情報を取得する。
     *
     * @param orderNo 注文No
     * @return エラーメッセージリスト
     */
    public List<BoardCommentDto> getCommentList(String orderNo) throws Exception {
        return orderDetailMapper.getCommentList(orderNo);

    }
    
    /**
     * コメント情報を更新する。
     * @param updateDto コメント更新情報
     * @return エラーメッセージリスト
     */
    public void commentUpdate(BoardCommentDto updateDto) throws Exception {
        orderDetailMapper.commentUpdate(updateDto);
    }

}
