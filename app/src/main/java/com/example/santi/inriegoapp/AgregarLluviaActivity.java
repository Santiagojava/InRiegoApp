package com.example.santi.inriegoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.santi.inriegoapp.Adapters.PivotAdapter;
import com.example.santi.inriegoapp.Adapters.SeleccionPivotAdapter;
import com.example.santi.inriegoapp.Objects.Pivot;
import com.example.santi.inriegoapp.sqlite.BD;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class AgregarLluviaActivity extends Activity {
EditText fecha,mm;
    Typeface face;
    int dyear, dmonth, dday;
    static  final int id=0;
   ImageButton bt;
    BD db1;
    ArrayList<Pivot> lista;
    ArrayList<Pivot> Seleccionados;
    ListView l ;
    Toolbar t;
    Button b;
    String token="";
    Timestamp timestamp;
    SeleccionPivotAdapter e;
    private DatePickerDialog.OnDateSetListener dpickerListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_lluvia);

        token=getIntent().getStringExtra("token");
        mm= (EditText) findViewById(R.id.cantidad_mm_ll);
        b= (Button) findViewById(R.id.bt_agregar_lluvia);
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

  e=  new SeleccionPivotAdapter(this,lista,face);
        e.setPivots(lista);
        l.setAdapter(e);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lista.get(position).ischecked()){
                    lista.get(position).setIschecked(false);
                }
                else
                {
                    lista.get(position).setIschecked(true);
                }
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
        db1=new BD(AgregarLluviaActivity.this," inriego.db",null,1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timestamp = new Timestamp(System.currentTimeMillis());
                SQLiteDatabase db = db1.getWritableDatabase();
lista=e.getPivots();
                for(int i=0;i<lista.size();i++) {
                    Pivot p=lista.get(i);ContentValues lluvia = new ContentValues();
                    JSONObject reg = new JSONObject();
                    if(p.ischecked()) {
                        try {
                            reg.put("Token", token);
                            reg.put("IrrigationUnitId", i);
                            reg.put("Milimeters", mm.getText());
                            reg.put("Date", fecha.getText());
                            lluvia.put("JSON", String.valueOf(reg));
                            lluvia.put("REG", String.valueOf(timestamp));
                            db.insert("INGRESOS", null, lluvia);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                db.close();

            }
        });
    }


}
