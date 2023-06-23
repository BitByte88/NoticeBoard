package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class BoardDeleteDto {

    /**
     *掲示情報ID
     */
	private String boardId;

    /**
     *更新者
     */
	private String updateUserId;

}
