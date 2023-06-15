package jp.co.noticeBoard.controller;


import jp.co.noticeBoard.service.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller

public class CommonErrorController implements ErrorController {

    private Logger logger = LoggerFactory.getLogger(CommonErrorController.class);

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private MessageSource messageSource;


    private static final String ERROR_PATH = "/error";
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }





        /**
         * エラー（デフォルト）
         *
         * @param model モデル

         * @return 画面パス
         */
    @RequestMapping(value = "/error")
    public String init(Model model) throws Exception {

        // エラーメッセージリスト
        List<String> messageList = new ArrayList<>();
        messageList.add(messageSource.getMessage("label.common.error.sub", null, null));
        model.addAttribute("messageList", messageList);
        logger.error(messageSource.getMessage("label.common.error.sub", null, null));


        return "views/error";
    }

}