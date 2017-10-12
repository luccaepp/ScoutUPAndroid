package com.tcc.lucca.scoutup.activitys;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.gerenciar.ListItemMaterialAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdicionarAtividadeActivity extends AppCompatActivity {


    private FragmentManager fragmentManager;
    private EditText editTextNome;
    private EditText editTextOutro;
    private List<String> materiais = new ArrayList<>();
    private ListItemMaterialAdapter adapter;
    private ListView listMateriais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_atividade);
        initComponents();


        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, new MapsFrag(), "Maps Frag");

        fragmentTransaction.commitAllowingStateLoss();

        adapter = new ListItemMaterialAdapter(this, materiais);

        listMateriais.setAdapter(adapter);


    }

    private void initComponents() {

        TextView tvAgenda = (TextView) findViewById(R.id.textViewTitulo);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvAgenda.setTypeface(type);

        editTextNome = (EditText) findViewById(R.id.etNome);
        editTextNome.requestFocus();
        editTextOutro = (EditText) findViewById(R.id.etItem);
        listMateriais = (ListView) findViewById(R.id.listViewMateriais);


    }

    public void adicionarMaterial(View view) {

        String material = editTextOutro.getText().toString().trim();
        materiais = adapter.getInfo();
        materiais.add(material);
        adapter.setInfo(materiais);
        adapter.notifyDataSetChanged();
        listMateriais.setAdapter(adapter);
        editTextOutro.setText("");
        view.invalidate();
        setListViewHeightBasedOnItems();


    }


    public boolean setListViewHeightBasedOnItems() {

        ListView listView = listMateriais;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }


    public void dataInicio(View view) {

        final Button time = (Button) findViewById(R.id.btnDataInicio);
        Calendar mcurrentTime = Calendar.getInstance();
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        int month = mcurrentTime.get(Calendar.MONTH);
        int year = mcurrentTime.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog;

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                time.setText("Data Inicio " + dia + "/" + mes + "/" + ano);

            }
        }, year, month, day);

        datePickerDialog.setTitle("Select Time");
        datePickerDialog.show();
    }

    public void dataFim(View view) {

        final Button time = (Button) findViewById(R.id.btnDataFim);
        Calendar mcurrentTime = Calendar.getInstance();
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        int month = mcurrentTime.get(Calendar.MONTH);
        int year = mcurrentTime.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog;

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                time.setText("Data Fim " + dia + "/" + mes + "/" + ano);

            }
        }, year, month, day);

        datePickerDialog.setTitle("Select Time");
        datePickerDialog.show();
    }


    public void horaInicio(View view) {

        final Button time = (Button) findViewById(R.id.btnHoraInicio);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time.setText("Horário inicial " + selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void horaFim(View view) {

        final Button time = (Button) findViewById(R.id.btnHoraFim);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time.setText("Horário final " + selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

}
