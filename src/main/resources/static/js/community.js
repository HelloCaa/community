/**
 * 提交回复
 */
function post(){
    let questionId = $("#question_id").val();

    let content = $("#comment_content").val();

    comment2Target(questionId, 1, content)
}

function comment2Target(targetId, type, content) {

    if(!content){
        alert("不能回复空内容～～")
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId":targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if(response.code == 2003){
                    let isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=f974fcc78bfbc3ecff75&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closable", "true");

                    }
                }else {
                    alert(response.message);
                }
            }
            console.log(response)
        },
        dataType: "json"
    });
}

function comment(e) {
    let commentId = e.getAttribute("data-id")
    let content = $("#input-"+commentId).val();
    comment2Target(commentId, 2, content)
}

/**
 * 展开二级评论
 */
function collapseComments(e){
    let id = e.getAttribute("data-id")
    let comments = $("#comment-" + id);

    //获取二级评论的展开状态
    let collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in")
        e.removeAttribute("data-collapse")
        e.classList.remove("active")
    } else {
        $.getJSON( "/comment/"+id, function( data ) {
            let subCommentContainer = $("#comment-"+id)
            $.each( data.data, function(index, comment) {
                console.log(comment)
                let c = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xm-12 comments",
                    html: comment.content
                })
                subCommentContainer.prepend(c)
            });

            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in")
            e.classList.add("active")
        });
    }

}