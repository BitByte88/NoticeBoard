package jp.co.noticeBoard.utils;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 共通処理
 */
public class CommonUtils {

    /**
     * 日付フォーマット変換処理 Date→String
     *
     * @param format 日付フォーマット
     * @param date   変換するDate
     * @return フォーマットされた現在日付(String)
     */
    public static String chgDateFormat(String format, Date date) {

        if ((date == null) || (StringUtils.isEmpty(format))) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);
    }

    /**
     * 日付フォーマット変換処理 String→Date→String
     *
     * @param beforeFormat 変換前日付フォーマット
     * @param afterFormat  変換後日付フォーマット
     * @param strDate      変換文字列
     * @return フォーマットされた現在日付(String)
     * @throws ParseException
     */
    public static String chgDateFormat(String beforeFormat, String afterFormat, String strDate) throws ParseException {

        if ((StringUtils.isEmpty(beforeFormat)) || (StringUtils.isEmpty(afterFormat))
                || (StringUtils.isEmpty(strDate))) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(beforeFormat);
        Date date = sdf.parse(strDate);
        sdf = new SimpleDateFormat(afterFormat);

        return sdf.format(date);
    }

    /**
     * 日付フォーマット変換処理 String→Date
     *
     * @param format  日付フォーマット
     * @param strDate 変換文字列
     * @return フォーマットされた現在日付(Date)
     * @throws ParseException
     */
    public static Date chgDateFormat(String format, String strDate) throws ParseException {

        if ((StringUtils.isEmpty(strDate)) || (StringUtils.isEmpty(format))) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.parse(strDate);
    }

    /**
     * 日付計算処理 入力値により、現在日付に加算したい月・日を加算、日付変換を行う
     *
     * @param field  カレンダフィールド
     * @param amount 加算値
     * @param format 日付フォーマット
     * @return 計算された日付(String)
     */
    public static String getCalcDateStr(int field, int amount, String format) {

        if ((field == 0) || (amount == 0) || (StringUtils.isEmpty(format))) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        // 現在日付取得
        calendar.setTime(new Date());
        // 加算したい月・日を入力
        calendar.add(field, amount);
        // 日付フォーマット設定
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(calendar.getTime());
    }

    /**
     * 日付計算処理 入力値により、入力した日付に加算したい月・日を加算、日付変換を行う
     *
     * @param field  カレンダフィールド
     * @param amount 加算値
     * @param format 日付フォーマット
     * @param date   変換日付
     * @return 計算された日付(String)
     */
    public static String getCalcDateStr(int field, int amount, String format, Date date) {

        if ((field == 0) || (amount == 0) || (StringUtils.isEmpty(format)) || (date == null)) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        // 日付取得
        calendar.setTime(date);
        // 加算したい月・日を入力
        calendar.add(field, amount);
        // 日付フォーマット設定
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(calendar.getTime());
    }

    /**
     * 現在日付取得処理 フォーマットされた現在日付を取得する。
     *
     * @param format 日付フォーマット
     * @return フォーマットされた現在日付(String)
     */
    public static String getFormatDate(String format) {

        if (StringUtils.isEmpty(format)) {
            return null;
        }

        // 日付フォーマット設定
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(new Date());
    }


    /**
     * 数値フォーマット変換処理 指定された数値をカンマ付のフォーマットに変換する
     *
     * @param strNum 数値
     * @return 変換した結果
     */
    public static String chgNumFormat(String strNum) {

        String chgNum = null;

        if (StringUtils.isEmpty(strNum)) {
            return chgNum;
        } else if (!StringUtils.isNumeric(strNum)) {
            return strNum;
        }
        chgNum = String.format("%,d", Integer.parseInt(strNum));

        return chgNum;
    }

    /**
     * 数値フォーマット変換処理 指定された数値をパーセント付のフォーマットに変換する
     *
     * @param num 数値
     * @return 変換した結果
     */
    public static String chgNumPercent(double num) {
        DecimalFormat format = new DecimalFormat("##0%");
        return format.format((num));
    }

    /**
     * Hiddenで保持した文字列リストを変換する
     *
     * @param strlist 変換前リスト
     * @return 変換後リスト
     */
    public static ArrayList<String> chgHdnStrList(ArrayList<String> strlist) {

        ArrayList<String> chgStrList = new ArrayList<String>();

        if ((strlist == null) || (strlist.size() == 0)) {
            return chgStrList;
        }
        String chgStr = null;
        int size = strlist.size();

        for (int i = 0; i < size; i++) {

            chgStr = strlist.get(i);

            if (chgStr != null) {
                if (i == 0) {
                    if (chgStr.indexOf("[") == 0) {
                        chgStr = chgStr.substring(1);
                    }
                } else if (i == (size - 1)) {
                    if (chgStr.lastIndexOf("]") == chgStr.length() - 1) {
                        chgStr = chgStr.substring(0, chgStr.length() - 1);
                    }
                }
            }
            chgStrList.add(chgStr);
        }
        return chgStrList;
    }

    /**
     * レスポンスのヘッダーにNo-Cacheを追加する.
     * @param response レスポンス
     */
    public static void setResponseNoCache(HttpServletResponse response){
        response.addHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");// HTTP 1.1.
        response.setIntHeader("Expires", 0);// HTTP 1.0.
        response.addHeader("Pragma", "no-cache");// Proxies.
    }

    /**
     * ロケールにより、日時フォーマットを変更する。
     * @param format 日時フォーマット（既存）
     * @param date 変更する日時
     * @param locale ロケール
     * @return 変更した日時
     */
    public static String convertDateWithLocale(String format, Date date, Locale locale) {
        String timeFormat = " HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format + timeFormat, locale);
        return sdf.format(date);
    }
}
