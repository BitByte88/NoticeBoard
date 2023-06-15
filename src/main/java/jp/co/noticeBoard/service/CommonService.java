package jp.co.noticeBoard.service;





import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import jp.co.noticeBoard.dao.mapper.LoginMapper;



@Service

public class CommonService {



     private static final Logger logger = LoggerFactory.getLogger(CommonService.class);



     @Autowired

     private LoginMapper loginMapper;



     @Autowired

     private MessageSource messageSource;


     /**

      * 日付チェック

      *

      * @return true,false

      * @param　checkDate チェックデータ

      */

     public boolean validationDate(String checkDate) {



          //日付フォーマット

          if (Pattern.matches("^(19|20)\\d{2}\\/(0[1-9]|1[012])\\/(0[1-9]|[12][0-9]|3[0-1])$", checkDate)) {

               return true;



          } else {

               return false;

          }



     }

     /**

      * 時刻チェック

      *

      * @return true,false

      * @param　checkDate チェックデータ

      */



     public boolean validationTime(String checkTime) {

          //時刻フォーマット

          if (Pattern.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", checkTime)) {

               return true;



          } else {

               return false;

          }



     }



     /**

      * 半角英数字チェック

      *

      * @return true,false

      * @param　checkDate チェックデータ

      */



     public boolean validationEngNumber(String checkString) {

          //半角英数字フォーマット

          if (Pattern.matches("^[0-9a-zA-Z]+$", checkString)) {

               return true;



          } else {

               return false;

          }



     }



     /**

      * 価格四捨五入

      *

      * @return true,false

      * @param　checkDate チェックデータ

      */



     public String rounding(Integer number){

          Integer num = 0;

          DecimalFormat formatter = new DecimalFormat("###,###");

          if(number!=null){

               num = Math.round(number);

          }

          return formatter.format(num);



     }



     /**

      * 指定したファイルパスを基に、ファイルを取得する.

      *

      * @param filePath ファイルパス

      * @return バイト文字列の配列

      */

     public byte[] getFileData(final String filePath) throws IOException {

          int nRead;

          byte[] fileContent = new byte[16384];

          try (ByteArrayOutputStream buffer = new ByteArrayOutputStream(); InputStream is = new FileInputStream(filePath);) {

               while ((nRead = is.read(fileContent, 0, fileContent.length)) != -1) {

                    buffer.write(fileContent, 0, nRead);

               }

               buffer.flush();

               buffer.close();

               return buffer.toByteArray();

          }

     }



     /**

      * ファイルまたはディレクトリを削除する.

      *

      * @param filePath ファイルパス

      * @return 正常に削除された場合は true、そうでない場合は false

      */

     public boolean fileDelete(final String filePath) {

          return new File(filePath).delete();

     }



     /**

      * 先頭1桁、下3桁以外「*」でマスクした鍵番号を呼び出し元へ返却する。

      *

      * @param keyNo 鍵番号

      * @return マスク後の鍵番号（返却例 0******789）

      */

     public String getMaskedKeyNo(String keyNo) {

          StringBuilder sb = new StringBuilder();

          sb.append(keyNo.substring(0,1));

          for (int i = 0; i < keyNo.length() - 4; i++) {

               sb.append("*");

          }

          sb.append(keyNo.substring(keyNo.length() -3));

          return sb.toString();

     }



     /**

      * 日付と時刻を合わせる処理を行う。

      *

      * @param date 日付

      * @param time 時刻

      * @return Dateタイプ日付＋時刻

      */

     public Date convertDate(String date,String time) throws ParseException {

          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

          Date dateTime = dateFormat.parse(date + " " + time);



          return dateTime;

     }



     /**

      * 識別フラグが奇数なのかを判別する。

      *

      * @param identificationFlag 識別フラグ

      * @return 「奇数」の場合：TRUE

      * 　上記以外の場合：FALSE

      */

     public Boolean checkOddFlag(String identificationFlag) {

          if(!StringUtils.equals(identificationFlag, null)) {

               int num = Integer.parseInt(identificationFlag);

               if (num % 2 == 1) {

                    return true;

               } else {

                    return false;

               }

          } else {

               return false;

          }

     }

}