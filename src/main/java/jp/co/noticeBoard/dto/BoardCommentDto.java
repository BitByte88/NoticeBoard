package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class BoardCommentDto {

    /**
     *掲示情報ID
     */
	private String boardId;

    /**
     *コメント内容
     */
	private String commentContent;

    /**
     *コメント作成者
     */
	private String commentRegisterUserId;

    /**
     *コメント作成日時
     */
	private String commentRegisterDate;

}
