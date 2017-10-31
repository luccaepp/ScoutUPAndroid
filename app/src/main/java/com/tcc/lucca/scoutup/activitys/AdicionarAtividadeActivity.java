package com.tcc.lucca.scoutup.activitys;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.adapters.ListItemMaterialAdapter;
import com.tcc.lucca.scoutup.adapters.PlaceAutoCompleteAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdicionarAtividadeActivity extends AppCompatActivity {


    private Spinner spinner;
    private FragmentManager fragmentManager;
    private EditText editTextNome;
    private EditText editTextOutro;
    private EditText editTextOutroItem;
    private List<String> materiais = new ArrayList<>();
    private ListItemMaterialAdapter adapter;
    private ListView listMateriais;
    private AutoCompleteTextView mACTVAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_atividade);
        initComponents();
        editTextOutroItem.setVisibility(View.INVISIBLE);

        final List<String> list = new ArrayList<String>();
        list.add("Atividade normal (sede)");
        list.add("Bivaque");
        list.add("Bivaque noturno");
        list.add("Acampamento");
        list.add("Acantonamento");
        list.add("Jornada");
        list.add("Atividade Náutica");
        list.add("Atividade Aérea");
        list.add("Visita a outro Grupo");
        list.add("Atividades Especiais");
        list.add("Outro");

        mACTVAddress = (AutoCompleteTextView) findViewById(R.id.place_autocomplete_fragment);
        mACTVAddress.setAdapter(new PlaceAutoCompleteAdapter(this));


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                if(i+1 ==  list.size()){

                    editTextOutroItem.setVisibility(View.VISIBLE);


                }else{

                    editTextOutroItem.setVisibility(View.INVISIBLE);

                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, new MapsFrag(), "Maps Frag");

        fragmentTransaction.commitAllowingStateLoss();

        mACTVAddress.setHint("Endereço");
        mACTVAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Address address = (Address) adapterView.getItemAtPosition(i);


                MapsFrag maps = (MapsFrag) getSupportFragmentManager().findFragmentById(R.id.container);


                MapsFrag newFragment = new MapsFrag();
                Bundle args = new Bundle();
                args.putParcelable("LatLng", new LatLng(address.getLatitude(), address.getLongitude()));
                newFragment.setArguments(args);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack("pilha");

                transaction.commit();

            }
        });


        adapter = new ListItemMaterialAdapter(this, materiais);

        listMateriais.setAdapter(adapter);

        listMateriais.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                materiais.remove(i);
                adapter.setInfo(materiais);
                adapter.notifyDataSetChanged();
                view.invalidate();
                setListViewHeightBasedOnItems();

                return false;
            }
        });


    }


    private void initComponents() {

        TextView tvAgenda = (TextView) findViewById(R.id.textViewTitulo);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvAgenda.setTypeface(type);

        spinner = (Spinner) findViewById(R.id.spinner);
        editTextNome = (EditText) findViewById(R.id.etNome);
        editTextNome.requestFocus();
        editTextOutro = (EditText) findViewById(R.id.etItem);
        editTextOutroItem = (EditText) findViewById(R.id.etOutro);
        listMateriais = (ListView) findViewById(R.id.listViewMateriais);


    }

    public void adicionarMaterial(View view) {

        String material = editTextOutro.getText().toString().trim();
        Log.d("Tag", material);
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
