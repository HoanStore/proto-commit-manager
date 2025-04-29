const gitRequest = (function () {

    // 폼을 자동으로 채우는 함수
    const fillForm = function (testCase) {
        $("#name").val(testCase.name);
        $("#email").val(testCase.email);
        $("#password").val(testCase.password);
        $("#url").val(testCase.url);
        $("#file").val(''); // 파일은 자동으로 채울 수 없습니다.
        $("#message").val(testCase.message);
        $("#branch").val(testCase.branch);
    }

    // 버튼 클릭 시 자동으로 폼 채우기
    const fillTestData = function () {
        $("#btn_fill_test1").click(function () {
            const testCase1 = {
                name: 'HoanStore',
                email: 'kwjo@mqnic.com',
                password: 'password123',
                url: 'https://github.com/HoanStore/proto-commit-manager.git',
                message: 'Initial commit',
                branch: 'main'
            };
            fillForm(testCase1);
        });

        $("#btn_fill_test2").click(function () {
            const testCase3 = {
                name: 'Hoan',
                email: 'ktm1296@naver.com',
                password: '1234',
                url: 'https://github.com/kostamember/commit-manager-test-repo.git',
                message: 'TEST',
                branch: 'main'
            };
            fillForm(testCase3);
        });
    }

    // 제출 기능 (폼 제출)
    const register = function () {
        $("#btn_register").click(function () {

            if(isValid() === false) {
                return;
            }

            const formData = new FormData($("#form")[0]);

            // 파일 목록 추가
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
        });
    }

    // 유효성 검사
    const isValid = function () {
        const title = $("#title").val();
        const businessname = $("#businessname").val();
        const ordercontent = $("#ordercontent").val();
        const datepicker = $("#datepicker").val();
        const ordercompany = $("#ordercompany").val();
        const regName = $("#reg_name").val();

        if (title === '') {
            alert("제목을 입력해주세요.");
            return false;
        }
        if (businessname === '') {
            alert("사업명을 입력해주세요.");
            return false;
        }
        if (ordercontent === '') {
            alert("사업내용을 입력해주세요.");
            return false;
        }
        if (datepicker === '') {
            alert("수주시기를 입력해주세요.");
            return false;
        }
        if (ordercompany === '') {
            alert("수주기업을 입력해주세요.");
            return false;
        }
        if (regName === '') {
            alert("작성자명을 입력해주세요.");
            return false;
        }
        return true;
    }

    return {
        register: register,
        fillTestData: fillTestData // fillTestData 함수도 외부로 노출
    }

})();

$(document).ready(function () {
    gitRequest.register();
    gitRequest.fillTestData(); // 자동 채우기 버튼 기능 추가
});
