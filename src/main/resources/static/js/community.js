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
                        window.open("/toLogin")
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
        let subCommentContainer = $("#comment-"+id)

        if(subCommentContainer.children().length != 1){
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in")
            e.classList.add("active")
        } else {
            $.getJSON( "/comment/"+id, function( data ) {
                $.each( data.data.reverse(), function(index, comment) {
                    let mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }))

                    let mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class":"menu"
                    }).append($("<span/>", {
                        "class":"pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })))

                    let mediaElement = $("<div/>", {
                        class: "media"
                    }).append(mediaLeftElement).append(mediaBodyElement)

                    let commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xm-12 comments"
                    }).append(mediaElement)

                    subCommentContainer.prepend(commentElement)
                });
            });
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in")
            e.classList.add("active")
        }
    }
}

function selectTag(e){
    let value = e.getAttribute("data-tag");
    let previous = $("#tag").val();

    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value)
        } else {
            $("#tag").val(value)
        }
    }
}

function showSelectTag(){
    $("#select-tag").show()
}

function comment_click(e){
    let id = e.getAttribute("data-id");
    $.ajax({
        type: "get",
        url: "/increaseLikeCount/" + id,
        success: function (res){
            if (res.code == -1){
                alert(res.message)
            } else if (res.code == 200){
                $("#likeCount"+id).text(res.data)
            } else {
                alert("服务器出了问题。。。")
            }
        }
    })
}
