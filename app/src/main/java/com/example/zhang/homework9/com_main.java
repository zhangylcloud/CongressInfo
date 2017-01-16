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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com_main.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com_main#newInstance} factory method to
 * create an instance of this fragment.
 */
@TargetApi(11)
public class com_main extends Fragment
    implements LoaderCallbacks<List<Committee>>{


    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String CongressApi = "https://congress.api.sunlightfoundation.com/committees";
    static final int committeeLoaderId = 3;

    private CommitteeAdapter cAdapterJoint;
    private CommitteeAdapter cAdapterHouse;
    private CommitteeAdapter cAdapterSenate;

    private TextView mEmptyStateTextViewCJoint;
    private TextView mEmptyStateTextViewCHouse;
    private TextView mEmptyStateTextViewCSenate;

    private List<Committee> comJointList;
    private List<Committee> comHouseList;
    private List<Committee> comSenateList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public com_main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment com_main.
     */
    // TODO: Rename and change types and number of parameters
    public static com_main newInstance(String param1, String param2) {
        com_main fragment = new com_main();
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
        return inflater.inflate(R.layout.fragment_com_main, container, false);
    }


    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //Set legislator lists to empty before access data
        ListView committeeListViewJoint = (ListView) getView().findViewById(R.id.list_com_joint);
        mEmptyStateTextViewCJoint = (TextView) getView().findViewById(R.id.empty_view_com_joint);
        committeeListViewJoint.setEmptyView(mEmptyStateTextViewCJoint);

        ListView committeeListViewHouse = (ListView) getView().findViewById(R.id.list_com_house);
        mEmptyStateTextViewCHouse = (TextView) getView().findViewById(R.id.empty_view_com_house);
        committeeListViewHouse.setEmptyView(mEmptyStateTextViewCHouse);

        ListView committeeListViewSenate = (ListView) getView().findViewById(R.id.list_com_senate);
        mEmptyStateTextViewCSenate = (TextView) getView().findViewById(R.id.empty_view_com_senate);
        committeeListViewSenate.setEmptyView(mEmptyStateTextViewCSenate);



        cAdapterJoint = new CommitteeAdapter(getActivity(), new ArrayList<Committee>());
        cAdapterHouse = new CommitteeAdapter(getActivity(), new ArrayList<Committee>());
        cAdapterSenate = new CommitteeAdapter(getActivity(), new ArrayList<Committee>());

        committeeListViewJoint.setAdapter(cAdapterJoint);
        committeeListViewHouse.setAdapter(cAdapterHouse);
        committeeListViewSenate.setAdapter(cAdapterSenate);





//Set on click listeners to the three list
        committeeListViewJoint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.zhang.homework9.ComDetailActivity");
                intent.putExtra("myCommittee", comJointList.get(position));
                startActivity(intent);
            }
        });
        committeeListViewHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.zhang.homework9.ComDetailActivity");
                intent.putExtra("myCommittee", comHouseList.get(position));
                startActivity(intent);
            }
        });
        committeeListViewSenate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.zhang.homework9.ComDetailActivity");
                intent.putExtra("myCommittee", comSenateList.get(position));
                startActivity(intent);

            }
        });


//Check network
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            android.app.LoaderManager loaderManager = this.getLoaderManager();
            loaderManager.initLoader(committeeLoaderId, null, this);

        }
        else{
            mEmptyStateTextViewCJoint.setText("No Internet Connection");
            mEmptyStateTextViewCHouse.setText("No Internet Connection");
            mEmptyStateTextViewCSenate.setText("No Internet Connection");
        }


        //Set tab host for legislator option
        TabHost tab = (TabHost) getView().findViewById(android.R.id.tabhost);
        tab.setup();
        TabHost.TabSpec spec1 = tab.newTabSpec("comHouse");
        spec1.setIndicator("House");
        spec1.setContent(R.id.comHouse);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("comSenate");
        spec2.setIndicator("Senate");
        spec2.setContent(R.id.comSenate);
        tab.addTab(spec2);

        TabHost.TabSpec spec3 = tab.newTabSpec("comJoint");
        spec3.setIndicator("Joint");
        spec3.setContent(R.id.comJoint);
        tab.addTab(spec3);


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public Loader<List<Committee>> onCreateLoader(int id, Bundle args){
        Uri baseUri = Uri.parse(CongressApi);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("per_page", "all");
        uriBuilder.appendQueryParameter("apikey", "0a0ed38f64bc40629c4df4cd22a23f27");
        return new CommitteeLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Committee>> loader, List<Committee> data){
        mEmptyStateTextViewCHouse.setText("No Committee Found");
        mEmptyStateTextViewCSenate.setText("No Committee Found");
        mEmptyStateTextViewCJoint.setText("No Committee Found");

        cAdapterJoint.clear();
        cAdapterHouse.clear();
        cAdapterSenate.clear();

        if(data != null && !data.isEmpty()){
            comHouseList = new ArrayList<Committee>();
            comSenateList = new ArrayList<Committee>();
            comJointList = new ArrayList<Committee>();

            Collections.sort(data, new Comparator<Committee>() {
                @Override
                public int compare(Committee o1, Committee o2) {
                    return o1.committeeName.compareTo(o2.committeeName);
                }
            });

            for(Committee c : data){
                if(c.chamber.equals("house")){
                    comHouseList.add(c);
                }
                else if(c.chamber.equals("senate")){
                    comSenateList.add(c);
                }
                else if(c.chamber.equals("joint")){
                    comJointList.add(c);
                }
            }
            cAdapterHouse.addAll(comHouseList);
            cAdapterSenate.addAll(comSenateList);
            cAdapterJoint.addAll(comJointList);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Committee>> loader){
        cAdapterHouse.clear();
        cAdapterJoint.clear();
        cAdapterSenate.clear();
    }
}
