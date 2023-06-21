package jp.co.noticeBoard.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.noticeBoard.dao.mapper.BoardDetailMapper;
import jp.co.noticeBoard.dto.BoardDetailDto;

@Service
public class BoardEditRegisterService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDetailService.class);

    @Autowired
    private BoardDetailMapper orderDetailMapper;


    /**
     * 掲示内容を登録する。
     *
     * @param  boardDto 掲示情報
     * @return エラーメッセージリスト
     */
    public void registerBoard(BoardDetailDto boardDto) throws Exception {
    	orderDetailMapper.registerBoard(boardDto);
    }

    /**
     * 掲示内容を更新する。
     *
     * @param boardDto 掲示情報
     * @return エラーメッセージリスト
     */
    public void updateBoard(BoardDetailDto boardDto) throws Exception {
        orderDetailMapper.updateBoard(boardDto);

    }

}
