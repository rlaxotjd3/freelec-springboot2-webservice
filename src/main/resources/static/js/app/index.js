var main = {
    init: function() {
        var _this = this;
        $('#btn-update').on('click', function() { // btn-update란 id를 가진 HTML 엘리먼트에 click 이벤트가 발생할 때 update
            //function을 실행하도록 이벤트를 등록합니다.
            _this.update();
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
    },
    update: function() {
        var data = {
            title : $('#title').val(),
            content: $('#content').val()
        };
        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            /*
            * 여러 HTTP Method 중 PUT 메소드를 선택합니다.
            * PostsApiController에 있는 API에서 이미 @PutMapping으로 선언했기 때문에 PUT을 사용해야 합니다. 참고로 이는 REST
            * 규약에 맞게 설정된 것입니다.
            * REST에서 CRUD는 다음과 같이 HTTP Method에 매핑됩니다.
            * 생성(Create) - POST
            * 읽기(Read) - GET
            * 수정(Update) - PUT
            * 삭제(Delete) - DELETE*/
            url: '/api/v1/posts/'+id,
            //어느 게시글을 수정할지 URL Path로 구분하기 위해 Path에 id를 추가합니다.
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};
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
main.init();
//index.js의 첫 문장에 var index={}라는 코드를 선언했습니다. 굳이 index라는 변수의 속성으로 function을 추가한 이유는 뭘까요? 
//브라우저의 스코프는 공용 공간으로 쓰이기 때문에 나중에 로딩된 js의 init, save가 있을 시 먼저 로딩된 js의 scope를 덮어쓰게 됩니다.
/*
여러 사람이 참여하는 프로젝트에서는 중복된 함수 이름은 자주 발생할 수 있습니다. 모든 function 이름을 확인하면서 만들 수는 없습니다.
그러다보니 이런 문제를 피하려고 index.js만의 유효범위를 만들어 사용합니다

방법은 var index이란 객체를 만들어 해당 객체에서 필요한 모든 function을 선언하는 것입니다. 이렇게 하면 index 객체 안에서만 function 이
유효하기 때문에 다른 JS와 겹칠 위험이 사라집니다. ㄴ
 */
