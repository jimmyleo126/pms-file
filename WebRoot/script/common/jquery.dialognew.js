
$(document).ready(function() {
	$(function($) {
		$(document).keydown(function(e) {
			var currKey = 0, e = e || event;
			currKey = e.keyCode || e.which || e.charCode;
			switch (currKey) {
				case 13: //ENTER
					var btns = window.parent.$(".dialogButton");
					if (btns.length == 0) {
						btns = $(".dialogButton");
					}
					if (window.parent.$("#dvMsgBox").length > 0
							&& window.parent.$("#dvMsgBox").is(":visible")) {
						window.parent.TipInfo.Msg.hide();
					} else if (!$("#editmoddataGrid").length > 0 && btns.length > 0) {
						$(btns[0]).click();
					}
					break;
				case 27: //ESC
					JDialog.close() || window.parent.JDialog.close();
					break;
				case 37: //ALT+ <-
					if (e.altKey) {
						e.preventDefault(); 
					}
					break;
				case 116: //F5
					e.preventDefault(); 
					break;
				default:
					break;
			}
		});
	});
});

var JDialog = {
	// 模态窗口背景色
	"cBackgroundColor" : "#ffffff",
	// 阴影距离(右)
	"cShdowRight" : 5,
	// 阴影距离(下)
	"cShdowDown" : 5,
	// 边框尺寸(像素)
	"cBorderSize" : 2,
	// 边框颜色
	"cBorderColor" : "#999999",
	// Header背景色
	"cHeaderBackgroundColor" : "#f0f0f0",
	// 右上角关闭显示文本
	"cCloseText" : "X 关闭",
	// 鼠标移上去时的提示文字
	"cCloseTitle" : "关闭",
	// 提交按钮文本
	"cSubmitText" : "提 交",
	// 取消按钮文本
	"cCancelText" : "取 消",
	// 关闭按钮文本
	"cCloseText" : "关  闭",
	// 拖拽效果
	"cDragTime" : "100",
	open : function(dialogTitle, iframeSrc, iframeWidth, iframeHeight, fn) {
		JDialog.init(dialogTitle, iframeSrc, iframeWidth, iframeHeight, fn, true, false);
	},
	openTo : function(dialogTitle, iframeSrc, iframeWidth, iframeHeight) {
		JDialog.init(dialogTitle, iframeSrc, iframeWidth, iframeHeight, false, true, false);
	},
	openWithNoTitle : function(dialogTitle, iframeSrc, iframeWidth, iframeHeight) {
		JDialog.init(dialogTitle, iframeSrc, iframeWidth, iframeHeight, false, true, true);
	},
	init : function(dialogTitle, iframeSrc, iframeWidth, iframeHeight, fn, isDrag, notitle) {
		alert(1);
		console.log(1);
		var _px_shadow = 5;
		var _px_top = 30;
		var _px_bottom = 50;
		if (!fn) {
			_px_bottom = 0;
		}
		var _client_width = document.body.clientWidth - 1;
		var _client_height = document.documentElement.scrollHeight - 1;
		if ($("#jd_shadow").length == 0) {
			$("body").prepend("<div id='jd_shadow'>&nbsp;</div>");
			var _jd_shadow = $("#jd_shadow");
			_jd_shadow.css("width", _client_width + "px");
			_jd_shadow.css("height", _client_height + "px");
		}
		if ($("#jd_dialog").length > 0) {
			$("#jd_dialog").remove();
		}
		$("body").prepend("<div id='jd_dialog' class='ui-jqdialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix'></div>");
		var _jd_dialog = $("#jd_dialog");
		var _left = (_client_width - (iframeWidth + JDialog.cBorderSize * 2 + JDialog.cShdowRight)) / 2;
		_jd_dialog.css("left", (_left < 0 ? 0 : _left) + document.documentElement.scrollLeft + "px");
		var _top = (document.documentElement.clientHeight - (iframeHeight
				+ JDialog.cBorderSize * 2 + _px_top + _px_bottom + JDialog.cShdowDown)) / 2;
		_jd_dialog.css("top", (_top < 0 ? 0 : _top) + document.documentElement.scrollTop + "px");
		_jd_dialog.append("<div id='jd_dialog_m'></div>");
		var _jd_dialog_m = $("#jd_dialog_m");
		_jd_dialog_m.css("width", iframeWidth + "px");	
		_jd_dialog_m.css("border-radius", "2px");
		_jd_dialog_m.css("background-color", JDialog.cBackgroundColor);
		if(!notitle) _jd_dialog_m.append("<div id='jd_dialog_m_h' style='font-size:16px;'></div>");
		var _jd_dialog_m_h = $("#jd_dialog_m_h");
		_jd_dialog_m_h.css("background-color","#4A5064");
		_jd_dialog_m_h.css("border-top-left-radius", "3px");
		_jd_dialog_m_h.css("border-top-right-radius", "3px");
		_jd_dialog_m_h.css("width", "100%");
		_jd_dialog_m_h.css("height", "30px");
		_jd_dialog_m_h.css("line-height", "30px");
		_jd_dialog_m_h.css("padding", ".5em 0");
		debugger;
		_jd_dialog_m_h.append("<span id='jd_dialog_m_h_l'>" + dialogTitle + "</span>");
		_jd_dialog_m_h.append("<span style='display:block;color:#333;'>ii</span>");
		//_jd_dialog_m_h.append("<a class='ui-jqdialog-titlebar-close ui-corner-all' style='float:right;'>"
		//		+ "<span class='ui-icon ui-icon-closethick dialog-close' onclick='JDialog.close();'></span></a>");
		_jd_dialog_m.append("<div id='jd_dialog_m_b'></div>");
		var _jd_dialog_m_b = $("#jd_dialog_m_b");
		_jd_dialog_m_b.css("width", iframeWidth + "px");
		_jd_dialog_m_b.css("height", iframeHeight + "px");
		_jd_dialog_m_b.append("<div id='jd_dialog_m_b_1'>&nbsp;</div>");
		var _jd_dialog_m_b_1 = $("#jd_dialog_m_b_1");
		_jd_dialog_m_b_1.css("top", "30px");
		_jd_dialog_m_b_1.css("font-family", "微软雅黑");
		_jd_dialog_m_b_1.css("width", iframeWidth + "px");
		_jd_dialog_m_b_1.css("height", iframeHeight + "px");
		_jd_dialog_m_b_1.css("display", "none");
		_jd_dialog_m_b.append("<div id='jd_dialog_m_b_2'></div>");
		$("#jd_dialog_m_b_2").append("<iframe id='jd_iframe' src='" + iframeSrc
						+ "' scrolling='no' frameborder='0' width='"
						+ iframeWidth + "' height='" + iframeHeight + "' />");
		if (fn) {
			_jd_dialog_m.append("<div id='jd_dialog_m_t' style='background-color:"
							+ JDialog.cBottomBackgroundColor + ";'><ul id='btns'></ul></div>");
			var btns = $("#btns");
			if (typeof fn == "string") {
				btns.append("<li><div id='sData' style='background-color:#4A5064;color:#fff;margin-top:7px;' class='dialogButton'" +
						" onclick='JDialog.ok(\"" + fn + "\");'>" + JDialog.cSubmitText + "</div></li>");
				btns.append("<li><div id='cData' class='dialogButton' onclick='JDialog.close();'>"
						+ JDialog.cCancelText + "</div></li>");
			} else {
				var width = (iframeWidth / 2 + 100) + "px";
				$("#jd_dialog_m_t").css({ "width": width });
				btns.append("<li><div id='cData' class='dialogButton' onclick='JDialog.close();'>"
						+ JDialog.cCloseText + "</div></li>");
			}
		}	
		if (isDrag) {
			DragAndDrop.Register(_jd_dialog[0], _jd_dialog_m_h[0]);
		}
		$("#jd_iframe")[0].focus();
	},
	close : function() {
		if ($("#jd_shadow").length > 0) {
			$("#jd_shadow").remove();
		}
		if ($("#jd_dialog").length > 0) {
			$("#jd_iframe")[0].src = "";
			$("#jd_dialog").remove();
		}
	},
	ok : function(fn) {
		var func = window.frames["jd_iframe"].contentWindow[fn] || eval(fn);
		func();
	},
	submitCompleted : function(alertMsg, isCloseDialog, isRefreshPage) {
		if ($.trim(alertMsg).length > 0) {
			alert(alertMsg);
		}
		if (isCloseDialog) {
			JDialog.close();
		}
		if (isRefreshPage) {
			window.location.href = window.location.href;
		}
	}
};

var DragAndDrop = function() {
	var _clientWidth;
	var _clientHeight;
	var _controlObj;
	var _dragObj;
	var _flag = false;
	var _dragObjCurrentLocation;
	var _mouseLastLocation;
	var getElementDocument = function(element) {
		return element.ownerDocument || element.document;
	};
	var dragMouseDownHandler = function(evt) {
		if (_dragObj) {
			evt = evt || window.event;
			_clientWidth = document.body.clientWidth;
			_clientHeight = document.documentElement.scrollHeight;
			$("#jd_dialog_m_b_1").css("display", "");
			_flag = true;
			_dragObjCurrentLocation = {
				x : $(_dragObj).offset().left,
				y : $(_dragObj).offset().top
			};
			_mouseLastLocation = {
				x : evt.screenX,
				y : evt.screenY
			};
			$(document).bind("mousemove", dragMouseMoveHandler);
			$(document).bind("mouseup", dragMouseUpHandler);
			if (evt.preventDefault) {
				evt.preventDefault();
			} else {
				evt.returnValue = false;
			}
		}
	};
	var dragMouseMoveHandler = function(evt) {
		if (_flag) {
			evt = evt || window.event;
			var _mouseCurrentLocation = {
				x : evt.screenX,
				y : evt.screenY
			};
			_dragObjCurrentLocation.x = _dragObjCurrentLocation.x
					+ (_mouseCurrentLocation.x - _mouseLastLocation.x);
			_dragObjCurrentLocation.y = _dragObjCurrentLocation.y
					+ (_mouseCurrentLocation.y - _mouseLastLocation.y);
			_mouseLastLocation = _mouseCurrentLocation;
			$(_dragObj).css("left", _dragObjCurrentLocation.x + "px");
			$(_dragObj).css("top", _dragObjCurrentLocation.y + "px");
			if (evt.preventDefault) {
				evt.preventDefault();
			} else {
				evt.returnValue = false;
			}
		}
	};
	var dragMouseUpHandler = function(evt) {
		if (_flag) {
			evt = evt || window.event;
			$("#jd_dialog_m_b_1").css("display", "none");
			cleanMouseHandlers();
			_flag = false;
		}
	};
	var cleanMouseHandlers = function() {
		if (_controlObj) {
			$(_controlObj.document).unbind("mousemove");
			$(_controlObj.document).unbind("mouseup");
		}
	};
	return {
		Register : function(dragObj, controlObj) {
			_dragObj = dragObj;
			_controlObj = controlObj;
			$(_controlObj).bind("mousedown", dragMouseDownHandler);
		}
	};
}();
