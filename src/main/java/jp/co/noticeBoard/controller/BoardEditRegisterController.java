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

import jp.co.noticeBoard.dto.BoardDetailDto;
import jp.co.noticeBoard.service.BoardDetailService;
import jp.co.noticeBoard.service.BoardEditRegisterService;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.SessionManager;

@Controller
@RequestMapping("/boardEditRegister")
public class BoardEditRegisterController {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BoardDetailService boardDetailService;

    @Autowired
    private BoardEditRegisterService boardEditRegisterService;

    /**
     * 作成修正画面表示（初期表示）
     *
     * @param model モデル
     * @param locale  ロケール
     * @return 画面パス
     */
    @RequestMapping("")
    public String boardEditRegister(@ModelAttribute BoardDetailDto updateDto, HttpServletRequest request,Model model, Locale locale) throws Exception {

		//エラーメッセージリスト
		List<String> messageList = new ArrayList<>();

		//requestからエラー情報取得
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		Map<String, Object> params = new HashMap<>();
		if (flashMap != null) {
			params = (Map<String, Object>) flashMap.get("params");
			if ((ArrayList) params.get("messageList") != null) {
				messageList = (ArrayList) params.get("messageList");
			}
		}

		if(messageList.size()!=0){
		    model.addAttribute("messageList", messageList);
		}
		
		updateDto = new BoardDetailDto();
		model.addAttribute("updateDto", updateDto);

        return "views/board_EditRegister";

    }

    /**
     * 作成修正画面表示（戻る）
     *
     * @return 画面パス
     */
    @RequestMapping("/returnBoardEdit")
    public String boardDetailBackButton(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {

        //エラーメッセージリスト
        List<String> messageList = new ArrayList<>();

    	String boardNo = request.getParameter("intoOrderNo");
    	
		//画面遷移用 掲示文No取得処理
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		Map<String, Object> params = new HashMap<>();
		if (flashMap != null) {
			params = (Map<String, Object>) flashMap.get("params");
			boardNo = (String) params.get("boardNo");
			if ((ArrayList) params.get("messageList") != null) {
				messageList = (ArrayList) params.get("messageList");
			}
		}
        
        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
        }

    	//掲示情報リスト取得
        List<BoardDetailDto> BoardUpdateList = new ArrayList<BoardDetailDto>();
        BoardUpdateList = boardDetailService.getBoardDetailList(boardNo);

    	// 新規作成の場合
    	if (boardNo == null || boardNo.equals(""))
    	{

    		model.addAttribute("registerFlg", "1");
    		model.addAttribute("boardDetailList", BoardUpdateList);

    	// 修正の場合
    	} else {
    		model.addAttribute("registerFlg", "2");
    		model.addAttribute("boardDetailList", BoardUpdateList);
    	}


        return "views/board_EditRegister";
    }

    /**
     * 登録・修正処理を行う。
     * @param updateDto 更新情報
     * @param model モデル
     * @param locale　ロケール
     * @return 画面パス
     */
    @RequestMapping("/toProcess")
    public String toBoardEditRegisterProcess(HttpServletRequest request, @ModelAttribute BoardDetailDto updateDto, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {

        List<String> messageList = new ArrayList<>();

        //タイトル入力チェック
        if(updateDto.getBoardTitle() == null || updateDto.getBoardTitle().equals("")){
            String noteLabel = messageSource.getMessage("label.title",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            Map<String,Object> params = new HashMap<>();
            params.put("messageList", messageList);
            params.put("boardNo", updateDto.getBoardId());
            redirectAttributes.addFlashAttribute("params", params);
        }

        //内容入力チェック
        if(updateDto.getBoardContent() == null || updateDto.getBoardContent().equals("")){
            String noteLabel = messageSource.getMessage("label.content",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            Map<String,Object> params = new HashMap<>();
            params.put("messageList", messageList);
            params.put("boardNo", updateDto.getBoardId());
            redirectAttributes.addFlashAttribute("params", params);
        }
        
        // 上記のチェックでエラーが存在する場合
        if(messageList.size()!=0){
        	return "redirect:/boardEditRegister/returnBoardEdit";
        }

        if(sessionManager.getSesUserInfo() == null)
        	return "views/admin_login";
        
        
        //新規の場合
        if(updateDto.getBoardId() == null || updateDto.getBoardId().equals("")) {
        	//作成者設定
            updateDto.setRegisterUserId(sessionManager.getSesUserInfo().getName());
        	
        	//登録処理
        	boardEditRegisterService.registerBoard(updateDto);

        //修正の場合
    	} else { 
    		//更新者設定
            updateDto.setUpdateUserId(sessionManager.getSesUserInfo().getName());
    		
        	//更新処理
    		boardEditRegisterService.updateBoard(updateDto);
    	}

        Map<String,Object> params = new HashMap<>();
        String restorationJudgment = request.getParameter("restorationJudgment");
        params.put("restorationJudgment", restorationJudgment);
        redirectAttributes.addFlashAttribute("params", params);

        return "redirect:/boardList";

    }
}