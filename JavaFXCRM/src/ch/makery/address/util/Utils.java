package ch.makery.address.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class Utils {
	
	
public String parseNumber(String num){
	String f3 = num.substring(1,4);
	String l1 = num.substring(4,7);
	String l2 = num.substring(7,9);
	String l3 = num.substring(9,11);
	return "+7("+f3+")"+l1+"-"+l2+"-"+l3;
}
	
	public static String timeZone(String region) {
	      if(region.contains("Калининг")||region.contains("Крым"))
          {
             return "-3";
          }
	      if(region.contains("дмурт")||region.contains("Самар")){
	    	  return "-1";
	      }
	      if(region.contains("ашкорт")||region.contains("Перм")||region.contains("Курган")||region.contains("Орен")||region.contains("Тюмен")||region.contains("Ямал")||region.contains("Хант")||region.contains("Челяб")||region.contains("Екат")||region.contains("Свердл")){
	    	  return "0";
	      }
	      if(region.contains("Алтай")||region.contains("Новосиб")||region.contains("Омск")||region.contains("Томск")){
	    	  return "1";
	      }
	      if(region.contains("Тыва")||region.contains("Хакас")||region.contains("Краснояр")||region.contains("Кемер")){
	    	  return "2";
	      }
	      if(region.contains("Бурят")||region.contains("Забай")||region.contains("Иркут")){
	    	  return "3";
	      }
	      if(region.contains("Амур")){
	    	  return "4";
	      }
	      if(region.contains("Примор")||region.contains("Хабар")||region.contains("Магад")||region.contains("Еврей")){
	    	  return "5";
	      }
	      if(region.contains("Саха")||region.contains("Якут")||region.contains("Сахалин")){
	    	  return "6";
	      }
	      if(region.contains("Камчат")||region.contains("Чукот")){
	    	  return "7";
	      }
	      else return "-2";
	}
	public static String getSearchString(String num) {
		String f3 = num.substring(1,4);
		String l7 = num.substring(4,num.length());
		String l1 = num.substring(4,7);
		String l2 = num.substring(7,9);
		String l3 = num.substring(9,11);
		
		
		String a[] = new String[9];
		a[0] = num;
		a[1] = "8"+f3+l7;
		a[2] = "7+"+f3+"+"+l7;
		a[3] = "8+"+f3+"+"+l7;
		a[4] = "7+"+f3+"+"+l1+"+"+l2+"+"+l3;
		a[5] = "8+"+f3+"+"+l1+"+"+l2+"+"+l3;
		a[6] = "7."+f3+l7;
		a[7] = "8("+f3+")"+l7;
		a[8] = "("+f3+")"+l7;
		
		
		String ya = "";
		String nig = "";
		for(int i=0;i<a.length;i++) {
			if(ya == "") ya += "%22"+a[i]+"%22"; else ya += "+%7C+%22"+a[i]+"%22";
//			if(google == "") google += "\""+a[i]+"\""; else google += " OR \""+a[i]+"\"";
			if(nig == "") nig += "%22"+a[i]+"%22"; else nig += "+%7C+%22"+a[i]+"%22";
		}
		return ya;
	}
		public static String numbers(String num) {
			String ref1 = "http://yandex.ru/yandsearch?text="+getSearchString(num);
		//	String ref2 = "http://www.nigma.ru/index.php?s="+nig;
		return ref1;
		
	}
		public static String callHistory(String num){
		
			return "http://192.168.67.25/cc-line24/reports.php/calls/out?date[start]=31.12.2013+00%3A00&date[end]=31.12.2017+23%3A50&phone=&calleeid="+num+"&callstatus=&uniqueid=&paginator[page]=0+%D1%81%D1%82%D1%80.&sort[by]=date&sort[dir]=asc";
		
		}
		public static  Map<String, String> googleNumber(String num) throws UnsupportedEncodingException, IOException{
			String search = num;
			String google = "http://www.google.com/search?q=";
			String charset = "UTF-8";
			String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
			 Map<String, String> hashmap = new HashMap<String, String>();
			// System.out.println(search);

			Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select("li.g>h3>a");

			for (Element link : links) {
			    String title = link.text();
			    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
			    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

			    if (!url.startsWith("http")) {
			        continue; // Ads/news/etc.
			    }
			    if (!checkSpam(url)){
			    System.out.println("Title: " + title);
			    System.out.println("URL: " + url);
			    hashmap.put(title, url);
			    }
			    
			 if (hashmap.size()>5)break;
			}
			return hashmap;
		}
		
		public static boolean checkSpam(String url){
			ArrayList<String> list = new ArrayList<String>();
			list.add("kodtelefona");
			list.add("roum");
			list.add("wiki-numbers");
			list.add("phonenum.ru");
			list.add("ktozvonit.ru");
			list.add("base4all.ru");
			list.add("wzcorp.ru");
			list.add("vtbase.ru");
			list.add("numberconnection");
			list.add("tel-base.ru");
			list.add("fdzp.cn");
			list.add("zvonil.ru");
			list.add("moperator.ru");
			list.add("mobile09.ru");
			list.add("chi-chiama.it");
			list.add("numberphone.ru");
			list.add("phone8.ru");
			list.add("mobilnomera.ru");
			list.add("numerierrati");
			list.add("bs9.eu");
			list.add("base2.ru");
			list.add("spravochnik09");
			list.add("rozshuk.ru");
			list.add("codephone.ru");
			list.add("fiber.ee");
			list.add("10c.ru");
			list.add("m09base.ru");
			list.add("vtradein.ru");
			list.add("numbersaplenty");
			list.add("nomerbeeline");
			list.add("numberabon.com");
			list.add("nomerzvonka");
			list.add("mtsnums.ru");
			list.add("ismser.ru");
			list.add("meganums.ru");
			list.add("cheynomer.ru");
			list.add("beenums");
			list.add("w-gsm.com");
			list.add("nomeru-telefona");
			list.add("telephonico");
			list.add("bases/number");
			list.add("infophon");
			list.add("pronomera");
			list.add("each-number");
			list.add("notacall");
			list.add("mightynumber");
			list.add("indiatelephone");
			list.add("waslorio");
			list.add("carleton");
			list.add("kto-mne-zvonil");
			list.add("0419job");
			list.add("prozvoni");
			list.add("poisk-mobile");
			list.add("helpalias");
			list.add("chei-nomer");
			
			for(String i:list) if(url.contains(i))return true;
			return false;
		}
		
		
		
		 public static void main(String[] args) throws UnsupportedEncodingException, IOException {
			 System.out.println(googleNumber("89676646462"));
		 }
}
	