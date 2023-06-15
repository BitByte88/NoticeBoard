package jp.co.noticeBoard.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.noticeBoard.dao.mapper.OrderDetailMapper;
import jp.co.noticeBoard.dto.OrderDetailDto;

@Service
public class BoardEditRegisterService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDetailService.class);

    @Autowired
    private OrderDetailMapper orderDetailMapper;


    /**
     * 掲示板を登録する。
     *
     * @param  orderDetailDtoList 注文詳細情報リスト
     * @return エラーメッセージリスト
     */
    public void registerBoard(OrderDetailDto boardDto) throws Exception {
    	orderDetailMapper.registerBoard(boardDto);
    }

    /**
     * 掲示板を更新する。
     *
     * @param orderNo 注文No
     * @return エラーメッセージリスト
     */
    public void updateBoard(OrderDetailDto boardDto) throws Exception {
        orderDetailMapper.updateBoard(boardDto);

    }

}
