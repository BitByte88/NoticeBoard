package jp.co.noticeBoard.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.noticeBoard.dto.BoardCommentDto;
import jp.co.noticeBoard.dto.BoardDeleteDto;
import jp.co.noticeBoard.dto.BoardDetailDto;

/**
 * 掲示文詳細取得 Mapperクラス.
 */

@Mapper
public interface BoardDetailMapper {

    /*
     * 掲示文情報を取得する。.
     *
     * @param boardNo 掲示板検索変数
     * @return 件数
     */
    List<BoardDetailDto> getBoardDetailList(@Param("boardNo") String boardNo);

    /*
     * 掲示文情報を取得する。.
     *
     * @param boardNo コメント検索変数
     * @return コメントリスト
     */
    List<BoardCommentDto> getCommentList(@Param("boardNo") String boardNo);

    /*
     * 閲覧数をカウントアップする。
     *
     * @param boardNo 掲示文No
     * @return 件数
     */
    void updateViewCount(@Param("boardNo") String boardNo);    
    
    /*
     * 掲示文を登録する。
     *
     * @param updateDto 掲示文情報
     */
    void registerBoard(@Param("updateDto") BoardDetailDto updateDto);

    /*
     * 掲示文を更新する。
     *
     * @param updateDto 掲示文情報
     */
    void updateBoard(@Param("updateDto") BoardDetailDto updateDto);
    
    /*
     * 掲示文を削除する。
     *
     * @param deleteDto 注文状況検索変数
     * @return 件数
     */
    void deleteBoard(@Param("deleteDto") BoardDeleteDto deleteDto);

    /*
     * コメントを登録する。
     *
     * @param commentDto 掲示板検索変数
     */
    void commentUpdate(@Param("commentDto") BoardCommentDto commentDto);

}


