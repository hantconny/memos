package com.glaway.lrds.provider;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

import com.glaway.lrds.dto.ItemActivity;
import com.glaway.lrds.dto.TransferData;

public class GenericProviderTest {
	String authorization = null;
	String url = null;
	TransferData transferData = null;
	ItemActivity itemActivity = null;
	StopWatch timer = null;

	public GenericProviderTest() throws Exception {
		JaxbUtil.getJAXBContext();
	}

	@Before
	public void setUp() throws Exception {

		authorization = "MDAwODczOjAwMDg3Mw==";//000873
		url = "http://ld.nriet.com/Windchill/app/activity/item?action=info";
		timer = new StopWatch();
		transferData = new TransferData();
		itemActivity = new ItemActivity();
		itemActivity.setOid("VR:com.glaway.lrds.activity.GLItemActivity:1665286");
		transferData.setItemActivity(itemActivity);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testServiceRequestStringStringTransferData() throws Exception {
		timer.start("activity/item?action=info");
		TransferData responseData = GenericProvider.serviceRequest(authorization, url, transferData);
		Assert.assertEquals(true, responseData.isSuccess());
		timer.stop();
		System.out.println(timer.prettyPrint());
	}

}
