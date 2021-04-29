package com.example.swipabledialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;

import java.io.InputStream;
import java.net.URL;

public class MyDialog {
    private static MyDialog single_instance = null;
    private MyDialog()
    {

    }

    public static MyDialog getInstance()
    {
        if (single_instance == null)
            single_instance = new MyDialog();

        return single_instance;
    }
    public void showDialogBox(Context context, String title, String imageURL,String success_url)
    {
        View dialog = LayoutInflater.from(context).inflate(R.layout.mydialogxml, null);
        final SwipeDismissDialog swipeDismissDialog = new SwipeDismissDialog.Builder(context)
                .setView(dialog)
                .build()
                .show();
        final TextView tvTitle = (TextView) dialog.findViewById(R.id.textView);
        ImageView imageView = (ImageView)dialog.findViewById(R.id.imageView);
        Button Success = (Button) dialog.findViewById(R.id.button);


        Success.setOnClickListener(v -> {
            String url = success_url;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        });
        tvTitle.setText(title);
        LoadImageTask obj =  new LoadImageTask(imageURL,imageView);
        obj.execute();


    }
    class LoadImageTask extends AsyncTask<Void,Void,Bitmap>
    {
        private  String url;
        private  ImageView imageView;

        public  LoadImageTask(String url,ImageView imageView)
        {
            this.url = url;
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(Void... voids) {
            try{
                URL connection = new URL(url);

                InputStream inputStream = connection.openStream();
                Bitmap myBitMap = BitmapFactory.decodeStream(inputStream);
                Bitmap resized = Bitmap.createScaledBitmap(myBitMap,450,450,true);
                return  resized;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }



}
