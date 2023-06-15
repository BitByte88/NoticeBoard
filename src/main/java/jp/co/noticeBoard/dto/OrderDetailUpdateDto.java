package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class OrderDetailUpdateDto {

    /*
     *ユーザーNo
     */
    private Integer userNo;

    /*
     *注文No
     */
    private String orderNo;

    /*
     *注文ステータス
     */
    private Integer orderStatus;

    /*
     *注文明細ステータス
     */
    private Integer orderDetailStatus;

    /*
     *備考
     */
    private String note;
}
