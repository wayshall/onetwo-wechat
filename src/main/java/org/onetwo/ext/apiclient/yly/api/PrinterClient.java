package org.onetwo.ext.apiclient.yly.api;

import org.onetwo.ext.apiclient.yly.core.YlyApiClient;
import org.onetwo.ext.apiclient.yly.request.AddPrinterRequest;
import org.onetwo.ext.apiclient.yly.request.BtnPrintRequest;
import org.onetwo.ext.apiclient.yly.request.PrinterRequest;
import org.onetwo.ext.apiclient.yly.response.PrinterStateResponse;
import org.onetwo.ext.apiclient.yly.response.YlyResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author weishao zeng
 * <br/>
 */
@YlyApiClient
public interface PrinterClient {

	@PostMapping(value = "/printer/addprinter", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	YlyResponse addPrinter(AddPrinterRequest request);


	@PostMapping(value = "/printer/btnprint", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	YlyResponse btnprint(BtnPrintRequest request);
	
	@PostMapping(value = "/printer/getprintstatus", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	PrinterStateResponse getPrintStatus(PrinterRequest request);
	

}
