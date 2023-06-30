package jp.co.noticeBoard.controller;

import java.util.ArrayList;
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
	 * 掲示情報一覧（初期表示）
	 *
	 * @return 画面パス
	 * @throws Exception 
	 */
	@RequestMapping("")
	public String boardList(HttpServletRequest request, Locale locale, Model model) throws Exception {

		//検索条件DTO
		BoardListSearchDto boardListsearchDto = new BoardListSearchDto();
		boardListsearchDto.setOffset(0);
		//掲示情報の検索条件格納
		sessionManager.setSesBoardListSearchInfo(boardListsearchDto);

		//検索条件に合致する掲示情報の件数を取得する。
		Integer count = boardListService.getBoardListCount(boardListsearchDto);
		//検索結果の件数が0件の場合
		if (count == 0) {
			//エラーメッセージリスト
			List<String> messageList = new ArrayList<>();
		    messageList.add(messageSource.getMessage("E00008", new Object[]{}, locale));
			logger.error(messageSource.getMessage("E00008", new Object[]{}, locale));
		    sessionManager.setSesBoardListSearchInfo(boardListsearchDto);
			model.addAttribute("messageList", messageList);
		
		    return "views/board_list";
		}

		//掲示情報リスト取得
		List<Tblboard> list = boardListService.getBoardList(boardListsearchDto);
		//画面表示項目設定
		List<BoardListDto> boardList = boardListService.boardListConversion(list);
		//ページング情報取得
		PageDto pageDto = boardListService.changeOffset(count, 0);

		//ページネーション初期値
		pageDto.setPaginationStart(1);

		//検索条件Dto格納
		model.addAttribute("boardListsearchDto", boardListsearchDto);
		//掲示情報リスト格納
		model.addAttribute("boardList", boardList);
		//画面表示_ページング
		model.addAttribute("pager",pageDto);

		return "views/board_list";

	}

	/**
	 * 掲示情報一覧（検索）
	 *
	 * @param boardListsearchDto 掲示情報一覧の検索条件Dto
	 * @param model　モデル
	 * @return 画面パス
	 */
	@RequestMapping("/search")
	public String boardListSearch(HttpServletRequest request, @ModelAttribute BoardListSearchDto boardListsearchDto, Model model) throws Exception {

		//エラーメッセージリスト
		List<String> messageList = new ArrayList<>();

		//画面復元判定
		String restorationJudgment = "init";
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			Map<String, Object> params = (Map<String, Object>) flashMap.get("params");
			restorationJudgment = (String) params.get("restorationJudgment");
		}

		//画面復元判定フラグが「RESTORATION」の場合
		if (restorationJudgment.equals("restoration")) {
			//セッションから検索条件取得
			boardListsearchDto = sessionManager.getSesBoardListSearchInfo();
		}

		//検索条件に合致する掲示情報の件数を取得する。
		Integer count = boardListService.getBoardListCount(boardListsearchDto);
		//検索結果の件数が0件の場合
		if (count == 0) {
            messageList.add(messageSource.getMessage("E00009", new Object[]{}, null));
			logger.error(messageSource.getMessage("E00009", new Object[]{}, null));
			sessionManager.setSesBoardListSearchInfo(boardListsearchDto);
			model.addAttribute("messageList", messageList);
			model.addAttribute("boardListsearchDto", boardListsearchDto);
			return "views/board_list";
		}

		//掲示情報リスト取得
		List<Tblboard> list = boardListService.getBoardList(boardListsearchDto);
		//画面表示項目設定
		List<BoardListDto> boardList = boardListService.boardListConversion(list);
		//ページング情報取得
		PageDto pageDto = boardListService.changeOffset(count, boardListsearchDto.getOffset());

		//ページネーション設定
		int startPage = ((pageDto.getCurrentPage() - 1) / pageDto.getBtnCount()) * pageDto.getBtnCount() + 1; 
		pageDto.setPaginationStart(startPage);
				
		//掲示情報の検索条件格納
		sessionManager.setSesBoardListSearchInfo(boardListsearchDto);

		//検索条件Dto格納
		model.addAttribute("boardListsearchDto", boardListsearchDto);
		//掲示情報リスト格納
		model.addAttribute("boardList", boardList);
		//画面表示_ページング
		model.addAttribute("pager", pageDto);

		return "views/board_list";
	}
}