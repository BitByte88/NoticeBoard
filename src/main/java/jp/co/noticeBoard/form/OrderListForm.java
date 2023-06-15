package jp.co.noticeBoard.form;

import lombok.Data;

@Data
public class OrderListForm {

	
    /**
     *掲示文番号
     */
	private String boardId;

    /**
     *掲示文タイトル
     */
	private String boardTitle;

    /**
     *掲示文内容
     */
	private String boardContent;

    /**
     *掲示文作成者
     */
	private String registerUserId;

    /**
     *掲示文作成日時
     */
	private String registerDate;

    /**
     *（検索用）タイプ
     */
	private String searchType;

    /**
     *（検索用）キーワード
     */
	private String searchKeyword;

    /**
     *オフセット
     */
    private Integer offset;
}
