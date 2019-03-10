function login() {

	var username = document.getElementById("username");
	var pass = document.getElementById("password");

	if (username.value == "") {

		alert("Please enter your username");

	} else if (pass.value == "") {

		alert("Please enter your password!");

	} else if (username.value == "admin" && pass.value == "123456") {

		window.location.href="personalpage.html";//界面跳转的语句

	} else {

		alert("please enter!")

	}
}

//用来判断用户名和密码是否正确