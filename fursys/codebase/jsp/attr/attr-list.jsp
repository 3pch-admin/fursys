<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<%@include file="/jsp/common/iframe.jsp"%>
<%@include file="/jsp/common/script.jsp"%>
</head>
<body onload="load();" id="container">
	<form id="form">
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>CAD 속성</span>
			>
			<span>원가 & 생산 속성 </span>
		</div>
		<table class="search-table top-color">
			<colgroup>
				<col width="80">
				<col width="100">
				<col width="80">
				<col width="100">
				<col width="80">
				<col width="100">
				<col width="100">
				<col width="100">
				<col width="50">
				<col width="100">
				<col width="60">
				<col width="100">
				<col width="60">
				<col width="100">
			</colgroup>
			<tr>
				<th>사업장</th>
				<td>
					<select name="plant" class="AXSelect w100px" id="plant">
						<option value="">선택</option>
					</select>
				</td>
				<th>자재/재공 코드</th>
				<td>
					<input type="text" name="partNo" class="AXInput w80p">
				</td>
				<th>색상</th>
				<td>
					<input type="text" name="color" class="AXInput w80p">
				</td>
				<th>부품명</th>
				<td>
					<input type="text" name="partName" class="AXInput w80p">
				</td>
				<th>상태</th>
				<td>
					<select name="state" class="AXSelect w100px" id="state">
						<option value="">선택</option>
						<option value="">사용 중</option>
					</select>
				</td>
				<th>규격구분</th>
				<td>
					<select name="spec" class="AXSelect w100px" id="spec">
						<option value="">전체(ALL)</option>
					</select>
				</td>
				<th>계정구분</th>
				<td></td>
			</tr>
			<tr>
				<th>카테고리대</th>
				<td>
					<select name="cat_l" class="AXSelect w100px" id="cat_l">
						<option value="">전체</option>
					</select>
				</td>
				<th>카테고리중</th>
				<td>
					<select name="cat_m" class="AXSelect w100px" id="cat_m">
						<option value="">전체</option>
					</select>
				</td>
				<th>카테고리소</th>
				<td>
					<select name="cat_s" class="AXSelect w100px" id="cat_s">
						<option value="">전체</option>
					</select>
				</td>
				<th>표면처리</th>
				<td>
					<select name="surface" class="AXSelect w100px" id="surface">
						<option value="">전체</option>
					</select>
				</td>

				<td colspan="2">
					<input type="checkbox" name="edge">
					엣지공정자재보기
				</td>

				<td colspan="2">
					<input type="checkbox" name="required">
					필수미입력자재
				</td>

				<td colspan="2">
					<input type="checkbox" name="visible">
					등록건만보기
				</td>
			</tr>
		</table>
		<table class="button-table">
			<tr>
				<td class="left">
					<button type="button" id="addRowBtn">추가</button>
					<button type="button" id="deleteRowBtn">삭제</button>
					<button type="button" id="saveBtn">저장</button>
				</td>
				<td class="right">
					<button type="button" id="excelBtn">엑셀</button>
					<button type="button" id="searchBtn">조회</button>
				</td>
			</tr>
		</table>
		<div id="grid_wrap" style="height: 710px;"></div>
		<script type="text/javascript">
			var myGridID;
			var columnLayout = [ {
				dataField : "itemCode",
				headerText : "단품코드",
				dataType : "string",
				width : 80,
				editable : true
			}, {
				dataField : "itemCodeColor",
				headerText : "단품코드<br> 색상",
				dataType : "string",
				width : 80,
				editable : false
			}, {
				dataField : "partNo",
				headerText : "part_no<br>(자재/재공코드)",
				dataType : "string",
				width : 80,
				editable : false
			}, {
				dataField : "color",
				headerText : "색상",
				dataType : "string",
				width : 80,
				editable : false
			}, {
				dataField : "partName",
				headerText : "part_name<br>(부품명)",
				dataType : "string",
				width : 80,
				editable : false
			}, {
				dataField : "",
				headerText : "도면<br> URL",
				dataType : "string",
				width : 60,
				editable : false
			}, {
				dataField : "cad_key",
				headerText : "CAD<br>KEY",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_width",
				headerText : "엣지 포함<br> 가로(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_depth",
				headerText : "엣지 포함<br> 세로(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_width",
				headerText : "가로(mm)<br> 목재는 엣지 제외",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_depth",
				headerText : "세로(mm)<br> 목재는 엣지 제외",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_height",
				headerText : "높이(mm)<br> 목재는 엣지 제외",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_total_leng",
				headerText : "엣지 <br>총 길이(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_urethane_vol",
				headerText : "우레탄 <br>부피(mm3)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_coat_area",
				headerText : "면도장 <br>면적(mm2)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_coat_area",
				headerText : "엣지도장 <br>면적(mm2)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_sawcut_leng_ac",
				headerText : "AC면 엣지-<br>홈파기 간격(mm) ",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_sawcut_leng_bd",
				headerText : "BD면 엣지-<br>홈파기 간격(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_curved_leng",
				headerText : "곡면엣지<br> 길이(mm) ",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_router_outside_leng",
				headerText : "외곽루터<br> 총 길이(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_router_inside",
				headerText : "내부루터<br> 길이(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_coat_strip_leng",
				headerText : "줄눈도장<br>(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_pocket_inside_leng",
				headerText : "내부도장<br>길이(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_spec_a",
				headerText : "A면 <br>엣지사양",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_spec_b",
				headerText : "B면 <br>엣지사양",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_spec_c",
				headerText : "C면 <br>엣지사양",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_spec_d",
				headerText : "D면 <br>엣지사양",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_router_spec_1",
				headerText : "루터마감 <br>사양1",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_router_spec_2",
				headerText : "루터마감 <br>사양2",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_router_spec_3",
				headerText : "루터마감 <br>사양3",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_router_leng_1",
				headerText : "루터마감 <br>길이 1(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_router_leng_2",
				headerText : "루터마감 <br>길이 2(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_router_leng_3",
				headerText : "루터마감 <br>길이 3(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_film_leng",
				headerText : "전사 <br>길이(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_boring_both",
				headerText : "양면보링<br> 여부",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1" ]
				}
			}, {
				dataField : "im_slant_process",
				headerText : "사선가공<br> 여부",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1면", "2면", "3면", "4면" ]
				}
			}, {
				dataField : "im_edge_coat",
				headerText : "엣지도장<br> 여부",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1"]
				}
			}, {
				dataField : "im_edge_curved_num",
				headerText : "곡면 접착<br> 횟수",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_edge_height",
				headerText : "엣지 포함<br> 두께(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_boring_nothru",
				headerText : "미관통 <br>보링개수(EA)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_casing_screw_a",
				headerText : "A면 케이싱<br>스크류 개수",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_casing_screw_b",
				headerText : "B면 케이싱<br>스크류 개수",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_casing_screw_c",
				headerText : "C면 케이싱<br>스크류 개수",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_casing_screw_d",
				headerText : "D면 케이싱<br>스크류 개수",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_casing_housing",
				headerText : "케이싱 하우징<br> 수량(EA)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_boring_dia_a",
				headerText : "A면 사이드<br>보링 최대 지름(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_boring_dia_b",
				headerText : "B면 사이드<br>보링 최대 지름(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_boring_dia_c",
				headerText : "C면 사이드보링 최대 지름(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_boring_dia_d",
				headerText : "D면 사이드<br>보링 최대 지름(mm)",
				dataType : "string",
				width : 80
			}, {
				//확장속성
				dataField : "im_edge_trim_a",
				headerText : "A면 엣지<br>마감 형태 (L/S)",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "L", "S" ]
				}
			}, {
				dataField : "im_edge_trim_b",
				headerText : "B면 엣지<br>마감 형태 (L/S)",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "L", "S" ]
				}
			}, {
				dataField : "im_edge_trim_c",
				headerText : "C면 엣지<br>마감 형태 (L/S)",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "L", "S" ]
				}
			}, {
				dataField : "im_edge_trim_d",
				headerText : "D면 엣지<br>마감 형태 (L/S)",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "L", "S" ]
				}
			}, {
				dataField : "im_boring_side_x",
				headerText : "A/C면 <br>50mm내 측면보링 유무",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1" ]
				}
			}, {
				dataField : "im_boring_side_y",
				headerText : "B/C면 <br>50mm내 측면보링 유무",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1" ]
				}
			}, {
				dataField : "im_pocket_outside_a",
				headerText : "A면 외곽 <br>포켓 유/무",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1" ]
				}
			}, {
				dataField : "im_pocket_outside_b",
				headerText : "B면 외곽 <br>포켓 유/무",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1" ]
				}
			}, {
				dataField : "im_pocket_outside_c",
				headerText : "C면 외곽 <br>포켓 유/무",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1" ]
				}
			}, {
				dataField : "im_pocket_outside_d",
				headerText : "D면 외곽 <br>포켓 유/무",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1" ]
				}
			}, {
				dataField : "im_junction_type",
				headerText : "접착<br>유형",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1", "2", "3" ]
				}
			}, {
				dataField : "im_uv_coat_1st",
				headerText : "UV하도 <br>면수",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1(일면)", "2(양면)" ]
				}
			}, {
				dataField : "im_uv_coat_2nd",
				headerText : "UV상도 <br>면수",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1(일면)", "2(양면)" ]
				}
			}, {
				dataField : "im_edge_1st_outin",
				headerText : "엣지하도 <br>유형 ",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1(SPB)", "2(MDF)", "3(형상)" ]
				}
			}, {
				dataField : "im_edge_2nd_outin",
				headerText : "엣지상도 <br>유형",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1(SPB)", "2(MDF)", "3(형상)" ]
				}
			}, {
				dataField : "im_urethane_1st",
				headerText : "목재소재 - <br>우레탄도장 (하도)",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1(오픈)", "2(클로즈)", "3(유색)" ]
				}
			}, {
				dataField : "im_urethane_2nd",
				headerText : "목재소재 - <br>우레탄도장 (상도)",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1(오픈)", "2(클로즈)", "3(유색)" ]
				}
			}, {
				dataField : "im_repair_coat",
				headerText : "수리<br>(도장)",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1(UV도장)", "2(우레탄도장)" ]
				}
			}, {
				dataField : "im_wash_face",
				headerText : "세척면수",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1(유)", "0(무)" ]
				}
			}, {
				dataField : "im_form_cut",
				headerText : "성형합판<br> 재단",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_form_comp",
				headerText : "성형합판<br> 조판",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_form_junction",
				headerText : "성형합판<br> 접착",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_form_3d",
				headerText : "성형합판<br> 3D가공",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_tenoner_leng",
				headerText : "테노너<br> 가공길이(mm)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_turn_key",
				headerText : "통작업<br> 유/무",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_wood_frame",
				headerText : "목재 <br>프레임여부",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1" ]
				}
			}, {
				dataField : "im_edge_coat_type",
				headerText : "엣지코팅<br>형상구분",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "1(FX1)", "0(무)" ]
				}
			}, {
				dataField : "im_surf_finish_first",
				headerText : "우선<br>마감면",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_assy_line",
				headerText : "조립라인<br> 여부 ",
				dataType : "string",
				width : 80,
				renderer : {
					type : "DropDownListRenderer",
					list : [ "0", "1" ]
				}
			}, {
				dataField : "im_assy_time",
				headerText : "조립시간<br>(초)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_frame_count",
				headerText : "프레임<br>(목대)",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_asm_hw",
				headerText : "조립부품",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_sper_hw",
				headerText : "별도부품",
				dataType : "string",
				width : 80
			}, {
				dataField : "im_pack_part",
				headerText : "포장부품",
				dataType : "string",
				width : 80
			}, {
				dataField : "createdDate",
				headerText : "등록일",
				dataType : "string",
				width : 100,
				editable : false
			}, {
				dataField : "oid",
				headerText : "oid",
				dataType : "string",
				visible : false
			}, ];
			var auiGridProps = {
				rowIdField : "oid",
				headerHeight : 50,
				rowHeight : 30,
				// 				fillColumnSizeMode : true,
				// 				rowCheckToRadio : true,
				showRowCheckColumn : true,
				enableFilter : true,
				showInlineFilter : true,
				editable : true,
				fixedColumnCount : 6,
				rowNumHeaderText : "번호",
			};
			myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
			function load() {
				var params = _data($("#form"));
				var url = _url("/attr/list");
				AUIGrid.showAjaxLoader(myGridID);
				_call(url, params, function(data) {
					AUIGrid.removeAjaxLoader(myGridID);
					AUIGrid.setGridData(myGridID, data.list);
				}, "POST");
			}

			$(function() {
				$("#searchBtn").click(function() {
					load();
				})

				$("#addRowBtn").click(function() {
					var item = new Object();
					AUIGrid.addRow(myGridID, item, "last"); // 최하단에 1행 추가
				})

				$("#deleteRowBtn").click(function() {
					var items = AUIGrid.getCheckedRowItems(myGridID);
					if (items.length == 0) {
						alert("삭제 할 행을 선택하세요.");
					}
					for (var i = 0; i < items.length; i++) {
						AUIGrid.removeRow(myGridID, items[i].rowIndex);
					}
				})

				$("#saveBtn").click(function() {
					var removeRows = AUIGrid.getRemovedItems(myGridID);
					var addRows = AUIGrid.getAddedRowItems(myGridID);
					var editRows = AUIGrid.getEditedRowItems(myGridID);
					if (removeRows.length == 0 && addRows.length == 0 && editRows.length == 0) {
						return false;
					}

					if (!confirm("저장 하시겠습니까?")) {
						return false;
					}
					var params = new Object();
					params.addRows = addRows;
					params.editRows = editRows;
					params.removeRows = removeRows;
					var url = _url("/attr/create");
					_call(url, params, function(data) {
						load();
					}, "POST");
				});

				$("#excelBtn").click(function() {
					_excel(myGridID, "원가 및 생산 속성", []);
				})

				_selector("surface");
				_selector("plant");
				_selector("cat_l");
				_selector("cat_m");
				_selector("cat_s");
				_selector("state");
				_selector("spec");
				$("input[name=visible]").checks();
				$("input[name=required]").checks();
				$("input[name=edge]").checks();

				$(window).resize(function() {
					AUIGrid.resize("#grid_wrap");
				})
			})
		</script>
	</form>
</body>
</html>