package com.elis.DepEat.backend;

import android.util.Patterns;

public class Utils {

    private static boolean emailCheck;
    private static boolean passwordCheck;
    private static boolean confirmPwdCheck;
    private static boolean phoneNumberCheck;

    public static boolean isEmailCheck() {
        return emailCheck;
    }

    public static void setEmailCheck(boolean emailCheck) {
        Utils.emailCheck = emailCheck;
    }

    public static boolean isPasswordCheck() {
        return passwordCheck;
    }

    public static void setPasswordCheck(boolean passwordCheck) {
        Utils.passwordCheck = passwordCheck;
    }

    public static boolean isConfirmPwdCheck() {
        return confirmPwdCheck;
    }

    public static void setConfirmPwdCheck(boolean confirmPwdCheck) {
        Utils.confirmPwdCheck = confirmPwdCheck;
    }

    public static boolean isPhoneNumberCheck() {
        return phoneNumberCheck;
    }

    public static void setPhoneNumberCheck(boolean phoneNumberCheck) {
        Utils.phoneNumberCheck = phoneNumberCheck;
    }


    public static boolean checkEmailFormat(String email){
        setEmailCheck(Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean checkPasswordFormat(String password){
        if(password.length()>6) {
            setPasswordCheck(true);
            return true;
        }
        else {
            setPasswordCheck(false);
            return false;
        }
    }

    public static boolean checkPasswordConfirmation(String password, String confirmationPassword){
        if(password.equals(confirmationPassword)){
            setConfirmPwdCheck(true);
            return true;
        } else {
            setConfirmPwdCheck(false);
            return false;
        }
    }

    public static boolean checkPhoneNumberFormat(String phoneNumber){
        setPhoneNumberCheck(Patterns.PHONE.matcher(phoneNumber).matches());
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    public static boolean checkPhoneNumberLenght(String phoneNumber){
        if(phoneNumber.length()<14){
            setPhoneNumberCheck(false);
            return false;
        } else {
            setPhoneNumberCheck(true);
            return true;
        }
    }

    public static boolean checkButtonAvailability(){
        System.out.println("email: "+emailCheck+" password: "+passwordCheck+" passwordcontrol: "+confirmPwdCheck+" phone number: "+phoneNumberCheck);
        if(emailCheck && passwordCheck && confirmPwdCheck && phoneNumberCheck) return true;
        else return false;
    }
}
