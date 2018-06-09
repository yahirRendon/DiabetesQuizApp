package com.example.android.diabetesquizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {
    //Animation
    Animation animFadeOut; //Main fade out
    Animation animFadeIn; //Main fade in
    Animation inputFadeOut; //for initial name input fade
    Animation q1SolutionFadeOut, q2SolutionFadeOut, q3SolutionFadeOut,
            q4SolutionFadeOut, q5SolutionFadeOut, q6SolutionFadeOut,
            summaryTextFadeOut; //for solution elements to questions fade
    //Views
    Button moreButton; //More button
    Button submitButton; //Begin/submit button
    EditText nameInputField; //Name input edit text
    TextView questionText; //Intro/question text
    TextView headerText; //Header text
    String userName; //String value of name input edit text
    TextView summaryText; //Summary text
    LinearLayout bodyLayout; //Body of app layout
    //Solutions
    RadioGroup q1RadioGroup; //Question 1 radio group
    int q1ID; //RadioGroup 2 ID
    RadioButton q1Ans; //RadioButton answer to question 1
    boolean q1Result; //Track question 1 result
    LinearLayout q2CheckboxGroup; //Question 2 checkbox layout group
    CheckBox q2Ans1; //Question 2 checkbox answer 1
    CheckBox q2Ans2; //Question 2 checkbox answer 2
    CheckBox q2Ans3; //Question 2 checkbox answer 3
    CheckBox q2Ans4; //Question 2 checkbox answer 4
    boolean q2Result; //Track question 2 result
    RadioGroup q3RadioGroup; //Question 3 radio group
    int q3ID; //RadioGroup 3 ID
    RadioButton q3Ans; //RadioButton answer to question 1
    boolean q3Result; //Track question 3 result
    EditText q4Input; //Question 4 edit text input
    String q4Answer; //String value of question 4 input field
    boolean q4Result; //Track question 4 result
    LinearLayout q5CheckboxGroup; //Question 5 checkbox layout group
    CheckBox q5Ans1; //Question 5 answer 1
    CheckBox q5Ans2; //Question 5 answer 2
    CheckBox q5Ans3; //Question 5 answer 3
    CheckBox q5Ans4; //Question 5 answer 4
    boolean q5Result; //Track question 5 result
    RadioGroup q6RadioGroup; //Question 6 radio group
    int q6ID; //RadioGroup 6 ID
    RadioButton q6Ans; //RadioButton answer to question 6
    boolean q6Result; //Track question 6 result
    //Logic
    int questionCount; //Count the questions to determine question text and solution options
    int correctAnswers; //Count number of correct answers
    boolean displaySummary; //Toggle detailed summary text
    boolean firstTime = true; //limit animation on retake

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onInitialize();
    }

    /*
     * Initialize method
     */
    public void onInitialize() {
        getWindow().setBackgroundDrawableResource(R.drawable.food); //Set background image
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //Soft keyboard
        //views
        nameInputField = (EditText)findViewById(R.id.nameInput);
        questionText = (TextView)findViewById(R.id.questionText);
        headerText = (TextView)findViewById(R.id.headerText);
        submitButton = (Button)findViewById(R.id.submitButton);
        moreButton = (Button)findViewById(R.id.moreButton);
        summaryText = (TextView)findViewById(R.id.summaryText);
        bodyLayout = (LinearLayout)findViewById(R.id.body_layout_view);
        //Animations
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        inputFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        q1SolutionFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        q2SolutionFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        q3SolutionFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        q4SolutionFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        q5SolutionFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        q6SolutionFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        summaryTextFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        //Animation Listeners
        animFadeIn.setAnimationListener(this); //Animation fadeIn listener
        animFadeOut.setAnimationListener(this); //Animation fadeOut listener
        //onClickListeners
        moreButton.setOnClickListener(mGlobal_OnClickListener); //More info button
        submitButton.setOnClickListener(mGlobal_OnClickListener); //Begin/submit button
        summaryText.setOnClickListener(mGlobal_OnClickListener); //Summary text clickable
        //Question/Answers
        q1RadioGroup = (RadioGroup)findViewById(R.id.radioGroup1); //Question 1 Radio Group
        q1Ans = (RadioButton)findViewById(R.id.q1_a3); //Question 1 answer
        q2CheckboxGroup = (LinearLayout)findViewById(R.id.checkboxGroup2); //Question 2 Layout Group
        q2Ans1 = (CheckBox) findViewById(R.id.q2_a1); //Question 2 checkbox 1
        q2Ans2 = (CheckBox) findViewById(R.id.q2_a2); //Question 2 checkbox 2
        q2Ans3 = (CheckBox) findViewById(R.id.q2_a3); //Question 2 checkbox 3
        q2Ans4 = (CheckBox) findViewById(R.id.q2_a4); //Question 2 checkbox 4
        q3RadioGroup = (RadioGroup)findViewById(R.id.radioGroup2); //Question 3 Radio Group
        q3Ans = (RadioButton)findViewById(R.id.q3_a4); //Question 3 answer
        q4Input = (EditText)findViewById(R.id.q4_input); //Question 4 answer input field
        q5CheckboxGroup = (LinearLayout)findViewById(R.id.checkboxGroup5); //Question 5 Layout Group
        q5Ans1 = (CheckBox) findViewById(R.id.q5_a1); //Question 5 checkbox 1
        q5Ans2 = (CheckBox) findViewById(R.id.q5_a2); //Question 5 checkbox 2
        q5Ans3 = (CheckBox) findViewById(R.id.q5_a3); //Question 5 checkbox 3
        q5Ans4 = (CheckBox) findViewById(R.id.q5_a4); //Question 5 checkbox 4
        q6RadioGroup = (RadioGroup)findViewById(R.id.radioGroup6); //Question 6 Radio Group
        q6Ans = (RadioButton)findViewById(R.id.q6_a2); //Question 6 answer
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // check for fadeOut animation
        if (animation == animFadeOut) {
            switch (questionCount) {
                case 0: //When question count is 0
                    if(firstTime) {
                        hideNameInput();
                    }
                    //Hide and update appropriate views
                    summaryText.setVisibility(View.GONE);
                    questionText.setText(R.string.question_1_text);
                    headerText.setText(R.string.question_1_header_text);
                    submitButton.setText(R.string.submit_btn_text);
                    //Bring question 1 to front so clickable
                    q1RadioGroup.bringToFront();
                    submitButton.bringToFront();
                    //Show appropriate views
                    q1RadioGroup.setVisibility(View.VISIBLE);
                    q1RadioGroup.startAnimation(animFadeIn);
                    headerText.startAnimation(animFadeIn);
                    questionText.startAnimation(animFadeIn);
                    submitButton.startAnimation(animFadeIn);
                    break;
                case 1:
                    //Hide and update appropriate views
                    q1RadioGroup.setVisibility(View.GONE);
                    questionText.setText(getString(R.string.question_2_text, userName));
                    headerText.setText(R.string.question_2_header_text);
                    //Bring question 2 to front so clickable
                    q2CheckboxGroup.bringToFront();
                    submitButton.bringToFront();
                    //Show appropriate views
                    q2CheckboxGroup.setVisibility(View.VISIBLE);
                    q2CheckboxGroup.startAnimation(animFadeIn);
                    headerText.startAnimation(animFadeIn);
                    questionText.startAnimation(animFadeIn);
                    submitButton.startAnimation(animFadeIn);
                    break;
                case 2:
                    q2CheckboxGroup.setVisibility(View.GONE);
                    questionText.setText(R.string.question_3_text);
                    headerText.setText(R.string.question_3_header_text);
                    //Bring question 3 to front so clickable
                    q3RadioGroup.bringToFront();
                    submitButton.bringToFront();
                    //Show appropriate views
                    q3RadioGroup.setVisibility(View.VISIBLE);
                    q3RadioGroup.startAnimation(animFadeIn);
                    headerText.startAnimation(animFadeIn);
                    questionText.startAnimation(animFadeIn);
                    submitButton.startAnimation(animFadeIn);
                    break;
                case 3:
                    q3RadioGroup.setVisibility(View.GONE);
                    questionText.setText(R.string.question_4_text);
                    headerText.setText(R.string.question_4_header_text);
                    //Bring question 4 to front so clickable
                    showQ4Input();
                    submitButton.bringToFront();
                    //Show appropriate views
                    q4Input.startAnimation(animFadeIn);
                    headerText.startAnimation(animFadeIn);
                    questionText.startAnimation(animFadeIn);
                    submitButton.startAnimation(animFadeIn);
                    break;
                case 4:
                    hideQ4Input();
                    questionText.setText(getString(R.string.question_5_text, userName));
                    headerText.setText(R.string.question_5_header_text);
                    //Bring question 5 to front so clickable
                    q5CheckboxGroup.bringToFront();
                    submitButton.bringToFront();
                    //Show appropriate views
                    q5CheckboxGroup.setVisibility(View.VISIBLE);
                    q5CheckboxGroup.startAnimation(animFadeIn);
                    headerText.startAnimation(animFadeIn);
                    questionText.startAnimation(animFadeIn);
                    submitButton.startAnimation(animFadeIn);
                    break;
                case 5:
                    q5CheckboxGroup.setVisibility(View.GONE);
                    questionText.setText(R.string.question_6_text);
                    headerText.setText(R.string.question_6_header_text);
                    //Bring question 6 to front so clickable
                    q6RadioGroup.bringToFront();
                    submitButton.bringToFront();
                    //Show appropriate views
                    q6RadioGroup.setVisibility(View.VISIBLE);
                    q6RadioGroup.startAnimation(animFadeIn);
                    headerText.startAnimation(animFadeIn);
                    questionText.startAnimation(animFadeIn);
                    submitButton.startAnimation(animFadeIn);
                    break;
                case 6:
                    displaySummary = true; //Insure detailed summary is not displayed to start
                    q6RadioGroup.setVisibility(View.GONE);
                    if(correctAnswers == 0) {
                        questionText.setText(getString(R.string.none_correct_text, userName));
                    } else if (correctAnswers < 5){
                        questionText.setText(getString(R.string.less_than_5_correct_text, userName, String.valueOf(correctAnswers)));
                    } else {
                        questionText.setText(getString(R.string.more_than_5_correct_text, userName, String.valueOf(correctAnswers)));

                    }
                    headerText.setText(R.string.results_header_text);
                    submitButton.setText(R.string.restart_quiz_btn_text);
                    //Bring summary detail view to front so clickable
                    summaryText.bringToFront();
                    submitButton.bringToFront();
                    //Show appropriate views
                    summaryText.setVisibility(View.VISIBLE);
                    summaryText.startAnimation(animFadeIn);
                    headerText.startAnimation(animFadeIn);
                    questionText.startAnimation(animFadeIn);
                    submitButton.startAnimation(animFadeIn);
                    detailedSummary();
                    break;
                default:
                    break;
            }
            questionCount++; //increase question count
        }


    }

    @Override
    public void onAnimationRepeat(Animation animation) {}

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    /*
     * When begin/submit button is clicked
     */
    public void onBeginSubmit() {
            switch (questionCount) {
                case 0:
                    if(clearInputFields(nameInputField, userName)) {
                        if (firstTime) {
                            nameInputField.startAnimation(inputFadeOut);
                        }
                        userName = nameInputField.getText().toString().trim();
                        //Fade out intro elements
                        questionText.startAnimation(animFadeOut);
                        submitButton.startAnimation(animFadeOut);
                    }
                    break;
                case 1:
                    if(clearRadioQuestions(q1RadioGroup)) { //Check at least one radio is selected
                        //Fade out question 1 elements
                        q1RadioGroup.startAnimation(q1SolutionFadeOut);
                        questionText.startAnimation(animFadeOut);
                        submitButton.startAnimation(animFadeOut);
                        //Check answer question 1
                        q1ID = q1RadioGroup.getCheckedRadioButtonId();
                        if (q1ID == q1Ans.getId()) {
                            q1Result = true;
                            correctAnswers += 1;
                        }
                    }
                    break;
                case 2:
                    if(clearCheckboxQuestions(q2Ans1, q2Ans2, q2Ans3, q2Ans4)) { //Check at least one checkbox is selected
                        //Fade out question 2 elements
                        q2CheckboxGroup.startAnimation(q2SolutionFadeOut);
                        questionText.startAnimation(animFadeOut);
                        submitButton.startAnimation(animFadeOut);
                        //Check answer question 2
                        if(q2Ans1.isChecked() && q2Ans3.isChecked() && !q2Ans2.isChecked() && !q2Ans4.isChecked()) {
                            q2Result = true;
                            correctAnswers = correctAnswers + 1;
                        }
                    }
                    break;
                case 3:
                    if(clearRadioQuestions(q3RadioGroup)) { //Check at least one radio is selected
                        //Fade out question 3 elements
                        q3RadioGroup.startAnimation(q3SolutionFadeOut);
                        questionText.startAnimation(animFadeOut);
                        submitButton.startAnimation(animFadeOut);
                        //Check answer question 3
                        q3ID = q3RadioGroup.getCheckedRadioButtonId();
                        if (q3ID == q3Ans.getId()) {
                            q3Result = true;
                            correctAnswers += 1;
                        }
                    }
                    break;
                case 4:
                    if(clearInputFields(q4Input, q4Answer)) {
                        //Fade out question 4 elements
                        q4Input.startAnimation(q4SolutionFadeOut);
                        questionText.startAnimation(animFadeOut);
                        submitButton.startAnimation(animFadeOut);
                        //Check answer question 4
                        q4Answer = q4Input.getText().toString().trim();
                        if (q4Answer.matches(getString(R.string.q4_input_ans1)) || q4Answer.matches(getString(R.string.q4_input_ans2))) {
                            correctAnswers += 1;
                            q4Result = true;
                        }
                    }
                    break;
                case 5:
                    if(clearCheckboxQuestions(q5Ans1, q5Ans2, q5Ans3, q5Ans4)) { //Check at least one checkbox is selected
                        //Fade out question 5 elements
                        q5CheckboxGroup.startAnimation(q5SolutionFadeOut);
                        questionText.startAnimation(animFadeOut);
                        submitButton.startAnimation(animFadeOut);
                        //Check answer question 5
                        if(q5Ans1.isChecked() && q5Ans2.isChecked() && q5Ans4.isChecked() && !q5Ans3.isChecked()) {
                            q5Result = true;
                            correctAnswers = correctAnswers + 1;
                        }
                    }
                    break;
                case 6:
                    if(clearRadioQuestions(q6RadioGroup)) { //Check at least one radio is selected
                        //Fade out question 6 elements
                        q6RadioGroup.startAnimation(q6SolutionFadeOut);
                        questionText.startAnimation(animFadeOut);
                        submitButton.startAnimation(animFadeOut);
                        //Check answer question 6
                        q6ID = q6RadioGroup.getCheckedRadioButtonId();
                        if (q6ID == q6Ans.getId()) {
                            q6Result = true;
                            correctAnswers += 1;
                        }
                    }
                    break;
                case 7:
                    //Reset quiz
                    questionCount = 0;
                    correctAnswers = 0;
                    q1RadioGroup.clearCheck();
                    q2Ans1.setChecked(false);
                    q2Ans2.setChecked(false);
                    q2Ans3.setChecked(false);
                    q2Ans4.setChecked(false);
                    q3RadioGroup.clearCheck();
                    q4Input.setText("");
                    q5Ans1.setChecked(false);
                    q5Ans2.setChecked(false);
                    q5Ans3.setChecked(false);
                    q5Ans4.setChecked(false);
                    q6RadioGroup.clearCheck();
                    q1Result = false;
                    q2Result = false;
                    q3Result = false;
                    q4Result = false;
                    q5Result = false;
                    q6Result = false;
                    questionText.startAnimation(animFadeOut);
                    submitButton.startAnimation(animFadeOut);
                    summaryText.startAnimation(summaryTextFadeOut);
                    firstTime = false;
                    break;
                default:
                    break;
            }
    }

    /*
     * Check if RadioGroup is empty
     *
     * solutionGroup = a group of radio buttons
     */
    boolean clearRadioQuestions(RadioGroup solutionGroup) {
        if (solutionGroup.getCheckedRadioButtonId() != -1) {
            return true;
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.select_radio_warning, userName), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*
     * Check if checkboxes are empty
     *
     * one = a checkbox view
     * two = a checkbox view
     * three = a checkbox view
     * four = a checkbox view
     */
    boolean clearCheckboxQuestions(CheckBox one, CheckBox two, CheckBox three, CheckBox four) {
        boolean box1 = one.isChecked();
        boolean box2 = two.isChecked();
        boolean box3 = three.isChecked();
        boolean box4 = four.isChecked();
        if(!box1 && !box2 && !box3 && !box4) {
            Toast.makeText(getBaseContext(), getString(R.string.select_checkbox_warning, userName), Toast.LENGTH_SHORT).show();
            return false;
        } else{
            return true;
        }
    }

    /*
     * Check if input fields are empty
     *
     * field = EditText view
     * input = String value of input field
     */
    boolean clearInputFields(EditText field, String input) {
        input = field.getText().toString().trim();
        if(input.matches("")) {
            if(questionCount == 0) {
                Toast.makeText(getBaseContext(), R.string.enter_name_warning, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), getString(R.string.enter_answer_warning, userName), Toast.LENGTH_SHORT).show();
            }
            return false;
        } else {
            return true;
        }
    }

    /*
     * Hide name edit text field
     */
    public void hideNameInput() {
        nameInputField.setVisibility(View.GONE);
        nameInputField.setEnabled(false);
        nameInputField.setFocusable(false);
    }

    /*
     * Hide question 4 answer edit text field
     */
    public void hideQ4Input() {
        q4Input.setVisibility(View.GONE);
        q4Input.setEnabled(false);
    }

    /*
     * Show question 4 answer edit text field
     */
    public void showQ4Input() {
        q4Input.bringToFront();
        q4Input.setVisibility(View.VISIBLE);
        q4Input.setEnabled(true);
        q4Input.setFocusable(true);
    }

    /*
     * Create a detailed summary of quiz answers
     */
    public void detailedSummary() {
        displaySummary =!displaySummary;
        String summaryMessage = getString(R.string.detailed_summary_initial);
        if(displaySummary) {
            if(q1Result) {
                summaryMessage += getString(R.string.q1_correct);
            } else {
                summaryMessage += getString(R.string.q1_incorrect);
            }
            summaryMessage += getString(R.string.q1_summary_statement);
            if(q2Result) {
                summaryMessage += getString(R.string.q2_correct);
            } else {
                summaryMessage += getString(R.string.q2_incorrect);
            }
            summaryMessage += getString(R.string.q2_summary_statement);
            if(q3Result) {
                summaryMessage += getString(R.string.q3_correct);
            } else {
                summaryMessage += getString(R.string.q3_incorrect);
            }
            summaryMessage += getString(R.string.q3_statement);
            if(q4Result) {
                summaryMessage += getString(R.string.q4_correct);
            } else {
                summaryMessage += getString(R.string.q4_incorrect);
            }
            summaryMessage += getString(R.string.q4_statement);
            if(q5Result) {
                summaryMessage += getString(R.string.q5_correct);
            } else {
                summaryMessage += getString(R.string.q5_incorrect);
            }
            summaryMessage += getString(R.string.q5_statement);
            if(q6Result) {
                summaryMessage += getString(R.string.q6_correct);
            } else {
                summaryMessage += getString(R.string.q6_incorrect);
            }
            summaryMessage += getString(R.string.q6_statement);
            summaryMessage += getString(R.string.summary_tap_hide_text);
            summaryText.setText(summaryMessage);
        } else {
            summaryText.setText(getString(R.string.summary_tap_text));
        }
    }

    /*
     * Global On click listener for all clickable views
     */
    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.moreButton:
                    //go to website
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://www.diabetes.org/"));
                    startActivity(intent);
                    break;
                case R.id.submitButton:
                    onBeginSubmit();
                    break;
                case R.id.summaryText:
                    detailedSummary();
                    break;
            }
        }
    };
}
