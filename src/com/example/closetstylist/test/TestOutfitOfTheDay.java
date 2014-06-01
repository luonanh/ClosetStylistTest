package com.example.closetstylist.test;

import com.example.closetstylist.MainActivity;
import com.robotium.solo.Solo;

//import junit.framework.TestCase;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

public class TestOutfitOfTheDay extends ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;
	
	public TestOutfitOfTheDay() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {
		assertTrue("com.example.closetstylist.MainActivity is not found!", 
				solo.waitForActivity(com.example.closetstylist.MainActivity.class));
		
		assertTrue("Outfit button on in MainActivity is not found!", 
				null != solo.getView(com.example.closetstylist.R.id.main_btn_outfit));
		
		//solo.clickOnButton(com.example.closetstylist.R.id.main_btn_outfit);
		solo.clickOnView(solo.getView(com.example.closetstylist.R.id.main_btn_outfit));
		
		assertTrue("com.example.closetstylist.MyClosetActivity is not found!", 
				solo.waitForActivity(com.example.closetstylist.OutfitActivity.class));
		
		assertTrue("top item is not found!", 
				null != solo.getView(com.example.closetstylist.R.id.outfit_label_top));		
		assertTrue("no item for top", 
				null != ((ImageView) solo.getView(com.example.closetstylist.R.id.outfit_label_top)).getDrawable());
		
		assertTrue("bottom item is not found!", 
				null != solo.getView(com.example.closetstylist.R.id.outfit_label_bottom));
		assertTrue("no item for bottom", 
				null != ((ImageView) solo.getView(com.example.closetstylist.R.id.outfit_label_bottom)).getDrawable());

		solo.goBack();
	}
	
	
}
