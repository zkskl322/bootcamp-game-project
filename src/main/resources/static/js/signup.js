document.addEventListener('DOMContentLoaded', function() {
    // Modal 관련 코드
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    const openModalBtn = document.getElementById('openModalBtn');
    const termsModal = document.getElementById('termsModal');
    const university = document.getElementById('university');
    const email = document.getElementById('email');
    const emailDomain = document.getElementById('emailDomain');
    const studentId = document.getElementById('studentId');
    const major = document.getElementById('major');
    const password = document.getElementById('password');
    const role = document.getElementById('role');
    const agreeCheckbox = document.getElementById('agreeCheckbox');
    const signupButton = document.getElementById('signupButton');    
    const sendEmailButton = document.getElementById('sendEmailButton');
    const emailVerifyButton = document.getElementById('emailVerifyButton');
    const emailVerify = document.getElementById('emailVerify');

    

    openModalBtn.addEventListener('click', () => {
        termsModal.style.display = 'block';
    });
    termsModal.addEventListener('click', () => {
        termsModal.style.display = 'none';
    });

    agreeCheckbox.addEventListener('change', () => {
        if (agreeCheckbox.checked && university.value && email.value && emailDomain.value && studentId.value && major.value && password.value && role.value) {
            signupButton.disabled = false;
        } else {
            signupButton.disabled = true;
        }
    });

    sendEmailButton.addEventListener('click', function() {
        if (email.value === '' || emailDomain.value === '') {
            alert('이메일 혹은 이메일 도메인을 입력해주세요.');
            return;
        }
        const emailValue = email.value + '@' + emailDomain.value;
        alert('이메일 전송: ' + emailValue);
        console.log(emailValue);
        $.ajax({
            url: "/user/signup/email/verify",
            type: "POST",
            data: {
                email: emailValue
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
                if (data === "fail") {
                    alert("이미 가입된 이메일입니다.");
                } else if (data === "success") {
                    alert("이메일 전송 완료");
                } else if (data === "already") {
                    alert("이미 인증이 완료된 이메일입니다.(해당 계정으로 이미 가입된 이메일이 있습니다.)");
                } else if (data === "wait") {
                    alert("5분 후에 다시 시도해주세요.");
                } else {
                    console.log(data);
                    alert("서버 응답 처리 오류");
                }
            },
            error: function() {
                alert("이메일 전송 실패");
            }
        });
    });

    emailVerifyButton.addEventListener('click', function() {
        if (email.value === '' || emailDomain.value === '') {
            alert('이메일 혹은 이메일 도메인을 입력해주세요.');
            return;
        }
        if (emailVerify.value === '') {
            alert('이메일 인증번호를 입력해주세요.');
            return;
        }
        const emailValue = email.value + '@' + emailDomain.value;
        const emailVerifyValue = emailVerify.value;
        alert('이메일 인증번호 확인: ' + emailVerifyValue + ' (' + emailValue + ')');
        console.log(emailVerifyValue, emailValue);
        $.ajax({
            url: "/user/signup/email/verify/check",
            type: "POST",
            data: {
                email: emailValue,
                code: emailVerifyValue
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
                if (data === "fail") {
                    alert("인증번호가 일치하지 않습니다.");
                } else if (data === "success") {
                    alert("인증번호 확인 완료");
                } else if (data === "already") {
                    alert("이미 인증이 완료된 이메일입니다.(해당 계정으로 이미 가입된 이메일이 있습니다.)");
                } else if (data === "wait") {
                    alert("5분 후에 다시 시도해주세요.");
                } else {
                    console.log(data);
                    alert("서버 응답 처리 오류");
                }
            },
            error: function() {
                alert("이메일 인증번호 확인 실패");
            }
        });
    });
});
