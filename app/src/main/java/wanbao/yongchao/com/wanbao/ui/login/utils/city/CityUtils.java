package wanbao.yongchao.com.wanbao.ui.login.utils.city;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CityUtils {

    public static String convertStraemToString(InputStream inputStream){

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try {
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return  stringBuilder.toString();
    }

    public static ArrayList<ProvinceModel> getProvince(ArrayList<CitiesBean> list){
        ArrayList<ProvinceModel> provinceModels=new ArrayList<>();
        ProvinceModel p=new ProvinceModel();
        ArrayList<CitiesBean> list1=new ArrayList<>();
        for (int i=0;i<=list.size()-1;i++){
            if (i==0){
                p.setKey(list.get(i).getAbb().substring(0,1));
                list1.add(list.get(i));
            }else {
                if (list.get(i-1).getAbb().substring(0,1).equals(list.get(i).getAbb().substring(0,1))){
                    list1.add(list.get(i));
                }else {
                    p.setList(list1);
                    provinceModels.add(p);
//                    list1.clear();
                    list1=new ArrayList<>();
                    p=new ProvinceModel();
                    list1.add(list.get(i));
                    p.setKey(list.get(i).getAbb().substring(0,1));
                }
            }
        }
        return provinceModels;
    }

    public static ArrayList<CityModel> getCity(ArrayList<CitiesBean.CityBean> list){
        ArrayList<CityModel> cityModels=new ArrayList<>();
        CityModel p=new CityModel();
        ArrayList<CitiesBean.CityBean> list1=new ArrayList<>();
        for (int i=0;i<=list.size()-1;i++){
            if (i==0){
                p.setKey(list.get(i).getAbb().substring(0,1));
                list1.add(list.get(i));
            }else {
                if (list.get(i-1).getAbb().substring(0,1).equals(list.get(i).getAbb().substring(0,1))){
                    list1.add(list.get(i));
                }else {
                    p.setList(list1);
                    cityModels.add(p);
//                    list1.clear();
                    list1=new ArrayList<>();
                    p=new CityModel();
                    list1.add(list.get(i));
                    p.setKey(list.get(i).getAbb().substring(0,1));
                }
            }
        }
        return cityModels;
    }


    public static ArrayList<AreaModel> getArea(ArrayList<CitiesBean.CityBean.AreaBean> list){
        ArrayList<AreaModel> areaModels=new ArrayList<>();
        AreaModel p=new AreaModel();
        ArrayList<CitiesBean.CityBean.AreaBean> list1=new ArrayList<>();
        for (int i=0;i<=list.size()-1;i++){
            if (i==0){
                p.setKey(list.get(i).getAbb().substring(0,1));
                list1.add(list.get(i));
            }else {
                if (list.get(i-1).getAbb().substring(0,1).equals(list.get(i).getAbb().substring(0,1))){
                    list1.add(list.get(i));
                }else {
                    p.setList(list1);
                    areaModels.add(p);
//                    list1.clear();
                    list1=new ArrayList<>();
                    p=new AreaModel();
                    list1.add(list.get(i));
                    p.setKey(list.get(i).getAbb().substring(0,1));
                }
            }
        }
        return areaModels;
    }
}
