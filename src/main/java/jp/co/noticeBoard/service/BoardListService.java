package jp.co.noticeBoard.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.co.noticeBoard.dao.mapper.BoardListMapper;
import jp.co.noticeBoard.dto.BoardListDto;
import jp.co.noticeBoard.dto.BoardListSearchDto;
import jp.co.noticeBoard.dto.PageDto;
import jp.co.noticeBoard.entitiy.Tblboard;
import jp.co.noticeBoard.form.BoardListForm;

@Service
public class BoardListService {

    private static final Logger logger = LoggerFactory.getLogger(BoardListService.class);

    //１ページあたりの最大件数
    @Value("${list.max.count}")
    private Integer limit;

    //ページャの数字ボタン数
    @Value("${pager.btn.count}")
    private Integer btnCount;

    @Autowired
    private BoardListMapper boardListMapper;

    /**
     * 掲示文条件チェック
     *
     * @param boardListForm 掲示文条件Form
     * @return エラーメッセージリスト
     */
    public List<String> searchInputCheck(BoardListForm boardListForm, Locale locale) throws Exception {

        // エラーメッセージリスト
        List<String> messageList = new ArrayList<>();
        
        
        // TODO 何もチェックしてない

        // ログ出力
        if (!messageList.isEmpty()) {
            for (String message : messageList) {
                logger.error(message);
            }
            return messageList;

        }

        return messageList;
    }

    /**
     * 検索条件DTO生成
     * @param boardListForm 掲示文検索条件Form
     * @return 掲示文一覧検索条件Dto
     */
    public BoardListSearchDto getSearchDto(BoardListForm boardListForm) throws Exception {

        BoardListSearchDto dto = new BoardListSearchDto();
        dto.setOffset(boardListForm.getOffset());
        dto.setSearchType(boardListForm.getSearchType());
        dto.setSearchKeyword(boardListForm.getSearchKeyword());

        return dto;
    }



    /**
     * 掲示文件数取得.
     *
     * @param boardListSearchDto 掲示文検索変数
     * @return 件数
     */
    public Integer getBoardListCount(BoardListSearchDto boardListSearchDto) {
        return boardListMapper.getBoardListCount(boardListSearchDto);
    }

    /**
     * 掲示文情報取得.
     *
     * @param boardListSearchDto　注文状況検索条件
     * @return 掲示文リスト
     */
    public List<Tblboard> getBoardList(BoardListSearchDto boardListSearchDto) {
        return boardListMapper.getBoardList(boardListSearchDto, limit);
    }

    /**
     * データの状況でオフセットを調整する.
     *
     * @param dataCnt     データ件数　
     * @param offset      現在のオフセット　
     * @return オフセット
     **/
    public PageDto changeOffset(int dataCnt, int offset) {
        return offsetControl(dataCnt, offset, limit.intValue());
    }

    /**
     * １ページあたりの最大件数取得
     *
      * @return limit
     **/
    public int getLimit() {
        return limit.intValue();
    }

    /**
     * オフセットの計算処理を行う.
     *
     * @param dataCnt    データ件数
     * @param offset     現在のオフセット
     * @param limitCount 1Pの表示件数
     * @return オフセット
     **/
    public PageDto offsetControl(int dataCnt, int offset, int limitCount) {
        PageDto dto = new PageDto();
        dto.setBtnCount(btnCount);
        // データ件数より最大ページ数を取得する。
        int maxPage = dataCnt / limitCount;
        int restCount = dataCnt % limitCount;
        if (restCount > 0) {
            maxPage = maxPage + 1;
        }

        // 現在のオフセットが0の場合、0を返す
        if (offset <= 0) {
            dto.setOffset(0);
            dto.setCurrentPage(1);
            dto.setPageLimit(limit);
            dto.setCount(dataCnt);
            dto.setTotalPage(maxPage);
            return dto;
        } else {
            // offsetは0開始なので、1加算する
            offset = offset + 1;
        }

        // データ件数が1Pの表示件数以下の場合、0を返す
        if (dataCnt <= limitCount) {
            dto.setOffset(0);
            dto.setCurrentPage(1);
            dto.setPageLimit(limit);
            dto.setCount(dataCnt);
            dto.setTotalPage(maxPage);

            return dto;
        }



        // 現在のオフセットと1Pの表示件数より、現在のページ数を取得する
        int currentPage = offset / limitCount;
        restCount = offset % limitCount;
        if (restCount > 0) {
            currentPage = currentPage + 1;
        }

        // 現在のページ数が、最大ページ数より大きい場合
        if (currentPage > maxPage) {
            // 最大ページ数-1 * 1Pの表示件数を返す
            dto.setOffset((maxPage - 1) * limitCount);
            dto.setCurrentPage(maxPage);
            dto.setPageLimit(limit);
            dto.setCount(dataCnt);
            dto.setTotalPage(maxPage);
            return dto;

        } else {
            // 現在のページ数-1 * 1Pの表示件数を返す
            dto.setOffset((currentPage - 1) * limitCount);
            dto.setCurrentPage(currentPage);
            dto.setPageLimit(limit);
            dto.setCount(dataCnt);
            dto.setTotalPage(maxPage);
            return dto;
        }
    }



    /**
     * 表示用文字列取得

     * @param list    掲示文リスト
     * @return 掲示文リスト
     **/

    public List<BoardListDto> boardListConversion(List<Tblboard> list,Locale locale){

        List<BoardListDto> boardList = new ArrayList<>();
        for (Tblboard board : list) {
            BoardListDto dto = new BoardListDto();

            //日付フォーマット変換
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String boardDate = dateFormat.format(board.getRegisterDate());

            dto.setBoardId(board.getBoardId());
            dto.setBoardTitle(board.getBoardTitle());
            dto.setBoardContent(board.getBoardContent());
            dto.setRegisterUserId(board.getRegisterUserId());
            dto.setRegisterDate(boardDate);
            dto.setViewCount(board.getViewCount());
            
            boardList.add(dto);
        }

        return boardList;
    }
}