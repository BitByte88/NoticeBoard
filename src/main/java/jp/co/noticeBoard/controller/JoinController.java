package jp.co.noticeBoard.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.noticeBoard.common.Const;
import jp.co.noticeBoard.dto.UserDto;
import jp.co.noticeBoard.dto.UserJoinDto;
import jp.co.noticeBoard.form.JoinForm;
import jp.co.noticeBoard.service.JoinService;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.SessionManager;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/join")
public class JoinController {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private SessionManager sessionManager;

    @Autowired
    private JoinService joinService;

    @Autowired
    private MessageSource messageSource;

    /**
     * 会員登録初期画面
     *
     * @return 画面パス
     */

    @GetMapping("")
    public String join(Model model) throws Exception {

		JoinForm joinForm = new JoinForm();

	    //初期値設定
        joinForm.setGender("0");
        joinForm.setJoinReason("0");

        model.addAttribute("joinForm", joinForm);

        return "views/admin_join";
    }

    /**
     * 会員登録画面
     *
     * @param joinForm 会員登録画面のForm
     * @return 画面パス
     */
    @RequestMapping("/register")
    public String join(@ModelAttribute("joinForm") JoinForm joinForm, Model model) throws Exception {

    	 //エラーメッセージリスト
        List<String> messageList = new ArrayList<>();
        UserJoinDto userJoinDto = new UserJoinDto();

        //ID入力チェック
        if(joinForm.getUserId() == null || joinForm.getUserId().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.join.userId",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
        }

        //ID桁数チェック
        if(joinForm.getUserId().length() <  Const.MIN_ID_LENGTH || joinForm.getUserId().length() > Const.MAX_ID_LENGTH)
        {
        	String noteLabel = messageSource.getMessage("label.join.userId",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00002", new Object[]{noteLabel, Const.MIN_ID_LENGTH, Const.MAX_ID_LENGTH}, null));
            logger.error(messageSource.getMessage("E00002", new Object[]{noteLabel, Const.MIN_ID_LENGTH, Const.MAX_ID_LENGTH}, null));
        }

        //ID重複チェック
        if(idCheck(joinForm.getUserId()) > 0)
        {
            messageList.add(messageSource.getMessage("E00004", new Object[]{}, null));
            logger.error(messageSource.getMessage("E00004", new Object[]{}, null));
        }

        //パスワード入力チェック
        if(joinForm.getPassword() == null || joinForm.getPassword().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.join.password",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
        }

        //パスワード桁数チェック
        if(joinForm.getPassword().length() <  Const.MIN_PW_LENGTH || joinForm.getPassword().length() > Const.MAX_PW_LENGTH)
        {
            String noteLabel = messageSource.getMessage("label.join.password",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00002", new Object[]{noteLabel, Const.MIN_PW_LENGTH, Const.MAX_PW_LENGTH}, null));
            logger.error(messageSource.getMessage("E00002", new Object[]{noteLabel, Const.MIN_PW_LENGTH, Const.MAX_PW_LENGTH}, null));
        }

        //名前入力チェック
        if(joinForm.getName() == null || joinForm.getName().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.join.name",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
        }

        //名前桁数チェック
        if(joinForm.getName().length() > Const.MAX_NAME_LENGTH)
        {
            String noteLabel = messageSource.getMessage("label.join.name",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00006", new Object[]{noteLabel, Const.MAX_NAME_LENGTH}, null));
            logger.error(messageSource.getMessage("E00006", new Object[]{noteLabel}, null));
        }

        //生年月日入力チェック
        if(joinForm.getBirthday() == null || joinForm.getBirthday().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.join.birthday",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
        }

        //生年月日妥当性チェック
        if(!joinService.checkDate(joinForm.getBirthday()))
        {
            String noteLabel = messageSource.getMessage("label.join.birthday",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00003", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00003", new Object[]{noteLabel}, null));
        }

        //性別入力チェック
        if(joinForm.getGender() == null || joinForm.getGender().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.join.gender",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
        }

        //メール入力チェック
        if(joinForm.getMail() == null || joinForm.getMail().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.join.mail",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, null));
        }

        //メール形式チェック
        if (!joinService.checkMail(joinForm.getMail())) {
            String noteLabel = messageSource.getMessage("label.join.mail",new Object[]{}, null);
            messageList.add(messageSource.getMessage("E00005", new Object[]{noteLabel}, null));
            logger.error(messageSource.getMessage("E00005", new Object[]{noteLabel}, null));
        }

        // 上記のチェックでエラーが存在する場合
        if(messageList.size()!=0){
            model.addAttribute("messageList", messageList);
            model.addAttribute("joinForm", joinForm);

            return "views/admin_join";
        }

		//画面引数パスワードをSHA-256アルゴリズムでハッシュ化する。
		String hashPassword = joinService.getHash(joinForm.getPassword());

        userJoinDto.setUserId(joinForm.getUserId());
        userJoinDto.setPassword(hashPassword);
        userJoinDto.setName(joinForm.getName());
        userJoinDto.setUserBirthday(joinForm.getBirthday());
        userJoinDto.setGender(joinForm.getGender());
        userJoinDto.setMail(joinForm.getMail());
        userJoinDto.setJoinReason(joinForm.getJoinReason());

        joinService.joinUser(userJoinDto);

        //セッション格納
		UserDto sesUserDto = new UserDto(); 
		sesUserDto.setName(userJoinDto.getName());
		sesUserDto.setUserId(userJoinDto.getUserId());
		sesUserDto.setPassword("");
        sessionManager.setSesUserInfo(sesUserDto);

        return "redirect:/boardList";
    }

    @RequestMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("userId") String userId) {
        int cnt = joinService.idCheck(userId);
        return cnt;
    }
}