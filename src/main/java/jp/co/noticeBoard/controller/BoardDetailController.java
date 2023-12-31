package jp.co.noticeBoard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	 * @param request
	 * @param model モデル
	 * @return 画面パス
	 */
	@RequestMapping("")
	public String boardDetail(HttpServletRequest request, Model model) throws Exception {

        //エラーメッセージリスト
        List<String> messageList = new ArrayList<>();

        //コメントDto（詳細画面再表示用）
        BoardCommentDto commentDto = new BoardCommentDto();

        //一覧画面から遷移用、掲示情報ID取得
        String boardId = request.getParameter("intoBoardId");

        //掲示情報取得
		BoardDetailDto boardDetail = boardDetailService.getBoardDetail(boardId);
        if(boardDetail == null){
            return "redirect:/error";
        }
		// コメント情報リスト取得
		List<BoardCommentDto> boardCommentList = boardDetailService.getCommentList(boardId);
		// 閲覧数カウントアップ
		boardDetailService.updateViewCount(boardId);
		// ログイン中のみチェック
		if(sessionManager.getSesUserInfo() != null) {
			// ログインユーザーIDと作成者IDが一致している場合、「修正」、「削除」ボタンを表示
			if (sessionManager.getSesUserInfo().getUserId().equals(boardDetail.getRegisterUserId())) {
				model.addAttribute("displayEditButtonFlg", "1");
			}
		}

		model.addAttribute("boardDetail", boardDetail);
		model.addAttribute("boardCommentList", boardCommentList);
        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
            model.addAttribute("commentDto", commentDto);
            return "views/board_detail";
        }

        //コメントDto初期化
        model.addAttribute("commentDto", new BoardCommentDto());

        return "views/board_detail";
	}

	/**
	 * 掲示情報の詳細表示（戻る）
	 *
	 * @param request
	 * @param redirectAttributes
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
	 * 掲示情報の詳細表示（修正）
	 *
	 * @param request
	 * @param model　モデル
	 * @return 画面パス
	 */
	@RequestMapping("/update")
	public String boardupdate(HttpServletRequest request, Model model) throws Exception {

		// 掲示情報ID取得
		String boardId =  request.getParameter("boardId");
		//掲示情報取得
		BoardDetailDto updateDto = boardDetailService.getBoardDetail(boardId);
        if(updateDto == null){
            return "redirect:/error";
        }

		model.addAttribute("registerFlg", "2");
		model.addAttribute("updateDto", updateDto);

		return "views/board_EditRegister";
	}

    /**
     * 掲示情報の詳細表示（削除）
	 *
     * @param deleteDto 更新情報
     * @return 画面パス
     */
    @RequestMapping("/delete")
    public String boardDelete(@ModelAttribute BoardDeleteDto deleteDto) throws Exception {

		//掲示情報削除
		deleteDto.setUpdateUserId(sessionManager.getSesUserInfo().getUserId());
        boardDetailService.deleteBoard(deleteDto);
        //コメント削除
        boardDetailService.deleteComment(deleteDto);

        return "redirect:/boardList";
    }
}