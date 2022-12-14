var uploadUrl = "/Windchill/platform/util/upload";
var deleteUrl = "/Windchill/platform/util/delete";
var primarys = new AXUpload5();
var secondarys = new AXUpload5();
var masterHost = location.protocol + "//" + location.host + "/Windchill/";
var upload = {
	init: function(oid, roleType) {
		if (roleType === "s" || roleType === "secondary") {
			upload.secondary.init(oid, roleType);
		}

		if (roleType === "p" || roleType === "primary") {
			upload.primary.init(oid, roleType);
		}
	},


	secondary: {
		init: function(oid, roleType) {
			secondarys.setConfig({
				isSingleUpload: false,
				targetID: "secondary_layer",
				dropBoxID: "uploadQueueBox",
				queueBoxID: "uploadQueueBox",
				buttonTxt: "파일 선택",
				uploadFileName: "data",
				uploadMaxFileSize: (1024 * 1024 * 1024),
				uploadMaxFileCount: 10,
				uploadUrl: uploadUrl,
				uploadPars: {
					masterHost: masterHost,
					roleType: roleType
				},

				deleteUrl: deleteUrl,
				fileKeys: { // 서버에서 리턴하는 json key 정의 (id는 예약어 사용할 수 없음)
					name: "name",
					type: "type",
					saveName: "saveName",
					fileSize: "fileSize",
					uploadedPath: "uploadedPath",
					thumbPath: "thumbUrl",
					roleType: "roleType",
					cacheId: "cacheId",
				},

				onComplete: function(list) {
					$secondary = $("input[name=secondary]");
					$.each($secondary, function(idx) {
						$secondary.eq(idx).remove();
					})
					var len = list.length;
					for (var i = 0; i < len; i++) {
						var value = list[i];
						//var cacheId = value.cacheId;
						var path = value.path;
						var value = value.value;
						$("form:eq(0)").append("<input type=\"hidden\" name=\"secondary\" value=\"" + value + "\">");
					}
				},
				onDelete: function() {
					$("form:eq(0)").find("input[id=" + this.file._id_ + "]").remove();
				}
			});

			var listUrl = "/Windchill/platform/util/list";
			listUrl += "?oid=" + oid + "&roleType=" + roleType + "&masterHost=" + masterHost;
			new AXReq(listUrl, {
				onsucc: function(res) {
					if (!res.error) {
						var s = res.secondaryFile;
						secondarys.setUploadedList(s);
						$len = s.length;
						for (var i = 0; i < $len; i++) {
							$("form:eq(0)").append("<input id=\"" + s[i]._id_ + "\" type=\"hidden\" name=\"secondary\" value=\"" + s[i].cacheId + "\">");
						}
					} else {
						alert(res.msg.dec());
					}
				}
			});
		}
	},

	primary: {
		init: function(oid, roleType) {
			primarys.setConfig({
				isSingleUpload: true,
				targetID: "primary_layer",
				buttonTxt: "파일 선택",
				uploadFileName: "data",
				uploadMaxFileSize: (1024 * 1024 * 1024),
				uploadUrl: uploadUrl,
				uploadPars: {
					masterHost: masterHost,
					roleType: roleType
				},
				deleteUrl: deleteUrl,
				fileKeys: { // 서버에서 리턴하는 json key 정의 (id는 예약어 사용할 수 없음)
					name: "name",
					type: "type",
					saveName: "saveName",
					fileSize: "fileSize",
					uploadedPath: "uploadedPath",
					thumbPath: "thumbUrl",
					roleType: "roleType",
					cacheId: "cacheId",
				},

				onComplete: function() {
					var value = this[0];
					//var cacheId = value.cacheId;
					var path = value.path;
					var value = value.value;
					console.log(value);
					$("form:eq(0)").append("<input type=\"hidden\" name=\"primary\" value=\"" + value + "\">");
				},

				onDelete: function() {
					$("form:eq(0)").find("input[name=primary]").remove();
				}
			});

			var listUrl = "/Windchill/platform/util/list";
			listUrl += "?oid=" + oid + "&roleType=" + roleType + "&masterHost=" + masterHost;
			new AXReq(listUrl, {
				onsucc: function(res) {
					if (!res.error) {
						var p = res.primaryFile;
						primarys.setUploadedList(p);
						$("form:eq(0)").append("<input id=\"" + p._id_ + "\" type=\"hidden\" name=\"primary\" value=\"" + p.cacheId + "\">");
					} else {
						alert(res.msg.dec());
					}
				}
			});
		}
	}
}	