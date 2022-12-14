<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
<link rel="stylesheet" href="/Windchill/login/fonts/icomoon/style.css">
<link rel="stylesheet" href="/Windchill/login/css/owl.carousel.min.css">
<link rel="stylesheet" href="/Windchill/login/css/bootstrap.min.css">
<link rel="stylesheet" href="/Windchill/login/css/style.css">

<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript"></script>
<title>FURSYS PLM 로그인 페이지</title>
<script type="text/javascript">
	function go() {
		document.location.href = "/Windchill/platform/main";
	}
</script>
</head>
<body>
</head>
<body>
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<img src="/Windchill/login/images/undraw_remotely_2j6y.svg" alt="Image" class="img-fluid">
				</div>
				<div class="col-md-6 contents">
					<div class="row justify-content-center">
						<div class="col-md-8">
							<div class="mb-4">
								<h3>
									<img src="/Windchill/login/images/logo.png">
								</h3>
									<font color="red">
										<b>아이디 또는 비밀번호가 일치하지 않습니다.</b>
									</font>
							</div>
							<form method="POST" action="j_security_check" id="login">
								<div class="form-group first">
									<label for="username">아이디</label>
									<input type="text" name="j_username" class="form-control" id="username" autofocus="autofocus" value="">
								</div>
								<br>
								<div class="form-group last mb-4">
									<label for="password">비밀번호</label>
									<input type="password" name="j_password" class="form-control" id="password" value="">
								</div>

								<div class="d-flex mb-5 align-items-center">
									<label class="control control--checkbox mb-0">
										<span class="caption">아이디 기억</span>
										<input type="checkbox" id="saveId" name="saveId" />
										<div class="control__indicator"></div>
									</label>
									&nbsp;
									<!-- 									<label class="control control--checkbox mb-0"> -->
									<!-- 										<span class="caption">비밀번호 기억</span> -->
									<!-- 										<input type="checkbox" checked="checked" /> -->
									<!-- 										<div class="control__indicator"></div> -->
									<!-- 									</label> -->
									<span class="ml-auto">
										<a href="#" class="forgot-pass">&nbsp;</a>
									</span>
								</div>

								<input type="submit" value="로 그 인" class="btn btn-block btn-primary" id="_btnLogin">

								<!-- 								<span class="d-block text-left my-4 text-muted">&mdash; or login with &mdash;</span> -->

								<!-- 								<div class="social-login"> -->
								<!-- 									<a href="#" class="facebook"> -->
								<!-- 										<span class="icon-facebook mr-3"></span> -->
								<!-- 									</a> -->
								<!-- 									<a href="#" class="twitter"> -->
								<!-- 										<span class="icon-twitter mr-3"></span> -->
								<!-- 									</a> -->
								<!-- 									<a href="#" class="google"> -->
								<!-- 										<span class="icon-google mr-3"></span> -->
								<!-- 									</a> -->
								<!-- 								</div> -->
							</form>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
	<script src="/Windchill/login/js/jquery-3.3.1.min.js"></script>
	<script src="/Windchill/login/js/popper.min.js"></script>
	<script src="/Windchill/login/js/bootstrap.min.js"></script>
	<script src="/Windchill/login/js/main.js"></script>
<script type="text/javascript">
$(function(){
	$("#_btnLogin").click(function() {
        if($("#username").val() == ""){
            $('#username').focus();
            alert("아이디를 입력해주세요");
            return false;
        }else if($('#password').val()== ""){
            $('#password').focus();
            alert("비밀번호를 입력해주세요");
            return false;
        }
	});
    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
    var userInputId = getCookie("userInputId");
    if(userInputId != "") {
    	$("input[id='username']").val(userInputId);
    	$("input[name='j_password']").focus();
    }
     
    if($("input[id='username']").val() != ""){ 
       // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
        $("#saveId").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
    }
     
    $("#saveId").change(function(){ // 체크박스에 변화가 있다면,
        if($("#saveId").is(":checked")){ // ID 저장하기 체크했을 때,
            var userInputId = $("input[id='username']").val();
            setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
        }else{ // ID 저장하기 체크 해제 시,
            deleteCookie("userInputId");
        }
    });
     
    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $("input[id='username']").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
        if($("#saveId").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            var userInputId = $("input[id='username']").val();
            setCookie("userInputId", userInputId, 30); // 30일 동안 쿠키 보관
        }
    });

 
    //쿠키를 위한 함수 
    function setCookie(cookieName, value, exdays){
        var exdate = new Date();
        exdate.setDate(exdate.getDate() + exdays);
        var cookieValue = escape(value) + ((exdays==null)?"":";expires="+exdate.toGMTString());
        document.cookie = cookieName + "=" + cookieValue;
    }
     
    function deleteCookie(cookieName){
        var expireDate = new Date();
        expireDate.setDate(expireDate.getDate() - 1);
        document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
    }
     
    function getCookie(cookieName) {
        cookieName = cookieName + '=';
        var cookieData = document.cookie;
        var start = cookieData.indexOf(cookieName);
        var cookieValue = '';
        if(start != -1){
            start += cookieName.length;
            var end = cookieData.indexOf(';', start);
            if(end == -1)end = cookieData.length;
            cookieValue = cookieData.substring(start, end);
        }
        return unescape(cookieValue);
    }
});

</script>
</body>
</html>