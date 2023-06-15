package jp.co.noticeBoard.entitiy;

import java.util.Date;

import lombok.Data;
/*注文テーブル*/
@Data
public class TblOrder {

	private Integer boardId;
	private String boardTitle;
	private String boardContent;
	private Integer viewCount;
	private String registerUserId;
	private Date registerDate;
	private String updateUserId;
	private Date UpdateDate;
	private Integer deleteFlag;

}
