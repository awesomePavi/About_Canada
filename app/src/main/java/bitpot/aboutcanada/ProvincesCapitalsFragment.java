package bitpot.aboutcanada;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import bitpot.aboutcanada.util.Utils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProvincesCapitalsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProvincesCapitalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProvincesCapitalsFragment extends android.support.v4.app.Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProvincesCapitalsFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProvincesCapitalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProvincesCapitalsFragment newInstance(String param1, String
            param2)
    {
        ProvincesCapitalsFragment fragment = new ProvincesCapitalsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_provinces_capitals, null);
        dispFileText((LinearLayout) view.findViewById(R.id.provincesCapital_ll));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provinces_capitals,
                container, false);
    }

    public void dispFileText(LinearLayout ll)
    {
        InputStream inStream = getResources().openRawResource(R.raw
                .provinces_capitals);
        BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        String line;
        TextView tv;
        ImageView imageView;

        int[] drawables = {R.drawable.flag_ontario, R.drawable.flag_quebec};

        try
        {
            int lineNum = 0;
//            while(br.readLine() != null)
            while(lineNum < 2 && br.readLine() != null)
            {
                imageView = new ImageView(getActivity());
                imageView.setImageBitmap(Utils
                        .decodeSampledBitmapFromResource(getResources(),
                                drawables[lineNum], 200, 200));
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setAdjustViewBounds(true);
                imageView.setPadding(0, 24, 0, 0);
                ll.addView(imageView);

                lineNum++;
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
//@Override
//public void onAttach(Activity activity)
//{
//    super.onAttach(activity);
//    try
//    {
//        mListener = (OnFragmentInteractionListener) activity;
//    }
//    catch (ClassCastException e)
//    {
//        throw new ClassCastException(activity.toString()
//                + " must implement OnFragmentInteractionListener");
//    }
//}

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating
     * .html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
