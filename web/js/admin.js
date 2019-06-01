$('#questions-button').click(function (event) {
    $('#appeals, #users').fadeOut(300);
    $('#questions').fadeIn(300);
    event.preventDefault();
});
$('#appeals-button').click(function (event) {
    $('#questions, #users').fadeOut(300);
    $('#appeals').fadeIn(300);
    event.preventDefault();
});
$('#users-button').click(function (event) {
    $('#appeals, #questions').fadeOut(300);
    $('#users').fadeIn(300);
    event.preventDefault();
});

function approveQuestion(isApproved, questionId) {
    var questionType = $('input#type' + questionId).val();
    var body = $('textarea#body' + questionId).val();
    var answer = $('textarea#answer' + questionId).val();
    var source = $('textarea#source' + questionId).val();
    $.ajax({
        method: 'get',
        data: {
            'isApproved': isApproved,
            'questionId': questionId,
            'type': questionType,
            'body': body,
            'answer': answer,
            'source': source
        },
        url: '/WhatWhereWhen/ajaxController?command=approve_question',
        complete: function () {
            $('#sent-question' + questionId).fadeOut(200);
        }
    })
}

function approveAppeal(isApproved, questionId, userId) {
    $.ajax({
        method: 'get',
        data: {
            'isApproved': isApproved,
            'questionId': questionId,
            'userId': userId
        },
        url: '/WhatWhereWhen/ajaxController?command=approve_appeal',
        complete: function () {
            $('#appeal-question' + questionId).fadeOut(200);
        }
    })
}

function removePoints(userId) {
    $.ajax({
        method: 'get',
        data: {
            'userId': userId
        },
        url: '/WhatWhereWhen/ajaxController?command=remove_points',
        success: function (data) {
            $('#user-rating' + userId).html(data);

        }
    })
}