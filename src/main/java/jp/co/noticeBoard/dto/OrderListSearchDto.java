package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class OrderListSearchDto {

    /**
     *オフセット
     */
    private Integer offset;

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
     *（検索用）タイプ
     */	
	private String searchType;
	
	/**
     *（検索用）キーワード
     */
	private String searchKeyword;
	


}
