package jp.co.noticeBoard.form;

import lombok.Data;

import java.util.Date;

@Data
public class DetailListForm {

    /**
     *オフセット
     */
    private Integer offset;

    /**
     * 注文日（FROM)
     */
    private String fromDate;

    /**
     * 注文時刻（FROM)
     */
    private String fromTime;

    /**
     * 注文日時（FROM)
     */
    private Date fromDateTime;

    /**
     * 注文日（TO)
     */
    private String toDate;

    /**
     * 注文時刻（TO)
     */
    private String toTime;

    /**
     * 注文日時（TO)
     */
    private Date toDateTime;

    /**
     * フラグ
     */
    private String flag;

}
