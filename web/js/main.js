/*
	Future Imperfect by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
*/

(function ($) {

    skel.breakpoints({
        xlarge: '(max-width: 1680px)',
        large: '(max-width: 1280px)',
        medium: '(max-width: 980px)',
        small: '(max-width: 736px)',
        xsmall: '(max-width: 480px)'
    });

    $(function () {

        var $window = $(window),
            $body = $('body'),
            $menu = $('#menu'),
            $sidebar = $('#sidebar'),
            $main = $('#main');

        // Disable animations/transitions until the page has loaded.
        $body.addClass('is-loading');

        $window.on('load', function () {
            window.setTimeout(function () {
                $body.removeClass('is-loading');
            }, 100);
        });

        // Prioritize "important" elements on medium.
        skel.on('+medium -medium', function () {
            $.prioritize(
                '.important\\28 medium\\29',
                skel.breakpoint('medium').active
            );
        });

        // IE<=9: Reverse order of main and sidebar.
        if (skel.vars.IEVersion <= 9)
            $main.insertAfter($sidebar);

        // Menu.
        $menu
            .appendTo($body)
            .panel({
                delay: 500,
                hideOnClick: true,
                hideOnSwipe: true,
                resetScroll: true,
                resetForms: true,
                side: 'right',
                target: $body,
                visibleClass: 'is-menu-visible'
            });

        // Search (header).
        var $search = $('#search'),
            $search_input = $search.find('input');

        $body
            .on('click', '[href="#search"]', function (event) {

                event.preventDefault();

                // Not visible?
                if (!$search.hasClass('visible')) {

                    // Reset form.
                    $search[0].reset();

                    // Show.
                    $search.addClass('visible');

                    // Focus input.
                    $search_input.focus();

                }

            });

        $search_input
            .on('keydown', function (event) {

                if (event.keyCode == 27)
                    $search_input.blur();

            })
            .on('blur', function () {
                window.setTimeout(function () {
                    $search.removeClass('visible');
                }, 100);
            });

        // Intro.
        var $intro = $('#intro');

        // Move to main on <=large, back to sidebar on >large.
        skel
            .on('+large', function () {
                $intro.prependTo($main);
            })
            .on('-large', function () {
                $intro.prependTo($sidebar);
            });

    });

})(jQuery);


function toArticles() {
    $('#one-article').fadeOut(200);
    $('#articles-list').delay(200).fadeIn(200);
}

function showArticle(articleId) {
    $.ajax({
        method: 'get',
        data: {'articleId': articleId},
        url: '/WhatWhereWhen/ajaxController?command=show_article',
        success: function (data) {
            $('#articles-list').fadeOut(200);
            $('#browse-header').html(data.header);
            $('#browse-theme').html(data.theme);
            $('#browse-body').html(data.body);
            $('#browse-date').html(data.date);
            if (data.authorId != null) {
                $('#browse-author').attr("src", "/WhatWhereWhen/image?command=display&authorId=" + data.authorId);
            }
            if (data.photo != null) {
                $('#browse-photo').attr("src", "/WhatWhereWhen/images/" + data.photo);
            }
            $('#one-article').delay(200).fadeIn(200);
        }
    })
}

function checkAnswer(questionId) {
    var answer = $('#answer' + questionId).text();
    var userAnswer = $('textarea#user-answer' + questionId).val();
    var answerResult = (answer.replace(/\s+/g, '').toUpperCase() === userAnswer.replace(/\s+/g, '').toUpperCase());
    $.ajax({
        method: 'get',
        data: {
            'questionId': questionId,
            'userAnswer': userAnswer,
            'answerResult': answerResult
        },
        url: '/WhatWhereWhen/ajaxController?command=commit_answer',
        success: function (status) {
            $('#user-answer' + questionId).fadeOut(200, function () {
                $(this).remove();
            });
            $('#answer-button' + questionId).fadeOut(200, function () {
                $(this).remove();
            });
            if (status === 200) {
                if (answerResult) {
                    $('#question-result' + questionId).html("Правильный ответ! ");
                } else {
                    $('#question-result' + questionId).html("Неправильный ответ! ");
                    $('#appeal-button' + questionId).delay(200).fadeIn(200);
                }
            } else {
                $('#question-result' + questionId).html("Произошла ошибка... ");
            }
            $('#answer' + questionId).delay(200).fadeIn(200);
        }
    })
}

$('#language-select').change(function () {
    var optionSelected = $(this).find("option:selected");
    $.ajax({
        method: 'post',
        data: {'locale': optionSelected.val()},
        url: '/WhatWhereWhen/ajaxController?command=change_locale',
        complete: function () {
            window.location.reload();
        }
    })
});

function fileAppeal(questionId) {
    $.ajax({
        method: 'get',
        data: {
            'questionId': questionId
        },
        url: '/WhatWhereWhen/ajaxController?command=file_appeal',
        success: function (status) {
            if (status === 200) {
                $('#question-result' + questionId).html("Аппеляция принята... ");
                $('#appeal-button' + questionId).fadeOut(200, function () {
                    $(this).remove();
                });
            } else {
                $('#question-result' + questionId).html("Произошла ошибка... ");
            }
        }
    })
}

