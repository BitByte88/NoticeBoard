package jp.co.noticeBoard.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.noticeBoard.dto.BoardListSearchDto;
import jp.co.noticeBoard.entitiy.Tblboard;

/**
 * 掲示板リスト取得 Mapperクラス.
 */

@Mapper
public interface BoardListMapper {

    /**
     * 掲示文件数取得.
     *
     * @param boardListSearchDto 掲示板検索変数
     * @return 件数
     */
    Integer getBoardListCount(@Param("boardListSearchDto") BoardListSearchDto boardListSearchDto);

    /**
     * 掲示文情報取得.
     *
     * @param boardListSearchDto 掲示板検索変数
     * @param limit 1ページに表示される注文件数
     * @return 掲示文リスト
     */
    List<Tblboard> getBoardList(@Param("boardListSearchDto") BoardListSearchDto boardListSearchDto, @Param("limit") Integer limit);

}