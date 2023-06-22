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
    BoardDetailDto getBoardDetail(@Param("boardId") String boardNo);

    /*
     * 掲示情報を取得する。
     *
     * @param boardNo コメント検索変数
     * @return コメントリスト
     */
    List<BoardCommentDto> getCommentList(@Param("boardNo") String boardNo);

    /*
     * 閲覧数をカウントアップする。
     *
     * @param boardNo 掲示No
     * @return 件数
     */
    void updateViewCount(@Param("boardNo") String boardNo);    
    
    /*
     * 掲示内容を登録する。
     *
     * @param updateDto 掲示内容
     */
    void registerBoard(@Param("updateDto") BoardDetailDto updateDto);

    /*
     * 掲示内容を更新する。
     *
     * @param updateDto 掲示内容
     */
    void updateBoard(@Param("updateDto") BoardDetailDto updateDto);
    
    /*
     * 掲示情報を削除する。
     *
     * @param deleteDto 掲示情報削除条件
     * @return 件数
     */
    void deleteBoard(@Param("deleteDto") BoardDeleteDto deleteDto);

    /*
     * コメントを登録する。
     *
     * @param commentDto 掲示板検索変数
     */
    void commentUpdate(@Param("commentDto") BoardCommentDto commentDto);

    /*
     * コメントを削除する。
     *
     * @param deleteDto 掲示情報削除条件
     */
    void deleteComment(@Param("deleteDto") BoardDeleteDto deleteDto);
}


