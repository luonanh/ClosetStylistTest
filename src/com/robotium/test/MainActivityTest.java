/*
 * This is an example test project created in Eclipse to test NotePad which is a sample 
 * project located in AndroidSDK/samples/android-11/NotePad
 * 
 * 
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

package com.robotium.test;

import com.robotium.solo.Solo;
import com.example.closetstylishgui.LoginActivity;
import com.example.closetstylishgui.MainActivity;
import com.example.closetstylishgui.R;
import com.example.closetstylishgui.RegisterActivity;
import com.example.closetstylishgui.SplashScreen;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

	private Solo solo;

	public MainActivityTest() {
		super(MainActivity.class);

	}

	@Override
	public void setUp() throws Exception {
		//setUp() is run before a test case is started. 
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		//tearDown() is run after a test case has finished. 
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		solo.finishOpenedActivities();
	}

	public void testAllFragment() throws Exception {
		//Unlock the lock screen
		solo.unlockScreen();
		int[] arrayItem = new int[] {
			R.string.dashboard,
			R.string.mycloset,
			R.string.mylaundrybag,
			R.string.outfitoftheday
		};
		for (int id : arrayItem) {
			solo.clickOnActionBarHomeButton();
			String string = getActivity().getString(id);
			solo.clickOnText(string);
			boolean getInDashboard = solo.searchText(string);
			assertTrue("Cannot enter fragment " + string, getInDashboard); 
		}
	}
	
	enum TestTextView {
		WeatherLocation(R.id.weather_location, "Chicago, IL", true),
		MaxTemp(R.id.weather_max_temp, "100", true),
		MinTemp(R.id.weather_min_temp, "0", true),
		CurrentTemp(R.id.weather_current_temp, "50", true);
		
		private int textViewId;
		private String expectedValue;
		private boolean negate;

		private TestTextView(int textViewId, String expectedValue) {
			this(textViewId, expectedValue, false);
		}
		private TestTextView(int textViewId, String expectedValue, boolean negate) {
			this.textViewId = textViewId;
			this.expectedValue = expectedValue;
			this.negate = negate;
		}
		public int getTextViewId() {
			return textViewId;
		}
		public String getExpectedValue() {
			return expectedValue;
		}
		public boolean isNegate() {
			return negate;
		}
		
		
	}
	
	public void testDashboardFragment() throws Exception {
		//Unlock the lock screen
		solo.unlockScreen();
		solo.clickOnActionBarHomeButton();
		String dashboardStr = getActivity().getString(R.string.dashboard);
		solo.clickOnText(dashboardStr);
		boolean getInDashboard = solo.searchText(dashboardStr);
		assertTrue("Cannot enter fragment " + dashboardStr, getInDashboard);
		
		for(TestTextView testTextView : TestTextView.values()) {
			TextView textView = null;
			try {
				textView = (TextView) solo.getView(testTextView
						.getTextViewId());
			} catch (Exception e) {
			}
			
			assertTrue("Cannot get " + testTextView.name() + " on " + dashboardStr, textView != null);

			boolean compareValueResult = textView.getText().equals(testTextView.getExpectedValue());
			
			assertTrue(
					"Cannot get value of " + testTextView.name(),
					textView.getText() != null
							&& (testTextView.isNegate() ^ compareValueResult));
		}
		
	}
	
}
