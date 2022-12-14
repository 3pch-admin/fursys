/**
 * AUIGantt 에서 사용되는 메세지 및 리소스들을 정의합니다.
 */
AUIGanttMessages = {
		
		// 경고 메세지
		alerts  : {
			"e1" : "Predecessor relationships represented are incorrect. (ex: 1, 1FS, 1FS+3D)",
			"e2" : "You can not create a predecessor of the task itself.",
			"e3" : "A summary tasks can not connect to one of the subtasks.",
			"e4" : "Two predecessors over the same follow-up task could not be connected.",
			"e5" : "You can not connect the predecessors did not create tasks.",
			"e6" : "This task has already been connected to each other.",
			"e7" : "Before finishing predecessors moved to the current task starts.\r\nCan't maintain the connection of the two tasks.\r\nDo you want to delete the connection and move the task?",
			"e8" : "The date is set before the entire project start date.\r\nDo you want to change the project start date by that date?"
		},
		
		// 날짜 포맷 ddd, dddd 에 해당되는 포맷 스트링 지정
		dayNames: [
			"S", "M", "T", "W", "T", "F", "S",
			"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
		],
		
		// 날짜 포맷 mmm, mmmm 에 해당되는 포맷 스트링 지정 
		monthNames: [
			"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
			"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
		],
		
		// 메이저 타임 축 날짜 포맷.
		majorDateFormatString : {
				"decade" : "yyyy ~ yyyy",
				"year" : "yyyy",
				"half" : ["H1", "H2"],
				"quarter" : ["Qtr 1", "Qtr 2", "Qtr 3", "Qtr 4"],
				"month" : "mmmm, yyyy",
				"month2" :  "mmm, yyyy",
				"week" : "dddd mmmm d, yyyy",
				"week2" : "dddd mmm d, yyyy",
				"day" : "dddd mm/dd/yyyy",
				"day2" : "dddd hTT / mmm d, yyyy",
				"hour" : "HH:MM"
		},
		
		// 마이너 타임 축 날짜 포맷.
		minorDateFormatString : {
				"year" : "yy",
				"half" : ["H1", "H2"],
				"quarter" : ["Q1", "Q2", "Q3", "Q4"],
				"month" : "m",
				"month2" : "mmm",
				"week" : "d",
				"week2" : "mmm d",
				"day" : "d",
				"day2" : "mmm d",
				"hour" : "H",
				"30minutes" : "H:MM",
				"20minutes" : "M",
				"10minutes" : "M"
		},
		
		// 다이어그램 차트 툴팁 스트링
		tooltipString : {
			start : "Start",
			end : "End",
			days : "day(s)",
			period : "Period",
			progress : "Complete"
		},
		
		todayLineText : "Today",
		projectStartLineText : "Project Start",
		projectEndLineText : "Project End",
		
		gridMessages : {
			noDataMessage: "No Data to display",
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
				titles : ["S", "M", "T", "W", "T", "F", "S"],
				formatYearString : "yyyy",
				monthTitleString : "mmm",
				formatMonthString : "mmm, yyyy",
				todayText : "Today",
				uncheckDateText : "Delete the date"
			}
		},
		
		
		// 다이어그램 스타일
		chartStyles : {
				headerBgColors : ["#F8F8F8", "#EEEEEE"], // 헤더 백그라운드
				headerLineColor : "#BDBDBD", // 헤더 상, 하 구분선 및 헤더 하단 선 색상
				headerMajorFont : "bold 12px Arial", // 헤더 폰트 스타일
				headerMinorFont : "normal 12px Arial", // 헤더 폰트 스타일
				headerMajorColor : "#000000", // 헤더 기본 폰트 색상
				headerMinorColor : "#000000", // 헤더 기본 폰트 색상
				headerSatColor : "#0000FF", // 토요일 색상
				headerSunColor : "#FF0000", // 일요일 색상
				headerUserColor : "#FF0000", // 사용자 정의 공휴일 색상
				rowBgColors : ["#FAFAFA", "#FFFFFF"], // 행 배경 색
				verticalLineColor : "#DCDCDC", // 수직 그리드 라인 컬러
				horizontalLineColor : "#DCDCDC", // 수평 그리드 라인 컬러
				holidayBgColor : "rgba(200,200,200, 0.25)", // 공휴일 배경색
				
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
			applyUserStyle2Xlsx : false, // 사용자가 지정한 스타일 엑셀 저장 시 무시
			style : "aui-gantt-default-left-style",
			labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
				var str = '<div>';
				var tip;
				
				if(item.editable === false) {
					tip = "This task is locked to prevent modification.";
					str += '<img src="./assets/lock_icon.png" height="16" width="16" style="vertical-align:middle" alt="' + tip +'" title="' + tip +'">';
				}
				
				if(Math.floor(Number(item.progress)) == 100) {
					tip = "This task was completed on " + AUIGantt.formatDate(item.end, "dddd mmmm d, yyyy");
					str += '<img src="./assets/green_check.png" height="16" width="16" style="vertical-align:middle" alt="' + tip +'" title="' + tip +'">';
				} 
				else if(item.fixedDate != "undefined") {
					if(item.fixedDateName == "afterStart") {
						tip = "This task is limited to begin after " + AUIGantt.formatDate(item.fixedDate, "dddd mmmm d, yyyy");
						str += '<img src="./assets/start_restrict_calendar.png" height="16" style="vertical-align:middle" width="16" alt="' + tip +'" title="' + tip +'">';
					} else if(item.fixedDateName == "afterEnd") {
						tip = "This action is limited to be completed after " + AUIGantt.formatDate(item.fixedDate, "dddd mmmm d, yyyy");
						str += '<img src="./assets/end_restrict_calendar.png" height="16" width="16" style="vertical-align:middle" alt="' + tip +'" title="' + tip +'">';
					}
				}
				
				if(item.hasMemo) {
					tip = "This task has issues.";
					str += '<img src="./assets/comment-rect-icon.png" height="16" width="16" style="vertical-align:middle;cursor:pointer;"  alt="' + tip +'" title="' + tip +'">';
				}
				
				str + "</div>";
				return str;
			} // end of labelFunction
		},
		
		// 작업 이름 필드
		"name" : {
			dataField : "name",
			headerText : "Task Name",
			width: 280,
			showHeaderMenu:true,
			style : "aui-gantt-default-left-style"
		},
		
		// 기간 필드
		"period" : {
			dataField : "period",
			headerText : "Period",
			width : 80,
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
					return { "validate" : isValid, "message"  : "Please enter only numbers" };
				}
			},
			
			labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
				if(typeof item.start == "undefined" && typeof item.end == "undefined") {
					return "";
				}
				
				var postfix = " days?";
				if(item.isFixedPeriod) {
					postfix = " days";
				}
				
				return Math.ceil(value)+ postfix;
			} // end of labelFunction
		},
		
		// 시작 날짜 필드
		"start" : {
			dataField : "start",
			headerText : "Start",
			width:100,
			dataType : "date",
			formatString : "mm/dd/yy",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "mm/dd/yy",
				showExtraDays : true, 
				showTodayBtn : true,
				onlyCalendar : false,
				showEditorBtnOver : true,
				todayText : "Today",
				validator : function(oldValue, newValue, rowItem) { // 에디팅 유효성 검사
					var isValid = true;
					var oldDate = new Date(oldValue);
					oldDate = isNaN(Date.parse(oldDate)) ? oldValue : AUIGantt.formatDate(oldDate, "mm/dd/yy");
					if(isNaN(Number(newValue)) ) {
						newValue = String(newValue).replace(/-/g, "/");
						if(isNaN(Date.parse(newValue))) { // 즉, JS 가 Date 로 파싱할 수 있는 형식인지 조사
							isValid = false;
						} else {
							isValid = true;
						}
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid, "message"  : "It doesn't support the date format" };
				}
			}
		},
		
		// 종료 날짜 필드
		"end" : {
			dataField : "end",
			headerText : "End",
			width : 100,
			dataType : "date",
			formatString : "mm/dd/yy",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "mm/dd/yy",
				showExtraDays : true, 
				showTodayBtn : true,
				onlyCalendar : false,
				showEditorBtnOver : true,
				todayText : "Today",
				validator : function(oldValue, newValue, rowItem) { // 에디팅 유효성 검사
					var isValid = true;
					var oldDate = new Date(oldValue);
					oldDate = isNaN(Date.parse(oldDate)) ? oldValue : AUIGantt.formatDate(oldDate, "mm/dd/yy");
					if(isNaN(Number(newValue)) ) {
						newValue = String(newValue).replace(/-/g, "/");
						if(isNaN(Date.parse(newValue))) { // 즉, JS 가 Date 로 파싱할 수 있는 형식인지 조사
							isValid = false;
						} else {
							isValid = true;
						}
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid,  "message"  : "It doesn't support the date format" };
				}
			}
		}, 
		
		// 실제 시작 날짜 필드 (baseline )
		"startActual" : {
			dataField : "startActual",
			headerText : "Baseline Start",
			width:100,
			dataType : "date",
			formatString : "mm/dd/yy",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "mm/dd/yy",
				showExtraDays : true, 
				showTodayBtn : true,
				onlyCalendar : false,
				showEditorBtnOver : true,
				todayText : "Today",
				validator : function(oldValue, newValue, rowItem) { // 에디팅 유효성 검사
					var isValid = true;
					var oldDate = new Date(oldValue);
					oldDate = isNaN(Date.parse(oldDate)) ? oldValue : AUIGantt.formatDate(oldDate, "mm/dd/yy");
					if(isNaN(Number(newValue)) ) {
						newValue = String(newValue).replace(/-/g, "/");
						if(isNaN(Date.parse(newValue))) { // 즉, JS 가 Date 로 파싱할 수 있는 형식인지 조사
							isValid = false;
						} else {
							isValid = true;
						}
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid, "message"  : "It doesn't support the date format" };
				}
			}
		},
		
		// 실제 종료 날짜 필드 (baseline )
		"endActual" : {
			dataField : "endActual",
			headerText : "Baseline End",
			width : 100,
			dataType : "date",
			formatString : "mm/dd/yy",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "mm/dd/yy",
				showExtraDays : true, 
				showTodayBtn : true,
				onlyCalendar : false,
				showEditorBtnOver : true,
				todayText : "Today",
				validator : function(oldValue, newValue, rowItem) { // 에디팅 유효성 검사
					var isValid = true;
					var oldDate = new Date(oldValue);
					oldDate = isNaN(Date.parse(oldDate)) ? oldValue : AUIGantt.formatDate(oldDate, "mm/dd/yy");
					if(isNaN(Number(newValue)) ) {
						newValue = String(newValue).replace(/-/g, "/");
						if(isNaN(Date.parse(newValue))) { // 즉, JS 가 Date 로 파싱할 수 있는 형식인지 조사
							isValid = false;
						} else {
							isValid = true;
						}
					}
					// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
					return { "validate" : isValid,  "message"  : "It doesn't support the date format" };
				}
			}
		}, 
		
		// 선행 작업 필드
		"predecessor" : {
			dataField : "predecessor",
			headerText : "Predecessors",
			width:80
		},
		
		// 자원 이름 필드
		"resource" : {
			dataField : "resource",
			headerText : "Resources",
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
			headerText : "Progress",
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
					return { "validate" : isValid, "message"  : "Please enter only numbers 0~100." };
				}
			}
		}
	}; // end of defaultColumnInfo
};
