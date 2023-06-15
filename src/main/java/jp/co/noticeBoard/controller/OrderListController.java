package jp.co.noticeBoard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import jp.co.noticeBoard.common.Const;
import jp.co.noticeBoard.dto.OrderListDto;
import jp.co.noticeBoard.dto.OrderListSearchDto;
import jp.co.noticeBoard.dto.PageDto;
import jp.co.noticeBoard.entitiy.TblOrder;
import jp.co.noticeBoard.form.OrderListForm;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.OrderListService;
import jp.co.noticeBoard.service.SessionManager;

@Controller
@RequestMapping("/BO/orderList")
public class OrderListController {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private OrderListService orderListService;

	/**
	 * 掲示文一覧（初期表示）
	 *
	 * @return 画面パス
	 * @throws Exception 
	 */
	@RequestMapping("")
	public String orderList(HttpServletRequest request, Locale locale, Model model) throws Exception {

		//注文状況一覧検索条件Formの初期設定
		OrderListForm orderListForm = orderListService.initOrderList();

		//セッション「注文状況検索条件」格納
		sessionManager.setSesOrderListSearchInfo(orderListForm);
		//現在時間格納
		sessionManager.setSesTime();

		//エラーメッセージリストorderListSearchD	to
		List<String> messageList = new ArrayList<>();
		
		//検索条件DTO
		OrderListSearchDto orderListsearchDto = new OrderListSearchDto();
		PageDto pageDto = new PageDto();
		
		// 初期表示用
		orderListForm.setOffset(0);
		
		//検索条件DTO生成
		orderListsearchDto = orderListService.getSearchDto(orderListForm);
		//条件に合わせた注文の件数を取得する。
		Integer count = orderListService.getOrderListCount(orderListsearchDto);
		//検索した注文件数が０の場合
		if (count == 0) {
		    messageList.add(messageSource.getMessage("E00009", new Object[]{}, locale));
		    model.addAttribute("messageList", messageList);
		    sessionManager.setSesOrderListSearchInfo(orderListForm);
		    logger.error(messageSource.getMessage("E00009", new Object[]{}, locale));
		
		    return "views/admin_order_list";
		}
		
		//ページング情報取得
		pageDto = orderListService.changeOffset(count,orderListForm.getOffset());
		
		//掲示文リスト取得
		List<TblOrder> list = new ArrayList<>();
		list = orderListService.getOrderList(orderListsearchDto);
		List<OrderListDto> orderList = new ArrayList<>();
		
		//画面表示用文字列取得
		orderList = orderListService.orderListConversion(list,locale);
		
		//注文リスト格納
		model.addAttribute("orderList", orderList);
		
		//注文状況検索条件格納
		sessionManager.setSesOrderListSearchInfo(orderListForm);
		
		//現在時間格納
		sessionManager.setSesTime();
		
		//画面表示_ページング
		model.addAttribute("pager",pageDto);

		return "views/admin_order_list";

	}

	/**
	 * 掲示文一覧（検索）
	 *
	 * @param orderListForm 注文状況一覧画面のForm
	 * @param locale ロケール
	 * @param model　モデル
	 * @return 画面パス
	 */

	@RequestMapping("/search")
	public String orderListSearch(HttpServletRequest request, @ModelAttribute OrderListForm orderListForm,
			Locale locale, Model model) throws Exception {


		//エラーメッセージリスト
		List<String> messageList = new ArrayList<>();

		//検索条件DTO
		OrderListSearchDto orderListsearchDto = new OrderListSearchDto();
		PageDto pageDto = new PageDto();

		//画面復元判定
		String restorationJudgment = "init";
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		Map<String, Object> params = new HashMap<>();
		if (flashMap != null) {
			params = (Map<String, Object>) flashMap.get("params");
			restorationJudgment = (String) params.get("restorationJudgment");
		}

		//画面復元判定フラグが「RESTORATION」の場合
		if (restorationJudgment.equals("restoration")) {
			//セッションから検索条件取得
			orderListForm = sessionManager.getSesOrderListSearchInfo();
		} else { //画面復元判定フラグが「RESTORATION」以外の場合
			//検索条件チェック
			messageList = orderListService.searchInputCheck(orderListForm, locale);
			if (!messageList.isEmpty()) {
				sessionManager.setSesOrderListSearchInfo(orderListForm);
				model.addAttribute("messageList", messageList);
				return "views/admin_order_list";
			}
		}
		//検索条件DTO生成
		orderListsearchDto = orderListService.getSearchDto(orderListForm);
		//条件に合わせた注文の件数を取得する。
		Integer count = orderListService.getOrderListCount(orderListsearchDto);
		//検索した注文件数が０の場合
		if (count == 0) {
            messageList.add(messageSource.getMessage("E00009", new Object[]{}, locale));
			model.addAttribute("messageList", messageList);
			sessionManager.setSesOrderListSearchInfo(orderListForm);
            logger.error(messageSource.getMessage("E00009", new Object[]{}, locale));

			return "views/admin_order_list";
		}

		//ページング情報取得
		pageDto = orderListService.changeOffset(count, orderListForm.getOffset());

		//掲示文リスト取得
		List<TblOrder> list = new ArrayList<>();
		list = orderListService.getOrderList(orderListsearchDto);
		List<OrderListDto> orderList = new ArrayList<>();

		//画面表示用文字列取得
		orderList = orderListService.orderListConversion(list, locale);

		//注文リスト格納
		model.addAttribute("orderList", orderList);

		//注文状況検索条件格納
		sessionManager.setSesOrderListSearchInfo(orderListForm);

		//現在時間格納
		sessionManager.setSesTime();

		//画面表示_ページング
		model.addAttribute("pager", pageDto);

		return "views/admin_order_list";
	}

	/**
	 * 掲示文一覧（戻る）
	 *
	 * @param session セッション
	 * @return 画面パス
	 */
	@RequestMapping("/back")
	public String back(HttpSession session) {
		//検索条件削除
		session.removeAttribute(Const.SES_ORDER_SEARCH_INFO);
		return "redirect:/BO/portal";
	}

}