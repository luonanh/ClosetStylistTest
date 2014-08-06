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
import com.example.closetstylishgui.RegisterActivity;
import com.example.closetstylishgui.SplashScreen;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;



public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{

	private Solo solo;

	public LoginActivityTest() {
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

	public void testGetInRegisterActivity() throws Exception {
		//Unlock the lock screen
		solo.unlockScreen();
		solo.clickOnText("Don't have account - register here");
		boolean getInRegister = solo.searchText("Register");
		assertTrue("Cannot enter Register Activity", getInRegister); 

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
