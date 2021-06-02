package com.example.datavisualiser;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<BarEntry> entries = new ArrayList<>();
    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (BarChart) findViewById(R.id.chart);

        try {
            AssetFileDescriptor descriptor = getAssets().openFd("data_accidental_deaths.csv");
            CSVReader reader = new CSVReader(new FileReader(descriptor.getFileDescriptor()));
            String val[] = reader.readNext();
            val[0] = "State";
            String state, cat, total;
            String labels[] = new String[50];
            int flag = 1, index = 0;
            while (flag == 1) {
                cat = val[0];
                state = val[1];
                total = val[5];
                if (cat.equals("State")) {
                    System.out.println("State = " + state + " Total = " + total + "\n");
                    int tot = Integer.parseInt(total);
                    entries.add(new BarEntry(index, tot));
                    labels[index] = state;
                    index++;

                }
                try {
                    val = reader.readNext();
                    if (val[0].equals("last")) {
                        System.out.println("YESSSSSSSSSSSSSSSSS");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(":(");
                }
            }
            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return labels[(int) value];
                }
            };
            BarDataSet set = new BarDataSet(entries, "Number of Accidental Deaths");
            BarData data = new BarData(set);
            XAxis xAxis = chart.getXAxis();
            YAxis yAxis = chart.getAxisLeft();
            yAxis.setAxisLineWidth(3);
            xAxis.setGranularity(1);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
            xAxis.setDrawGridLines(false);
            xAxis.setAxisLineWidth(3);
            xAxis.setValueFormatter(formatter);
            data.setBarWidth(0.9f);
            Description description = chart.getDescription();
            description.setText("Graph Visualising the number of Accidental Deaths/Suicides in each state");
            chart.setData(data);
            chart.setDescription(description);
            description.setPosition(900, 200);
            chart.setFitBars(true);
            chart.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}