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
import org.springframework.web.servlet.support.RequestContextUtils;

import jp.co.noticeBoard.dto.BoardListDto;
import jp.co.noticeBoard.dto.BoardListSearchDto;
import jp.co.noticeBoard.dto.PageDto;
import jp.co.noticeBoard.entitiy.Tblboard;
import jp.co.noticeBoard.form.BoardListForm;
import jp.co.noticeBoard.service.BoardListService;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.SessionManager;

@Controller
@RequestMapping("/boardList")
public class BoardListController {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BoardListService boardListService;

	/**
	 * 掲示文一覧（初期表示）
	 *
	 * @return 画面パス
	 * @throws Exception 
	 */
	@RequestMapping("")
	public String boardList(HttpServletRequest request, Locale locale, Model model) throws Exception {

		//注文状況一覧検索条件Formの初期設定
		BoardListForm boardListForm = boardListService.initBoardList();

		//セッション「掲示板条件」格納
		sessionManager.setSesOrderListSearchInfo(boardListForm);
		//現在時間格納
		sessionManager.setSesTime();

		//エラーメッセージリスト
		List<String> messageList = new ArrayList<>();
		
		//検索条件DTO
		BoardListSearchDto boardListsearchDto = new BoardListSearchDto();
		PageDto pageDto = new PageDto();
		
		// 初期表示用
		boardListForm.setOffset(0);
		
		//検索条件DTO生成
		boardListsearchDto = boardListService.getSearchDto(boardListForm);
		//条件に合わせた注文の件数を取得する。
		Integer count = boardListService.getBoardListCount(boardListsearchDto);
		//検索した注文件数が０の場合
		if (count == 0) {
		    messageList.add(messageSource.getMessage("E00009", new Object[]{}, locale));
		    model.addAttribute("messageList", messageList);
		    sessionManager.setSesOrderListSearchInfo(boardListForm);
		    logger.error(messageSource.getMessage("E00009", new Object[]{}, locale));
		
		    return "views/board_list";
		}
		
		//ページング情報取得
		pageDto = boardListService.changeOffset(count,boardListForm.getOffset());
		
		//掲示文リスト取得
		List<Tblboard> list = new ArrayList<>();
		list = boardListService.getBoardList(boardListsearchDto);
		List<BoardListDto> boardList = new ArrayList<>();
		
		//画面表示用文字列取得
		boardList = boardListService.boardListConversion(list,locale);
		
		//掲示文リスト格納
		model.addAttribute("boardList", boardList);
		
		//注文状況検索条件格納
		sessionManager.setSesOrderListSearchInfo(boardListForm);
		
		//現在時間格納
		sessionManager.setSesTime();
		
		//画面表示_ページング
		model.addAttribute("pager",pageDto);

		return "views/board_list";

	}

	/**
	 * 掲示文一覧（検索）
	 *
	 * @param boardListForm 掲示文一覧画面のForm
	 * @param locale ロケール
	 * @param model　モデル
	 * @return 画面パス
	 */

	@RequestMapping("/search")
	public String boardListSearch(HttpServletRequest request, @ModelAttribute BoardListForm boardListForm,
			Locale locale, Model model) throws Exception {


		//エラーメッセージリスト
		List<String> messageList = new ArrayList<>();

		//検索条件DTO
		BoardListSearchDto boardListsearchDto = new BoardListSearchDto();
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
			boardListForm = sessionManager.getSesOrderListSearchInfo();
		} else { //画面復元判定フラグが「RESTORATION」以外の場合
			//検索条件チェック
			messageList = boardListService.searchInputCheck(boardListForm, locale);
			if (!messageList.isEmpty()) {
				sessionManager.setSesOrderListSearchInfo(boardListForm);
				model.addAttribute("messageList", messageList);
				return "views/board_list";
			}
		}
		//検索条件DTO生成
		boardListsearchDto = boardListService.getSearchDto(boardListForm);
		//条件に合わせた掲示文の件数を取得する。
		Integer count = boardListService.getBoardListCount(boardListsearchDto);
		//検索した掲示文件数が０の場合
		if (count == 0) {
            messageList.add(messageSource.getMessage("E00009", new Object[]{}, locale));
			model.addAttribute("messageList", messageList);
			sessionManager.setSesOrderListSearchInfo(boardListForm);
            logger.error(messageSource.getMessage("E00009", new Object[]{}, locale));

			return "views/board_list";
		}

		//ページング情報取得
		pageDto = boardListService.changeOffset(count, boardListForm.getOffset());

		//掲示文リスト取得
		List<Tblboard> list = new ArrayList<>();
		list = boardListService.getBoardList(boardListsearchDto);
		List<BoardListDto> boardList = new ArrayList<>();

		//画面表示用文字列取得
		boardList = boardListService.boardListConversion(list, locale);

		//掲示文リスト格納
		model.addAttribute("boardList", boardList);

		//掲示文リスト条件格納
		sessionManager.setSesOrderListSearchInfo(boardListForm);

		//現在時間格納
		sessionManager.setSesTime();

		//画面表示_ページング
		model.addAttribute("pager", pageDto);

		return "views/board_list";
	}
}