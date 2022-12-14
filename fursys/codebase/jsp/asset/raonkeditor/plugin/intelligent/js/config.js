// 인텔리전트 툴바 사용유무 및 사용 아이콘
G_KPlugin["intelligent"].intelligentMenu = {
    Use: '1',
    selectArea: {
        tool1: 'formatting,separator,font_family,separator,font_size,separator,line_height',
        tool2: 'bold,italic,underline,strike_through,separator,hyperlink_create,separator,list_number,list_bullets,align_group,separator,font_color,font_bg_color'
    },
    selectedMultiCell: {
        tool1: 'font_family,separator,font_size,separator,font_color,font_bg_color',
        tool2: 'bold,italic,underline,strike_through,separator,table_property,cell_property,table_group'
    },
    cellSelectText: {
        tool1: 'formatting,separator,font_family,separator,font_size,separator,line_height',
        tool2: 'bold,italic,underline,strike_through,separator,hyperlink_create,separator,list_number,list_bullets,align_group,separator,font_color,font_bg_color'
    },
    focusImage: {
        tool1: 'cut,copy,hyperlink_create,remove_hyperlink,image_property'
    }
};

// 인텔리전트 테이블 문단 삽입 사용유무
G_KPlugin["intelligent"].InsertParagraph = '1';