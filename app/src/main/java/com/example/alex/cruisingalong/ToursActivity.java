package com.example.alex.cruisingalong;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ToursActivity extends AppCompatActivity {

    ListView list;
    String[] tourTitles;
    String[] tourDescriptions;
    String[] tourLinks;
    int[] images={R.drawable.amazing_newzealand_wine_tours, R.drawable.art_deco_logo, R.drawable.bay_heliwork_logo, R.drawable.boutique_horsetreks_clive, R.drawable.citywalksz_logo, R.drawable.cruise_ship_golfing_new, R.drawable.hawkes_bay_scenic_tours, R.drawable.mohaka_rafting, R.drawable.napier_prison_logo, R.drawable.vines_and_views_logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);

        Resources res = getResources();
        tourTitles = res.getStringArray(R.array.titles);
        tourDescriptions = res.getStringArray(R.array.descriptions);

        list = (ListView) findViewById(R.id.listViewtours);
        CompAdaptor adaptor = new CompAdaptor(getApplicationContext(), tourTitles, images, tourDescriptions);
        list.setAdapter(adaptor);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] links = getResources().getStringArray(R.array.links);
                Uri uri = Uri.parse(links[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }

    AdapterView.OnItemClickListener adapterClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView lvMoogle5 = (ListView)findViewById(R.id.list_item);
            lvMoogle5.setOnItemClickListener(adapterClick);
            String[] links = getResources().getStringArray(R.array.links);

            Uri uri = Uri.parse(links[position]);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    };
}