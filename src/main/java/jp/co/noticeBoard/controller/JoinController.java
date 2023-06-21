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

import jp.co.noticeBoard.dto.UserDto;
import jp.co.noticeBoard.dto.UserJoinDto;
import jp.co.noticeBoard.form.JoinForm;
import jp.co.noticeBoard.service.JoinService;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.SessionManager;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
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
    public String join(HttpServletRequest request, Model model) throws Exception {

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
    @PostMapping("/register")
    public String join(@ModelAttribute("joinForm") JoinForm joinForm, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {

    	 //エラーメッセージリスト
        List<String> messageList = new ArrayList<>();

        UserJoinDto userJoinDto = new UserJoinDto();

        //ID入力チェック
        if(joinForm.getUserId() == null || joinForm.getUserId().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.id",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
        }

        //ID桁数チェック
        if(joinForm.getUserId().length() <  4 || joinForm.getUserId().length() > 18)
        {
        	String noteLabel = messageSource.getMessage("label.id",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00002", new Object[]{noteLabel, "4", "18"}, locale));
            logger.error(messageSource.getMessage("E00002", new Object[]{noteLabel, "4", "18"}, locale));
        }

        //ID重複チェック
        if(idCheck(joinForm.getUserId()) > 0)
        {
            messageList.add(messageSource.getMessage("E00004", new Object[]{}, locale));
            logger.error(messageSource.getMessage("E00004", new Object[]{}, locale));
        }

        //パスワード入力チェック
        if(joinForm.getPassword() == null || joinForm.getPassword().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.password",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
        }

        //パスワード桁数チェック
        if(joinForm.getPassword().length() <  4 || joinForm.getPassword().length() > 18)
        {
            String noteLabel = messageSource.getMessage("label.password",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00002", new Object[]{noteLabel, "4", "14"}, locale));
            logger.error(messageSource.getMessage("E00002", new Object[]{noteLabel, "4", "14"}, locale));
        }

        //名前入力チェック
        if(joinForm.getName() == null || joinForm.getName().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.name",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
        }

        //名前桁数チェック
        if(joinForm.getName().length() > 18)
        {
            String noteLabel = messageSource.getMessage("label.name",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00006", new Object[]{noteLabel, "30	"}, locale));
            logger.error(messageSource.getMessage("E00006", new Object[]{noteLabel}, locale));
        }

        //生年月日入力チェック
        if(joinForm.getBirthday() == null || joinForm.getBirthday().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.birthday",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
        }

        //生年月日妥当性チェック
        if(!checkDate(joinForm.getBirthday()))
        {
            String noteLabel = messageSource.getMessage("label.birthday",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00003", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00003", new Object[]{noteLabel}, locale));
        }

        //性別入力チェック
        if(joinForm.getGender() == null || joinForm.getGender().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.gender",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
        }

        //メール入力チェック
        if(joinForm.getMail() == null || joinForm.getMail().equals(""))
        {
            String noteLabel = messageSource.getMessage("label.mail",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00001", new Object[]{noteLabel}, locale));
        }

        //メール形式チェック
        if (!checkMail(joinForm.getMail())) {
            String noteLabel = messageSource.getMessage("label.mail",new Object[]{},locale);
            messageList.add(messageSource.getMessage("E00005", new Object[]{noteLabel}, locale));
            logger.error(messageSource.getMessage("E00005", new Object[]{noteLabel}, locale));
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

    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("userId") String userId) {
        int cnt = joinService.idCheck(userId);
        return cnt;

    }

   /**
    * 日付の妥当性チェックを行います。
    * 指定した日付文字列（yyyyMMdd）が
    * カレンダーに存在するかどうかを返します。
    * @param strDate チェック対象の文字列
    * @return 存在する日付の場合true
    */
   public boolean checkDate(String strDate)
   {
       DateFormat format = new SimpleDateFormat("yyyyMMdd");	

       format.setLenient(false);
       try {
           format.parse(strDate);
           return true;
       } catch (Exception e) {
           return false;
       }
   }

   /**
    * メールアドレスの妥当性チェックを行います。
    * @param strMail チェック対象の文字列
    * @return パータンにマッチする場合true
    */
   public boolean checkMail(String strMail)
   {
       Pattern pattern = Pattern.compile("^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]");
       Matcher matcher = pattern.matcher(strMail);

       if (matcher.find()) {
    	   return true;
    	   
       } else {
    	   return false;
       }
   }
}