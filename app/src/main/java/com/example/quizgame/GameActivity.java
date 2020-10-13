package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizgame.model.WordItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mQuestionImageView;
    private Button[] mButtons = new Button[4];
    private Random mRandom;
    private String mAnswerWord;
    private List<WordItem> mItemList;
    int score = 0;
    int round = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        mQuestionImageView = findViewById(R.id.question_image_view);
        mButtons[0] = findViewById(R.id.choice_1_button);
        mButtons[1] = findViewById(R.id.choice_2_button);
        mButtons[2] = findViewById(R.id.choice_3_button);
        mButtons[3] = findViewById(R.id.choice_4_button);


        mButtons[0].setOnClickListener(this);
        mButtons[1].setOnClickListener(this);
        mButtons[2].setOnClickListener(this);
        mButtons[3].setOnClickListener(this);

        mRandom = new Random();
        newQuiz();

    }

    private void newQuiz() {

        mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));
        TextView scoreText = findViewById(R.id.point_view);
        scoreText.setText(score + " คะแนน");
        //random index of answer
        int answerIndex = mRandom.nextInt(mItemList.size());

        //เข้าถึง WordItem ตาม index ที่สุ่มได้
        WordItem item = mItemList.get(answerIndex);

        //แสดงรูปคำถาม
        mQuestionImageView.setImageResource(item.imageResId);
        mAnswerWord = item.word;

        //สุ่มตำแหน่งปุ่มที่จะแสดงคำตอบ
        int randomButton = mRandom.nextInt(4);
        mButtons[randomButton].setText(item.word);

        //ลบคำตอบออกจาก list
        mItemList.remove(item);

        //เอา list ที่เหลือมาสุ่ม
        Collections.shuffle(mItemList);

        for(int i=0; i<4; i++){
            if(i==randomButton){
                continue;
            }
                mButtons[i].setText(mItemList.get(i).word);
        }
    }

    @Override
    public void onClick(View view) {
        TextView scoreText = findViewById(R.id.point_view);
                    Button b = findViewById(view.getId());
                    String buttonText = b.getText().toString();
                    if (buttonText.equals(mAnswerWord)) {
                        //Toast.makeText(GameActivity.this, "ถูกต้องจ้าา", Toast.LENGTH_SHORT).show();
                        score++;
                        round++;
                        scoreText.setText(score + " คะแนน");
                    } else {
                        round++;
                        //Toast.makeText(GameActivity.this, "ผิดจ้าา", Toast.LENGTH_SHORT).show();
                    }
                    if(round == 5){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                        dialog.setTitle("สรุปผล");
                        dialog.setMessage("คุณได้ "+score+" คะแนน"+"\n"+"คุณต้องการจะเล่นเกมใหม่หรือไม่");
                        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                round = 0;
                                score = 0;
                                newQuiz();
                            }
                        });
                        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        dialog.show();;
                    }
                    newQuiz();
    }
}