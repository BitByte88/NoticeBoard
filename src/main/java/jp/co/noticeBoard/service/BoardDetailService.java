package jp.co.noticeBoard.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.co.noticeBoard.dao.mapper.BoardDetailMapper;
import jp.co.noticeBoard.dto.BoardCommentDto;
import jp.co.noticeBoard.dto.BoardDeleteDto;
import jp.co.noticeBoard.dto.BoardDetailDto;

@Service
public class BoardDetailService {

    private static final Logger logger = LoggerFactory.getLogger(BoardDetailService.class);

    //１ページあたりの最大件数
    @Value("${list.max.count}")
    private Integer limit;

    @Autowired
    private BoardDetailMapper boardDetailMapper;

    /**
     * 掲示情報の詳細情報を取得する。
     *
     * @param boardId 掲示情報ID
     */
    public BoardDetailDto getBoardDetail(String boardId) throws Exception {
        return boardDetailMapper.getBoardDetail(boardId);
    }

    /**
     * 掲示文を削除する。
     *
     * @param deleteDto 削除Dto
     */
    public void deleteBoard(BoardDeleteDto deleteDto) throws Exception {
        boardDetailMapper.deleteBoard(deleteDto);

    }

    /**
     * 閲覧数をカウントアップする。
     *
     * @param boardNo 掲示文No
     */
    public void updateViewCount(String boardNo) throws Exception {
        boardDetailMapper.updateViewCount(boardNo);

    }

    /**
     * コメント情報を取得する。
     *
     * @param boardNo 掲示文No
     */
    public List<BoardCommentDto> getCommentList(String boardNo) throws Exception {
        return boardDetailMapper.getCommentList(boardNo);

    }
    
    /**
     * コメント情報を更新する。
     * 
     * @param updateDto コメント更新情報
     */
    public void commentUpdate(BoardCommentDto updateDto) throws Exception {
        boardDetailMapper.commentUpdate(updateDto);
    }

    /**
     * コメントを削除する。
     *
     * @param deleteDto 削除Dto
     */
    public void deleteComment(BoardDeleteDto deleteDto) throws Exception {
        boardDetailMapper.deleteComment(deleteDto);

    }
}
