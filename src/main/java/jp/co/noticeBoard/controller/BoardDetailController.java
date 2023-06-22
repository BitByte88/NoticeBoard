package jp.co.noticeBoard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jp.co.noticeBoard.common.Const;
import jp.co.noticeBoard.dto.BoardCommentDto;
import jp.co.noticeBoard.dto.BoardDeleteDto;
import jp.co.noticeBoard.dto.BoardDetailDto;
import jp.co.noticeBoard.service.BoardDetailService;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.SessionManager;

@Controller
@RequestMapping("/boardDetail")
public class BoardDetailController {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BoardDetailService boardDetailService;

	/**
	 * 掲示情報の詳細表示（初期表示）
	 *
	 * @param HttpServletRequest
	 * @param model モデル
	 * @return 画面パス
	 */
	@RequestMapping("")
	public String boardDetail(HttpServletRequest request, Model model) throws Exception {



        //エラーメッセージリスト
        List<String> messageList = new ArrayList<>();

        //一覧画面から遷移用、掲示No取得
        String boardNo = request.getParameter("intoBoardNo");
        
		//コメント登録後、詳細画面再表示用 掲示No取得処理
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		Map<String, Object> params = new HashMap<>();
		if (flashMap != null) {
			params = (Map<String, Object>) flashMap.get("params");
			boardNo = (String) params.get("boardNo");
			if ((ArrayList) params.get("messageList") != null) {
				messageList = (ArrayList) params.get("messageList");
			}
		}

        //掲示情報取得
        BoardDetailDto boardDetail = new BoardDetailDto();
        boardDetail = boardDetailService.getBoardDetail(boardNo);

        if(boardDetail == null){
            return "redirect:/error";
        }

        model.addAttribute("boardDetail", boardDetail);
        

        // ログイン中のみチェック
        if(sessionManager.getSesUserInfo() != null) {

        	// ログインユーザーIDと作成者IDが一致している場合、「修正」、「削除」ボタンを表示
        	if (sessionManager.getSesUserInfo().getUserId().equals(boardDetail.getRegisterUserId())) {
        		model.addAttribute("displayEditButtonFlg", "1");
        	}
        }

        if(!boardNo.isEmpty()) {
    		// コメントリスと取得
    		List<BoardCommentDto> boardCommentList = new ArrayList<BoardCommentDto>();
    		boardCommentList = boardDetailService.getCommentList(boardNo);
    		
    		// コメントリスト格納
    		model.addAttribute("boardCommentList", boardCommentList);
    		
    		// 閲覧数カウントアップ
        	boardDetailService.updateViewCount(boardNo);
        }

        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
        }

        return "views/board_detail";

	}

	/**
	 * 掲示情報一覧表示（戻る）
	 *
	 * @param HttpServletRequest
	 * @param RedirectAttributes
	 * @return 画面パス
	 */
	@RequestMapping("/returnBoardList")
	public String boardDetailBackButton(HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {

        //復員変数設定
        Map<String,Object> params = new HashMap<>();
        String restorationJudgment = request.getParameter("restorationJudgment");
        params.put("restorationJudgment", restorationJudgment);
        redirectAttributes.addFlashAttribute("params", params);

        return "redirect:/boardList/search";
    }

	/**
	 * 掲示情報の修正表示（更新）
	 * @param HttpServletRequest
	 * @param model　モデル
	 * @return 画面パス
	 */
	@RequestMapping("/update")
	public String boardupdate(HttpServletRequest request, Model model) throws Exception {

		List<String> messageList = new ArrayList<>();
		
		String boardId =  request.getParameter("boardId");

		if (messageList.size() != 0) {
			model.addAttribute("messageList", messageList);
		}

        //掲示詳細情報リスト取得
        BoardDetailDto updateDto = new BoardDetailDto();
        updateDto = (BoardDetailDto) boardDetailService.getBoardDetail(boardId);

        if(updateDto == null){
            return "redirect:/error";
        }

		model.addAttribute("registerFlg", "2");
		model.addAttribute("updateDto", updateDto);

		return "views/board_EditRegister";

	}

    /**
     * 掲示情報削除
     * @param updateDto 更新情報
     * @return 画面パス
     */
    @RequestMapping("/delete")
    public String boardDelete(@ModelAttribute BoardDeleteDto deleteDto) throws Exception {

		//削除対象設定
		deleteDto.setBoardId(deleteDto.getBoardId());
		deleteDto.setUpdateUserId(sessionManager.getSesUserInfo().getUserId());
        
        //掲示情報削除
        boardDetailService.deleteBoard(deleteDto);

        //コメント削除
        boardDetailService.deleteComment(deleteDto);

        return "redirect:/boardList";
    }


    /**
     * コメント登録
     * @param commentDto コメントDto
     * @param locale　ロケール
     * @param RedirectAttributes
     * @return 画面パス
     */
    @RequestMapping("/commentwrite")
    public String commentwrite(@ModelAttribute BoardCommentDto commentDto, Locale locale, RedirectAttributes redirectAttributes) throws Exception {

        List<String> messageList = new ArrayList<>();

        // コメント入力チェック
		if(commentDto.getCommentContent() == null || commentDto.getCommentContent().equals("")){
		    messageList.add(messageSource.getMessage("E00007", new Object[]{}, locale));
		    logger.error(messageSource.getMessage("E00007", new Object[]{}, locale));

		}

        // コメント桁数チェック
        if(commentDto.getCommentContent().length() > Const.MAX_COMMENT_LENGTH){
            String noteLabel = messageSource.getMessage("label.boardDetail.comment",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00006", new Object[]{noteLabel, Const.MAX_COMMENT_LENGTH}, locale));
            logger.error(messageSource.getMessage("E00006", new Object[]{noteLabel, Const.MAX_COMMENT_LENGTH}, locale));
        }
		
        // 上記のチェックでエラーが存在する場合
        if(messageList.size()!=0){
		    Map<String,Object> params = new HashMap<>();
		    params.put("messageList", messageList);
		    params.put("boardNo", commentDto.getBoardId());
		    redirectAttributes.addFlashAttribute("params", params);

		    return "redirect:/boardDetail/";
        }

		//作成者設定
		commentDto.setCommentRegisterUserId(sessionManager.getSesUserInfo().getUserId());

		//改行変換
		commentDto.setCommentContent(commentDto.getCommentContent().replace(Const.CRLF, Const.CR));

		// コメント情報更新
		boardDetailService.commentUpdate(commentDto);
		
		//掲示Noリダイレクト
		Map<String,Object> params = new HashMap<>();
		params.put("boardNo", commentDto.getBoardId());
		redirectAttributes.addFlashAttribute("params", params);
		
        return "redirect:/boardDetail/";

    }


}