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
import jp.co.noticeBoard.dto.OrderDetailDto;
import jp.co.noticeBoard.dto.OrderDetailUpdateDto;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.OrderDetailService;
import jp.co.noticeBoard.service.SessionManager;

@Controller
@RequestMapping("/BO/orderDetail")
public class OrderDetailController {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 注文詳細表示（初期表示）
     *
     * @param model モデル
     * @param locale  ロケール
     * @return 画面パス
     */
    @RequestMapping("")
    public String orderDetail(HttpServletRequest request, Model model, Locale locale) throws Exception {



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
        orderDetailList = orderDetailService.getOrderDetailList(orderNo);

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
    		boardCommentList = orderDetailService.getCommentList(orderNo);
    		
    		// コメントリスト格納
    		model.addAttribute("boardCommentList", boardCommentList);
    		
    		// 閲覧数カウントアップ
        	orderDetailService.updateViewCount(orderNo);
        }

        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
        }

        return "views/admin_order_detail";

    }

    /**
     * 注文詳細表示（戻る）
     *
     * @return 画面パス
     */
    @RequestMapping("/returnOrderList")
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
     * @param updateDto 更新情報
     * @param model モデル
     * @param locale　ロケール
     * @return 画面パス
     */
    @RequestMapping("/update")
    public String orderupdate(@ModelAttribute OrderDetailUpdateDto updateDto, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {


        List<String> messageList = new ArrayList<>();

        //備考チェック
        if(updateDto.getNote() == null || updateDto.getNote().equals("")){
            String noteLabel = messageSource.getMessage("label.note",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            Map<String,Object> params = new HashMap<>();
            params.put("messageList", messageList);
            params.put("orderNo", updateDto.getOrderNo());
            redirectAttributes.addFlashAttribute("params", params);
            return "redirect:/BO/orderDetail/";
        }
        //更新者設定
        updateDto.setUserNo(sessionManager.getSesUserInfo().getUserNo());

        //注文情報更新
        //orderDetailService.orderDetailUpdate(updateDto);

        //注文Noリダイレクト
        Map<String,Object> params = new HashMap<>();
        params.put("orderNo", updateDto.getOrderNo());
        redirectAttributes.addFlashAttribute("params", params);

        return "redirect:/BO/orderDetail/";

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
		    return "redirect:/BO/orderDetail/";
		}

		//作成者名設定
		commentDto.setCommentRegisterUserId(sessionManager.getSesUserInfo().getName());

		// コメント情報更新
		orderDetailService.commentUpdate(commentDto);
		
		//注文Noリダイレクト
		Map<String,Object> params = new HashMap<>();
		params.put("orderNo", commentDto.getBoardId());
		redirectAttributes.addFlashAttribute("params", params);
		
        return "redirect:/BO/orderDetail/";

    }

}