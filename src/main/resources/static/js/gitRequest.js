const gitRequest = (function () {

    const register = function () {
        $("#btn_register").click(function () {
            debugger;

            // if(isValid() === false) {
            //     return;
            // }

            const formData = new FormData($("#form")[0]);

            // file list 추가
            const fileLists = $("#file")[0].files;
            for (let i = 0; i < fileLists.length; i++) {
                formData.append("fileLists", fileLists[i]);
            }


            $.ajax({
                url: '/register',
                method: 'POST',
                data: formData,
                processData: false,    // FormData 객체는 jQuery가 처리하지 않도록 설정
                contentType: false,    // `multipart/form-data` 헤더는 자동으로 처리되도록 설정
                success: function(_res) {
                    window.location.href = '/';
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                    window.location.href = '/';
                }
            });


        })
    }


    const isValid = function () {

        const title = $("#title").val();
        const businessname = $("#businessname").val();
        const ordercontent = $("#ordercontent").val();
        const datepicker = $("#datepicker").val();
        const ordercompany = $("#ordercompany").val();
        const regName = $("#reg_name").val();

        if(title == '') {
            alert("제목을 입력해주세요.");
            return false;
        }

        if(businessname == '') {
            alert("사업명을 입력해주세요.");
            return false;
        }

        if(ordercontent == '') {
            alert("사업내용을 입력해주세요.");
            return false;
        }

        if(datepicker == '') {
            alert("수주시기를 입력해주세요.");
            return false;
        }

        if(ordercompany == '') {
            alert("수주기업을 입력해주세요.");
            return false;
        }

        if(regName == '') {
            alert("작성자명을 입력해주세요.");
            return false;
        }

        if(regName == '') {
            alert("작성자명을 입력해주세요.");
            return false;
        }
        return true;
    }

    return {
        register : register,
    }
})();

$(document).ready(function () {
    gitRequest.register();
});
