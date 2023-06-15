package jp.co.noticeBoard.common;

public class Const {
    // 日付の書式
    public static final String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
    public static final String FORMAT_DATE_YYYYMMDDHHMISS = "yyyyMMddHHmmss";
    public static final String FORMAT_DATE_YYYYMMDD_SLASH = "yyyy/MM/dd";
    public static final String FORMAT_DATE_YYYYMMDDHHMISS_SLASH = "yyyy/MM/dd HH:mm:ss";

    // ハイフン
    public static final String HYPHEN = "-";
    // コンマ
    public static final char COMMA = ',';
    // ピリオド
    public static final String DOT = ".";
    // アンダーライン
    public static final String UNDERLINE = "_";
    // アスタリスク
    public static final String ASTERISK = "＊";
    // TAB
    public static final String TAB = "\t";
    // 改行（CRLF）
    public static final String CRLF = "\r\n";
    // 数字フォーマット
    public static final String NUMBER_FORMAT = "^[0-9]+$";
    // 英字数字記号フォーマット
    public static final String ENG_NUM_SYM_FORMAT = "^[0-9a-zA-Z+－＝＄％＆＊＃？／]+$";

    //日付フォーマット
    public static final String DATE_FORMAT  = "YYYY/MM/DD";

    //時刻フォーマット
    public static final String TIME_FORMAT  = "HH:MM";

    // セッション「管理者情報」のキー
    public static final String SES_USER_INFO = "SesUserInfo";
    //セッション「注文状況検索条件」のキー
    public static final String SES_ORDER_SEARCH_INFO ="SesOrderSearchInfo";
    //セッション「現在時間」のキー
    public static final String SES_TIME = "SesTime";
}
