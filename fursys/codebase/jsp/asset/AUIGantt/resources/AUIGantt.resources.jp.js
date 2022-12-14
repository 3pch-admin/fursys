/**
 * AUIGantt 에서 사용되는 메세지 및 리소스들을 정의합니다.
 */
AUIGanttMessages = {
		
		// 경고 메세지
		alerts  : {
			"e1" : "前処理された関係が正しくありません.（例：1,1FS、1FS + 3D）",
			"e2" : "タスク自体の先行操作を作成することはできません.",
			"e3" : "サマリー・タスクは、サブタスクの1つに接続できません.",
			"e4" : "同じフォローアップタスクの2つの先行操作を接続できませんでした.",
			"e5" : "タスクを作成しなかった前任者を接続することはできません.",
			"e6" : "このタスクは既に相互に接続されています.",
			"e7" : "先行タスクを終了する前に、現在のタスクが開始されます.\r\n2つのタスクの接続を維持できない.\r\n接続を削除してタスクを移動しますか？",
			"e8" : "プロジェクトの開始日より前に日付が設定されています.\r\nその日までにプロジェクトの開始日を変更しますか?"
		},
		
		
		// 날짜 포맷 ddd, dddd 에 해당되는 포맷 스트링 지정
		dayNames: [
			"日", "月", "火", "水", "木", "金", "土",
			"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
		],
		
		// 날짜 포맷 mmm, mmmm 에 해당되는 포맷 스트링 지정 
		monthNames: [
			"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月",
			"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
		],
		
		// 메이저 타임 축 날짜 포맷.
		majorDateFormatString : {
				"decade" : "yyyy ~ yyyy",
				"year" : "yyyy",
				"half" : ["H1", "H2"],
				"quarter" : ["Qtr 1", "Qtr 2", "Qtr 3", "Qtr 4"],
				"month" : "yyyy年 m月",
				"month2" :  "yyyy年 m月",
				"week" : "yyyy年 m月 d日",
				"week2" : "yyyy年 m月 d日",
				"day" : "yyyy年 m月 d日 (ddd)",
				"day2" : "yyyy年 m月 d日 (ddd) hTT",
				"hour" : "HH:MM"
		},
		
		// 마이너 타임 축 날짜 포맷.
		minorDateFormatString : {
				"year" : "yy",
				"half" : ["H1", "H2"],
				"quarter" : ["Q1", "Q2", "Q3", "Q4"],
				"month" : "m",
				"month2" : "m月",
				"week" : "d",
				"week2" : "mm-dd",
				"day" : "d",
				"day2" : "d(ddd)",
				"hour" : "h",
				"30minutes" : "H:MM",
				"20minutes" : "M",
				"10minutes" : "M"
		},
		
		// 다이어그램 차트 툴팁 스트링
		tooltipString : {
			start : "開始日",
			end : "終了日",
			days : "日",
			period : "期間",
			progress : "進捗"
		},
		
		todayLineText : "Today",
		projectStartLineText : "Project Start",
		projectEndLineText : "Project End",
		
		gridMessages : {
			noDataMessage: "表示するデータがありません",
			filterNoValueText: "( Empty Value )",
			filterCheckAllText: "( Check All )",
			filterClearText: "Clear Filter",
			filterSearchCheckAllText: "( Check All Found )",
			filterSearchPlaceholder : "Search", // 필터 검색 플레이홀더 텍스트
			filterOkText: "Okay",
			filterCancelText: "Cancel",
			filterItemMoreMessage: "Too many items...Search words",
			filterNumberOperatorList: ["Equal(=)", "Greater than(>)", "Greater than or Equal(>=)", "Less than(<)", "Less than or Equal(<=)", "Not Equal(!=)"],
			thousandSeparator : ",",
			rowNumHeaderText: "No.",
			remoterPlaceholder: "Input your text",
			calendar : {
				titles : ["日", "月", "火", "水", "木", "金", "土"],
				formatYearString : "yyyy年",
				monthTitleString : "m月",
				formatMonthString : "yyyy年 mm月",
				todayText : " 今日 ",
				uncheckDateText : "選択の解除"
			}
		},
		
		// 다이어그램 스타日
		chartStyles : {
				headerBgColors : ["#F8F8F8", "#EEEEEE"], // 헤더 백그라운드
				headerLineColor : "#BDBDBD", // 헤더 상, 하 구분선 및 헤더 하단 선 색상
				headerMajorFont : "bold 12px Arial", // 헤더 폰트 스타日
				headerMinorFont : "normal 12px Arial", // 헤더 폰트 스타日
				headerMajorColor : "#000000", // 헤더 기본 폰트 색상
				headerMinorColor : "#000000", // 헤더 기본 폰트 색상
				headerSatColor : "#0000FF", // 토요日 색상
				headerSunColor : "#FF0000", // 日요日 색상
				headerUserColor : "#FF0000", // 사용자 정의 공휴日 색상
				rowBgColors : ["#FAFAFA", "#FFFFFF"], // 행 배경 색
				verticalLineColor : "#DCDCDC", // 수직 그리드 라인 컬러
				horizontalLineColor : "#DCDCDC", // 수평 그리드 라인 컬러
				holidayBgColor : "rgba(200,200,200, 0.25)", // 공휴日 배경색
				
				// 브랜치
				branchBgColor : "#555555",
				branchProgressColor : "#008299",
				
				//리프
				taskBgColor : "#B2CCFF",
				taskProgressColor : "#6A84B7",
				
				// 베이스라인
				branchBaselineColor : "#F29661",
				taskBaselineColor : "#F29661",
				milestoneBaselineColor : "#F29661",
				
				// 마일스톤
				milestoneBgColor : "#0B7903",
				
				// 선행관계 연결선
				connectorColor : "#4374D9",
				
				// 프로젝트 시작, 종료, 오늘 핀업 선 컬러
				projectStartLineColor : "#008299",
				projectEndLineColor : "#008299",
				todayLineColor : "#FF5E00",
				projectStartLineWidth : 1,
				projectEndLineWidth : 1,
				todayLineWidth : 1,
				
				// 바의 좌, 우 텍스트 출력 폰트 지정
				barLabelFont : "bold 12px Arial",
				barLabelFontColor : "#222222",
				barLabelLeftFontColor : "#222222",
				textPadding : 10,
				textTopPadding : 0
		}
};

//기본 칼럼 정보
if(typeof AUIGantt != "undefined") {
	AUIGantt.defaultColumnInfo = {
			
		// 작업 정보 필드
		"index" : { 	
			dataField : "index",
			headerText : '<img style="margin-top:6px" src="./assets/info-icon_24_24.png">',
			editable : false,
			sortable : false,
			width : 60,
			renderer : { // HTML 템플릿 렌더러 사용
				type : "TemplateRenderer"
			},
			applyUserStyle2Xlsx : false, // 사용자가 지정한 스타日 엑셀 저장 시 무시
			style : "aui-gantt-default-left-style",
			labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
				var str = '<div>';
				var tip;
				
				if(item.editable === false) {
					tip = "このタスクは、修正を防ぐためにロックされています";
					str += '<img src="./assets/lock_icon.png" height="16" width="16" style="vertical-align:middle" alt="' + tip +'" title="' + tip +'">';
				}
				
				if(Math.floor(Number(item.progress)) == 100) {
					tip =  "このタスクは" + AUIGantt.formatDate(item.end, "yyyy-mm-dd (ddd)") + "に完了しました"; 
					str += '<img src="./assets/green_check.png" height="16" width="16" style="vertical-align:middle" alt="' + tip +'" title="' + tip +'">';
				} 
				else if(item.fixedDate != "undefined") {
					if(item.fixedDateName == "afterStart") {
						tip = "この作業は" + AUIGantt.formatDate(item.fixedDate, "yyyy-mm-dd (ddd)") + "以降に限定されます";
						str += '<img src="./assets/start_restrict_calendar.png" height="16" style="vertical-align:middle" width="16" alt="' + tip +'" title="' + tip +'">';
					} else if(item.fixedDateName == "afterEnd") {
						tip = "この行動は" + AUIGantt.formatDate(item.fixedDate, "yyyy-mm-dd (ddd)") + "以降に完了するように制限されています";
						str += '<img src="./assets/end_restrict_calendar.png" height="16" width="16" style="vertical-align:middle" alt="' + tip +'" title="' + tip +'">';
					}
				}
				
				if(item.hasMemo) {
					tip = "このタスクには問題があります.";
					str += '<img src="./assets/comment-rect-icon.png" height="16" width="16" style="vertical-align:middle;cursor:pointer;"  alt="' + tip +'" title="' + tip +'">';
				}
				
				str + "</div>";
				return str;
			} // end of labelFunction
		},
		
		// 작업 이름 필드
		"name" : {
			dataField : "name",
			headerText : "タスク名",
			width: 280,
			showHeaderMenu:true,
			style : "aui-gantt-default-left-style"
		},
		
		// 기간 필드
		"period" : {
			dataField : "period",
			headerText : "期間",
			width : 50,
			editRenderer : {
				type : "InputEditRenderer",
				
				// 에디팅 유효성 검사
				validator : function(oldValue, newValue, rowItem, dataField, isClipboard) {
					var isValid = true;
					var numValue = Number(newValue);
					if(isNaN(numValue)) {
						isValid = false;
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid, "message"  : "数字だけを入力してください." };
				}
			},
			
			labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
				if(typeof item.start == "undefined" && typeof item.end == "undefined") {
					return "";
				}
				
				var postfix = " 日?";
				if(item.isFixedPeriod) {
					postfix = " 日";
				}
				
				return Math.ceil(value)+ postfix;
			} // end of labelFunction
		},
		
		// 시작 날짜 필드
		"start" : {
			dataField : "start",
			headerText : "開始日",
			width : 120,
			dataType : "date",
			formatString : "yyyy-mm-dd (ddd)",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "yyyy-mm-dd",
				showExtraDays : true, 
				showTodayBtn : true,
				onlyCalendar : false,
				showEditorBtnOver : true,
				validator : function(oldValue, newValue, rowItem) { // 에디팅 유효성 검사
					var isValid = true;
					var oldDate = new Date(oldValue);
					oldDate = isNaN(Date.parse(oldDate)) ? oldValue : AUIGantt.formatDate(oldDate, "yyyy-mm-dd");
					if(isNaN(Number(newValue)) ) {
						newValue = String(newValue).replace(/-/g, "/");
						if(isNaN(Date.parse(newValue))) { // 즉, JS 가 Date 로 파싱할 수 있는 형식인지 조사
							isValid = false;
						} else {
							isValid = true;
						}
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid,  "message"  : "日付形式はサポートしていません." };
				}
			}
		},
		
		// 종료 날짜 필드
		"end" : {
			dataField : "end",
			headerText : "終了日",
			width : 120,
			dataType : "date",
			formatString : "yyyy-mm-dd (ddd)",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "yyyy-mm-dd",
				showExtraDays : true, 
				showTodayBtn : true,
				onlyCalendar : false,
				showEditorBtnOver : true,
				validator : function(oldValue, newValue, rowItem) { // 에디팅 유효성 검사
					var isValid = true;
					var oldDate = new Date(oldValue);
					oldDate = isNaN(Date.parse(oldDate)) ? oldValue : AUIGantt.formatDate(oldDate, "yyyy-mm-dd");
					if(isNaN(Number(newValue)) ) {
						newValue = String(newValue).replace(/-/g, "/");
						if(isNaN(Date.parse(newValue))) { // 즉, JS 가 Date 로 파싱할 수 있는 형식인지 조사
							isValid = false;
						} else {
							isValid = true;
						}
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid,  "message"  : "日付形式はサポートしていません." };
				}
			}
		}, 
		
		// 실제 시작 날짜 필드 (baseline )
		"startActual" : {
			dataField : "startActual",
			headerText : "実際開始日",
			width : 120,
			dataType : "date",
			formatString : "yyyy-mm-dd (ddd)",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "yyyy-mm-dd",
				showExtraDays : true, 
				showTodayBtn : true,
				onlyCalendar : false,
				showEditorBtnOver : true,
				validator : function(oldValue, newValue, rowItem) { // 에디팅 유효성 검사
					var isValid = true;
					var oldDate = new Date(oldValue);
					oldDate = isNaN(Date.parse(oldDate)) ? oldValue : AUIGantt.formatDate(oldDate, "yyyy-mm-dd");
					if(isNaN(Number(newValue)) ) {
						newValue = String(newValue).replace(/-/g, "/");
						if(isNaN(Date.parse(newValue))) { // 즉, JS 가 Date 로 파싱할 수 있는 형식인지 조사
							isValid = false;
						} else {
							isValid = true;
						}
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid,  "message"  : "日付形式はサポートしていません." };
				}
			}
		},
		
		// 실제 종료 날짜 필드 (baseline )
		"endActual" : {
			dataField : "endActual",
			headerText : "実際終了日",
			width : 120,
			dataType : "date",
			formatString : "yyyy-mm-dd (ddd)",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "yyyy-mm-dd",
				showExtraDays : true, 
				showTodayBtn : true,
				onlyCalendar : false,
				showEditorBtnOver : true,
				validator : function(oldValue, newValue, rowItem) { // 에디팅 유효성 검사
					var isValid = true;
					var oldDate = new Date(oldValue);
					oldDate = isNaN(Date.parse(oldDate)) ? oldValue : AUIGantt.formatDate(oldDate, "yyyy-mm-dd");
					if(isNaN(Number(newValue)) ) {
						newValue = String(newValue).replace(/-/g, "/");
						if(isNaN(Date.parse(newValue))) { // 즉, JS 가 Date 로 파싱할 수 있는 형식인지 조사
							isValid = false;
						} else {
							isValid = true;
						}
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid,  "message"  : "日付形式はサポートしていません." };
				}
			}
		}, 
		
		// 선행 작업 필드
		"predecessor" : {
			dataField : "predecessor",
			headerText : "先行",
			width:80
		},
		
		// 자원 이름 필드
		"resource" : {
			dataField : "resource",
			headerText : "リソース名",
			width:80,
			filter : {
				showIcon :true
			},
			editRenderer : {
				type : "ComboBoxRenderer",
				autoCompleteMode : true, // 자동완성 모드 설정
				showEditorBtnOver : true, // 마우스 오버 시 에디터버턴 보이기
				historyMode : true, // 히스토리 모드 사용
				list : [] // 현재 칼럼에 있는 값만을 리스트로 사용하기 위해 list 는 빈 배열.
			}
		},
		
		// 완료율 필드
		"progress" : {
			dataField : "progress",
			headerText : "進捗",
			width:60,
			xlsxTextConversion : true,
			postfix : "%",
			renderer : { // 진행 상태를 바 차트로 표현.
				type : "BarRenderer",
				min : 0,
				max : 100
			},
			editRenderer : {
				type : "InputEditRenderer",
				onlyNumeric : true,
				allowPoint : true,
				
				// 에디팅 유효성 검사
				validator : function(oldValue, newValue, rowItem, dataField, isClipboard) {
					var isValid = true;
					var numValue = Number(newValue);
					if(isNaN(numValue) ||  numValue < 0 || numValue > 100) {
						isValid = false;
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid, "message"  : "数字(0~100)だけを入力してください." };
				}
			}
		}
	}; // end of defaultColumnInfo
};