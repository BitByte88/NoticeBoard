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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jp.co.noticeBoard.dto.OrderDetailDto;
import jp.co.noticeBoard.service.BoardDetailService;
import jp.co.noticeBoard.service.BoardEditRegisterService;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.SessionManager;

@Controller
@RequestMapping("/BO/boardEditRegister")
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
     * 注文詳細表示（初期表示）
     *
     * @param model モデル
     * @param locale  ロケール
     * @return 画面パス
     */
    @RequestMapping("")
    public String boardEditRegister(@ModelAttribute OrderDetailDto updateDto, HttpServletRequest request,Model model, Locale locale) throws Exception {

        //エラーメッセージリスト
        List<String> messageList = new ArrayList<>();

        //一覧画面から遷移用、注文番号取得
        String orderNo = updateDto.getBoardId();
        

        if (orderNo == null || orderNo.equals(""))
        {
        	model.addAttribute("registerFlg", "1");
        	  return "views/boardEditRegisterConfirm";
        } else {
        	model.addAttribute("registerFlg", "2");
        }
		
        //掲示板情報リスト取得
        List<OrderDetailDto> orderDetailList = new ArrayList<OrderDetailDto>();
        orderDetailList.add(updateDto);

        if(orderDetailList.size()==0){
            return "redirect:/error";
        }

        //現在時間格納
        sessionManager.setSesTime();

        model.addAttribute("orderDetailList", orderDetailList);

        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
        }

        return "views/boardEditRegisterConfirm";

    }

    /**
     * 注文詳細表示（戻る）
     *
     * @return 画面パス
     */
    @RequestMapping("/returnBoardEdit")
    public String orderDetailBackButton(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {

        //エラーメッセージリスト
        List<String> messageList = new ArrayList<>();

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
        
        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
        }

    	//注文詳細リスト取得
        List<OrderDetailDto> BoardUpdateList = new ArrayList<OrderDetailDto>();
        BoardUpdateList = boardDetailService.getOrderDetailList(orderNo);

    	// 新規作成の場合
    	if ( orderNo == null || orderNo.equals(""))
    	{
    				String Title = request.getParameter("boardTitle");
			  		String Content = request.getParameter("boardContent");
			  		BoardUpdateList.get(0).setBoardTitle(Title);
			  		BoardUpdateList.get(0).setBoardContent(Content);
			  		model.addAttribute("registerFlg", "1");
    		model.addAttribute("orderDetailList", BoardUpdateList);
    		return "views/boardEditRegister";
    	}
    	
    	// 修正の場合


        if(BoardUpdateList.size()==0){
            return "redirect:/error";
        }
		
		model.addAttribute("registerFlg", "2");
		model.addAttribute("orderDetailList", BoardUpdateList);


        return "views/boardEditRegister";
    }

    /**
     * 登録・修正 確認画面へ
     * @param updateDto 更新情報
     * @param model モデル
     * @param locale　ロケール
     * @return 画面パス
     */
    @PostMapping("/toConfirm")
    public String toBoardEditRegisterConfirm(@ModelAttribute OrderDetailDto updateDto, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {


        //一覧画面から遷移用、注文番号取得
        String orderNo = updateDto.getBoardId();
        
        List<String> messageList = new ArrayList<>();

        //タイトル入力チェック
        if(updateDto.getBoardTitle() == null || updateDto.getBoardTitle().equals("")){
            String noteLabel = messageSource.getMessage("label.title",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            Map<String,Object> params = new HashMap<>();
            params.put("messageList", messageList);
            params.put("orderNo", updateDto.getBoardId());
            redirectAttributes.addFlashAttribute("params", params);
        }

        //内容入力チェック
        if(updateDto.getBoardContent() == null || updateDto.getBoardContent().equals("")){
            String noteLabel = messageSource.getMessage("label.content",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            Map<String,Object> params = new HashMap<>();
            params.put("messageList", messageList);
            params.put("orderNo", updateDto.getBoardId());
            redirectAttributes.addFlashAttribute("params", params);
        }
        
        // 上記のチェックでエラーが存在する場合
        if(messageList.size()!=0){
        	return "redirect:/BO/boardEditRegister/returnBoardEdit";
        }

        //新規の場合
        if(updateDto.getBoardId() == null || updateDto.getBoardId().equals("")) {
        	List<OrderDetailDto> BoardUpdateList = new ArrayList<OrderDetailDto>();
        	
        	BoardUpdateList.add(updateDto);

            model.addAttribute("registerFlg", "1");
            model.addAttribute("orderDetailList", BoardUpdateList);

        //修正の場合
        } else {
        	//注文詳細リスト取得
            List<OrderDetailDto> BoardUpdateList = new ArrayList<OrderDetailDto>();
            BoardUpdateList = boardDetailService.getOrderDetailList(orderNo);
            
            if(BoardUpdateList.size()==0){
                return "redirect:/error";
            }

            BoardUpdateList.get(0).setBoardTitle(updateDto.getBoardTitle());
            BoardUpdateList.get(0).setBoardContent(updateDto.getBoardContent());
    		
    		model.addAttribute("registerFlg", "2");
    		model.addAttribute("orderDetailList", BoardUpdateList);
        }

        return "views/boardEditRegisterConfirm";

    }

    /**
     * 登録・修正処理を行う。
     * @param updateDto 更新情報
     * @param model モデル
     * @param locale　ロケール
     * @return 画面パス
     */
    @RequestMapping("/toProcess")
    public String toBoardEditRegisterProcess(HttpServletRequest request, @ModelAttribute OrderDetailDto updateDto, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {


        List<String> messageList = new ArrayList<>();
        
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

        return "redirect:/BO/boardList";

    }
}