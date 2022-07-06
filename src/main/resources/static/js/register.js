let ok1 = false
let ok2 = false
let ok3 = false
function info(e){
    let info = $("#info");
    let state = e.getAttribute("state");
    if (state){
        info.addClass("hide")
        e.removeAttribute("state")
    } else {
        info.removeClass("hide")
        e.setAttribute("state", "in")
    }
}

function validate(){
    if (ok1 && ok2 && ok3){
        let passWord = $("#passWord2").val();
        $.ajax({
            type: "POST",
            url: "/register",
            contentType: "application/json",
            data: JSON.stringify({
                "userName":$("#userName").val(),
                "loginId": $("#login_id").val(),
                "password": hex_md5($("#passWord2").val())
            }),
            success: function (res) {
                if (res.code == 200){
                    alert("注册成功")
                    window.location.href = '/toLogin'
                } else {
                    alert("服务器好像出了点小问题。。。")
                }
            }
        });
    }
}

function judgeUserName(){
    let userName = $("#userName");
    let length = userName.val().length;
    let p1 = document.getElementById("p1");
    if (length > 0 && length <= 10){
        // 提交服务器去判断
        $.ajax({
            type: "get",
            url: "/judgeUserName/" + userName.val(),
            success: function (res){
                if (res.code == 200){
                    p1.innerText="恭喜，用户名可用"
                    ok1 = true
                } else if (res.code == 300) {
                    p1.innerText="用户名被占用了"
                    ok1 = false
                } else if (res.code == 400){
                    p1.innerText="用户名不合法"
                    ok1 = false
                }
            }
        })

    }else if (length <= 0) {
        p1.innerText="用户名不能为空"
        ok1 = false
    }else if (length > 10){
        p1.innerText="用户名太长了"
        ok1 = false
    }
}

function judgeId(){
    judgeUserName()
    let loginId = $("#login_id")
    let p2 = document.getElementById("p2")
    let length = loginId.val().length;
    let reg = /^[1-9][0-9]+$/g
    if (length != 9){
        p2.innerText = "格式错误"
        ok2 = false
    }
    if (reg.test(loginId.val())){
        // 提交服务器判断
        // judgeLoginId
        $.ajax({
            type: "get",
            url: "/judgeLoginId/" + loginId.val(),
            success: function (res){
                if (res.code == 200){
                    p2.innerText="恭喜，id可用"
                    ok2 = true
                } else if (res.code == 300) {
                    p2.innerText="id被占用了"
                    ok2 = false
                } else if (res.code == 400){
                    p2.innerText="id不合法"
                    ok2 = false
                }
            }
        })

    }else {
        p2.innerText = "格式错误"
        ok2 = false
    }
}

function judgePW(){
    judgeUserName()
    judgeId()
    let pw1 = $("#passWord1");
    let pw2 = $("#passWord2");
    let length = pw2.val().length;
    let p3 = document.getElementById("p3");
    if (length <= 0 || length > 16 || pw1.val() != pw2.val()){
        p3.innerText = "格式错误"
    }else{
        p3.innerText = ""
        ok3 = true
    }
}


