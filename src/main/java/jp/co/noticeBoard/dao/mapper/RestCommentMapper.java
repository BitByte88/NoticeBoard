package jp.co.noticeBoard.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.noticeBoard.dto.BoardCommentDto;

/**
 * コメント情報を取得 Mapperクラス.
 */

@Mapper
public interface RestCommentMapper {

    /*
     * コメント情報を取得する。
     *
     * @param boardId 掲示情報ID
     * @return コメントリスト
     */
    List<BoardCommentDto> getCommentList(@Param("boardId") String boardId);

    /*
     * コメントを登録する。
     *
     * @param commentDto 掲示板検索変数
     */
    void writeComment(@Param("commentDto") BoardCommentDto commentDto);
}