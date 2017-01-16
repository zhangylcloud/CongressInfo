package com.example.zhang.homework9;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link leg_main.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link leg_main#newInstance} factory method to
 * create an instance of this fragment.
 */
@TargetApi(11)
public class leg_main extends Fragment
    implements LoaderCallbacks<List<Legislator>>{


    private static final String LOG_TAG = MainActivity.class.getName();

    //private static final String CongressApi = "http://104.198.0.197:8080/legislators";
    private static final String CongressApi = "https://congress.api.sunlightfoundation.com/legislators";

    private static final int legislatorLoaderId = 1;

    private LegislatorAdapter lAdapterState;
    private LegislatorAdapter lAdapterHouse;
    private LegislatorAdapter lAdapterSenate;

    private TextView mEmptyStateTextViewState;
    private TextView mEmptyStateTextViewHouse;
    private TextView mEmptyStateTextViewSenate;

    private List<Legislator> legStateList;
    private List<Legislator> legHouseList;
    private List<Legislator> legSenateList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public leg_main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment leg_main.
     */
    // TODO: Rename and change types and number of parameters
    public static leg_main newInstance(String param1, String param2) {
        leg_main fragment = new leg_main();
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
        View RootView = inflater.inflate(R.layout.fragment_leg_main, container, false);

        return RootView;
    }



    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //Set legislator lists to empty before access data
        ListView legislatorListViewState = (ListView) getView().findViewById(R.id.list_leg_state);
        mEmptyStateTextViewState = (TextView) getView().findViewById(R.id.empty_view_leg_state);
        legislatorListViewState.setEmptyView(mEmptyStateTextViewState);

        ListView legislatorListViewHouse = (ListView) getView().findViewById(R.id.list_leg_house);
        mEmptyStateTextViewHouse = (TextView) getView().findViewById(R.id.empty_view_leg_house);
        legislatorListViewHouse.setEmptyView(mEmptyStateTextViewHouse);

        ListView legislatorListViewSenate = (ListView) getView().findViewById(R.id.list_leg_senate);
        mEmptyStateTextViewSenate = (TextView) getView().findViewById(R.id.empty_view_leg_senate);
        legislatorListViewSenate.setEmptyView(mEmptyStateTextViewSenate);



        lAdapterState = new LegislatorAdapter(getActivity(), new ArrayList<Legislator>());
        lAdapterHouse = new LegislatorAdapter(getActivity(), new ArrayList<Legislator>());
        lAdapterSenate = new LegislatorAdapter(getActivity(), new ArrayList<Legislator>());

        legislatorListViewState.setAdapter(lAdapterState);
        legislatorListViewHouse.setAdapter(lAdapterHouse);
        legislatorListViewSenate.setAdapter(lAdapterSenate);



//Set on click listeners to the three list
        legislatorListViewState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.zhang.homework9.LegDetailActivity");
                intent.putExtra("myLegislator", legStateList.get(position));
                startActivity(intent);

            }
        });
        legislatorListViewHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.zhang.homework9.LegDetailActivity");
                intent.putExtra("myLegislator", legHouseList.get(position));
                startActivity(intent);
            }
        });
        legislatorListViewSenate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.zhang.homework9.LegDetailActivity");
                intent.putExtra("myLegislator", legSenateList.get(position));
                startActivity(intent);

            }
        });


//Check network
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            android.app.LoaderManager loaderManager = this.getLoaderManager();
            loaderManager.initLoader(legislatorLoaderId, null, this);

        }
        else{
            mEmptyStateTextViewState.setText("No Internet Connection");
            mEmptyStateTextViewHouse.setText("No Internet Connection");
            mEmptyStateTextViewSenate.setText("No Internet Connection");
        }


        //Set tab host for legislator option
        TabHost tab = (TabHost) getView().findViewById(android.R.id.tabhost);
        tab.setup();
        TabHost.TabSpec spec1 = tab.newTabSpec("legState");
        spec1.setIndicator("By State");
        spec1.setContent(R.id.legStates);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("legHouse");
        spec2.setIndicator("House");
        spec2.setContent(R.id.legHouse);
        tab.addTab(spec2);

        TabHost.TabSpec spec3 = tab.newTabSpec("legSenate");
        spec3.setIndicator("Senate");
        spec3.setContent(R.id.legSenate);
        tab.addTab(spec3);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction("Legislators");
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
    public Loader<List<Legislator>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(CongressApi);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("per_page", "all");
        uriBuilder.appendQueryParameter("apikey", "0a0ed38f64bc40629c4df4cd22a23f27");


        return new LegislatorLoader(getActivity(), uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<Legislator>> loader, List<Legislator> data) {

        mEmptyStateTextViewState.setText("No Legislator found.");
        mEmptyStateTextViewHouse.setText("No Legislator found.");
        mEmptyStateTextViewSenate.setText("No Legislator found.");
        lAdapterState.clear();
        lAdapterHouse.clear();
        lAdapterSenate.clear();

        if(data != null && !data.isEmpty()){//Get all legislators data

            legStateList = new ArrayList<Legislator>(data);//By state array
            Collections.sort(legStateList, new Comparator<Legislator>() {
                @Override
                public int compare(Legislator leg1, Legislator leg2)
                {
                    return  leg1.state.compareTo(leg2.state);
                }
            });

            legHouseList = new ArrayList<Legislator>(); //By house array
            for(Legislator l : data){
                if(l.chamber.equals("house")){
                    legHouseList.add(l);
                }
            }
            Collections.sort(legHouseList, new Comparator<Legislator>() {
                @Override
                public int compare(Legislator o1, Legislator o2) {
                    return o1.lastname.compareTo(o2.lastname);
                }
            });

            legSenateList = new ArrayList<Legislator>(); //By senate array
            for(Legislator l : data){
                if(l.chamber.equals("senate")){
                    legSenateList.add(l);
                }
            }
            Collections.sort(legSenateList, new Comparator<Legislator>() {
                @Override
                public int compare(Legislator o1, Legislator o2) {
                    return o1.lastname.compareTo(o2.lastname);
                }
            });

            lAdapterState.addAll(legStateList);
            lAdapterHouse.addAll(legHouseList);
            lAdapterSenate.addAll(legSenateList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Legislator>> loader) {
        lAdapterState.clear();
        lAdapterHouse.clear();
        lAdapterSenate.clear();
    }
}
