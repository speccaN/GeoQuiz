package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquz.answer_shown";
    private static final String TAG = "CheatActivity";
    private static final String KEY_CHEATER = "Cheated";
    private static final String CHEAT_ANSWER = "Answer";

    private boolean mAnswerIsTrue;
    private boolean mIsCheater;

    private TextView mAnswerTextView;
    private Button mShowAnswer;

    public static Intent newIntent(QuizActivity packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        if (savedInstanceState != null) { // if there is a savedInstanceState
            setAnswerShownResult(savedInstanceState.getBoolean(KEY_CHEATER, false));
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER);
            mAnswerIsTrue = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_TRUE, false);
            Log.v(TAG, "" + mIsCheater);
            if (mIsCheater == true) { // if the User cheated
                mAnswerTextView.setText(savedInstanceState.getString(CHEAT_ANSWER));
            }
        }
        else { // if there is no savedInstanceState
            mIsCheater = false;
            setAnswerShownResult(false);
        }

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true); //User looked at the answer
                mIsCheater = true; //User cheated is true
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
        savedInstanceState.putBoolean(EXTRA_ANSWER_IS_TRUE, mAnswerIsTrue);
        savedInstanceState.putString(CHEAT_ANSWER, mAnswerTextView.getText().toString());
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        mIsCheater = isAnswerShown;
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
                setResult(RESULT_OK, data);
    }

}
