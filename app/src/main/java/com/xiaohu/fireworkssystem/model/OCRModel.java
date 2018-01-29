package com.xiaohu.fireworkssystem.model;

/**
 * Created by Administrator on 2018/1/22.
 */

public class OCRModel {

    /**
     * Success : true
     * Message : null
     * Data : {"IdentyNumber":"131182199302281014","PersonName":"柳伟杰","PersonNation":"汉","PersonAddress":"河北省深州市辰时逞日1蒙庄村132号","PersonFaceImage":"/9j/4AAQ"}
     */

    private boolean Success;
    private Object Message;
    private OCR Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object Message) {
        this.Message = Message;
    }

    public OCR getData() {
        return Data;
    }

    public void setData(OCR Data) {
        this.Data = Data;
    }

    public static class OCR {
        /**
         * IdentyNumber : 131182199302281014
         * PersonName : 柳伟杰
         * PersonNation : 汉
         * PersonAddress : 河北省深州市辰时逞日1蒙庄村132号
         * PersonFaceImage : /9j/4AAQ
         */

        private String IdentyNumber;
        private String PersonName;
        private String PersonNation;
        private String PersonAddress;
        private String PersonFaceImage;

        public String getIdentyNumber() {
            return IdentyNumber;
        }

        public void setIdentyNumber(String IdentyNumber) {
            this.IdentyNumber = IdentyNumber;
        }

        public String getPersonName() {
            return PersonName;
        }

        public void setPersonName(String PersonName) {
            this.PersonName = PersonName;
        }

        public String getPersonNation() {
            return PersonNation;
        }

        public void setPersonNation(String PersonNation) {
            this.PersonNation = PersonNation;
        }

        public String getPersonAddress() {
            return PersonAddress;
        }

        public void setPersonAddress(String PersonAddress) {
            this.PersonAddress = PersonAddress;
        }

        public String getPersonFaceImage() {
            return PersonFaceImage;
        }

        public void setPersonFaceImage(String PersonFaceImage) {
            this.PersonFaceImage = PersonFaceImage;
        }
    }
}
