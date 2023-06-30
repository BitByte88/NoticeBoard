package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class PageDto {

    /*
     * オフセット
     */
    private Integer offset;

    /*
     * 現在ページ
     */
    private Integer currentPage ;

    /*
     * ページネーションページ
     */
    private Integer paginationStart;

    /*
     * 最大ページ
     */
    private Integer totalPage;

    /*
     * 掲示情報件数
     */
    private Integer count;
    /*
     * 1ページに表示する件数
     */
    private Integer pageLimit;

    /*
     * ページャの数字ボタン数
     */
    private Integer btnCount;

}
