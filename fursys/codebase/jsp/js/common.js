var base = "/Windchill/platform";

function _logout() {
	if (!confirm("로그아웃 하시겠습니까?")) {
		return false;
	}
	document.execCommand("ClearAuthenticationCache");
	document.location.href = "/Windchill/login";
}


function call(url, params, cbm, type) {
	if (params == null) {
		params = new Object();
	}

	params = JSON.stringify(params);

	$.ajax({
		type: type,
		url: url,
		dataType: "JSON",
		crossDomain: true,
		data: params,
		async: true, //동기처리를 해야만 펑션리턴이 가능함
		contentType: "application/json; charset=UTF-8",
		beforeSend: function() {
			mask.open();
			$("#loading_layer").show();
		},
		complete: function() {
			mask.close();
			$("#loading_layer").hide();
		},
		success: function(data) {
			if (data.msg) {
				alert(data.msg);
			}
			if (data.result) {
				cbm(data);
			}
		},
		error: function(xhr, error) {
		}
	});
}

function _call(url, params, cbm, type) {
	if (params == null) {
		params = new Object();
	}

	params = JSON.stringify(params);

	$.ajax({
		type: type,
		url: url,
		dataType: "JSON",
		crossDomain: true,
		data: params,
		async: true, //동기처리를 해야만 펑션리턴이 가능함
		contentType: "application/json; charset=UTF-8",
		beforeSend: function() {
			if (opener == null) {
				parent.open();
				$(parent.document).find("#loading_layer").show();
			} else {
				mask.open();
				$("#loading_layer").show();
			}
		},
		complete: function() {
			if (opener == null) {
				parent.close();
				$(parent.document).find("#loading_layer").hide();
			} else {
				mask.close();
				$("#loading_layer").hide();
			}
		},
		success: function(data) {
			if (data.msg) {
				alert(data.msg);
			}
			if (data.result) {
				cbm(data);
			}
		},
		error: function(xhr, error) {
		}
	});
}

function _data($form) {
	var o = {};
	var a = $form.serializeArray();
	$.each(a, function() {
		var name = $.trim(this.name),
			value = $.trim(this.value);

		if (name == "secondary" || name == "poid" || name == "doid" || name == "appOid" || name == "refOid") {

			if (o[name]) {
				o[name].push(value || '');
			} else {
				o[name] = [];
				o[name].push(value || '');
			}

		} else if (o[name]) {

			if (!o[name].push) {
				o[name] = [o[name]];
			}
			o[name].push(value || '');
		} else {
			o[name] = value || '';
		}
	});

	return o;
}

function _folder(ids, loc, context) {
	$("#" + ids).click(function() {
		var url = "/Windchill/platform/util/folder?location=" + loc + "&target=" + ids + "&context=" + context;
		_popup(url, 600, 500, "n");
	})
}

function _finder(ids, _surl, _furl) {
	$("#" + ids).bindSelector({
		reserveKeys: {
			options: "list",
			optionValue: "value",
			optionText: "name"
		},
		optionPrintLength: "all",
		onsearch: function(id, val, callBack) {
			var key = $("#" + id).val();
			var params = new Object();
			params.key = key;
			var url = _surl
			_call(url, params, function(data) {
				callBack({
					options: data.list
				})
			}, "POST");
		},
		onchange: function() {
			var value;
			var targetID = this.targetID;
			var target = targetID + "Oid";
			if (this.selectedOption != null) {
				value = this.selectedOption.value;
			}
			$("#" + target).remove();
			$("#" + targetID).before("<input type=\"hidden\" name=\"" + target + "\" id=\"" + target + "\"> ");
			$("#" + target).val(value);
		},
		finder: {
			onclick: function() {
				var url = _furl;
			}
		}
	})
}

function _user(ids) {
	$("#" + ids).bindSelector({
		reserveKeys: {
			options: "list",
			optionValue: "value",
			optionText: "name"
		},
		optionPrintLength: "all",
		onsearch: function(id, val, callBack) {
			var key = $("#" + id).val();
			var params = new Object();
			params.key = key;
			var url = "/Windchill/platform/util/user";
			_call(url, params, function(data) {
				callBack({
					options: data.list
				})
			}, "POST");
		},
		onchange: function() {
			var value;
			var targetID = this.targetID;
			var target = targetID + "Oid";
			if (this.selectedOption != null) {
				value = this.selectedOption.value;
			}
			$("#" + target).remove();
			$("#" + targetID).before("<input type=\"hidden\" name=\"" + target + "\" id=\"" + target + "\"> ");
			$("#" + target).val(value);
		},
		finder: {
			onclick: function() {
				var targetID = this.targetID;
				var url = "/Windchill/platform/user/popup?target=" + targetID;
				_popup(url, 1200, 650, "n");
			}
		}
	})
}

function _fixeduser(ids) {
	$("#" + ids).bindSelector({
		reserveKeys: {
			options: "list",
			optionValue: "value",
			optionText: "name"
		},
		optionPrintLength: "all",
		onsearch: function(id, val, callBack) {
			var key = $("#" + id).val();
			var params = new Object();
			params.key = key;
			var url = "/Windchill/platform/util/user";
			_call(url, params, function(data) {
				callBack({
					options: data.list
				})
			}, "POST");
		},
		onchange: function() {
			var value;
			var targetID = this.targetID;
			var target = targetID + "Oid";
			if (this.selectedOption != null) {
				value = this.selectedOption.value;
			}
			$("#" + target).remove();
			$("#" + targetID).before("<input type=\"hidden\" name=\"" + target + "\" id=\"" + target + "\"> ");
			$("#" + target).val(value);
		},
		finder: {
			onclick: function() {
				var targetID = this.targetID;
				var url = "/Windchill/platform/user/popup?target=" + targetID;
				_popup(url, 1200, 650, "n");
			}
		}
	})
}

function _distributor(ids) {
	$("#" + ids).bindSelector({
		reserveKeys: {
			options: "list",
			optionValue: "value",
			optionText: "name"
		},
		optionPrintLength: "all",
		onsearch: function(id, val, callBack) {
			var key = $("#" + id).val();
			var params = new Object();
			params.key = key;
			var url = "/Windchill/platform/distributor/getDistributor";
			_call(url, params, function(data) {
				callBack({
					options: data.list
				})
			}, "POST");
		},
		onchange: function() {
			var value;
			var targetID = this.targetID;
			var target = targetID + "Oid";
			if (this.selectedOption != null) {
				value = this.selectedOption.value;
			}
			$("#" + target).remove();
			$("#" + targetID).before("<input type=\"hidden\" name=\"" + target + "\" id=\"" + target + "\"> ");
			$("#" + target).val(value);
		},
		finder: {
			onclick: function() {
				var targetID = this.targetID;
				var url = "/Windchill/platform/distributor/popup?target=" + targetID;
				_popup(url, 1000, 650, "n");
			}
		}
	})
}

function _clearUser(clazz) {
	$("." + clazz).click(function() {
		var target = $(this).data("target");
		$("input[name=" + target + "]").val("");
		$("input[name=" + target + "Oid]").val("");
	})
}

function _selector(ids) {
	$("#" + ids).bindSelect();
}

function _date(ids) {
	$("#" + ids).bindDate({
		align: "left",
		valign: "top",
		buttonText: "확인",
		customPos: {
			top: 25,
			left: 25
		},
	});
}

function _between(ids) {
	var start = $("#" + ids).data("start");
	$("#" + ids).bindTwinDate({
		align: "left",
		valign: "top",
		buttonText: "확인",
		customPos: {
			top: 25,
			left: 25
		},
		startTargetID: start
	});
}

function _clearBetween(clazz) {
	$("." + clazz).click(function() {
		var target = $(this).data("target");
		var start = "start" + target;
		var end = "end" + target;
		$("input[name=" + start + "]").val("");
		$("input[name=" + end + "]").val("");
	})
}

function _check(ids) {
	$("input[name=" + ids + "]").bindChecked();
}

function _url(url, oid) {
	if (oid === undefined) {
		return base + url;
	} else {
		if (url.indexOf("?") > -1) {
			return base + url + "&oid=" + oid;
		} else {
			return base + url + "?oid=" + oid;
		}
	}
}

function _setBoxs(box) {
	$box = $(box);
	$.each($box, function(idx) {
		if (!$box.eq(idx).hasClass("isBox")) {
			$(this).checks();
			$(this).addClass("isBox");
		}
	})
}

function _page(s) {
	var url = base + s;
	document.getElementById("body").src = url;
}

function _redirect(s) {
	var url = base + s;
	document.location.href = url;
}


function _popup(url, width, height, opt) {
	var popW = width;
	var popH = height;
	var left = (screen.width - popW) / 2;
	var top = (screen.height - popH) / 2;

	if (opt == "no" || opt == "n") {
		opt = "scrollbars=yes resizable=yes";
	}

	if (opt == "" || opt == undefined || opt == "full" || opt == "f") {
		popW = screen.width;
		popH = screen.height;
		opt = "scrollbars=yes, resizable=yes, fullscreen=yes";
	}
	// 중복 생성 방지?
	window.open(url, "", opt + ", top=" + (top - 50) + ", left=" + left + ", height=" + popH + ", width=" + popW);
}

function _time(format) {
	var nowDate = new Date();
	return moment(nowDate).format(format);
}

function _excel(gridID, name, fields) {
	var excelName = name + "_" + _time("YYYYMMDDHHmmss");
	var props = {
		fileName: excelName,
		progressBar: true,
		exportWithStyle: true,
		exceptColumnFields: fields,
		headers: [{
			text: "", height: 20 // 첫행 빈줄
		}, {
			text: name, height: 24, style: { fontSize: 20, textAlign: "center", fontWeight: "bold", underline: true, background: "#DAD9FF" }
		}, {
			text: "작성일 : " + _time("MM-DD-YYYY"), style: { textAlign: "right" }
		}, {
			text: "", height: 5, style: { background: "#555555" } // 빈줄 색깔 경계 만듬
		}],
		// 푸터 내용
		footers: [{
			text: "", height: 5, style: { background: "#555555" } // 빈줄 색깔 경계 만듬
		}, {
			text: "FURSYS PLM", height: 24, style: { textAlign: "right", fontWeight: "bold", color: "#ffffff", background: "#222222" }
		}]
	};
	AUIGrid.exportToXlsx(gridID, props);
}

function _array(items) {
	var arr = new Array();
	for (var i = 0; i < items.length; i++) {
		var oid = items[i].item.oid;
		arr.push(oid);
	}
	return arr;
}


function _delete() {
	$("._delete").click(function() {
		var target = $(this).data("target");
		$("input[name=" + target + "]").val("");
		$("input[name=" + target + "Oid]").remove();
	})
}

function preLoading() {
	mask.open();
}

function completeLoading() {
	mask.close();
	$("#loading_layer").hide();
}

function _openCreoView(oid) {
	if (oid == "" || oid == null) {
		return false;
	}
	var url = "/Windchill/platform/util/openCreoView?oid=" + oid;

	_call(url, null, function(data) {
		var params = new Object();
		params.linkurl = "/Windchill/wtcore/jsp/wvs/edrview.jsp?url=" + data.url;
		params.browser = "chrome";
		$.ajax({
			type: "POST",
			url: "/Windchill/netmarkets/jsp/wvs/wvsGW.jsp?class=com.ptc.wvs.server.ui.UIHelper&method=getOpenInCreoViewServiceCustomURI",
			data: jQuery.param(params, true),
			processData: false,
			async: true,
			dataType: "json",
			cache: false,
			timeout: 600000,
			success: function(res) {
				location.href = res.uri;
			}
		})
	}, "GET");
}