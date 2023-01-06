/**
 * AUIGantt 에서 사용되는 메세지 및 리소스들을 정의합니다.
 */
AUIGanttMessages = {
		
		// 경고 메세지
		alerts  : {
			"e1" : "선행 작업 관계 표현이 잘못되었습니다. (예: 1 또는 1FS 또는 1FS+3D)",
			"e2" : "작업의 선행 작업을 자기 자신으로 만들 수 없습니다.",
			"e3" : "요약 작업을 하위 작업 중 하나에 연결할 수 없습니다.",
			"e4" : "같은 후속 작업에 선행 작업을 두개 이상 연결 할 수 없습니다.",
			"e5" : "작성되지 않은 작업을 선행 작업으로 연결할 수 없습니다.",
			"e6" : "이 작업은 서로 이미 연결되어 있습니다.",
			"e7" : "선행 작업을 마치기 전에 현재 작업이 시작하도록 옮겼습니다. 두 작업의 연결을 유지 할 수 없습니다.\r\n연결을 삭제하고 작업을 옮기시겠습니까?",
			"e8" : "해당 날짜가 전체 프로젝트 시작일보다 이전으로 설정되었습니다.\r\n해당 날짜로 프로젝트 시작일을 변경하시겠습니까?"
		},
		
		
		// 날짜 포맷 ddd, dddd 에 해당되는 포맷 스트링 지정
		dayNames: [
			"일", "월", "화", "수", "목", "금", "토",
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
				"month" : "yyyy년 m월",
				"month2" :  "yyyy년 m월",
				"week" : "yyyy년 m월 d일",
				"week2" : "yyyy년 m월 d일",
				"day" : "yyyy년 m월 d일 (ddd)",
				"day2" : "yyyy년 m월 d일 (ddd) hTT",
				"hour" : "HH:MM"
		},
		
		// 마이너 타임 축 날짜 포맷.
		minorDateFormatString : {
				"year" : "yy",
				"half" : ["H1", "H2"],
				"quarter" : ["Q1", "Q2", "Q3", "Q4"],
				"month" : "m",
				"month2" : "m월",
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
			start : "시작 날짜",
			end : "종료 날짜",
			days : "일",
			period : "기간",
			progress : "완료율"
		},
		
		todayLineText : "오늘",
		projectStartLineText : "프로젝트 시작",
		projectEndLineText : "프로젝트 종료",
		
		gridMessages : {
			noDataMessage: "출력할 데이터가 없습니다.",
			filterNoValueText: "(필드 값 없음)",
			filterCheckAllText: "(전체선택)",
			filterClearText: "필터 초기화",
			filterSearchCheckAllText: "(검색 전체선택)",
			filterSearchPlaceholder : "검색", // 필터 검색 플레이홀더 텍스트
			filterOkText: "확 인",
			filterCancelText: "취 소",
			filterItemMoreMessage: "Too many items...Search words",
			filterNumberOperatorList: ["같다(=)", "크다(>)", "크거나 같다(>=)", "작다(<)", "작거나 같다(<=)", "같지 않다(!=)"],
			thousandSeparator : ",",
			rowNumHeaderText: "No.",
			remoterPlaceholder: "검색어를 입력하세요.",
			calendar : {
				titles : ["일", "월", "화", "수", "목", "금", "토"],
				formatYearString : "yyyy년",
				monthTitleString : "m월",
				formatMonthString : "yyyy년 mm월",
				todayText : "오늘 선택",
				uncheckDateText : "날짜 선택 해제"
			}
		},
		
		// 다이어그램 스타일
		chartStyles : {
				headerBgColors : ["#F8F8F8", "#EEEEEE"], // 헤더 백그라운드
				headerLineColor : "#BDBDBD", // 헤더 상, 하 구분선 및 헤더 하단 선 색상
				headerMajorFont : "bold 12px 맑은 고딕", // 헤더 폰트 스타일
				headerMinorFont : "normal 12px 맑은 고딕", // 헤더 폰트 스타일
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
				barLabelFont : "bold 12px 맑은 고딕",
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
					tip = "이 작업은 수정하지 못하도록 잠금 설정 되어 있습니다.";
					str += '<img src="./assets/lock_icon.png" height="16" width="16" style="vertical-align:middle" alt="' + tip +'" title="' + tip +'">';
				}
				
				if(Math.floor(Number(item.progress)) == 100) {
					tip = "이 작업은 " + AUIGantt.formatDate(item.end, "yyyy년 m월 d일 (ddd)") + "에 완료되었습니다.";
					str += '<img src="./assets/green_check.png" height="16" width="16" style="vertical-align:middle" alt="' + tip +'" title="' + tip +'">';
				} 
				else if(item.fixedDate != "undefined") {
					if(item.fixedDateName == "afterStart") {
						tip = AUIGantt.formatDate(item.fixedDate, "yyyy년 m월 d일 (ddd)") + " 이후에 시작하도록 제한되어 있습니다."
						str += '<img src="./assets/start_restrict_calendar.png" height="16" style="vertical-align:middle" width="16" alt="' + tip +'" title="' + tip +'">';
					} else if(item.fixedDateName == "afterEnd") {
						tip = AUIGantt.formatDate(item.fixedDate, "yyyy년 m월 d일 (ddd)") + " 이후에 완료되도록 제한되어 있습니다.";
						str += '<img src="./assets/end_restrict_calendar.png" height="16" width="16" style="vertical-align:middle" alt="' + tip +'" title="' + tip +'">';
					}
				}
				
				if(item.hasMemo) {
					tip = "이 작업에는 이슈사항이 있습니다.";
					str += '<img src="./assets/comment-rect-icon.png" height="16" width="16" style="vertical-align:middle;cursor:pointer;"  alt="' + tip +'" title="' + tip +'">';
				}
				
				str + "</div>";
				return str;
			} // end of labelFunction
		},
		
		// 작업 이름 필드
		"name" : {
			dataField : "name",
			headerText : "작업 이름",
			width: 280,
			showHeaderMenu:true,
			style : "aui-gantt-default-left-style"
		},
		
		// 기간 필드
		"period" : {
			dataField : "period",
			headerText : "기간",
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
					return { "validate" : isValid, "message"  : "기간은 일(days) 단위입니다. 숫자만 입력하십시오." };
				}
			},
			
			labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
				if(typeof item.start == "undefined" && typeof item.end == "undefined") {
					return "";
				}
				
				var postfix = " 일?";
				if(item.isFixedPeriod) {
					postfix = " 일";
				}
				
				return Math.ceil(value)+ postfix;
			} // end of labelFunction
		},
		
		// 시작 날짜 필드
		"start" : {
			dataField : "start",
			headerText : "시작 날짜",
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
				todayText : "오늘 날짜 선택",
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
					return { "validate" : isValid,  "message"  : "지원하지 않는 날짜 입력 형식입니다.(입력 예: 2016-02-01 또는 2016/02/01)" };
				}
			}
		},
		
		// 종료 날짜 필드
		"end" : {
			dataField : "end",
			headerText : "완료 날짜",
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
				todayText : "오늘 날짜 선택",
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
					return { "validate" : isValid,  "message"  : "지원하지 않는 날짜 입력 형식입니다.(입력 예: 2016-02-01 또는 2016/02/01)" };
				}
			}
		}, 
		
		// 실제 시작 날짜 필드 (baseline )
		"startActual" : {
			dataField : "startActual",
			headerText : "실제 시작 날짜",
			width : 120,
			dataType : "date",
			style : "aui-gantt-default-actual-start-column",
			formatString : "yyyy-mm-dd (ddd)",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "yyyy-mm-dd",
				showExtraDays : true, 
				showTodayBtn : true,
				showUncheckDateBtn : true,
				uncheckDateValue : "",
				onlyCalendar : false,
				showEditorBtnOver : true,
				todayText : "오늘 날짜 선택",
				uncheckDateText : "날짜 선택 해제",
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
					return { "validate" : isValid,  "message"  : "지원하지 않는 날짜 입력 형식입니다.(입력 예: 2016-02-01 또는 2016/02/01)" };
				}
			}
		},
		
		// 실제 종료 날짜 필드 (baseline )
		"endActual" : {
			dataField : "endActual",
			headerText : "실제 완료 날짜",
			width : 120,
			dataType : "date",
			style : "aui-gantt-default-actual-end-column",
			formatString : "yyyy-mm-dd (ddd)",
			editRenderer : {
				type : "CalendarRenderer",
				defaultFormat : "yyyy-mm-dd",
				showExtraDays : true, 
				showTodayBtn : true,
				showUncheckDateBtn : true,
				uncheckDateValue : "",
				onlyCalendar : false,
				showEditorBtnOver : true,
				todayText : "오늘 날짜 선택",
				uncheckDateText : "날짜 선택 해제",
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
					return { "validate" : isValid,  "message"  : "지원하지 않는 날짜 입력 형식입니다.(입력 예: 2016-02-01 또는 2016/02/01)" };
				}
			}
		}, 
		
		// 선행 작업 필드
		"predecessor" : {
			dataField : "predecessor",
			headerText : "선행 작업",
			width:80
		},
		
		// 자원 이름 필드
		"resource" : {
			dataField : "resource",
			headerText : "자원 이름",
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
			headerText : "완료율",
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
					return { "validate" : isValid, "message"  : "0~100의 범위를 가지는 숫자를 입력하십시오." };
				}
			}
		}
	}; // end of defaultColumnInfo
};
