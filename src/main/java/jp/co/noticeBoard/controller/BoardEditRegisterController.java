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
import jp.co.noticeBoard.dto.BoardDetailDto;
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
    private BoardEditRegisterService boardEditRegisterService;

    /**
     * 掲示情報の登録・修正（初期表示）
     *
     * @param model モデル
     * @return 画面パス
     */
    @RequestMapping("")
    public String boardEditRegister(@ModelAttribute BoardDetailDto updateDto, HttpServletRequest request, Model model) throws Exception {

		//エラーメッセージリスト
		List<String> messageList = new ArrayList<>();

		//requestからエラー情報取得
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
            Map<String, Object> params = (Map<String, Object>) flashMap.get("params");
			if ((ArrayList) params.get("messageList") != null) {
				messageList = (ArrayList) params.get("messageList");
			}
		}

		model.addAttribute("updateDto", updateDto);
        model.addAttribute("registerFlg", "1");
        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
        }

        return "views/board_EditRegister";
    }

    /**
     * 掲示情報の登録・修正（再表示）
     *
     * @return 画面パス
     */
    @RequestMapping("/returnBoardEdit")
    public String boardDetailBackButton(HttpServletRequest request, Model model) throws Exception {

        //エラーメッセージリスト
        List<String> messageList = new ArrayList<>();
        BoardDetailDto updateDto = new BoardDetailDto();
    	String boardId = null;
    	
		//画面再表示の為、値取得
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		Map<String, Object> params = new HashMap<>();
		if (flashMap != null) {
			params = (Map<String, Object>) flashMap.get("params");
			updateDto =(BoardDetailDto) flashMap.get("updateDto");
			boardId =(String) flashMap.get("boardId");

			if ((ArrayList) params.get("messageList") != null) {
				messageList = (ArrayList) params.get("messageList");
			}
		}

        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
        }
    	// 新規作成の場合
    	if (boardId == null || boardId.equals(""))
    	{
    		model.addAttribute("registerFlg", "1");
    		model.addAttribute("updateDto", updateDto);
    	} else { // 修正の場合
    		model.addAttribute("registerFlg", "2");
    		model.addAttribute("updateDto", updateDto);
    	}

        return "views/board_EditRegister";
    }

    /**
     * 掲示情報の登録・修正
     * @param updateDto 更新情報
     * @return 画面パス
     */
    @RequestMapping("/toProcess")
    public String toBoardEditRegisterProcess(HttpServletRequest request, @ModelAttribute BoardDetailDto updateDto, RedirectAttributes redirectAttributes) throws Exception {

    	// 修正画面から遷移した場合
    	String boardId = request.getParameter("boardId");
        List<String> messageList = new ArrayList<>();

        //タイトル入力チェック
        if(updateDto.getBoardTitle() == null || updateDto.getBoardTitle().equals("")){
            String noteLabel = messageSource.getMessage("label.title",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
        }

        //タイトル桁数チェック
        if(updateDto.getBoardTitle().length() > Const.MAX_TITLE_LENGTH){
            String noteLabel = messageSource.getMessage("label.title",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00006", new Object[]{noteLabel, Const.MAX_TITLE_LENGTH}, null));
            logger.error(messageSource.getMessage("E00006", new Object[]{noteLabel, Const.MAX_TITLE_LENGTH}, null));
        }

        //内容入力チェック
        if(updateDto.getBoardContent() == null || updateDto.getBoardContent().equals("")){
            String noteLabel = messageSource.getMessage("label.content",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
        }

        //内容桁数チェック
        if(updateDto.getBoardContent().length() > Const.MAX_CONTENT_LENGTH){
            String noteLabel = messageSource.getMessage("label.content",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00006", new Object[]{noteLabel, Const.MAX_CONTENT_LENGTH}, null));
            logger.error(messageSource.getMessage("E00006", new Object[]{noteLabel, Const.MAX_CONTENT_LENGTH}, null));
        }

        // 上記のチェックでエラーが存在する場合
        if(messageList.size()!=0){
        	redirectAttributes.addFlashAttribute("boardId", boardId);
        	redirectAttributes.addFlashAttribute("updateDto", updateDto);
        	
        	Map<String,Object> params = new HashMap<>();
            params.put("messageList", messageList);
            redirectAttributes.addFlashAttribute("params", params);

        	return "redirect:/boardEditRegister/returnBoardEdit/";
        }

        if(sessionManager.getSesUserInfo() == null)
        	return "views/admin_login";
        //改行処理
        updateDto.setBoardContent(updateDto.getBoardContent().replace(Const.CRLF, Const.CR));
        //新規の場合
        if(updateDto.getBoardId() == null || updateDto.getBoardId().equals("")) {
        	//作成者設定
            updateDto.setRegisterUserId(sessionManager.getSesUserInfo().getUserId());
        	//登録処理
        	boardEditRegisterService.registerBoard(updateDto);
    	} else { //修正の場合
    		//更新者設定
            updateDto.setUpdateUserId(sessionManager.getSesUserInfo().getUserId());
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