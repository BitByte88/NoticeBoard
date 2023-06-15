package jp.co.noticeBoard.dao.mapper;

import java.util.List;

import jp.co.noticeBoard.dto.BoardCommentDto;
import jp.co.noticeBoard.dto.BoardDeleteDto;
import jp.co.noticeBoard.dto.OrderDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * シンダータイプ判別 Mapperクラス.
 */

@Mapper
public interface OrderDetailMapper {

    /*
     * 注文情報を取得する。.
     *
     * @param orderNo 注文状況検索変数
     * @return 件数
     */
    List<OrderDetailDto> getOrderDetailList(@Param("orderNo") String orderNo);

    /*
     * 注文情報を取得する。.
     *
     * @param orderNo 注文状況検索変数
     * @return 件数
     */
    List<BoardCommentDto> getCommentList(@Param("orderNo") String orderNo);

    /*
     * 閲覧数をカウントアップする。
     *
     * @param orderNo 掲示文No
     * @return 件数
     */
    void updateViewCount(@Param("orderNo") String orderNo);    
    
    /*
     * 掲示文を登録する。
     *
     * @param OrderDetailDto 掲示文情報
     */
    void registerBoard(@Param("updateDto") OrderDetailDto updateDto);

    /*
     * 掲示文を更新する。
     *
     * @param OrderDetailDto 掲示文情報
     */
    void updateBoard(@Param("updateDto") OrderDetailDto updateDto);
    
    /*
     * 注文情報を更新する。
     *
     * @param orderNo 注文状況検索変数
     * @return 件数
     */
    void deleteBoard(@Param("deleteDto") BoardDeleteDto deleteDto);

    /*
     * コメント情報を更新する。
     *
     * @param orderNo 注文状況検索変数
     * @return 件数
     */
    void commentUpdate(@Param("commentDto") BoardCommentDto commentDto);

}


