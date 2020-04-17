package com.bjshDkl.agriculture.price;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bjshDkl.agriculture.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriceFragment extends Fragment {


    RecyclerView priceRecyclerView;
    ArrayList<PriceModel> priceModelArrayList = new ArrayList<>();
    PriceAdapter priceAdapter;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://kalimati.herokuapp.com/";

    public PriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_price, container, false);

        bindFragment(rootView);
        getPriceData();

        return rootView;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        priceAdapter = new PriceAdapter(getActivity(), priceModelArrayList);


        priceRecyclerView.setLayoutManager(linearLayoutManager);
        priceRecyclerView.setAdapter(priceAdapter);

    }

    private void getPriceData() {
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    // jsonString is a string variable that holds the JSON
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray itemArray = jsonObject.getJSONArray("retail");
                    for (int i = 0; i < itemArray.length(); i++) {

                        PriceModel priceModel = new PriceModel();

                        JSONArray item = itemArray.getJSONArray(i);

                        priceModel.setItemName(item.getString(0));
                        priceModel.setItemUnit(item.getString(1));
                        priceModel.setItemPrice(item.getString(4));

                        priceModelArrayList.add(priceModel);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("json", priceModelArrayList.size() + " ");
                initRecyclerView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error Fetching Data", Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(mStringRequest);

    }

    private void bindFragment(View rootView) {
        priceRecyclerView = rootView.findViewById(R.id.priceRecyclerView);

    }

}
