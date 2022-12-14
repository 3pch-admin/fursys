<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	// AUIGantt 생성 후 반환 ID
	var myGanttID;

	// 윈도우 onload
	// DOM 완료 후 간트 차트 생성함.
	window.onload = function() {

		// 간트차트를 생성합니다.
		createAUIGanttChart();
// 		AUIGantt.setGanttData(myGanttID, myGanttData);

	};

	// AUIGantt 를 생성합니다.
	function createAUIGanttChart() {

		// 간트 그리드(시트) 에 출력할 칼럼 필드 들 작성함.
		// 아래는 기본값으로 위치 또는 임의 작성으로 개발자는 작성 가능합니다.
		var myColumnLayout = [
		// 			AUIGantt.defaultColumnInfo.index, // 기본 정보 필드
		AUIGantt.defaultColumnInfo.name, // 작업 이름 필드
		AUIGantt.defaultColumnInfo.period, // 기간 필드
		AUIGantt.defaultColumnInfo.start, // 작업 시작 날짜 필드
		AUIGantt.defaultColumnInfo.end, // 작업 종료 날짜 필드
		AUIGantt.defaultColumnInfo.startActual, // 작업 종료 날짜 필드
		AUIGantt.defaultColumnInfo.endActual, // 작업 종료 날짜 필드
		AUIGantt.defaultColumnInfo.predecessor, // 선행 관계 필드
		AUIGantt.defaultColumnInfo.resource, // 자원 필드
		AUIGantt.defaultColumnInfo.progress // 진행률 필드
		];

		// 간트차트 속성 설정
		var ganttProps = {
			showBaselineBar : true,

			// 편집 가능 여부
			editable : false,
			width : 2000,
			// 			useContextMenu : true,
			// 			gridWidth : "40%",
			// 인덱스 1에 트리 칼럼을 만듬. 즉, 설정한 columnLayout 기준임.
			treeColumnIndex : 0,
			showTooltip : true,
			// 간트 그리드(시트) 레이아웃 (필수 정의해야 함)
			columnLayout : myColumnLayout

		};

		// 실제로 #gantt_wrap 에 간트차트 생성
		myGanttID = AUIGantt.create("#gantt_wrap", ganttProps);

		// ready 이벤트 바인딩
		AUIGantt.bind(myGanttID, "ready", function(event) {
			// 처음에 rowIndex 0, columnIndex 1 에 엑셀처럼 선택자 만들기.
			AUIGantt.setSelectionByIndex(myGanttID, 0, 0);
		});
	};
</script>

<div id="gantt_wrap" style="height: 840px;" class="close"></div>
