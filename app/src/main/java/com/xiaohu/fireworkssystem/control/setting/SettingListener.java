package com.xiaohu.fireworkssystem.control.setting;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.CompanyModel;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.UserModel;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.view.SysCodeModel;
import com.xiaohu.fireworkssystem.model.view.ViewStockModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.BusinessLicenseNumberValidity;
import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;
import static com.xiaohu.fireworkssystem.config.MyKeys.CompanyName;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;
import static com.xiaohu.fireworkssystem.config.MyKeys.LicenseNumberValidity;
import static com.xiaohu.fireworkssystem.config.MyKeys.UserID;

/**
 * Created by Administrator on 2016/9/5.
 */
public class SettingListener {
    SettingView view;
    Context myContext;
    OkHttpUtils okHttpUtils;
    String[] codes;
    String stringIP;
    Utils utils;

    public SettingListener(SettingView v, Context context) {
        this.view = v;
        myContext = context;
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));

    }

    public void getUserInfo(String name, String psw) {
        stringIP = utils.ReadString("IP");
        String str = "'{\"LoginName\":\"" + name + "\",\"PassWord\":\"" + psw + "\"}'";
        okHttpUtils.requestGetByAsyn(stringIP, "GetUser", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                ResultModel model = gson.fromJson(result, ResultModel.class);

                if (model.isSuccess()) {
                    UserModel user = gson.fromJson(model.getData(), UserModel.class);
                    utils.WriteString(UserID, user.getUserID());
                    getCompanyInfo();


                } else {
                    view.getUserInfo(false, model.getMessage());
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.getUserInfo(false, "网络错误");
            }
        });
    }

    //获取设备信息
    public void getTerminal(String sam) {
        if (null == sam || "".equals(sam)) {
            view.getSamCode(false, "设备验证失败", "");
        } else {
            stringIP = utils.ReadString("IP");
            String companyid = utils.ReadString(COMPANYID);
            final String finalSam = sam;
            okHttpUtils.requestGetByAsyn(stringIP, "SetTerminal", "'{\"TerminalCode\":\"" + sam + "\"," + "\"TerminalType\":\"02\"," + "\"CompanyId\":\"" + companyid + "\"" + "}'", new ReqCallBack() {
                @Override
                public void onReqSuccess(String result) {
                    //System.out.println("searchwarehouse:" + result);
                    Gson gson = new Gson();
                    ResultModel model = gson.fromJson(result, ResultModel.class);
                    if (model.isSuccess()) {
                        view.getSamCode(true, "", finalSam);
                    } else {
                        view.getSamCode(false, "设备验证失败", "");
                    }
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    //System.out.println("searchwarehouse:544555" );
                    view.getSamCode(false, "网络错误", "");
                }
            });
        }

    }

    public void getCompanyInfo() {
        stringIP = utils.ReadString("IP");
        String str = "'{\"CompanyId\":\"" + utils.ReadString(COMPANYID) + "\"}'";
        okHttpUtils.requestGetByAsyn(stringIP, "GetCompany", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                ResultModel model = gson.fromJson(result, ResultModel.class);
                if (model.isSuccess()) {
                    CompanyModel user = gson.fromJson(model.getData(), CompanyModel.class);
                    //utils.WriteString(PARENTCOMPANYID, user.getParentCompanyID());
                    utils.WriteString(CompanyName, user.getCompanyName());
                    utils.WriteString(LicenseNumberValidity, user.getLicenseNumberValidity());
                    utils.WriteString(BusinessLicenseNumberValidity, user.getBusinessLicenseNumberValidity());
                    view.getUserInfo(true, "");
                } else {
                    view.getUserInfo(false, "登录失败");
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.getUserInfo(false, "网络错误");
            }
        });
    }

    public void getCode() {
        codes = myContext.getResources().getStringArray(R.array.CodeNameList);
        getCodeByName(0);
    }

    public void getRegCode() {
        codes = myContext.getResources().getStringArray(R.array.RegCodeNameList);
        getCodeByName(0);
    }

    public void getCode2(List<SysCodeModel> list) {
        codes = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            codes[i] = list.get(i).getName();
        }
        getCodeByName(0);
    }

    public void getCodeByName(final int index) {
        stringIP = utils.ReadString("IP");
        String str = "'{\"Table\":\"" + codes[index] + "\"}'";
        System.out.println("code stringip:" + stringIP);
        okHttpUtils.requestGetByAsyn(stringIP, "GetCode", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        System.out.println("data" + model.getData());
                        if (model.isSuccess()) {
                            TypeToken<List<CodeModel>> list = new TypeToken<List<CodeModel>>() {
                            };
                            List<CodeModel> codeModel = new ArrayList<CodeModel>();
                            codeModel = gson.fromJson(model.getData(), list.getType());
                            CodeDao dao = new CodeDao(myContext);
                            for (int i = 0; i < codeModel.size(); i++) {
                                CodeModel c = codeModel.get(i);
                                c.setCodeName(codes[index]);
                                dao.add(c);
                            }
                            int inex = index + 1;
                            if (inex == codes.length) {
                                if (codes.length == 2) {
                                    view.getCodeResult(true, "注册下载成功");
                                } else {
                                    view.getCodeResult(true, "下载成功");
                                }
                            } else {
                                getCodeByName(inex);
                            }
                        } else {
                            view.getCodeResult(false, "下载失败");
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
                        System.out.println("code" + errorMsg);
                        view.getCodeResult(false, "字典下载失败");
                    }
                }
        );
    }

    public void RefushCode() {
        stringIP = utils.ReadString("IP");
        codes = myContext.getResources().getStringArray(R.array.AllCodeNameList);
        okHttpUtils.requestGetByAsyn(stringIP, "GetModified", "", new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if (model.isSuccess()) {
                            System.out.println("data" + model.getData());
                            TypeToken<List<SysCodeModel>> list = new TypeToken<List<SysCodeModel>>() {
                            };
                            List<SysCodeModel> codeModel = new ArrayList<SysCodeModel>();
                            codeModel = gson.fromJson(model.getData(), list.getType());
                            if (codeModel != null) {
                                List<SysCodeModel> sysList = new ArrayList<SysCodeModel>();
                                if (codeModel.size() > 0) {
                                    for (int i = 0; i < codeModel.size(); i++) {
                                        for (int j = 0; j < codes.length; j++) {
                                            if (codes[j].equals(codeModel.get(i).getName())) {
                                                sysList.add(codeModel.get(i));
                                                CodeDao dao = new CodeDao(myContext);
                                                dao.deleteall(dao.QueryByName("CodeName", codeModel.get(i).getName()));
                                            }
                                        }
                                    }
                                    if (sysList.size() > 0) {
                                        getCode2(sysList);
                                    } else {
                                        view.getCodeResult(true, "已经是最新字典！");
                                    }
                                } else {
                                    view.getCodeResult(true, "已经是最新字典！");
                                }
                            } else {
                                view.getCodeResult(true, "已经是最新字典！");
                            }
                        } else {
                            view.getCodeResult(false, "更新失败");
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
                        System.out.println("3e" + errorMsg);
                        view.getCodeResult(false, "网络错误");
                    }
                }
        );
    }
}
