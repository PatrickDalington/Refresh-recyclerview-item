package com.ourlove.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FruitAdapter fruitAdapter;
    List<String> myFruit;
    AutoCompleteTextView search;
    EditText nameText;
    Button add,sort;
    TextToSpeech tts;

    SwipeRefreshLayout sr;
    CoordinatorLayout coordinatorLayout;



    long pressTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        sr = findViewById(R.id.swipe);
        coordinatorLayout = findViewById(R.id.main);


        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Collections.shuffle(myFruit);
                fruitAdapter.notifyDataSetChanged();

                Snackbar snackbar = Snackbar.make(coordinatorLayout,"Refreshed",Snackbar.LENGTH_INDEFINITE);
                snackbar.show();

                sr.setRefreshing(false);
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("My Alert");
        alert.setMessage("This is my alert dialog");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Information gotten", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();



        recyclerView = findViewById(R.id.recycler);
        nameText = findViewById(R.id.name);
        add = findViewById(R.id.add);
        sort = findViewById(R.id.sort);
        search = findViewById(R.id.search);



        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);


        myFruit = new ArrayList<>();

        myFruit.add("Apple");
        myFruit.add("ball");
        myFruit.add("goat");
        myFruit.add("corn");
        myFruit.add("shoe");
        myFruit.add("Cow");
        myFruit.add("Richmond");


        fruitAdapter = new FruitAdapter(this,myFruit);
        recyclerView.setAdapter(fruitAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,myFruit);
        search.setAdapter(adapter);

        

        add.setOnClickListener(v -> {

            if (TextUtils.isEmpty(nameText.getText())){
                Toast.makeText(MainActivity.this, "Please input name of fruit", Toast.LENGTH_SHORT).show();
            }else{

                myFruit.add(nameText.getText().toString().trim());
                fruitAdapter.notifyItemInserted(myFruit.size()-1);
                nameText.setText("");

            }

        });

        sort.setOnClickListener(v->{

            myFruit.sort(String::compareTo);
            fruitAdapter = new FruitAdapter(this,myFruit);
            recyclerView.setAdapter(fruitAdapter);
        });

    }

    @Override
    public void onBackPressed() {

        if (pressTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        pressTime = System.currentTimeMillis();
    }
}