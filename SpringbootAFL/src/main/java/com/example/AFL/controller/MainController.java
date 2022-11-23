package com.example.AFL.controller;


import com.example.AFL.service.AnalyzeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private AnalyzeService analyzeService;

    @GetMapping("/")
    private String mainController(){
        return "main";
    }





    @GetMapping("/analyze")
    public String test(@RequestParam(value = "data",required = false) String userName, Model model) throws IOException {

        if(userName == ""){     //예외처리 유저 닉네임이 없을 경우
            return "nodata";
        }
        String[] array2 =  analyzeService.analyze(userName);    //유저 닉네임을 받아서 AI서버에 데이터를 보내고 결과값을 수신받아서 리턴해주는 메소드
        if(array2[0] == "nodata"){      //예외처리 유저 데이터가 부족하거나 존재하지않은 유저일 경우
            return "nodata";
        }
        String[] data_result = new String[7];
        double[] newArr1 = new double[7];
        double percent = 0;
        double[] percent_result = new double[7];
        int j = 0;
        for (int i = 1; i < 8; i++) {
            newArr1[j] = Integer.parseInt(array2[i]);       //계산을 위한 int형 캐스팅
            percent += newArr1[j];
            j++;
        }
        for (int i = 0; i < 7; i++){
            percent_result[i] = newArr1[i]/percent * 100.0;
            data_result[i] = String.format("%.2f", percent_result[i]);  //double을 String형태로 캐스팅
        }
        int maxIndex = 0;  //가장 큰 값을 찾는 for문
        String result_str = "";
        double max = percent_result[0];
        for (int i = 0; i < 7; i++){
            if(max < percent_result[i]){
                max = percent_result[i];
                maxIndex = i;
            }
        }
            //플레이 성향별로 가장 높은 성향에 적합한 종합 분석 결과
        switch (maxIndex){
            case 0: result_str = "그냥 못하시는 거 같아요... \n롤을 접으시는게 다른 사람에 대한 배려가 아닐지.."; break;
            case 1: result_str = "서포터 유저로 활약하시면 승률이 높아 지시겠네요! \n 매우 안정적인 도구(서포터)입니다!"; break;
            case 2: result_str = "킬을 먹어서 캐리하시는 성향 이시군요!\n 안정적인 라이너 성향 입니다!"; break;
            case 3: result_str = "공격적인 OR 무지성 도구(서포터) 입니다 .. \n 공격성을 줄이실 필요가 있어요.."; break;
            case 4: result_str = "무지성 라이너의 대표적 성향이네요 \n 안정적인 플레이를 추천드립니다!"; break;
            case 5: result_str = "킬 관여율이 매우 높은 성향이군요! \n 데스 줄이는 것을 신경 쓰시면 승률이 높아질거 같아요!"; break;
            case 6: result_str = "롤을 정말 잘하시는 군요 ! \n 팀원들에게 꼭 오더를 내려서 팀을 승리로 이끄세요!"; break;
            default: break;
        }
        model.addAttribute("result_str", result_str);
        model.addAttribute("userName", userName);
        model.addAttribute("result1", data_result[0]);
        model.addAttribute("result2", data_result[1]);
        model.addAttribute("result3", data_result[2]);
        model.addAttribute("result4", data_result[3]);
        model.addAttribute("result5", data_result[4]);
        model.addAttribute("result6", data_result[5]);
        model.addAttribute("result7", data_result[6]);      //mustache파일에 바인딩하기위한 어트리뷰트 추가


        return "result";
    }




}
