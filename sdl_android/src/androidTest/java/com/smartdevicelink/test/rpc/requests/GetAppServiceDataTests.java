package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetAppServiceData;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class GetAppServiceDataTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		GetAppServiceData msg = new GetAppServiceData();
		msg.setServiceType(Test.GENERAL_APP_SERVICE_TYPE);
		msg.setSubscribe(Test.GENERAL_BOOLEAN);
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.GET_APP_SERVICE_DATA.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(GetAppServiceData.KEY_SERVICE_TYPE, Test.GENERAL_APP_SERVICE_TYPE);
			result.put(GetAppServiceData.KEY_SUBSCRIBE, Test.GENERAL_BOOLEAN);
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		AppServiceType serviceType = ( (GetAppServiceData) msg ).getServiceType();
		boolean subscribe = ( (GetAppServiceData) msg ).getSubscribe();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_APP_SERVICE_TYPE, serviceType);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, subscribe);

		// Invalid/Null Tests
		GetAppServiceData msg = new GetAppServiceData();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(msg.getServiceType());
		assertNull(msg.getSubscribe());
	}

	/**
	 * Tests constructor with required params
	 */
	public void testRequiredParamsConstructor () {

		GetAppServiceData msg = new GetAppServiceData(Test.GENERAL_APP_SERVICE_TYPE);
		assertNotNull(Test.NOT_NULL, msg);
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_APP_SERVICE_TYPE, msg.getServiceType());
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetAppServiceData cmd = new GetAppServiceData(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetAppServiceData.KEY_SUBSCRIBE), cmd.getSubscribe());
			assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetAppServiceData.KEY_SERVICE_TYPE).toString(), cmd.getServiceType().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}