package jp.co.noticeBoard.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.noticeBoard.dao.mapper.RestCommentMapper;
import jp.co.noticeBoard.dto.BoardCommentDto;

@Service
public class RestCommentService {

    private static final Logger logger = LoggerFactory.getLogger(RestCommentService.class);

    @Autowired
    private RestCommentMapper restCommentMapper;

    /**
     * コメント情報を取得する。
     *
     * @param boardId 掲示情報ID
     */
    public List<BoardCommentDto> getCommentList(String boardId) throws Exception {
        return restCommentMapper.getCommentList(boardId);
    }

    /**
     * コメントを登録する。
     * 
     * @param updateDto コメント更新情報
     */
    public void writeComment(BoardCommentDto updateDto) throws Exception {
        restCommentMapper.writeComment(updateDto);
    }

}
