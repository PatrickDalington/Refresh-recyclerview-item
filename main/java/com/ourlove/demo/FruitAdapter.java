package com.ourlove.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {


    Context context;
    List<String> fruits;
    String mFruit;
    TextToSpeech tts;

    public FruitAdapter(Context context,List<String> fruits){
        this.context = context;
        this.fruits = fruits;
    }


    @NonNull
    @Override
    public FruitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fruit_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitAdapter.ViewHolder holder, int position) {


        mFruit = fruits.get(position);

        holder.name.setText(mFruit);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFruit = fruits.get(position);

                tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS){
                            int result = tts.setLanguage(Locale.ENGLISH);
                            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                                Toast.makeText(context, "Language not supported", Toast.LENGTH_SHORT).show();
                            }else{
                                tts.speak(mFruit,TextToSpeech.QUEUE_FLUSH,null);
                            }

                        }
                    }
                });
                Toast.makeText(context, mFruit, Toast.LENGTH_SHORT).show();

            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int x = position;
                CharSequence[] delete = {
                        "Delete"
                };
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setItems(delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){

                            fruits.remove(x);
                            notifyItemRemoved(x);
                        }
                    }
                });

                alert.create().show();

                return false;
            }
        });




    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
        }
    }

}
