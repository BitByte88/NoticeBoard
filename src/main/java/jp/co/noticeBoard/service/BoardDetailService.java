package jp.co.noticeBoard.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.noticeBoard.dao.mapper.BoardDetailMapper;
import jp.co.noticeBoard.dto.BoardCommentDto;
import jp.co.noticeBoard.dto.BoardDeleteDto;
import jp.co.noticeBoard.dto.BoardDetailDto;

@Service
public class BoardDetailService {

    private static final Logger logger = LoggerFactory.getLogger(BoardDetailService.class);

    @Autowired
    private BoardDetailMapper boardDetailMapper;

    /**
     * 掲示情報を取得する。
     *
     * @param boardId 掲示情報ID
     */
    public BoardDetailDto getBoardDetail(String boardId) throws Exception {
        return boardDetailMapper.getBoardDetail(boardId);
    }

    /**
     * コメント情報を取得する。
     *
     * @param boardId 掲示情報ID
     */
    public List<BoardCommentDto> getCommentList(String boardId) throws Exception {
        return boardDetailMapper.getCommentList(boardId);

    }

    /**
     * 閲覧数をカウントアップする。
     *
     * @param boardId 掲示情報ID
     */
    public void updateViewCount(String boardId) throws Exception {
        boardDetailMapper.updateViewCount(boardId);
    }

    /**
     * 掲示情報を削除する。
     *
     * @param deleteDto 削除Dto
     */
    public void deleteBoard(BoardDeleteDto deleteDto) throws Exception {
        boardDetailMapper.deleteBoard(deleteDto);
    }

    /**
     * コメントを削除する。
     *
     * @param deleteDto 削除Dto
     */
    public void deleteComment(BoardDeleteDto deleteDto) throws Exception {
        boardDetailMapper.deleteComment(deleteDto);
    }
    
    /**
     * コメントを登録する。
     * 
     * @param updateDto コメント更新情報
     */
    public void commentUpdate(BoardCommentDto updateDto) throws Exception {
        boardDetailMapper.commentUpdate(updateDto);
    }

}
