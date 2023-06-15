package jp.co.noticeBoard.dao.mapper;

import java.util.List;

import jp.co.noticeBoard.entitiy.TblOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.noticeBoard.dto.OrderListSearchDto;

/**
 * シンダータイプ判別 Mapperクラス.
 */

@Mapper
public interface OrderListMapper {

    /**
     * 注文件数取得.
     *
     * @param orderListSearchDto 注文状況検索変数
     * @return 件数
     */
    Integer getOrderListCount(@Param("orderListSearchDto") OrderListSearchDto orderListSearchDto);

    /**
     * 注文情報取得.
     *
     * @param orderListSearchDto 注文状況検索変数
     * @param limit 1ページに表示される注文件数
     * @return 注文リスト
     */
    List<TblOrder> getOrderList(@Param("orderListSearchDto") OrderListSearchDto orderListSearchDto, @Param("limit") Integer limit);

}
