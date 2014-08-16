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
import com.adl.closetstylist.ui.LoginActivity;
import com.adl.closetstylist.ui.MainActivity;
import com.adl.closetstylist.R;
import com.adl.closetstylist.ui.RegisterActivity;
import com.adl.closetstylist.ui.SplashScreen;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;


/**
 * test ClearMyCloset → Register → Dashboard → MyCloset → create default for male → OutfitOfTheDay
 * @author truongnguyen
 *
 */
public class FlowTest extends ActivityInstrumentationTestCase2<LoginActivity>{

	private Solo solo;

	public FlowTest() {
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
		solo.sleep(1000);
		
		//get in register view
		solo.clickOnText("Don't have account - register here");
		boolean getInRegister = solo.searchText("Register");
		assertTrue("Cannot enter Register Activity", getInRegister);
		
		//fill register data
		solo.clickOnMenuItem(getActivity().getString(R.string.register_menu_load_male));
		EditText userName = (EditText) solo.getView(R.id.username);
		assertTrue("Cannot fill male profile", userName.getText() != null || userName.getText().length() > 0);
		
		//click register
		solo.clickOnView(solo.getView(R.id.btn_register));
		solo.sleep(1000);
		solo.assertCurrentActivity("Cannot enter Main Actvity", MainActivity.class);
		
		//get in My Closet
		String myClosetStr = getActivity().getString(R.string.mycloset);
		
		solo.clickOnText(myClosetStr);
		solo.sleep(1000);
		assertTrue("Cannot enter " + myClosetStr + " view", solo.searchText(myClosetStr));

		//create male closet
		solo.clickOnMenuItem(getActivity().getString(
				R.string.my_closet_menu_create_default_closet_male));
		solo.sleep(2000);
		
		//get out and get in back to MyCloset for checking new created items
		solo.clickOnText(myClosetStr);
		solo.sleep(1000);
		String dashboardStr = getActivity().getString(R.string.dashboard);
		solo.clickOnText(dashboardStr);
		solo.sleep(1000);
		solo.clickOnText(myClosetStr);
		solo.sleep(2000);
		ListView listItem = (ListView) solo.getView(R.id.garmentlist);
		assertTrue("Cannot see new created item in garment list", listItem.getAdapter().getCount() > 0);
		
		//get in Outfit of the Day
		solo.clickOnText(myClosetStr);
		String myOFODStr = getActivity().getString(R.string.outfitoftheday);
		solo.clickOnText(myOFODStr);
		solo.sleep(1000);
		assertTrue("Cannot enter " + myOFODStr + " view", solo.searchText(myOFODStr));
		
	}
	
	public void testLogin() throws Exception {
		EditText usernameEditText = solo.getEditText(0);
		assertTrue("cannot find username field", usernameEditText != null);
		EditText passwordEditText = solo.getEditText(1);
		assertTrue("cannot find password field", passwordEditText != null);
		
		solo.enterText(usernameEditText, "anh");
		solo.enterText(passwordEditText, "pwd");
		solo.clickOnButton("Let me in");
		
		boolean getInMainActivity = solo.searchText("Dashboard");
		assertTrue("Cannot enter Main Activity", getInMainActivity); 
	}
	
}
