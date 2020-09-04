package co.ke.tonyoa.android.associate_android_developer_practice_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.ActivitySubmissionBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.AppBarSubmissionBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.ContentSubmissionBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.DialogAreYouSureBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.DialogSuccessfulBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.DialogUnsuccessfulBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels.SubmissionActivityViewModel;

import static co.ke.tonyoa.android.associate_android_developer_practice_project.Utils.getTextViewText;
import static co.ke.tonyoa.android.associate_android_developer_practice_project.Utils.isEmptyTextView;

public class SubmissionActivity extends AppCompatActivity {

    private ActivitySubmissionBinding mActivitySubmissionBinding;
    private AppBarSubmissionBinding mAppBarSubmissionBinding;
    private ContentSubmissionBinding mContentSubmissionBinding;
    private SubmissionActivityViewModel mSubmissionActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySubmissionBinding=ActivitySubmissionBinding.inflate(getLayoutInflater());
        setContentView(mActivitySubmissionBinding.getRoot());
        mAppBarSubmissionBinding=mActivitySubmissionBinding.layoutSubmissionAppbar;
        mContentSubmissionBinding=mActivitySubmissionBinding.layoutSubmissionContent;

        setSupportActionBar(mAppBarSubmissionBinding.toolBarSubmission);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mSubmissionActivityViewModel=new ViewModelProvider(this).get(SubmissionActivityViewModel.class);
        //If loading, make items inactive
        mSubmissionActivityViewModel.getLoadingMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                Boolean performPost=mSubmissionActivityViewModel.getPerformPost().getValue();
                if (performPost!=null && performPost) {
                    List<View> views=Arrays.asList(mContentSubmissionBinding.textInputEditTextSubmissionGithub,
                            mContentSubmissionBinding.textInputEditTextSubmissionLastName,
                            mContentSubmissionBinding.textInputEditTextSubmissionFirstName,
                            mContentSubmissionBinding.textInputEditTextSubmissionEmail,
                            mContentSubmissionBinding.textViewSubmissionSubmit,
                            mContentSubmissionBinding.groupSubmission);
                    if (isLoading) {
                        enableViews(false, views);
                        mContentSubmissionBinding.progressBarSubmission.setVisibility(View.VISIBLE);
                    } else {
                        enableViews(true, views);
                        mContentSubmissionBinding.progressBarSubmission.setVisibility(View.GONE);
                    }
                }
            }
        });
        //Notify user of success or failure
        mSubmissionActivityViewModel.getSuccessMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                Boolean performPost=mSubmissionActivityViewModel.getPerformPost().getValue();
                if (performPost!=null && performPost) {
                    if (success) {
                        DialogSuccessfulBinding dialogSuccessfulBinding=DialogSuccessfulBinding.inflate(getLayoutInflater(),
                                null, false);
                        AlertDialog.Builder builder=new AlertDialog.Builder(SubmissionActivity.this);
                        builder.setView(dialogSuccessfulBinding.getRoot());
                        builder.create().show();
                    }
                    else {
                        DialogUnsuccessfulBinding dialogUnsuccessfulBinding=DialogUnsuccessfulBinding.inflate(getLayoutInflater(),
                                null, false);
                        AlertDialog.Builder builder=new AlertDialog.Builder(SubmissionActivity.this);
                        builder.setView(dialogUnsuccessfulBinding.getRoot());
                        builder.create().show();
                    }
                }
            }
        });

        mContentSubmissionBinding.textViewSubmissionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyField())
                    return;
                String email=getTextViewText(mContentSubmissionBinding.textInputEditTextSubmissionEmail);
                String firstName=getTextViewText(mContentSubmissionBinding.textInputEditTextSubmissionFirstName);
                String lastName=getTextViewText(mContentSubmissionBinding.textInputEditTextSubmissionLastName);
                String githubLink=getTextViewText(mContentSubmissionBinding.textInputEditTextSubmissionGithub);
                if (isEmailOrLinkInvalid(email, githubLink))
                    return;

                showConfirmDialog(email, firstName, lastName, githubLink);
            }
        });
    }

    private void showConfirmDialog(String email, String firstName, String lastName, String githubLink) {
        DialogAreYouSureBinding dialogAreYouSureBinding=DialogAreYouSureBinding.inflate(getLayoutInflater(),
                null, false);
        AlertDialog.Builder builder=new AlertDialog.Builder(SubmissionActivity.this);
        builder.setView(dialogAreYouSureBinding.getRoot());
        builder.setCancelable(false);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        dialogAreYouSureBinding.imageViewDialogSureCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        dialogAreYouSureBinding.textViewDialogSureYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mSubmissionActivityViewModel.submitForm(email, firstName, lastName, githubLink);
            }
        });
    }

    private void enableViews(boolean enable, List<View> views){
        for (View view:views){
            view.setEnabled(enable);
        }
    }

    private boolean isEmailOrLinkInvalid(String email, String githubLink) {
        if (!isValidEmail(email)){
            mContentSubmissionBinding.textInputEditTextSubmissionEmail.setError("Enter a valid email address");
            mContentSubmissionBinding.textInputEditTextSubmissionEmail.requestFocus();
            return true;
        }
        if (!isValidWebsite(githubLink)){
            mContentSubmissionBinding.textInputEditTextSubmissionGithub.setError("Enter a valid website link");
            mContentSubmissionBinding.textInputEditTextSubmissionGithub.requestFocus();
            return true;
        }
        return false;
    }

    private boolean isEmptyField() {
        List<TextView> textViews= Arrays.asList(mContentSubmissionBinding.textInputEditTextSubmissionFirstName,
                mContentSubmissionBinding.textInputEditTextSubmissionLastName,
                mContentSubmissionBinding.textInputEditTextSubmissionEmail,
                mContentSubmissionBinding.textInputEditTextSubmissionGithub);
        TextView emptyTextView=isEmptyTextView(textViews);
        if (emptyTextView!=null){
            emptyTextView.setError("Please fill this field");
            emptyTextView.requestFocus();
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isValidWebsite(String website){
        Pattern pattern = Patterns.WEB_URL;
        return pattern.matcher(website).matches();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}