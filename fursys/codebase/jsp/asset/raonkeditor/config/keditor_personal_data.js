/*
Copyright (c) 2013, Raonwiz Technology Inc. All rights reserved.
 - 개인 정보 추출 config
*/
var KEDITOR_PERSONAL_DATA_CONFIG = {
    /*
    작성예시)   아래사항은 예약키워드입니다.

    항목명: {
        regexp: new RegExp('검색하는 정규식'),
        ignore: new RegExp('검색된 데이터에서 제외할 정규식')
    }

    */

    email: {
        regexp: new RegExp('[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})', 'ig')
    },
    phone: {
        regexp: new RegExp('((\\+\\d{1,3}(-| |\\.)?\\(?\\d\\)?(-| |\\.)?\\d{1,5})|(\\d{2,6}\\)?))(-| |\\.)?(\\d{3,4})(-| |\\.)?(\\d{4})', 'g'),
        ignore: new RegExp('(:?^\\d{6}-\\d{7})')
    },
    rrn: {
        regexp: new RegExp('(\\d{2}(:?1[0-2]|0[1-9])(:?0[1-9]|[1-2]\\d|3[0-1])-?[1-4]\\d{6})', 'gm')
    },
    passport: {
        regexp: new RegExp('([M|S|R|O|D|m|s|r|o|d][0-9]{8})', 'g')
    },
    driver: {
        regexp: new RegExp('(\\d{2}[ -:.])(\\d{6}[ -:.])(\\d{2})', 'g')
    },
    foreigner: {
        regexp: new RegExp('([01][0-9]{5}[ ~-]?[1-8][0-9]{6}|[2-9][0-9]{5}[ ~-]?[1256][0-9]{6})', 'g')
    }
}