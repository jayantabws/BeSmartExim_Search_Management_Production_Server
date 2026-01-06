package com.besmartexim.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.besmartexim.dto.request.UserSearchRequest;
import com.besmartexim.dto.response.CountryWiseCount;
import com.besmartexim.dto.response.CountryWiseCountResponse;
import com.besmartexim.dto.response.ExpFor;
import com.besmartexim.dto.response.ExpInd;
import com.besmartexim.dto.response.ImpFor;
import com.besmartexim.dto.response.ImpInd;
import com.besmartexim.dto.response.ListCities;
import com.besmartexim.dto.response.ListCitiesResponse;
import com.besmartexim.dto.response.ListCountries;
import com.besmartexim.dto.response.ListCountriesResponse;
import com.besmartexim.dto.response.ListDistinctColumnValues;
import com.besmartexim.dto.response.ListDistinctColumnValuesResponse;
import com.besmartexim.dto.response.ListExporters;
import com.besmartexim.dto.response.ListExportersResponse;
import com.besmartexim.dto.response.ListHscodes;
import com.besmartexim.dto.response.ListHscodesResponse;
import com.besmartexim.dto.response.ListImporters;
import com.besmartexim.dto.response.ListImportersResponse;
import com.besmartexim.dto.response.ListMonthwise;
import com.besmartexim.dto.response.ListMonthwiseResponse;
import com.besmartexim.dto.response.ListPorts;
import com.besmartexim.dto.response.ListPortsResponse;
import com.besmartexim.dto.response.ListShipmentMode;
import com.besmartexim.dto.response.ListShipmentModeResponse;
import com.besmartexim.dto.response.ListStdUnit;
import com.besmartexim.dto.response.ListStdUnitResponse;
import com.besmartexim.dto.response.SuggestionList;
import com.besmartexim.dto.response.SuggestionListResponse;
import com.besmartexim.dto.response.UserSearchResponse;

@Component
public class UserSearchServiceHelper {

	public UserSearchResponse creteResponse(ResultSet rs, UserSearchRequest userSearchRequest) throws Exception {
		UserSearchResponse userSearchResponse = new UserSearchResponse();

		if (!userSearchRequest.getCountryCode().equals("IND") && !userSearchRequest.getCountryCode().equals("SEZ")) {

			if (userSearchRequest.getTradeType().getValue() == "EXPORT") {
				List<ExpFor> list = new ArrayList<ExpFor>();

				while (rs.next()) {

					ExpFor exp = new ExpFor();

					exp.setId(rs.getLong("id"));
					exp.setAttributes(rs.getString("attributes"));
					exp.setBl_number(rs.getString("bl_number"));
					exp.setBrand(rs.getString("brand"));
					exp.setCategory(rs.getString("category"));
					exp.setCif_value_botswanan_pula((rs.getString("cif_value_botswanan_pula") != null)
							? rs.getBigDecimal("cif_value_botswanan_pula").toPlainString()
							: null);
					exp.setCif_value_naira((rs.getString("cif_value_naira") != null)
							? rs.getBigDecimal("cif_value_naira").toPlainString()
							: null);
					exp.setCif_value_ugx(rs.getString("cif_value_ugx"));
					exp.setClearance_fee(rs.getString("clearance_fee"));
					exp.setClearing_agent(rs.getString("clearing_agent"));
					exp.setCommercial_quantity((rs.getString("commercial_quantity") != null)
							? rs.getBigDecimal("commercial_quantity").toPlainString()
							: null);
					exp.setCommercial_quantity_unit(rs.getString("commercial_quantity_unit"));
					exp.setCondition(rs.getString("condition"));
					exp.setConocimiento_emb(rs.getString("conocimiento_emb"));
					exp.setContainer(rs.getString("container"));
					exp.setCurrency(rs.getString("currency"));
					exp.setCustoms_clearance_office(rs.getString("customs_clearance_office"));
					exp.setDate(rs.getString("date"));
					exp.setDeclarant(rs.getString("declarant"));
					exp.setDeclarant_code(rs.getString("declarant_code"));
					exp.setDeclaration_type(rs.getString("declaration_type"));
					exp.setDestination_country(rs.getString("destination_country"));
					exp.setDestination_port(rs.getString("destination_port"));
					exp.setDestination_port_code(rs.getString("destination_port_code"));
					exp.setEconomic_zone(rs.getString("economic_zone"));
					exp.setExchange_rate(
							(rs.getString("exchange_rate") != null) ? rs.getBigDecimal("exchange_rate").toPlainString()
									: null);
					exp.setExport_country(rs.getString("export_country"));
					exp.setExport_purpose(rs.getString("export_purpose"));
					exp.setExport_purpose_group(rs.getString("export_purpose_group"));
					exp.setExporter_address(rs.getString("exporter_address"));
					exp.setExporter_economic_key(rs.getString("exporter_economic_key"));
					exp.setExporter_name(rs.getString("exporter_name"));
					exp.setExporter_tel(rs.getString("exporter_tel"));
					exp.setFob_value_botswanan_pula((rs.getString("fob_value_botswanan_pula") != null)
							? rs.getBigDecimal("fob_value_botswanan_pula").toPlainString()
							: null);
					exp.setFob_value_naira((rs.getString("fob_value_naira") != null)
							? rs.getBigDecimal("fob_value_naira").toPlainString()
							: null);
					exp.setFreight_value(
							(rs.getString("freight_value") != null) ? rs.getBigDecimal("freight_value").toPlainString()
									: null);
					exp.setFreight_value_botswanan_pula((rs.getString("freight_value_botswanan_pula") != null)
							? rs.getBigDecimal("freight_value_botswanan_pula").toPlainString()
							: null);
					exp.setFreight_value_usd((rs.getString("freight_value_usd") != null)
							? rs.getBigDecimal("freight_value_usd").toPlainString()
							: null);
					exp.setGross_weight(
							(rs.getString("gross_weight") != null) ? rs.getBigDecimal("gross_weight").toPlainString()
									: null);
					exp.setGross_weight_unit(rs.getString("gross_weight_unit"));
					exp.setHs_code(rs.getString("hs_code"));
					exp.setHs_code2(rs.getString("hs_code2"));
					exp.setHs_code4(rs.getString("hs_code4"));
					exp.setHs_code6(rs.getString("hs_code6"));
					exp.setHs_description(rs.getString("hs_description"));
					exp.setIec(rs.getString("iec"));
					exp.setIncoterm(rs.getString("incoterm"));
					exp.setInsurance_value((rs.getString("insurance_value") != null)
							? rs.getBigDecimal("insurance_value").toPlainString()
							: null);
					exp.setInsurance_value_botswanan_pula((rs.getString("insurance_value_botswanan_pula") != null)
							? rs.getBigDecimal("insurance_value_botswanan_pula").toPlainString()
							: null);
					exp.setInsurance_value_usd((rs.getString("insurance_value_usd") != null)
							? rs.getBigDecimal("insurance_value_usd").toPlainString()
							: null);
					exp.setItem_no(rs.getString("item_no"));
					exp.setItem_value_cif((rs.getString("item_value_cif") != null)
							? rs.getBigDecimal("item_value_cif").toPlainString()
							: null);
					exp.setItem_value_fob((rs.getString("item_value_fob") != null)
							? rs.getBigDecimal("item_value_fob").toPlainString()
							: null);
					exp.setLicence_code(rs.getString("licence_code"));
					exp.setMode_of_transport(rs.getString("mode_of_transport"));
					exp.setMonth(rs.getString("month"));
					exp.setNet_weight(
							(rs.getString("net_weight") != null) ? rs.getBigDecimal("net_weight").toPlainString()
									: null);
					exp.setNet_weight_unit(rs.getString("net_weight_unit"));
					exp.setNo_of_package(
							(rs.getString("no_of_package") != null) ? rs.getBigDecimal("no_of_package").toPlainString()
									: null);
					exp.setNotify_party_code(rs.getString("notify_party_code"));
					exp.setNotify_party_name(rs.getString("notify_party_name"));
					exp.setOrigin_country(rs.getString("origin_country"));
					exp.setOrigin_port(rs.getString("origin_port"));
					exp.setOrigin_port_code(rs.getString("origin_port_code"));
					exp.setPackage_description(rs.getString("package_description"));
					exp.setPackage_type(rs.getString("package_type"));
					exp.setPayment_mode(rs.getString("payment_mode"));
					exp.setPlace_of_discharge(rs.getString("place_of_discharge"));
					exp.setProduct_description(rs.getString("product_description"));
					exp.setPurpose_consumption(rs.getString("purpose_consumption"));
					exp.setQuantity((rs.getString("quantity") != null)
							? new BigDecimal(rs.getString("quantity")).setScale(3, RoundingMode.HALF_UP)
							: null);
					exp.setQuantity_of_container((rs.getString("quantity_of_container") != null)
							? rs.getBigDecimal("quantity_of_container").toPlainString()
							: null);
					exp.setRecepient_address(rs.getString("recepient_address"));
					exp.setRecepient_name(rs.getString("recepient_name"));
					exp.setSb_no(rs.getString("sb_no"));
					exp.setShip_name(rs.getString("ship_name"));
					exp.setStd_quantity((rs.getString("std_quantity") != null)
							? new BigDecimal(rs.getString("std_quantity")).setScale(3, RoundingMode.HALF_UP)
							: null);
					exp.setStd_unit(rs.getString("std_unit"));
					exp.setSubitem_no(rs.getString("subitem_no"));
					exp.setTax_amount(
							(rs.getString("tax_amount") != null) ? rs.getBigDecimal("tax_amount").toPlainString()
									: null);
					exp.setTotal_cif_value((rs.getString("total_cif_value") != null)
							? rs.getBigDecimal("total_cif_value").toPlainString()
							: null);
					// exp.setTotal_fob_value((rs.getString("total_fob_value") != null) ?
					// rs.getDouble("total_fob_value").toPlainString() : null);
					exp.setTotal_fob_value((rs.getString("total_fob_value") != null)
							? new BigDecimal(rs.getString("total_fob_value")).setScale(3, RoundingMode.HALF_UP)
							: null);
					exp.setTotal_fob_value_pkr((rs.getString("total_fob_value_pkr") != null)
							? rs.getBigDecimal("total_fob_value_pkr").toPlainString()
							: null);
					exp.setTotal_fob_value_usd((rs.getString("total_fob_value_usd") != null)
							? rs.getBigDecimal("total_fob_value_usd").toPlainString()
							: null);
					exp.setTotal_invoice_value((rs.getString("total_invoice_value") != null)
							? rs.getBigDecimal("total_invoice_value").toPlainString()
							: null);
					exp.setTotal_invoice_value_cif_usd((rs.getString("total_invoice_value_cif_usd") != null)
							? rs.getBigDecimal("total_invoice_value_cif_usd").toPlainString()
							: null);
					exp.setTotal_invoice_value_usd((rs.getString("total_invoice_value_usd") != null)
							? rs.getBigDecimal("total_invoice_value_usd").toPlainString()
							: null);

					exp.setTotal_value_etb((rs.getString("total_value_etb") != null)
							? rs.getBigDecimal("total_value_etb").toPlainString()
							: null);

					exp.setTransport_company(rs.getString("transport_company"));
					exp.setTransport_doc_date(rs.getString("transport_doc_date"));
					exp.setTransport_doc_no(rs.getString("transport_doc_no"));
					exp.setType_of_cargo(rs.getString("type_of_cargo"));
					exp.setUnit(rs.getString("unit"));
					exp.setUnit_price((rs.getString("unit_price") != null)
							? new BigDecimal(rs.getString("unit_price")).setScale(3, RoundingMode.HALF_UP)
							: null);
					exp.setVariety(rs.getString("variety"));
					exp.setVoyage_number(rs.getString("voyage_number"));
					exp.setYear(rs.getString("year"));
					exp.setPlace_of_receipt(rs.getString("place_of_receipt"));
					exp.setFob_value_currency(rs.getString("fob_value_currency"));
					exp.setUnit_price_usd((rs.getString("unit_price_usd") != null)
							? new BigDecimal(rs.getString("unit_price_usd")).setScale(3, RoundingMode.HALF_UP)
							: null);
					exp.setTotal_value_bdt((rs.getString("total_value_bdt") != null)
							? rs.getBigDecimal("total_value_bdt").toPlainString()
							: null);
					exp.setTotal_value_usd((rs.getString("total_value_usd") != null)
							? new BigDecimal(rs.getString("total_value_usd")).setScale(3, RoundingMode.HALF_UP)
							: null);

					exp.setCif_value_usd(
							(rs.getString("cif_value_usd") != null) ? rs.getBigDecimal("cif_value_usd").toPlainString()
									: null);
					exp.setUnit_value_cif_usd((rs.getString("unit_value_cif_usd") != null)
							? rs.getBigDecimal("unit_value_cif_usd").toPlainString()
							: null);
					exp.setCif_currency(rs.getString("cif_currency"));

					exp.setCtry_code(rs.getString("ctry_code"));
					exp.setCtry_name(rs.getString("ctry_name"));

					// exp.setStd_unit_rate_usd(Float.toString(rs.getFloat("std_unit_rate_usd")));
					exp.setStd_unit_rate_usd((rs.getString("std_unit_rate_usd") != null)
							? new BigDecimal(rs.getString("std_unit_rate_usd")).setScale(3, RoundingMode.HALF_UP)
							: null);
					exp.setDraw_back((rs.getString("draw_back") != null)
							? new BigDecimal(rs.getString("draw_back")).setScale(3, RoundingMode.HALF_UP)
							: null);

					list.add(exp);
				}
				userSearchResponse.setExpForeignList(list);
			} else {
				List<ImpFor> list = new ArrayList<ImpFor>();

				while (rs.next()) {

					ImpFor imp = new ImpFor();

					imp.setId(rs.getLong("id"));
					imp.setAssed_unit_value((rs.getString("assed_unit_value") != null)
							? rs.getBigDecimal("assed_unit_value").toPlainString()
							: null);

					imp.setAssessed_import_value_pkr((rs.getBigDecimal("assessed_import_value_pkr") != null)
							? rs.getBigDecimal("assessed_import_value_pkr").toPlainString()
							: null);
					imp.setAssessed_value((rs.getString("assessed_value") != null)
							? rs.getBigDecimal("assessed_value").toPlainString()
							: null);
					imp.setAttributes(rs.getString("attributes"));
					imp.setBe_date(rs.getString("be_date"));
					imp.setBl_date(rs.getString("bl_date"));
					imp.setBl_number(rs.getString("bl_number"));
					imp.setBrand(rs.getString("brand"));
					imp.setCif_unit_price((rs.getString("cif_unit_price") != null)
							? rs.getBigDecimal("cif_unit_price").toPlainString()
							: null);
					imp.setCif_value_botswanan_pula((rs.getString("cif_value_botswanan_pula") != null)
							? rs.getBigDecimal("cif_value_botswanan_pula").toPlainString()
							: null);
					imp.setCommercial_deposit(rs.getString("commercial_deposit"));
					imp.setCommercial_quantity((rs.getString("commercial_quantity") != null)
							? rs.getBigDecimal("commercial_quantity").toPlainString()
							: null);
					imp.setCommercial_quantity_unit(rs.getString("commercial_quantity_unit"));
					imp.setCondition(rs.getString("condition"));
					imp.setConocimiento_emb(rs.getString("conocimiento_emb"));
					imp.setContainer(rs.getString("container"));
					imp.setCurrency(rs.getString("currency"));
					imp.setCustom_clearing_agent(rs.getString("custom_clearing_agent"));
					imp.setCustom_clearing_agent_address(rs.getString("custom_clearing_agent_address"));
					imp.setCustom_clearing_agent_code(rs.getString("custom_clearing_agent_code"));
					imp.setCustom_clearing_office(rs.getString("custom_clearing_office"));
					imp.setDeclarant(rs.getString("declarant"));
					imp.setDeclarant_code(rs.getString("declarant_code"));
					imp.setDeclaration_type(rs.getString("declaration_type"));
					imp.setDelivery_term_place(rs.getString("delivery_term_place"));
					imp.setDestination_country(rs.getString("destination_country"));
					imp.setDestination_port(rs.getString("destination_port"));
					imp.setDestination_port_code(rs.getString("destination_port_code"));
					imp.setDuty((rs.getString("duty") != null)
							? new BigDecimal(rs.getString("duty")).setScale(3, RoundingMode.HALF_UP)
							: null);
					imp.setDuty_mod(rs.getString("duty_mod"));
					imp.setEconomic_zone(rs.getString("economic_zone"));
					imp.setExchange_rate(
							(rs.getString("exchange_rate") != null) ? rs.getBigDecimal("exchange_rate").toPlainString()
									: null);
					imp.setExport_country(rs.getString("export_country"));
					imp.setExporter_address(rs.getString("exporter_address"));
					imp.setExporter_name(rs.getString("exporter_name"));
					imp.setExporter_phone_email(rs.getString("exporter_phone_email"));
					imp.setFob_value_botswanan_pula((rs.getString("fob_value_botswanan_pula") != null)
							? rs.getBigDecimal("fob_value_botswanan_pula").toPlainString()
							: null);
					imp.setFreight_value_botswanan_pula((rs.getString("freight_value_botswanan_pula") != null)
							? rs.getBigDecimal("freight_value_botswanan_pula").toPlainString()
							: null);
					imp.setFreight_value_usd((rs.getString("freight_value_usd") != null)
							? rs.getBigDecimal("freight_value_usd").toPlainString()
							: null);
					imp.setGross_weight(
							(rs.getString("gross_weight") != null) ? rs.getBigDecimal("gross_weight").toPlainString()
									: null);
					imp.setGross_weight_unit(rs.getString("gross_weight_unit"));
					imp.setHs_code(rs.getString("hs_code"));
					imp.setHs_code2(rs.getString("hs_code2"));
					imp.setHs_code4(rs.getString("hs_code4"));
					imp.setHs_code6(rs.getString("hs_code6"));
					imp.setHs_description(rs.getString("hs_description"));
					imp.setIec(rs.getString("iec"));
					imp.setImport_purpose(rs.getString("import_purpose"));
					imp.setImport_purpose_group(rs.getString("import_purpose_group"));
					imp.setImporter_address(rs.getString("importer_address"));
					imp.setImporter_economic_key(rs.getString("importer_economic_key"));
					imp.setImporter_email(rs.getString("importer_email"));
					imp.setImporter_name(rs.getString("importer_name"));
					imp.setImporter_phone(rs.getString("importer_phone"));
					imp.setIncome_tax_amount((rs.getString("income_tax_amount") != null)
							? rs.getBigDecimal("income_tax_amount").toPlainString()
							: null);
					imp.setIncome_tax_mod(rs.getString("income_tax_mod"));
					imp.setIncoterm(rs.getString("incoterms"));
					imp.setInsurance_value_botswanan_pula((rs.getString("insurance_value_botswanan_pula") != null)
							? rs.getBigDecimal("insurance_value_botswanan_pula").toPlainString()
							: null);
					imp.setInsurance_value_usd((rs.getString("insurance_value_usd") != null)
							? rs.getBigDecimal("insurance_value_usd").toPlainString()
							: null);
					imp.setItem_no(rs.getString("item_no"));
					imp.setItem_value_cif((rs.getString("item_value_cif") != null)
							? rs.getBigDecimal("item_value_cif").toPlainString()
							: null);
					imp.setItem_value_fob((rs.getString("item_value_fob") != null)
							? rs.getBigDecimal("item_value_fob").toPlainString()
							: null);
					imp.setLuxury_tax_mod(rs.getString("luxury_tax_mod"));
					imp.setManufacture_year(rs.getString("manufacture_year"));
					imp.setMenifest_date(rs.getString("menifest_date"));
					imp.setMenifest_no(rs.getString("menifest_no"));
					imp.setMode_of_payment(rs.getString("mode_of_payment"));
					imp.setMode_of_transport(rs.getString("mode_of_transport"));
					imp.setMonth(rs.getString("month"));
					imp.setNet_weight(
							(rs.getString("net_weight") != null) ? rs.getBigDecimal("net_weight").toPlainString()
									: null);
					imp.setNet_weight_unit(rs.getString("net_weight_unit"));
					imp.setNo_of_package(
							(rs.getString("no_of_package") != null) ? rs.getBigDecimal("no_of_package").toPlainString()
									: null);
					imp.setNotify_party_address(rs.getString("notify_party_address"));
					imp.setNotify_party_code(rs.getString("notify_party_code"));
					imp.setNotify_party_name(rs.getString("notify_party_name"));
					imp.setOrigin_country(rs.getString("origin_country"));
					imp.setOrigin_port(rs.getString("origin_port"));
					imp.setOrigin_port_code(rs.getString("origin_port_code"));
					imp.setPackage_description(rs.getString("package_description"));
					imp.setPackage_type(rs.getString("package_type"));
					imp.setProduct_description(rs.getString("product_description"));
					imp.setQuantity((rs.getString("quantity") != null)
							? new BigDecimal(rs.getString("quantity")).setScale(3, RoundingMode.HALF_UP)
							: null);
					imp.setShip_name(rs.getString("ship_name"));
					imp.setStd_quantity((rs.getString("std_quantity") != null)
							? new BigDecimal(rs.getString("std_quantity")).setScale(3, RoundingMode.HALF_UP)
							: null);
					imp.setStd_unit(rs.getString("std_unit"));
					imp.setSubitem_no(rs.getString("subitem_no"));
					imp.setTax_amount(
							(rs.getString("tax_amount") != null) ? rs.getBigDecimal("tax_amount").toPlainString()
									: null);
					imp.setTax_percentge(rs.getString("tax_percentge"));
					imp.setTotal_freight_value((rs.getString("total_freight_value") != null)
							? rs.getBigDecimal("total_freight_value").toPlainString()
							: null);
					imp.setTotal_insurance_value((rs.getString("total_insurance_value") != null)
							? rs.getBigDecimal("total_insurance_value").toPlainString()
							: null);
					imp.setTotal_invoice_value((rs.getString("total_invoice_value") != null)
							? rs.getBigDecimal("total_invoice_value").toPlainString()
							: null);
					imp.setTotal_invoice_value_usd((rs.getString("total_invoice_value_usd") != null)
							? rs.getBigDecimal("total_invoice_value_usd").toPlainString()
							: null);
					imp.setTotal_value((rs.getString("total_value") != null)
							? new BigDecimal(rs.getString("total_value")).setScale(3, RoundingMode.HALF_UP)
							: null);
					imp.setTotal_value_bdt((rs.getString("total_value_bdt") != null)
							? rs.getBigDecimal("total_value_bdt").toPlainString()
							: null);
					imp.setTotal_value_cif((rs.getString("total_value_cif") != null)
							? rs.getBigDecimal("total_value_cif").toPlainString()
							: null);
					imp.setTotal_value_cif_naira((rs.getString("total_value_cif_naira") != null)
							? rs.getBigDecimal("total_value_cif_naira").toPlainString()
							: null);
					imp.setTotal_value_cif_ugx((rs.getString("total_value_cif_ugx") != null)
							? rs.getBigDecimal("total_value_cif_ugx").toPlainString()
							: null);
					imp.setTotal_value_cif_usd((rs.getString("total_value_cif_usd") != null)
							? rs.getBigDecimal("total_value_cif_usd").toPlainString()
							: null);
					imp.setTotal_value_cif_zwd((rs.getString("total_value_cif_zwd") != null)
							? rs.getBigDecimal("total_value_cif_zwd").toPlainString()
							: null);
					imp.setTotal_value_etb((rs.getString("total_value_etb") != null)
							? rs.getBigDecimal("total_value_etb").toPlainString()
							: null);
					imp.setTotal_value_kes((rs.getString("total_value_kes") != null)
							? rs.getBigDecimal("total_value_kes").toPlainString()
							: null);
					imp.setTotal_value_lsl((rs.getString("total_value_lsl") != null)
							? rs.getBigDecimal("total_value_lsl").toPlainString()
							: null);
					imp.setTotal_value_naira((rs.getString("total_value_naira") != null)
							? rs.getBigDecimal("total_value_naira").toPlainString()
							: null);
					imp.setTotal_value_uah((rs.getString("total_value_uah") != null)
							? rs.getBigDecimal("total_value_uah").toPlainString()
							: null);
					imp.setTotal_value_usd((rs.getString("total_value_usd") != null)
							? new BigDecimal(rs.getString("total_value_usd")).setScale(3, RoundingMode.HALF_UP)
							: null);
					imp.setTransport_company(rs.getString("transport_company"));
					imp.setTransport_doc_date(rs.getString("transport_doc_date"));
					imp.setTransport_doc_no(rs.getString("transport_doc_no"));
					imp.setUnit(rs.getString("unit"));
					imp.setUnit_price((rs.getString("unit_price") != null)
							? new BigDecimal(rs.getString("unit_price")).setScale(3, RoundingMode.HALF_UP)
							: null);
					imp.setUnit_price_bdt((rs.getString("unit_price_bdt") != null)
							? rs.getBigDecimal("unit_price_bdt").toPlainString()
							: null);
					imp.setUnit_price_kes((rs.getString("unit_price_kes") != null)
							? rs.getBigDecimal("unit_price_kes").toPlainString()
							: null);
					imp.setUnit_price_usd((rs.getString("unit_price_usd") != null)
							? new BigDecimal(rs.getString("unit_price_usd")).setScale(3, RoundingMode.HALF_UP)
							: null);
					imp.setValue_added_tax_amount((rs.getString("value_added_tax_amount") != null)
							? rs.getBigDecimal("value_added_tax_amount").toPlainString()
							: null);
					imp.setValue_added_tax_mod(rs.getString("value_added_tax_mod"));
					imp.setVariety(rs.getString("variety"));
					imp.setVat_preference(rs.getString("vat_preference"));
					imp.setYear(rs.getString("year"));
					imp.setPlace_of_receipt(rs.getString("place_of_receipt"));
					imp.setAssessed_value_currency(rs.getString("assessed_value_currency"));

					imp.setUnit_value_cif_usd((rs.getString("unit_value_cif_usd") != null)
							? rs.getBigDecimal("unit_value_cif_usd").toPlainString()
							: null);
					imp.setInvoice_currency(rs.getString("invoice_currency"));

					imp.setCtry_code(rs.getString("ctry_code"));
					imp.setCtry_name(rs.getString("ctry_name"));

					imp.setStd_unit_rate_usd((rs.getString("std_unit_rate_usd") != null)
							? new BigDecimal(rs.getString("std_unit_rate_usd")).setScale(3, RoundingMode.HALF_UP)
							: null);
					// imp.setStd_unit_rate_usd(rs.getString("std_unit_rate_usd"));
					list.add(imp);
				}
				userSearchResponse.setImpForeignList(list);
			}

		} else if (userSearchRequest.getCountryCode().equals("IND")) {

			if (userSearchRequest.getTradeType().getValue() == "EXPORT") {
				List<ExpInd> list = new ArrayList<ExpInd>();

				while (rs.next()) {

					ExpInd expInd = new ExpInd();

					expInd.setId(rs.getLong("id"));
					expInd.setSb_no(rs.getString("sb_no"));
					expInd.setSb_date(rs.getDate("sb_date"));
					expInd.setHs_code(rs.getString("hs_code"));
					expInd.setProduct(rs.getString("product"));
					expInd.setQty(Double.parseDouble(String.format("%.2f", rs.getDouble("qty"))));
					expInd.setUnit(rs.getString("unit"));
					expInd.setUnit_rate_in_foreign_currency(
							Double.parseDouble(String.format("%.2f", rs.getDouble("unit_rate_in_foreign_currency"))));
					expInd.setUnit_rate_currency(rs.getString("unit_rate_currency"));
					expInd.setTotal_sb_value_in_inr_in_lacs(
							Double.parseDouble(String.format("%.2f", rs.getDouble("total_sb_value_in_inr_in_lacs"))));
					expInd.setValue_in_fc(Double.parseDouble(String.format("%.2f", rs.getDouble("value_in_fc"))));
					expInd.setPort_of_destination(rs.getString("port_of_destination"));
					expInd.setCity_of_destination(rs.getString("city_of_destination"));
					expInd.setPort_of_origin(rs.getString("port_of_origin"));
					expInd.setIndian_exportar_name(rs.getString("indian_exportar_name"));
					expInd.setExporter_add1(rs.getString("exporter_add1"));
					expInd.setExporter_add2(rs.getString("exporter_add2"));
					expInd.setExporter_city(rs.getString("exporter_city"));
					expInd.setForeign_importer_name(rs.getString("foreign_importer_name"));
					expInd.setFor_add1(rs.getString("for_add1"));
					expInd.setFor_add2(rs.getString("for_add2"));
					expInd.setFor_add3(rs.getString("for_add3"));
					expInd.setFor_add4(rs.getString("for_add4"));
					expInd.setImporter_country(rs.getString("importer_country"));
					expInd.setMonth(rs.getString("month"));
					expInd.setHs2(rs.getString("hs2"));
					expInd.setHs4(rs.getString("hs4"));
					expInd.setCur_que(rs.getString("cur_que"));
					expInd.setInvoice_no(rs.getString("invoice_no"));
					expInd.setItem_no(Double.parseDouble(String.format("%.2f", rs.getDouble("item_no"))));
					expInd.setIec(rs.getString("iec"));
					expInd.setInvoice_srl_no(rs.getString("invoice_srl_no"));
					expInd.setChallan_no(rs.getString("challan_no"));
					expInd.setDraw_back(Double.parseDouble(String.format("%.2f", rs.getDouble("draw_back"))));
					expInd.setRaw_port(rs.getString("raw_port"));
					expInd.setCush(rs.getString("cush"));
					expInd.setInvoice_date(rs.getString("invoice_date"));
					expInd.setCha_no(rs.getString("cha_no"));
					expInd.setCha_name(rs.getString("cha_name"));
					expInd.setFor_port_code(rs.getString("for_port_code"));
					expInd.setLeo_date(rs.getDate("leo_date"));
					expInd.setCountry_code(rs.getString("country_code"));
					expInd.setDrawback_excise(
							Double.parseDouble(String.format("%.2f", rs.getDouble("drawback_excise"))));
					expInd.setDrawback_customs(
							Double.parseDouble(String.format("%.2f", rs.getDouble("drawback_customs"))));

					expInd.setStd_qty(Double.parseDouble(String.format("%.2f", rs.getDouble("std_qty"))));
					expInd.setStd_unit(rs.getString("std_unit"));
					expInd.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
					expInd.setUnit_rate_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("unit_rate_usd"))));
					expInd.setShip_mode(rs.getString("ship_mode"));
					expInd.setIncoterm(rs.getString("incoterm"));
					expInd.setUsd_rate(Double.parseDouble(String.format("%.2f", rs.getDouble("usd_rate"))));

					expInd.setStd_unit_rate_usd(rs.getString("std_unit_rate_usd"));

					list.add(expInd);
				}
				userSearchResponse.setExpIndList(list);
			} else {
				List<ImpInd> list = new ArrayList<ImpInd>();

				while (rs.next()) {

					ImpInd impInd = new ImpInd();

					impInd.setId(rs.getLong("id"));
					impInd.setHs_code(rs.getString("hs_code"));
					impInd.setIec(rs.getString("iec"));
					impInd.setImporter(rs.getString("importer"));
					impInd.setAddress1(rs.getString("address1"));
					impInd.setAddress2(rs.getString("address2"));
					impInd.setCity(rs.getString("city"));
					impInd.setPin(rs.getString("pin"));
					impInd.setProd_desc(rs.getString("prod_desc"));
					impInd.setInd_port(rs.getString("ind_port"));
					impInd.setFor_count(rs.getString("for_count"));
					impInd.setFor_port(rs.getString("for_port"));
					impInd.setValue(Double.parseDouble(String.format("%.2f", rs.getDouble("value"))));
					impInd.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
					impInd.setQty(Double.parseDouble(String.format("%.2f", rs.getDouble("qty"))));
					impInd.setUnit(rs.getString("unit"));
					impInd.setVal_duty_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("val_duty_inr"))));
					impInd.setVal_duty_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("val_duty_usd"))));
					impInd.setApplicable_duty(
							Double.parseDouble(String.format("%.2f", rs.getDouble("applicable_duty"))));
					impInd.setUnit_rate_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("unit_rate_inr"))));
					impInd.setShip_mode(rs.getString("ship_mode"));
					impInd.setUnit_rate_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("unit_rate_usd"))));
					impInd.setBeno(rs.getString("beno"));
					impInd.setCha_no(rs.getString("cha_no"));
					impInd.setCha_name(rs.getString("cha_name"));
					impInd.setActual_duty(Double.parseDouble(String.format("%.2f", rs.getDouble("actual_duty"))));
					impInd.setTotal_duty_on_entire_be(
							Double.parseDouble(String.format("%.2f", rs.getDouble("total_duty_on_entire_be"))));
					impInd.setTyp(rs.getString("typ"));
					impInd.setAg(rs.getString("ag"));
					impInd.setInv_no(rs.getString("inv_no"));
					impInd.setItem_no(rs.getString("item_no"));
					impInd.setUnitprice_fc(Double.parseDouble(String.format("%.2f", rs.getDouble("unitprice_fc"))));
					impInd.setCurrency(rs.getString("currency"));
					impInd.setForeign_exporter(rs.getString("foreign_exporter"));
					impInd.setForeign_address(rs.getString("foreign_address"));
					impInd.setDate(rs.getDate("date"));
					impInd.setCush(rs.getString("cush"));
					impInd.setLocation(rs.getString("location"));
					impInd.setInv_date(rs.getString("inv_date"));
					impInd.setInv_srl_no(rs.getString("inv_srl_no"));
					impInd.setInv_value(Double.parseDouble(String.format("%.2f", rs.getDouble("inv_value"))));
					impInd.setForcntry_code(rs.getString("forcntry_code"));
					impInd.setForport_code(rs.getString("forport_code"));
					impInd.setShipmentportcode(rs.getString("shipmentportcode"));
					impInd.setShipmentport(rs.getString("shipmentport"));
					impInd.setBcd_notn(rs.getString("bcd_notn"));
					impInd.setBcd_rate(Double.parseDouble(String.format("%.2f", rs.getDouble("bcd_rate"))));
					impInd.setBcd_amt(Double.parseDouble(String.format("%.2f", rs.getDouble("bcd_amt"))));
					impInd.setCvd_notn(rs.getString("cvd_notn"));
					impInd.setCvd_rate(Double.parseDouble(String.format("%.2f", rs.getDouble("cvd_rate"))));
					impInd.setCvd_amt(Double.parseDouble(String.format("%.2f", rs.getDouble("cvd_amt"))));
					impInd.setIgst_amt(Double.parseDouble(String.format("%.2f", rs.getDouble("igst_amt"))));
					impInd.setGst_cess_amt(Double.parseDouble(String.format("%.2f", rs.getDouble("gst_cess_amt"))));

					impInd.setStd_qty(Double.parseDouble(String.format("%.2f", rs.getDouble("std_qty"))));
					impInd.setStd_unit(rs.getString("std_unit"));
					impInd.setIncoterm(rs.getString("incoterm"));

					impInd.setHs2(rs.getString("hs2"));
					impInd.setHs4(rs.getString("hs4"));

					impInd.setStd_unit_rate_usd(rs.getString("std_unit_rate_usd"));

					list.add(impInd);
				}
				userSearchResponse.setImpIndList(list);
			}

		} else {

			if (userSearchRequest.getTradeType().getValue() == "EXPORT") {
				List<ExpInd> list = new ArrayList<ExpInd>();

				while (rs.next()) {
					ExpInd expInd = new ExpInd();

					expInd.setId(rs.getLong("id"));
					expInd.setSb_no(rs.getString("sb_no"));
					expInd.setSb_date(rs.getDate("sb_date"));
					expInd.setHs_code(rs.getString("hs_code"));
					expInd.setProduct(rs.getString("product"));
					expInd.setQty(Double.parseDouble(String.format("%.2f", rs.getDouble("qty"))));
					expInd.setUnit(rs.getString("unit"));
					expInd.setUnit_rate_in_foreign_currency(
							Double.parseDouble(String.format("%.2f", rs.getDouble("unit_rate_in_foreign_currency"))));
					expInd.setUnit_rate_currency(rs.getString("unit_rate_currency"));
					expInd.setTotal_sb_value_in_inr_in_lacs(
							Double.parseDouble(String.format("%.2f", rs.getDouble("total_sb_value_in_inr_in_lacs"))));
					expInd.setValue_in_fc(Double.parseDouble(String.format("%.2f", rs.getDouble("value_in_fc"))));
					expInd.setPort_of_destination(rs.getString("port_of_destination"));
					expInd.setCity_of_destination(rs.getString("city_of_destination"));
					expInd.setPort_of_origin(rs.getString("port_of_origin"));
					expInd.setIndian_exportar_name(rs.getString("indian_exportar_name"));
					expInd.setExporter_add1(rs.getString("exporter_add1"));
					expInd.setExporter_add2(rs.getString("exporter_add2"));
					expInd.setExporter_city(rs.getString("exporter_city"));
					expInd.setForeign_importer_name(rs.getString("foreign_importer_name"));
					expInd.setFor_add1(rs.getString("for_add1"));
					expInd.setFor_add2(rs.getString("for_add2"));
					expInd.setFor_add3(rs.getString("for_add3"));
					expInd.setFor_add4(rs.getString("for_add4"));
					expInd.setImporter_country(rs.getString("importer_country"));
					expInd.setMonth(rs.getString("month"));
					expInd.setHs2(rs.getString("hs2"));
					expInd.setHs4(rs.getString("hs4"));
					expInd.setCur_que(rs.getString("cur_que"));
					expInd.setInvoice_no(rs.getString("invoice_no"));
					expInd.setItem_no(Double.parseDouble(String.format("%.2f", rs.getDouble("item_no"))));
					expInd.setIec(rs.getString("iec"));
					expInd.setInvoice_srl_no(rs.getString("invoice_srl_no"));
					expInd.setChallan_no(rs.getString("challan_no"));
					expInd.setDraw_back(Double.parseDouble(String.format("%.2f", rs.getDouble("draw_back"))));
					expInd.setRaw_port(rs.getString("raw_port"));
					expInd.setCush(rs.getString("cush"));
					expInd.setInvoice_date(rs.getString("invoice_date"));
					expInd.setCha_no(rs.getString("cha_no"));
					expInd.setCha_name(rs.getString("cha_name"));
					expInd.setFor_port_code(rs.getString("for_port_code"));
					expInd.setLeo_date(rs.getDate("leo_date"));
					expInd.setCountry_code(rs.getString("country_code"));
					expInd.setDrawback_excise(
							Double.parseDouble(String.format("%.2f", rs.getDouble("drawback_excise"))));
					expInd.setDrawback_customs(
							Double.parseDouble(String.format("%.2f", rs.getDouble("drawback_customs"))));

					expInd.setStd_qty(Double.parseDouble(String.format("%.2f", rs.getDouble("std_qty"))));
					expInd.setStd_unit(rs.getString("std_unit"));
					expInd.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
					expInd.setUnit_rate_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("unit_rate_usd"))));
					expInd.setShip_mode(rs.getString("ship_mode"));
					expInd.setIncoterm(rs.getString("incoterm"));
					expInd.setUsd_rate(Double.parseDouble(String.format("%.2f", rs.getDouble("usd_rate"))));

					expInd.setStd_unit_rate_usd(rs.getString("std_unit_rate_usd"));

					list.add(expInd);

				}

				userSearchResponse.setExpIndList(list);
			} else {
				List<ImpInd> list = new ArrayList<ImpInd>();

				while (rs.next()) {
					ImpInd impInd = new ImpInd();

					impInd.setId(rs.getLong("id"));
					impInd.setHs_code(rs.getString("hs_code"));
					impInd.setIec(rs.getString("iec"));
					impInd.setImporter(rs.getString("importer"));
					impInd.setAddress1(rs.getString("address1"));
					impInd.setAddress2(rs.getString("address2"));
					impInd.setCity(rs.getString("city"));
					impInd.setPin(rs.getString("pin"));
					impInd.setProd_desc(rs.getString("prod_desc"));
					impInd.setInd_port(rs.getString("ind_port"));
					impInd.setFor_count(rs.getString("for_count"));
					impInd.setFor_port(rs.getString("for_port"));
					impInd.setValue(Double.parseDouble(String.format("%.2f", rs.getDouble("value"))));
					impInd.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
					impInd.setQty(Double.parseDouble(String.format("%.2f", rs.getDouble("qty"))));
					impInd.setUnit(rs.getString("unit"));
					impInd.setVal_duty_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("val_duty_inr"))));
					impInd.setVal_duty_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("val_duty_usd"))));
					impInd.setApplicable_duty(
							Double.parseDouble(String.format("%.2f", rs.getDouble("applicable_duty"))));
					impInd.setUnit_rate_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("unit_rate_inr"))));
					impInd.setShip_mode(rs.getString("ship_mode"));
					impInd.setUnit_rate_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("unit_rate_usd"))));
					impInd.setBeno(rs.getString("beno"));
					impInd.setCha_no(rs.getString("cha_no"));
					impInd.setCha_name(rs.getString("cha_name"));
					impInd.setActual_duty(Double.parseDouble(String.format("%.2f", rs.getDouble("actual_duty"))));
					impInd.setTotal_duty_on_entire_be(
							Double.parseDouble(String.format("%.2f", rs.getDouble("total_duty_on_entire_be"))));
					impInd.setTyp(rs.getString("typ"));
					impInd.setAg(rs.getString("ag"));
					impInd.setInv_no(rs.getString("inv_no"));
					impInd.setItem_no(rs.getString("item_no"));
					impInd.setUnitprice_fc(Double.parseDouble(String.format("%.2f", rs.getDouble("unitprice_fc"))));
					impInd.setCurrency(rs.getString("currency"));
					impInd.setForeign_exporter(rs.getString("foreign_exporter"));
					impInd.setForeign_address(rs.getString("foreign_address"));
					impInd.setDate(rs.getDate("date"));
					impInd.setCush(rs.getString("cush"));
					impInd.setLocation(rs.getString("location"));
					impInd.setInv_date(rs.getString("inv_date"));
					impInd.setInv_srl_no(rs.getString("inv_srl_no"));
					impInd.setInv_value(Double.parseDouble(String.format("%.2f", rs.getDouble("inv_value"))));
					impInd.setForcntry_code(rs.getString("forcntry_code"));
					impInd.setForport_code(rs.getString("forport_code"));
					impInd.setShipmentportcode(rs.getString("shipmentportcode"));
					impInd.setShipmentport(rs.getString("shipmentport"));
					impInd.setBcd_notn(rs.getString("bcd_notn"));
					impInd.setBcd_rate(Double.parseDouble(String.format("%.2f", rs.getDouble("bcd_rate"))));
					impInd.setBcd_amt(Double.parseDouble(String.format("%.2f", rs.getDouble("bcd_amt"))));
					impInd.setCvd_notn(rs.getString("cvd_notn"));
					impInd.setCvd_rate(Double.parseDouble(String.format("%.2f", rs.getDouble("cvd_rate"))));
					impInd.setCvd_amt(Double.parseDouble(String.format("%.2f", rs.getDouble("cvd_amt"))));
					impInd.setIgst_amt(Double.parseDouble(String.format("%.2f", rs.getDouble("igst_amt"))));
					impInd.setGst_cess_amt(Double.parseDouble(String.format("%.2f", rs.getDouble("gst_cess_amt"))));

					impInd.setStd_qty(Double.parseDouble(String.format("%.2f", rs.getDouble("std_qty"))));
					impInd.setStd_unit(rs.getString("std_unit"));
					impInd.setIncoterm(rs.getString("incoterm"));

					impInd.setHs2(rs.getString("hs2"));
					impInd.setHs4(rs.getString("hs4"));

					impInd.setStd_unit_rate_usd(rs.getString("std_unit_rate_usd"));

					list.add(impInd);

				}

				userSearchResponse.setImpIndList(list);
			}

		}
		return userSearchResponse;
	}

	public ListImportersResponse creteListImporters(ResultSet rs) throws Exception {

		ListImportersResponse listImportersResponse = new ListImportersResponse();

		BigDecimal bd = new BigDecimal("0.00");
		BigDecimal bdINR = new BigDecimal("0.00");
		Double qnt = 0.0d;
		Long sc = 0l;
		Double vs = 0.0d;

		BigDecimal bdTop10 = new BigDecimal("0.00");
		BigDecimal bdINRTop10 = new BigDecimal("0.00");
		Double qntTop10 = 0.0d;
		Long scTop10 = 0l;
		Double vsTop10 = 0.0d;

		String importer_name;

		int i = 0;

		List<ListImporters> list = new ArrayList<ListImporters>();

		while (rs.next()) {
			ListImporters listImporters = new ListImporters();

			importer_name = rs.getString("importer_name");

			if (importer_name == null)
				continue;

			listImporters.setImporter_name(rs.getString("importer_name"));
			listImporters.setValue_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("value_inr"))));
			listImporters.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
			listImporters.setQuantity(Double.parseDouble(String.format("%.2f", rs.getDouble("total_count"))));
			listImporters.setShare(Double.parseDouble(String.format("%.2f", rs.getDouble("tot_share"))));
			listImporters.setShipment_count(rs.getLong("shipment_count"));

			bd = bd.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
			bdINR = bdINR.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
			qnt = qnt + rs.getDouble("total_count");
			sc = sc + rs.getLong("shipment_count");
			vs = vs + rs.getDouble("tot_share");

			if (i > 9) {
				bdTop10 = bdTop10.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
				bdINRTop10 = bdINRTop10.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
				qntTop10 = qntTop10 + rs.getDouble("total_count");
				scTop10 = scTop10 + rs.getLong("shipment_count");
				vsTop10 = vsTop10 + rs.getDouble("tot_share");

			}
			i++;

			list.add(listImporters);
		}
		listImportersResponse.setImportersList(list);

		listImportersResponse.setTotalQuantity(Double.parseDouble(String.format("%.2f", qnt)));
		listImportersResponse.setTotalValueUSD(bd.setScale(2, RoundingMode.DOWN));
		listImportersResponse.setTotalValueINR(bdINR.setScale(2, RoundingMode.DOWN));
		listImportersResponse.setShipmentCount(sc);
		listImportersResponse.setValueShare(Double.parseDouble(String.format("%.0f", vs)));

		listImportersResponse.setTotalQuantityTop10(Double.parseDouble(String.format("%.2f", qntTop10)));
		listImportersResponse.setTotalValueUSDTop10(bdTop10.setScale(2, RoundingMode.DOWN));
		listImportersResponse.setTotalValueINRTop10(bdINRTop10.setScale(2, RoundingMode.DOWN));
		listImportersResponse.setShipmentCountTop10(scTop10);
		listImportersResponse.setValueShareTop10(Double.parseDouble(String.format("%.2f", vsTop10)));

		return listImportersResponse;
	}

	public ListExportersResponse creteListExporters(ResultSet rs) throws Exception {
		ListExportersResponse listExportersResponse = new ListExportersResponse();

		BigDecimal bd = new BigDecimal("0.00");
		BigDecimal bdINR = new BigDecimal("0.00");
		Double qnt = 0.0d;
		Long sc = 0l;
		Double vs = 0.0d;

		BigDecimal bdTop10 = new BigDecimal("0.00");
		BigDecimal bdINRTop10 = new BigDecimal("0.00");
		Double qntTop10 = 0.0d;
		Long scTop10 = 0l;
		Double vsTop10 = 0.0d;

		String exporter_name;

		int i = 0;
		List<ListExporters> list = new ArrayList<ListExporters>();

		while (rs.next()) {
			ListExporters listExporters = new ListExporters();

			exporter_name = rs.getString("exporter_name");

			if (exporter_name == null)
				continue;

			listExporters.setExporter_name(rs.getString("exporter_name"));
			listExporters.setValue_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("value_inr"))));
			listExporters.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
			listExporters.setQuantity(Double.parseDouble(String.format("%.2f", rs.getDouble("total_count"))));
			listExporters.setShare(Double.parseDouble(String.format("%.2f", rs.getDouble("tot_share"))));
			listExporters.setShipment_count(rs.getLong("shipment_count"));

			bd = bd.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
			bdINR = bdINR.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
			qnt = qnt + rs.getDouble("total_count");
			sc = sc + rs.getLong("shipment_count");
			vs = vs + rs.getDouble("tot_share");

			if (i > 9) {
				bdTop10 = bdTop10.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
				bdINRTop10 = bdINRTop10.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
				qntTop10 = qntTop10 + rs.getDouble("total_count");
				scTop10 = scTop10 + rs.getLong("shipment_count");
				vsTop10 = vsTop10 + rs.getDouble("tot_share");

			}
			i++;
			list.add(listExporters);
		}
		listExportersResponse.setExportersList(list);

		listExportersResponse.setTotalQuantity(Double.parseDouble(String.format("%.2f", qnt)));
		listExportersResponse.setTotalValueUSD(bd.setScale(2, RoundingMode.DOWN));
		listExportersResponse.setTotalValueINR(bdINR.setScale(2, RoundingMode.DOWN));
		listExportersResponse.setShipmentCount(sc);
		listExportersResponse.setValueShare(Double.parseDouble(String.format("%.0f", vs)));

		listExportersResponse.setTotalQuantityTop10(Double.parseDouble(String.format("%.2f", qntTop10)));
		listExportersResponse.setTotalValueUSDTop10(bdTop10.setScale(2, RoundingMode.DOWN));
		listExportersResponse.setTotalValueINRTop10(bdINRTop10.setScale(2, RoundingMode.DOWN));
		listExportersResponse.setShipmentCountTop10(scTop10);
		listExportersResponse.setValueShareTop10(Double.parseDouble(String.format("%.2f", vsTop10)));

		return listExportersResponse;
	}

	public ListCountriesResponse creteListCountries(ResultSet rs) throws Exception {
		ListCountriesResponse listCountriesResponse = new ListCountriesResponse();

		BigDecimal bd = new BigDecimal("0.00");
		BigDecimal bdINR = new BigDecimal("0.00");
		Double qnt = 0.0d;
		Long sc = 0l;
		Double vs = 0.0d;

		BigDecimal bdTop10 = new BigDecimal("0.00");
		BigDecimal bdINRTop10 = new BigDecimal("0.00");
		Double qntTop10 = 0.0d;
		Long scTop10 = 0l;
		Double vsTop10 = 0.0d;

		String country_name;

		int i = 0;

		List<ListCountries> list = new ArrayList<ListCountries>();

		while (rs.next()) {
			ListCountries listCountries = new ListCountries();

			country_name = rs.getString("country_name");

			if (country_name == null)
				continue;

			listCountries.setCountry_name(rs.getString("country_name"));
			listCountries.setValue_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("value_inr"))));
			listCountries.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
			listCountries.setQuantity(Double.parseDouble(String.format("%.2f", rs.getDouble("total_count"))));
			listCountries.setShare(Double.parseDouble(String.format("%.2f", rs.getDouble("tot_share"))));
			listCountries.setShipment_count(rs.getLong("shipment_count"));

			bd = bd.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
			bdINR = bdINR.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
			qnt = qnt + rs.getDouble("total_count");
			sc = sc + rs.getLong("shipment_count");
			vs = vs + rs.getDouble("tot_share");

			if (i > 9) {
				bdTop10 = bdTop10.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
				bdINRTop10 = bdINRTop10.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
				qntTop10 = qntTop10 + rs.getDouble("total_count");
				scTop10 = scTop10 + rs.getLong("shipment_count");
				vsTop10 = vsTop10 + rs.getDouble("tot_share");

			}
			i++;

			list.add(listCountries);
		}
		listCountriesResponse.setCountriesList(list);

		listCountriesResponse.setTotalQuantity(Double.parseDouble(String.format("%.2f", qnt)));
		listCountriesResponse.setTotalValueUSD(bd.setScale(2, RoundingMode.DOWN));
		listCountriesResponse.setTotalValueINR(bdINR.setScale(2, RoundingMode.DOWN));
		listCountriesResponse.setShipmentCount(sc);
		listCountriesResponse.setValueShare(Double.parseDouble(String.format("%.0f", vs)));

		listCountriesResponse.setTotalQuantityTop10(Double.parseDouble(String.format("%.2f", qntTop10)));
		listCountriesResponse.setTotalValueUSDTop10(bdTop10.setScale(2, RoundingMode.DOWN));
		listCountriesResponse.setTotalValueINRTop10(bdINRTop10.setScale(2, RoundingMode.DOWN));
		listCountriesResponse.setShipmentCountTop10(scTop10);
		listCountriesResponse.setValueShareTop10(Double.parseDouble(String.format("%.2f", vsTop10)));

		return listCountriesResponse;
	}

	public ListCitiesResponse creteListCities(ResultSet rs) throws Exception {
		ListCitiesResponse listCitiesResponse = new ListCitiesResponse();

		BigDecimal bd = new BigDecimal("0.00");
		BigDecimal bdINR = new BigDecimal("0.00");
		Double qnt = 0.0d;
		Long sc = 0l;
		Double vs = 0.0d;

		BigDecimal bdTop10 = new BigDecimal("0.00");
		BigDecimal bdINRTop10 = new BigDecimal("0.00");
		Double qntTop10 = 0.0d;
		Long scTop10 = 0l;
		Double vsTop10 = 0.0d;

		String city_name;

		int i = 0;

		List<ListCities> list = new ArrayList<ListCities>();

		while (rs.next()) {
			ListCities listCities = new ListCities();

			city_name = rs.getString("city_name");

			if (city_name == null)
				continue;

			listCities.setCity_name(rs.getString("city_name"));
			listCities.setValue_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("value_inr"))));
			listCities.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
			listCities.setQuantity(Double.parseDouble(String.format("%.2f", rs.getDouble("total_count"))));
			listCities.setShare(Double.parseDouble(String.format("%.2f", rs.getDouble("tot_share"))));
			listCities.setShipment_count(rs.getLong("shipment_count"));

			bd = bd.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
			bdINR = bdINR.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
			qnt = qnt + rs.getDouble("total_count");
			sc = sc + rs.getLong("shipment_count");
			vs = vs + rs.getDouble("tot_share");

			if (i > 9) {
				bdTop10 = bdTop10.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
				bdINRTop10 = bdINRTop10.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
				qntTop10 = qntTop10 + rs.getDouble("total_count");
				scTop10 = scTop10 + rs.getLong("shipment_count");
				vsTop10 = vsTop10 + rs.getDouble("tot_share");

			}
			i++;

			list.add(listCities);
		}
		listCitiesResponse.setCitiesList(list);

		listCitiesResponse.setTotalQuantity(Double.parseDouble(String.format("%.2f", qnt)));
		listCitiesResponse.setTotalValueUSD(bd.setScale(2, RoundingMode.DOWN));
		listCitiesResponse.setTotalValueINR(bdINR.setScale(2, RoundingMode.DOWN));
		listCitiesResponse.setShipmentCount(sc);
		listCitiesResponse.setValueShare(Double.parseDouble(String.format("%.0f", vs)));

		listCitiesResponse.setTotalQuantityTop10(Double.parseDouble(String.format("%.2f", qntTop10)));
		listCitiesResponse.setTotalValueUSDTop10(bdTop10.setScale(2, RoundingMode.DOWN));
		listCitiesResponse.setTotalValueINRTop10(bdINRTop10.setScale(2, RoundingMode.DOWN));
		listCitiesResponse.setShipmentCountTop10(scTop10);
		listCitiesResponse.setValueShareTop10(Double.parseDouble(String.format("%.2f", vsTop10)));

		return listCitiesResponse;
	}

	public ListPortsResponse creteListPorts(ResultSet rs) throws Exception {
		ListPortsResponse listPortsResponse = new ListPortsResponse();

		BigDecimal bd = new BigDecimal("0.00");
		BigDecimal bdINR = new BigDecimal("0.00");
		Double qnt = 0.0d;
		Long sc = 0l;
		Double vs = 0.0d;

		BigDecimal bdTop10 = new BigDecimal("0.00");
		BigDecimal bdINRTop10 = new BigDecimal("0.00");
		Double qntTop10 = 0.0d;
		Long scTop10 = 0l;
		Double vsTop10 = 0.0d;

		String port_name;

		int i = 0;

		List<ListPorts> list = new ArrayList<ListPorts>();

		while (rs.next()) {
			ListPorts listPorts = new ListPorts();

			port_name = rs.getString("port_name");

			if (port_name == null)
				continue;

			listPorts.setPort_name(rs.getString("port_name"));
			listPorts.setValue_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("value_inr"))));
			listPorts.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
			listPorts.setQuantity(Double.parseDouble(String.format("%.2f", rs.getDouble("total_count"))));
			listPorts.setShare(Double.parseDouble(String.format("%.2f", rs.getDouble("tot_share"))));
			listPorts.setShipment_count(rs.getLong("shipment_count"));

			bd = bd.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
			bdINR = bdINR.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
			qnt = qnt + rs.getDouble("total_count");
			sc = sc + rs.getLong("shipment_count");
			vs = vs + rs.getDouble("tot_share");

			if (i > 9) {
				bdTop10 = bdTop10.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
				bdINRTop10 = bdINRTop10.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
				qntTop10 = qntTop10 + rs.getDouble("total_count");
				scTop10 = scTop10 + rs.getLong("shipment_count");
				vsTop10 = vsTop10 + rs.getDouble("tot_share");

			}
			i++;

			list.add(listPorts);
		}

		listPortsResponse.setPortsList(list);

		listPortsResponse.setTotalQuantity(Double.parseDouble(String.format("%.2f", qnt)));
		listPortsResponse.setTotalValueUSD(bd.setScale(2, RoundingMode.DOWN));
		listPortsResponse.setTotalValueINR(bdINR.setScale(2, RoundingMode.DOWN));
		listPortsResponse.setShipmentCount(sc);
		listPortsResponse.setValueShare(Double.parseDouble(String.format("%.0f", vs)));

		listPortsResponse.setTotalQuantityTop10(Double.parseDouble(String.format("%.2f", qntTop10)));
		listPortsResponse.setTotalValueUSDTop10(bdTop10.setScale(2, RoundingMode.DOWN));
		listPortsResponse.setTotalValueINRTop10(bdINRTop10.setScale(2, RoundingMode.DOWN));
		listPortsResponse.setShipmentCountTop10(scTop10);
		listPortsResponse.setValueShareTop10(Double.parseDouble(String.format("%.2f", vsTop10)));

		return listPortsResponse;
	}

	public ListHscodesResponse creteListHscodes(ResultSet rs) throws Exception {

		ListHscodesResponse listHscodesResponse = new ListHscodesResponse();

		BigDecimal bd = new BigDecimal("0.00");
		BigDecimal bdINR = new BigDecimal("0.00");
		Double qnt = 0.0d;
		Long sc = 0l;
		Double vs = 0.0d;

		BigDecimal bdTop10 = new BigDecimal("0.00");
		BigDecimal bdINRTop10 = new BigDecimal("0.00");
		Double qntTop10 = 0.0d;
		Long scTop10 = 0l;
		Double vsTop10 = 0.0d;

		String hscode;

		int i = 0;

		List<ListHscodes> list = new ArrayList<ListHscodes>();

		while (rs.next()) {
			ListHscodes listHscodes = new ListHscodes();

			hscode = rs.getString("hscode");

			if (hscode == null)
				continue;

			listHscodes.setHscode(rs.getString("hscode"));
			listHscodes.setValue_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("value_inr"))));
			listHscodes.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
			listHscodes.setQuantity(Double.parseDouble(String.format("%.2f", rs.getDouble("total_count"))));
			listHscodes.setShare(Double.parseDouble(String.format("%.2f", rs.getDouble("tot_share"))));
			listHscodes.setShipment_count(rs.getLong("shipment_count"));

			bd = bd.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
			bdINR = bdINR.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
			qnt = qnt + rs.getDouble("total_count");
			sc = sc + rs.getLong("shipment_count");
			vs = vs + rs.getDouble("tot_share");

			if (i > 9) {
				bdTop10 = bdTop10.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
				bdINRTop10 = bdINRTop10.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
				qntTop10 = qntTop10 + rs.getDouble("total_count");
				scTop10 = scTop10 + rs.getLong("shipment_count");
				vsTop10 = vsTop10 + rs.getDouble("tot_share");

			}
			i++;

			list.add(listHscodes);
		}
		listHscodesResponse.setHscodesList(list);

		listHscodesResponse.setTotalQuantity(Double.parseDouble(String.format("%.2f", qnt)));
		listHscodesResponse.setTotalValueUSD(bd.setScale(2, RoundingMode.DOWN));
		listHscodesResponse.setTotalValueINR(bdINR.setScale(2, RoundingMode.DOWN));
		listHscodesResponse.setShipmentCount(sc);
		listHscodesResponse.setValueShare(Double.parseDouble(String.format("%.0f", vs)));

		listHscodesResponse.setTotalQuantityTop10(Double.parseDouble(String.format("%.2f", qntTop10)));
		listHscodesResponse.setTotalValueUSDTop10(bdTop10.setScale(2, RoundingMode.DOWN));
		listHscodesResponse.setTotalValueINRTop10(bdINRTop10.setScale(2, RoundingMode.DOWN));
		listHscodesResponse.setShipmentCountTop10(scTop10);
		listHscodesResponse.setValueShareTop10(Double.parseDouble(String.format("%.2f", vsTop10)));

		return listHscodesResponse;
	}

	public ListMonthwiseResponse creteListMonthwise(ResultSet rs) throws Exception {

		ListMonthwiseResponse listMonthwiseResponse = new ListMonthwiseResponse();

		BigDecimal bd = new BigDecimal("0.00");
		BigDecimal bdINR = new BigDecimal("0.00");
		Double qnt = 0.0d;
		Long sc = 0l;
		Double vs = 0.0d;

		BigDecimal bdTop10 = new BigDecimal("0.00");
		BigDecimal bdINRTop10 = new BigDecimal("0.00");
		Double qntTop10 = 0.0d;
		Long scTop10 = 0l;
		Double vsTop10 = 0.0d;

		String month_name;

		int i = 0;

		List<ListMonthwise> list = new ArrayList<ListMonthwise>();

		while (rs.next()) {
			ListMonthwise listMonthwise = new ListMonthwise();

			month_name = rs.getString("month_name");

			if (month_name == null)
				continue;

			listMonthwise.setMonth_name(rs.getString("month_name"));
			listMonthwise.setValue_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("value_inr"))));
			listMonthwise.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
			listMonthwise.setQuantity(Double.parseDouble(String.format("%.2f", rs.getDouble("total_count"))));
			listMonthwise.setShare(Double.parseDouble(String.format("%.2f", rs.getDouble("tot_share"))));
			listMonthwise.setShipment_count(rs.getLong("shipment_count"));

			bd = bd.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
			bdINR = bdINR.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
			qnt = qnt + rs.getDouble("total_count");
			sc = sc + rs.getLong("shipment_count");
			vs = vs + rs.getDouble("tot_share");

			if (i > 9) {
				bdTop10 = bdTop10.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
				bdINRTop10 = bdINRTop10.add(BigDecimal.valueOf(rs.getDouble("value_inr")));
				qntTop10 = qntTop10 + rs.getDouble("total_count");
				scTop10 = scTop10 + rs.getLong("shipment_count");
				vsTop10 = vsTop10 + rs.getDouble("tot_share");

			}
			i++;

			list.add(listMonthwise);
		}
		listMonthwiseResponse.setMonthwiseList(list);

		listMonthwiseResponse.setTotalQuantity(Double.parseDouble(String.format("%.2f", qnt)));
		listMonthwiseResponse.setTotalValueUSD(bd.setScale(2, RoundingMode.DOWN));
		listMonthwiseResponse.setTotalValueINR(bdINR.setScale(2, RoundingMode.DOWN));
		listMonthwiseResponse.setShipmentCount(sc);
		listMonthwiseResponse.setValueShare(Double.parseDouble(String.format("%.0f", vs)));

		listMonthwiseResponse.setTotalQuantityTop10(Double.parseDouble(String.format("%.2f", qntTop10)));
		listMonthwiseResponse.setTotalValueUSDTop10(bdTop10.setScale(2, RoundingMode.DOWN));
		listMonthwiseResponse.setTotalValueINRTop10(bdINRTop10.setScale(2, RoundingMode.DOWN));
		listMonthwiseResponse.setShipmentCountTop10(scTop10);
		listMonthwiseResponse.setValueShareTop10(Double.parseDouble(String.format("%.2f", vsTop10)));

		return listMonthwiseResponse;
	}

	public ListShipmentModeResponse creteListShipmentMode(ResultSet rs) throws Exception {

		ListShipmentModeResponse listShipmentModeResponse = new ListShipmentModeResponse();

		List<ListShipmentMode> list = new ArrayList<ListShipmentMode>();

		while (rs.next()) {
			ListShipmentMode listShipmentMode = new ListShipmentMode();

			listShipmentMode.setShip_mode(rs.getString("ship_mode"));

			list.add(listShipmentMode);
		}
		listShipmentModeResponse.setShipmentModeList(list);

		return listShipmentModeResponse;
	}

	public ListStdUnitResponse creteListStdUnit(ResultSet rs) throws Exception {

		ListStdUnitResponse listStdUnitResponse = new ListStdUnitResponse();

		List<ListStdUnit> list = new ArrayList<ListStdUnit>();

		while (rs.next()) {
			ListStdUnit listStdUnit = new ListStdUnit();

			listStdUnit.setStd_unit(rs.getString("std_unit"));

			list.add(listStdUnit);
		}
		listStdUnitResponse.setStdUnitList(list);

		return listStdUnitResponse;
	}

	public SuggestionListResponse creteListSuggestion(ResultSet rs) throws Exception {

		SuggestionListResponse suggestionListResponse = new SuggestionListResponse();

		List<SuggestionList> list = new ArrayList<SuggestionList>();

		while (rs.next()) {
			SuggestionList suggestionList = new SuggestionList();

			suggestionList.setListSuggestion(rs.getString("list_suggestion"));
			suggestionList.setShipmentCount(rs.getLong("shipment_count"));

			list.add(suggestionList);
		}
		suggestionListResponse.setSuggestionList(list);

		return suggestionListResponse;
	}

	public CountryWiseCountResponse creteCountryWiseCount(ResultSet rs) throws Exception {

		CountryWiseCountResponse countryWiseCountResponse = new CountryWiseCountResponse();

		List<CountryWiseCount> list = new ArrayList<CountryWiseCount>();

		while (rs.next()) {
			CountryWiseCount countryWiseCount = new CountryWiseCount();

			countryWiseCount.setCtry_code(rs.getString("ctry_code"));
			countryWiseCount.setCtry_name(rs.getString("ctry_name"));
			countryWiseCount.setShipment_count(rs.getLong("shipment_count"));

			list.add(countryWiseCount);
		}
		countryWiseCountResponse.setCountryWiseCount(list);

		return countryWiseCountResponse;
	}

	public ListDistinctColumnValuesResponse creteListDistinctColumnValues(ResultSet rs) throws Exception {
		// TODO Auto-generated method stub
		ListDistinctColumnValuesResponse listDistinctColumnValuesResponse = new ListDistinctColumnValuesResponse();

		List<ListDistinctColumnValues> list = new ArrayList<ListDistinctColumnValues>();

		while (rs.next()) {
			ListDistinctColumnValues listDistinctColumnValues = new ListDistinctColumnValues();

			listDistinctColumnValues.setColumn_name(rs.getString("column_name"));
			listDistinctColumnValues.setRecords_count(rs.getLong("records_count"));

			list.add(listDistinctColumnValues);
		}

		listDistinctColumnValuesResponse.setDistinctColumnValuesList(list);

		return listDistinctColumnValuesResponse;
	}

	public UserSearchResponse convertDataType(String exIpm, UserSearchResponse res) {

		if (exIpm.equalsIgnoreCase("EXPORT")) {

		} else {

		}
		return res;
	}
	
	public ListMonthwiseResponse creteListMonthwiseForInDepth(ResultSet rs) throws Exception {

		ListMonthwiseResponse listMonthwiseResponse = new ListMonthwiseResponse();
		BigDecimal bd = new BigDecimal("0.00");
		Double qnt = 0.0d;
		Long sc = 0l;
		String month_name;

		List<ListMonthwise> list = new ArrayList<ListMonthwise>();

		while (rs.next()) {
			ListMonthwise listMonthwise = new ListMonthwise();

			month_name = rs.getString("month_name");
			if (month_name == null)
				continue;

			listMonthwise.setMonth_name(rs.getString("month_name"));
			listMonthwise.setValue_inr(Double.parseDouble(String.format("%.2f", rs.getDouble("value_inr"))));
			listMonthwise.setValue_usd(Double.parseDouble(String.format("%.2f", rs.getDouble("value_usd"))));
			listMonthwise.setQuantity(Double.parseDouble(String.format("%.2f", rs.getDouble("total_count"))));
			listMonthwise.setShare(Double.parseDouble(String.format("%.2f", rs.getDouble("tot_share"))));
			listMonthwise.setShipment_count(rs.getLong("shipment_count"));

			qnt = qnt + rs.getDouble("total_count");
			bd = bd.add(BigDecimal.valueOf(rs.getDouble("value_usd")));
			sc = sc + rs.getLong("shipment_count");

			list.add(listMonthwise);
		}
		listMonthwiseResponse.setMonthwiseList(list);
		listMonthwiseResponse.setTotalQuantity(Double.parseDouble(String.format("%.2f", qnt)));
		listMonthwiseResponse.setTotalValueUSD(bd.setScale(2, RoundingMode.DOWN));
		listMonthwiseResponse.setShipmentCount(sc);

		return listMonthwiseResponse;
	}
}
