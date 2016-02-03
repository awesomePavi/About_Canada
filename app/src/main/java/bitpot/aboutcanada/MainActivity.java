package bitpot.aboutcanada;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        AboutCanadaFragment.OnFragmentInteractionListener,
        AboutCitiesFragment.OnFragmentInteractionListener,
        CultureFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        HolidaysFragment.OnFragmentInteractionListener,
        FinanceFragment.OnFragmentInteractionListener,
        LandmarksFragment.OnFragmentInteractionListener,
        CanadaFragment.OnFragmentInteractionListener,
        TransBlankFragment.OnFragmentInteractionListener
//        ProvincesCapitalsFragment.OnFragmentInteractionListener
{

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link
     * #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /**
     * Map of holiday dates (key) and holiday names (values)
     */
    private final Map<Calendar, String> holidayDatesMap = new HashMap<>();

    public static final Map<String, Integer> MONTHS_MAP = new HashMap<>();

    private static int NUM_O_CANADA = 0;

    /**
     * MediaPlayer object to play O Canada
     */
    private MediaPlayer mediaPlayer;

    static
    {
        MONTHS_MAP.put("JANUARY", 0);
        MONTHS_MAP.put("FEBRUARY", 1);
        MONTHS_MAP.put("MARCH", 2);
        MONTHS_MAP.put("APRIL", 3);
        MONTHS_MAP.put("MAY", 4);
        MONTHS_MAP.put("JUNE", 5);
        MONTHS_MAP.put("JULY", 6);
        MONTHS_MAP.put("AUGUST", 7);
        MONTHS_MAP.put("SEPTEMBER", 8);
        MONTHS_MAP.put("OCTOBER", 9);
        MONTHS_MAP.put("NOVEMBER", 10);
        MONTHS_MAP.put("DECEMBER", 11);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id
                        .navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Populate holidayDatesMap
        // todo don't use hardcoded dates in the future
        Calendar date1 = Calendar.getInstance();
        date1.set(2015, MONTHS_MAP.get("JANUARY"), 1);
        holidayDatesMap.put(date1, getText(R.string.new_years).toString());
        Log.d("YEE: ", date1.toString());

        Calendar date2 = Calendar.getInstance();
        date2.set(2015, MONTHS_MAP.get("APRIL"), 3);
        holidayDatesMap.put(date2, getText(R.string.good_friday).toString());
        Log.d("YEE: ", date2.toString());

        Calendar date3 = Calendar.getInstance();
        date3.set(2015, MONTHS_MAP.get("MAY"), 10);
        holidayDatesMap.put(date3, getText(R.string.mothers_day).toString());

        Calendar date4 = Calendar.getInstance();
        date4.set(2015, MONTHS_MAP.get("MAY"), 18);
        holidayDatesMap.put(date4, getText(R.string.victoria_day).toString());

        Calendar date5 = Calendar.getInstance();
        date5.set(2015, MONTHS_MAP.get("JUNE"), 21);
        holidayDatesMap.put(date5, getText(R.string.fathers_day).toString());

        Calendar date6 = Calendar.getInstance();
        date6.set(2015, MONTHS_MAP.get("JULY"), 1);
        holidayDatesMap.put(date6, getText(R.string.canada_day).toString());

        Calendar date7 = Calendar.getInstance();
        date7.set(2015, MONTHS_MAP.get("SEPTEMBER"), 7);
        holidayDatesMap.put(date7, getText(R.string.labour_day).toString());

        Calendar date8 = Calendar.getInstance();
        date8.set(2015, MONTHS_MAP.get("OCTOBER"), 12);
        holidayDatesMap.put(date8, getText(R.string.thanksgiving_day).toString
                ());

        Calendar date9 = Calendar.getInstance();
        date9.set(2015, MONTHS_MAP.get("OCTOBER"), 31);
        holidayDatesMap.put(date9, getText(R.string.halloween).toString());

        Calendar date10 = Calendar.getInstance();
        date10.set(2015, MONTHS_MAP.get("NOVEMBER"), 11);
        holidayDatesMap.put(date10, getText(R.string.remembrance_day)
                .toString());

        Calendar date11 = Calendar.getInstance();
        date11.set(2015, MONTHS_MAP.get("DECEMBER"), 25);
        Log.d("YEE: ", date11.toString());
        holidayDatesMap.put(date11, getText(R.string.christmas).toString());

        Calendar date12 = Calendar.getInstance();
        date10.set(2015, MONTHS_MAP.get("NOVEMBER"), 29);
        holidayDatesMap.put(date10, getText(R.string.hack_western).toString());

        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentDay = currentDate.get(Calendar.DATE);

        String holiday;
        NotificationManager NM;
        Notification.Builder notifyHoliday;

        for (Calendar lDate : holidayDatesMap.keySet())
        {
            Log.d("YO current: ", "" + currentYear + " " + currentMonth + " "
                    + currentDay);
            Log.d("YO lDate: ", "" + lDate.get(Calendar.YEAR) + " " + lDate
                    .get(Calendar.MONTH) + " " + lDate.get(Calendar
                    .DAY_OF_MONTH));
            Log.d("YO lDate: ", lDate.toString());

            if (currentYear == lDate.get(Calendar.YEAR) && currentMonth ==
                    lDate.get(Calendar.MONTH) && currentDay == lDate.get
                    (Calendar.DAY_OF_MONTH))
            {
                holiday = holidayDatesMap.get(lDate);

                NM = (NotificationManager) getSystemService
                        (NOTIFICATION_SERVICE);
                notifyHoliday = new Notification.Builder(this)
                        .setContentTitle("Today is " + holiday + "!")
                        .setSmallIcon(R.drawable.action_bar_icon)
                        .setSound(Uri.parse("android.resource://" +
                                getPackageName() + "/" + R
                                .raw.sorry));

                NM.notify(1, notifyHoliday.getNotification());
            }
        }

        mediaPlayer = MediaPlayer.create(this.getBaseContext(),
                R.raw.ocanada);

//        TextView home_TxtVw = (TextView) findViewById(R.id.txtVw_home);
//        home_TxtVw.setText(readTxt((LinearLayout) findViewById(R.id
// .home_ll)));
//
//        readTxt((LinearLayout) findViewById(R.id.home_ll));

//        TextView home_TxtVw = new TextView(this);
//        home_TxtVw.setText("Welcome!");
//
//        DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer_layout);
//        dl.addView(home_TxtVw);

        // update the main content by replacing fragments
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new
                HomeFragment()).commit();
    }

    private String readTxt(LinearLayout ll)
    {
        InputStream inputStream = getResources().openRawResource(R.raw.home);
        ByteArrayOutputStream byteAOS = new ByteArrayOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader
                (inputStream));
        String line;
        String entireFile = "";

        int i;

        try
        {
            i = inputStream.read();
            while (i != -1)
            {
                byteAOS.write(i);
                i = inputStream.read();
            }

            while ((line = br.readLine()) != null)
            { // <--------- place readLine() inside loop
                // entireFile += (line + "\n"); // <---------- add each line
                // to entireFile
                TextView tv = new TextView(this);
                if (line.length() > 0)
                {
                    tv.setText(line.substring(3, line.length()));
                }
                Calendar c = Calendar.getInstance();
                int seconds = c.get(Calendar.SECOND);

                ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                                .LayoutParams.MATCH_PARENT);
                if (line.length() <= 0)
                {

                } else
                {

                    if (line.substring(0, 3).equals("H--"))
                    {
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
                        tv.setTextColor(Color.parseColor(getResources()
                                .getString(R.string.font_header)));
                        tv.setPadding((int) (15 * getResources()
                                        .getDisplayMetrics().density), (int)
                                        (5 *
                                                getResources()
                                                        .getDisplayMetrics()
                                                        .density),
                                (int) (15 * getResources().getDisplayMetrics
                                        ().density), (int) (5 * getResources
                                        ().getDisplayMetrics().density));
                    } else if (line.substring(0, 3).equals("P--"))
                    {
                        tv.setText("   • " + line.substring(3, line.length()));
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        tv.setTextColor(Color.parseColor(getResources()
                                .getString(R.string.font_body)));
                        tv.setPadding((int) (10 * getResources()
                                .getDisplayMetrics().density), 0, (int) (10 *
                                getResources().getDisplayMetrics().density), 0);
                    } else
                    {
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        tv.setTextColor(Color.parseColor(getResources()
                                .getString(R.string.font_body)));
                        tv.setPadding((int) (10 * getResources()
                                .getDisplayMetrics().density), 0, (int) (10 *
                                getResources().getDisplayMetrics().density), 0);
                    }
                    tv.setLayoutParams(layout);
                    ll.addView(tv);
                    if (line.substring(0, 3).equals("H--"))
                    {
                        View lineDr = new View(this);
                        ViewGroup.LayoutParams linelay = new ViewGroup
                                .LayoutParams(ViewGroup.LayoutParams
                                .MATCH_PARENT, 1);
                        lineDr.setLayoutParams(linelay);
                        lineDr.setBackgroundColor(Color.parseColor
                                (getResources().getString(R.string
                                        .line_header)));
                        ll.addView(lineDr);
                    }
                }
            }


            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return byteAOS.toString();
    }


//        if ((holiday = holidayDatesMap.get(currentDay)) != null && (holiday =
//                holidayDatesMap.get(currentDay)).isEmpty())
//        {
//            notifyHoliday = new Notification.Builder(this)
//                    .setContentTitle("Today is" + holiday + "!")
//                    .setContentText("YO")
//                    .setSmallIcon(R.drawable.action_bar_icon)
//                    .getNotification();
//            show = notifyHoliday.build();
//            NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            NM.notify(1, show);
//        }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance
                        (position + 1))
                .commit();
    }

    public void onSectionAttached(int number)
    {
        switch (number)
        {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_aboutCanada);
                break;
            case 3:
                mTitle = getString(R.string.title_city);
                break;
            case 4:
                mTitle = getString(R.string.title_socialCulture);
                break;
            case 5:
                mTitle = getString(R.string.title_holidays);
                break;
            case 6:
                mTitle = getString(R.string.title_currency);
                break;
            case 7:
                mTitle = getString(R.string.title_landmarks);
                break;
            case 8:
                mTitle = getString(R.string.title_translations);
                break;
            case 9:
                mTitle = getString(R.string.title_anthem);
                break;
        }
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Log.d("YOY before increment", "" + NUM_O_CANADA);
        NUM_O_CANADA++;
        Log.d("YOY after increment", "" + NUM_O_CANADA);

        if (!(mediaPlayer.getCurrentPosition() <= mediaPlayer.getDuration()))
        {
            Log.d("YOY ", "Inside MediaPlayer object reset");
            mediaPlayer = MediaPlayer.create(this.getBaseContext(),
                    R.raw.ocanada);

        }

        // If leaf icon is clicked on odd number of times, O Canada plays
        // Even number - O Canada is paused
        if ((NUM_O_CANADA % 2 == 1) && item.getItemId() == R.id.leaf_icon)
        {
            Log.d("YOY ", "Inside if");
            mediaPlayer.start();
        } else if (NUM_O_CANADA % 2 == 0)
        {
            Log.d("YOY ", "Inside else if");
            mediaPlayer.pause();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_main,
                    container, false);

//            dispFileText((LinearLayout) rootView.findViewById(R.id
// .fragCityLinLay));
            return rootView;
        }

        @Override
        public void onAttach(Activity activity)
        {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        public void dispFileText(LinearLayout ll)
        {
            InputStream is = getResources().openRawResource(R.raw.aboutcanada);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            String entireFile = "";

            try
            {
                while ((line = br.readLine()) != null)
                {
                    entireFile += (line + "\n");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

//
//    public void dispFileText(LinearLayout ll){
//        InputStream is = getResources().openRawResource(R.raw.home);
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        String line;
//        String entireFile = "";
//
//        try {
//            while((line = br.readLine()) != null) { // <--------- place
// readLine() inside loop
//                // entireFile += (line + "\n"); // <---------- add each
// line to entireFile
//                TextView tv = new TextView(getApplicationContext());
//                if (line.length() > 0) {
//                    tv.setText(line.substring(3, line.length()));
//                }
//                Calendar c = Calendar.getInstance();
//                int seconds = c.get(Calendar.SECOND);
//
//                ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams
// (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                if (line.length() <= 0) {
//
//                }
//                else {
//
//                    if (line.substring(0, 3).equals("H--")) {
//                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
//                        tv.setTextColor(Color.parseColor(getResources()
//                                .getString(R.string.font_header)));
//                        tv.setPadding((int) (15 * getResources()
// .getDisplayMetrics().density), (int) (5 * getResources().getDisplayMetrics
// ().density), (int) (15 * getResources().getDisplayMetrics().density),
// (int) (5 * getResources().getDisplayMetrics().density));
//                    } else if (line.substring(0, 3).equals("P--")) {
//                        tv.setText("   • " + line.substring(3, line.length
// ()));
//                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                        tv.setTextColor(Color.parseColor(getResources()
// .getString(R.string.font_body)));
//                        tv.setPadding((int) (10 * getResources()
// .getDisplayMetrics().density), 0, (int) (10 * getResources()
// .getDisplayMetrics().density), 0);
//                    } else {
//                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                        tv.setTextColor(Color.parseColor(getResources()
// .getString(R.string.font_body)));
//                        tv.setPadding((int) (10 * getResources()
// .getDisplayMetrics().density), 0, (int) (10 * getResources()
// .getDisplayMetrics().density), 0);
//                    }
//                    tv.setLayoutParams(layout);
//                    ll.addView(tv);
//                    if (line.substring(0, 3).equals("H--")) {
//                        View lineDr = new View(getApplicationContext());
//                        ViewGroup.LayoutParams linelay = new ViewGroup
// .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
//                        lineDr.setLayoutParams(linelay);
//                        lineDr.setBackgroundColor(Color.parseColor
// (getResources().getString(R.string.line_header)));
//                        ll.addView(lineDr);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}
