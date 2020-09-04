package co.ke.tonyoa.android.associate_android_developer_practice_project;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.TextView;

import java.util.List;

public class Utils {

    public static boolean areTheSame(Object firstObject, Object otherObject) {
        boolean same=false;
        //Check to prevent null exceptions
        if (firstObject !=null){
            if (firstObject.equals(otherObject))
                same= true;
        }
        //If the original is null and the other is null, they are the same
        else if (otherObject == null){
            same=true;
        }
        return same;
    }

    public static boolean isNotNull(Object object){
        return object!=null;
    }

    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm==null)
            return false;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static boolean isNotEmpty(TextView textView){
        if (textView==null)
            return false;
        String text=textView.getText().toString().trim();
        return !text.isEmpty();
    }

    public static TextView isEmptyTextView(List<TextView> textViews){
        for (TextView textView: textViews){
            if (!isNotEmpty(textView))
                return textView;
        }
        return null;
    }


    public static String getTextViewText(TextView textView){
        if (textView==null)
            return null;
        return textView.getText().toString();
    }
}
