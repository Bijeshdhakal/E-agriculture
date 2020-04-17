package com.bjshDkl.agriculture.analysis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.analysis.database.MyCropDatabase;
import com.bjshDkl.agriculture.analysis.model.CropDetailModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AnalyisActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton addDataFAb;
    MyCropDatabase cropDatabase;

    ImageView noDataImageView;
    ScrollView dataavailableLL;
    LineChart incomeExpenseLineChart;
    LineChart goodsLineChart;

    List<CropDetailModel> cropDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyis);


        cropDatabase = MyCropDatabase.getInstance(AnalyisActivity.this);

        bindActivity();
        getDatabase();
    }

    private void getDatabase() {
        cropDetails.clear();
        cropDetails = cropDatabase.myDao().getCropDetails();
        if (cropDetails.size() > 0) {
            noDataImageView.setVisibility(View.GONE);
            dataavailableLL.setVisibility(View.VISIBLE);

            setUpGraphView();
        } else {
            noDataImageView.setVisibility(View.VISIBLE);
            dataavailableLL.setVisibility(View.GONE);
        }

    }

    private void setUpGraphView() {

        setUpIncomeExpenseGraph();
        setUpGoodsGraph();

    }

    private void setUpGoodsGraph() {
        ArrayList<Entry> producedDatas = new ArrayList<>();
        ArrayList<Entry> soldDatas = new ArrayList<>();

        for (int i = 0; i < cropDetails.size(); i++) {
            producedDatas.add(new Entry(cropDetails.get(i).getDay(), cropDetails.get(i).getProducedGoods()));
            soldDatas.add(new Entry(cropDetails.get(i).getDay(), cropDetails.get(i).getSoldGoods()));
        }


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet producedGoodsLine = new LineDataSet(producedDatas, "Produced Goods");
        producedGoodsLine.setDrawCircles(false);
        producedGoodsLine.setColor(Color.GREEN);
        producedGoodsLine.setLineWidth(4);

        LineDataSet soldGoodsLine = new LineDataSet(soldDatas, "Sold Goods");
        soldGoodsLine.setDrawCircles(false);
        soldGoodsLine.setColor(Color.RED);
        soldGoodsLine.setLineWidth(4);


        lineDataSets.add(producedGoodsLine);
        lineDataSets.add(soldGoodsLine);

        goodsLineChart.setData(new LineData(lineDataSets));
        goodsLineChart.invalidate();

        XAxis xAxis = goodsLineChart.getXAxis();
        xAxis.setLabelCount(1);


    }

    private void setUpIncomeExpenseGraph() {
        ArrayList<Entry> incomeDatas = new ArrayList<>();
        ArrayList<Entry> expenseDatas = new ArrayList<>();

        for (int i = 0; i < cropDetails.size(); i++) {
            incomeDatas.add(new Entry(cropDetails.get(i).getDay(), cropDetails.get(i).getIncome()));
            expenseDatas.add(new Entry(cropDetails.get(i).getDay(), cropDetails.get(i).getExpense()));
        }


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet incomeLine = new LineDataSet(incomeDatas, "Income");
        incomeLine.setDrawCircles(false);
        incomeLine.setColor(Color.GREEN);
        incomeLine.setLineWidth(4);

        LineDataSet expenseLine = new LineDataSet(expenseDatas, "Expense");
        expenseLine.setDrawCircles(false);
        expenseLine.setColor(Color.RED);
        expenseLine.setLineWidth(4);


        lineDataSets.add(incomeLine);
        lineDataSets.add(expenseLine);

        incomeExpenseLineChart.setData(new LineData(lineDataSets));
        incomeExpenseLineChart.invalidate();

        XAxis xAxis = incomeExpenseLineChart.getXAxis();
        xAxis.setLabelCount(1);


    }

    private void bindActivity() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addDataFAb = (FloatingActionButton) findViewById(R.id.addDataFAB);
        addDataFAb.setOnClickListener(this);

        noDataImageView = (ImageView) findViewById(R.id.noDataImageView);
        dataavailableLL = (ScrollView) findViewById(R.id.dataLL);

        incomeExpenseLineChart = (LineChart) findViewById(R.id.incomeExpenseLineChart);
        goodsLineChart = (LineChart) findViewById(R.id.goodsLineChart);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addDataFAB:
                showCropDetail();
                break;
        }

    }

    private void showCropDetail() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogview = inflater.inflate(R.layout.dialog_add_crop, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Add Crop Detail of this day.");
//        alertDialog.setIcon("Icon id here");
        alertDialog.setCancelable(false);


        final EditText soldGoodsET = (EditText) dialogview.findViewById(R.id.soldGoodsET);
        final EditText producedGoodsET = (EditText) dialogview.findViewById(R.id.producedGoodsET);
        final EditText incomeET = (EditText) dialogview.findViewById(R.id.incomeET);
        final EditText expenseET = (EditText) dialogview.findViewById(R.id.expenseET);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Float soldGoods = Float.parseFloat(soldGoodsET.getText().toString());
                Float producedGoods = Float.parseFloat(producedGoodsET.getText().toString());
                Float income = Float.parseFloat(incomeET.getText().toString());
                Float expense = Float.parseFloat(expenseET.getText().toString());

                if (soldGoodsET.getText().toString().length() <= 0) {
                    soldGoodsET.setError("Enter this field.");
                } else if (producedGoodsET.getText().toString().length() <= 0) {
                    producedGoodsET.setError("Enter this field.");

                } else if (incomeET.getText().toString().length() <= 0) {
                    incomeET.setError("Enter this field.");

                } else if (expenseET.getText().toString().length() <= 0) {
                    expenseET.setError("Enter this field.");

                } else {
                    CropDetailModel cropDetailModel = new CropDetailModel();
                    cropDetailModel.setSoldGoods(soldGoods);
                    cropDetailModel.setProducedGoods(producedGoods);
                    cropDetailModel.setIncome(income);
                    cropDetailModel.setExpense(expense);
                    cropDetailModel.setProfit(expense - income);

                    cropDatabase.myDao().insertSample(cropDetailModel);
                    AnalyisActivity.this.recreate();
                    Toast.makeText(AnalyisActivity.this, "Data saved.", Toast.LENGTH_LONG).show();


                }


            }
        });


        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });


        alertDialog.setView(dialogview);
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
