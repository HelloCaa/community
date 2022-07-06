let ok1 = false
let ok2 = false

function login() {
    let id = judgeId();
    let pwd = judgePwd();
    if (ok1 && ok2) {
        let user = {
            "loginId": id,
            "password": hex_md5(pwd)
        }
        $.ajax({
            type: "POST",
            url: "/login",
            contentType: "application/json", //必须这样写
            dataType: "json",
            data: JSON.stringify(user),
            success: function (res) {
                if (res.data){
                    window.location.href = '/'
                }else {
                    alert("账号或密码不正确！")
                }
            }
        })
    }
}

function judgeId() {
    let loginId = document.getElementById('login_id');
    if (loginId.value.length > 0) {
        ok1 = true
        return loginId.value
    }
}

function judgePwd() {
    let pwd = document.getElementById('password');
    if (pwd.value.length > 0) {
        ok2 = true
        return pwd.value
    }
}