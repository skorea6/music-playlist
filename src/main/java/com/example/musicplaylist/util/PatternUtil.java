package com.example.musicplaylist.util;

public class PatternUtil {
    public static final String USER_ID_PATTERN = "^(?!kakao_|google_|naver_)[a-z0-9_]{4,20}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\\$%^&*()_+\\-=\\[\\]{};':\",./<>?|\\\\`~])[a-zA-Z0-9!@#\\$%^&*()_+\\-=\\[\\]{};':\",./<>?|\\\\`~]{8,20}$";
    public static final String NICK_PATTERN = "^(?!kakao_|google_|naver_)[a-zA-Z0-9가-힣!@#$%^&*()-+=\\[\\]{};':\",./<>?|\\\\ㄱ-ㅎㅏ-ㅣ_ ]{2,20}$";
    public static final String NAME_PATTERN = "^[a-zA-Z가-힣 ]{1,20}$";

    public static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String DATETIME_PATTERN = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";

    public static final String SORT_DIRECTION_PATTERN = "^(desc|asc)$";

    public static final String USER_ID_MESSAGE = "영어 소문자, 숫자, 언더바만 가능하며, 4~20자리로 입력해주세요.";
    public static final String PASSWORD_MESSAGE = "영어, 숫자, 특정 특수문자를 포함한 8~20자리로 입력해주세요.";
    public static final String NICK_MESSAGE = "영어, 한글, 숫자, 특정 특수문자만 가능하며, 2~20자리로 입력해주세요.";
    public static final String NAME_MESSAGE = "영문, 한글만 가능하며, 1~20자리로 입력해주세요.";
    public static final String DATE_MESSAGE = "날짜를 yyyy-MM-dd 형식으로 입력해주세요.";
    public static final String DATETIME_MESSAGE = "날짜와 시간을 yyyy-MM-dd HH:mm:ss 형식으로 입력해주세요.";
    public static final String SORT_DIRECTION_MESSAGE = "desc 혹은 asc 만 가능합니다.";

}
