package kr.hs.emirim.duckbill0306.simplediary;

import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DatePicker datePic;
    EditText editDiary;
    Button butSave;
    String fileName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePic=(DatePicker)findViewById(R.id.dat_picker);
        editDiary=(EditText)findViewById(R.id.edit_content);
        butSave=(Button)findViewById(R.id.but_save);

        //현재 날짜 구하기
        Calendar calendar=Calendar.getInstance(); //캘린더 객체는 '추상 클래스'이므로, 메소드를 사용한다!
        int nowYear=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1; //월은 +1을 해주어야 한다!
        int date=calendar.get(Calendar.DATE);

        //DatePicker에 현재 날짜 설정
        datePic.init(nowYear, month, date, new DatePicker.OnDateChangedListener() { //익명 클래스 핸들러 가능!
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName=year+"_"+(monthOfYear+1)+"_"+dayOfMonth+".txt";

                String content=readDiary(fileName); //메소드 설정! (값을 읽은 다음 저장!)
                editDiary.setText(content);//읽어온 값 저장!
                butSave.setEnabled(true);//버튼 활성화!
            }
        });
    }

    String readDiary(String fileName){
        String diaryContents=null;
        try {
            FileInputStream in=openFileInput(fileName);
            byte[] txt=new byte[500]; //한글 20자 정도 저장!
            in.read(txt); //예외가 발생했을때 catch문 실행!
            in.close();
            diaryContents=new String(txt);
            butSave.setText("수정 하기"); //버튼의 글짜 바꾸기!

        } catch (IOException e) {
            editDiary.setHint("읽어올 일기가 없음");
            butSave.setText("새로 저장");
        }
        return diaryContents;
    }

}