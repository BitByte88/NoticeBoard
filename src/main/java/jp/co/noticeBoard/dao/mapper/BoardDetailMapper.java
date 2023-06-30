package jp.co.noticeBoard.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.noticeBoard.dto.BoardCommentDto;
import jp.co.noticeBoard.dto.BoardDeleteDto;
import jp.co.noticeBoard.dto.BoardDetailDto;

/**
 * 掲示情報の詳細情報取得 Mapperクラス.
 */

@Mapper
public interface BoardDetailMapper {

    /*
     * 掲示情報を取得する。
     *
     * @param boardId 掲示情報ID
     * @return BoardDetailDto
     */
    BoardDetailDto getBoardDetail(@Param("boardId") String boardId);

    /*
     * コメント情報を取得する。
     *
     * @param boardId 掲示情報ID
     * @return コメントリスト
     */
    List<BoardCommentDto> getCommentList(@Param("boardId") String boardId);

    /*
     * 閲覧数をカウントアップする。
     *
     * @param boardId 掲示情報ID
     */
    void updateViewCount(@Param("boardId") String boardId);
    
    /*
     * 掲示内容を登録する。
     *
     * @param updateDto 掲示内容
     */
    void registerBoard(@Param("updateDto") BoardDetailDto updateDto);

    /*
     * 掲示内容を修正する。
     *
     * @param updateDto 掲示内容
     */
    void updateBoard(@Param("updateDto") BoardDetailDto updateDto);
    
    /*
     * 掲示情報を削除する。
     *
     * @param deleteDto 掲示情報削除条件
     */
    void deleteBoard(@Param("deleteDto") BoardDeleteDto deleteDto);

    /*
     * コメントを削除する。
     *
     * @param deleteDto 掲示情報削除条件
     */
    void deleteComment(@Param("deleteDto") BoardDeleteDto deleteDto);
}


