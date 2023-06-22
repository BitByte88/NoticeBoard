package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class BoardListSearchDto {

    /**
     *オフセット
     */
    private Integer offset;

	/**
     *（検索用）タイプ
     */	
	private String searchType;
	
	/**
     *（検索用）キーワード
     */
	private String searchKeyword;
	


}
