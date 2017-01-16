package com.example.zhang.homework9;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link bill_main.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link bill_main#newInstance} factory method to
 * create an instance of this fragment.
 */
@TargetApi(11)
public class bill_main extends Fragment
    implements LoaderCallbacks<List<Bill>>{

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String CongressApi = "https://congress.api.sunlightfoundation.com/bills";


    private static final int billLoaderId = 2;

    private BillAdapter bAdapterActive;
    private BillAdapter bAdapterNew;

    private TextView mEmptyStateTextViewActive;
    private TextView mEmptyStateTextViewNew;

    private List<Bill> billActiveList;
    private List<Bill> billNewList;





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public bill_main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bill_main.
     */
    // TODO: Rename and change types and number of parameters
    public static bill_main newInstance(String param1, String param2) {
        bill_main fragment = new bill_main();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_main, container, false);
    }



    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        ListView billListViewActive = (ListView) getView().findViewById(R.id.list_bill_active);
        mEmptyStateTextViewActive = (TextView) getView().findViewById(R.id.empty_view_bill_active);
        billListViewActive.setEmptyView(mEmptyStateTextViewActive);

        ListView billListViewNew = (ListView) getView().findViewById(R.id.list_bill_new);
        mEmptyStateTextViewNew = (TextView) getView().findViewById(R.id.empty_view_bill_new);
        billListViewNew.setEmptyView(mEmptyStateTextViewNew);

        bAdapterActive = new BillAdapter(getActivity(), new ArrayList<Bill>());
        bAdapterNew = new BillAdapter(getActivity(), new ArrayList<Bill>());
        billListViewActive.setAdapter(bAdapterActive);
        billListViewNew.setAdapter(bAdapterNew);

        billListViewActive.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.zhang.homework9.BillDetailActivity");
                intent.putExtra("myBill", billActiveList.get(position));
                startActivity(intent);
            }
        });

        billListViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.zhang.homework9.BillDetailActivity");
                intent.putExtra("myBill", billNewList.get(position));
                startActivity(intent);

            }
        });

        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            android.app.LoaderManager loaderManager = this.getLoaderManager();
            loaderManager.initLoader(billLoaderId, null, this);
        }
        else{
            mEmptyStateTextViewActive.setText("No Internet Connection");
            mEmptyStateTextViewNew.setText("No Internet Connection");
        }

        TabHost tab = (TabHost) getView().findViewById(android.R.id.tabhost);
        tab.setup();
        TabHost.TabSpec spec1 = tab.newTabSpec("billActive");
        spec1.setIndicator("Active Bill");
        spec1.setContent(R.id.billActive);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("billNew");
        spec2.setIndicator("New Bill");
        spec2.setContent(R.id.billNew);
        tab.addTab(spec2);



    }







    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction("Bills");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag);
    }

    @Override
    public Loader<List<Bill>> onCreateLoader(int id, Bundle args){
        Uri baseUri = Uri.parse(CongressApi);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("per_page", "all");
        uriBuilder.appendQueryParameter("apikey", "0a0ed38f64bc40629c4df4cd22a23f27");
        return new BillLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Bill>> loader, List<Bill> data){
        mEmptyStateTextViewActive.setText("No Legislator Found");
        mEmptyStateTextViewNew.setText("No Legislator Found");
        bAdapterActive.clear();
        bAdapterNew.clear();

        if(data != null && !data.isEmpty()){
            billActiveList = new ArrayList<Bill>();
            billNewList = new ArrayList<Bill>();
            Collections.sort(data, new Comparator<Bill>() {
                @Override
                public int compare(Bill o1, Bill o2) {
                    return o2.introduced.compareTo(o1.introduced);
                }
            });
            for(Bill b : data){
                if(b.isActive){
                    billActiveList.add(b);
                }
                else{
                    billNewList.add(b);
                }
            }

            bAdapterActive.addAll(billActiveList);
            bAdapterNew.addAll(billNewList);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<Bill>> loader){
        bAdapterActive.clear();
        bAdapterNew.clear();
    }
}
