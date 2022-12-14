/*
 Copyright (c) 2017, Raonwiz Technology Inc. All rights reserved.
*/
if ("undefined" != typeof window && !window.RAONKEDITOR) {
	var RAONKEditor = function(a) {
		var d = a.Id, b = new keditor_user_config, c; for (c in a) a.hasOwnProperty(c) && (b[c] = a[c]); void 0 == d && (d = RAONKEDITOR.util.makeGuidTagName("raonkeditor_")); RAONKEDITOR.CEditorID = d; a = RAONKEDITOR.util.isExistEditorName(d, b); if (1 == a) alert("editor's name is empty. Please, check editor's name"); else {
			if (2 == a) if ("1" == b.IgnoreSameEditorName) d = RAONKEDITOR.util.getNewNextEditorName(d); else {
				alert("editor's name is already exist. Please, check editor's name");
				return
			} 3 != a && (RAONKEDITOR.RAONKMULTIPLEID.push(d), RAONKEDITOR.RAONKMULTIPLE["raonk_frame_" + d] = d, RAONKEDITOR.RAONKHOLDER[d] = b.EditorHolder); RAONKEDITOR.IsEditorLoadedHashTable && RAONKEDITOR.IsEditorLoadedHashTable.setItem(d, ""); try { null == RAONKEDITOR.UserConfigHashTable && (RAONKEDITOR.UserConfigHashTable = new RAONKEDITOR.util.hashTable, RAONKEDITOR.EditorContentsHashTable = new RAONKEDITOR.util.hashTable), b.Id = d, RAONKEDITOR.UserConfigHashTable.setItem(d, b) } catch (e) { } b.XhrWithCredentials && (RAONKEDITOR.ajax.xhrWithCredentials =
				!0); var f = new RAONKEditor_CONFIG(b); if (0 < b.InitServerXml.length) if (-1 < b.InitServerXml.indexOf("f=")) try { var h = b.InitServerXml.split("?"), k = h[0], l = h[1].split("&"), n = l.length; a = ""; c = "&"; for (h = 0; h < n; h++) { var p = l[h].split("="); "f" == p[0] ? a = p[1] : c += p[0] + "=" + p[1] } var m = "kc" + RAONKSolution.Agent.formfeed + "c06" + RAONKSolution.Agent.vertical, m = m + ("k14" + RAONKSolution.Agent.formfeed + a), m = RAONKEDITOR.util.makeEncryptParam(m); f.config_url = k + "?k00=" + m; 1 < c.length && (f.config_url += c) } catch (q) { alert("Error occurred reading the Editor Settings") } else {
					alert("Error occurred reading the Editor Settings");
					return
				} else 0 < b.InitXml.length && (0 == b.InitXml.indexOf("http") ? f.config_url = b.InitXml : f.config_url = RAONKEDITOR.rootPath + "config/" + b.InitXml); if (!Array.prototype.indexOf) try { for (var r in f) "[object array]" == Object.prototype.toString.call(f[r]).toLowerCase() && (f[r].indexOf = function(a, b) { b = b || 0; for (var c = this.length; b < c;) { if (this[b] === a) return b; ++b } return -1 }) } catch (u) { } "1" == b.UseConfigTimeStamp ? -1 < f.config_url.indexOf("?") ? f.config_url += "&t=" + RAONKEDITOR.util.getTimeStamp() : f.config_url += "?t=" + RAONKEDITOR.util.getTimeStamp() :
					-1 < f.config_url.indexOf("?") ? f.config_url += "&ver=" + RAONKEDITOR.ReleaseVer : f.config_url += "?ver=" + RAONKEDITOR.ReleaseVer; m = null; m = 0 < b.InitServerXml.length ? RAONKEDITOR.ajax.load(f.config_url) : RAONKEDITOR.ajax.loadXml(f.config_url); if (null == m) alert("ErrCode : 1000"); else {
						if ("object" == typeof m) "" == m.xml && (m = RAONKEDITOR.ajax.load(f.config_url), m = RAONKEDITOR.util.stringToXML(m)); else if (m = RAONKEDITOR.util.parseDataFromServer(m), 0 == m.indexOf("[OK]")) m = m.substring(4), m = RAONKEDITOR.util.makeDecryptReponseMessage(m),
							m = m.substring(m.indexOf("<?")), m = RAONKEDITOR.util.stringToXML(m); else { if (0 == m.indexOf("[FAIL]")) { alert("Error occurred reading the Editor Settings"); return } (m = RAONKEDITOR.util.stringToXML(m)) || alert("Error occurred reading the Editor Settings") } this.configXml = m; var t = RAONKEDITOR.util.xml.getAllNodes(m), g = { "JS|InterworkingModuleFirst": { config: "interworkingModuleFirst" }, "JS|InterworkingModuleSecond": { config: "interworkingModuleSecond" }, "JS|InterworkingModuleThird": { config: "interworkingModuleThird" } };
						g["XML|xss_protection.xss_use@check_attribute"] = g["JS|XssCheckAttribute"] = { config: "xss_check_attribute", configFn: function(a) { return a.split(",") } }; g["XML|xss_protection.xss_use"] = g["JS|XssUse"] = { config: "xss_use", configFn: function(a) { return "" == a ? "0" : a } }; g["XML|xss_protection.xss_remove_tags"] = g["JS|XssRemoveTags"] = { config: "xss_remove_tags", configFn: function(a) { return "0" == a ? "" : a.toLowerCase() } }; g["XML|xss_protection.xss_remove_events"] = g["JS|XssRemoveEvents"] = {
							config: "xss_remove_events", configFn: function(a) {
								return "all" ==
									a.toLowerCase() ? "onabort,onactivate,onafterprint,onafterupdate,onbeforeactivate,onbeforecopy,onbeforecut,onbeforedeactivate,onbeforeeditfocus,onbeforepaste,onbeforeprint,onbeforeunload,onbeforeupdate,onbegin,onblur,onbounce,oncellchange,onchange,onclick,oncontentready,oncontentsave,oncontextmenu,oncontrolselect,oncopy,oncut,ondataavailable,ondatasetchanged,ondatasetcomplete,ondblclick,ondeactivate,ondetach,ondocumentready,ondrag,ondragdrop,ondragend,ondragenter,ondragleave,ondragover,ondragstart,ondrop,onend,onerror,onerrorupdate,onfilterchange,onfinish,onfocus,onfocusin,onfocusout,onhelp,onhide,onkeydown,onkeypress,onkeyup,onlayoutcomplete,onload,onlosecapture,onmediacomplete,onmediaerror,onmedialoadfailed,onmousedown,onmouseenter,onmouseleave,onmousemove,onmouseout,onmouseover,onmouseup,onmousewheel,onmove,onmoveend,onmovestart,onopenstatechange,onoutofsync,onpaste,onpause,onplaystatechange,onpropertychange,onreadystatechange,onrepeat,onreset,onresize,onresizeend,onresizestart,onresume,onreverse,onrowclick,onrowenter,onrowexit,onrowout,onrowover,onrowsdelete,onrowsinserted,onsave,onscroll,onseek,onselect,onselectionchange,onselectstart,onshow,onstart,onstop,onsubmit,onsyncrestored,ontimeerror,ontrackchange,onunload,onurlflip,formaction,onwheel" :
									"0" == a ? "" : a.toLowerCase()
							}
						}; g["XML|xss_protection.xss_use@allow_events_attribute"] = g["JS|XssAllowEventsAttribute"] = { config: "xss_allow_events_attribute" }; g["XML|xss_protection.xss_allow_url.allow_url"] = g["JS|XssAllowUrl"] = { config: "xss_allow_url", valueType: "array", jsFn: function(a) { return a = a.split(",") } }; g["JS|ZIndex"] = g["JS|zIndex"] = { config: "zIndex", configFn: function(a) { if (1E4 <= (a = parseInt(a, 10))) return a } }; g["JS|EditorBorder"] = { config: "editorborder" }; g["XML|top_menu.menu"] = g["JS|TopMenuItem"] = {
							config: "topMenuItem",
							valueType: "array", jsFn: function(a) { return a.split(",") }
						}; g["XML|remove_item.item"] = g["JS|RemoveItem"] = { config: "removeItem", valueType: "array", jsFn: function(a) { return a.split(",") } }; g["XML|remove_context_item.item"] = g["JS|RemoveContextItem"] = { config: "removeContextItem", valueType: "array", jsFn: function(a) { return a.split(",") } }; g["XML|setting.figure@use"] = g["JS|Figure.Use"] = { config: "figure.use" }; g["XML|setting.figure@figure_style"] = g["JS|Figure.FigureStyle"] = { config: "figure.figurestyle" }; g["XML|setting.figure@figcaption_style"] =
							g["JS|Figure.FigcaptionStyle"] = { config: "figure.figcaptionstyle" }; g["XML|setting.figure@default_caption"] = g["JS|Figure.DefaultCaption"] = { config: "figure.defaultcaption" }; g["XML|setting.mime_use"] = g["JS|MimeUse"] = { config: "mimeUse" }; g["XML|setting.print_preview"] = g["JS|PrintPreview"] = { config: "printPreview" }; g["XML|setting.print_header_left"] = g["JS|PrintHeaderLeft"] = { config: "printHeaderLeft" }; g["XML|setting.print_header_center"] = g["JS|PrintHeaderCenter"] = { config: "printHeaderCenter" }; g["XML|setting.print_header_right"] =
								g["JS|PrintHeaderRight"] = { config: "printHeaderRight" }; g["XML|setting.print_footer_left"] = g["JS|PrintFooterLeft"] = { config: "printFooterLeft" }; g["XML|setting.print_footer_center"] = g["JS|PrintFooterCenter"] = { config: "printFooterCenter" }; g["XML|setting.print_footer_right"] = g["JS|PrintFooterRight"] = { config: "printFooterRight" }; g["XML|setting.print_margin_ltrb"] = g["JS|PrintMarginltrb"] = { config: "printMarginltrb" }; g["XML|setting.use_form_print"] = g["JS|UseFormPrint"] = { config: "useFormPrint" }; g["XML|setting.auto_list@use"] =
									g["JS|AutoList.Use"] = { config: "autoList.use" }; g["XML|setting.undo.undo_count"] = g["JS|UndoCount"] = { config: "undoCount", configFn: function(a) { return parseInt(a, 10) } }; g["XML|setting.undo.allow_delete_count"] = g["JS|AllowDeleteCount"] = { config: "allowDeleteCount" }; g["XML|setting.undo.light_mode"] = g["JS|UseUndoLightMode"] = { config: "useUndoLightMode" }; g["XML|setting.word_count"] = g["JS|WordCount.Use"] = { config: "wordCount.use" }; g["XML|setting.word_count@limit"] = g["JS|WordCount.Limit"] = {
										config: "wordCount.limit", configFn: function(a) {
											if ("1" ==
												f.wordCount.use) return a
										}
									}; g["XML|setting.word_count@limit_char"] = g["JS|WordCount.LimitChar"] = { config: "wordCount.limitchar", configFn: function(a) { if ("1" == f.wordCount.use) return f.wordCount.limitcount = a } }; g["XML|setting.word_count@limit_byte"] = g["JS|WordCount.LimitByte"] = {
										config: "wordCount.limitbyte", configFn: function(a) {
											if ("1" == f.wordCount.use) return "" == f.wordCount.limitchar || "0" == f.wordCount.limitchar ? (f.wordCount.limitcount = a, f.wordCount.limitunit = "byte", f.undoCount > parseInt(a) && (f.undoCount = parseInt(a))) :
												(f.wordCount.limitcount = f.wordCount.limitchar, f.wordCount.limitunit = "char"), a
										}
									}; g["XML|setting.word_count@limit_line"] = g["JS|WordCount.LimitLine"] = { config: "wordCount.limitline", configFn: function(a) { if ("1" == f.wordCount.use) return a } }; g["XML|setting.word_count@count_white_space"] = g["JS|WordCount.CountWhiteSpace"] = { config: "wordCount.countwhitespace", configFn: function(a) { if ("1" == f.wordCount.use && ("" != f.wordCount.limit || "" != f.wordCount.limitcount)) return a } }; g["XML|setting.word_count@limit_message"] = g["JS|WordCount.LimitMessage"] =
										{ config: "wordCount.limitmessage", configFn: function(a) { if ("1" == f.wordCount.use && "1" == f.wordCount.limit) return a } }; g["XML|font_family@use_local_font"] = g["JS|UseLocalFont"] = { config: "useLocalFont" }; g["XML|font_family@use_keyin"] = g["JS|UseFontFamilyKeyin"] = { config: "useFontFamilyKeyin" }; g["XML|font_size@use_keyin"] = g["JS|UseFontSizeKeyin"] = { config: "useFontSizeKeyin" }; g["XML|line_height@use_keyin"] = g["JS|UseLineHeightKeyin"] = { config: "useLineHeightKeyin" }; g["XML|font_size@use_inc_dec"] = g["JS|UseFontSizeIncDec"] =
											{ config: "useFontSizeIncDec" }; g["XML|line_height@use_inc_dec"] = g["JS|UseLineHeightIncDec"] = { config: "useLineHeightIncDec" }; g["XML|letter_spacing@use_inc_dec"] = g["JS|UseLetterSpacingIncDec"] = { config: "useLetterSpacingIncDec" }; g["XML|font_family@use_recently_font"] = g["JS|UseRecentlyFont"] = { config: "useRecentlyFont" }; g["XML|setting.forbidden_word"] = g["JS|ForbiddenWord"] = { config: "forbiddenWord" }; g["XML|setting.personal_data"] = g["JS|PersonalData"] = { config: "personalData" }; g["XML|setting.use_remove_style"] =
												g["JS|RemoveStyle.Use"] = { config: "removeStyle.use" }; g["XML|setting.use_remove_style@tag"] = g["JS|RemoveStyle.Tag"] = { config: "removeStyle.tag", configFn: function(a) { if ("1" == f.removeStyle.use) return a } }; g["XML|setting.use_remove_style@style"] = g["JS|RemoveStyle.Style"] = { config: "removeStyle.style", configFn: function(a) { if ("1" == f.removeStyle.use) return a } }; g["XML|setting.allow_unable_to_delete_msg"] = g["JS|AllowUnableToDeleteMsg"] = { config: "allowUnableToDeleteMsg" }; g["XML|setting.font_family"] = g["JS|DefaultFontFamily"] =
													{ config: "defaultFontFamily", configFn: function(a) { "\ub9d1\uc740\uace0\ub515" == a && (a = "\ub9d1\uc740 \uace0\ub515"); return a } }; g["XML|setting.font_size"] = g["JS|DefaultFontSize"] = { config: "defaultFontSize", configFn: function(a) { 0 > a.toString().indexOf("pt") && 0 > a.toString().indexOf("px") && (a += "pt"); return a } }; g["XML|setting.font_size@inc_dec_gap"] = g["JS|FontSizeIncDecGap"] = { config: "fontSizeIncDecGap" }; g["XML|setting.line_height"] = g["JS|DefaultLineHeight"] = { config: "defaultLineHeight" }; g["XML|setting.line_height@mode"] =
														g["JS|LineHeightMode"] = { config: "lineHeightMode" }; g["XML|setting.line_height@inc_dec_gap"] = g["JS|LineHeightIncDecGap"] = { config: "lineHeightIncDecGap" }; g["XML|setting.letter_spacing@inc_dec_gap"] = g["JS|LetterSpacingIncDecGap"] = { config: "letterSpacingIncDecGap" }; g["XML|setting.font_margin@top"] = g["JS|DefaultFontMarginTop"] = { config: "defaultFontMarginTop", configFn: function(a) { 0 > a.indexOf("pt") && 0 > a.indexOf("px") && (a += "px"); return a } }; g["XML|setting.font_margin@bottom"] = g["JS|DefaultFontMarginBottom"] = {
															config: "defaultFontMarginBottom",
															configFn: function(a) { 0 > a.indexOf("pt") && 0 > a.indexOf("px") && (a += "px"); return a }
														}; g["XML|setting.allow_img_size"] = g["JS|AllowImgSize"] = { config: "allowImgSize" }; g["XML|setting.frame_fullscreen"] = g["JS|FrameFullScreen"] = { config: "frameFullScreen" }; g["XML|setting.runtimes"] = g["JS|Runtimes"] = { config: "runtimes" }; g["JS|DropZoneTransparency"] = { config: "dropZoneTransparency", configFn: function(a) { a = parseInt(a, 10); 1 > a ? a = 1 : 255 < a && (a = 255); return a } }; g["XML|tool_bar_1.tool"] = g["JS|ToolBar1"] = {
															config: "toolbarArr1",
															valueType: "array", configFn: function(a) { "string" == typeof a && (a = a.split(",")); for (var b = [], c = 0; c < a.length; c++) { var e = a[c]; "chart" == e && (e = "p_chart"); b.push(e) } return b }
														}; g["XML|tool_bar_2.tool"] = g["JS|ToolBar2"] = { config: "toolbarArr2", valueType: "array", configFn: function(a) { "string" == typeof a && (a = a.split(",")); for (var b = [], c = 0; c < a.length; c++) { var e = a[c]; "chart" == e && (e = "p_chart"); b.push(e) } return b } }; g["XML|tool_bar_1.tool:apply_format@except_style"] = g["XML|tool_bar_2.tool:apply_format@except_style"] = g["JS|ApplyFormatExceptStyle"] =
															{ config: "applyFormatExceptStyle", configFn: function(a) { return a.split(",") } }; g["XML|tool_bar_1.tool:full_screen@top"] = g["XML|tool_bar_2.tool:full_screen@top"] = g["JS|FullScreenTop"] = { config: "fullScreenTop", configFn: function(a) { return RAONKEDITOR.util.parseIntOr0(a) } }; g["XML|tool_bar_1.tool:full_screen@left"] = g["XML|tool_bar_2.tool:full_screen@left"] = g["JS|FullScreenLeft"] = { config: "fullScreenLeft", configFn: function(a) { return RAONKEDITOR.util.parseIntOr0(a) } }; g["XML|tool_bar_1.tool:full_screen@right"] =
																g["XML|tool_bar_2.tool:full_screen@right"] = g["JS|FullScreenRight"] = { config: "fullScreenRight", configFn: function(a) { return RAONKEDITOR.util.parseIntOr0(a) } }; g["XML|tool_bar_1.tool:full_screen@bottom"] = g["XML|tool_bar_2.tool:full_screen@bottom"] = g["JS|FullScreenBottom"] = { config: "fullScreenBottom", configFn: function(a) { return RAONKEDITOR.util.parseIntOr0(a) } }; g["XML|plugin_ex.tool"] = g["JS|PluginToolEx"] = {
																	config: "pluginToolExArr", valueType: "array", configFn: function(a) {
																		"string" == typeof a && (a = a.split(","));
																		for (var b = [], c = 0; c < a.length; c++)b.push(a[c]); return b
																	}
																}; g["XML|setting.editor_tab_disable"] = g["JS|EditorTabDisable"] = { config: "EditorTabDisable" }; g["XML|setting.editor_tab_disable@tab_space"] = g["JS|TabSpace"] = { config: "tabSpace", configFn: function(a) { var b = a; if (0 < b) { a = ""; for (var c = 0; c < b; c++)a += "&nbsp;"; return a } } }; g["XML|setting.context_menu_disable"] = g["JS|ContextMenuDisable"] = { config: "contextMenuDisable" }; g["XML|setting.ie_compatible"] = g["JS|IECompatible"] = { config: "ieCompatible" }; g["XML|setting.auto_url_detect"] =
																	g["JS|AutoUrlDetect"] = { config: "autoUrlDetect" }; g["XML|setting.office_linefix"] = g["JS|OfficeLineFix"] = { config: "officeLineFix" }; g["XML|setting.paste_remove_empty_tag"] = g["JS|PasteRemoveEmptyTag"] = { config: "pasteRemoveEmptyTag" }; g["XML|setting.scroll_overflow"] = g["JS|ScrollOverflow"] = { config: "scrollOverflow" }; g["XML|setting.default_imemode"] = g["JS|DefaultImemode"] = { config: "defaultImemode" }; g["XML|setting.disable_tabletap"] = g["JS|DisableTabletap"] = { config: "disableTabletap" }; g["XML|setting.auto_body_fit"] =
																		g["JS|AutoBodyFit"] = { config: "autoBodyFit" }; g["XML|setting.auto_body_fit@use_noncreation_area_background"] = g["JS|UseNoncreationAreaBackground"] = { config: "useNoncreationAreaBackground" }; g["XML|setting.disable_keydown"] = g["JS|DisableKeydown"] = { config: "disableKeydown" }; g["XML|setting.custom_event.image.ondbclick"] = g["JS|CustomEventImageOndbclick"] = { config: "customEventCmd.image.ondbclick" }; g["XML|setting.custom_event.hyperlink.ondbclick"] = g["JS|CustomEventHyperlinkOndbclick"] = { config: "customEventCmd.hyperLink.ondbclick" };
						g["XML|setting.pageurl.script.symbol"] = g["JS|SymbolUrl"] = { config: "symbolUrl" }; g["XML|setting.pageurl.script.symbol@custom_css_url"] = g["JS|SymbolCustomCssUrl"] = { config: "symbolCustomCssUrl" }; g["XML|setting.icon_name"] = g["JS|IconName"] = { config: "style.iconName" }; g["XML|setting.image_base_url"] = g["JS|ImageBaseUrl"] = { config: "imageBaseUrl" }; g["XML|setting.drag_resize"] = g["JS|DragResize"] = { config: "dragResize" }; g["XML|setting.drag_resize@apply_browser"] = g["JS|DragResizeApplyBrowser"] = {
							config: "dragResizeApplyBrowser",
							configFn: function(a) { return a.split(",") }
						}; g["XML|setting.drag_resize@movable"] = g["JS|DragResizeMovable"] = { config: "dragResizeMovable" }; g["XML|setting.drag_resize@apply_div_class"] = g["JS|DragResizeApplyDivClass"] = { config: "dragResizeApplyDivClass" }; g["XML|font_family.font"] = g["JS|FontFamilyList"] = { config: "fontFamilyList", valueType: "array", jsFn: function(a) { return a.split(",") } }; g["XML|font_size.size"] = g["JS|FontSizeList"] = { config: "fontSizeList", valueType: "array", jsFn: function(a) { return f.fontSizeList.concat(a.split(",")) } };
						g["XML|line_height.line"] = g["JS|LineHeightList"] = { config: "lineHeightList", valueType: "array", jsFn: function(a) { return a.split(",") } }; g["XML|zoom.item"] = g["JS|ZoomList"] = { config: "zoomList", valueType: "array", jsFn: function(a) { return a.split(",") } }; g["JS|FormattingList"] = { config: "formattingList", jsFn: function(a) { f.formattingList.push(""); return f.formattingList.concat(a.split(",")) } }; g["XML|letter_spacing.spacing"] = g["JS|LetterSpacingList"] = { config: "letterSpacingList", valueType: "array", jsFn: function(a) { return a.split(",") } };
						g["XML|setting.use_ruler"] = g["JS|Ruler.Use"] = { config: "ruler.use", jsFn: function(a) { f.ruler.configType = "JS"; return a }, configFn: function(a) { if ("1" == a) return f.ruler.view = "1", a } }; g["XML|setting.use_ruler@ruler_init_position"] = g["JS|Ruler.InitPosition"] = { config: "ruler.rulerInitPosition", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use) return a } }; g["XML|setting.use_ruler@view_pointer"] = g["JS|Ruler.ViewPointer"] =
							{ config: "ruler.viewPointer", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use) return a } }; g["XML|setting.use_ruler@view_guide_line"] = g["JS|Ruler.ViewGuideLine"] = { config: "ruler.viewGuideLine", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use) return a } }; g["XML|setting.use_ruler@guide_line_style"] = g["JS|Ruler.GuideLineStyle"] =
								{ config: "ruler.guideLineStyle", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use) return a } }; g["XML|setting.use_ruler@view_ruler"] = g["JS|Ruler.ViewRuler"] = { config: "ruler.viewRuler", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use) return a } }; g["XML|setting.use_ruler@mode"] = g["JS|Ruler.Mode"] = {
									config: "ruler.mode",
									jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) {
										if ("1" == f.ruler.use) {
											if ("2" == a) {
												if (RAONKEDITOR.browser.ie && 7 >= RAONKEDITOR.browser.ieVersion) return "1"; var b = f.ruler.rulerInitPosition.split(","), c = b.length; 1 == c ? (b.splice(0, 0, "10"), b.splice(0, 0, "10"), b.push("10")) : 2 == c ? (c = b[0], b.splice(0, 0, c), b.push(c)) : 3 == c ? (c = b[0], b.push(c)) : 4 <= c && b.splice(4, 4); f.ruler.rulerInitPosition = b.join(","); f.ruler.viewPointer = "1"; f.ruler.viewGuideLine =
													"1"; f.ruler.viewRuler = "1"; f.autoBodyFit = "1"
											} return a
										}
									}
								}; g["XML|setting.use_ruler@guide_line_color"] = g["JS|Ruler.GuideLineColor"] = { config: "ruler.guideLineColor", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use) return a } }; g["XML|setting.use_ruler@move_guide_line_color"] = g["JS|Ruler.MoveGuideLineColor"] = {
									config: "ruler.moveGuideLineColor", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) {
										if ("JS" !=
											f.ruler.configType) return a
									}, configFn: function(a) { if ("1" == f.ruler.use && "2" == f.ruler.mode) return a }
								}; g["XML|setting.use_ruler@move_guide_line_style"] = g["JS|Ruler.MoveGuideLineStyle"] = { config: "ruler.moveGuideLineStyle", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use && "2" == f.ruler.mode) return a } }; g["XML|setting.use_ruler@use_inoutdent"] = g["JS|Ruler.UseInoutdent"] = {
									config: "ruler.useInoutdent", jsFn: function(a) {
										if ("JS" ==
											f.ruler.configType) return a
									}, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use && "2" == f.ruler.mode) return a }
								}; g["XML|setting.use_ruler@move_gap"] = g["JS|Ruler.MoveGap"] = { config: "ruler.moveGap", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use && "2" == f.ruler.mode) return a } }; g["XML|setting.use_ruler@use_resize_event"] = g["JS|Ruler.UseResizeEvent"] = {
									config: "ruler.useResizeEvent",
									jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use && "2" == f.ruler.mode) return a }
								}; g["XML|setting.use_ruler@default_view"] = g["JS|Ruler.DefaultView"] = { config: "ruler.defaultView", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use) return a } }; g["XML|setting.use_ruler@auto_fit_mode"] = g["JS|Ruler.AutoFitMode"] =
									{ config: "ruler.autoFitMode", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use) return a } }; g["XML|setting.use_ruler@fix_editor_width"] = g["JS|Ruler.FixEditorWidth"] = { config: "ruler.fixEditorWidth", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use && "2" == f.ruler.mode && "1" == f.ruler.autoFitMode && "0" == f.useNoncreationAreaBackground) return a } };
						g["XML|setting.use_ruler@use_vertical"] = g["JS|Ruler.UseVertical"] = { config: "ruler.useVertical", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) { if ("1" == f.ruler.use) return a } }; g["XML|setting.use_ruler@use_mouse_guide"] = g["JS|Ruler.UseMouseGuide"] = {
							config: "ruler.useMouseGuide", jsFn: function(a) { if ("JS" == f.ruler.configType) return a }, xmlFn: function(a) { if ("JS" != f.ruler.configType) return a }, configFn: function(a) {
								if ("1" == f.ruler.use &&
									"2" == f.ruler.mode) return a
							}
						}; g["XML|setting.use_horizontal_line"] = { config: "horizontalLine.use" }; g["XML|setting.use_horizontal_line@url"] = g["JS|UseHorizontalLine"] = { config: "horizontalLine.url", jsFn: function(a) { f.horizontalLine.use = "1"; return a }, configFn: function(a) { "0" == a && (f.horizontalLine.use = "0"); return a } }; g["XML|setting.use_horizontal_line@height"] = g["JS|UseHorizontalLineHeight"] = { config: "horizontalLine.height", configFn: function(a) { f.horizontalLine.use = "2"; return a } }; g["XML|setting.use_horizontal_line@style"] =
							g["JS|UseHorizontalLineStyle"] = { config: "horizontalLine.style" }; g["XML|setting.use_horizontal_line@repeat"] = g["JS|UseHorizontalLineRepeat"] = { config: "horizontalLine.repeat" }; g["XML|setting.enter_tag"] = g["JS|EnterTag"] = { config: "enterTag", configFn: function(a) { a = a.toLowerCase(); return "br" != a && "div" != a ? "" : a } }; g["XML|setting.set_default_style"] = g["JS|SetDefaultStyle.Value"] = { config: "setDefaultStyle.value" }; g["XML|setting.set_default_style@body_id"] = g["JS|SetDefaultStyle.BodyId"] = {
								config: "setDefaultStyle.body_id",
								configFn: function(a) { if ("" != f.setDefaultStyle.value && "0" != f.setDefaultStyle.value) return a }
							}; g["XML|setting.set_default_style@user_style"] = g["JS|SetDefaultStyle.UserStyle"] = { config: "setDefaultUserStyle", configFn: function(a) { if ("" != f.setDefaultStyle.value && "0" != f.setDefaultStyle.value) return a.split(",") } }; g["XML|setting.set_default_style@line_height_mode"] = g["JS|SetDefaultStyle.LineHeightMode"] = { config: "setDefaultStyle.line_height_mode", configFn: function(a) { if ("" != f.setDefaultStyle.value && "0" != f.setDefaultStyle.value) return a } };
						g["XML|setting.set_default_style@set_style"] = g["JS|SetDefaultStyle.SetStyle"] = { config: "setDefaultStyle.set_style", configFn: function(a) { if ("" == f.setDefaultStyle.value || "0" == f.setDefaultStyle.value) return a } }; g["XML|setting.drag_and_drop_allow"] = g["JS|DragAndDropAllow"] = { config: "dragAndDropAllow" }; g["XML|setting.limit_paste_html_length"] = g["JS|LimitPasteHtml"] = { config: "limitPasteHtmlLength.value" }; g["XML|setting.limit_paste_html_length@length"] = g["JS|LimitPasteHtmlLength"] = {
							config: "limitPasteHtmlLength.length",
							configFn: function(a) { if (f.limitPasteHtmlLength.value) return parseInt(a, 10) }
						}; g["XML|setting.wrap_ptag_to_source"] = g["JS|WrapPtagToSource"] = { config: "wrapPtagToSource" }; g["XML|setting.wrap_ptag_to_source@apply_to_get_api"] = g["JS|WrapPtagToGetApi"] = { config: "wrapPtagToGetApi", xmlFn: function(a) { if (RAONKEDITOR.browser.gecko) return a } }; g["XML|setting.wrap_ptag_to_source@skip_tag"] = g["JS|WrapPtagSkipTag"] = { config: "wrapPtagSkipTag" }; g["XML|setting.paste_to_image"] = g["JS|PasteToImage"] = {
							config: "pasteToImage",
							configFn: function(a) { if (RAONKEDITOR.browser.mobile) return "0"; if ("0" == a || "2" == a) return a }
						}; g["XML|setting.paste_to_image@excel_image_fix"] = g["JS|ExcelImageFix"] = { config: "excelImageFix" }; g["XML|setting.paste_to_image@popup_mode"] = g["JS|PasteToImagePopupMode"] = { config: "pasteToImagePopupMode" }; g["XML|setting.agent_temp_root_directory"] = g["JS|AgentTempRootDirectory"] = { config: "agentTempRootDirectory" }; g["XML|setting.target_domain_for_edit"] = g["JS|TargetDomainForEdit"] = { config: "targetDomainForEdit" }; g["XML|setting.color_picker_input_kind"] =
							g["JS|ColorPickerInputKind"] = { config: "colorPickerInputKind" }; g["XML|setting.cell_selection_mode"] = g["JS|CellSelectionMode"] = { config: "cellSelectionMode" }; g["XML|setting.remove_space_in_tagname"] = g["JS|RemoveSpaceInTagname"] = { config: "removeSpaceInTagname" }; g["XML|setting.view_mode_browser_menu"] = g["JS|ViewModeBrowserMenu"] = { config: "viewModeBrowserMenu" }; g["XML|setting.view_mode_allow_copy"] = g["JS|ViewModeAllowCopy"] = { config: "viewModeAllowCopy" }; g["XML|setting.event_for_paste_image"] = g["JS|EventForPasteImage"] =
								{ config: "eventForPasteImage" }; g["XML|setting.remove_colgroup"] = g["JS|RemoveColgroup"] = { config: "removeColgroup" }; g["XML|setting.use_htmlprocess_worker"] = g["JS|UseHtmlProcessByWorker"] = { config: "useHtmlProcessByWorker", configFn: function(a) { return 0 == (RAONKEDITOR.browser.HTML5Supported && "Worker" in window) ? "0" : a } }; g["XML|setting.use_htmlprocess_worker@use_set_api"] = g["JS|UseHtmlProcessByWorkerSetApi"] = { config: "useHtmlProcessByWorkerSetApi", configFn: function(a) { if ("1" == f.useHtmlProcessByWorker) return a } };
						g["XML|setting.un_orderedlist_property@ulClass"] = g["JS|UnOrderListDefaultClass"] = { config: "unOrderListDefaultClass" }; g["XML|setting.un_orderedlist_property@olClass"] = g["JS|OrderListDefaultClass"] = { config: "orderListDefaultClass" }; g["XML|setting.use_html_correction"] = g["JS|UseHtmlCorrection"] = { config: "useHtmlCorrection" }; g["XML|setting.use_html_correction@remove_incorrect_attribute"] = g["JS|RemoveIncorrectAttribute"] = { config: "removeIncorrectAttribute", configFn: function(a) { if ("1" == f.useHtmlCorrection) return a } };
						g["XML|setting.use_html_correction@replace_space"] = g["JS|ReplaceSpace"] = { config: "replaceSpace", configFn: function(a) { if ("1" == f.useHtmlCorrection) return a } }; g["XML|setting.use_html_correction@skip_tag"] = g["JS|SkipTagInParser"] = { config: "skipTagInParser", configFn: function(a) { if ("1" == f.useHtmlCorrection) return a } }; g["XML|setting.use_html_correction@limit_length"] = g["JS|HtmlCorrectionLimitLength"] = { config: "htmlCorrectionLimitLength", configFn: function(a) { if ("1" == f.useHtmlCorrection) return RAONKEDITOR.util.parseIntOr0(a) } };
						g["XML|setting.formlist_url"] = g["JS|FormListUrl"] = { config: "forms_url", configFn: function(a) { "/" == a.substring(0, 1) && (a = location.protocol + "//" + location.host + a); return a } }; g["XML|setting.emoticon_url"] = g["JS|EmoticonUrl"] = { config: "emoticon_url", configFn: function(a) { "/" == a.substring(0, 1) && (a = location.protocol + "//" + location.host + a); return a } }; g["XML|setting.set_auto_save"] = g["JS|SetAutoSave.Mode"] = { config: "setAutoSave.mode" }; g["XML|setting.set_auto_save@interval"] = g["JS|SetAutoSave.Interval"] = {
							config: "setAutoSave.interval",
							configFn: function(a) { if ("1" == f.setAutoSave.mode && (a = Math.floor(10 * a) / 10, .5 <= a)) return a }
						}; g["XML|setting.set_auto_save@max_count"] = g["JS|SetAutoSave.MaxCount"] = { config: "setAutoSave.maxCount", configFn: function(a) { if ("0" != f.setAutoSave.mode) return 8 < RAONKEDITOR.util.parseIntOr0(a) && (a = "8"), a } }; g["XML|setting.set_auto_save@unique_key"] = g["JS|SetAutoSave.UniqueKey"] = { config: "setAutoSave.unique_key", configFn: function(a) { if ("0" != f.setAutoSave.mode) return a } }; g["XML|setting.set_auto_save@use_encrypt"] = g["JS|SetAutoSave.UseEncrypt"] =
							{ config: "setAutoSave.use_encrypt", configFn: function(a) { if ("0" != f.setAutoSave.mode) return a } }; g["XML|setting.set_auto_save@use_manually_save"] = g["JS|SetAutoSave.UseManuallySave"] = { config: "setAutoSave.useManuallySave" }; g["XML|setting.set_auto_save@use_manually_save_shortcut_key"] = g["JS|SetAutoSave.UseManuallySaveShortcutKey"] = { config: "setAutoSave.useManuallySaveShortcutKey", configFn: function(a) { if ("1" == f.setAutoSave.useManuallySave) return a } }; g["XML|setting.set_auto_save@save_and_start_interval"] = g["JS|SetAutoSave.SaveAndStartInterval"] =
								{ config: "setAutoSave.saveAndStartInterval" }; g["XML|setting.insert_carriage_return"] = g["JS|InsertCarriageReturn"] = { config: "insertCarriageReturn" }; g["XML|setting.return_event@mouse_event"] = g["JS|ReturnEventMouse"] = { config: "returnEvent.mouse_event" }; g["XML|setting.return_event@keyboard_event"] = g["JS|ReturnEventKeyboard"] = { config: "returnEvent.key_event" }; g["XML|setting.return_event@command_event"] = g["JS|ReturnEventCommand"] = { config: "returnEvent.command_event" }; g["XML|setting.use_correct_in_outdent"] =
									g["JS|UseCorrectInOutdent"] = { config: "useCorrectInOutdent" }; g["XML|setting.browser_bugfixed.ie11_jaso"] = g["JS|Ie11BugFixedJASO"] = { config: "ie11_BugFixed_JASO", configFn: function(a) { if (RAONKEDITOR.browser.ie && (7 == RAONKEDITOR.browser.trident || 12 <= RAONKEDITOR.browser.ieVersion)) return a } }; g["XML|setting.browser_bugfixed.ie11_jaso@replace_br"] = g["JS|Ie11BugFixedReplaceBr"] = { config: "ie11_BugFixed_ReplaceBr", configFn: function(a) { if ("1" == f.ie11_BugFixed_JASO) return a } }; g["XML|setting.browser_bugfixed.ie11_jaso@delete_table_align"] =
										g["JS|Ie11BugFixedDeleteTableAlign"] = { config: "ie11_BugFixed_DeleteTableAlign", allowEmpty: !0, jsFn: function(a) { "" != a && (this.inEmpty = !0); return a }, configFn: function(a) { if ("1" == f.ie11_BugFixed_JASO) return "" == a ? "1" == f.ie11_BugFixed_JASO ? "1" : "0" : a } }; g["XML|setting.browser_bugfixed.ie11_jaso@replace_align_margin"] = g["JS|Ie11BugFixedReplaceAlignMargin"] = { config: "ie11_BugFixed_ReplaceAlignMargin" }; g["XML|setting.browser_bugfixed.ie11_typing_bug_in_table"] = g["JS|Ie11BugFixedTypingBugInTable"] = {
											config: "ie11_BugFixed_typing_bug_in_table",
											configFn: function(a) { if ("0" != f.ie11_BugFixed_JASO && RAONKEDITOR.browser.ie && 11 == RAONKEDITOR.browser.ieVersion) return a }
										}; g["XML|setting.browser_bugfixed.ie_remove_hyfont"] = g["JS|IeBugFixedHyfont"] = { config: "ie_BugFixed_Hyfont" }; g["XML|setting.browser_bugfixed.ie_remove_hyfont@replace_font"] = g["JS|IeBugFixedHyfontReplaceFont"] = {
											config: "ie_BugFixed_Hyfont_Replace_Font", configFn: function(a) {
												if ("1" == f.ie_BugFixed_Hyfont) {
													a = a.split("|"); for (var b = a.length, c = 0; c < b; c++) {
														var e = a[c].split(","); f.ie_BugFixed_Hyfont_Replace_Font[e[0]] =
															e[1]
													}
												} return f.ie_BugFixed_Hyfont_Replace_Font
											}
										}; g["XML|setting.browser_bugfixed.apply_all_browser"] = g["JS|IeBugFixedApplyAllBrowser"] = { config: "ie_BugFixed_Apply_All_Browser", configFn: function(a) { "1" == a && (f.ie11_BugFixed_JASO = "2", f.ie11_BugFixed_DeleteTableAlign = "1"); return a } }; g["XML|setting.replace_empty_tag_with_space"] = g["JS|ReplaceEmptyTagWithSpace"] = { config: "replaceEmptyTagWithSpace" }; g["XML|setting.image_custom_property_delimiter"] = g["JS|ImageCustomPropertyDelimiter"] = { config: "imageCustomPropertyDelimiter" };
						g["XML|setting.manager_mode@use"] = g["JS|ManagerMode"] = { config: "formMode" }; g["XML|setting.manager_mode.event_list.event"] = g["JS|EventList"] = { config: "eventList", valueType: "array", jsFn: function(a) { if ("1" == f.formMode) return a.split(",") }, configFn: function(a) { if ("1" == f.formMode) return a } }; g["XML|setting.manager_mode.table_lock@default_show_lock_icon_user_mode"] = g["JS|AdminTableLock.DefaultShowLockIconUserMode"] = {
							config: "adminTableLock.defaultShowLockIconUserMode", jsFn: function(a) {
								if (!f.adminTableLock.configType ||
									"JS" == f.adminTableLock.configType) return f.adminTableLock.configType = "JS", a
							}, xmlFn: function(a) { if (!f.adminTableLock.configType || "XML" == f.adminTableLock.configType) return f.adminTableLock.configType = "XML", a }
						}; g["XML|setting.manager_mode.table_lock@default_lock_name"] = g["JS|AdminTableLock.DefaultLockName"] = {
							config: "adminTableLock.defaultLockName", jsFn: function(a) { if (!f.adminTableLock.configType || "JS" == f.adminTableLock.configType) return f.adminTableLock.configType = "JS", a }, xmlFn: function(a) {
								if (!f.adminTableLock.configType ||
									"XML" == f.adminTableLock.configType) return f.adminTableLock.configType = "XML", a
							}
						}; g["XML|setting.manager_mode.table_lock@check_lock_name"] = g["JS|AdminTableLock.CheckLockName"] = { config: "adminTableLock.checkLockName", jsFn: function(a) { if (!f.adminTableLock.configType || "JS" == f.adminTableLock.configType) return f.adminTableLock.configType = "JS", a }, xmlFn: function(a) { if (!f.adminTableLock.configType || "XML" == f.adminTableLock.configType) return f.adminTableLock.configType = "XML", a }, configFn: function(a) { return a.split(",") } };
						g["XML|setting.manager_mode.table_lock@default_table_lock_mode"] = g["JS|AdminTableLock.DefaultTableLockMode"] = { config: "adminTableLock.defaultTableLockMode", jsFn: function(a) { if (!f.adminTableLock.configType || "JS" == f.adminTableLock.configType) return f.adminTableLock.configType = "JS", a }, xmlFn: function(a) { if (!f.adminTableLock.configType || "XML" == f.adminTableLock.configType) return f.adminTableLock.configType = "XML", a } }; g["XML|setting.table_lock_user_mode"] = g["JS|UserTableLock.Use"] = {
							config: "userTableLock.use",
							jsFn: function(a) { f.userTableLock.configType = "JS"; return a }, xmlFn: function(a) { f.userTableLock.configType = "XML"; return a }
						}; g["XML|setting.table_lock_user_mode@lock_name"] = g["JS|UserTableLock.LockName"] = { config: "userTableLock.lockName", jsFn: function(a) { if ("JS" == f.userTableLock.configType) return a }, xmlFn: function(a) { if ("JS" != f.userTableLock.configType) return a }, configFn: function(a) { if ("1" == f.userTableLock.use) return a.split(",") } }; g["XML|setting.table_lock_user_mode@default_table_lock_mode"] = g["JS|UserTableLock.DefaultTableLockMode"] =
							{ config: "userTableLock.defaultTableLockMode", jsFn: function(a) { if ("JS" == f.userTableLock.configType) return a }, xmlFn: function(a) { if ("JS" != f.userTableLock.configType) return a }, configFn: function(a) { if ("1" == f.userTableLock.use) return a } }; g["XML|setting.table_lock_user_mode@default_show_lock_icon"] = g["JS|UserTableLock.DefaultShowLockIcon"] = {
								config: "userTableLock.defaultShowLockIcon", jsFn: function(a) { if ("JS" == f.userTableLock.configType) return a }, xmlFn: function(a) { if ("JS" != f.userTableLock.configType) return a },
								configFn: function(a) { if ("1" == f.userTableLock.use) return a }
							}; g["XML|setting.table_lock_user_mode@allow_change_mode"] = g["JS|UserTableLock.AllowChangeMode"] = { config: "userTableLock.allowChangeMode" }; g["XML|setting.open_document@before_open_event"] = g["JS|OpenDocument.BeforeOpenEvent"] = { config: "openDocument.beforeOpenEvent" }; g["XML|setting.open_document.word@max_size"] = g["JS|OpenDocument.Word.MaxSize"] = { config: "openDocument.word.maxSize", xmlFn: function(a) { return parseInt(a, 0) } }; g["XML|setting.open_document.word@max_page"] =
								g["JS|OpenDocument.Word.MaxPage"] = { config: "openDocument.word.maxPage", xmlFn: function(a) { return parseInt(a, 0) } }; g["XML|setting.open_document.excel@max_size"] = g["JS|OpenDocument.Excel.MaxSize"] = { config: "openDocument.excel.maxSize", xmlFn: function(a) { return parseInt(a, 0) } }; g["XML|setting.open_document.excel@max_sheet"] = g["JS|OpenDocument.Excel.MaxSheet"] = { config: "openDocument.excel.maxSheet", xmlFn: function(a) { return parseInt(a, 0) } }; g["XML|setting.open_document.powerpoint@max_size"] = g["JS|OpenDocument.Powerpoint.MaxSize"] =
									{ config: "openDocument.powerpoint.maxSize", xmlFn: function(a) { return parseInt(a, 0) } }; g["XML|setting.open_document.powerpoint@max_slide"] = g["JS|OpenDocument.Powerpoint.MaxSlide"] = { config: "openDocument.powerpoint.maxSlide", xmlFn: function(a) { return parseInt(a, 0) } }; g["XML|setting.open_document@use_hwp"] = g["JS|OpenDocument.UseHwp"] = { config: "openDocument.useHwp" }; g["XML|setting.open_document@use_html5_fileopen"] = g["JS|OpenDocument.UseHtml5FileOpen"] = { config: "openDocument.useHtml5FileOpen" }; g["XML|setting.remove_last_br_tag"] =
										g["JS|RemoveLastBrTag"] = { config: "removeLastBrTag" }; g["XML|setting.editor_body_editable"] = g["JS|EditorBodyEditable"] = { config: "editorBodyEditableTemp", configFn: function(a) { "0" == a ? (f.editorBodyEditable = !1, f.editorBodyEditableEx = !1) : "1" == a && (f.editorBodyEditable = !0, f.editorBodyEditableEx = !0); return a } }; g["XML|setting.editor_body_editable@mode"] = g["JS|EditorBodyEditableMode"] = { config: "editorBodyEditableMode" }; g["XML|setting.replace_outside_image"] = g["JS|ReplaceOutsideImage"] = {
											config: "replaceOutsideImage.use",
											jsFn: function(a) { f.replaceOutsideImage.configType = "JS"; return a }
										}; g["XML|setting.replace_outside_image@except_domain"] = g["JS|ReplaceOutsideImageExceptDomain"] = { config: "replaceOutsideImage.exceptDomain", jsFn: function(a) { if ("JS" == f.replaceOutsideImage.configType) return a }, xmlFn: function(a) { if ("JS" != f.replaceOutsideImage.configType) return a }, configFn: function(a) { if ("1" == f.replaceOutsideImage.use) return a.split(",") } }; g["XML|setting.replace_outside_image@target_domain"] = g["JS|ReplaceOutsideImageTargetDomain"] =
											{ config: "replaceOutsideImage.targetDomain", jsFn: function(a) { if ("JS" == f.replaceOutsideImage.configType) return a }, xmlFn: function(a) { if ("JS" != f.replaceOutsideImage.configType) return a }, configFn: function(a) { if ("1" == f.replaceOutsideImage.use) return a.split(",") } }; g["XML|setting.remove_comment"] = g["JS|RemoveComment"] = { config: "removeComment" }; g["XML|setting.set_default_value_in_empty_tag"] = g["JS|SetDefaultValueInEmptyTag"] = { config: "setDefaultValueInEmptyTag", configFn: function(a) { return a.split(",") } }; g["XML|setting.document.doc_title"] =
												g["JS|Document.DocTitle"] = { config: "document.docTitle" }; g["XML|setting.hybrid.hybrid_window_mode"] = g["JS|HybridWindowMode"] = { config: "hybridWindowMode" }; g["XML|setting.use_get_htmltolowercase"] = g["JS|UseGetHtmlToLowerCase"] = { config: "useGetHtmlToLowerCase" }; g["XML|setting.apply_style_empty_tag"] = g["JS|ApplyStyleEmptyTag"] = { config: "applyStyleEmptyTag" }; g["XML|setting.auto_destroy"] = g["JS|AutoDestroy.Use"] = { config: "autoDestroy.use" }; g["XML|setting.auto_destroy@move_cursor"] = g["JS|AutoDestroy.MoveCursor"] =
													{ config: "autoDestroy.moveCursor" }; g["XML|setting.init_focus"] = g["JS|InitFocus"] = { config: "initFocusTemp", configFn: function(a) { "0" == a ? (f.initFocus = !1, f.initFocusForSetAPI = !1) : "1" == a && (f.initFocus = !0, f.initFocusForSetAPI = !0); return a } }; g["XML|setting.empty_tag_remove_in_setapi"] = g["JS|EmptyTagRemoveInSetapi"] = { config: "emptyTagRemoveInSetapi" }; g["XML|setting.replace_empty_span_tag_in_setapi"] = g["JS|ReplaceEmptySpanTagInSetapi"] = { config: "replaceEmptySpanTagInSetapi" }; g["XML|setting.remove_mso_class"] =
														g["JS|RemoveMsoClass"] = { config: "removeMsoClass" }; g["JS|PhotoEditorId"] = { config: "photoEditorId" }; g["XML|setting.table_template_list_url"] = g["JS|TableTemplateListUrl"] = { config: "tableTemplateListUrl", configFn: function(a) { "/" == a.substring(0, 1) && (a = location.protocol + "//" + location.host + a); return a } }; g["XML|setting.table_template_list_url@use_basic_template"] = g["JS|UseBasicTemplate"] = { config: "useBasicTemplate", configFn: function(a) { if ("" != f.tableTemplateListUrl) return a } }; g["XML|setting.use_replace_image"] =
															g["JS|UseReplaceImage"] = { config: "useReplaceImage" }; g["XML|setting.remove_empty_tag"] = g["JS|RemoveEmptyTag"] = { config: "removeEmptyTag" }; g["XML|setting.remove_empty_tag@use_set_value"] = g["JS|RemoveEmptyTagSetValue"] = { config: "removeEmptyTagSetValue", configFn: function(a) { if ("0" == f.removeEmptyTag) return a } }; g["XML|setting.remove_empty_tag@insert_nbsp_for_line_break"] = g["JS|RemoveEmptyTagInsertNbspForLineBreak"] = { config: "removeEmptyTagInsertNbspForLineBreak", configFn: function(a) { if ("0" == f.removeEmptyTag) return a } };
						g["JS|ButtonText001"] = { config: "buttonText001" }; g["JS|NTLM"] = { config: "NTLM", configFn: function(a) { if (-1 < a.indexOf("Basic")) return a } }; g["XML|setting.disable_insert_image"] = g["JS|DisableInsertImage"] = { config: "disableInsertImage", configFn: function(a) { if ("" != a) { var b = ""; return b = "all" == a.toLowerCase() ? ",image,cell,doc_bg_image,hyperlink,table,paste_image," : "," + a.toLowerCase() + "," } } }; g["XML|uploader_setting.handler_url_save_for_notes"] = g["JS|HandlerUrlSaveForNotes"] = { config: "post_url_save_for_notes" }; g["XML|extra_setting.paste_image_base64"] =
							g["JS|PasteImageBase64"] = { config: "paste_image_base64" }; g["XML|uploader_setting.handler_url_save"] = g["JS|HandlerUrlSave"] = { config: "handlerUrlSave" }; g["XML|extra_setting.paste_image_base64@remove"] = g["JS|PasteImageBase64Remove"] = { config: "paste_image_base64_remove", configFn: function(a) { "1" == a && (f.pasteToImage = "0"); return a } }; g["XML|extra_setting.empty_tag_remove"] = g["JS|EmptyTagRemove"] = { config: "empty_tag_remove" }; g["XML|extra_setting.custom_code"] = g["JS|CustomCode"] = {
								config: "custom_code", configFn: function(a) {
									return b.CustomCode =
										a
								}
							}; g["XML|extra_setting.paste_image_base64@view_base64_source"] = g["JS|ViewImgBase64Source"] = { config: "viewImgBase64Source" }; g["XML|uploader_setting@method"] = g["JS|UploadMethod"] = { config: "uploadMethod", configFn: function(a) { if ("base64" == a) { var b = "FileReader" in window; "html5" != f.runtimes || b ? f.paste_image_base64 = "1" : f.uploadMethod = "upload"; return a.toLowerCase() } } }; g["XML|uploader_setting@use_html5mode"] = g["JS|UploadUseHTML5"] = { config: "uploadUseHTML5", configFn: function(a) { return 1 } }; g["XML|uploader_setting@upload_image_file_object"] =
								g["JS|UploadImageFileObject"] = { config: "uploadImageFileObject" }; g["XML|setting.DEXT5_install"] = g["JS|DEXT5Install"] = { config: "dext5Install" }; g["XML|uploader_setting.image_convert_format"] = g["JS|ImageConvertFormat"] = { config: "image_convert_format" }; g["XML|uploader_setting.image_convert_width"] = g["JS|ImageConvertWidth"] = { config: "image_convert_width" }; g["XML|uploader_setting.image_convert_height"] = g["JS|ImageConvertHeight"] = { config: "image_convert_height" }; g["XML|uploader_setting.image_auto_fit"] = g["JS|ImageAutoFit"] =
									{ config: "image_auto_fit" }; g["XML|uploader_setting.image_auto_convert"] = g["JS|ImageAutoConvert"] = { config: "imageAutoConvert" }; g["XML|uploader_setting.allow_media_file_type"] = g["JS|AllowMediaFileType"] = { config: "allowMediaFileType", configFn: function(a) { if (0 < a.length) return a.split(",") } }; g["XML|uploader_setting.allow_media_file_type@max_file_size"] = g["JS|MaxMediaFileSize"] = {
										config: "maxMediaFileSize", configFn: function(a) {
											var b = RAONKEDITOR.util.getUnit(a), b = RAONKEDITOR.util.getUnitSize(b); return parseInt(a,
												10) * b
										}
									}; g["XML|uploader_setting.allow_image_file_type"] = g["JS|AllowImageFileType"] = { config: "allowImageFileType", configFn: function(a) { if (0 < a.length) return a.split(",") } }; g["XML|uploader_setting.allow_image_file_type@max_file_size"] = g["JS|MaxImageFileSize"] = { config: "maxImageFileSize", configFn: function(a) { var b = RAONKEDITOR.util.getUnit(a), b = RAONKEDITOR.util.getUnitSize(b); return parseInt(a, 10) * b } }; g["XML|uploader_setting.allow_image_file_type@max_base64file_count"] = g["JS|MaxImageBase64fileCount"] = { config: "maxImageBase64fileCount" };
						g["XML|uploader_setting.allow_flash_file_type"] = g["JS|AllowFlashFileType"] = { config: "allowFlashFileType", configFn: function(a) { if (0 < a.length) return a.split(",") } }; g["XML|uploader_setting.allow_flash_file_type@max_file_size"] = g["JS|MaxFlashFileSize"] = { config: "maxFlashFileSize", configFn: function(a) { var b = RAONKEDITOR.util.getUnit(a), b = RAONKEDITOR.util.getUnitSize(b); return parseInt(a, 10) * b } }; g["XML|uploader_setting.allow_insert_file_type"] = g["JS|AllowInsertFileType"] = {
							config: "allowInsertFileType", configFn: function(a) {
								if (0 <
									a.length) return a.split(",")
							}
						}; g["XML|uploader_setting.allow_insert_file_type@max_file_size"] = g["JS|MaxInsertFileSize"] = { config: "maxInsertFileSize", configFn: function(a) { var b = RAONKEDITOR.util.getUnit(a), b = RAONKEDITOR.util.getUnitSize(b); return parseInt(a, 10) * b } }; g["XML|uploader_setting.allow_video_file_type"] = g["JS|AllowVideoFileType"] = { config: "allowVideoFileType", configFn: function(a) { if (0 < a.length) return a.split(",") } }; g["XML|uploader_setting.allow_video_file_type@max_file_size"] = g["JS|MaxVideoFileSize"] =
							{ config: "maxVideoFileSize", configFn: function(a) { var b = RAONKEDITOR.util.getUnit(a), b = RAONKEDITOR.util.getUnitSize(b); return parseInt(a, 10) * b } }; g["XML|uploader_setting.allow_video_file_type@use_poster"] = g["JS|UseVideoPoster"] = { config: "useVideoPoster", configFn: function(a) { RAONKEDITOR.browser.mobile && (a = "0"); return a } }; g["XML|uploader_setting.allow_video_file_type@default_responsive_width"] = g["JS|VideoResponsiveWidthDefaultValue"] = { config: "videoResponsiveWidthDefaultValue", configFn: function(a) { return a } };
						g["XML|uploader_setting.attach_file_image"] = g["JS|AttachFileImage"] = { config: "attachFileImage" }; g["XML|setting.file_delimiter@unit_delimiter"] = g["JS|UnitDelimiter"] = { config: "unitDelimiter", configFn: function(a) { return String.fromCharCode(a) } }; g["XML|setting.file_delimiter@unit_attribute_delimiter"] = g["JS|UnitAttributeDelimiter"] = { config: "unitAttributeDelimiter", configFn: function(a) { return String.fromCharCode(a) } }; g["XML|setting.inoutdent@default_size"] = g["JS|InoutdentDefaultSize"] = { config: "inoutdentDefaultSize" };
						g["XML|setting.table_property@width"] = g["JS|TableDefaultWidth"] = { config: "tableDefaultWidth", configFn: function(a) { return RAONKEDITOR.util.parseIntOr0(a) } }; g["XML|setting.table_property@height"] = g["JS|TableDefaultHeight"] = { config: "tableDefaultHeight", configFn: function(a) { return RAONKEDITOR.util.parseIntOr0(a) } }; g["XML|setting.table_property@class"] = g["JS|TableDefaultClass"] = { config: "tableDefaultClass" }; g["XML|setting.table_property@inoutdent"] = g["JS|TableDefaultInoutdent"] = { config: "tableDefaultInoutdent" };
						g["XML|setting.table_property@init_inoutdent"] = g["JS|TableInitInoutdent"] = { config: "tableInitInoutdent" }; g["XML|setting.table_property@td_height"] = g["JS|TableDefaultTdHeight"] = { config: "tableDefaultTdHeight" }; g["XML|setting.table_property@row_max_count"] = g["JS|TableRowMaxCount"] = { config: "tableRowMaxCount" }; g["XML|setting.table_property@col_max_count"] = g["JS|TableColMaxCount"] = { config: "tableColMaxCount" }; g["XML|setting.table_property@allow_inoutdent_text"] = g["JS|AllowInoutdentText"] = { config: "allowInoutdentText" };
						g["XML|setting.table_property@default_border_color"] = g["JS|DefaultBorderColor"] = { config: "defaultBorderColor" }; g["XML|setting.table_property@use_border_attribute"] = g["JS|UseTableBorderAttribute"] = { config: "useTableBorderAttribute" }; g["XML|setting.table_property@adjust_cursor_in_table"] = g["JS|AdjustCursorInTable"] = { config: "adjustCursorInTable", configFn: function(a) { return "1" == a && RAONKEDITOR.browser.gecko ? "0" : a } }; g["XML|setting.table_property.use_mouse_inoutdent"] = g["JS|UseMouseTableInoutdent"] = {
							config: "useMouseTableInoutdentTemp",
							configFn: function(a) { "0" == f.dragResizeMovable && (f.useMouseTableInoutdent = "1" == a ? !0 : !1); return a }
						}; g["XML|setting.table_property.limit_table_inoutdent"] = g["JS|LimitTableInoutdent"] = { config: "limitTableInoutdent" }; g["XML|setting.table_property.class_list"] = g["JS|TableClassList"] = { config: "tableClassList", configFn: function(a) { return a.split(",") } }; g["XML|setting.table_property.show_line_for_border_none"] = g["JS|ShowLineForBorderNone"] = { config: "showLineForBorderNone" }; g["XML|setting.table_property.show_line_for_border_none@skip_class"] =
							g["JS|ShowLineForBorderNoneSkipClass"] = { config: "showLineForBorderNoneSkipClass" }; g["XML|setting.table_property.show_line_for_border_none@skip_attribute"] = g["JS|ShowLineForBorderNoneSkipAttribute"] = { config: "showLineForBorderNoneSkipAttribute" }; g["XML|setting.table_property.line_style"] = g["JS|TableLineStyleList"] = { config: "tableLineStyleList", configFn: function(a) { return a.split(",") } }; g["XML|setting.table_property.use_background_image"] = g["JS|UseTableBackgroundImage"] = { config: "useTableBackgroundImage" };
						g["XML|setting.table_property.set_default_tag_in_empty_cell"] = g["JS|SetDefaultTagInEmptyCell"] = { config: "setDefaultTagInEmptyCell" }; g["XML|setting.hyperlink_property.target"] = g["JS|HyperLinkTargetList"] = { config: "hyperlinkTargetList", configFn: function(a) { return a.split(",") } }; g["XML|setting.hyperlink_property.category"] = g["JS|HyperLinkCategoryList"] = { config: "hyperlinkCategoryList", configFn: function(a) { return a.split(",") } }; g["XML|setting.hyperlink_property.protocol"] = g["JS|HyperLinkProtocolList"] = {
							config: "hyperlinkProtocolList",
							configFn: function(a) { return a.split(",") }
						}; g["XML|setting.table_property.no_resize_class"] = g["JS|TableNoResizeClass"] = { config: "tableNoResizeClass" }; g["XML|setting.table_property.no_selection_class"] = g["JS|TableNoSelectionClass"] = { config: "tableNoSelectionClass" }; g["XML|setting.table_property.no_action_class"] = g["JS|TableNoActionClass"] = { config: "tableNoActionClass" }; g["XML|setting.table_property.auto_adjust.in_paste"] = g["JS|TableAutoAdjustInPaste"] = { config: "tableAutoAdjustInPaste" }; g["XML|setting.table_property.auto_adjust.in_set_html"] =
							g["JS|TableAutoAdjustInSetHtml"] = { config: "tableAutoAdjustInSetHtml" }; g["XML|setting.img_default_size@width"] = g["JS|ImgDefaultWidth"] = { config: "imgDefaultWidth" }; g["XML|setting.img_default_size@height"] = g["JS|ImgDefaultHeight"] = { config: "imgDefaultHeight" }; g["XML|setting.img_default_margin@margin_left"] = g["JS|ImgDefaultMarginLeft"] = { config: "imgDefaultMarginLeft" }; g["XML|setting.img_default_margin@margin_right"] = g["JS|ImgDefaultMarginRight"] = { config: "imgDefaultMarginRight" }; g["XML|setting.img_default_margin@margin_top"] =
								g["JS|ImgDefaultMarginTop"] = { config: "imgDefaultMarginTop" }; g["XML|setting.img_default_margin@margin_bottom"] = g["JS|ImgDefaultMarginBottom"] = { config: "imgDefaultMarginBottom" }; g["XML|setting.width"] = g["JS|Width"] = { config: "style.width", configFn: function(a) { return a + "" } }; g["XML|setting.height"] = g["JS|Height"] = { config: "style.height", configFn: function(a) { return a + "" } }; g["XML|setting.skinname"] = g["JS|SkinName"] = {
									config: "style.skinName", configFn: function(a) {
										return "random" == a ? RAONKEDITOR.util.getSkinNames()[Math.floor(11 *
											Math.random())] : a
									}
								}; g["JS|DefaultMessage"] = { config: "defaultMessage" }; g["JS|FirstLoadType"] = { config: "firstLoadType" }; g["JS|FirstLoadMessage"] = { config: "firstLoadMessage" }; g["JS|FileFieldID"] = { config: "fileFieldID" }; g["JS|NextTabElementId"] = { config: "nextTabElementId" }; g["JS|UserFieldID"] = { config: "userFieldID" }; g["JS|UserFieldValue"] = { config: "userFieldValue" }; g["XML|setting.source_viewtype"] = g["JS|SourceViewtype"] = { config: "sourceViewtype" }; g["XML|setting.source_viewtype@use_selection_mark"] = g["JS|UseSelectionMark"] =
									{ config: "useSelectionMark", configFn: function(a) { "1" == a && (f.sourceViewtype = "2"); return a } }; g["XML|setting.source_viewtype@unformatted"] = g["JS|SourceViewtypeUnformatted"] = { config: "sourceViewtype_unformatted", configFn: function(a) { if ("3" == f.sourceViewtype && a && "" != a) return a = a.split(",") } }; g["XML|setting.source_viewtype@empty_tag_mode"] = g["JS|SourceViewtypeEmptyTagMode"] = { config: "sourceViewtypeEmptyTagMode" }; g["XML|setting.user_css_url"] = g["JS|UserCssUrl"] = { config: "userCssUrl", configFn: function(a) { return RAONKEDITOR.util.setProtocolBaseDomainURL(a) } };
						g["XML|setting.user_css_url@always_set"] = g["JS|UserCssAlwaysSet"] = { config: "userCssAlwaysSet" }; g["XML|setting.web_font_css_url"] = g["JS|WebFontCssUrl"] = { config: "webFontCssUrl", configFn: function(a) { return RAONKEDITOR.util.setProtocolBaseDomainURL(a) } }; g["XML|setting.web_font_css_url@always_set"] = g["JS|WebFontCssAlwaysSet"] = { config: "webFontCssAlwaysSet" }; g["XML|setting.user_js_url"] = g["JS|UserJsUrl"] = { config: "userJsUrl", configFn: function(a) { return a = RAONKEDITOR.util.setProtocolBaseDomainURL(a) } }; g["XML|setting.xhtml_value"] =
							g["JS|XhtmlValue"] = { config: "xhtml_value" }; g["XML|setting.system_title"] = g["JS|SystemTitle"] = { config: "system_title", configFn: function(a) { "0" == a && (a = ""); return a } }; g["XML|setting.view_mode_auto_height"] = g["JS|ViewModeAutoHeight"] = { config: "view_mode_auto_height" }; g["XML|setting.view_mode_auto_width"] = g["JS|ViewModeAutoWidth"] = { config: "view_mode_auto_width" }; g["JS|ChangeMultiValueMode"] = { config: "changeMultiValueMode" }; g["XML|setting.table_auto_adjust"] = g["JS|TableAutoAdjust"] = { config: "tableAutoAdjust" };
						g["XML|setting.allow_link_media_caption"] = g["JS|AllowLinkMediaCaption"] = { config: "allowLinkMediaCaption" }; g["XML|setting.user_help_url"] = g["JS|UserHelpUrl"] = { config: "userHelpUrl", configFn: function(a) { if (0 == a.substring(0, 4).toLowerCase().indexOf("http")) return "/" != a.substring(a.length - 1, a.length) && (a += "/"), f.webPath.help = a } }; g["XML|setting.img_upload_contenttype"] = g["JS|ImgUploadContenttype"] = { config: "imgUploadContenttype" }; g["JS|MimeCharset"] = { config: "mimeCharset" }; g["JS|MimeConentEncodingType"] = { config: "mimeConentEncodingType" };
						g["JS|MimeLocalOnly"] = { config: "mimeLocalOnly" }; g["JS|MimeRemoveHeader"] = { config: "mimeRemoveHeader" }; g["JS|MimeFileTypeFilter"] = { config: "mimeFileTypeFilter" }; g["JS|UserOpenDlgTitle"] = { config: "userOpenDlgTitle" }; g["JS|UserOpenDlgType"] = { config: "userOpenDlgType" }; g["JS|UserOpenDlgInitDir"] = { config: "userOpenDlgInitDir" }; g["JS|UserImageDlgStyle"] = { config: "userImageDlgStyle" }; g["JS|MimeBaseURL"] = { config: "mimeBaseURL" }; g["XML|setting.showdialog_pos"] = g["JS|ShowDialogPosition"] = { config: "showDialogPosition" };
						g["XML|status_bar.status"] = g["JS|StatusBarItem"] = { config: "statusBarItem", valueType: "array", jsFn: function(a) { return a.split(",") } }; g["XML|status_bar@init_mode"] = g["JS|StatusBarInitMode"] = { config: "statusBarInitMode" }; g["XML|status_bar.status@title"] = g["JS|StatusBarItemTitle"] = { config: "statusBarTitle", valueType: "array", jsFn: function(a) { return a.split(",") } }; g["XML|setting.grid_color"] = g["JS|GridColor"] = { config: "gridColor" }; g["XML|setting.grid_color_nm"] = g["JS|GridColorName"] = { config: "gridColorName" };
						g["XML|setting.grid_spans"] = g["JS|GridSpans"] = { config: "gridSpans" }; g["XML|setting.grid_form"] = g["JS|GridForm"] = { config: "gridForm" }; g["XML|setting.encoding"] = g["JS|Encoding"] = { config: "encoding" }; g["XML|setting.doctype"] = g["JS|DocType"] = { config: "docType" }; g["XML|setting.xmlnsname"] = g["JS|Xmlnsname"] = { config: "xmlnsname" }; g["XML|setting.show_font_real"] = g["JS|ShowFontReal"] = { config: "showRealFont" }; g["XML|setting.lang"] = g["JS|Lang"] = {
							config: "lang", jsFn: function(a) { if (2 <= a.length) return a }, configFn: function(a) {
								f.managerLang =
								a; a = "" == a ? "ko-kr" : RAONKEDITOR.util.getUserLang(a); return RAONKEDITOR.UseLang = a
							}
						}; g["XML|setting.accessibility"] = g["JS|Accessibility"] = { config: "accessibility" }; g["XML|setting.accessibility@validation_item"] = g["JS|AccessibilityValidationItems"] = { config: "accessibilityValidationItems" }; g["XML|setting.accessibility@tab_sequence_mode"] = g["JS|TabSequenceMode"] = { config: "tabSequenceMode" }; g["XML|uploader_setting.save_foldername_rule"] = g["JS|SaveFolderNameRule"] = { config: "saveFolderNameRule" }; g["XML|uploader_setting.save_file_foldername_rule"] =
							g["JS|SaveFileFolderNameRule"] = { config: "saveFileFolderNameRule" }; g["XML|uploader_setting.save_filename_rule"] = g["JS|SaveFileNameRule"] = { config: "saveFileNameRule", configFn: function(a) { if ("GUID" == a || "REALFILENAME" == a) return a } }; g["XML|uploader_setting.save_filename_rule_ex"] = g["JS|SaveFileNameRuleEx"] = { config: "saveFileNameRuleEx" }; g["XML|setting.show_topmenu"] = g["JS|ShowTopMenu"] = { config: "top_menu", configFn: function(a) { return "0" == a ? "1" : "0" } }; g["XML|setting.topmenu"] = g["JS|TopMenu"] = { config: "top_menu" };
						g["XML|setting.show_toolbar"] = g["JS|ShowToolBar"] = { config: "tool_bar", configFn: function(a) { switch (a) { case "1": return "2"; case "2": return "1"; case "3": return "0" }return "3" } }; g["XML|setting.toolbar"] = g["JS|ToolBar"] = { config: "tool_bar", configFn: function(a) { } }; g["XML|setting.show_topstatusbar"] = { config: "top_status_bar", configFn: function(a) { return "0" == a ? "0" : "1" } }; g["XML|setting.topstatusbar"] = g["JS|ShowTopStatusBar"] = {
							config: "top_status_bar", xmlFn: function(a) { return "" != a ? a : "1" }, jsFn: function(a) {
								return "0" ==
									a ? "1" : "0"
							}
						}; g["XML|setting.show_statusbar"] = g["XML|setting.statusbar"] = g["JS|ShowStatusBar"] = { config: "status_bar", configFn: function(a) { return "0" == a ? "1" : "0" } }; g["XML|setting.toolbar@grouping"] = g["XML|setting.show_toolbar@grouping"] = g["JS|ToolBarGrouping"] = { config: "tool_bar_grouping" }; g["XML|setting.resizebar"] = g["JS|ResizeBar"] = { config: "resize_bar" }; g["XML|tool_bar_admin.tool"] = g["JS|ToolBarAdmin"] = { config: "tool_bar_admin", valueType: "array", jsFn: function(a) { return a.split(",") } }; g["XML|setting.statusbar@loading"] =
							g["XML|setting.show_statusbar@loading"] = g["JS|StatusBarLoading"] = { config: "statusBarLoading" }; g["XML|setting.topstatusbar@loading"] = g["XML|setting.show_topstatusbar@loading"] = g["JS|TopStatusBarLoading"] = { config: "topStatusBarLoading" }; g["XML|setting.use_auto_toolbar"] = g["JS|UseAutoToolBar"] = { config: "useAutoToolbar", configFn: function(a) { if (RAONKEDITOR.browser.mobile) return "1" == a && (f.tool_bar_grouping = "0"), a } }; g["JS|AutoToolBar"] = {
								config: "autoToolbar", configFn: function(a) {
									if ("1" == f.useAutoToolbar) {
										for (var b in a) try {
											var c =
												a[b]; f.autoToolbar[b] = "0" == c ? [] : c.split(",")
										} catch (e) { } for (b in f.autoToolbar) { c = f.autoToolbar[b]; a = c.length; for (var d = 0; d < a; d++) { var g = c[d]; 0 > f.toolBar2.indexOf(g) && f.toolBar2.push(g) } } for (d = f.toolBar2.length - 1; 0 <= d; d--)-1 < f.toolBar1.indexOf(f.toolBar2[d]) && f.toolBar2.splice(d, 1)
									}
								}
							}; g["XML|tool_bar_2.tool:mini_photo_editor"] = g["XML|tool_bar_1.tool:mini_photo_editor"] = g["JS|UseMiniImageEditor"] = {
								config: "useMiniImageEditor", configFn: function(a) {
									return RAONKEDITOR.browser.ie && 9 >= RAONKEDITOR.browser.ieVersion ||
										"Windows" == RAONKEDITOR.UserAgent.os.name && RAONKEDITOR.browser.safari ? "0" : -1 < f.toolbarArr1.indexOf("mini_photo_editor") ? "1" : "0"
								}
							}; g["XML|setting.mini_photo_editor.width"] = g["JS|MiniPhotoEditor.Width"] = { config: "miniPhotoEditor.width" }; g["XML|setting.mini_photo_editor.height"] = g["JS|MiniPhotoEditor.Height"] = { config: "miniPhotoEditor.height" }; g["XML|setting.mini_photo_editor.background_color"] = g["JS|MiniPhotoEditor.BackgroundColor"] = { config: "miniPhotoEditor.backgroundColor" }; g["XML|setting.mini_photo_editor.background_opacity"] =
								g["JS|MiniPhotoEditor.BackgroundOpacity"] = { config: "miniPhotoEditor.backgroundOpacity" }; g["XML|setting.mini_photo_editor.img_tag_remove_attribute"] = g["JS|MiniPhotoEditor.ImgTagRemoveAttribute"] = { config: "miniPhotoEditor.imgTagRemoveAttribute", configFn: function(a) { return a.split(",") } }; g["JS|HasContainer"] = { config: "hasContainer" }; g["XML|setting.default_mode"] = g["JS|Mode"] = { config: "mode" }; g["XML|uploader_setting.develop_langage"] = g["JS|DevelopLangage"] = { config: "developLang" }; g["JS|InitVisible"] = {
									config: "style.InitVisible",
									configFn: function(a) { 0 == a && (f.style.height = "1px"); return a }
								}; g["XML|setting.security.encrypt_param"] = g["JS|Security.EncryptParam"] = { config: "security.encryptParam", configFn: function(a) { if ("user" != f.developLang.toLowerCase() && "none" != f.developLang.toLowerCase()) return a } }; g["XML|setting.use_gzip"] = g["JS|UseGZip"] = {
									config: "editor_url", configFn: function(a) {
										if (RAONKEDITOR.isRelease && "1" == a) switch (f.developLang.toUpperCase()) {
											case "JAVA": case "JSP": return RAONKEDITOR.rootPath + "pages/editor_release_java.html";
											case "PHP": return RAONKEDITOR.rootPath + "pages/editor_release_php.html"; default: return RAONKEDITOR.rootPath + "pages/editor_release_net.html"
										} else if (RAONKEDITOR.isRelease && "2" == a) return RAONKEDITOR.rootPath + "pages/editor_release_static_gzip.html"
									}
								}; g["JS|FocusInitObjId"] = { config: "focusInitObjId", configFn: function(a) { "" != a && (f.initFocus = !1); return a } }; g["JS|FocusInitEditorObjId"] = { config: "focusInitEditorObjId" }; g["JS|TabIndexObjId"] = { config: "tabIndexObjId" }; g["JS|SetValueObjId"] = { config: "setValueObjId" };
						g["JS|LoadedEvent"] = { config: "LoadedEvent" }; g["JS|DirectEditHtmlUrl"] = { config: "directEditHtmlUrl" }; g["XML|license.trust_sites"] = g["JS|TrustSites"] = { config: "trustSites" }; g["XML|license.license_key"] = g["JS|LicenseKey"] = { config: "licenseKey" }; g["XML|license.license_key_html5"] = g["JS|LicenseKeyHtml5"] = { config: "licenseKeyHtml5" }; g["XML|license.print_preview@print_landscape"] = g["JS|PrintLandscape"] = { config: "printLandscape" }; g["XML|license.product_key"] = g["JS|ProductKey"] = { config: "productKey" }; g["XML|license.image_editor_use"] =
							g["JS|imageEditorUse"] = g["JS|ImageEditorUse"] = { config: "imageEditorUse" }; g["XML|setting.user_color_picker"] = g["JS|UserColorPicker"] = { config: "userColorPicker", xmlFn: function(a) { a = RAONKEDITOR.util.replaceAll(a, "\r\n", ""); a = RAONKEDITOR.util.replaceAll(a, "\r", ""); a = RAONKEDITOR.util.replaceAll(a, "\n", ""); a = RAONKEDITOR.util.replaceAll(a, "\t", ""); return a = RAONKEDITOR.util.replaceAll(a, unescape("%20"), "") } }; g["XML|setting.use_agent_install_guide"] = g["JS|UseAgentInstallGuide"] = { config: "useAgentInstallGuide" };
						g["XML|setting.use_agent_install_guide@zindex"] = g["JS|AgentInstallGuideZIndex"] = { config: "agentInstallGuideZIndex", configFn: function(a) { if ("1" == f.useAgentInstallGuide) return parseInt(a, 10) } }; g["XML|setting.use_agent_install_guide@logo_url"] = g["JS|AgentInstallGuideLogoUrl"] = { config: "agentInstallGuideLogoUrl", configFn: function(a) { if ("1" == f.useAgentInstallGuide) return a } }; g["XML|setting.agent_install_folder_url"] = g["JS|AgentInstallFolderUrl"] = {
							config: "agentInstallFolderUrl", configFn: function(a) {
								"/" !=
								a.substring(a.length - 1) && (a += "/"); return a
							}
						}; g["XML|setting.agent_install_file_url"] = g["JS|AgentInstallFileUrl"] = { config: "agentInstallFileUrl", allowEmpty: !0, jsFn: function(a) { "" != a && (this.inEmpty = !0); return a }, configFn: function(a) { if ("" == a || void 0 == a) return f.agentInstallFolderUrl + "raonkSetup.exe"; var b = a.split("/"); f.agentInstallFileName = b[b.length - 1]; return a } }; g["XML|setting.use_auto_install"] = g["JS|UseAutoInstall"] = { config: "useAutoInstall" }; g["JS|UseLog"] = { config: "useLog" }; g["XML|setting.browser_spell_check"] =
							g["JS|BrowserSpellCheck"] = { config: "browserSpellCheck" }; g["XML|setting.use_personal_setting"] = g["JS|UsePersonalSetting"] = { config: "usePersonalSetting" }; g["XML|setting.insert_multi_image"] = g["JS|InsertMultiImage"] = { config: "insertMultiImage" }; g["XML|setting.table_property.insert_drag_size"] = g["JS|TableInsertDragSize"] = { config: "tableInsertDragSize" }; g["JS|KeepEditorFromDOMChange"] = { config: "keepEditorFromDOMChange" }; g["XML|setting.agent_install_fail_check"] = g["JS|AgentInstallFailCheck.Use"] = { config: "agentInstallFailCheck.use" };
						g["XML|setting.agent_install_fail_check@time"] = g["JS|AgentInstallFailCheck.Time"] = { config: "agentInstallFailCheck.time", configFn: function(a) { var b = RAONKEDITOR.util.parseIntOr0(a); 0 < b && (a = RAONKEDITOR.util.parseIntOr0(b)); return a } }; g["XML|setting.agent_install_fail_check@stop_install_check"] = g["JS|AgentInstallFailCheck.StopInstallCheck"] = {
							config: "agentInstallFailCheck.stopInstallCheckTemp", configFn: function(a) {
								"1" == a ? f.agentInstallFailCheck.stopInstallCheck = !0 : "0" == a && (f.agentInstallFailCheck.stopInstallCheck =
									!1); return a
							}
						}; g["XML|setting.image_continue_insert_maintain_value"] = g["JS|ImageContinueInsertMaintainValue"] = { config: "imageContinueInsertMaintainValue" }; g["XML|setting.use_line_break"] = g["JS|UseLineBreak"] = { config: "useLineBreak" }; g["XML|setting.use_line_break@word_break_type"] = g["JS|WordBreakType"] = { config: "wordBreakType", configFn: function(a) { if ("1" == f.useLineBreak) return a } }; g["XML|setting.use_line_break@word_wrap_type"] = g["JS|WordWrapType"] = { config: "wordWrapType", configFn: function(a) { if ("1" == f.useLineBreak) return a } };
						g["XML|setting.use_table_paste_dialog"] = g["JS|UseTablePasteDialog"] = { config: "useTablePasteDialog" }; g["XML|setting.table_property.split_cell_style"] = g["JS|SplitCellStyle"] = { config: "splitCellStyle" }; g["XML|setting.keep_original_html"] = g["JS|KeepOriginalHtml"] = { config: "keepOriginalHtml", configFn: function(a) { "1" == a && (f.sourceViewtype = "0", f.useSelectionMark = "0"); return a } }; g["XML|setting.use_enterprise_mode"] = g["JS|UseEnterpriseMode"] = {
							config: "useEnterpriseMode", configFn: function(a) {
								"1" == a && (f.ruler.useResizeEvent =
									"0"); return a
							}
						}; g["XML|setting.use_default_body_space"] = g["JS|DefaultBodySpace.Use"] = { config: "defaultBodySpace.use" }; g["XML|setting.use_default_body_space@mode"] = g["JS|DefaultBodySpace.Mode"] = { config: "defaultBodySpace.mode", configFn: function(a) { if ("1" == f.defaultBodySpace.use) return a } }; g["XML|setting.use_default_body_space@value"] = g["JS|DefaultBodySpace.Value"] = { config: "defaultBodySpace.value", configFn: function(a) { if ("1" == f.defaultBodySpace.use) return a } }; g["XML|setting.ol_list_style_type_sequence"] =
							g["JS|OlListStyleTypeSequence"] = { config: "olListStyleTypeSequence", configFn: function(a) { for (var b = a.split(","), c = b.length, e = 0; e < c; e++) { var d = e + 1; b[d] || (d = 0); a[b[e]] = b[d] } return a } }; g["XML|setting.ul_list_style_type_sequence"] = g["JS|UlListStyleTypeSequence"] = { config: "ulListStyleTypeSequence", configFn: function(a) { for (var b = a.split(","), c = b.length, e = 0; e < c; e++) { var d = e + 1; b[d] || (d = 0); a[b[e]] = b[d] } return a } }; g["XML|setting.paste_remove_span_absolute"] = g["JS|PasteRemoveSpanAbsolute"] = { config: "pasteRemoveSpanAbsolute" };
						g["XML|setting.drag_move"] = g["JS|DragMove"] = { config: "dragMove" }; g["XML|setting.paste_when_table_is_last"] = g["JS|PasteWhenTableIsLast"] = { config: "pasteWhenTableIsLast" }; g["XML|setting.custom_css_url"] = g["JS|CustomCssUrl"] = { config: "style.customCssUrl" }; g["XML|setting.htmlprocess_custom_text"] = g["JS|HtmlprocessCustomText"] = { config: "style.htmlprocessCustomText" }; g["XML|setting.remove_image_in_paste_excel"] = g["JS|RemoveImageInPasteExcel"] = { config: "removeImageInPasteExcel" }; g["XML|setting.remove_td_style_in_paste_ppt"] =
							g["JS|RemoveTdStyleInPastePpt"] = { config: "removeTdStyleInPastePpt" }; g["XML|setting.auto_move_init_focus"] = g["JS|AutoMoveInitFocus.Use"] = { config: "autoMoveInitFocus.use", configFn: function(a) { "1" == a && (f.focusInitObjId = ""); return a } }; g["XML|setting.auto_move_init_focus@target_window"] = g["JS|AutoMoveInitFocus.TargetWindow"] = { config: "autoMoveInitFocus.targetWindow", configFn: function(a) { "string" == typeof a && (a = eval(a)); return a } }; g["XML|setting.show_toolbar@enable_disable_mode"] = g["JS|ToolBarEnableDisableMode"] =
								{ config: "toolBarEnableDisableMode" }; g["XML|setting.replace_line_break"] = g["JS|ReplaceLineBreak"] = { config: "replaceLineBreak" }; g["XML|plugin_setting.auto_grow_mode"] = g["JS|AutoGrowMode"] = { config: "autoGrowMode", configFn: function(a) { "0" != a && f.pluginToolExArr.push("autogrow"); return a } }; g["XML|setting.use_hwp_open"] = g["JS|UseHwpOpen"] = { config: "useHwpOpen" }; g["XML|setting.adjust_currentcolor_in_set_api"] = g["JS|AdjustCurrentColorInSetApi"] = { config: "adjustCurrentColorInSetApi" }; g["XML|setting.adjust_textindent_in_paste"] =
									g["JS|AdjustTextIndentInPaste"] = { config: "adjustTextIndentInPaste" }; g["XML|setting.adjust_empty_table_in_excel"] = g["JS|AdjustEmptyTableInExcel"] = { config: "adjustEmptyTableInExcel" }; g["XML|setting.replace_space_to_nbsp_in_excel"] = g["JS|ReplaceOneSpaceToNbspInExcel"] = { config: "replaceOneSpaceToNbspInExcel" }; g["XML|setting.undo@mode"] = g["JS|UndoMode"] = { config: "undoMode" }; g["XML|setting.remove_carriage_return_in_tag"] = g["JS|RemoveCarriageReturnInTag"] = { config: "removeCarriageReturnInTag" }; g["XML|setting.hyperlink_property.click_ctrl_hyperlink"] =
										g["JS|ClickCtrlHyperlink"] = { config: "clickCtrlHyperlink", configFn: function(a) { if (RAONKEDITOR.browser.ie && 7 >= RAONKEDITOR.browser.ieVersion || RAONKEDITOR.browser.mobile) a = "0"; return a } }; g["XML|setting.remove_lang_attribute"] = g["JS|RemoveLangAttribute"] = { config: "removeLangAttribute" }; g["JS|Event.AfterChangeMode"] = { config: "event.afterChangeMode", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.CreationComplete"] = { config: "event.creationComplete", configFn: function(a) { if ("function" === typeof a) return a } };
						g["JS|Event.OnError"] = { config: "event.onError", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.OnLanguageDefinition"] = { config: "event.onLanguageDefinition", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.AfterPopupShow"] = { config: "event.afterPopupShow", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.AgentInstall"] = { config: "event.agentInstall", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.BeforeInsertUrl"] = {
							config: "event.beforeInsertUrl",
							configFn: function(a) { if ("function" === typeof a) return a }
						}; g["JS|Event.Mouse"] = { config: "event.mouse", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.Command"] = { config: "event.command", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.Key"] = { config: "event.key", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.Resized"] = { config: "event.resized", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.DocumentEditComplete"] = {
							config: "event.documentEditComplete",
							configFn: function(a) { if ("function" === typeof a) return a }
						}; g["JS|Event.PasteImage"] = { config: "event.pasteImage", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.WordCount"] = { config: "event.wordCount", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.BeforePaste"] = { config: "event.beforePaste", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.CustomAction"] = { config: "event.customAction", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.FullScreen"] =
							{ config: "event.fullScreen", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.SetComplete"] = { config: "event.setComplete", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.SetInsertComplete"] = { config: "event.setInsertComplete", configFn: function(a) { if ("function" === typeof a) return a } }; g["JS|Event.CloseInstallPopup"] = { config: "event.closeInstallPopup", configFn: function(a) { if ("function" === typeof a) return a } }; g["XML|setting.adjust_cell_size_after_delete_cell"] = g["JS|AdjustCellSizeAfterDeleteCell"] =
								{ config: "adjustCellSizeAfterDeleteCell" }; g["XML|setting.remove_dummy_div_in_hwp_paste"] = g["JS|RemoveDummyDivInHwpPaste"] = { config: "removeDummyDivInHwpPaste" }; g["XML|setting.paste_to_text_mode"] = g["JS|PasteToTextMode"] = { config: "pasteToTextMode" }; g["XML|setting.adjust_cursor_in_relative"] = g["JS|AdjustCursorInRelative"] = { config: "adjustCursorInRelative", configFn: function(a) { if (RAONKEDITOR.browser.ie) return a } }; g["XML|setting.move_style_tag_to_head"] = g["JS|MoveStyleTagToHead"] = { config: "moveStyleTagToHead" };
						g["XML|setting.remove_dummy_tag"] = g["JS|RemoveDummyTag"] = { config: "removeDummyTag" }; g["XML|setting.placeholder@content"] = g["JS|Placeholder.Content"] = { config: "placeholder.content", configFn: function(a) { if (RAONKEDITOR.browser.ie && 10 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie) return a = a.replace(/\r?\n?\r?\n/gi, "\\A"), a = a.replace(/\\n/gi, "\\A") } }; g["XML|setting.placeholder@font_color"] = g["JS|Placeholder.FontColor"] = {
							config: "placeholder.fontColor", configFn: function(a) {
								if (RAONKEDITOR.browser.ie &&
									10 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie) return a
							}
						}; g["XML|setting.placeholder@font_size"] = g["JS|Placeholder.FontSize"] = { config: "placeholder.fontSize", configFn: function(a) { if (RAONKEDITOR.browser.ie && 10 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie) return 0 > a.indexOf("pt") && 0 > a.indexOf("px") && (a += "px"), a } }; g["XML|setting.placeholder@font_family"] = g["JS|Placeholder.FontFamily"] = {
							config: "placeholder.fontFamily", configFn: function(a) {
								if (RAONKEDITOR.browser.ie && 10 <= RAONKEDITOR.browser.ieVersion ||
									!RAONKEDITOR.browser.ie) return "\ub9d1\uc740\uace0\ub515" == a && (a = "\ub9d1\uc740 \uace0\ub515"), a
							}
						}; g["XML|setting.ignore_different_editor_name"] = g["JS|IgnoreDifferentEditorName"] = { config: "ignoreDifferentEditorName" }; g["XML|setting.paste_text_line_break"] = g["JS|PasteTextLineBreak"] = { config: "pasteTextLineBreak" }; g["XML|setting.replace_mso_style"] = g["JS|ReplaceMsoStyle"] = { config: "replaceMsoStyle.settingStyle", configFn: function(a) { return a.split(",") } }; g["XML|setting.force_save_as_server"] = g["JS|ForceSaveAsServer"] =
							{ config: "forceSaveAsServer" }; g["XML|setting.forbidden_word@mode"] = g["JS|ForbiddenWordMode"] = { config: "forbiddenWordMode", configFn: function(a) { RAONKEDITOR.browser.ie && 7 >= RAONKEDITOR.browser.ieVersion && (a = "0"); return a } }; g["JS|Event.SetForbiddenWordComplete"] = { config: "event.setForbiddenWordComplete", configFn: function(a) { if ("function" === typeof a) return a } }; g["XML|setting.forbidden_word@width"] = g["JS|ForbiddenWordWidth"] = {
								config: "forbiddenWordWidth", configFn: function(a) {
									if ("1" == f.forbiddenWordMode) return a =
										RAONKEDITOR.util.parseIntOr0(a), 300 >= a && (a = 300), a
								}
							}; g["XML|setting.forbidden_word@height"] = g["JS|ForbiddenWordHeight"] = { config: "forbiddenWordHeight", configFn: function(a) { if ("1" == f.forbiddenWordMode) return a = RAONKEDITOR.util.parseIntOr0(a), 150 >= a && (a = 150), a } }; g["XML|setting.clean_nested_tag_options.remove_tag"] = g["JS|CleanNestedTagOptions.RemoveTag"] = { config: "cleanNestedTagOptions.removeTag", configFn: function(a) { "string" == typeof a && "" != a && (a = a.replace(/ /g, "")); return a } }; g["XML|setting.clean_nested_tag_options.allow_style"] =
								g["JS|CleanNestedTagOptions.AllowStyle"] = { config: "cleanNestedTagOptions.allowStyle", configFn: function(a) { "string" == typeof a && "" != a && (a = a.replace(/ /g, ""), a = a.split(",")); return a } }; g["XML|setting.clean_nested_tag_options.nested_count"] = g["JS|CleanNestedTagOptions.NestedCount"] = { config: "cleanNestedTagOptions.nestedCount", configFn: function(a) { return RAONKEDITOR.util.parseIntOr0(a) } }; var y = function(a, b, c) {
									var e = f, g = a.config; if (-1 < g.indexOf(".")) {
										for (var g = g.split("."), k = 0; k < g.length - 1; k++)e = e[g[k]];
										g = g[k]
									} if (c) { if ((b = c(b)) || 0 === b || "" == b) e[g] = b, a["set" + d] = 1 } else e[g] = b, a["set" + d] = 1
								}; (function() {
									for (var a in g) {
										var c = g[a]; if (!c["set" + d]) if (0 == a.indexOf("JS|")) { for (var e = a.substring(3).split("."), f = e.length, k = b, h = 0; h < f; h++)k = k ? k[e[h]] : void 0; e = !1; !c.allowEmpty || k && "" != k || (e = !0); if ("boolean" == typeof k || k && "" != k || c.allowEmpty) { c.jsFn && (returnValue = c.jsFn(k)) && (k = returnValue); try { y(c, k, c.configFn), e && c.inEmpty && (c["set" + d] = !0) } catch (l) { } } } else {
											for (var e = a.split("@"), f = e[0].split(":"), h = f[0].substring(4).split("."),
												n = h.length, f = 1 < f.length ? f[1] : 0, e = 1 < e.length ? e[1] : 0, k = t, m = 0; m < n; m++) { var p = h[m], k = k ? k[p] || (k[0] ? k[0][p] : void 0) : void 0; c.nodeObj = k } if (f && k) { n = !1; for (h = 0; h < k.length; h++)if (k[h]._value == f) { k = k[h]; n = !0; break } if (!n) continue } if ("undefined" != typeof k) {
													if (e) if ("array" == c.valueType) if (k.slice) { tempXmlValue = k.slice(); for (h = 0; h < tempXmlValue.length; h++)tempXmlValue[h] = tempXmlValue[h]._attributes ? tempXmlValue[h]._attributes[e] : ""; k = tempXmlValue } else if (k._attributes) k = [k._attributes[e]]; else continue; else if (k._attributes) k =
														k._attributes[e]; else continue; else { f = e = !1; if ("object" == typeof k && 0 < k.length) { k.slice && (k = k.slice()); for (h = 0; h < k.length; h++)k[h]._value && (k[h] = k[h]._value); "array" != c.valueType ? k = k[0] : e = !0 } else if ("array" == c.valueType) k = [k._value], e = !0; else if ("object" == typeof k) { for (var A in k) { f = !0; break } f || (k = "") } !e && k._value && (k = k._value); e || "string" == typeof k || (k = "") } if ("boolean" == typeof k || k && "" != k || c.allowEmpty) { c.xmlFn && (returnValue = c.xmlFn(k)) && (k = returnValue); try { y(c, k, c.configFn) } catch (q) { } }
												}
										}
									}
								})(); h = "";
						k = RAONKEDITOR.util.xml.getNode(m, "uploader_setting"); "" != b.HandlerUrl ? h = b.HandlerUrl : (k = RAONKEDITOR.util.xml.getNodeValue(k, "handler_url"), 0 < k.length && (h = k)); k = ""; k = 0 == h.length ? RAONKEDITOR.rootPath : "/" == h.substring(0, 1) ? location.protocol + "//" + location.host : 0 == h.toLowerCase().indexOf("http") ? "" : RAONKEDITOR.rootPath; if (0 == h.length) switch (f.developLang.toUpperCase()) {
							case "JAVA": case "JSP": h = k + "handler/raonkhandler.jsp"; break; case "PHP": h = k + "handler/raonkhandler.php"; break; case "NONE": h = ""; break; default: h =
								k + "handler/raonkhandler.ashx"
						} else { if (0 == h.toLowerCase().indexOf("http") && h.match(/:\/\/(.[^\/]+)/)[1] != window.location.host && "file:" != location.protocol) { l = ""; switch (f.developLang.toUpperCase()) { case "JAVA": case "JSP": l = RAONKEDITOR.rootPath + "handler/upload_handler.jsp"; break; case "PHP": l = RAONKEDITOR.rootPath + "handler/upload_handler.php"; break; default: l = RAONKEDITOR.rootPath + "handler/upload_handler.ashx" }f.proxy_url = l } h = k + h } f.handlerUrl = h; h = RAONKEDITOR.util.GetUserRunTimeEditor(f.runtimes); f.runtimes =
							h.mode; 0 == h.isAgent && (f.useKManager = "0"); "0" == f.useKManager && "html4" == f.runtimes && (f.useKManager = "1"); "1" == f.useKManager && (0 > RAONKEDITOR.UserAgent.os.name.toLowerCase().indexOf("windows") || -1 < RAONKEDITOR.UserAgent.device.type.toLowerCase().indexOf("mobile")) && (f.useKManager = "0"); "0" == f.useKManager && (f.useLocalFont = "0"); "html4" == f.runtimes && (f.openDocument.useHtml5FileOpen = "0"); h = RAONKEDITOR.util.xml.getNode(m, "setting"); k = !1; "1" == b.AgentHttps ? k = !0 : "1" == RAONKEDITOR.util.xml.getNodeValue(h, "agent_https") &&
								(k = !0); "1" == f.useKManager && (RAONKEDITOR.browser.isHttps && (f.agentHttps = !0), k && (f.agentHttps = !0)); if ("1" == f.useKManager) {
									k = RAONKEDITOR.util.xml.getNode(h, "agent_document_edit"); if ("" != b.AgentDocumentEdit) f.agentDocumentEdit = b.AgentDocumentEdit.split(","); else if (k && (l = RAONKEDITOR.util.xml.count(k, "item"), 0 < l)) for (f.agentDocumentEdit = [], h = 0; h < l; h++)n = RAONKEDITOR.util.xml.getNodeValueIdx(k, "item", h), f.agentDocumentEdit.push(n), "word" == n && (n = RAONKEDITOR.util.xml.getNodeIdx(k, "item", h)) && n.getAttribute("remove_mso_font_style") &&
										"" != n.getAttribute("remove_mso_font_style") && (f.agentDocumentEditSetting.removeMsoFontStyle = n.getAttribute("remove_mso_font_style")); b.AgentDocumentEditSetting && b.AgentDocumentEditSetting.RemoveMsoFontStyle && "" != b.AgentDocumentEditSetting.RemoveMsoFontStyle && (f.agentDocumentEditSetting.removeMsoFontStyle = b.AgentDocumentEditSetting.RemoveMsoFontStyle)
								} else f.agentDocumentEdit = []; if ("" != b.ItemCustomUrl.Item && "" != b.ItemCustomUrl.Url) f.itemCustomUrl.item = b.ItemCustomUrl.Item.split(","), f.itemCustomUrl.url =
									b.ItemCustomUrl.Url.split(","); else if (m = RAONKEDITOR.util.xml.getNode(m, "item_custom_url")) if (k = RAONKEDITOR.util.xml.count(m, "item"), 0 < k) for (f.itemCustomUrl.item = [], f.itemCustomUrl.url = [], h = 0; h < k; h++)l = RAONKEDITOR.util.xml.getNodeValueIdx(m, "item", h), n = RAONKEDITOR.util.xml.getNodeIdx(m, "item", h).getAttribute("url"), f.itemCustomUrl.item.push(l), f.itemCustomUrl.url.push(n); if ("" != f.userJsUrl) { h = !0; for (m = 0; m < f.xss_allow_url.length; m++)if (f.xss_allow_url[m] == f.userJsUrl) { h = !1; break } 1 == h && f.xss_allow_url.push(f.userJsUrl) } 4 ==
										f.productKey.split("_").length && (f.useServerLicense = !0); if ("1" == f.autoBodyFit) { if ("0" == f.useLineBreak || "1" == f.useLineBreak && "0" == f.wordBreakType) f.wordBreakType = "1"; f.useLineBreak = "1"; f.wordWrapType = "1" } "" != f.userFieldValue && (-1 < f.handlerUrl.indexOf("?") ? f.handlerUrl = f.handlerUrl + "&" + f.userFieldID + "=" + f.userFieldValue : f.handlerUrl = f.handlerUrl + "?" + f.userFieldID + "=" + f.userFieldValue); 4 < f.handlerUrl.length && "http" == f.handlerUrl.substring(0, 4).toLowerCase() && f.handlerUrl.match(/:\/\/(.[^\/]+)/)[1] !=
											window.location.host && (f.isCrossDomain = !0); RAONKEDITOR.browser.HTML5Supported && "Worker" in window && (window.URL || window.webkitURL) || (f.openDocument.useHtml5FileOpen = "0"); 0 == RAONKEDITOR.browser.ajaxOnProgressSupported && (f.uploadUseHTML5 = "0"); if ("base64" == f.uploadMethod || "0" == f.useKManager) f.insertMultiImage = "0"; if ("NET" == f.developLang.toUpperCase() || "JSP" == f.developLang.toUpperCase() || "JAVA" == f.developLang.toUpperCase() || "PHP" == f.developLang.toUpperCase() || "ASP" == f.developLang.toUpperCase()) if (h = f.handlerUrl,
												m = "" + ("kc" + RAONKSolution.Agent.formfeed + "c00"), m = RAONKEDITOR.util.makeEncryptParam(m), h = -1 < h.indexOf("?") ? h + ("&t=" + RAONKEDITOR.util.getTimeStamp()) : h + ("?t=" + RAONKEDITOR.util.getTimeStamp()), f.isCrossDomain) {
													var v = document.createElement("div"), k = RAONKEDITOR.util.getDefaultIframeSrc(); v.innerHTML = '<iframe name="initCheckframe" id="initCheckframe" style="display:none;" src="' + k + '"></iframe>'; v.style.display = "none"; document.body.appendChild(v); RAONKEDITOR.util.postFormData(document, h, "initCheckframe",
														[["k00", m]]); RAONKEDITOR.util.addEvent(v.firstChild, "load", function() { v.firstChild.contentWindow.postMessage("check", "*") }, !0); if (window.postMessage) { var A = function(a) { a = RAONKEDITOR.util.trim(a.data); RAONKEDITOR.util.initHandlerCheck(a, f, b); document.body.removeChild(v); RAONKEDITOR.util.removeEvent(window, "message", A) }; RAONKEDITOR.util.addEvent(window, "message", A) }
											} else RAONKEDITOR.ajax.postData(h, "k00=" + m, function(a) { RAONKEDITOR.util.initHandlerCheck(a, f, b) }); f.printHeader = f.printHeaderLeft + "&b" +
												f.printHeaderCenter + "&b" + f.printHeaderRight; f.printFooter = f.printFooterLeft + "&b" + f.printFooterCenter + "&b" + f.printFooterRight; if ("" != f.printHeader && -1 < f.printHeader.indexOf("&u")) { h = ""; try { h = parent.parent.location.href } catch (F) { } "" != h && (f.printHeader = f.printHeader.replace(/&u/, h)) } if ("" != f.printFooter && -1 < f.printFooter.indexOf("&u")) { h = ""; try { h = parent.parent.location.href } catch (E) { } "" != h && (f.printFooter = f.printFooter.replace(/&u/, h)) } m = f.statusBarItem.length; k = !1; for (h = 0; h < m; h++)if ("design" == f.statusBarItem[h]) {
													k =
													!0; break
												} 0 == k && f.statusBarItem.splice(0, 0, "design"); -1 < f.disableInsertImage.indexOf(",paste_image,") && (f.pasteToImage = "0", 0 > RAONKEDITOR.util.arrayIndexOf(f.removeItem, "paste_to_image") && f.removeItem.push("paste_to_image"), 0 > RAONKEDITOR.util.arrayIndexOf(f.removeItem, "paste_to_image_detail") && f.removeItem.push("paste_to_image_detail")); RAONKEDITOR.isRelease ? (f.popupCssUrl = "ko-kr" != f.lang ? "../css/editor_popup_" + f.lang + ".css" : "../css/editor_popup.css", f.dialogJSUrl = "../js/editor_dialog.js", f.fileEncodingJSUrl =
													"../js/editor_fileopen_encoding.min.js", f.fileEncodingWorkerJSUrl = "../js/editor_fileopen_encodingWorker.min.js", f.ajaxUploadJSUrl = "../js/editor_upload.min.js", f.conversionJSUrl = "../js/editor_conversion.min.js", f.htmlProcessWorkerJSUrl = "../js/editor_htmlProcessWorker.js") : (f.popupCssUrl = "ko-kr" != f.lang ? "../css_dev/editor_popup_" + f.lang + ".css" : "../css_dev/editor_popup.css", f.dialogJSUrl = "../js_dev/editor_dialog.js", f.fileEncodingJSUrl = "../js_dev/editor_fileopen_encoding.js", f.fileEncodingWorkerJSUrl =
														"../js_dev/editor_fileopen_encodingWorker.js", f.ajaxUploadJSUrl = "../js_dev/editor_upload.js", f.conversionJSUrl = "../js_dev/editor_conversion.js", f.htmlProcessWorkerJSUrl = "../js_dev/editor_htmlProcessWorker.js"); this.editor = null; f.editor_id = d; this.FrameID = "raonk_frame_" + d; f.holderID = "raonk_frame_holder" + d; this.ID = d; h = ""; h = f.style.height; 0 == f.style.InitVisible && (h = "1px"); h = '<div id="' + f.holderID + '" style="width:' + f.style.width + "; height:" + h + "; "; h = RAONKEDITOR.browser.iOS || RAONKEDITOR.browser.mobile ?
															"view" == f.mode && "1" == f.view_mode_auto_height ? h + " overflow:hidden;" : h + " overflow:scroll; overflow-x:hidden; -webkit-overflow-scrolling:touch; " : h + " overflow: visible;"; h += '"></div>'; "" != b.EditorHolder ? (m = document.getElementById(b.EditorHolder)) ? m.innerHTML = h : document.write(h) : document.write(h); var h = document.getElementById(f.holderID), x; try { x = document.createElement('<iframe frameborder="0" scrolling="no">') } catch (J) {
																x = document.createElement("iframe"), x.setAttribute("frameborder", "0"), x.setAttribute("scrolling",
																	"no")
															} h.appendChild(x); x.setAttribute("id", this.FrameID); x.setAttribute("title", "RAON K Editor"); x.style.width = "100%"; x.style.height = "100%"; if (RAONKEDITOR.browser.iOS || RAONKEDITOR.browser.mobile) x.style.overflow = "hidden", x.style.display = "inline-block", RAONKEDITOR.browser.iOS && (x.style.height = "101%"); x.width = "100%"; x.height = "100%"; var w = this; RAONKEDITOR.util.addEvent(x, "load", function() {
																var a = RAONKEDITOR.util.CheckEditorVisible(d); try {
																	if (1 == a && "" != x.src && -1 < x.src.indexOf("editor_dummy.html")) RAONKEDITOR.startUpEditor(d);
																	else if (1 == a) if (null != w && "undefined" != typeof w) x.contentWindow.raonk_frame_loaded_event(d, f, w, b), w = f = null; else { var c = RAONKEDITOR.UserConfigHashTable.getItem(d); "undefined" != typeof c && "1" == c.KeepEditorFromDOMChange && (RAONKEDITOR.RAONKMULTIPLE && (RAONKEDITOR.RAONKMULTIPLE["raonk_frame_" + d] = null), RAONKEDITOR.RAONKHOLDER && (RAONKEDITOR.RAONKHOLDER[d] = null), new RAONKEditor(c)) }
																} catch (e) { 0 != a && "" != b.LoadErrorCustomMessage && alert(b.LoadErrorCustomMessage) }
															}, !1); if (1 == RAONKEDITOR.util.CheckEditorVisible(d)) x.src =
																f.editor_url; else {
																	if ("1" == b.AutoStartUp) { var H = function() { x.contentWindow.autoStartUp = x.contentWindow.setInterval(function() { try { RAONKEDITOR.util.CheckEditorVisible(d) && (x.contentWindow.clearInterval(x.contentWindow.autoStartUp), RAONKEDITOR.util.removeEvent(x, "load", H), RAONKEDITOR.startUpEditor(d)) } catch (a) { x.contentWindow.clearInterval(x.contentWindow.autoStartUp), RAONKEDITOR.util.removeEvent(x, "load", H) } }, 1E3) }; RAONKEDITOR.util.addEvent(x, "load", H, !1) } x.src = f.dummy_url; x.setAttribute("keditor_customsrc",
																		f.editor_url)
						} "1" == f.forbiddenWordMode && f.pluginToolExArr.push("forbiddenword")
					}
		}
	}, RAONKEditor_CONFIG = function(a) {
		"" != a.RootPath && (RAONKEDITOR.rootPath = a.RootPath); RAONKEDITOR.rootPath = RAONKEDITOR.util.setProtocolBaseDomainURL(RAONKEDITOR.rootPath); this.lang = "ko-kr"; this.managerLang = "auto"; this.defaultLineHeight = this.defaultFontFamily = this.defaultFontSize = ""; this.defaultFontMarginBottom = this.defaultFontMarginTop = "0px"; this.tabSequenceMode = this.accessibility = "0"; this.visibility = !0; this.tool_bar = this.top_menu =
			"0"; this.top_status_bar = "1"; this.status_bar = "0"; this.xmlnsname = this.docType = this.encoding = this.gridForm = this.gridSpans = this.gridColorName = this.gridColor = ""; this.saveFileFolderNameRule = this.saveFolderNameRule = "YYYY/MM"; this.saveFileNameRule = "GUID"; this.saveFileNameRuleEx = "_"; this.showDialogPosition = "1"; this.image_convert_height = this.image_convert_width = this.image_convert_format = ""; this.image_auto_fit = "0"; this.productKey = ""; this.imageEditorUse = "0"; this.firstLoadMessage = this.firstLoadType = this.defaultMessage =
				""; this.fileFieldID = "Filedata"; this.printLandscape = this.printPreview = "0"; this.basePrintFooter = this.basePrintHeader = this.useFormPrint = this.printMarginltrb = this.printFooterRight = this.printFooterCenter = this.printFooterLeft = this.printFooter = this.printHeaderRight = this.printHeaderCenter = this.printHeaderLeft = this.printHeader = ""; this.sourceViewtype = "0"; this.sourceViewtype_unformatted = ""; this.useSelectionMark = this.sourceViewtypeEmptyTagMode = "0"; this.nextTabElementId = this.webFontCssAlwaysSet = this.webFontCssUrl =
					this.userCssUrl = ""; this.xhtml_value = this.empty_tag_remove = this.paste_image_base64_remove = this.paste_image_base64 = "0"; this.system_title = "K Editor"; this.view_mode_auto_width = this.view_mode_auto_height = "0"; this.changeMultiValueMode = "doctype"; this.tableAutoAdjust = "0"; this.trustSites = ""; this.contextMenuDisable = "0"; this.enterTag = ""; this.userFieldID = "Userdata"; this.userFieldValue = ""; this.mimeCharset = "utf-8"; this.mimeConentEncodingType = "3"; this.mimeLocalOnly = this.mimeFileTypeFilter = "1"; this.mimeRemoveHeader =
						"0"; this.imgDefaultMarginBottom = this.imgDefaultMarginTop = this.imgDefaultMarginRight = this.imgDefaultMarginLeft = this.imgDefaultHeight = this.imgDefaultWidth = this.mimeBaseURL = this.userImageDlgStyle = this.userOpenDlgInitDir = this.userOpenDlgType = this.userOpenDlgTitle = ""; this.officeLineFix = "1"; this.useLog = "0"; this.EditorTabDisable = ""; this.unitDelimiter = "\f"; this.unitAttributeDelimiter = "\x0B"; this.style = {
							skinName: "bluegray", width: "100%", height: "600px", InitVisible: !0, isFontSizeUse: !0, defaultMinHeight: "300px",
							iconName: "icon", customCssUrl: "", htmlprocessCustomText: "<b>Processing...</b>"
						}; this.webPath = {
							image: RAONKEDITOR.rootPath + "images/editor/", page: RAONKEDITOR.rootPath + "pages/", js: RAONKEDITOR.rootPath + "js/", js_dev: RAONKEDITOR.rootPath + "js_dev/", css: RAONKEDITOR.rootPath + "css/", css_dev: RAONKEDITOR.rootPath + "css_dev/", include: RAONKEDITOR.rootPath + "include/", config: RAONKEDITOR.rootPath + "config/", help: RAONKEDITOR.rootPath + "help/", agent: RAONKEDITOR.rootPath + "agent/", agentInstallGuide: RAONKEDITOR.rootPath + "agent/installguide/raonk_setup.html",
							plugin: RAONKEDITOR.rootPath + "plugin/", multiUserCheck: RAONKEDITOR.rootPath + "agent/installguide/multi_user_check.html"
						}; this.holderID = ""; this.config_url = RAONKEDITOR.rootPath + "config/raonkeditor.config.xml"; this.emoticon_url = RAONKEDITOR.rootPath + "config/raonkeditor.emoticon.xml"; this.forms_url = RAONKEDITOR.rootPath + "config/raonkeditor.formlist.xml"; this.handlerUrl = RAONKEDITOR.rootPath + "handler/raonkhandler.ashx"; this.proxy_url = this.post_url_save_for_notes = ""; this.editor_url = RAONKEDITOR.isRelease ? RAONKEDITOR.rootPath +
							"pages/editor_release.html?ver=" + RAONKEDITOR.ReleaseVer : RAONKEDITOR.rootPath + "pages/editor.html?ver=" + RAONKEDITOR.ReleaseVer; this.dummy_url = RAONKEDITOR.rootPath + "pages/editor_dummy.html?ver=" + RAONKEDITOR.ReleaseVer; this.pages = {
								find: "editor_find.html?ver=" + RAONKEDITOR.ReleaseVer, replace: "editor_replace.html?ver=" + RAONKEDITOR.ReleaseVer, image: "editor_image.html?ver=" + RAONKEDITOR.ReleaseVer, horizontal_line: "editor_horizontal_line.html?ver=" + RAONKEDITOR.ReleaseVer, doc_bg_img: "editor_doc_bg_img.html?ver=" +
									RAONKEDITOR.ReleaseVer, flash: "editor_flash.html?ver=" + RAONKEDITOR.ReleaseVer, media: "editor_media.html?ver=" + RAONKEDITOR.ReleaseVer, table: "editor_table.html?ver=" + RAONKEDITOR.ReleaseVer, table_property: "editor_table_property.html?ver=" + RAONKEDITOR.ReleaseVer, cell_property: "editor_cell_property.html?ver=" + RAONKEDITOR.ReleaseVer, insert_row_column: "editor_insert_row_column.html?ver=" + RAONKEDITOR.ReleaseVer, row_property: "editor_row_property.html?ver=" + RAONKEDITOR.ReleaseVer, column_property: "editor_column_property.html?ver=" +
										RAONKEDITOR.ReleaseVer, emoticon: "editor_emoticon.html?ver=" + RAONKEDITOR.ReleaseVer, bookmark: "editor_bookmark.html?ver=" + RAONKEDITOR.ReleaseVer, iframe: "editor_iframe.html?ver=" + RAONKEDITOR.ReleaseVer, hyperlink: "editor_hyperlink.html?ver=" + RAONKEDITOR.ReleaseVer, split_cell: "editor_split_cell.html?ver=" + RAONKEDITOR.ReleaseVer, about: "editor_about.html?ver=" + RAONKEDITOR.ReleaseVer, symbol: "editor_symbol.html?ver=" + RAONKEDITOR.ReleaseVer, setting: "editor_setting.html?ver=" + RAONKEDITOR.ReleaseVer, template: "editor_template.html?ver=" +
											RAONKEDITOR.ReleaseVer, tool_replace: "editor_tool_replace.html?ver=" + RAONKEDITOR.ReleaseVer, insert_link_media: "editor_insert_link_media.html?ver=" + RAONKEDITOR.ReleaseVer, m_insert_link_media: "editor_m_insert_link_media.html?ver=" + RAONKEDITOR.ReleaseVer, m_image: "editor_m_image.html?ver=" + RAONKEDITOR.ReleaseVer, m_emoticon: "editor_m_emoticon.html?ver=" + RAONKEDITOR.ReleaseVer, cell_border: "editor_cell_border.html?ver=" + RAONKEDITOR.ReleaseVer, paste_dialog: "editor_paste.html?ver=" + RAONKEDITOR.ReleaseVer, shortcut: "editor_shortcut.html?ver=" +
												RAONKEDITOR.ReleaseVer, layout: "editor_layout.html?ver=" + RAONKEDITOR.ReleaseVer, insert_file: "editor_insert_file.html?ver=" + RAONKEDITOR.ReleaseVer, file_open: "editor_file_open.html?ver=" + RAONKEDITOR.ReleaseVer, blockquote: "editor_blockquote.html?ver=" + RAONKEDITOR.ReleaseVer, input_text: "editor_input_text.html?ver=" + RAONKEDITOR.ReleaseVer, input_radio: "editor_input_radio.html?ver=" + RAONKEDITOR.ReleaseVer, input_checkbox: "editor_input_checkbox.html?ver=" + RAONKEDITOR.ReleaseVer, input_button: "editor_input_button.html?ver=" +
													RAONKEDITOR.ReleaseVer, input_hidden: "editor_input_hidden.html?ver=" + RAONKEDITOR.ReleaseVer, input_textarea: "editor_input_textarea.html?ver=" + RAONKEDITOR.ReleaseVer, input_select: "editor_input_select.html?ver=" + RAONKEDITOR.ReleaseVer, input_image: "editor_input_image.html?ver=" + RAONKEDITOR.ReleaseVer, accessibility_validation: "editor_accessibility_validation.html?ver=" + RAONKEDITOR.ReleaseVer, personal_data: "editor_personal_data.html?ver=" + RAONKEDITOR.ReleaseVer, forbidden_word: "editor_forbidden_word.html?ver=" +
														RAONKEDITOR.ReleaseVer, table_lock_property: "editor_table_lock_property.html?ver=" + RAONKEDITOR.ReleaseVer, pastetoimage: "editor_pastetoimage.html?ver=" + RAONKEDITOR.ReleaseVer, insert_datetime: "editor_insert_datetime.html?ver=" + RAONKEDITOR.ReleaseVer, conversion: "editor_conversion.html?ver=" + RAONKEDITOR.ReleaseVer, video: "editor_video.html?ver=" + RAONKEDITOR.ReleaseVer, m_video: "editor_m_video.html?ver=" + RAONKEDITOR.ReleaseVer, spell_check: "editor_spell_check.html?ver=" + RAONKEDITOR.ReleaseVer, calculator: "editor_calculator.html?ver=" +
															RAONKEDITOR.ReleaseVer, load_auto_save: "editor_load_auto_save.html?ver=" + RAONKEDITOR.ReleaseVer, placeholder: "editor_placeholder.html?ver=" + RAONKEDITOR.ReleaseVer, paragraph_attribute: "editor_paragraph_attribute.html?ver=" + RAONKEDITOR.ReleaseVer, paste_table: "editor_paste_table.html?ver=" + RAONKEDITOR.ReleaseVer
							}; this.xss_remove_events = this.xss_remove_tags = this.replace_tagname = ""; this.xss_use = "0"; this.xss_allow_events_attribute = "1"; this.xss_check_attribute = ["src", "background", "href", "url"]; this.xss_allow_url =
								[]; this.editor_id = ""; this.mode = "edit"; this.mimeUse = "0"; this.zIndex = 1E4; this.ruler = { use: "0", view: "0", rulerInitPosition: "0", viewPointer: "1", viewGuideLine: "1", viewRuler: "1", guideLineStyle: "dotted", mode: "1", guideLineColor: "rgb(255, 0, 0)", moveGuideLineStyle: "dotted", moveGuideLineColor: "#666666", useInoutdent: "1", moveGap: "1", useResizeEvent: "1", defaultView: "1", autoFitMode: "0", fixEditorWidth: "1", useVertical: "0", useMouseGuide: "0" }; this.horizontalLine = { use: "0", url: "", height: "768", style: "", repeat: "1" }; this.editorborder =
									"1"; this.imageBaseUrl = this.custom_code = ""; this.allowMediaFileType = ["wmv", "asf"]; this.allowImageFileType = ["jpg", "jpeg", "png", "gif", "bmp"]; this.allowFlashFileType = ["swf"]; this.allowInsertFileType = "txt doc docx xls xlsx ppt pptx hwp pdf".split(" "); this.allowVideoFileType = ["mp4", "ogv", "webm"]; this.maxImageFileSize = this.maxMediaFileSize = 0; this.maxImageBase64fileCount = -1; this.videoResponsiveWidthDefaultValue = this.useVideoPoster = this.maxVideoFileSize = this.maxInsertFileSize = this.maxFlashFileSize = 0; this.attachFileImage =
										"1"; this.tableClassList = []; this.tableLineStyleList = []; this.tableDefaultTdHeight = this.tableDefaultClass = this.tableDefaultHeight = this.tableDefaultWidth = ""; this.tableLineStyleValue = {}; this.tableLineStyleValue._solid = "border-top: 1px solid #5d5d5d;"; this.tableLineStyleValue._dotted = "border-top: 1px dotted #5d5d5d;"; this.tableLineStyleValue._dashed = "border-top: 1px dashed #5d5d5d;"; this.tableLineStyleValue._double = "border-top: 3px double #5d5d5d;"; this.tableLineStyleValue._groove = "border: 3px groove #e1e1e1;";
		this.tableLineStyleValue._ridge = "border: 3px ridge #e1e1e1;"; this.tableLineStyleValue._inset = "border: 3px inset #e1e1e1;"; this.tableLineStyleValue._outset = "border: 3px outset #e1e1e1;"; this.tableLineStyleValue._none = "border: 1px none #5d5d5d;"; this.tableLineStyleValue._hidden = "border: 1px none #5d5d5d;"; this.tableDefaultInoutdent = 20; this.tableNoSelectionClass = this.tableNoActionClass = this.tableNoResizeClass = this.tableInitInoutdent = ""; this.tableColMaxCount = this.tableRowMaxCount = "13"; this.allowInoutdentText =
			"0"; this.defaultBorderColor = "#000000"; this.useTableBorderAttribute = "1"; this.imageLineStyleList = []; this.imageLineStyleValue = {}; this.imageLineStyleValue._solid = "border-top: 1px solid #5d5d5d;"; this.imageLineStyleValue._dotted = "border-top: 1px dotted #5d5d5d;"; this.imageLineStyleValue._dashed = "border-top: 1px dashed #5d5d5d;"; this.imageLineStyleValue._double = "border-top: 3px double #5d5d5d;"; this.imageLineStyleValue._groove = "border: 3px groove #e1e1e1;"; this.imageLineStyleValue._ridge = "border: 3px ridge #e1e1e1;";
		this.imageLineStyleValue._inset = "border: 3px inset #e1e1e1;"; this.imageLineStyleValue._outset = "border: 3px outset #e1e1e1;"; this.imageLineStyleValue._none = "border: 1px none #5d5d5d;"; this.imageLineStyleValue._hidden = "border: 1px none #5d5d5d;"; this.showLineForBorderNone = this.tableAutoAdjustInSetHtml = this.tableAutoAdjustInPaste = "0"; this.tabIndexObjId = this.focusInitEditorObjId = this.focusInitObjId = this.showLineForBorderNoneSkipAttribute = this.showLineForBorderNoneSkipClass = ""; this.setDefaultStyle = {
			value: "0",
			body_id: "keditor_body", set_style: "1", line_height_mode: "1"
		}; this.setDefaultUserStyle = []; this.popupCssUrl = "../css/editor_popup.css?ver=" + RAONKEDITOR.ReleaseVer; this.symbolUrl = 1 == RAONKEDITOR.isRelease ? "../js/editor_symbol.js?ver=" + RAONKEDITOR.ReleaseVer : "../js_dev/editor_symbol.js?ver=" + RAONKEDITOR.ReleaseVer; this.dialogJSUrl = "../js/editor_dialog.js?ver=" + RAONKEDITOR.ReleaseVer; this.fileEncodingJSUrl = "../js/editor_fileopen_encoding.min.js?ver=" + RAONKEDITOR.ReleaseVer; this.fileEncodingWorkerJSUrl = "../js/editor_fileopen_encodingWorker.min.js?ver=" +
			RAONKEDITOR.ReleaseVer; this.ajaxUploadJSUrl = "../js/editor_upload.min.js?ver=" + RAONKEDITOR.ReleaseVer; this.htmlProcessWorkerJSUrl = "../js/editor_htmlProcessWorker.js?ver=" + RAONKEDITOR.ReleaseVer; this.customEventCmd = { image: { ondbclick: "" }, hyperLink: { ondbclick: "" } }; this.ieCompatible = ""; this.autoUrlDetect = "1"; this.scrollOverflow = this.pasteRemoveEmptyTag = "0"; this.autoBodyFit = this.disableTabletap = this.defaultImemode = ""; this.useNoncreationAreaBackground = "0"; this.disableKeydown = ""; this.dragAndDropAllow = "0";
		this.limitPasteHtmlLength = { value: "0", length: 13E5 }; this.pasteToImage = "1"; this.wrapPtagToGetApi = this.wrapPtagToSource = this.pasteToImagePopupMode = "0"; this.wrapPtagSkipTag = ""; this.removeSpaceInTagname = "0"; this.viewModeBrowserMenu = "1"; this.eventForPasteImage = this.notRepairInViewMode = this.viewModeAllowCopy = "0"; this.useHtmlCorrection = this.removeColgroup = "1"; this.useHtmlProcessByWorkerSetApi = this.useHtmlProcessByWorker = this.replaceSpace = this.removeIncorrectAttribute = "0"; this.skipTagInParser = this.orderListDefaultClass =
			this.unOrderListDefaultClass = ""; this.htmlCorrectionLimitLength = 45E4; this.runtimes = "html5"; this.setAutoSave = { mode: "2", interval: 5, maxCount: "5", unique_key: "", use_encrypt: "0", useManuallySave: "0", useManuallySaveShortcutKey: "0", saveAndStartInterval: "0" }; this.insertCarriageReturn = "0"; this.returnEvent = { mouse_event: "0", key_event: "0", command_event: "0" }; this.ie_BugFixed_Hyfont = this.ie11_BugFixed_typing_bug_in_table = this.ie11_BugFixed_ReplaceAlignMargin = this.ie11_BugFixed_DeleteTableAlign = this.ie11_BugFixed_ReplaceBr =
				this.ie11_BugFixed_JASO = this.useCorrectInOutdent = "0"; this.ie_BugFixed_Hyfont_Replace_Font = {}; this.ie_BugFixed_Apply_All_Browser = "0"; this.isLoadedEditor = !1; this.setValueObjId = ""; this.replaceEmptyTagWithSpace = "1"; this.LoadedEvent = ""; this.event = {
					afterChangeMode: null, creationComplete: null, onError: null, onLanguageDefinition: null, afterPopupShow: null, agentInstall: null, beforeInsertUrl: null, mouse: null, command: null, key: null, resized: null, documentEditComplete: null, pasteImage: null, wordCount: null, beforePaste: null,
					customAction: null, fullScreen: null, setComplete: null, setInsertComplete: null, closeInstallPopup: null, setForbiddenWordComplete: null
				}; this.frameFullScreen = "0"; this.currFontFamily = ""; this.allowImgSize = "1"; this.hyperlinkTargetList = []; this.hyperlinkCategoryList = []; this.hyperlinkProtocolList = []; this.developLang = "NET"; this.formMode = "0"; this.openDocument = { beforeOpenEvent: "0", useHwp: "0", word: { maxSize: 0, maxPage: 0 }, excel: { maxSize: 0, maxSheet: 0 }, powerpoint: { maxSize: 0, maxSlide: 0 }, useHtml5FileOpen: "1" }; this.removeLastBrTag =
					"0"; this.editorBodyEditableEx = this.editorBodyEditable = !0; this.editorBodyEditableMode = "1"; this.inoutdentDefaultSize = 40; this.accessibilityValidationItems = ""; this.statusBarItem = []; this.statusBarTitle = []; this.statusBarInitMode = "design"; this.dragResize = "0"; this.dragResizeApplyBrowser = []; this.dragResizeMovable = "0"; this.dragResizeApplyDivClass = ""; this.replaceOutsideImage = { use: "0", exceptDomain: [], targetDomain: [] }; this.topMenuItem = []; this.removeItem = []; this.removeContextItem = []; this.eventList = []; this.userJsUrl =
						""; this.fontFamilyList = []; this.fontSizeList = []; this.lineHeightList = "100% 120% 150% 180% 200% 250% 300%".split(" "); this.zoomList = null; this.formattingList = []; this.letterSpacingList = []; this.adminTableLock = { defaultShowLockIconUserMode: "0", defaultLockName: "keditor_lock", checkLockName: ["keditor_lock"], defaultTableLockMode: "2" }; this.userTableLock = { use: "0", lockName: ["keditor_lock"], defaultTableLockMode: "2", defaultShowLockIcon: "1", allowChangeMode: "0" }; this.removeComment = "1"; this.tool_bar_grouping = "0"; this.setDefaultValueInEmptyTag =
							"p div h1 h2 h3 h4 h5".split(" "); this.userHelpUrl = this.metaHttpEquiv = this.directEditHtmlUrl = this.userCssAlwaysSet = this.photoEditorId = ""; this.useGZip = "0"; this.topStatusBarLoading = this.statusBarLoading = "1"; this.hasContainer = "0"; this.security = { encryptParam: "0", keyValue: RAONKEDITOR.ReleaseVer.substring(0, 11) }; this.tabSpace = "&nbsp;&nbsp;&nbsp;&nbsp;"; this.document = { docTitle: "" }; this.addHtmlToSetValue = { html: "", preOrSub: 0 }; this.imgUploadContenttype = "0"; this.imageCustomPropertyDelimiter = "|"; this.useHybrid =
								"0"; this.lineHeightMode = this.useGetHtmlToLowerCase = this.hybridWindowMode = "1"; this.letterSpacingIncDecGap = this.lineHeightIncDecGap = this.fontSizeIncDecGap = ""; this.applyStyleEmptyTag = "0"; this.undoCount = 20; this.allowDeleteCount = "0"; this.autoDestroy = { use: "0", moveCursor: "0" }; this.initFocusForSetAPI = this.initFocus = !0; this.autoMoveInitFocus = { use: "0", targetWindow: null }; this.replaceEmptySpanTagInSetapi = this.emptyTagRemoveInSetapi = this.allowUnableToDeleteMsg = "0"; this.applyFormatExceptStyle = []; this.enforceDoctype =
									"2"; this.userColorPicker = ""; this.useAgentInstallGuide = "0"; this.agentInstallFileName = "raonkSetup.exe"; this.agentInstallGuideZIndex = 1E3; this.agentInstallFileUrl = this.agentInstallGuideLogoUrl = ""; this.agentInstallFolderUrl = this.webPath.agent; this.useAutoInstall = "1"; this.imageAutoConvert = "0"; this.uploadMethod = "upload"; this.removeMsoClass = this.uploadUseHTML5 = "1"; this.tableTemplateListUrl = ""; this.useTableBackgroundImage = this.useReplaceImage = this.useBasicTemplate = "1"; this.buttonText001 = ""; this.interworkingModuleThird =
										this.interworkingModuleSecond = this.interworkingModuleFirst = null; this.useServerLicense = !1; this.removeEmptyTag = "1"; this.removeEmptyTagSetValue = "0"; this.removeEmptyTagInsertNbspForLineBreak = "1"; this.useUndoLightMode = "0"; this.fullScreenBottom = this.fullScreenRight = this.fullScreenLeft = this.fullScreenTop = 0; this.adjustCursorInTable = "0"; this.focusInitObjIdEx = ""; this.viewImgBase64Source = "0"; this.removeStyle = { use: "0", tag: "", style: "" }; this.personalData = "email,phone,RRN"; this.forbiddenWord = ""; this.forbiddenWordMode =
											"0"; this.forbiddenWordWidth = 800; this.forbiddenWordHeight = 560; this.useRecentlyFont = this.useLocalFont = "0"; this.wordCount = { use: "0", limit: "0", limitchar: "0", limitbyte: "0", limitline: "0", countwhitespace: "0", limitmessage: "0", limitcount: "0", limitunit: "char" }; this.managerPortArr = ["47317", "47139", "57317", "57318", "57419"]; this.managerHttpsPortArr = ["47337", "47159", "57337", "57338", "57439"]; this.sendToManagerType = "1"; -1 < RAONKEDITOR.UserAgent.os.name.toLowerCase().indexOf("windows") && -1 < RAONKEDITOR.UserAgent.browser.name.toLocaleLowerCase().indexOf("safari") &&
												(this.sendToManagerType = "2"); "1" == this.sendToManagerType && RAONKEDITOR.browser.ie && 9 >= RAONKEDITOR.browser.ieVersion && (this.sendToManagerType = "2"); this.useKManager = "1"; this.agentHttps = !1; this.NTLM = ""; this.isCrossDomain = !1; this.dropZoneTransparency = 1; this.agentDocumentEdit = []; this.agentDocumentEditSetting = { removeMsoFontStyle: "0" }; this.toolbarArr1 = []; this.toolbarArr2 = []; this.pluginToolExArr = []; this.useMiniImageEditor = "0"; this.miniPhotoEditor = {
													width: "800px", height: "600px", backgroundColor: "black", backgroundOpacity: "0.5",
													imgTagRemoveAttribute: []
												}; this.useLetterSpacingIncDec = this.useLineHeightIncDec = this.useFontSizeIncDec = this.useLineHeightKeyin = this.useFontSizeKeyin = this.useFontFamilyKeyin = "0"; this.autoList = { use: "0" }; this.figure = { use: "0", figurestyle: "margin: 0px; text-align: center; display: inline-block;", figcaptionstyle: "", defaultcaption: "" }; this.browserSpellCheck = "0"; this.useMouseTableInoutdent = !0; this.LimitTableInoutdent = "0"; this.disableInsertImage = ""; this.usePersonalSetting = "1"; this.uploadImageFileObject = this.insertMultiImage =
													"0"; this.excelImageFix = this.dext5Install = "1"; this.targetDomainForEdit = this.agentTempRootDirectory = ""; this.cellSelectionMode = this.colorPickerInputKind = "0"; this.tableInsertDragSize = "10,10"; this.keepEditorFromDOMChange = this.showRealFont = "0"; this.agentInstallFailCheck = { use: "0", time: 30, stopInstallCheck: !1 }; this.wordWrapType = this.wordBreakType = this.useLineBreak = this.imageContinueInsertMaintainValue = "0"; this.splitCellStyle = this.useTablePasteDialog = "1"; this.keepOriginalHtml = "0"; this.clipImageHex = []; this.useAutoToolbar =
														"0"; this.autoToolbar = {
															"default": ["font_size", "bold", "underline", "italic", "strike_through"], selectedSingleCell: "font_size bold underline italic strike_through separator split_cell cell_bg_color detail_cell_bg_color".split(" "), selectedMultiCell: "font_size bold underline italic strike_through separator split_cell merge_cell cell_bg_color detail_cell_bg_color table_same_width table_same_height table_same_widthheight".split(" "), focusInCell: "font_size bold underline italic strike_through separator split_cell cell_bg_color detail_cell_bg_color split_cell table_row_clone insert_row_up insert_row_down delete_row delete_column table_delete insert_column_left insert_column_right table_select_all table_select_cell table_select_row table_select_column cell_property table_property".split(" "),
															selectArea: ["font_size", "bold", "underline", "italic", "strike_through"], focusImage: ["image_align_default", "image_align_left", "image_align_right", "separator", "image_property"]
														}; this.useEnterpriseMode = "0"; this.defaultBodySpace = { use: "1", mode: "1", value: "10px" }; this.olListStyleTypeSequence = { decimal: "upper-alpha", "decimal-leading-zero": "upper-alpha", "upper-alpha": "lower-alpha", "lower-alpha": "upper-roman", "upper-roman": "lower-roman", "lower-roman": "lower-greek", "lower-greek": "decimal" }; this.ulListStyleTypeSequence =
															{ disc: "circle", circle: "square", square: "disc" }; this.pasteRemoveSpanAbsolute = ""; this.dragMove = "0"; this.pasteWhenTableIsLast = "1"; this.allowLinkMediaCaption = ""; this.tool_bar_admin = this.resize_bar = this.removeImageInPasteExcel = "0"; this.toolBarEnableDisableMode = "2"; this.useHwpOpen = this.autoGrowMode = this.replaceLineBreak = "0"; this.adjustCurrentColorInSetApi = "1"; this.adjustEmptyTableInExcel = this.adjustTextIndentInPaste = "0"; this.replaceOneSpaceToNbspInExcel = "1"; this.undoMode = "2"; this.removeCarriageReturnInTag =
																this.setDefaultTagInEmptyCell = "1"; this.clickCtrlHyperlink = "0"; this.symbolCustomCssUrl = ""; this.removeLangAttribute = "1"; this.itemCustomUrl = { item: [], url: [] }; this.adjustCellSizeAfterDeleteCell = "1"; this.moveStyleTagToHead = this.adjustCursorInRelative = this.pasteToTextMode = this.removeDummyDivInHwpPaste = "0"; this.removeDummyTag = ""; this.useConfigTimeStamp = "1"; this.placeholder = { content: "", fontColor: "#999999", fontSize: "", fontFamily: "" }; this.pasteTextLineBreak = this.ignoreDifferentEditorName = "0"; this.replaceMsoStyle =
																	{ settingStyle: [], styleValue: { "mso-spacerun": "yes" }, replaceAttributeName: "raonk" }; this.forceSaveAsServer = "0"; this.handlerUrlSave = ""; this.cleanNestedTagOptions = { removeTag: "", allowStyle: [], nestedCount: 50 }
	}, keditor_user_config = function() {
		return {
			Width: "", Height: "", SkinName: "", InitXml: "", InitServerXml: "", HandlerUrl: "", InitVisible: !0, DefaultMessage: "", FirstLoadType: "", FirstLoadMessage: "", FileFieldID: "", EditorHolder: "", ShowTopMenu: "", ShowToolBar: "", ShowTopStatusBar: "", ShowStatusBar: "", XssUse: "", XssRemoveTags: "",
			XssRemoveEvents: "", XssAllowEventsAttribute: "", XssCheckAttribute: "", XssAllowUrl: "", Mode: "", ZIndex: "", NextTabElementId: "", IgnoreSameEditorName: "0", KeepEditorFromDOMChange: "0", ChangeMultiValueMode: "", EditorBorder: "", GridColor: "", GridColorName: "", GridSpans: "", GridForm: "", Encoding: "", DocType: "", Xmlnsname: "", ShowFontReal: "", Lang: "", UseLang: "", IconName: "icon", CustomCode: "", EnterTag: "", DefaultFontFamily: "", DefaultFontSize: "", DefaultLineHeight: "", DefaultFontMarginTop: "", DefaultFontMarginBottom: "", UserFieldID: "",
			UserFieldValue: "", MimeCharset: "", MimeConentEncodingType: "", MimeFileTypeFilter: "", MimeLocalOnly: "", MimeRemoveHeader: "", UserOpenDlgTitle: "", UserOpenDlgType: "", UserOpenDlgInitDir: "", UserImageDlgStyle: "", MimeBaseURL: "", FocusInitObjId: "", FocusInitEditorObjId: "", TabIndexObjId: "", ImageEditorUse: "", MimeUse: "", OfficeLineFix: "", UseLog: "", RootPath: "", EditorTabDisable: "", FormListUrl: "", EmoticonUrl: "", Runtimes: "", MouseEventCancel: !1, KeyEventCancel: !1, SetValueObjId: "", LoadedEvent: "", Event: {
				AfterChangeMode: null,
				CreationComplete: null, OnError: null, OnLanguageDefinition: null, AfterPopupShow: null, AgentInstall: null, BeforeInsertUrl: null, Mouse: null, Command: null, Key: null, Resized: null, DocumentEditComplete: null, PasteImage: null, WordCount: null, BeforePaste: null, CustomAction: null, FullScreen: null, SetComplete: null, SetInsertComplete: null, CloseInstallPopup: null, SetForbiddenWordComplete: null
			}, ScrollOverflow: "", DocType: "", DevelopLangage: "", UnitDelimiter: "", UnitAttributeDelimiter: "", EditorBodyEditable: "", EditorBodyEditableMode: "",
			SymbolUrl: "", AccessibilityValidationItems: "", TabSequenceMode: "", ViewModeAutoHeight: "", ViewModeAutoWidth: "", ShowDialogPosition: "", StatusBarItem: "", StatusBarItemTitle: "", StatusBarInitMode: "", ImageBaseUrl: "", DragResize: "", DragResizeApplyBrowser: "", DragResizeMovable: "", DragResizeApplyDivClass: "", ReplaceOutsideImage: "", ReplaceOutsideImageExceptDomain: "", ReplaceOutsideImageTargetDomain: "", TopMenuItem: "", RemoveItem: "", RemoveContextItem: "", ManagerMode: "", EventList: "", TableLock: {
				Use: "", LockName: "", ShowLockIconUserMode: "",
				TableLockMode: ""
			}, FontFamilyList: "", FontSizeList: "", LineHeightList: "", ZoomList: "", FormattingList: "", LetterSpacingList: "", FrameFullScreen: "", DialogWindow: null, AdminTableLock: { DefaultShowLockIconUserMode: "", DefaultLockName: "", CheckLockName: "", DefaultTableLockMode: "" }, UserTableLock: { Use: "", LockName: "", DefaultTableLockMode: "", DefaultShowLockIcon: "", AllowChangeMode: "" }, RemoveComment: "", SaveFolderNameRule: "", SaveFileFolderNameRule: "", ToolBarGrouping: "", SetDefaultValueInEmptyTag: "", LoadErrorCustomMessage: "",
			PhotoEditorId: "", ProductKey: "", LicenseKey: "", LicenseKeyHtml5: "", DirectEditHtmlUrl: "", UserHelpUrl: "", UseGZip: "0", StatusBarLoading: "", TopStatusBarLoading: "", HandlerUrlSaveForNotes: "", HasContainer: "", Security: { EncryptParam: "" }, TabSpace: "", AutoBodyFit: "", UseNoncreationAreaBackground: "", Ruler: {
				Use: "", InitPosition: "", ViewPointer: "", ViewGuideLine: "", ViewRuler: "", GuideLineStyle: "", GuideLineColor: "", MoveGuideLineStyle: "", MoveGuideLineColor: "", UseInoutdent: "", MoveGap: "", UseResizeEvent: "", DefaultView: "", AutoFitMode: "",
				FixEditorWidth: "", UseVertical: "", UseMouseGuide: ""
			}, UseConfigTimeStamp: "1", UseHorizontalLine: "", UseHorizontalLineHeight: "", UseHorizontalLineStyle: "", UseHorizontalLineRepeat: "", Document: { DocTitle: "" }, ImgUploadContenttype: "", ImageCustomPropertyDelimiter: "", LineHeightMode: "", FontSizeIncDecGap: "", LineHeightIncDecGap: "", LetterSpacingIncDecGap: "", PrintMarginltrb: "", UseFormPrint: "", TrustSites: "", ApplyStyleEmptyTag: "", UndoCount: "", AllowDeleteCount: "", AutoDestroy: { Use: "", MoveCursor: "" }, InitFocus: "", AutoMoveInitFocus: {
				Use: "",
				TargetWindow: ""
			}, AllowUnableToDeleteMsg: "", PasteToImage: "", PasteToImagePopupMode: "", ImageAutoFit: "", SaveFileNameRule: "", SaveFileNameRuleEx: "", ImageConvertFormat: "", ImageConvertWidth: "", ImageConvertHeight: "", Accessibility: "", SourceViewtype: "", UserCssUrl: "", UserCssAlwaysSet: "", WebFontCssUrl: "", WebFontCssAlwaysSet: "", UserJsUrl: "", SystemTitle: "", PasteRemoveEmptyTag: "", DefaultImemode: "", DisableTabletap: "", EmptyTagRemoveInSetapi: "", ReplaceEmptySpanTagInSetapi: "", WrapPtagToSource: "", WrapPtagToGetApi: "",
			WrapPtagSkipTag: "", UseHtmlCorrection: "", RemoveIncorrectAttribute: "", ReplaceSpace: "", UseHtmlProcessByWorker: "", UseHtmlProcessByWorkerSetApi: "", UnOrderListDefaultClass: "", OrderListDefaultClass: "", SkipTagInParser: "", HtmlCorrectionLimitLength: "", ShowLineForBorderNone: "", ShowLineForBorderNoneSkipClass: "", ShowLineForBorderNoneSkipAttribute: "", TableAutoAdjust: "", ApplyFormatExceptStyle: "", EnforceDoctype: "", UserColorPicker: "", ImageAutoConvert: "", UploadMethod: "", UploadUseHTML5: "", RemoveMsoClass: "", TableTemplateListUrl: "",
			UseBasicTemplate: "", UseReplaceImage: "", UseTableBackgroundImage: "", OpenDocument: { BeforeOpenEvent: "", UseHwp: "", Word: { MaxSize: "", MaxPage: "" }, Excel: { MaxSize: "", MaxSheet: "" }, Powerpoint: { MaxSize: "", MaxSlide: "" }, UseHtml5FileOpen: "" }, ButtonText001: "", MaxMediaFileSize: "", MaxImageFileSize: "", MaxImageBase64fileCount: "", MaxFlashFileSize: "", MaxInsertFileSize: "", MaxVideoFileSize: "", UseVideoPoster: "", VideoResponsiveWidthDefaultValue: "", TableDefaultWidth: "", TableDefaultHeight: "", TableDefaultClass: "", TableDefaultInoutdent: "",
			TableInitInoutdent: "", TableDefaultTdHeight: "", TableRowMaxCount: "", TableColMaxCount: "", AllowInoutdentText: "", DefaultBorderColor: "", UseTableBorderAttribute: "", TableClassList: "", TableLineStyleList: "", TableNoResizeClass: "", TableNoSelectionClass: "", TableNoActionClass: "", TableAutoAdjustInPaste: "", TableAutoAdjustInSetHtml: "", RemoveEmptyTag: "", RemoveEmptyTagSetValue: "", RemoveEmptyTagInsertNbspForLineBreak: "", PasteImageBase64Remove: "", UseUndoLightMode: "", FullScreenTop: "", FullScreenLeft: "", FullScreenRight: "",
			FullScreenBottom: "", AdjustCursorInTable: "", ViewImgBase64Source: "", RemoveStyle: { Use: "", Tag: "", Style: "" }, PersonalData: "", ForbiddenWord: "", ForbiddenWordMode: "", ForbiddenWordWidth: "", ForbiddenWordHeight: "", UseLocalFont: "", UseRecentlyFont: "", WordCount: { Use: "", Limit: "", LimitChar: "", LimitByte: "", LimitLine: "", CountWhiteSpace: "", LimitMessage: "", LimitCount: "" }, SetAutoSave: { Mode: "", Interval: "", MaxCount: "", UniqueKey: "", UseEncrypt: "", UseManuallySave: "", UseManuallySaveShortcutKey: "", SaveAndStartInterval: "" }, AgentHttps: "",
			NTLM: "", DropZoneTransparency: "", AgentDocumentEdit: "", AgentDocumentEditSetting: { RemoveMsoFontStyle: "" }, UseAgentInstallGuide: "", AgentInstallGuideZIndex: "", AgentInstallGuideLogoUrl: "", AgentInstallFileUrl: "", AgentInstallFolderUrl: "", UseAutoInstall: "", MiniPhotoEditor: { Width: "", Height: "", BackgroundColor: "", BackgroundOpacity: "", ImgTagRemoveAttribute: "" }, UseFontFamilyKeyin: "", UseFontSizeKeyin: "", UseLineHeightKeyin: "", UseFontSizeIncDec: "", UseLineHeightIncDec: "", UseLetterSpacingIncDec: "", AutoList: { Use: "" },
			Figure: { Use: "", FigureStyle: "", FigcaptionStyle: "", DefaultCaption: "" }, BrowserSpellCheck: "", UseMouseTableInoutdent: "", LimitTableInoutdent: "", DisableInsertImage: "", UsePersonalSetting: "", InsertMultiImage: "", UploadImageFileObject: "", DEXT5Install: "", ExcelImageFix: "", AgentTempRootDirectory: "", TargetDomainForEdit: "", ColorPickerInputKind: "", CellSelectionMode: "", TableInsertDragSize: "", AgentInstallFailCheck: null, ImageContinueInsertMaintainValue: "", UseLineBreak: "", WordBreakType: "", WordWrapType: "", UseTablePasteDialog: "",
			SplitCellStyle: "", KeepOriginalHtml: "", UseAutoToolBar: "", AutoToolBar: null, UseEnterpriseMode: "", DefaultBodySpace: { Use: "", Mode: "", Value: "" }, OlListStyleTypeSequence: "", UlListStyleTypeSequence: "", PasteRemoveSpanAbsolute: "", DragMove: "", PasteWhenTableIsLast: "", CustomCssUrl: "", HtmlprocessCustomText: "", RemoveImageInPasteExcel: "", AllowLinkMediaCaption: "", DisableKeydown: "", CustomEventImageOndbclick: "", CustomEventHyperlinkOndbclick: "", PrintPreview: "", PrintHeaderLeft: "", PrintHeaderCenter: "", PrintHeaderRight: "",
			PrintFooterLeft: "", PrintFooterCenter: "", PrintFooterRight: "", AllowImgSize: "", ContextMenuDisable: "", IECompatible: "", AutoUrlDetect: "", SetDefaultStyle: { Value: "", BodyId: "", UserStyle: "", LineHeightMode: "", SetStyle: "" }, DragAndDropAllow: "", LimitPasteHtml: "", LimitPasteHtmlLength: "", RemoveSpaceInTagname: "", ViewModeBrowserMenu: "", ViewModeAllowCopy: "", EventForPasteImage: "", RemoveColgroup: "", InsertCarriageReturn: "", ReturnEventMouse: "", ReturnEventKeyboard: "", ReturnEventCommand: "", UseCorrectInOutdent: "", Ie11BugFixedJASO: "",
			Ie11BugFixedReplaceBr: "", Ie11BugFixedDeleteTableAlign: "", Ie11BugFixedReplaceAlignMargin: "", Ie11BugFixedTypingBugInTable: "", IeBugFixedHyfont: "", IeBugFixedHyfontReplaceFont: "", IeBugFixedApplyAllBrowser: "", ReplaceEmptyTagWithSpace: "", RemoveLastBrTag: "", HybridWindowMode: "", UseGetHtmlToLowerCase: "", EmptyTagRemove: "", AllowMediaFileType: "", AllowImageFileType: "", AllowFlashFileType: "", AllowInsertFileType: "", AllowVideoFileType: "", AttachFileImage: "", HyperLinkTargetList: "", HyperLinkCategoryList: "", HyperLinkProtocolList: "",
			ImgDefaultWidth: "", ImgDefaultHeight: "", ImgDefaultMarginLeft: "", ImgDefaultMarginRight: "", ImgDefaultMarginTop: "", ImgDefaultMarginBottom: "", UseSelectionMark: "", SourceViewtypeUnformatted: "", SourceViewtypeEmptyTagMode: "", TopMenu: "", ToolBar: "", Topstatusbar: "", Statusbar: "", ResizeBar: "", ToolBarAdmin: "", ToolBarEnableDisableMode: "", ReplaceLineBreak: "", AutoGrowMode: "", UseHwpOpen: "", AdjustCurrentColorInSetApi: "", AdjustTextIndentInPaste: "", AdjustEmptyTableInExcel: "", ReplaceOneSpaceToNbspInExcel: "", UndoMode: "",
			SetDefaultTagInEmptyCell: "", RemoveCarriageReturnInTag: "", ClickCtrlHyperlink: "", SymbolCustomCssUrl: "", RemoveLangAttribute: "", ItemCustomUrl: { Item: "", Url: "" }, AdjustCellSizeAfterDeleteCell: "", RemoveDummyDivInHwpPaste: "", PasteToTextMode: "", AdjustCursorInRelative: "", MoveStyleTagToHead: "", RemoveDummyTag: "", Placeholder: { Content: "", FontColor: "", FontSize: "", FontFamily: "" }, IgnoreDifferentEditorName: "", PasteTextLineBreak: "", ReplaceMsoStyle: { SettingStyle: "", StyleValue: null, ReplaceAttributeName: "raonk" }, ForceSaveAsServer: "",
			HandlerUrlSave: "", CleanNestedTagOptions: { RemoveTag: "", AllowStyle: "", NestedCount: "" }
		}
	}; window.RAONKEDITOR = function() {
		return {
			isRelease: !0, logMode: !1, logLevel: "ALL", logGroupingName: "", isPopUpCssLoaded: !1, version: "RAON K Editor", ReleaseVer: "2018.1477084.1330.01", ServerReleaseVer: "", rootPath: function() {
				var a = window.RAONKEDITOR_ROOTPATH || ""; if (!a) for (var d = document.getElementsByTagName("script"), b = d.length, c = null, e = 0; e < b; e++)if (c = d[e], c = c.src.match(/(^|.*[\\\/])raonkeditor\.js/i)) { a = c[1]; break } -1 == a.indexOf(":/") &&
					(a = 0 === a.indexOf("/") ? location.href.match(/^.*?:\/\/[^\/]*/)[0] + a : location.href.match(/^[^\?]*\/(?:)/)[0] + a); a = a.substring(0, a.length - 1); a = a.substring(0, a.lastIndexOf("/")) + "/"; if (!a) throw "RAON K Editor installation path could not be automatically detected."; return a
			}(), getUrl: function(a) { -1 == a.indexOf(":/") && 0 !== a.indexOf("/") && (a = this.rootPath + a); this.ReleaseVer && "/" != a.charAt(a.length - 1) && !/[&?]ver=/.test(a) && (a += (0 <= a.indexOf("?") ? "&" : "?") + "ver=" + this.ReleaseVer); return a }, RAONKMULTIPLE: {}, RAONKHOLDER: {},
			RAONKMULTIPLEID: [], ShowTextChangeAlert: !0, ShowDestroyAlert: !0, CEditorID: "", IsEditorLoadedHashTable: null, InitEditorDataHashTable: null, UserConfigHashTable: null, EditorContentsHashTable: null
		}
	}(); try {
		"undefined" == typeof RAONKSolutionLog && "undefined" != typeof RAONKEditorLogMode && RAONKEditorLogMode && (RAONKEDITOR.logMode = !0, "undefined" != typeof RAONKEditorLogModeLevel && (RAONKEDITOR.logLevel = RAONKEditorLogModeLevel.toUpperCase()), document.write('<script src="' + RAONKEDITOR.rootPath + "js/log4javascript/raonk.log4javascript.min.js?ver=" +
			RAONKEDITOR.ReleaseVer + '" type="text/javascript">\x3c/script>'))
	} catch (e$$17) { } RAONKEDITOR.browser || (RAONKEDITOR.browser = function() {
		var a = navigator.userAgent.toLowerCase(), d = window.opera, d = {
			ie: -1 < a.search("trident") || -1 < a.search("msie") || /(edge)\/((\d+)?[\w\.]+)/i.test(a) ? !0 : !1, opera: !!d && d.version, webkit: -1 < a.indexOf(" applewebkit/"), mac: -1 < a.indexOf("macintosh"), quirks: (-1 < a.search("trident") || -1 < a.search("msie") || /(edge)\/((\d+)?[\w\.]+)/i.test(a)) && "BackCompat" == document.compatMode, mobile: -1 <
				a.indexOf("mobile") || -1 < a.indexOf("android") || -1 < a.indexOf("iphone"), iOS: /(ipad|iphone|ipod)/.test(a), isCustomDomain: function() { if (!this.ie) return !1; var a = document.domain, b = window.location.hostname; return a != b && a != "[" + b + "]" }, isHttps: "https:" == location.protocol, G_AP12: 9, G_AP24: "i"
		}; d.gecko = "Gecko" == navigator.product && !d.webkit && !d.opera; d.ie && (d.gecko = !1); d.webkit && (-1 < a.indexOf("chrome") ? (d.chrome = !0, -1 < a.indexOf("opr") && (d.opera = !0, d.chrome = !1)) : d.safari = !0); var b; d.ieVersion = 0; d.ie && (d.quirks ||
			!document.documentMode ? -1 < a.indexOf("msie") ? b = parseFloat(a.match(/msie (\d+)/)[1]) : -1 < a.indexOf("trident") ? b = parseFloat(a.match(/rv:([\d\.]+)/)[1]) : /(edge)\/((\d+)?[\w\.]+)/i.test(a) ? (b = 12, d.chrome = !1) : b = 7 : b = document.documentMode, d.ieVersion = b, d.ie12 = 12 == b, d.ie11 = 11 == b, d.ie10 = 10 == b, d.ie9 = 9 == b, d.ie8 = 8 == b, d.ie7 = 7 == b, d.ie6 = 7 > b || d.quirks, -1 < a.indexOf("trident") ? d.trident = parseFloat(a.match(/ trident\/(\d+)/)[1]) : d.trident = ""); d.gecko && (b = a.match(/rv:([\d\.]+)/)) && (b = b[1].split("."), b = 1E4 * b[0] + 100 * (b[1] ||
				0) + 1 * (b[2] || 0)); d.webkit && (b = parseFloat(a.match(/ applewebkit\/(\d+)/)[1])); d.HTML5Supported = "File" in window && "FileReader" in window && "Blob" in window; d.ie && 10 > d.ieVersion && (d.HTML5Supported = !1); d.LocalStorageSupported = !1; try { window.localStorage && (window.localStorage.keditor_localstorage_test = "test", "test" == window.localStorage.keditor_localstorage_test && (d.LocalStorageSupported = !0, window.localStorage.removeItem("keditor_localstorage_test"))) } catch (c) { } a = null; try {
					a = new XMLHttpRequest, d.ajaxOnProgressSupported =
						!!(a && "upload" in a && "onprogress" in a.upload)
				} catch (e) { d.ajaxOnProgressSupported = !1 } a = null; a = document.createElement("canvas"); a.getContext && a.getContext("2d") ? d.canvasSupported = !0 : d.canvasSupported = !1; a = null; a = void 0; return d
	}()); RAONKEDITOR.UserAgent || (RAONKEDITOR.UserAgent = function() {
		var a = window && window.navigator && window.navigator.userAgent ? window.navigator.userAgent : "", d = {
			extend: function(a, b) {
				for (var c in b) -1 !== "browser cpu device engine os".indexOf(c) && 0 === b[c].length % 2 && (a[c] = b[c].concat(a[c]));
				return a
			}, has: function(a, b) { return "string" === typeof a ? -1 !== b.toLowerCase().indexOf(a.toLowerCase()) : !1 }, lowerize: function(a) { return a.toLowerCase() }, major: function(a) { return "string" === typeof a ? a.split(".")[0] : void 0 }
		}, b = function() {
			for (var b, c = 0, e, d, f, m, q, r, u = arguments; c < u.length && !q;) {
				var t = u[c], g = u[c + 1]; if ("undefined" === typeof b) for (f in b = {}, g) m = g[f], "object" === typeof m ? b[m[0]] = void 0 : b[m] = void 0; for (e = d = 0; e < t.length && !q;)if (q = t[e++].exec(a)) for (f = 0; f < g.length; f++)r = q[++d], m = g[f], "object" === typeof m &&
					0 < m.length ? 2 == m.length ? b[m[0]] = "function" == typeof m[1] ? m[1].call(this, r) : m[1] : 3 == m.length ? b[m[0]] = "function" !== typeof m[1] || m[1].exec && m[1].test ? r ? r.replace(m[1], m[2]) : void 0 : r ? m[1].call(this, r, m[2]) : void 0 : 4 == m.length && (b[m[0]] = r ? m[3].call(this, r.replace(m[1], m[2])) : void 0) : b[m] = r ? r : void 0; c += 2
			} return b
		}, c = function(a, b) { for (var c in b) if ("object" === typeof b[c] && 0 < b[c].length) for (var e = 0; e < b[c].length; e++) { if (d.has(b[c][e], a)) return "?" === c ? void 0 : c } else if (d.has(b[c], a)) return "?" === c ? void 0 : c; return a },
		e = { ME: "4.90", "NT 3.11": "NT3.51", "NT 4.0": "NT4.0", 2E3: "NT 5.0", XP: ["NT 5.1", "NT 5.2"], Vista: "NT 6.0", 7: "NT 6.1", 8: "NT 6.2", "8.1": "NT 6.3", 10: ["NT 6.4", "NT 10.0"], RT: "ARM" }, f = [[/\((ipad|playbook);[\w\s\);-]+(rim|apple)/i], ["model", "vendor", ["type", "tablet"]], [/applecoremedia\/[\w\.]+ \((ipad)/], ["model", ["vendor", "Apple"], ["type", "tablet"]], [/(apple\s{0,1}tv)/i], [["model", "Apple TV"], ["vendor", "Apple"]], [/(archos)\s(gamepad2?)/i, /(hp).+(touchpad)/i, /(kindle)\/([\w\.]+)/i, /\s(nook)[\w\s]+build\/(\w+)/i,
			/(dell)\s(strea[kpr\s\d]*[\dko])/i], ["vendor", "model", ["type", "tablet"]], [/(kf[A-z]+)\sbuild\/[\w\.]+.*silk\//i], ["model", ["vendor", "Amazon"], ["type", "tablet"]], [/(sd|kf)[0349hijorstuw]+\sbuild\/[\w\.]+.*silk\//i], [["model", c, { "Fire Phone": ["SD", "KF"] }], ["vendor", "Amazon"], ["type", "mobile"]], [/\((ip[honed|\s\w*]+);.+(apple)/i], ["model", "vendor", ["type", "mobile"]], [/\((ip[honed|\s\w*]+);/i], ["model", ["vendor", "Apple"], ["type", "mobile"]], [/(blackberry)[\s-]?(\w+)/i, /(blackberry|benq|palm(?=\-)|sonyericsson|acer|asus|dell|huawei|meizu|motorola|polytron)[\s_-]?([\w-]+)*/i,
			/(hp)\s([\w\s]+\w)/i, /(asus)-?(\w+)/i], ["vendor", "model", ["type", "mobile"]], [/\(bb10;\s(\w+)/i], ["model", ["vendor", "BlackBerry"], ["type", "mobile"]], [/android.+(transfo[prime\s]{4,10}\s\w+|eeepc|slider\s\w+|nexus 7)/i], ["model", ["vendor", "Asus"], ["type", "tablet"]], [/(sony)\s(tablet\s[ps])\sbuild\//i, /(sony)?(?:sgp.+)\sbuild\//i], [["vendor", "Sony"], ["model", "Xperia Tablet"], ["type", "tablet"]], [/(?:sony)?(?:(?:(?:c|d)\d{4})|(?:so[-l].+))\sbuild\//i], [["vendor", "Sony"], ["model", "Xperia Phone"], ["type",
				"mobile"]], [/\s(ouya)\s/i, /(nintendo)\s([wids3u]+)/i], ["vendor", "model", ["type", "console"]], [/android.+;\s(shield)\sbuild/i], ["model", ["vendor", "Nvidia"], ["type", "console"]], [/(playstation\s[3portablevi]+)/i], ["model", ["vendor", "Sony"], ["type", "console"]], [/(sprint\s(\w+))/i], [["vendor", c, { HTC: "APA", Sprint: "Sprint" }], ["model", c, { "Evo Shift 4G": "7373KT" }], ["type", "mobile"]], [/(lenovo)\s?(S(?:5000|6000)+(?:[-][\w+]))/i], ["vendor", "model", ["type", "tablet"]], [/(htc)[;_\s-]+([\w\s]+(?=\))|\w+)*/i, /(zte)-(\w+)*/i,
			/(alcatel|geeksphone|huawei|lenovo|nexian|panasonic|(?=;\s)sony)[_\s-]?([\w-]+)*/i], ["vendor", ["model", /_/g, " "], ["type", "mobile"]], [/(nexus\s9)/i], ["model", ["vendor", "HTC"], ["type", "tablet"]], [/[\s\(;](xbox(?:\sone)?)[\s\);]/i], ["model", ["vendor", "Microsoft"], ["type", "console"]], [/(kin\.[onetw]{3})/i], [["model", /\./g, " "], ["vendor", "Microsoft"], ["type", "mobile"]], [/\s(milestone|droid(?:[2-4x]|\s(?:bionic|x2|pro|razr))?(:?\s4g)?)[\w\s]+build\//i, /mot[\s-]?(\w+)*/i, /(XT\d{3,4}) build\//i], ["model",
			["vendor", "Motorola"], ["type", "mobile"]], [/android.+\s(mz60\d|xoom[\s2]{0,2})\sbuild\//i], ["model", ["vendor", "Motorola"], ["type", "tablet"]], [/android.+((sch-i[89]0\d|shw-m380s|gt-p\d{4}|gt-n8000|sgh-t8[56]9|nexus 10))/i, /((SM-T\w+))/i], [["vendor", "Samsung"], "model", ["type", "tablet"]], [/((s[cgp]h-\w+|gt-\w+|galaxy\snexus|sm-n900))/i, /(sam[sung]*)[\s-]*(\w+-?[\w-]*)*/i, /sec-((sgh\w+))/i], [["vendor", "Samsung"], "model", ["type", "mobile"]], [/(samsung);smarttv/i], ["vendor", "model", ["type", "smarttv"]], [/\(dtv[\);].+(aquos)/i],
		["model", ["vendor", "Sharp"], ["type", "smarttv"]], [/sie-(\w+)*/i], ["model", ["vendor", "Siemens"], ["type", "mobile"]], [/(maemo|nokia).*(n900|lumia\s\d+)/i, /(nokia)[\s_-]?([\w-]+)*/i], [["vendor", "Nokia"], "model", ["type", "mobile"]], [/android\s3\.[\s\w;-]{10}(a\d{3})/i], ["model", ["vendor", "Acer"], ["type", "tablet"]], [/android\s3\.[\s\w;-]{10}(lg?)-([06cv9]{3,4})/i], [["vendor", "LG"], "model", ["type", "tablet"]], [/(lg) netcast\.tv/i], ["vendor", "model", ["type", "smarttv"]], [/(nexus\s[45])/i, /lg[e;\s\/-]+(\w+)*/i],
		["model", ["vendor", "LG"], ["type", "mobile"]], [/android.+(ideatab[a-z0-9\-\s]+)/i], ["model", ["vendor", "Lenovo"], ["type", "tablet"]], [/linux;.+((jolla));/i], ["vendor", "model", ["type", "mobile"]], [/((pebble))app\/[\d\.]+\s/i], ["vendor", "model", ["type", "wearable"]], [/android.+;\s(glass)\s\d/i], ["model", ["vendor", "Google"], ["type", "wearable"]], [/android.+(\w+)\s+build\/hm\1/i, /android.+(hm[\s\-_]*note?[\s_]*(?:\d\w)?)\s+build/i, /android.+(mi[\s\-_]*(?:one|one[\s_]plus)?[\s_]*(?:\d\w)?)\s+build/i], [["model",
			/_/g, " "], ["vendor", "Xiaomi"], ["type", "mobile"]], [/(mobile|tablet);.+rv\:.+gecko\//i], [["type", d.lowerize], "vendor", "model"]], e = [[/microsoft\s(windows)\s(vista|xp)/i], ["name", "version"], [/(windows)\snt\s6\.2;\s(arm)/i, /(windows\sphone(?:\sos)*|windows\smobile|windows)[\s\/]?([ntce\d\.\s]+\w)/i], ["name", ["version", c, e]], [/(win(?=3|9|n)|win\s9x\s)([nt\d\.]+)/i], [["name", "Windows"], ["version", c, e]], [/\((bb)(10);/i], [["name", "BlackBerry"], "version"], [/(blackberry)\w*\/?([\w\.]+)*/i, /(tizen)[\/\s]([\w\.]+)/i,
				/(android|webos|palm\sos|qnx|bada|rim\stablet\sos|meego|contiki)[\/\s-]?([\w\.]+)*/i, /linux;.+(sailfish);/i], ["name", "version"], [/(symbian\s?os|symbos|s60(?=;))[\/\s-]?([\w\.]+)*/i], [["name", "Symbian"], "version"], [/\((series40);/i], ["name"], [/mozilla.+\(mobile;.+gecko.+firefox/i], [["name", "Firefox OS"], "version"], [/(nintendo|playstation)\s([wids3portablevu]+)/i, /(mint)[\/\s\(]?(\w+)*/i, /(mageia|vectorlinux)[;\s]/i, /(joli|[kxln]?ubuntu|debian|[open]*suse|gentoo|arch|slackware|fedora|mandriva|centos|pclinuxos|redhat|zenwalk|linpus)[\/\s-]?([\w\.-]+)*/i,
				/(hurd|linux)\s?([\w\.]+)*/i, /(gnu)\s?([\w\.]+)*/i], ["name", "version"], [/(cros)\s[\w]+\s([\w\.]+\w)/i], [["name", "Chromium OS"], "version"], [/(sunos)\s?([\w\.]+\d)*/i], [["name", "Solaris"], "version"], [/\s([frentopc-]{0,4}bsd|dragonfly)\s?([\w\.]+)*/i], ["name", "version"], [/(ip[honead]+)(?:.*os\s*([\w]+)*\slike\smac|;\sopera)/i], [["name", "iOS"], ["version", /_/g, "."]], [/(mac\sos\sx)\s?([\w\s\.]+\w)*/i, /(macintosh|mac(?=_powerpc)\s)/i], [["name", "Mac OS"], ["version", /_/g, "."]], [/((?:open)?solaris)[\/\s-]?([\w\.]+)*/i,
				/(haiku)\s(\w+)/i, /(aix)\s((\d)(?=\.|\)|\s)[\w\.]*)*/i, /(plan\s9|minix|beos|os\/2|amigaos|morphos|risc\sos|openvms)/i, /(unix)\s?([\w\.]+)*/i], ["name", "version"]], c = b.apply(this, [[/(opera\smini)\/([\w\.-]+)/i, /(opera\s[mobiletab]+).+version\/([\w\.-]+)/i, /(opera).+version\/([\w\.]+)/i, /(opera)[\/\s]+([\w\.]+)/i], ["name", "version"], [/\s(opr)\/([\w\.]+)/i], [["name", "Opera"], "version"], [/(kindle)\/([\w\.]+)/i, /(lunascape|maxthon|netfront|jasmine|blazer)[\/\s]?([\w\.]+)*/i, /(avant\s|iemobile|slim|baidu)(?:browser)?[\/\s]?([\w\.]*)/i,
					/(?:ms|\()(ie)\s([\w\.]+)/i, /(rekonq)\/([\w\.]+)*/i, /(chromium|flock|rockmelt|midori|epiphany|silk|skyfire|ovibrowser|bolt|iron|vivaldi)\/([\w\.-]+)/i], ["name", "version"], [/(trident).+rv[:\s]([\w\.]+).+like\sgecko/i], [["name", "IE"], "version"], [/(edge)\/((\d+)?[\w\.]+)/i], ["name", "version"], [/(yabrowser)\/([\w\.]+)/i], [["name", "Yandex"], "version"], [/(comodo_dragon)\/([\w\.]+)/i], [["name", /_/g, " "], "version"], [/(chrome|omniweb|arora|[tizenoka]{5}\s?browser)\/v?([\w\.]+)/i, /(uc\s?browser|qqbrowser)[\/\s]?([\w\.]+)/i],
				["name", "version"], [/(dolfin)\/([\w\.]+)/i], [["name", "Dolphin"], "version"], [/((?:android.+)crmo|crios)\/([\w\.]+)/i], [["name", "Chrome"], "version"], [/XiaoMi\/MiuiBrowser\/([\w\.]+)/i], ["version", ["name", "MIUI Browser"]], [/android.+version\/([\w\.]+)\s+(?:mobile\s?safari|safari)/i], ["version", ["name", "Android Browser"]], [/FBAV\/([\w\.]+);/i], ["version", ["name", "Facebook"]], [/version\/([\w\.]+).+?mobile\/\w+\s(safari)/i], ["version", ["name", "Mobile Safari"]], [/version\/([\w\.]+).+?(mobile\s?safari|safari)/i],
				["version", "name"], [/webkit.+?(mobile\s?safari|safari)(\/[\w\.]+)/i], ["name", ["version", c, { "1.0": "/8", "1.2": "/1", "1.3": "/3", "2.0": "/412", "2.0.2": "/416", "2.0.3": "/417", "2.0.4": "/419", "?": "/" }]], [/(konqueror)\/([\w\.]+)/i, /(webkit|khtml)\/([\w\.]+)/i], ["name", "version"], [/(navigator|netscape)\/([\w\.-]+)/i], [["name", "Netscape"], "version"], [/(swiftfox)/i, /(icedragon|iceweasel|camino|chimera|fennec|maemo\sbrowser|minimo|conkeror)[\/\s]?([\w\.\+]+)/i, /(firefox|seamonkey|k-meleon|icecat|iceape|firebird|phoenix)\/([\w\.-]+)/i,
					/(mozilla)\/([\w\.]+).+rv\:.+gecko\/\d+/i, /(polaris|lynx|dillo|icab|doris|amaya|w3m|netsurf)[\/\s]?([\w\.]+)/i, /(links)\s\(([\w\.]+)/i, /(gobrowser)\/?([\w\.]+)*/i, /(ice\s?browser)\/v?([\w\._]+)/i, /(mosaic)[\/\s]([\w\.]+)/i], ["name", "version"]]); c.major = d.major(c.version); e = b.apply(this, e); if (b = b.apply(this, f)) void 0 == b.model && (b.model = ""), void 0 == b.type && (b.type = ""), void 0 == b.vendor && (b.vendor = ""); return { browser: c, os: e, device: b }
	}()); RAONKEDITOR.ajax || (RAONKEDITOR.ajax = function() {
		var a = function() {
			try {
				var a =
					new XMLHttpRequest; RAONKEDITOR.ajax.xhrWithCredentials && "withCredentials" in a && (a.withCredentials = !0); return a
			} catch (b) { } try { return new ActiveXObject("Msxml2.XHLHTTP") } catch (c) { } try { return new ActiveXObject("Microsoft.XMLHTTP") } catch (e) { } return null
		}, d = function() { }, b = function(a) { return 4 == a.readyState && (200 <= a.status && 300 > a.status || 304 == a.status || 0 === a.status || 1223 == a.status) }, c = function(a) { var c = null; b(a) && (c = a.responseText); a && a.onreadystatechange && (a.onreadystatechange = d); return c }, e = function(a) {
			var c =
				null; b(a) && (c = a.responseXML, c || (c = a.responseText)); a && a.onreadystatechange && (a.onreadystatechange = d); return c
		}, f = function(b, c, e) {
			var d = !!c, f = a(); if (!f) return null; f.open("GET", b, d); d && (f.onreadystatechange = function() { 4 == f.readyState && c(e(f)) }); try { f.send(null) } catch (h) { return null } if (!d) { var r = setTimeout(function() { try { f.abort() } catch (a) { } clearTimeout(r) }, 5E3); "undefined" == typeof RAONKEDITOR.RAONKMULTIPLETIMEOUT && (RAONKEDITOR.RAONKMULTIPLETIMEOUT = []); RAONKEDITOR.RAONKMULTIPLETIMEOUT.push(r) } return d ?
				"" : e(f)
		}, h = function(b, c, e, d) { var f = !!e, h = a(); if (!h) return null; h.open("POST", b, f); h.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); f && (h.onreadystatechange = function() { 4 == h.readyState && e(d(h, c)) }); try { h.send(c) } catch (r) { return null } if (!f) { var u = setTimeout(function() { try { h.abort() } catch (a) { } clearTimeout(u) }, 5E3); "undefined" == typeof RAONKEDITOR.RAONKMULTIPLETIMEOUT && (RAONKEDITOR.RAONKMULTIPLETIMEOUT = []); RAONKEDITOR.RAONKMULTIPLETIMEOUT.push(u) } return f ? "" : d(h) }; return {
			load: function(a,
				b) { return f(a, b, c) }, loadXml: function(a, b) { return f(a, b, e) }, postData: function(a, b, e) { return h(a, b, e, c) }, postMultiPart: function(b, c) { var e; a: if (e = a()) { try { e.open("POST", b, !1), e.send(c) } catch (d) { e = null; break a } e = e.responseText } else e = null; return e }, postDataCallback: function(a, b, e) { return h(a, b, e, c) }, postFileData: function(b, c) {
					var e; a: if (e = a()) {
						try {
							e.open("POST", b, !1); var d = "--------------------" + (new Date).getTime(); e.setRequestHeader("Content-Type", "multipart/form-data; boundary=" + d); for (var d = "--" +
								d, f = "", h = c.split("&"), r = h.length, u = null, t = "", g = "", y = 0; y < r; y++)u = h[y].split("="), "imagedata" == u[0] ? t = u[1] : (f += d + "\r\n", f += 'Content-Disposition: form-data; name="' + u[0] + '"\r\n\r\n', f += u[1] + "\r\n"); for (var h = null, r = "", v = window.atob(t), A = v.length, h = new Uint8Array(new ArrayBuffer(A)), y = 0; y < A; y++)h[y] = v.charCodeAt(y), r += String.fromCharCode(h[y]); String.fromCharCode.apply([], new Uint8Array(h)); new Uint8Array(h); g = r; f += d + "\r\n"; f += 'Content-Disposition: form-data; name="Filedata"; filename="' + (new Date).getTime() +
									'"\r\n'; f += "Content-Type: image/png\r\n"; f += "\r\n"; f += g + "\r\n"; e.send(f + (d + "--\r\n"))
						} catch (F) { e = null; break a } e = e.responseText
					} else e = null; return e
				}
		}
	}()); RAONKEDITOR.util || (RAONKEDITOR.util = {
		G_IMG_LIST: {}, writeLog: function(a, d, b, c) {
			try {
				if ("undefined" != typeof RAONKSolutionLog && "undefined" != typeof RAONKEditorLogMode && RAONKEditorLogMode) {
					var e = ""; if ("string" == typeof c) {
						var f = c.split("#"); 2 == f.length && ("" == RAONKEDITOR.logGroupingName && "s" == f[1].toLowerCase() ? (RAONKEDITOR.logGroupingName = f[0].toLowerCase(),
							e = "#separator#") : "" != RAONKEDITOR.logGroupingName && RAONKEDITOR.logGroupingName == f[0].toLowerCase() && "e" == f[1].toLowerCase() && (RAONKEDITOR.logGroupingName = ""))
					} RAONKSolution.writeLog(e + "[RAON K EDITOR Editor]", a, d, b)
				}
			} catch (h) { }
		}, G_AP11: 6, G_AP13: 7, trim: function(a) { return "" == a ? a : a.trim ? a.trim() : a.replace(/^[\s\xa0]+|[\s\xa0]+$/g, "") }, rtrim: function(a) { return "" == a ? a : a.replace(/\s+$/, "") }, ltrim: function(a) { return "" == a ? a : a.replace(/^\s+/, "") }, setClass: function(a, d) { a.className = d }, getClass: function(a) { return a.className },
		checkUrl: function(a) { var d = !1; a = a.replace(/ /gm, ""); return d = (new RegExp(/(http|ftp|https|news):\/\/[\S-]+(\.[\S-]+)+([\S.,@?^=%&amp;:\/~+#-]*[\S@?^=%&amp;\/~+#-])?/)).test(a) }, getDefaultIframeSrc: function() { var a = "", a = "document.open();" + (RAONKEDITOR.browser.isCustomDomain() ? 'document.domain="' + document.domain + '";' : "") + " document.close();"; return a = RAONKEDITOR.browser.ie && 12 > RAONKEDITOR.browser.ieVersion ? "javascript:void(function(){" + encodeURIComponent(a) + "}())" : "" }, makeIframe: function() {
			var a; try {
				a =
				document.createElement('<iframe frameborder="0" >')
			} catch (d) { a = document.createElement("iframe") } a.style.margin = "0px"; a.style.padding = "0px"; a.frameBorder = 0; a.style.overflow = "auto"; a.style.overflowX = "hidden"; a.style.backgroundColor = "#ffffff"; a.title = "K Editor"; return a
		}, addEvent: function(a, d, b, c) {
			a.addEventListener ? a.addEventListener(d, b, c) : a.attachEvent && a.attachEvent("on" + d, b); try {
				"" != RAONKEDITOR.CEditorID ? (RAONKEDITOR.RAONKMULTIPLEEVENT = {}, RAONKEDITOR.RAONKMULTIPLEEVENT[RAONKEDITOR.CEditorID] = [],
					RAONKEDITOR.RAONKMULTIPLEEVENT[RAONKEDITOR.CEditorID].push({ element: a, event: d, func: b }), RAONKEDITOR.CEditorID = "") : KEDITORTOP.G_CURRKEDITOR && RAONKEDITOR.RAONKMULTIPLEEVENT[KEDITORTOP.G_CURRKEDITOR.ID] && RAONKEDITOR.RAONKMULTIPLEEVENT[KEDITORTOP.G_CURRKEDITOR.ID].push({ element: a, event: d, func: b })
			} catch (e) { }
		}, removeEvent: function(a, d, b, c) { a.removeEventListener ? a.removeEventListener(d, b, c) : a.detachEvent && a.detachEvent("on" + d, b) }, stopEvent: function(a) {
			"bubbles" in a ? a.bubbles && a.stopPropagation() : a.cancelBubble =
				!0
		}, cancelEvent: function(a) { a.preventDefault ? a.preventDefault() : a.returnValue = !1 }, ajax: { xml_http_request: function() { var a; window.XMLHttpRequest ? a = new XMLHttpRequest : window.ActiveXObject && (a = new ActiveXObject("Microsoft.XMLHTTP")); return a } }, url: { full_url: function(a) { var d = ""; if (0 == a.indexOf("http://") || 0 == a.indexOf("https://")) d = a; else if (0 == a.indexOf("/")) d = location.protocol + "//" + location.host + a; else var d = location.href, b = d.lastIndexOf("/"), d = d.substring(0, b + 1), d = d + a; return d } }, xml: {
			count: function(a,
				d) { return a ? a.getElementsByTagName(d).length : 0 }, getNode: function(a, d) { return null == a || void 0 == a ? null : this.getNodeIdx(a, d, 0) }, getNodeIdx: function(a, d, b) { return a.getElementsByTagName(d)[b] }, getNodeValue: function(a, d) { return null == a || void 0 == a ? "" : this.getNodeValueIdx(a, d, 0) }, getNodeValueIdx: function(a, d, b) { a = this.getNodeIdx(a, d, b); return this.getValue(a) }, getValue: function(a) {
					var d = "", b = ""; if (void 0 != a) try {
						0 < a.childNodes.length && (b = d = a.firstChild.nodeValue); try {
							("product_key" == a.nodeName || "license_key" ==
								a.nodeName || "font" == a.nodeName || "encoding" == a.nodeName || "doctype" == a.nodeName) && 2 <= a.childNodes.length && (d = a.textContent ? a.textContent : b)
						} catch (c) { d = b }
					} catch (e) { d = "parsing error" } return d
				}, getAllNodes: function(a) {
					var d = {}, b = function(a, e) {
						for (var d = a.childNodes, h = !1, k = 0; k < d.length; k++) {
							var l = d[k]; if (3 != l.nodeType && 8 != l.nodeType && 4 != l.nodeType) {
								var h = !0, n = {}; if (0 < l.attributes.length) { n._attributes = {}; for (var p = 0; p < l.attributes.length; p++) { var m = l.attributes[p]; n._attributes[m.nodeName] = m.nodeValue } } "undefined" ==
									typeof e[l.nodeName] ? e[l.nodeName] = n : 0 < e[l.nodeName].length ? e[l.nodeName].push(n) : e[l.nodeName] = [e[l.nodeName], n]; 0 < l.childNodes.length && b(l, n)
							}
						} h || (value = a.textContent || a.nodeTypedValue || "", 4 != a.firstChild.nodeType && (value = value.replace(/^[\s]+|[\s]+&/g, "")), value && "" != value && (e._value = value))
					}; "xml" == a.firstChild.nodeName.toLowerCase() ? b(a.firstChild.nextSibling, d) : b(a.firstChild, d); return d
				}
		}, htmlToLowerCase: function(a) {
			if ("1" != KEDITORTOP.G_CURRKEDITOR._config.useGetHtmlToLowerCase || 8 < KEDITORTOP.RAONKEDITOR.browser.ieVersion &&
				0 == KEDITORTOP.RAONKEDITOR.browser.quirks || 11 <= KEDITORTOP.RAONKEDITOR.browser.ieVersion && 1 == KEDITORTOP.RAONKEDITOR.browser.quirks) return a; var d = RegExp("<[^>]+>", "g"); results = a.match(d); if (null == results) return a; var b = d = -1, c = 0, e = results.length; for (i = 0; i < e; i++) {
					original = results[i]; d = original.indexOf("'"); b = original.indexOf('"'); c = 2; 1 < d * b && (-1 < original.indexOf('="') ? (results[i] = results[i].replace(/\'/g, "raonk_1q"), c = 2) : -1 < original.indexOf("='") && (results[i] = results[i].replace(/\"/g, "raonk_2q"), c = 1));
					stripquoted = 1 == c ? results[i].replace(/ [^=]+= *\'[^\']*\'/g, "") : results[i].replace(/ [^=]+= *"[^"]*"/g, ""); d = RegExp(" [^=]+=[^ >]+", "g"); if (unquoted = stripquoted.match(d)) for (d = unquoted.length, j = 0; j < d; j++) {
						if ("1" == KEDITORTOP.G_CURRKEDITOR._config.xss_use && "0" == KEDITORTOP.G_CURRKEDITOR._config.xss_allow_events_attribute && (b = KEDITORTOP.G_CURRKEDITOR._config.xss_remove_events, "" != b)) { var b = "," + b + ",", f = unquoted[j].split("=")[0]; if (-1 < b.indexOf("," + RAONKEDITOR.util.trim(f) + ",")) return a } addquotes = 1 == c ? unquoted[j].replace(/( [^=]+=)([^ >]+)/g,
							"$1'$2'") : unquoted[j].replace(/( [^=]+=)([^ >]+)/g, '$1"$2"'); results[i] = results[i].replace(unquoted[j], addquotes)
					} results[i] = results[i].replace(/<\/?[^>|^ ]+/, function(a) { return a.toLowerCase() }); results[i] = 1 == c ? results[i].replace(/ [^=|^ ]+=\'/g, function(a) { return a.toLowerCase() }) : results[i].replace(/ [^=|^ ]+="/g, function(a) { return a.toLowerCase() }); results[i] = results[i].replace(/raonk_1q/g, "'"); results[i] = results[i].replace(/raonk_2q/g, '"'); a = a.replace(original, results[i])
				} return a
		}, hexDigits: "0123456789abcdef".split(""),
		hex: function(a) { return isNaN(a) ? "00" : this.hexDigits[(a - a % 16) / 16] + this.hexDigits[a % 16] }, rgb2hex: function(a) { a = a.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/); if (!(null == a || 4 > a.length)) return "#" + this.hex(a[1]) + this.hex(a[2]) + this.hex(a[3]) }, rgbToHex: function() {
			var a, d, b; 1 == arguments.length ? (a = arguments[0].toLowerCase(), a = a.replace("rgb(", ""), a = a.replace("rgba(", ""), a = a.replace(")", ""), b = a.split(","), a = parseInt(b[0], 10), d = parseInt(b[1], 10), b = parseInt(b[2], 10)) : 3 <= arguments.length && (a = arguments[0], d = arguments[1],
				b = arguments[2]); return "#" + (16777216 + (a << 16) + (d << 8) + b).toString(16).slice(1)
		}, raonkSetAttribute: function(a, d, b, c) { "string" == typeof b ? 0 < b.length && (a.style[d] = b + c) : "number" == typeof b && (a.style[d] = b) }, getRealBackgoundImagePath: function(a) { return a = a.replace(/url\(\s*(["']?)\s*([^\)]*)\s*\1\s*\)/i, function(a, b, c) { "1" == KEDITORTOP.G_CURRKEDITOR._config.useKManager && (c = decodeURIComponent(c), c = KEDITORTOP.G_CURRKEDITOR._FRAMEWIN.convertImageAgentSrc(c, !1), c = decodeURIComponent(c)); return c }) }, replaceForXSS: function(a,
			d, b, c, e) {
				var f = !1, h = !1; try {
					if (a) { var k = RegExp("&#x3c;", "gi"); 1 == k.test(a) && (f = !0, a = a.replace(k, "&amp;lt;")); k = RegExp("&#x3e;", "gi"); 1 == k.test(a) && (f = !0, a = a.replace(k, "&amp;gt;")); k = RegExp("&#9;", "gi"); 1 == k.test(a) && (f = !0, a = a.replace(k, "")); k = RegExp("&#13;", "gi"); 1 == k.test(a) && (f = !0, a = a.replace(k, "")) } if ("" != b) for (var l = b.split(","), n = l.length, p = 0; p < n; p++) {
						var m = l[p], q = new RegExp("<" + m + "[^>]*>[\\s\\S]*?</" + m + ">", "gim"); 1 == q.test(a) && (f = !0, a = a.replace(q, "")); var r = new RegExp("<" + m + "[^>]*>", "gi"); 1 ==
							r.test(a) && (f = !0, a = a.replace(r, ""))
					} if ("" != c) for (var u = c.split(","), t = u.length, p = 0; p < t; p++) { var g = u[p], y = new RegExp("(?:" + g + ')\\s*=\\s*"[^"]*"\\s*', "gi"), v = new RegExp("(?:" + g + ")\\s*=\\s*'[^']*'\\s*", "gi"); if (y.test(a) || v.test(a)) f = !0, a = a.replace(y, ""), a = a.replace(v, "") } if ("" != d) for (var A = d.split(","), F = A.length, p = 0; p < F; p++) {
						var E = new RegExp("<\\/" + A[p] + ">", "gi"), x = a.match(new RegExp("<" + A[p] + "[^>]*>", "gi")); if (x) {
							var f = !0, J = x.length; d = ""; b = !1; for (c = 0; c < J; c++) {
								if ("script" == A[p]) {
									var w = x[c].match(RegExp("src\\s*=\\s*(?:'|\")([^(\"|')]*)(?:'|\")",
										"gi")); if (null != w) try { for (var H = w[0].substring(5, w[0].length - 1), z = KEDITORTOP.G_CURRKEDITOR._config.xss_allow_url, k = 0; k < z.length; k++)if (H == z[k]) { b = !0; break } } catch (G) { h = !0 }
								} 0 == b && (d = x[c].toLowerCase(), d = d.replace("<", "&lt;"), d = d.replace(">", "&gt;"), a = a.replace(x[c], d))
							} 0 == b && (a = a.replace(E, "&lt;/" + A[p] + "&gt;"))
						}
					} null != e && void 0 != e && e && (f = !0, y = RegExp("(<[^>]*url)(\\(['\"]?(javascript|jav ascript|vbscript)([^'\")]*)['\"]?[\\)]?[;]?\\))", "gi"), a = a.replace(y, "$1)"), e = "", 0 < KEDITORTOP.G_CURRKEDITOR._config.xss_check_attribute.length &&
						(e = KEDITORTOP.G_CURRKEDITOR._config.xss_check_attribute.join("|"), v = new RegExp("(<[^>]*(" + e + ')=")(javascript|jav ascript|vbscript).*?"', "gi"), a = a.replace(v, '$1"')))
				} catch (C) { h = !0 } 0 == f || 1 == h ? a = "" : "" == a && (a = KEDITORTOP.G_CURRKEDITOR._FRAMEWIN.getDefaultParagraphValue(!0)); return a
		}, removeTags: function(a, d) { if ("" != d) for (var b = d.split(","), c = null, e = b.length, f = 0, h = 0; h < e; h++)for (c = a.getElementsByTagName(b[h]), f = c.length, --f; 0 <= f; f--) { var k = c[f]; k.parentNode.removeChild(k) } }, removeEvents: function(a, d, b) {
			if ("" !=
				d) { a = d.split(","); d = b.length; for (var c = 0, e = 0; e < d; e++)for (var c = a.length, f = 0; f < c; f++)b[e].removeAttribute(a[f]) }
		}, removeElement: function(a) { try { if (null != a) { for (; a.firstChild;)a.parentNode.insertBefore(a.firstChild, a); a.parentNode.removeChild(a) } } catch (d) { } }, removeElementWithChildNodes: function(a) {
			try {
				if (null != a) {
					if ("1" == a.nodeType && "iframe" == a.tagName.toLowerCase()) {
						var d = a.contentDocument || a.contentWindow.document || a.document; if (d) {
							if (a.contentWindow) for (var b in a.contentWindow) if (a.contentWindow.hasOwnProperty(b)) try {
								a.contentWindow[b] =
								null, delete a.contentWindow[b]
							} catch (c) { } for (var e = d.getElementsByTagName("script"); 0 != e.length;) { e[0].parentNode.removeChild(e[0]); for (var f in e) delete e[f] } d.body && (d.body.innerHTML = ""); d.body.parentNode && (d.body.parentNode.innerHTML = "")
						} a.setAttribute("src", "")
					} for (; a.hasChildNodes();)try { a.removeChild(a.firstChild), a.firstChild = null, delete a.firstChild } catch (h) { } a.parentNode.removeChild(a); try { delete null } catch (k) { }
				}
			} catch (l) { }
		}, officeCleanByDom: function(a, d) {
			if ("" == a || void 0 == a) return ""; a = KEDITORTOP.G_CURRKEDITOR._FRAMEWIN.adjustMsoBorder(a);
			var b = !1; /([:| ])([-]{0,1}[0-9]+|[-]{0,1}[0-9]*[.]{1}[0-9]+)pt/gi.test(a) && (b = !0); /([:| ])([:| ][-]{0,1}[0-9]+|[-]{0,1}[0-9]*[.]{1}[0-9]+)cm/gi.test(a) && (b = !0); if (1 == b) {
				b = document.createElement("div"); b.innerHTML = a; b.style.display = "none"; KEDITORTOP.G_CURRKEDITOR._FRAMEWIN.document.body.appendChild(b); for (var c = b.getElementsByTagName("*"), e = c.length, f = 0; f < e; f++) {
					var h = c[f]; if (h.style.width) {
						var k = h.style.width.toLowerCase(); -1 < k.indexOf("pt") ? (k = 4 * parseFloat(k) / 3, 0 < k && 1 > k && (k = 1), 0 > k && (k = 0), k = Math.round(k) +
							"px", h.style.width = k) : -1 < k.indexOf("cm") && (_cm = 37.795275593333 * parseFloat(k), 0 < _cm && 1 > _cm && (_cm = 1), 0 > _cm && (_cm = 0), k = Math.round(_cm) + "px", h.style.width = k)
					} h.style.height && (k = h.style.height.toLowerCase(), -1 < k.indexOf("pt") ? (k = 4 * parseFloat(k) / 3, 0 < k && 1 > k && (k = 1), 0 > k && (k = 0), k = Math.round(k) + "px", h.style.height = k) : -1 < k.indexOf("cm") && (_cm = 37.795275593333 * parseFloat(k), 0 < _cm && 1 > _cm && (_cm = 1), 0 > _cm && (_cm = 0), k = Math.round(_cm) + "px", h.style.height = k)); k = ""; h.style.borderStyle && (k = h.style.borderStyle); "" != h.style.borderTop &&
						(h.style.borderTop = RAONKEDITOR.util.replacePtOrCmToPx(h.style.borderTop)); "" != h.style.borderRight && (h.style.borderRight = RAONKEDITOR.util.replacePtOrCmToPx(h.style.borderRight)); "" != h.style.borderBottom && (h.style.borderBottom = RAONKEDITOR.util.replacePtOrCmToPx(h.style.borderBottom)); "" != h.style.borderLeft && (h.style.borderLeft = RAONKEDITOR.util.replacePtOrCmToPx(h.style.borderLeft)); "" != h.style.borderWidth && (h.style.borderWidth = RAONKEDITOR.util.replacePtOrCmToPx(h.style.borderWidth)); "" != k && (h.style.borderStyle =
							k); RAONKEDITOR.util.adjustBorderStyle(h)
				} a = b.innerHTML; try { b.innerHTML = "", b.parentNode.removeChild(b), delete null } catch (l) { }
			} return a
		}, officeClean: function(a, d) {
			if ("" == a || void 0 == a) return ""; var b = null, c = []; a = RAONKEDITOR.util.officeCleanByDom(a, d); a = a.replace(/\.0px/g, "px"); a = a.replace(/\t/g, ""); a = RAONKEDITOR.util.removeOfficeDummyTag(a, "\x3c!--[if supportFields]>", "<![endif]--\x3e"); b = RegExp("<o:p></o:p>", "gi"); a = a.replace(b, ""); b = RegExp("<o:p>\\s+</o:p>", "gi"); a = a.replace(b, ""); b = RegExp("<o:p ([^>]+)></o:p>",
				"gi"); a = a.replace(b, ""); b = RegExp("<o:p ([^>]+)>\\s+</o:p>", "gi"); a = a.replace(b, ""); b = RegExp("<w:sdt[^>]*>", "gi"); a = a.replace(b, ""); b = RegExp("</w:sdt>", "gi"); a = a.replace(b, ""); b = RegExp('<[^>]+(lang=["]?en-us["])[^>]*>', "gi"); if (b = a.match(b)) for (var c = b.length, e = 0; e < c; e++) { var f = b[e], h = RegExp('\\slang=[\\"]?en-us[\\"]?', "gi"), f = f.replace(h, ""); a = a.replace(b[e], f) } b = RegExp("<[^>]+(mso)[^>]*>", "gi"); if (b = a.match(b)) for (c = b.length, e = 0; e < c; e++)f = b[e], "1" == KEDITORTOP.G_CURRKEDITOR._config.removeMsoClass &&
					(h = RegExp('\\sclass=[\\"]?(mso)\\w+[\\"]?', "gi"), f = f.replace(h, "")), f = f.replace(/&quot;/gi, "^"), h = RegExp("mso-[^:]+:\\^\\^(;?)", "gi"), f = f.replace(h, ""), h = RegExp('(\\s+)?mso-number-format:(\\s+)?"(.+?)"(\\s+)?;', "gi"), f = f.replace(h, ""), h = RegExp('(\\s+)?mso-number-format:(\\s+)?"(.+?)"(\\s+)?;?', "gi"), f = f.replace(h, ""), h = RegExp("\\s?mso-[\\w\uac00-\ud7a3\\-: ?'\"\\^@%\\.\\\\_]+; ?", "gi"), f = f.replace(h, ""), h = RegExp("\\s?mso-[\\w\uac00-\ud7a3\\-: ?]+(['\"])", "gi"), f = f.replace(h, "$1"), f = f.replace(/\^/gi,
						"&quot;"), a = a.replace(b[e], f); a = RAONKEDITOR.util.replaceAll(a, "hairline", "dotted"); b = new RegExp("(<span[^>]*?raon_placeholder.*?>)(?:\\s|<br>|<br />|  | |)</span>", "gim"); a = a.replace(b, "$1" + unescape("%u200B") + "</span>"); for (e = 0; 5 > e; e++) {
							c = ["o:p", "span", "font"]; h = c.length; for (f = 0; f < h; f++)b = new RegExp("<" + c[f] + "[^>]*></" + c[f] + ">", "gi"), a = a.replace(b, ""), b = new RegExp("<" + c[f] + "[^>]*>&nbsp;</" + c[f] + ">", "gi"), a = a.replace(b, "&nbsp;"), "span" != c[f] && (b = new RegExp("<" + c[f] + "[^>]*> </" + c[f] + ">", "gi"), a = a.replace(b,
								"&nbsp;")); c = ["o", "v", "w", "m", "x"]; h = c.length; for (f = 0; f < h; f++)0 == f && (a = a.replace(/<o:p/gi, "<raonko:p")), -1 < a.indexOf("<" + c[f]) && (b = new RegExp("<" + c[f] + ":[^/>]+/>", "gi"), a = a.replace(b, ""), b = new RegExp("<" + c[f] + ":[^>]+>[^<]*</" + c[f] + ":[^>]+>", "gi"), a = a.replace(b, ""), "v" == c[f] && (b = new RegExp("<" + c[f] + ":[^>]+>", "gi"), a = a.replace(b, ""), b = new RegExp("</" + c[f] + ":[^>]+>", "gi"), a = a.replace(b, ""))), f == h - 1 && (a = a.replace(/<raonko:p/gi, "<o:p"))
						} e = "(<span[^>]*?raon_placeholder.*?>)(" + unescape("%u200B") + "?)</span>";
			b = new RegExp(e, "gim"); a = a.replace(b, "$1</span>"); a = a.replace(/style=""/gi, ""); a = a.replace(/style=''/gi, ""); a = a.replace(/\s>/gi, ">"); a = KEDITORTOP.RAONKEDITOR.util.replaceOneSpaceToNbsp(a); a = a.replace(/<\/td>&nbsp;<\/tr>/g, "</td></tr>"); a = a.replace(/<\/td>&nbsp;<\/td>/g, "</td></td>"); a = a.replace(/<\/th>&nbsp;<\/tr>/g, "</th></tr>"); a = a.replace(/<\/th>&nbsp;<\/th>/g, "</th></th>"); if (KEDITORTOP.RAONKEDITOR.browser.ie && 10 > KEDITORTOP.RAONKEDITOR.browser.ieVersion) {
				var b = a.length, f = c = !1, k = h = "", l = ""; if (-1 ==
					a.indexOf("<") && -1 == a.indexOf(">")) for (e = 0; e < b; e++)h = a.charAt(e), k = a.charAt(e + 1), " " != h && 32 != h.charCodeAt(0) || " " != k && 32 != k.charCodeAt(0) || (h = "&nbsp;"), l += h; else for (e = 0; e < b; e++)h = a.charAt(e), k = a.charAt(e + 1), "<" == h ? (c = !1, f = "p" == k.toLowerCase() ? !0 : !1) : ">" == h && 1 == f ? c = !0 : 1 != c || " " != h && 32 != h.charCodeAt(0) || " " != k && 32 != k.charCodeAt(0) || (h = "&nbsp;"), l += h; a = l
			} 0 == d && (a = KEDITORTOP.RAONKEDITOR.util.removeLocalFileImage(a)); return a
		}, removeLocalFileImage: function(a) {
			return "1" == KEDITORTOP.G_CURRKEDITOR._config.useKManager ?
				a : "" == a || void 0 == a ? "" : a = a.replace(RegExp("<img[^>]+file:///[^>]+>", "gi"), "")
		}, html2xhtml: function(a) { if ("" == a || void 0 == a) return ""; for (var d = "area base br col embed frame hr img input link meta param".split(" "), b = d.length, c = null, e = RegExp(), f = 0; f < b; f++)c = d[f], e = new RegExp("<" + c + " ([^>]*)>", "gi"), a = a.replace(e, "<" + c + " $1 />"), e = new RegExp("<" + c + ">", "gi"), a = a.replace(e, "<" + c + " />"); return a }, emptyTagRemove: function(a) {
			if ("" == a || void 0 == a) return ""; try {
				if ("1" == KEDITORTOP.G_CURRKEDITOR._config.empty_tag_remove) {
					var d =
						document.createElement("div"); d.innerHTML = a; for (var b = ["p", "div"], c = b.length, e = [], f = 0; f < c; f++) { for (var h = d.getElementsByTagName(b[f]), k = h.length, l = 0; l < k; l++)n = h[l], "" == n.innerHTML && e.push(n); for (; 0 < e.length;) { var n = e.pop(); n.parentNode.removeChild(n) } } a = d.innerHTML
				}
			} catch (p) { } return a
		}, emptyTagRemoveCheckInContentTag: function(a, d) {
			if ("" == a || void 0 == a) return ""; try {
				if ("1" == KEDITORTOP.G_CURRKEDITOR._config.empty_tag_remove) {
					var b = document.createElement("div"); b.innerHTML = a; for (var c = b.getElementsByTagName("p"),
						e = c.length, f, h, k = "", l = [], n = !1, p = 0; p < e; p++)if (f = c[p], k = "textContent" in f ? f.textContent : f.innerText, "" == k) { h = f.innerHTML; for (var n = !1, m = d.length, q = 0; q < m; q++)if (-1 < h.toLowerCase().indexOf("<" + d[q])) { n = !0; break } 0 == n && l.push(f) } for (var r = l.length; 0 < r;)f = l.pop(), b.removeChild(f); a = b.innerHTML
				}
			} catch (u) { } return a
		}, replaceBrOrSpaceToNbspString: function(a, d) {
			reg_exp = new RegExp("<" + d + "><br></" + d + ">", "gi"); a = a.replace(reg_exp, "<" + d + ">&nbsp;</" + d + ">"); reg_exp = new RegExp("<" + d + "><br/></" + d + ">", "gi"); a = a.replace(reg_exp,
				"<" + d + ">&nbsp;</" + d + ">"); reg_exp = new RegExp("<" + d + "><br /></" + d + ">", "gi"); a = a.replace(reg_exp, "<" + d + ">&nbsp;</" + d + ">"); reg_exp = new RegExp("<" + d + "></" + d + ">", "gi"); a = a.replace(reg_exp, "<" + d + ">&nbsp;</" + d + ">"); reg_exp = new RegExp("<" + d + ">\\s+</" + d + ">", "gi"); a = a.replace(reg_exp, "<" + d + ">&nbsp;</" + d + ">"); reg_exp = new RegExp("<" + d + " ([^>]+)><br></" + d + ">", "gi"); a = a.replace(reg_exp, "<" + d + " $1>&nbsp;</" + d + ">"); reg_exp = new RegExp("<" + d + " ([^>]+)><br/></" + d + ">", "gi"); a = a.replace(reg_exp, "<" + d + " $1>&nbsp;</" + d +
					">"); reg_exp = new RegExp("<" + d + " ([^>]+)><br /></" + d + ">", "gi"); a = a.replace(reg_exp, "<" + d + " $1>&nbsp;</" + d + ">"); reg_exp = new RegExp("<" + d + " ([^>]+)></" + d + ">", "gi"); a = a.replace(reg_exp, "<" + d + " $1>&nbsp;</" + d + ">"); reg_exp = new RegExp("<" + d + " ([^>]+)>\\s+</" + d + ">", "gi"); return a = a.replace(reg_exp, "<" + d + " $1>&nbsp;</" + d + ">")
		}, htmlRevision: function(a, d) {
			if ("" == a || void 0 == a) return ""; var b = null; "1" == KEDITORTOP.G_CURRKEDITOR._config.removeComment && (a = a.replace(/(<style[^>]*>\s*\x3c!--)|(\x3c!--.*?--\x3e)|(\x3c!--[\w\W\n\s]+?--\x3e)/gi,
				"$1")); if (RAONKEDITOR.browser.ie && 11 > RAONKEDITOR.browser.ieVersion || d) for (b = RegExp("<(td|th)([^>]*?>)([\\\\w\\\\W\\\\s]*?)<p([^>]*?>)<br ?/?></p>([\\\\w\\\\W\\\\s]*?)</(td|th)>", "gi"); b.test(a);)a = a.replace(b, "<$1$2$3<p$4&nbsp;</p>$5</$6>"); else for (b = RegExp("<(td|th)([^>]*?>)([\\\\w\\\\W\\\\s]*?)<p([^>]*?>)&nbsp;</p>([\\\\w\\\\W\\\\s]*?)</(td|th)>", "gi"); b.test(a);)a = a.replace(b, "<$1$2$3<p$4<br></p>$5</$6>"); b = new RegExp("(<span[^>]*?raon_placeholder.*?>)(?:\\s|<br>|<br />|  | |)</span>", "gim");
			a = a.replace(b, "$1" + unescape("%u200B") + "</span>"); for (var c = KEDITORTOP.G_CURRKEDITOR._config.setDefaultValueInEmptyTag, e = c.length, f = 0; f < e; f++) {
				var h = c[f]; RAONKEDITOR.browser.ie && 11 > RAONKEDITOR.browser.ieVersion || d || "1" == KEDITORTOP.G_CURRKEDITOR._config.ie11_BugFixed_ReplaceBr ? "span" != h && (a = RAONKEDITOR.util.replaceBrOrSpaceToNbspString(a, h)) : RAONKEDITOR.browser.ie && 11 == RAONKEDITOR.browser.ieVersion && 0 == h.indexOf("h") ? (b = new RegExp("<" + h + "></" + h + ">", "gi"), a = a.replace(b, "<" + h + ">" + unescape("%u200B") + "</" +
					h + ">"), b = new RegExp("<" + h + ">&nbsp;</" + h + ">", "gi"), a = a.replace(b, "<" + h + ">" + unescape("%u200B") + "</" + h + ">"), b = new RegExp("<" + h + ">\\s+</" + h + ">", "gi"), a = a.replace(b, "<" + h + ">" + unescape("%u200B") + "</" + h + ">"), b = new RegExp("<" + h + " ([^>]+)></" + h + ">", "gi"), a = a.replace(b, "<" + h + " $1>" + unescape("%u200B") + "</" + h + ">"), b = new RegExp("<" + h + " ([^>]+)>&nbsp;</" + h + ">", "gi"), a = a.replace(b, "<" + h + " $1>" + unescape("%u200B") + "</" + h + ">"), b = new RegExp("<" + h + " ([^>]+)>\\s+</" + h + ">", "gi"), a = a.replace(b, "<" + h + " $1>" + unescape("%u200B") +
						"</" + h + ">")) : "span" != h && (b = new RegExp("<" + h + "></" + h + ">", "gi"), a = a.replace(b, "<" + h + "><br></" + h + ">"), b = new RegExp("<" + h + ">&nbsp;</" + h + ">", "gi"), a = a.replace(b, "<" + h + "><br></" + h + ">"), b = new RegExp("<" + h + ">\\s+</" + h + ">", "gi"), a = a.replace(b, "<" + h + "><br></" + h + ">"), b = new RegExp("<" + h + " ([^>]+)></" + h + ">", "gi"), a = a.replace(b, "<" + h + " $1><br></" + h + ">"), b = new RegExp("<" + h + " ([^>]+)>&nbsp;</" + h + ">", "gi"), a = a.replace(b, "<" + h + " $1><br></" + h + ">"), b = new RegExp("<" + h + " ([^>]+)>\\s+</" + h + ">", "gi"), a = a.replace(b, "<" + h +
							" $1><br></" + h + ">"))
			} b = "(<span[^>]*?raon_placeholder.*?>)(" + unescape("%u200B") + "?)</span>"; b = new RegExp(b, "gim"); a = a.replace(b, "$1</span>"); 0 == KEDITORTOP.RAONKEDITOR.browser.ie && "1" == KEDITORTOP.G_CURRKEDITOR._config.removeCarriageReturnInTag && (b = RegExp("<([^>]*)([\r\n])(.*?)>", "igm"), b.test(a) && (a = a.replace(b, "<$1$3>")), b = RegExp("<([^>]*)([\n])(.*?)>", "igm"), b.test(a) && (a = a.replace(b, "<$1$3>"))); c = ""; c = KEDITORTOP.RAONKEDITOR.browser.ie && "xp" != KEDITORTOP.RAONKEDITOR.UserAgent.os.version.toLowerCase() ?
				unescape("%u200B") : unescape("%uFEFF"); b = RegExp("<a ([^>]+)></a>", "gi"); a = a.replace(b, "<a $1>" + c + "</a>"); b = RegExp("<c:foreach", "gi"); a = a.replace(b, "<c:forEach"); b = RegExp("</c:foreach", "gi"); a = a.replace(b, "</c:forEach"); b = new RegExp(unescape("%u202c"), "g"); return a = a.replace(b, "")
		}, nbspRemove: function(a) { if ("" == a || void 0 == a) return ""; var d = null, d = RegExp("<p>&nbsp;</p>", "gi"); a = a.replace(d, "<p></p>"); d = RegExp("<p ([^>]+)>&nbsp;</p>", "gi"); return a = a.replace(d, "<p $1></p>") }, ImageSrcConvert: function(a,
			d, b) { var c = document.createElement("div"); c.innerHTML = a; var e = c.getElementsByTagName("img"), f = e.length, h = null, k = "", l = ""; b += "_raonk_base64_image_"; for (var n = 0; n < f; n++)h = e[n], l = b + n, k = h.src, 1 == d ? -1 < k.indexOf("data:image") && -1 < k.indexOf("base64,") && (this.G_IMG_LIST[l] = k, h.src = l) : -1 < k.indexOf(b) && (h.src = this.G_IMG_LIST[l], this.G_IMG_LIST[l] = ""); d = !1; for (var p in this.G_IMG_LIST) { d = !0; break } d && (a = c.innerHTML); return a }, getUrlString: function(a) {
				a = RAONKEDITOR.util.replaceAll(a, " ", "%20"); a = RAONKEDITOR.util.replaceAll(a,
					"&nbsp;", "%21"); a = RAONKEDITOR.util.replaceAll(a, "!", "%20"); a = RAONKEDITOR.util.replaceAll(a, '"', "%22"); a = RAONKEDITOR.util.replaceAll(a, "#", "%23"); a = RAONKEDITOR.util.replaceAll(a, "$", "%24"); a = RAONKEDITOR.util.replaceAll(a, "%", "%25"); a = RAONKEDITOR.util.replaceAll(a, "&", "%26"); a = RAONKEDITOR.util.replaceAll(a, "'", "%27"); a = RAONKEDITOR.util.replaceAll(a, "+", "%2B"); a = RAONKEDITOR.util.replaceAll(a, "/", "%2F"); a = RAONKEDITOR.util.replaceAll(a, ":", "%3A"); a = RAONKEDITOR.util.replaceAll(a, ";", "%3B"); a = RAONKEDITOR.util.replaceAll(a,
						"<", "%3C"); a = RAONKEDITOR.util.replaceAll(a, "=", "%3D"); a = RAONKEDITOR.util.replaceAll(a, ">", "%3E"); a = RAONKEDITOR.util.replaceAll(a, "?", "%3F"); return a = RAONKEDITOR.util.replaceAll(a, "@", "%40")
			}, replaceAll: function(a, d, b) { a && "" != a && (a = a.split(d).join(b)); return a }, getSkinNames: function() { return "blue brown darkgray gold gray green orange pink purple red silver yellow".split(" ") }, replaceOneSpaceToNbsp: function(a) {
				if (3 == KEDITORTOP.G_CURRKEDITOR.agentPasteFormatType && "0" == KEDITORTOP.G_CURRKEDITOR._config.replaceOneSpaceToNbspInExcel) return a;
				var d = a, b, c = ""; try {
					for (var e = "span font a b strong i em strike u sup sub".split(" "), f = e.length, h = 0; h < f; h++) {
						var k = !0, l = e[h]; ("font" == l || "b" == l || "em" == l || "sup" == l || "sub" == l) && 0 > d.toLowerCase().indexOf("<" + l) && (k = !1); if (k) for (var n = 0; n < f; n++) {
							c = d; b = new RegExp("<" + e[h] + " *([^>?+])*>(\\s+)</" + e[n] + ">", "gi"); reg_exp2 = "u" == e[n] ? new RegExp("</" + e[h] + ">(\\s+)<" + e[n] + "[^l]", "gi") : new RegExp("</" + e[h] + ">(\\s+)<" + e[n] + " *([^>?+])*>", "gi"); reg_exp3 = new RegExp("<" + e[h] + " *([^>?+])*>(\\s+)<" + e[n] + ">", "gi"); reg_exp4 =
								"u" == e[n] ? new RegExp("</" + e[h] + ">(\\s+)</" + e[n] + "[^l]", "gi") : new RegExp("</" + e[h] + ">(\\s+)</" + e[n] + " *([^>?+])*>", "gi"); try {
									var p = d.match(b); if (p) for (var m = p.length, q = 0; q < m; q++) { var r = p[q]; if (!("b" == e[h] && -1 < r.toLowerCase().indexOf("<br"))) var u = />\s+</.exec(r), t = u[0].replace(RegExp("\\s\\s", "gi"), "&nbsp;&nbsp;"), r = r.replace(u, t), d = d.replace(p[q], r) } if (p = d.match(reg_exp2)) for (m = p.length, q = 0; q < m; q++)r = p[q], "b" == e[n] && -1 < match_string.toLowerCase().indexOf("<br") || (u = />\s+</.exec(r), t = u[0].replace(RegExp("\\s\\s",
										"gi"), "&nbsp;&nbsp;"), r = r.replace(u, t), d = d.replace(p[q], r)); if (p = d.match(reg_exp3)) for (m = p.length, q = 0; q < m; q++)r = p[q], "b" == e[h] && -1 < match_string.toLowerCase().indexOf("<br") || (u = />\s+</.exec(r), t = u[0].replace(RegExp("\\s\\s", "gi"), "&nbsp;&nbsp;"), r = r.replace(u, t), d = d.replace(p[q], r)); if (p = d.match(reg_exp4)) for (m = p.length, q = 0; q < m; q++)r = p[q], u = />\s+</.exec(r), t = u[0].replace(RegExp("\\s\\s", "gi"), "&nbsp;&nbsp;"), r = r.replace(u, t), d = d.replace(p[q], r)
								} catch (g) { d = c }
						}
					} for (h = 0; h < f; h++)if (k = !0, l = e[h], ("font" ==
						l || "b" == l || "em" == l || "sup" == l || "sub" == l) && 0 > d.toLowerCase().indexOf("<" + l) && (k = !1), k) {
							"b" == l ? (d = d.replace(/<br/gi, "<temp_br"), d = d.replace(/<\/br/gi, "</temp_br")) : "u" == l && (d = d.replace(/<ul/gi, "<temp_ul"), d = d.replace(/<\/ul/gi, "</temp_ul")); for (n = 0; n < f; n++)b = new RegExp("/" + e[h] + ">\\s<" + e[n], "gi"), d = d.replace(b, "/" + e[h] + ">&nbsp;<" + e[n]), b = new RegExp("/" + e[h] + ">\\s\\n<" + e[n], "gi"), d = d.replace(b, "/" + e[h] + ">&nbsp;<" + e[n]); "b" == l ? (d = d.replace(/<temp_br/gi, "<br"), d = d.replace(/<\/temp_br/gi, "</br")) : "u" == l &&
								(d = d.replace(/<temp_ul/gi, "<ul"), d = d.replace(/<\/temp_ul/gi, "</ul"))
					}
				} catch (y) { d = a } return d
			}, parseIntOr0: function(a) { a = parseInt(a, 10); return isNaN(a) ? 0 : a }, parseFloatOr0: function(a) { a = parseFloat(a, 10); return isNaN(a) ? 0 : a }, getUserLang: function(a) {
				a = a.toLowerCase(); var d = "ko-kr"; "auto" == a && (d = "en-us", (a = navigator.language || navigator.userLanguage) || (a = d), a = a.toLowerCase()); if (-1 < a.indexOf("ko")) d = "ko-kr"; else if (-1 < a.indexOf("en")) d = "en-us"; else if (-1 < a.indexOf("ja")) d = "ja-jp"; else if (-1 < a.indexOf("zh-cn") ||
					0 == a.indexOf("cn")) d = "zh-cn"; else if (-1 < a.indexOf("zh-tw") || 0 == a.indexOf("tw")) d = "zh-tw"; return d
			}, postFormData: function(a, d, b, c) { void 0 == c && (c = []); var e = a.createElement("form"); e.method = "post"; e.action = d; e.target = b; d = c.length; for (b = 0; b < d; b++) { var f = a.createElement("input"); f.type = "hidden"; f.name = c[b][0]; f.value = c[b][1]; e.appendChild(f) } a.body.appendChild(e); e.submit(); a.body.removeChild(e) }, base64_encode: function(a) {
				var d = "", b, c, e, f, h, k, l = 0; for (a = RAONKEDITOR.util.utf8_encode(a); l < a.length;)b = a.charCodeAt(l++),
					c = a.charCodeAt(l++), e = a.charCodeAt(l++), f = b >> 2, b = (b & 3) << 4 | c >> 4, h = (c & 15) << 2 | e >> 6, k = e & 63, isNaN(c) ? h = k = 64 : isNaN(e) && (k = 64), d = d + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(f) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(b) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(h) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(k); return d
			}, base64_decode: function(a) {
				var d = "", b, c, e, f, h, k = 0; for (a =
					a.replace(/[^A-Za-z0-9\+\/\=]/g, ""); k < a.length;)b = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(a.charAt(k++)), c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(a.charAt(k++)), f = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(a.charAt(k++)), h = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(a.charAt(k++)), b = b << 2 | c >> 4, c = (c & 15) << 4 | f >> 2, e = (f & 3) << 6 | h, d += String.fromCharCode(b), 64 != f && (d += String.fromCharCode(c)),
						64 != h && (d += String.fromCharCode(e)); return d = RAONKEDITOR.util.utf8_decode(d)
			}, makeEncryptParam: function(a) {
				a = RAONKEDITOR.util.base64_encode(a); 10 <= a.length ? (a = RAONKEDITOR.util.insertAt(a, RAONKSolution.Agent.G_AP10, RAONKEDITOR.util.G_AP27), a = RAONKEDITOR.util.insertAt(a, RAONKEDITOR.util.G_AP11, RAONKSolution.Agent.G_AP.G_AP22), a = RAONKEDITOR.util.insertAt(a, RAONKEDITOR.browser.G_AP12, RAONKEDITOR.util.G_AP25), a = RAONKEDITOR.util.insertAt(a, RAONKEDITOR.util.G_AP13, RAONKSolution.Agent.G_AP23), a = RAONKEDITOR.util.insertAt(a,
					RAONKSolution.Agent.G_AP10, RAONKSolution.Agent.G_AP.G_AP29), a = RAONKEDITOR.util.insertAt(a, RAONKEDITOR.util.G_AP11, RAONKEDITOR.browser.G_AP24), a = RAONKEDITOR.util.insertAt(a, RAONKEDITOR.browser.G_AP12, RAONKSolution.Agent.G_AP20)) : (a = RAONKEDITOR.util.insertAt(a, a.length - 1, "$"), a = RAONKEDITOR.util.insertAt(a, 0, "$")); return a = a.replace(/[+]/g, "%2B")
			}, makeEncryptParamOld: function(a) { a = RAONKEDITOR.util.base64_encode(a); a = "R" + a; a = RAONKEDITOR.util.base64_encode(a); return a = a.replace(/[+]/g, "%2B") }, makeEncryptParamEx: function(a) {
				var d =
					RAONKEDITOR.util.makeGuid().toLowerCase(), d = RAONKEDITOR.util.encode(d), d = d.substring(0, 15), b = G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Utf8.parse(d); b.sigBytes = 16; a = G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Utf8.parse(a); a = G_CURRKEDITOR._FRAMEWIN.CryptoJS.AES.encrypt(a, b, { iv: b }); b = G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Base64._map; G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Base64._map = "adebcfijghklopmnqruvstwyzxAHIJDBCEFLMNUVGKRSTOWXPQYZ0163847259+/="; a = d + G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Base64.stringify(a.ciphertext);
				G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Base64._map = b; return a = a.replace(/[+]/g, "%2B")
			}, makeDecryptReponseMessage: function(a) { var d = function(a, c) { var e = a.split(""); e.splice(c, 1); return e = e.join("") }; a = a.replace(/ /g, ""); a = a.replace(/\r/g, ""); a = a.replace(/\n/g, ""); a = a.replace(/%2B/g, "+"); 15 <= a.length ? (a = d(a, 9), a = d(a, 6), a = d(a, 8), a = d(a, 7), a = d(a, 9), a = d(a, 6), a = d(a, 8)) : (a = a.replace(/#/g, ""), a = a.replace(/$/g, "")); return a = RAONKEDITOR.util.base64_decode(a) }, makeDecryptReponseMessageEx: function(a) {
				try {
					a = a.replace(/ /g,
						""); a = a.replace(/\r/g, ""); a = a.replace(/\n/g, ""); a = a.replace(/%2B/g, "+"); var d = a.substring(0, 15); a = a.substring(15); d = G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Utf8.parse(d); d.sigBytes = 16; var b = G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Base64._map; G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Base64._map = "adebcfijghklopmnqruvstwyzxAHIJDBCEFLMNUVGKRSTOWXPQYZ0163847259+/="; a = G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Base64.parse(a); G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Base64._map = b; a = G_CURRKEDITOR._FRAMEWIN.CryptoJS.AES.decrypt({ ciphertext: a },
							d, { iv: d }).toString(G_CURRKEDITOR._FRAMEWIN.CryptoJS.enc.Utf8)
				} catch (c) { } return a
			}, utf8_decode: function(a) { for (var d = "", b = 0, c = c1 = c2 = 0; b < a.length;)c = a.charCodeAt(b), 128 > c ? (d += String.fromCharCode(c), b++) : 191 < c && 224 > c ? (c2 = a.charCodeAt(b + 1), d += String.fromCharCode((c & 31) << 6 | c2 & 63), b += 2) : (c2 = a.charCodeAt(b + 1), c3 = a.charCodeAt(b + 2), d += String.fromCharCode((c & 15) << 12 | (c2 & 63) << 6 | c3 & 63), b += 3); return d }, utf8_encode: function(a) {
				a = a.replace(/\r\n/g, "\n"); for (var d = "", b = 0; b < a.length; b++) {
					var c = a.charCodeAt(b); 128 >
						c ? d += String.fromCharCode(c) : (127 < c && 2048 > c ? d += String.fromCharCode(c >> 6 | 192) : (d += String.fromCharCode(c >> 12 | 224), d += String.fromCharCode(c >> 6 & 63 | 128)), d += String.fromCharCode(c & 63 | 128))
				} return d
			}, encode: function(a) {
				var d = "", b, c, e, f, h, k, l = 0; for (a = RAONKEDITOR.util.utf8_encode(a); l < a.length;)b = a.charCodeAt(l++), c = a.charCodeAt(l++), e = a.charCodeAt(l++), f = b >> 2, b = (b & 3) << 4 | c >> 4, h = (c & 15) << 2 | e >> 6, k = e & 63, isNaN(c) ? h = k = 64 : isNaN(e) && (k = 64), d = d + "adebcfijghklopmnqruvstwyzxAHIJDBCEFLMNUVGKRSTOWXPQYZ0163847259+/=".charAt(f) +
					"adebcfijghklopmnqruvstwyzxAHIJDBCEFLMNUVGKRSTOWXPQYZ0163847259+/=".charAt(b) + "adebcfijghklopmnqruvstwyzxAHIJDBCEFLMNUVGKRSTOWXPQYZ0163847259+/=".charAt(h) + "adebcfijghklopmnqruvstwyzxAHIJDBCEFLMNUVGKRSTOWXPQYZ0163847259+/=".charAt(k); return d
			}, insertAt: function(a, d, b) { return String.prototype.insertAt ? a.insertAt(d, b) : a.substr(0, d) + b + a.substr(d) }, G_AP27: "r", G_AP25: "o", stringToXML: function(a) {
				var d; try {
					window.DOMParser ? d = (new DOMParser).parseFromString(a, "text/xml") : (d = new ActiveXObject("Microsoft.XMLDOM"),
						d.async = "false", d.loadXML(a))
				} catch (b) { d = null } return d
			}, xmlToString: function(a) { return window.ActiveXObject ? a.xml : (new XMLSerializer).serializeToString(a) }, removeOfficeDummyTag: function(a, d, b) { var c = a; try { for (var e = a.indexOf(d), f = a.indexOf(b); -1 < e && -1 < f;)var h = c.substring(0, e), k = c.substring(f + b.length), c = h + k, e = c.indexOf(d), f = c.indexOf(b) } catch (l) { c = a } return c }, getStyle: function(a, d) {
				var b, c = !1, e; "fontSize" == d ? G_CURRKEDITOR._FRAMEWIN._iframeWin.getComputedStyle ? (KEDITORTOP.RAONKEDITOR.browser.ie &&
					11 == KEDITORTOP.RAONKEDITOR.browser.ieVersion && G_CURRKEDITOR._config.defaultFontSize && -1 < G_CURRKEDITOR._config.defaultFontSize.indexOf("px") && (e = a.style.lineHeight, a.style.lineHeight = "1", c = !0), b = G_CURRKEDITOR._FRAMEWIN._iframeWin.getComputedStyle(a, "")) : a.currentStyle && (b = a.currentStyle) : a.currentStyle ? b = a.currentStyle : G_CURRKEDITOR._FRAMEWIN._iframeWin.getComputedStyle && (b = G_CURRKEDITOR._FRAMEWIN._iframeWin.getComputedStyle(a, "")); b ? "all" != d && (b.getProperty ? b = b.getProperty(d) : "fontSize" == d && c ? (b =
						b.lineHeight, void 0 != e && (a.style.lineHeight = e)) : b = b[d], "fontSize" == d && (0 > b.indexOf("px") && 0 > b.indexOf("pt") && 0 > b.indexOf("em") ? b = "" : -1 < b.indexOf("px") && (b = G_CURRKEDITOR._FRAMEWIN.getFontSizeStyle(b, "")))) : b = ""; return b
			}, hashTable: function(a) {
				this.length = 0; this.items = {}; for (var d in a) a.hasOwnProperty(d) && (this.items[d] = a[d], this.length++); this.setItem = function(a, c) { var e = void 0; this.hasItem(a) ? e = this.items[a] : this.length++; this.items[a] = c; return e }; this.getItem = function(a) {
					return this.hasItem(a) ?
						this.items[a] : void 0
				}; this.hasItem = function(a) { return this.items.hasOwnProperty(a) }; this.removeItem = function(a) { if (this.hasItem(a)) return previous = this.items[a], this.length--, delete this.items[a], previous }; this.keys = function() { var a = [], c; for (c in this.items) this.hasItem(c) && a.push(c); return a }; this.values = function() { var a = [], c; for (c in this.items) this.hasItem(c) && a.push(this.items[c]); return a }; this.each = function(a) { for (var c in this.items) this.hasItem(c) && a(c, this.items[c]) }; this.clear = function() {
					this.items =
					{}; this.length = 0
				}
			}, isExistEditorName: function(a, d) { if (void 0 == a || "" == a) return 1; var b = RAONKEDITOR.RAONKMULTIPLE["raonk_frame_" + a]; return void 0 == b || null == b ? 0 : "" != d.EditorHolder && RAONKEDITOR.RAONKHOLDER[a] == d.EditorHolder ? 3 : 2 }, getNewNextEditorName: function(a) { var d = "", b = a.split("_"), c = 0; do d = b.length, 1 < d && (a = a.replace("_" + b[d - 1], "")), d = a + "_" + c, c++; while (0 < RAONKEDITOR.util.isExistEditorName(d)); return d }, replacePtOrCmToPx: function(a) {
				try {
					for (var d = a.toLowerCase().split(" "), b = d.length, c, e, f = "", h = 0; h <
						b; h++) { var k = d[h]; -1 < k.indexOf("pt") ? (c = 4 * parseFloat(k) / 3, 0 < c && 1 > c && (c = 1), 0 > c && (c = 0), e = Math.round(c) + "px", f = "" != f ? f + " " + e : e) : -1 < k.indexOf("cm") ? (_cm = 37.795275593333 * parseFloat(k), 0 < _cm && 1 > _cm && (_cm = 1), 0 > _cm && (_cm = 0), e = Math.round(_cm) + "px", f = "" != f ? f + " " + e : e) : f = "" != f ? f + " " + k : k } return "" != f ? f : a
				} catch (l) { return a }
			}, GetUserRunTimeEditor: function(a) { a = a.toLowerCase(); var d = "", b = !1; if ("" == a || "agent" == a) b = !0; d = 1 == RAONKEDITOR.browser.HTML5Supported ? "html5" : "html4"; return { isAgent: b, mode: d } }, CheckEditorVisible: function(a) {
				a =
				document.getElementById("raonk_frame_" + a); var d = !1; "undefined" != typeof a && (d = !(0 == a.offsetWidth && 0 == a.offsetHeight)) && (d = "hidden" != (window.getComputedStyle ? window.getComputedStyle(a, null) : a.currentStyle).visibility); return d
			}, IsCustomDomain: function(a) { if (!RAONKEDITOR.browser.ie) return !1; var d = a.domain; a = RAONKEDITOR.util.GetDocWindow(a).location.hostname; return d != a && d != "[" + a + "]" }, GetDocWindow: function(a) { return a.parentWindow || a.defaultView }, getUnitSize: function(a) {
				var d = 1; switch (a.toLowerCase()) {
					case "kb": d *=
						1024; break; case "mb": d *= 1048576; break; case "gb": d *= 1073741824
				}return d
			}, getUnit: function(a) { a = a.toLowerCase(); var d = ""; -1 < a.indexOf("mb") ? d = a.substring(a.indexOf("mb")) : -1 < a.indexOf("gb") ? d = a.substring(a.indexOf("gb")) : -1 < a.indexOf("kb") ? d = a.substring(a.indexOf("kb")) : -1 < a.indexOf("b") && (d = a.substring(a.indexOf("b"))); return d }, bytesToSize: function(a) {
				a = parseInt(a, 10); var d = "0 byte"; isNaN(a) && (a = "", d = "N/A"); d = { size: 0, unit: "byte", toString: d }; if (0 == a) return d; var b = parseInt(Math.floor(Math.log(a) / Math.log(1024)));
				d.size = Math.round(a / Math.pow(1024, b) * 100, 2) / 100; d.unit = ["bytes", "KB", "MB", "GB", "TB"][b]; d.toString = d.size + " " + d.unit; return d
			}, HtmlToText: function(a) {
				var d = a; try {
					var b = d.match(/<body[^>]*>([\w|\W]*)<\/body>/im); b && (d = b[1]); d = d.replace(/\r/g, ""); d = d.replace(/[\n|\t]/g, ""); d = d.replace(/[\v|\f]/g, ""); d = d.replace(/<p><br><\/p>/gi, "\n"); d = d.replace(/<P>&nbsp;<\/P>/gi, "\n"); "undefined" != typeof RAONKEDITOR && RAONKEDITOR.browser.ie && 11 <= RAONKEDITOR.browser.ieVersion && (d = d.replace(/<br(\s)*\/?><\/p>/gi, "</p>"),
						d = d.replace(/<br(\s[^\/]*)?><\/p>/gi, "</p>")); var d = d.replace(/<br(\s)*\/?>/gi, "\n"), d = d.replace(/<br(\s[^\/]*)?>/gi, "\n"), d = d.replace(/<\/p(\s[^\/]*)?>/gi, "\n"), d = d.replace(/<\/li(\s[^\/]*)?>/gi, "\n"), d = d.replace(/<\/tr(\s[^\/]*)?>/gi, "\n"), c = d.lastIndexOf("\n"); -1 < c && "\n" == d.substring(c) && (d = d.substring(0, c)); d = d.replace(RegExp("</?[^>]*>", "gi"), ""); d = d.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&nbsp;/g, " ").replace(/&amp;/g, "&")
				} catch (e) { d = a } return d
			}, _getEditor: function(a) {
				var d = null,
				d = RAONKEDITOR.util.getEditorByName(a); return void 0 == d || null == d ? (-1 == location.href.indexOf("editor_container.html") && RAONKEDITOR.ShowDestroyAlert && alert("Editor's Name is not correct, Please check editor's name. \ror\rThe editor was not initialized, Please check the location of api call"), RAONKEDITOR.ShowDestroyAlert = !0, null) : d
			}, getEditorByName: function(a) {
				var d = null; if (void 0 == a || "" == a) d = G_CURRKEDITOR; else {
					try {
						if ("1" == KEDITORTOP.G_CURRKEDITOR._config.ignoreDifferentEditorName && 1 == RAONKEDITOR.RAONKMULTIPLEID.length) {
							var b =
								RAONKEDITOR.RAONKMULTIPLEID[0]; a != b && RAONKEDITOR.isLoadedEditorEx(b) && (a = b)
						}
					} catch (c) { } d = RAONKEDITOR.RAONKMULTIPLE["raonk_frame_" + a]; "object" != typeof d && (d = null)
				} return d
			}, _getEditorName: function(a) {
				if (void 0 == a || "" == a) {
					if (null != RAONKEDITOR.RAONKMULTIPLEID && void 0 != RAONKEDITOR.RAONKMULTIPLEID && 1 == RAONKEDITOR.RAONKMULTIPLEID.length) return RAONKEDITOR.RAONKMULTIPLEID[0]; -1 == location.href.indexOf("editor_container.html") && RAONKEDITOR.ShowDestroyAlert && alert("Editor's Name is not correct, Please check editor's name. \ror\rThe editor was not initialized, Please check the location of api call");
					RAONKEDITOR.ShowDestroyAlert = !0; return null
				} return a
			}, _setEditor: function(a) { a = RAONKEDITOR.util._getEditor(a); if (void 0 == a || null == a) return !1; a._FRAMEWIN.setCurrentEditor(a); return a }, getValueByMultiMode: function() { switch (G_CURRKEDITOR._config.changeMultiValueMode) { case "doctype": return RAONKEDITOR.getHtmlValueExWithDocType(); case "htmlEx": return RAONKEDITOR.getHtmlValueEx(); case "html": return RAONKEDITOR.getHtmlValue(); case "bodyEx": return RAONKEDITOR.getBodyValueEx(); case "body": return RAONKEDITOR.getBodyValue() } },
		setValueByMultiMode: function(a) { switch (G_CURRKEDITOR._config.changeMultiValueMode) { case "doctype": return RAONKEDITOR.setHtmlValueExWithDocType(a); case "htmlEx": return RAONKEDITOR.setHtmlValueEx(a); case "html": return RAONKEDITOR.setHtmlValue(a); case "bodyEx": return RAONKEDITOR.setBodyValueEx(a); case "body": return RAONKEDITOR.setBodyValue(a) } }, removeHtmlLangAttrDuplication: function(a) {
			var d = a; try {
				var b = a.match(/<html ([^>]+)>/i); if (b) {
					var c = b[0], e = /( lang+)=["']?((?:.(?!["']?\s+(?:\S+)=|[>"']))+.)["']?/i,
					f = c.match(RegExp("( lang+)=[\"']?((?:.(?![\"']?\\s+(?:\\S+)=|[>\"']))+.)[\"']?", "gi")).length; if (1 < f) { for (var h = 1; h < f; h++)c = c.replace(e, ""); a = a.replace(b[0], c) } d = a
				}
			} catch (k) { } return d
		}, setInLineDefaultStyle: function(a) {
			if ("2" == a._config.setDefaultStyle.value) {
				var d = ["span", "font"], b = d.length, c = function(a, b) {
					if ("" == a.style.fontFamily) try { a.style.fontFamily = KEDITORTOP.RAONKEDITOR.util.getStyle(a, "fontFamily") } catch (c) { } if ("" == a.style.fontSize) {
						for (var e = !1, d = 1; 5 >= d; d++)if (null != G_CURRKEDITOR._FRAMEWIN.GetParentbyTagName(a,
							"h" + d)) { e = !0; break } if (0 == e) if ("font" == a.tagName.toLowerCase()) { if ("" == a.size) { d = RAONKEDITOR.util.getStyle(a, "fontSize"); try { isNaN(d) ? a.style.fontSize = d : a.size = d } catch (f) { } } if ("" == a.face) try { a.face = RAONKEDITOR.util.getStyle(a, "fontFamily") } catch (h) { } } else try { a.style.fontSize = RAONKEDITOR.util.getStyle(a, "fontSize") } catch (u) { }
					} if ("" == a.style.lineHeight && "span" != a.tagName.toLowerCase()) if ("2" == b._config.setDefaultStyle.line_height_mode) {
						d = ""; try { d = RAONKEDITOR.util.getStyle(a, "lineHeight") } catch (t) { } var e =
							-1 < b._config.defaultLineHeight.indexOf("px") ? !0 : !1, g = -1 < d.indexOf("px") ? !0 : !1; if (0 == KEDITORTOP.RAONKEDITOR.browser.ie && 1 == g && 0 == e) { g = a.style.fontSize; if ("" == g) try { g = RAONKEDITOR.util.getStyle(a, "fontSize") } catch (y) { } if ("" != g) { var e = -1 < g.indexOf("pt") ? !0 : !1, v = -1 < g.indexOf("px") ? !0 : !1; if (e || v) g = parseFloat(g), e && (g *= 4 / 3), e = -1 < b._config.defaultLineHeight.indexOf("%") ? !0 : !1, d = parseFloat(d) / g, d = Math.round(10 * d), d = 1 * d / 10, e && (d = 100 * d + "%") } } "" != d && (a.style.lineHeight = d)
					} else a.style.lineHeight = b._setting.line_height &&
						"" != b._setting.line_height ? b._setting.line_height : b._config.defaultLineHeight; 0 != a.tagName.toLowerCase().indexOf("h") && "span" != a.tagName.toLowerCase() && ("" == a.style.marginTop && (a.style.marginTop = b._config.defaultFontMarginTop), "" == a.style.marginBottom && (a.style.marginBottom = b._config.defaultFontMarginBottom)); e = b._config.setDefaultUserStyle.length; for (d = 0; d < e; d++)if ("" == a.style[b._config.setDefaultUserStyle[d]]) try { a.style[b._config.setDefaultUserStyle[d]] = RAONKEDITOR.util.getStyle(a, b._config.setDefaultUserStyle[d]) } catch (A) { }
				};
				if (document.createTreeWalker) (function(b) { var e; for (b = document.createTreeWalker(b, NodeFilter.SHOW_TEXT, null, !1); e = b.nextNode();) { var f = e.nodeValue, f = f.replace(/\n/g, ""), f = f.replace(/\t/g, ""); "" != f && (e = e.parentNode) && e.tagName && (f = e.tagName.toLowerCase(), -1 < ("," + d.join(",") + ",").indexOf("," + f + ",") && c(e, a)) } })(a._BODY); else for (var e = 0; e < b; e++)for (var f = a._DOC.getElementsByTagName(d[e]), h = f.length, h = h - 1; 0 <= h; h--)c(f[h], a); d = "li p h1 h2 h3 h4 h5 div".split(" "); b = d.length; for (e = 0; e < b; e++)for (f = a._DOC.getElementsByTagName(d[e]),
					h = f.length, --h; 0 <= h; h--)c(f[h], a)
			}
		}, postimageToServer: function(a, d, b) {
			var c = a._DOC, e = a._config; if ("1" == e.useKManager && "0" == e.mimeUse) {
				for (var f = a._FRAMEWIN, h = [], k = "", l = ["img", "table", "td", "th"], n = l.length, p = [], m = 0; m < n; m++)p.push(c.body.getElementsByTagName(l[m])); p.push(Array(c.body)); n = p.length; for (m = 0; m < n; m++)for (var q = p[m], r = q.length, l = 0; l < r; l++) {
					var u = q[l], t = ""; if ("img" == u.tagName.toLowerCase()) {
						if (t = u.src, !(-1 < t.toLowerCase().indexOf("file:///none"))) if (h.push(t), t = decodeURIComponent(t), t = f.convertImageAgentSrc(t,
							!1), t = decodeURIComponent(t), 0 == t.indexOf("file:///")) { var t = t.replace("file:///", ""), g = RAONKEDITOR.util.makeGuid(); u.src = "cid:" + g; k += "cid:" + g + RAONKSolution.Agent.formfeed + t + RAONKSolution.Agent.formfeed + "0" + RAONKSolution.Agent.vertical } else if ("1" == e.replaceOutsideImage.use && (-1 < t.toLowerCase().indexOf("http:") || -1 < t.toLowerCase().indexOf("https:"))) {
								var g = t.replace("http://", "").replace("https://", "").split("/")[0], y = !0, v = a._config.replaceOutsideImage.exceptDomain, A = v.length, F = a._config.replaceOutsideImage.targetDomain,
								E = F.length; if (0 < E) for (var y = !1, x = 0; x < E; x++) { if (-1 < g.indexOf(F[x])) { y = !0; break } } else if (0 < A) for (y = !0, x = 0; x < A; x++)if (-1 < g.indexOf(v[x])) { y = !1; break } y && (h.push(t), g = RAONKEDITOR.util.makeGuid(), u.src = "cid:" + g, k += "cid:" + g + RAONKSolution.Agent.formfeed + t + RAONKSolution.Agent.formfeed + "0" + RAONKSolution.Agent.vertical)
							}
					} else if (t = u.style.backgroundImage, "none" == t && (t = ""), "" == t && u.getAttribute("background") && (t = u.getAttribute("background")), t = t.replace('url("', "").replace('")', ""), t = t.replace("url('", "").replace("')",
						""), t = t.replace("url(", "").replace(")", ""), -1 < t.toLowerCase().indexOf("file:///none") && (t = ""), "" != t && "none" != t.toLowerCase()) t = decodeURIComponent(t), t = f.convertImageAgentSrc(t, !1), t = decodeURIComponent(t), 0 == t.indexOf("file:///") && (h.push(t), t = t.replace("file:///", ""), g = RAONKEDITOR.util.makeGuid(), u.style.backgroundImage = "url(cid:" + g + ")", k += "cid:" + g + RAONKSolution.Agent.formfeed + t + RAONKSolution.Agent.formfeed + "0" + RAONKSolution.Agent.vertical); else if ("1" == e.replaceOutsideImage.use && (-1 < t.toLowerCase().indexOf("http:") ||
							-1 < t.toLowerCase().indexOf("https:"))) { g = t.replace("http://", "").replace("https://", "").split("/")[0]; y = !1; v = a._config.replaceOutsideImage.exceptDomain; A = v.length; if (0 == A) y = !1; else for (x = 0; x < A; x++)if (-1 < g.indexOf(v[x])) { y = !0; break } y || (h.push(t), g = RAONKEDITOR.util.makeGuid(), u.style.backgroundImage = "url(cid:" + g + ")", k += "cid:" + g + RAONKSolution.Agent.formfeed + t + RAONKSolution.Agent.formfeed + "0" + RAONKSolution.Agent.vertical) }
				} m = [["kcmd", "KC30"]]; m.push(["k00", {
					browser: RAONKEDITOR.UserAgent.browser.name.toLowerCase(),
					useragent: encodeURIComponent(navigator.userAgent)
				}]); m.push(["k04", encodeURIComponent(e.handlerUrl)]); m.push(["k05", e.security.keyValue]); m.push(["k06", "EDITOR"]); m.push(["k14", "K Editor"]); m.push(["k15", 1]); m.push(["k16", e.managerLang]); m.push(["k19", e.saveFileNameRule]); m.push(["k20", e.saveFileNameRuleEx]); m.push(["k21", e.saveFolderNameRule]); m.push(["k41", RAONKEDITOR.util.parseIntOr0(e.security.encryptParam)]); h = []; p = f.G_FormData.length; for (l = 0; l < p; l++)n = {
					form: encodeURIComponent(f.G_FormData[l].form_name +
						"=" + f.G_FormData[l].form_value)
				}, h.push(n); m.push(["k70", h]); m.push(["k71", f.getHttpHeaderAgentData(a)]); k = k.substring(0, k.length - 1); m.push(["kp1", encodeURIComponent(k)]); m.push(["kp2", e.fileFieldID]); m = f.setManagerParam("{}", m); m = RAONKSolution.Agent.makeEncryptManagerParam(m); f.sendMessageToAgent(m, function(b) {
					var e = KEDITORTOP.RAONKSolution.Agent.parseRtn(b); b = e.code; e = e.valueArr; if (1E3 == b || 7777 == b) {
						b = []; for (var g = 0; g < e.length; g++) {
							var f = e[g].split(RAONKSolution.Agent.formfeed), h = ""; if ((h = "1" == f[1] ?
								f[2] : "[FAIL]" + f[2]) && "" != h) { h = h.replace(/\r\n/g, ""); h = h.replace(/\n/g, ""); h = h.replace(/\t/g, ""); try { var k = { strUrl: h }, t; t = "function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.beforeInsertUrl ? KEDITORTOP.G_CURRKEDITOR._config.event.beforeInsertUrl(KEDITORTOP.G_CURRKEDITOR.ID, k) : KEDITORTOP.KEDITORWIN.RAONKEDITOR_BeforeInsertUrl(KEDITORTOP.G_CURRKEDITOR.ID, k); null != t && void 0 != t && (h = t) } catch (l) { } } if ("[FAIL]" == h.substring(0, 6)) try {
									if (k = { strType: "imageUpload", strMessage: h }, "function" == typeof a._config.event.onError) a._config.event.onError(a.ID,
										k); else KEDITORTOP.KEDITORWIN.RAONKEDITOR_OnError(a.ID, k)
								} catch (n) { } b.push(h)
						} k = 0; h = ["img", "table", "td", "th"]; e = h.length; t = []; for (g = 0; g < e; g++)t.push(c.body.getElementsByTagName(h[g])); t.push(Array(c.body)); e = t.length; for (g = 0; g < e; g++)for (var f = t[g], m = f.length, p = 0; p < m; p++) {
							var A = f[p], h = ""; if ("img" == A.tagName.toLowerCase()) {
								if (h = A.src, 0 == h.indexOf("cid:")) {
									h = b[k]; k++; var r = []; "" != h && "0" != a._config.imageCustomPropertyDelimiter && (r = h.split(a._config.imageCustomPropertyDelimiter), 1 < r.length && (h = r[0]));
									var q = h.split("?"); 2 == q.length && (h = q[0], q = q[1].split("^"), 2 != q.length && 3 == q.length && (h += "?" + q[2])); "[FAIL]" == h.substring(0, 6) ? (A.setAttribute("src", a._config.webPath.image + "dialog/image_xbox.jpg"), A.setAttribute("alt", h.substring(6, h.length).replace(/"/g, "'"))) : A.src = h; A.getAttribute("border") && "" != A.getAttribute("border") && 0 != A.getAttribute("border") && (A.style.border = A.getAttribute("border") + "px solid currentColor"); if (r && 1 < r.length) for (h = r.length, g = 1; g < h; g++)q = r[g].split("^"), 2 == q.length && A.setAttribute(q[0],
										q[1])
								}
							} else h = A.style.backgroundImage, "" == h && A.getAttribute("background") && (h = A.getAttribute("background")), h = h.replace('url("', "").replace('")', ""), h = h.replace("url('", "").replace("')", ""), h = h.replace("url(", "").replace(")", ""), 0 == h.indexOf("cid:") && (h = b[k], k++, "" != h && "0" != a._config.imageCustomPropertyDelimiter && (r = h.split(a._config.imageCustomPropertyDelimiter), 1 < r.length && (h = r[0])), q = h.split("?"), 2 == q.length && (h = q[0], q = q[1].split("^"), 2 != q.length && 3 == q.length && (h += "?" + q[2])), "" != h && (A.style.backgroundImage =
								"url(" + h + ")", A.removeAttribute("background")))
						} d()
					}
				}, null, b.async)
			} else { if ("upload" == G_CURRKEDITOR._config.uploadMethod) for (b = c.body.getElementsByTagName("img"), e = b.length, f = G_CURRKEDITOR._FRAMEWIN, m = 0; m < e; m++)b[m].getAttribute("raon_chart") && (k = b[m].src, "data:" == k.substring(0, 5) && (k = k.split(","), 2 == k.length && (l = f.getExtFromImageDataSrc(k[0]), f.postBase64ImageToServer(k[1], l, b[m], !1)))); d() }
		}, setBodyBackground: function(a) {
			a._PageProp.bshowgrid && 1 == a._PageProp.bshowgrid || "1" == a._config.horizontalLine.use &&
				-1 < G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.style.backgroundImage.indexOf(a._config.horizontalLine.url) || ("" != G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.style.backgroundImage ? a._PageProp.bodyimage = G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.style.backgroundImage : G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.getAttribute("background") && (a._PageProp.bodyimage = G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.getAttribute("background").replace(/\\/gi, "/")), "" != G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.style.backgroundColor &&
					(a._PageProp.bodycolor = G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.style.backgroundColor), "" != G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.style.backgroundAttachment && (a._PageProp.bodyattachment = G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.style.backgroundAttachment), "" != G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.style.backgroundRepeat && (a._PageProp.bodyrepeat = G_CURRKEDITOR._FRAMEWIN._iframeDoc.body.style.backgroundRepeat))
		}, checkServerLicense: function(a) {
			var d = RAONKEDITOR.util.makeEncryptParamOld("ED" + a._config.unitAttributeDelimiter +
				a._FRAMEWIN.___ + a._config.unitAttributeDelimiter + a._config.productKey); try {
					_S = "https:" != location.protocol ? a._FRAMEWIN.S1.toString() : a._FRAMEWIN.S2.toString(); _S = _S.replace(/,/gi, ""); _S = _S + "?t=" + RAONKEDITOR.util.getTimeStamp(); var b = document.createElement("div"), c = RAONKEDITOR.util.getDefaultIframeSrc(); b.innerHTML = '<iframe name="initCheckframe" id="initCheckframe" style="display:none;" src="' + c + '" title="RAON K Editor"></iframe>'; b.style.display = "none"; document.body.appendChild(b); RAONKEDITOR.util.postFormData(document,
						_S, "initCheckframe", [["p", d]]); RAONKEDITOR.util.addEvent(b.firstChild, "load", function() { b.firstChild.contentWindow.postMessage("check", "*") }); if (window.postMessage) {
							var e = function(c) {
								c = RAONKEDITOR.util.trim(c.data); "1" == c || "2" == c ? alert(RAONKEDITOR.util.makeDecryptReponseMessage(a._FRAMEWIN.M1)) : "3" == c ? alert(RAONKEDITOR.util.makeDecryptReponseMessage(a._FRAMEWIN.M2)) : "4" == c && alert(RAONKEDITOR.util.makeDecryptReponseMessage(a._FRAMEWIN.M3)); document.body.removeChild(b); RAONKEDITOR.util.removeEvent(window,
									"message", e)
							}; RAONKEDITOR.util.addEvent(window, "message", e)
						}
				} catch (f) { }
		}, checkForOverlap: function(a, d) { var b = a.getBoundingClientRect(), c = d.getBoundingClientRect(), e = b.left <= c.left; return (e ? b : c).right > (e ? c : b).left ? (e = b.top <= c.top, (e ? b : c).bottom > (e ? c : b).top) : !1 }, leadingZeros: function(a, d) { var b = ""; a = a.toString(); if (a.length < d) for (i = 0; i < d - a.length; i++)b += "0"; return b + a }, removeDuplicatesArray: function(a) { if (!a) return null; for (var d = {}, b = [], c = 0; c < a.length; c++)a[c] in d || (b.push(a[c]), d[a[c]] = !0); return b },
		jsonToString: function(a) { return KEDITORTOP.G_CURRKEDITOR._FRAMEWIN.JSON.stringify(a) }, stringToJson: function(a) { return KEDITORTOP.G_CURRKEDITOR._FRAMEWIN.JSON.parse(a) }, saveJsonToLocalStorage: function(a, d) { var b = !0, c = RAONKEDITOR.util.jsonToString(d); try { window.localStorage.setItem(a, c) } catch (e) { b = !1 } return b }, loadJsonFromLocalStorage: function(a) { a = window.localStorage[a]; var d = null; a && (d = RAONKEDITOR.util.stringToJson(a)); return d }, makeGuidTagName: function(a) {
			var d = 0, b = (new Date).getTime().toString(32),
			c; for (c = 0; 5 > c; c++)b += Math.floor(65535 * Math.random()).toString(32); return (a || "o_") + b + (d++).toString(32)
		}, makeGuid: function(a) { var d = function() { return (65536 * (1 + Math.random()) | 0).toString(16).substring(1) }, d = (d() + d() + d() + d() + d() + d() + d() + d()).toLowerCase(); void 0 != a && (d = a + "-" + d); return d }, trim: function(a) { return a.trim ? a.trim() : a.replace(/^[\s\xa0]+|[\s\xa0]+$/g, "") }, initHandlerCheck: function(a, d, b) {
			if ((a = RAONKEDITOR.util.parseDataFromServer(a)) && -1 < a.toLowerCase().indexOf("hello raonk")) -1 < a.indexOf("-") &&
				(RAONKEDITOR.ServerReleaseVer = a.split("-")[1]); else if (a && 0 < a.length) if (-1 < a.indexOf(d.unitDelimiter)) d = a.split(d.unitDelimiter), RAONKEDITOR.ServerReleaseVer = d[1], RAONKEDITOR._0_ = d[2], alert(d[0]); else if (a && 0 == a.indexOf("[FAIL]")) try {
					var c = RAONKEDITOR.util.makeDecryptReponseMessage(a.substring(6)); b = { strType: "handlerCheck", strMessage: c }; if ("function" == typeof d.event.onError) d.event.onError(d.editor_id, b); else "function" === typeof RAONKEDITOR_OnError ? RAONKEDITOR_OnError(d.editor_id, b) : alert("Error Type : handlerCheck\nError Message : " +
						c)
				} catch (e) { } else alert(a); else c = "", b = 0 < b.InitXml.length ? b.InitXml : "raonkeditor.config.xml", "ko-kr" == d.lang ? (c = "Editor\uc758 \uc124\uc815\uac12\uc774 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4. \uc544\ub798 URL \uc811\uadfc\uc774 \uc720\ud6a8\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.\n\n" + (d.handlerUrl + "\n\n"), c += b + " \ud30c\uc77c\uc758 uploader_setting \uc139\uc158\uc758 <develop_langage>\uc640 <handler_url> \uc124\uc815\uac12\uc744 \ud655\uc778\ud558\uc138\uc694.") : (c = "Editor's setting is not correct. Access the following URL is not valid.\n\n" +
					(d.handlerUrl + "\n\n"), c += 'Please check the settings, <handler_url> and <develop_langage> in "uploader_setting" section in the "' + b + '."'), alert(c)
		}, parseDataFromServer: function(a) { if (a) { var d = a.toLowerCase().indexOf("<raonk>"); -1 < d && (a = a.substring(d + 7)); d = a.toLowerCase().indexOf("</raonk>"); -1 < d && (a = a.substring(0, d)) } return a }, isEmptyContents: function(a) {
			var d = !1, b = "", c = ""; if (a) {
				if (3 == a.nodeType) b = a.textContent, c = ""; else if (1 == a.nodeType) {
					if (b = a.innerText, c = a.innerHTML, -1 < ",TABLE,IMG,A,IFRAME,HR,VIDEO,OBJECT,EMBED,INPUT,BUTTON,FIGURE,TEXTAREA,BLOCKQUOTE,".indexOf("," +
						a.nodeName + ",")) return d
				} else "string" === typeof a && (c = b = a); b = b.replace(unescape("%u200B"), ""); b = b.replace(unescape("%uFEFF"), ""); c = c.replace(unescape("%u200B"), ""); c = c.replace(unescape("%uFEFF"), ""); "" == b && 0 == /<table|<img|<a|<iframe|<hr|<video|<object|<embed/ig.test(c) && (d = !0)
			} return d
		}, replaceHyFont: function(a, d) {
			var b = a; try {
				if (RegExp("<.*?HY.*?>", "gi").test(b.replace(/[\n\r]/gm, ""))) {
					var c = d._DOC.createElement("div"); c.innerHTML = b; for (var e = c.getElementsByTagName("*"), f = e.length, h = 0; h < f; h++) {
						var k =
							e[h]; if (1 == k.nodeType) {
								var l = k.style.fontFamily; "FONT" == k.tagName && "" == l && (l = k.face); if ("" != l && -1 < l.indexOf("HY")) {
									for (var n = l.split(","), p = n.length - 1; 0 <= p; p--) {
										var m = n[p], m = RAONKEDITOR.util.trim(m), m = m.replace(/['"]/g, ""); if (-1 < m.indexOf("HY")) {
											var q = ""; d._config.ie_BugFixed_Hyfont_Replace_Font.all ? q = d._config.ie_BugFixed_Hyfont_Replace_Font.all : d._config.ie_BugFixed_Hyfont_Replace_Font[m] && (q = d._config.ie_BugFixed_Hyfont_Replace_Font[m]); "" == q ? n.splice(p, 1) : (-1 < q.indexOf(" ") && (q = "'" + q + "'"), n[p] =
												q)
										}
									} k.style.fontFamily = n.join(",")
								}
							}
					} b = c.innerHTML
				}
			} catch (r) { } return b
		}, dataURItoBlob: function(a) { var d = atob(a.split(",")[1]); a = a.split(",")[0].split(":")[1].split(";")[0]; for (var b = new ArrayBuffer(d.length), c = new Uint8Array(b), e = 0; e < d.length; e++)c[e] = d.charCodeAt(e); d = new DataView(b); return new Blob([d.buffer], { type: a }) }, getDomainInUrl: function(a) {
			var d = { fullDomain: "", domain: "", port: "" }; try {
				var b = a.match(/^https?\:\/\/([^\/?#]+)(?:[\/?#]|$)/i); d.fullDomain = b && b[1] ? b && b[1] : ""; if ("" != d.fullDomain) {
					var c =
						d.fullDomain.split(":"); if (c[0] && "" != c[0]) { var e = c[0].split("."), f = e.length; if (1 == f) d.domain = e[0]; else { for (a = 1; a < f; a++)d.domain += e[a] + "."; d.domain = d.domain.substring(0, d.domain.length - 1) } } c[1] && (d.port = c[1])
				}
			} catch (h) { } return d
		}, getDocType: function(a) { var d = ""; if (a && a.doctype) try { var b = a.doctype, d = "<!DOCTYPE " + b.name + (b.publicId ? ' PUBLIC "' + b.publicId + '"' : "") + (!b.publicId && b.systemId ? " SYSTEM" : "") + (b.systemId ? ' "' + b.systemId + '"' : "") + ">" } catch (c) { } return d }, hexToBytes: function(a) {
			for (var d = [], b = a.length /
				2, c = 0, c = 0; c < b; c++)d.push(parseInt(a.substr(2 * c, 2), 16)); return d
		}, bytesToBase64: function(a) { for (var d = "", b = a.length, c = 0; c < b; c += 3) { var e = a.slice(c, c + 3), f = e.length, h = [], k = void 0; if (3 > f) for (k = f; 3 > k; k++)e[k] = 0; h[0] = (252 & e[0]) >> 2; h[1] = (3 & e[0]) << 4 | e[1] >> 4; h[2] = (15 & e[1]) << 2 | (192 & e[2]) >> 6; h[3] = 63 & e[2]; for (k = 0; 4 > k; k++)d += k <= f ? "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(h[k]) : "=" } return d }, escapeHtml: function(a) {
			var d = null; try { d = KEDITORTOP.G_CURRKEDITOR._FRAMEWIN.document.createElement("div") } catch (b) {
				d =
				document.createElement("div")
			} void 0 != d.innerText ? d.innerText = a : d.textContent = a; return d.innerHTML
		}, setProtocolBaseDomainURL: function(a) { var d = "", d = "/" == a.substring(0, 1) ? location.protocol + "//" + location.host : 4 < a.length && "http" == a.substring(0, 4).toLowerCase() ? "" : RAONKEDITOR.rootPath; return d + a }, getWordBreakStyle: function(a) { var d = "normal"; a = a.wordBreakType; "1" == a ? d = "break-all" : "2" == a && (d = "keep-all"); return d }, getWordWrapStyle: function(a) {
			var d = "normal", b = a.wordWrapType; if ("1" == a.autoBodyFit || "1" == b) d =
				"break-word"; return d
		}, adjustBorderStyle: function(a, d) {
			try {
				var b = function(a) {
					for (var b = ["borderTop", "borderRight", "borderBottom", "borderLeft"], c = b.length, e = 0; e < c; e++) {
						var d = !1, f = a.style[b[e]].toLowerCase(); "" != f && (-1 < f.indexOf("currentcolor") ? d = !0 : 2 == f.split(" ").length && "currentcolor" == a.style[b[e] + "Color"].toLowerCase() && (d = !0)); if (d) {
							var h = f = d = "", d = RAONKEDITOR.util.getStyle(a, b[e] + "Color"), f = RAONKEDITOR.util.getStyle(a, b[e] + "Style"), h = RAONKEDITOR.util.getStyle(a, b[e] + "Width"); a.style[b[e]] = f + " " +
								h + " " + d
						}
					}
				}; if (a) b(a); else for (var c = d._DOC.getElementsByTagName("*"), e = c.length, f = 0; f < e; f++)b(c[f])
			} catch (h) { }
		}, getTimeStamp: function() { var a = "", a = this.makeGuid(); return a = a.replace(/-/g, "") }, arrayIndexOf: function(a, d) { if (a.indexOf) return a.indexOf(d); for (var b = -1, c = a.length, e = 0; e < c; e++)if (a[e] == d) { b = e; break } return b }, parseSetApiParam: function(a) { var d = { html: "" }; switch (typeof a) { case "string": d.html = a; break; case "object": d = a }return d }, overrideFn: function(a) {
			try {
				top.document.location.hostname != window.document.location.hostname &&
				(a.alert = function(a) { top.alert(a) }, a.confirm = function(a) { return top.confirm(a) })
			} catch (d) { }
		}
	}); if (!window.RAONKSolution || window.RAONKSolution && !window.RAONKSolution.Agent.connectedPort) window.RAONKSolution = {
		Agent: {
			vertical: "\x0B", formfeed: "\f", backspace: "\b", space: " ", rtnCode1: "2000", parseRtn: function(a, d) {
				var b = { code: 0, valueArr: [], splitCode: "" }; a = a.trim ? a.trim() : a.replace(/^[\s\xa0]+|[\s\xa0]+$/g, ""); 0 == a.indexOf("7777") && 5 == a.toLowerCase().indexOf("<pre") && (a = a.replace(/ <pre/i, RAONKSolution.Agent.vertical +
					"<pre"), a = a.replace(RAONKSolution.Agent.vertical + "<pre>", RAONKSolution.Agent.vertical), a = a.replace(/<\/pre>$/i, "")); var c; -1 < a.indexOf(RAONKSolution.Agent.vertical) ? (c = a.split(RAONKSolution.Agent.vertical), b.splitCode = RAONKSolution.Agent.vertical) : (c = a.split(RAONKSolution.Agent.space), b.splitCode = RAONKSolution.Agent.space); b.code = parseInt(c[0], 10); if (7777 == b.code) for (var e = c.length, f = 1; f < e; f++)b.valueArr[f - 1] = c[f]; else c[1] ? (c = RAONKSolution.Agent.decryptManagerParam(c[1]), b.valueArr = c.split(RAONKSolution.Agent.vertical),
						"" == b.valueArr[b.valueArr.length - 1] && b.valueArr.splice(b.valueArr.length - 1, 1)) : isNaN(b.code) && (b.valueArr[0] = a); return b
			}, parseRtnInWorker: function(a, d) {
				var b = "", b = RAONKUPLOAD.ReleaseVer; "1" == KUPLOADTOP.G_CURRKUPLOADER._config.cacheProtectMode && (b = RAONKUPLOAD.util.getTimeStamp()); var b = RAONKUPLOAD.isRelease ? KUPLOADTOP.G_CURRKUPLOADER._config.webPath.js + "raonkupload.processmanagerparam.min.js?ver=" + b : KUPLOADTOP.G_CURRKUPLOADER._config.webPath.jsDev + "raonkupload.processmanagerparam.js?ver=" + b, c = new Worker(b),
					e = { code: 0, valueArr: [] }; c.onmessage = function(a) { a = a.data; switch (a.type) { case "decrypt": e = a.agentMsg, c.terminate() }"undefined" != typeof d && d(e) }; c.onerror = function(a) { c.terminate(); "undefined" != typeof d && (e.valueArr[0] = "Agent Param Process Worker Error", d(e)) }; c.postMessage({ type: "decrypt", agentMsg: a, agentVar: { vertical: "\x0B", formfeed: "\f", backspace: "\b", space: " " } })
			}, isLoaded: !1, isUpdating: !1, isStartInstall: !1, isOtherUploadWaiting: !1, G_AP: { G_AP29: "w", G_AP22: "a" }, makeEncryptManagerParam: function(a) {
				var d =
					null; try { RAONKEDITOR && RAONKEDITOR.util && (d = RAONKEDITOR.util.base64_encode) } catch (b) { } try { null == d && RAONKUPLOAD && RAONKUPLOAD.util && (d = RAONKUPLOAD.util.base64_encode) } catch (c) { } try { null == d && RAONKPHOTO && RAONKPHOTO.util && (d = RAONKPHOTO.util.base64_encode) } catch (e) { } a = "R" + d(a); a = d(a); a = a.replace(/[+]/g, "%2B"); return a += "\x0B"
			}, decryptManagerParam: function(a) {
				var d = null; try { RAONKEDITOR && RAONKEDITOR.util && (d = RAONKEDITOR.util.base64_decode) } catch (b) { } try { null == d && RAONKUPLOAD && RAONKUPLOAD.util && (d = RAONKUPLOAD.util.base64_decode) } catch (c) { } try {
					null ==
					d && RAONKPHOTO && RAONKPHOTO.util && (d = RAONKPHOTO.util.base64_decode)
				} catch (e) { } a = a.replace(/%2B/g, "+"); a = d(a); a = a.substring(1); return a = d(a)
			}, G_AP10: 8, G_AP23: "n", G_AP20: "z", isCheckingPort: !1, connectedPort: "", sendCount: 0, managerFinalUrl: "", reset: function() { this.isCheckingPort = this.isOtherUploadWaiting = this.isStartInstall = this.isUpdating = this.isLoaded = !1; this.connectedPort = ""; this.sendCount = 0; this.managerFinalUrl = "" }
		}
	}; RAONKEDITOR._manager || (RAONKEDITOR._manager = {
		createManagerRequest: function(a) {
			var d =
				null; return d = "html4" == (a ? a : KEDITORTOP.G_CURRKEDITOR)._config.userRunTimeMode ? new XDomainRequest : new XMLHttpRequest
		}, sendDataWidthAjax: function(a) { var d = a.req; a.errorCallBack && (d.onerror = function() { a.errorCallBack(d) }); a.successCallBack && (d.onload = function() { a.successCallBack(d.responseText); d = null; d = void 0 }); d.raonPort = a.port; d.open("POST", a.url ? a.url : KEDITORTOP.RAONKSolution.managerFinalUrl, a.async); -1 < a.data.indexOf("{") ? d.send("k02=" + a.data) : d.send("k00=" + a.data) }, sendDataWithForm: function(a,
			d, b, c) { void 0 == c && (c = []); var e = a.createElement("form"); e.method = "post"; e.action = d; e.target = b; d = c.length; for (b = 0; b < d; b++) { var f = a.createElement("input"); f.type = "hidden"; f.name = c[b][0]; f.value = c[b][1]; e.appendChild(f) } a.body.appendChild(e); e.submit(); a.body.removeChild(e) }
	}); RAONKEDITOR.getEditorByName = RAONKEDITOR.GetEditorByName = function(a) { var d = null; try { void 0 == a || "" == a ? d = G_CURRKEDITOR : RAONKEDITOR.isLoadedEditorEx(a) && (d = RAONKEDITOR.RAONKMULTIPLE["raonk_frame_" + a]), void 0 == d && (d = null) } catch (b) { } return d };
	RAONKEDITOR.addFormData = RAONKEDITOR.AddFormData = function(a, d, b) { try { var c = RAONKEDITOR.util._setEditor(b); if (c) { KEDITORTOP.G_CURRKEDITOR = c; var e = KEDITORTOP.G_CURRKEDITOR._FRAMEWIN; if (e && a && "" != a && d && "" != d) { var f = e.G_FormData, h = f.length; b = !0; for (c = 0; c < h; c++)if (f[c].form_name.toLowerCase() == a.toLowerCase()) { f[c].form_value = d; b = !1; break } b && e.G_FormData.push({ form_name: a, form_value: d }) } } } catch (k) { } }; RAONKEDITOR.getEditor = RAONKEDITOR.GetEditor = function(a) { return RAONKEDITOR.util._getEditor(a) }; RAONKEDITOR.setEditor =
		RAONKEDITOR.SetEditor = function(a) { return RAONKEDITOR.util._setEditor(a) }; RAONKEDITOR.setAccessibility = RAONKEDITOR.SetAccessibility = function(a, d) { try { var b = RAONKEDITOR.util.getEditorByName(d); !b || "0" != a && "1" != a && "2" != a || (b._config.accessibility = a + "") } catch (c) { } }; RAONKEDITOR.getAccessibility = RAONKEDITOR.GetAccessibility = function(a) { var d = ""; try { var b = RAONKEDITOR.util.getEditorByName(a); b && (d = b._config.accessibility) } catch (c) { } return d }; RAONKEDITOR.setVisibility = RAONKEDITOR.SetVisibility = function(a,
			d) { try { var b = RAONKEDITOR.util.getEditorByName(d); b && (b._config.visibility = 1 == a ? a : !1) } catch (c) { } }; RAONKEDITOR.getVisibility = RAONKEDITOR.GetVisibility = function(a) { var d = ""; try { var b = RAONKEDITOR.util.getEditorByName(a); b && (d = b._config.visibility) } catch (c) { } return d }; RAONKEDITOR.show = RAONKEDITOR.Show = function(a) {
				try {
					var d = RAONKEDITOR.util._setEditor(a); if (d) {
						if (void 0 == a || "" == a) a = d.ID; var b = d._FRAMEWIN, c = KEDITORDOC.getElementById("raonk_frame_holder" + a); c && (RAONKEDITOR.setVisibility(!0, a), c.style.width =
							d._config.style.width, c.style.height = d._config.style.height, c.style.display = "", b.resizeEditor(null, !0), "1" == d._config.ruler.view && b.showRuler(d))
					}
				} catch (e) { }
			}; RAONKEDITOR.hidden = RAONKEDITOR.Hidden = function(a) {
				try {
					var d = RAONKEDITOR.util._setEditor(a); if (d) {
						if (void 0 == a || "" == a) a = d.ID; var b = d._FRAMEWIN; KEDITORDOC.getElementById("keditor_context_iframe" + a) && (KEDITORDOC.getElementById("keditor_context_iframe" + a).style.display = "none", KEDITORDOC.getElementById("keditor_context_background" + a) && (KEDITORDOC.getElementById("keditor_context_background" +
							a).style.display = "none", b.dialogCancel())); b.event_dialog_cancel(KEDITORDOC.getElementById("keditor_dialog")); b.hideTopMenuAndFocus(); var c = KEDITORDOC.getElementById("keditor_toolmenu_background" + a); c && (b.dialogCancel(), b.G_SUB_DIALOG && (b.isGroupingIcon(b.G_USE_EDITOR_ID, "", b.G_SUB_DIALOG) && b.hideGroupingBox(), b.G_SUB_DIALOG.style.display = "none"), b.G_CURRENT_IFRAME && (b.G_CURRENT_IFRAME.style.display = "none"), b.G_CURRENT_IFRAME_HOLDER && (b.G_CURRENT_IFRAME_HOLDER.style.display = "none"), c.style.display =
								"none"); var e = KEDITORDOC.getElementById("raonk_frame_holder" + a); e && (RAONKEDITOR.setVisibility(!1, a), e.style.height = "0px", e.style.display = "none", window.focus())
					}
				} catch (f) { }
			}; RAONKEDITOR.setSize = RAONKEDITOR.SetSize = function(a, d, b) {
				try {
					if (1 == RAONKEDITOR.getVisibility(b)) {
						var c = RAONKEDITOR.util._setEditor(b); if (c) {
							var e = c._FRAMEWIN; if (void 0 == b || "" == b) b = c.ID; var f = KEDITORDOC.getElementById("raonk_frame_holder" + b), h = KEDITORDOC.getElementById("raonk_frame_" + b).contentWindow.document.getElementById("ue_editor_holder_" +
								b); b = 0; b = c.baseMenuToolbarHeight; e.isViewMode(c) ? c._defaultHeight = parseInt(d, 10) - 4 : c._defaultHeight = parseInt(d, 10) - b; -1 < a.toString().indexOf("%") || -1 < a.toString().indexOf("px") ? f.style.width = a : f.style.width = a + "px"; c._config.style.width = f.style.width; -1 < d.toString().indexOf("%") || -1 < d.toString().indexOf("px") ? f.style.height = d : f.style.height = d + "px"; c._config.style.height = f.style.height; h.style.height = c._defaultHeight + "px"; "1" == G_CURRKEDITOR._config.ruler.view && "design" == G_CURRKEDITOR._currentMode &&
									e.showRuler(G_CURRKEDITOR); e.groupingIcon(); try { if (c.initSetSize) c.initSetSize = !1; else if (!c.isOccurredResizeEvent) { c.isOccurredResizeEvent = !0; var k = { objResizedWindow: KEDITORTOP.G_CURRKEDITOR.Frame }; "function" == typeof c._config.event.resized ? c._config.event.resized(c.ID, k) : KEDITORWIN.RAONKEDITOR_Resized(c.ID, k) } } catch (l) { }
						}
					}
				} catch (n) { }
			}; RAONKEDITOR.getImages = RAONKEDITOR.GetImages = function(a, d) {
				var b = ""; try {
					var c = RAONKEDITOR.util._setEditor(a); if (c) {
						var e = c._FRAMEWIN, f = c.getEditorMode(); "source" !=
							f && "text" != f || c.setChangeMode("design"); e.ReplaceImageToRealObject(); 1 != d && e.changeBodyContenteditable(!1); e.changeBodyImageProperty(!0); for (var h = c._DOC.getElementsByTagName("img"), k = f = "", l = -1, n = h.length, p = 0; p < n; p++) {
								f = h[p].getAttribute("src"); if ("1" == c._config.useKManager) { var m = f, m = decodeURIComponent(m), m = e.convertImageAgentSrc(m, !1), m = decodeURIComponent(m), m = m.replace(/\\/g, "/"); m != f && (f = m) } -1 < f.indexOf("data:image") ? k = "" : (l = f.lastIndexOf("/"), k = f.substring(l + 1)); b = "" == b ? f + c._config.unitAttributeDelimiter +
									k : b + c._config.unitDelimiter + f + c._config.unitAttributeDelimiter + k
							} e.changeBodyImageProperty(!1); e.ReplaceRealObjectToImage(); e.changeBodyContenteditable(!0)
					}
				} catch (q) { } return b
			}; RAONKEDITOR.getImagesEx = RAONKEDITOR.GetImagesEx = function(a, d) {
				var b = ""; try {
					var c = RAONKEDITOR.util._setEditor(a); if (c) {
						var e = c._FRAMEWIN, f = c.getEditorMode(); "source" != f && "text" != f || c.setChangeMode("design"); e.ReplaceImageToRealObject(); 1 != d && e.changeBodyContenteditable(!1); e.changeBodyImageProperty(!0); var h = c._DOC.body.outerHTML,
							k = f = "", l = -1, n = RegExp("<img[^>]*src=(.*?)>", "gi"), p = h.match(RegExp("<[^>]*url\\((.*?)\\)", "gi")), m = h.match(n); if (p) for (var q = p.length, r = 0; r < q; r++)if ((f = p[r].match("url\\((.*?)\\)")[1].replace(/"/gi, "").replace(/'/gi, "").replace(/&quot;/gi, "")) && "" != f) {
								if ("1" == c._config.useKManager) { var u = f, u = decodeURIComponent(u), u = e.convertImageAgentSrc(u, !1), u = decodeURIComponent(u), u = u.replace(/\\/g, "/"); u != f && (f = u) } -1 < f.indexOf("data:image") ? k = "" : (l = f.lastIndexOf("/"), k = f.substring(l + 1)); b = "" == b ? f + c._config.unitAttributeDelimiter +
									k : b + c._config.unitDelimiter + f + c._config.unitAttributeDelimiter + k
							} if (m) for (var t = m.length, r = 0; r < t; r++)(f = m[r].match("src=\"(.*?)\"|src='(.*?)'")[1].replace(/"/gi, "").replace(/'/gi, "")) && "" != f && ("1" == c._config.useKManager && (u = f, u = decodeURIComponent(u), u = e.convertImageAgentSrc(u, !1), u = decodeURIComponent(u), u = u.replace(/\\/g, "/"), u != f && (f = u)), -1 < f.indexOf("data:image") ? k = "" : (l = f.lastIndexOf("/"), k = f.substring(l + 1)), b = "" == b ? f + c._config.unitAttributeDelimiter + k : b + c._config.unitDelimiter + f + c._config.unitAttributeDelimiter +
								k); e.changeBodyImageProperty(!1); e.ReplaceRealObjectToImage(); e.changeBodyContenteditable(!0)
					}
				} catch (g) { } return b
			}; RAONKEDITOR.getContentsUrl = RAONKEDITOR.GetContentsUrl = function(a, d) {
				var b = ""; try {
					var c = RAONKEDITOR.util._setEditor(a); if (c) {
						var e = c._FRAMEWIN, f = c.getEditorMode(); "source" != f && "text" != f || c.setChangeMode("design"); e.ReplaceImageToRealObject(); 1 != d && e.changeBodyContenteditable(!1); e.changeBodyImageProperty(!0); var h = c._DOC.body.outerHTML, k = f = "", l = -1, n = RegExp("<img[^>]*src=(.*?)>", "gi"),
							p = RegExp("<embed[^>]*src=(.*?)>", "gi"), m = RegExp("<a[^>]*KEditorInsertFile(.*?)>", "gi"), q = h.match(RegExp("<[^>]*url\\((.*?)\\)", "gi")), r = h.match(n), u = h.match(p), t = h.match(m); if (q) for (var g = q.length, y = 0; y < g; y++)if ((f = q[y].match("url\\((.*?)\\)")[1].replace(/"/gi, "").replace(/'/gi, "").replace(/&quot;/gi, "")) && "" != f) {
								if ("1" == c._config.useKManager) { var v = f, v = decodeURIComponent(v), v = e.convertImageAgentSrc(v, !1), v = decodeURIComponent(v), v = v.replace(/\\/g, "/"); v != f && (f = v) } l = f.lastIndexOf("/"); k = f.substring(l +
									1); b = "" == b ? f + c._config.unitAttributeDelimiter + k : b + c._config.unitDelimiter + f + c._config.unitAttributeDelimiter + k
							} if (r) for (var A = r.length, y = 0; y < A; y++)(f = r[y].match("src=\"(.*?)\"|src='(.*?)'")[1].replace(/"/gi, "").replace(/'/gi, "")) && "" != f && ("1" == c._config.useKManager && (v = f, v = decodeURIComponent(v), v = e.convertImageAgentSrc(v, !1), v = decodeURIComponent(v), v = v.replace(/\\/g, "/"), v != f && (f = v)), l = f.lastIndexOf("/"), k = f.substring(l + 1), b = "" == b ? f + c._config.unitAttributeDelimiter + k : b + c._config.unitDelimiter + f + c._config.unitAttributeDelimiter +
								k); if (u) for (A = u.length, y = 0; y < A; y++)(f = u[y].match("src=\"(.*?)\"|src='(.*?)'")[1].replace(/"/gi, "").replace(/'/gi, "")) && "" != f && ("1" == c._config.useKManager && (v = f, v = decodeURIComponent(v), v = e.convertImageAgentSrc(v, !1), v = decodeURIComponent(v), v = v.replace(/\\/g, "/"), v != f && (f = v)), l = f.lastIndexOf("/"), k = f.substring(l + 1), b = "" == b ? f + c._config.unitAttributeDelimiter + k : b + c._config.unitDelimiter + f + c._config.unitAttributeDelimiter + k); if (t) for (A = t.length, y = 0; y < A; y++)(f = t[y].match("href=\"(.*?)\"|href='(.*?)'")[1].replace(/"/gi,
									"").replace(/'/gi, "")) && "" != f && ("1" == c._config.useKManager && (v = f, v = decodeURIComponent(v), v = e.convertImageAgentSrc(v, !1), v = decodeURIComponent(v), v = v.replace(/\\/g, "/"), v != f && (f = v)), l = f.lastIndexOf("/"), k = f.substring(l + 1), b = "" == b ? f + c._config.unitAttributeDelimiter + k : b + c._config.unitDelimiter + f + c._config.unitAttributeDelimiter + k); e.changeBodyImageProperty(!1); e.ReplaceRealObjectToImage(); e.changeBodyContenteditable(!0)
					}
				} catch (F) { } return b
			}; RAONKEDITOR.getServerImages = function(a, d, b) {
				var c = ""; try {
					a = a.toLowerCase();
					var e = RAONKEDITOR.util._setEditor(d); if (e) {
						var f = e._FRAMEWIN, h = e.getEditorMode(); "source" != h && "text" != h || e.setChangeMode("design"); f.ReplaceImageToRealObject(); 1 != b && f.changeBodyContenteditable(!1); f.changeBodyImageProperty(!0); var k = e._DOC.getElementsByTagName("img"); b = d = ""; for (var h = -1, l = k.length, n = 0; n < l; n++) {
							d = k[n].src; if ("1" == e._config.useKManager) { var p = d, p = decodeURIComponent(p), p = f.convertImageAgentSrc(p, !1), p = decodeURIComponent(p); p != d && (d = p) } -1 < d.toLowerCase().indexOf(a) && (h = d.lastIndexOf("/"),
								b = d.substring(h + 1), c = "" == c ? d + e._config.unitAttributeDelimiter + b : c + e._config.unitDelimiter + d + e._config.unitAttributeDelimiter + b)
						} e._PageProp.bshowgrid && 1 == e._PageProp.bshowgrid && f.changeBodyImageProperty(!1); f.ReplaceRealObjectToImage(); f.changeBodyContenteditable(!0)
					}
				} catch (m) { } return c
			}; RAONKEDITOR.setHtmlValueExWithDocType = RAONKEDITOR.SetHtmlValueExWithDocType = function(a, d) {
				var b = RAONKEDITOR.util.parseSetApiParam(a); a = b.html; if ("" == a || "" == RAONKEDITOR.util.trim(a)) RAONKEDITOR.setBodyValue("", d);
				else try {
					if (d = RAONKEDITOR.util._getEditorName(d), null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) try {
						var c = RAONKEDITOR.util._setEditor(d); if (c) {
							c.setValueIsBusy = !0; !0 === b.notFocusToEditor && (c.notFocusToEditor = !0); var e = c._FRAMEWIN; e.getApplyDragResize(c) && c.keditor_dragresize.resizeHandleClear(); var f = c.isInitFocusHandler = !1, h = !1; "" == c._config.focusInitObjId && "" == c._config.focusInitObjIdEx && !c.autoMoveInitFocusData.targetNode && !c.autoMoveInitFocusData.targetNodeEx || e.isViewMode(c) || (f = !0); 0 != c._config.initFocusForSetAPI ||
								e.isViewMode(c) || (h = c._config.initFocusForSetAPI = !0); if (f || h) c._BODY.contentEditable = !1, c._config.editorBodyEditable = !1; a = e.addHtmlToSetValue(c, a); a = e.removeCarriageReturn(c, a); e.setChangeModeForSetApi(c); e.setGlobalTableDefaultValue(); c.UndoManager.reset(); "1" == c._config.emptyTagRemoveInSetapi && (a = e.CleanZeroChar(a)); a = e.removeDummyTag(a); a = e.removeIncorrectSpaceInTag(a); a = e.RAONK_EDITOR.HTMLParser.RemoveOfficeTag2(a); a = e.externalPageBreakDataRaplaceInEditor(a); b = function(a) {
									"1" == c._config.useHtmlProcessByWorkerSetApi &&
									(e.destoryWebWorkerVar(), e.hideProcessingBackground()); a = e.removeTagStyle(a); a = e.htmlAsciiToChar(a); a = RAONKEDITOR.util.htmlRevision(a); a = e.xssReplaceScript(null, a); c._config.userCssUrl && "" != c._config.userCssUrl && c._config.userCssAlwaysSet && "1" == c._config.userCssAlwaysSet && (a = e.userCssSet(a, c._config.userCssUrl)); c._config.webFontCssUrl && "" != c._config.webFontCssUrl && c._config.webFontCssAlwaysSet && "1" == c._config.webFontCssAlwaysSet && (a = e.userCssSet(a, c._config.webFontCssUrl)); a = e.adjustInputChecked(a);
									"1" == c._config.ie_BugFixed_Hyfont && (a = RAONKEDITOR.util.replaceHyFont(a, c)); "1" == c._config.replaceEmptySpanTagInSetapi && (a = e.replaceEmptySpanTag(a)); e.command_InsertDogBGImg(c.ID, c._EDITOR.design, "Y", "", "", "", "", []); try { for (var b in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[b].onBeforeDocumentWrite) { var d = e.G_KPlugin[b].onBeforeDocumentWrite({ html: a }); d && "string" == typeof d.html && (a = d.html) } } catch (k) { } a = e.insertCarriageReturnBeforeCloseCell(a); a = e.removeEditorAttribute(a); e.setHtmlValueWithDocTypeToEditor(a,
										!0, c); try { for (b in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[b].onAfterDocumentWrite) e.G_KPlugin[b].onAfterDocumentWrite() } catch (l) { } "" != c._config.placeholder.content && e.placeholderControl(c, "set"); b = function(a, b) {
											"1" == b._config.removeEmptyTagSetValue && e.setEmptyTagWhiteSpace(b); "0" != b._config.setDefaultStyle.value && "0" != b._config.setDefaultStyle.set_style && b._BODY.id != b._config.setDefaultStyle.body_id && (b._BODY.id = b._config.setDefaultStyle.body_id); if ("1" == b._config.ruler.use && "" != b._config.ruler.rulerInitPosition &&
												"0" != b._config.ruler.rulerInitPosition) if ("2" == b._config.ruler.mode && "1" == b._config.ruler.autoFitMode) if ("0" == b._config.ruler.fixEditorWidth) b._BODY.style.removeProperty ? b._BODY.style.removeProperty("width") : b._BODY.style.removeAttribute("width"); else { var c = RAONKEDITOR.util.parseIntOr0(b._config.ruler.rulerInitPosition.split(",")[2]), c = c - (2 * RAONKEDITOR.util.parseIntOr0(b._config.defaultBodySpace.value) + 15); b._BODY.style.width = c + "px" } else c = KEDITORTOP.RAONKEDITOR.util.parseIntOr0(b._config.ruler.rulerInitPosition.split(",")[2]) -
													KEDITORTOP.RAONKEDITOR.util.parseIntOr0(b._config.defaultBodySpace.value), 0 < c && (b._BODY.style.width = c + "px"); b._config.zoomList && 0 < b._config.zoomList.length && e.command_zoom(b.ID, e.document.getElementById("keditor_design_" + b.ID)); "1" == b._config.useKManager && e.convertAllImageAgentSrc(e._iframeDoc, !0, !0); setTimeout(function() {
														for (var a = e._iframeDoc.getElementsByTagName("input"), c = a.length, d = 0; d < c; d++)"radio" == a[d].type && null != a[d].getAttribute("keditorchecked") && (a[d].checked = !0, a[d].setAttribute("checked",
															"checked"), a[d].removeAttribute("keditorchecked")); e.adjustInputNodeForFF(b._DOC, !0)
													}, 10); 0 == e.isViewMode(b) && (b._editorCustomDataMode = !0, "1" == b._config.formMode ? (e.ReplaceRealEventToCustomData(), e.ReplaceRealObjectToImage(!1)) : e.ReplaceRealObjectToImage()); e.xssReplaceScript(e._iframeDoc); e.setScrollOverflow(b); e.setStyleForTableBorderNodeClass(b); e.setCssForFormMode(b); "1" == b._config.adjustCurrentColorInSetApi && RAONKEDITOR.util.adjustBorderStyle(null, b); b.ShowTableBorder && (b.ShowTableBorder = !1);
											b._iconEnable(""); 0 == e.isViewMode(b) && e.setBodyDefaultValue(); e.wrapPtagForNotBlockTag(b); e.removeEmptySpanBRTag(b._BODY); e.replaceBrTag(b); e.fn_IEJASOBug(b); e.removeLastBrTag(b); 0 == e.isViewMode(b) && (e.removeNbspInPTag(b), e.replaceClassForBorder(b, b._BODY, "show"), e.replaceClassForBookmark(b, b._BODY, "show"), !0 !== b.notFocusToEditor && (e.setFocusToBody(), e.setFocusFirstChildForStyle(b._BODY))); RAONKEDITOR.util.setBodyBackground(b); 0 == e.isViewMode(b) && (b.UndoManager.putUndo(), b.UndoManager.charCount = 0,
												b.UndoManager.canUndo = !1, b.UndoManager.canRedo = !1); b._iconEnable(""); e.insertImageSrc(b); e.showRuler(b); e.setAutoBodyFit(b); e.setClassTableAndCellLock(b); e.set_view_mode_auto_height(b); "1" == b._config.tableAutoAdjustInSetHtml && e.command_AdjustTableAndCellWidth(b.ID, b, { type: "setHtml" }); e.setAdjustTableBorder(b._DOC); "show_blocks" == G_CURRKEDITOR.ShowBlocks && (G_CURRKEDITOR.ShowBlocks = "", e.command_showBlocks(b.ID, b)); setTimeout(function() {
													try {
														e.adjustScroll(b); if ("" != b._config.focusInitObjIdEx || b.autoMoveInitFocusData.targetNodeEx) {
															var a;
															"" != b._config.focusInitObjIdEx ? a = KEDITORTOP.KEDITORDOC.getElementById(b._config.focusInitObjIdEx) : b.autoMoveInitFocusData.targetNodeEx && (a = b.autoMoveInitFocusData.targetNodeEx); a ? (a.focus(), b._config.focusInitObjIdEx = "", b.autoMoveInitFocusData.targetNodeEx = null) : (f = !1, h = !0)
														} if (f || h) !e.isViewMode(b) && b._config.editorBodyEditableEx && (b._BODY.contentEditable = !0, b._config.editorBodyEditable = !0), h && 0 == f && !0 !== b.notFocusToEditor && (KEDITORTOP.focus(), KEDITORTOP.document.body.focus()); e.command_BeforeSetCompleteSpellCheck(b);
														"2" == b._config.ruler.mode && e.setRulerPositionForNode(b); e.g_findRepalceRange = null; try { "function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.setComplete ? KEDITORTOP.G_CURRKEDITOR._config.event.setComplete(b.ID) : KEDITORTOP.RAONKEDITOR_SetComplete(b.ID) } catch (c) { } try { e.onChange({ editor: b }) } catch (d) { } b.UndoManager.reset(); "2" == b._config.undoMode && b.UndoManager.putUndo(!0); b.setValueIsBusy = !1; !0 === b.notFocusToEditor && (b.notFocusToEditor = void 0)
													} catch (g) { e.restoreValueInSetError(b) }
												}, 300)
										}; "base64" ==
											c._config.uploadMethod ? /<img[^>]+file:\/\/\/[^>]+>/i.test(a) ? e.localImageToBase64InHtml({ isAllLocalFile: !0, targetNode: c._BODY, callbackFn: b, callbackArguments: [c] }) : b(null, c) : b(null, c)
								}; "1" == c._config.useHtmlCorrection ? "1" == c._config.useHtmlProcessByWorkerSetApi ? (e.showProcessingBackground(), e.releaseProcessHtmlWorker(), e.fn_processHtmlWorker({
									editorBrowser: { ie: RAONKEDITOR.browser.ie, ieVersion: RAONKEDITOR.browser.ieVersion, gecko: RAONKEDITOR.browser.gecko }, editorConfig: c._config, callFn: "htmlTagRevision",
									callFnParam: [a, !1], callBackFn: b
								})) : (a = e.htmlTagRevision(a, !1), b(a)) : b(a)
						}
					} catch (k) { e.restoreValueInSetError(c) } else null == RAONKEDITOR.InitEditorDataHashTable && (RAONKEDITOR.InitEditorDataHashTable = new RAONKEDITOR.util.hashTable), RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "setHtmlValueExWithDocType", value: b })
				} catch (l) { }
			}; RAONKEDITOR.getHtmlContents = RAONKEDITOR.GetHtmlContents = function(a, d) {
				try {
					var b = function(a, b) {
						var c = a.type; if (void 0 == c || "" == c) c = "body"; var e = ""; switch (c.toLowerCase()) {
							case "text": e =
								"GetBodyTextValue"; break; case "htmlexwithdoctype": e = "GetHtmlValueExWithDocType"; break; case "htmlex": e = "GetHtmlValueEx"; break; case "html": e = "GetHtmlValue"; break; case "bodyex": e = "GetBodyValueEx"; break; default: e = "GetBodyValue"
						}RAONKEDITOR[e](a, b)
					}, c = RAONKEDITOR.util._setEditor(d); if (c) if (0 == c.isLoadingFile) b(a, d); else var e = setInterval(function() { 0 == c.isLoadingFile && (clearInterval(e), b(a, d)) }, 1E3)
				} catch (f) { }
			}; RAONKEDITOR.getHtmlValueExWithDocType = RAONKEDITOR.GetHtmlValueExWithDocType = function(a, d) {
				var b =
					""; try {
						var c = RAONKEDITOR.util._setEditor(d); if (c) {
							0 != !!a.isAuto || "undefined" != typeof a.undoReset && 1 != a.undoReset || c.UndoManager.reset(); var e = c._FRAMEWIN; if (1 == a.isAuto) { "" != c._config.placeholder.content && e.placeholderControl(c, "remove"); e.getHTMLForAutoSave(c, a); return } try { for (var f in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[f].onBeforeGetApi) e.G_KPlugin[f].onBeforeGetApi({ targetDoc: c._DOC }) } catch (h) { } var k = c.getEditorMode(); "source" != k && "text" != k || c.setChangeMode("design"); e.clearAllFormControlSelected();
							e.ReplaceBase64ImageToArray(c._config, c._FRAMEWIN._iframeDoc.body); e.setRemoveClass(["td", "th"], ["keditor_dot"]); e.replaceClassForBorder(c, c._BODY, "hidden"); e.replaceClassForBookmark(c, c._BODY, "hidden"); e.ReplaceImageToRealObject(); e.xssReplaceScript(e._iframeDoc); e.ClearDraggingTableAllTable(); "1" == c._config.formMode && e.ReplaceCustomDataToRealEvent(); var l, n; if (1 == a.isAuto) try {
								RAONKEDITOR.browser.ie ? (l = Math.max(e._iframeDoc.documentElement.scrollLeft, e._iframeDoc.body.scrollLeft), n = Math.max(e._iframeDoc.documentElement.scrollTop,
									e._iframeDoc.body.scrollTop)) : (l = e._iframeWin.scrollX, n = e._iframeWin.scrollY)
							} catch (p) { } 0 == !!a.isAuto && e.changeBodyContenteditable(!1); e.changeBodyImageProperty(!0); try {
								a.async = !1 === a.async ? !1 : !0, RAONKEDITOR.util.postimageToServer(c, function() {
									1 != a.isAuto && (e.removeEmptySpanBRTag(c._BODY), "1" == c._config.wrapPtagToGetApi && e.wrapPtagForNotBlockTag(c), e.removeFakeLineHeight(c._BODY), RAONKEDITOR.util.setInLineDefaultStyle(c)); e.setMatchSelectedValue(c._BODY); e.setMatchInputValue(c._BODY, a.isAuto); e.adjustInputNodeForFF(c._DOC,
										!1); e.removeScrollStyleForIOS(c); e.setEmptyTagWhiteSpace(c); var d = "", f = "", h = ""; c._BODY.style.transformOrigin && "" != c._BODY.style.transformOrigin && (d = c._BODY.style.transformOrigin, c._BODY.style.transformOrigin = ""); c._BODY.style.transform && "" != c._BODY.style.transform && (f = c._BODY.style.transform, c._BODY.style.transform = ""); c._BODY.style.zoom && "" != c._BODY.style.zoom && (h = c._BODY.style.zoom, c._BODY.style.zoom = ""); "1" == c._config.autoBodyFit && e.setBodyFitStyle(c, !1); "" != c._config.placeholder.content && e.placeholderControl(c,
											"remove"); b = c._DOC.documentElement.outerHTML; "1" == c._config.autoBodyFit && e.setBodyFitStyle(c, !0); "" != c._config.placeholder.content && e.placeholderControl(c, "set"); "" != d && (c._BODY.style.transformOrigin = d); "" != f && (c._BODY.style.transform = f); "" != h && (c._BODY.style.zoom = h); b = RAONKEDITOR.util.removeHtmlLangAttrDuplication(b); b = e.dummyTagClassClear(b); c._PageProp.bshowgrid && 1 == c._PageProp.bshowgrid && e.changeBodyImageProperty(!1); "1" == c._config.formMode ? (e.ReplaceRealEventToCustomData(), e.ReplaceRealObjectToImage(!1)) :
												e.ReplaceRealObjectToImage(); e.changeBodyContenteditable(!0); if (1 == a.isAuto) try { e._iframeWin.scroll(l, n) } catch (g) { } void 0 != c._PageProp.doctype && 0 < c._docType[c._PageProp.doctype].length && (b = c._docType[c._PageProp.doctype] + b); 1 == RAONKEDITOR.browser.ie && (b = RAONKEDITOR.util.htmlToLowerCase(b)); b = e.RemoveUnnecessaryChar(b); b = e.CleanZeroChar(b); d = !1; "1" == c._config.replaceEmptyTagWithSpace && (d = !0); b = RAONKEDITOR.util.htmlRevision(b, d); "1" == c._config.xhtml_value && (b = RAONKEDITOR.util.html2xhtml(b)); b = e.removeEditorCss(b,
													c); e.setAddClass(["td", "th"], ["keditor_dot"]); 0 == e.isViewMode(c) && (e.replaceClassForBorder(c, c._BODY, "show"), e.replaceClassForBookmark(c, c._BODY, "show")); b = e.replaceLineBreak(c, b); b = e.insertCarriageReturn(c, b); b = e.ReplaceArrayToBase64Image(c._config, c._FRAMEWIN._iframeDoc.body, b); e.setScrollStyleForIOS(c); e.showRuler(c); "" != c._config.placeholder.content && e.placeholderControl(c, "class"); a.callback({ strEditorId: c.ID, strData: b })
								}, a)
							} catch (m) { }
						}
					} catch (q) { } return b
			}; RAONKEDITOR.setHtmlValueEx = RAONKEDITOR.SetHtmlValueEx =
				function(a, d) {
					var b = RAONKEDITOR.util.parseSetApiParam(a); a = b.html; if ("" == a || "" == RAONKEDITOR.util.trim(a)) RAONKEDITOR.setBodyValue("", d); else try {
						if (d = RAONKEDITOR.util._getEditorName(d), null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) try {
							var c = RAONKEDITOR.util._setEditor(d); if (c) {
								c.setValueIsBusy = !0; !0 === b.notFocusToEditor && (c.notFocusToEditor = !0); var e = c._FRAMEWIN; e.getApplyDragResize(c) && c.keditor_dragresize.resizeHandleClear(); var f = c.isInitFocusHandler = !1, h = !1; "" == c._config.focusInitObjId && "" == c._config.focusInitObjIdEx &&
									!c.autoMoveInitFocusData.targetNode && !c.autoMoveInitFocusData.targetNodeEx || e.isViewMode(c) || (f = !0); 0 != c._config.initFocusForSetAPI || e.isViewMode(c) || (h = c._config.initFocusForSetAPI = !0); if (f || h) c._BODY.contentEditable = !1, c._config.editorBodyEditable = !1; a = e.addHtmlToSetValue(c, a); a = e.removeCarriageReturn(c, a); e.setChangeModeForSetApi(c); e.setGlobalTableDefaultValue(); c.UndoManager.reset(); "1" == c._config.emptyTagRemoveInSetapi && (a = e.CleanZeroChar(a)); a = e.removeDummyTag(a); a = e.removeIncorrectSpaceInTag(a);
								a = e.RAONK_EDITOR.HTMLParser.RemoveOfficeTag2(a); a = e.externalPageBreakDataRaplaceInEditor(a); b = function(a) {
									"1" == c._config.useHtmlProcessByWorkerSetApi && (e.destoryWebWorkerVar(), e.hideProcessingBackground()); a = e.removeTagStyle(a); a = e.htmlAsciiToChar(a); a = RAONKEDITOR.util.htmlRevision(a); a = e.xssReplaceScript(null, a); c._config.userCssUrl && "" != c._config.userCssUrl && c._config.userCssAlwaysSet && "1" == c._config.userCssAlwaysSet && (a = e.userCssSet(a, c._config.userCssUrl)); c._config.webFontCssUrl && "" != c._config.webFontCssUrl &&
										c._config.webFontCssAlwaysSet && "1" == c._config.webFontCssAlwaysSet && (a = e.userCssSet(a, c._config.webFontCssUrl)); a = e.adjustInputChecked(a); "1" == c._config.ie_BugFixed_Hyfont && (a = RAONKEDITOR.util.replaceHyFont(a, c)); "1" == c._config.replaceEmptySpanTagInSetapi && (a = e.replaceEmptySpanTag(a)); e.command_InsertDogBGImg(c.ID, c._EDITOR.design, "Y", "", "", "", "", []); try {
											for (var b in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[b].onBeforeDocumentWrite) {
												var d = e.G_KPlugin[b].onBeforeDocumentWrite({ html: a }); d && "string" ==
													typeof d.html && (a = d.html)
											}
										} catch (k) { } a = e.insertCarriageReturnBeforeCloseCell(a); a = e.removeEditorAttribute(a); e.setHtmlValueToEditor(a, !0, c); try { for (b in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[b].onAfterDocumentWrite) e.G_KPlugin[b].onAfterDocumentWrite() } catch (l) { } "" != c._config.placeholder.content && e.placeholderControl(c, "set"); b = function(a, b) {
											"1" == b._config.removeEmptyTagSetValue && e.setEmptyTagWhiteSpace(b); "0" != b._config.setDefaultStyle.value && "0" != b._config.setDefaultStyle.set_style && b._BODY.id !=
												b._config.setDefaultStyle.body_id && (b._BODY.id = b._config.setDefaultStyle.body_id); if ("1" == b._config.ruler.use && "" != b._config.ruler.rulerInitPosition && "0" != b._config.ruler.rulerInitPosition) if ("2" == b._config.ruler.mode && "1" == b._config.ruler.autoFitMode) if ("0" == b._config.ruler.fixEditorWidth) b._BODY.style.removeProperty ? b._BODY.style.removeProperty("width") : b._BODY.style.removeAttribute("width"); else {
													var c = RAONKEDITOR.util.parseIntOr0(b._config.ruler.rulerInitPosition.split(",")[2]), c = c - (2 * RAONKEDITOR.util.parseIntOr0(b._config.defaultBodySpace.value) +
														15); b._BODY.style.width = c + "px"
												} else c = KEDITORTOP.RAONKEDITOR.util.parseIntOr0(b._config.ruler.rulerInitPosition.split(",")[2]) - KEDITORTOP.RAONKEDITOR.util.parseIntOr0(b._config.defaultBodySpace.value), 0 < c && (b._BODY.style.width = c + "px"); b._config.zoomList && 0 < b._config.zoomList.length && e.command_zoom(b.ID, e.document.getElementById("keditor_design_" + b.ID)); "1" == b._config.useKManager && e.convertAllImageAgentSrc(e._iframeDoc, !0, !0); setTimeout(function() {
													for (var a = e._iframeDoc.getElementsByTagName("input"),
														c = a.length, d = 0; d < c; d++)"radio" == a[d].type && null != a[d].getAttribute("keditorchecked") && (a[d].checked = !0, a[d].setAttribute("checked", "checked"), a[d].removeAttribute("keditorchecked")); e.adjustInputNodeForFF(b._DOC, !0)
												}, 10); 0 == e.isViewMode(b) && (b._editorCustomDataMode = !0, "1" == b._config.formMode ? (e.ReplaceRealEventToCustomData(), e.ReplaceRealObjectToImage(!1)) : e.ReplaceRealObjectToImage()); e.xssReplaceScript(e._iframeDoc); e.setScrollOverflow(b); e.setStyleForTableBorderNodeClass(b); e.setCssForFormMode(b);
											"1" == b._config.adjustCurrentColorInSetApi && RAONKEDITOR.util.adjustBorderStyle(null, b); b.ShowTableBorder && (b.ShowTableBorder = !1); b._iconEnable(""); 0 == e.isViewMode(b) && e.setBodyDefaultValue(); e.wrapPtagForNotBlockTag(b); e.removeEmptySpanBRTag(b._BODY); e.replaceBrTag(b); e.fn_IEJASOBug(b); e.removeLastBrTag(b); 0 == e.isViewMode(b) && (e.removeNbspInPTag(b), e.replaceClassForBorder(b, b._BODY, "show"), e.replaceClassForBookmark(b, b._BODY, "show"), !0 !== b.notFocusToEditor && (e.setFocusToBody(), e.setFocusFirstChildForStyle(b._BODY)));
											RAONKEDITOR.util.setBodyBackground(b); 0 == e.isViewMode(b) && "2" != b._config.undoMode && (b.UndoManager.putUndo(), b.UndoManager.charCount = 0, b.UndoManager.canUndo = !1, b.UndoManager.canRedo = !1); b._iconEnable(""); e.insertImageSrc(b); e.showRuler(b); e.setAutoBodyFit(b); e.setClassTableAndCellLock(b); e.set_view_mode_auto_height(b); "1" == b._config.tableAutoAdjustInSetHtml && e.command_AdjustTableAndCellWidth(b.ID, b, { type: "setHtml" }); e.setAdjustTableBorder(b._DOC); "show_blocks" == G_CURRKEDITOR.ShowBlocks && (G_CURRKEDITOR.ShowBlocks =
												"", e.command_showBlocks(b.ID, b)); setTimeout(function() {
													try {
														e.adjustScroll(b); if ("" != b._config.focusInitObjIdEx || b.autoMoveInitFocusData.targetNodeEx) { var a; "" != b._config.focusInitObjIdEx ? a = KEDITORTOP.KEDITORDOC.getElementById(b._config.focusInitObjIdEx) : b.autoMoveInitFocusData.targetNodeEx && (a = b.autoMoveInitFocusData.targetNodeEx); a ? (a.focus(), b._config.focusInitObjIdEx = "", b.autoMoveInitFocusData.targetNodeEx = null) : (f = !1, h = !0) } if (f || h) !e.isViewMode(b) && b._config.editorBodyEditableEx && (b._BODY.contentEditable =
															!0, b._config.editorBodyEditable = !0), h && 0 == f && !0 !== b.notFocusToEditor && (KEDITORTOP.focus(), KEDITORTOP.document.body.focus()); e.command_BeforeSetCompleteSpellCheck(b); e.g_findRepalceRange = null; try { "function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.setComplete ? KEDITORTOP.G_CURRKEDITOR._config.event.setComplete(b.ID) : KEDITORTOP.RAONKEDITOR_SetComplete(b.ID) } catch (c) { } try { e.onChange({ editor: b }) } catch (d) { } b.UndoManager.reset(); "2" == b._config.undoMode && b.UndoManager.putUndo(!0); b.setValueIsBusy = !1;
														!0 === b.notFocusToEditor && (b.notFocusToEditor = !1)
													} catch (g) { e.restoreValueInSetError(b) }
												}, 300)
										}; "base64" == c._config.uploadMethod ? /<img[^>]+file:\/\/\/[^>]+>/i.test(a) ? e.localImageToBase64InHtml({ isAllLocalFile: !0, targetNode: c._BODY, callbackFn: b, callbackArguments: [c] }) : b(null, c) : b(null, c)
								}; "1" == c._config.useHtmlCorrection ? "1" == c._config.useHtmlProcessByWorkerSetApi ? (e.showProcessingBackground(), e.releaseProcessHtmlWorker(), e.fn_processHtmlWorker({
									editorBrowser: {
										ie: RAONKEDITOR.browser.ie, ieVersion: RAONKEDITOR.browser.ieVersion,
										gecko: RAONKEDITOR.browser.gecko
									}, editorConfig: c._config, callFn: "htmlTagRevision", callFnParam: [a, !1], callBackFn: b
								})) : (a = e.htmlTagRevision(a, !1), b(a)) : b(a)
							}
						} catch (k) { e.restoreValueInSetError(c) } else null == RAONKEDITOR.InitEditorDataHashTable && (RAONKEDITOR.InitEditorDataHashTable = new RAONKEDITOR.util.hashTable), RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "setHtmlValueEx", value: b })
					} catch (l) { }
				}; RAONKEDITOR.getHtmlValueEx = RAONKEDITOR.GetHtmlValueEx = function(a, d) {
					var b = ""; try {
						var c = RAONKEDITOR.util._setEditor(d);
						if (c) {
							0 != !!a.isAuto || "undefined" != typeof a.undoReset && 1 != a.undoReset || c.UndoManager.reset(); var e = c._FRAMEWIN; if (1 == a.isAuto) { "" != c._config.placeholder.content && e.placeholderControl(c, "remove"); e.getHTMLForAutoSave(c, a); return } try { for (var f in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[f].onBeforeGetApi) e.G_KPlugin[f].onBeforeGetApi({ targetDoc: c._DOC }) } catch (h) { } var k = c.getEditorMode(); "source" != k && "text" != k || c.setChangeMode("design"); e.clearAllFormControlSelected(); e.ReplaceBase64ImageToArray(c._config,
								c._FRAMEWIN._iframeDoc.body); e.setRemoveClass(["td", "th"], ["keditor_dot"]); e.replaceClassForBorder(c, c._BODY, "hidden"); e.replaceClassForBookmark(c, c._BODY, "hidden"); e.ReplaceImageToRealObject(); e.xssReplaceScript(e._iframeDoc); e.ClearDraggingTableAllTable(); "1" == c._config.formMode && e.ReplaceCustomDataToRealEvent(); var l, n; if (1 == a.isAuto) try {
									RAONKEDITOR.browser.ie ? (l = Math.max(e._iframeDoc.documentElement.scrollLeft, e._iframeDoc.body.scrollLeft), n = Math.max(e._iframeDoc.documentElement.scrollTop, e._iframeDoc.body.scrollTop)) :
									(l = e._iframeWin.scrollX, n = e._iframeWin.scrollY)
								} catch (p) { } 0 == !!a.isAuto && e.changeBodyContenteditable(!1); e.changeBodyImageProperty(!0); try {
									a.async = !1 === a.async ? !1 : !0, RAONKEDITOR.util.postimageToServer(c, function() {
										1 != a.isAuto && (e.removeEmptySpanBRTag(c._BODY), "1" == c._config.wrapPtagToGetApi && e.wrapPtagForNotBlockTag(c), e.removeFakeLineHeight(c._BODY), RAONKEDITOR.util.setInLineDefaultStyle(c)); e.setMatchSelectedValue(c._BODY); e.setMatchInputValue(c._BODY, a.isAuto); e.adjustInputNodeForFF(c._DOC, !1);
										e.removeScrollStyleForIOS(c); e.setEmptyTagWhiteSpace(c); var d = "", f = "", h = ""; c._BODY.style.transformOrigin && "" != c._BODY.style.transformOrigin && (d = c._BODY.style.transformOrigin, c._BODY.style.transformOrigin = ""); c._BODY.style.transform && "" != c._BODY.style.transform && (f = c._BODY.style.transform, c._BODY.style.transform = ""); c._BODY.style.zoom && "" != c._BODY.style.zoom && (h = c._BODY.style.zoom, c._BODY.style.zoom = ""); "1" == c._config.autoBodyFit && e.setBodyFitStyle(c, !1); "" != c._config.placeholder.content && e.placeholderControl(c,
											"remove"); b = c._DOC.documentElement.outerHTML; "1" == c._config.autoBodyFit && e.setBodyFitStyle(c, !0); "" != c._config.placeholder.content && e.placeholderControl(c, "set"); "" != d && (c._BODY.style.transformOrigin = d); "" != f && (c._BODY.style.transform = f); "" != h && (c._BODY.style.zoom = h); b = RAONKEDITOR.util.removeHtmlLangAttrDuplication(b); b = e.dummyTagClassClear(b); c._PageProp.bshowgrid && 1 == c._PageProp.bshowgrid && e.changeBodyImageProperty(!1); "1" == c._config.formMode ? (e.ReplaceRealEventToCustomData(), e.ReplaceRealObjectToImage(!1)) :
												e.ReplaceRealObjectToImage(); e.changeBodyContenteditable(!0); if (1 == a.isAuto) try { e._iframeWin.scroll(l, n) } catch (g) { } 1 == RAONKEDITOR.browser.ie && (b = RAONKEDITOR.util.htmlToLowerCase(b)); b = e.RemoveUnnecessaryChar(b); b = e.CleanZeroChar(b); d = !1; "1" == c._config.replaceEmptyTagWithSpace && (d = !0); b = RAONKEDITOR.util.htmlRevision(b, d); "1" == c._config.xhtml_value && (b = RAONKEDITOR.util.html2xhtml(b)); b = e.removeEditorCss(b, c); e.setAddClass(["td", "th"], ["keditor_dot"]); 0 == e.isViewMode(c) && (e.replaceClassForBorder(c, c._BODY,
													"show"), e.replaceClassForBookmark(c, c._BODY, "show")); b = e.replaceLineBreak(c, b); b = e.insertCarriageReturn(c, b); b = e.ReplaceArrayToBase64Image(c._config, c._FRAMEWIN._iframeDoc.body, b); e.setScrollStyleForIOS(c); e.showRuler(c); "" != c._config.placeholder.content && e.placeholderControl(c, "class"); d = { strEditorId: c.ID, strData: b }; a.defaultStyle && (d.defaultStyle = a.defaultStyle, d.defaultStyleText = a.defaultStyleText); a.callback(d)
									}, a)
								} catch (m) { }
						}
					} catch (q) { } return b
				}; RAONKEDITOR.setHtmlValue = RAONKEDITOR.SetHtmlValue =
					function(a, d) {
						var b = RAONKEDITOR.util.parseSetApiParam(a); a = b.html; if ("" == a || "" == RAONKEDITOR.util.trim(a)) RAONKEDITOR.setBodyValue("", d); else try {
							if (d = RAONKEDITOR.util._getEditorName(d), null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) try {
								var c = RAONKEDITOR.util._setEditor(d); if (c) {
									c.setValueIsBusy = !0; !0 === b.notFocusToEditor && (c.notFocusToEditor = !0); var e = c._FRAMEWIN; e.getApplyDragResize(c) && c.keditor_dragresize.resizeHandleClear(); var f = c.isInitFocusHandler = !1, h = !1; "" == c._config.focusInitObjId && "" == c._config.focusInitObjIdEx &&
										!c.autoMoveInitFocusData.targetNode && !c.autoMoveInitFocusData.targetNodeEx || e.isViewMode(c) || (f = !0); 0 != c._config.initFocusForSetAPI || e.isViewMode(c) || (h = c._config.initFocusForSetAPI = !0); if (f || h) c._BODY.contentEditable = !1, c._config.editorBodyEditable = !1; a = e.addHtmlToSetValue(c, a); a = e.removeCarriageReturn(c, a); e.setChangeModeForSetApi(c); e.setGlobalTableDefaultValue(); c.UndoManager.reset(); "1" == c._config.emptyTagRemoveInSetapi && (a = e.CleanZeroChar(a)); a = e.removeDummyTag(a); a = e.removeIncorrectSpaceInTag(a);
									a = e.RAONK_EDITOR.HTMLParser.RemoveOfficeTag2(a); a = e.externalPageBreakDataRaplaceInEditor(a); b = function(a) {
										"1" == c._config.useHtmlProcessByWorkerSetApi && (e.destoryWebWorkerVar(), e.hideProcessingBackground()); a = e.removeTagStyle(a); a = e.htmlAsciiToChar(a); a = RAONKEDITOR.util.htmlRevision(a); a = e.xssReplaceScript(null, a); c._config.userCssUrl && "" != c._config.userCssUrl && c._config.userCssAlwaysSet && "1" == c._config.userCssAlwaysSet && (a = e.userCssSet(a, c._config.userCssUrl)); c._config.webFontCssUrl && "" != c._config.webFontCssUrl &&
											c._config.webFontCssAlwaysSet && "1" == c._config.webFontCssAlwaysSet && (a = e.userCssSet(a, c._config.webFontCssUrl)); a = e.adjustInputChecked(a); "1" == c._config.ie_BugFixed_Hyfont && (a = RAONKEDITOR.util.replaceHyFont(a, c)); "1" == c._config.replaceEmptySpanTagInSetapi && (a = e.replaceEmptySpanTag(a)); e.command_InsertDogBGImg(c.ID, c._EDITOR.design, "Y", "", "", "", "", []); try {
												for (var b in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[b].onBeforeDocumentWrite) {
													var d = e.G_KPlugin[b].onBeforeDocumentWrite({ html: a }); d && "string" ==
														typeof d.html && (a = d.html)
												}
											} catch (k) { } a = e.insertCarriageReturnBeforeCloseCell(a); a = e.removeEditorAttribute(a); e.setHeadValueToEditor(a, c); try { for (b in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[b].onAfterDocumentWrite) e.G_KPlugin[b].onAfterDocumentWrite() } catch (l) { } "" != c._config.placeholder.content && e.placeholderControl(c, "set"); b = function(a, b) {
												"1" == b._config.removeEmptyTagSetValue && e.setEmptyTagWhiteSpace(b); "0" != b._config.setDefaultStyle.value && "0" != b._config.setDefaultStyle.set_style && b._BODY.id !=
													b._config.setDefaultStyle.body_id && (b._BODY.id = b._config.setDefaultStyle.body_id); if ("1" == b._config.ruler.use && "" != b._config.ruler.rulerInitPosition && "0" != b._config.ruler.rulerInitPosition) if ("2" == b._config.ruler.mode && "1" == b._config.ruler.autoFitMode) if ("0" == b._config.ruler.fixEditorWidth) b._BODY.style.removeProperty ? b._BODY.style.removeProperty("width") : b._BODY.style.removeAttribute("width"); else {
														var c = RAONKEDITOR.util.parseIntOr0(b._config.ruler.rulerInitPosition.split(",")[2]), c = c - (2 * RAONKEDITOR.util.parseIntOr0(b._config.defaultBodySpace.value) +
															15); b._BODY.style.width = c + "px"
													} else c = KEDITORTOP.RAONKEDITOR.util.parseIntOr0(b._config.ruler.rulerInitPosition.split(",")[2]) - KEDITORTOP.RAONKEDITOR.util.parseIntOr0(b._config.defaultBodySpace.value), 0 < c && (b._BODY.style.width = c + "px"); b._config.zoomList && 0 < b._config.zoomList.length && e.command_zoom(b.ID, e.document.getElementById("keditor_design_" + b.ID)); "1" == b._config.useKManager && e.convertAllImageAgentSrc(e._iframeDoc, !0, !0); setTimeout(function() {
														for (var a = e._iframeDoc.getElementsByTagName("input"),
															c = a.length, d = 0; d < c; d++)"radio" == a[d].type && null != a[d].getAttribute("keditorchecked") && (a[d].checked = !0, a[d].setAttribute("checked", "checked"), a[d].removeAttribute("keditorchecked")); e.adjustInputNodeForFF(b._DOC, !0)
													}, 10); 0 == e.isViewMode(b) && (b._editorCustomDataMode = !0, "1" == b._config.formMode ? (e.ReplaceRealEventToCustomData(), e.ReplaceRealObjectToImage(!1)) : e.ReplaceRealObjectToImage(), e.replaceClassForBorder(b, b._BODY, "show"), e.replaceClassForBookmark(b, b._BODY, "show")); e.xssReplaceScript(e._iframeDoc);
												e.setScrollOverflow(b); e.setStyleForTableBorderNodeClass(b); e.setCssForFormMode(b); "1" == b._config.adjustCurrentColorInSetApi && RAONKEDITOR.util.adjustBorderStyle(null, b); b.ShowTableBorder && (b.ShowTableBorder = !1); b._iconEnable(""); 0 == e.isViewMode(b) && e.setBodyDefaultValue(); e.wrapPtagForNotBlockTag(b); e.removeEmptySpanBRTag(b._BODY); e.replaceBrTag(b); e.fn_IEJASOBug(b); e.removeLastBrTag(b); 0 == e.isViewMode(b) && (e.removeNbspInPTag(b), !0 !== b.notFocusToEditor && (e.setFocusToBody(), e.setFocusFirstChildForStyle(b._BODY)));
												RAONKEDITOR.util.setBodyBackground(b); 0 == e.isViewMode(b) && "2" != b._config.undoMode && (b.UndoManager.putUndo(), b.UndoManager.charCount = 0, b.UndoManager.canUndo = !1, b.UndoManager.canRedo = !1); b._iconEnable(""); e.insertImageSrc(b); e.showRuler(b); e.setAutoBodyFit(b); e.setClassTableAndCellLock(b); e.set_view_mode_auto_height(b); "1" == b._config.tableAutoAdjustInSetHtml && e.command_AdjustTableAndCellWidth(b.ID, b, { type: "setHtml" }); e.setAdjustTableBorder(b._DOC); "show_blocks" == G_CURRKEDITOR.ShowBlocks && (G_CURRKEDITOR.ShowBlocks =
													"", e.command_showBlocks(b.ID, b)); setTimeout(function() {
														try {
															e.adjustScroll(b); if ("" != b._config.focusInitObjIdEx || b.autoMoveInitFocusData.targetNodeEx) { var a; "" != b._config.focusInitObjIdEx ? a = KEDITORTOP.KEDITORDOC.getElementById(b._config.focusInitObjIdEx) : b.autoMoveInitFocusData.targetNodeEx && (a = b.autoMoveInitFocusData.targetNodeEx); a ? (a.focus(), b._config.focusInitObjIdEx = "", b.autoMoveInitFocusData.targetNodeEx = null) : (f = !1, h = !0) } if (f || h) !e.isViewMode(b) && b._config.editorBodyEditableEx && (b._BODY.contentEditable =
																!0, b._config.editorBodyEditable = !0), h && 0 == f && !0 !== b.notFocusToEditor && (KEDITORTOP.focus(), KEDITORTOP.document.body.focus()); e.command_BeforeSetCompleteSpellCheck(b); "2" == b._config.ruler.mode && e.setRulerPositionForNode(b); e.g_findRepalceRange = null; try { "function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.setComplete ? KEDITORTOP.G_CURRKEDITOR._config.event.setComplete(b.ID) : KEDITORTOP.RAONKEDITOR_SetComplete(b.ID) } catch (c) { } try { e.onChange({ editor: b }) } catch (d) { } b.UndoManager.reset(); "2" == b._config.undoMode &&
																	b.UndoManager.putUndo(!0); b.setValueIsBusy = !1; !0 === b.notFocusToEditor && (b.notFocusToEditor = !1)
														} catch (g) { e.restoreValueInSetError(b) }
													}, 300)
											}; "base64" == c._config.uploadMethod ? /<img[^>]+file:\/\/\/[^>]+>/i.test(a) ? e.localImageToBase64InHtml({ isAllLocalFile: !0, targetNode: c._BODY, callbackFn: b, callbackArguments: [c] }) : b(null, c) : b(null, c)
									}; "1" == c._config.useHtmlCorrection ? "1" == c._config.useHtmlProcessByWorkerSetApi ? (e.showProcessingBackground(), e.releaseProcessHtmlWorker(), e.fn_processHtmlWorker({
										editorBrowser: {
											ie: RAONKEDITOR.browser.ie,
											ieVersion: RAONKEDITOR.browser.ieVersion, gecko: RAONKEDITOR.browser.gecko
										}, editorConfig: c._config, callFn: "htmlTagRevision", callFnParam: [a, !1], callBackFn: b
									})) : (a = e.htmlTagRevision(a, !1), b(a)) : b(a)
								}
							} catch (k) { e.restoreValueInSetError(c) } else null == RAONKEDITOR.InitEditorDataHashTable && (RAONKEDITOR.InitEditorDataHashTable = new RAONKEDITOR.util.hashTable), RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "setHtmlValue", value: b })
						} catch (l) { }
					}; RAONKEDITOR.getHtmlValue = RAONKEDITOR.GetHtmlValue = function(a, d) {
						var b =
							""; try {
								var c = RAONKEDITOR.util._setEditor(d); if (c) {
									0 != !!a.isAuto || "undefined" != typeof a.undoReset && 1 != a.undoReset || c.UndoManager.reset(); var e = c._FRAMEWIN; if (1 == a.isAuto) { "" != c._config.placeholder.content && e.placeholderControl(c, "remove"); e.getHTMLForAutoSave(c, a); return } try { for (var f in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[f].onBeforeGetApi) e.G_KPlugin[f].onBeforeGetApi({ targetDoc: c._DOC }) } catch (h) { } var k = c.getEditorMode(); "source" != k && "text" != k || c.setChangeMode("design"); e.clearAllFormControlSelected();
									e.ReplaceBase64ImageToArray(c._config, c._FRAMEWIN._iframeDoc.body); e.setRemoveClass(["td", "th"], ["keditor_dot"]); e.replaceClassForBorder(c, c._BODY, "hidden"); e.replaceClassForBookmark(c, c._BODY, "hidden"); e.ReplaceImageToRealObject(); e.xssReplaceScript(e._iframeDoc); e.ClearDraggingTableAllTable(); "1" == c._config.formMode && e.ReplaceCustomDataToRealEvent(); var l, n; if (1 == a.isAuto) try {
										RAONKEDITOR.browser.ie ? (l = Math.max(e._iframeDoc.documentElement.scrollLeft, e._iframeDoc.body.scrollLeft), n = Math.max(e._iframeDoc.documentElement.scrollTop,
											e._iframeDoc.body.scrollTop)) : (l = e._iframeWin.scrollX, n = e._iframeWin.scrollY)
									} catch (p) { } 0 == !!a.isAuto && e.changeBodyContenteditable(!1); e.changeBodyImageProperty(!0); try {
										a.async = !1 === a.async ? !1 : !0, RAONKEDITOR.util.postimageToServer(c, function() {
											1 != a.isAuto && (e.removeEmptySpanBRTag(c._BODY), "1" == c._config.wrapPtagToGetApi && e.wrapPtagForNotBlockTag(c), e.removeFakeLineHeight(c._BODY), RAONKEDITOR.util.setInLineDefaultStyle(c)); e.setMatchSelectedValue(c._BODY); e.setMatchInputValue(c._BODY, a.isAuto); e.adjustInputNodeForFF(c._DOC,
												!1); e.removeScrollStyleForIOS(c); e.setEmptyTagWhiteSpace(c); var d = "", f = "", h = ""; c._BODY.style.transformOrigin && "" != c._BODY.style.transformOrigin && (d = c._BODY.style.transformOrigin, c._BODY.style.transformOrigin = ""); c._BODY.style.transform && "" != c._BODY.style.transform && (f = c._BODY.style.transform, c._BODY.style.transform = ""); c._BODY.style.zoom && "" != c._BODY.style.zoom && (h = c._BODY.style.zoom, c._BODY.style.zoom = ""); "1" == c._config.autoBodyFit && e.setBodyFitStyle(c, !1); "" != c._config.placeholder.content && e.placeholderControl(c,
													"remove"); b = c._DOC.documentElement.innerHTML; "1" == c._config.autoBodyFit && e.setBodyFitStyle(c, !0); "" != c._config.placeholder.content && e.placeholderControl(c, "set"); "" != d && (c._BODY.style.transformOrigin = d); "" != f && (c._BODY.style.transform = f); "" != h && (c._BODY.style.zoom = h); b = e.dummyTagClassClear(b); c._PageProp.bshowgrid && 1 == c._PageProp.bshowgrid && e.changeBodyImageProperty(!1); "1" == c._config.formMode ? (e.ReplaceRealEventToCustomData(), e.ReplaceRealObjectToImage(!1)) : e.ReplaceRealObjectToImage(); e.changeBodyContenteditable(!0);
											if (1 == a.isAuto) try { e._iframeWin.scroll(l, n) } catch (g) { } 1 == RAONKEDITOR.browser.ie && (b = RAONKEDITOR.util.htmlToLowerCase(b)); b = e.RemoveUnnecessaryChar(b); b = e.CleanZeroChar(b); d = !1; "1" == c._config.replaceEmptyTagWithSpace && (d = !0); b = RAONKEDITOR.util.htmlRevision(b, d); "1" == c._config.xhtml_value && (b = RAONKEDITOR.util.html2xhtml(b)); b = e.removeEditorCss(b, c); e.setAddClass(["td", "th"], ["keditor_dot"]); 0 == e.isViewMode(c) && (e.replaceClassForBorder(c, c._BODY, "show"), e.replaceClassForBookmark(c, c._BODY, "show")); b =
												e.replaceLineBreak(c, b); b = e.insertCarriageReturn(c, b); b = e.ReplaceArrayToBase64Image(c._config, c._FRAMEWIN._iframeDoc.body, b); e.setScrollStyleForIOS(c); e.showRuler(c); "" != c._config.placeholder.content && e.placeholderControl(c, "class"); a.callback({ strEditorId: c.ID, strData: b })
										}, a)
									} catch (m) { }
								}
							} catch (q) { } return b
					}; RAONKEDITOR.setBodyValueEx = RAONKEDITOR.SetBodyValueEx = function(a, d) {
						var b = RAONKEDITOR.util.parseSetApiParam(a); a = b.html; if ("" == a || "" == RAONKEDITOR.util.trim(a)) RAONKEDITOR.setBodyValue("", d); else try {
							if (d =
								RAONKEDITOR.util._getEditorName(d), null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) try {
									var c = RAONKEDITOR.util._setEditor(d); if (c) {
										c.setValueIsBusy = !0; !0 === b.notFocusToEditor && (c.notFocusToEditor = !0); var e = c._FRAMEWIN; e.getApplyDragResize(c) && c.keditor_dragresize.resizeHandleClear(); var f = c.isInitFocusHandler = !1, h = !1; "" == c._config.focusInitObjId && "" == c._config.focusInitObjIdEx && !c.autoMoveInitFocusData.targetNode && !c.autoMoveInitFocusData.targetNodeEx || e.isViewMode(c) || (f = !0); 0 != c._config.initFocusForSetAPI ||
											e.isViewMode(c) || (h = c._config.initFocusForSetAPI = !0); if (f || h) c._BODY.contentEditable = !1, c._config.editorBodyEditable = !1; a = e.addHtmlToSetValue(c, a); a = e.removeCarriageReturn(c, a); e.setChangeModeForSetApi(c); e.setGlobalTableDefaultValue(); c.UndoManager.reset(); "1" == c._config.emptyTagRemoveInSetapi && (a = e.CleanZeroChar(a)); a = e.removeDummyTag(a); a = e.removeIncorrectSpaceInTag(a); a = e.RAONK_EDITOR.HTMLParser.RemoveOfficeTag2(a); a = e.externalPageBreakDataRaplaceInEditor(a); b = function(a) {
												"1" == c._config.useHtmlProcessByWorkerSetApi &&
												(e.destoryWebWorkerVar(), e.hideProcessingBackground()); a = e.removeTagStyle(a); a = e.htmlAsciiToChar(a); a = RAONKEDITOR.util.htmlRevision(a); a = e.xssReplaceScript(null, a); "1" == c._config.ie_BugFixed_Hyfont && (a = RAONKEDITOR.util.replaceHyFont(a, c)); "1" == c._config.replaceEmptySpanTagInSetapi && (a = e.replaceEmptySpanTag(a)); e.command_InsertDogBGImg(c.ID, c._EDITOR.design, "Y", "", "", "", "", []); try {
													for (var b in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[b].onBeforeDocumentWrite) {
														var d = e.G_KPlugin[b].onBeforeDocumentWrite({ html: a });
														d && "string" == typeof d.html && (a = d.html)
													}
												} catch (k) { } a = e.insertCarriageReturnBeforeCloseCell(a); a = e.removeEditorAttribute(a); e.setBodyValueToEditorEx(a, c); try { for (b in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[b].onAfterDocumentWrite) e.G_KPlugin[b].onAfterDocumentWrite() } catch (l) { } "" != c._config.placeholder.content && e.placeholderControl(c, "set"); b = function(a, b) {
													"1" == b._config.removeEmptyTagSetValue && e.setEmptyTagWhiteSpace(b); "0" != b._config.setDefaultStyle.value && "0" != b._config.setDefaultStyle.set_style &&
														b._BODY.id != b._config.setDefaultStyle.body_id && (b._BODY.id = b._config.setDefaultStyle.body_id); if ("1" == b._config.ruler.use && "" != b._config.ruler.rulerInitPosition && "0" != b._config.ruler.rulerInitPosition) if ("2" == b._config.ruler.mode && "1" == b._config.ruler.autoFitMode) if ("0" == b._config.ruler.fixEditorWidth) b._BODY.style.removeProperty ? b._BODY.style.removeProperty("width") : b._BODY.style.removeAttribute("width"); else {
															var c = RAONKEDITOR.util.parseIntOr0(b._config.ruler.rulerInitPosition.split(",")[2]), c = c -
																(2 * RAONKEDITOR.util.parseIntOr0(b._config.defaultBodySpace.value) + 15); b._BODY.style.width = c + "px"
														} else c = KEDITORTOP.RAONKEDITOR.util.parseIntOr0(b._config.ruler.rulerInitPosition.split(",")[2]) - KEDITORTOP.RAONKEDITOR.util.parseIntOr0(b._config.defaultBodySpace.value), 0 < c && (b._BODY.style.width = c + "px"); b._config.zoomList && 0 < b._config.zoomList.length && e.command_zoom(b.ID, e.document.getElementById("keditor_design_" + b.ID)); "1" == b._config.useKManager && e.convertAllImageAgentSrc(e._iframeDoc, !0, !0); for (var c =
															e._iframeDoc.getElementsByTagName("input"), d = c.length, k = 0; k < d; k++)"radio" == c[k].type && null != c[k].getAttribute("keditorchecked") && (c[k].checked = !0, c[k].setAttribute("checked", "checked"), c[k].removeAttribute("keditorchecked")); e.adjustInputNodeForFF(b._DOC, !0); 0 == e.isViewMode(b) && (b._editorCustomDataMode = !0, "1" == b._config.formMode ? (e.ReplaceRealEventToCustomData(), e.ReplaceRealObjectToImage(!1)) : e.ReplaceRealObjectToImage(), e.replaceClassForBorder(b, b._BODY, "show"), e.replaceClassForBookmark(b, b._BODY,
																"show")); e.xssReplaceScript(e._iframeDoc.body); e.setScrollOverflow(b); e.setStyleForTableBorderNodeClass(b); e.setCssForFormMode(b); "1" == b._config.adjustCurrentColorInSetApi && RAONKEDITOR.util.adjustBorderStyle(null, b); b.ShowTableBorder && (b.ShowTableBorder = !1); b._iconEnable(""); 0 == e.isViewMode(b) && e.setBodyDefaultValue(); e.wrapPtagForNotBlockTag(b); e.removeEmptySpanBRTag(b._BODY); e.replaceBrTag(b); e.fn_IEJASOBug(b); e.removeLastBrTag(b); 0 == e.isViewMode(b) && (e.removeNbspInPTag(b), !0 !== b.notFocusToEditor &&
																	(e.setFocusToBody(), e.setFocusFirstChildForStyle(b._BODY))); RAONKEDITOR.util.setBodyBackground(b); 0 == e.isViewMode(b) && "2" != b._config.undoMode && (b.UndoManager.putUndo(), b.UndoManager.charCount = 0, b.UndoManager.canUndo = !1, b.UndoManager.canRedo = !1); b._iconEnable(""); e.insertImageSrc(b); e.showRuler(b); e.setAutoBodyFit(b); e.setClassTableAndCellLock(b); (RAONKEDITOR.browser.chrome || RAONKEDITOR.browser.opera || RAONKEDITOR.browser.safari) && e.set_view_mode_auto_height(b); "1" == b._config.tableAutoAdjustInSetHtml &&
																		e.command_AdjustTableAndCellWidth(b.ID, b, { type: "setHtml" }); e.setAdjustTableBorder(b._DOC); setTimeout(function() {
																			try {
																				e.adjustScroll(b); if ("" != b._config.focusInitObjIdEx || b.autoMoveInitFocusData.targetNodeEx) { var a; "" != b._config.focusInitObjIdEx ? a = KEDITORTOP.KEDITORDOC.getElementById(b._config.focusInitObjIdEx) : b.autoMoveInitFocusData.targetNodeEx && (a = b.autoMoveInitFocusData.targetNodeEx); a ? (a.focus(), b._config.focusInitObjIdEx = "", b.autoMoveInitFocusData.targetNodeEx = null) : (f = !1, h = !0) } if (f || h) !e.isViewMode(b) &&
																					b._config.editorBodyEditableEx && (b._BODY.contentEditable = !0, b._config.editorBodyEditable = !0), h && 0 == f && !0 !== b.notFocusToEditor && (KEDITORTOP.focus(), KEDITORTOP.document.body.focus()); e.command_BeforeSetCompleteSpellCheck(b); "2" == b._config.ruler.mode && e.setRulerPositionForNode(b); e.g_findRepalceRange = null; try { "function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.setComplete ? KEDITORTOP.G_CURRKEDITOR._config.event.setComplete(b.ID) : KEDITORTOP.RAONKEDITOR_SetComplete(b.ID) } catch (c) { } try { e.onChange({ editor: b }) } catch (d) { } b.UndoManager.reset();
																				"2" == b._config.undoMode && b.UndoManager.putUndo(!0); b.setValueIsBusy = !1; !0 === b.notFocusToEditor && (b.notFocusToEditor = !1)
																			} catch (g) { e.restoreValueInSetError(b) }
																		}, 300)
												}; "base64" == c._config.uploadMethod ? /<img[^>]+file:\/\/\/[^>]+>/i.test(a) ? e.localImageToBase64InHtml({ isAllLocalFile: !0, targetNode: c._BODY, callbackFn: b, callbackArguments: [c] }) : b(null, c) : b(null, c)
											}; "1" == c._config.useHtmlCorrection ? "1" == c._config.useHtmlProcessByWorkerSetApi ? (e.showProcessingBackground(), e.releaseProcessHtmlWorker(), e.fn_processHtmlWorker({
												editorBrowser: {
													ie: RAONKEDITOR.browser.ie,
													ieVersion: RAONKEDITOR.browser.ieVersion, gecko: RAONKEDITOR.browser.gecko
												}, editorConfig: c._config, callFn: "htmlTagRevision", callFnParam: [a, !1], callBackFn: b
											})) : (a = e.htmlTagRevision(a, !1), b(a)) : b(a)
									}
								} catch (k) { e.restoreValueInSetError(c) } else null == RAONKEDITOR.InitEditorDataHashTable && (RAONKEDITOR.InitEditorDataHashTable = new RAONKEDITOR.util.hashTable), RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "setBodyValueEx", value: b })
						} catch (l) { }
					}; RAONKEDITOR.getBodyValueEx = RAONKEDITOR.GetBodyValueEx = function(a,
						d) {
							var b = ""; try {
								var c = RAONKEDITOR.util._setEditor(d); if (c) {
									0 != !!a.isAuto || "undefined" != typeof a.undoReset && 1 != a.undoReset || c.UndoManager.reset(); var e = c._FRAMEWIN; if (1 == a.isAuto) { "" != c._config.placeholder.content && e.placeholderControl(c, "remove"); e.getHTMLForAutoSave(c, a); return } try { for (var f in e.G_KPlugin) if ("function" === typeof e.G_KPlugin[f].onBeforeGetApi) e.G_KPlugin[f].onBeforeGetApi({ targetDoc: c._DOC }) } catch (h) { } var k = c.getEditorMode(); "source" != k && "text" != k || c.setChangeMode("design"); e.clearAllFormControlSelected();
									e.ReplaceBase64ImageToArray(c._config, c._FRAMEWIN._iframeDoc.body); e.setRemoveClass(["td", "th"], ["keditor_dot"]); e.replaceClassForBorder(c, c._BODY, "hidden"); e.replaceClassForBookmark(c, c._BODY, "hidden"); e.ReplaceImageToRealObject(); e.xssReplaceScript(e._iframeDoc.body); e.ClearDraggingTableAllTable(); "1" == c._config.formMode && e.ReplaceCustomDataToRealEvent(); var l, n; if (1 == a.isAuto) try {
										RAONKEDITOR.browser.ie ? (l = Math.max(e._iframeDoc.documentElement.scrollLeft, e._iframeDoc.body.scrollLeft), n = Math.max(e._iframeDoc.documentElement.scrollTop,
											e._iframeDoc.body.scrollTop)) : (l = e._iframeWin.scrollX, n = e._iframeWin.scrollY)
									} catch (p) { } 0 == !!a.isAuto && e.changeBodyContenteditable(!1); e.changeBodyImageProperty(!0); try {
										a.async = !1 === a.async ? !1 : !0, RAONKEDITOR.util.postimageToServer(c, function() {
											1 != a.isAuto && (e.removeEmptySpanBRTag(c._BODY), "1" == c._config.wrapPtagToGetApi && e.wrapPtagForNotBlockTag(c), e.removeFakeLineHeight(c._BODY), RAONKEDITOR.util.setInLineDefaultStyle(c)); e.setMatchSelectedValue(c._BODY); e.setMatchInputValue(c._BODY, a.isAuto); e.adjustInputNodeForFF(c._DOC,
												!1); e.setEmptyTagWhiteSpace(c); var d = "", f = "", h = ""; c._BODY.style.transformOrigin && "" != c._BODY.style.transformOrigin && (d = c._BODY.style.transformOrigin, c._BODY.style.transformOrigin = ""); c._BODY.style.transform && "" != c._BODY.style.transform && (f = c._BODY.style.transform, c._BODY.style.transform = ""); c._BODY.style.zoom && "" != c._BODY.style.zoom && (h = c._BODY.style.zoom, c._BODY.style.zoom = ""); "1" == c._config.autoBodyFit && e.setBodyFitStyle(c, !1); "" != c._config.placeholder.content && e.placeholderControl(c, "remove");
											b = c._BODY.outerHTML; "1" == c._config.autoBodyFit && e.setBodyFitStyle(c, !0); "" != c._config.placeholder.content && e.placeholderControl(c, "set"); "" != d && (c._BODY.style.transformOrigin = d); "" != f && (c._BODY.style.transform = f); "" != h && (c._BODY.style.zoom = h); b = e.dummyTagClassClear(b); c._PageProp.bshowgrid && 1 == c._PageProp.bshowgrid && e.changeBodyImageProperty(!1); "1" == c._config.formMode ? (e.ReplaceRealEventToCustomData(), e.ReplaceRealObjectToImage(!1)) : e.ReplaceRealObjectToImage(); e.changeBodyContenteditable(!0); if (1 ==
												a.isAuto) try { e._iframeWin.scroll(l, n) } catch (g) { } 1 == RAONKEDITOR.browser.ie && (b = RAONKEDITOR.util.htmlToLowerCase(b)); b = e.RemoveUnnecessaryChar(b); b = e.CleanZeroChar(b); d = !1; "1" == c._config.replaceEmptyTagWithSpace && (d = !0); b = RAONKEDITOR.util.htmlRevision(b, d); "1" == c._config.xhtml_value && (b = RAONKEDITOR.util.html2xhtml(b)); e.setAddClass(["td", "th"], ["keditor_dot"]); 0 == e.isViewMode(c) && (e.replaceClassForBorder(c, c._BODY, "show"), e.replaceClassForBookmark(c, c._BODY, "show")); b = e.replaceLineBreak(c, b); b = e.insertCarriageReturn(c,
													b); b = e.ReplaceArrayToBase64Image(c._config, c._FRAMEWIN._iframeDoc.body, b); e.showRuler(c); "" != c._config.placeholder.content && e.placeholderControl(c, "class"); a.callback({ strEditorId: c.ID, strData: b })
										}, a)
									} catch (m) { }
								}
							} catch (q) { } return b
					}; RAONKEDITOR.setBodyValue = RAONKEDITOR.SetBodyValue = function(a, d, b, c, e) {
						try {
							var f = RAONKEDITOR.util.parseSetApiParam(a); a = f.html; !b || "" != d && void 0 != d || (d = KEDITORTOP.G_CURRKEDITOR.ID); d = RAONKEDITOR.util._getEditorName(d); if (null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) try {
								var h =
									RAONKEDITOR.util._setEditor(d); if (h) {
										0 == !!b && 0 == !!c && (h.setValueIsBusy = !0, !0 === f.notFocusToEditor && (h.notFocusToEditor = !0)); if ("" == a || "" == RAONKEDITOR.util.trim(a)) a = "<p></p>"; var k = h._FRAMEWIN; k.getApplyDragResize(h) && h.keditor_dragresize.resizeHandleClear(); var l = h.isInitFocusHandler = !1, n = !1; 1 != b && ("" == h._config.focusInitObjId && "" == h._config.focusInitObjIdEx && !h.autoMoveInitFocusData.targetNode && !h.autoMoveInitFocusData.targetNodeEx || k.isViewMode(h) || (l = !0), 0 != h._config.initFocusForSetAPI || k.isViewMode(h) ||
											(n = h._config.initFocusForSetAPI = !0), l || n) && (h._BODY.contentEditable = !1, h._config.editorBodyEditable = !1); a = k.addHtmlToSetValue(h, a); a = k.removeCarriageReturn(h, a); k.setChangeModeForSetApi(h, c); k.setGlobalTableDefaultValue(); h.UndoManager.reset(); "1" == h._config.emptyTagRemoveInSetapi && (a = k.CleanZeroChar(a)); a = k.removeDummyTag(a); a = k.removeIncorrectSpaceInTag(a); a = k.RAONK_EDITOR.HTMLParser.RemoveOfficeTag2(a); a = k.externalPageBreakDataRaplaceInEditor(a); d = function(a) {
												"1" == h._config.useHtmlProcessByWorkerSetApi &&
												(k.destoryWebWorkerVar(), k.hideProcessingBackground()); a = k.removeTagStyle(a); a = k.htmlAsciiToChar(a); var d = "BackCompat" == k._iframeDoc.compatMode; RAONKEDITOR.browser.ie && 7 < RAONKEDITOR.browser.ieVersion && 11 > RAONKEDITOR.browser.ieVersion && 1 != d ? 0 == !!b && (a = RAONKEDITOR.util.htmlRevision(a)) : a = RAONKEDITOR.util.htmlRevision(a); 1 != c && (a = k.xssReplaceScript(null, a)); a = k.adjustInputChecked(a); "1" == h._config.ie_BugFixed_Hyfont && (a = RAONKEDITOR.util.replaceHyFont(a, h)); "1" == h._config.replaceEmptySpanTagInSetapi &&
													(a = k.replaceEmptySpanTag(a)); try { for (var f in k.G_KPlugin) if ("function" === typeof k.G_KPlugin[f].onBeforeDocumentWrite) { var m = k.G_KPlugin[f].onBeforeDocumentWrite({ html: a }); m && "string" == typeof m.html && (a = m.html) } } catch (g) { } a = k.insertCarriageReturnBeforeCloseCell(a); a = k.removeEditorAttribute(a); k.setBodyValueToEditor(a, h); try { for (f in k.G_KPlugin) if ("function" === typeof k.G_KPlugin[f].onAfterDocumentWrite) k.G_KPlugin[f].onAfterDocumentWrite() } catch (p) { } "" != h._config.placeholder.content && k.placeholderControl(h,
														"set"); d = function(a, d) {
															"1" == d._config.removeEmptyTagSetValue && k.setEmptyTagWhiteSpace(d); "0" != d._config.setDefaultStyle.value && "0" != d._config.setDefaultStyle.set_style && d._BODY.id != d._config.setDefaultStyle.body_id && (d._BODY.id = d._config.setDefaultStyle.body_id); if ("1" == d._config.ruler.use && "" != d._config.ruler.rulerInitPosition && "0" != d._config.ruler.rulerInitPosition) if ("2" == d._config.ruler.mode && "1" == d._config.ruler.autoFitMode) if ("0" == d._config.ruler.fixEditorWidth) d._BODY.style.removeProperty ?
																d._BODY.style.removeProperty("width") : d._BODY.style.removeAttribute("width"); else { var f = RAONKEDITOR.util.parseIntOr0(d._config.ruler.rulerInitPosition.split(",")[2]), f = f - (2 * RAONKEDITOR.util.parseIntOr0(d._config.defaultBodySpace.value) + 15); d._BODY.style.width = f + "px" } else f = KEDITORTOP.RAONKEDITOR.util.parseIntOr0(d._config.ruler.rulerInitPosition.split(",")[2]) - KEDITORTOP.RAONKEDITOR.util.parseIntOr0(d._config.defaultBodySpace.value), 0 < f && (d._BODY.style.width = f + "px"); 0 == !!b && "1" == d._config.useKManager &&
																	k.convertAllImageAgentSrc(k._iframeDoc, !0, !0); for (var f = k._iframeDoc.getElementsByTagName("input"), g = f.length, h = 0; h < g; h++)"radio" == f[h].type && null != f[h].getAttribute("keditorchecked") && (f[h].checked = !0, f[h].setAttribute("checked", "checked"), f[h].removeAttribute("keditorchecked")); k.adjustInputNodeForFF(d._DOC, !0); 1 != b && 0 == k.isViewMode(d) && (d._editorCustomDataMode = !0, "1" == d._config.formMode ? (k.ReplaceRealEventToCustomData(), k.ReplaceRealObjectToImage(!1)) : k.ReplaceRealObjectToImage(), k.replaceClassForBookmark(d,
																		d._BODY, "show")); k.xssReplaceScript(k._iframeDoc.body); e && k.ReplaceRealObjectToImage(); k.replaceClassForBorder(d, d._BODY, "show"); k.setStyleForTableBorderNodeClass(d); k.setCssForFormMode(d); "1" == d._config.adjustCurrentColorInSetApi && RAONKEDITOR.util.adjustBorderStyle(null, d); d.ShowTableBorder && (d.ShowTableBorder = !1); d._iconEnable(""); 0 == k.isViewMode(d) && k.setBodyDefaultValue(); k.wrapPtagForNotBlockTag(d); k.removeEmptySpanBRTag(d._BODY); k.replaceBrTag(d); k.fn_IEJASOBug(d); k.removeLastBrTag(d); 0 == k.isViewMode(d) &&
																			k.removeNbspInPTag(d); 1 != b && 0 == k.isViewMode(d) && (k.replaceClassForBorder(d, d._BODY, "show"), k.replaceClassForBookmark(d, d._BODY, "show"), !0 !== d.notFocusToEditor && (k.setFocusToBody(), k.setFocusFirstChildForStyle(d._BODY))); RAONKEDITOR.util.setBodyBackground(d); 0 == k.isViewMode(d) && "2" != d._config.undoMode && (d.UndoManager.putUndo(), d.UndoManager.charCount = 0, d.UndoManager.canUndo = !1, d.UndoManager.canRedo = !1); d._iconEnable(""); k.insertImageSrc(d, null, c); k.showRuler(d); k.setAutoBodyFit(d); k.setClassTableAndCellLock(d);
															(RAONKEDITOR.browser.chrome || RAONKEDITOR.browser.opera || RAONKEDITOR.browser.safari) && k.set_view_mode_auto_height(d); "1" == d._config.tableAutoAdjustInSetHtml && k.command_AdjustTableAndCellWidth(d.ID, d, { type: "setHtml" }); k.setAdjustTableBorder(d._DOC); c || setTimeout(function() {
																try {
																	k.adjustScroll(d); if ("" != d._config.focusInitObjIdEx || d.autoMoveInitFocusData.targetNodeEx) {
																		var a; "" != d._config.focusInitObjIdEx ? a = KEDITORTOP.KEDITORDOC.getElementById(d._config.focusInitObjIdEx) : d.autoMoveInitFocusData.targetNodeEx &&
																			(a = d.autoMoveInitFocusData.targetNodeEx); a ? (0 == b && a.focus(), d._config.focusInitObjIdEx = "", d.autoMoveInitFocusData.targetNodeEx = null) : (l = !1, n = !0)
																	} if (l || n) !k.isViewMode(d) && d._config.editorBodyEditableEx && (d._BODY.contentEditable = !0, d._config.editorBodyEditable = !0), n && 0 == l && !0 !== d.notFocusToEditor && (KEDITORTOP.focus(), KEDITORTOP.document.body.focus()); k.command_BeforeSetCompleteSpellCheck(d); "2" == d._config.ruler.mode && k.setRulerPositionForNode(d); k.g_findRepalceRange = null; try {
																		"function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.setComplete ?
																		KEDITORTOP.G_CURRKEDITOR._config.event.setComplete(d.ID) : KEDITORTOP.RAONKEDITOR_SetComplete(d.ID)
																	} catch (c) { } try { k.onChange({ editor: d }) } catch (e) { } d.UndoManager.reset(); "2" == d._config.undoMode && d.UndoManager.putUndo(!0); d.setValueIsBusy = !1; !0 === d.notFocusToEditor && (d.notFocusToEditor = !1)
																} catch (f) { k.restoreValueInSetError(d) }
															}, 300)
														}; "base64" == h._config.uploadMethod ? /<img[^>]+file:\/\/\/[^>]+>/i.test(a) ? k.localImageToBase64InHtml({ isAllLocalFile: !0, targetNode: h._BODY, callbackFn: d, callbackArguments: [h] }) :
															d(null, h) : d(null, h)
											}; "1" == h._config.useHtmlCorrection ? "1" == h._config.useHtmlProcessByWorkerSetApi ? (k.showProcessingBackground(), k.releaseProcessHtmlWorker(), k.fn_processHtmlWorker({ editorBrowser: { ie: RAONKEDITOR.browser.ie, ieVersion: RAONKEDITOR.browser.ieVersion, gecko: RAONKEDITOR.browser.gecko }, editorConfig: h._config, callFn: "htmlTagRevision", callFnParam: [a, !1], callBackFn: d })) : (a = k.htmlTagRevision(a, !1), d(a)) : d(a)
									}
							} catch (p) { k.restoreValueInSetError(h) } else null == RAONKEDITOR.InitEditorDataHashTable &&
								(RAONKEDITOR.InitEditorDataHashTable = new RAONKEDITOR.util.hashTable), RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "setBodyValue", value: f })
						} catch (m) { }
					}; RAONKEDITOR.getBodyValue = RAONKEDITOR.GetBodyValue = function(a, d) {
						var b = ""; try {
							var c = RAONKEDITOR.util._setEditor(d); if (c) {
								0 != !!a.isAuto || "undefined" != typeof a.undoReset && 1 != a.undoReset || c.UndoManager.reset(); var e = c._FRAMEWIN; if (1 == a.isAuto) "" != c._config.placeholder.content && e.placeholderControl(c, "remove"), e.getHTMLForAutoSave(c, a); else {
									try {
										for (var f in e.G_KPlugin) if ("function" ===
											typeof e.G_KPlugin[f].onBeforeGetApi) e.G_KPlugin[f].onBeforeGetApi({ targetDoc: c._DOC })
									} catch (h) { } var k = c.getEditorMode(); "source" != k && "text" != k || c.setChangeMode("design"); e.clearAllFormControlSelected(); e.ReplaceBase64ImageToArray(c._config, c._FRAMEWIN._iframeDoc.body); e.setRemoveClass(["td", "th"], ["keditor_dot"]); e.replaceClassForBorder(c, c._BODY, "hidden"); e.replaceClassForBookmark(c, c._BODY, "hidden"); e.ReplaceImageToRealObject(); e.xssReplaceScript(e._iframeDoc.body); e.ClearDraggingTableAllTable();
									"1" == c._config.formMode && e.ReplaceCustomDataToRealEvent(); e.changeBodyImageProperty(!0); try {
										a.async = !1 === a.async ? !1 : !0, RAONKEDITOR.util.postimageToServer(c, function() {
											1 != a.isAuto && (e.removeEmptySpanBRTag(c._BODY), "1" == c._config.wrapPtagToGetApi && e.wrapPtagForNotBlockTag(c), e.removeFakeLineHeight(c._BODY), RAONKEDITOR.util.setInLineDefaultStyle(c)); e.setMatchSelectedValue(c._BODY); e.setMatchInputValue(c._BODY, a.isAuto); e.adjustInputNodeForFF(c._DOC, !1); e.setEmptyTagWhiteSpace(c); "1" == c._config.autoBodyFit &&
												e.setBodyFitStyle(c, !1); "" != c._config.placeholder.content && e.placeholderControl(c, "remove"); b = c._BODY.innerHTML; "1" == c._config.autoBodyFit && e.setBodyFitStyle(c, !0); "" != c._config.placeholder.content && e.placeholderControl(c, "set"); b = e.dummyTagClassClear(b); c._PageProp.bshowgrid && 1 == c._PageProp.bshowgrid && e.changeBodyImageProperty(!1); "1" == c._config.formMode ? (e.ReplaceRealEventToCustomData(), e.ReplaceRealObjectToImage(!1)) : e.ReplaceRealObjectToImage(); 1 == RAONKEDITOR.browser.ie && (b = RAONKEDITOR.util.htmlToLowerCase(b));
											b = e.RemoveUnnecessaryChar(b); b = e.CleanZeroChar(b); var d = !1; "1" == c._config.replaceEmptyTagWithSpace && (d = !0); b = RAONKEDITOR.util.htmlRevision(b, d); "1" == c._config.xhtml_value && (b = RAONKEDITOR.util.html2xhtml(b)); e.setAddClass(["td", "th"], ["keditor_dot"]); 0 == e.isViewMode(c) && (e.replaceClassForBorder(c, c._BODY, "show"), e.replaceClassForBookmark(c, c._BODY, "show")); b = e.replaceLineBreak(c, b); b = e.insertCarriageReturn(c, b); b = e.ReplaceArrayToBase64Image(c._config, c._FRAMEWIN._iframeDoc.body, b); e.showRuler(c); "" !=
												c._config.placeholder.content && e.placeholderControl(c, "class"); a.callback({ strEditorId: c.ID, strData: b })
										}, a)
									} catch (l) { }
								}
							}
						} catch (n) { }
					}; RAONKEDITOR.loadHtmlValueExFromURL = RAONKEDITOR.LoadHtmlValueExFromURL = function(a, d) {
						try {
							if (d = RAONKEDITOR.util._getEditorName(d), null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) try { if (RAONKEDITOR.util._setEditor(d)) { var b = RAONKEDITOR.ajax.load(a); RAONKEDITOR.setHtmlContents(b, d) } } catch (c) { } else null == RAONKEDITOR.InitEditorDataHashTable && (RAONKEDITOR.InitEditorDataHashTable =
								new RAONKEDITOR.util.hashTable), RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "loadHtmlValueExFromURL", value: a })
						} catch (e) { }
					}; RAONKEDITOR.loadHtmlValueExFromServerURL = RAONKEDITOR.LoadHtmlValueExFromServerURL = function(a, d) {
						try {
							if (d = RAONKEDITOR.util._getEditorName(d), null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) try {
								var b = RAONKEDITOR.util._setEditor(d); if (b) {
									0 != a.toLowerCase().indexOf("http:") && 0 != a.toLowerCase().indexOf("https:") && (0 < a.toLowerCase().indexOf("www.") ? a = location.protocol + "//" + a : 0 ==
										a.toLowerCase().indexOf("/") && (a = location.protocol + "//" + location.host + a)); var c = "", c = c + ("kc" + RAONKSolution.Agent.formfeed + "c05" + RAONKSolution.Agent.vertical), c = c + ("k30" + RAONKSolution.Agent.formfeed + a), c = RAONKEDITOR.util.makeEncryptParam(c); params = "k00=" + c; var e = RAONKEDITOR.ajax.postData(b._config.handlerUrl, params), e = KEDITORTOP.RAONKEDITOR.util.parseDataFromServer(e); -1 >= e.indexOf("[OK]") ? alert(e) : (e = e.replace("[OK]", ""), e = RAONKEDITOR.util.makeDecryptReponseMessage(e), RAONKEDITOR.setHtmlContents(e,
											d))
								}
							} catch (f) { } else null == RAONKEDITOR.InitEditorDataHashTable && (RAONKEDITOR.InitEditorDataHashTable = new RAONKEDITOR.util.hashTable), RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "loadHtmlValueExFromServerURL", value: a })
						} catch (h) { }
					}; RAONKEDITOR.setDirectEditHtmlUrl = RAONKEDITOR.SetDirectEditHtmlUrl = function(a, d) {
						try {
							if (d = RAONKEDITOR.util._getEditorName(d), null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) {
								if (a && "" != a) try {
									var b = RAONKEDITOR.util._setEditor(d); if (b) {
										b.ShowTableBorder && (b.ShowTableBorder =
											!1); b._config.directEditHtmlUrl = a; var c = b._FRAMEWIN; c.getApplyDragResize(b) && b.keditor_dragresize.resizeHandleClear(); c.loadDirectEditHtmlUrl(b._config)
									}
								} catch (e) { }
							} else null == RAONKEDITOR.InitEditorDataHashTable && (RAONKEDITOR.InitEditorDataHashTable = new RAONKEDITOR.util.hashTable), RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "setDirectEditHtmlUrl", value: a })
						} catch (f) { }
					}; RAONKEDITOR.getBodyTextValue = RAONKEDITOR.GetBodyTextValue = function(a, d) {
						var b = ""; try {
							var c = RAONKEDITOR.util._setEditor(d),
							e = c._FRAMEWIN; if (c) {
								try {
									var f = c.getEditorMode(); "source" != f && "text" != f || c.setChangeMode("design"); e.changeBodyImageProperty(!0); f = function() {
										b = c._BODY.innerHTML; c._PageProp.bshowgrid && 1 == c._PageProp.bshowgrid && changeBodyImageProperty(!1); b = b.replace(/\r/g, ""); b = b.replace(/[\n|\t]/g, ""); b = b.replace(/[\v|\f]/g, ""); b = b.replace(/<p><br><\/p>/gi, "\n"); b = b.replace(/<P>&nbsp;<\/P>/gi, "\n"); RAONKEDITOR.browser.ie && 11 == RAONKEDITOR.browser.ieVersion && (b = b.replace(/<br(\s)*\/?><\/p>/gi, "</p>"), b = b.replace(/<br(\s[^\/]*)?><\/p>/gi,
											"</p>")); b = b.replace(/<br(\s)*\/?>/gi, "\n"); b = b.replace(/<br(\s[^\/]*)?>/gi, "\n"); b = b.replace(/<\/p(\s[^\/]*)?>/gi, "\n"); b = b.replace(/<\/li(\s[^\/]*)?>/gi, "\n"); b = b.replace(/<\/tr(\s[^\/]*)?>/gi, "\n"); var d = b.lastIndexOf("\n"); -1 < d && "\n" == b.substring(d) && (b = b.substring(0, d)); b = e.removeStripTags(b, null); b = e.unhtmlSpecialChars(b); a.callback({ strEditorId: c.ID, strData: b })
									}; try { a.async = !1 === a.async ? !1 : !0, RAONKEDITOR.util.postimageToServer(c, f, a) } catch (h) { }
								} catch (k) { b = "" } e.showRuler(c)
							}
						} catch (l) { }
					}; RAONKEDITOR.encodeMime =
						RAONKEDITOR.EncodeMime = function(a, d) {
							try {
								var b = RAONKEDITOR.util._setEditor(d), c = b._config, e = b._FRAMEWIN; if ("1" == c.useKManager && "1" == c.mimeUse) {
									0 == !!a.htmlType && (a.htmlType = "htmlvalueex"); e.ReplaceImageToRealObject(); e.xssReplaceScript(e._iframeDoc); e.ClearDraggingTableAllTable(); e.changeBodyImageProperty(!0); var f = "", h, k, l = [], n, p, m = [], q, r, u = [], t, g, y = [], v, A, F = [], E, x, J = []; void 0 === a.image && (a.image = !0); if (a.image) {
										h = b._DOC.getElementsByTagName("img"); k = h.length; for (var w = 0; w < k; w++) {
											var H = h[w].src, z =
												H, z = decodeURIComponent(z), z = e.convertImageAgentSrc(z, !1), z = decodeURIComponent(z), G = !0; if (a.imageLocalOnly) 0 == z.toLowerCase().indexOf("http") && 0 == z.toLowerCase().indexOf("http") && (G = !1); else if ("" != a.targetDomain) { var C = z.toLowerCase(); 0 == C.indexOf("http:") && (C = C.replace("https://", ""), C = C.replace("http://", ""), 0 != C.indexOf(a.targetDomain) && (G = !1)) } if (G) {
													l.push(H); var z = z.replace("file:///", ""), D = RAONKEDITOR.util.makeGuid(); h[w].src = "cid:" + D; f += "cid:" + D + RAONKSolution.Agent.formfeed + z + RAONKSolution.Agent.formfeed +
														"0" + RAONKSolution.Agent.vertical
												}
										} n = ["table", "td", "body"]; p = n.length; for (w = 0; w < p; w++)for (var K = b._DOC.getElementsByTagName(n[w]), N = K.length, c = 0; c < N; c++) {
											var B = K[c].style.backgroundImage; "" == B && K[c].getAttribute("background") && (B = K[c].getAttribute("background")); B = B.replace('url("', "").replace('")', ""); B = B.replace("url('", "").replace("')", ""); B = B.replace("url(", "").replace(")", ""); "" != B && "none" != B.toLowerCase() && "initial" != B.toLowerCase() && (B = decodeURIComponent(B), B = e.convertImageAgentSrc(B, !1), B =
												decodeURIComponent(B), G = !0, a.imageLocalOnly ? 0 == B.toLowerCase().indexOf("http") && 0 == B.toLowerCase().indexOf("http") && (G = !1) : "" != a.targetDomain && (C = B.toLowerCase(), 0 == C.indexOf("http:") && (C = C.replace("https://", ""), C = C.replace("http://", ""), 0 != C.indexOf(a.targetDomain) && (G = !1))), G && (m.push(B), B = B.replace("file:///", ""), D = "cid:" + RAONKEDITOR.util.makeGuid(), K[c].style.backgroundImage = "url(" + D + ")", f += D + RAONKSolution.Agent.formfeed + B + RAONKSolution.Agent.formfeed + "0" + RAONKSolution.Agent.vertical))
										}
									} if (a.css) for (q =
										b._DOC.getElementsByTagName("LINK"), r = q.length, w = 0; w < r; w++)z = q[w].href, 0 > z.indexOf("/editor_contents.css") && 0 < z.length && (u.push(z), D = "cid:" + RAONKEDITOR.util.makeGuid(), q[w].href = D, f += D + RAONKSolution.Agent.formfeed + z + RAONKSolution.Agent.formfeed + "0" + RAONKSolution.Agent.vertical); if (a.js) for (t = b._DOC.getElementsByTagName("SCRIPT"), g = t.length, w = 0; w < g; w++)z = t[w].src, 0 < t.length && (y.push(z), D = "cid:" + RAONKEDITOR.util.makeGuid(), t[w].src = D, f += D + RAONKSolution.Agent.formfeed + z + RAONKSolution.Agent.formfeed +
											"0" + RAONKSolution.Agent.vertical); if (a.object) {
												v = b._DOC.getElementsByTagName("EMBED"); A = v.length; for (w = 0; w < A; w++)z = v[w].src, 0 < z.length && (F.push(z), D = "cid:" + RAONKEDITOR.util.makeGuid(), v[w].src = D, f += D + RAONKSolution.Agent.formfeed + z + RAONKSolution.Agent.formfeed + "0" + RAONKSolution.Agent.vertical); E = b._DOC.getElementsByTagName("OBJECT"); x = E.length; for (w = 0; w < x; w++)z = E[w].data, 0 < z.length && (J.push(z), D = "cid:" + RAONKEDITOR.util.makeGuid(), v[w].src = D, f += D + RAONKSolution.Agent.formfeed + z + RAONKSolution.Agent.formfeed +
													"0" + RAONKSolution.Agent.vertical)
											} w = !0; !1 === a.async && (w = !1); RAONKEDITOR.GetHtmlContents({
												type: a.htmlType, isAuto: !1, async: w, callback: function(c) {
													c = c.strData; var d = 0; if (a.image) {
														for (var w = 0; w < k; w++) { var z = h[w].src; 0 == z.indexOf("cid:") && (h[w].src = l[d], d++) } for (w = d = 0; w < p; w++)for (var z = b._DOC.getElementsByTagName(n[w]), B = z.length, D = 0; D < B; D++) {
															var C = z[D].style.backgroundImage; "" == C && z[D].getAttribute("background") && (C = z[D].getAttribute("background")); C = C.replace('url("', "").replace('")', ""); C = C.replace("url('",
																"").replace("')", ""); C = C.replace("url(", "").replace(")", ""); 0 == C.indexOf("cid:") && (z[D].style.backgroundImage = "url(" + m[d] + ")", d++)
														}
													} if (a.css) for (w = d = 0; w < r; w++)z = q[w].href, 0 == z.indexOf("cid:") && (q[w].href = u[d], d++); if (a.js) for (w = d = 0; w < g; w++)z = t[w].src, 0 == z.indexOf("cid:") && (t[w].src = y[d], d++); if (a.object) { for (w = d = 0; w < A; w++)z = v[w].src, 0 == z.indexOf("cid:") && (v[w].src = F[d], d++); for (w = d = 0; w < x; w++)z = E[w].src, 0 == z.indexOf("cid:") && (E[w].src = J[d], d++) } e.ReplaceRealObjectToImage(); try {
														J = E = F = v = y = t = u = q = m = n =
														l = h = void 0
													} catch (G) { } d = [["kcmd", "KC33"]]; d.push(["k00", { browser: RAONKEDITOR.UserAgent.browser.name.toLowerCase(), useragent: encodeURIComponent(navigator.userAgent) }]); d.push(["k15", 1]); d.push(["k71", e.getHttpHeaderAgentData(b)]); d.push(["kp1", 0]); f = f.substring(0, f.length - 1); d.push(["kp2", encodeURIComponent(f)]); d.push(["kp3", c]); c = ""; void 0 != a.subject && "" != a.subject && (c = "0" == a.subject ? "None" : a.subject); d.push(["s1", encodeURIComponent(c)]); c = ""; void 0 != a.date && "0" == a.date && (c = "None"); d.push(["s2", encodeURIComponent(c)]);
													c = ""; void 0 != a.xGenerator && "" != a.xGenerator && (c = "0" == a.xGenerator ? "None" : a.xGenerator); d.push(["s3", encodeURIComponent(c)]); c = e.setManagerParam("{}", d); a.async = !1 === a.async ? !1 : !0; e.sendMessageToAgent(c + "\x0B", function(c) { c = KEDITORTOP.RAONKSolution.Agent.parseRtn(c); var d = c.code; c = c.valueArr; if (1E3 == d || 7777 == d) { c = { strEditorId: b.ID, strData: c[0] }; try { a.callback(c) } catch (e) { } } else c = { strEditorId: b.ID, strData: "0" }, a.callback(c) }, null, a.async)
												}
											}, b.ID)
								}
							} catch (P) { }
						}; RAONKEDITOR.decodeMime = RAONKEDITOR.DecodeMime =
							function(a, d) {
								try {
									var b = RAONKEDITOR.util._setEditor(d), c = b._FRAMEWIN; if ("1" == b._config.useKManager) {
										var e = [["kcmd", "KC33"]]; e.push(["k00", { browser: RAONKEDITOR.UserAgent.browser.name.toLowerCase(), useragent: encodeURIComponent(navigator.userAgent) }]); e.push(["k15", 1]); e.push(["k71", c.getHttpHeaderAgentData(b)]); e.push(["kp1", 1]); e.push(["kp2", ""]); var f = a.mimeData, f = f.replace(/\r\n/g, "\n"), f = f.replace(/\n/g, "\r\n"); e.push(["kp3", f]); var h = c.setManagerParam("{}", e), e = !0; !1 === a.async && (e = !1); c.sendMessageToAgent(h +
											"\x0B", function(c) { c = KEDITORTOP.RAONKSolution.Agent.parseRtn(c); var d = c.splitCode, e = c.code; c = c.valueArr; if (1E3 == e || 7777 == e) { var f = { strEditorId: b.ID, strData: c[0] }; if (7777 == e && " " == d) for (var e = c.length, h = 1; h < e; h++)f.strData += d + c[h]; try { a.callback(f) } catch (k) { } } else f = { strEditorId: b.ID, strData: "0" }, a.callback(f) }, null, e)
									}
								} catch (k) { }
							}; RAONKEDITOR.decodeMimeEx = RAONKEDITOR.DecodeMimeEx = function(a, d) {
								try {
									var b = RAONKEDITOR.util._setEditor(d); b && "1" == b._config.useKManager && "1" == b._config.mimeUse && RAONKEDITOR.DecodeMime({
										mimeData: a,
										callback: function(a) { if (0 < a.strData.length && "0" != a.strData) { var c = b._config.xss_use, d = b._config.xss_remove_tags, k = b._config.xss_remove_events; b._config.xss_use = ""; b._config.xss_remove_tags = ""; b._config.xss_remove_events = ""; RAONKEDITOR.setHtmlValueEx(a.strData, b.ID); b._config.xss_use = c; b._config.xss_remove_tags = d; b._config.xss_remove_events = k } }
									}, b.ID)
								} catch (c) { }
							}; RAONKEDITOR.isEmpty = RAONKEDITOR.IsEmpty = function(a) {
								var d = !1; try {
									var b = RAONKEDITOR.util._setEditor(a); if (b) {
										if ("" != b._config.defaultMessage) return !0;
										var c = b._FRAMEWIN, e = c._iframeDoc.body.textContent || c._iframeDoc.body.innerText; "" != e && (e = e.replace(/ /gi, ""), e = e.replace(/\s/gi, ""), e = e.replace(/\t/g, ""), e = e.replace(/\r?\n?\r?\n/g, ""), e = e.replace(/&nbsp;/gi, ""), e = e.replace(/<br \/>/gi, ""), e = e.replace(/<br>/gi, ""), e = e.replace(/<p *([^>?+])*><\/p>/gi, "")); var f = c._iframeDoc.body.innerHTML; if ("" != f) {
											var f = f.replace(/ /gi, ""), f = f.replace(/\s/gi, ""), f = f.replace(/\t/g, ""), f = f.replace(/\r?\n?\r?\n/g, ""), f = f.replace(/&nbsp;/gi, ""), f = f.replace(/<br \/>/gi,
												""), f = f.replace(/<br>/gi, ""), h = b._config.removeEmptyTag; b._config.removeEmptyTag = "1"; f = c.CleanZeroChar(f); b._config.removeEmptyTag = h; f = f.replace(/<p *([^>?+])*><\/p>/gi, ""); 1 == RAONKEDITOR.util.isEmptyContents(f) && (f = "")
										} d = 0 == e.length && 0 == f.length ? !0 : !1
									} else d = !1
								} catch (k) { } return d
							}; RAONKEDITOR.isEmptyToString = RAONKEDITOR.IsEmptyToString = function(a) { var d = !1, d = RAONKEDITOR.isEmpty(a); return d = d.toString().toLowerCase() }; RAONKEDITOR.setInsertHTMLToObject = RAONKEDITOR.SetInsertHTMLToObject = function(a,
								d, b) { if (void 0 != a && "" != a && void 0 != d && "" != d) try { var c = RAONKEDITOR.util._setEditor(b); if (c) { var e = c._DOC.getElementById(d); a = c._FRAMEWIN.externalPageBreakDataRaplaceInEditor(a); e && (e.innerHTML = a); setTimeout(function() { try { "function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.setInsertComplete ? KEDITORTOP.G_CURRKEDITOR._config.event.setInsertComplete(c.ID) : KEDITORTOP.RAONKEDITOR_SetInsertComplete(c.ID) } catch (a) { } }, 200) } } catch (f) { } }; RAONKEDITOR.setInsertHTML = RAONKEDITOR.SetInsertHTML = function(a, d) {
									if (void 0 !=
										a && "" != a) try {
											var b = RAONKEDITOR.util._setEditor(d); if (b) {
												var c = b._FRAMEWIN; b.isInitFocusHandler = !1; c.restoreSelection(); c.setFocusToBody(); setTimeout(function() {
													0 < b.UndoManager.charCount && b.UndoManager.putUndo(); c.ReplaceImageToRealObject(); c.xssReplaceScript(c._iframeDoc); c.ClearDraggingTableAllTable(); if (!RAONKEDITOR.browser.chrome && !RAONKEDITOR.browser.opera) {
														var d = document.createElement("div"); d.innerHTML = a; d.lastChild && 1 == d.lastChild.nodeType && -1 < ",input,select,button,textarea,".indexOf("," + d.lastChild.tagName.toLowerCase() +
															",") && (a += unescape("%uFEFF"))
													} a = c.externalPageBreakDataRaplaceInEditor(a); a = c.removeEditorAttribute(a); try { var e = b.isTablePaste; e && (b.isTablePaste = !1); c.pasteHtmlAtCaretHuge(a, !0, !1, e) } catch (k) { } c.isViewMode(b) || (b._editorCustomDataMode = !0, c.ReplaceRealObjectToImage()); try { c.onChange({ editor: b, isPossibleDirty: !0 }) } catch (l) { } b.UndoManager.putUndo(); b.UndoManager.charCount = 0; b._iconEnable(""); c.setFocusToBody(); try {
														"function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.setInsertComplete ? KEDITORTOP.G_CURRKEDITOR._config.event.setInsertComplete(b.ID) :
														KEDITORTOP.RAONKEDITOR_SetInsertComplete(b.ID)
													} catch (n) { }
												}, 200)
											}
										} catch (e) { }
								}; RAONKEDITOR.setInsertHTMLEx = RAONKEDITOR.SetInsertHTMLEx = function(a, d, b) {
									if (void 0 != a && "" != a) try {
										var c = RAONKEDITOR.util._setEditor(b), e = c._FRAMEWIN; c && (c.isInitFocusHandler = !1, e.restoreSelection(), e.setFocusToBody(), setTimeout(function() {
											if ("" != d && (0 == d || 1 == d)) {
												var b = c._BODY; if (1 == RAONKEDITOR.browser.chrome || 1 == RAONKEDITOR.browser.opera) {
													var f = document.createElement("p"); f.innerHTML = "<br>"; b = f; 0 == d ? c._BODY.insertBefore(f,
														c._BODY.firstChild) : 1 == d && c._BODY.appendChild(f)
												} e.doSetCaretPosition(b, d)
											} 0 < c.UndoManager.charCount && c.UndoManager.putUndo(); e.ReplaceImageToRealObject(); e.xssReplaceScript(e._iframeDoc); e.ClearDraggingTableAllTable(); a = e.externalPageBreakDataRaplaceInEditor(a); a = e.removeEditorAttribute(a); if (1 == RAONKEDITOR.browser.chrome || 1 == RAONKEDITOR.browser.opera || 1 == RAONKEDITOR.browser.gecko) {
												1 == RAONKEDITOR.browser.gecko && (b = document.createElement("div"), b.innerHTML = a, b.lastChild && 1 == b.lastChild.nodeType &&
													-1 < ",input,select,button,textarea,".indexOf("," + b.lastChild.tagName.toLowerCase() + ",") && (a += unescape("%uFEFF"))); try { e._iframeDoc.execCommand("inserthtml", !1, a) } catch (l) { }
											} else { b = document.createElement("div"); b.innerHTML = a; b.lastChild && 1 == b.lastChild.nodeType && -1 < ",input,select,button,textarea,".indexOf("," + b.lastChild.tagName.toLowerCase() + ",") && (a += unescape("%uFEFF")); try { e.pasteHtmlAtCaretHuge(a, !0) } catch (n) { } } e.isViewMode(c) || (c._editorCustomDataMode = !0, e.ReplaceRealObjectToImage()); try {
												e.onChange({
													editor: c,
													isPossibleDirty: !0
												})
											} catch (p) { } c.UndoManager.putUndo(); c.UndoManager.charCount = 0; c._iconEnable(""); e.setFocusToBody(); try { "function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.setInsertComplete ? KEDITORTOP.G_CURRKEDITOR._config.event.setInsertComplete(c.ID) : KEDITORTOP.RAONKEDITOR_SetInsertComplete(c.ID) } catch (m) { }
										}, 200))
									} catch (f) { }
								}; RAONKEDITOR.setInsertText = RAONKEDITOR.SetInsertText = function(a, d) {
									if (void 0 != a && "" != a) try {
										var b = RAONKEDITOR.util._setEditor(d), c = b._FRAMEWIN; b && (b.UndoManager.putUndo(),
											c.restoreSelection(), c.setFocusToBody(), setTimeout(function() {
												var d = c.getFirstRange(), e = d.range, d = d.sel; e.deleteContents(); 0 < e.toString().length ? c.setRestoreCaretPos(!0) : c.setRestoreCaretPos(!1); a += '<span id="keditor_symbol">keditor_symbol</span>'; e.pasteHtml(a); var k = c._iframeDoc.getElementById("keditor_symbol"); try { e.setStartBefore(k), e.setEndBefore(k), d.removeAllRanges(), d.addRange(e), k.parentNode.removeChild(k) } catch (l) { k && k.parentNode.removeChild(k) } try { c.onChange({ editor: b, isPossibleDirty: !0 }) } catch (n) { } b.UndoManager.putUndo();
												try { "function" == typeof KEDITORTOP.G_CURRKEDITOR._config.event.setInsertComplete ? KEDITORTOP.G_CURRKEDITOR._config.event.setInsertComplete(b.ID) : KEDITORTOP.RAONKEDITOR_SetInsertComplete(b.ID) } catch (p) { }
											}, 200))
									} catch (e) { }
								}; RAONKEDITOR.setEditorBorder = RAONKEDITOR.SetEditorBorder = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); b && b._FRAMEWIN.setEditorBorder(b, a) } catch (c) { } }; RAONKEDITOR.setEditorMode = RAONKEDITOR.SetEditorMode = function(a, d) {
									try {
										var b = RAONKEDITOR.util._setEditor(d); if (b) {
											var c = b._FRAMEWIN,
											e = !1; if ("edit" == a && "edit" != b._config.mode) {
												b._BODY.contentEditable = !0; b._config.mode = "edit"; if ((KEDITORTOP.RAONKEDITOR.browser.mobile || RAONKEDITOR.browser.iOS) && "1" == b._config.view_mode_auto_height) { var f = KEDITORDOC.getElementById(b._config.holderID), h = KEDITORDOC.getElementById("raonk_frame_" + b.ID), k = h.contentWindow.document, l = k.getElementById("ue_editor_holder_" + b.ID); f.style.webkitOverflowScrolling = "touch"; l.style.webkitOverflowScrolling = "touch"; f.style.overflow = ""; l.style.overflow = "" } "0" == b._config.top_menu &&
													(c.document.getElementById("keditor_menu_" + b.ID).style.display = ""); if (3 > parseInt(b._config.tool_bar, 10)) { c.document.getElementById("keditor_toolbar_" + b.ID).style.display = ""; if ("1" == b._config.tool_bar || "0" == b._config.tool_bar) c.document.getElementById("keditor_tab_tool2_" + b.ID).style.display = ""; if ("2" == b._config.tool_bar || "0" == b._config.tool_bar) c.document.getElementById("keditor_tab_tool1_" + b.ID).style.display = "" } "0" == b._config.status_bar && (c.document.getElementById("keditor_statusbar_" + b.ID).style.height =
														"23px", c.document.getElementById("keditor_statusbar_" + b.ID).style.display = "", c.document.getElementById("div_resizebar_" + b.ID) && (c.document.getElementById("div_resizebar_" + b.ID).style.display = "")); "0" == b._config.top_status_bar && (c.document.getElementById("keditor_topstatusbar_" + b.ID).style.height = "28px", c.document.getElementById("keditor_topstatusbar_" + b.ID).style.display = ""); e = !0; b._BODY.style.cursor = "text"
											} else if ("view" == a && "view" != b._config.mode) {
												c.hideRuler(b); b._BODY.contentEditable = !1; b._config.mode =
													"view"; (KEDITORTOP.RAONKEDITOR.browser.mobile || KEDITORTOP.RAONKEDITOR.browser.iOS) && "1" == b._config.view_mode_auto_height && (f = c.KEDITORTOP.KEDITORDOC.getElementById(b._config.holderID), h = KEDITORTOP.KEDITORDOC.getElementById("raonk_frame_" + b.ID), k = h.contentWindow.document, l = k.getElementById("ue_editor_holder_" + b.ID), f.style.webkitOverflowScrolling = "touch", l.style.webkitOverflowScrolling = "touch", f.style.overflow = "hidden", l.style.overflow = "hidden"); "0" == b._config.top_menu && (c.document.getElementById("keditor_menu_" +
														b.ID).style.display = "none"); if (3 > parseInt(b._config.tool_bar, 10)) { c.document.getElementById("keditor_toolbar_" + b.ID).style.display = "none"; if ("1" == b._config.tool_bar || "0" == b._config.tool_bar) c.document.getElementById("keditor_tab_tool2_" + b.ID).style.display = "none"; if ("2" == b._config.tool_bar || "0" == b._config.tool_bar) c.document.getElementById("keditor_tab_tool1_" + b.ID).style.display = "none" } "0" == b._config.status_bar && (c.document.getElementById("keditor_statusbar_" + b.ID).style.height = "1px", c.document.getElementById("keditor_statusbar_" +
															b.ID).style.display = "none", c.document.getElementById("div_resizebar_" + b.ID) && (c.document.getElementById("div_resizebar_" + b.ID).style.display = "none")); "0" == b._config.top_status_bar && (c.document.getElementById("keditor_topstatusbar_" + b.ID).style.height = "1px", c.document.getElementById("keditor_topstatusbar_" + b.ID).style.display = "none"); e = !0; b._BODY.style.cursor = "auto"
											} e && (c.resizeEditor(null, !1), RAONKEDITOR.GetHtmlContents({
												isAuto: !1, type: "htmlex", callback: function(d) {
													RAONKEDITOR.setHtmlContents(d.strData,
														d.strEditorId); "view" == a ? (0 == RAONKEDITOR.browser.ie && KEDITORTOP.focus(), KEDITORDOC.body.focus(), "" != b._config.placeholder.content && c.placeholderControl(b, "remove")) : ("1" == b._config.ruler.view && c.showRuler(b), "" != b._config.placeholder.content && c.placeholderControl(b, "set"))
												}
											}, b.ID)); c.setEditorIframeTitle(b); c.groupingIcon()
										}
									} catch (n) { }
								}; RAONKEDITOR.editorPrint = RAONKEDITOR.EditorPrint = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); if (d) try { d._FRAMEWIN.command_print("", d) } catch (b) { } } catch (c) { } };
	RAONKEDITOR.doPrint = RAONKEDITOR.DoPrint = function(a, d, b) { try { var c = RAONKEDITOR.util._setEditor(b); c && c._editorCommands(c.ID, "print") } catch (e) { } }; RAONKEDITOR.doPrintPreview = RAONKEDITOR.DoPrintPreview = function(a, d, b) {
		try {
			var c = RAONKEDITOR.util._setEditor(b); if (c) if (G_CURRKEDITOR._FRAMEWIN.G_dext5plugIn) {
				var e = c._config.printPreview; c._config.printPreview = "1"; c.setChangeMode("preview"); setTimeout(function() {
					var b; b = c._EDITOR.preview.contentDocument ? c._EDITOR.preview.contentDocument : c._EDITOR.preview.contentWindow.document;
					RAONKEDITOR.browser.ie && (b = b.getElementById("keditorprintpreview")) && b.doPrintPreview(a, d, ""); c.setChangeMode("design")
				}, 350); c._config.printPreview = e
			} else c._FRAMEWIN.command_print("", c)
		} catch (f) { }
	}; RAONKEDITOR.doSaveHTML = RAONKEDITOR.DoSaveHTML = function(a, d, b, c) { try { var e = RAONKEDITOR.util._setEditor(c); if (e && "1" == e._config.useKManager) { var f = e._FRAMEWIN; if (0 == !!d || "" == d) d = "RAONK"; f.agentSaveFile(e, d, a, function(a) { b(a) }) } } catch (h) { } }; RAONKEDITOR.addUserCssUrl = RAONKEDITOR.AddUserCssUrl = function(a,
		d) { try { var b = RAONKEDITOR.util._setEditor(d); if (b) { var c = b._FRAMEWIN; b.isInitFocusHandler = !1; var e = b._DOC.getElementsByTagName("head")[0]; if (e) { c.createDynamicCssLinkToHeadTag(b._DOC, e, a); c.setFocusToBody(); try { setTimeout(function() { if (!0 !== b.notFocusToEditor) { var a = c.getFirstRange().range, d = null; a && a.startContainer && (d = c.getMyElementNode(a.startContainer), c.setMenuIconRealable(d)) } }, 10) } catch (f) { } } } } catch (h) { } }; RAONKEDITOR.clearUserCssUrl = RAONKEDITOR.ClearUserCssUrl = function(a, d) {
			try {
				var b = RAONKEDITOR.util._setEditor(d);
				if (b) { var c = b._FRAMEWIN; b.isInitFocusHandler = !1; var e = b._DOC.getElementsByTagName("head")[0]; if (e) { c.clearDynamicCssLinkToHeadTag(e, a); c.setFocusToBody(); try { setTimeout(function() { var a = c.getFirstRange().range, b = null; a && a.startContainer && (b = c.getMyElementNode(a.startContainer), c.setMenuIconRealable(b)) }, 10) } catch (f) { } } }
			} catch (h) { }
		}; RAONKEDITOR.addWebFontCssUrl = RAONKEDITOR.AddWebFontCssUrl = function(a, d) {
			try {
				var b = RAONKEDITOR.util._setEditor(d); if (b) {
					var c = b._FRAMEWIN; b.isInitFocusHandler = !1; var e =
						b._DOC.getElementsByTagName("head")[0]; if (e) {
							a = RAONKEDITOR.util.setProtocolBaseDomainURL(a); c.createDynamicCssLinkToHeadTag(b._DOC, e, a); var f = c.getDialogWindow().document.getElementById("keditor_fontfamily_iframe_" + b.ID); f ? c.createDynamicCssLinkToHeadTag(f.contentWindow.document, f.contentWindow.document.getElementsByTagName("head")[0], a) : b._config.webFontCssUrl = a; c.setFocusToBody(); try {
								setTimeout(function() {
									var a = c.getFirstRange().range, b = null; a && a.startContainer && (b = c.getMyElementNode(a.startContainer),
										c.setMenuIconRealable(b))
								}, 10)
							} catch (h) { }
						}
				}
			} catch (k) { }
		}; RAONKEDITOR.setUserCssText = RAONKEDITOR.SetUserCssText = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); if (b) { var c = b._FRAMEWIN; b.isInitFocusHandler = !1; var e = b._DOC.getElementsByTagName("head")[0]; e && (c.addDynamicCssToHeadTag(b._DOC, e, a), c.setFocusToBody(), setTimeout(function() { try { var a = c.getFirstRange().range, b = null; a && a.startContainer && (b = c.getMyElementNode(a.startContainer), c.setMenuIconRealable(b)) } catch (d) { } }, 10)) } } catch (f) { } }; RAONKEDITOR.clearUserCssText =
			RAONKEDITOR.ClearUserCssText = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); if (d) { var b = d._FRAMEWIN; d.isInitFocusHandler = !1; var c = d._DOC.getElementsByTagName("head")[0]; if (c) { b.addDynamicCssToHeadTag(d._DOC, c, ""); b.setFocusToBody(); try { setTimeout(function() { var a = b.getFirstRange().range, c = null; a && a.startContainer && (c = b.getMyElementNode(a.startContainer), b.setMenuIconRealable(c)) }, 10) } catch (e) { } } } } catch (f) { } }; RAONKEDITOR.addUserJsUrl = RAONKEDITOR.AddUserJsUrl = function(a, d) {
				try {
					var b = RAONKEDITOR.util._setEditor(d);
					if (b) { a = RAONKEDITOR.util.setProtocolBaseDomainURL(a); var c = !1; if ("1" == b._config.xss_use) for (var e = a.toLowerCase(), f = 0; f < b._config.xss_allow_url.length; f++) { if (b._config.xss_allow_url[f].toLowerCase() == e) { c = !0; break } } else c = !0; if (1 == c) { var h = b._FRAMEWIN; b.isInitFocusHandler = !1; var k = b._DOC.getElementsByTagName("head")[0]; k && (h.createDynamicJsLinkToHeadTag(b._DOC, k, a), h.setFocusToBody()) } }
				} catch (l) { }
			}; RAONKEDITOR.clearUserJsUrl = RAONKEDITOR.ClearUserJsUrl = function(a) {
				try {
					var d = RAONKEDITOR.util._setEditor(a);
					if (d) { var b = d._FRAMEWIN; d.isInitFocusHandler = !1; var c = d._DOC.getElementsByTagName("head")[0]; c && (b.clearDynamicJsLinkToHeadTag(c), b.setFocusToBody()) }
				} catch (e) { }
			}; RAONKEDITOR.setUserJsText = RAONKEDITOR.SetUserJsText = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); if (b) { var c = b._FRAMEWIN; b.isInitFocusHandler = !1; var e = b._DOC.getElementsByTagName("head")[0]; e && (c.addDynamicJsToHeadTag(b._DOC, e, a), c.setFocusToBody()) } } catch (f) { } }; RAONKEDITOR.clearUserJsText = RAONKEDITOR.ClearUserJsText = function(a) {
				try {
					var d =
						RAONKEDITOR.util._setEditor(a); if (d) { var b = d._FRAMEWIN; d.isInitFocusHandler = !1; var c = d._DOC.getElementsByTagName("head")[0]; c && (b.addDynamicJsToHeadTag(d._DOC, c, ""), b.setFocusToBody()) }
				} catch (e) { }
			}; RAONKEDITOR.setNextTabElementId = RAONKEDITOR.SetNextTabElementId = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); b && (b._config.NextTabElementId = a) } catch (c) { } }; RAONKEDITOR.setFullScreen = RAONKEDITOR.SetFullScreen = function(a) {
				try {
					var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.command_fullScreen(d.ID,
						d)
				} catch (b) { }
			}; RAONKEDITOR.loadAutoSaveHtml = RAONKEDITOR.LoadAutoSaveHtml = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); if (d) { var b = d._FRAMEWIN.getAutoSaveHtml_userID(a, d._config.setAutoSave.unique_key, d._config.setAutoSave.maxCount); b && "" != b && RAONKEDITOR.setHtmlValueExWithDocType(b[b.length - 1].html, a) } } catch (c) { } }; RAONKEDITOR.saveCurrValueInMultiValue = RAONKEDITOR.SaveCurrValueInMultiValue = function(a, d) {
				try {
					var b = RAONKEDITOR.util._setEditor(d); if (b) {
						var c = RAONKEDITOR.util.getValueByMultiMode(),
						e = []; e.push(c); for (c = 2; c < arguments.length; c++)e.push(arguments[c]); b.changeMultiValue[a] || (b.changeMultiValue.valueLength += 1); b.changeMultiValue[a] = e
					}
				} catch (f) { }
			}; RAONKEDITOR.setNextValueInMultiValue = RAONKEDITOR.SetNextValueInMultiValue = function(a, d) {
				var b = null; try {
					var c = RAONKEDITOR.util._setEditor(d); if (c) {
						if (c.changeMultiValue[a]) { var e = c.changeMultiValue[a][0]; RAONKEDITOR.util.setValueByMultiMode(e) } else e = "<p><br></p>", RAONKEDITOR.browser.ie && 11 > RAONKEDITOR.browser.ieVersion && (e = "<p>&nbsp</p>"),
							RAONKEDITOR.setBodyValue(e); b = c.changeMultiValue[a] ? c.changeMultiValue[a] : null
					}
				} catch (f) { } return b
			}; RAONKEDITOR.getMultiValue = RAONKEDITOR.GetMultiValue = function(a, d) { var b = null; try { var c = RAONKEDITOR.util._setEditor(d); c && (b = c.changeMultiValue[a]) } catch (e) { } return b }; RAONKEDITOR.getMultiValueLength = RAONKEDITOR.GetMultiValueLength = function(a) { var d = null; try { var b = RAONKEDITOR.util._setEditor(a); b && (d = b.changeMultiValue.valueLength) } catch (c) { } return d }; RAONKEDITOR.setEditorChangeMode = RAONKEDITOR.SetEditorChangeMode =
				function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); b && b.setChangeMode(a) } catch (c) { } }; RAONKEDITOR.setUserFontFamily = RAONKEDITOR.SetUserFontFamily = function(a, d) { try { if (void 0 != a && null != a && "" != a) { var b = RAONKEDITOR.util._setEditor(d); if (b && ("\ub9d1\uc740\uace0\ub515" == a && (a = "\ub9d1\uc740 \uace0\ub515"), b._DOC.body.style.fontFamily = a, "1" == b._setting.show_font_real)) { var c = document.getElementById("ue_" + b.ID + "font_family"); c && (c.innerHTML = "<span>" + a + "</span>", b._setting.font_family = a) } } } catch (e) { } };
	RAONKEDITOR.setUserFontSize = RAONKEDITOR.SetUserFontSize = function(a, d) { try { if (void 0 != a && null != a && "" != a) { var b = RAONKEDITOR.util._setEditor(d); if (b && (b._DOC.body.style.fontSize = a + "pt", "1" == b._setting.show_font_real)) { var c = document.getElementById("ue_" + b.ID + "font_size"); c && (c.innerHTML = "<span>" + a + "pt</span>", b._setting.font_size = a + "pt") } } } catch (e) { } }; RAONKEDITOR.setFocusToEditor = RAONKEDITOR.SetFocusToEditor = function(a) {
		try {
			var d = RAONKEDITOR.util._setEditor(a); d && (d.isInitFocusHandler = !1, d.setFocusToBody(),
				setTimeout(function() { var a = d._FRAMEWIN, b = a.getFirstRange().range; b && b.startContainer == b.endContainer && 1 == b.collapsed && ((elem = a.getMyElementNode(b.startContainer)) && elem.tagName && "p" == elem.tagName.toLowerCase() && ("" == elem.innerHTML || "<br>" == elem.innerHTML.toLowerCase()) ? a.doSetCaretPosition(elem, 0) : !elem || elem && elem.tagName && "body" == elem.tagName.toLowerCase() ? ((b = d._BODY.firstChild) && "" == b.innerHTML && (b.innerHTML = "<br>"), a.doSetCaretPosition(b, 0)) : a.setFocusToBody()) }, 1))
		} catch (b) { }
	}; RAONKEDITOR.setRulerPosition =
		RAONKEDITOR.SetRulerPosition = function(a, d) { if (void 0 != a && "" != a) try { var b = RAONKEDITOR.util._setEditor(d); if (b) { var c = b._FRAMEWIN; 1 == b._config.ruler.use && (b._setting.ruler_position = a, c.set_ruler_point_position(b), c.set_ruler_pointline_position(b)) } } catch (e) { } }; RAONKEDITOR.setClassStyle = RAONKEDITOR.SetClassStyle = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); b && b._FRAMEWIN.command_setClassStyle(b, a) } catch (c) { } }; RAONKEDITOR.removeClassStyle = RAONKEDITOR.RemoveClassStyle = function(a, d) {
			try {
				var b =
					RAONKEDITOR.util._setEditor(d); b && b._FRAMEWIN.command_removeClassStyle(b, a)
			} catch (c) { }
		}; RAONKEDITOR.setFontFamily = RAONKEDITOR.SetFontFamily = function(a, d) { if (void 0 != a && "" != a) try { var b = RAONKEDITOR.util._setEditor(d); b && b._FRAMEWIN.command_fontfamily(b.ID, b._EDITOR.design, a) } catch (c) { } }; RAONKEDITOR.setFontSize = RAONKEDITOR.SetFontSize = function(a, d) { if (void 0 != a && "" != a) try { var b = RAONKEDITOR.util._setEditor(d); b && b._FRAMEWIN.command_fontsize(b.ID, b._EDITOR.design, a) } catch (c) { } }; RAONKEDITOR.setFontFormat =
			RAONKEDITOR.SetFontFormat = function(a, d) { if (void 0 != a && "" != a) try { var b = RAONKEDITOR.util._setEditor(d); if (b) { var c = b._FRAMEWIN; switch (a) { case "bold": c.command_bold(b.ID, b); break; case "underline": c.command_underline(b.ID, b); break; case "italic": c.command_italic(b.ID, b); break; case "strike_through": c.command_strikeThrough(b.ID, b); break; case "superscript": c.command_superscript(b.ID, b); break; case "subscript": c.command_subscript(b.ID, b); break; case "remove_format": c.command_removeFormat(b.ID, b) } } } catch (e) { } };
	RAONKEDITOR.setFontColor = RAONKEDITOR.SetFontColor = function(a, d) { if (void 0 != a && "" != a) try { var b = RAONKEDITOR.util._setEditor(d); b && b._FRAMEWIN.command_fontColor(b.ID, b._EDITOR.design, a) } catch (c) { } }; RAONKEDITOR.setFontBgColor = RAONKEDITOR.SetFontBgColor = function(a, d) { if (void 0 != a && "" != a) try { var b = RAONKEDITOR.util._setEditor(d); b && b._FRAMEWIN.command_fontBgColor(b.ID, b._EDITOR.design, a) } catch (c) { } }; RAONKEDITOR.selectAll = RAONKEDITOR.SelectAll = function(a) {
		try {
			var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.command_selectAll(d.ID,
				d)
		} catch (b) { }
	}; RAONKEDITOR.removeCss = RAONKEDITOR.RemoveCss = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.command_removeCss(d.ID, d) } catch (b) { } }; RAONKEDITOR.setOriginalHtmlValue = RAONKEDITOR.SetOriginalHtmlValue = function(a, d) {
		try {
			if (d = RAONKEDITOR.util._getEditorName(d), null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) try {
				var b = RAONKEDITOR.util._setEditor(d); if (b) {
					var c = b._FRAMEWIN; 1 == c.isViewMode(b) && (c.setGlobalTableDefaultValue(), c.FixFlashError2(c._iframeDoc), c._iframeDoc.open("text/html",
						"replace"), c.isCustomDomain(document) && (c._iframeDoc.domain = document.domain), c._iframeDoc.write(a), c._iframeDoc.close(), b._load_editor_frame(!1))
				}
			} catch (e) { } else null == RAONKEDITOR.InitEditorDataHashTable && (RAONKEDITOR.InitEditorDataHashTable = new RAONKEDITOR.util.hashTable), RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "setOriginalHtmlValue", value: a })
		} catch (f) { }
	}; RAONKEDITOR.getDeletedImageUrl = RAONKEDITOR.GetDeletedImageUrl = function(a, d) {
		var b = []; try {
			var c = RAONKEDITOR.util._setEditor(a); if (c) {
				var e =
					c._FRAMEWIN, f = c.setImageArr, h = f.length, k = c.getEditorMode(); "source" != k && "text" != k || c.setChangeMode("design"); e.ReplaceImageToRealObject(); 1 != d && e.changeBodyContenteditable(!1); e.changeBodyImageProperty(!0); var l = c._DOC.body.outerHTML; e.changeBodyImageProperty(!1); e.ReplaceRealObjectToImage(); e.changeBodyContenteditable(!0); for (c = 0; c < h; c++)0 > l.indexOf(f[c]) && b.push(f[c])
			}
		} catch (n) { b = null } return b
	}; RAONKEDITOR.getDeletedElementsUrl = RAONKEDITOR.GetDeletedElementsUrl = function(a, d) {
		var b = []; try {
			var c =
				RAONKEDITOR.util._setEditor(a); if (c) { var e = c._FRAMEWIN, f = c.getEditorMode(); "source" != f && "text" != f || c.setChangeMode("design"); e.ReplaceImageToRealObject(); 1 != d && e.changeBodyContenteditable(!1); e.changeBodyImageProperty(!0); var h = c._DOC.body.outerHTML; e.changeBodyImageProperty(!1); e.ReplaceRealObjectToImage(); e.changeBodyContenteditable(!0); for (var k = c.setElementsArr, l = k.length, c = 0; c < l; c++)0 > h.indexOf(k[c]) && b.push(k[c]) }
		} catch (n) { b = null } return b
	}; RAONKEDITOR.showTopMenu = RAONKEDITOR.ShowTopMenu = function(a,
		d) { try { var b = RAONKEDITOR.util._setEditor(d); if (b) { var c = b._FRAMEWIN; 0 == c.isViewMode(b) && ("0" == a ? (c.document.getElementById("keditor_menu_" + b.ID).style.display = "none", b._config.top_menu = "1") : (c.document.getElementById("keditor_menu_" + b.ID).style.display = "", b._config.top_menu = "0"), c.setEditorIframeTitle(b), c.resizeEditor(b, !1)) } } catch (e) { } }; RAONKEDITOR.showToolbar = RAONKEDITOR.ShowToolbar = function(a, d) {
			try {
				var b = RAONKEDITOR.util._setEditor(d); if (b) {
					var c = b._FRAMEWIN; 0 == c.isViewMode(b) && ("0" == a ? (c.document.getElementById("keditor_toolbar_" +
						b.ID).style.display = "none", b._config.tool_bar = "3") : "1" == a ? (c.document.getElementById("keditor_tab_tool1_" + b.ID).style.display = "", c.document.getElementById("keditor_tab_tool2_" + b.ID).style.display = "none", c.document.getElementById("keditor_toolbar_" + b.ID).style.display = "", b._config.tool_bar = "2", c.displayAgentDocumentIcon(b), "1" == b._config.tool_bar_grouping && (c.groupingIcon(b), c.setPositionGroupingDiv(1))) : "2" == a ? (c.document.getElementById("keditor_tab_tool1_" + b.ID).style.display = "none", c.document.getElementById("keditor_tab_tool2_" +
							b.ID).style.display = "", c.document.getElementById("keditor_toolbar_" + b.ID).style.display = "", b._config.tool_bar = "1", c.displayAgentDocumentIcon(b), "1" == b._config.tool_bar_grouping && (c.groupingIcon(b), c.setPositionGroupingDiv(2))) : (c.document.getElementById("keditor_tab_tool1_" + b.ID).style.display = "", c.document.getElementById("keditor_tab_tool2_" + b.ID).style.display = "", c.document.getElementById("keditor_toolbar_" + b.ID).style.display = "", b._config.tool_bar = "0", c.displayAgentDocumentIcon(b), "1" == b._config.tool_bar_grouping &&
								(c.groupingIcon(b), c.setPositionGroupingDiv(1), c.setPositionGroupingDiv(2))), c.setEditorIframeTitle(b), c.resizeEditor(b, !1))
				}
			} catch (e) { }
		}; RAONKEDITOR.showStatusbar = RAONKEDITOR.ShowStatusbar = function(a, d) {
			try {
				var b = RAONKEDITOR.util._setEditor(d); if (b) {
					var c = b._FRAMEWIN; 0 == c.isViewMode(b) && ("0" == a ? (c.document.getElementById("keditor_statusbar_" + b.ID).style.display = "none", b._config.status_bar = "1") : (c.document.getElementById("keditor_statusbar_" + b.ID).style.display = "", b._config.status_bar = "0"), c.setEditorIframeTitle(b),
						c.resizeEditor(b, !1))
				}
			} catch (e) { }
		}; RAONKEDITOR.setHtmlContents = RAONKEDITOR.SetHtmlContents = function(a, d) { RAONKEDITOR.setHtmlContentsEw(a, d) }; RAONKEDITOR.isLoadedEditor = RAONKEDITOR.IsLoadedEditor = function(a) { var d = null; try { var b = RAONKEDITOR.util._setEditor(a); b && (d = b._config.isLoadedEditor) } catch (c) { d = !1 } return d }; RAONKEDITOR.setHtmlContentsEw = RAONKEDITOR.SetHtmlContentsEw = function(a, d) {
			try {
				var b = RAONKEDITOR.util.parseSetApiParam(a); d = RAONKEDITOR.util._getEditorName(d); if (null != d) if (RAONKEDITOR.isLoadedEditorEx(d)) try {
					if (RAONKEDITOR.util._setEditor(d)) {
						var c =
							b.html.toLowerCase(), e, f, h, k; e = f = h = k = -1; e = c.indexOf("<!doctype"); f = c.indexOf("<html"); h = c.indexOf("<head"); 0 > h && (h = c.indexOf("<meta")); 0 > h && (h = c.indexOf("<title")); k = c.indexOf("<body"); -1 < e && e < f && f < k ? RAONKEDITOR.setHtmlValueExWithDocType(b, d) : -1 < f && f < k ? RAONKEDITOR.setHtmlValueEx(b, d) : -1 < h && h < k ? RAONKEDITOR.setHtmlValue(b, d) : -1 < k ? RAONKEDITOR.setBodyValueEx(b, d) : RAONKEDITOR.setBodyValue(b, d)
					}
				} catch (l) { } else null == RAONKEDITOR.InitEditorDataHashTable && (RAONKEDITOR.InitEditorDataHashTable = new RAONKEDITOR.util.hashTable),
					RAONKEDITOR.InitEditorDataHashTable.setItem(d, { mode: "setHtmlContents", value: b })
			} catch (n) { }
		}; RAONKEDITOR.isLoadedEditorEx = RAONKEDITOR.IsLoadedEditorEx = function(a) {
			var d = !1; try {
				var b = document.getElementById("raonk_frame_" + a); if (b && b.contentWindow.document.getElementById("editorContentArea") && RAONKEDITOR.IsEditorLoadedHashTable) { var c = RAONKEDITOR.IsEditorLoadedHashTable.getItem(a); "undefined" != typeof c && "1" == c && (d = !0) } if (!d && RAONKEDITOR.IsEditorLoadedHashTable) try {
					c = RAONKEDITOR.IsEditorLoadedHashTable.getItem(a),
					"undefined" != typeof c && RAONKEDITOR.IsEditorLoadedHashTable.setItem(a, "0")
				} catch (e) { }
			} catch (f) { } return d
		}; RAONKEDITOR.setHeightForDisplay = RAONKEDITOR.SetHeightForDisplay = function(a) {
			try {
				var d = RAONKEDITOR.util._setEditor(a); d && "view" == d._config.mode && ("1" == d._config.view_mode_auto_height || "1" == d._config.view_mode_auto_width ? RAONKEDITOR.isEmpty() ? RAONKEDITOR.setSize(d._config.style.width, d._config.style.height, d.ID) : RAONKEDITOR.getHtmlContents({
					type: "htmlexwithdoctype", isAuto: !1, callback: function(a) {
						RAONKEDITOR.setHtmlValueExWithDocType(a.strData,
							d.ID)
					}
				}, d.ID) : RAONKEDITOR.setSize(d._config.style.width, d._config.style.height, d.ID))
			} catch (b) { }
		}; RAONKEDITOR.setFocusToObject = RAONKEDITOR.SetFocusToObject = function(a, d) {
			try {
				var b = RAONKEDITOR.util._setEditor(d); if (b) {
					var c = b._FRAMEWIN; b.isInitFocusHandler = !1; var e = null; "string" == typeof a ? e = b._DOC.getElementById(a) : "object" == typeof a && (e = a); if (null != e && void 0 != e) {
						if (RAONKEDITOR.browser.ie && e.firstChild && 3 != e.firstChild.nodeType) for (e = e.firstChild; e;)if (1 == e.nodeType && e.firstChild && 3 != e.firstChild.nodeType) e =
							e.firstChild; else break; e && (!e.tagName || "IMG" != e.tagName && "BR" != e.tagName || (e = e.parentNode), c.setFocusToBody(), setTimeout(function() {
								try {
									if (!e.tagName || "TEXTAREA" != e.tagName && "INPUT" != e.tagName) {
										var a = c._iframeDoc.body.clientHeight, b = c._iframeDoc.body.clientWidth, d = c._iframeDoc.body.scrollTop, f = d + a, p = c._iframeDoc.body.scrollLeft, m = p + b; c._iframeWin.scroll(0, 0); var q = c.getClientRect(e); d > q.top ? (c._iframeWin.scroll(p, q.top), d = c._iframeDoc.body.scrollTop) : f < q.top && (a = 50 - a, c._iframeWin.scroll(p, q.top +
											(0 > a ? a : 0)), d = c._iframeDoc.body.scrollTop); p > q.left ? c._iframeWin.scroll(q.left, d) : m < q.left ? (b = 50 - b, c._iframeWin.scroll(q.left + (0 > b ? b : 0), d)) : c._iframeWin.scroll(p, d); c.doSetCaretPosition(e, !1)
									} else e.focus()
								} catch (r) { }
							}, 1))
					}
				}
			} catch (f) { }
		}; RAONKEDITOR.getEditorSize = RAONKEDITOR.GetEditorSize = function(a) { var d = null; try { var b = RAONKEDITOR.util._setEditor(a); b && (d = { width: b._config.style.width, height: b._config.style.height }) } catch (c) { } return d }; RAONKEDITOR.isDirty = RAONKEDITOR.IsDirty = function(a) {
			try {
				var d =
					RAONKEDITOR.util._setEditor(a); if (d) { if (1 == d._FRAMEWIN.G_IsPossibleDirty) { var b = d._BODY.innerHTML, b = b.replace(/\r?\n?\r?\n/g, ""), b = b.replace(/>\t+</g, "><"), b = b.replace(/> +</g, "><"), b = b.replace(/&nbsp;/g, " "), b = RAONKEDITOR.util.nbspRemove(b), c = d._editingCheckedValue; "" == c && (c = d._FRAMEWIN.G_BODY_DEFAULT_VALUE); c = c.replace(/\r?\n?\r?\n/g, ""); c = c.replace(/>\t+</g, "><"); c = c.replace(/> +</g, "><"); c = c.replace(/&nbsp;/g, " "); c = RAONKEDITOR.util.nbspRemove(c); return b == c ? d._FRAMEWIN.G_IsPossibleDirty = !1 : !0 } return !1 }
			} catch (e) { }
		};
	RAONKEDITOR.setDirty = RAONKEDITOR.SetDirty = function(a, d) {
		try {
			"undefined" != typeof arguments && 1 == arguments.length && (d = a); var b = RAONKEDITOR.util._setEditor(d); b && (b._FRAMEWIN.G_IsPossibleDirty = !0, "undefined" != typeof arguments && 2 <= arguments.length ? ("undefined" === typeof a ? a = b._BODY.innerHTML : -1 < a.indexOf("<body") && (a = a.substring(a.indexOf("<body") + 5), a = a.substring(a.indexOf("<")), a = a.substring(0, a.indexOf("</body>"))), "" == a && (a = b._FRAMEWIN.G_BODY_DEFAULT_VALUE), b._editingCheckedValue = a) : b._editingCheckedValue =
				b._BODY.innerHTML)
		} catch (c) { }
	}; RAONKEDITOR.getAccessibilityValidation = RAONKEDITOR.GetAccessibilityValidation = function(a) { var d = !1; try { var b = RAONKEDITOR.util._setEditor(a); if (b) { var c = b._FRAMEWIN, e = b.getEditorMode(); "source" != e && "text" != e || b.setChangeMode("design"); var f = c.getViolationNodes(b), d = 0 < f.violateNodes.length || 0 < f.idSamNodes.length ? !1 : !0 } } catch (h) { } return d }; RAONKEDITOR.setAccessibilityValidation = RAONKEDITOR.SetAccessibilityValidation = function(a) {
		try {
			var d = RAONKEDITOR.util._setEditor(a);
			if (d) { var b = d._FRAMEWIN, c = d.getEditorMode(); "source" != c && "text" != c || d.setChangeMode("design"); b.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page, d._config.pages.accessibility_validation) }
		} catch (e) { }
	}; RAONKEDITOR.setEditorBodyEditable = RAONKEDITOR.SetEditorBodyEditable = function(a, d) {
		try {
			var b = RAONKEDITOR.util._setEditor(d); b && (a ? (b._BODY.contentEditable = !0, b._config.editorBodyEditable = !0) : (b._BODY.contentEditable = !1, b._config.editorBodyEditable = !1, b._iconEnable("editableFalse"), b.keditor_dragresize &&
				b.keditor_dragresize.resizeHandleClear()))
		} catch (c) { }
	}; RAONKEDITOR.getEditorStyle = RAONKEDITOR.GetEditorStyle = function(a, d, b) { var c = null; try { RAONKEDITOR.util._setEditor(b) && (c = RAONKEDITOR.util.getStyle(a, d)) } catch (e) { } return c }; RAONKEDITOR.setAdjustTableBorderStyle = RAONKEDITOR.SetAdjustTableBorderStyle = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.setAdjustTableBorder(d._DOC) } catch (b) { } }; RAONKEDITOR.getPersonalDataValidation = RAONKEDITOR.GetPersonalDataValidation = function(a) {
		var d =
			!1; try { var b = RAONKEDITOR.util._setEditor(a); if (b) { var c = b._FRAMEWIN, e = b.getEditorMode(); "source" != e && "text" != e || b.setChangeMode("design"); var f = c.getPersonalData(b), h; for (h in f) if (f[h]) { d = !0; break } } } catch (k) { } return d
	}; RAONKEDITOR.setPersonalDataValidation = RAONKEDITOR.SetPersonalDataValidation = function(a) {
		try {
			var d = RAONKEDITOR.util._setEditor(a); if (d) {
				var b = d._FRAMEWIN, c = d.getEditorMode(); "source" != c && "text" != c || d.setChangeMode("design"); b.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page,
					d._config.pages.personal_data)
			}
		} catch (e) { }
	}; RAONKEDITOR.getForbiddenWordValidation = RAONKEDITOR.GetForbiddenWordValidation = function(a) { var d = !1; try { var b = RAONKEDITOR.util._setEditor(a); if (b) { var c = b._FRAMEWIN, e = b.getEditorMode(); "source" != e && "text" != e || b.setChangeMode("design"); d = c.getForbiddenWord(b) ? !0 : !1 } } catch (f) { } return d }; RAONKEDITOR.setForbiddenWordValidation = RAONKEDITOR.SetForbiddenWordValidation = function(a) {
		try {
			var d = RAONKEDITOR.util._setEditor(a); if (d) {
				var b = d._FRAMEWIN, c = d.getEditorMode();
				"source" != c && "text" != c || d.setChangeMode("design"); b.command_forbidden_word(d.ID, d, "API")
			}
		} catch (e) { }
	}; RAONKEDITOR.getCaretElement = RAONKEDITOR.GetCaretElement = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); if (d) { var b, c = d._FRAMEWIN.getFirstRange(), e = c.range; if (e && e.startContainer) if ((b = c.sel.focusNode) && 3 == b.nodeType) for (; 1 != b.nodeType;)if (b.parentNode) b = b.parentNode; else break; else b = getMyElementNode(e.startContainer); return b } } catch (f) { } }; RAONKEDITOR.getCaretElementEx = RAONKEDITOR.GetCaretElementEx =
		function(a) { try { var d = RAONKEDITOR.util._setEditor(a); if (d) { var b = d._FRAMEWIN; return b.G_CURSOR_ELEMENT && "html" != b.G_CURSOR_ELEMENT.nodeName.toLowerCase() && "body" != b.G_CURSOR_ELEMENT.nodeName.toLowerCase() ? b.G_CURSOR_ELEMENT : RAONKEDITOR.getCaretElement(a) } } catch (c) { } }; RAONKEDITOR.setCaretMousePosition = RAONKEDITOR.SetCaretMousePosition = function(a, d) {
			try {
				var b = RAONKEDITOR.util._setEditor(d); if (b) {
					var c = b._FRAMEWIN; if (KEDITORTOP.RAONKEDITOR.browser.ie && 0 == KEDITORTOP.RAONKEDITOR.browser.quirks && 7 != KEDITORTOP.RAONKEDITOR.browser.ieVersion) {
						var e =
							c.getFirstRange().range; e && 0 == e.toString().length && c._iframeDoc.body.createTextRange && (e = c._iframeDoc.body.createTextRange(), e.moveToPoint(a.X, a.Y), e.select()); c.setFocusToBody()
					} else if (KEDITORTOP.RAONKEDITOR.browser.chrome || KEDITORTOP.RAONKEDITOR.browser.opera) { if (0 == c.getFirstRange().range.toString().length) try { c.createSelectionFromPoint(a.X, a.Y, a.X, a.Y), c.setFocusToBody() } catch (f) { } } else c.setFocusToBody(); if ((elem = c.getFirstRange().sel.focusNode) && 3 == elem.nodeType) for (; 1 != elem.nodeType;)if (elem.parentNode) elem =
						elem.parentNode; else break; else (e = c.getFirstRange().range) && e.startContainer == e.endContainer && 1 == e.collapsed && (elem = c.getMyElementNode(e.startContainer)); c.setMenuIconRealable(elem)
				}
			} catch (h) { }
		}; RAONKEDITOR.setLockCommand = RAONKEDITOR.SetLockCommand = function(a, d, b) { try { var c = RAONKEDITOR.util._setEditor(b); if (c) { var e = "," + c.lockCommand.join(",") + ","; d ? 0 > e.indexOf("," + a + ",") && c.lockCommand.push(a) : (e = e.replace("," + a + ",", ","), e = "," == e ? "" : e.substring(1, e.length - 1), c.lockCommand = e.split(",")); c._iconEnable("") } } catch (f) { } };
	RAONKEDITOR.setDomMode = RAONKEDITOR.SetDomMode = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); if (b) { var c = b._FRAMEWIN; if (1 == a) { var e = b.getEditorMode(); "source" != e && "text" != e || b.setChangeMode("design"); c.ReplaceImageToRealObject(); c.ClearDraggingTableAllTable(); c.changeBodyImageProperty(!0) } else b._PageProp.bshowgrid && 1 == b._PageProp.bshowgrid && c.changeBodyImageProperty(!1), c.ReplaceRealObjectToImage() } } catch (f) { } }; RAONKEDITOR.getDocumentDom = RAONKEDITOR.GetDocumentDom = function(a) {
		var d = null;
		try { var b = RAONKEDITOR.util._setEditor(a); b && (d = b._DOC) } catch (c) { } return d
	}; RAONKEDITOR.getDocumentElementDom = RAONKEDITOR.GetDocumentElementDom = function(a) { var d = null; try { var b = RAONKEDITOR.util._setEditor(a); b && (d = b._DOC.documentElement) } catch (c) { } return d }; RAONKEDITOR.getBodyDom = RAONKEDITOR.GetBodyDom = function(a) { var d = null; try { var b = RAONKEDITOR.util._setEditor(a); b && (d = b._BODY) } catch (c) { } return d }; RAONKEDITOR.getValueInTextMode = RAONKEDITOR.GetValueInTextMode = function(a) {
		var d = ""; try {
			var b = RAONKEDITOR.util._setEditor(a);
			b && ("text" != b.getEditorMode() && (RAONKEDITOR.ShowTextChangeAlert = !1, b.setChangeMode("text"), RAONKEDITOR.ShowTextChangeAlert = !0), d = b._EDITOR.text.value)
		} catch (c) { } return d
	}; RAONKEDITOR.setValueInTextMode = RAONKEDITOR.SetValueInTextMode = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); b && ("text" != b.getEditorMode() && (RAONKEDITOR.ShowTextChangeAlert = !1, b.setChangeMode("text"), RAONKEDITOR.ShowTextChangeAlert = !0), b._EDITOR.text.value = a, b._EDITOR.text.focus()) } catch (c) { } }; RAONKEDITOR.convertHtmlToText =
		RAONKEDITOR.ConvertHtmlToText = function(a, d) {
			var b = a; try {
				var c = RAONKEDITOR.util._setEditor(d), e = c._FRAMEWIN, b = e.removeDummyfontTagInTable(b), b = e.htmlTagRevision(b, !0, !1), b = b.replace(/\r/g, ""), b = b.replace(/[\n|\t]/g, ""), b = b.replace(/[\v|\f]/g, ""), b = b.replace(/(\s+)<td/gi, "<td"), b = b.replace(/(\s+)<th/gi, "<th"), b = b.replace(/(\s+)<tr/gi, "<tr"), b = b.replace(/(\s+)?<\/td>(\s+)?/gi, "&nbsp;"), b = b.replace(/(\s+)?<\/th>(\s+)?/gi, "&nbsp;"), b = b.replace(/<p><br><\/p>/gi, "\n"), b = b.replace(/<P>&nbsp;<\/P>/gi, "\n"),
				b = b.replace(/<div><br><\/div>/gi, "\n"), b = b.replace(/<div>&nbsp;<\/div>/gi, "\n"), b = b.replace(/<br(\s)*\/?>/gi, "\n"), b = b.replace(/<br(\s[^\/]*)?>/gi, "\n"), b = b.replace(/<\/p(\s[^\/]*)?>/gi, "\n"), b = b.replace(/<\/div(\s[^\/]*)?>/gi, "\n"), b = b.replace(/(\s+)?<\/tr(\s[^\/]*)?>(\s+)?/gi, "\n"), b = b.replace(/<\/li(\s[^\/]*)?>/gi, "\n"), f = RegExp("(<li(s[^/]*)?>)(.+?)(<ol)", "gi"), b = b.replace(f, "$1$3\n$4"), f = RegExp("(<li(s[^/]*)?>)(.+?)(<ul)", "gi"), b = b.replace(f, "$1$3\n$4"); nIdx = b.lastIndexOf("\n"); -1 < nIdx && "\n" ==
					b.substring(nIdx) && (b = b.substring(0, nIdx)); b = e.removeStripTags(b, null); b = b.replace(/(\n\r|\n|\r)/gm, "\n"); b = b.replace(/(\n \n)/gm, "\n\n"); b = b.replace(/(\n\t\n)/gm, "\n\n"); b = b.replace(/(\n\n\n\n\n\n\n)/gm, "\n\n"); b = b.replace(/(\n\n\n\n\n\n)/gm, "\n\n"); b = b.replace(/(\n\n\n\n\n)/gm, "\n\n"); b = b.replace(/(\n\n\n\n)/gm, "\n\n"); b = b.replace(/(\n\n\n)/gm, "\n\n"); b = RAONKEDITOR.util.trim(b); b = e.addLineBreaker(c._config.enterTag, b); b = b.replace(/&amp;#13;/gi, ""); b = b.replace(/&#13;/gi, "")
			} catch (h) { b = a } return b
		};
	RAONKEDITOR.getDom = RAONKEDITOR.GetDom = function(a) {
		var d = {}, b = !1; try { var c = RAONKEDITOR.util._setEditor(a); if (c) { var e = c._BODY.getElementsByTagName("*"), f = e.length; d.document = c._DOC; d.body = c._BODY; d.all = c._DOC.all; for (a = 0; a < f; a++) { var h = e[a]; if (h.id && 0 < h.id.length) { var k = h.id; if (null == d[k] || void 0 == d[k]) d[k] = h, b = !0 } else if (h.name && 0 < h.name.length) { k = h.name; if (null == d[k] || void 0 == d[k]) d[k] = h; else { if (d[k] && "undefined" == typeof d[k].length) { var l = d[k]; delete d[k]; d[k] = [l] } d[k].push(h) } b = !0 } } } } catch (n) { } 0 ==
			b && (d = null); return d
	}; RAONKEDITOR.$ = function(a, d) {
		var b = null; try {
			var c = RAONKEDITOR.util._setEditor(d); if (c) {
				var e = c._DOC; if (void 0 == a || "" == a || "*" == a) b = e.getElementsByTagName("*"); else {
					c = ""; 0 < a.length && (c = a.substring(0, 1)); var f = a.indexOf("*="); if ("#" == c) return a = a.replace("#", ""), e.getElementById(a); if ("." == c || -1 < f) {
						var h = ""; if ("." == c) h = a.substring(1, a.length); else { var k = a.split("="); 2 == k.length && "classname*" == k[0].toLowerCase() && 0 < k[1].length && (h = k[1]) } h = h.toLowerCase(); if ("" == h) return b; for (var l =
							e.getElementsByTagName("*"), n = l.length, p = 0; p < n; p++) { var m = l[p], q = m.className; void 0 != q && (q = q.toLowerCase()); "." == c && h == q ? (null == b && (b = []), b.push(m)) : -1 < f && h.length <= q.length && h == q.substring(0, h.length) && (null == b && (b = []), b.push(m)) }
					} else if (-1 < a.indexOf(":")) {
						var r = a.split(":"); if (2 == r.length && "form" == r[0].toLowerCase() && 0 < r[1].length) {
							var u = r[1].toLowerCase(), f = ["INPUT", "SELECT", "TEXTAREA", "BUTTON"]; if ("all" == u) for (h = f.length, p = 0; p < h; p++) {
								if (l = e.getElementsByTagName(f[p]), n = l.length, 0 < n) for (null ==
									b && (b = []), k = 0; k < n; k++)b.push(l[k])
							} else { l = e.getElementsByTagName("INPUT"); n = l.length; for (p = 0; p < n; p++)l[p] && void 0 != l[p].type && l[p].type == u && (null == b && (b = []), b.push(l[p])); l = []; if ("button" == u || "select" == u || "textarea" == u) l = e.getElementsByTagName(u.toUpperCase()); n = l.length; for (p = 0; p < n; p++)null == b && (b = []), b.push(l[p]) }
						}
					} else -1 < a.indexOf("=") ? (r = a.split("="), 2 == r.length && "name" == r[0].toLowerCase() && 0 < r[1].length && (b = e.getElementsByName(r[1]))) : (a = a.toUpperCase(), b = e.getElementsByTagName(a))
				}
			}
		} catch (t) {
			b =
			null
		} return b
	}; RAONKEDITOR.getElementById = RAONKEDITOR.GetElementById = function(a, d) { var b = null; try { var c = RAONKEDITOR.util._setEditor(d); c && (b = c._DOC.getElementById(a)) } catch (e) { } return b }; RAONKEDITOR.getElementsByName = RAONKEDITOR.GetElementsByName = function(a, d) { var b = []; try { var c = RAONKEDITOR.util._setEditor(d); c && (b = c._DOC.getElementsByName(a)) } catch (e) { } return b }; RAONKEDITOR.getElementsByTagName = RAONKEDITOR.GetElementsByTagName = function(a, d) {
		var b = []; try {
			var c = RAONKEDITOR.util._setEditor(d); if (c) {
				var e =
					c._DOC; a.toUpperCase(); b = e.getElementsByTagName(a)
			}
		} catch (f) { } return b
	}; RAONKEDITOR.setElementInnerHTML = RAONKEDITOR.SetElementInnerHTML = function(a, d, b) { var c = ""; try { var e = RAONKEDITOR.util._setEditor(b); if (e) { var f = e._DOC.getElementById(a); f && (f.innerHTML = d) } } catch (h) { c = "An error has occurred during the current function operation." } return c }; RAONKEDITOR.setElementInnerText = RAONKEDITOR.SetElementInnerText = function(a, d, b) {
		var c = ""; try {
			var e = RAONKEDITOR.util._setEditor(b); if (e) {
				var f = e._DOC.getElementById(a);
				f && (f.innerText = d)
			}
		} catch (h) { c = "An error has occurred during the current function operation." } return c
	}; RAONKEDITOR.setFormDataTextValue = RAONKEDITOR.SetFormDataTextValue = function(a, d, b) { var c = ""; try { var e = RAONKEDITOR.util._setEditor(b); if (e) { var f = e._DOC.getElementsByName(a); if (0 < f.length) { var h = f[0]; h && (h.value = d) } } } catch (k) { c = "An error has occurred during the current function operation." } return c }; RAONKEDITOR.setCaretBeforeOrAfter = RAONKEDITOR.SetCaretBeforeOrAfter = function(a, d) {
		try {
			if (a) {
				var b =
					a.node ? a.node : null; if (null != b) { var c = a.pos ? a.pos : "before", e = RAONKEDITOR.util._setEditor(d); if (e) { var f = e._FRAMEWIN.getFirstRange(), h = f.range, c = c.charAt(0).toUpperCase() + c.slice(1); h["setStart" + c](b); h["setEnd" + c](b); f.sel.removeAllRanges(); f.sel.addRange(h); e._LastRange = h } }
			}
		} catch (k) { }
	}; RAONKEDITOR.removeNode = RAONKEDITOR.RemoveNode = function(a, d) { try { if (a) { var b = a.node ? a.node : null; null != b && RAONKEDITOR.util._setEditor(d) && a.node.parentNode.removeChild(b) } } catch (c) { } }; RAONKEDITOR.inputTextfield = RAONKEDITOR.InputTextfield =
		function(a) { try { var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page, d._config.pages.input_text) } catch (b) { } }; RAONKEDITOR.inputRadio = RAONKEDITOR.InputRadio = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page, d._config.pages.input_radio) } catch (b) { } }; RAONKEDITOR.inputCheckbox = RAONKEDITOR.InputCheckbox = function(a) {
			try {
				var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page,
					d._config.pages.input_checkbox)
			} catch (b) { }
		}; RAONKEDITOR.inputButton = RAONKEDITOR.InputButton = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page, d._config.pages.input_button) } catch (b) { } }; RAONKEDITOR.inputHiddenfield = RAONKEDITOR.InputHiddenfield = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page, d._config.pages.input_hidden) } catch (b) { } }; RAONKEDITOR.inputTextarea =
			RAONKEDITOR.InputTextarea = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page, d._config.pages.input_textarea) } catch (b) { } }; RAONKEDITOR.inputSelect = RAONKEDITOR.InputSelect = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); d && d._FRAMEWIN.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page, d._config.pages.input_select) } catch (b) { } }; RAONKEDITOR.inputImagebutton = RAONKEDITOR.InputImagebutton = function(a) {
				try {
					var d = RAONKEDITOR.util._setEditor(a);
					d && d._FRAMEWIN.RAONK_EDITOR.prototype.dialog.show(d._config.webPath.page, d._config.pages.input_image)
				} catch (b) { }
			}; RAONKEDITOR.insertInput = RAONKEDITOR.InsertInput = function(a, d, b) {
				try {
					var c = RAONKEDITOR.util._setEditor(b); if (c) {
						var e = c._FRAMEWIN, f = c._DOC; c.isInitFocusHandler = !1; e.restoreSelection(); e.setFocusToBody(); 0 < c.UndoManager.charCount && c.UndoManager.putUndo(); var h = e.getFirstRange().range, k; k = null == d ? f.createElement("input") : d; b = ""; for (var l in a) switch (l.toLowerCase()) {
							case "type": k.type = a[l];
								break; case "name": b = a[l]; break; case "id": k.id = a[l]; break; case "class": k.className = a[l]; break; case "title": k.title = a[l]; break; case "value": k.setAttribute(l, a[l]); break; case "size": "" != a[l] && (k.size = a[l]); break; case "maxlength": "" != a[l] && (k.maxlength = a[l]); break; case "disabled": 0 != a[l] && (k.disabled = !0); break; case "readonly": 0 != a[l] && (k.readonly = !0); break; case "checked": 0 != a[l] && (k.checked = !0, k.setAttribute(l, a[l])); break; case "alt": "" != a[l] && (k.alt = a[l]); break; case "style.width": "" != a[l] && (k.style.width =
									a[l]); break; case "style.height": "" != a[l] && (k.style.height = a[l]); break; case "style.text_align": "" != a[l] && (k.style.textAlign = a[l])
						}if (null == d) {
							h.deleteContents(); if (RAONKEDITOR.browser.ie && 8 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie) "" != b && k.setAttribute("name", b), h.insertNode(k), h.selectNode(k); else { var n = k.outerHTML; "" != b && (n = n.replace(">", ' name="' + b + '" >')); h.pasteHtml(n) } "hidden" == k.type && (G_CURRKEDITOR._editorCustomDataMode = !0, ReplaceRealObjectToImage(!1)); h.collapse(!1); e.rangy.getSelection(e).removeAllRanges();
							e.rangy.getSelection(e).addRange(h)
						} else RAONKEDITOR.browser.ie && 8 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie ? "" != b && k.setAttribute("name", b) : (n = k.outerHTML, "" != b && (n = n.replace(">", ' name="' + b + '" >')), k.outerHTML = n); c.UndoManager.putUndo(); c.UndoManager.charCount = 0
					}
				} catch (p) { }
			}; RAONKEDITOR.insertTextarea = RAONKEDITOR.InsertTextarea = function(a, d, b) {
				try {
					var c = RAONKEDITOR.util._setEditor(b); if (c) {
						var e = c._FRAMEWIN, f = c._DOC; c.isInitFocusHandler = !1; e.restoreSelection(); e.setFocusToBody(); 0 <
							c.UndoManager.charCount && c.UndoManager.putUndo(); var h = e.getFirstRange().range, k; k = null == d ? f.createElement("textarea") : d; var f = b = "", l; for (l in a) switch (l.toLowerCase()) {
								case "name": b = a[l]; break; case "id": k.id = a[l]; break; case "class": k.className = a[l]; break; case "title": k.title = a[l]; break; case "value": k.setAttribute(l, a[l]); f = a[l]; break; case "rows": "" != a[l] && (k.rows = a[l]); break; case "cols": "" != a[l] && (k.cols = a[l]); break; case "disabled": 0 != a[l] && (k.disabled = a[l]); break; case "readonly": 0 != a[l] && (k.readonly =
									a[l]); break; case "style.width": "" != a[l] && (k.style.width = a[l]); break; case "style.height": "" != a[l] && (k.style.height = a[l]); break; case "style.text_align": "" != a[l] && (k.style.textAlign = a[l])
							}k.innerHTML = f; if (null == d) {
								h.deleteContents(); if (RAONKEDITOR.browser.ie && 8 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie) "" != b && k.setAttribute("name", b), h.insertNode(k), h.selectNode(k); else { var n = k.outerHTML; "" != b && (n = n.replace(">", ' name="' + b + '" >')); h.pasteHtml(n) } h.collapse(!1); e.rangy.getSelection(e).removeAllRanges();
								e.rangy.getSelection(e).addRange(h)
							} else RAONKEDITOR.browser.ie && 8 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie ? "" != b && k.setAttribute("name", b) : (n = k.outerHTML, "" != b && (n = n.replace(">", ' name="' + b + '" >')), k.outerHTML = n); c.UndoManager.putUndo(); c.UndoManager.charCount = 0
					}
				} catch (p) { }
			}; RAONKEDITOR.insertSelect = RAONKEDITOR.InsertSelect = function(a, d, b, c, e, f) {
				try {
					var h = RAONKEDITOR.util._setEditor(f); if (h) {
						var k = h._FRAMEWIN, l = h._DOC; h.isInitFocusHandler = !1; k.restoreSelection(); k.setFocusToBody();
						0 < h.UndoManager.charCount && h.UndoManager.putUndo(); var n = k.getFirstRange().range, p; p = null == e ? l.createElement("select") : e; f = ""; for (var m in a) switch (m.toLowerCase()) {
							case "name": f = a[m]; break; case "id": p.id = a[m]; break; case "class": p.className = a[m]; break; case "title": p.title = a[m]; break; case "multiple": p.multiple = a[m]; break; case "size": p.size = a[m]; break; case "disabled": 0 != a[m] && (p.disabled = a[m]); break; case "style.width": "" != a[m] && (p.style.width = a[m]); break; case "style.height": "" != a[m] && (p.style.height =
								a[m])
						}if (null != e) for (var q = e.childNodes.length, r = 0; r < q; r++)e.removeChild(e.childNodes[r]); q = d.length; for (r = 0; r < q; r++) { var u = l.createElement("option"); u.innerHTML = d[r]; u.setAttribute("value", b[r]); "" != c[r] && (u.selected = c[r], u.setAttribute("selected", c[r])); p.appendChild(u) } n.deleteContents(); if (RAONKEDITOR.browser.ie && 8 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie) "" != f && p.setAttribute("name", f), n.insertNode(p), n.selectNode(p); else {
							var t = p.outerHTML; "" != f && (t = t.replace(">", ' name="' + f + '" >'));
							n.pasteHtml(t)
						} n.collapse(!1); k.rangy.getSelection(k).removeAllRanges(); k.rangy.getSelection(k).addRange(n); h.UndoManager.putUndo(); h.UndoManager.charCount = 0
					}
				} catch (g) { }
			}; RAONKEDITOR.insertImg = RAONKEDITOR.InsertImg = function(a, d, b) {
				try {
					var c = RAONKEDITOR.util._setEditor(b); if (c) {
						var e = c._FRAMEWIN, f = c._DOC; c.isInitFocusHandler = !1; e.restoreSelection(); e.setFocusToBody(); 0 < c.UndoManager.charCount && c.UndoManager.putUndo(); var h = e.getFirstRange().range, k; k = null == d ? f.createElement("img") : d; d = ""; for (var l in a) switch (l.toLowerCase()) {
							case "name": d =
								a[l]; break; case "id": k.id = a[l]; break; case "class": k.className = a[l]; break; case "title": k.title = a[l]; break; case "src": k.src = a[l]; break; case "alt": "" != a[l] && (k.alt = a[l]); break; case "style.width": "" != a[l] && (k.style.width = a[l]); break; case "style.height": "" != a[l] && (k.style.height = a[l]); break; case "style.text_align": "" != a[l] && (k.style.textAlign = a[l])
						}h.deleteContents(); if (RAONKEDITOR.browser.ie && 8 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie) "" != d && k.setAttribute("name", d), h.insertNode(k), h.selectNode(k);
						else { var n = k.outerHTML; "" != d && (n = n.replace(">", ' name="' + d + '" >')); h.pasteHtml(n) } h.collapse(!1); e.rangy.getSelection(e).removeAllRanges(); e.rangy.getSelection(e).addRange(h); c.UndoManager.putUndo(); c.UndoManager.charCount = 0
					}
				} catch (p) { }
			}; RAONKEDITOR.insertDiv = RAONKEDITOR.InsertDiv = function(a, d, b) {
				try {
					var c = RAONKEDITOR.util._setEditor(b); if (c) {
						var e = c._FRAMEWIN, f = c._DOC; c.isInitFocusHandler = !1; e.restoreSelection(); e.setFocusToBody(); 0 < c.UndoManager.charCount && c.UndoManager.putUndo(); var h = e.getFirstRange().range,
							k; k = null == d ? f.createElement("div") : d; d = ""; for (var l in a) switch (l.toLowerCase()) { case "name": d = a[l]; break; case "id": k.id = a[l]; break; case "class": k.className = a[l]; break; case "title": k.title = a[l]; break; case "alt": "" != a[l] && (k.alt = a[l]); break; case "style.width": "" != a[l] && (k.style.width = a[l]); break; case "style.height": "" != a[l] && (k.style.height = a[l]); break; case "style.text_align": "" != a[l] && (k.style.textAlign = a[l]) }h.deleteContents(); if (RAONKEDITOR.browser.ie && 8 <= RAONKEDITOR.browser.ieVersion || !RAONKEDITOR.browser.ie) "" !=
								d && k.setAttribute("name", d), h.insertNode(k), h.selectNode(k); else { var n = k.outerHTML; "" != d && (n = n.replace(">", ' name="' + d + '" >')); h.pasteHtml(n) } h.collapse(!1); e.rangy.getSelection(e).removeAllRanges(); e.rangy.getSelection(e).addRange(h); c.UndoManager.putUndo(); c.UndoManager.charCount = 0
					}
				} catch (p) { }
			}; RAONKEDITOR.insertDynamicTable = RAONKEDITOR.InsertDynamicTable = function(a) {
				try {
					var d = RAONKEDITOR.util._setEditor(a); if (d) {
						var b = d._FRAMEWIN; d.isInitFocusHandler = !1; b.restoreSelection(); b.setFocusToBody();
						0 < d.UndoManager.charCount && d.UndoManager.putUndo(); var c = b.getFirstRange().range, e = b.getMyElementNode(c.startContainer); if (e) { var f = b.GetParentbyTagName(e, "table"); f && (f.className = "keditor_DynGird", c.collapse(!1), b.rangy.getSelection(b).removeAllRanges(), b.rangy.getSelection(b).addRange(c), d.UndoManager.putUndo(), d.UndoManager.charCount = 0) }
					}
				} catch (h) { }
			}; RAONKEDITOR.deleteDynamicTable = RAONKEDITOR.DeleteDynamicTable = function(a) {
				try {
					var d = RAONKEDITOR.util._setEditor(a); if (d) {
						var b = d._FRAMEWIN; d.isInitFocusHandler =
							!1; b.restoreSelection(); b.setFocusToBody(); 0 < d.UndoManager.charCount && d.UndoManager.putUndo(); var c = b.getFirstRange().range, e = b.getMyElementNode(c.startContainer); if (e) { var f = b.GetParentbyTagName(e, "table"); f && (f.className = "", c.collapse(!1), b.rangy.getSelection(b).removeAllRanges(), b.rangy.getSelection(b).addRange(c), d.UndoManager.putUndo(), d.UndoManager.charCount = 0) }
					}
				} catch (h) { }
			}; RAONKEDITOR.changeImageData = RAONKEDITOR.ChangeImageData = function(a, d) {
				try {
					var b = RAONKEDITOR.util._setEditor(d); if (b) {
						var c =
							b._FRAMEWIN; b.isInitFocusHandler = !1; c.restoreSelection(); c.setFocusToBody(); 0 < b.UndoManager.charCount && b.UndoManager.putUndo(); var e = c.getFirstRange().range; RAONKEDITOR.G_SELECTED_ELEMENT.src = a.src; RAONKEDITOR.G_SELECTED_ELEMENT.style.width = a.width + "px"; RAONKEDITOR.G_SELECTED_ELEMENT.style.height = a.height + "px"; e.collapse(!1); c.rangy.getSelection(c).removeAllRanges(); c.rangy.getSelection(c).addRange(e); b.UndoManager.putUndo(); b.UndoManager.charCount = 0
					}
				} catch (f) { }
			}; RAONKEDITOR.getSelectedCell = RAONKEDITOR.GetSelectedCell =
				function(a) { try { var d = RAONKEDITOR.util._setEditor(a); if (d) { var b = d._FRAMEWIN, c = b.GetTableSelectionCells(RAONKEDITOR.G_SELECTED_ELEMENT); if (0 == c.length) { b.restoreSelection(); var e = b.getFirstRange().range; if (e) { var f = b.GetTDTHCell(e.startContainer); f && c.push(f) } } return c } } catch (h) { } return null }; RAONKEDITOR.setHorizontalLine = RAONKEDITOR.SetHorizontalLine = function(a, d) {
					try {
						var b = RAONKEDITOR.util._setEditor(d); if (b) {
							var c = b._FRAMEWIN, e = {}, f = "image"; "string" === typeof a ? (f = "image", e.url = a, e.repeat = "1") :
								"object" === typeof a && (e = a, e.repeat = "undefined" === typeof e.repeat ? "1" : e.repeat + "", "string" === typeof e.url ? f = "image" : "undefined" !== typeof e.height && (f = "style", e.height = RAONKEDITOR.util.parseIntOr0(e.height) + "", "undefined" === typeof e.style && (e.style = ""))); if ("image" === f) "" != e.url ? (b._config.horizontalLine.use = "1", b._config.horizontalLine.url = e.url, b._config.horizontalLine.repeat = e.repeat, b._BODY.style.backgroundImage = 'url("' + e.url + '")', b._BODY.style.backgroundRepeat = "repeat" + ("0" == e.repeat ? "-x" : ""),
									b._BODY.style.margin = "0px 10px 10px 10px") : (b._config.horizontalLine.use = "0", b._config.horizontalLine.url = "", b._config.horizontalLine.repeat = "0", b._BODY.style.backgroundImage = "", b._BODY.style.backgroundRepeat = "", b._BODY.style.margin = "10px", c.changeBodyImageProperty(!0)); else if ("style" === f) {
										var h = c.getEditorFrameDoc(b).getElementById("keditor_horizontal_line_" + b.ID); null != h && h.parentNode.removeChild(h); "0" == e.height ? b._config.horizontalLine.use = "0" : (b._config.horizontalLine.use = "2", b._config.horizontalLine.height =
											e.height, b._config.horizontalLine.repeat = e.repeat, b._config.horizontalLine.style = e.style + ""); c.set_horizontal_line_2(b)
									}
						}
					} catch (k) { }
				}; RAONKEDITOR.commands = RAONKEDITOR.Commands = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); b && b._editorCommands(b.ID, a) } catch (c) { } }; RAONKEDITOR.addHtmlToSetValue = RAONKEDITOR.AddHtmlToSetValue = function(a, d, b) { if (void 0 != a && "" != a) try { var c = RAONKEDITOR.util._setEditor(b); c && (c._config.addHtmlToSetValue.html = a, void 0 != d && (c._config.addHtmlToSetValue.preOrSub = KEDITORTOP.RAONKEDITOR.util.parseIntOr0(d))) } catch (e) { } };
	RAONKEDITOR.getFileSize = RAONKEDITOR.GetFileSize = function(a, d, b) {
		if (null != a && void 0 != a && "" != a && ("[object Array]" !== Object.prototype.toString.call(a) || 0 != a.length)) try {
			var c = RAONKEDITOR.util._setEditor(b); if (c) {
				var e = "", f = c._config.handlerUrl, h = RAONKEDITOR.util.getDefaultIframeSrc(), k; try { k = document.createElement('<iframe frameborder="0" >') } catch (l) { k = document.createElement("iframe") } k.id = "download_frame"; k.name = "download_frame"; k.src = h; k.setAttribute("id", "download_frame"); k.setAttribute("name", "download_frame");
				k.setAttribute("src", ""); k.style.display = "none"; k.frameBorder = 0; k.title = "RAON K Editor download"; document.body.appendChild(k); RAONKEDITOR.util.addEvent(k, "load", function() { k.contentWindow.postMessage("check", "*") }); if (window.postMessage) {
					var n = function(a) {
						e = a.data; if (null == e || "" == e) e = ""; RAONKEDITOR.util.removeEvent(window, "message", n); document.body.removeChild(k); e = RAONKEDITOR.util.parseDataFromServer(e); 0 == e.indexOf("[OK]") ? (e = e.replace("[OK]", ""), e = RAONKEDITOR.util.makeDecryptReponseMessage(e)) :
							e = ""; d && d(e)
					}; RAONKEDITOR.util.addEvent(window, "message", n)
				} b = ""; b += "kc" + KEDITORTOP.RAONKSolution.Agent.formfeed + "c07" + KEDITORTOP.RAONKSolution.Agent.vertical; if ("[object Array]" === Object.prototype.toString.call(a)) for (var p = a.length, c = 0; c < p; c++)b += "k30" + KEDITORTOP.RAONKSolution.Agent.formfeed + a[c] + KEDITORTOP.RAONKSolution.Agent.vertical; else b += "k30" + KEDITORTOP.RAONKSolution.Agent.formfeed + a + KEDITORTOP.RAONKSolution.Agent.vertical; b = b.substring(0, b.length - 1); b = KEDITORTOP.RAONKEDITOR.util.makeEncryptParam(b);
				a = []; a.push(["k00", b]); RAONKEDITOR.util.postFormData(document, f, "download_frame", a)
			}
		} catch (m) { }
	}; RAONKEDITOR.destroy = RAONKEDITOR.Destroy = function(a, d) {
		try {
			if (editorTemp = null, "object" == typeof a ? editorTemp = a : (RAONKEDITOR.ShowDestroyAlert = !1, editorTemp = RAONKEDITOR.util._setEditor(a)), editorTemp) {
				var b = editorTemp._FRAMEWIN, c = b.getDialogWindow(), e = b.getDialogDocument(); try {
					if (!RAONKEDITOR.browser.mobile && !b.isViewMode()) {
						var f = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop ||
							0, h = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0, k = document.createElement("input"); k.setAttribute("type", "input"); document.body.appendChild(k); k.focus(); k.parentNode.removeChild(k); var l = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0; h == (window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft || 0) && f == l || scrollTo(h, f)
					}
				} catch (n) { } if (RAONKEDITOR.RAONKMULTIPLEEVENT[editorTemp.ID]) {
					editorEventList = RAONKEDITOR.RAONKMULTIPLEEVENT[editorTemp.ID];
					for (var p = 0, m = editorEventList.length; p < m; p++)try { editorEventList[p].element && (RAONKEDITOR.util.removeEvent(editorEventList[p].element, editorEventList[p].event, editorEventList[p].func), editorEventList[p].func = null, delete editorEventList[p].func) } catch (q) { } try { editorEventList && (editorEventList = null, delete editorEventList, RAONKEDITOR.RAONKMULTIPLEEVENT[editorTemp.ID] = null, delete RAONKEDITOR.RAONKMULTIPLEEVENT[editorTemp.ID]) } catch (r) { }
				} try {
					RAONKEDITOR.browser.ie ? (RAONKEDITOR.util.removeEvent(editorTemp._DOC.body,
						"beforedeactivate", b._iframeDoc_BlurHandler), RAONKEDITOR.util.removeEvent(editorTemp._DOC.body, "focus", b._iframeDoc_FocusHandler), RAONKEDITOR.util.removeEvent(b.document.body, "focus", b._editorframe_Focus)) : (RAONKEDITOR.util.removeEvent(editorTemp._DOC, "blur", b._iframeDoc_BlurHandler), RAONKEDITOR.util.removeEvent(editorTemp._DOC, "focus", b._iframeDoc_FocusHandler), RAONKEDITOR.util.removeEvent(b, "focus", b._editorframe_Focus))
				} catch (u) { } try {
					RAONKEDITOR.util.removeEvent(KEDITORWIN, "resize", editorTemp.toolmenu_bg_resize),
					RAONKEDITOR.util.removeEvent(KEDITORWIN, "resize", b.resizeKeditorWin), RAONKEDITOR.util.removeEvent(KEDITORWIN, "resize", editorTemp.topmenu_bg_resize), RAONKEDITOR.util.removeEvent(c, "resize", editorTemp.context_bg_resize), RAONKEDITOR.util.removeEvent(c, "resize", b.setLayerbgResize)
				} catch (t) { } try { "1" == editorTemp._config.resize_bar && (editorTemp.keditorResize.remove(e), delete editorTemp.keditorResize) } catch (g) { } try {
					"1" == editorTemp._config.dragResize && (editorTemp.keditor_dragresize.remove(editorTemp._DOC),
						delete editorTemp.keditor_dragresize)
				} catch (y) { } RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_context_iframe" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(document.getElementById("keditor_paste_temp_frame")); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_dialog")); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_context_background" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_toolmenu_background" +
					editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_topmenu_background" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_topmenu_iframe" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_formatting_iframe_" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_fontfamily_iframe_" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_fontsize_iframe_" +
						editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_lineheight_iframe_" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_detail_list_number_iframe_" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_detail_list_bullets_iframe_" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_detail_align_group_iframe_" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("keditor_detail_table_group_iframe_" +
							editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(editorTemp._FRAMEWIN.document.getElementById("keditor_design_" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(editorTemp._FRAMEWIN.document.getElementById("keditor_source_" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(editorTemp._FRAMEWIN.document.getElementById("keditor_preview_" + editorTemp.ID)); RAONKEDITOR.util.removeElementWithChildNodes(editorTemp._FRAMEWIN.document.getElementById("keditor_text_" + editorTemp.ID));
				try { editorTemp._FRAMEWIN.RAONK_EDITOR && (editorTemp._FRAMEWIN.RAONK_EDITOR.prototype = null, delete editorTemp._FRAMEWIN.RAONK_EDITOR.prototype) } catch (v) { } try { editorTemp._FRAMEWIN.keditorDragResize && (editorTemp._FRAMEWIN.keditorDragResize.prototype = null, delete editorTemp._FRAMEWIN.keditorDragResize.prototype, editorTemp._FRAMEWIN.keditorDragResize = null, delete editorTemp._FRAMEWIN.keditorDragResize) } catch (A) { } if ("undefined" != typeof editorTemp.UndoManager) try {
					editorTemp.UndoManager.reset(), editorTemp.UndoManager =
						null, delete editorTemp.UndoManager
				} catch (F) { } try { editorTemp._FRAMEWIN.UndoManager && (editorTemp._FRAMEWIN.UndoManager.prototype = null, delete editorTemp._FRAMEWIN.UndoManager.prototype, editorTemp._FRAMEWIN.UndoManager = null, delete editorTemp._FRAMEWIN.UndoManager) } catch (E) { } try { editorTemp._FRAMEWIN.Range && (editorTemp._FRAMEWIN.Range.prototype = null, delete editorTemp._FRAMEWIN.Range.prototype, editorTemp._FRAMEWIN.Range = null, delete editorTemp._FRAMEWIN.Range) } catch (x) { } var J = editorTemp._config.autoDestroy.moveCursor;
				if (RAONKEDITOR.RAONKMULTIPLE["raonk_frame_" + editorTemp.ID]) {
					RAONKEDITOR.IsEditorLoadedHashTable && "undefined" != typeof RAONKEDITOR.IsEditorLoadedHashTable.getItem(editorTemp.ID) && RAONKEDITOR.IsEditorLoadedHashTable.removeItem(editorTemp.ID); for (var p = 0, w = RAONKEDITOR.RAONKMULTIPLETIMEOUT, m = w.length; p < m; p++)if (w[p]) try { window.clearTimeout(w[p]), w[p] = null, delete w[p] } catch (H) { } try { RAONKEDITOR.RAONKMULTIPLETIMEOUT = null, delete RAONKEDITOR.RAONKMULTIPLETIMEOUT } catch (z) { } if (editorTemp._FRAMEWIN.KEDITOR_CONTEXT._config) try {
						delete editorTemp._FRAMEWIN.KEDITOR_CONTEXT._config,
						editorTemp._FRAMEWIN.KEDITOR_CONTEXT = null, delete editorTemp._FRAMEWIN.KEDITOR_CONTEXT
					} catch (G) { } editorTemp._FRAMEWIN._k_editor._config && delete editorTemp._FRAMEWIN._k_editor._config; editorTemp._FRAMEWIN._k_editor.Frame.editor && delete editorTemp._FRAMEWIN._k_editor.Frame.editor; editorTemp._FRAMEWIN._k_editor.Frame && delete editorTemp._FRAMEWIN._k_editor.Frame; editorTemp._FRAMEWIN.RAONK_EDITOR.HTMLParser && delete editorTemp._FRAMEWIN.RAONK_EDITOR.HTMLParser; editorTemp._FRAMEWIN.RAONK_EDITOR.HWPFilter &&
						delete editorTemp._FRAMEWIN.RAONK_EDITOR.HWPFilter; if (editorTemp._FRAMEWIN.RAONK_EDITOR) { try { delete editorTemp._FRAMEWIN.RAONK_EDITOR } catch (C) { editorTemp._FRAMEWIN.RAONK_EDITOR = null } editorTemp._FRAMEWIN.RAONK_EDITOR && (editorTemp._FRAMEWIN.RAONK_EDITOR = null) } if (editorTemp._FRAMEWIN.keditorResizeObj) { try { delete editorTemp._FRAMEWIN.keditorResizeObj } catch (D) { editorTemp._FRAMEWIN.keditorResizeObj = null } editorTemp._FRAMEWIN.keditorResizeObj && (editorTemp._FRAMEWIN.keditorResizeObj = null) } if (editorTemp._FRAMEWIN.resizebar_props.editor) {
							try { delete editorTemp._FRAMEWIN.resizebar_props.editor } catch (K) {
								editorTemp._FRAMEWIN.resizebar_props.editor =
								null
							} try { delete editorTemp._FRAMEWIN.resizebar_props } catch (N) { editorTemp._FRAMEWIN.resizebar_props = null } editorTemp.keditorResize && delete editorTemp.keditorResize
						} if (editorTemp._FRAMEWIN.KEditorResize) { try { delete editorTemp._FRAMEWIN.KEditorResize } catch (B) { editorTemp._FRAMEWIN.KEditorResize = null } editorTemp._FRAMEWIN.KEditorResize && (editorTemp._FRAMEWIN.KEditorResize = null) } if (editorTemp._FRAMEWIN._k_editor) {
							try { delete editorTemp._FRAMEWIN._k_editor } catch (P) { editorTemp._FRAMEWIN._k_editor = null } editorTemp._FRAMEWIN._k_editor &&
								(editorTemp._FRAMEWIN._k_editor = null)
						} var L = editorTemp.ID, Q = editorTemp.FrameID, I; for (I in editorTemp) if (editorTemp.hasOwnProperty(I)) try { editorTemp[I] = null, delete editorTemp[I] } catch (S) { } try { editorTemp._BODY && (editorTemp._BODY = null, delete editorTemp._BODY) } catch (T) { } try { editorTemp._DOC && (editorTemp._DOC = null, delete editorTemp._DOC) } catch (U) { } RAONKEDITOR.util.removeElementWithChildNodes(document.getElementById(Q)); try {
							RAONKEDITOR.util.removeElementWithChildNodes(e.getElementById("raonk_frame_holder" +
								L))
						} catch (V) { } var p = 0, O; for (O in RAONKEDITOR.RAONKHOLDER) if (O == L) break; else p++; RAONKEDITOR.RAONKMULTIPLEID.splice(p, 1); RAONKEDITOR.RAONKMULTIPLE["raonk_frame_" + L] && delete RAONKEDITOR.RAONKMULTIPLE["raonk_frame_" + L]; "undefined" != typeof RAONKEDITOR.RAONKHOLDER[L] && delete RAONKEDITOR.RAONKHOLDER[L]; if (RAONKEDITOR.RAONKMULTIPLEID && 0 < RAONKEDITOR.RAONKMULTIPLEID.length) G_CURRKEDITOR = RAONKEDITOR.RAONKMULTIPLE["raonk_frame_" + RAONKEDITOR.RAONKMULTIPLEID[0]]; else { try { G_CURRKEDITOR = null, delete G_CURRKEDITOR } catch (W) { } try { delete RAONKEDITOR.RAONKMULTIPLEEVENT } catch (X) { } }
				} editorTemp =
					null; delete editorTemp; if ("1" == J) { try { e.body.focus() } catch (Y) { } b = !1; try { for (var M = e.getElementsByTagName("input"), R = M.length, c = 0; c < R; c++)if ("text" == M[c].type) { M[c].focus(); b = !0; break } } catch (Z) { } try { !b && e.getElementsByTagName("textarea")[0] && (e.getElementsByTagName("textarea")[0].focus(), b = !0) } catch (aa) { } try { b || e.getElementsByTagName("select")[0] && e.getElementsByTagName("select")[0].focus() } catch (ba) { } } if (d) {
						KEDITORTOP.RAONKEDITOR.G_COPIEDCELLDATA = null; KEDITORTOP.RAONKEDITOR.IsCrossDomain = null; KEDITORTOP.RAONKEDITOR.G_COPIEDTABLE =
							null; KEDITORTOP.RAONKEDITOR.G_SELECTED_ELEMENT = null; KEDITORTOP.RAONKEDITOR.G_SELECTED_IMAGE_ELEMENT = null; try { delete KEDITORTOP.RAONKEDITOR.G_COPIEDCELLDATA, delete KEDITORTOP.RAONKEDITOR.IsCrossDomain, delete KEDITORTOP.RAONKEDITOR.G_COPIEDTABLE, delete KEDITORTOP.RAONKEDITOR.G_SELECTED_ELEMENT, delete KEDITORTOP.RAONKEDITOR.G_SELECTED_IMAGE_ELEMENT } catch (ca) { } try { KEDITORTOP = KEDITORDOC = KEDITORWIN = null, delete KEDITORTOP, delete KEDITORDOC, delete KEDITORWIN } catch (da) { } for (I in RAONKEDITOR) if (RAONKEDITOR.hasOwnProperty(I)) try {
								RAONKEDITOR[I] =
								null, delete RAONKEDITOR[I]
							} catch (ea) { } try { RAONKEDITOR = null, delete RAONKEDITOR } catch (fa) { } try { RAONKEditor = RAONKEditor_CONFIG = null } catch (ga) { } if ("function" === typeof RAONKEDITOR_CreationComplete) try { RAONKEDITOR_CreationComplete = null, delete RAONKEDITOR_CreationComplete } catch (ha) { } if ("function" === typeof RAONKEDITOR_BeforePaste) try { RAONKEDITOR_BeforePaste = null, delete RAONKEDITOR_BeforePaste } catch (ia) { } if ("function" === typeof RAONKEDITOR_CustomAction) try { RAONKEDITOR_CustomAction = null, delete RAONKEDITOR_CustomAction } catch (ja) { } if ("function" ===
								typeof RAONKEDITOR_SetComplete) try { RAONKEDITOR_SetComplete = null, delete RAONKEDITOR_SetComplete } catch (ka) { } if ("function" === typeof RAONKEDITOR_OnLanguageDefinition) try { RAONKEDITOR_OnLanguageDefinition = null, delete RAONKEDITOR_OnLanguageDefinition } catch (la) { } if ("function" === typeof RAONKEDITOR_AfterChangeMode) try { RAONKEDITOR_AfterChangeMode = null, delete RAONKEDITOR_AfterChangeMode } catch (ma) { } if ("function" === typeof RAONKEDITOR_BeforeInsertUrl) try { RAONKEDITOR_BeforeInsertUrl = null, delete RAONKEDITOR_BeforeInsertUrl } catch (na) { } if ("function" ===
									typeof RAONKEDITOR_OnError) try { RAONKEDITOR_OnError = null, delete RAONKEDITOR_OnError } catch (oa) { } if ("function" === typeof RAONKEDITOR_Resized) try { RAONKEDITOR_Resized = null, delete RAONKEDITOR_Resized } catch (pa) { } if ("function" === typeof RAONKEDITOR_KeyEvent) try { RAONKEDITOR_KeyEvent = null, delete RAONKEDITOR_KeyEvent } catch (qa) { } if ("function" === typeof RAONKEDITOR_MouseEvent) try { RAONKEDITOR_MouseEvent = null, delete RAONKEDITOR_MouseEvent } catch (ra) { } if ("function" === typeof RAONKEDITOR_CommandEvent) try {
										RAONKEDITOR_CommandeEvent =
										null, delete RAONKEDITOR_CommandeEvent
									} catch (sa) { }
					}
			}
		} catch (ta) { }
	}; RAONKEDITOR.getByteLength = RAONKEDITOR.GetByteLength = function(a) { var d = 0; try { var b, c, e; for (b = c = 0; e = a.charCodeAt(c++); b += e >> 11 ? 3 : e >> 7 ? 2 : 1); d = b } catch (f) { } return d }; RAONKEDITOR.getUserRuntimeMode = RAONKEDITOR.GetUserRuntimeMode = function(a) { var d = null; try { var b = RAONKEDITOR.util._setEditor(a); b && (KEDITORTOP.G_CURRKEDITOR = b, d = "1" == KEDITORTOP.G_CURRKEDITOR._config.useKManager ? "agent" : KEDITORTOP.G_CURRKEDITOR._config.runtimes) } catch (c) { } return d };
	RAONKEDITOR.createHyperLink = RAONKEDITOR.CreateHyperLink = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); if (b) { var c = b._FRAMEWIN; a.type = "hyperlink"; a.target || (a.target = ""); a.title || (a.title = ""); c.command_InsertHyperLink(b.ID, b._EDITOR.design, a) } } catch (e) { } }; RAONKEDITOR.setTableEdgeReduce = RAONKEDITOR.SetTableEdgeReduce = function(a, d) {
		try {
			var b = RAONKEDITOR.util._setEditor(d); if (b) {
				var c = b._FRAMEWIN; if (c) for (var e = c._iframeDoc.body.getElementsByTagName("table"), f = e.length, b = 0; b < f; b++) {
					"N" == a ? e[b].style.borderCollapse =
						"collapse" : "Y" == a && (e[b].style.borderCollapse = "separate"); "" == e[b].style.borderTop && (e[b].style.borderTop = "1px solid rgb(0, 0, 0)"); "" == e[b].style.borderLeft && (e[b].style.borderLeft = "1px solid rgb(0, 0, 0)"); "" == e[b].style.borderBottom && (e[b].style.borderBottom = "1px solid rgb(0, 0, 0)"); "" == e[b].style.borderRight && (e[b].style.borderRight = "1px solid rgb(0, 0, 0)"); for (var h = e[b].rows.length, k = c = 0; k < h; k++)for (var c = e[b].rows[k].cells.length, l = 0; l < c; l++)"" == e[b].rows[k].cells[l].style.borderTop && (e[b].rows[k].cells[l].style.borderTop =
							"1px solid rgb(0, 0, 0)"), "" == e[b].rows[k].cells[l].style.borderLeft && (e[b].rows[k].cells[l].style.borderLeft = "1px solid rgb(0, 0, 0)"), "" == e[b].rows[k].cells[l].style.borderBottom && (e[b].rows[k].cells[l].style.borderBottom = "1px solid rgb(0, 0, 0)"), "" == e[b].rows[k].cells[l].style.borderRight && (e[b].rows[k].cells[l].style.borderRight = "1px solid rgb(0, 0, 0)")
				}
			}
		} catch (n) { }
	}; RAONKEDITOR.getCaretObject = RAONKEDITOR.GetCaretObject = function(a) {
		var d = null; try {
			var b = RAONKEDITOR.util._setEditor(a); if (b) {
				KEDITORTOP.G_CURRKEDITOR =
				b; var c = b._FRAMEWIN; c.restoreSelection(); c.setFocusToBody(); var e = c.getFirstRange().range.commonAncestorContainer; if (e) for (d = e; d && 1 !== d.nodeType;)d = d.parentNode
			}
		} catch (f) { } return d
	}; RAONKEDITOR.replaceBlocktoBr = RAONKEDITOR.ReplaceBlocktoBr = function(a) { try { var d = RegExp("<p[^>]*>", "gi"); a = a.replace(d, ""); d = RegExp("</?p>", "gi"); a = a.replace(d, "<br />") } catch (b) { } return a }; RAONKEDITOR.getMetaTag = RAONKEDITOR.GetMetaTag = function(a, d) {
		var b = ""; try {
			if ("" == a) return ""; var c = RAONKEDITOR.getEditorByName(KEDITORTOP.G_CURRKEDITOR.ID);
			if (c) for (var e = c._FRAMEWIN._iframeDoc.getElementsByTagName("META"), c = 0, f = e.length, h; c < f; c++)if (h = e[c], h.name.toLowerCase() == a.toLowerCase()) { b = h.content; break }
		} catch (k) { } return b
	}; RAONKEDITOR.setMetaTag = RAONKEDITOR.SetMetaTag = function(a, d, b) {
		try {
			if ("" != a && "" != d) {
				var c = RAONKEDITOR.getEditorByName(KEDITORTOP.G_CURRKEDITOR.ID); if (c) {
					b = !1; for (var e = c._FRAMEWIN._iframeDoc.getElementsByTagName("META"), f = 0, h = e.length, k; f < h; f++)if (k = e[f], k.name.toLowerCase() == a.toLowerCase()) { k.content = d; b = !0; break } if (0 ==
						b) { var l = c._FRAMEWIN._iframeDoc.createElement("META"); l.name = a; l.content = d; c._FRAMEWIN._iframeDoc.getElementsByTagName("head")[0].appendChild(l) }
				}
			}
		} catch (n) { }
	}; RAONKEDITOR.convertHWPFilter = RAONKEDITOR.ConvertHWPFilter = function(a, d) { var b = a; try { var c = null; try { c = RAONKEDITOR.getEditorByName(KEDITORTOP.G_CURRKEDITOR.ID) } catch (e) { c = RAONKEDITOR.getEditorByName("") } if (c) { var f; "string" == typeof d ? (f = d, d = {}) : "object" == typeof d ? f = d.extraID : d = {}; b = c._FRAMEWIN.RAONK_EDITOR.HWPFilter.HTMLtoXML(a, f, d) } } catch (h) { } return b };
	RAONKEDITOR.convertMMToPxUnit = RAONKEDITOR.ConvertMMToPxUnit = function(a) {
		var d = a; try {
			if (-1 < d.indexOf("mm")) {
				var b = function(a) { var b = a, c = !1; -1 < a.indexOf("mm") && (b = b.replace(/mm/ig, ""), c = !0); c && (b = b / .264583 + "px"); return b }, c = !1, e = !1, d = d.replace(/ width=['\"](\d*(\.\d+)?mm)['\"]/ig, function(a, d) { c = !0; return ' width="' + b(d) + '"' }), d = d.replace(/ height=['\"](\d*(\.\d+)?mm)['\"]/ig, function(a, c) { e = !0; return ' height="' + b(c) + '"' }); 0 == c && (d = d.replace(/ width=(\d*(\.\d+)?mm)/ig, function(a, c) {
					return ' width="' +
						b(c) + '"'
				})); 0 == e && (d = d.replace(/ height=(\d*(\.\d+)?mm)/ig, function(a, c) { return ' height="' + b(c) + '"' }))
			}
		} catch (f) { d = a } return d
	}; RAONKEDITOR.getBodyTextLength = RAONKEDITOR.GetBodyTextLength = function(a) { var d; try { var b = RAONKEDITOR.getEditorByName(a); if (b) { var c = b._FRAMEWIN._iframeDoc.body.innerText, c = c.replace(/\r\n/g, ""); 0 == b._config.wordCount.countwhitespace && (c = c.replace(/ /gim, "")); d = c.length } } catch (e) { } return d }; RAONKEDITOR.getBodyLineCount = RAONKEDITOR.GetBodyLineCount = function(a) {
		var d = 0; try {
			var b =
				RAONKEDITOR.getEditorByName(a); if (b) { var c = b._FRAMEWIN, e = c.getInnerText(c._iframeDoc.body, !0); e.match(/\n/gi) && (d = e.match(/\n/gi).length) }
		} catch (f) { } return d
	}; RAONKEDITOR.AddHttpHeader = function(a, d, b) { try { var c = RAONKEDITOR.util._setEditor(b); c && (RAONKEDITOR.G_CURRKEDITOR = c, RAONKEDITOR.G_CURRKEDITOR._FRAMEWIN && "1" == c._config.useKManager && (c.tempHttpHeaderObj[a] = d)) } catch (e) { } }; RAONKEDITOR.cleanNestedHtml = RAONKEDITOR.CleanNestedHtml = function(a, d, b) {
		try {
			var c = RAONKEDITOR.util._setEditor(b); c && c._FRAMEWIN.RAONK_EDITOR.HTMLParser.CleanNestedHtml(c,
				a, d)
		} catch (e) { }
	}; RAONKEDITOR.cleanNestedHtmlEx = RAONKEDITOR.CleanNestedHtmlEx = function(a, d, b, c) { try { var e = RAONKEDITOR.util._setEditor(c); e && e._FRAMEWIN.RAONK_EDITOR.HTMLParser.CleanNestedHtml(e, a, d, b) } catch (f) { } }; RAONKEDITOR.cleanNestedHtmlForNode = RAONKEDITOR.CleanNestedHtmlForNode = function(a, d, b, c) { var e = ""; try { var f = RAONKEDITOR.util._setEditor(c); if (f) { if ("" == b || 0 == b) b = "p"; e = f._FRAMEWIN.RAONK_EDITOR.HTMLParser.CleanNestedHtml(f, "2", d, b, a) } } catch (h) { e = html } return e }; RAONKEDITOR.cleanNestedTag = RAONKEDITOR.CleanNestedTag =
		function(a, d, b, c, e, f) { try { var h = RAONKEDITOR.util._setEditor(f); if (h) { var k = f = null; if (d && "" != d) if ("string" == typeof d) { var l = document.createElement("div"); l.innerHTML = d; k = l } else "object" == typeof d && 1 == d.nodeType && (k = d); else k = h._DOC; k && (d = "", void 0 != c && "" != c && (d = RAONKEDITOR.util.parseIntOr0(c)), h._FRAMEWIN.RAONK_EDITOR.HTMLParser.CheckNeedNestedHTML(k, d, e) && (d = !0, a && "" != a && (d = confirm(a)), d && (a = "", k != h._DOC && (a = k), f = h._FRAMEWIN.RAONK_EDITOR.HTMLParser.CleanNestedHtml(h, b, c, e, a)))); return f } } catch (n) { } };
	RAONKEDITOR.manuallyTempSave = RAONKEDITOR.ManuallyTempSave = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); d && KEDITORTOP.RAONKEDITOR.GetHtmlContents({ type: "htmlexwithdoctype", isAuto: !0, callback: function(a) { try { d._FRAMEWIN.fn_saveToLocalStorage(d, a) && d._setting.auto_save.toString().split(",")[1] && null != d._setting.auto_save_fn && (KEDITORTOP.KEDITORWIN.clearInterval(d._setting.auto_save_fn), d._setting.auto_save_fn = d._FRAMEWIN.fn_AutoSaveInterval(d)) } catch (b) { } } }, d.ID) } catch (b) { } }; RAONKEDITOR.closeDialogPopup =
		RAONKEDITOR.CloseDialogPopup = function(a) { var d, b; try { if (d = RAONKEDITOR.util._setEditor(a)) { b = d._FRAMEWIN; var c = KEDITORDOC.getElementById("keditor_dialog"); "undefined" != typeof c && b.event_dialog_cancel(c) } } catch (e) { } }; RAONKEDITOR.findWord = RAONKEDITOR.FindWord = function(a, d) {
			var b, c; try {
				if (b = RAONKEDITOR.util._setEditor(d), c = b._FRAMEWIN, "undefined" != typeof a && "undefined" != typeof a.searchText) {
					var e = "undefined" != typeof a.caseSensitive ? a.caseSensitive : !1, f = "undefined" != typeof a.revertEndDocument ? a.revertEndDocument :
						!0, h = "undefined" != typeof a.wordByWord ? a.wordByWord : !1, k = "undefined" != typeof a.searchDirection ? a.searchDirection : !0; if (1 == !!a.initFocus) {
							c.focus(); c.document.body.focus(); var l = c.getFirstRange().range; if (k) l.setStart(c._iframeDoc.body, 0), l.setEnd(c._iframeDoc.body, 0); else { var n; try { n = c._iframeDoc.body.childNodes.length } catch (p) { n = 0 } l.setStart(c._iframeDoc.body, n); l.setEnd(c._iframeDoc.body, n) } var m = c._iframeWin.getSelection ? c._iframeWin.getSelection() : c._iframeDoc.selection; m && l && (l.select ? l.select() :
								m.removeAllRanges && m.addRange && (m.removeAllRanges(), m.addRange(l))); b._LastRange = l; c.g_findRepalceRange = null
						} c.event_keditor_find(a.searchText, e, f, h, k)
				}
			} catch (q) { }
		}; RAONKEDITOR.setReadOnly = RAONKEDITOR.SetReadOnly = function(a, d, b) {
			try {
				var c = RAONKEDITOR.util._setEditor(b); if (c) {
					var e = c._FRAMEWIN; "undefined" != typeof arguments && 3 == arguments.length && "undefined" != typeof d && (d = d.replace(/ /gi, ""), e.G_ICON_READONLY_ACTIVATION_SET = d.split(",")); c._config.editorBodyEditableMode = "2"; a ? (c._iconEnable("default"),
						c._iconEnable("editableFalse"), "" != c._config.placeholder.content && e.placeholderControl(c, "remove")) : (c._iconEnable("default"), "" != c._config.placeholder.content && (e.placeholderControl(c, "set"), e.placeholderControl(c, "class"))); e.changeBodyContenteditable(!a); var f = e.document.getElementById("ue_" + c.ID + "source"); f && (f.style.display = a ? "none" : ""); var h = e.document.getElementById("ue_" + c.ID + "preview"); h && (h.style.display = a ? "none" : ""); var k = e.document.getElementById("ue_" + c.ID + "text"); k && (k.style.display =
							a ? "none" : "")
				}
			} catch (l) { }
		}; RAONKEDITOR.setCustomDisableIconList = RAONKEDITOR.SetCustomDisableIconList = function(a, d) { try { var b = RAONKEDITOR.util._setEditor(d); if (b) { var c = b._FRAMEWIN; "undefined" != typeof a && (a = a.replace(/ /ig, ""), c.G_ICON_CUSTOM_ADD_DISABLED_SET = "" != a ? a.split(",") : [], b._iconEnable(""), b._iconEnable("default")) } } catch (e) { } }; RAONKEDITOR.setZoom = RAONKEDITOR.SetZoom = function(a, d) {
			try {
				0 == !!a && (a = "100%"); var b = RAONKEDITOR.util._setEditor(d); if (b) {
					var c = b._FRAMEWIN, e = c.document.getElementById("ue_" +
						b.ID + "zoom_text"); e && (e.value = a, c.command_zoom(b.ID, c.document.getElementById("keditor_design_" + b.ID)))
				}
			} catch (f) { }
		}; RAONKEDITOR.changeToAgentMode = RAONKEDITOR.ChangeToAgentMode = function(a) { try { var d = RAONKEDITOR.util._setEditor(a); if (d && "0" == d._config.useKManager) { a = d.ID; var b = RAONKEDITOR.UserConfigHashTable.getItem(a); "undefined" != typeof b && (RAONKEDITOR.Destroy(a, !1), b.Runtimes = "agent", new RAONKEditor(b)) } } catch (c) { } }; window.RAONKEDITOR && (RAONKEDITOR.startUpEditor = RAONKEDITOR.StartUpEditor = function(a) {
			try {
				void 0 !=
				a && "" != a || 1 != RAONKEDITOR.RAONKMULTIPLEID.length || (a = RAONKEDITOR.RAONKMULTIPLEID[0]); var d = document.getElementById("raonk_frame_" + a); if ("undefined" != typeof d && -1 < d.getAttribute("src").indexOf("editor_dummy.html")) { var b = d.getAttribute("keditor_customsrc"); d.setAttribute("keditor_customsrc", ""); d.removeAttribute("keditor_customsrc"); d.src = b; d.contentWindow.location.replace(b) }
			} catch (c) { }
		})
};
