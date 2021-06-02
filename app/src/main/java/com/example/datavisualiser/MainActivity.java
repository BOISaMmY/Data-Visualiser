package com.example.datavisualiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;

import com.opencsv.CSVReaderHeaderAware;
import com.example.datavisualiser.R;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            AssetFileDescriptor descriptor = getAssets().openFd("data_accidental_deaths.csv");
            Map<String, String> var = new CSVReaderHeaderAware(new FileReader(descriptor.getFileDescriptor())).readMap();
            for(Map.Entry<String,String> entry : var.entrySet()){
                System.out.println(entry.getKey()+ " "+ entry.getValue()+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}