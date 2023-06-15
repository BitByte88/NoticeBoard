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

import jp.co.noticeBoard.dto.BoardCommentDto;
import jp.co.noticeBoard.dto.BoardDeleteDto;
import jp.co.noticeBoard.dto.OrderDetailDto;
import jp.co.noticeBoard.service.BoardDetailService;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.SessionManager;

@Controller
@RequestMapping("/BO/boardDetail")
public class BoardDetailController {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BoardDetailService boardDetailService;

	/**
	 * 注文詳細表示（初期表示）
	 *
	 * @param model モデル
	 * @param locale  ロケール
	 * @return 画面パス
	 */
	@RequestMapping("")
	public String boardDetail(HttpServletRequest request, Model model, Locale locale) throws Exception {



        //エラーメッセージリスト
        List<String> messageList = new ArrayList<>();

        //注文状況一覧画面から遷移用、注文番号取得
        String orderNo = request.getParameter("intoOrderNo");
        
		//コメント登録用 掲示文No取得処理
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		Map<String, Object> params = new HashMap<>();
		if (flashMap != null) {
			params = (Map<String, Object>) flashMap.get("params");
			orderNo = (String) params.get("orderNo");
			if ((ArrayList) params.get("messageList") != null) {
				messageList = (ArrayList) params.get("messageList");
			}
		}

        //掲示板情報リスト取得
        List<OrderDetailDto> orderDetailList = new ArrayList<OrderDetailDto>();
        orderDetailList = boardDetailService.getOrderDetailList(orderNo);

        if(orderDetailList.size()==0){
            return "redirect:/error";
        }

        model.addAttribute("orderDetailList", orderDetailList);
        
        // ログインユーザーIDと作成者IDが一致している場合、「修正」、「削除」ボタンを表示
        if (sessionManager.getSesUserInfo().getName().equals(orderDetailList.get(0).getRegisterUserId())) {
        	model.addAttribute("displayEditButtonFlg", "1");
        }

        //現在時間格納
        sessionManager.setSesTime();

        if(!orderNo.isEmpty()) {
    		// コメントリスと取得
    		List<BoardCommentDto> boardCommentList = new ArrayList<BoardCommentDto>();
    		boardCommentList = boardDetailService.getCommentList(orderNo);
    		
    		// コメントリスト格納
    		model.addAttribute("boardCommentList", boardCommentList);
    		
    		// 閲覧数カウントアップ
        	boardDetailService.updateViewCount(orderNo);
        }

        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
        }

        return "views/admin_order_detail";

	}

	/**
	 * 掲示文一覧表示（戻る）
	 *
	 * @return 画面パス
	 */
	@RequestMapping("/returnBoardList")
	public String orderDetailBackButton(HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {

        //復員変数設定
        Map<String,Object> params = new HashMap<>();
        String restorationJudgment = request.getParameter("restorationJudgment");
        params.put("restorationJudgment", restorationJudgment);
        redirectAttributes.addFlashAttribute("params", params);

        return "redirect:/BO/orderList/search";
    }

	/**
	 * 注文詳細表示（更新）
	 * @param model モデル
	 * @param locale　ロケール
	 * @return 画面パス
	 */
	@RequestMapping("/update")
	public String orderupdate(HttpServletRequest request, Model model, Locale locale, 
			RedirectAttributes redirectAttributes) throws Exception {

		List<String> messageList = new ArrayList<>();
		
		String boardId =  request.getParameter("boardId");

		if (messageList.size() != 0) {
			model.addAttribute("messageList", messageList);
		}

        //注文詳細リスト取得
        List<OrderDetailDto> BoardUpdateList = new ArrayList<OrderDetailDto>();
        BoardUpdateList = boardDetailService.getOrderDetailList(boardId);

        if(BoardUpdateList.size()==0){
            return "redirect:/error";
        }
		
		model.addAttribute("registerFlg", "2");
		model.addAttribute("orderDetailList", BoardUpdateList);

		return "views/boardEditRegister";

	}

    /**
     * 掲示文削除
     * @param updateDto 更新情報
     * @param model モデル
     * @param locale　ロケール
     * @return 画面パス
     */
    @RequestMapping("/delete")
    public String boardDelete(HttpServletRequest request, @ModelAttribute BoardDeleteDto deleteDto, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {

        List<String> messageList = new ArrayList<>();

        //作成者チェック
    	// ログインユーザーと掲示文作成者が一致しない場合
    	if(!sessionManager.getSesUserInfo().getName().equals(deleteDto.getRegisterUserId())) {
    	
    		messageList.add(messageSource.getMessage("E0006", new Object[]{}, locale));
	        logger.error(messageSource.getMessage("E0006", new Object[]{}, locale));
	        Map<String,Object> params = new HashMap<>();
	        params.put("messageList", messageList);
	        params.put("orderNo", deleteDto.getBoardId());
	        redirectAttributes.addFlashAttribute("params", params);

	        return "redirect:/BO/boardDetail/";
    	}

		//削除対象設定
		deleteDto.setBoardId(deleteDto.getBoardId());
		deleteDto.setRegisterUserId(sessionManager.getSesUserInfo().getName());
        
        //削除処理
        boardDetailService.deleteBoard(deleteDto);

        Map<String,Object> params = new HashMap<>();
        String restorationJudgment = request.getParameter("restorationJudgment");
        params.put("restorationJudgment", restorationJudgment);
        redirectAttributes.addFlashAttribute("params", params);

        return "redirect:/BO/orderList/search";
    }

	/**
	 * 掲示文登録
	 * @param updateDto 更新情報
	 * @param model モデル
	 * @param locale　ロケール
	 * @return 画面パス
	 */
	@RequestMapping("/write")
	public String boardwrite(Model model, Locale locale,
			RedirectAttributes redirectAttributes) throws Exception {

		List<String> messageList = new ArrayList<>();

		if (messageList.size() != 0) {
			model.addAttribute("messageList", messageList);
		}

		model.addAttribute("registerFlg", "1");
		return "views/boardEditRegister";

	}

    /**
     * コメント登録
     * @param updateDto 更新情報
     * @param model モデル
     * @param locale　ロケール
     * @return 画面パス
     */
    @RequestMapping("/commentwrite")
    public String commentwrite(@ModelAttribute BoardCommentDto commentDto, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {

        List<String> messageList = new ArrayList<>();

        // コメント入力チェック
		if(commentDto.getCommentContent() == null || commentDto.getCommentContent().equals("")){
		    messageList.add(messageSource.getMessage("E0007", new Object[]{}, locale));
		    logger.error(messageSource.getMessage("E0007", new Object[]{}, locale));
		    Map<String,Object> params = new HashMap<>();
		    params.put("messageList", messageList);
		    params.put("orderNo", commentDto.getBoardId());
		    redirectAttributes.addFlashAttribute("params", params);
		    return "redirect:/BO/boardDetail/";
		}

		//作成者名設定
		commentDto.setCommentRegisterUserId(sessionManager.getSesUserInfo().getName());

		// コメント情報更新
		boardDetailService.commentUpdate(commentDto);
		
		//注文Noリダイレクト
		Map<String,Object> params = new HashMap<>();
		params.put("orderNo", commentDto.getBoardId());
		redirectAttributes.addFlashAttribute("params", params);
		
        return "redirect:/BO/boardDetail/";

    }


}