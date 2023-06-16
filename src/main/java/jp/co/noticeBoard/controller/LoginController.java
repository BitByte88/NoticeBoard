package jp.co.noticeBoard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.noticeBoard.dto.UserDto;
import jp.co.noticeBoard.form.LoginForm;
import jp.co.noticeBoard.service.LoginService;
import jp.co.noticeBoard.service.SessionManager;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LoginService loginService;
	
		/**
		* ログイン（初期表示）
		*
		* @return 画面パス
		*/
		@RequestMapping("")
			public String login() throws Exception {
			 sessionManager.clearUserInfo();
			 return "views/admin_login";
			}
			

	/**
	 * ログイン
	 *
	 * @param model    モデル
	 * @param loginForm ログイン画面のForm
	 * @param locale   リクエストヘッダーのAccept-Language
	 * @return 画面パス
	 */
	@RequestMapping("/process")
	public String login(Locale locale, Model model, @ModelAttribute LoginForm loginForm) throws Exception {

		// エラーメッセージリスト
		List<String> messageList = new ArrayList<>();

		// ID,PASSWORDチェック
		messageList = loginService.loginInputCheck(loginForm, locale);
		if (!messageList.isEmpty()) {
			model.addAttribute("messageList", messageList);
			return "views/admin_login";
		} else {
			//画面引数アカウントを引数として設定しユーザ情報を取得する。
			UserDto userdto = loginService.getUserInfo(loginForm.getAccount());

			//アカウント存在チェック
			if (userdto == null) {
				messageList.add(messageSource.getMessage("E09995", new Object[] {}, locale));
				logger.error(messageSource.getMessage("E09995", new Object[] {}, locale));
				model.addAttribute("messageList", messageList);
				return "views/admin_login";
			}

			//画面引数パスワードをSHA-256アルゴリズムでハッシュ化する。
			String hashPassword = loginService.getHash(loginForm.getPassword());

			//パスワード一致チェック
			if (!hashPassword.equals(userdto.getPassword())) {
				messageList.add(messageSource.getMessage("E09995", new Object[] {}, locale));
				logger.error(messageSource.getMessage("E09995", new Object[] {}, locale));
				model.addAttribute("messageList", messageList);
				return "views/admin_login";
			}

			//セッション格納
			UserDto sesUserDto = userdto;
			sesUserDto.setPassword("");
			//ログイン情報
			sessionManager.setSesUserInfo(sesUserDto);
			//現在時間
			sessionManager.setSesTime();

			return "redirect:/boardList";
		}

	}

	/**
	 * ポータル（初期表示）
	 *
	 * @return 画面パス
	 */
	@RequestMapping("/portal")
	public String portal() {

		return "views/admin_portal";
	}

	/**
	 * ログアウト
	 *
	 * @return 画面パス
	 */
	@RequestMapping("/logout")
	public String logout(Model model) {
		List<String> messageList = new ArrayList<>();
		if ((List<String>) model.asMap().get("messageList") != null) {
			messageList.addAll((List<String>) model.asMap().get("messageList"));
		}
		sessionManager.clearUserInfo();
		return "redirect:/boardList";
	}

	/**
	 * セッションタイムアウト     *
	 * @param model モデル
	 * @return 画面パス
	 */
	@RequestMapping(value = "/returnLogin")
	public String returnLogin(Model model, RedirectAttributes redirectAttributes) throws Exception {
		// エラーメッセージリスト
		List<String> messageList = new ArrayList<>();
		messageList.add(messageSource.getMessage("E09999", null, null));
		model.addAttribute("messageList", messageList);
		logger.error(messageSource.getMessage("E09999", null, null));
		redirectAttributes.addFlashAttribute("messageList", messageList);
		return "redirect:/logout";
	}

}