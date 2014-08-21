package com.robotium.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.adl.closetstylist.R;
import com.adl.closetstylist.ui.LoginActivity;
import com.adl.closetstylist.ui.MainActivity;
import com.robotium.solo.Solo;

public class FlowTestFemale extends ActivityInstrumentationTestCase2<LoginActivity> {

	private static final int WAIT_TIME = 2000;
	private Solo solo;

	public FlowTestFemale() {
		super(LoginActivity.class);

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

	
	/**
	 * test ClearMyCloset → Register → Dashboard → MyCloset → create default for male → OutfitOfTheDay
	 * @throws Exception
	 */
	public void testFlow1() throws Exception {
		//Unlock the lock screen
		solo.unlockScreen();
		
		//clear data
		solo.clickOnText("ClearMyCloset");
		solo.sleep(WAIT_TIME);
		
		//get in register view
		solo.clickOnText("Don't have account - register here");
		boolean getInRegister = solo.searchText("Register");
		assertTrue("Cannot enter Register Activity", getInRegister);
		
		//fill register data
		solo.clickOnMenuItem(getActivity().getString(R.string.register_menu_load_female)); // load female UserProfile
		EditText userName = (EditText) solo.getView(R.id.username);
		assertTrue("Cannot fill male profile", userName.getText() != null || userName.getText().length() > 0);
		
		//click register
		solo.clickOnView(solo.getView(R.id.btn_register));
		solo.sleep(WAIT_TIME);
		solo.assertCurrentActivity("Cannot enter Main Actvity", MainActivity.class);
		
		//get in My Closet
		String myClosetStr = getActivity().getString(R.string.mycloset);
		
		solo.clickOnText(myClosetStr);
		solo.sleep(WAIT_TIME);
		assertTrue("Cannot enter " + myClosetStr + " view", solo.searchText(myClosetStr));

		//create female closet
		solo.clickOnMenuItem(getActivity().getString(
				R.string.my_closet_menu_create_default_closet_female));

		solo.sleep(WAIT_TIME);
		
		//get out and get in back to MyCloset for checking new created items
		solo.clickOnText(myClosetStr);
		solo.sleep(WAIT_TIME);
		String dashboardStr = getActivity().getString(R.string.dashboard);
		solo.clickOnText(dashboardStr);
		solo.sleep(WAIT_TIME);
		solo.clickOnText(myClosetStr);
		solo.sleep(WAIT_TIME);
		ListView listItem = (ListView) solo.getView(R.id.garmentlist);
		assertTrue("Cannot see new created item in garment list", listItem.getAdapter().getCount() > 0);
		
		//get in Outfit of the Day
		solo.clickOnText(myClosetStr);
		String myOFODStr = getActivity().getString(R.string.outfitoftheday);
		solo.clickOnText(myOFODStr);
		solo.sleep(WAIT_TIME);
		assertTrue("Cannot enter " + myOFODStr + " view", solo.searchText(myOFODStr));
		
		//click on WEAR an outfit
		solo.clickOnText(getActivity().getString(R.string.wear));
		solo.sleep(WAIT_TIME);
		
		//check to get in Outfit history
		String myOutfitHistoryStr = getActivity().getString(R.string.outfit_history);
		assertTrue("Cannot enter " + myOutfitHistoryStr + " view", solo.searchText(myOutfitHistoryStr));
		solo.sleep(WAIT_TIME);
		
		//click on outfit item
		listItem = (ListView) solo.getView(R.id.garmentlist);
		assertTrue("Cannot see new created item in outfit history list", listItem.getAdapter().getCount() > 0);
		solo.clickOnView(getViewAtIndex(listItem, 0, getInstrumentation()));
		solo.sleep(WAIT_TIME);
		
		//check to get in Outfit Preview
		String outfitPreviewStr = getActivity().getString(R.string.outfit_preview);
		assertTrue("Cannot enter " + outfitPreviewStr + " view", solo.searchText(outfitPreviewStr));
		
		//get in My Laundry Bag
		solo.clickOnText(outfitPreviewStr);
		String myLaundryBagStr = getActivity().getString(R.string.mylaundrybag);
		solo.clickOnText(myLaundryBagStr);
		assertTrue("Cannot enter " + myLaundryBagStr + " view", solo.searchText(myLaundryBagStr));
		solo.sleep(WAIT_TIME);
	}
	
	public void testLogin() throws Exception {
		EditText usernameEditText = solo.getEditText(0);
		assertTrue("cannot find username field", usernameEditText != null);
		EditText passwordEditText = solo.getEditText(1);
		assertTrue("cannot find password field", passwordEditText != null);
		
		solo.enterText(usernameEditText, "an");
		solo.enterText(passwordEditText, "pwd");
		solo.clickOnButton("Let me in");
		
		boolean getInMainActivity = solo.searchText("Dashboard");
		assertTrue("Cannot enter Main Activity", getInMainActivity); 
	}
	
	
	private View getViewAtIndex(final ListView listElement, final int indexInList, Instrumentation instrumentation) {
	    ListView parent = listElement;
	    if (parent != null) {
	        if (indexInList <= parent.getAdapter().getCount()) {
	            scrollListTo(parent, indexInList, instrumentation);
	            int indexToUse = indexInList - parent.getFirstVisiblePosition();
	            return parent.getChildAt(indexToUse);
	        }
	    }
	    return null;
	}

	private <T extends ListView> void scrollListTo(final T listView,
	        final int index, Instrumentation instrumentation) {
	    instrumentation.runOnMainSync(new Runnable() {
	        @Override
	        public void run() {
	            listView.setSelection(index);
	        }
	    });
	    instrumentation.waitForIdleSync();
	}
}
