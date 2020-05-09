var index= {
    init: function() {
        var _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        });
    },
    save : function() {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다');
            window.location.href='/';
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }

};
index.init();
//index.js의 첫 문장에 var index={}라는 코드를 선언했습니다. 굳이 index라는 변수의 속성으로 function을 추가한 이유는 뭘까요? 
//브라우저의 스코프는 공용 공간으로 쓰이기 때문에 나중에 로딩된 js의 init, save가 있을 시 먼저 로딩된 js의 scope를 덮어쓰게 됩니다.
/*
여러 사람이 참여하는 프로젝트에서는 중복된 함수 이름은 자주 발생할 수 있습니다. 모든 function 이름을 확인하면서 만들 수는 없습니다.
그러다보니 이런 문제를 피하려고 index.js만의 유효범위를 만들어 사용합니다

방법은 var index이란 객체를 만들어 해당 객체에서 필요한 모든 function을 선언하는 것입니다. 이렇게 하면 index 객체 안에서만 function 이
유효하기 때문에 다른 JS와 겹칠 위험이 사라집니다. ㄴ
 */
