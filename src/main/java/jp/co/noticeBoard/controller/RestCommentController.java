package jp.co.noticeBoard.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.noticeBoard.common.Const;
import jp.co.noticeBoard.dto.BoardCommentDto;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.RestCommentService;
import jp.co.noticeBoard.service.SessionManager;

@RestController
public class RestCommentController {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private RestCommentService restCommentService;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private MessageSource messageSource;

    /**
     * コメント情報リスト取得
     *
     * @param boardId 掲示情報ID
     * @return boardCommentList
     */
    @RequestMapping("/boardDetail/comments/{boardId}")
    public List<BoardCommentDto> getCommentList(@PathVariable final String boardId) throws Exception {

        // コメント情報リスト取得
        List<BoardCommentDto> boardCommentList = restCommentService.getCommentList(boardId);

        return boardCommentList;
    }

    /**
     * コメント登録
     *
     * @param params コメントDto
     * @return true
     */
    @RequestMapping("/boardDetail/writeComment")
    public List writeComment(@RequestBody BoardCommentDto params) throws Exception {
    	
        List<String> messageList = new ArrayList<>();

        // コメント入力チェック
		if(params.getCommentContent() == null || params.getCommentContent().equals("")){
		    messageList.add(messageSource.getMessage("E00007", new Object[]{}, null));
		    logger.error(messageSource.getMessage("E00007", new Object[]{}, null));

		}
        // コメント桁数チェック
        if(params.getCommentContent().length() > Const.MAX_COMMENT_LENGTH){
            String noteLabel = messageSource.getMessage("label.boardDetail.comment",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00006", new Object[]{noteLabel, Const.MAX_COMMENT_LENGTH}, null));
            logger.error(messageSource.getMessage("E00006", new Object[]{noteLabel, Const.MAX_COMMENT_LENGTH}, null));
        }

        // 上記のチェックでエラーが存在する場合
        if(messageList.size()!=0){
        	return messageList;
        }

        //作成者設定
        params.setCommentRegisterUserId(sessionManager.getSesUserInfo().getUserId());
        //改行変換
        params.setCommentContent(params.getCommentContent().replace(Const.CRLF, Const.CR));

        // コメント情報更新
        restCommentService.writeComment(params);

        return messageList;
    }
}
