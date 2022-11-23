package com.example.AFL.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnalyzeService {

    public String[] analyze(String userName) throws IOException {   //유저 닉네임을 받아서 AI서버에 데이터를 보내고 결과값을 수신받아서 리턴해주는 메소드
        String url = "https://www.op.gg/summoners/kr/" + userName; //jsoup 디펜던시로 op.gg데이터 크롤링
        Document doc = Jsoup.connect(url).get();
        doc.text();


        URL url1 = new URL(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(url1.openStream()));
        StringBuffer sc = new StringBuffer();
        String sc_line = "";
        while ((sc_line = br.readLine()) != null){
            sc.append(sc_line);
        }
        Elements elem = doc.select("div[class=\"k-d-a\"]");
        elem.text();
        String arrays = elem.toString();



        String intStr = elem.toString().replaceAll(" / ", ",").replaceAll("\n", ",");

        String[] array = intStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replaceAll("\n", "").replaceAll(" ", "").split(",");
        List<String> result = new ArrayList<>(Arrays.asList(array));  //크롤링한 데이터에서 킬, 데스, 어시만을 나열해 배열로 추출.

        result.removeAll(Arrays.asList("", null));
        array = result.toArray(new String[0]);
        if(array.length != 60){
            String[] nodata = new String[1];
            nodata[0] = "nodata";
            return nodata;
        }
        double[] newArr = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            newArr[i] = Integer.parseInt(array[i]);
        }                       //원하는 데이터를 추출해내서 newArr[]배열에 할당

        String userData = "";
        for(int i = 0; i < newArr.length; i++){
            if(i == 0){
                userData = String.valueOf(newArr[i]);
            }
            else if(i == newArr.length - 1){
                userData += ',' + String.valueOf(newArr[i]);
            }
            else {
                userData += ',' + String.valueOf(newArr[i]);
            }

        }               //newArr[]배열에 있던 데이터를 문자열로 캐스팅해서 userData에 할당
        String dataurl = "http://220.127.74.78:5002/?data=" + userData; //AI서버에 데이터를 쿼리파라미터로 보냄.
        Document doc1 = Jsoup.connect(dataurl).get();

        doc1.text();

        URL url2 = new URL(dataurl);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(url2.openStream()));
        StringBuffer sc1 = new StringBuffer();
        String sc_line1 = "";
        while ((sc_line1 = br1.readLine()) != null){
            sc1.append(sc_line1);
        }
        Elements elem1 = doc1.select("body");
        elem1.text();
        String intStr1 = elem1.toString().replaceAll(" / ", ",").replaceAll("\n", ",");

        String[] array2 = intStr1.split(",");
        array2 = array2[1].split(" ");
        List<String> result1 = new ArrayList<>(Arrays.asList(array2));

        result1.removeAll(Arrays.asList("", null));
        for (int i = 0; i < 8; i++){
            array2[i] = array2[i].replaceAll("[^0-9]", "");     //데이터를 double배열로 만들기위해 추출
        }

        //AI서버에서 띄우는 데이터를 추출하여 array2[]배열에 저장




        return array2;
    }


}
