package com.ezen.booktving.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.ezen.booktving.service.ApiService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApiController {
	
	/*
	 * private final ApiService apiService;
	 * 
	 * //@Scheduled(fixedDelay = 10000)
	 * 
	 * @Scheduled(cron = "40 * * * * *") public void getInfo() {
	 * 
	 * try {
	 * 
	 * String apiURL =
	 * "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttblyczang41056001&QueryType=Bestseller&MaxResults=10&Cover=Big&start=1&SearchTarget=Book&output=JS&Version=20131101";
	 * 
	 * URL url = new URL(apiURL);
	 * 
	 * BufferedReader bf;
	 * 
	 * bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
	 * 
	 * String result = bf.readLine();
	 * 
	 * apiService.getInfo(result);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */
}
