package jp.co.noticeBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.noticeBoard.dto.UserJoinDto;
import jp.co.noticeBoard.form.JoinForm;
import jp.co.noticeBoard.service.JoinService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
@RequestMapping("/join")
public class JoinController {

    @Autowired
    private JoinService joinService;

    /**
     * 会員登録初期画面
     *
     * @return 画面パス
     */

    @GetMapping("")
    public String join() throws Exception {

        return "views/admin_join";

    }

    /**
     * 会員登録画面
     *
     * @param joinForm 会員登録画面のForm
     * @return 画面パス
     */
    @PostMapping("/register")
    public String join(@ModelAttribute("joinForm") JoinForm joinForm, Model model) throws Exception {

        UserJoinDto userJoinDto = new UserJoinDto();
        
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

        model.addAttribute("joinForm", userJoinDto);

        return "redirect:/login";

    }

    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("userId") String userId) {
        int cnt = joinService.idCheck(userId);
        return cnt;

    }
}