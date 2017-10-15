package com.example.santi.inriegoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.santi.inriegoapp.Adapters.PivotAdapter;
import com.example.santi.inriegoapp.Adapters.SeleccionPivotAdapter;
import com.example.santi.inriegoapp.Objects.Pivot;

import java.util.ArrayList;
import java.util.Calendar;

public class AgregarLluviaActivity extends Activity {
EditText fecha;
    Typeface face;
    int dyear, dmonth, dday;
    static  final int id=0;
   ImageButton bt;
    ArrayList<Pivot> lista;
    ListView l ;
    Toolbar t;
    private DatePickerDialog.OnDateSetListener dpickerListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_lluvia);
        t= (Toolbar) findViewById(R.id.toolbar_agregarlluvia);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        face= Typeface.createFromAsset(getAssets(),"Raleway-Light.ttf");
        lista= (ArrayList<Pivot>) getIntent().getSerializableExtra("pivots");
        l= (ListView) findViewById(R.id.lista_pivots_ll);
      SeleccionPivotAdapter e=  new SeleccionPivotAdapter(this,lista,face);
        Pivot a=lista.get(0);
        Pivot b=lista.get(1);
        lista.add(a);
        lista.add(b);
        e.setPivots(lista);
        l.setAdapter(e);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

            }
        });
        fecha= (EditText) findViewById(R.id.Fecha_ll);
      bt= (ImageButton) findViewById(R.id.bt_fecha_ll);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                dyear=cal.get(Calendar.YEAR);
                dmonth=cal.get(Calendar.MONTH);
                dday=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(
                        AgregarLluviaActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dpickerListener,dyear,dmonth,dday);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis()+345600000);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis()-172800000);
                dialog.show();
            }
        });
        dpickerListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dyear=year;
                dmonth=month;
                dday=dayOfMonth;
                fecha.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
    }


}
